package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.PhoVoy;

public class PhonECC implements IPhonetikFonction {

	/**
	 * Supprime les C finaux si ceux-ci sont precedes d'une Consonne
	 */
	public String process(String tampon) {

		StringBuffer result = new StringBuffer(tampon);
		/* supprime le 'Q' final si precede par 'C' */
		int iLgTampon = result.length();

	    if(iLgTampon > 1)
	    	if ((result.charAt(iLgTampon - 1) == 'Q') && (result.charAt(iLgTampon - 2) == 'C'))
		    	result.deleteCharAt(iLgTampon - 1);

		iLgTampon = result.length();

	    if(iLgTampon-1 > 2)
	    	if (result.charAt(iLgTampon - 1) == 'C')
		    	if (PhoVoy.isConsonne(result.charAt(iLgTampon - 2)))
			    	/* cas particulier arc, irc, orc, urc, yrc */
				    if ((result.charAt(iLgTampon - 2) != 'R') ||
					     (result.charAt(iLgTampon - 3) == 'E'))
				    	result.setCharAt(iLgTampon - 1, '.');
		return result.toString();
	}

}
