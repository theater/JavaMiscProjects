package mainApp;

public class ZoneStateFlags {
	private byte[] zonesOpened;
	private byte[] zonesTampered;
	private byte[] zonesLowBattery;

	public byte[] getZonesOpened() {
		return zonesOpened;
	}

	public void setZonesOpened(byte[] zonesOpened) {
		this.zonesOpened = zonesOpened;
	}

	public byte[] getZonesTampered() {
		return zonesTampered;
	}

	public void setZonesTampered(byte[] zonesTampered) {
		this.zonesTampered = zonesTampered;
	}

	public byte[] getZonesLowBattery() {
		return zonesLowBattery;
	}

	public void setZonesLowBattery(byte[] zonesLowBattery) {
		this.zonesLowBattery = zonesLowBattery;
	}

}
