package ar.edu.itba.pod.collators;

import ar.edu.itba.pod.models.YearCountValues;
import com.hazelcast.mapreduce.Collator;

import java.util.Map;

public class CountPerDateTypeCollator implements Collator<Map<Integer, YearCountValues>, Map<Integer, YearCountValues>>  {
    @Override
    public Map<Integer, YearCountValues> collate(Iterable<Map<Integer, YearCountValues>> iterable) {
        return null;
    }
}
