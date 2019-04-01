package damage_calculator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import config.Configuration;
import config.UserInputParameters;

public class CalculationsHelper {

    public final Map<ArmyType, Double> ATTACK_MODIFIERS;
    public final Map<ArmyType, Double> DAMAGE_MODIFIERS;
    public final Map<ArmyType, ArmySubType[]> TYPE_TO_SUBTYPE_MAP;
    public final Map<ArmyType, int[]> BASE_ATTACK_FACTORS;
    public final double CAVALRY_VS_INFANTRY_DAMAGE;
    public final double DISTANCE_VS_INFANTRY_DAMAGE;
    public boolean limitArmyDamage;


    public CalculationsHelper(UserInputParameters input, Configuration configuration) {
        CAVALRY_VS_INFANTRY_DAMAGE = input.cavalryVsInfantryDamage;
        DISTANCE_VS_INFANTRY_DAMAGE = input.distanceVsInfantryDamage;
        limitArmyDamage = input.limitArmyDamage;
        TYPE_TO_SUBTYPE_MAP = Collections.unmodifiableMap(configuration.TYPE_TO_SUBTYPE_MAP);
        BASE_ATTACK_FACTORS = Collections.unmodifiableMap(configuration.BASE_ATTACK_FACTORS);

        {
            Map<ArmyType, Double> tempMap = new HashMap<>();
            tempMap.put(ArmyType.DISTANCE, input.troopAttack + input.distanceAttack);
            tempMap.put(ArmyType.CAVALRY, input.troopAttack + input.cavalryAttack);
            tempMap.put(ArmyType.INFANTRY, input.troopAttack + input.infantryAttack);
            tempMap.put(ArmyType.ARTILLERY, input.troopAttack + input.artilleryAttack);
            ATTACK_MODIFIERS = Collections.unmodifiableMap(tempMap);
        }
        {
            Map<ArmyType, Double> tempMap = new HashMap<>();
            tempMap.put(ArmyType.DISTANCE, input.troopDamage + input.distanceDamage);
            tempMap.put(ArmyType.CAVALRY, input.troopDamage + input.cavalryDamage);
            tempMap.put(ArmyType.INFANTRY, input.troopDamage + input.infantryDamage);
            tempMap.put(ArmyType.ARTILLERY, input.troopDamage + input.artilleryDamage);
            DAMAGE_MODIFIERS = Collections.unmodifiableMap(tempMap);
        }
    }
}
