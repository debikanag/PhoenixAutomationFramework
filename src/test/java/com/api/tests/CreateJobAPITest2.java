package com.api.tests;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.DateTimeUtil;
import com.github.javafaker.Faker;

public class CreateJobAPITest2 {

	private CreateJobPayload createJobPayload;
	private static final String COUNTRY = "India";

	@BeforeMethod(description = "Create the request payload for create job api")
	public void setup() {
		Faker faker = new Faker(new Locale("en-IND"));// India specific data

		String fname = faker.name().firstName();
		String lname = faker.name().lastName();
		String mobileNumber = faker.numerify(("70########"));
		String alternateMobileNumber = faker.numerify(("70########"));
		String customerEmailAddress = faker.internet().emailAddress();

		Customer customer = new Customer(fname, lname, mobileNumber, alternateMobileNumber, customerEmailAddress, "");
		System.out.println(customer);

		// customer address fake object creation
		String flatNumber = faker.numerify(("####"));
		String apartmentName = faker.address().streetName();
		String streetName = faker.address().streetName();
		String landmark = faker.address().streetName();
		String area = faker.address().streetName();
		String pinCode = faker.numerify(("#####"));

		String state = faker.address().state();

		CustomerAddress customerAddress = new CustomerAddress(flatNumber, apartmentName, streetName, landmark, area,
				pinCode, COUNTRY, state);

		System.out.println(customerAddress);

		// customer product fake object creation

		String dop = DateTimeUtil.getTimeWithDaysAgo(10);
		String imeiSerialNumber = faker.numerify(("###############"));
		String popUrl = faker.internet().url();

		CustomerProduct customerProduct = new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber,
				popUrl, 1, 1);

		System.out.println(customerProduct);

		// Problems

		// want to generate random number between 1-27
		Random random = new Random();
		int problemId = random.nextInt(27) + 1;

		String fakeRemark = faker.lorem().sentence(3);
		Problems problems = new Problems(problemId, fakeRemark);
		System.out.println(problems);

		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);

		createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemList);
		System.out.println(createJobPayload);
	}

	@Test(description = "Verify if create job api is able to create Inwarranty job", groups = { "api", "regression",
			"smoke" })

	public void CreateJobAPITest() throws IOException {

		given().spec(requestSpecWithAuth(Role.FD, createJobPayload))

				.when().post("/job/create").then().spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));

	}

}
