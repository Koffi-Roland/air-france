package com.airfrance.repind.fonetik.fonc;

public class PhonEZE implements IPhonetikFonction {

	/**
	 * Supprime le E muet
	 */
	public String process(String tampon) {
		String result = tampon;
		if (result.length()>2) {
			result = result.replaceAll("E$", ".");
		}
		return result;
	}

}
