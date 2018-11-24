package mainApp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mainApp.communication.EvoCommunicator;
import mainApp.communication.IParadoxCommunicator;
import mainApp.model.ParadoxSecuritySystem;
import mainApp.model.Partition;
import mainApp.model.Zone;

/**
 * The {@link Main} - used for testing purposes only.
 *
 * @author Konstantin_Polihronov - Initial contribution
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String IP_ADDRESS = "192.168.254.231";
    private static final int PORT = 10000;

    // PC Password is the value of section 3012, i.e. if value is 0987, PC Password is two bytes 0x09, 0x87
    private static final String PC_PASSWORD = "0987";

    public static void main(String[] args) {
        String ip150Password = handleArguments(args);

        try {
            IParadoxCommunicator communicator = new EvoCommunicator(IP_ADDRESS, PORT, ip150Password, PC_PASSWORD);
            ParadoxSecuritySystem paradoxSystem = new ParadoxSecuritySystem(communicator);

//            while (true) {
//                infiniteLoop(paradoxSystem);
//            }
			communicator.logoutSequence();
			communicator.close();
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage(), e);
            System.exit(0);
        }
    }

    private static void infiniteLoop(ParadoxSecuritySystem paradoxSystem) {
        try {
            paradoxSystem.getCommunicator().refreshMemoryMap();
            Thread.sleep(5000);
            paradoxSystem.updateEntities();
        } catch (InterruptedException e1) {
            logger.error(e1.getMessage(), e1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static List<Zone> initializeZones(IParadoxCommunicator paradoxSystem) {
        List<String> zoneLabels = paradoxSystem.readZoneLabels();
        List<Zone> zones = new ArrayList<Zone>();
        for (int i = 0; i < 40; i++) {
            Zone zone = new Zone(i + 1, zoneLabels.get(i));

            zones.add(zone);
        }
        return zones;
    }

    private static List<Partition> initializePartitions(IParadoxCommunicator paradoxSystem) {
        List<String> partitionLabels = paradoxSystem.readPartitionLabels();
        List<Partition> partitions = new ArrayList<Partition>();
        for (int i = 0; i < partitionLabels.size(); i++) {
            Partition partition = new Partition(i + 1, partitionLabels.get(i));
            partitions.add(partition);
            logger.debug("Partition {}:\t{}", i + 1, partition.getState().getMainState());
        }
        return partitions;
    }

    private static String handleArguments(String[] args) {
        if (args == null || args.length < 2 || !"--password".equals(args[0]) || args[1] == null || args[1].isEmpty()) {
            logger.error("Usage: application --password <YOUR_PASSWORD_FOR_IP150>");
            System.exit(0);
        } else {
            logger.info("Password retrieved successfully from CLI.");
        }
        return args[1];
    }

    private static String retrievePassword(String file) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(file));
            if (bytes != null && bytes.length > 0) {
                String result = new String(bytes);
                logger.debug("Password: {}", result);
                return result;
            }
        } catch (IOException e) {
            logger.debug("Exception during reading password from file", e);
        }
        return "";
    }
}
