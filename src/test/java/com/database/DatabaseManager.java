package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {

	private static final String DB_URL = ConfigManager.getProperty("DB_URL");
	private static final String DB_USER_NAME = ConfigManager.getProperty("DB_USER_NAME");
	private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");

	private static final int MAXIMUM_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));

	private static final int MINIMUM_IDLE_COUNT = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE_COUNT"));

	private static final int CONNECTION_TIMEOUT_IN_SECS = Integer
			.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SECS"));

	private static final int IDLE_TIMEOUT_SECS = Integer.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT_SECS"));
	private static final int MAX_LIFE_TIME_IN_MINS = Integer
			.parseInt(ConfigManager.getProperty("MAX_LIFE_TIME_IN_MINS"));

	private static final String HIKARI_CP_POOL_NAME = ConfigManager.getProperty("HIKARI_CP_POOL_NAME");

	private static HikariConfig hikariConfig;
	private static volatile HikariDataSource hikariDataSource;
	private static Connection connection=null ;

	private DatabaseManager() {

	}// singleton class

	public static void initializePool() {

		// double checking lock pattern

		if (hikariDataSource == null)// first check which all the parallel threads will enter
		{
			synchronized (DatabaseManager.class) {

				if (hikariDataSource == null)// only and only for the first connection request
				{
					HikariConfig hikariConfig = new HikariConfig();
					hikariConfig.setJdbcUrl(DB_URL);
					hikariConfig.setUsername(DB_USER_NAME);
					hikariConfig.setPassword(DB_PASSWORD);

					hikariConfig.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
					hikariConfig.setMinimumIdle(MINIMUM_IDLE_COUNT);
					hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT_IN_SECS * 10000);// 10 sec-after 10 sec Java
																							// will throw sql connection
					// timeout exception
					hikariConfig.setIdleTimeout(IDLE_TIMEOUT_SECS * 10000);
					hikariConfig.setMaxLifetime(MAX_LIFE_TIME_IN_MINS * 60 * 1000);// 30 mins
					hikariConfig.setPoolName(HIKARI_CP_POOL_NAME);

					hikariDataSource = new HikariDataSource(hikariConfig);
				}
			}

		}

	}

	public static Connection getConnection() throws SQLException {

		if (hikariDataSource == null) {
			initializePool();
		}

		else if (hikariDataSource.isClosed()) {
			throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
		}
		connection = hikariDataSource.getConnection();
		return connection;
	}

}
