package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static com.api.utils.SpecUtil.responseSpec_TEXT;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.services.DashboardService;

public class CountAPITest {

	private DashboardService dashboardService;

	@BeforeMethod(description = "Initializing the Dashboard Service")

	public void setup() {
		dashboardService = new DashboardService();
	}

	@Test(description = "Verify if count api is correct response", groups = { "api", "regression", "smoke" })
	public void verifyCountAPIResponse() throws IOException {
		dashboardService.count(FD)

				.then().spec(responseSpec_OK()).body("message", equalTo("Success")).body("data", notNullValue())
				.body("data.size()", equalTo(3)).body("data.count", everyItem(greaterThanOrEqualTo(0)))
				.body("data.label", everyItem(not(blankOrNullString())))
				.body("data.key", containsInAnyOrder("pending_for_delivery", "pending_fst_assignment", "created_today"))
				.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"));
	}

	@Test(description = "Verify if count api is correct status code for invalid token", groups = { "api", "regression",
			"smoke", "negative" })

	public void countAPITest_MissingAuthToken() throws IOException {
		dashboardService.countWithNoAuth().then().spec(responseSpec_TEXT(401));
	}

}
