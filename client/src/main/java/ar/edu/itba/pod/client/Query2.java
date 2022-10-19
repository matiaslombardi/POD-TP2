package ar.edu.itba.pod.client;

import ar.edu.itba.pod.mappers.ReadingDateTypeMapper;
import ar.edu.itba.pod.mappers.ReadingNameMapper;
import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.Reading;
import ar.edu.itba.pod.models.YearCountValues;
import ar.edu.itba.pod.reducers.CountPerDateTypeReducerFactory;
import ar.edu.itba.pod.reducers.ReadingCountReducerFactory;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query1.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LOGGER.info("tpe2-g6-parent Client Starting ...");

        // TODO: parse address from args
        HazelcastInstance hz = Utils.getHazelcastInstance();

        Utils.parseReadings("../../src/main/resources/data/readings.csv", hz);
        Utils.parseSensorsData("../../src/main/resources/data/sensors.csv", hz);

        IMap<Integer, Reading> sensorIMap = hz.getMap(Constants.READINGS_MAP);
        KeyValueSource<Integer, Reading> source = KeyValueSource.fromMap(sensorIMap);

        JobTracker t = hz.getJobTracker("query-2");
        Job<Integer, Reading> job = t.newJob(source);

        ICompletableFuture<Map<Integer, YearCountValues>> future = job.mapper(new ReadingDateTypeMapper())
                .reducer(new CountPerDateTypeReducerFactory())
                .submit();

        Map<Integer, YearCountValues> result = future.get();
        result.forEach((k, v) -> System.out.printf("%d,%d,%d,%d%n",
                v.getYear(), v.getWeekdaysCount(), v.getWeekendCount(), v.getWeekdaysCount() + v.getWeekendCount()));

        HazelcastClient.shutdownAll();
    }
}
