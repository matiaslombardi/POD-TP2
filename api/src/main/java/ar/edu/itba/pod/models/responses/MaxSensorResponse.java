package ar.edu.itba.pod.models.responses;

import ar.edu.itba.pod.models.CSVWriteable;
import ar.edu.itba.pod.models.MaxSensorReading;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.util.Objects;

public class MaxSensorResponse extends MaxSensorReading implements Comparable<MaxSensorResponse>,
        DataSerializable, CSVWriteable {
    private String sensor;

    public MaxSensorResponse() {
    }

    public MaxSensorResponse(String sensor, MaxSensorReading reading) {
        super(reading);
        this.sensor = sensor;
    }

    public String getSensor() {
        return sensor;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        super.writeData(out);
        out.writeUTF(sensor);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        super.readData(in);
        sensor = in.readUTF();
    }

    @Override
    public int compareTo(MaxSensorResponse o) {
        int toReturn = -Long.compare(getMaxReading(), o.getMaxReading());
        if (toReturn == 0)
            toReturn = sensor.compareTo(o.getSensor());
        return toReturn;
    }

    @Override
    public String[] toCSVData() {
        return new String[]{sensor, String.valueOf(getMaxReading()), formatDate()};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaxSensorResponse that = (MaxSensorResponse) o;
        return sensor.equals(that.sensor)
                && getMaxReading() == that.getMaxReading()
                && Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensor);
    }

}
