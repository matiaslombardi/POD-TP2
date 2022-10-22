package ar.edu.itba.pod.client;

import ar.edu.itba.pod.collators.MaxReadingCollator;
import ar.edu.itba.pod.mappers.MaxReadingMapper;
import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.MaxSensorReading;
import ar.edu.itba.pod.models.Reading;
import ar.edu.itba.pod.models.Utils;
import ar.edu.itba.pod.reducers.MaxReadingReducerFactory;
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

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query3 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query3.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LOGGER.info("tpe2-g6-parent Client Starting ...");

        // TODO: parse address from args
        HazelcastInstance hz = Utils.getHazelcastInstance();

        Utils.parseReadings("../../src/main/resources/data/readings.csv", hz);
        Utils.parseSensorsData("../../src/main/resources/data/sensors.csv", hz);

        IList<Reading> readingIList = hz.getList(Constants.READINGS_MAP);
        KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);

        JobTracker t = hz.getJobTracker("query-3");
        Job<String, Reading> job = t.newJob(source);

        ICompletableFuture<List<Map.Entry<String, MaxSensorReading>>> future = job.mapper(new MaxReadingMapper(700))
                .reducer(new MaxReadingReducerFactory())
                .submit(new MaxReadingCollator());

        List<Map.Entry<String, MaxSensorReading>> result = future.get();
        result.forEach((entry) ->
                System.out.printf("%s %d %d/%s/%d %s%n",
                    entry.getKey(), entry.getValue().getMaxReading(), entry.getValue().getmDate(), entry.getValue().getMonth(), entry.getValue().getYear(), entry.getValue().getTime())
        );

        QueryResponseWriter.writeMaxSensorReading(result);

        HazelcastClient.shutdownAll();
    }
}
