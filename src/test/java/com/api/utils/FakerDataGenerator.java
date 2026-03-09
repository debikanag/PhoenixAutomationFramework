package com.api.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

import io.qameta.allure.Step;

public class FakerDataGenerator {

	private static Faker faker = new Faker(new Locale("en-IND"));// India specific data
	private static final String COUNTRY = "India";
	private static final Random RANDOM = new Random();
	private static final int MST_SERVICE_LOCATION_ID = 1;
	private static final int MST_PLATFORM_ID = 2;
	private static final int MST_WARRANTY_STATUS_ID = 1;
	private static final int MST_OEM_ID = 1;
	private static final int PRODUCT_ID = 1;

	private static final int VALIDPROBLEMSID[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 16, 17, 19, 20, 22, 24,
			26, 27, 28, 29 };

	private static final Logger LOGGER = LogManager.getLogger(FakerDataGenerator.class);

	private FakerDataGenerator() {

	}
	@Step("Generating fake create job data" )
	public static CreateJobPayload generateFakeCreateJobData() {
		LOGGER.info("Generating the fake payload for create job ");

		Customer customer = generateFakeCustomerData();
		CustomerAddress customerAddress = generateFakeCustomerAddressData();
		CustomerProduct customerProduct = generateFakeCustomerProduct();
		List<Problems> problemList = generateFakeProblemList();
		CreateJobPayload payload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID,
				MST_WARRANTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemList);
		return payload;

	}
	@Step("Generating multiple create fake create job data with the count" )
	public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {
		LOGGER.info("Generating the fake {} payloads for create job ", count);

		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		for (int i = 1; i <= count; i++) {

			Customer customer = generateFakeCustomerData();
			CustomerAddress customerAddress = generateFakeCustomerAddressData();
			CustomerProduct customerProduct = generateFakeCustomerProduct();
			List<Problems> problemList = generateFakeProblemList();
			CreateJobPayload payload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID,
					MST_WARRANTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemList);
			payloadList.add(payload);

		}
		return payloadList.iterator();
	}

	
	@Step("Generating fake Problem List for the create job payload " )
	private static List<Problems> generateFakeProblemList() {

		int count = RANDOM.nextInt(3) + 1;
		String fakeRemark;
		Problems problems;
		List<Problems> problemList = new ArrayList<Problems>();
		for (int i = 1; i <= count; i++) {

//generating random problem id and added to the list
			int randomIndex = RANDOM.nextInt(VALIDPROBLEMSID.length);
			fakeRemark = faker.lorem().sentence(1);
			problems = new Problems(VALIDPROBLEMSID[randomIndex], fakeRemark);

			problemList.add(problems);
		}
		return problemList;
	}

	
	@Step("Generating fake Customer Product  for the create job payload " )
	private static CustomerProduct generateFakeCustomerProduct() {

		String dop = DateTimeUtil.getTimeWithDaysAgo(10);
		String imeiSerialNumber = faker.numerify(("###############"));
		String popUrl = faker.internet().url();

		CustomerProduct customerProduct = new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber,
				popUrl, PRODUCT_ID, PRODUCT_ID);
		return customerProduct;
	}
	@Step("Generating fake Customer Address data for the create job payload " )
	private static CustomerAddress generateFakeCustomerAddressData() {

		String flatNumber = faker.numerify(("####"));
		String apartmentName = faker.address().streetName();
		String streetName = faker.address().streetName();
		String landmark = faker.address().streetName();
		String area = faker.address().streetName();
		String pinCode = faker.numerify(("#####"));

		String state = faker.address().state();

		CustomerAddress customerAddress = new CustomerAddress(flatNumber, apartmentName, streetName, landmark, area,
				pinCode, COUNTRY, state);

		return customerAddress;
	}
	@Step("Generating fake Customer data for the create job payload " )
	private static Customer generateFakeCustomerData() {

		String fname = faker.name().firstName();
		String lname = faker.name().lastName();
		String mobileNumber = faker.numerify(("70########"));
		String alternateMobileNumber = faker.numerify(("70########"));
		String customerEmailAddress = faker.internet().emailAddress();

		Customer customer = new Customer(fname, lname, mobileNumber, alternateMobileNumber, customerEmailAddress, "");

		return customer;
	}

}
