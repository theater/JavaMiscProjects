package main.java.battle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.config.ArmyStats;
import main.java.config.ArmySubType;
import main.java.config.ArmyType;
import main.java.config.CalculationsHelper;
import main.java.config.ConfigManager;
import main.java.config.Configuration;
import main.java.config.UserInputParameters;
import main.java.parser.JSONParser;

public class Battle {

    private static final double RANDOM_FACTOR = 0.85;
    private static long timer = System.currentTimeMillis();
    private static Logger logger = LoggerFactory.getLogger(Battle.class);

    private static final Configuration CONFIGURATION = ConfigManager.getInstance().getConfiguration();
    private static final int MAX_TIER = CONFIGURATION.ABSOLUTE_MAX_TIER;

    private final static int ROUNDS_COUNTER = 30;
    private static final String attackerFile = "attacker.json";
    private static final String defenderFile = "defender.json";

    private List<Army> attacker = new ArrayList<>();
    private List<Army> defender = new ArrayList<>();

    private List<Army> attackerLosses = new ArrayList<>();
    private List<Army> defenderLosses = new ArrayList<>();

    public Battle() throws IOException {
        JSONParser parser = new JSONParser();
        UserInputParameters attackerInput = parser.parseUserInput(attackerFile);
        initializeArmyCollection(attacker, attackerInput);

        UserInputParameters defenderInput = parser.parseUserInput(defenderFile);
        initializeArmyCollection(defender, defenderInput);

        initializeArmyCollection(attackerLosses);
        initializeArmyCollection(defenderLosses);
    }

    private void initializeArmyCollection(List<Army> armyCollection) {
        ArmyType[] armyTypes = ArmyType.values();
        for (ArmyType armyType : armyTypes) {
            for (int i = 0; i < MAX_TIER; i++) {
                armyCollection.add(new Army(armyType, i, 0));
            }
        }
        Collections.sort(armyCollection);
    }

    private void initializeArmyCollection(List<Army> armyCollection, UserInputParameters input) {
        ArmyType[] armyTypes = ArmyType.values();
        Map<ArmyType, List<Integer>> army = input.getArmy();
        CalculationsHelper calculationsHelper = new CalculationsHelper(input);
        for (ArmyType armyType : armyTypes) {
            List<Integer> armyByType = army.get(armyType);
            for (int i = 0; i < MAX_TIER; i++) {
                int unitsAmount = 0;
                if (armyByType != null && armyByType.size() == MAX_TIER) {
                    unitsAmount = armyByType.get(i);
                }
                Army newArmy = new Army(armyType, i, unitsAmount);
                armyCollection.add(newArmy);
                newArmy.addModifiedArmyStats(calculationsHelper);
            }
        }
        Collections.sort(armyCollection);
    }

    public void fight() {
        // TODO this will be calculated. Currently is constant
        int counter = ROUNDS_COUNTER;
        for (int i = 0; i < counter; i++) {
            doRound();
        }
        printResults();
    }

    private void doRound() {
        for (int i = 0; i < attacker.size(); i++) {
            Army attackingArmyOfAttacker = attacker.get(i);
            Army defenderDefendingArmy = getOpponentArmy(attackingArmyOfAttacker, defender);
            int defenderLosses = 0;
            if (attackingArmyOfAttacker.getNumber() > 0 && defenderDefendingArmy.getNumber() > 0) {
                defenderLosses = calculateDefenderLosses(attackingArmyOfAttacker, defenderDefendingArmy);
            }

            Army attackingArmyOfDefender = defender.get(i);
            Army attackerDefendingArmy = getOpponentArmy(attackingArmyOfDefender, attacker);
            int attackerLosses = 0;
            if (attackingArmyOfDefender.getNumber() > 0 && attackerDefendingArmy.getNumber() > 0) {
                attackerLosses = calculateDefenderLosses(attackingArmyOfDefender, attackerDefendingArmy);
            }

            updateLosses(defenderLosses, defenderDefendingArmy, true);
            updateLosses(attackerLosses, attackerDefendingArmy, false);
        }
    }

