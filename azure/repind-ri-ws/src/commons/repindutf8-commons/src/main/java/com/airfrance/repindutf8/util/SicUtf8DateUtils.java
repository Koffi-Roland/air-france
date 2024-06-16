package com.airfrance.repindutf8.util;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


public class SicUtf8DateUtils {

	private static Log LOGGER  = LogFactory.getLog(SicUtf8DateUtils.class);
	private static SimpleDateFormat MMddYYYYformatter = new SimpleDateFormat("yyyy-MM-dd");

	
	private SicUtf8DateUtils() {
		
	}
	
	
	
	/**
	 * @param date
	 * @return
	 */
	public static XMLGregorianCalendar dateTOxmlGregorianCalendar(Date date) {
		if(date == null) {
			return null;
		}
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(date);
		XMLGregorianCalendar xmlCalendar = null;
		try {
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
		} catch (DatatypeConfigurationException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(e);
			}
		}
		return xmlCalendar;
	}
	
	
	/**
	 * @param date
	 * @return
	 */
	public static String computeFrenchDate(Date date) {
		if(date == null) {
			return null;
		} else {
			XMLGregorianCalendar xDateTOxmlGregorianCalendar = dateTOxmlGregorianCalendar(date);
			
			// REPIND-1398 : Test SONAR NPE
			if (xDateTOxmlGregorianCalendar != null) {
				String value = "";
				value += computeInterger(xDateTOxmlGregorianCalendar.getDay());
				value += computeInterger(xDateTOxmlGregorianCalendar.getMonth());
				value += xDateTOxmlGregorianCalendar.getYear();
				return value;
			} else {
				return null;	
			}
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * Transform an integer on String with 2 digits or more.
	 * @param intOnOneCarac
	 * @return
	 */
	private static String computeInterger(int intOnOneCarac) {
		if(intOnOneCarac <= 9) {
			return "0"+intOnOneCarac;
		} else {
			return String.valueOf(intOnOneCarac);
		}
		
	}
	
	
}
