package ar.edu.itba.pod.assets;

import ar.edu.itba.pod.models.DaysPerMonth;
import ar.edu.itba.pod.models.MaxSensorReading;
import ar.edu.itba.pod.models.hazelcast.Reading;
import ar.edu.itba.pod.models.hazelcast.Sensor;
import ar.edu.itba.pod.models.hazelcast.Status;
import ar.edu.itba.pod.models.responses.MaxSensorResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestConstants {

    public static final String SENSOR_1_NAME = "San Martín - Perón";
    public static final String SENSOR_2_NAME = "Lavardén - Los Patos";
    public static final String SENSOR_3_NAME = "Eduardo Madero - Alem";
    public static final String SENSOR_4_NAME = "Iguazú - Los Patos";
    public static final List<Reading> READINGS = new ArrayList<Reading>() {{
        add(new Reading(2020, "May", 3, "Monday", 2, 1, 18000));
        add(new Reading(2021, "September", 9, "Friday", 7, 2, 40000));
        add(new Reading(2021, "November", 16, "Monday", 10, 3, 35000));
        add(new Reading(2021, "December", 25, "Wednesday", 12, 3, 60000));
        add(new Reading(2021, "December", 25, "Sunday", 21, 1, 60000));
        add(new Reading(2022, "January", 21, "Thursday", 15, 1, 20000));
        add(new Reading(2022, "May", 11, "Tuesday", 14, 2, 9000));
    }};

    public static final List<Reading> MILLIONS_READINGS_1 = new ArrayList<Reading>() {{
        add(new Reading(2020, "May", 3, "Monday", 2, 1, 1800000));
        add(new Reading(2020, "October", 8, "Saturday", 6, 4, 1500000));
        add(new Reading(2021, "September", 9, "Friday", 7, 2, 1200000));
        add(new Reading(2021, "November", 16, "Monday", 10, 3, 35000));
        add(new Reading(2021, "December", 25, "Wednesday", 12, 3, 2000000));
        add(new Reading(2021, "December", 25, "Sunday", 21, 1, 600000));
        add(new Reading(2022, "January", 21, "Thursday", 15, 1, 200000));
        add(new Reading(2022, "May", 11, "Tuesday", 14, 2, 9000));
        add(new Reading(2022, "June", 8, "Sunday", 9, 4, 18000));

    }};

    public static final List<Reading> MILLIONS_READINGS_2 = new ArrayList<Reading>() {{
        add(new Reading(2020, "May", 3, "Monday", 2, 1, 1800000));
        add(new Reading(2021, "September", 9, "Friday", 7, 2, 1200000));
        add(new Reading(2021, "November", 16, "Monday", 10, 3, 35000));
        add(new Reading(2021, "December", 25, "Wednesday", 12, 3, 2000000));
        add(new Reading(2021, "December", 25, "Sunday", 21, 1, 600000));
        add(new Reading(2022, "January", 21, "Thursday", 15, 1, 200000));
        add(new Reading(2022, "May", 11, "Tuesday", 14, 2, 9000));
    }};

    public static final Map<Integer, Sensor> SENSORS = new HashMap<Integer, Sensor>() {{
        put(1, new Sensor(1, SENSOR_1_NAME, Status.A));
        put(2, new Sensor(2, SENSOR_2_NAME, Status.A));
        put(3, new Sensor(3, SENSOR_3_NAME, Status.I));
    }};

    public static final Map<Integer, Sensor> ALL_ACTIVE_SENSORS = new HashMap<Integer, Sensor>() {{
        put(1, new Sensor(1, SENSOR_1_NAME, Status.A));
        put(2, new Sensor(2, SENSOR_2_NAME, Status.A));
        put(3, new Sensor(3, SENSOR_3_NAME, Status.A));
    }};

    public static final Map<Integer, Sensor> NONACTIVE_SENSORS = new HashMap<Integer, Sensor>() {{
        put(1, new Sensor(1, SENSOR_1_NAME, Status.R));
        put(2, new Sensor(2, SENSOR_2_NAME, Status.I));
        put(3, new Sensor(3, SENSOR_3_NAME, Status.I));
    }};

    public static final Map<Integer, Sensor> MILLIONS_SENSORS = new HashMap<Integer, Sensor>() {{
        put(1, new Sensor(1, SENSOR_1_NAME, Status.A));
        put(2, new Sensor(2, SENSOR_2_NAME, Status.A));
        put(3, new Sensor(3, SENSOR_3_NAME, Status.A));
        put(4, new Sensor(4, SENSOR_4_NAME, Status.A));
    }};

    public static final long MIN_VALUE_1 = 25000;
    public static final long MIN_VALUE_2 = 50000;

    private static final MaxSensorReading MAX_SENSOR_READING_1 = new MaxSensorReading(60000, LocalDateTime.of(2021, 12, 25, 21, 0));
    public static final MaxSensorResponse MAX_SENSOR_RESPONSE_1 = new MaxSensorResponse(TestConstants.SENSOR_1_NAME, MAX_SENSOR_READING_1);

    public static final int YEAR_1 = 2021;
    public static final int YEAR_2 = 2022;

    public static final int TOP_AMOUNT = 2;

    public static final double AVERAGE_1 = Math.floor(60000 * 100 / DaysPerMonth.DECEMBER.getDays()) / 100;
    public static final double AVERAGE_2 = Math.floor(40000 * 100 / DaysPerMonth.SEPTEMBER.getDays()) / 100;
}
