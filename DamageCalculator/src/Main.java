import java.util.Date;

class Main {

	public static void main(String[] args) {
		DamageCalculator calculator = new DamageCalculator();
		int[] distribution = calculator.calculate().getDistribution();
		for (int counter = 0; counter < distribution.length; counter++) {
			int tier = counter + 1;
			System.out.println("Tier " + tier + " amount: " + distribution[counter]);
		}
	}

}
