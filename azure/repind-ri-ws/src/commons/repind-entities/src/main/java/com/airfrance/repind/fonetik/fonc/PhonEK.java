package com.airfrance.repind.fonetik.fonc;


public class PhonEK implements IPhonetikFonction {

	/**
	 * Remplace les C qui ne precedent 
     *     pas un H en un K 
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		if (result.length()==1 && result.charAt(0) == 'C') {
			result.setCharAt(0, 'K');
		} else {
			for (int indiceTampon = 0; indiceTampon < result.length(); indiceTampon++) {
				if (result.charAt(indiceTampon) == 'C' &&
					(indiceTampon == result.length()-1 || result.charAt(indiceTampon+1) != 'H')) 
						result.setCharAt(indiceTampon, 'K');
			}					
		}
		return result.toString();
	}

}
