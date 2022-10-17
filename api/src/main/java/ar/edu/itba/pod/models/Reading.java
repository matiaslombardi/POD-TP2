package ar.edu.itba.pod.models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class Reading implements DataSerializable {

    private int sensorId;
    private int year;
    private String month;
    private int mDate;
    private String day;
    private int time;
    private int hourlyCounts;

    public Reading(int sensorId, int year, String month, int mDate, String day, int time, int hourlyCounts) {
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

    public int getHourlyCounts() {
        return hourlyCounts;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(sensorId);
        out.writeInt(year);
        out.writeUTF(month);
        out.writeInt(mDate);
        out.writeUTF(day);
        out.writeInt(time);
        out.writeInt(hourlyCounts);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        sensorId = in.readInt();
        year = in.readInt();
        month = in.readUTF();
        mDate = in.readInt();
        day = in.readUTF();
        time = in.readInt();
        hourlyCounts = in.readInt();
    }
}
