package com.afklm.repind.msv.preferences.services.builder;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Component
public class DateHelper {
	
	/**
	 * Given a date, this method convert it to UTC format so when it's received by the WS it's the correct date.
	 * @param d (Date)
	 * @return a Date object
	 */
	public static Date convertDateToUTC(Date d) {
		
		// Create 2 calendars one with the UTC time zone and the other with the default time zone
		Calendar calendarUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		Calendar calendar = Calendar.getInstance();
		
		// Set the time of the two calendars with the date given in parameter
		calendarUTC.setTime(d);
		calendar.setTime(d);
		
		// UTC date information
		int yearUTC = calendarUTC.get(Calendar.YEAR);
		int monthUTC = calendarUTC.get(Calendar.MONTH);
		int dayUTC = calendarUTC.get(Calendar.DAY_OF_MONTH);
		
		// Default date information
		int defaultYear = calendar.get(Calendar.YEAR);
		int defaultMonth = calendar.get(Calendar.MONTH);
		int defaultDay = calendar.get(Calendar.DAY_OF_MONTH);
		
		// Do all the checks to be sure that the UTC date is correct
		if (dayUTC != defaultDay) {
			calendarUTC.set(Calendar.DAY_OF_MONTH, defaultDay);
		}
		if (monthUTC != defaultMonth) {
			calendarUTC.set(Calendar.MONTH, defaultMonth);
			calendarUTC.set(Calendar.DAY_OF_MONTH, defaultDay);
		}
		if (yearUTC != defaultYear) {
			calendarUTC.set(Calendar.YEAR, defaultYear);
			calendarUTC.set(Calendar.MONTH, defaultMonth);
			calendarUTC.set(Calendar.DAY_OF_MONTH, defaultDay);
		}
		
		// Set the hour/minute/second to 0
		calendarUTC.set(Calendar.HOUR_OF_DAY, 0);
		calendarUTC.set(Calendar.MINUTE, 0);
		calendarUTC.set(Calendar.SECOND, 0);
		
		// Return the UTC date
		return calendarUTC.getTime();
		
	}

}
