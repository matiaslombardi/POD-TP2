package ar.edu.itba.pod.client;

import ar.edu.itba.pod.collators.TopAverageMonthCollator;
import ar.edu.itba.pod.mappers.TopAverageMonthMapper;
import ar.edu.itba.pod.models.*;
import ar.edu.itba.pod.models.hazelcast.Reading;
import ar.edu.itba.pod.reducers.TopAverageMonthReducerFactory;
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

public class Query4 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query4.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // TODO: capaaz usar predicate
        LOGGER.info("tpe2-g6-parent Client Starting ...");
        // TODO: parse address from args
        HazelcastInstance hz = Utils.getHazelcastInstance();

        Utils.parseReadings("../../src/main/resources/data/readings.csv", hz);
        Utils.parseSensorsData("../../src/main/resources/data/sensors.csv", hz);

        IList<Reading> readingIList = hz.getList(Constants.READINGS_MAP);
        KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);

        JobTracker t = hz.getJobTracker("query-4");
        Job<String, Reading> job = t.newJob(source);

        // TODO: chequear #nomenclatura y parsear year
        ICompletableFuture<Collection<TopSensorMonth>> future = job.mapper(
                new TopAverageMonthMapper(2021))
                .reducer(new TopAverageMonthReducerFactory())
                .submit(new TopAverageMonthCollator(3));

        Collection<TopSensorMonth> result = future.get();

        result.forEach(v -> System.out.println(v.getSensor() + " " + v.getMonth() + " " + v.getAverage()));

        HazelcastClient.shutdownAll();
    }
}
