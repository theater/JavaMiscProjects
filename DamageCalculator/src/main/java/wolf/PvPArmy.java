package main.java.wolf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.config.ArmySubType;
import main.java.config.ArmyType;
import main.java.config.CalculationsHelper;

public class PvPArmy extends Army {

    private static Logger logger = LoggerFactory.getLogger(WolfArmy.class);

    public PvPArmy(ArmyType type, int tier, CalculationsHelper helper, double enemyDefense) {
        super(type, tier, helper, enemyDefense);
    }

    @Override
    protected double calculateDamage() {
        double baseAttack = getBaseAttack();
        logger.trace(this + " base attack:\t\t" + baseAttack);

        double modifiedAttack = baseAttack * (1 + (helper.ATTACK_MODIFIERS.get(getType())) / 100);
        logger.trace(this + " modified attack:\t" + modifiedAttack);

        double baseDamage = modifiedAttack * Math.min(0.75, Math.max(modifiedAttack / (modifiedAttack + enemyDefense), 0.3));
        logger.trace(this + " base damage:\t\t" + baseDamage);

        double efficiencyFactor = Math.min(Math.max(attackEfficiency - 1.0, 0.3), 3);
        logger.trace(this + " efficiency:\t\t" + efficiencyFactor);

        double calculatedDamage = baseDamage * Math.min(Math.max(1 + (helper.DAMAGE_MODIFIERS.get(getType()) / 100) - 3, 0.3), 3) * efficiencyFactor;
        logger.trace(this + " calculated damage:\t" + calculatedDamage);

        return calculatedDamage;
    }

    @Override
    protected double calculateAttackEfficiency() {
        double baseEfficiency = subType == ArmySubType.RIFLEMEN ? 0.8 : subType == ArmySubType.HEAVY_CAVALRY ? 1.2 : 1;
        double efficiencyModifier = type == ArmyType.CAVALRY ? helper.CAVALRY_VS_INFANTRY_DAMAGE / 100
            : type == ArmyType.DISTANCE ? helper.DISTANCE_VS_INFANTRY_DAMAGE / 100 : 0;
        double reduction = 0;
        return Math.min(MAX_EFFICIENCY_FACTOR, baseEfficiency + efficiencyModifier - reduction);
    }

}
