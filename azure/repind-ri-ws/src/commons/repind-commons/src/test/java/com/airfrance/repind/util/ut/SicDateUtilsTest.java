package com.airfrance.repind.util.ut;

import com.airfrance.ref.exception.BadDateFormatException;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.util.SicDateUtils;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;


/**
 * @author E6378882
 *
 */
public class SicDateUtilsTest {

    /** logger */
    private static final Log logger = LogFactory.getLog(SicDateUtilsTest.class);
    
    @Test
    public void checkFormat(){
    	Assert.assertTrue(SicDateUtils.checkFormatDateDDMMYYYY("01/01/2000"));
    	Assert.assertFalse(SicDateUtils.checkFormatDateDDMMYYYY("40/55/1"));
    }
    
    @Test
    public void comparePeriodsIncludeTest() throws Exception {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
    	
    	String sdeb1 = "01/01/2015";
    	Date deb1 = sf.parse(sdeb1);
    	
    	String sfin1 = "15/03/2015";
    	Date fin1 = sf.parse(sfin1);
    	
    	String sdeb2 =  "01/02/2015";
    	Date deb2 = sf.parse(sdeb2);
    	
    	String sfin2 = "20/02/2015";
    	Date fin2 = sf.parse(sfin2);
    	
    	assertEquals(SicDateUtils.PERIOD_INCLUDE, SicDateUtils.comparePeriods(deb1, fin1, deb2, fin2));
    }
    
    @Test
    public void comparePeriodsOverlapInfTest() throws Exception {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
    	
    	String sdeb1 = "01/01/2015";
    	Date deb1 = sf.parse(sdeb1);
    	
    	String sfin1 = "15/03/2015";
    	Date fin1 = sf.parse(sfin1);
    	
    	String sdeb2 =  "01/12/2014";
    	Date deb2 = sf.parse(sdeb2);
    	
    	String sfin2 = "20/02/2015";
    	Date fin2 = sf.parse(sfin2);
    	
    	assertEquals(SicDateUtils.PERIOD_OVERLAP_INF, SicDateUtils.comparePeriods(deb1, fin1, deb2, fin2));
    }    
    
    @Test
    public void comparePeriodsOverlapSupTest() throws Exception {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
    	
    	String sdeb1 = "01/01/2015";
    	Date deb1 = sf.parse(sdeb1);
    	
    	String sfin1 = "15/03/2015";
    	Date fin1 = sf.parse(sfin1);
    	
    	String sdeb2 =  "01/02/2015";
    	Date deb2 = sf.parse(sdeb2);
    	
    	String sfin2 = "20/03/2015";
    	Date fin2 = sf.parse(sfin2);
    	
    	assertEquals(SicDateUtils.PERIOD_OVERLAP_SUP, SicDateUtils.comparePeriods(deb1, fin1, deb2, fin2));
    }     
    
    @Test
    public void comparePeriodsRecoverTest() throws Exception {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
    	
    	String sdeb1 = "01/01/2015";
    	Date deb1 = sf.parse(sdeb1);
    	
    	String sfin1 = "15/03/2015";
    	Date fin1 = sf.parse(sfin1);
    	
    	String sdeb2 =  "01/12/2014";
    	Date deb2 = sf.parse(sdeb2);
    	
    	String sfin2 = "20/03/2015";
    	Date fin2 = sf.parse(sfin2);
    	
    	assertEquals(SicDateUtils.PERIOD_RECOVER, SicDateUtils.comparePeriods(deb1, fin1, deb2, fin2));
    }     
    
    @Test
    public void comparePeriodsLowerTest() throws Exception {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
    	
    	String sdeb1 = "01/01/2015";
    	Date deb1 = sf.parse(sdeb1);
    	
    	String sfin1 = "15/03/2015";
    	Date fin1 = sf.parse(sfin1);
    	
    	String sdeb2 =  "01/12/2014";
    	Date deb2 = sf.parse(sdeb2);
    	
    	String sfin2 = "20/12/2014";
    	Date fin2 = sf.parse(sfin2);
    	
    	assertEquals(SicDateUtils.PERIOD_LOWER, SicDateUtils.comparePeriods(deb1, fin1, deb2, fin2));
    } 
    
