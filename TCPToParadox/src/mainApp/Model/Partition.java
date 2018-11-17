package mainApp.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Partition {
	private String label;
	private PartitionState state = new PartitionState();

	private static Logger logger = LoggerFactory.getLogger(Partition.class);

	public Partition(String label) {
		this.label = label;
		logger.debug("Creating partition with label: {}", label);
	}

	public Partition setState(byte[] partitionFlags) {
		PartitionState state = this.getState();
		state.updateStates(partitionFlags);
		return this;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return "Partition [label=" + label + ", state=" + state + "]";
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
