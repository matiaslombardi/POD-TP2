package ar.edu.itba.pod.models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MaxSensorReading implements DataSerializable {
    private long maxReading;
    private LocalDateTime date;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:00");

    public MaxSensorReading() {
        date = LocalDateTime.MIN;
        maxReading = 0;
    }

    public MaxSensorReading(MaxSensorReading reading) {
        this.maxReading = reading.getMaxReading();
        this.date = reading.date;
    }

    public MaxSensorReading(MaxSensorReading reading, LocalDateTime localDateTime) {
        this.maxReading = reading.getMaxReading();
        this.date = localDateTime;
    }

    public MaxSensorReading(long reading, LocalDateTime localDateTime) {
        this.maxReading = reading;
        this.date = localDateTime;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getMaxReading() {
        return maxReading;
    }

    public void setMaxReading(long maxReading) {
        this.maxReading = maxReading;
    }

    public boolean isAfter(LocalDateTime toCompare) {
        return date.isAfter(toCompare);
    }

    public void updateMax(MaxSensorReading reading) {
        this.maxReading = reading.getMaxReading();
        this.date = reading.getDate();
    }

    public String formatDate() {
        return date.format(formatter);
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeLong(maxReading);
        out.writeUTF(date.toString());
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.maxReading = in.readLong();
        this.date = LocalDateTime.parse(in.readUTF());
    }
}
