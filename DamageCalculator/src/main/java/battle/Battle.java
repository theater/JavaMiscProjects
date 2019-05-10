package main.java.battle;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.config.ArmyStats;
import main.java.config.ArmySubType;
import main.java.config.ArmyType;
import main.java.config.ConfigManager;
import main.java.config.Configuration;

public class Battle implements IBattle {

    private static final Logger logger = LoggerFactory.getLogger(Battle.class);

    protected static final double RANDOM_FACTOR = 0.89;

    private static final Configuration CONFIGURATION = ConfigManager.getInstance().getConfiguration();

    private int rounds = 85;

    protected List<Army> attacker;
    protected List<Army> defender;

    protected int attackerTotalLosses;
    protected int defenderTotalLosses;
    protected BattleType type;

    public Battle(List<Army> attacker, List<Army> defender) {
        List<Army> clonedAttacker = Util.cloneArmy(attacker);
        sortAttacker(clonedAttacker);
        this.attacker = clonedAttacker;

        List<Army> clonedDefender = Util.cloneArmy(defender);
        sortDefender(clonedDefender);
        this.defender = clonedDefender;

        this.type = BattleType.NORMAL;
    }

    private void sortDefender(List<Army> defender) {
        Collections.sort(defender, (army1, army2) -> {
            int result = Integer.compare(army2.getSubType().getAttackSpeed(), army1.getSubType().getAttackSpeed());
            if (result == 0) {
                result = Integer.compare(army1.getTier(), army2.getTier());
            }
            return result;
        });
    }

    private void sortAttacker(List<Army> defender) {
        Collections.sort(defender, (army1, army2) -> {
            int result = Integer.compare(army2.getSubType().getAttackSpeed(), army1.getSubType().getAttackSpeed());
            if (result == 0) {
                result = Integer.compare(army2.getTier(), army1.getTier());
            }
            return result;
        });
    }

    @Override
    public void fight() {
        long startTime = System.currentTimeMillis();

        // TODO this will be calculated. Currently is constant
        int counter = rounds;
        for (int i = 0; i < counter; i++) {
            logger.info("#######################################");
            logger.info("##### Round = " + (i + 1));
            logger.info("#######################################");
            doRound();
        }
        attackerTotalLosses = attacker.stream().mapToInt(army -> army.getTotalLosses()).sum();
        defenderTotalLosses = defender.stream().mapToInt(army -> army.getTotalLosses()).sum();

        logger.debug("##########################################################################");
        logger.debug("Fight calculation took " + (System.currentTimeMillis() - startTime) + "ms");
        logger.debug("##########################################################################");
    }

    private void doRound() {
        for (int i = 0; i < attacker.size(); i++) {
            Army attackingArmyOfAttacker = attacker.get(i);
            Army defenderDefendingArmy = getOpponentArmy(attackingArmyOfAttacker, defender);
            if (attackingArmyOfAttacker.getNumber() > 0 && defenderDefendingArmy.getNumber() > 0) {
                logger.debug("### Attacker attacks...");
                int currentDefenderArmyLosses = calculateDefenderLosses(attackingArmyOfAttacker, defenderDefendingArmy);
                defenderDefendingArmy.addLosses(currentDefenderArmyLosses);
            }


            Army attackingArmyOfDefender = defender.get(i);
            Army attackerDefendingArmy = getOpponentArmy(attackingArmyOfDefender, attacker);
            if (attackingArmyOfDefender.getNumber() > 0 && attackerDefendingArmy.getNumber() > 0) {
                logger.debug("*** Defender attacks...");
                int currentAttackerArmyLosses = calculateDefenderLosses(attackingArmyOfDefender, attackerDefendingArmy);
                attackerDefendingArmy.addLosses(currentAttackerArmyLosses);
            }
        }

        updateLosses(attacker);
        updateLosses(defender);
    }

    private void updateLosses(List<Army> armiesToUpdate) {
        for (int i = 0; i < armiesToUpdate.size(); i++) {
            armiesToUpdate.get(i).updateLosses();
        }
    }

    private Army getOpponentArmy(Army attackingArmyOfAttacker, List<Army> defender) {
        int randomChance = calculateRandomChance(attackingArmyOfAttacker.getSubType());

        Army result = defender.get(defender.size() - 1);
        for (int i = defender.size() - 1; i >= 0; i--) {
            Army iteratedArmy = defender.get(i);
            if (iteratedArmy.getNumber() <= 0) {
                continue;
            }
            if (result.getNumber() <= 0 || (result.getNumber() - result.getTemporaryLosses() <= 0)) {
                result = iteratedArmy;
                continue;
            }

            Integer currentCriteria = BattleHelper.ATTACKER_CHOICE_CRITERIA.get(randomChance).get(result.getSubType());
            Integer iteratedCriteria = BattleHelper.ATTACKER_CHOICE_CRITERIA.get(randomChance)
                .get(iteratedArmy.getSubType());
            if (currentCriteria > iteratedCriteria) {
                result = iteratedArmy;
            } else if (currentCriteria == iteratedCriteria && (iteratedArmy.getTier() < result.getTier())) {
                result = iteratedArmy;
            }
        }
        return result;
    }

