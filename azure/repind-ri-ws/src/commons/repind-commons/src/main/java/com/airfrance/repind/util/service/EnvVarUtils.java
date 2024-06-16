package com.airfrance.repind.util.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnvVarUtils {

	/**
	 * Transform patterns string from DB to a List<String> to be used, return
	 * empty list by default
	 * 
	 * @param patternsFromDB,
	 *            String from DB with pattern
	 * @param delimiterFromDB,
	 *            String used to delimiter patterns into the string
	 *            patternsFromDB
	 * @return List<String> containing patterns
	 */
	public static List<String> preparePatternsToMatchPassword(String patternsFromDB, String delimiterFromDB) {
		
		if (patternsFromDB == null || delimiterFromDB == null) {
			return new ArrayList<String>();
		}
		List<String> patterns = new ArrayList<String>();
		patterns = new ArrayList<String>(Arrays.asList(patternsFromDB.split(delimiterFromDB)));
		
		return patterns == null ? new ArrayList<String>() : patterns;
	}

	/**
	 * Transform Additional patterns string from DB to a List<String> to be
	 * used, Add new rules and sub no useful rules from commun pattern return
	 * empty list by default
	 * 
	 * @param communPatterns,
	 *            String from DB with pattern common for all process
	 * @param patternsAdditionalFromDB,
	 *            String from db with additional pattern to include
	 *            patternsFromDB
	 * @return List<String> containing patterns
	 */
	/**
	 * public static List<String> preparePatternsAdditionalToMatchPassword(List
	 * <String> communPatterns, String patternsAdditionalFromDB, String
	 * delimiterFromDB) {
	 * 
	 * if (communPatterns == null) { return new ArrayList<String>(); } else if
	 * (patternsAdditionalFromDB == null) { return communPatterns; } List
	 * <String> patternsToAdd = new ArrayList<String>(); List
	 * <String> patternsToSub = new ArrayList<String>();
	 * 
	 * 
	 * 
	 * 
	 * for (String pattern : patternsToSub) { if
	 * (communPatterns.contains(pattern)) { communPatterns.remove(pattern); } }
	 * 
	 * for (String pattern : patternsToAdd) { if
	 * (!communPatterns.contains(pattern)) { communPatterns.add(pattern); } }
	 * 
	 * return communPatterns == null ? new ArrayList<String>() : communPatterns;
	 * }
	 * 
	 * public static Map<String, List<String>>
	 * transformStringIntoMultidataMap(String patternsAdditionalFromDB, List
	 * <String> delimiters) {
	 * 
	 * //The Map returned at the end Map<String, List<String>> result = new
	 * HashMap<String,List<String>>();
	 * 
	 * //We created the pattern from the list String stringPatternDelimiter =
	 * concatListOfStringToBecameOrRegex(delimiters);
	 * 
	 * //Cut the string based on delimiter to create the Map wit values Pattern
	 * patternDelimiter = Pattern.compile(stringPatternDelimiter); Matcher
	 * matcher = patternDelimiter.matcher(patternsAdditionalFromDB); int
	 * cutAtValue = 0; String delimiterFound = ""; while (matcher.find()) {
	 * 
	 * if (cutAtValue != 0) { for(int i = 0; i < delimiters.size();i++) {
	 * if(delimiterFound.equals(delimiters.get(i))) {
	 * if(result.containsKey(delimiterFound)) {
	 * result.get(delimiterFound).add(patternsAdditionalFromDB.substring(
	 * cutAtValue, matcher.start())); } else { ArrayList<String> pattern = new
	 * ArrayList<String>();
	 * pattern.add(patternsAdditionalFromDB.substring(cutAtValue,
	 * matcher.start())); result.put(delimiterFound, pattern); } } } }
	 * 
	 * cutAtValue = matcher.end(); delimiterFound =
	 * patternsAdditionalFromDB.substring(matcher.start(), cutAtValue); }
	 * 
	 * return result; }
	 * 
	 * private static String concatListOfStringToBecameOrRegex(List
	 * <String> strings) {
	 * 
	 * String stringPatternDelimiter = "("; for (int i = 0; i < strings.size();
	 * i++) { stringPatternDelimiter += strings.get(i); if (i < strings.size() -
	 * 1) { stringPatternDelimiter += "|"; } } stringPatternDelimiter += "){1}";
	 * 
	 * return stringPatternDelimiter; }
	 **/

}
