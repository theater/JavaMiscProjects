package config;

import java.util.Arrays;
import java.util.Map;

import damage_calculator.ArmySubType;
import damage_calculator.ArmyType;

public class Configuration {

    private int ABSOLUTE_MAX_TIER = 12;
    public Map<Integer, Integer> CASTLE_BASE_MARCH_CAPACITY;
    public Map<ArmyType, ArmySubType[]> TYPE_TO_SUBTYPE_MAP;
    public ArmySubType[] distanceSubtypes;
    public ArmySubType[] cavalrySubtypes;
    public Map<ArmyType, int[]> BASE_ATTACK_FACTORS;

    @Override
    public String toString() {
        return "Configuration [ABSOLUTE_MAX_TIER=" + ABSOLUTE_MAX_TIER + ", CASTLE_BASE_MARCH_CAPACITY=" + CASTLE_BASE_MARCH_CAPACITY + ", TYPE_TO_SUBTYPE_MAP=" + TYPE_TO_SUBTYPE_MAP +
                ", distanceSubtypes=" + Arrays.toString(distanceSubtypes) + ", cavalrySubtypes=" + Arrays.toString(cavalrySubtypes) + ", BASE_ATTACK_FACTORS=" + BASE_ATTACK_FACTORS + "]";
    }


}
