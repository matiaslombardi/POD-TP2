package ar.edu.itba.pod.mappers;

import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.Reading;
import ar.edu.itba.pod.models.Sensor;
import ar.edu.itba.pod.models.Status;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class ReadingNameMapper implements Mapper<String, Reading, String, Long>,
        HazelcastInstanceAware {

    private transient HazelcastInstance hz;

    @Override
    public void map(String key, Reading value, Context<String, Long> context) {
        Sensor sensor = (Sensor) hz.getMap(Constants.SENSORS_MAP).get(value.getSensorId());
        if (sensor.getStatus().equals(Status.A))
            context.emit(sensor.getDescription(), value.getHourlyCounts());
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hz) {
        this.hz = hz;
    }
}
