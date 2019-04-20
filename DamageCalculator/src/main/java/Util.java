package main.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import main.java.battle.Army;
import main.java.config.ArmyType;
import main.java.config.CalculationsHelper;
import main.java.config.ConfigManager;
import main.java.config.UserInputParameters;

public class Util {

    public static int calculateAverage(int currentNumber, int prevAvg, int iteration) {
        if (iteration <= 0) {
            throw new ArithmeticException("Iteration should start from 1!");
        }

        return ((iteration - 1) * prevAvg + currentNumber) / iteration;
    }

	public static void initializeArmyCollection(List<Army> armyCollection, UserInputParameters input) {
		final int MAX_TIER = ConfigManager.getInstance().getConfiguration().ABSOLUTE_MAX_TIER;

		if(armyCollection == null) {
			armyCollection = new ArrayList<Army>();
		}

		ArmyType[] armyTypes = ArmyType.values();
		Map<ArmyType, List<Integer>> army = input.getArmy();
		CalculationsHelper calculationsHelper = new CalculationsHelper(input);
		for (ArmyType armyType : armyTypes) {
			List<Integer> armyByType = army.get(armyType);
			for (int i = 0; i < MAX_TIER; i++) {
				int unitsAmount = 0;
				if (armyByType != null && armyByType.size() == MAX_TIER) {
					unitsAmount = armyByType.get(i);
				}
				Army newArmy = new Army(armyType, i, unitsAmount);
				armyCollection.add(newArmy);
				newArmy.addModifiedArmyStats(calculationsHelper);
			}
		}
		Collections.sort(armyCollection);
	}
}
