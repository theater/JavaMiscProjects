package main.java.battle;

import main.java.config.ArmySubType;
import main.java.config.ArmyType;
import main.java.config.ConfigManager;
import main.java.config.Configuration;

public class Army implements Comparable<Army> {
	private ArmyType type;
	private ArmySubType subType;
	private int tier;
	private int number;

	public Army(ArmyType type, int tier, int number) {
		this.type = type;
		this.tier = tier;
		this.number = number;

		Configuration configuration = ConfigManager.getInstance().getConfiguration();
		this.subType = configuration.TYPE_TO_SUBTYPE_MAP.get(type)[tier];
	}

	@Override
	public int compareTo(Army object) {
		int result = Integer.compare(object.getSubType().getAttackSpeed(), this.getSubType().getAttackSpeed());
		if (result == 0) {
			result = Integer.compare(this.getTier(), object.getTier());
		}
		return result;
	}

	public ArmyType getType() {
		return type;
	}

	public void setType(ArmyType type) {
		this.type = type;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public ArmySubType getSubType() {
		return subType;
	}

	public void setSubType(ArmySubType subType) {
		this.subType = subType;
	}

	@Override
	public String toString() {
		return "[type=" + type + "[" + (tier + 1) + "], subType=" + subType + ", number=" + number + "]";
	}

}
