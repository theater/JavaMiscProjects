package main.java.battle;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.Util;

public class BattleWithMultipleExecutions extends Battle {

	private static final Logger logger = LoggerFactory.getLogger(Battle.class);
	private static final int ITERATIONS = 20;

	private int attackerAverageLosses = 0;
	private int defenderAverageLosses = 0;


	public BattleWithMultipleExecutions(List<Army> attacker, List<Army> defender) {
		super(attacker, defender);
	}

	@Override
	public void fight() {
		List<Integer> attackerLossesForEachRound = new ArrayList<Integer>(ITERATIONS);
		List<Integer> defenderLossesForEachRound = new ArrayList<Integer>(ITERATIONS);

		List<Army> attackerClone = Util.cloneArmy(attacker);
		List<Army> defenderClone = Util.cloneArmy(defender);
		for (int i = 0 ; i < ITERATIONS ; i++) {
			logger.debug("Starting fight=" + (i + 1));
			super.fight();

			attackerLossesForEachRound.add(i, getAttackerTotalLosses());
			defenderLossesForEachRound.add(i, getDefenderTotalLosses());

			attacker = Util.cloneArmy(attackerClone);
			defender = Util.cloneArmy(defenderClone);
			logger.debug("Ending fight iteration for fight=" + (i + 1));
		}

		attackerAverageLosses = (int) Math.floor(attackerLossesForEachRound.stream().mapToDouble(a -> a).average().getAsDouble());
		defenderAverageLosses = (int) Math.floor(defenderLossesForEachRound.stream().mapToDouble(a -> a).average().getAsDouble());
	}

	@Override
	public void printResults() {
		logger.info("Average atacker losses for " + ITERATIONS + " fights = " + attackerAverageLosses);
		logger.info("Average defender losses for " + ITERATIONS + " fights = " + defenderAverageLosses);
	}
}
