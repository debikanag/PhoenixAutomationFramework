package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.request.model.Detail;
import com.api.services.DashboardService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners(com.listeners.APITestListener.class)
@Epic("Job Management")
@Feature("Job Details")
public class DetailsAPITest {
	private DashboardService dashboardService;
	private Detail detailPayload;

	@BeforeMethod(description = "Instanciating the Dashboard Service and creating detail payload")

	public void setup() {
		dashboardService = new DashboardService();
		detailPayload = new Detail("created_today");

	}

	@Story("Job Details is shown correctly for FD")
	@Description("Job Details is shown correctly for FD")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Verify if details api is correct response", groups = { "api", "regression", "smoke" })
	public void verifyDetailAPIResponse() throws IOException {
		dashboardService.details(FD, detailPayload)

				.then().spec(responseSpec_OK()).body("message", equalTo("Success")).body("data", notNullValue());
	}
}
