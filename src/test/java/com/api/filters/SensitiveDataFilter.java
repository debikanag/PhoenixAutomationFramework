package com.api.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(SensitiveDataFilter.class);

	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {

		System.out.println("-------------------------Hello from the filter-----------------------------");
		redactPayload(requestSpec);

		Response response = ctx.next(requestSpec, responseSpec);// making the request

		redactResponseBody(response);
		System.out.println("-------------------------got the response from filter-----------------------------");

		return response;
	}

	// create a method which is going to redact/hide the password from the request
	// payload

	private void redactResponseBody(Response response) {

		String responseBody = response.asPrettyString();

		responseBody = responseBody.replaceAll("\"token\"\s*:\s*\"[^\"]+\"", "\"token\":\"[REDACTED]\"");
		LOGGER.info("RESPONSE BODY {}", responseBody);

	}

	public void redactPayload(FilterableRequestSpecification requestSpec) {
		String requestPayload = requestSpec.getBody().toString();// print the request body in String format

		requestPayload = requestPayload.replaceAll("\"password\"\s*:\s*\"[^\"]+\"", "\"password\":\"[REDACTED]\"");
		LOGGER.info("REQUEST PAYLOAD {}", requestPayload);
	}

}
