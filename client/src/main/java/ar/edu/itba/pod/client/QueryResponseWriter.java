package ar.edu.itba.pod.client;

import ar.edu.itba.pod.models.CSVWriteable;
import ar.edu.itba.pod.models.responses.MaxSensorResponse;
import ar.edu.itba.pod.models.TotalReadingSensor;
import ar.edu.itba.pod.models.YearCountValues;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class QueryResponseWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryResponseWriter.class);

    public static void writeQueryResponse(String filename, Collection<? extends CSVWriteable> results,
                                          String[] header) {
        try {
            FileWriter fw = new FileWriter(filename);
            CSVWriter writer = new CSVWriter(fw, ';', CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            writer.writeNext(header);
            for (CSVWriteable result : results) {
                writer.writeNext(result.toCSVData());
            }
            writer.close();
        } catch (IOException e) {
            LOGGER.error("Error writing results to file", e);
        }
    }

    public static void writeReadingCount(Iterable<TotalReadingSensor> totalReadingSensors) {
        try {
            FileWriter fw = new FileWriter("query1.csv");
            CSVWriter writer = new CSVWriter(fw, ';', CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            String[] header = {"Sensor", "Total_Count"};
            writer.writeNext(header);

            for (TotalReadingSensor totalReadingSensor : totalReadingSensors) {
                String[] data = {totalReadingSensor.getSensor(),
                        String.valueOf(totalReadingSensor.getTotal())};
                writer.writeNext(data);
            }

            writer.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException("Error writing to file");
        }
    }

    public static void writeTotalCountPerYear(Map<Integer, YearCountValues> totalCountPerYearMap) {
        try {
            FileWriter fw = new FileWriter("query2.csv");
            CSVWriter writer = new CSVWriter(fw, ';', CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            String[] header = {"Year", "Weekdays_Count", "Weekends_Count", "Total_Count"};
            writer.writeNext(header);

            Set<Map.Entry<Integer, YearCountValues>> auxSet = totalCountPerYearMap.entrySet();

            for (Map.Entry<Integer, YearCountValues> entry : auxSet) {
                String[] data = {String.valueOf(entry.getKey()),
                        String.valueOf(entry.getValue().getWeekdaysCount()),
                        String.valueOf(entry.getValue().getWeekendCount()),
                        String.valueOf(entry.getValue().getWeekdaysCount() + entry.getValue().getWeekendCount())
                };
                writer.writeNext(data);
            }

            writer.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException("Error writing to file");
        }
    }

    public static void writeMaxSensorReading(Collection<MaxSensorResponse> maxSensorReadings) {
        try {
            FileWriter fw = new FileWriter("query3.csv");
            CSVWriter writer = new CSVWriter(fw, ';', CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            String[] header = {"Sensor", "Max_Reading_Count", "Max_Reading_DateTime"};
            writer.writeNext(header);

            for (MaxSensorResponse reading : maxSensorReadings) {
                String[] data = {reading.getSensor(),
                        String.valueOf(reading.getMaxReading()),
                        reading.formatDate()
                };
                writer.writeNext(data);
            }

            writer.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException("Error writing to file");
        }
    }
}
