package ar.edu.itba.pod.client;

import ar.edu.itba.pod.models.Reading;
import ar.edu.itba.pod.models.Sensor;
import ar.edu.itba.pod.models.Status;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.ICSVParser;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVParsers {
    private static final Logger LOGGER = LoggerFactory.getLogger(CSVParsers.class);
    private static final ICSVParser CSV_PARSER = new CSVParserBuilder().withSeparator(';').build();

    public static void parseReadings(String fileName) {
        Map<Integer, Reading> readingMap = new HashMap<>();
        try (FileReader fr = new FileReader(fileName); CSVReader reader = new CSVReaderBuilder(fr)
                .withCSVParser(CSV_PARSER).build()) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                String[] reading = new String[7];
                readingMap.put(Integer.parseInt(nextLine[7]),
                        new Reading(Integer.parseInt(nextLine[2]),
                                Integer.parseInt(nextLine[3]),
                                nextLine[4], Integer.parseInt(nextLine[5]),
                                nextLine[6], Integer.parseInt(nextLine[7]),
                                Integer.parseInt(nextLine[9])));
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException("Error reading CSV");
        }
    }

    public static void parseSensorsData(String fileName) {
        Map<Integer, Sensor> sensorMap = new HashMap<>();
        try (FileReader fr = new FileReader(fileName); CSVReader reader = new CSVReaderBuilder(fr)
                .withCSVParser(CSV_PARSER).build()) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                String[] reading = new String[3];
                sensorMap.put(Integer.parseInt(nextLine[0]),
                        new Sensor(Integer.parseInt(nextLine[0]),
                        nextLine[1], Status.valueOf(nextLine[1])));

                reading[0] = nextLine[0]; //Sensor_id
                reading[1] = nextLine[1]; //Sensor_description
                reading[2] = nextLine[4]; //Status
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException("Error reading CSV");
        }
    }
}
