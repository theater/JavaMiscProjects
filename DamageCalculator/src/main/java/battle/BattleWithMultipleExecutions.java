package main.java.battle;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.config.ConfigManager;

public class BattleWithMultipleExecutions extends Battle {

    private static final boolean IS_PARALLEL_COMPUTING = ConfigManager.getInstance().getConfiguration().IS_PARALLEL;

    private static final Logger logger = LoggerFactory.getLogger(Battle.class);
    private static final int ITERATIONS = 20;

    private int attackerAverageLosses = 0;
    private int defenderAverageLosses = 0;


    public BattleWithMultipleExecutions(List<Army> attacker, List<Army> defender) {
        super(attacker, defender);
        type = BattleType.AVERAGE_LOSSES;
    }

    @Override
    public void fight() {
        List<Battle> battles = new ArrayList<>();
        for (int i = 0; i < ITERATIONS; i++) {
            logger.debug("Creating battle #", i + 1);
            battles.add(new Battle(attacker, defender));
        }
        if (IS_PARALLEL_COMPUTING) {
            battles.parallelStream().forEach(battle -> {
                battle.fight();
            });
        } else {
            battles.stream().forEach(battle -> {
                battle.fight();
            });
        }

        attackerTotalLosses = (int) Math.floor(battles.stream().mapToDouble(battle -> battle.getAttackerTotalLosses()).average().getAsDouble());
        defenderTotalLosses = (int) Math.floor(battles.stream().mapToDouble(battle -> battle.getDefenderTotalLosses()).average().getAsDouble());
    }

    @Override
    public void printResults() {
        logger.info("Average atacker losses for " + ITERATIONS + " fights = " + attackerAverageLosses);
        logger.info("Average defender losses for " + ITERATIONS + " fights = " + defenderAverageLosses);
    }
}
