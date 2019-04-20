package main.java.web.dto;

import java.util.List;
import java.util.Map;

import main.java.config.ArmyType;

public class UserInputParameters {

	private int castleLevel = 30;
	private int troopsAmount = 200000;
	private boolean useMarchCapacityBoost = true;
	private boolean useMarchCapacitySpell = true;

	private int maxTier = 10;

	private double troopAttack;
	private double troopDefense;
	private double troopHealth;

	private double infantryAttack;
	private double infantryDefense;
	private double infantryHealth;

	private double cavalryAttack;
	private double cavalryDefense;
	private double cavalryHealth;

	private double distanceAttack;
	private double distanceDefense;
	private double distanceHealth;

	private double artilleryAttack;
	private double artilleryDefense;
	private double artilleryHealth;

	private double troopDamage;
	private double infantryDamage;
	private double cavalryDamage;
	private double distanceDamage;
	private double artilleryDamage;

	private double troopDamageReduction;
	private double infantryDamageReduction;
	private double cavalryDamageReduction;
	private double distanceDamageReduction;
	private double artilleryDamageReduction;

	private double distanceVsInfantryDamage;
	private double distanceVsCavalryDamage;
	private double distanceVsArtilleryDamage;
	private double cavalryVsInfantryDamage;
	private double cavalryVsDistanceDamage;
	private double cavalryVsArtilleryDamage;
	private double infantryVsDistanceDamageReduction;
	private double infantryVsCavalryDamageReduction;
	private double infantryVsArtilleryDamageReduction;
	private Map<ArmyType, List<Integer>> army;

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

	public double getTroopDefense() {
		return troopDefense;
	}

	public void setTroopDefense(double troopDefense) {
		this.troopDefense = troopDefense;
	}

	public double getTroopHealth() {
		return troopHealth;
	}

	public void setTroopHealth(double troopHealth) {
		this.troopHealth = troopHealth;
	}

	public double getInfantryAttack() {
		return infantryAttack;
	}

	public void setInfantryAttack(double infantryAttack) {
		this.infantryAttack = infantryAttack;
	}

	public double getInfantryDefense() {
		return infantryDefense;
	}

	public void setInfantryDefense(double infantryDefense) {
		this.infantryDefense = infantryDefense;
	}

	public double getInfantryHealth() {
		return infantryHealth;
	}

	public void setInfantryHealth(double infantryHealth) {
		this.infantryHealth = infantryHealth;
	}

	public double getCavalryAttack() {
		return cavalryAttack;
	}

	public void setCavalryAttack(double cavalryAttack) {
		this.cavalryAttack = cavalryAttack;
	}

	public double getCavalryDefense() {
		return cavalryDefense;
	}

	public void setCavalryDefense(double cavalryDefense) {
		this.cavalryDefense = cavalryDefense;
	}

	public double getCavalryHealth() {
		return cavalryHealth;
	}

	public void setCavalryHealth(double cavalryHealth) {
		this.cavalryHealth = cavalryHealth;
	}

	public double getDistanceAttack() {
		return distanceAttack;
	}

	public void setDistanceAttack(double distanceAttack) {
		this.distanceAttack = distanceAttack;
	}

	public double getDistanceDefense() {
		return distanceDefense;
	}

	public void setDistanceDefense(double distanceDefense) {
		this.distanceDefense = distanceDefense;
	}

	public double getDistanceHealth() {
		return distanceHealth;
	}

	public void setDistanceHealth(double distanceHealth) {
		this.distanceHealth = distanceHealth;
	}

	public double getArtilleryAttack() {
		return artilleryAttack;
	}

	public void setArtilleryAttack(double artilleryAttack) {
		this.artilleryAttack = artilleryAttack;
	}

	public double getArtilleryDefense() {
		return artilleryDefense;
	}

	public void setArtilleryDefense(double artilleryDefense) {
		this.artilleryDefense = artilleryDefense;
	}

	public double getArtilleryHealth() {
		return artilleryHealth;
	}

