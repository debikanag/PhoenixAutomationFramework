package com.api.tests;


import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


import java.io.IOException;


import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;


public class MasterAPITest {

	@Test(description = "Verify if master api is correct response",groups = {"api","regression","smoke"})
	public void MasterAPITest() throws IOException
	{
		given()
		.spec(requestSpecWithAuth(FD))
		.when()
		.post("master")
		.then()
		.spec(responseSpec_OK())
		.body("message",equalTo("Success"))
		.body("data",notNullValue())
		.body("data", hasKey("mst_oem"))
		.body("data",hasKey("mst_model"))
		.body("$", hasKey("message"))
		.body("$", hasKey("data"))
		.body("data.mst_oem.size()",greaterThan(0))
		.body("data.mst_oem.size()",equalTo(2))
		.body("data.mst_model.size()",greaterThan(0))
		.body("data.mst_oem.id", everyItem(notNullValue()))
		.body("data.mst_oem.name",everyItem(notNullValue()))
		.time(lessThan(1000L))
		.body(matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));
		
	}
	@Test(description = "Verify if master api is correct status code for invalid token",groups = {"api","regression","smoke","negative"})
	public void invalidTokenMasterAPITest() throws IOException
	{
		given()
		.spec(requestSpec())
		.when()
		.post("master")
		.then()
		.spec(responseSpec_TEXT(401));
	}
	
	
}