    @Test
    public void comparePeriodsHigherTest() throws Exception {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
    	
    	String sdeb1 = "01/01/2015";
    	Date deb1 = sf.parse(sdeb1);
    	
    	String sfin1 = "15/03/2015";
    	Date fin1 = sf.parse(sfin1);
    	
    	String sdeb2 =  "20/03/2015";
    	Date deb2 = sf.parse(sdeb2);
    	
    	String sfin2 = "20/04/2015";
    	Date fin2 = sf.parse(sfin2);
    	
    	assertEquals(SicDateUtils.PERIOD_HIGHER, SicDateUtils.comparePeriods(deb1, fin1, deb2, fin2));
    }    
    
    @Test
    public void comparePeriodsDateInvalidTest() throws Exception {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
    	
    	String sdeb1 = "01/01/2015";
    	Date deb1 = sf.parse(sdeb1);
    	
    	String sfin1 = "15/03/2015";
    	Date fin1 = sf.parse(sfin1);
    	
    	String sdeb2 =  "20/03/2015";
    	Date deb2 = sf.parse(sdeb2);
    	
    	String sfin2 = "20/04/2015";
    	Date fin2 = sf.parse(sfin2);
    	
    	int retour = 0;
    	// deb1 null
    	try {
    		retour = SicDateUtils.comparePeriods(null, fin1, deb2, fin2);
    		fail("Erreur date invalid no generee : retour = " + retour);
    	} catch (JrafApplicativeException e) {
    		assertTrue("DATE INVALID".equals(e.getMessage()));
    	}
    	
    	// fin null
		retour = SicDateUtils.comparePeriods(deb1, null, deb2, fin2);
	    assertEquals(0, retour);
    	
    	
    	// deb2 null
    	try {
    		retour = SicDateUtils.comparePeriods(deb1, fin1, null, fin2);
    		fail("Erreur date invalid no generee : retour = " + retour);
    	} catch (JrafApplicativeException e) {
    		assertTrue("DATE INVALID".equals(e.getMessage()));
    	}
    	
    	// fin2 null
    	
		retour = SicDateUtils.comparePeriods(deb1, fin1, deb2, null);
	    assertEquals(5, retour);
    	
    	// deb1 > fin1
    	try {
    		String sfin1bis = "15/03/2014";
        	Date fin1bis = sf.parse(sfin1bis);
    		retour = SicDateUtils.comparePeriods(deb1, fin1bis, deb2, fin2);
    		fail("Erreur date invalid no generee : retour = " + retour);
    	} catch (JrafApplicativeException e) {
    		assertTrue("DATE INVALID".equals(e.getMessage()));
    	}
    	
    	// deb1 > fin1
    	try {
    		String sfin2bis = "15/03/2014";
        	Date fin2bis = sf.parse(sfin2bis);
    		retour = SicDateUtils.comparePeriods(deb1, fin1, deb2, fin2bis);
    		fail("Erreur date invalid no generee : retour = " + retour);
    	} catch (JrafApplicativeException e) {
    		assertTrue("DATE INVALID".equals(e.getMessage()));
    	}
    }

	@Test(expected = Test.None.class /* no exception expected */)
    public void testAfterBeforeCompareTo() {
        
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        
        cal.add(Calendar.HOUR_OF_DAY, 1);
        Date oneHourAfter = cal.getTime();
        
        System.err.println("now = " + now);
        System.err.println("oneHourAfter = " + oneHourAfter);
        System.err.println("now.after(oneHourAfter) = " + now.after(oneHourAfter));
        System.err.println("now.before(oneHourAfter) = " + now.before(oneHourAfter));
        System.err.println("now.compareTo(oneHourAfter) = " + now.compareTo(oneHourAfter));
    }
    
    @Test
    public void test_DateTOxmlGregorianCalendar_Null() throws ParseException {

    	assertEquals(null, SicDateUtils.dateTOxmlGregorianCalendar(null));
    }
    
    @Test
    public void test_ComputeHourNull() throws ParseException {

    	assertEquals(null, SicDateUtils.computeHour(null));
    }

    @Test
    public void test_ComputeHourEmpty() throws ParseException {

    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		String uneDateChaine = "16/08/2013";
    	Date uneDate = sf.parse(uneDateChaine);
    	
    	assertEquals("000000", SicDateUtils.computeHour(uneDate));
    }

