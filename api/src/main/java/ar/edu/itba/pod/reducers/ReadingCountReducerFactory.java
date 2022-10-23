package ar.edu.itba.pod.reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class ReadingCountReducerFactory implements ReducerFactory<String, Long, Long> {

    @Override
    public Reducer<Long, Long> newReducer(String s) {
        return new ReadingCountReducer();
    }

    private static class ReadingCountReducer extends Reducer<Long, Long> {
        private long count;
        @Override
        public void beginReduce() {
            count = 0;
        }

        @Override
        public void reduce(Long value) {
            count += value;
        }

        @Override
        public Long finalizeReduce() {
            return count;
        }
    }
}
