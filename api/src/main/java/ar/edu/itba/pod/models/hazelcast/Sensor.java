package ar.edu.itba.pod.models.hazelcast;

import java.io.Serializable;

public class Sensor implements Serializable {
    private int id;
    private String description;
    private Status status;

    public Sensor() {
    }

    public Sensor(int id, String description, Status status) {
        this.id = id;
        this.description = description;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return status.equals(Status.A);
    }
}
