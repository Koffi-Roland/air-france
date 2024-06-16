package com.afklm.rigui.fonetik.fonc;

public class PhonCtrl {
	public static String Phonctrl(String str) {
		String result = str;
		if (!result.matches("[A-Z/\\\\0-9\\-éèêë]+")) {
			result = "???????????????";
		} else {
			result = result.replaceAll("[0-9\\-]", " ");
		}
		return result;
	}
}
