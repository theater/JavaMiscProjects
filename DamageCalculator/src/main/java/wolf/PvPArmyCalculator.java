package main.java.wolf;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.config.ArmyStats;
import main.java.config.ArmyType;
import main.java.config.ConfigManager;
import main.java.config.Configuration;
import main.java.web.dto.UserInputParameters;

public class PvPArmyCalculator extends DamageCalculator {

    private static final double DEFENSE_MODIFICATION_FACTOR = 1.1;

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
        // final int maxEvenInfantryIndex = inputParameters.getMaxTier() % 2 == 0 ? inputParameters.getMaxTier() - 1
        // : inputParameters.getMaxTier() - 2;
        // logger.debug("Maximum even infantry index = {}", maxEvenInfantryIndex);

        ArmyType[] armyTypes = ArmyType.values();
        Map<ArmyType, Integer> maxTierPerArmy = inputParameters.getMaxTierPerArmy();

        int maxInfantryIndex = maxTierPerArmy.get(ArmyType.INFANTRY) - 1;
        final double modifiedDefense = DEFENSE_MODIFICATION_FACTOR * calculateModifiedDefense(maxInfantryIndex);
        logger.info("Modified defense against we will calculate = {}", modifiedDefense);
        for (ArmyType armyType : armyTypes) {
            int maxTier = maxTierPerArmy != null ? maxTierPerArmy.get(armyType) : inputParameters.getMaxTier();
            for (int i = 0; i < maxTier; i++) {
                PvPArmy currentArmy = new PvPArmy(armyType, i, helper, modifiedDefense);
                armyDistribution.add(currentArmy);
            }

            if (armyType == ArmyType.INFANTRY) {
                Army maxInfantry = armyDistribution.get(maxInfantryIndex);
                maxInfantry.setTroopsNumber((int) (calculatedMarchCapacity * infantryPercentage));
                calculatedMarchCapacity -= maxInfantry.getTroopsNumber();
            }
            Collections.sort(armyDistribution);
        }
    }

    // TODO maybe extract this in separate UTIL which can be used in battle and here
    // as well. Necessary parameters are Unit
    // type and unit index.
    private double calculateModifiedDefense(final int maxEvenInfantryIndex) {
        final Configuration configuration = ConfigManager.getInstance().getConfiguration();
        final ArmyStats baseArmyStats = configuration.BASE_UNIT_STATS_PER_ARMYTYPE.get(ArmyType.INFANTRY)
            .get(maxEvenInfantryIndex);
        final double baseDefense = baseArmyStats.getDefense();
        logger.debug("Base defense = {}", baseDefense);
        Double defenseModifiers = helper.DEFENSE_MODIFIERS.get(ArmyType.INFANTRY);
        logger.debug("Base modifiers = {}", defenseModifiers);
        final double modifiedDefense = baseDefense * (1 + defenseModifiers / 100);
        return modifiedDefense;
    }

    @Override
    protected void validateResult() {
        int troopsCount = 0;
        for (Army army : armyDistribution) {
            troopsCount += army.getTroopsNumber();
        }
        logger.info("TODO: improve this validation from root class. Current total troops count:" + troopsCount);
    }

}
