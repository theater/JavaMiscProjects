package main.java.calculator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Battle {

	private static Logger logger = LoggerFactory.getLogger(Battle.class);
//	March attacker = new March();
//	March defender = new March();
//
//	March attackersLosses = new March();
//	March defenderLosses = new March();

	private final static int COUNTER = 50;

	private List<Army> attacker = new ArrayList<>();
	private List<Army> defender = new ArrayList<>();

	private List<Army> attackerLosses = new ArrayList<>();
	private List<Army> defenderLosses = new ArrayList<>();


	public Battle() {
		InitializationUtil.initializeAttacker(attacker);
		InitializationUtil.initializeAttacker(defender);
		InitializationUtil.initializeLosses(attackerLosses);
		InitializationUtil.initializeLosses(defenderLosses);
	}

	public void fight() {
//		for (int i = 0; i < COUNTER; i++) {
			doRound();
//		}
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
		Army result = defender.get(defender.size() -1);
		for (int i = defender.size() - 1; i >= 0; i--) {
			if(result.getNumber() == 0) {
				result = defender.get(i);
			}

			Army iteratedArmy = defender.get(i);
			Integer currentCriteria = BattleHelper.CHOICE_CRIT.get(result.getSubType());
			Integer iteratedCriteria = BattleHelper.CHOICE_CRIT.get(iteratedArmy.getSubType());
			if(currentCriteria > iteratedCriteria) {
				result = iteratedArmy;
			} else if (currentCriteria == iteratedCriteria && result.getTier() >= iteratedArmy.getTier()) {
				result = iteratedArmy;
			}
		}
		return result;
	}

	private int calculateDefenderLosses(Army attackingArmyOfAttacker, Army defendingArmy) {
		logger.info("Attacking army: " + attackingArmyOfAttacker + "\tDefendingArmy: " + defendingArmy);
		return 30;
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


	public static void main (String ...args) {
		logger.info("Entering main");
		Battle battle = new Battle();
		battle.fight();
	}
}
