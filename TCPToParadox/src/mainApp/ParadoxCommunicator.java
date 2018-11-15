package mainApp;

import java.io.IOException;
import java.util.List;

public interface ParadoxCommunicator {

	void close() throws IOException;

	void logoutSequence() throws IOException;

	void loginSequence() throws IOException, InterruptedException;

	List<String> readPartitionLabels();

	List<String> readZoneLabels();

}