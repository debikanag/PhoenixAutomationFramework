package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.dataproviders.api.bean.UserBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVReaderUtil {

	// constructor is private
	// static methods
	// Job: help me to read the CSV file and map it to bean

	private CSVReaderUtil() {
		// No one can create object of CSVReaderUtil outside the class
		// Singleton class constructors are private
	}

	public static void loadCSV(String pathOfCSVFile) {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
		InputStreamReader isr = new InputStreamReader(is);

		CSVReader csvReader = new CSVReader(isr);

		// write the code to map the CSV to POJO

		CsvToBean<UserBean> csvToBean = new CsvToBeanBuilder(csvReader)
				.withType(UserBean.class)
				.withIgnoreEmptyLine(true)
				.build();

		List<UserBean> userList = csvToBean.parse();
		System.out.println(userList);

	}
}
