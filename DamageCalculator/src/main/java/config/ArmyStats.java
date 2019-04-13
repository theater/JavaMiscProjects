package main.java.config;

public class ArmyStats {

	private double attack;
	private double defense;
	private double health;

	public ArmyStats() {
		// Used for Jackson serialization purposes only
	}

	public ArmyStats(double attack, double defense, double health) {
		this.attack = attack;
		this.defense = defense;
		this.health = health;
	}

	@Override
	public ArmyStats clone() {
		return new ArmyStats(attack, defense, health);
	}

	public double getAttack() {
		return attack;
	}

	public void setAttack(double attack) {
		this.attack = attack;
	}

	public double getDefense() {
		return defense;
	}

	public void setDefense(double defense) {
		this.defense = defense;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}


}
