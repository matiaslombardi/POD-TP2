package ar.edu.itba.pod.server;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class Server {
    private final static Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {
        LOGGER.info("tpe2-g6-parent Server Starting ...");

        // Config
        Config config = new Config();
        GroupConfig groupConfig = new GroupConfig()
                .setName("g6")
                .setPassword("password");

        config.setGroupConfig(groupConfig);

        // Network Config
        MulticastConfig multicastConfig = new MulticastConfig();

        JoinConfig joinConfig = new JoinConfig().setMulticastConfig(multicastConfig);

        InterfacesConfig interfacesConfig = new InterfacesConfig()
                .setInterfaces(Collections.singletonList("192.168.0.*"))
                .setEnabled(true);
        //TODO: ver bien que red poner

        NetworkConfig networkConfig = new NetworkConfig().setInterfaces(interfacesConfig).setJoin(joinConfig);

        config.setNetworkConfig(networkConfig);

        // Management Center Config
        // TODO: esto no es necesario
        ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig()
                .setUrl("http://localhost:32768/mancenter/")
                .setEnabled(true);
        config.setManagementCenterConfig(managementCenterConfig);

        // Start cluster
        Hazelcast.newHazelcastInstance(config);
    }
}
