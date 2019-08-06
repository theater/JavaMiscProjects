package main.java.wolf;

import main.java.config.ArmyType;
import main.java.config.CalculationsHelper;

public class PvPArmy extends WolfArmy {

	public PvPArmy(ArmyType type, int tier, CalculationsHelper helper) {
		super(type, tier, helper);
		defense = 25000;
	}

}
