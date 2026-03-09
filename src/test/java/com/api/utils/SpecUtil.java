package com.api.utils;

import static com.api.utils.ConfigManager.getProperty;

import java.io.IOException;

import org.hamcrest.Matchers;

import com.api.constant.Role;
import com.api.filters.SensitiveDataFilter;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {

	// for GET-DEL method
	@Step("Setting up the BASE_URI,ContentType as Application/JSON and attaching the sensitive data filter")
	public static RequestSpecification requestSpec() throws IOException {

		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).addFilter(new SensitiveDataFilter())

				.addFilter(new AllureRestAssured()).build();
		return requestSpecification;

	}

	// for POST-PUT-PATCH(BODY) method

	@Step("Setting up the BASE_URI,ContentType as Application/JSON and attaching the sensitive data filter")
	public static RequestSpecification requestSpec(Object payload) throws IOException {

		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).setBody(payload)
				.addFilter(new SensitiveDataFilter()).addFilter(new AllureRestAssured()).build();
		return requestSpecification;

	}

	@Step("Setting up the BASE_URI,ContentType as Application/JSON and attaching the sensitive data filter for a role")
	public static RequestSpecification requestSpecWithAuth(Role role) throws IOException {

		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role)).addFilter(new SensitiveDataFilter())

				.addFilter(new AllureRestAssured())

				.build();
		return requestSpecification;

	}

	@Step("Setting up the BASE_URI,ContentType as Application/JSON and attaching the sensitive data filter for a role and attaching payload")

	public static RequestSpecification requestSpecWithAuth(Role role, Object payload) throws IOException {

		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role)).setBody(payload)
				.addFilter(new SensitiveDataFilter()).addFilter(new AllureRestAssured()).build();
		return requestSpecification;

	}

	@Step("Expecting the response to have Content Type as Application/JSON, status 200 and Response Time Less than 2000 ms  ")
	public static ResponseSpecification responseSpec_OK() {

		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(200).expectResponseTime(Matchers.lessThan(2000L))

				.build();

		return responseSpecification;

	}

	@Step("Expecting the response to have Content Type as Application/JSON and Response Time Less than 1000 ms and status code  ")
	public static ResponseSpecification responseSpec_JSON(int statusCode) {

		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(statusCode).expectResponseTime(Matchers.lessThan(1000L)).build();
		return responseSpecification;

	}

	@Step("Expecting the response to have Content Type as Text, Response Time Less than 1000 ms and status code ")
	public static ResponseSpecification responseSpec_TEXT(int statusCode) {

		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(statusCode)
				.expectResponseTime(Matchers.lessThan(1000L)).build();
		return responseSpecification;

	}

}
