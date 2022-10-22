package ar.edu.itba.pod.models;

@FunctionalInterface
public interface CSVWriteable {
    String[] toCSVData();
}
