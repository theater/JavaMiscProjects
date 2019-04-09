package main.java.calculator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import main.java.config.Configuration;
import main.java.config.UserInputParameters;

public class CalculationsHelper {

    public final Map<ArmyType, Double> ATTACK_MODIFIERS;
    public final Map<ArmyType, Double> DAMAGE_MODIFIERS;
    public final Map<ArmyType, ArmySubType[]> TYPE_TO_SUBTYPE_MAP;
    public final Map<ArmyType, int[]> BASE_ATTACK_FACTORS;
    public final double CAVALRY_VS_INFANTRY_DAMAGE;
    public final double DISTANCE_VS_INFANTRY_DAMAGE;
    public boolean limitArmyDamage;


    public CalculationsHelper(UserInputParameters input, Configuration configuration) {
        CAVALRY_VS_INFANTRY_DAMAGE = input.getCavalryVsInfantryDamage();
        DISTANCE_VS_INFANTRY_DAMAGE = input.getDistanceVsInfantryDamage();
        limitArmyDamage = input.isLimitArmyDamage();
        TYPE_TO_SUBTYPE_MAP = Collections.unmodifiableMap(configuration.TYPE_TO_SUBTYPE_MAP);
        BASE_ATTACK_FACTORS = Collections.unmodifiableMap(configuration.BASE_ATTACK_FACTORS);

        {
            Map<ArmyType, Double> tempMap = new HashMap<>();
            tempMap.put(ArmyType.DISTANCE, input.getTroopAttack() + input.getDistanceAttack());
            tempMap.put(ArmyType.CAVALRY, input.getTroopAttack() + input.getCavalryAttack());
            tempMap.put(ArmyType.INFANTRY, input.getTroopAttack() + input.getInfantryAttack());
            tempMap.put(ArmyType.ARTILLERY, input.getTroopAttack() + input.getArtilleryAttack());
            ATTACK_MODIFIERS = Collections.unmodifiableMap(tempMap);
        }
        {
            Map<ArmyType, Double> tempMap = new HashMap<>();
            tempMap.put(ArmyType.DISTANCE, input.getTroopDamage() + input.getDistanceDamage());
            tempMap.put(ArmyType.CAVALRY, input.getTroopDamage() + input.getCavalryDamage());
            tempMap.put(ArmyType.INFANTRY, input.getTroopDamage() + input.getInfantryDamage());
            tempMap.put(ArmyType.ARTILLERY, input.getTroopDamage() + input.getArtilleryDamage());
            DAMAGE_MODIFIERS = Collections.unmodifiableMap(tempMap);
        }
    }
}
