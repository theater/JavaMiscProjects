package main.java.wolf;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.config.ArmyType;
import main.java.config.CalculationsHelper;
import main.java.config.ConfigManager;
import main.java.config.Configuration;
import main.java.config.UserInputParameters;

public class DamageCalculator {

    private static Logger logger = LoggerFactory.getLogger(DamageCalculator.class);

    private static final String NEW_LINE = "\n\r";

    private static final double SPELL_CAPACITY_BOOST = 0.1;
    private static final double MARCH_CAPACITY_BOOST = 0.25;
    private static final int STEP_UNITS = 1;

    protected double totalArmyDamage;
    private int calculatedMarchCapacity;
    private List<WolfArmy> armyDistribution = new ArrayList<>();
    private UserInputParameters inputParameters;
    private CalculationsHelper helper;

    public DamageCalculator(UserInputParameters parameters) throws IOException {
        this.inputParameters = parameters;
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

    private void initializeDistribution() {
        ArmyType[] armyTypes = ArmyType.values();
        for (ArmyType armyType : armyTypes) {
            for (int i = 0; i < inputParameters.getMaxTier(); i++) {
                armyDistribution.add(new WolfArmy(armyType, i, helper));
            }
        }
        Collections.sort(armyDistribution);
    }

    public DamageCalculator calculate() {
        calculateDistribution();
        calculateTotalDamage();
        return this;
    }

    private DamageCalculator calculateDistribution() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < calculatedMarchCapacity; i += STEP_UNITS) {
            WolfArmy bestArmy = calculateBestArmy(armyDistribution);
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
        for (WolfArmy army : armyDistribution) {
            totalDamage += army.getCalculatedDamage() * Math.sqrt(army.getTroopsNumber());
        }
        totalArmyDamage = totalDamage;
    }

    private void validateResult() {
        int troopsCount = 0;
        for (WolfArmy army : armyDistribution) {
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
        for (WolfArmy army : armyDistribution) {
            sb.append(army + " troops:\t" + army.getTroopsNumber() + NEW_LINE);
        }
        sb.append(totalDamageToString());
        logger.info(sb.toString());
        return sb.toString();
    }

    public String printToHTMLTable() {
        final String LINE_SEPARATOR = "<BR>";
        StringBuilder sb = new StringBuilder();

        sb.append("<h3>Calculations</h3>");
        sb.append("Initial capacity: " + getInputParameters().getTroopsAmount() + LINE_SEPARATOR);
        sb.append("Calculated capacity: " + getCalculatedMarchCapacity() + LINE_SEPARATOR);
        sb.append(totalDamageToString());

        sb.append("<h3>WolfArmy Distribution</h3>");
        sb.append("<FORM>");
        sb.append("<TABLE border=1>");
        sb.append("<TR>");
        sb.append("<TH>WolfArmy type</TH>");
        sb.append("<TH>Tier</TH>");
        sb.append("<TH>Troops to send</TH>");
        sb.append("</TR>");
        for (WolfArmy army : getArmyDistribution()) {
            sb.append("<TR>");
            sb.append("<TD>").append(army.getType()).append("</TD>");
            sb.append("<TD>").append(army.getTier() + 1).append("</TD>");
            sb.append("<TD>").append(army.getTroopsNumber()).append("</TD>");
            sb.append("</TR>");
        }
        sb.append("</TABLE>");
        sb.append("</FORM>");
        return sb.toString();
    }

    public String totalDamageToString() {
        return "Total damage:\t" + new DecimalFormat("#.0").format(totalArmyDamage) + "\n";
    }

    private WolfArmy calculateBestArmy(List<WolfArmy> distribution) {
        WolfArmy bestArmy = null;
        double maxDelta = 0;
        for (WolfArmy army : distribution) {
            double calculatedDelta = calculateDamageDelta(army);
            if (maxDelta < calculatedDelta) {
                maxDelta = calculatedDelta;
                bestArmy = army;
            }
        }
        return bestArmy;
    }

    private double calculateDamageDelta(WolfArmy army) {
        double damage = army.getCalculatedDamage();
        return damage * Math.sqrt(army.getTroopsNumber() + STEP_UNITS) - damage * Math.sqrt(army.getTroopsNumber());
    }

    public List<WolfArmy> getArmyDistribution() {
        return armyDistribution;
    }

    public UserInputParameters getInputParameters() {
        return inputParameters;
    }

    public int getCalculatedMarchCapacity() {
        return calculatedMarchCapacity;
    }
}
