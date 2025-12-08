package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPItest {

	@Test

	public void CreateJobAPITest() throws IOException {
		
		
		Customer customer = new Customer("Debika", "Nag","7434565434", "","debika0989@gmail.com","");
		CustomerAddress customerAddress = new CustomerAddress("912","Thames","Napier","Station","Reading","700129","Berkshire","UK");
		
		CustomerProduct customerProduct = new CustomerProduct("2025-04-06T18:30:00.000Z","66986541958887","66986541958887","66986541958887","2025-04-06T18:30:00.000Z",1,1);
		
		Problems problems = new Problems(1,"Battery Issue");
		
		
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemList);

		given()
		.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
				
				.when()
				.post("/job/create")
				.then()
				.spec(SpecUtil.responseSpec_OK())
		        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
		.body("message",equalTo("Job created successfully. "))
		.body("data.mst_service_location_id",equalTo(1))
		.body("data.job_number",startsWith("JOB_"));

	}

}
