package com.airfrance.jraf.batch.common.util;


import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author T444761
 *
 */
public class ToolBoxTest {
	
	private static final String EXCEPTION_WAS_SUPPOSED_TO_BE_THROWN = "An exception was supposed to be thrown!";
	private static final String NO_EXCEPTION_WAS_SUPPOSED_TO_BE_THROWN = "No exception was supposed to be thrown!";

	/**
	 * Test method for {@link com.airfrance.jraf.batch.common.util.ToolBox#convertDate2String(java.lang.String, java.text.SimpleDateFormat)}.
	 */
	@Test
	public void testConvertDate2String() {
		
		// Initialize local variables
		String dateToParse = null;
		SimpleDateFormat dateFormat = null;

		// Test 1: all input is null
		try {
			Date date = ToolBox.convertDate2String(dateToParse, dateFormat);
			Assert.assertNull(date);
		} catch ( ParseException e) {
			e.printStackTrace();
		}

		// Test 2: dateFormat=null
		dateToParse = "23-11-2016";

	// to be fixed
//		try {
//			ToolBox.convertDate2String(dateToParse, dateFormat);
//			Assert.fail(EXCEPTION_WAS_SUPPOSED_TO_BE_THROWN);
//		} catch ( ParseException e) {
//			Assert.assertTrue(e.getType() == ParseException.technical);
//		}
		
		// Test 3: dateToParse does not respect expected format
		dateFormat = new SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH);
		
		try {
			ToolBox.convertDate2String(dateToParse, dateFormat);
			Assert.fail(EXCEPTION_WAS_SUPPOSED_TO_BE_THROWN);
		} catch ( ParseException e) {
			Assert.assertTrue(e.getClass() == ParseException.class);
		}
		
		// Test 4: everything is OK
		dateToParse = "23Nov2016";
		
		try {
			ToolBox.convertDate2String(dateToParse, dateFormat);
		} catch ( ParseException e) {
			Assert.fail(NO_EXCEPTION_WAS_SUPPOSED_TO_BE_THROWN);
		}
	}


	/**
	 * Test method for {@link com.airfrance.jraf.batch.common.util.ToolBox#checkFileToProcess(java.lang.String)}.
	 */
	@Test
	public void checkFileToProcessTest() {
		
		// Initialize local variables
		String fileName = null;
		String directory = null;
		
		// Test 1: input is null
			ToolBox.checkFileToProcess(fileName, directory);
			Assert.fail(EXCEPTION_WAS_SUPPOSED_TO_BE_THROWN);
		// Test 2: input is empty
		fileName = "";
			ToolBox.checkFileToProcess(fileName, directory);
			Assert.fail(EXCEPTION_WAS_SUPPOSED_TO_BE_THROWN);
		
		// Test 3: File does not exist
		fileName = "test.test";
			ToolBox.checkFileToProcess(fileName, directory);
			Assert.fail(EXCEPTION_WAS_SUPPOSED_TO_BE_THROWN);
		
		// Test 3: File is correct
		fileName = "ToolBoxTest.java";
		directory = "src/test/java/com/airfrance/jraf/batch/common/util/";
			ToolBox.checkFileToProcess(fileName, directory);
	}

}
