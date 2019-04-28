package main.java.web.dto;


public class BattleInputParameters {

    private UserInputParameters attacker;
    private UserInputParameters defender;

    public UserInputParameters getAttacker() {
        return attacker;
    }

    public void setAttacker(UserInputParameters attacker) {
        this.attacker = attacker;
    }

    public UserInputParameters getDefender() {
        return defender;
    }

    public void setDefender(UserInputParameters defender) {
        this.defender = defender;
    }

    @Override
    public String toString() {
        return "BattleInputParameters [attacker=" + attacker + "\n, defender=" + defender + "]";
    }

}
