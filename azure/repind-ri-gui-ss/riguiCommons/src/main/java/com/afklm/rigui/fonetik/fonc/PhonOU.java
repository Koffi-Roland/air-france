package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.PhUtils;

public class PhonOU implements IPhonetikFonction {

	/**
	 * Remplace les OU par des U
	 */
	public String process(String tampon) {
		return PhUtils.replace(tampon, "OU", "U");
	}

}
