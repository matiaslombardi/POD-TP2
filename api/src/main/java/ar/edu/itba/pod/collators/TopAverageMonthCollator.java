package ar.edu.itba.pod.collators;

import ar.edu.itba.pod.models.responses.TopSensorMonth;
import com.hazelcast.mapreduce.Collator;

import java.util.*;
import java.util.stream.Collectors;

public class TopAverageMonthCollator implements Collator<Map.Entry<String, Map<String, Double>>,
        Collection<TopSensorMonth>> {

    private final int n;

    public TopAverageMonthCollator(int n) {
        this.n = n;
    }

    @Override
    public Collection<TopSensorMonth> collate(Iterable<Map.Entry<String, Map<String, Double>>> iterable) {
        Collection<TopSensorMonth> topSensors = new TreeSet<>();
        iterable.forEach(sensorEntry -> {
            String sensor = sensorEntry.getKey();
            Map.Entry<String, Double> maxValueMonth = sensorEntry.getValue().entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .orElseThrow(RuntimeException::new);

            topSensors.add(new TopSensorMonth(sensor, maxValueMonth.getKey(),
                    maxValueMonth.getValue()));
        });
        return topSensors.stream().limit(n).collect(Collectors.toList());
    }
}