    private Army getOpponentArmy(Army attackingArmyOfAttacker, List<Army> defender) {
        int randomChance = calculateRandomChance(attackingArmyOfAttacker.getSubType());

        Army result = defender.get(defender.size() - 1);
        for (int i = defender.size() - 1; i >= 0; i--) {
            Army iteratedArmy = defender.get(i);
            if (iteratedArmy.getNumber() <= 0) {
                continue;
            }
            if (result.getNumber() <= 0) {
                result = iteratedArmy;
                continue;
            }

            Integer currentCriteria = BattleHelper.ATTACKER_CHOICE_CRITERIA.get(randomChance).get(result.getSubType());
            Integer iteratedCriteria = BattleHelper.ATTACKER_CHOICE_CRITERIA.get(randomChance).get(iteratedArmy.getSubType());
            if (currentCriteria > iteratedCriteria) {
                result = iteratedArmy;
            } else if (currentCriteria == iteratedCriteria && (iteratedArmy.getTier() < result.getTier())) {
                result = iteratedArmy;
            }
        }
        return result;
    }

    // TODO Improve this. Currently it's not generic at all...
    private int calculateRandomChance(ArmySubType armySubType) {
        int result = 1;
        if (ArmySubType.LIGHT_CAVALRY == armySubType) {
            if (Math.random() > RANDOM_FACTOR) {
                result = 3;
            }
        } else if (ArmySubType.RIFLEMEN == armySubType) {
            if (Math.random() > RANDOM_FACTOR) {
                result = 2;
            }
        }

        return result;
    }

    private int calculateDefenderLosses(Army attackingArmy, Army defendingArmy) {
        logger.info("Attacking army: " + attackingArmy + "\tDefendingArmy: " + defendingArmy);
        ArmyStats attackerStats = attackingArmy.getArmyStats();
        ArmyStats defenderStats = defendingArmy.getArmyStats();

        double defense = defenderStats.getDefense();
        double modifiedAttack = attackerStats.getAttack();
        double baseDamage = calculateBaseDamage(defense, modifiedAttack);

        // Must be >= 0
        double damageModifiers = Math.max(0,
            Math.min(1 + ((attackerStats.getDamage() - defenderStats.getDamageReduction()) / 100), 3));
        logger.info("Damage modifier = " + damageModifiers);

        // Must be >= 0
        double efficiencyFactor = Math.max(0, calculateEfficiencyFactor(attackingArmy, defendingArmy));
        logger.info("Calculated efficiency = " + efficiencyFactor);

        double calculatedDamage = baseDamage * damageModifiers * efficiencyFactor;
        logger.info("Calculated damage = " + calculatedDamage);

        double losses = (calculatedDamage * Math.sqrt(attackingArmy.getNumber())) / defenderStats.getHealth();
        logger.info("Losses = " + losses);

        return losses <= defendingArmy.getNumber() ? (int) Math.floor(losses) : defendingArmy.getNumber();
    }

    private double calculateBaseDamage(double defense, double modifiedAttack) {
        return modifiedAttack * Math.max(0.3, Math.min(0.75, modifiedAttack / (modifiedAttack + defense)));
    }

    private double calculateEfficiencyFactor(Army attackingArmy, Army defendingArmy) {
        double staticEfficiency = retrieveStaticEfficiency(attackingArmy.getSubType(), defendingArmy.getSubType());
        logger.trace(this + " static efficiency:\t\t" + staticEfficiency);

        double dynamicEfficiency = calculateDynamicEfficiency(attackingArmy, defendingArmy);
        return staticEfficiency + dynamicEfficiency;
    }

    private double calculateDynamicEfficiency(Army attackingArmy, Army defendingArmy) {
        double specificDamage = 0, specificReduction = 0;

        Map<ArmyType, Double> specificDamageMap = attackingArmy.getDamageVsOthers();
        Map<ArmyType, Double> specificReductionMap = defendingArmy.getDamageReductionVsOthers();
        if (ArmyType.INFANTRY != attackingArmy.getType()) {
            specificDamage = specificDamageMap.getOrDefault(defendingArmy.getType(), 0.0);
        }
        if (ArmyType.INFANTRY == defendingArmy.getType()) {
            specificReduction = specificReductionMap.getOrDefault(attackingArmy.getType(), 0.0);
        }

        // this is percentage
        return (specificDamage - specificReduction) / 100;
    }

