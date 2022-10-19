package ar.edu.itba.pod.models;

public class DateTypeReading {

    private int year;
    private long hourlyCount;
    private boolean isWeekend;

    public DateTypeReading(int year, long hourlyCount, boolean isWeekend) {
        this.year = year;
        this.hourlyCount = hourlyCount;
        this.isWeekend = isWeekend;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getHourlyCount() {
        return hourlyCount;
    }

    public void setHourlyCount(long hourlyCount) {
        this.hourlyCount = hourlyCount;
    }

    public boolean isWeekend() {
        return isWeekend;
    }

    public void setWeekend(boolean weekend) {
        isWeekend = weekend;
    }
}
