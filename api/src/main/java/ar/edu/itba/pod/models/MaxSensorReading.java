package ar.edu.itba.pod.models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class MaxSensorReading implements DataSerializable {
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
}
