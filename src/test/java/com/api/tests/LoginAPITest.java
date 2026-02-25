package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;

public class LoginAPITest {
	private UserBean userCredentials;
	private AuthService authService;
	
	

	@BeforeMethod(description = "Create the request payload for login api")
	public void setup() {
		userCredentials = new UserBean();
		 userCredentials.setUsername("iamfd");  // hardcode to verify
		    userCredentials.setPassword("password");
		authService = new AuthService();

	}

	@Test(description = "Verifying if login api is working for FD user", groups = { "api", "regression", "smoke" })

	public void loginAPITest() throws IOException {

		authService.login(userCredentials).then().spec(responseSpec_OK()).body("message", equalTo("Success")).and()
				.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));

	}

}
