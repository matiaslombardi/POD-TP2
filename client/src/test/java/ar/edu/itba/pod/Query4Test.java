package ar.edu.itba.pod;

import ar.edu.itba.pod.assets.TestConstants;
import ar.edu.itba.pod.collators.TopAverageMonthCollator;
import ar.edu.itba.pod.mappers.TopAverageMonthMapper;
import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.hazelcast.Reading;
import ar.edu.itba.pod.models.hazelcast.Sensor;
import ar.edu.itba.pod.models.responses.TopSensorMonth;
import ar.edu.itba.pod.reducers.TopAverageMonthReducerFactory;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Query4Test {
//    private TestHazelcastFactory factory;
//    private HazelcastInstance member, client;
//
//    @BeforeEach
//    public void before() {
//        factory = new TestHazelcastFactory();
//
//        // Config
//        GroupConfig groupConfig = new GroupConfig()
//                .setName("g6")
//                .setPassword("g6-pass");
//
//        // Start cluster
//        Config config = new Config().setGroupConfig(groupConfig);
//        member = factory.newHazelcastInstance(config);
//
//        // CLient config
//        ClientConfig clientConfig = new ClientConfig().setGroupConfig(groupConfig);
//        client = factory.newHazelcastClient(clientConfig);
//    }
//
//    @Test
//    public void query4TestSuccessfully() throws ExecutionException, InterruptedException {
//        IList<Reading> readingIList = client.getList(Constants.READINGS_MAP);
//        readingIList.addAll(TestConstants.READINGS);
//
//        IMap<Integer, Sensor> sensorIMap = client.getMap(Constants.SENSORS_MAP);
//        sensorIMap.putAll(TestConstants.ALL_ACTIVE_SENSORS);
//
//        final KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);
//
//        JobTracker jobTracker = client.getJobTracker("query-4-test");
//        Job<String, Reading> job = jobTracker.newJob(source);
//
//        ICompletableFuture<Collection<TopSensorMonth>> future = job
//                .mapper(new TopAverageMonthMapper(TestConstants.YEAR_1))
//                .reducer(new TopAverageMonthReducerFactory())
//                .submit(new TopAverageMonthCollator(TestConstants.TOP_AMOUNT));
//
//        Collection<TopSensorMonth> actualValue = future.get();
//
//        assertEquals(2, actualValue.size());
//        assertEquals(new TopSensorMonth(TestConstants.SENSOR_3_NAME, "December", TestConstants.AVERAGE_1),
//                actualValue.toArray()[0]);
//        assertEquals(new TopSensorMonth(TestConstants.SENSOR_1_NAME, "December", TestConstants.AVERAGE_1),
//                actualValue.toArray()[1]);
//    }
//
//    @Test
//    public void query4TestWithActiveAndNonActiveSensors() throws ExecutionException, InterruptedException {
//        IList<Reading> readingIList = client.getList(Constants.READINGS_MAP);
//        readingIList.addAll(TestConstants.READINGS);
//
//        IMap<Integer, Sensor> sensorIMap = client.getMap(Constants.SENSORS_MAP);
//        sensorIMap.putAll(TestConstants.SENSORS);
//
//        final KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);
//
//        JobTracker jobTracker = client.getJobTracker("query-4-test");
//        Job<String, Reading> job = jobTracker.newJob(source);
//
//        ICompletableFuture<Collection<TopSensorMonth>> future = job
//                .mapper(new TopAverageMonthMapper(TestConstants.YEAR_1))
//                .reducer(new TopAverageMonthReducerFactory())
//                .submit(new TopAverageMonthCollator(TestConstants.TOP_AMOUNT));
//
//        Collection<TopSensorMonth> actualValue = future.get();
//
//        assertEquals(2, actualValue.size());
//        assertEquals(new TopSensorMonth(TestConstants.SENSOR_1_NAME, "December", TestConstants.AVERAGE_1),
//                actualValue.toArray()[0]);
//        assertEquals(new TopSensorMonth(TestConstants.SENSOR_2_NAME, "September", TestConstants.AVERAGE_2),
//                actualValue.toArray()[1]);
//    }
//
//    @Test
//    public void query4TestWithoutActiveSensors() throws ExecutionException, InterruptedException {
//        IList<Reading> readingIList = client.getList(Constants.READINGS_MAP);
//        readingIList.addAll(TestConstants.READINGS);
//
//        IMap<Integer, Sensor> sensorIMap = client.getMap(Constants.SENSORS_MAP);
//        sensorIMap.putAll(TestConstants.NONACTIVE_SENSORS);
//
//        final KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);
//
//        JobTracker jobTracker = client.getJobTracker("query-4-test");
//        Job<String, Reading> job = jobTracker.newJob(source);
//
//        ICompletableFuture<Collection<TopSensorMonth>> future = job
//                .mapper(new TopAverageMonthMapper(TestConstants.YEAR_1))
//                .reducer(new TopAverageMonthReducerFactory())
//                .submit(new TopAverageMonthCollator(TestConstants.TOP_AMOUNT));
//
//        Collection<TopSensorMonth> actualValue = future.get();
//
//        assertTrue(actualValue.isEmpty());
//    }
//
//    @AfterEach
//    public void after() {
//        factory.shutdownAll();
//    }
}
