package main.java.wolf;

import main.java.config.ArmyType;
import main.java.config.CalculationsHelper;

public class PvPArmy extends Army {

	public PvPArmy(ArmyType type, int tier, CalculationsHelper helper) {
		super(type, tier, helper);
		defense = 25000;
	}

	@Override
	protected double calculateDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected double calculateAttackEfficiency() {
		// TODO Auto-generated method stub
		return 0;
	}

}
