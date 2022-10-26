package ar.edu.itba.pod.mappers;

import ar.edu.itba.pod.models.*;
import ar.edu.itba.pod.models.hazelcast.Reading;
import ar.edu.itba.pod.models.hazelcast.Sensor;
import ar.edu.itba.pod.models.hazelcast.Status;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import java.util.Map;

public class TopAverageMonthMapper implements Mapper<String, Reading, String, MonthReading> {
    private final int year;
    private final Map<Integer, Sensor> sensorMap;

    public TopAverageMonthMapper(int year, Map<Integer, Sensor> sensorMap) {
        this.year = year;
        this.sensorMap = sensorMap;
    }

    @Override
    public void map(String key, Reading reading, Context<String, MonthReading> context) {
        Sensor sensor = sensorMap.get(reading.getSensorId());
        if (sensor.getStatus().equals(Status.A) && reading.getYear() == this.year)
            context.emit(sensor.getDescription(),
                    new MonthReading(reading.getMonth(), reading.getHourlyCounts()));
    }
}
