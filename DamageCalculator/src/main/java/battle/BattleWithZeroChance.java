package main.java.battle;

import java.util.List;

public class BattleWithZeroChance extends Battle {

    public BattleWithZeroChance(List<Army> attacker, List<Army> defender) {
        super(attacker, defender);
        type = BattleType.ZERO_CHANCE;
    }

    public BattleWithZeroChance(List<Army> attacker, List<Army> defender, int rounds) {
        super(attacker, defender, rounds);
        type = BattleType.ZERO_CHANCE;
    }

    @Override
    protected boolean isHighChance() {
        return false;
    }

}
