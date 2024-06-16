package com.afklm.rigui.fonetik;


public class PhUtils {

	private static final String regexCarSeparateur = "[\"/\\-&\\(\\)\\+\\.,'_=~#\\{\\[\\|`\\^\\]\\}ú$£%\\*µ:!;\\?<> ]";
	public static String[] ExtraitMots(String str, String separateur) {
		return str.split(separateur);
	}
	
	public static String extraitConsonnes(String str) {
		return str.replaceAll("[AEIOUY]", "");
	}
	
	public static String enleveBlancs(String str) {
		String result = str.replaceAll("[ ]{2,}", " ");
		return result.trim();
	}
	
	public static String replace(String str, String expr, String substitute) {
		String result = str.replaceAll(expr, substitute);
		while(!str.equals(result)) {
			str = result;
			result = result.replaceAll(expr, substitute);
		}
		return result;
	}

}

