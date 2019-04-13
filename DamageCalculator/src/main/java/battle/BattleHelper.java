package main.java.battle;

import java.util.HashMap;
import java.util.Map;

import main.java.config.ArmySubType;

public class BattleHelper {
	public static Map<ArmySubType, Integer> CHOICE_CRIT = new HashMap<>();
	static {
		CHOICE_CRIT.put(ArmySubType.MUSKETEERS, 0);
		CHOICE_CRIT.put(ArmySubType.PIKEMEN, 1);
		CHOICE_CRIT.put(ArmySubType.CANNON, 2);
		CHOICE_CRIT.put(ArmySubType.LIGHT_CAVALRY, 3);
		CHOICE_CRIT.put(ArmySubType.HEAVY_CAVALRY, 4);
		CHOICE_CRIT.put(ArmySubType.RIFLEMEN, 5);
		CHOICE_CRIT.put(ArmySubType.GRENADIERS, 6);
		CHOICE_CRIT.put(ArmySubType.MORTAR, 7);
	}
}
