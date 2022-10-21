package ar.edu.itba.pod.mappers;

import ar.edu.itba.pod.models.*;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class ReadingDateTypeMapper implements Mapper<String, Reading, Integer, DateTypeReading> {
    @Override
    public void map(String key, Reading reading, Context<Integer, DateTypeReading> context) {
        DateTypeReading value = new DateTypeReading(reading.getHourlyCounts(), false);
        if (reading.getDay().equalsIgnoreCase("SATURDAY")
                || reading.getDay().equalsIgnoreCase("SUNDAY")){
            value.setWeekend(true);
        }

        context.emit(reading.getYear(), value);
    }
}
