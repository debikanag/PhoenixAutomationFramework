package com.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPDemo {

	public static void main(String[] args) throws SQLException {

		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(ConfigManager.getProperty("DB_URL"));
		hikariConfig.setUsername(ConfigManager.getProperty("DB_USER_NAME"));
		hikariConfig.setPassword(ConfigManager.getProperty("DB_PASSWORD"));

		hikariConfig.setMaximumPoolSize(4);
		hikariConfig.setMinimumIdle(1);
		hikariConfig.setConnectionTimeout(10000);// 10 sec-after 10 sec Java will throw sql connection timeout exception
		hikariConfig.setIdleTimeout(10000);
		hikariConfig.setMaxLifetime(1800000);// 30 mins
		hikariConfig.setPoolName("Phoenix Test Automation Framework");

		HikariDataSource ds = new HikariDataSource(hikariConfig);
		Connection conn = ds.getConnection();
		System.out.println(conn);

		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery("select first_name,last_name,mobile_number from tr_customer tc;");

		while (resultSet.next()) {
			String first_name = resultSet.getString("first_name");
			String last_name = resultSet.getString("last_name");
			String mobile_number = resultSet.getString("mobile_number");

			System.out.println(first_name + "|" + last_name + "|" + mobile_number);
		}
		ds.close();
	}

}
