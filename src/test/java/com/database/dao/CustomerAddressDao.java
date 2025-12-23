package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerAddressDBModel;

public class CustomerAddressDao {

	// Executing the query for the tr_customer_address table! which will get the
	// details of customer address

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

	public static CustomerAddressDBModel getCustomerAddress(int customerAddressId) {

		CustomerAddressDBModel customerAddressDBModel = null;

		try {

			Connection conn = DatabaseManager.getConnection();

			PreparedStatement preparedStatement = conn.prepareStatement(CUSTOMER_ADDRESS_QUERY);
			preparedStatement.setInt(1, customerAddressId);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next())

			{

				customerAddressDBModel = new CustomerAddressDBModel(resultSet.getInt("id"),
						resultSet.getString("flat_number"),
						resultSet.getString("apartment_name"), resultSet.getString("street_name"),
						resultSet.getString("landmark"), resultSet.getString("area"), resultSet.getString("pincode"),
						resultSet.getString("country"), resultSet.getString("state"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return customerAddressDBModel;

	}
}
