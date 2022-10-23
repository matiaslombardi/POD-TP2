package ar.edu.itba.pod.mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class GroupingMillionsMapper implements Mapper<String, Long, Long, String> {

    @Override
    public void map(String sensor, Long count, Context<Long, String> context) {
        long newKey = count / 1_000_000;
        if (newKey != 0)
            context.emit(newKey * 1_000_000, sensor);
    }
}
