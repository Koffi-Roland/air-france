package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhoVoy;

public class PhonKW implements IPhonetikFonction {

	/**
	 * Remplace les "W" précédés d'une consonne[0] par des "OU"
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		if (result.length()>1 && PhoVoy.isConsonne(result.charAt(0)) && result.charAt(1) == 'W') 
		{
			result.replace(1, 2, "OU");
		} 
		return result.toString();
	}

}
