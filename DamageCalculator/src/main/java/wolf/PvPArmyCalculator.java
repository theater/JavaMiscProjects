package main.java.wolf;

import java.io.IOException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.config.ArmyType;
import main.java.web.dto.UserInputParameters;

public class PvPArmyCalculator extends DamageCalculator {

	private static Logger logger = LoggerFactory.getLogger(PvPArmyCalculator.class);

	private double infantryPercentage;

	public PvPArmyCalculator(UserInputParameters parameters) throws IOException {
		super(parameters);
		int infantryPercentage = parameters.getInfantryPercentage();
		this.infantryPercentage = ((double) infantryPercentage) / 100;
		logger.info("Infantry percentage = {}", this.infantryPercentage);

	}

	@Override
	protected void initializeDistribution() {
		ArmyType[] armyTypes = ArmyType.values();
		for (ArmyType armyType : armyTypes) {
			int maxEvenInafantryIndex = 0;
			for (int i = 0; i < inputParameters.getMaxTier(); i++) {
				PvPArmy currentArmy = new PvPArmy(armyType, i, helper);
				armyDistribution.add(currentArmy);

				// This is a bit fishy. We're indexing from 0, therefore to have latest even infantry tier we need to have latest odd index :)
				if (armyType == ArmyType.INFANTRY && i % 2 != 0) {
					maxEvenInafantryIndex = i;
				}
			}

			if (maxEvenInafantryIndex > 0) {
				Army maxEvenInfantry = armyDistribution.get(maxEvenInafantryIndex);
				maxEvenInfantry.setTroopsNumber((int) (calculatedMarchCapacity * infantryPercentage));
				calculatedMarchCapacity -= maxEvenInfantry.getTroopsNumber();
			}
		}
		Collections.sort(armyDistribution);
	}

	protected void validateResult() {
        int troopsCount = 0;
        for (Army army : armyDistribution) {
            troopsCount += army.getTroopsNumber();
        }
		logger.info(
				"TODO: improve this validation from root class. Current total troops count:"
						+ troopsCount);
	}

}
