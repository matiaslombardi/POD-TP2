package ar.edu.itba.pod.mappers;

import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.Reading;
import ar.edu.itba.pod.models.Sensor;
import ar.edu.itba.pod.models.Status;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class MaxReadingMapper implements Mapper<Integer, Reading, String, Reading>,
        HazelcastInstanceAware {

    private transient HazelcastInstance hz;

    private final long minValue;

    public MaxReadingMapper(long minValue) {
        this.minValue = minValue;
    }

    // TODO aca habria que filtrar por el minimo? o en el reducer?
    @Override
    public void map(Integer key, Reading value, Context<String, Reading> context) {
        Sensor sensor = (Sensor) hz.getMap(Constants.SENSORS_MAP).get(value.getSensorId());
        if (sensor.getStatus().equals(Status.A) && value.getHourlyCounts() >= minValue)
            context.emit(sensor.getDescription(), value);
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hz) {
        this.hz = hz;
    }
}
