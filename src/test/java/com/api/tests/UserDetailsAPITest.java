package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.services.UserService;

public class UserDetailsAPITest {

	private UserService userService;

	@BeforeMethod(description = "Initializing the User Service")

	public void setup() {
		userService = new UserService();
	}

	@Test(description = "Verifyif the UserDetails API response is shown correctly", groups = { "api", "regression",
			"smoke" })
	public void userDetailsAPITest() throws IOException {

		userService.userDetails(FD).then().spec(responseSpec_OK()).and()
				.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));

	}

}
