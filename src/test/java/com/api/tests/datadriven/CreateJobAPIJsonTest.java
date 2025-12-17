package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;

public class CreateJobAPIJsonTest {



	@Test(description = "Verify if create job api is able to create Inwarranty job", groups = { "api", "regression",
			"datadriven","json" }, dataProviderClass = com.dataproviders.DataProviderUtils.class, dataProvider = "CreateJobAPIJsonDataProvider")

	public void CreateJobAPITest(CreateJobPayload createJobPayload) throws IOException {

		given().spec(requestSpecWithAuth(Role.FD, createJobPayload))

				.when().post("/job/create").then().spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));

	}

}
