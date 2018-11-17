package mainApp.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Entity {
	private static Logger logger = LoggerFactory.getLogger(Entity.class);

	private int id;
	private String label;

	public Entity(int id, String label) {
		this.id = id;
		this.label = label;
		logger.debug("Creating entity with label: {} and ID: {}", label, id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "Partition [id=" + id + ", label=" + label;
	}

}
