package com.database.dao;

import java.sql.SQLException;

import com.database.model.MapJobProblemModel;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {
		MapJobProblemModel customerPoblemDBData = MapJobProblemDao.getProblemDetails(136383);
		System.out.println(customerPoblemDBData);

	}

}
