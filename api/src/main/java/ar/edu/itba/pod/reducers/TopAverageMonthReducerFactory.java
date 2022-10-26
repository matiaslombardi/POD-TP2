package ar.edu.itba.pod.reducers;

import ar.edu.itba.pod.models.DaysPerMonth;
import ar.edu.itba.pod.models.MonthReading;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import java.util.HashMap;
import java.util.Map;

public class TopAverageMonthReducerFactory implements ReducerFactory<String, MonthReading,
        Map<String, Double>> {

    @Override
    public Reducer<MonthReading, Map<String, Double>> newReducer(String s) {
        return new TopAverageMonthReducer();
    }

    private static class TopAverageMonthReducer extends Reducer<MonthReading, Map<String, Double>> {
        private Map<String, Double> averagePerMonth;

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
                int days = getDaysPerMonth(entry.getKey());
                entry.setValue(Math.floor(entry.getValue() * 100 / days) / 100);
            }

            return averagePerMonth;
        }

        private int getDaysPerMonth(String month) {
            return DaysPerMonth.valueOf(month.toUpperCase()).getDays();
        }
    }
}
