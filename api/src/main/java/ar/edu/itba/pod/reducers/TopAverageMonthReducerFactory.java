package ar.edu.itba.pod.reducers;

import ar.edu.itba.pod.models.MonthReading;
import ar.edu.itba.pod.models.Utils;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class TopAverageMonthReducerFactory implements ReducerFactory<String, MonthReading,
        Map<String, Double>> {

    @Override
    public Reducer<MonthReading, Map<String, Double>> newReducer(String s) {
        return new TopAverageMonthReducer();
    }

    private static class TopAverageMonthReducer extends Reducer<MonthReading, Map<String, Double>> {
        Map<String, Double> averagePerMonth;

        @Override
        public void beginReduce() {
            averagePerMonth = new HashMap<>();
        }

        @Override
        public void reduce(MonthReading monthReading) {
            averagePerMonth.putIfAbsent(monthReading.getMonth(), 0.0);
            averagePerMonth.put(monthReading.getMonth(),
                    averagePerMonth.get(monthReading.getMonth()) + monthReading.getHourlyCount());
        }

        @Override
        public Map<String, Double> finalizeReduce() {
            for (Map.Entry<String, Double> entry : averagePerMonth.entrySet()) {
                int days = Utils.getDaysPerMonth(entry.getKey());
                entry.setValue(Math.floor(entry.getValue() * 100 / days) / 100);
            }

            return averagePerMonth;
        }
    }
}
