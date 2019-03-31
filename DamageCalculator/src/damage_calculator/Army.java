package damage_calculator;

import org.apache.log4j.Logger;

public class Army implements Comparable<Army> {

    Logger logger = Logger.getLogger(Army.class);

    private static final int MAX_EFFICIENCY_FACTOR = 2;

    private static InputParameters input;

    private ArmyType type;
    private ArmySubType subType;
    private int tier;
    private double attackEfficiency = 1;
    private int baseAttack;
    private double calculatedFinalDamage;
    private int troopsNumber = 0;

    public Army(ArmyType type, int tier) {
        this.type = type;
        this.tier = tier;
        subType = StaticData.TYPE_TO_SUBTYPE_MAP.get(type)[tier];
        baseAttack = StaticData.BASE_ATTACK_FACTORS.get(type)[tier];
        switch (type) {
            case CAVALRY:
                attackEfficiency = Math.min(MAX_EFFICIENCY_FACTOR, calculateAttackEfficiency() + input.cavalryVsInfantryDamage);
                break;
            case DISTANCE:
                attackEfficiency = Math.min(MAX_EFFICIENCY_FACTOR, calculateAttackEfficiency() + input.distanceVsInfantryDamage);
                break;
            default:
                break;
        }
        calculatedFinalDamage = calculateDamage();
    }

    private double calculateAttackEfficiency() {
        return subType == ArmySubType.GRENADIERS ? 1.2 : subType == ArmySubType.LIGHT_CAVALRY ? 0.8 : 1;
    }

    private double calculateDamage() {
        int baseAttack = getBaseAttack();
        logger.trace(this + " base attack:\t\t" + baseAttack);

        double modifiedAttack = baseAttack * (1 + (StaticData.ATTACK_MODIFIERS.get(getType())) / 100);
        logger.trace(this + " modified attack:\t" + modifiedAttack);

        int defense = 0;

        double baseDamage = input.limitArmyDamage ? modifiedAttack * Math.min(0.75, modifiedAttack / (modifiedAttack + defense)) : Math.pow(modifiedAttack, 2) / (modifiedAttack + defense);
        logger.trace(this + " base damage:\t\t" + baseDamage);

        double efficiencyFactor = getAttackEfficiency();
        logger.trace(this + " efficiency:\t\t" + efficiencyFactor);

        double calculatedDamage = baseDamage * Math.min(1 + (StaticData.DAMAGE_MODIFIERS.get(getType()) / 100), 3) * efficiencyFactor;
        logger.trace(this + " calculated damage:\t" + calculatedDamage);

        return calculatedDamage;
    }

    public void addUnit(int unitStep) {
        setTroopsNumber(getTroopsNumber() + unitStep);
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

    public static InputParameters getInput() {
        return input;
    }

    public static void setInput(InputParameters input) {
        Army.input = input;
    }

}
