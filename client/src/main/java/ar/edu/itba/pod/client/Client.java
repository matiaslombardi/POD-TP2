package ar.edu.itba.pod.client;

import ar.edu.itba.pod.collators.MaxReadingCollator;
import ar.edu.itba.pod.collators.OrderByYearCollator;
import ar.edu.itba.pod.collators.TotalReadingCollator;
import ar.edu.itba.pod.mappers.MaxReadingMapper;
import ar.edu.itba.pod.mappers.ReadingDateTypeMapper;
import ar.edu.itba.pod.mappers.ReadingNameMapper;
import ar.edu.itba.pod.models.*;
import ar.edu.itba.pod.models.hazelcast.Reading;
import ar.edu.itba.pod.models.responses.MaxSensorResponse;
import ar.edu.itba.pod.models.responses.YearCountResponse;
import ar.edu.itba.pod.reducers.CountPerDateTypeReducerFactory;
import ar.edu.itba.pod.reducers.MaxReadingReducerFactory;
import ar.edu.itba.pod.reducers.ReadingCountReducerFactory;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        LOGGER.info("tpe2-g6-parent Client Starting ...");

        ClientParser parser = new ClientParser();
        parser.parse();

        HazelcastInstance hz = Utils.getHazelcastInstance();


        Utils.parseReadings(parser.getInPath(), hz);
        Utils.parseSensorsData(parser.getOutPath(), hz);

        IList<Reading> readingIList = hz.getList(Constants.READINGS_MAP);
        KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);

        switch (parser.getQuery()) {
            case 1:
//                runQuery1(hz,source);
                break;
            case 2:
//                 runQuery2(hz,source);
                break;
            case 3:
//                 runQuery3(hz,source, parser.getMin());
                break;
            case 4:
//                runQuery4(hz,source, parser.getYear(), parser.getN());
                break;
            case 5:
                // TODO: query5
                break;
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

        // TODO: poner el generico
        QueryResponseWriter.writeReadingCount(result);
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

//        QueryResponseWriter.writeTotalCountPerYear(result);
    }

    public static void runQuery3(HazelcastInstance hz, KeyValueSource<String, Reading> source, int minValue) throws InterruptedException, ExecutionException {
        JobTracker t = hz.getJobTracker("query-3");
        Job<String, Reading> job = t.newJob(source);

        ICompletableFuture<Collection<MaxSensorResponse>> future = job.mapper(new MaxReadingMapper(minValue))
                .reducer(new MaxReadingReducerFactory())
                .submit(new MaxReadingCollator());

        Collection<MaxSensorResponse> result = future.get();
//        result.forEach((response) ->
//                System.out.printf("%s %d %d/%s/%d %s%n",
//                        response.getSensor(), response.getReading().getMaxReading(), response.getReading().getmDate(), response.getReading().getMonth(), response.getReading().getYear(), response.getReading().getTime())
//        );

        QueryResponseWriter.writeMaxSensorReading(result);
    }

    public static void runQuery4(HazelcastInstance hz, KeyValueSource<String, Reading> source, int year, int topAmount) throws InterruptedException, ExecutionException {
        JobTracker t = hz.getJobTracker("query-4");
        Job<String, Reading> job = t.newJob(source);

        // TODO: chequear #nomenclatura y parsear year
//        ICompletableFuture<List<TopSensorMonth>> future = job.mapper(
//                        new TopAverageMonthMapper(year))
//                .reducer(new TopAverageMonthReducerFactory())
//                .submit(new TopAverageMonthCollator(topAmount));

//        List<TopSensorMonth> result = future.get();

//        result.forEach(v -> System.out.println(v.getSensor() + " " + v.getMonth() + " " + v.getAverage()));
    }


}
