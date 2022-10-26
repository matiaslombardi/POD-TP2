package ar.edu.itba.pod.models.responses;

import ar.edu.itba.pod.models.CSVWriteable;

import java.io.Serializable;
import java.util.Objects;

public class MillionsPairResponse implements Serializable, CSVWriteable, Comparable<MillionsPairResponse> {
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
        int toReturn = -Long.compare(value, o.value);
        if (toReturn == 0) {
            toReturn = first.compareTo(o.first);
            if (toReturn == 0) {
                toReturn = second.compareTo(o.second);
            }
        }
        return toReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MillionsPairResponse that = (MillionsPairResponse) o;
        return value == that.value && Objects.equals(first, that.first) && Objects.equals(second, that.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, first, second);
    }
}
