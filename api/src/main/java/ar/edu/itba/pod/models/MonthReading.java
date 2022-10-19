package ar.edu.itba.pod.models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class MonthReading implements DataSerializable {

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

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeUTF(month);
        out.writeLong(hourlyCount);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.month = in.readUTF();
        this.hourlyCount = in.readLong();
    }
}
