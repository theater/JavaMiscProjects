import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class Main {

    public static void main(String[] args) {
        DamageCalculator calculator = new DamageCalculator();
        Map<ArmyType, int[]> distribution = calculator.calculate().getDistribution();
        Set<Entry<ArmyType, int[]>> entrySet = distribution.entrySet();
        for (Entry<ArmyType, int[]> entry : entrySet) {
            ArmyType key = entry.getKey();
            System.out.println("Army type:\t" + key);
            int[] tiersAmount = entry.getValue();
            for (int counter = 0; counter < tiersAmount.length; counter++) {
                int tier = counter + 1;
                System.out.println("Tier " + tier + " amount: " + tiersAmount[counter]);
            }
        }
    }

}
