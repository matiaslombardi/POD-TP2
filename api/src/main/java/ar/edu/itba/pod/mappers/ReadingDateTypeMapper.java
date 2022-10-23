package ar.edu.itba.pod.mappers;

import ar.edu.itba.pod.models.*;
import ar.edu.itba.pod.models.hazelcast.Reading;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class ReadingDateTypeMapper implements Mapper<String, Reading, Integer, YearCountValues> {
    @Override
    public void map(String key, Reading reading, Context<Integer, YearCountValues> context) {
//        DateTypeReading value = new DateTypeReading(reading.getHourlyCounts(), false);
//        if (reading.getDay().equalsIgnoreCase("SATURDAY")
//                || reading.getDay().equalsIgnoreCase("SUNDAY")){
//            value.setWeekend(true);
//        }
//
//        context.emit(reading.getYear(), value);

        boolean isWeekend = reading.getDay().equalsIgnoreCase("SATURDAY")
                || reading.getDay().equalsIgnoreCase("SUNDAY");

        YearCountValues value = new YearCountValues(reading.getHourlyCounts(), isWeekend);
        context.emit(reading.getYear(), value);


    }
}
