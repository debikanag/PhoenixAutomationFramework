package com.api.tests;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.DateTimeUtil;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.dao.MapJobProblemDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.MapJobProblemModel;

import io.restassured.response.Response;

public class CreateJobAPIDBValidationTest {

	private CreateJobPayload createJobPayload;
	private CustomerAddress customerAddress;
	private Customer customer;
	private CustomerProduct customerProduct;

	@BeforeMethod(description = "Create the request payload for create job api")
	public void setup() {
		customer = new Customer("Debika", "Nag", "7434565434", "", "debika0989@gmail.com", "");
		customerAddress = new CustomerAddress("912", "Thames", "Napier", "Station", "Reading", "700129", "Berkshire",
				"UK");

		customerProduct = new CustomerProduct(DateTimeUtil.getTimeWithDaysAgo(10), "77056591958888", "77056591958888",
				"77056591958888", DateTimeUtil.getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(),
				Model.NEXUS_2_BLUE.getCode());

		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");

		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);

		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, problemList);
	}

	@Test(description = "Verify if create job api is able to create Inwarranty job", groups = { "api", "regression",
			"smoke" })

	public void CreateJobAPITest() {

		try {
			Response response = given().spec(requestSpecWithAuth(Role.FD, createJobPayload))

					.when().post("/job/create").then().spec(responseSpec_OK())
					.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
					.body("message", equalTo("Job created successfully. "))
					.body("data.mst_service_location_id", equalTo(1)).body("data.job_number", startsWith("JOB_"))
					.extract().response();

			System.out.println("------------------------");

			int customerId = response.then().extract().jsonPath().getInt("data.tr_customer_id");

			System.out.println(customerId);

			CustomerDBModel customerDataFromDB = CustomerDao.getCustomerInformation(customerId);
			System.out.println(customerDataFromDB);

			Assert.assertEquals(customer.first_name(), customerDataFromDB.getFirst_name());
			Assert.assertEquals(customer.last_name(), customerDataFromDB.getLast_name());
			Assert.assertEquals(customer.mobile_number(), customerDataFromDB.getMobile_number());
			Assert.assertEquals(customer.mobile_number_alt(), customerDataFromDB.getMobile_number_alt());
			Assert.assertEquals(customer.email_id(), customerDataFromDB.getEmail_id());
			Assert.assertEquals(customer.email_id_alt(), customerDataFromDB.getEmail_id_alt());
			System.out.println("------------------------");

			

			CustomerAddressDBModel customerAddressFromDB = CustomerAddressDao
					.getCustomerAddress(customerDataFromDB.getTr_customer_address_id());
			System.out.println(customerAddressFromDB);

			Assert.assertEquals(customerAddress.flat_number(), customerAddressFromDB.getFlat_number());
			Assert.assertEquals(customerAddress.apartment_name(), customerAddressFromDB.getApartment_name());
			Assert.assertEquals(customerAddress.street_name(), customerAddressFromDB.getStreet_name());
			Assert.assertEquals(customerAddress.landmark(), customerAddressFromDB.getLandmark());
			Assert.assertEquals(customerAddress.area(), customerAddressFromDB.getArea());
			Assert.assertEquals(customerAddress.pincode(), customerAddressFromDB.getPincode());
			Assert.assertEquals(customerAddress.country(), customerAddressFromDB.getCountry());
			Assert.assertEquals(customerAddress.state(), customerAddressFromDB.getState());
			System.out.println("------------------------");

			int customerProductId = response.then().extract().jsonPath().getInt("data.tr_customer_product_id");
			System.out.println(customerProductId);

			CustomerProductDBModel customerProductFromDB = CustomerProductDao.getCustomerProduct(customerProductId);
			System.out.println(customerProductFromDB);

			Assert.assertEquals(customerProduct.mst_model_id(), customerProductFromDB.getMst_model_id());
			// Assert.assertEquals(customerProduct.dop(), customerProductFromDB.getDop());
			Assert.assertEquals(customerProduct.serial_number(), customerProductFromDB.getSerial_number());
			Assert.assertEquals(customerProduct.imei1(), customerProductFromDB.getImei1());
			Assert.assertEquals(customerProduct.imei2(), customerProductFromDB.getImei2());
			Assert.assertEquals(customerProduct.popurl(), customerProductFromDB.getPopurl());

			int tr_job_head_id = response.then().extract().jsonPath().getInt("data.id");
			System.out.println(tr_job_head_id);

			MapJobProblemModel problemFromDB = MapJobProblemDao.getProblemDetails(tr_job_head_id);
			System.out.println(problemFromDB);

			Assert.assertEquals(createJobPayload.problems().get(0).id(), problemFromDB.getMst_problem_id());
			Assert.assertEquals(createJobPayload.problems().get(0).remark(), problemFromDB.getRemark());

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

	}
}
