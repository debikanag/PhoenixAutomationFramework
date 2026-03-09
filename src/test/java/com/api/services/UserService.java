package com.api.services;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserService {

	// service class!! It is going to hold the APIs that belongs to the Auth

	private static final String USERDETAILS_ENDPOINT = "/userdetails";
	
	private static final Logger LOGGER = LogManager.getLogger(UserService.class);

	
	@Step("Making UserDetails API request" )
	
	public Response userDetails(Role role) throws IOException {
		
		LOGGER.info("Making request to the {} for the role {}", USERDETAILS_ENDPOINT,role);

		Response response = given().spec(requestSpecWithAuth(role)).when().get(USERDETAILS_ENDPOINT);

		return response;

	}

}
