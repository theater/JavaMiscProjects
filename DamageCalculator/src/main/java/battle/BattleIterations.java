package main.java.battle;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.Util;

public class BattleIterations extends Battle {

	private static final Logger logger = LoggerFactory.getLogger(Battle.class);
	private static final int ITERATIONS = 200;

	private int attackerAverageLosses = 0;
	private int defenderAverageLosses = 0;

	public BattleIterations(List<Army> attacker, List<Army> defender) {
		super(attacker, defender);
	}

	@Override
	public void fight() {
		List<Army> attackerClone = Util.cloneArmy(attacker);
		List<Army> defenderClone = Util.cloneArmy(defender);
		for (int i = 0 ; i < ITERATIONS ; i++) {
			logger.info("Starting fight " + (i + 1));
			super.fight();

			if (i == 0) {
				attackerAverageLosses = getAttackerTotalLosses();
				defenderAverageLosses = getDefenderTotalLosses();
			} else {
				attackerAverageLosses = Util.calculateAverage(getAttackerTotalLosses(), attackerAverageLosses, i);
				defenderAverageLosses = Util.calculateAverage(getDefenderTotalLosses(), defenderAverageLosses, i);
			}

			attacker = Util.cloneArmy(attackerClone);
			defender = Util.cloneArmy(defenderClone);
		}
	}

	@Override
	public void printResults() {
//		super.printResults();
		logger.info("Average atacker losses for " + ITERATIONS + " fights = " + attackerAverageLosses);
		logger.info("Average defender losses for " + ITERATIONS + " fights = " + defenderAverageLosses);
	}
}
