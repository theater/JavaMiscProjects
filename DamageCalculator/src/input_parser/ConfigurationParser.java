package input_parser;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import damage_calculator.ConfigurationParameters;

public class ConfigurationParser {

    private final Logger logger = Logger.getLogger(ConfigurationParser.class);

    private File fileToParse;

    public ConfigurationParser(String fileLocation) throws IOException {
        fileToParse = new java.io.File(fileLocation);
    }

    public ConfigurationParameters parse() throws IOException {
        ConfigurationParameters parameters = new ObjectMapper().readValue(fileToParse, ConfigurationParameters.class);
        logger.trace("Parsed parameters from JSON: " + parameters);
        return parameters;
    }
}
