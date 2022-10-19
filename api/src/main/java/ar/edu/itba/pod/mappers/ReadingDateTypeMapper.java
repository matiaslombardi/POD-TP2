package ar.edu.itba.pod.mappers;

import ar.edu.itba.pod.models.*;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class ReadingDateTypeMapper implements Mapper<Integer, Reading, Integer, DateTypeReading>, HazelcastInstanceAware {

    private transient HazelcastInstance hz;
    @Override
    public void map(Integer key, Reading value, Context<Integer, DateTypeReading> context) {
        if (value.getDay().toUpperCase().equals("SATURDAY") || value.getDay().toUpperCase().equals("SUNDAY")){
            context.emit(value.getYear(), new DateTypeReading(value.getYear(), value.getHourlyCounts(), true));
            System.out.println("Emitiendo: " + value.getYear() + " " + value.getHourlyCounts() + " " + true);
        }
        else
            context.emit(value.getYear(), new DateTypeReading(value.getYear(), value.getHourlyCounts(), false));

    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hz) {
        this.hz = hz;
    }
}
