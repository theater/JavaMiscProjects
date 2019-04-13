package main.java.config;

import java.util.ArrayList;

public class ArmyStats {

	private int attack;
	private int defense;
	private int health;

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public String toString() {
		return "UnitStats [attack=" + attack + ", defense=" + defense + ", health=" + health + "]";
	}

	@Override
	public ArmyStats clone() {
		ArmyStats unitStats = new ArmyStats();
		unitStats.setAttack(attack);
		unitStats.setDefense(defense);
		unitStats.setHealth(health);
		return unitStats;
	}

}
