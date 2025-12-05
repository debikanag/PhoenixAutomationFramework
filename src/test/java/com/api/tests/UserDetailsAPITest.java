package com.api.tests;

import static com.api.utils.ConfigManagerOLD.getProperty;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import static com.api.utils.AuthTokenProvider.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import static com.api.constant.Role.*;

public class UserDetailsAPITest {
	
	@Test
	public void userDetailsAPITest() throws IOException {
		
		
	given()
	.spec(SpecUtil.requestSpecWithAuth(FD))
	.when()
	.get("userdetails")
	.then()
	.spec(SpecUtil.responseSpec_OK())
	.and()
	.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
	
		
	}

}
