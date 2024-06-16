package com.airfrance.repind.fonetik.fonc;

public class PhonEG implements IPhonetikFonction {

	/**
	 * Remplace les G de Tampon par un K
	 */
	public String process(String tampon) {		
		return tampon.replaceAll("G", "K");
	}

}
