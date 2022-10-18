package ar.edu.itba.pod.client;

import ar.edu.itba.pod.mappers.MaxReadingMapper;
import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.MaxSensorReading;
import ar.edu.itba.pod.models.Reading;
import ar.edu.itba.pod.reducers.MaxReadingReducerFactory;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query3 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query1.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LOGGER.info("tpe2-g6-parent Client Starting ...");

        // TODO: parse address from args
        HazelcastInstance hz = Utils.getHazelcastInstance();

        Utils.parseReadings("../../src/main/resources/data/readings.csv", hz);
        Utils.parseSensorsData("../../src/main/resources/data/sensors.csv", hz);

        IMap<Integer, Reading> sensorIMap = hz.getMap(Constants.READINGS_MAP);
        KeyValueSource<Integer, Reading> source = KeyValueSource.fromMap(sensorIMap);

        JobTracker t = hz.getJobTracker("query-3");
        Job<Integer, Reading> job = t.newJob(source);

        ICompletableFuture<Map<String, MaxSensorReading>> future = job.mapper(new MaxReadingMapper(700))
                .reducer(new MaxReadingReducerFactory())
                .submit();

        Map<String, MaxSensorReading> result = future.get();
        result.forEach((k, v) -> System.out.println(k + " " + v.getMaxReading()));

        HazelcastClient.shutdownAll();
    }
}
