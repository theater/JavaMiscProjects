package damage_calculator;


public class InputParameters {

    public int castleLevel = 30;
    public int troopsAmount = 100000;
    public boolean useMarchCapacityBoost = true;
    public boolean useMarchCapacitySpell = true;
    public boolean limitArmyDamage = false;

    public int maxTier = 10;

    public int troopAttack = 100;
    public int infantryAttack = 100;
    public int cavalryAttack = 100;
    public int distanceAttack = 100;
    public int artilleryAttack = 100;

    public int troopDamage = 100;
    public int infantryDamage = 100;
    public int cavalryDamage = 100;
    public int distanceDamage = 100;
    public int artilleryDamage = 100;

    public int distanceVsInfantryDamage = 100;
    public int cavalryVsInfantryDamage = 100;

    @Override
    public String toString() {
        return "InputParameters [castleLevel=" + castleLevel + ", troopsAmount=" + troopsAmount + ", useMarchCapacityBoost=" + useMarchCapacityBoost + ", useMarchCapacitySpell=" +
                useMarchCapacitySpell + ", maxTier=" + maxTier + ", troopAttack=" + troopAttack + ", infantryAttack=" + infantryAttack + ", cavalryAttack=" + cavalryAttack + ", distanceAttack=" +
                distanceAttack + ", artilleryAttack=" + artilleryAttack + ", troopDamage=" + troopDamage + ", infantryDamage=" + infantryDamage + ", cavalryDamage=" + cavalryDamage +
                ", distanceDamage=" + distanceDamage + ", artilleryDamage=" + artilleryDamage + ", distanceVsInfantryDamage=" + distanceVsInfantryDamage + ", cavalryVsInfantryDamage=" +
                cavalryVsInfantryDamage + "]";
    }
}
