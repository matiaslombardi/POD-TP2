package ar.edu.itba.pod.mappers;

import ar.edu.itba.pod.models.*;
import ar.edu.itba.pod.models.hazelcast.Reading;
import ar.edu.itba.pod.models.hazelcast.Sensor;
import ar.edu.itba.pod.models.hazelcast.Status;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class TopAverageMonthMapper implements Mapper<String, Reading, String, MonthReading>,
        HazelcastInstanceAware {

    private transient HazelcastInstance hz;

    private final int year;

    public TopAverageMonthMapper(int year) {
        this.year = year;
    }

    @Override
    public void map(String key, Reading reading, Context<String, MonthReading> context) {
        Sensor sensor = (Sensor) hz.getMap(Constants.SENSORS_MAP).get(reading.getSensorId());
        // TODO capaz usar predicate
        if (sensor.getStatus().equals(Status.A) && reading.getYear() == this.year)
            context.emit(sensor.getDescription(),
                    new MonthReading(reading.getMonth(), reading.getHourlyCounts()));
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        this.hz = hazelcastInstance;
    }
}
