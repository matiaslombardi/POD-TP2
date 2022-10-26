package ar.edu.itba.pod.models.responses;

import ar.edu.itba.pod.models.CSVWriteable;

import java.io.Serializable;
import java.util.Objects;

public class TopSensorMonth implements Comparable<TopSensorMonth>, CSVWriteable, Serializable {
    private String sensor;
    private String month;
    private double average;

    public TopSensorMonth() {}

    public TopSensorMonth(String sensor, String month, double average) {
        this.sensor = sensor;
        this.month = month;
        this.average = average;
    }

    public String getSensor() {
        return sensor;
    }

    public String getMonth() {
        return month;
    }

    public double getAverage() {
        return average;
    }

    @Override
    public int compareTo(TopSensorMonth o) {
        int toReturn = -Double.compare(average, o.average);
        if (toReturn == 0)
            toReturn = sensor.compareTo(o.sensor);
        return toReturn;
    }

    @Override
    public String[] toCSVData() {
        return new String[]{sensor, month, String.format("%.2f", average)};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopSensorMonth that = (TopSensorMonth) o;
        return Double.compare(that.average, average) == 0 && Objects.equals(sensor, that.sensor) && Objects.equals(month, that.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensor, month, average);
    }


}
