package com.api.services;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class JobService {

	private static final String CREATE_JOB_ENDPOINT = "/job/create";
	private static final String SEARCH_JOB_ENDPOINT = "/job/search";
	private static final Logger LOGGER = LogManager.getLogger(JobService.class);

	
	
	@Step("Creating Inwarranty Job with Create Job API" )
	public Response createJob(Role role, CreateJobPayload createJobPayload) throws IOException {
		
		LOGGER.info("Making request to the {} for the role {} and payload {}", CREATE_JOB_ENDPOINT,role,createJobPayload);

		Response response = given().spec(requestSpecWithAuth(role, createJobPayload))

				.when().post(CREATE_JOB_ENDPOINT);

		return response;

	}
	@Step("Making Search API request" )
	public Response searchJob(Role role, Object payload) throws IOException {
		
		LOGGER.info("Making request to the {} for the role {} and payload {}", SEARCH_JOB_ENDPOINT,role,payload);

		Response response = given().spec(requestSpecWithAuth(role)).body(payload)

				.when().post(SEARCH_JOB_ENDPOINT);

		return response;

	}

}
