package ar.edu.itba.pod.mappers;

import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.DaysPerMonth;
import ar.edu.itba.pod.models.MaxSensorReading;
import ar.edu.itba.pod.models.hazelcast.Reading;
import ar.edu.itba.pod.models.hazelcast.Sensor;
import ar.edu.itba.pod.models.hazelcast.Status;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import java.time.LocalDateTime;
import java.util.Map;

public class MaxReadingMapper implements Mapper<String, Reading, String, MaxSensorReading> {

    private final Map<Integer, Sensor> sensorMap;

    private final long minValue;

    public MaxReadingMapper(long minValue, Map<Integer, Sensor> sensorMap) {
        this.minValue = minValue;
        this.sensorMap = sensorMap;
    }

    @Override
    public void map(String key, Reading value, Context<String, MaxSensorReading> context) {
        Sensor sensor = sensorMap.get(value.getSensorId());
        if (sensor.getStatus().equals(Status.A) && value.getHourlyCounts() >= minValue) {
            LocalDateTime dateTime = LocalDateTime.of(
                    value.getYear(),
                    DaysPerMonth.valueOf(value.getMonth().toUpperCase()).getIndex(),
                    value.getmDate(), value.getTime(), 0
            );

            context.emit(sensor.getDescription(), new MaxSensorReading(value.getHourlyCounts(),
                    dateTime));
        }
    }

}
