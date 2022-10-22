package ar.edu.itba.pod.collators;

import ar.edu.itba.pod.models.MaxSensorReading;
import ar.edu.itba.pod.models.responses.MaxSensorResponse;
import com.hazelcast.mapreduce.Collator;

import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;

public class MaxReadingCollator implements Collator<Map.Entry<String, MaxSensorReading>,
        Collection<MaxSensorResponse>> {

    @Override
    public Collection<MaxSensorResponse> collate(Iterable<Map.Entry<String, MaxSensorReading>> iterable) {
        Collection<MaxSensorResponse> toReturn = new TreeSet<>();
        iterable.forEach(entry ->
                toReturn.add(new MaxSensorResponse(entry.getKey(), entry.getValue())));

        return toReturn;
    }
}

