package mainApp.Model;

public class Zone {
	private String label;
	private boolean isOpened;
	private boolean isTampered;
	private boolean hasLowBattery;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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

	public boolean isHasLowBattery() {
		return hasLowBattery;
	}

	public void setHasLowBattery(boolean hasLowBattery) {
		this.hasLowBattery = hasLowBattery;
	}
}
