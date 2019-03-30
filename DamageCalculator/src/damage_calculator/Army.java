package damage_calculator;
public class Army implements Comparable<Army> {

    private static final int MAX_EFFICIENCY_FACTOR = 2;

    private ArmyType type;
    private ArmySubType subType;
    private int tier;
    private double attackEfficiency = 1;
    private double modifiedAttackEfficiency;
    private int baseAttack;

    public Army(ArmyType type, int tier) {
        this.type = type;
        this.tier = tier;
        subType = StaticData.TYPE_TO_SUBTYPE_MAP.get(type)[tier];
        baseAttack = StaticData.BASE_ATTACK_FACTORS.get(type)[tier];
        attackEfficiency = subType == ArmySubType.GRENADIERS ? 1.2 : subType == ArmySubType.LIGHT_CAVALRY ? 0.8 : 1;
        switch (type) {
            case CAVALRY:
                modifiedAttackEfficiency = Math.min(MAX_EFFICIENCY_FACTOR, attackEfficiency + StaticData.CAVALRY_VS_INF_DAMAGE);
                break;
            case DISTANCE:
                modifiedAttackEfficiency = Math.min(MAX_EFFICIENCY_FACTOR, attackEfficiency + StaticData.DISTANCE_VS_INF_DAMAGE);
                break;
            default:
                modifiedAttackEfficiency = attackEfficiency;
        }
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
        return "Type=" + type + "\tSubType=" + subType + "\tT[" + (tier + 1) + "]";
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public double getModifiedAttackEfficiency() {
        return modifiedAttackEfficiency;
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
}
