package main;

import java.io.IOException;

import damage_calculator.DamageCalculator;


class Main {

    public static void main(String[] args) throws IOException {
        // InputParser inputParser = new
        // InputParser("D:\\src\\JavaMiscProjects\\DamageCalculator\\src\\input_parser\\inputFile.txt");
        // Map<String, Integer> parse = inputParser.parse();
        // System.out.println(parse);
        new DamageCalculator().calculate().printResults();
    }
}