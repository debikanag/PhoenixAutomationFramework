package com.api.utils;

import static com.api.utils.ConfigManager.getProperty;

import java.io.IOException;

import org.hamcrest.Matchers;

import com.api.constant.Role;
import com.api.filters.SensitiveDataFilter;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {

	// for GET-DEL method
	public static RequestSpecification requestSpec() throws IOException {

		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).addFilter(new SensitiveDataFilter())
				.build();
		return requestSpecification;

	}

	// for POST-PUT-PATCH(BODY) method

	public static RequestSpecification requestSpec(Object payload) throws IOException {

		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).setBody(payload)
				.addFilter(new SensitiveDataFilter()).build();
		return requestSpecification;

	}

	public static RequestSpecification requestSpecWithAuth(Role role) throws IOException {

		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role)).addFilter(new SensitiveDataFilter())
				.build();
		return requestSpecification;

	}

	public static RequestSpecification requestSpecWithAuth(Role role, Object payload) throws IOException {

		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role)).setBody(payload)
				.addFilter(new SensitiveDataFilter()).build();
		return requestSpecification;

	}

	public static ResponseSpecification responseSpec_OK() {

		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(200).expectResponseTime(Matchers.lessThan(2000L)).build();

		return responseSpecification;

	}

	public static ResponseSpecification responseSpec_JSON(int statusCode) {

		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(statusCode).expectResponseTime(Matchers.lessThan(1000L)).build();
		return responseSpecification;

	}

	public static ResponseSpecification responseSpec_TEXT(int statusCode) {

		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(statusCode)
				.expectResponseTime(Matchers.lessThan(1000L)).build();
		return responseSpecification;

	}

}
