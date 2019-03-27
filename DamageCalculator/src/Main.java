class Main {

    public static void main(String[] args) {
        DamageCalculator calculator = new DamageCalculator();
        int[] distribution = calculator.calculate().getDistribution();
        for (int counter = 1; counter < distribution.length; counter++) {
            System.out.println("Tier " + counter + " amount: " + distribution[counter]);
        }
    }

}
