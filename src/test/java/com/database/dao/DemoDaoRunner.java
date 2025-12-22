package com.database.dao;

import java.sql.SQLException;

import com.database.model.CustomerDBModel;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {
		CustomerDBModel customerDBData = CustomerDao.getCustomerInformation();
		System.out.println(customerDBData);

	}

}
