package ar.edu.itba.pod.models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class DateTypeReading implements DataSerializable {

    private long hourlyCount;
    private boolean isWeekend;

    public DateTypeReading() {}

    public DateTypeReading(long hourlyCount, boolean isWeekend) {
        this.hourlyCount = hourlyCount;
        this.isWeekend = isWeekend;
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

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeLong(hourlyCount);
        out.writeBoolean(isWeekend);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.hourlyCount = in.readLong();
        this.isWeekend = in.readBoolean();
    }
}
