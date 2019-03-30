import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StaticData {

    public static final int CASTLE_LEVEL = 30;
    public static final int TROOPS_AMOUNT = 208030;
    public static final boolean USE_MARCH_CAPACITY_BOOST = true;
    public static final boolean USE_MARCH_CAPACITY_SPELL = false;


    public static final int MAX_TIER = 10;

    private static final int TROOP_ATTACK = 477;
    private static final int INFANTRY_ATTACK = 758;
    private static final int CAVALRY_ATTACK = 770;
    private static final int DISTANCE_ATTACK = 841;
    // private static final int ARTILLERY_ATTACK = 50;

    private static final int TROOP_DAMAGE = 107;
    private static final int INFANTRY_DAMAGE = 153;
    private static final int CAVALRY_DAMAGE = 198;
    private static final int DISTANCE_DAMAGE = 183;
    // private static final int ARTILLERY_DAMAGE = 50;

    public static final int DISTANCE_VS_INF_DAMAGE = 183;
    public static final int CAVALRY_VS_INF_DAMAGE = 183;


    private static final int ABSOLUTE_MAX_TIER = 12;

    public static final Map<ArmyType, ArmySubType[]> TYPE_TO_SUBTYPE_MAP;
    // Array index is tier number -1, i.e. tier 5 has index 4, value is subtype
    static {
        Map<ArmyType, ArmySubType[]> tempMap = new HashMap<>();
        ArmySubType[] distanceSubtypes = new ArmySubType[ABSOLUTE_MAX_TIER];
        distanceSubtypes[0] = ArmySubType.RIFLEMEN;
        distanceSubtypes[1] = ArmySubType.GRENADIERS;
        distanceSubtypes[2] = ArmySubType.GRENADIERS;
        distanceSubtypes[3] = ArmySubType.RIFLEMEN;
        distanceSubtypes[4] = ArmySubType.GRENADIERS;
        distanceSubtypes[5] = ArmySubType.GRENADIERS;
        distanceSubtypes[6] = ArmySubType.RIFLEMEN;
        distanceSubtypes[7] = ArmySubType.RIFLEMEN;
        distanceSubtypes[8] = ArmySubType.GRENADIERS;
        distanceSubtypes[9] = ArmySubType.RIFLEMEN;
        distanceSubtypes[10] = ArmySubType.GRENADIERS;
        distanceSubtypes[11] = ArmySubType.RIFLEMEN;
        tempMap.put(ArmyType.DISTANCE, distanceSubtypes);

        ArmySubType[] cavalrySubtypes = new ArmySubType[ABSOLUTE_MAX_TIER];
        cavalrySubtypes[0] = ArmySubType.HEAVY_CAVALRY;
        cavalrySubtypes[1] = ArmySubType.LIGHT_CAVALRY;
        cavalrySubtypes[2] = ArmySubType.HEAVY_CAVALRY;
        cavalrySubtypes[3] = ArmySubType.HEAVY_CAVALRY;
        cavalrySubtypes[4] = ArmySubType.LIGHT_CAVALRY;
        cavalrySubtypes[5] = ArmySubType.HEAVY_CAVALRY;
        cavalrySubtypes[6] = ArmySubType.LIGHT_CAVALRY;
        cavalrySubtypes[7] = ArmySubType.HEAVY_CAVALRY;
        cavalrySubtypes[8] = ArmySubType.LIGHT_CAVALRY;
        cavalrySubtypes[9] = ArmySubType.LIGHT_CAVALRY;
        cavalrySubtypes[10] = ArmySubType.HEAVY_CAVALRY;
        cavalrySubtypes[11] = ArmySubType.LIGHT_CAVALRY;
        tempMap.put(ArmyType.CAVALRY, cavalrySubtypes);

        ArmySubType[] infantrySubtypes = new ArmySubType[ABSOLUTE_MAX_TIER];
        for (int i = 0; i < MAX_TIER; i++) {
            if (i % 2 == 0) {
                infantrySubtypes[i] = ArmySubType.MUSKETEERS;
            } else {
                infantrySubtypes[i] = ArmySubType.RIFLEMEN;
            }
        }
        tempMap.put(ArmyType.INFANTRY, infantrySubtypes);

        TYPE_TO_SUBTYPE_MAP = Collections.unmodifiableMap(tempMap);
    }

    public static final Map<ArmyType, int[]> BASE_ATTACK_FACTORS;
    static {
        Map<ArmyType, int[]> tempMap = new HashMap<>();
        tempMap.put(ArmyType.DISTANCE, new int[] { 106, 143, 165, 161, 218, 251, 245, 282, 382, 373, 505, 493 });
        tempMap.put(ArmyType.CAVALRY, new int[] { 113, 111, 150, 173, 169, 228, 223, 302, 295, 339, 459, 449 });
        tempMap.put(ArmyType.INFANTRY, new int[] { 34, 39, 45, 52, 60, 68, 79, 91, 104, 120, 138, 158 });

        BASE_ATTACK_FACTORS = Collections.unmodifiableMap(tempMap);
    }

    public static final Map<ArmyType, Integer> ATTACK_MODIFIERS;
    static {
        Map<ArmyType, Integer> tempMap = new HashMap<>();
        tempMap.put(ArmyType.DISTANCE, TROOP_ATTACK + DISTANCE_ATTACK);
        tempMap.put(ArmyType.CAVALRY, TROOP_ATTACK + CAVALRY_ATTACK);
        tempMap.put(ArmyType.INFANTRY, TROOP_ATTACK + INFANTRY_ATTACK);

        ATTACK_MODIFIERS = Collections.unmodifiableMap(tempMap);
    }

    public static final Map<ArmyType, Integer> DAMAGE_MODIFIERS;
    static {
        Map<ArmyType, Integer> tempMap = new HashMap<>();
        tempMap.put(ArmyType.DISTANCE, TROOP_DAMAGE + DISTANCE_DAMAGE);
        tempMap.put(ArmyType.CAVALRY, TROOP_DAMAGE + CAVALRY_DAMAGE);
        tempMap.put(ArmyType.INFANTRY, TROOP_DAMAGE + INFANTRY_DAMAGE);

        DAMAGE_MODIFIERS = Collections.unmodifiableMap(tempMap);
    }

    public static final Map<Integer, Integer> CASTLE_BASE_MARCH_CAPACITY;
    static {
        Map<Integer, Integer> tempMap = new HashMap<>();
        tempMap.put(30, 97500);
        tempMap.put(32, 106500);

        CASTLE_BASE_MARCH_CAPACITY = Collections.unmodifiableMap(tempMap);
    }

}