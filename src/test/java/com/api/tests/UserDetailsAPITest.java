package com.api.tests;


import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;

import org.testng.annotations.Test;

public class UserDetailsAPITest {
	
	@Test(description = "Verifyif the UserDetails API response is shown correctly",groups = {"api","regression","smoke"})
	public void userDetailsAPITest() throws IOException {
		
		
	given()
	.spec(requestSpecWithAuth(FD))
	.when()
	.get("userdetails")
	.then()
	.spec(responseSpec_OK())
	.and()
	.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
	
		
	}

}
