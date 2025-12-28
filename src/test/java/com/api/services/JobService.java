package com.api.services;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;

import io.restassured.response.Response;

public class JobService {

	private static final String CREATE_JOB_ENDPOINT = "/job/create";

	public Response createJob(Role role, CreateJobPayload createJobPayload) throws IOException {

		Response response = given().spec(requestSpecWithAuth(role, createJobPayload))

				.when().post(CREATE_JOB_ENDPOINT);

		return response;

	}

}
