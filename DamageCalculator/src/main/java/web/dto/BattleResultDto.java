package main.java.web.dto;

public class BattleResultDto {

    private int attackerLosses;
    private int defenderLosses;
    private String battleType;

    public BattleResultDto() {

    }

    public BattleResultDto(int attackerLosses, int defenderLosses, String battleType) {
        this.attackerLosses = attackerLosses;
        this.defenderLosses = defenderLosses;
        this.battleType = battleType;
    }

    public int getAttackerLosses() {
        return attackerLosses;
    }

    public void setAttackerLosses(int attackerLosses) {
        this.attackerLosses = attackerLosses;
    }

    public int getDefenderLosses() {
        return defenderLosses;
    }

    public void setDefenderLosses(int defenderLosses) {
        this.defenderLosses = defenderLosses;
    }

    public String getBattleType() {
        return battleType;
    }

    public void setBattleType(String battleDetails) {
        this.battleType = battleDetails;
    }

}
