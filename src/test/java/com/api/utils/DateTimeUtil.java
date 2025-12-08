package com.api.utils;




import org.joda.time.LocalDate;

public class DateTimeUtil {

	
	private DateTimeUtil()
	{
	
	
	}
	
	public static String getTimeWithDaysAgo(int days)
	{
		
		return LocalDate.now().minusDays(days).toString();
	
	}
	
}
