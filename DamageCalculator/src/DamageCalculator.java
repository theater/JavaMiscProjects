import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DamageCalculator {

    private static final double SPELL_CAPACITY_BOOST = 0.1;
    private static final double MARCH_CAPACITY_BOOST = 0.25;
    private static final int MAX_TIER = StaticData.MAX_TIER;
    private static final int STEP_UNITS = 1;

    private int calculatedMarchCapacity;

    private Map<Army, Integer> distribution = new HashMap<>();

    DamageCalculator() {
        calculatedMarchCapacity = calculateCapacity();
        initializeDistribution();
    }

    private int calculateCapacity() {
        double capacityModifier = 0;
        if (StaticData.USE_MARCH_CAPACITY_BOOST) {
            capacityModifier += MARCH_CAPACITY_BOOST;
        }
        if (StaticData.USE_MARCH_CAPACITY_SPELL) {
            capacityModifier += SPELL_CAPACITY_BOOST;
        }
        int castleCapacity = StaticData.CASTLE_BASE_MARCH_CAPACITY.get(StaticData.CASTLE_LEVEL);
        return (int) (capacityModifier * castleCapacity + StaticData.TROOPS_AMOUNT);
    }

    private void initializeDistribution() {
        ArmyType[] armyTypes = ArmyType.values();
        for (ArmyType armyType : armyTypes) {
            for (int tier = 0; tier < MAX_TIER; tier++) {
                Army army = new Army(armyType, tier);
                distribution.put(army, 0);
            }
        }
    }

    public DamageCalculator calculate() {
        long startTime = System.currentTimeMillis();
        Set<Army> allArmies = distribution.keySet();
        for (int counter = 1; counter <= calculatedMarchCapacity; counter += STEP_UNITS) {
            Army bestArmy = retrieveBestArmy(allArmies);
            Integer currentAmount = distribution.get(bestArmy);
            distribution.put(bestArmy, currentAmount + STEP_UNITS);
        }
        long timeElapsed = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed: " + timeElapsed + "ms.");
        return this;
    }

    public DamageCalculator printResults() {
        System.out.println("Initial capacity: " + StaticData.TROOPS_AMOUNT);
        System.out.println("Calculated capacity: " + calculatedMarchCapacity);

        Set<Entry<Army, Integer>> entrySet = distribution.entrySet();
        for (Entry<Army, Integer> entry : entrySet) {
            Army army = entry.getKey();
            int troopsAmount = entry.getValue();
            System.out.println(army + ":\t" + troopsAmount);
        }
        return this;
    }

    private Army retrieveBestArmy(Set<Army> allArmies) {
        Army bestArmy = null;
        double maxDelta = 0;
        for (Army army : allArmies) {
            double calculatedDelta = calculateDamageDelta(army);
            if (maxDelta < calculatedDelta) {
                maxDelta = calculatedDelta;
                bestArmy = army;
            }
        }
        return bestArmy;
    }

    private double calculateDamageDelta(Army army) {
        double damage = calculateDamage(army);
        return damage * Math.sqrt(distribution.get(army) + STEP_UNITS) - damage * Math.sqrt(distribution.get(army));
    }

    // private double calculateDamageDelta(ArmyType armyType, int tierIndex, Map<ArmyType, int[]> dataStruct) {
    // return (dataStruct.get(armyType)[tierIndex] * Math.sqrt(dataStruct.get(armyType)[tierIndex] + STEP_UNITS) -
    // dataStruct.get(armyType)[tierIndex] * Math.sqrt(dataStruct.get(armyType)[tierIndex]));
    // }

    private double calculateDamage(Army army) {
        int defense = 0;

        int baseAttack = army.getBaseAttack();
        double modifiedAttack = baseAttack * (1 + (StaticData.ATTACK_MODIFIERS.get(army.getType())) / 100);

        double baseDamage = Math.pow(modifiedAttack, 2) / (modifiedAttack + defense);

        double efficiencyFactor = army.getModifiedAttackEfficiency();

        return baseDamage * Math.min(1 + (StaticData.DAMAGE_MODIFIERS.get(army.getType()) / 100), 3) * efficiencyFactor;
    }
}
