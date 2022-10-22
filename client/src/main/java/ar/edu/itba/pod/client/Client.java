package ar.edu.itba.pod.client;

import ar.edu.itba.pod.collators.*;
import ar.edu.itba.pod.mappers.*;
import ar.edu.itba.pod.models.*;
import ar.edu.itba.pod.models.hazelcast.Reading;
import ar.edu.itba.pod.models.responses.MaxSensorResponse;
import ar.edu.itba.pod.models.responses.MillionsPairResponse;
import ar.edu.itba.pod.models.responses.YearCountResponse;
import ar.edu.itba.pod.reducers.*;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    // TODO: falta hacer un combiner
    // TODO: fijarse ints y longs

    public static void main(String[] args) {
        LOGGER.info("tpe2-g6-parent Client Starting ...");

        ClientParser parser = new ClientParser();
        parser.parse();

        HazelcastInstance hz = Utils.getHazelcastInstance();


        Utils.parseReadings(parser.getInPath(), hz);
        Utils.parseSensorsData(parser.getOutPath(), hz);

        IList<Reading> readingIList = hz.getList(Constants.READINGS_MAP);
        KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);

        try {
            switch (parser.getQuery()) {
                case 1:
                    runQuery1(hz, source);
                    break;
                case 2:
                    runQuery2(hz, source);
                    break;
                case 3:
                    runQuery3(hz, source, parser.getMin());
                    break;
                case 4:
                    runQuery4(hz, source, parser.getYear(), parser.getN());
                    break;
                case 5:
                    runQuery5(hz, source);
                    break;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            LOGGER.error("Future job wasn't able to finish");
        }

        HazelcastClient.shutdownAll();
    }

    public static void runQuery1(HazelcastInstance hz, KeyValueSource<String, Reading> source) throws InterruptedException, ExecutionException {
        JobTracker t = hz.getJobTracker("query-1");
        Job<String, Reading> job = t.newJob(source);

        ICompletableFuture<Collection<TotalReadingSensor>> future = job.mapper(new ReadingNameMapper())
                .reducer(new ReadingCountReducerFactory())
                .submit(new TotalReadingCollator());

        Collection<TotalReadingSensor> result = future.get();


        result.forEach(value -> System.out.println(value.getSensor() + " " + value.getTotal()));

        QueryResponseWriter.writeQueryResponse("query1.csv", result, new String[] {"Sensor", "Total_Count"});
    }

    public static void runQuery2(HazelcastInstance hz, KeyValueSource<String, Reading> source) throws InterruptedException, ExecutionException {
        JobTracker t = hz.getJobTracker("query-2");
        Job<String, Reading> job = t.newJob(source);

        ICompletableFuture<Collection<YearCountResponse>> future = job.mapper(new ReadingDateTypeMapper())
                .reducer(new CountPerDateTypeReducerFactory())
                .submit(new OrderByYearCollator());

        Collection<YearCountResponse> result = future.get();
        result.forEach((response) -> System.out.printf("%d,%d,%d,%d%n",
                response.getYear(), response.getWeekdaysCount(), response.getWeekendCount(), response.getWeekdaysCount() + response.getWeekendCount()));

        QueryResponseWriter.writeQueryResponse("query2.csv", result, new String[] {"Year", "Weekdays_Count", "Weekends_Count", "Total_Count"});
    }

    public static void runQuery3(HazelcastInstance hz, KeyValueSource<String, Reading> source, long minValue) throws InterruptedException, ExecutionException {
        JobTracker t = hz.getJobTracker("query-3");
        Job<String, Reading> job = t.newJob(source);

        ICompletableFuture<Collection<MaxSensorResponse>> future = job.mapper(new MaxReadingMapper(minValue))
                .reducer(new MaxReadingReducerFactory())
                .submit(new MaxReadingCollator());

        Collection<MaxSensorResponse> result = future.get();
        result.forEach((response) ->
                System.out.printf("%s %d %d/%s/%d %s%n",
                        response.getSensor(), response.getMaxReading(), response.getmDate(),
                        response.getMonth(), response.getYear(), response.getTime())
        );

        QueryResponseWriter.writeQueryResponse("query3.csv", result, new String[] {"Sensor", "Max_Reading_Count", "Max_Reading_DateTime"});
    }

    public static void runQuery4(HazelcastInstance hz, KeyValueSource<String, Reading> source, int year, int topAmount) throws InterruptedException, ExecutionException {
        JobTracker t = hz.getJobTracker("query-4");
        Job<String, Reading> job = t.newJob(source);

        // TODO: chequear #nomenclatura y parsear year
        ICompletableFuture<Collection<TopSensorMonth>> future = job.mapper(
                        new TopAverageMonthMapper(year))
                .reducer(new TopAverageMonthReducerFactory())
                .submit(new TopAverageMonthCollator(topAmount));

        Collection<TopSensorMonth> result = future.get();

        result.forEach(v -> System.out.println(v.getSensor() + " " + v.getMonth() + " " + v.getAverage()));

        QueryResponseWriter.writeQueryResponse("query4.csv", result, new String[]
                {"Sensor", "Month", "Max_Monthly_Avg"});

    }

    public static void runQuery5(HazelcastInstance hz, KeyValueSource<String, Reading> rawSource) throws ExecutionException, InterruptedException {
        JobTracker t = hz.getJobTracker("query-5");
        Job<String, Reading> countingJob = t.newJob(rawSource);


        ICompletableFuture<Map<String, Long>> futureCounted = countingJob.mapper(new ReadingNameMapper())
                .reducer(new ReadingCountReducerFactory())
                .submit();

        Map<String, Long> countedResult = futureCounted.get();
        // TODO: poner en constants el nombre de la coleccion
        IMap<String, Long> countedResultHz = hz.getMap("g6-count-sensors");
        countedResultHz.putAll(countedResult);

        KeyValueSource<String, Long> countedSource = KeyValueSource.fromMap(countedResultHz);
        Job<String, Long> groupingJob = t.newJob(countedSource);

        // TODO: agregar la parte de millones capaz en un predicate
        ICompletableFuture<Collection<MillionsPairResponse>> futureGrouped = groupingJob.mapper(
                        new GroupingMillionsMapper())
                .reducer(new GroupingMillionsReducerFactory())
                .submit(new MillionsPairCollator());

        Collection<MillionsPairResponse> groupedResult = futureGrouped.get();

        QueryResponseWriter.writeQueryResponse("query5.csv", groupedResult, new String[]
                {"Group", "Sensor A", "Sensor B"});
    }
}
