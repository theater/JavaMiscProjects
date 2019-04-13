package main.java.parser;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.config.Configuration;
import main.java.config.UserInputParameters;

public class JSONParser {

    private final Logger logger = LoggerFactory.getLogger(JSONParser.class);

    private ObjectMapper mapper = new ObjectMapper();

    public Configuration parseConfiguration(String file) throws IOException {
        InputStream is = new ClassPathResource(file).getInputStream();
        Configuration config = mapper.readValue(is, Configuration.class);

        logger.info("Parsed configuration from JSON: " + config);

        return config;
    }

    public UserInputParameters parseUserInput(String file) throws IOException {
    	InputStream is = new ClassPathResource(file).getInputStream();
    	UserInputParameters userInput = mapper.readValue(is, UserInputParameters.class);

    	logger.info("Parsed configuration from JSON: " + userInput);
    	return userInput;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

}
