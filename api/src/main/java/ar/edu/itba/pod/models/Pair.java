package ar.edu.itba.pod.models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class Pair implements DataSerializable {
    private String first;
    private String second;

    public Pair(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeUTF(first);
        out.writeUTF(second);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        first = in.readUTF();
        second = in.readUTF();
    }
}
