package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.MapJobProblemModel;

public class MapJobProblemDao {
	private static final Logger LOGGER = LogManager.getLogger(MapJobProblemDao.class);

	private static final String PROBLEM_QUERY = "select * from map_job_problem where tr_job_head_id = ? ";

	private MapJobProblemDao() {

	}

	public static MapJobProblemModel getProblemDetails(int tr_job_head_id) throws SQLException {
		MapJobProblemModel mapJobProblemModel = null;
		try {
			LOGGER.info("Getting the connection from databse manager");
			Connection conn = DatabaseManager.getConnection();
			LOGGER.info("Executing the sql query {}", PROBLEM_QUERY);
			PreparedStatement preparedStatement = conn.prepareStatement(PROBLEM_QUERY);
			preparedStatement.setInt(1, tr_job_head_id);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next())

			{

				mapJobProblemModel = new MapJobProblemModel(resultSet.getInt("tr_job_head_id"), resultSet.getInt("id"),
						resultSet.getInt("mst_problem_id"), resultSet.getString("remark"));

			}
		} catch (SQLException e) {
			LOGGER.error("Cannot convert the result set to the MapJobProblemModelO bean", e);

			e.printStackTrace();
		}

		return mapJobProblemModel;

	}

}
