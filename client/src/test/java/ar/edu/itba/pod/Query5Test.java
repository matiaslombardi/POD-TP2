package ar.edu.itba.pod;

import ar.edu.itba.pod.assets.TestConstants;
import ar.edu.itba.pod.collators.MillionsPairCollator;
import ar.edu.itba.pod.mappers.GroupingMillionsMapper;
import ar.edu.itba.pod.mappers.ReadingNameMapper;
import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.hazelcast.Reading;
import ar.edu.itba.pod.models.hazelcast.Sensor;
import ar.edu.itba.pod.models.responses.MillionsPairResponse;
import ar.edu.itba.pod.reducers.GroupingMillionsReducerFactory;
import ar.edu.itba.pod.reducers.ReadingCountReducerFactory;
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
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Query5Test {
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
//    public void query5TestSuccessfully() throws ExecutionException, InterruptedException {
//        IList<Reading> readingIList = client.getList(Constants.READINGS_MAP);
//        readingIList.addAll(TestConstants.MILLIONS_READINGS_1);
//
//        IMap<Integer, Sensor> sensorIMap = client.getMap(Constants.SENSORS_MAP);
//        sensorIMap.putAll(TestConstants.MILLIONS_SENSORS);
//
//        final KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);
//
//        JobTracker jobTracker = client.getJobTracker("query-5-test");
//        Job<String, Reading> countingJob = jobTracker.newJob(source);
//
//        ICompletableFuture<Map<String, Long>> futureCounted = countingJob
//                .mapper(new ReadingNameMapper(sensorIMap))
//                .reducer(new ReadingCountReducerFactory())
//                .submit();
//
//        Map<String, Long> countedResult = futureCounted.get();
//        IMap<String, Long> countedResultHz = client.getMap(Constants.SENSORS_COUNT_MAP);
//        countedResultHz.putAll(countedResult);
//
//        KeyValueSource<String, Long> countedSource = KeyValueSource.fromMap(countedResultHz);
//        Job<String, Long> groupingJob = jobTracker.newJob(countedSource);
//
//        ICompletableFuture<Collection<MillionsPairResponse>> futureGrouped = groupingJob
//                .mapper(new GroupingMillionsMapper())
//                .reducer(new GroupingMillionsReducerFactory())
//                .submit(new MillionsPairCollator());
//
//        Collection<MillionsPairResponse> actualValue = futureGrouped.get();
//
//        System.out.println(actualValue);
//        assertEquals(2, actualValue.size());
//        assertEquals(new MillionsPairResponse(2000000, TestConstants.SENSOR_1_NAME, TestConstants.SENSOR_3_NAME), actualValue.toArray()[0]);
//    }
//
//    @Test
//    public void query5TestWithActiveAndNonActiveSensors() throws ExecutionException, InterruptedException {
//        IList<Reading> readingIList = client.getList(Constants.READINGS_MAP);
//        readingIList.addAll(TestConstants.MILLIONS_READINGS_2);
//
//        IMap<Integer, Sensor> sensorIMap = client.getMap(Constants.SENSORS_MAP);
//        sensorIMap.putAll(TestConstants.SENSORS);
//
//        final KeyValueSource<String, Reading> source = KeyValueSource.fromList(readingIList);
//
//        JobTracker jobTracker = client.getJobTracker("query-5-test");
//        Job<String, Reading> countingJob = jobTracker.newJob(source);
//
//        ICompletableFuture<Map<String, Long>> futureCounted = countingJob
//                .mapper(new ReadingNameMapper(sensorIMap))
//                .reducer(new ReadingCountReducerFactory())
//                .submit();
//
//        Map<String, Long> countedResult = futureCounted.get();
//        IMap<String, Long> countedResultHz = client.getMap(Constants.SENSORS_COUNT_MAP);
//        countedResultHz.putAll(countedResult);
//
//        KeyValueSource<String, Long> countedSource = KeyValueSource.fromMap(countedResultHz);
//        Job<String, Long> groupingJob = jobTracker.newJob(countedSource);
//
//        ICompletableFuture<Collection<MillionsPairResponse>> futureGrouped = groupingJob
//                .mapper(new GroupingMillionsMapper())
//                .reducer(new GroupingMillionsReducerFactory())
//                .submit(new MillionsPairCollator());
//
//        Collection<MillionsPairResponse> actualValue = futureGrouped.get();
//
//        assertTrue(actualValue.isEmpty());
//    }
//
//    @AfterEach
//    public void after() {
//        factory.shutdownAll();
//    }
}
