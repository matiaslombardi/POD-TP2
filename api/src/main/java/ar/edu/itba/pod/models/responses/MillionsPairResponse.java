package ar.edu.itba.pod.models.responses;

import ar.edu.itba.pod.models.CSVWriteable;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class MillionsPairResponse implements DataSerializable, CSVWriteable, Comparable<MillionsPairResponse> {
    private long value;
    private String first;
    private String second;

    public MillionsPairResponse() {
        super();
    }

    public MillionsPairResponse(long value, String first, String second) {
        this.value = value;
        this.first = first;
        this.second = second;
    }

    @Override
    public String[] toCSVData() {
        return new String[] {String.valueOf(value), first, second};
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeLong(value);
        out.writeUTF(first);
        out.writeUTF(second);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.value = in.readLong();
        this.first = in.readUTF();
        this.second = in.readUTF();
    }


    public long getValue() {
        return value;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    @Override
    public int compareTo(MillionsPairResponse o) {
        return -Long.compare(value, o.value);
    }
}
