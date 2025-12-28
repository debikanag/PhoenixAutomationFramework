package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.api.constant.Role;

import io.restassured.response.Response;

public class DashboardService {

	private static final String COUNT_ENDPOINT = "/dashboard/count";
	private static final String DETAILS_ENDPOINT = "/dashboard/details";

	public Response count(Role role) throws IOException {

		Response response = given().spec(requestSpecWithAuth(role)).when().get(COUNT_ENDPOINT);

		return response;

	}

	public Response countWithNoAuth() throws IOException {

		Response response = given().spec(requestSpec()).when().get(COUNT_ENDPOINT);

		return response;

	}

	public Response details(Role role, Object payload) throws IOException {

		Response response = given().spec(requestSpecWithAuth(role)).body(payload).when().post(DETAILS_ENDPOINT);

		return response;

	}

}
