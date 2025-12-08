package com.api.tests;

import static io.restassured.RestAssured.*;


import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import static com.api.utils.SpecUtil.*;


import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class LoginAPITest {
	private UserCredentials userCredentials;
	
	@BeforeMethod(description = "Create the request payload for login api")
	public void setup()
	{
		userCredentials = new UserCredentials("iamfd","password");
		
		
	}
	
	@Test(description = "Verifying if login api is working for FD user",groups = {"api","regression","smoke"})
	
	public void loginAPITest() throws IOException
	{

		given()
		.spec(requestSpec(userCredentials))
		.when()
		.post("login")
		.then()
		.spec(responseSpec_OK())
		.body("message",equalTo("Success"))
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
		
		
		
		
	}
	
	

}
