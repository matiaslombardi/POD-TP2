package ar.edu.itba.pod.server;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

public class Server {
    private final static Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private static final String MASK = "mask";

    public static void main(String[] args) {
        LOGGER.info("tpe2-g6-parent Server Starting ...");

        Properties properties = System.getProperties();
        String mask = properties.getProperty(MASK);

        if (mask == null) {
            LOGGER.info("No mask provided, using default mask");
            mask = "127.0.0.*";
        }

        // Config
        Config config = new Config();
        GroupConfig groupConfig = new GroupConfig()
                .setName("g6")
                .setPassword("g6-pass");

        config.setGroupConfig(groupConfig);

        // Network Config
        MulticastConfig multicastConfig = new MulticastConfig();

        JoinConfig joinConfig = new JoinConfig().setMulticastConfig(multicastConfig);

        InterfacesConfig interfacesConfig = new InterfacesConfig()
                .setInterfaces(Collections.singletonList(mask))
                .setEnabled(true);
        NetworkConfig networkConfig = new NetworkConfig().setInterfaces(interfacesConfig)
                .setJoin(joinConfig);

        config.setNetworkConfig(networkConfig);

        // Start cluster
        Hazelcast.newHazelcastInstance(config);
    }
}
