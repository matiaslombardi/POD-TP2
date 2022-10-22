package ar.edu.itba.pod.reducers;

import ar.edu.itba.pod.models.DateTypeReading;
import ar.edu.itba.pod.models.YearCountValues;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class CountPerDateTypeReducerFactory implements ReducerFactory<Integer, DateTypeReading, YearCountValues> {

    @Override
    public Reducer<DateTypeReading, YearCountValues> newReducer(Integer integer) {
        return new CountPerDateTypeReducerFactory.CountPerDateTypeReducer();
    }

    private static class CountPerDateTypeReducer extends Reducer<DateTypeReading, YearCountValues> {
        private YearCountValues yearCountValues;

        @Override
        public void beginReduce() {
            yearCountValues = new YearCountValues(yearCountValues);
        }

        @Override
        public void reduce(DateTypeReading value) {
            if (value.isWeekend()) {
                yearCountValues.sumWeekendCount(value.getHourlyCount());
            } else {
                yearCountValues.sumWeekdaysCount(value.getHourlyCount());
            }
        }

        @Override
        public YearCountValues finalizeReduce() {
            return yearCountValues;
        }
    }
}