    private double retrieveStaticEfficiency(ArmySubType attackerSubType, ArmySubType defenderSubType) {
        Map<ArmySubType, Double> staticEfficiency = CONFIGURATION.STATIC_EFFICIENCY.get(attackerSubType);
        if (staticEfficiency == null) {
            return 1;
        }
        Double efficiency = staticEfficiency.get(defenderSubType);
        logger.info("Efficiency: " + efficiency);
        return efficiency == null || efficiency == 0 ? 1 : efficiency;
    }

    private void updateLosses(int defenderLossesNumber, Army defendingArmy, boolean isForDefender) {
        List<Army> lossesToUpdate = isForDefender ? defenderLosses : attackerLosses;
        List<Army> armyToUpdate = isForDefender ? defender : attacker;
        for (int i = 0; i < armyToUpdate.size(); i++) {
            Army army = armyToUpdate.get(i);
            if (army.getTier() == defendingArmy.getTier() && army.getType() == defendingArmy.getType()) {
                int value = army.getNumber() - defenderLossesNumber;
                army.setNumber(Math.max(0, value));

                Army losses = lossesToUpdate.get(i);
                losses.setNumber(losses.getNumber() + defenderLossesNumber);
            }
        }
    }

    public static void main(String... args) throws JsonGenerationException, JsonMappingException, IOException {
        logger.info("Entering main");

        long startTime = System.currentTimeMillis();
        Battle battle = new Battle();
        logger.info("Construction of battle object took " + (System.currentTimeMillis() - startTime) + "ms");

        startTime = System.currentTimeMillis();
        battle.fight();
        logger.info("Fight calculation took " + (System.currentTimeMillis() - startTime) + "ms");

        // Map<ArmySubType, Map<ArmySubType, Double>> map = new HashMap<>();
        // Map<ArmySubType, Double> innerMap = new HashMap<>();
        // innerMap.put(ArmySubType.PIKEMEN, 1.0);
        // innerMap.put(ArmySubType.MUSKETEERS, 1.0);
        // innerMap.put(ArmySubType.RIFLEMEN, 1.0);
        // innerMap.put(ArmySubType.GRENADIERS, 1.0);
        // innerMap.put(ArmySubType.LIGHT_CAVALRY, 1.0);
        // innerMap.put(ArmySubType.HEAVY_CAVALRY, 1.0);
        // map.put(ArmySubType.PIKEMEN, innerMap);
        //
        // innerMap = new HashMap<>();
        // innerMap.put(ArmySubType.PIKEMEN, 1.0);
        // innerMap.put(ArmySubType.MUSKETEERS, 1.0);
        // innerMap.put(ArmySubType.RIFLEMEN, 1.0);
        // innerMap.put(ArmySubType.GRENADIERS, 1.0);
        // innerMap.put(ArmySubType.LIGHT_CAVALRY, 1.2);
        // innerMap.put(ArmySubType.HEAVY_CAVALRY, 1.2);
        // map.put(ArmySubType.MUSKETEERS, innerMap);
        // printInputParams(map);

        logger.info("Overall program time is " + (System.currentTimeMillis() - timer) + "ms");
    }

    public void printResults() {
        for (Army army : attacker) {
            logger.info("ArmyOfAtackerFinal:" + army);
        }
        for (Army army : defender) {
            logger.info("ArmyOfDefenderFinal:" + army);
        }
        for (Army army : attackerLosses) {
            if (army.getNumber() > 0) {
                logger.info("Attacker losses:" + army);
            }
        }
        for (Army army : defenderLosses) {
            if (army.getNumber() > 0) {
                logger.info("Defender losses:" + army);
            }
        }
    }

    // Careful - it erases resources\\inputParams.json. Use it only as a template
    private static void printInputParams(Object object)
            throws IOException, JsonGenerationException, JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("resources\\inputParams.json"), object);
    }
}
