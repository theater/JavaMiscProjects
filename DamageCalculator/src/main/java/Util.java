package main.java;


public class Util {

    public static int calculateAverage(int currentNumber, int prevAvg, int iteration) {
        if (iteration <= 0) {
            throw new ArithmeticException("Iteration should start from 1!");
        }

        return ((iteration - 1) * prevAvg + currentNumber) / iteration;
    }
}
