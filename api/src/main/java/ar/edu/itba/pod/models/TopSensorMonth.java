package ar.edu.itba.pod.models;

public class TopSensorMonth implements Comparable<TopSensorMonth> {
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
}
