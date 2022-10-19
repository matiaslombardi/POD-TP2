package ar.edu.itba.pod.reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.ArrayList;
import java.util.List;

public class GroupingMillionsReducerFactory implements ReducerFactory<Long, String, List<String>> {

    @Override
    public Reducer<String, List<String>> newReducer(Long l) {
        return new GroupingMillionsReducer();
    }

    private static class GroupingMillionsReducer extends Reducer<String, List<String>> {
        private List<String> grouped;

        @Override
        public void beginReduce() {
            grouped = new ArrayList<>();
        }

        @Override
        public void reduce(String value) {
            grouped.add(value);
        }

        @Override
        public List<String> finalizeReduce() {
            return grouped;
        }
    }
}