	public void setArtilleryHealth(double artilleryHealth) {
		this.artilleryHealth = artilleryHealth;
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

	public double getTroopDamageReduction() {
		return troopDamageReduction;
	}

	public void setTroopDamageReduction(double troopDamageReduction) {
		this.troopDamageReduction = troopDamageReduction;
	}

	public double getInfantryDamageReduction() {
		return infantryDamageReduction;
	}

	public void setInfantryDamageReduction(double infantryDamageReduction) {
		this.infantryDamageReduction = infantryDamageReduction;
	}

	public double getCavalryDamageReduction() {
		return cavalryDamageReduction;
	}

	public void setCavalryDamageReduction(double cavalryDamageReduction) {
		this.cavalryDamageReduction = cavalryDamageReduction;
	}

	public double getDistanceDamageReduction() {
		return distanceDamageReduction;
	}

	public void setDistanceDamageReduction(double distanceDamageReduction) {
		this.distanceDamageReduction = distanceDamageReduction;
	}

	public double getArtilleryDamageReduction() {
		return artilleryDamageReduction;
	}

	public void setArtilleryDamageReduction(double artilleryDamageReduction) {
		this.artilleryDamageReduction = artilleryDamageReduction;
	}

	public double getDistanceVsInfantryDamage() {
		return distanceVsInfantryDamage;
	}

	public void setDistanceVsInfantryDamage(double distanceVsInfantryDamage) {
		this.distanceVsInfantryDamage = distanceVsInfantryDamage;
	}

	public double getDistanceVsCavalryDamage() {
		return distanceVsCavalryDamage;
	}

	public void setDistanceVsCavalryDamage(double distanceVsCavalryDamage) {
		this.distanceVsCavalryDamage = distanceVsCavalryDamage;
	}

	public double getDistanceVsArtilleryDamage() {
		return distanceVsArtilleryDamage;
	}

	public void setDistanceVsArtilleryDamage(double distanceVsArtilleryDamage) {
		this.distanceVsArtilleryDamage = distanceVsArtilleryDamage;
	}

	public double getCavalryVsInfantryDamage() {
		return cavalryVsInfantryDamage;
	}

	public void setCavalryVsInfantryDamage(double cavalryVsInfantryDamage) {
		this.cavalryVsInfantryDamage = cavalryVsInfantryDamage;
	}

	public double getCavalryVsDistanceDamage() {
		return cavalryVsDistanceDamage;
	}

	public void setCavalryVsDistanceDamage(double cavalryVsDistanceDamage) {
		this.cavalryVsDistanceDamage = cavalryVsDistanceDamage;
	}

	public double getCavalryVsArtilleryDamage() {
		return cavalryVsArtilleryDamage;
	}

	public void setCavalryVsArtilleryDamage(double cavalryVsArtilleryDamage) {
		this.cavalryVsArtilleryDamage = cavalryVsArtilleryDamage;
	}

	public double getInfantryVsDistanceDamageReduction() {
		return infantryVsDistanceDamageReduction;
	}

	public void setInfantryVsDistanceDamageReduction(double infantryVsDistanceDamageReduction) {
		this.infantryVsDistanceDamageReduction = infantryVsDistanceDamageReduction;
	}

	public double getInfantryVsCavalryDamageReduction() {
		return infantryVsCavalryDamageReduction;
	}

	public void setInfantryVsCavalryDamageReduction(double infantryVsCavalryDamageReduction) {
		this.infantryVsCavalryDamageReduction = infantryVsCavalryDamageReduction;
	}

	public double getInfantryVsArtilleryDamageReduction() {
		return infantryVsArtilleryDamageReduction;
	}

	public void setInfantryVsArtilleryDamageReduction(double infantryVsArtilleryDamageReduction) {
		this.infantryVsArtilleryDamageReduction = infantryVsArtilleryDamageReduction;
	}

	public Map<ArmyType, List<Integer>> getArmy() {
		return army;
	}

	public void setArmy(Map<ArmyType, List<Integer>> army) {
		this.army = army;
	}

	@Override
	public String toString() {
		return "UserInputParameters [castleLevel=" + castleLevel + ", troopsAmount=" + troopsAmount
				+ ", useMarchCapacityBoost=" + useMarchCapacityBoost + ", useMarchCapacitySpell="
				+ useMarchCapacitySpell + ", maxTier=" + maxTier + ", troopAttack=" + troopAttack + ", troopDefense="
				+ troopDefense + ", troopHealth=" + troopHealth + ", infantryAttack=" + infantryAttack
				+ ", infantryDefense=" + infantryDefense + ", infantryHealth=" + infantryHealth + ", cavalryAttack="
				+ cavalryAttack + ", cavalryDefense=" + cavalryDefense + ", cavalryHealth=" + cavalryHealth
				+ ", distanceAttack=" + distanceAttack + ", distanceDefense=" + distanceDefense + ", distanceHealth="
				+ distanceHealth + ", artilleryAttack=" + artilleryAttack + ", artilleryDefense=" + artilleryDefense
				+ ", artilleryHealth=" + artilleryHealth + ", troopDamage=" + troopDamage + ", infantryDamage="
				+ infantryDamage + ", cavalryDamage=" + cavalryDamage + ", distanceDamage=" + distanceDamage
				+ ", artilleryDamage=" + artilleryDamage + ", troopDamageReduction=" + troopDamageReduction
				+ ", infantryDamageReduction=" + infantryDamageReduction + ", cavalryDamageReduction="
				+ cavalryDamageReduction + ", distanceDamageReduction=" + distanceDamageReduction
				+ ", artilleryDamageReduction=" + artilleryDamageReduction + ", distanceVsInfantryDamage="
				+ distanceVsInfantryDamage + ", distanceVsCavalryDamage=" + distanceVsCavalryDamage
				+ ", distanceVsArtilleryDamage=" + distanceVsArtilleryDamage + ", cavalryVsInfantryDamage="
				+ cavalryVsInfantryDamage + ", cavalryVsDistanceDamage=" + cavalryVsDistanceDamage
				+ ", cavalryVsArtilleryDamage=" + cavalryVsArtilleryDamage + ", infantryVsDistanceDamageReduction="
				+ infantryVsDistanceDamageReduction + ", infantryVsCavalryDamageReduction="
				+ infantryVsCavalryDamageReduction + ", infantryVsArtilleryDamageReduction="
				+ infantryVsArtilleryDamageReduction + ", army=" + army + "]";
	}

}
