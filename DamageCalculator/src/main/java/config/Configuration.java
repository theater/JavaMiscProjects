package main.java.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.calculator.ArmySubType;
import main.java.calculator.ArmyType;

public class Configuration {
	
	private static Logger logger = LoggerFactory.getLogger(Configuration.class);

    private int ABSOLUTE_MAX_TIER = 12;
    public Map<Integer, Integer> CASTLE_BASE_MARCH_CAPACITY;
    public Map<ArmyType, ArmySubType[]> TYPE_TO_SUBTYPE_MAP;
    public ArmySubType[] distanceSubtypes;
    public ArmySubType[] cavalrySubtypes;
    public Map<ArmyType, int[]> BASE_ATTACK_FACTORS;
    public Map<ArmyType, ArrayList<UnitStats>> BASE_UNIT_STATS_PER_ARMYTYPE = new HashMap<ArmyType, ArrayList<UnitStats>>();
    
    @Override
    public String toString() {
        return "Configuration [ABSOLUTE_MAX_TIER=" + ABSOLUTE_MAX_TIER + ", CASTLE_BASE_MARCH_CAPACITY=" + CASTLE_BASE_MARCH_CAPACITY + ", TYPE_TO_SUBTYPE_MAP=" + TYPE_TO_SUBTYPE_MAP +
                ", distanceSubtypes=" + Arrays.toString(distanceSubtypes) + ", cavalrySubtypes=" + Arrays.toString(cavalrySubtypes) + ", BASE_ATTACK_FACTORS=" + BASE_ATTACK_FACTORS + "]";
    }
    
    public void pritnBaseArmyFactors() {
    	Set<Entry<ArmyType,ArrayList<UnitStats>>> entrySet = BASE_UNIT_STATS_PER_ARMYTYPE.entrySet();
    	for (Entry<ArmyType, ArrayList<UnitStats>> entry : entrySet) {
			List<UnitStats> tiersStats = entry.getValue();
			for (UnitStats tierStats : tiersStats) {
				logger.debug("UnitType: " + entry.getKey() + ": " + tierStats);
			}
		}
    }
}
