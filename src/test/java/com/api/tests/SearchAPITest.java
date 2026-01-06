package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.Search;
import com.api.services.JobService;

public class SearchAPITest {
	private JobService jobService;
	private static final String JOB_Number = "JOB_139717";
	private Search searchPayload;

	@BeforeMethod(description = "Instanciating the Job Service and creating search payload")

	public void setup() {
		jobService = new JobService();
		searchPayload = new Search(JOB_Number);

	}

	@Test(description = "Verify if search api is correct response", groups = { "api", "regression", "smoke" })
	public void verifySearchAPIResponse() throws IOException {
		jobService.searchJob(FD, searchPayload)

				.then().spec(responseSpec_OK()).body("message", equalTo("Success"));
	}
}
