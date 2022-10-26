package ar.edu.itba.pod.collators;

import ar.edu.itba.pod.models.responses.TotalReadingSensor;
import com.hazelcast.mapreduce.Collator;
import java.util.*;

public class TotalReadingCollator implements Collator<Map.Entry<String, Long>,
        Collection<TotalReadingSensor>> {

    @Override
    public Collection<TotalReadingSensor> collate(Iterable<Map.Entry<String, Long>> iterable) {
        Collection<TotalReadingSensor> sensors = new TreeSet<>();
        iterable.forEach(sensorEntry ->
                sensors.add(new TotalReadingSensor(sensorEntry.getKey(), sensorEntry.getValue())));

        return sensors;
    }
}