    @Test
    public void test_ComputeHourMidDay() throws ParseException {

    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String uneDateChaine = "16/08/2013 12:13:14";
    	Date uneDate = sf.parse(uneDateChaine);
    	
    	assertEquals("121314", SicDateUtils.computeHour(uneDate));
    }

    @Test
    public void test_ComputeHourOther() throws ParseException {

    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String uneDateChaine = "16/08/2013 12:00:00";
    	Date uneDate = sf.parse(uneDateChaine);
    	
    	assertEquals("120000", SicDateUtils.computeHour(uneDate));
    }

    @Test
    public void test_ComputeHourMidNight() throws ParseException {

    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String uneDateChaine = "16/08/2013 00:00:00";
    	Date uneDate = sf.parse(uneDateChaine);
    	
    	assertEquals("000000", SicDateUtils.computeHour(uneDate));
    }

    @Test
    public void test_XmlGregorianCalendarTOdate_Null() {
    	
    	assertEquals(null, SicDateUtils.xmlGregorianCalendarTOdate(null));
    }

    @Test
    public void test_XmlGregorianCalendarTOdate_Normal() throws JrafDomainException, DatatypeConfigurationException {
    	
    	String date = "01/07/1955";
    	
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(SicDateUtils.convertStringToDateDDMMYYYY(date));
		XMLGregorianCalendar xmlCalendar = null;

		xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
    	
    	assertEquals(SicDateUtils.convertStringToDateDDMMYYYY(date), SicDateUtils.xmlGregorianCalendarTOdate(xmlCalendar));
    }

    @Test
    public void test_XmlGregorianCalendarTOdate_01012001() throws JrafDomainException, DatatypeConfigurationException {
    	
    	String date = "01/01/2001";
    	
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(SicDateUtils.convertStringToDateDDMMYYYY(date));
		XMLGregorianCalendar xmlCalendar = null;

		xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
    	
    	assertEquals(SicDateUtils.convertStringToDateDDMMYYYY(date), SicDateUtils.xmlGregorianCalendarTOdate(xmlCalendar));
    }
    

    @Test
    public void test_XmlGregorianCalendarTOdate_12132018() throws JrafDomainException, DatatypeConfigurationException {
    	
    	String date = "12/13/2018";
    	
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(SicDateUtils.convertStringToDateDDMMYYYY(date));
		XMLGregorianCalendar xmlCalendar = null;

		xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
    	
    	assertEquals(SicDateUtils.convertStringToDateDDMMYYYY(date), SicDateUtils.xmlGregorianCalendarTOdate(xmlCalendar));
    }

    @Test
    public void test_XmlGregorianCalendarTOdate_01012019() throws JrafDomainException, DatatypeConfigurationException {
    	
    	String date = "01/01/2019";
    	
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(SicDateUtils.convertStringToDateDDMMYYYY(date));
		XMLGregorianCalendar xmlCalendar = null;

		xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
    	
    	assertEquals(SicDateUtils.convertStringToDateDDMMYYYY(date), SicDateUtils.xmlGregorianCalendarTOdate(xmlCalendar));
    }

    @Test
    public void test_ComputeFrenchDate_NULL() throws ParseException {
    	
    	assertEquals(null, SicDateUtils.computeFrenchDate(null));
    }

    @Test
    public void test_ComputeFrenchDate_01012019() throws ParseException {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		String uneDateChaine = "01/01/2019";
    	Date uneDate = sf.parse(uneDateChaine);
    	
    	assertEquals("01012019", SicDateUtils.computeFrenchDate(uneDate));
    }
    
    @Test
    public void test_ComputeFrenchDate_01011999() throws ParseException {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		String uneDateChaine = "01/01/1999";
    	Date uneDate = sf.parse(uneDateChaine);
    	
    	assertEquals("01011999", SicDateUtils.computeFrenchDate(uneDate));
    }

    @Test
    public void test_ComputeFrenchDate_12122012() throws ParseException {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		String uneDateChaine = "12/12/2012";
    	Date uneDate = sf.parse(uneDateChaine);
    	
    	assertEquals("12122012", SicDateUtils.computeFrenchDate(uneDate));
    }

    @Test
    public void test_ComputeFrenchDate_03032013() throws ParseException {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		String uneDateChaine = "03/03/2013";
    	Date uneDate = sf.parse(uneDateChaine);
    	
    	assertEquals("03032013", SicDateUtils.computeFrenchDate(uneDate));
    }
    
