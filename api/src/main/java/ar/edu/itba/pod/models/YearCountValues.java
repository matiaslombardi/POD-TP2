package ar.edu.itba.pod.models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import java.io.IOException;

public class YearCountValues implements DataSerializable {
    private long weekendCount;
    private long weekdaysCount;

    public YearCountValues() {}

    public YearCountValues(long count, boolean isWeekend) {
        if (isWeekend)
            weekendCount = count;
        else
            weekdaysCount = count;

    }

    public YearCountValues(YearCountValues yearCountValues) {
        weekdaysCount = yearCountValues.weekdaysCount;
        weekendCount = yearCountValues.weekendCount;
    }

    public long getWeekendCount() {
        return weekendCount;
    }

    public void setWeekendCount(long weekendCount) {
        this.weekendCount = weekendCount;
    }

    public long getWeekdaysCount() {
        return weekdaysCount;
    }

    public void setWeekdaysCount(long weekdaysCount) {
        this.weekdaysCount = weekdaysCount;
    }

    public void sumWeekendCount(long count){
        weekendCount += count;
    }

    public void sumWeekdaysCount(long count){
        weekdaysCount += count;
    }

    public void sumValues(YearCountValues other) {
        sumWeekendCount(other.getWeekendCount());
        sumWeekdaysCount(other.getWeekdaysCount());
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeLong(weekdaysCount);
        out.writeLong(weekendCount);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.weekdaysCount = in.readLong();
        this.weekendCount = in.readLong();
    }
}
