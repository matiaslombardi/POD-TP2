package ar.edu.itba.pod.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ClientParser {

    private final Logger LOGGER = LoggerFactory.getLogger(ClientParser.class);

    private static final String QUERY = "query";
    private static final String ADDRESSES = "addresses";
    private static final String IN_PATH = "inPath";
    private static final String OUT_PATH = "outPath";
    private static final String MIN = "min";
    private static final String N = "n";
    private static final String YEAR = "year";
    private static final String COMBINE = "combine";

    private int query;
    private String[] addresses;
    private String inPath;
    private String outPath;
    private long min;
    private int n;
    private int year;
    private boolean combine;

    public void parse() {
        Properties properties = System.getProperties();

        query = Integer.parseInt(properties.getProperty(QUERY));

        if (properties.getProperty(ADDRESSES) == null) {
            LOGGER.error("No addresses provided");
            System.exit(1);
        }
        addresses = properties.getProperty(ADDRESSES).split(";");


        if ((inPath = properties.getProperty(IN_PATH)) == null) {
            LOGGER.error("No inPath provided");
            System.exit(1);
        }


        if ((outPath = properties.getProperty(OUT_PATH)) == null) {
            LOGGER.error("No outPath provided");
            System.exit(1);
        }

        if ((query == 2 || query == 1) && properties.getProperty(COMBINE) != null) {
            combine = true;
        }

        if (query == 3) {
            if ((min = Long.parseLong(properties.getProperty(MIN))) == 0) {
                LOGGER.error("No min provided");
                System.exit(1);
            }
        }

        if (query == 4) {
            if ((n = Integer.parseInt(properties.getProperty(N))) == 0) {
                LOGGER.error("No n provided");
                System.exit(1);
            }

            if ((year = Integer.parseInt(properties.getProperty(YEAR))) == 0) {
                LOGGER.error("No year provided");
                System.exit(1);
            }
        }
    }

    public int getQuery() {
        return query;
    }

    public String[] getAddresses() {
        return addresses;
    }

    public String getInPath() {
        return inPath;
    }

    public String getOutPath() {
        return outPath;
    }

    public long getMin() {
        return min;
    }

    public int getN() {
        return n;
    }

    public int getYear() {
        return year;
    }
    public boolean getCombine() {
        return combine;
    }
}
