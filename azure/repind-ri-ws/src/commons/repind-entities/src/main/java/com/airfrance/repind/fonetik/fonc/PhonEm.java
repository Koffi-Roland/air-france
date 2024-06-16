package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhoVoy;

public class PhonEm implements IPhonetikFonction {

	/**
	 *  Elimination des M/N si position 0 et suivi de CONSONNE != C/H
	 */
	public String process(String tampon) {
		String result = tampon;
	    if(result.length()>1 && ((result.charAt(0) == 'M') || (result.charAt(0) == 'N')))
	    {
	        if((PhoVoy.isConsonne(result.charAt(1))) && (result.charAt(1) != 'H') && (result.charAt(1) != 'C'))
	        {
	            result = result.substring(1);
	        }
	    }
		return result;
	}

}
