package com.airfrance.repind.fonetik.fonc;


public class PhonDbs implements IPhonetikFonction {

	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		for (int i=0; i < result.length()-1; i++) {
			if (result.charAt(i) == result.charAt(i+1)) 
				{ 
					result.deleteCharAt(i);
					i--;
				} 
		}
		return result.toString();
	}

}
