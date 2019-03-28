import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DamageCalculator {

    private static final int TROOPS_AMOUNT = 220000;
    private static final int MAX_TIER = 8;
    private static final int MAX_TIER_INDEX = MAX_TIER - 1;
    private static Map<ArmyType, int[]> baseFactors = new HashMap<>();
    static {
        baseFactors.put(ArmyType.DISTANCE, new int[] { 1, 3, 5, 8, 13, 16, 19, 25 });
        baseFactors.put(ArmyType.CAVALRY, new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
        baseFactors.put(ArmyType.INFANTRY, new int[] { 1, 2, 2, 3, 3, 4, 4, 4 });
        baseFactors.put(ArmyType.ARTILLERY, new int[] { 1, 1, 1, 2, 3, 2, 3, 2 });
    }

    private Map<ArmyType, int[]> distribution = new HashMap<>();

    DamageCalculator() {
        initializeDistributionWithZeroes();
    }

    private void initializeDistributionWithZeroes() {
        ArmyType[] armyTypes = ArmyType.values();
        for (ArmyType armyType : armyTypes) {
            int[] amount = new int[MAX_TIER];
            distribution.put(armyType, amount);
            Arrays.fill(amount, 0);
        }
    }

    public DamageCalculator calculate() {
        long startTime = System.currentTimeMillis();
        for (int counter = 1; counter <= TROOPS_AMOUNT; counter++) {
            BestArmyIndex index = retrieveBestIndex();
            ArmyType bestType = index.getType();
            int tierIndex = index.getIndex();
            distribution.get(bestType)[tierIndex]++;
        }
        long timeElapsed = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed: " + timeElapsed + "ms.");
        return this;
    }

    private BestArmyIndex retrieveBestIndex() {
        double maxDelta = 0;
        int bestTierIndex = MAX_TIER_INDEX;
        ArmyType bestArmyType = null;

        Set<Entry<ArmyType, int[]>> entrySet = baseFactors.entrySet();
        for (Entry<ArmyType, int[]> entry : entrySet) {
            ArmyType armyType = entry.getKey();
            for (int tierIndex = MAX_TIER_INDEX; tierIndex >= 0; tierIndex--) {
                double calculatedDelta = calculateDamageDelta(armyType, tierIndex);
                if (maxDelta < calculatedDelta) {
                    maxDelta = calculatedDelta;
                    bestTierIndex = tierIndex;
                    bestArmyType = armyType;
                }
            }
        }
        return new BestArmyIndex(bestArmyType, bestTierIndex);
    }

    private double calculateDamageDelta(ArmyType armyType, int tierIndex) {
        return (baseFactors.get(armyType)[tierIndex] * Math.sqrt(distribution.get(armyType)[tierIndex] + 1) - baseFactors.get(armyType)[tierIndex] * Math.sqrt(distribution.get(armyType)[tierIndex]));
    }

    public Map<ArmyType, int[]> getDistribution() {
        return distribution;
    }
}
