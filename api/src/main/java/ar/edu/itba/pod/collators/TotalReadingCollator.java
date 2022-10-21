package ar.edu.itba.pod.collators;

import ar.edu.itba.pod.models.TotalReadingSensor;
import com.hazelcast.mapreduce.Collator;
import java.util.*;

public class TotalReadingCollator implements Collator<Map.Entry<String, Long>,
        List<TotalReadingSensor>> {

    @Override
    public List<TotalReadingSensor> collate(Iterable<Map.Entry<String, Long>> iterable) {
        Set<TotalReadingSensor> sensors = new TreeSet<>();
        iterable.forEach(sensorEntry ->
                sensors.add(new TotalReadingSensor(sensorEntry.getKey(), sensorEntry.getValue())));

        // TODO: capaz retornar Collection y listo
        return new ArrayList<>(sensors);
    }
}
