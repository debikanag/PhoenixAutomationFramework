package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;
import com.api.request.model.UserCredentials;

import io.restassured.response.Response;

public class DashboardService {

	private static final String COUNT_ENDPOINT = "/dashboard/count";
	private static final String DETAILS_ENDPOINT = "/dashboard/details";
	private static final Logger LOGGER = LogManager.getLogger(DashboardService.class);

	public Response count(Role role) throws IOException {
		LOGGER.info("Making request to the {} for the role {}", COUNT_ENDPOINT,role);

		Response response = given().spec(requestSpecWithAuth(role)).when().get(COUNT_ENDPOINT);

		return response;

	}

	public Response countWithNoAuth() throws IOException {

		LOGGER.info("Making request to the {} with no auth Token", COUNT_ENDPOINT);
		
		Response response = given().spec(requestSpec()).when().get(COUNT_ENDPOINT);

		return response;

	}

	public Response details(Role role, Object payload) throws IOException {

		LOGGER.info("Making request to the {} for the role {} and the payload {}", DETAILS_ENDPOINT,role,payload);
		
		Response response = given().spec(requestSpecWithAuth(role)).body(payload).when().post(DETAILS_ENDPOINT);

		return response;

	}

}
