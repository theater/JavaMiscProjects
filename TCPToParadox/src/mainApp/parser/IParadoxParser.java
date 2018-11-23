package mainApp.parser;

import mainApp.ZoneStateFlags;
import mainApp.model.PartitionState;
import mainApp.model.ZoneState;

public interface IParadoxParser {
	public PartitionState calculatePartitionState(byte[] partitionFlags);
	public ZoneState calculateZoneState(int id, ZoneStateFlags zoneStateFlags);
}
