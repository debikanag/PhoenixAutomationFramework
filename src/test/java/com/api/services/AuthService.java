package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import io.restassured.response.Response;

public class AuthService {

//service class!! It is going to hold the APIs that belongs to the Auth

	private static final String LOGIN_ENDPOINT = "/login";

	public Response login(Object usercredentials) throws IOException {

		Response response = given().spec(requestSpec(usercredentials)).when().post(LOGIN_ENDPOINT);

		return response;

	}

}
