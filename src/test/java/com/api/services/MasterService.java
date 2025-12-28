package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.api.constant.Role;

import io.restassured.response.Response;

public class MasterService {

	private static final String MASTER_ENDPOINT = "/master";

	public Response master(Role role) throws IOException {

		Response response = given().spec(requestSpecWithAuth(role)).when().post(MASTER_ENDPOINT);

		return response;

	}

	public Response masterInvalidToken() throws IOException {

		Response response = given().spec(requestSpec()).when().post(MASTER_ENDPOINT);

		return response;

	}

}
