package com.airfrance.repind.fonetik.fonc;

public class PhonESH implements IPhonetikFonction {

	/**
	 * Remplace le SH par CH.
	 */
	public String process(String tampon) {		
		return tampon.replaceAll("SH", "CH");
	}

}
