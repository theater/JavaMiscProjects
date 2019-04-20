package main.java.web.dto;

public class BattleResultDto {

	private int attackerLosses;
	private int defenderLosses;
	private String battleDetails;


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

	public String getBattleDetails() {
		return battleDetails;
	}

	public void setBattleDetails(String battleDetails) {
		this.battleDetails = battleDetails;
	}

}
