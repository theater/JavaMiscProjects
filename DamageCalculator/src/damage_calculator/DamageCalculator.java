package damage_calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

public class DamageCalculator {

    private static Logger logger = Logger.getLogger(DamageCalculator.class);

    private static final double SPELL_CAPACITY_BOOST = 0.1;
    private static final double MARCH_CAPACITY_BOOST = 0.25;
    private static final int STEP_UNITS = 1;

    private int calculatedMarchCapacity;

    private Map<ArmyType, Army[]> distribution = new HashMap<>();
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
        int baseCapacity = StaticData.CASTLE_BASE_MARCH_CAPACITY.get(inputParameters.castleLevel);
        return (int) (capacityModifier * baseCapacity + inputParameters.troopsAmount);
    }

    private void initializeDistribution() {
        ArmyType[] armyTypes = ArmyType.values();
        for (ArmyType armyType : armyTypes) {
            Army[] tiers = new Army[inputParameters.maxTier];
            distribution.put(armyType, tiers);
            for (int tier = 0; tier < inputParameters.maxTier; tier++) {
                Army army = new Army(armyType, tier);
                tiers[tier] = army;
            }
        }
    }

    public DamageCalculator calculate() {
        long startTime = System.currentTimeMillis();
        Set<Entry<ArmyType, Army[]>> allArmies = distribution.entrySet();
        for (int counter = 1; counter <= calculatedMarchCapacity; counter += STEP_UNITS) {
            calculateBestArmy(allArmies);
        }
        long timeElapsed = System.currentTimeMillis() - startTime;
        logger.info("Time elapsed: " + timeElapsed + "ms.");
        return this;
    }

    public DamageCalculator printResults() {
        logger.info("Initial capacity: " + inputParameters.troopsAmount);
        logger.info("Calculated capacity: " + calculatedMarchCapacity);

        List<Army> armiesToSort = new ArrayList<>();
        Set<Entry<ArmyType, Army[]>> armies = distribution.entrySet();
        for (Entry<ArmyType, Army[]> entry : armies) {
            Army[] value = entry.getValue();
            for (Army army : value) {
                armiesToSort.add(army);
            }
        }
        Collections.sort(armiesToSort);
        for (Army army : armiesToSort) {
            logger.info(army);
        }
        return this;
    }

    private void calculateBestArmy(Set<Entry<ArmyType, Army[]>> allArmies) {
        Army bestArmy = null;
        double maxDelta = 0;
        for (Entry<ArmyType, Army[]> entry : allArmies) {
            Army[] armiesPerType = entry.getValue();
            for (int i = 0; i < armiesPerType.length; i++) {
                Army army = armiesPerType[i];
                double calculatedDelta = calculateDamageDelta(army);
                if (maxDelta < calculatedDelta) {
                    maxDelta = calculatedDelta;
                    bestArmy = army;
                }
            }
        }
        if (bestArmy != null) {
            bestArmy.addUnit(STEP_UNITS);
        } else {
            logger.error("Unable to calculate best Army. Army is null");
        }
    }

    private double calculateDamageDelta(Army army) {
        double damage = army.getCalculatedFinalDamage();
        return damage * Math.sqrt(army.getTroopsNumber() + STEP_UNITS) - damage * Math.sqrt(army.getTroopsNumber());
    }
}
