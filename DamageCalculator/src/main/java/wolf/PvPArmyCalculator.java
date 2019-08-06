package main.java.wolf;

import java.io.IOException;
import java.util.Collections;

import main.java.config.ArmyType;
import main.java.web.dto.UserInputParameters;

public class PvPArmyCalculator extends DamageCalculator {

	public PvPArmyCalculator(UserInputParameters parameters) throws IOException {
		super(parameters);
	}

	@Override
	protected void initializeDistribution() {
		ArmyType[] armyTypes = ArmyType.values();
		for (ArmyType armyType : armyTypes) {
			for (int i = 0; i < inputParameters.getMaxTier(); i++) {
				armyDistribution.add(new PvPArmy(armyType, i, helper));
			}
		}
		Collections.sort(armyDistribution);
	}

}
