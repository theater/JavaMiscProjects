package input_parser;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import config.Configuration;
import config.UserInputParameters;

public class JSONParser {

    private final Logger logger = LoggerFactory.getLogger(JSONParser.class);

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
