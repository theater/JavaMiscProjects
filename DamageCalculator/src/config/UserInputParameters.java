package config;


public class UserInputParameters {

    public int castleLevel = 30;
    public int troopsAmount = 100000;
    public boolean useMarchCapacityBoost = true;
    public boolean useMarchCapacitySpell = true;
    public boolean limitArmyDamage = false;

    public int maxTier = 10;

    public double troopAttack = 100;
    public double infantryAttack = 100;
    public double cavalryAttack = 100;
    public double distanceAttack = 100;
    public double artilleryAttack = 100;

    public double troopDamage = 100;
    public double infantryDamage = 100;
    public double cavalryDamage = 100;
    public double distanceDamage = 100;
    public double artilleryDamage = 100;

    public double distanceVsInfantryDamage = 100;
    public double cavalryVsInfantryDamage = 100;

    @Override
    public String toString() {
        return "InputParameters [castleLevel=" + castleLevel + ", troopsAmount=" + troopsAmount + ", useMarchCapacityBoost=" + useMarchCapacityBoost + ", useMarchCapacitySpell=" +
                useMarchCapacitySpell + ", maxTier=" + maxTier + ", troopAttack=" + troopAttack + ", infantryAttack=" + infantryAttack + ", cavalryAttack=" + cavalryAttack + ", distanceAttack=" +
                distanceAttack + ", artilleryAttack=" + artilleryAttack + ", troopDamage=" + troopDamage + ", infantryDamage=" + infantryDamage + ", cavalryDamage=" + cavalryDamage +
                ", distanceDamage=" + distanceDamage + ", artilleryDamage=" + artilleryDamage + ", distanceVsInfantryDamage=" + distanceVsInfantryDamage + ", cavalryVsInfantryDamage=" +
                cavalryVsInfantryDamage + "]";
    }
}
