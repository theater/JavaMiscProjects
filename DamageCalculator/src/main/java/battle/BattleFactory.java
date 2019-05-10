package main.java.battle;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BattleFactory {

    private static Logger logger = LoggerFactory.getLogger(BattleFactory.class);

    public IBattle getBattle(List<Army> attacker, List<Army> defender, int rounds) {
        return getBattle(BattleType.NORMAL, attacker, defender, rounds);
    }

    public IBattle getBattle(BattleType type, List<Army> attacker, List<Army> defender, int rounds) {
        logger.debug("Creating new battle with type: " + type);
        switch (type) {
            case STRICT_CHANCE:
                return new BattleWithStrictChance(attacker, defender, rounds);
            case ZERO_CHANCE:
                return new BattleWithZeroChance(attacker, defender, rounds);
            case FULL_CHANCE:
                return new BattleWithBestChance(attacker, defender, rounds);
            case AVERAGE_LOSSES:
                return new BattleWithMultipleExecutions(attacker, defender, rounds);
            case NORMAL:
            default:
                return new Battle(attacker, defender, rounds);
        }
    }
}
