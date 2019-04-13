package main.java.wolf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import main.java.config.ArmySubType;
import main.java.config.ArmyType;
import main.java.config.ConfigManager;
import main.java.config.Configuration;
import main.java.config.ArmyStats;
import main.java.config.UserInputParameters;

public class WolfCalculationsHelper {

    public final Map<ArmyType, Double> ATTACK_MODIFIERS;
    public final Map<ArmyType, Double> DAMAGE_MODIFIERS;
    public final Map<ArmyType, ArmySubType[]> TYPE_TO_SUBTYPE_MAP;
    public final double CAVALRY_VS_INFANTRY_DAMAGE;
    public final double DISTANCE_VS_INFANTRY_DAMAGE;
	public final Map<ArmyType, ArrayList<ArmyStats>> BASE_UNIT_STATS_PER_ARMYTYPE;


    public WolfCalculationsHelper(UserInputParameters input) {
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
            tempMap.put(ArmyType.DISTANCE, input.getTroopDamage() + input.getDistanceDamage());
            tempMap.put(ArmyType.CAVALRY, input.getTroopDamage() + input.getCavalryDamage());
            tempMap.put(ArmyType.INFANTRY, input.getTroopDamage() + input.getInfantryDamage());
            tempMap.put(ArmyType.ARTILLERY, input.getTroopDamage() + input.getArtilleryDamage());
            DAMAGE_MODIFIERS = Collections.unmodifiableMap(tempMap);
        }
    }
}
