package ar.edu.itba.pod.collators;

import ar.edu.itba.pod.models.responses.MillionsPairResponse;
import com.hazelcast.mapreduce.Collator;

import java.util.*;
import java.util.stream.Collectors;

public class MillionsPairCollator implements Collator<Map.Entry<Long, List<String>>,
        Collection<MillionsPairResponse>> {
    @Override
    public Collection<MillionsPairResponse> collate(Iterable<Map.Entry<Long, List<String>>> iterable) {
        Collection<MillionsPairResponse> toReturn = new TreeSet<>();

        iterable.forEach(entry -> {
            if (entry.getValue().size() < 2)
                return;

            Long key = entry.getKey();
            List<String> values = entry.getValue().stream().sorted().collect(Collectors.toList());

            for (int i = 0; i < values.size(); i++) {
                for (int j = i + 1; j < values.size(); j++)
                    toReturn.add(new MillionsPairResponse(key, values.get(j), values.get(i)));
            }
        });

        return toReturn;
    }
}
