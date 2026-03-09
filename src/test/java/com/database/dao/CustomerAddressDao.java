package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.CustomerAddressDBModel;

import io.qameta.allure.Step;

public class CustomerAddressDao {

	// Executing the query for the tr_customer_address table! which will get the
	// details of customer address
	private static final Logger LOGGER = LogManager.getLogger(CustomerAddressDao.class);
	private static final String CUSTOMER_ADDRESS_QUERY = """
			select id,
				flat_number,
				apartment_name,
				street_name,
				landmark,
				area,
				pincode,
				country,
				state
				from tr_customer_address where id = ?

							""";

	private CustomerAddressDao() {

	}
	
	
	@Step("Retrieving the Customer Address data from the database for the specific customer address id" )

	public static CustomerAddressDBModel getCustomerAddress(int customerAddressId) {

		CustomerAddressDBModel customerAddressDBModel = null;

		try {
			LOGGER.info("Getting the connection from databse manager");

			Connection conn = DatabaseManager.getConnection();
			LOGGER.info("Executing the sql query {}", CUSTOMER_ADDRESS_QUERY);
			PreparedStatement preparedStatement = conn.prepareStatement(CUSTOMER_ADDRESS_QUERY);
			preparedStatement.setInt(1, customerAddressId);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next())

			{

				customerAddressDBModel = new CustomerAddressDBModel(resultSet.getInt("id"),
						resultSet.getString("flat_number"), resultSet.getString("apartment_name"),
						resultSet.getString("street_name"), resultSet.getString("landmark"),
						resultSet.getString("area"), resultSet.getString("pincode"), resultSet.getString("country"),
						resultSet.getString("state"));
			}
		} catch (SQLException e) {
			LOGGER.error("Cannot convert the result set to the CustomerAddressDBModel bean", e);

			e.printStackTrace();
		}

		return customerAddressDBModel;

	}
}
