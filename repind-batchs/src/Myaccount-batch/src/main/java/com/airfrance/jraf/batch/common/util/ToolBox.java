package com.airfrance.jraf.batch.common.util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author T444761
 *
 */
public class ToolBox {
	
	/**
	 * Generates a Date object from the string <b>dateToParse</b>
	 * using the specified SimpleDateFormat <b>dateFormat</b>.
	 * 
	 * <b>null</b> is returned if the given dateToParse is null.
	 * 
	 * @param dateToParse String to parse
	 * @param dateFormat Date Format to follow
	 * @return
	 * 
	 * @throws BatchAlimentationMRException if <b>dateFormat</b> is null or if conversion does not work.
	 */
	public static Date convertDate2String(String dateToParse, SimpleDateFormat dateFormat) throws ParseException {
		
		Date dateToReturn = null;

		if (dateToParse != null) {
			dateToParse = dateToParse.trim();
			
			if (!dateToParse.isEmpty()) {


				try {
					dateToReturn = dateFormat.parse(dateToParse.trim().toUpperCase());
				} catch (ParseException e) {
					throw new ParseException("Date format is incorrect!", e.getErrorOffset());
				}
			}
		}
		
		return dateToReturn;
	}


	/**
	 * Checks if the file whose path is <b>fileToProcess</b> exists.
	 * 
	 * The given path is cleaned from blank spaces at its beginning
	 * and its end, then returned.
	 * 
	 * @param fileName Path of the file to be checked for existence
	 * @return
	 * 
	 * @throws BatchAlimentationMRException if path is empty or if the the file does not exist
	 */
	public static String checkFileToProcess(String fileName, String directory)  {
		

		
		fileName = directory + fileName.trim();


		return fileName;
	}


	public static String checkDirectory(String directory)  {


		
		directory = directory.trim();
		

		
		String separator = File.separator;
		if (!directory.endsWith(separator)) {
			directory += separator;
		}
		
		return directory;
	}

}
