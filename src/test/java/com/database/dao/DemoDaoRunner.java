package com.database.dao;

import java.sql.SQLException;

import com.database.model.CustomerProductDBModel;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {
		CustomerProductDBModel customerProductDBData= CustomerProductDao.getCustomerProduct(136293);
		System.out.println(customerProductDBData);

	}

}
