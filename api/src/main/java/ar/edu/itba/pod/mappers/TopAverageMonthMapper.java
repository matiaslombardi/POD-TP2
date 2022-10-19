package ar.edu.itba.pod.mappers;

import ar.edu.itba.pod.models.*;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class TopAverageMonthMapper implements Mapper<Integer, Reading, String, MonthReading>,
        HazelcastInstanceAware {

    private transient HazelcastInstance hz;

    private final int year;

    public TopAverageMonthMapper(int year) {
        this.year = year;
    }

    @Override
    public void map(Integer integer, Reading reading, Context<String, MonthReading> context) {
        Sensor sensor = (Sensor) hz.getMap(Constants.SENSORS_MAP).get(reading.getSensorId());
        if (sensor.getStatus().equals(Status.A) && reading.getYear() == this.year)
            context.emit(sensor.getDescription(),
                    new MonthReading(reading.getMonth(), reading.getHourlyCounts()));
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.hz = hazelcastInstance;
    }
}
