package main.java.wolf;

import main.java.config.ArmySubType;
import main.java.config.ArmyType;
import main.java.config.CalculationsHelper;

public abstract class Army implements Comparable<Army> {

    protected static final int MAX_EFFICIENCY_FACTOR = 3;

    private int tier;
    private double baseAttack;
    private double calculatedFinalDamage;
    private int troopsNumber = 0;

    protected ArmyType type;
    protected ArmySubType subType;
    protected double attackEfficiency;
    protected CalculationsHelper helper;
    protected double enemyDefense;

    public Army(ArmyType type, int tier, CalculationsHelper helper, double enemyDefense) {
        this.type = type;
        this.tier = tier;
        this.helper = helper;
        this.enemyDefense = enemyDefense;
        subType = helper.TYPE_TO_SUBTYPE_MAP.get(type)[tier];
        baseAttack = helper.BASE_UNIT_STATS_PER_ARMYTYPE.get(type).get(tier).getAttack();
        attackEfficiency = calculateAttackEfficiency();
        calculatedFinalDamage = calculateDamage();
    }

    protected abstract double calculateDamage();

    protected abstract double calculateAttackEfficiency();

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
}
