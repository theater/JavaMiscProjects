package main.java.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.parser.JSONParser;

public class ConfigManager {

	private final Logger logger = LoggerFactory.getLogger(JSONParser.class);

	private final static String configFileName = "Configuration.json";

	private static ConfigManager manager;
	private Configuration configuration;

	private ConfigManager() {
		try {
			JSONParser jsonParser = new JSONParser();
			configuration = jsonParser.parseConfiguration(configFileName);
		} catch (IOException e) {
			logger.error("Unable to initalize configuration" + e);
			System.exit(1);
		}
	}

	public static ConfigManager getInstance() {
		if (manager == null) {
			synchronized (ConfigManager.class) {
				if (manager == null) {
					manager = new ConfigManager();
				}
			}
		}
		return manager;
	}

	public Configuration getConfiguration() {
		return configuration;
	}
}
