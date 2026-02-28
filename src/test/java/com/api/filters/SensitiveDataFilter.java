package com.api.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilter implements Filter {

	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {

		System.out.println("-------------------------Hello from the filter-----------------------------");

		Response response = ctx.next(requestSpec, responseSpec);

		System.out.println("-------------------------got the response from filter-----------------------------");

		return response;
	}

}
