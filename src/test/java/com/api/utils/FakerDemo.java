package com.api.utils;

import java.util.Locale;

import com.github.javafaker.Faker;

public class FakerDemo {

	public static void main(String[] args) {
		// we will be using faker library for our test data creation
		// creating new fakerUtil to use faker library
		Faker faker = new Faker(new Locale("en-IND"));// use fluent style of usage

		String firstName = faker.name().firstName();
		String lastName=faker.name().lastName();
		String buildingNumber=faker.address().buildingNumber();
		String streetAddress=faker.address().streetAddress();
		
		
		
		
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(buildingNumber);
		System.out.println(streetAddress);
		System.out.println(faker.address().city());
		System.out.println(faker.number().digits(2));
		System.out.println(faker.numerify("704#######"));
		System.out.println(faker.internet().emailAddress());
	}

}
