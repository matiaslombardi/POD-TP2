package ar.edu.itba.pod.models.hazelcast;

import java.io.Serializable;

public class Reading implements Serializable {
    private int sensorId;
    private int year;
    private String month;
    private int mDate;
    private String day;
    private int time;
    private long hourlyCounts;

    public Reading() {
    }


    public Reading(int year, String month, int mDate, String day, int time, int sensorId, long hourlyCounts) {
        this.sensorId = sensorId;
        this.year = year;
        this.month = month;
        this.mDate = mDate;
        this.day = day;
        this.time = time;
        this.hourlyCounts = hourlyCounts;
    }

    public int getSensorId() {
        return sensorId;
    }

    public long getHourlyCounts() {
        return hourlyCounts;
    }

    public int getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }
    public int getmDate() {
        return mDate;
    }

    public String getDay() {
        return day;
    }

    public int getTime() {
        return time;
    }


}