    // TODO Improve this. Currently it's not generic at all...
    protected int calculateRandomChance(ArmySubType armySubType) {
        int result = 1;
        if (ArmySubType.LIGHT_CAVALRY == armySubType) {
            if (isHighChance()) {
                result = 3;
            }
        } else if (ArmySubType.RIFLEMEN == armySubType) {
            if (isHighChance()) {
                result = 2;
            }
        }

        return result;
    }

    protected boolean isHighChance() {
        return Math.random() > RANDOM_FACTOR;
    }

    private int calculateDefenderLosses(Army attackingArmy, Army defendingArmy) {
        logger.debug("Attacking army: " + attackingArmy + "\tDefendingArmy: " + defendingArmy);
        ArmyStats attackerStats = attackingArmy.getArmyStats();
        ArmyStats defenderStats = defendingArmy.getArmyStats();

        double defense = defenderStats.getDefense();
        double modifiedAttack = attackerStats.getAttack();
        double baseDamage = calculateBaseDamage(defense, modifiedAttack);

        // Must be >= 0
        double damageModifiers = Math.max(0,
            Math.min(1 + ((attackerStats.getDamage() - defenderStats.getDamageReduction()) / 100), 3));
        logger.debug("Damage modifier = " + damageModifiers);

        // Must be >= 0
        double efficiencyFactor = Math.max(0, calculateEfficiencyFactor(attackingArmy, defendingArmy));
        logger.debug("Calculated efficiency = " + efficiencyFactor);

        double calculatedDamage = baseDamage * damageModifiers * efficiencyFactor;
        logger.debug("Calculated damage = " + calculatedDamage);

        double losses = (calculatedDamage * Math.sqrt(attackingArmy.getNumber())) / defenderStats.getHealth();
        logger.debug("Losses = " + losses);

        return losses <= defendingArmy.getNumber() ? (int) Math.floor(losses) : defendingArmy.getNumber();
    }

    private double calculateBaseDamage(double defense, double modifiedAttack) {
        return modifiedAttack * Math.max(0.3, Math.min(0.75, modifiedAttack / (modifiedAttack + defense)));
    }

    private double calculateEfficiencyFactor(Army attackingArmy, Army defendingArmy) {
        double staticEfficiency = retrieveStaticEfficiency(attackingArmy.getSubType(), defendingArmy.getSubType());
        logger.debug("Static efficiency:\t\t" + staticEfficiency);

        double dynamicEfficiency = calculateDynamicEfficiency(attackingArmy, defendingArmy);
        return staticEfficiency + dynamicEfficiency;
    }

    private double calculateDynamicEfficiency(Army attackingArmy, Army defendingArmy) {
        double specificDamage = 0, specificReduction = 0;

        Map<ArmyType, Double> specificDamageMap = attackingArmy.getDamageVsOthers();
        Map<ArmyType, Double> specificReductionMap = defendingArmy.getDamageReductionVsOthers();
        if (ArmyType.INFANTRY != attackingArmy.getType()) {
            specificDamage = specificDamageMap.getOrDefault(defendingArmy.getType(), 0.0);
        }
        if (ArmyType.INFANTRY == defendingArmy.getType()) {
            specificReduction = specificReductionMap.getOrDefault(attackingArmy.getType(), 0.0);
        }

        // this is percentage
        return (specificDamage - specificReduction) / 100;
    }

    private double retrieveStaticEfficiency(ArmySubType attackerSubType, ArmySubType defenderSubType) {
        Map<ArmySubType, Double> staticEfficiency = CONFIGURATION.STATIC_EFFICIENCY.get(attackerSubType);
        if (staticEfficiency == null) {
            return 1;
        }
        Double efficiency = staticEfficiency.get(defenderSubType);
        logger.debug("Efficiency: " + efficiency);
        return efficiency == null || efficiency == 0 ? 1 : efficiency;
    }

    @Override
    public void printResults() {
        logger.info("Attacker resulting army:");
        attacker.stream().forEach(army -> logger.info(army.toString()));

        logger.info("Defender resulting army:");
        defender.stream().forEach(army -> logger.info(army.toString()));

        logger.info("Attacker detailed losses:");
        attacker.stream().filter(army -> army.getTotalLosses() > 0)
            .forEach(army -> logger.info("Losses for " + army.getTypeForPrinting() + army.getTotalLosses()));

        logger.info("Defender detailed losses:");
        defender.stream().filter(army -> army.getTotalLosses() > 0)
            .forEach(army -> logger.info("Losses for " + army.getTypeForPrinting() + army.getTotalLosses()));

        logger.info("Total attacker losses: " + attackerTotalLosses);
        logger.info("Total defender losses: " + defenderTotalLosses);
    }

    @Override
    public int getAttackerTotalLosses() {
        return attackerTotalLosses;
    }

    @Override
    public int getDefenderTotalLosses() {
        return defenderTotalLosses;
    }

    @Override
    public BattleType getType() {
        return type;
    }

    @Override
    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

}
