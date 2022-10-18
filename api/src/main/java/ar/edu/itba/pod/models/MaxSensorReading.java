package ar.edu.itba.pod.models;

public class MaxSensorReading {
    private int year;
    private String month;
    private int mDate;
    private int time;
    private long maxReading;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getmDate() {
        return mDate;
    }

    public void setmDate(int mDate) {
        this.mDate = mDate;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public long getMaxReading() {
        return maxReading;
    }

    public void setMaxReading(long maxReading) {
        this.maxReading = maxReading;
    }

    public void updateMax(Reading reading) {
        this.maxReading = reading.getHourlyCounts();
        this.year = reading.getYear();
        this.month = reading.getMonth();
        this.mDate = reading.getmDate();
        this.time = reading.getTime();
    }
}
