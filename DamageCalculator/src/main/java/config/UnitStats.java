package main.java.config;

public class UnitStats {

	private int tier;
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

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	@Override
	public String toString() {
		return "UnitStats [attack=" + attack + ", defense=" + defense + ", health=" + health + "]";
	}

}
