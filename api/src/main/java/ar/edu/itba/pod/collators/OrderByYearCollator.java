package ar.edu.itba.pod.collators;

import ar.edu.itba.pod.models.TotalReadingSensor;
import ar.edu.itba.pod.models.YearCountValues;
import com.hazelcast.mapreduce.Collator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OrderByYearCollator implements Collator<Map.Entry<Integer, YearCountValues>,
        Map<Integer, YearCountValues>> {
    @Override
    public Map<Integer, YearCountValues> collate(Iterable<Map.Entry<Integer, YearCountValues>> iterable) {
        Map<Integer, YearCountValues> toReturn = new TreeMap<>(Comparator.reverseOrder());
        for (Map.Entry<Integer, YearCountValues> entry : iterable) {
            toReturn.put(entry.getKey(), entry.getValue());
        }
        return toReturn;
    }
}
