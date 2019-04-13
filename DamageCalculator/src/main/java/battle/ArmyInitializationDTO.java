package main.java.battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.config.ArmyType;

public class ArmyInitializationDTO {
	private Map<ArmyType, List<Integer>> army = new HashMap<>();

	public ArmyInitializationDTO() {
		ArmyType[] values = ArmyType.values();
		for (ArmyType armyType : values) {
			List<Integer> armies = new ArrayList<>();
			for (int i = 0; i<12; i++) {
				armies.add(100);
			}
			army.put(armyType, armies);
		}
	}

	public Map<ArmyType, List<Integer>> getArmy() {
		return army;
	}

	public void setArmy(Map<ArmyType, List<Integer>> army) {
		this.army = army;
	}

}
