package com.api.tests;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


import java.io.IOException;


import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CountAPITest {
	@Test(description = "Verify if count api is correct response",groups = {"api","regression","smoke"})
	public void verifyCountAPIResponse() throws IOException
	{
		
	given()
	.spec(requestSpecWithAuth(FD))
	.when()
	.get("/dashboard/count")
	.then()
	.spec(responseSpec_OK())
	.body("message",equalTo("Success"))
	.body("data",notNullValue())
	.body("data.size()",equalTo(3))
	.body("data.count",everyItem(greaterThanOrEqualTo(0)))
	.body("data.label",everyItem(not(blankOrNullString())))
	.body("data.key",containsInAnyOrder("pending_for_delivery","pending_fst_assignment","created_today"))
	.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"));
	}
	
	
	@Test(description = "Verify if count api is correct status code for invalid token",groups = {"api","regression","smoke","negative"})
	public void countAPITest_MissingAuthToken() throws IOException
	{
		given()
		.spec(requestSpec())
		.when()
		.get("/dashboard/count")
		.then()
		.spec(responseSpec_TEXT(401));
	}

}
