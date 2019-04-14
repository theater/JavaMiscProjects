package main.java.battle;

import java.util.HashMap;
import java.util.Map;

import main.java.config.ArmySubType;

public class BattleHelper {
	public static Map<Integer, Map<ArmySubType, Integer>> ATTACKER_CHOICE_CRITERIA = new HashMap<>();
	static {
		Map<ArmySubType, Integer> tempMap = new HashMap<>();
		tempMap.put(ArmySubType.PIKEMEN, 0);
		tempMap.put(ArmySubType.MUSKETEERS, 1);
		tempMap.put(ArmySubType.CANNON, 2);
		tempMap.put(ArmySubType.LIGHT_CAVALRY, 3);
		tempMap.put(ArmySubType.HEAVY_CAVALRY, 4);
		tempMap.put(ArmySubType.RIFLEMEN, 5);
		tempMap.put(ArmySubType.GRENADIERS, 6);
		tempMap.put(ArmySubType.MORTAR, 7);
		ATTACKER_CHOICE_CRITERIA.put(1, tempMap);

		tempMap = new HashMap<>();
		tempMap.put(ArmySubType.LIGHT_CAVALRY, 0);
		tempMap.put(ArmySubType.HEAVY_CAVALRY, 1);
		tempMap.put(ArmySubType.PIKEMEN, 2);
		tempMap.put(ArmySubType.MUSKETEERS, 3);
		tempMap.put(ArmySubType.CANNON, 4);
		tempMap.put(ArmySubType.RIFLEMEN, 5);
		tempMap.put(ArmySubType.GRENADIERS, 6);
		tempMap.put(ArmySubType.MORTAR, 7);
		ATTACKER_CHOICE_CRITERIA.put(2, tempMap);

		tempMap = new HashMap<>();
		tempMap.put(ArmySubType.RIFLEMEN, 0);
		tempMap.put(ArmySubType.GRENADIERS, 1);
		tempMap.put(ArmySubType.PIKEMEN, 2);
		tempMap.put(ArmySubType.MUSKETEERS, 3);
		tempMap.put(ArmySubType.CANNON, 4);
		tempMap.put(ArmySubType.LIGHT_CAVALRY, 5);
		tempMap.put(ArmySubType.HEAVY_CAVALRY, 6);
		tempMap.put(ArmySubType.MORTAR, 7);
		ATTACKER_CHOICE_CRITERIA.put(3, tempMap);
	}
}
