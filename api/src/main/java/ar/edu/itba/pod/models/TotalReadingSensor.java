package ar.edu.itba.pod.models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class TotalReadingSensor implements DataSerializable, Comparable<TotalReadingSensor> {
    private String sensor;
    private long total;

    public TotalReadingSensor() {}

    public TotalReadingSensor(String sensor, long total) {
        this.sensor = sensor;
        this.total = total;
    }

    public String getSensor() {
        return sensor;
    }

    public long getTotal() {
        return total;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeUTF(sensor);
        out.writeLong(total);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        sensor = in.readUTF();
        total = in.readLong();
    }

    @Override
    public int compareTo(TotalReadingSensor o) {
        int toReturn = -Long.compare(o.total, total);
        if (toReturn == 0)
            toReturn = sensor.compareTo(o.sensor);
        return toReturn;
    }
}
