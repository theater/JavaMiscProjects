package main.java.battle;

public interface IBattle {

    void fight();

    void printResults();

    int getAttackerTotalLosses();

    int getDefenderTotalLosses();

    public BattleType getType();

    void setRounds(int rounds);
}
