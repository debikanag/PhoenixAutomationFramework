package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;

public class CustomerProductDao {

	// Executing the query for the tr_customer_address table! which will get the
	// details of customer address

	private static final String CUSTOMER_PRODUCT_QUERY = """
			select id,
					serial_number,
					imei1,
					imei2,
					popurl,
					dop,
					mst_model_id,
					tr_customer_id
			from tr_customer_product where id = ?

												""";

	private CustomerProductDao() {

	}

	public static CustomerProductDBModel getCustomerProduct(int tr_customer_product_id) {

		CustomerProductDBModel customerProductDBModel = null;

		try {

			Connection conn = DatabaseManager.getConnection();

			PreparedStatement preparedStatement = conn.prepareStatement(CUSTOMER_PRODUCT_QUERY);
			preparedStatement.setInt(1, tr_customer_product_id);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next())

			{

				customerProductDBModel = new CustomerProductDBModel(resultSet.getInt("id"),
						resultSet.getInt("tr_customer_id"), resultSet.getInt("mst_model_id"),
						resultSet.getString("dop"), resultSet.getString("popurl"), resultSet.getString("imei2"),
						resultSet.getString("imei1"), resultSet.getString("serial_number"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return customerProductDBModel;

	}
}
