package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.request.model.Search;
import com.api.services.JobService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
@Listeners(com.listeners.APITestListener.class)
@Epic("Job Management")
@Feature("Search Job")
public class SearchAPITest {
	private JobService jobService;
	private static final String JOB_Number = "JOB_139717";
	private Search searchPayload;

	@BeforeMethod(description = "Instanciating the Job Service and creating search payload")

	public void setup() {
		jobService = new JobService();
		searchPayload = new Search(JOB_Number);

	}
	@Story("FD should be able to search Job")
	@Description("FD should be able to search Job")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "Verify if search api is correct response", groups = { "api", "regression", "smoke" })
	public void verifySearchAPIResponse() throws IOException {
		jobService.searchJob(FD, searchPayload)

				.then().spec(responseSpec_OK()).body("message", equalTo("Success"));
	}
}
