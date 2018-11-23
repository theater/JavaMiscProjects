package mainApp.model;

public class ZoneState {
	private boolean isOpened;
	private boolean isTampered;
	private boolean hasLowBattery;

	public ZoneState(boolean isOpened, boolean isTampered, boolean hasLowBattery) {
		this.isOpened = isOpened;
		this.isTampered = isTampered;
		this.hasLowBattery = hasLowBattery;
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
