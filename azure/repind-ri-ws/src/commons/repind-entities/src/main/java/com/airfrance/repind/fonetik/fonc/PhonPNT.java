package com.airfrance.repind.fonetik.fonc;

public class PhonPNT implements IPhonetikFonction {

	/**
	 * Supprime les . dans la chaine tampon
	 */
	public String process(String tampon) {		
		return tampon.replaceAll("\\.", "");
	}

}
