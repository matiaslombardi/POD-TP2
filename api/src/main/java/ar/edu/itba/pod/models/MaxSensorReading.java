package ar.edu.itba.pod.models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

public class MaxSensorReading implements DataSerializable, Comparable<MaxSensorReading> {
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

    public boolean isAfter(int year, String month, int mDate, int time) {
        return LocalDateTime.of(year, LocalDateTime.parse(month).getMonthValue(), mDate, time, 0)
                .isAfter(LocalDateTime.of(this.year, LocalDateTime.parse(this.month).getMonthValue(), this.mDate, this.time, 0));
    }

    public void updateMax(Reading reading) {
        this.maxReading = reading.getHourlyCounts();
        this.year = reading.getYear();
        this.month = reading.getMonth();
        this.mDate = reading.getmDate();
        this.time = reading.getTime();
    }

    public String formatDate() {
        return String.format("%d/%s/%d %d:00", mDate, Month.valueOf(month.toUpperCase()).getValue(), year, time);
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeLong(maxReading);
        out.writeInt(time);
        out.writeUTF(month);
        out.writeInt(year);
        out.writeInt(mDate);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.maxReading = in.readLong();
        this.time = in.readInt();
        this.month = in.readUTF();
        this.year = in.readInt();
        this.mDate = in.readInt();
    }

    @Override
    public int compareTo(MaxSensorReading o) {
        return -Long.compare(maxReading, o.maxReading);
    }
}
