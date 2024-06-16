package com.afklm.rigui.fonetik.fonc;

public class PhonApos implements IPhonetikFonction {

	/**
	 * Supprime les apostrophes dans Tampon
	 */
	public String process(String tampon) {
		String result = tampon;
		result = result.replaceAll("'[ ]*", "");
		return result;
	}

}
