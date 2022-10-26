package ar.edu.itba.pod.models.responses;

import ar.edu.itba.pod.models.CSVWriteable;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.util.Objects;

public class TotalReadingSensor implements DataSerializable, Comparable<TotalReadingSensor>,
        CSVWriteable {

    private String sensor;
    private long total;


    public TotalReadingSensor() {
    }

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
        int toReturn = -Long.compare(total, o.total);
        if (toReturn == 0)
            toReturn = sensor.compareTo(o.sensor);
        return toReturn;
    }

    @Override
    public String[] toCSVData() {
        return new String[]{sensor, String.valueOf(total)};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalReadingSensor that = (TotalReadingSensor) o;
        return total == that.total && Objects.equals(sensor, that.sensor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensor, total);
    }
}
