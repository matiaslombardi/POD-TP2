package ar.edu.itba.pod.client;

import ar.edu.itba.pod.mappers.ReadingNameMapper;
import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.Reading;
import ar.edu.itba.pod.models.Utils;
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

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query1.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LOGGER.info("tpe2-g6-parent Client Starting ...");

        // TODO: parse address from args
        HazelcastInstance hz = Utils.getHazelcastInstance();

        //TODO: loggear -> inicio la lectura del archivo
        Utils.parseReadings("../../src/main/resources/data/readings.csv", hz);
        Utils.parseSensorsData("../../src/main/resources/data/sensors.csv", hz);
        //TODO: loggear -> fin lectura del archivo

        IMap<Integer, Reading> sensorIMap = hz.getMap(Constants.READINGS_MAP);
        KeyValueSource<Integer, Reading> source = KeyValueSource.fromMap(sensorIMap);

        JobTracker t = hz.getJobTracker("query-1");
        Job<Integer, Reading> job = t.newJob(source);



        ICompletableFuture<Map<String, Long>> future = job.mapper(new ReadingNameMapper())
                .reducer(new ReadingCountReducerFactory())
                .submit();

        Map<String, Long> result = future.get();


        result.forEach((k, v) -> System.out.println(k + " " + v));

        HazelcastClient.shutdownAll();
    }
}
