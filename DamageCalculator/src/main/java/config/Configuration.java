package main.java.config;

import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {

	private static Logger logger = LoggerFactory.getLogger(Configuration.class);

	public int ABSOLUTE_MAX_TIER;
	public Map<Integer, Integer> CASTLE_BASE_MARCH_CAPACITY;
	public Map<ArmyType, ArmySubType[]> TYPE_TO_SUBTYPE_MAP;
	public Map<ArmyType, int[]> BASE_ATTACK_FACTORS;
	public Map<ArmyType, ArrayList<ArmyStats>> BASE_UNIT_STATS_PER_ARMYTYPE;
	public Map<ArmySubType, Map<ArmySubType, Double>> STATIC_EFFICIENCY;

	@Override
	public String toString() {
		return "Configuration [ABSOLUTE_MAX_TIER=" + ABSOLUTE_MAX_TIER + ", CASTLE_BASE_MARCH_CAPACITY="
				+ CASTLE_BASE_MARCH_CAPACITY + ", TYPE_TO_SUBTYPE_MAP=" + TYPE_TO_SUBTYPE_MAP + ", BASE_ATTACK_FACTORS="
				+ BASE_ATTACK_FACTORS + ", BASE_UNIT_STATS_PER_ARMYTYPE=" + BASE_UNIT_STATS_PER_ARMYTYPE
				+ ", STATIC_EFFICIENCY=" + STATIC_EFFICIENCY + "]";
	}

}
