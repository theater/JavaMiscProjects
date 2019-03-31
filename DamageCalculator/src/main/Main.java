package main;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import damage_calculator.DamageCalculator;
import damage_calculator.InputParameters;
import input_parser.InputParser;


class Main {

    private static Logger logger = Logger.getLogger(Main.class);

    private static String fileLocation = null;

    public static void main(String[] args) throws IOException {
        parseCLIArguments(args);
        if (fileLocation == null || fileLocation.isEmpty()) {
            throw new IllegalArgumentException("Missing mandatory input parameter. Please provide input JSON file by using -file <JSON file> parameter.");
        }
        logger.info("File location: " + fileLocation);
        logger.info("Log level: " + Logger.getRootLogger().getLevel());
        InputParser inputParser = new InputParser(fileLocation);
        InputParameters parsedInput = inputParser.parse();
        new DamageCalculator(parsedInput).calculate().printResults();
        // BufferedWriter stream = new BufferedWriter(stream);
        // new ObjectMapper().writeValueAsString(StaticData);
    }

    private static void parseCLIArguments(String[] args) {
        logger.info("Usage: java -jar DamageCalculator.jar -file <Path to JSON file> -logLevel <LOG4J LEVEL>");
        if (args.length == 0) {
            logger.warn("No arguments passed to CLI. Will use default file.");
        }
        if (args.length % 2 != 0) {
            logger.warn("Arguments are not even. Probably there is missed argument. Check usage.");
        }
        for (int i = 0; i < args.length; i += 2) {
            if ("-file".equals(args[i])) {
                if (i + 1 < args.length && !args[i + 1].isEmpty()) {
                    fileLocation = args[i + 1];
                }
            }
            if ("-logLevel".equals(args[i])) {
                if (i + 1 < args.length && !args[i + 1].isEmpty()) {
                    Level level = Level.toLevel(args[3]);
                    Logger.getRootLogger().setLevel(level);
                } else {
                    Level defaultLogLevel = Level.TRACE;
                    logger.info("Logging level not provided. Will use default " + defaultLogLevel + " level. If you want to provide log level please use -loglevel <LEVEL> parameter.");
                    Logger.getRootLogger().setLevel(defaultLogLevel);
                }
            }
        }
    }
}