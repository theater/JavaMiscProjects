package main.java.calculator;

import main.java.config.Configuration;

public class Army implements Comparable<Army>{
	private ArmyType type;
	private ArmySubType subType;
	private int tier;
	private int number;

	public Army(ArmyType type, int tier, int number) {
		this.type = type;
		this.tier = tier;
		this.number = number;
		
		Configuration configuration = DamageCalculator.configuration;
		this.subType = configuration.TYPE_TO_SUBTYPE_MAP.get(type)[tier];
	}
	
	@Override
	public int compareTo(Army object) {
		if(object.getSubType().getAttackSpeed() < subType.getAttackSpeed()) {
			return -1;
		} else if (object.getSubType().getAttackSpeed() > subType.getAttackSpeed()){
			return 1;
		} else if(object.getSubType().getAttackSpeed() == subType.getAttackSpeed()) {
				return (object.getTier() > getTier()) ? 1 : -1;
			}
		return 0;
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
		return "[type=" + type + "[" + tier + "], number=" + number + "]";
	}
	
	
}
