package com.afklm.rigui.util;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.fonetik.PhEntree;
import com.afklm.rigui.fonetik.PhonetikInput;
import org.apache.commons.lang.StringUtils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NormalizedStringUtils {

	/**
	 * Normalizable characters:
	 * 
	 * <ul>
	 * 	<li>space [ ]</li>
	 * 	<li>single quote [']</li>
	 * 	<li>hyphen [-]</li>
	 * 	<li>dot [.]</li>
	 *  <li>figures [0-9]</li>
	 * 	<li>upper-case letters [A-Z]</li>
	 * 	<li>underscore [_]</li>
	 * 	<li>lower-case letters [a-z]</li>
	 * 	<li>extended characters [ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝßàáâãäåæçèéêëìíîïðñòóôõöùúûüýþÿ]</li>
	 * </ul>
	 */
	public static final String NORMALIZABLE_CHARS = "^[ '-.0-9A-Z_a-zÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝßàáâãäåæçèéêëìíîïðñòóôõöùúûüýþÿ]*$";
	
	/**
	 * Deleted characters:
	 * 
	 * <ul>
	 * 	<li>HTML codes "&#[0-9]+;"</li>
	 *  <li>circumflex accent [^]</li>
	 *  <li>single quote [']</li>
	 *  <li>semicolon [;]</li>
	 *  <li>double quote ["]</li>
	 *  <li>a superscript [ª]</li>
	 * </ul>
	 */
	private static final List<String> DELETION_LIST = buildDeletionList();
	
	/**
	 * Transformed characters:
	 * 
	 * <ul>
	 * 	<li>[ÀÁÂÄ] -> "A"</li>
	 * 	<li>[àáâä] -> "a"</li>
	 * 	<li>[Ç] -> "C"</li>
	 * 	<li>[ç] -> "c"</li>
	 * 	<li>[ÈÉÊË] -> "E"</li>
	 * 	<li>[èéêë] -> "e"</li>
	 * 	<li>[ÌÍÎÏ] -> "I"</li>
	 * 	<li>[ìíîï] -> "i"</li>
	 * 	<li>[ÒÓÔÖ] -> "O"</li>
	 * 	<li>[òóôö] -> "o"</li>
	 * 	<li>[ÙÚÛÜ] -> "U"</li>
	 * 	<li>[üùúû] -> "u'</li>
	 * 	<li>[Ý] -> "Y"</li>
	 * 	<li>[ýÿ] -> "y"</li>
	 * 	<li>[ß] -> "SS"</li>
	 * </ul>
	 */
	private static final Map<String,String> TRANSFORMATION_MAP = buildTransformationMap();

	/**
	 * Check if the provided string is supported by the string normalizer
	 * 
	 * <p>
	 * 	Only following characters are normalizable: {@link #NORMALIZABLE_CHARS}
	 * </p>
	 * 
	 * @param str not normalized string
	 * @return string normalizable or not
	 */
	public static boolean isNormalizableString(String str) {
		
		if(StringUtils.isEmpty(str)) {
			return true;
		}
		
		return str.matches(NORMALIZABLE_CHARS);
	}
	
	/**
	 * Normalize the provided string according following process:
	 * 
	 * <ol>
	 * 	<li>Remove following characters: {@link #DELETION_LIST}</li>
	 * 	<li>Transform following characters: {@link #TRANSFORMATION_MAP}</li>
	 * </ol>
	 * 
	 * @param str not normalized string
	 * @return normalized string
	 * @throws IllegalArgumentException
	 */
	public static String normalizeString(String str) {
	
		if(StringUtils.isEmpty(str)) {
			return null;
		}
				
		// remove special characters
		for(String regex : DELETION_LIST) {
			str = str.replaceAll(regex, "");
		}
		
		// REPIND-1623 : replace accent
		str = replaceAccent(str);
		
		// replace special characters
		for(Map.Entry<String, String> association : TRANSFORMATION_MAP.entrySet()) {
			str = str.replaceAll(association.getKey(), association.getValue());
		}
		
		return str;
	}

	/**
	 * Build transformation map between special and normalized characters
	 * 
	 * @return transformation map
	 */
	private static Map<String,String> buildTransformationMap() {
		
		Map<String,String> map = new HashMap<String, String>();
		
		map.put("[ÀÁÂÄ]","A");
		map.put("[àáâä]","a");
		map.put("[Ç]","C");
		map.put("[ç]","c");
		map.put("[ÈÉÊË]","E");
		map.put("[èéêë]","e");
		map.put("[ÌÍÎÏ]","I");
		map.put("[ìíîï]","i");
		map.put("[ÒÓÔÖ]","O");
		map.put("[òóôö]","o");
		map.put("[ÙÚÛÜ]","U");
		map.put("[üùúû]","u");
		map.put("[Ý]","Y");
		map.put("[ýÿ]","y");
		map.put("[ß]","SS");
		
		return map;
	}
	
	/**
	 * Build deletion list
	 * 
	 * @return deletion list
	 */
	private static List<String> buildDeletionList() {
		
		List<String> list = new ArrayList<String>();
		
		list.add("&#[0-9]+;");
		list.add("[\\^';\"ª]");
		
		return list;
	}

	public static String replaceAccent(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		// convert accents to non accents
		String result = Normalizer.normalize(str, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
		return result;
	}

	public static String PhonetiseChaine(String ident, boolean isIndiv) throws JrafDomainException {
		PhonetikInput input = new PhonetikInput();
		input.setIdent(ident);
		int iCodRet = PhEntree.Fonetik_Entree(input);

		if (iCodRet <= 2) { // Phonetisation Ok
			return input.getIndict();
		} else {
			if (isIndiv) {
				// erreur phonetisation nom/prenom
				throw new JrafDomainException("123");
			} else {
				throw new JrafDomainException("124");
			}
		}
	}

	public static String removeCharAt(String s, int pos) {
		StringBuffer buf = new StringBuffer(s.length() - 1);
		buf.append(s.substring(0, pos)).append(s.substring(pos + 1));
		return buf.toString();
	}
}
