package com.api.tests;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.response.model.CreateJobResponseModel;
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.dao.JobHeadDao;
import com.database.dao.MapJobProblemDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.JobHeadModel;
import com.database.model.MapJobProblemModel;

public class CreateJobAPIwithFakeData {

	private CreateJobPayload createJobPayload;

	@BeforeMethod(description = "Create the request payload for create job api")
	public void setup() {

		createJobPayload = FakerDataGenerator.generateFakeCreateJobData();
		System.out.println(createJobPayload);
		System.out.println("------------------------");
		System.out.println(createJobPayload.mst_service_location_id());
		System.out.println("------------------------");
	}

	@Test(description = "Verify if create job api is able to create Inwarranty job", groups = { "api", "regression",
			"smoke" })

	public void CreateJobAPITest() {

		CustomerDBModel actualCustomerDataInDB = null;
		CustomerAddressDBModel actualCustomerAddressInDB = null;
		CustomerProductDBModel actualCustomerProductInDB = null;
		JobHeadModel actualJobDataInDB = null;
		MapJobProblemModel actualProblemInDB = null;

		try {
			CreateJobResponseModel createJobResponseModel = given().spec(requestSpecWithAuth(Role.FD, createJobPayload))

					.when().post("/job/create").then().spec(responseSpec_OK())
					.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
					.body("message", equalTo("Job created successfully. "))
					.body("data.mst_service_location_id", equalTo(1)).body("data.job_number", startsWith("JOB_"))
					.extract().as(CreateJobResponseModel.class);

			int customerId = createJobResponseModel.getData().getTr_customer_id();
			int customerProductId = createJobResponseModel.getData().getTr_customer_product_id();
			int jobHeadId = createJobResponseModel.getData().getId();

			actualCustomerDataInDB = CustomerDao.getCustomerInformation(customerId);
			actualCustomerAddressInDB = CustomerAddressDao
					.getCustomerAddress(actualCustomerDataInDB.getTr_customer_address_id());

			actualCustomerProductInDB = CustomerProductDao.getCustomerProduct(customerProductId);

			actualJobDataInDB = JobHeadDao.getDataFromJobhead(customerId);
			actualProblemInDB = MapJobProblemDao.getProblemDetails(jobHeadId);

			Customer expectedCustomerData = createJobPayload.customer();

			CustomerAddress expectedCustomerAddress = createJobPayload.customer_address();

			CustomerProduct expectedCustomerProduct = createJobPayload.customer_product();

			Assert.assertEquals(expectedCustomerData.first_name(), actualCustomerDataInDB.getFirst_name());
			Assert.assertEquals(expectedCustomerData.last_name(), actualCustomerDataInDB.getLast_name());
			Assert.assertEquals(expectedCustomerData.mobile_number(), actualCustomerDataInDB.getMobile_number());
			Assert.assertEquals(expectedCustomerData.mobile_number_alt(),
					actualCustomerDataInDB.getMobile_number_alt());
			Assert.assertEquals(expectedCustomerData.email_id(), actualCustomerDataInDB.getEmail_id());
			Assert.assertEquals(expectedCustomerData.email_id_alt(), actualCustomerDataInDB.getEmail_id_alt());

			System.out.println("------------------------");
			System.out.println(expectedCustomerAddress);

			System.out.println("------------------------");
			System.out.println(actualCustomerAddressInDB);

			Assert.assertEquals(expectedCustomerAddress.flat_number(), actualCustomerAddressInDB.getFlat_number());
			Assert.assertEquals(expectedCustomerAddress.apartment_name(),
					actualCustomerAddressInDB.getApartment_name());
			Assert.assertEquals(expectedCustomerAddress.street_name(), actualCustomerAddressInDB.getStreet_name());
			Assert.assertEquals(expectedCustomerAddress.landmark(), actualCustomerAddressInDB.getLandmark());
			Assert.assertEquals(expectedCustomerAddress.area(), actualCustomerAddressInDB.getArea());
			Assert.assertEquals(expectedCustomerAddress.pincode(), actualCustomerAddressInDB.getPincode());
			Assert.assertEquals(expectedCustomerAddress.country(), actualCustomerAddressInDB.getCountry());
			Assert.assertEquals(expectedCustomerAddress.state(), actualCustomerAddressInDB.getState());

			System.out.println("------------------------");
			System.out.println(expectedCustomerProduct);

			System.out.println("------------------------");
			System.out.println(actualCustomerProductInDB);

			Assert.assertEquals(expectedCustomerProduct.mst_model_id(), actualCustomerProductInDB.getMst_model_id());
			// Assert.assertEquals(expectedCustomerProduct.dop(),
			// actualCustomerProductInDB.getDop());
			Assert.assertEquals(expectedCustomerProduct.serial_number(), actualCustomerProductInDB.getSerial_number());
			Assert.assertEquals(expectedCustomerProduct.imei1(), actualCustomerProductInDB.getImei1());
			Assert.assertEquals(expectedCustomerProduct.imei2(), actualCustomerProductInDB.getImei2());
			Assert.assertEquals(expectedCustomerProduct.popurl(), actualCustomerProductInDB.getPopurl());

			
			
			
			System.out.println("------------------------");
			System.out.println(actualJobDataInDB.getMst_service_location_id());
			System.out.println("------------------------");
			
			System.out.println(createJobPayload.mst_warrenty_status_id());
			System.out.println(createJobPayload.mst_oem_id());
			System.out.println(createJobPayload.mst_service_location_id());
			System.out.println(createJobPayload.mst_platform_id());
			System.out.println("------------------------");
			
			
			Assert.assertEquals(createJobPayload.mst_warrenty_status_id(),
					actualJobDataInDB.getMst_warrenty_status_id());
			Assert.assertEquals(createJobPayload.mst_oem_id(), actualJobDataInDB.getMst_oem_id());
			Assert.assertEquals(createJobPayload.mst_service_location_id(),actualJobDataInDB.getMst_service_location_id());
			Assert.assertEquals(createJobPayload.mst_platform_id(), actualJobDataInDB.getMst_platform_id());

			Assert.assertEquals(createJobPayload.problems().get(1).id(), actualProblemInDB.getMst_problem_id());
			Assert.assertEquals(createJobPayload.problems().get(1).remark(), actualProblemInDB.getRemark());

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

	}

}
