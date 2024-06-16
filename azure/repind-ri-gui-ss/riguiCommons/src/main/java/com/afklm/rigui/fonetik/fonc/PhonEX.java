package com.afklm.rigui.fonetik.fonc;

public class PhonEX implements IPhonetikFonction {

	/**
	 * 
	 */
	public String process(String tampon) {
		String result = tampon;
		result = result.replaceAll("X", "KS");
		return result;
	}

}
