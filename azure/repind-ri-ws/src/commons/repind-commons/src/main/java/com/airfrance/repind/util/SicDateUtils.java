package com.airfrance.repind.util;

import com.airfrance.ref.exception.BadDateFormatException;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SicDateUtils {

	private static Log LOGGER  = LogFactory.getLog(SicDateUtils.class);
	private static SimpleDateFormat MMddYYYYformatter = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat ddMMYYYYformatter = new SimpleDateFormat("dd/MM/yyyy");

	// Specific return code of the compare method period overlap
	public static int PERIOD_INCLUDE = 0;
	public static int PERIOD_OVERLAP_INF = 1;
	public static int PERIOD_OVERLAP_SUP = 2;
	public static int PERIOD_RECOVER = 3;
	public static int PERIOD_LOWER = 4;
	public static int PERIOD_HIGHER = 5;

	public static final Date NO_EXPIRE = new Date( Long.MAX_VALUE );

	private SicDateUtils() {

	}

	/**
	 * @param calendar
	 * @return
	 */
	public static Date xmlGregorianCalendarTOdate(XMLGregorianCalendar calendar) {
		if(calendar == null) {
			return null;
		}
		return calendar.toGregorianCalendar().getTime();
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
			if(SicDateUtils.LOGGER.isErrorEnabled()) {
				SicDateUtils.LOGGER.error(e);
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
		}

		XMLGregorianCalendar gregorianCalendar = SicDateUtils.dateTOxmlGregorianCalendar(date);
		if (gregorianCalendar == null) {
			return null;
		}

		String value = "";

		value += SicDateUtils.computeInterger(gregorianCalendar.getDay());
		value += SicDateUtils.computeInterger(gregorianCalendar.getMonth());
		value += gregorianCalendar.getYear();

		return value;
	}


	/**
	 * @param date
	 * @return
	 */
	public static String computeHour(Date date) {
		if(date == null) {
			return null;
		}

		XMLGregorianCalendar gregorianCalendar = SicDateUtils.dateTOxmlGregorianCalendar(date);
		if (gregorianCalendar == null) {
			return null;
		}

		String value = "";

		value += SicDateUtils.computeInterger(gregorianCalendar.getHour());
		value += SicDateUtils.computeInterger(gregorianCalendar.getMinute());
		value += SicDateUtils.computeInterger(gregorianCalendar.getSecond());

		return value;
	}

	public static Date stringToDate(String sDate) throws BadDateFormatException, JrafApplicativeException {
		Date result = null;
		String dateToParse = sDate;
		if (dateToParse!=null) {
			try {
				if (SicStringUtils.isDDMMYYYFormatDate(sDate)) {
					dateToParse = SicStringUtils.formatDateToWsdl(sDate);
				}
				// REPIND-260 : On a une date vide sans etre null
				if (!"".equals(sDate)) {
					result = SicDateUtils.MMddYYYYformatter.parse(dateToParse);
				}
			} catch (ParseException e) {
				throw new BadDateFormatException(e);
			}
		}
		return result;
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

	/**
	 * Compare two periods overlap (first period is the period of reference)
	 * @param dateDebut1 	start date of the first period
	 * @param dateFin1		end date of the first period
	 * @param dateDebut2	start date of the second period
	 * @param dateFin2		end date of the second period
	 * @return if second period is PERIOD_INCLUDE or PERIOD_OVERLAP_INF or
	 * PERIOD_OVERLAP_SUP or PERIOD_RECOVER or PERIOD_LOWER or PERIOD_HIGHER in/than first one
	 * @throws JrafApplicativeException if input dates are invalid
	 */
	public static int comparePeriods(Date dateDebut1, Date dateFin1, Date dateDebut2, Date dateFin2) throws JrafApplicativeException {

		boolean bInferieur = false;
		boolean bSuperieur = false;
		boolean bDebutInclus = false;
		boolean bFinInclus = false;

		// check periods are valid
		if (dateDebut1 == null
				|| dateDebut2 == null
				|| (dateFin1 != null && dateDebut1.after(dateFin1))
				|| (dateFin2 != null && dateDebut2.after(dateFin2)))
		{
			throw new JrafApplicativeException("DATE INVALID"); // Date invalid
		}
		dateFin1 = dateFin1 == null ? SicDateUtils.NO_EXPIRE : dateFin1;
		dateFin2 = dateFin2 == null ? SicDateUtils.NO_EXPIRE : dateFin2;

		if (dateDebut2.getTime() < dateDebut1.getTime() && dateFin2.before(dateDebut1))
		{
			bInferieur = true;
		}
		if ( dateFin2.getTime() > dateFin1.getTime() &&
				dateDebut2.getTime() > dateFin1.getTime())
		{
			bSuperieur = true;
		}
		if (dateDebut2.getTime() >= dateDebut1.getTime() &&
				dateDebut2.getTime() <= dateFin1.getTime()){
			bDebutInclus = true;
		}
		if (dateFin2.getTime() >= dateDebut1.getTime()
				&&  dateFin2.getTime() <= dateFin1.getTime()){
			bFinInclus = true;
		}

		if (bDebutInclus && bFinInclus){ return SicDateUtils.PERIOD_INCLUDE; }
		if (bDebutInclus && !bFinInclus){ return SicDateUtils.PERIOD_OVERLAP_SUP; }
		if (bFinInclus && !bDebutInclus){ return SicDateUtils.PERIOD_OVERLAP_INF; }
		if (bInferieur){ return SicDateUtils.PERIOD_LOWER; }
		if (bSuperieur){ return SicDateUtils.PERIOD_HIGHER; }
		return SicDateUtils.PERIOD_RECOVER;
	}



	/**
	 * @param pDate date
	 * @return minuit
	 */
	public static Date midnight(final Date pDate) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(pDate);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		return cal.getTime();
	}

	public static String dateToString(Date date) {
		return SicDateUtils.ddMMYYYYformatter.format(date);
	}
	
	public static Date convertStringToDateDDMMYYYY(String string) throws JrafDomainException  {
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			return df.parse(string);
		}
		catch (Exception e) {
			throw new JrafDomainException("Invalid date format. Expected format is 'dd/MM/yyyy'");
		}
	}

	public static boolean checkFormatDateDDMMYYYY(String string)  {
		if(string == null){
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);

		try {
			//if not valid, it will throw ParseException
			Date date = sdf.parse(string);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static Date cleanIndividualBirthDate(Date birthDate) {
		if (birthDate == null) {
			return null;
		}
		
		return midnight(birthDate);
	}

	public static Date localDateToDate(LocalDate ld) {
		if (ld != null) {
			return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
		}
		else {
			return null;
		}
	}

	public static Calendar localDateToCalendar(LocalDate ld) {
		if (ld != null) {
			Calendar calendar = Calendar.getInstance();
			Instant instant = ld.atStartOfDay(ZoneId.systemDefault()).toInstant();
			calendar.setTime(Date.from(instant));
			return calendar;
		}
		else {
			return null;
		}
	}
}
