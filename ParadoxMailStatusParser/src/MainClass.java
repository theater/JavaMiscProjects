import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainClass {

	private static MyLogger logger = MyLogger.getInstance();

	private static final String MAIL_PIR = "src/resources/mailPIR";
	private static final String MAIL_ALARM = "src/resources/mailAlarm";

	public static void main(String[] args) throws IOException {
		Path path = Paths.get(MAIL_ALARM);

		List<String> fileLines = readFileLines(path);
		MailParser parser = MailParser.getInstance();
		Map<String, String> operationMap = parser.parseToMap(fileLines);
		updatePartitionStatus(operationMap);
	}

	private static void updatePartitionStatus(Map<String, String> operationMap) {
		String partition = operationMap.get("Partition");
		String status = operationMap.get("Message");
		switch (status) {
		case "Arming":
			logger.logDebug("Partition " + partition + " is armed.");
			break;
		case "Disarming":
			logger.logDebug("Partition " + partition + " is disarmed.");
			break;
		case "Alarm":
			String zone = operationMap.get("Zone");
			logger.logDebug("Partition " + partition + " has alarm in zone: " + zone);
			break;
		}
	}

	private static List<String> readFileLines(Path path) throws IOException {
		List<String> lines = new ArrayList<>();

		BufferedReader reader = Files.newBufferedReader(path);
		logger.logDebug("Start reading file " + path);
		while (reader.ready()) {
			String readLine = reader.readLine();
			lines.add(readLine);
			logger.logDebug(readLine);
		}
		logger.logDebug("Finish reading file");

		return lines;
	}

}
