package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.api.utils.ConfigManager;

public class DatabaseManager {

	private static final String DB_URL = ConfigManager.getProperty("DB_URL");
	private static final String DB_USER_NAME = ConfigManager.getProperty("DB_USER_NAME");
	private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");
	private static volatile Connection conn;

	private DatabaseManager() {

	}// singleton class

	public static void createConnection() throws SQLException {
		
		//double checking lock pattern

		if (conn == null)// first check which all the parallel threads will enter
		{
			synchronized (DatabaseManager.class) {

				if (conn == null)// only and only for the first connection request
				{
					conn = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
					System.out.println(conn);
				}
			}

		}

	}
}
