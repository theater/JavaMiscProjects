package mainApp.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Partition extends Entity {

	private static Logger logger = LoggerFactory.getLogger(Partition.class);

	private PartitionState state = new PartitionState();

	public Partition(int id, String label) {
		super(id, label);
	}

	public Partition setState(byte[] partitionFlags) {
		PartitionState state = this.getState();
		state.updateStates(partitionFlags);
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
