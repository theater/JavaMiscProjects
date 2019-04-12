package main.java.calculator;

import java.util.Collections;
import java.util.List;

public class InitializationUtil {

	private static final int MAX_TIER = 12;
	public static void initializeAttacker(List<Army> army) {
		army.add(makeDistanceArmy(0, 100));
		army.add(makeDistanceArmy(1, 100));
		army.add(makeDistanceArmy(2, 100));
		army.add(makeDistanceArmy(3, 100));
		army.add(makeDistanceArmy(4, 100));
		army.add(makeDistanceArmy(5, 100));
		army.add(makeDistanceArmy(6, 100));
		army.add(makeDistanceArmy(7, 100));
		army.add(makeDistanceArmy(8, 100));
		army.add(makeDistanceArmy(9, 100));
		army.add(makeDistanceArmy(10, 100));
		army.add(makeDistanceArmy(11, 100));

		army.add(makeInfantryArmy(0, 100));
		army.add(makeInfantryArmy(1, 100));
		army.add(makeInfantryArmy(2, 100));
		army.add(makeInfantryArmy(3, 100));
		army.add(makeInfantryArmy(4, 100));
		army.add(makeInfantryArmy(5, 100));
		army.add(makeInfantryArmy(6, 100));
		army.add(makeInfantryArmy(7, 100));
		army.add(makeInfantryArmy(8, 100));
		army.add(makeInfantryArmy(9, 100));
		army.add(makeInfantryArmy(10, 100));
		army.add(makeInfantryArmy(11, 100));

		army.add(makeCavalryArmy(0, 100));
		army.add(makeCavalryArmy(1, 100));
		army.add(makeCavalryArmy(2, 100));
		army.add(makeCavalryArmy(3, 100));
		army.add(makeCavalryArmy(4, 100));
		army.add(makeCavalryArmy(5, 100));
		army.add(makeCavalryArmy(6, 100));
		army.add(makeCavalryArmy(7, 100));
		army.add(makeCavalryArmy(8, 100));
		army.add(makeCavalryArmy(9, 100));
		army.add(makeCavalryArmy(10, 100));
		army.add(makeCavalryArmy(11, 100));

		army.add(makeArtilleryArmy(0, 100));
		army.add(makeArtilleryArmy(1, 100));
		army.add(makeArtilleryArmy(2, 100));
		army.add(makeArtilleryArmy(3, 100));
		army.add(makeArtilleryArmy(4, 100));
		army.add(makeArtilleryArmy(5, 100));
		army.add(makeArtilleryArmy(6, 100));
		army.add(makeArtilleryArmy(7, 100));
		army.add(makeArtilleryArmy(8, 100));
		army.add(makeArtilleryArmy(9, 100));
		army.add(makeArtilleryArmy(10, 100));
		army.add(makeArtilleryArmy(11, 100));

		Collections.sort(army);
	}

	public static void initializeLosses(List<Army> list) {
		ArmyType[] armyTypes = ArmyType.values();
		for (ArmyType armyType : armyTypes) {
			for (int i = 0; i < MAX_TIER; i++) {
				list.add(new Army(armyType, i, 0));
			}
		}
		Collections.sort(list);
	}

	private static Army makeDistanceArmy(int tier, int number) {
		return new Army(ArmyType.DISTANCE, tier, number);
	}
	private static Army makeInfantryArmy(int tier, int number) {
		return new Army(ArmyType.INFANTRY, tier, number);
	}
	private static Army makeArtilleryArmy(int tier, int number) {
		return new Army(ArmyType.ARTILLERY, tier, number);
	}
	private static Army makeCavalryArmy(int tier, int number) {
		return new Army(ArmyType.CAVALRY, tier, number);
	}
}
