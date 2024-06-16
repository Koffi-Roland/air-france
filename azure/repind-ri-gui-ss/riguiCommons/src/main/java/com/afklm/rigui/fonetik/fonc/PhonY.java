package com.afklm.rigui.fonetik.fonc;

public class PhonY implements IPhonetikFonction {

	/**
	 * Remplace le Y par un I 
	 */
	public String process(String tampon) {		
		return tampon.replaceAll("Y", "I");
	}

}
