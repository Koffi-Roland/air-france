package com.afklm.rigui.fonetik.fonc;

public class PhonECH implements IPhonetikFonction {

	/**
	 * Remplace les CH par un K 
     *     si il n'est pas precede d'un T ou d'un S 
     *     et si la lettre qui suit le H n'est pas 
     *     une voyelle 
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		for (int indiceTampon = 0; indiceTampon < result.length()-1; indiceTampon++) {
			if (result.charAt(indiceTampon) == 'C' && result.charAt(indiceTampon+1) == 'H') 
			{ 
				if (((indiceTampon == 0)  ||
					  ((result.charAt(indiceTampon-1) != 'T') &&
						(result.charAt(indiceTampon-1) != 'S')))&&

					 ((indiceTampon+2 == result.length()) ||
					  (((result.charAt(indiceTampon+2) == 'R') || (result.charAt(indiceTampon+2) == 'L')) && (result.charAt(indiceTampon+2) != '.'))
						))
				{
					result.replace(indiceTampon, indiceTampon+2, "K");
				} 
	 
				if ((indiceTampon > 0) && result.charAt(indiceTampon-1)=='S') 
				{ 
					result.deleteCharAt(indiceTampon-1);
					indiceTampon--;
				} 
			} 
		}
		return result.toString();
	}

}
