package ar.edu.itba.pod.collators;

import ar.edu.itba.pod.models.responses.YearCountResponse;
import ar.edu.itba.pod.models.YearCountValues;
import com.hazelcast.mapreduce.Collator;

import java.util.*;

public class OrderByYearCollator implements Collator<Map.Entry<Integer, YearCountValues>,
        Collection<YearCountResponse>> {
    @Override
    public Collection<YearCountResponse> collate(Iterable<Map.Entry<Integer, YearCountValues>> iterable) {
        Collection<YearCountResponse> toReturn = new TreeSet<>();
        iterable.forEach(response -> toReturn.add(new YearCountResponse(response.getKey(), response.getValue())));
        return toReturn;
    }
}
