package com.database.dao;

import java.sql.SQLException;

import com.database.model.JobHeadModel;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {
		JobHeadModel jobHeadDBData = JobHeadDao.getDataFromJobhead(136414);
		System.out.println(jobHeadDBData);

	}

}
