package input_parser;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import config.Configuration;
import config.UserInputParameters;

public class JSONParser {

    private final Logger logger = Logger.getLogger(JSONParser.class);

    private ObjectMapper mapper = new ObjectMapper();

    public UserInputParameters parseInputParameters(String fileLocation) throws IOException {
        File fileToParse = new java.io.File(fileLocation);
        UserInputParameters inputParams = mapper.readValue(fileToParse, UserInputParameters.class);
        logger.trace("Parsed parameters from JSON: " + inputParams);
        return inputParams;
    }

    public Configuration parseConfiguration(String fileLocation) throws IOException {
        File fileToParse = new java.io.File(fileLocation);
        Configuration config = mapper.readValue(fileToParse, Configuration.class);
        logger.trace("Parsed configuration from JSON: " + config);
        return config;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

}
