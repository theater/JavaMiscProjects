package damage_calculator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import config.UserInputParameters;

public class Army implements Comparable<Army> {

    Logger logger = Logger.getLogger(Army.class);

    private static final int MAX_EFFICIENCY_FACTOR = 3;
    private static Map<ArmyType, Double> ATTACK_MODIFIERS;
    private static Map<ArmyType, Double> DAMAGE_MODIFIERS;
    private static UserInputParameters input;

    private ArmyType type;
    private ArmySubType subType;
    private int tier;
    private double attackEfficiency = 1;
    private int baseAttack;
    private double calculatedFinalDamage;
    private int troopsNumber = 0;

    public Army(ArmyType type, int tier, UserInputParameters input) {
        initializeStaticFields(input);

        this.type = type;
        this.tier = tier;
        subType = DamageCalculator.configuration.TYPE_TO_SUBTYPE_MAP.get(type)[tier];
        baseAttack = DamageCalculator.configuration.BASE_ATTACK_FACTORS.get(type)[tier];
        switch (type) {
            case CAVALRY:
                attackEfficiency = Math.min(MAX_EFFICIENCY_FACTOR, calculateAttackEfficiency() + input.cavalryVsInfantryDamage / 100);
                break;
            case DISTANCE:
                attackEfficiency = Math.min(MAX_EFFICIENCY_FACTOR, calculateAttackEfficiency() + input.distanceVsInfantryDamage / 100);
                break;
            default:
                break;
        }
        calculatedFinalDamage = calculateDamage();
    }

    private void initializeStaticFields(UserInputParameters inputParameters) {
        if (Army.input == null) {
            if (inputParameters == null) {
                throw new IllegalArgumentException("Cannot initialize anything without proper input. InputParameters are null.");
            }
            setInput(inputParameters);
        }

        if (ATTACK_MODIFIERS == null) {
            Map<ArmyType, Double> tempMap = new HashMap<>();
            tempMap.put(ArmyType.DISTANCE, inputParameters.troopAttack + inputParameters.distanceAttack);
            tempMap.put(ArmyType.CAVALRY, inputParameters.troopAttack + inputParameters.cavalryAttack);
            tempMap.put(ArmyType.INFANTRY, inputParameters.troopAttack + inputParameters.infantryAttack);
            tempMap.put(ArmyType.ARTILLERY, inputParameters.troopAttack + inputParameters.artilleryAttack);
            ATTACK_MODIFIERS = Collections.unmodifiableMap(tempMap);
        }

        if (DAMAGE_MODIFIERS == null) {
            Map<ArmyType, Double> tempMap = new HashMap<>();
            tempMap.put(ArmyType.DISTANCE, inputParameters.troopDamage + inputParameters.distanceDamage);
            tempMap.put(ArmyType.CAVALRY, inputParameters.troopDamage + inputParameters.cavalryDamage);
            tempMap.put(ArmyType.INFANTRY, inputParameters.troopDamage + inputParameters.infantryDamage);
            tempMap.put(ArmyType.ARTILLERY, inputParameters.troopDamage + inputParameters.artilleryDamage);
            DAMAGE_MODIFIERS = Collections.unmodifiableMap(tempMap);
        }

    }

    private double calculateAttackEfficiency() {
        return subType == ArmySubType.GRENADIERS ? 1.2 : subType == ArmySubType.LIGHT_CAVALRY ? 0.8 : 1;
    }

    private double calculateDamage() {
        int baseAttack = getBaseAttack();
        logger.trace(this + " base attack:\t\t" + baseAttack);

        double modifiedAttack = baseAttack * (1 + (ATTACK_MODIFIERS.get(getType())) / 100);
        logger.trace(this + " modified attack:\t" + modifiedAttack);

        int defense = 0;

        double baseDamage = input.limitArmyDamage ? modifiedAttack * Math.min(0.75, modifiedAttack / (modifiedAttack + defense)) : Math.pow(modifiedAttack, 2) / (modifiedAttack + defense);
        logger.trace(this + " base damage:\t\t" + baseDamage);

        double efficiencyFactor = getAttackEfficiency();
        logger.trace(this + " efficiency:\t\t" + efficiencyFactor);

        double calculatedDamage = baseDamage * Math.min(1 + (DAMAGE_MODIFIERS.get(getType()) / 100), 3) * efficiencyFactor;
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

    public double getAttackEfficiency() {
        return attackEfficiency;
    }

    @Override
    public String toString() {
        return "Type=" + type + "\tT[" + (tier + 1) + "]:\t";
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subType == null) ? 0 : subType.hashCode());
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
        Army other = (Army) obj;
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
    public int compareTo(Army o) {
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

    public static UserInputParameters getInput() {
        return input;
    }

    public static void setInput(UserInputParameters input) {
        Army.input = input;
    }

}
