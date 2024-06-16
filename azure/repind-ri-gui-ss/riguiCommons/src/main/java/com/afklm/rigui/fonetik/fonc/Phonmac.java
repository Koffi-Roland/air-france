package com.afklm.rigui.fonetik.fonc;

public class Phonmac implements IPhonetikFonction {
	/**
	 * Remplace MC par MAC
	 * 
	 */
	public String process(String tampon) {
		String result = tampon;
		if ( result.length()>1 && result.charAt(0) == 'M' && result.charAt(1) == 'C') {
			result =result.replaceFirst("MC", "MAC");
		}
		return result;
	}

}
