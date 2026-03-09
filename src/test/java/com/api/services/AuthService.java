package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserBean;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class AuthService {

//service class!! It is going to hold the APIs that belongs to the Auth

	private static final String LOGIN_ENDPOINT = "/login";

	private static final Logger LOGGER = LogManager.getLogger(AuthService.class);

	
	
	
	@Step("Perform login request with the user credentials")
	public Response login(UserBean userBean) throws IOException {

		UserCredentials userCredentials = new UserCredentials(userBean.getUsername(), userBean.getPassword());

		LOGGER.info("Making login request for the payload {}", userCredentials.username());

		Response response = given().spec(requestSpec(userCredentials)).when().post(LOGIN_ENDPOINT);

		return response;

	}

}
