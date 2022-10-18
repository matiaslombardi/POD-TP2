package ar.edu.itba.pod.reducers;

import ar.edu.itba.pod.models.MaxSensorReading;
import ar.edu.itba.pod.models.Reading;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

// TODO: ver de poner otras clases auxiliares
public class MaxReadingReducerFactory implements ReducerFactory<String, Reading, MaxSensorReading> {
    @Override
    public Reducer<Reading, MaxSensorReading> newReducer(String s) {
        return new MaxReadingReducer();
    }

    private static class MaxReadingReducer extends Reducer<Reading, MaxSensorReading> {
        private MaxSensorReading maxReading;

        @Override
        public void beginReduce() {
            maxReading = new MaxSensorReading();
        }

        @Override
        public void reduce(Reading value) {
            if (value.getHourlyCounts() > maxReading.getMaxReading()) {
                maxReading.updateMax(value);
            }
        }

        @Override
        public MaxSensorReading finalizeReduce() {
            return maxReading;
        }
    }
}
