package damage_calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

public class DamageCalculator {

    private static Logger logger = Logger.getLogger(DamageCalculator.class);

    private static final double SPELL_CAPACITY_BOOST = 0.1;
    private static final double MARCH_CAPACITY_BOOST = 0.25;
    private static final int STEP_UNITS = 1;

    protected int calculatedMarchCapacity;
    protected List<Army> armyDistribution = new ArrayList<>();
    protected double totalArmyDamage;
    protected InputParameters inputParameters;

    public DamageCalculator(InputParameters inputParameters) {
        this.inputParameters = inputParameters;
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
            for (int i = 0; i < inputParameters.maxTier; i++) {
                armyDistribution.add(new Army(armyType, i, inputParameters));
            }
        }
        Collections.sort(armyDistribution);
    }

    public DamageCalculator calculate() {
        calculateDistribution();
        calculateTotalDamage();
        return this;
    }

    public DamageCalculator calculateDistribution() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < calculatedMarchCapacity; i += STEP_UNITS) {
            Army bestArmy = calculateBestArmy(armyDistribution);
            if (bestArmy != null) {
                bestArmy.addUnits(STEP_UNITS);
            } else {
                logger.error("Unable to calculate best Army. Army is null");
            }
        }
        long timeElapsed = System.currentTimeMillis() - startTime;
        logger.info("Time elapsed: " + timeElapsed + "ms.");
        validateResult();
        return this;
    }

    private void validateResult() {
        int troopsCount = 0;
        for (Army army : armyDistribution) {
            troopsCount += army.getTroopsNumber();
        }
        if (troopsCount != calculatedMarchCapacity) {
            logger.warn("Calculated troops count differs from calculated capacity. Troops count:" + troopsCount);
        } else {
            logger.info("Calculation finished successfully.");
        }
    }

    public void calculateTotalDamage() {
        double totalDamage = 0;
        for (Army army : armyDistribution) {
            totalDamage += army.getCalculatedDamage() * Math.sqrt(army.getTroopsNumber());
        }
        totalArmyDamage = totalDamage;
    }

    public DamageCalculator printResults() {
        logger.info("Initial capacity: " + inputParameters.troopsAmount);
        logger.info("Calculated capacity: " + calculatedMarchCapacity);
        for (Army army : armyDistribution) {
            logger.info(army + " troops:\t" + army.getTroopsNumber());
        }
        printTotalDamage();
        return this;
    }

    protected void printTotalDamage() {
        logger.info("Total damage:\t" + new DecimalFormat("#.0").format(totalArmyDamage));
    }

    private Army calculateBestArmy(List<Army> distribution) {
        Army bestArmy = null;
        double maxDelta = 0;
        for (Army army : distribution) {
            double calculatedDelta = calculateDamageDelta(army);
            if (maxDelta < calculatedDelta) {
                maxDelta = calculatedDelta;
                bestArmy = army;
            }
        }
        return bestArmy;
    }

    private double calculateDamageDelta(Army army) {
        double damage = army.getCalculatedDamage();
        return damage * Math.sqrt(army.getTroopsNumber() + STEP_UNITS) - damage * Math.sqrt(army.getTroopsNumber());
    }
}
