package main;

import java.io.IOException;

import org.apache.log4j.Logger;

import damage_calculator.DamageCalculator;
import damage_calculator.InputParameters;
import input_parser.InputParser;


class Main {

    private static Logger logger = Logger.getLogger(Main.class);

    private static String fileLocation = "D:\\src\\JavaMiscProjects\\DamageCalculator\\resources\\InputFile.json";

    public static void main(String[] args) throws IOException {
        parseCLIArguments(args);
        logger.debug("File location: " + fileLocation);
        InputParser inputParser = new InputParser(fileLocation);
        InputParameters parsedInput = inputParser.parse();
        new DamageCalculator(parsedInput).calculate().printResults();
    }

    private static void parseCLIArguments(String[] args) {
        if (args.length != 2) {
            if (args.length == 0) {
                logger.warn("No arguments passed to CLI. Will use default file.");
            }
        } else {
            if ("-file".equals(args[0]) && !args[1].isEmpty()) {
                fileLocation = args[1];
                return;
            }
        }
        logger.info("Usage: java -jar DamageCalculator.jar -file <Path to JSON file>");

    }
}