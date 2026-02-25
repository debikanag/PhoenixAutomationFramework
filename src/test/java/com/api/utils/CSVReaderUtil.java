package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVReaderUtil {

	// constructor is private
	// static methods
	// Job: help me to read the CSV file and map it to bean

	private static final Logger LOGGER = LogManager.getLogger(CSVReaderUtil.class);

	private CSVReaderUtil() {
		// No one can create object of CSVReaderUtil outside the class
		// Singleton class constructors are private
	}

	public static <T> Iterator<T> loadCSV(String pathOfCSVFile, Class<T> bean) {
		LOGGER.info("Loading the CSV file from the path {}", pathOfCSVFile);
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
		InputStreamReader isr = new InputStreamReader(is);

		CSVReader csvReader = new CSVReader(isr);

		// write the code to map the CSV to POJO

		LOGGER.info("Converting the CSV to the Bean class {}", bean);
		CsvToBean<T> csvToBean = new CsvToBeanBuilder(csvReader).withType(bean).withIgnoreEmptyLine(true).build();

		List<T> list = csvToBean.parse();
		return list.iterator();

	}
}
