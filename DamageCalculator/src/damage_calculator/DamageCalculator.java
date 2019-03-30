package damage_calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class DamageCalculator {

    private static Logger logger = Logger.getLogger(DamageCalculator.class);

    private static final double SPELL_CAPACITY_BOOST = 0.1;
    private static final double MARCH_CAPACITY_BOOST = 0.25;
    private static final int STEP_UNITS = 1;

    private int calculatedMarchCapacity;

    private Map<Army, Integer> distribution = new HashMap<>();
    private InputParameters inputParameters;

    public DamageCalculator(InputParameters inputParams) {
        this.inputParameters = inputParams;
        calculatedMarchCapacity = calculateCapacity();
        initializeDistribution();
    }

    private int calculateCapacity() {
        double capacityModifier = 0;
        if (inputParameters.useMarchCapacityBoost) {
            capacityModifier += MARCH_CAPACITY_BOOST;
        }
        if (inputParameters.useMarchCapacitySpell) {
            capacityModifier += SPELL_CAPACITY_BOOST;
        }
        int castleCapacity = StaticData.CASTLE_BASE_MARCH_CAPACITY.get(inputParameters.castleLevel);
        return (int) (capacityModifier * castleCapacity + inputParameters.troopsAmount);
    }

    private void initializeDistribution() {
        ArmyType[] armyTypes = ArmyType.values();
        for (ArmyType armyType : armyTypes) {
            for (int tier = 0; tier < inputParameters.maxTier; tier++) {
                Army army = new Army(armyType, tier);
                distribution.put(army, 0);
            }
        }
    }

    public DamageCalculator calculate() {
        long startTime = System.currentTimeMillis();
        Set<Army> allArmies = distribution.keySet();
        for (int counter = 1; counter <= calculatedMarchCapacity; counter += STEP_UNITS) {
            Army bestArmy = retrieveBestArmy(allArmies);
            Integer currentAmount = distribution.get(bestArmy);
            distribution.put(bestArmy, currentAmount + STEP_UNITS);
        }
        long timeElapsed = System.currentTimeMillis() - startTime;
        logger.info("Time elapsed: " + timeElapsed + "ms.");
        return this;
    }

    public DamageCalculator printResults() {
        logger.info("Initial capacity: " + inputParameters.troopsAmount);
        logger.info("Calculated capacity: " + calculatedMarchCapacity);

        Set<Army> armies = distribution.keySet();
        List<Army> armiesToSort = new ArrayList<>(armies);
        Collections.sort(armiesToSort);
        for (Army army : armiesToSort) {
            int troopsAmount = distribution.get(army);
            logger.info(army + ":\t" + troopsAmount);
        }
        return this;
    }

    private Army retrieveBestArmy(Set<Army> allArmies) {
        Army bestArmy = null;
        double maxDelta = 0;
        for (Army army : allArmies) {
            double calculatedDelta = calculateDamageDelta(army);
            if (maxDelta < calculatedDelta) {
                maxDelta = calculatedDelta;
                bestArmy = army;
            }
        }
        return bestArmy;
    }

    private double calculateDamageDelta(Army army) {
        double damage = army.getCalculatedFinalDamage();
        return damage * Math.sqrt(distribution.get(army) + STEP_UNITS) - damage * Math.sqrt(distribution.get(army));
    }
}
