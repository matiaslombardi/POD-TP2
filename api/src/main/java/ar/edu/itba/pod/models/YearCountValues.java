package ar.edu.itba.pod.models;

public class YearCountValues {
    private int year;
    private long weekendCount;
    private long weekdaysCount;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
}
