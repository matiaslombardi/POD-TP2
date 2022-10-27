package ar.edu.itba.pod.client;

import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.hazelcast.Reading;
import ar.edu.itba.pod.models.hazelcast.Sensor;
import ar.edu.itba.pod.models.hazelcast.Status;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.ICSVParser;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientUtils.class);
    private static final ICSVParser CSV_PARSER = new CSVParserBuilder().withSeparator(';').build();

    public static void parseReadings(String filePath, HazelcastInstance hz) {
        IList<Reading> readings = hz.getList(Constants.READINGS_MAP);
        readings.clear();

        List<Reading> toAdd = new ArrayList<>();
        try {
            Files.readAllLines(Path.of(filePath + Constants.READINGS_FILE)).stream()
                    .skip(1)
                    .map(line -> line.split(";"))
                    .forEach(nextLine -> toAdd.add(new Reading(
                            Integer.parseInt(nextLine[Constants.YEAR]),
                            nextLine[Constants.MONTH],
                            Integer.parseInt(nextLine[Constants.M_DATE]),
                            nextLine[Constants.DAY],
                            Integer.parseInt(nextLine[Constants.TIME]),
                            Integer.parseInt(nextLine[Constants.READING_SENSOR_ID]),
                            Long.parseLong(nextLine[Constants.HOURLY_COUNT]))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        readings.addAll(toAdd);
    }

    public static Map<Integer, Sensor> parseSensorsData(String fileName, HazelcastInstance hz) {
        Map<Integer, Sensor> sensorMap = new HashMap<>();
        try (FileReader fr = new FileReader(fileName + Constants.SENSORS_FILE);
             CSVReader reader = new CSVReaderBuilder(fr).withCSVParser(CSV_PARSER).build()) {
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
            if (e instanceof FileNotFoundException)
                LOGGER.error("File not found");
            else
                LOGGER.error("Error reading CSV");
            System.exit(1);
        }

        return sensorMap;
    }

    public static HazelcastInstance getHazelcastInstance(String[] addresses) {
        ClientConfig config = new ClientConfig();

        GroupConfig groupConfig = new GroupConfig().setName("g6").setPassword("g6-pass");
        config.setGroupConfig(groupConfig);
        ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();
        clientNetworkConfig.addAddress(addresses);
        config.setNetworkConfig(clientNetworkConfig);

        return HazelcastClient.newHazelcastClient(config);
    }
}
