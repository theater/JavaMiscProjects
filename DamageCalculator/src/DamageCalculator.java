import java.util.Arrays;

public class DamageCalculator {

    private static final int TROOPS_AMOUNT = 220000;
    private static final int MAX_TIER = 8;
    private static final int MAX_TIER_INDEX = MAX_TIER - 1;

    DamageCalculator() {
        Arrays.fill(distribution, 0);
    }

    private static int[] factors = {
            1, 2, 3, 4, 5, 6, 7, 8
    };

    private int[] distribution = new int[MAX_TIER];

    public DamageCalculator calculate() {
    	long startTime = System.currentTimeMillis();
        for (int counter = 1; counter <= TROOPS_AMOUNT; counter++) {
            int tier = retrieveBestIndex();
            distribution[tier]++;
        }
        long timeElapsed = System.currentTimeMillis() - startTime;
        System.out.println("Time elapsed: " + timeElapsed + "ms.");
        return this;
    }

    private int retrieveBestIndex() {
        double maxDelta = 0;
        int bestTierIndex = MAX_TIER_INDEX;
        for (int tierIndex = MAX_TIER_INDEX; tierIndex >= 0; tierIndex--) {
            double calculatedDelta = calculateDamageDelta(tierIndex);
            if (maxDelta < calculatedDelta) {
                maxDelta = calculatedDelta;
                bestTierIndex = tierIndex;
            }
        }
        return bestTierIndex;
    }

	private double calculateDamageDelta(int tierIndex) {
		return (factors[tierIndex] * Math.sqrt(distribution[tierIndex] +1) - factors[tierIndex] * Math.sqrt(distribution[tierIndex]));
	}


    public int[] getDistribution() {
        return distribution;
    }


}
