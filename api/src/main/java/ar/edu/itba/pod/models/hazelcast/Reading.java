package ar.edu.itba.pod.models.hazelcast;

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

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(year);
        out.writeUTF(month);
        out.writeInt(mDate);
        out.writeUTF(day);
        out.writeInt(time);
        out.writeInt(sensorId);
        out.writeLong(hourlyCounts);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        year = in.readInt();
        month = in.readUTF();
        mDate = in.readInt();
        day = in.readUTF();
        time = in.readInt();
        sensorId = in.readInt();
        hourlyCounts = in.readLong();
    }
}
