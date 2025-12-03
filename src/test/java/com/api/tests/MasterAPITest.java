package com.api.tests;

import static com.api.utils.ConfigManager.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constant.Role.*;
import static com.api.utils.AuthTokenProvider.*;

public class MasterAPITest {

	@Test
	public void MasterAPITest() throws IOException
	{
		given()
		.baseUri(getProperty("BASE_URI"))
		.and()
		.header("Authorization",getToken(FD))
		.and()
		.contentType("")//for post method default content type application/url-formencoded
		.log().uri()
		.log().method()
		.log().headers()
		.when()
		.post("master")
		.then()
		.log().all()
		.statusCode(200)
		.body("message",equalTo("Success"))
		.body("data",Matchers.notNullValue())
		.body("data", Matchers.hasKey("mst_oem"))
		.body("data",Matchers.hasKey("mst_model"))
		.body("$", Matchers.hasKey("message"))
		.body("$", Matchers.hasKey("data"))
		.body("data.mst_oem.size()",Matchers.greaterThan(0))
		.body("data.mst_oem.size()",Matchers.equalTo(2))
		.body("data.mst_model.size()",Matchers.greaterThan(0))
		.body("data.mst_oem.id", Matchers.everyItem(Matchers.notNullValue()))
		.body("data.mst_oem.name", Matchers.everyItem(Matchers.notNullValue()))
		.time(lessThan(1000L))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));
		
	}
	@Test
	public void invalidTokenMasterAPITest() throws IOException
	{
		given()
		.baseUri(getProperty("BASE_URI"))
		.and()
		.header("Authorization","")
		.and()
		.contentType("")//for post method default content type application/url-formencoded
		.log().uri()
		.log().method()
		.log().headers()
		.when()
		.post("master")
		.then()
		.log().all()
		.statusCode(401);
	}
	
	
}
