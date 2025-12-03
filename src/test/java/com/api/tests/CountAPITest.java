package com.api.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.net.http.HttpResponse.BodyHandler;

import static org.hamcrest.Matchers.*;

import org.checkerframework.checker.index.qual.LessThan;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.utils.AuthTokenProvider;
import static com.api.utils.ConfigManager.*;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CountAPITest {
	@Test
	public void verifyCountAPIResponse() throws IOException
	{
		
	given()
	.baseUri(getProperty("BASE_URI"))
	.and()
	.header("Authorization",AuthTokenProvider.getToken(Role.FD))
	.log().uri()
	.log().method()
	.log().headers()
	.when()
	.get("/dashboard/count")
	.then()
	.log().all()
	.statusCode(200)
	.body("message",equalTo("Success"))
	.time(lessThan(1000L))
	.body("data",notNullValue())
	.body("data.size()",equalTo(3))
	.body("data.count",everyItem(greaterThanOrEqualTo(0)))
	.body("data.label",Matchers.everyItem(Matchers.not(Matchers.blankOrNullString())))
	.body("data.key",Matchers.containsInAnyOrder("pending_for_delivery","pending_fst_assignment","created_today"))
	.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"));
	}
	@Test
	public void countAPITest_MissingAuthToken() throws IOException
	{
		given()
		.baseUri(getProperty("BASE_URI"))
		.and()
		.log().uri()
		.log().method()
		.log().headers()
		.when()
		.get("/dashboard/count")
		.then()
		.log().all()
		.statusCode(401);
	}

}
