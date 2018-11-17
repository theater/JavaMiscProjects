package mainApp.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mainApp.ParadoxUtil;
import mainApp.ZoneStateFlags;

public class Zone extends Entity {
	private boolean isOpened;
	private boolean isTampered;
	private boolean hasLowBattery;

	private static Logger logger = LoggerFactory.getLogger(Partition.class);

	public Zone(int id, String label) {
		super(id, label);
	}

	public void setFlags(ZoneStateFlags zoneStateFlags) {
		int id = getId();
		int index = id / 8;
		int bitNumber = id % 8 - 1;

		byte[] zonesOpened = zoneStateFlags.getZonesOpened();
		isOpened = ParadoxUtil.isBitSet(zonesOpened[index], bitNumber);

		byte[] zonesTampered = zoneStateFlags.getZonesTampered();
		isTampered = ParadoxUtil.isBitSet(zonesTampered[index], bitNumber);

		byte[] zonesLowBattery = zoneStateFlags.getZonesLowBattery();
		hasLowBattery = ParadoxUtil.isBitSet(zonesLowBattery[index], bitNumber);
	}

	public boolean isOpened() {
		return isOpened;
	}

	public void setOpened(boolean isOpened) {
		this.isOpened = isOpened;
	}

	public boolean isTampered() {
		return isTampered;
	}

	public void setTampered(boolean isTampered) {
		this.isTampered = isTampered;
	}

	public boolean hasLowBattery() {
		return hasLowBattery;
	}

	public void setHasLowBattery(boolean hasLowBattery) {
		this.hasLowBattery = hasLowBattery;
	}

}