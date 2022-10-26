package ar.edu.itba.pod.models;

import java.io.Serializable;
import java.util.Objects;

public class YearCountValues implements Serializable {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YearCountValues that = (YearCountValues) o;
        return weekendCount == that.weekendCount && weekdaysCount == that.weekdaysCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weekendCount, weekdaysCount);
    }
}
