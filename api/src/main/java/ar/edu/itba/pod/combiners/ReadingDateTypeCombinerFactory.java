package ar.edu.itba.pod.combiners;

import ar.edu.itba.pod.models.YearCountValues;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class ReadingDateTypeCombinerFactory implements CombinerFactory<Integer, YearCountValues, YearCountValues> {
    @Override
    public Combiner<YearCountValues, YearCountValues> newCombiner(Integer integer) {
        return new ReadingDateTypeCombiner();
    }

    private static class ReadingDateTypeCombiner extends Combiner<YearCountValues, YearCountValues> {
        private YearCountValues yearCountValues;

        @Override
        public void beginCombine() {
            yearCountValues = new YearCountValues();
        }

        @Override
        public void combine(YearCountValues value) {
            yearCountValues.sumValues(value);
        }

        @Override
        public YearCountValues finalizeChunk() {
            return yearCountValues;
        }

        @Override
        public void reset() {
            yearCountValues = new YearCountValues();
        }
    }
}
