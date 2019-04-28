package main.java.battle;

import java.util.List;

import main.java.config.ArmySubType;

public class BattleWithStrictChance extends Battle {

    private static final int CHANCE_RATIO = 9;

    private int lightCavalryCounter = 1;
    private int riflemenCounter = 1;

    public BattleWithStrictChance(List<Army> attacker, List<Army> defender) {
        super(attacker, defender);
        type = BattleType.STRICT_CHANCE;
    }

    @Override
    protected int calculateRandomChance(ArmySubType armySubType) {
        int result = 1;
        if (ArmySubType.LIGHT_CAVALRY == armySubType) {
            if (lightCavalryCounter > 9) {
                result = 3;
                lightCavalryCounter = 1;
            }
            lightCavalryCounter++;
        } else if (ArmySubType.RIFLEMEN == armySubType) {
            if (riflemenCounter > CHANCE_RATIO) {
                result = 2;
                riflemenCounter = 1;
            }
            riflemenCounter++;
        }

        return result;
    }

}
