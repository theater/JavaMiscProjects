package mainApp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final String PASSWORD_FILE = "resources/password.txt";

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String IP_ADDRESS = "192.168.254.231";
    private static final int PORT = 10000;
    private static final String PASSWORD = retrievePassword(PASSWORD_FILE);

    public static void main(String[] args) {
        try {
            ParadoxSystem paradoxSystem = new ParadoxSystem(IP_ADDRESS, PORT, PASSWORD);
            paradoxSystem.logonSequence();
            List<String> partitionLabels = paradoxSystem.readPartitions();
            // List<String> zoneLabels = paradoxSystem.readZones();

            paradoxSystem.logoutSequence();
            paradoxSystem.close();

            partitionLabels.stream().forEach(a -> logger.debug("Partition label: " + a));
            logger.debug("############################################################################");
            // zoneLabels.stream().forEach(a -> System.out.println("Zone label: " + a));
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage(), e);
        }
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