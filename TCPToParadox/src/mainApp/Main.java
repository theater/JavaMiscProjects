package mainApp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mainApp.Model.Partition;

public class Main {

	private static final String PASSWORD_FILE = "resources/password.txt";

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	private static final String IP_ADDRESS = "192.168.254.231";
	private static final int PORT = 10000;
	private static final String PASSWORD = retrievePassword(PASSWORD_FILE);

	public static void main(String[] args) {
		try {
			Evo192Communicator paradoxSystem = new Evo192Communicator(IP_ADDRESS, PORT, PASSWORD);

			List<String> partitionLabels = paradoxSystem.readPartitionLabels();
//            List<String> zoneLabels = paradoxSystem.readZoneLabels();

			List<byte[]> partitionFlags = paradoxSystem.readPartitionFlags();
//            for (int i = 0; i < partitionFlags.size() ; i++) {
//            	ParadoxUtil.printByteArray("Partition " + (i+1) + ": ", partitionFlags.get(i), 6);
//            }

			List<Partition> partitions = new ArrayList<Partition>();
			for (int i = 0; i < partitionLabels.size(); i++) {
				Partition partition = new Partition(partitionLabels.get(i));
				partition.setState(partitionFlags.get(i));
				partitions.add(partition);
				logger.debug("Partition {}:\t{}", i + 1, partition.getState().calculatedState());
			}

			while (true) {
				paradoxSystem.refreshMemoryMap();
				List<byte[]> currentPartitionFlags = paradoxSystem.readPartitionFlags();
				for (int i = 0; i < partitions.size(); i++) {
					Partition partition = partitions.get(i);
					partition.setState(currentPartitionFlags.get(i));
					logger.debug("Partition {}:\t{}", partition.getLabel(), partition.getState().calculatedState());
				}
//            	updatePartitionStates(partitions);
				Thread.sleep(5000);
				logger.debug("############################################################################");
			}

//             zoneLabels.stream().forEach(a -> logger.debug("Zone label: {}", a));

//			paradoxSystem.logoutSequence();
//			paradoxSystem.close();
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