package main.java.wolf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.config.ArmySubType;
import main.java.config.ArmyType;
import main.java.config.CalculationsHelper;

public class WolfArmy implements Comparable<WolfArmy> {

    Logger logger = LoggerFactory.getLogger(WolfArmy.class);

    private static final int MAX_EFFICIENCY_FACTOR = 3;

    private ArmyType type;
    private ArmySubType subType;
    private int tier;
    private double attackEfficiency;
    private double baseAttack;
    private double calculatedFinalDamage;
    private int troopsNumber = 0;
    private CalculationsHelper helper;

	protected double defense;

    public WolfArmy(ArmyType type, int tier, CalculationsHelper helper) {
        this.type = type;
        this.tier = tier;
        this.helper = helper;
        subType = helper.TYPE_TO_SUBTYPE_MAP.get(type)[tier];
        baseAttack = helper.BASE_UNIT_STATS_PER_ARMYTYPE.get(type).get(tier).getAttack();
        attackEfficiency = calculateAttackEfficiency();
        calculatedFinalDamage = calculateDamage();
    }

    private double calculateAttackEfficiency() {
        double baseEfficiency = subType == ArmySubType.GRENADIERS ? 1.2 : subType == ArmySubType.LIGHT_CAVALRY ? 0.8 : 1;
        double efficiencyModifier = type == ArmyType.CAVALRY ? helper.CAVALRY_VS_INFANTRY_DAMAGE / 100 : type == ArmyType.DISTANCE ? helper.DISTANCE_VS_INFANTRY_DAMAGE / 100 : 0;
        double reduction = 0;
		return Math.min(MAX_EFFICIENCY_FACTOR, baseEfficiency + efficiencyModifier - reduction );
    }

    private double calculateDamage() {
        double baseAttack = getBaseAttack();
        logger.trace(this + " base attack:\t\t" + baseAttack);

        double modifiedAttack = baseAttack * (1 + (helper.ATTACK_MODIFIERS.get(getType())) / 100);
        logger.trace(this + " modified attack:\t" + modifiedAttack);

        double baseDamage = modifiedAttack * Math.min(0.75, modifiedAttack / (modifiedAttack + defense));
        logger.trace(this + " base damage:\t\t" + baseDamage);

        double efficiencyFactor = attackEfficiency;
        logger.trace(this + " efficiency:\t\t" + efficiencyFactor);

        double calculatedDamage = baseDamage * Math.min(1 + (helper.DAMAGE_MODIFIERS.get(getType()) / 100), 3) * efficiencyFactor;
        logger.trace(this + " calculated damage:\t" + calculatedDamage);

        return calculatedDamage;
    }

    public void addUnits(int numberOfUnits) {
        setTroopsNumber(getTroopsNumber() + numberOfUnits);
    }

    public double getCalculatedDamage() {
        return calculatedFinalDamage;
    }

    public ArmyType getType() {
        return type;
    }

    public ArmySubType getSubType() {
        return subType;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public String toString() {
        return "Type=" + type + "\tT[" + (tier + 1) + "]:\t";
    }

    public double getBaseAttack() {
        return baseAttack;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + tier;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        WolfArmy other = (WolfArmy) obj;
        if (type != other.type) {
            return false;
        }
        if (subType != other.subType) {
            return false;
        }
        if (tier != other.tier) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(WolfArmy o) {
        if (tier < o.tier) {
            return 1;
        } else if (tier > o.tier) {
            return -1;
        }

        return type.compareTo(o.type);
    }

    public int getTroopsNumber() {
        return troopsNumber;
    }

    public void setTroopsNumber(int troopsNumber) {
        this.troopsNumber = troopsNumber;
    }

}
