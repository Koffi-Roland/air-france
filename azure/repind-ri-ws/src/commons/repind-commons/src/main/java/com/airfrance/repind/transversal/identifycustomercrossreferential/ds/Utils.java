package com.airfrance.repind.transversal.identifycustomercrossreferential.ds;

import java.util.regex.Pattern;

public class Utils {
	
	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
	private static String queryAlphaNumericPattern = "[a-zA-Z0-9]";
	private static String emailPattern = "[a-zA-Z0-9]";
	
	
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	
	/**
	 * Check if a text is alphanumeric
	 * @param text
	 * @return
	 */
	public static boolean isAlphaNumeric(String text)
	{
		boolean result = false;
		Pattern p = Pattern.compile(queryAlphaNumericPattern);
		result = p.matcher(text).find();
		return result;
	}
	
	
	/**
	 * Checks if a text is email compliant
	 * @param text
	 * @return
	 */
	public static boolean isEmailCompliant(String text)
	{
		boolean result = false;
		Pattern p = Pattern.compile(emailPattern);
		result = p.matcher(text).find();
		return result;
	}
}
