package ar.edu.itba.pod.client;

import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.Reading;
import ar.edu.itba.pod.models.Sensor;
import ar.edu.itba.pod.models.Status;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
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

    public static void parseReadings(String fileName, HazelcastInstance hz) {
        Map<Integer, Reading> readingMap = new HashMap<>();
        try (FileReader fr = new FileReader(fileName); CSVReader reader = new CSVReaderBuilder(fr)
                .withCSVParser(CSV_PARSER).build()) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                String[] reading = new String[7];
                readingMap.put(Integer.parseInt(nextLine[0]), // TODO: ver la clave
                        new Reading(Integer.parseInt(nextLine[Constants.YEAR]),
                                Integer.parseInt(nextLine[Constants.MONTH]),
                                nextLine[Constants.M_DATE], Integer.parseInt(nextLine[Constants.DAY]),
                                nextLine[Constants.TIME], Integer.parseInt(nextLine[Constants.READING_SENSOR_ID]),
                                Integer.parseInt(nextLine[Constants.HOURLY_COUNT])));
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException("Error reading CSV");
        }

        // TODO: check
        IMap<Integer, Reading> readings = hz.getMap(Constants.READINGS_MAP);
        readings.putAll(readingMap);
    }

    public static void parseSensorsData(String fileName, HazelcastInstance hz) {
        Map<Integer, Sensor> sensorMap = new HashMap<>();
        try (FileReader fr = new FileReader(fileName); CSVReader reader = new CSVReaderBuilder(fr)
                .withCSVParser(CSV_PARSER).build()) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                sensorMap.put(Integer.parseInt(nextLine[Constants.SENSOR_ID]),
                        new Sensor(
                                Integer.parseInt(nextLine[Constants.SENSOR_ID]),
                                nextLine[Constants.SENSOR_DESCRIPTION],
                                Status.valueOf(nextLine[Constants.SENSOR_STATUS])));
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException("Error reading CSV");
        }

        IMap<Integer, Sensor> sensors = hz.getMap(Constants.SENSORS_MAP);
        sensors.putAll(sensorMap);
    }
}
