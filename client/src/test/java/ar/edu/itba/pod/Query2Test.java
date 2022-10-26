package ar.edu.itba.pod;

import ar.edu.itba.pod.assets.TestConstants;
import ar.edu.itba.pod.collators.OrderByYearCollator;
import ar.edu.itba.pod.mappers.ReadingDateTypeMapper;
import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.YearCountValues;
import ar.edu.itba.pod.models.hazelcast.Reading;
import ar.edu.itba.pod.models.hazelcast.Sensor;
import ar.edu.itba.pod.models.responses.YearCountResponse;
import ar.edu.itba.pod.reducers.CountPerDateTypeReducerFactory;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.test.TestHazelcastFactory;
import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Query2Test {

    private TestHazelcastFactory factory;
    private HazelcastInstance member, client;

    @BeforeEach
    public void before() {
        factory = new TestHazelcastFactory();

        // Config
        GroupConfig groupConfig = new GroupConfig()
                .setName("g6")
                .setPassword("g6-pass");

        // Start cluster
        Config config = new Config().setGroupConfig(groupConfig);
        member = factory.newHazelcastInstance(config);

        // CLient config
        ClientConfig clientConfig = new ClientConfig().setGroupConfig(groupConfig);
        client = factory.newHazelcastClient(clientConfig);
    }

    @Test
    public void query2TestSuccessfully() throws ExecutionException, InterruptedException {
        IList<Reading> readingIList = client.getList(Constants.READINGS_MAP);
        readingIList.addAll(TestConstants.READINGS);

        IMap<Integer, Sensor> sensorIMap = client.getMap(Constants.SENSORS_MAP);
        sensorIMap.putAll(TestConstants.SENSORS);

        final KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);

        JobTracker jobTracker = client.getJobTracker("query-2-test");
        Job<String, Reading> job = jobTracker.newJob(source);

        ICompletableFuture<Collection<YearCountResponse>> future = job
                .mapper(new ReadingDateTypeMapper())
                .reducer(new CountPerDateTypeReducerFactory())
                .submit(new OrderByYearCollator());

        Collection<YearCountResponse> actualValue = future.get();

        assertEquals(3, actualValue.size());
        System.out.println(actualValue);
        assertEquals(new YearCountResponse(2022, new YearCountValues(29000, false)), actualValue.toArray()[0]);
        assertEquals(new YearCountResponse(2021, new YearCountValues() {{
            setWeekdaysCount(135000);
            setWeekendCount(60000);
        }}), actualValue.toArray()[1]);
        assertEquals(new YearCountResponse(2020, new YearCountValues(18000, false)), actualValue.toArray()[2]);
    }

    @AfterEach
    public void after() {
        factory.shutdownAll();
    }
}
