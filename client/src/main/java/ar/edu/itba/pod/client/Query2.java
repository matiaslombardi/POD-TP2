package ar.edu.itba.pod.client;

import ar.edu.itba.pod.collators.OrderByYearCollator;
import ar.edu.itba.pod.mappers.ReadingDateTypeMapper;
import ar.edu.itba.pod.models.*;
import ar.edu.itba.pod.models.hazelcast.Reading;
import ar.edu.itba.pod.models.responses.YearCountResponse;
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

import java.util.Collection;
import java.util.concurrent.ExecutionException;

public class Query2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query2.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LOGGER.info("tpe2-g6-parent Client Starting ...");

        // TODO: parse address from args
        HazelcastInstance hz = Utils.getHazelcastInstance();


        Utils.parseReadings("/Users/kazu/Documents/ITBA/POD/store-distribuido", hz);
        Utils.parseSensorsData("/Users/kazu/Documents/ITBA/POD/store-distribuido", hz);

        IList<Reading> readingIList = hz.getList(Constants.READINGS_MAP);
        KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);

        JobTracker t = hz.getJobTracker("query-2");
        Job<String, Reading> job = t.newJob(source);

        ICompletableFuture<Collection<YearCountResponse>> future = job.mapper(new ReadingDateTypeMapper())
                .reducer(new CountPerDateTypeReducerFactory())
                .submit(new OrderByYearCollator());

        Collection<YearCountResponse> result = future.get();
        result.forEach((response) -> System.out.printf("%d,%d,%d,%d%n",
                response.getYear(), response.getWeekdaysCount(), response.getWeekendCount(), response.getWeekdaysCount() + response.getWeekendCount()));

//        QueryResponseWriter.writeTotalCountPerYear(result);
        HazelcastClient.shutdownAll();
    }
}
