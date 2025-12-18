package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;

public class LoginAPIExcelDataDrivenTest {

	@Test(description = "Verifying if login api is working for FD user", groups = { "api", "regression", "datadriven",
			"json" }, dataProviderClass = com.dataproviders.DataProviderUtils.class, dataProvider = "LoginAPIExcelDataProvider")

	public void loginAPITest(UserCredentials userCredentials) throws IOException {

		given().spec(requestSpec(userCredentials)).when().post("login").then().spec(responseSpec_OK())
				.body("message", equalTo("Success")).and()
				.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));

	}

}
