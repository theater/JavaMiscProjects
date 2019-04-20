package main.java.battle;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.Util;
import main.java.config.ArmyStats;
import main.java.config.ArmySubType;
import main.java.config.ArmyType;
import main.java.config.ConfigManager;
import main.java.config.Configuration;
import main.java.config.UserInputParameters;
import main.java.parser.JSONParser;

public class Battle implements IBattle {

	private static Logger logger = LoggerFactory.getLogger(Battle.class);

	protected static final double RANDOM_FACTOR = 0.89;

	private static final Configuration CONFIGURATION = ConfigManager.getInstance().getConfiguration();

	private final static int ROUNDS_COUNTER = 30;
	private static final String attackerFile = "attacker.json";
	private static final String defenderFile = "defender.json";

	private List<Army> attacker;
	private List<Army> defender;

	public Battle(List<Army> attacker, List<Army> defender) {
		this.attacker = attacker;
		this.defender = defender;
	}

	public Battle() throws IOException {
		JSONParser parser = new JSONParser();
		UserInputParameters attackerInput = parser.parseUserInput(attackerFile);
		Util.initializeArmyCollection(attacker, attackerInput);

		UserInputParameters defenderInput = parser.parseUserInput(defenderFile);
		Util.initializeArmyCollection(defender, defenderInput);
	}

	public void fight() {
		long startTime = System.currentTimeMillis();

		// TODO this will be calculated. Currently is constant
		int counter = ROUNDS_COUNTER;
		for (int i = 0; i < counter; i++) {
			doRound();
		}
		logger.info("##########################################################################");
		logger.info("Fight calculation took " + (System.currentTimeMillis() - startTime) + "ms");
		logger.info("##########################################################################");
	}

	private void doRound() {
		for (int i = 0; i < attacker.size(); i++) {
			Army attackingArmyOfAttacker = attacker.get(i);
			Army defenderDefendingArmy = getOpponentArmy(attackingArmyOfAttacker, defender);
			if (attackingArmyOfAttacker.getNumber() > 0 && defenderDefendingArmy.getNumber() > 0) {
				int currentDefenderArmyLosses = calculateDefenderLosses(attackingArmyOfAttacker, defenderDefendingArmy);
				defenderDefendingArmy.addLosses(currentDefenderArmyLosses);
			}

			Army attackingArmyOfDefender = defender.get(i);
			Army attackerDefendingArmy = getOpponentArmy(attackingArmyOfDefender, attacker);
			if (attackingArmyOfDefender.getNumber() > 0 && attackerDefendingArmy.getNumber() > 0) {
				int currentAttackerArmyLosses = calculateDefenderLosses(attackingArmyOfDefender, attackerDefendingArmy);
				attackerDefendingArmy.addLosses(currentAttackerArmyLosses);
			}
		}

		updateLosses(attacker);
		updateLosses(defender);
	}

	private void updateLosses(List<Army> armiesToUpdate) {
		for (int i = 0; i < armiesToUpdate.size(); i++) {
			armiesToUpdate.get(i).updateLosses();
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
			Integer iteratedCriteria = BattleHelper.ATTACKER_CHOICE_CRITERIA.get(randomChance)
					.get(iteratedArmy.getSubType());
			if (currentCriteria > iteratedCriteria) {
				result = iteratedArmy;
			} else if (currentCriteria == iteratedCriteria && (iteratedArmy.getTier() < result.getTier())) {
				result = iteratedArmy;
			}
		}
		return result;
	}

	// TODO Improve this. Currently it's not generic at all...
	protected int calculateRandomChance(ArmySubType armySubType) {
		int result = 1;
		if (ArmySubType.LIGHT_CAVALRY == armySubType) {
			if (isHighChance()) {
				result = 3;
			}
		} else if (ArmySubType.RIFLEMEN == armySubType) {
			if (isHighChance()) {
				result = 2;
			}
		}

		return result;
	}

	protected boolean isHighChance() {
		return Math.random() > RANDOM_FACTOR;
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
		logger.trace("Static efficiency:\t\t" + staticEfficiency);

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

	public void printResults() {
		logger.info("Attacker resulting army:");
		attacker.stream().forEach(army -> logger.info(army.toString()));

		logger.info("Defender resulting army:");
		defender.stream().forEach(army -> logger.info(army.toString()));

		logger.info("Attacker losses:");
		attacker.stream().filter(army -> army.getTotalLosses() > 0)
				.forEach(army -> logger.info("Losses for " + army.getTypeForPrinting() + army.getTotalLosses()));
		int sum = attacker.stream().mapToInt(army -> army.getTotalLosses()).sum();
		logger.info("Total attacker losses: " + sum);

		logger.info("Defender losses:");
		defender.stream().filter(army -> army.getTotalLosses() > 0)
				.forEach(army -> logger.info("Losses for " + army.getTypeForPrinting() + army.getTotalLosses()));
		sum = defender.stream().mapToInt(army -> army.getTotalLosses()).sum();
		logger.info("Total defender losses: " + sum);
	}
}
