package main.java.battle;

import java.util.List;

public class BattleWithZeroChance extends Battle {

	public BattleWithZeroChance(List<Army> attacker, List<Army> defender) {
		super(attacker, defender);
	}

	@Override
	protected boolean isHighChance() {
		return false;
	}

}
