package ar.edu.itba.pod.client;

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

public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    private static final ICSVParser CSV_PARSER = new CSVParserBuilder().withSeparator(';').build();

    public static void main(String[] args) {
        LOGGER.info("tpe2-g6-parent Client Starting ...");

        ClientConfig config = new ClientConfig();

        GroupConfig groupConfig = new GroupConfig().setName("g6").setPassword("password");
        config.setGroupConfig(groupConfig);

        ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();
        String[] addresses = {"127.0.0.1:5702"};
        clientNetworkConfig.addAddress(addresses);
        config.setNetworkConfig(clientNetworkConfig);

        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(config);

        //TODO: rearmar
        String mapName = "pedestrians";

        IMap<Integer, String> testMapFromMember = hazelcastInstance.getMap(mapName);
        testMapFromMember.set(1,"test1");

        IMap<Integer, String> testMap = hazelcastInstance.getMap(mapName);
        System.out.println(testMap.get(1));


//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //escribo t0
        //leo
        //escribo tfin

        //escribo t0
        //hazelcast
        //escribo tfin
        //cierro archivo de salida

        HazelcastClient.shutdownAll();
    }

    public static void parseReadingsData(String fileName) {
        try (FileReader fr = new FileReader(fileName); CSVReader reader = new CSVReaderBuilder(fr)
                .withCSVParser(CSV_PARSER).build()) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                String[] reading = new String[7];

                //TODO: Fix to pedestrian csv
                reading[0] = nextLine[2]; //Year
                reading[1] = nextLine[3]; //Month
                reading[2] = nextLine[4]; //Mdate
                reading[3] = nextLine[5]; //Day
                reading[4] = nextLine[6]; //Time
                reading[5] = nextLine[7]; //Sensor_id
                reading[6] = nextLine[9]; //Hourly_Counts
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException("Error reading CSV");
        }
    }

    public static void parseSensorsData(String fileName) {
        try (FileReader fr = new FileReader(fileName); CSVReader reader = new CSVReaderBuilder(fr)
                .withCSVParser(CSV_PARSER).build()) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                String[] reading = new String[3];

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
