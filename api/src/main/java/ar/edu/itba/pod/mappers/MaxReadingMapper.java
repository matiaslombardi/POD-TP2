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

public class MaxReadingMapper implements Mapper<String, Reading, String, MaxSensorReading>,
        HazelcastInstanceAware {

    private transient HazelcastInstance hz;

    private final long minValue;

    public MaxReadingMapper(long minValue) {
        this.minValue = minValue;
    }

    @Override
    public void map(String key, Reading value, Context<String, MaxSensorReading> context) {
        Sensor sensor = (Sensor) hz.getMap(Constants.SENSORS_MAP).get(value.getSensorId());
        if (sensor.getStatus().equals(Status.A) && value.getHourlyCounts() >= minValue) {
            LocalDateTime dateTime = LocalDateTime.of(
                    value.getYear(),
                    DaysPerMonth.valueOf(value.getMonth().toUpperCase()).getIndex(),
                    value.getmDate(), value.getTime(), 0
            );

            // TODO: no esta muy bueno que sea MaxSensorReading pero sino
            //  habria que crear otra clase y no tiene sentido
            context.emit(sensor.getDescription(), new MaxSensorReading(value.getHourlyCounts(),
                    dateTime));
        }
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hz) {
        this.hz = hz;
    }
}
