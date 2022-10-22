package ar.edu.itba.pod.collators;

import ar.edu.itba.pod.models.MaxReadingComparator;
import ar.edu.itba.pod.models.MaxSensorReading;
import com.hazelcast.mapreduce.Collator;

import java.util.*;

public class MaxReadingCollator implements Collator<Map.Entry<String, MaxSensorReading>,
        List<Map.Entry<String, MaxSensorReading>>> {

    @Override
    public List<Map.Entry<String, MaxSensorReading>> collate(Iterable<Map.Entry<String, MaxSensorReading>> iterable) {
        // TODO chequear si esta bien hacer una lista de entries
        Set<Map.Entry<String, MaxSensorReading>> maxReadingsSet = new TreeSet<>(new MaxReadingComparator<>());
        iterable.forEach(maxReadingsSet::add);

        return new ArrayList<>(maxReadingsSet);
    }
}

