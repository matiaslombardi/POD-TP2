package ar.edu.itba.pod.reducers;

import ar.edu.itba.pod.models.MaxSensorReading;
import ar.edu.itba.pod.models.hazelcast.Reading;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class MaxReadingReducerFactory implements ReducerFactory<String, MaxSensorReading, MaxSensorReading> {
    @Override
    public Reducer<MaxSensorReading, MaxSensorReading> newReducer(String s) {
        return new MaxReadingReducer();
    }

    private static class MaxReadingReducer extends Reducer<MaxSensorReading, MaxSensorReading> {
        private MaxSensorReading maxReading;

        @Override
        public void beginReduce() {
            maxReading = new MaxSensorReading();
        }

        @Override
        public void reduce(MaxSensorReading value) {
            // TODO chequear comparacion de fechas -> Tira error cuando quiere levanter el month

            if (value.getMaxReading() == maxReading.getMaxReading() &&
                    value.isAfter(maxReading.getDate())) {
                maxReading.updateMax(value);
            }
            else if (value.getMaxReading() > maxReading.getMaxReading()) {
                maxReading.updateMax(value);
            }
        }

        @Override
        public MaxSensorReading finalizeReduce() {
            return maxReading;
        }
    }
}
