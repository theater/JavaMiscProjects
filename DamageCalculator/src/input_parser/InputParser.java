package input_parser;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import damage_calculator.InputParameters;

public class InputParser {

    private final Logger logger = Logger.getLogger(InputParser.class);

    private File fileToParse;

    public InputParser(String fileLocation) throws IOException {
        fileToParse = new java.io.File(fileLocation);
    }

    public InputParameters parse() throws IOException {
        InputParameters inputParams = new ObjectMapper().readValue(fileToParse, InputParameters.class);
        logger.trace("Parsed parameters from JSON: " + inputParams);
        return inputParams;
    }
}
