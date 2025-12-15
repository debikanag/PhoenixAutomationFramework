package com.dataproviders;

import java.util.Iterator;

import org.testng.annotations.DataProvider;

import com.api.utils.CSVReaderUtil;
import com.dataproviders.api.bean.UserBean;



public class DataProviderUtils {

	
	
	@DataProvider(name = "LoginAPIDataProvider",parallel=true)
	public static Iterator<UserBean> LoginAPIDataProvider()
	{
		//Data Provider needs to return 3 way -
		//single dimension array[]/two dim array[]/iterator<>
				
	return CSVReaderUtil.loadCSV("testData/loginCreds.csv");
	
	}
}
