package mainApp.communication;

import java.io.IOException;
import java.util.List;

import mainApp.model.ZoneStateFlags;

public interface IParadoxCommunicator {

    void close() throws IOException;

    void logoutSequence() throws IOException;

    void loginSequence() throws IOException, InterruptedException;

    public void refreshMemoryMap() throws Exception;

    public List<byte[]> readPartitionFlags() throws Exception;

    public ZoneStateFlags readZoneStateFlags() throws Exception;

    public List<String> readPartitionLabels();

    public List<String> readZoneLabels();

    public byte[] getPanelInfoBytes();
}
