package ar.edu.itba.pod.mappers;

import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.hazelcast.Reading;
import ar.edu.itba.pod.models.hazelcast.Sensor;
import ar.edu.itba.pod.models.hazelcast.Status;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import java.util.Map;

public class ReadingNameMapper implements Mapper<String, Reading, String, Long> {

    private final Map<Integer, Sensor> sensorMap;

    public ReadingNameMapper(Map<Integer, Sensor> sensorMap) {
        this.sensorMap = sensorMap;
    }

    @Override
    public void map(String key, Reading value, Context<String, Long> context) {
        Sensor sensor = sensorMap.get(value.getSensorId());
        if (sensor.getStatus().equals(Status.A))
            context.emit(sensor.getDescription(), value.getHourlyCounts());
    }
}
