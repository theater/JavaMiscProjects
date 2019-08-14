package main.java.wolf;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.config.ArmyType;
import main.java.web.dto.UserInputParameters;


public class WolfDamageCalculator extends DamageCalculator {

    Logger logger = LoggerFactory.getLogger(WolfDamageCalculator.class);

    public WolfDamageCalculator(UserInputParameters parameters) throws IOException {
        super(parameters);
    }

    @Override
    public String totalDamageToString() {
        return "Total damage:\t" + new DecimalFormat("#").format((totalArmyDamage / 1000) * 30) + "\n";
    }

    protected void initializeDistribution() {
        ArmyType[] armyTypes = ArmyType.values();
		Map<ArmyType, Integer> maxTierPerArmy = inputParameters.getMaxTierPerArmy();
        for (ArmyType armyType : armyTypes) {
			int maxTier = maxTierPerArmy != null ? maxTierPerArmy.get(armyType) : inputParameters.getMaxTier();
			for (int i = 0; i < maxTier; i++) {
                armyDistribution.add(new WolfArmy(armyType, i, helper));
            }
        }
        Collections.sort(armyDistribution);
    }
}
