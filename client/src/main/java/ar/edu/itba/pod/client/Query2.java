package ar.edu.itba.pod.client;

import ar.edu.itba.pod.collators.OrderByYearCollator;
import ar.edu.itba.pod.mappers.ReadingDateTypeMapper;
import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.Reading;
import ar.edu.itba.pod.models.Utils;
import ar.edu.itba.pod.models.YearCountValues;
import ar.edu.itba.pod.reducers.CountPerDateTypeReducerFactory;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query2.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LOGGER.info("tpe2-g6-parent Client Starting ...");

        // TODO: parse address from args
        HazelcastInstance hz = Utils.getHazelcastInstance();

        Utils.parseReadings("../../src/main/resources/data/readings.csv", hz);
        Utils.parseSensorsData("../../src/main/resources/data/sensors.csv", hz);

        IList<Reading> readingIList = hz.getList(Constants.READINGS_MAP);
        KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);

        JobTracker t = hz.getJobTracker("query-2");
        Job<String, Reading> job = t.newJob(source);

        ICompletableFuture<Map<Integer, YearCountValues>> future = job.mapper(new ReadingDateTypeMapper())
                .reducer(new CountPerDateTypeReducerFactory())
                .submit(new OrderByYearCollator());

        Map<Integer, YearCountValues> result = future.get();
        result.forEach((k, v) -> System.out.printf("%d,%d,%d,%d%n",
                k, v.getWeekdaysCount(), v.getWeekendCount(), v.getWeekdaysCount() + v.getWeekendCount()));

        QueryResponseWriter.writeTotalCountPerYear(result);
        HazelcastClient.shutdownAll();
    }
}
