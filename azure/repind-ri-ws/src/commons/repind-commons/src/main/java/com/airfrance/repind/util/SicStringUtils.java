package com.airfrance.repind.util;

import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.sun.xml.ws.api.message.Header;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.soap.SOAPException;
import javax.xml.ws.WebServiceContext;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class SicStringUtils {

	private static Log log = LogFactory.getLog(SicStringUtils.class);

	private static String regexCleanEmail = "(\"?[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-][^\\s]*[a-zA-Z0-9]+\\]?)";

	private static final String UPPERCASE_ASCII = "AEIOU" // grave
			+ "AEIOUY" // acute
			+ "AEIOUY" // circumflex
			+ "AON" // tilde
			+ "AEIOUY" // trema
			+ "A" // ring
			+ "C" // cedilla
			+ "OU"; // double acute

	private static final String UPPERCASE_UNICODE = "\u00C0\u00C8\u00CC\u00D2\u00D9" // grave
			+ "\u00C1\u00C9\u00CD\u00D3\u00DA\u00DD" // acute
			+ "\u00C2\u00CA\u00CE\u00D4\u00DB\u0176" // circumflex
			+ "\u00C3\u00D5\u00D1" // tilde
			+ "\u00C4\u00CB\u00CF\u00D6\u00DC\u0178" // trema
			+ "\u00C5" // ring
			+ "\u00C7" // cedilla
			+ "\u0150\u0170"; // double acute

	private static final String UPPERCASE_ASCII_FIRMS = "AEIOU" // grave
			+ "AEIOUY" // acute
			+ "AEIOY" // circumflex
			+ "Y" // trema
			+ "OU"; // double acute

	private static final String UPPERCASE_UNICODE_FIRMS = "\u00C0\u00C8\u00CC\u00D2\u00D9" // grave
			+ "\u00C1\u00C9\u00CD\u00D3\u00DA\u00DD" // acute
			+ "\u00C2\u00CA\u00CE\u00D4\u0176" // circumflex
			+ "\u0178" // trema
			+ "\u0150\u0170"; // double acute

	private SicStringUtils() {

	}

	/**
	 * Converts all accented characters of a string to ASCII ones using specific
	 * characters set.
	 * 
	 * @param txt
	 * 
	 * @return
	 */
	public static String toUpperCaseWithoutAccents(String txt) {

		return toUpperCaseWithoutAccents(txt, UPPERCASE_UNICODE, UPPERCASE_ASCII);
	}

	/**
	 * Converts all accented characters of a string to ASCII ones using specific
	 * characters set without any NON-ASCII char
	 * 
	 * @param txt
	 * 
	 * @return
	 */
	public static String toUpperCaseWithoutAccentsOnlyASCII(String txt) {

		return toUpperCaseWithoutAccents(getASCII(txt), UPPERCASE_UNICODE, UPPERCASE_ASCII);
	}
	
    /**
     * Envoie uniquement les caractères ASCII
     * @return String
     */
    public static String getASCII(String chaine) { 
    	
    	if (chaine == null) {
    		return null;	
    	} else {
    		// We delete all the non-ASCII char ! So what is kept is only ASCII char... 
    		return chaine.replaceAll("[^\\p{ASCII}]", "");
    	}
    }
	
	/**
	 * Converts all accented characters of a string to ASCII ones using specific
	 * characters set for firms.
	 * 
	 * @param txt
	 * 
	 * @return
	 */
	public static String toUpperCaseWithoutAccentsForFirms(String txt) {

		return toUpperCaseWithoutAccentsKeepEszett(txt, UPPERCASE_UNICODE_FIRMS, UPPERCASE_ASCII_FIRMS);
	}

	protected static String toUpperCaseWithoutAccentsKeepEszett(String txt, String uppercaseUnicode, String uppercaseASCII) {

		if (txt == null) {
			return null;
		}
		
		// first pass, eszett detection
		int txtLength = txt.length();
		StringBuilder sbUp = new StringBuilder();
		for (int i = 0; i < txtLength; i++) {
			char c = txt.charAt(i);
			if ('ß'==c) {
				sbUp.append(c); // the uppercase 'ẞ' is not in charset				
			} else {
				sbUp.append(Character.toUpperCase(c));				
			}
		}
		
		String txtUpper = sbUp.toString();
		StringBuilder sb = new StringBuilder();
		int n = txtUpper.length();
		for (int i = 0; i < n; i++) {

			char c = txtUpper.charAt(i);
			int pos = uppercaseUnicode.indexOf(c);
			if (pos > -1) {
				sb.append(uppercaseASCII.charAt(pos));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	protected static String toUpperCaseWithoutAccents(String txt, String uppercaseUnicode, String uppercaseASCII) {

		if (txt == null) {
			return null;
		}
		String txtUpper = txt.toUpperCase();
		StringBuilder sb = new StringBuilder();
		int n = txtUpper.length();
		for (int i = 0; i < n; i++) {

			char c = txtUpper.charAt(i);
			int pos = uppercaseUnicode.indexOf(c);
			if (pos > -1) {
				sb.append(uppercaseASCII.charAt(pos));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Formate une date de type YYYY-MM-DD HH:MM:SS.S en DDMMYYYY ou une date de
	 * type YYYY-MM-DD en DDMMYYYY
	 * 
	 * @return date en format DDMMYYYY
	 */
	public static String formatDateToAdh(String date) throws JrafApplicativeException {
		if (date.length() < 10) {
			throw new JrafApplicativeException("Bad date format");
		}
		StringBuffer result = new StringBuffer();
		result.append(date.substring(8, 10));
		result.append(date.substring(5, 7));
		result.append(date.substring(0, 4));
		return result.toString();
	}

	/**
	 * Formate une date de type DDMMYYYY en YYYY-MM-DD
	 * 
	 * @return date en format DDMMYYYY
	 */
	public static String formatDateToWsdl(String date) throws JrafApplicativeException {
		if (date.length() < 8) {
			throw new JrafApplicativeException("Bad date format");
		}
		StringBuffer result = new StringBuffer();
		result.append(date.substring(4, 8)).append("-");
		result.append(date.substring(2, 4)).append("-");
		result.append(date.substring(0, 2));
		return result.toString();
	}

	public static boolean isDDMMYYYFormatDate(String date) throws JrafApplicativeException {
		boolean isValid = false;

		String expression = "^((0[1-9])|(1\\d)|(2\\d)|(3[0-1]))((0[1-9])|(1[0-2]))((\\d{4}))$";
		CharSequence inputStr = date;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * Check if the email is valid
	 * 
	 * @param email
	 * @return
	 * @throws JrafApplicativeException
	 */
	public static boolean isValidEmail(String email) {
		return EmailUtils.isValidEmail(email);
	}

	/**
	 * Check if the fb identifier is valid
	 * 
	 * @param fbIdent
	 * @return
	 * @throws JrafApplicativeException
	 */
	public static boolean isValidFbIdentifier(String fbIdent) throws JrafApplicativeException {
		return SicStringUtils.isValidIdentifier(fbIdent);
	}

	/**
	 * Check if the identifier is valid
	 * 
	 * @param ident
	 * @return
	 * @throws JrafApplicativeException
	 */
	public static boolean isValidIdentifier(String ident) throws JrafApplicativeException {
		boolean isValid = true;
		if (ident.length() != 12 || !SicStringUtils.isNumeric(ident)) {
			isValid = false;
		}
		return isValid;
	}

	/**
	 * Check if the string is valid
	 * 
	 * @param myaccntIdent
	 * @return
	 * @throws JrafApplicativeException
	 */
	public static boolean isValidMyAccntIdentifier(String myaccntIdent) throws JrafApplicativeException {
		boolean isValid = false;

		// Initialize reg ex for Myaccount identifier
		// Any string begins with 6 digits following by 2 letters
		String expression = "\\d{6}[a-zA-Z]{2}";
		CharSequence inputStr = myaccntIdent;

		// Make the comparison case-insensitive.
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * Check if the string is numeric
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNumeric(String s) {
		return s.matches("\\d+");
	}

	public static int getLevenshteinDistance(String s, String t) {
		if (s == null || t == null) {
			throw new IllegalArgumentException("Strings must not be null");
		}

		/*
		 * The difference between this impl. and the previous is that, rather
		 * than creating and retaining a matrix of size s.length()+1 by
		 * t.length()+1, we maintain two single-dimensional arrays of length
		 * s.length()+1. The first, d, is the 'current working' distance array
		 * that maintains the newest distance cost counts as we iterate through
		 * the characters of String s. Each time we increment the index of
		 * String t we are comparing, d is copied to p, the second int[]. Doing
		 * so allows us to retain the previous cost counts as required by the
		 * algorithm (taking the minimum of the cost count to the left, up one,
		 * and diagonally up and to the left of the current cost count being
		 * calculated). (Note that the arrays aren't really copied anymore, just
		 * switched...this is clearly much better than cloning an array or doing
		 * a System.arraycopy() each time through the outer loop.)
		 * 
		 * Effectively, the difference between the two implementations is this
		 * one does not cause an out of memory condition when calculating the LD
		 * over two very large strings.
		 */

		int n = s.length(); // length of s
		int m = t.length(); // length of t

		if (n == 0) {
			return m;
		} else if (m == 0) {
			return n;
		}

		int p[] = new int[n + 1]; // 'previous' cost array, horizontally
		int d[] = new int[n + 1]; // cost array, horizontally
		int _d[]; // placeholder to assist in swapping p and d

		// indexes into strings s and t
		int i; // iterates through s
		int j; // iterates through t

		char t_j; // jth character of t

		int cost; // cost

		for (i = 0; i <= n; i++) {
			p[i] = i;
		}

		for (j = 1; j <= m; j++) {
			t_j = t.charAt(j - 1);
			d[0] = j;

			for (i = 1; i <= n; i++) {
				cost = s.charAt(i - 1) == t_j ? 0 : 1;
				// minimum of cell to the left+1, to the top+1, diagonally left
				// and up +cost
				d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
			}

			// copy current distance counts to 'previous row' distance counts
			_d = p;
			p = d;
			d = _d;
		}

		// our last action in the above loop was to switch d and p, so p now
		// actually has the most recent cost counts
		return p[n];
	}

	/**
	 * Envoie O/N ou nulm
	 * 
	 * @param bool
	 * @return
	 */
	public static String getStringFrenchBoolean(Boolean bool) {
		String result = null;
		if (bool != null && bool) {
			result = "O";
		} else {
			result = "N";
		}
		return result;
	}

	/**
	 * Envoie true si bool vaut 'O' ou Y
	 * 
	 * @param bool
	 * @return
	 */
	public static Boolean getFrenchBoolean(String bool) {
		Boolean result = null;
		if (bool != null && ("O".equalsIgnoreCase(bool.trim()) || "Y".equalsIgnoreCase(bool.trim()))) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Envoie true si bool vaut 'N'
	 * 
	 * @param bool
	 * @return
	 */
	public static Boolean getNotFrenchBoolean(String bool) {
		Boolean result = null;
		if (bool != null && "N".equalsIgnoreCase(bool.trim())) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Decode a businessKey into a property : key=value, key=value, ...
	 * 
	 * @return
	 * @throws JrafApplicativeException
	 */
	public static Properties decodeBusinessKey(String myBusinessKey, String mySeparator, String mySepKeyVal) {
		Properties myProperties = new Properties();

		if (myBusinessKey != null) {
			StringTokenizer st = new StringTokenizer(myBusinessKey, mySeparator);

			while (st.hasMoreTokens()) {

				String intermediateString = st.nextToken();
				if (!intermediateString.equals("")) {
					if (SicStringUtils.log.isDebugEnabled()) {
						SicStringUtils.log.debug("decodeBusinessKey : " + intermediateString);
					}
					StringTokenizer stKeyVal = new StringTokenizer(intermediateString.trim(), mySepKeyVal);
					String sKey = null;
					String sValue = null;

					if (stKeyVal.hasMoreTokens()) {
						sKey = stKeyVal.nextToken();
					}
					if (stKeyVal.hasMoreTokens()) {
						sValue = stKeyVal.nextToken();
					}

					if (sKey != null && sValue != null) {
						myProperties.put(sKey, sValue);
					}
				}
			}
		}
		return myProperties;
	}

	public static String trimLeft(String stringToTrim, String stringToRemove) {
		String result = stringToTrim;

		while (result.startsWith(stringToRemove)) {
			result = result.substring(stringToRemove.length());
		}

		return result;
	}

	public static String getStringFromObject(Object obj) {

		if (obj == null) {
			return null;
		}

		return obj.toString();
	}

	public static Integer getIntegerFromString(String str) {

		Integer result = null;

		if (StringUtils.isNotEmpty(str)) {
			result = Integer.valueOf(str);
		}

		return result;
	}

	public static String truncate(String str, int maxLenght) {

		if (StringUtils.isEmpty(str) || str.length() < maxLenght) {
			return str;
		}

		return str.substring(0, maxLenght - 1);
	}

	// Truncate on the left 123 => 23 and ABC => BC
	public static String leftTruncate(String stringToTruncate, int lengthTotruncate) {

		if (stringToTruncate != null && stringToTruncate.length() > lengthTotruncate) {
			stringToTruncate = stringToTruncate.substring(stringToTruncate.length() - lengthTotruncate);
		}

		return stringToTruncate;
	}

	//
	public static String cleanBadChar(String s) {
		// Beware of NPE
		if (s != null) {
			s = s.replace('\'', ' ');
			if (!StringUtils.isAlphanumeric(s) || NormalizedStringUtilsV2.isNormalizableString(s)) {
				s = NormalizedStringUtilsV2.normalizeString(s);
				String ret = "";
				for (char c : s.toCharArray()) {
					if (CharUtils.isAsciiAlphanumeric(c)) {
						ret += c;
					} else {
						ret += " ";
					}
				}
				s = ret;
			}
		} else {
			s = "";
		}
		return s;
	}

	/**
	 * Adding @param Lenght zero to @param pStr
	 * 
	 * @param pStr
	 * @param pLenght
	 * @return
	 */
	public static String addingZeroToTheLeft(String pStr, int pLenght) {
		return StringUtils.leftPad(pStr, pLenght, "0");
	}
	
	/**
	 * Deleting zero on the left for @param
	 * 
	 * @param pStr
	 * @return
	 */
	public static String deleteZeroToTheLeft(String pStr) {

		if (pStr != null) {
			while (pStr.length() > 1 && pStr.startsWith("0")) {
				pStr = pStr.substring(1, pStr.length());
			}
		}

		return pStr;
	}

	/**
	 * Return only two last char from @param
	 * 
	 * @param pStr
	 * @return
	 */
	public static String truncToTwoRightChar(String pStr) {

		if (pStr != null) {

			int l = pStr.length();
			if (l > 2) {
				pStr = pStr.substring(l - 2, l);
				pStr = deleteZeroToTheLeft(pStr);
			}
		}

		return pStr;
	}

	/**
	 * Transforms the {@link Long} GIN/CIN to a 12 characters String. It adds 0
	 * to reach 12 if necessary.
	 * 
	 * @return
	 */
	public static String longIdentifierNumberToString(Long cin) {
		return SicStringUtils.addingZeroToTheLeft(cin.toString(), 12);
	}

	/**
	 * Test if a string is a matricule
	 * 
	 * @param str
	 * @return true if str is a matricule, false otherwise
	 */
	public static boolean isMatricule(String str) {
		return str != null && str.matches("[a-zA-Z]?[0-9]+");
	}

	public static String removeSpecialChars(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		// remove special chars
		str = str.replaceAll("[^\\w| |\\-|']", "");
		return str;
	}
	
	public static String replaceAccentAndRemoveSpecialChars(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		// convert accents to non accents
		String result = NormalizedStringUtils.replaceAccent(str);

		// remove special chars
		result = result.replaceAll("[^\\w| |\\-|']", "");
		return result;
	}

	public static String replaceNonPrintableChars(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		
		// replace accentuation
		String result = NormalizedStringUtils.replaceAccent(str);
		
		// remove                         NON ASCII     Control char
		result = result.replaceAll("[[^\\x00-\\xFF][\\p{Cntrl}\\p{C}\\p{Cc}\\p{Cf}\\p{Co}\\p{Cn}\\r\\n\\t]]", " ");
		return result;
	}
	
	public static String replaceNonPrintableChars(String str, String replacement) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		
		if (StringUtils.isBlank(replacement)) {
			replacement = " ";
		}
		
		// Replace accentuation
		String result = NormalizedStringUtils.replaceAccent(str);
		
		// Remove NON ASCII control char
		result = result.replaceAll("[[^\\x00-\\xFF][\\p{Cntrl}\\p{C}\\p{Cc}\\p{Cf}\\p{Co}\\p{Cn}\\r\\n\\t]]", replacement);
		return result;
	}
	
	// Expected format for preference data is dd/MM/yyyy
	public static boolean isValidPreferenceData(String value) {
		String expression = "^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)\\/(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)\\d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$";
		CharSequence inputStr = value;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isBoolean(String value) {
		return ("O".equalsIgnoreCase(value) || "Y".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value)
				|| "N".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value));
	}

	/**
	 * Envoie true si chaine contient que des caractères iso Latin
	 * 
	 * @return
	 */
	public static boolean isISOLatinString(String chaine) {
		boolean result = true;
		if (chaine != null) {
			for (char car : chaine.toCharArray()) {
				if (!isISOLatinCharacter(car)) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Envoie true si car est un caractère iso Latin
	 * 
	 * @return
	 */
	public static boolean isISOLatinCharacter(char car) {
		return ((car == 32) || // space
				(car == 39) || // '
				(car == 45) || // -
				(car == 46) || // .
				(car == 95) || // _
				(car >= 'A' && car <= 'Z') || // A-Z
				(car >= 'a' && car <= 'z') || // a-z
				(car >= '0' && car <= '9') || // 0-9
				(car >= 192 && car <= 255) // Lettres capitales accentuées
											// Latin-1 & // lettres Bas de casse
											// accentuées Latin-1
		) && ( // sauf
		(car != 247) && // ÷
				(car != 248) && // ø
				(car != 222) && // þ
				(car != 215) // ×
		);
	}

	/**
	 * Check if the string match with patterns passed in parameters
	 *
	 * @param string,
	 *            the string to check
	 * @param patterns,
	 *            pattern to match
	 * @return If OK or NOT
	 */
	public static boolean isMatchingStringWithPatterns(String string, List<String> patterns) {
		Matcher generatedString;
		Pattern regex;
		boolean isMatching = true;

		// Si aucun pattern est passé, on return true
		if (patterns == null || patterns.isEmpty()) {
			return true;
		}

		for (int i = 0; i < patterns.size(); i++) {
			if (StringUtils.isNotBlank(patterns.get(i))) {
				try {
					regex = Pattern.compile(patterns.get(i));
					generatedString = regex.matcher(string);
					isMatching = isMatching && generatedString.matches();
				} catch (PatternSyntaxException e) {
					SicStringUtils.log.warn("Pattern '" + patterns.get(i) + "' is not good and usable !");
				}
			}
		}

		return isMatching;
	}

	// REPIND-1288 : Send back a Normalize email form, in case of a search or a store.
	public static String normalizeEmail(String email) {

		if (email != null) {
			email = hardTrim(email);
			email = email.trim(); // Delete starting whitespace and ending
									// whitespace
			email = email.toLowerCase(); // Put email in lower case

			return email;
		}

		return null;
	}

	// REPIND-1767 : Delete UTF8 char
	public static String normalizeEmailOnlyASCII(String email) {
		email = normalizeEmail(getASCII(email));
		
		return email;
	}

	private static String hardTrim(String toBlank) {

		Pattern pattern = Pattern.compile(regexCleanEmail);
		Matcher matcher = pattern.matcher(toBlank);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return toBlank;
		}
	}

	/**
	 * Masking using PCIDSS or custom method
	 */
	// REPIND-1175 : PCIDSS Check length of input number in order to mask CCNR
	// Boolean seems to be a masking since first char (FALSE) or not (TRUE)
	public static String maskingPCIDSS(String fieldPreferences) {
		return maskingPCIDSS(fieldPreferences, true);
	}
	
	public static String maskingPCIDSS(String fieldPreferences, boolean f) {

		if (fieldPreferences != null && (fieldPreferences.length() == 15 || fieldPreferences.length() == 16)) {		
			int fieldSize = fieldPreferences.length();
			String response="";
			for(int i =0;i<fieldPreferences.length();i++){
				if(!(fieldPreferences.charAt(i)==' ')){
					if(i==6 && fieldSize>4 && f) {
						response += 'C';
					} else if(i>5 && fieldSize>4 && f) {
						response += 'X';
					} else if (fieldSize>4 && !f && i==0) {
						response += 'C';
					} else if (fieldSize>4 && !f) {
						response += 'X';
					} else {
						response += fieldPreferences.charAt(i);
					}
				} else {
					response += ' ';
				}
				fieldSize--;
			}
			return response;
		} else {
			return fieldPreferences;
		}
	}
	
	// REPIND-1441 : Add some Business Trace in Log for watching Who are using What 
	public static final String TraceInputConsumer(WebServiceContext context) {
		String retour = "";
		
		Header header = ReadSoaHeaderHelper.getHeaderFromContext(context, "trackingMessageHeader");
		String consumerID = "";		
		try {
			consumerID = ReadSoaHeaderHelper.getHeaderChildren(header, "consumerID");
		} catch (SOAPException e) {
			consumerID = "ERROR " + e.getMessage();
		}	

		retour += "CONS:" + WriteNull(consumerID) + "; ";
		return retour;
	}

	public static final String TraceInputRequestor(String channel, String matricule, String officeId, String site, String signature, String appicationCode) {
		String retour = "";

		retour += "CHA:" + WriteNull(channel) + "; ";
		retour += "MAT:" + WriteNull(matricule) + "; ";
		retour += "OFF:" + WriteNull(officeId) + "; ";
		
		retour += "SIT:" + WriteNull(site) + "; ";
		retour += "SIG:" + WriteNull(signature) + "; ";
		retour += "APP:" + WriteNull(appicationCode) + "; ";
		
		return retour;
	}

	public static final String TraceInputScopeToProvide(List<String> scopeToProvide) {
		String retour = "";
		if (scopeToProvide != null && scopeToProvide.size() > 0) {
			retour += "STP:";
			for (String stp : scopeToProvide) {
				retour += WriteNull(stp) + "|";
			}
			retour = retour.substring(0, retour.length()-1);
			retour += "; ";
		}		
		return retour;
	}

	public static final String TraceInputScope(String scope) {
		String retour = "";
		if (scope != null) {
			retour += "STP:" + WriteNull(scope) + "; ";
		}		
		return retour;
	}

	public static final String TraceInputOptionIdentificationNumber(String option, String identificationNumber) {
		String retour = "";
		if (option != null) {
			retour += "OPT:" + WriteNull(option) + "; ";
		} else {

		}
		if (identificationNumber != null) {
			retour += " ID:" + identificationNumber + "; ";
		}
		return retour;
	}

	private static String WriteNull(String chaine, String defaut) {
		if (chaine == null) {
			return defaut;
		} else {
			return chaine;
		}
	}

	public static String WriteNull(String chaine) {
		return WriteNull(chaine, "");
	}

	// Return True if some non ASCII char are find !
	public static boolean isNonASCII(String chaine) {
		if (chaine == null) {
			return false;
		}
	    String nonAscii = chaine.replaceAll("[\\p{ASCII}]", "");
	    return (nonAscii != null && !"".equals(nonAscii)); 
	}
}
