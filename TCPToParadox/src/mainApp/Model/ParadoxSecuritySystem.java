package mainApp.Model;

import java.util.List;

import java.util.ArrayList;

public class ParadoxSecuritySystem {
	private List<Partition> partitions = new ArrayList<Partition>(8);
	private List<Zone> zones = new ArrayList<Zone>(192);

	public List<Partition> getPartitions() {
		return partitions;
	}

	public void setPartitions(List<Partition> partitions) {
		this.partitions = partitions;
	}

	public List<Zone> getZones() {
		return zones;
	}

	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}
}
