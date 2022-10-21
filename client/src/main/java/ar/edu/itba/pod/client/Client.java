package ar.edu.itba.pod.client;

import ar.edu.itba.pod.models.Constants;
import ar.edu.itba.pod.models.Reading;
import ar.edu.itba.pod.models.Utils;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.KeyValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        LOGGER.info("tpe2-g6-parent Client Starting ...");

        ClientParser parser = new ClientParser();
        parser.parse();

        HazelcastInstance hz = Utils.getHazelcastInstance();

        Utils.parseReadings(parser.getInPath(), hz);
        Utils.parseSensorsData(parser.getOutPath(), hz);

        IMap<Integer, Reading> readingIMap = hz.getMap(Constants.READINGS_MAP);
        KeyValueSource<Integer, Reading> source = KeyValueSource.fromMap(readingIMap);

        switch (parser.getQuery()) {
            case 1:
                // TODO: query1
                break;
            case 2:
                // TODO: query2
                break;
            case 3:
                // TODO: query3
                break;
            case 4:
                // TODO: query4
                break;
            case 5:
                // TODO: query5
                break;
        }

        HazelcastClient.shutdownAll();
    }
}
