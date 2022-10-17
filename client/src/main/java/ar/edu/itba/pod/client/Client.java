package ar.edu.itba.pod.client;

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

public class Client {
    private static Logger logger = LoggerFactory.getLogger(Client.class);
    private static final ICSVParser CSV_PARSER = new CSVParserBuilder().withSeparator(';').build();

    public static void main(String[] args) {
        logger.info("tpe2-g6-parent Client Starting ...");
    }

    public static void parsePedestrianData(String fileName) {
        try (FileReader fr = new FileReader(fileName); CSVReader reader = new CSVReaderBuilder(fr)
                .withCSVParser(CSV_PARSER).build()) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                String pedestrianReading = nextLine[0];
                System.out.println(pedestrianReading);
                Map<String, int[]> map = new HashMap<>();

                //TODO: Fix to pedestrian csv
                String[] categories = nextLine[1].split(";");
                for (String category : categories) {
                    String[] parts = category.split("#");
                    String seatCategory = parts[0];
                    int rows = Integer.parseInt(parts[1]);
                    int cols = Integer.parseInt(parts[2]);
                    map.put(seatCategory, new int[]{rows, cols});
                }
                try {
                    System.out.println(map.size());
//                    flightManagerService.addPlaneModel(planeModel, map);
                } catch (RuntimeException e) {
//                    LOGGER.error(e.getMessage());
//                    LOGGER.info("Ignoring model");
                }
            }
        } catch (IOException | CsvValidationException e) {
//            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException("Error reading CSV");
        }
    }
}
