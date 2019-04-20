package main.java.battle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.Util;
import main.java.config.UserInputParameters;
import main.java.parser.JSONParser;

public class MainForBattles {

	private static final Logger logger = LoggerFactory.getLogger(Battle.class);
	private static final String attackerFile = "attacker.json";
	private static final String defenderFile = "defender.json";

	private static long timer = System.currentTimeMillis();

	@SuppressWarnings("unchecked")
	public static void main(String ... args) throws IOException {
		logger.info("Entering main");

		ArrayList<Army> attacker = new ArrayList<Army>();
		ArrayList<Army> defender = new ArrayList<Army>();

		initializeArmies(attacker, defender);
		ArrayList<Army> clonedAttacker = (ArrayList<Army>) attacker.clone();
		ArrayList<Army> clonedDefender = (ArrayList<Army>) defender.clone();

		BattleFactory factory = new BattleFactory();
		IBattle battle = factory.getBattle(BattleTypes.STRICT_CHANCE, clonedAttacker, clonedDefender);

		battle.fight();

		battle.printResults();

		battleAndPrintResults(battle);
		logger.info("Overall program time is " + (System.currentTimeMillis() - timer) + "ms");
	}


	public static void battleAndPrintResults(IBattle battle) {
		battle.fight();
		battle.printResults();
	}


	private static void initializeArmies(List<Army> attacker, List<Army> defender) throws IOException {
		JSONParser parser = new JSONParser();
		UserInputParameters attackerInput = parser.parseUserInput(attackerFile);
		Util.initializeArmyCollection(attacker, attackerInput);

		UserInputParameters defenderInput = parser.parseUserInput(defenderFile);
		Util.initializeArmyCollection(defender, defenderInput);
	}
}
