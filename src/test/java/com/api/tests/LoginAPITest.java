package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners(com.listeners.APITestListener.class)
@Epic("User Management")
@Feature("Authentication")
public class LoginAPITest {
	private UserBean userCredentials;
	private AuthService authService;

	@BeforeMethod(description = "Create the request payload for login api")
	public void setup() {
		userCredentials = new UserBean();
		userCredentials.setUsername("iamfd");
		userCredentials.setPassword("password");
		authService = new AuthService();

	}

	@Story("Valid User should be able to login into the system")
	@Description("Verify if FD user is able to login via api")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Verifying if login api is working for FD user", groups = { "api", "regression", "smoke" })

	public void loginAPITest() throws IOException {

		authService.login(userCredentials).then().spec(responseSpec_OK()).body("message", equalTo("Success")).and()
				.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));

	}

}
