package mainApp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final String PASSWORD = "blabla";

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	private static final int PORT = 10000;
	private static final String IP_ADDRESS = "192.168.254.231";

	public static void main(String[] args) throws Exception {
		ParadoxSystem paradoxSystem = new ParadoxSystem(IP_ADDRESS, PORT, PASSWORD);
		paradoxSystem.logonSequence();
		List<String> partitionLabels = paradoxSystem.readPartitions();
//		List<String> zoneLabels = paradoxSystem.readZones();

//		paradoxSystem.logoutSequence();
		paradoxSystem.close();

		partitionLabels.stream().forEach(a -> logger.debug("Partition label: " + a));
		logger.debug("############################################################################");
//		zoneLabels.stream().forEach(a -> System.out.println("Zone label: " + a));
	}
}