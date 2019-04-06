package main.java;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import main.java.calculator.WolfDamageCalculator;


@SpringBootApplication
public class Main {

    private static String userDataFileLocation = "resources\\InputFile.json";
    private static String configFileLocation = "resources\\Configuration.json";

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Main.class, args);
        new WolfDamageCalculator(userDataFileLocation, configFileLocation).calculate().printResults();
    }
}