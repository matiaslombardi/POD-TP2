package ar.edu.itba.pod.collators;

import ar.edu.itba.pod.models.MaxSensorReading;
import com.hazelcast.mapreduce.Collator;

import java.util.HashMap;
import java.util.Map;

public class MaxReadingCollator implements Collator<Map.Entry<String, MaxSensorReading>,
        Map<String, MaxSensorReading>> {

    @Override
    public Map<String, MaxSensorReading> collate(Iterable<Map.Entry<String, MaxSensorReading>> iterable) {
        // TODO ver como sortear value y despues key o usar otra clase

        Map<String, MaxSensorReading> maxReadings = new HashMap<>();
        iterable.forEach(entry -> maxReadings.put(entry.getKey(), entry.getValue()));
        return maxReadings;
    }
}

