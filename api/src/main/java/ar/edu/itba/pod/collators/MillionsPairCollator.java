package ar.edu.itba.pod.collators;

import ar.edu.itba.pod.models.Pair;
import com.hazelcast.mapreduce.Collator;

import java.util.*;
import java.util.stream.Collectors;

public class MillionsPairCollator implements Collator<Map.Entry<Long, List<String>>, Map<Long, List<Pair>>> {
    @Override
    public Map<Long, List<Pair>> collate(Iterable<Map.Entry<Long, List<String>>> iterable) {
        Map<Long, List<Pair>> toReturn = new HashMap<>();

        iterable.forEach(entry -> {
            if (entry.getValue().size() < 2)
                return;

            Long key = entry.getKey();
            List<String> values = entry.getValue().stream().sorted().collect(Collectors.toList());

            List<Pair> pairValues = new ArrayList<>();
            for (int i = 0; i < values.size(); i++) {
                for (int j = i + 1; j < values.size(); j++)
                    pairValues.add(new Pair(values.get(i), values.get(j)));
            }

            toReturn.put(key, pairValues);
        });

        return toReturn;
    }
}
