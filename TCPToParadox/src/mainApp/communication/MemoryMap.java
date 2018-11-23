package mainApp.communication;

import java.util.ArrayList;
import java.util.List;

public class MemoryMap {
	private List<byte[]> ramCache = new ArrayList<byte[]>();

	public MemoryMap(List<byte[]> ramCache) {
		this.ramCache = ramCache;
	}

	public List<byte[]> getRamCache() {
		return ramCache;
	}

	public void setRamCache(List<byte[]> ramCache) {
		this.ramCache = ramCache;
	}

	public byte[] getElement(int index) {
		return ramCache.get(index);
	}

	public void updateElement(int index, byte[] elementValue) {
		ramCache.set(index, elementValue);
	}
}
