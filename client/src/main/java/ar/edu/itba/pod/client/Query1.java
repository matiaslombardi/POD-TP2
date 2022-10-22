package ar.edu.itba.pod.client;

import ar.edu.itba.pod.collators.TotalReadingCollator;
import ar.edu.itba.pod.mappers.ReadingNameMapper;
import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.Reading;
import ar.edu.itba.pod.models.TotalReadingSensor;
import ar.edu.itba.pod.models.Utils;
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

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Query1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query1.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // TODO capaz tener un solo main que en base a un parametro llama a las queries
        LOGGER.info("tpe2-g6-parent Client Starting ...");

        // TODO: parse address from args
        HazelcastInstance hz = Utils.getHazelcastInstance();

        //TODO: loggear -> inicio la lectura del archivo
        Utils.parseReadings("../../src/main/resources/data/readings.csv", hz);
        Utils.parseSensorsData("../../src/main/resources/data/sensors.csv", hz);
        //TODO: loggear -> fin lectura del archivo

        IList<Reading> readingIList = hz.getList(Constants.READINGS_MAP);
        KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);

        JobTracker t = hz.getJobTracker("query-1");
        Job<String, Reading> job = t.newJob(source);



        ICompletableFuture<List<TotalReadingSensor>> future = job.mapper(new ReadingNameMapper())
                .reducer(new ReadingCountReducerFactory())
                .submit(new TotalReadingCollator());

        List<TotalReadingSensor> result = future.get();


        result.forEach(value -> System.out.println(value.getSensor() + " " + value.getTotal()));

        QueryResponseWriter.writeReadingCount(result);

        HazelcastClient.shutdownAll();
    }
}
