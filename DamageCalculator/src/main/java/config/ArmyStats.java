package main.java.config;

public class ArmyStats {

	private double attack;
	private double defense;
	private double health;
	private double damage;
	private double damageReduction;

	public ArmyStats() {
		// Used for Jackson serialization purposes only
	}

	public ArmyStats(double attack, double defense, double health, double damage, double damageReduction) {
		this.attack = attack;
		this.defense = defense;
		this.health = health;
		this.damage = damage;
		this.damageReduction = damageReduction;
	}

	@Override
	public ArmyStats clone() {
		return new ArmyStats(attack, defense, health, damage, damageReduction);
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

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getDamageReduction() {
		return damageReduction;
	}

	public void setDamageReduction(double damageReduction) {
		this.damageReduction = damageReduction;
	}


}
