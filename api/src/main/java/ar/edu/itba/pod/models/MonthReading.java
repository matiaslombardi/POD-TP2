package ar.edu.itba.pod.models;

import java.io.Serializable;

public class MonthReading implements Serializable {

    private String month;
    private long hourlyCount;

    public MonthReading(String month, long hourlyCount) {
        this.month = month;
        this.hourlyCount = hourlyCount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public long getHourlyCount() {
        return hourlyCount;
    }

    public void setHourlyCount(long hourlyCount) {
        this.hourlyCount = hourlyCount;
    }
}
