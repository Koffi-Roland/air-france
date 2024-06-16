package com.afklm.rigui.repindutf8.util;

import com.afklm.rigui.exception.jraf.JrafApplicativeException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SicUtf8StringUtils {
	/**
	 * Utilisée lors du passage de paramètres à une méthode 
	 * lorsqu'on veut modifier la valeur du paramètre 
	 */
	private String val;
	
	private static Log log  = LogFactory.getLog(SicUtf8StringUtils.class);
	
	

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}
	
	
	/**
	 * Check if the email is valid.
	 * @param email  : email a validé.
	 * @return vrai si l'email est valide.
	 * @throws JrafApplicativeException
	 */
	public static boolean isValidEmail(String email) throws JrafApplicativeException {
		boolean isValid = false;

		/*
		Email format: A valid email address will have following format:
		        [\\w\\.-]+: Begins with word characters, (may include periods and hypens).
			@: It must have a '@' symbol after initial characters.
			([\\w\\-]+\\.)+: '@' must follow by more alphanumeric characters (may include hypens.).
		This part must also have a "." to separate domain and subdomain names.
			[A-Z]{2,4}$ : Must end with two to four alaphabets.
		(This will allow domain names with 2, 3 and 4 characters e.g pa, com, net, wxyz)

		Examples: Following email addresses will pass validation
		abc@xyz.net; ab.c@tx.gov
		*/

//		Initialize reg ex for email.
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;
//		Make the comparison case-insensitive.
		Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if(matcher.matches()){
		isValid = true;
		}
		return isValid;

	}
	
	/**
	 * Check if the email is valid with the well-formed format.
	 * @param email  : email a validé.
	 * @return vrai si l'email est valide.
	 * @throws JrafApplicativeException
	 */
	public static boolean isValidEmailV2(String email) throws JrafApplicativeException {
		boolean isValid = false;

//		Initialize reg ex for email.
		String expression = "^[_a-zA-Z0-9\\-]+(\\.[_a-zA-Z0-9\\-]+)*@[a-zA-Z0-9\\-]+(\\.[a-zA-Z0-9\\-]+)*\\.[a-zA-Z]{2,6}$";
		CharSequence inputStr = email;
//		Make the comparison case-insensitive.
		Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if(matcher.matches()){
		isValid = true;
		}
		return isValid;
	}
	
	/**
	 * Check if the fb identifier is valid
	 * @param fbIdent
	 * @return
	 * @throws JrafApplicativeException
	 */
	public static boolean isValidFbIdentifier(String fbIdent) throws JrafApplicativeException {
		boolean isValid = true;
		if (fbIdent.length() != 12 || !isNumeric(fbIdent)) {			
			isValid = false;
		}			
		return isValid;
	}
	
	/**
	 * Check if the GIN is valid
	 * @param gin
	 * @return
	 * @throws JrafApplicativeException
	 */
	public static boolean isValidGIN(String gin) throws JrafApplicativeException {
		boolean isValid = true;
		if (gin.length() != 12 || !isNumeric(gin)) {			
			isValid = false;
		}			
		return isValid;
	}
	
	/**
	 * Check if the string is valid
	 * @param myaccntIdent
	 * @return
	 *  @throws JrafApplicativeException
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
		if(matcher.matches()){
			isValid = true;
		}
		return isValid;
	}
	
	/**
	 * Check if the string is numeric
	 * @param s
	 * @return
	 */
    public static boolean isNumeric(String s) {
        return s.matches("\\d+");
    }
    
    /**
     * Envoie true si chaine contient que des caractères iso Latin
     * @param bool
     * @return
     */
    public static boolean isISOLatinString(String chaine) {
    	boolean result = true;
    	if (chaine!=null) {
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
     * @param bool
     * @return
     */
    public static boolean isISOLatinCharacter(char car) {
    	return ( (car == 32) || 					// space
   			     (car == 39) ||					    // '
    			 (car == 45) || 					// -
    			 (car == 46) || 					// .
    			 (car == 95) ||					    // _
    			 (car >= 'A' && car <= 'Z') || 		// A-Z
    			 (car >= 'a' && car <= 'z') ||		// a-z 
    			 (car >= '0' && car <= '9') ||      // 0-9
    			 (car >= 192 && car <= 255) 		// Lettres capitales accentuées Latin-1 &    			 									// lettres Bas de casse accentuées Latin-1
    			) && ( // sauf  
    			  (car != 247) &&					// ÷
    			  (car != 248) &&					// ø
    			  (car != 222) &&					// þ
    			  (car != 215)						// ×
    			);
    }
    
    public static String ReplaceSpecialChar(String str){
    	if (StringUtils.isBlank(str)) {
			return str;
		}
    	
    	String sNewchaine = str;
    	sNewchaine = sNewchaine.replaceAll("[ÀÁÂÄ]","A");
    	sNewchaine = sNewchaine.replaceAll("[àáâäã]","a");
    	sNewchaine = sNewchaine.replaceAll("Ç","C");
    	sNewchaine = sNewchaine.replaceAll("ç","c");
    	sNewchaine = sNewchaine.replaceAll("[ÈÉÊË]","E");
    	sNewchaine = sNewchaine.replaceAll("[èéêë]","e");
    	sNewchaine = sNewchaine.replaceAll("[ÌÍÎÏ]","I");
    	sNewchaine = sNewchaine.replaceAll("[ìíîï]","i");
    	sNewchaine = sNewchaine.replaceAll("[ÒÓÔÖ]","O");
    	sNewchaine = sNewchaine.replaceAll("[òóôö]","o");
    	sNewchaine = sNewchaine.replaceAll("[ÙÚÛÜ]","U");
    	sNewchaine = sNewchaine.replaceAll("[üùúû]","u");
    	sNewchaine = sNewchaine.replaceAll("Ý","Y");
    	sNewchaine = sNewchaine.replaceAll("ß","?");
    	sNewchaine = sNewchaine.replaceAll("[ýÿ]","y");
    	sNewchaine = sNewchaine.replaceAll("&#[0-9]+","");
    	sNewchaine = sNewchaine.replaceAll("[\\^\"ª]","");
    	
    return sNewchaine;
    }

    /**
     * Envoie true si chaine comporte un caractère non ASCII
     * @param bool
     * @return
     */
    // REPIND-1767 : Detect non ASCII char
    public static boolean isNonASCII(String chaine) { 
    	if (chaine == null) {
    		return false;
    	}
    	
    	// We delete all the ASCII char ! So what is kept is non ASCII char... 
    	String nonAsciiIND = chaine.replaceAll("[\\p{ASCII}]", "");
    	return !nonAsciiIND.isEmpty();		// Empty = ASCII
    }


    /**
     * Envoie true si chaine comporte une expression HTML
     * @param bool
     * @return
     */
    // REPIND-1528 : Detect HTML char
    public static boolean isHTML(String chaine) { 
    	if (chaine == null) {
    		return false;
    	}
    	
		CharSequence inputStr = chaine;
		// Commence par ce que l on veux puis un "&" suivi de lettre min ou maj ou bien de "#" et de chiffre terminant par ";" et peu importe la fin
		Pattern pattern = Pattern.compile("^.*&(?:[a-zA-Z]+|#x?[0-9]+|#x?[0-9a-fA-F]+);.*$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		boolean trouve = matcher.find();
		
		return trouve;
    }
}
