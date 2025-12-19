package com.api.tests;


import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static com.api.utils.SpecUtil.responseSpec_TEXT;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.Test;


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