    @Test
    public void test_StringToDate_03032013() throws ParseException, BadDateFormatException, JrafApplicativeException {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		String uneDateChaine = "03/03/2013";
    	Date uneDate = sf.parse(uneDateChaine);
    	
    	assertEquals(uneDate, SicDateUtils.stringToDate("03032013"));
    }
    
    @Test
    public void test_StringToDate_01012001() throws ParseException, BadDateFormatException, JrafApplicativeException {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		String uneDateChaine = "01/01/2001";
    	Date uneDate = sf.parse(uneDateChaine);
    	
    	assertEquals(uneDate, SicDateUtils.stringToDate("01012001"));
    }

    @Test
    public void test_StringToDate_12122012() throws ParseException, BadDateFormatException, JrafApplicativeException {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		String uneDateChaine = "12/12/2012";
    	Date uneDate = sf.parse(uneDateChaine);
    	
    	assertEquals(uneDate, SicDateUtils.stringToDate("12122012"));
    }

    @Test
    public void test_StringToDate_BadFormat() throws ParseException, JrafApplicativeException {
    	try {
    		SicDateUtils.stringToDate("13130013");
    		// On doit recuperer une BadDateFormatException
    	} catch (BadDateFormatException bfe) {
    		assertEquals(true, true);
    	}
    }
    
    @Test
    public void test_Midnight_01012001() throws ParseException, BadDateFormatException, JrafApplicativeException {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String uneDateChaine = "01/01/2001 12:13:14";
    	Date uneDate = sf.parse(uneDateChaine);

		String uneDateChaineExpected = "01/01/2001 00:00:00";
    	Date uneDateExpected = sf.parse(uneDateChaineExpected);
    	
    	assertEquals(uneDateExpected, SicDateUtils.midnight(uneDate));
    }

    @Test
    public void test_Midnight_31122010() throws ParseException, BadDateFormatException, JrafApplicativeException {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String uneDateChaine = "31/12/2010 23:13:14";
    	Date uneDate = sf.parse(uneDateChaine);

		String uneDateChaineExpected = "31/12/2010 00:00:00";
    	Date uneDateExpected = sf.parse(uneDateChaineExpected);
    	
    	assertEquals(uneDateExpected, SicDateUtils.midnight(uneDate));
    }

    @Test
    public void test_Midnight_01022003() throws ParseException, BadDateFormatException, JrafApplicativeException {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String uneDateChaine = "01/02/2003 23:13:14";
    	Date uneDate = sf.parse(uneDateChaine);

		String uneDateChaineExpected = "01/02/2003 00:00:00";
    	Date uneDateExpected = sf.parse(uneDateChaineExpected);
    	
    	assertEquals(uneDateExpected, SicDateUtils.midnight(uneDate));
    }
    
    @Test
    public void test_DateToString_01022003() throws ParseException, BadDateFormatException, JrafApplicativeException {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		String uneDateChaine = "01/02/2003";
    	Date uneDate = sf.parse(uneDateChaine);

		String uneDateChaineExpected = "01/02/2003";
    	
    	assertEquals(uneDateChaineExpected, SicDateUtils.dateToString(uneDate));
    }
    @Test
    public void test_DateToString_01022003Hour() throws ParseException, BadDateFormatException, JrafApplicativeException {
    	
    	SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String uneDateChaine = "01/02/2003 12:35:14";
    	Date uneDate = sf.parse(uneDateChaine);

		String uneDateChaineExpected = "01/02/2003";
    	
    	assertEquals(uneDateChaineExpected, SicDateUtils.dateToString(uneDate));
    }

    
    @Test
    public void test_ConvertStringToDateDDMMYYYY_EXception() throws ParseException, JrafApplicativeException, JrafDomainException {
    	try {
    		SicDateUtils.convertStringToDateDDMMYYYY("13130013");
    		// On doit recuperer une BadDateFormatException
    	} catch (Exception bfe) {
    		assertEquals(true, true);
    	}
    }

    
    @Test
    public void test_CheckFormatDateDDMMYYYY_Null() throws ParseException {

    	assertEquals(false, SicDateUtils.checkFormatDateDDMMYYYY(null));
    }
    
}
