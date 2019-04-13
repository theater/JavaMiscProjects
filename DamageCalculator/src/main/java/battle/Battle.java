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

import main.java.config.ArmyType;
import main.java.config.ConfigManager;
import main.java.config.UserInputParameters;
import main.java.parser.JSONParser;

public class Battle {

	private static Logger logger = LoggerFactory.getLogger(Battle.class);
	private static final int MAX_TIER = ConfigManager.getInstance().getConfiguration().ABSOLUTE_MAX_TIER;

	private final static int COUNTER = 5;
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
		for (ArmyType armyType : armyTypes) {
			List<Integer> armyByType = army.get(armyType);
			for (int i = 0; i < MAX_TIER; i++) {
				int unitsAmount = 0;
				if (armyByType != null && armyByType.size() == MAX_TIER) {
					unitsAmount = armyByType.get(i);
				}
				Army newArmy = new Army(armyType, i, unitsAmount);
				armyCollection.add(newArmy);
				addArmyModifiers(newArmy, input);
			}
		}
		Collections.sort(armyCollection);
	}

	private void addArmyModifiers(Army newArmy, UserInputParameters attackerInput) {
		//TODO add new fields to Army class and calculate necessary modifiers in this method
	}


	public void fight() {
		for (int i = 0; i < COUNTER; i++) {
			doRound();
		}
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

	private void doRound() {
		for (int i = 0; i <  attacker.size(); i++) {
			Army attackingArmyOfAttacker = attacker.get(i);
			Army defenderDefendingArmy = getOpponentArmy(attackingArmyOfAttacker, defender);
			int defenderLosses = calculateDefenderLosses(attackingArmyOfAttacker, defenderDefendingArmy);

			Army attackingArmyOfDefender = defender.get(i);
			Army attackerDefendingArmy = getOpponentArmy(attackingArmyOfDefender, attacker);
			int attackerLosses = calculateDefenderLosses(attackingArmyOfDefender, attackerDefendingArmy);

			updateLosses(defenderLosses, defenderDefendingArmy, true);
			updateLosses(attackerLosses, attackerDefendingArmy, false);
		}
	}

	private Army getOpponentArmy(Army attackingArmyOfAttacker, List<Army> defender) {
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

			Integer currentCriteria = BattleHelper.CHOICE_CRIT.get(result.getSubType());
			Integer iteratedCriteria = BattleHelper.CHOICE_CRIT.get(iteratedArmy.getSubType());
			if(currentCriteria > iteratedCriteria) {
				result = iteratedArmy;
			} else if (currentCriteria == iteratedCriteria
					&& (iteratedArmy.getTier() < result.getTier())) {
				result = iteratedArmy;
			}
		}
		return result;
	}

	private int calculateDefenderLosses(Army attackingArmyOfAttacker, Army defendingArmy) {
		logger.info("Attacking army: " + attackingArmyOfAttacker + "\tDefendingArmy: " + defendingArmy);
		return 12;
	}

	private void updateLosses(int defenderLossesNumber, Army defendingArmy, boolean isForDefender) {
		List<Army> lossesToUpdate = isForDefender ? defenderLosses : attackerLosses;
		List<Army> armyToUpdate = isForDefender ? defender : attacker;
		for (int i = 0; i < armyToUpdate.size() ; i ++) {
			Army army = armyToUpdate.get(i);
			if (army.getTier() == defendingArmy.getTier() && army.getType() == defendingArmy.getType()) {
				int value = army.getNumber() - defenderLossesNumber;
				army.setNumber(Math.max(0, value));

				Army losses = lossesToUpdate.get(i);
				losses.setNumber(losses.getNumber() + defenderLossesNumber);
			}
		}
	}


	public static void main (String ...args) throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("Entering main");

		Battle battle = new Battle();
		battle.fight();
	}

	//Careful - it erases resources\\inputParams.json. Use it only as a template
	private static void printInputParams(Object object) throws IOException, JsonGenerationException, JsonMappingException {
		UserInputParameters userInputParameters = new UserInputParameters();
		ObjectMapper mapper = new ObjectMapper();
//		mapper.writeValue(new File("resources\\inputParams.json"), userInputParameters);
		mapper.writeValue(new File("resources\\inputParams.json"), object);
	}
}
