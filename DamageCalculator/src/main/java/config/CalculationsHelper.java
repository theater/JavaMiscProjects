package main.java.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import main.java.web.dto.UserInputParameters;

public class CalculationsHelper {

    public final Map<ArmyType, Double> ATTACK_MODIFIERS;
    public final Map<ArmyType, Double> DEFENSE_MODIFIERS;
    public final Map<ArmyType, Double> HEALTH_MODIFIERS;
    public final Map<ArmyType, Double> DAMAGE_MODIFIERS;
    public final Map<ArmyType, Double> DAMAGE_REDUCTION_MODIFIERS;
    public final Map<ArmyType, ArmySubType[]> TYPE_TO_SUBTYPE_MAP;
    public final double CAVALRY_VS_INFANTRY_DAMAGE;
    public final double DISTANCE_VS_INFANTRY_DAMAGE;
	public final Map<ArmyType, ArrayList<ArmyStats>> BASE_UNIT_STATS_PER_ARMYTYPE;
	public final Map<ArmyType, Map<ArmyType, Double>> SPECIFIC_EFFICIENCY;


    public CalculationsHelper(UserInputParameters input) {
        CAVALRY_VS_INFANTRY_DAMAGE = input.getCavalryVsInfantryDamage();
        DISTANCE_VS_INFANTRY_DAMAGE = input.getDistanceVsInfantryDamage();

        Configuration configuration = ConfigManager.getInstance().getConfiguration();
		TYPE_TO_SUBTYPE_MAP = Collections.unmodifiableMap(configuration.TYPE_TO_SUBTYPE_MAP);
        BASE_UNIT_STATS_PER_ARMYTYPE = Collections.unmodifiableMap(configuration.BASE_UNIT_STATS_PER_ARMYTYPE);
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
        	tempMap.put(ArmyType.DISTANCE, input.getTroopDefense() + input.getDistanceDefense());
        	tempMap.put(ArmyType.CAVALRY, input.getTroopDefense() + input.getCavalryDefense());
        	tempMap.put(ArmyType.INFANTRY, input.getTroopDefense() + input.getInfantryDefense());
        	tempMap.put(ArmyType.ARTILLERY, input.getTroopDefense() + input.getArtilleryDefense());
        	DEFENSE_MODIFIERS = Collections.unmodifiableMap(tempMap);
        }
        {
        	Map<ArmyType, Double> tempMap = new HashMap<>();
        	tempMap.put(ArmyType.DISTANCE, input.getTroopHealth() + input.getDistanceHealth());
        	tempMap.put(ArmyType.CAVALRY, input.getTroopHealth() + input.getCavalryHealth());
        	tempMap.put(ArmyType.INFANTRY, input.getTroopHealth() + input.getInfantryHealth());
        	tempMap.put(ArmyType.ARTILLERY, input.getTroopHealth() + input.getArtilleryHealth());
        	HEALTH_MODIFIERS = Collections.unmodifiableMap(tempMap);
        }
        {
            Map<ArmyType, Double> tempMap = new HashMap<>();
            tempMap.put(ArmyType.DISTANCE, input.getTroopDamage() + input.getDistanceDamage());
            tempMap.put(ArmyType.CAVALRY, input.getTroopDamage() + input.getCavalryDamage());
            tempMap.put(ArmyType.INFANTRY, input.getTroopDamage() + input.getInfantryDamage());
            tempMap.put(ArmyType.ARTILLERY, input.getTroopDamage() + input.getArtilleryDamage());
            DAMAGE_MODIFIERS = Collections.unmodifiableMap(tempMap);
        }
        {
            Map<ArmyType, Double> tempMap = new HashMap<>();
            tempMap.put(ArmyType.DISTANCE, input.getTroopDamageReduction() + input.getDistanceDamageReduction());
            tempMap.put(ArmyType.CAVALRY, input.getTroopDamageReduction() + input.getCavalryDamageReduction());
            tempMap.put(ArmyType.INFANTRY, input.getTroopDamageReduction() + input.getInfantryDamageReduction());
            tempMap.put(ArmyType.ARTILLERY, input.getTroopDamageReduction() + input.getArtilleryDamageReduction());
            DAMAGE_REDUCTION_MODIFIERS = Collections.unmodifiableMap(tempMap);
        }
        {
        	Map<ArmyType, Map<ArmyType, Double>> specificEfficiency = new HashMap<>();
        	Map<ArmyType, Double> tempMap = new HashMap<>();
        	tempMap.put(ArmyType.INFANTRY, input.getDistanceVsInfantryDamage());
        	tempMap.put(ArmyType.CAVALRY, input.getDistanceVsCavalryDamage());
        	tempMap.put(ArmyType.ARTILLERY, input.getDistanceVsArtilleryDamage());
        	specificEfficiency.put(ArmyType.DISTANCE, tempMap);

        	tempMap = new HashMap<>();
        	tempMap.put(ArmyType.INFANTRY, input.getCavalryVsInfantryDamage());
        	tempMap.put(ArmyType.DISTANCE, input.getCavalryVsDistanceDamage());
        	tempMap.put(ArmyType.ARTILLERY, input.getCavalryVsArtilleryDamage());
        	specificEfficiency.put(ArmyType.CAVALRY, tempMap);

        	tempMap = new HashMap<>();
        	tempMap.put(ArmyType.ARTILLERY, input.getInfantryVsArtilleryDamageReduction());
        	tempMap.put(ArmyType.DISTANCE, input.getInfantryVsDistanceDamageReduction());
        	tempMap.put(ArmyType.CAVALRY, input.getInfantryVsCavalryDamageReduction());
        	specificEfficiency.put(ArmyType.INFANTRY, tempMap);
        	SPECIFIC_EFFICIENCY = Collections.unmodifiableMap(specificEfficiency);
        }

    }
}
