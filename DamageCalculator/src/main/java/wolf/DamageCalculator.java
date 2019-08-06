package main.java.wolf;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.config.CalculationsHelper;
import main.java.config.ConfigManager;
import main.java.config.Configuration;
import main.java.web.dto.UserInputParameters;

public abstract class DamageCalculator {

    private static Logger logger = LoggerFactory.getLogger(DamageCalculator.class);

    private static final String NEW_LINE = "\n\r";

    private static final double SPELL_CAPACITY_BOOST = 0.1;
    private static final double MARCH_CAPACITY_BOOST = 0.25;
    private static final int STEP_UNITS = 1;

    private int calculatedMarchCapacity;

    protected double totalArmyDamage;
    protected List<Army> armyDistribution = new ArrayList<>();
    protected UserInputParameters inputParameters;
    protected CalculationsHelper helper;
    protected abstract void initializeDistribution();

    public DamageCalculator(UserInputParameters parameters) throws IOException {
        inputParameters = parameters;
        helper = new CalculationsHelper(inputParameters);
        calculatedMarchCapacity = calculateCapacity();
        initializeDistribution();
    }

    private int calculateCapacity() {
        double capacityModifier = 0;
        if (inputParameters.isUseMarchCapacityBoost()) {
            capacityModifier += MARCH_CAPACITY_BOOST;
        }
        if (inputParameters.isUseMarchCapacitySpell()) {
            capacityModifier += SPELL_CAPACITY_BOOST;
        }

        Configuration configuration = ConfigManager.getInstance().getConfiguration();
        int baseCapacity = configuration.CASTLE_BASE_MARCH_CAPACITY.get(inputParameters.getCastleLevel());
        return (int) (capacityModifier * baseCapacity + inputParameters.getTroopsAmount());
    }


    public void calculate() {
        calculateDistribution();
        calculateTotalDamage();
    }

    private DamageCalculator calculateDistribution() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < calculatedMarchCapacity; i += STEP_UNITS) {
            Army bestArmy = calculateBestArmy(armyDistribution);
            if (bestArmy != null) {
                bestArmy.addUnits(STEP_UNITS);
            } else {
                logger.error("Unable to calculate best WolfArmy. WolfArmy is null");
            }
        }
        long timeElapsed = System.currentTimeMillis() - startTime;
        logger.info("Time elapsed: " + timeElapsed + "ms.");
        validateResult();
        return this;
    }

    private void calculateTotalDamage() {
        double totalDamage = 0;
        for (Army army : armyDistribution) {
            totalDamage += army.getCalculatedDamage() * Math.sqrt(army.getTroopsNumber());
        }
        totalArmyDamage = totalDamage;
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

    public String printResults() {
        StringBuilder sb = new StringBuilder();
        sb.append("Initial capacity: " + inputParameters.getTroopsAmount() + NEW_LINE);
        sb.append("Calculated capacity: " + calculatedMarchCapacity + NEW_LINE);
        for (Army army : armyDistribution) {
            sb.append(army + " troops:\t" + army.getTroopsNumber() + NEW_LINE);
        }
        sb.append(totalDamageToString());
        logger.info(sb.toString());
        return sb.toString();
    }

    public String totalDamageToString() {
        return "Total damage:\t" + new DecimalFormat("#.0").format(totalArmyDamage) + "\n";
    }

    private Army calculateBestArmy(List<Army> armyDistribution) {
        Army bestArmy = null;
        double maxDelta = 0;
        for (Army army : armyDistribution) {
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

    public List<Army> getArmyDistribution() {
        return armyDistribution;
    }

    public UserInputParameters getInputParameters() {
        return inputParameters;
    }

    public int getCalculatedMarchCapacity() {
        return calculatedMarchCapacity;
    }
}
