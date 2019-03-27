package mainApp.parser;

import mainApp.model.PartitionState;
import mainApp.model.Version;
import mainApp.model.ZoneState;
import mainApp.model.ZoneStateFlags;

public interface IParadoxParser {
	public PartitionState calculatePartitionState(byte[] partitionFlags);
	public ZoneState calculateZoneState(int id, ZoneStateFlags zoneStateFlags);

	public Version parseApplicationVersion(byte[] panelInfoBytes);
	public Version parseHardwareVersion(byte[] panelInfoBytes);
	public Version parseBootloaderVersion(byte[] panelInfoBytes);
}
