package main.java.config;


public enum ArmySubType {
    RIFLEMEN(20),
    GRENADIERS(19),
    MUSKETEERS(15),
    PIKEMEN(14),
    LIGHT_CAVALRY(17),
    HEAVY_CAVALRY(16),
    CANNON(13),
    MORTAR(18);
    
    private int attackSpeed;
    
    ArmySubType(int speed) {
    	this.attackSpeed = speed;
    }

	public int getAttackSpeed() {
		return attackSpeed;
	}
}
