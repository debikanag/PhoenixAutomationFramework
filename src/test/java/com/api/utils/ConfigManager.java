package com.api.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigManager {
//write a program to read the properties file from src/test/resources/config/config.properties

	private static Properties prop = new Properties();
	private static String path = "config/config.properties";
	private static String env;
	private static final Logger LOGGER = LogManager.getLogger(ConfigManager.class);

	private ConfigManager()// private constructor
	{

	}

	// static block it will execute once during class loading time
	static {
		// File configFile = new File(System.getProperty("user.dir")+ File.separator
		// +"src"+ File.separator +"test"+ File.separator +"resources"+ File.separator
		// +"config"+ File.separator +"config.properties");

		// FileReader fileReader = null;

		LOGGER.info("Reading env value passed from terminal");
		if (System.getProperty("env") == null) {
			LOGGER.warn("Env variable is not set ...... using qa as the env ");
		}
		env = System.getProperty("env", "qa");

		LOGGER.info("Running the tests in the env{}", env);
		env = env.toLowerCase().trim();

		switch (env) {
		case "dev" -> path = "config/config.dev.properties";
		case "qa" -> path = "config/config.qa.properties";
		case "uat" -> path = "config/config.uat.properties";
		default -> path = "config/config.qa.properties";
		}

		LOGGER.info("Using the properties file from the path {}", path);
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

		if (input == null) {
			LOGGER.error("Cannot find the file at the path {}", path);
			throw new RuntimeException("cannot find the file at the path" + path);
		}

		try {
			// fileReader = new FileReader(configFile);
			prop.load(input);
		} catch (FileNotFoundException e) {
			LOGGER.error("Cannot find the file at the path {}", path, e);
			e.printStackTrace();

		} catch (IOException e) {
			LOGGER.error("Something went wrong ...please check the file {}", path, e);
			e.printStackTrace();
		}

	}

	public static String getProperty(String key) {

		// Load the properties file using the load()

		return prop.getProperty(key);

		// System.out.println(System.getProperty("user.dir"));

	}

}
