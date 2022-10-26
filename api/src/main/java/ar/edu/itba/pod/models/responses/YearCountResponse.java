package ar.edu.itba.pod.models.responses;

import ar.edu.itba.pod.models.CSVWriteable;
import ar.edu.itba.pod.models.YearCountValues;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.util.Objects;

public class YearCountResponse extends YearCountValues implements DataSerializable, Comparable<YearCountResponse>, CSVWriteable {

    private int year;

    public YearCountResponse() {
        super();
    }

    public YearCountResponse(int year, YearCountValues yearCountValues) {
        super(yearCountValues);
        this.year = year;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        super.writeData(out);
        out.writeInt(year);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        super.readData(in);
        this.year = in.readInt();
    }

    public int getYear() {
        return year;
    }

    @Override
    public int compareTo(YearCountResponse o) {
        return -Integer.compare(year, o.getYear());
    }

    @Override
    public String[] toCSVData() {
        return new String[]{String.valueOf(year), String.valueOf(getWeekdaysCount()),
                String.valueOf(getWeekendCount()), String.valueOf(getWeekdaysCount() + getWeekendCount())};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        YearCountResponse that = (YearCountResponse) o;
        return year == that.year
                && super.getWeekendCount() == that.getWeekendCount()
                && super.getWeekdaysCount() == that.getWeekdaysCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), year);
    }

}
