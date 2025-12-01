package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
//write a program to read the properties file from src/test/resources/config/config.properties

	private static Properties prop = new Properties();
	private static String path = "config/config.properties";
	private static String env;

	private ConfigManager()// private constructor
	{

	}

	// static block it will execute once during class loading time
	static {
		// File configFile = new File(System.getProperty("user.dir")+ File.separator
		// +"src"+ File.separator +"test"+ File.separator +"resources"+ File.separator
		// +"config"+ File.separator +"config.properties");

		// FileReader fileReader = null;
		env = System.getProperty("env","qa");
		env = env.toLowerCase().trim();
		System.out.println("Running test in "+env);
		switch(env) {
		case "dev" -> path = "config/config.dev.properties";
		case "qa" -> path = "config/config.qa.properties";
		case "uat" -> path = "config/config.uat.properties";
		default ->path = "config/config.qa.properties";
		}
 InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

		if (input == null) {
			throw new RuntimeException("cannot find the file at the path" + path);
		}

		try {
			// fileReader = new FileReader(configFile);
			prop.load(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getProperty(String key) throws IOException {

		// Load the properties file using the load()

		return prop.getProperty(key);

		// System.out.println(System.getProperty("user.dir"));
	

	}

}
