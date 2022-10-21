package ar.edu.itba.pod.models;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
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

public class Utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);
    private static final ICSVParser CSV_PARSER = new CSVParserBuilder().withSeparator(';').build();

    public static void parseReadings(String fileName, HazelcastInstance hz) {
        // TODO: check
        IMap<Integer, Reading> readings = hz.getMap(Constants.READINGS_MAP);
        try (FileReader fr = new FileReader(fileName); CSVReader reader = new CSVReaderBuilder(fr)
                .withCSVParser(CSV_PARSER).build()) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                readings.put(Integer.parseInt(nextLine[0]), // TODO: ver la clave
                        new Reading(
                                Integer.parseInt(nextLine[Constants.YEAR]),
                                nextLine[Constants.MONTH],
                                Integer.parseInt(nextLine[Constants.M_DATE]),
                                nextLine[Constants.DAY],
                                Integer.parseInt(nextLine[Constants.TIME]),
                                Integer.parseInt(nextLine[Constants.READING_SENSOR_ID]),
                                Long.parseLong(nextLine[Constants.HOURLY_COUNT])));
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException("Error reading CSV");
        }
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

    public static HazelcastInstance getHazelcastInstance() {
        ClientConfig config = new ClientConfig();

        GroupConfig groupConfig = new GroupConfig().setName("g6").setPassword("password");
        config.setGroupConfig(groupConfig);

        // TODO: agregar address parseada
        ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();
        String[] addresses = {"192.168.0.127:5701"};
        clientNetworkConfig.addAddress(addresses);
        config.setNetworkConfig(clientNetworkConfig);

        return HazelcastClient.newHazelcastClient(config);
    }


    public static int getDaysPerMonth(String month) {

        return DaysPerMonth.valueOf(month.toUpperCase()).getDays();//daysPerMonth.get(month.toUpperCase());
    }
}
