package ar.edu.itba.pod.reducers;

import ar.edu.itba.pod.models.YearCountValues;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class CountPerDateTypeReducerFactory implements ReducerFactory<Integer, YearCountValues, YearCountValues> {

    @Override
    public Reducer<YearCountValues, YearCountValues> newReducer(Integer integer) {
        return new CountPerDateTypeReducerFactory.CountPerDateTypeReducer();
    }

    private static class CountPerDateTypeReducer extends Reducer<YearCountValues, YearCountValues> {
        private YearCountValues yearCountValues;

        @Override
        public void beginReduce() {
            yearCountValues = new YearCountValues();
        }

        @Override
        public void reduce(YearCountValues value) {
            yearCountValues.sumValues(value);
        }

        @Override
        public YearCountValues finalizeReduce() {
            return yearCountValues;
        }
    }
}
