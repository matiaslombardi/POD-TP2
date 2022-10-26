package ar.edu.itba.pod;

import ar.edu.itba.pod.assets.TestConstants;
import ar.edu.itba.pod.collators.MaxReadingCollator;
import ar.edu.itba.pod.mappers.MaxReadingMapper;
import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.MaxSensorReading;
import ar.edu.itba.pod.models.hazelcast.Reading;
import ar.edu.itba.pod.models.hazelcast.Sensor;
import ar.edu.itba.pod.models.responses.MaxSensorResponse;
import ar.edu.itba.pod.reducers.MaxReadingReducerFactory;
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

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Query3Test {
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
    public void query3TestSuccessfully() throws ExecutionException, InterruptedException {
        IList<Reading> readingIList = client.getList(Constants.READINGS_MAP);
        readingIList.addAll(TestConstants.READINGS);

        IMap<Integer, Sensor> sensorIMap = client.getMap(Constants.SENSORS_MAP);
        sensorIMap.putAll(TestConstants.SENSORS);

        final KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);

        JobTracker jobTracker = client.getJobTracker("query-3-test");
        Job<String, Reading> job = jobTracker.newJob(source);

        ICompletableFuture<Collection<MaxSensorResponse>> future = job
                .mapper(new MaxReadingMapper(TestConstants.MIN_VALUE_1))
                .reducer(new MaxReadingReducerFactory())
                .submit(new MaxReadingCollator());

        Collection<MaxSensorResponse> actualValue = future.get();

        System.out.println(actualValue);
        assertEquals( 2, actualValue.size());
        assertEquals(TestConstants.MAX_SENSOR_RESPONSE_1,
                actualValue.toArray()[0]);
        assertEquals(new MaxSensorResponse(TestConstants.SENSOR_2_NAME, new MaxSensorReading(40000, LocalDateTime.of(2021, 9, 9, 7, 0))),
                actualValue.toArray()[1]);
    }

    @Test
    public void query3TestWithHigherMinValue() throws ExecutionException, InterruptedException {
        IList<Reading> readingIList = client.getList(Constants.READINGS_MAP);
        readingIList.addAll(TestConstants.READINGS);

        IMap<Integer, Sensor> sensorIMap = client.getMap(Constants.SENSORS_MAP);
        sensorIMap.putAll(TestConstants.SENSORS);

        final KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);

        JobTracker jobTracker = client.getJobTracker("query-3-test");
        Job<String, Reading> job = jobTracker.newJob(source);

        ICompletableFuture<Collection<MaxSensorResponse>> future = job
                .mapper(new MaxReadingMapper(TestConstants.MIN_VALUE_2))
                .reducer(new MaxReadingReducerFactory())
                .submit(new MaxReadingCollator());

        Collection<MaxSensorResponse> actualValue = future.get();

        assertEquals(1, actualValue.size());
        assertEquals(TestConstants.MAX_SENSOR_RESPONSE_1, actualValue.toArray()[0]);
    }

    @Test
    public void query3TestWithoutActiveSensors() throws ExecutionException, InterruptedException {
        IList<Reading> readingIList = client.getList(Constants.READINGS_MAP);
        readingIList.addAll(TestConstants.READINGS);

        IMap<Integer, Sensor> sensorIMap = client.getMap(Constants.SENSORS_MAP);
        sensorIMap.putAll(TestConstants.NONACTIVE_SENSORS);

        final KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);

        JobTracker jobTracker = client.getJobTracker("query-3-test");
        Job<String, Reading> job = jobTracker.newJob(source);

        ICompletableFuture<Collection<MaxSensorResponse>> future = job
                .mapper(new MaxReadingMapper(TestConstants.MIN_VALUE_1))
                .reducer(new MaxReadingReducerFactory())
                .submit(new MaxReadingCollator());

        Collection<MaxSensorResponse> actualValue = future.get();

        assertTrue(actualValue.isEmpty());
    }

    @AfterEach
    public void after() {
        factory.shutdownAll();
    }
}
