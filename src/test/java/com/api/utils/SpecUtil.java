package com.api.utils;

import static com.api.utils.ConfigManager.getProperty;

import java.io.IOException;

import org.hamcrest.Matchers;

import com.api.constant.Role;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {

	// for GET-DEL method
	public static RequestSpecification requestSpec() throws IOException {

		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).log(LogDetail.URI).log(LogDetail.METHOD)
				.log(LogDetail.HEADERS).log(LogDetail.BODY).build();
		return requestSpecification;

	}

	// for POST-PUT-PATCH(BODY) method

	public static RequestSpecification requestSpec(Object payload) throws IOException {

		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).setBody(payload).log(LogDetail.URI)
				.log(LogDetail.METHOD).log(LogDetail.HEADERS).log(LogDetail.BODY).build();
		return requestSpecification;

	}

	public static RequestSpecification requestSpecWithAuth(Role role) throws IOException {

		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role)).log(LogDetail.URI).log(LogDetail.METHOD)
				.log(LogDetail.HEADERS).log(LogDetail.BODY).build();
		return requestSpecification;

	}

	public static RequestSpecification requestSpecWithAuth(Role role, Object payload) throws IOException {

		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role)).setBody(payload).log(LogDetail.URI)
				.log(LogDetail.METHOD).log(LogDetail.HEADERS).log(LogDetail.BODY).build();
		return requestSpecification;

	}

	public static ResponseSpecification responseSpec_OK() {
		System.out.println("before");
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(200).expectResponseTime(Matchers.lessThan(2000L)).log(LogDetail.ALL).build();

		System.out.println("after");
		return responseSpecification;

	}

	public static ResponseSpecification responseSpec_JSON(int statusCode) {

		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(statusCode).expectResponseTime(Matchers.lessThan(1000L)).log(LogDetail.ALL).build();
		return responseSpecification;

	}

	public static ResponseSpecification responseSpec_TEXT(int statusCode) {

		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(statusCode)
				.expectResponseTime(Matchers.lessThan(1000L)).log(LogDetail.ALL).build();
		return responseSpecification;

	}

}
