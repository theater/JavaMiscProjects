package main.java.battle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.parser.JSONParser;
import main.java.web.dto.UserInputParameters;

public class MainForBattles {

    private static final Logger logger = LoggerFactory.getLogger(Battle.class);
    private static final String attackerFile = "attacker.json";
    private static final String defenderFile = "defender.json";

    private static long timer = System.currentTimeMillis();
    private static int rounds;

    public static void main(String... args) throws IOException {
        logger.info("Entering main");

        ArrayList<Army> attacker = new ArrayList<Army>();
        ArrayList<Army> defender = new ArrayList<Army>();

        initializeArmies(attacker, defender);

        BattleFactory factory = new BattleFactory();
        BattleType[] values = BattleType.values();
        for (BattleType type : values) {
            logger.info("Starting battle type: " + type);
            List<Army> clonedAttacker = Util.cloneArmy(attacker);
            List<Army> clonedDefender = Util.cloneArmy(defender);
            IBattle battle = factory.getBattle(type, clonedAttacker, clonedDefender);
            battle.setRounds(rounds);
            battle.fight();
        }

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
        rounds = attackerInput.getRounds();

        UserInputParameters defenderInput = parser.parseUserInput(defenderFile);
        Util.initializeArmyCollection(defender, defenderInput);
    }
}
