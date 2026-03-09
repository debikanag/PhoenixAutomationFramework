package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.poiji.bind.Poiji;

import io.qameta.allure.Step;

public class ExcelReaderUtil {
	private static final Logger LOGGER = LogManager.getLogger(ExcelReaderUtil.class);

	private ExcelReaderUtil() {

	}

	
	
	@Step("Loading test data from the excel file " )
	public static <T> Iterator<T> loadTestData(String xlsxFile, String sheetName, Class<T> clazz) {
		LOGGER.info("Reading the test data from .xlsx file {} and the sheet name is {} ", xlsxFile, sheetName);
		// APACHE POI OOXML
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("testData/PhoenixTestData.xlsx");
		XSSFWorkbook myWorkBook = null;
		try {
			myWorkBook = new XSSFWorkbook(is);
		} catch (IOException e) {

			LOGGER.error("Can not read the excel {}", xlsxFile, e);
			e.printStackTrace();
		}
		// focus on the sheet

		/*
		 * XSSFRow headerRows = mySheet.getRow(0);// return me the header row of the
		 * excel
		 * 
		 * // Read the excel file and stored in the ArrayList<UserCredentials>
		 * 
		 * int userNameIndex = -1; int passwordIndex = -1;
		 * 
		 * for (Cell cell : headerRows) { if
		 * (cell.getStringCellValue().trim().equalsIgnoreCase("username")) {
		 * userNameIndex = cell.getColumnIndex(); }
		 * 
		 * if (cell.getStringCellValue().trim().equalsIgnoreCase("password")) {
		 * passwordIndex = cell.getColumnIndex(); } }
		 * 
		 * System.out.println(userNameIndex + " " + passwordIndex);
		 * 
		 * int lastRowIndex = mySheet.getLastRowNum();
		 * 
		 * XSSFRow rowData;
		 * 
		 * UserCredentials userCredentials;
		 * 
		 * ArrayList<UserCredentials> userList = new ArrayList<UserCredentials>();
		 * 
		 * for (int rowIndex = 1; rowIndex <= lastRowIndex; rowIndex++) {
		 * 
		 * rowData = mySheet.getRow(rowIndex);
		 * 
		 * userCredentials = new
		 * UserCredentials(rowData.getCell(userNameIndex).toString(),
		 * rowData.getCell(passwordIndex).toString());
		 * 
		 * userList.add(userCredentials); }
		 * 
		 * return userList.iterator();
		 */

		XSSFSheet mySheet = myWorkBook.getSheet(sheetName);// "LoginTestData"
		LOGGER.info("Converting the XSSFSheet {} to POJO Class of type {} ", sheetName, clazz);

		List<T> dataList = Poiji.fromExcel(mySheet, clazz);
		return dataList.iterator();

	}

}
