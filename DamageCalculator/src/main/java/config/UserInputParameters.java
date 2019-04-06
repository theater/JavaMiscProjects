package main.java.config;


public class UserInputParameters {

    private int castleLevel = 30;
    private int troopsAmount = 100000;
    private boolean useMarchCapacityBoost = true;
    private boolean useMarchCapacitySpell = true;
    private boolean limitArmyDamage = false;

    private int maxTier = 10;

    private double troopAttack = 100;
    private double infantryAttack = 100;
    private double cavalryAttack = 100;
    private double distanceAttack = 100;
    private double artilleryAttack = 100;

    private double troopDamage = 100;
    private double infantryDamage = 100;
    private double cavalryDamage = 100;
    private double distanceDamage = 100;
    private double artilleryDamage = 100;

    private double distanceVsInfantryDamage = 100;
    private double cavalryVsInfantryDamage = 100;

    @Override
    public String toString() {
        return "InputParameters [castleLevel=" + castleLevel + ", troopsAmount=" + troopsAmount + ", useMarchCapacityBoost=" + useMarchCapacityBoost + ", useMarchCapacitySpell=" +
                useMarchCapacitySpell + ", maxTier=" + maxTier + ", troopAttack=" + troopAttack + ", infantryAttack=" + infantryAttack + ", cavalryAttack=" + cavalryAttack + ", distanceAttack=" +
                distanceAttack + ", artilleryAttack=" + artilleryAttack + ", troopDamage=" + troopDamage + ", infantryDamage=" + infantryDamage + ", cavalryDamage=" + cavalryDamage +
                ", distanceDamage=" + distanceDamage + ", artilleryDamage=" + artilleryDamage + ", distanceVsInfantryDamage=" + distanceVsInfantryDamage + ", cavalryVsInfantryDamage=" +
                cavalryVsInfantryDamage + "]";
    }

    public int getCastleLevel() {
        return castleLevel;
    }

    public void setCastleLevel(int castleLevel) {
        this.castleLevel = castleLevel;
    }

    public int getTroopsAmount() {
        return troopsAmount;
    }

    public void setTroopsAmount(int troopsAmount) {
        this.troopsAmount = troopsAmount;
    }

    public boolean isUseMarchCapacityBoost() {
        return useMarchCapacityBoost;
    }

    public void setUseMarchCapacityBoost(boolean useMarchCapacityBoost) {
        this.useMarchCapacityBoost = useMarchCapacityBoost;
    }

    public boolean isUseMarchCapacitySpell() {
        return useMarchCapacitySpell;
    }

    public void setUseMarchCapacitySpell(boolean useMarchCapacitySpell) {
        this.useMarchCapacitySpell = useMarchCapacitySpell;
    }

    public boolean isLimitArmyDamage() {
        return limitArmyDamage;
    }

    public void setLimitArmyDamage(boolean limitArmyDamage) {
        this.limitArmyDamage = limitArmyDamage;
    }

    public int getMaxTier() {
        return maxTier;
    }

    public void setMaxTier(int maxTier) {
        this.maxTier = maxTier;
    }

    public double getTroopAttack() {
        return troopAttack;
    }

    public void setTroopAttack(double troopAttack) {
        this.troopAttack = troopAttack;
    }

    public double getInfantryAttack() {
        return infantryAttack;
    }

    public void setInfantryAttack(double infantryAttack) {
        this.infantryAttack = infantryAttack;
    }

    public double getCavalryAttack() {
        return cavalryAttack;
    }

    public void setCavalryAttack(double cavalryAttack) {
        this.cavalryAttack = cavalryAttack;
    }

    public double getDistanceAttack() {
        return distanceAttack;
    }

    public void setDistanceAttack(double distanceAttack) {
        this.distanceAttack = distanceAttack;
    }

    public double getArtilleryAttack() {
        return artilleryAttack;
    }

    public void setArtilleryAttack(double artilleryAttack) {
        this.artilleryAttack = artilleryAttack;
    }

    public double getTroopDamage() {
        return troopDamage;
    }

    public void setTroopDamage(double troopDamage) {
        this.troopDamage = troopDamage;
    }

    public double getInfantryDamage() {
        return infantryDamage;
    }

    public void setInfantryDamage(double infantryDamage) {
        this.infantryDamage = infantryDamage;
    }

    public double getCavalryDamage() {
        return cavalryDamage;
    }

    public void setCavalryDamage(double cavalryDamage) {
        this.cavalryDamage = cavalryDamage;
    }

    public double getDistanceDamage() {
        return distanceDamage;
    }

    public void setDistanceDamage(double distanceDamage) {
        this.distanceDamage = distanceDamage;
    }

    public double getArtilleryDamage() {
        return artilleryDamage;
    }

    public void setArtilleryDamage(double artilleryDamage) {
        this.artilleryDamage = artilleryDamage;
    }

    public double getDistanceVsInfantryDamage() {
        return distanceVsInfantryDamage;
    }

    public void setDistanceVsInfantryDamage(double distanceVsInfantryDamage) {
        this.distanceVsInfantryDamage = distanceVsInfantryDamage;
    }

    public double getCavalryVsInfantryDamage() {
        return cavalryVsInfantryDamage;
    }

    public void setCavalryVsInfantryDamage(double cavalryVsInfantryDamage) {
        this.cavalryVsInfantryDamage = cavalryVsInfantryDamage;
    }
}
