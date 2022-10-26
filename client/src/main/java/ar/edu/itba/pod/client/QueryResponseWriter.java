package ar.edu.itba.pod.client;

import ar.edu.itba.pod.models.CSVWriteable;
import ar.edu.itba.pod.models.responses.MaxSensorResponse;
import ar.edu.itba.pod.models.responses.TotalReadingSensor;
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
            results.forEach(r -> writer.writeNext(r.toCSVData()));
            writer.close();
        } catch (IOException e) {
            LOGGER.error("Error writing results to file", e);
        }
    }
}
