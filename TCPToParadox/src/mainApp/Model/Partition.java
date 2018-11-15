package mainApp.Model;

public class Partition {
	private String label;
	private PartitionState state;

	public String getLabel() {
		return label;
	}

	public Partition setLabel(String label) {
		this.label = label;
		return this;
	}

	public PartitionState getState() {
		return state;
	}

	public Partition setState(PartitionState state) {
		this.state = state;
		return this;
	}
}
