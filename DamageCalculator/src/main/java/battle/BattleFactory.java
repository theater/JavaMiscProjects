package main.java.battle;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BattleFactory {

	private static Logger logger = LoggerFactory.getLogger(BattleFactory.class);

	public IBattle getBattle(List<Army> attacker, List<Army> defender) {
		return getBattle(BattleTypes.NORMAL, attacker, defender);
	}

	public IBattle getBattle(BattleTypes type, List<Army> attacker, List<Army> defender) {
		logger.info("Creating new battle with type: ", type);
		switch (type) {
		case STRICT_CHANCE:
			return new BattleWithStrictChance(attacker, defender);
		case ZERO_CHANCE:
			return new BattleWithZeroChance(attacker, defender);
		case FULL_CHANCE:
			return new BattleWithBestChance(attacker, defender);
		case NORMAL:
		default:
			return new Battle(attacker, defender);
		}
	}
}
