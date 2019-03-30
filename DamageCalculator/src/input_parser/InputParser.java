package input_parser;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import damage_calculator.InputParameters;

public class InputParser {

    private static final String SEPARATOR = ":";
    private File fileToParse;

    public InputParser(String fileLocation) throws IOException {
        fileToParse = new java.io.File(fileLocation);
    }

    public InputParameters parse() throws IOException {
        InputParameters inputParams = new ObjectMapper().readValue(fileToParse, InputParameters.class);
        return inputParams;
    }
}
