package ar.edu.itba.pod.client;

import ar.edu.itba.pod.collators.MillionsPairCollator;
import ar.edu.itba.pod.mappers.GroupingMillionsMapper;
import ar.edu.itba.pod.mappers.ReadingNameMapper;
import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.Pair;
import ar.edu.itba.pod.models.Reading;
import ar.edu.itba.pod.models.Utils;
import ar.edu.itba.pod.reducers.GroupingMillionsReducerFactory;
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

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query5 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Query5.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LOGGER.info("tpe2-g6-parent Client Starting ...");

        // TODO: parse address from args
        HazelcastInstance hz = Utils.getHazelcastInstance();

        //TODO: loggear -> inicio la lectura del archivo
        Utils.parseReadings("../../src/main/resources/data/readings.csv", hz);
        Utils.parseSensorsData("../../src/main/resources/data/sensors.csv", hz);
        //TODO: loggear -> fin lectura del archivo

        IMap<Integer, Reading> rawReadings = hz.getMap(Constants.READINGS_MAP);
        KeyValueSource<Integer, Reading> rawSource = KeyValueSource.fromMap(rawReadings);

        JobTracker t = hz.getJobTracker("query-5-p1");
        Job<Integer, Reading> countingJob = t.newJob(rawSource);


        ICompletableFuture<Map<String, Long>> futureCounted = countingJob.mapper(new ReadingNameMapper())
                .reducer(new ReadingCountReducerFactory())
                .submit();

        Map<String, Long> countedResult = futureCounted.get();
        // TODO: poner en constante
        IMap<String, Long> countedResultHz = hz.getMap("g6-count-sensors");
        countedResultHz.putAll(countedResult);

        KeyValueSource<String, Long> countedSource = KeyValueSource.fromMap(countedResultHz);
        Job<String, Long> groupingJob = t.newJob(countedSource);

        // TODO: agregar la parte de millones capaz en un predicate
        ICompletableFuture<Map<Long, List<Pair>>> futureGrouped = groupingJob.mapper(
                        new GroupingMillionsMapper())
                .reducer(new GroupingMillionsReducerFactory())
                .submit(new MillionsPairCollator());

        Map<Long, List<Pair>> groupedResult = futureGrouped.get();

        groupedResult.forEach((k, v) -> {
            v.forEach(p ->
                System.out.println(k + " " + p.getFirst() + " " + p.getSecond())
            );
        });

        HazelcastClient.shutdownAll();
    }
}
