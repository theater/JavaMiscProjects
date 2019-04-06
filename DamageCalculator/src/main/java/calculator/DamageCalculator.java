package main.java.calculator;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.config.Configuration;
import main.java.config.UserInputParameters;
import main.java.parser.JSONParser;

public class DamageCalculator {

    private static Logger logger = LoggerFactory.getLogger(DamageCalculator.class);

    private static final double SPELL_CAPACITY_BOOST = 0.1;
    private static final double MARCH_CAPACITY_BOOST = 0.25;
    private static final int STEP_UNITS = 1;

    protected static Configuration configuration;
    protected double totalArmyDamage;

    private int calculatedMarchCapacity;
    private List<Army> armyDistribution = new ArrayList<>();
    private UserInputParameters inputParameters;
    private CalculationsHelper helper;

    public DamageCalculator(String userInputFilePath, String configurationFilePath) throws IOException {
        importExternalData(userInputFilePath, configurationFilePath);
        helper = new CalculationsHelper(inputParameters, configuration);
        calculatedMarchCapacity = calculateCapacity();
        initializeDistribution();
    }

    private void importExternalData(String userDataFileLocation, String configFileLocation) throws IOException {
        JSONParser jsonParser = new JSONParser();
        inputParameters = jsonParser.parseInputParameters(userDataFileLocation);
        String parsedUserInputAsString = jsonParser.getMapper().writeValueAsString(inputParameters);
        logger.debug(parsedUserInputAsString);

        configuration = jsonParser.parseConfiguration(configFileLocation);
        String parsedConfigurationAsString = jsonParser.getMapper().writeValueAsString(configuration);
        logger.debug(parsedConfigurationAsString);
    }

    private int calculateCapacity() {
        double capacityModifier = 0;
        if (inputParameters.useMarchCapacityBoost) {
            capacityModifier += MARCH_CAPACITY_BOOST;
        }
        if (inputParameters.useMarchCapacitySpell) {
            capacityModifier += SPELL_CAPACITY_BOOST;
        }
        int baseCapacity = configuration.CASTLE_BASE_MARCH_CAPACITY.get(inputParameters.castleLevel);
        return (int) (capacityModifier * baseCapacity + inputParameters.troopsAmount);
    }

    private void initializeDistribution() {
        ArmyType[] armyTypes = ArmyType.values();
        for (ArmyType armyType : armyTypes) {
            for (int i = 0; i < inputParameters.maxTier; i++) {
                armyDistribution.add(new Army(armyType, i, helper));
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

    public String printResults() {
        StringBuilder sb = new StringBuilder();
        sb.append("Initial capacity: " + inputParameters.troopsAmount + "\n\r");
        sb.append("Calculated capacity: " + calculatedMarchCapacity + "\n\r");
        for (Army army : armyDistribution) {
            sb.append(army + " troops:\t" + army.getTroopsNumber() + "\n\r");
        }
        sb.append(totalDamageToString());
        logger.info(sb.toString());
        return sb.toString();
    }

    public String totalDamageToString() {
        return "Total damage:\t" + new DecimalFormat("#.0").format(totalArmyDamage) + "\n";
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
