package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhUtils;

public class PhonOU implements IPhonetikFonction {

	/**
	 * Remplace les OU par des U
	 */
	public String process(String tampon) {
		return PhUtils.replace(tampon, "OU", "U");
	}

}
