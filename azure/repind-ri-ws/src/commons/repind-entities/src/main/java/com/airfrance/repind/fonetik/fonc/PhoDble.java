package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhoVoy;

public class PhoDble implements IPhonetikFonction {

	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		for (int i=0; i < result.length()-3; i++) {
			if (result.charAt(i) == result.charAt(i+2) && 
				result.charAt(i+1) == result.charAt(i+3) && 
				PhoVoy.isVoyelle(result.charAt(0)) && 
				PhoVoy.isVoyelle(result.charAt(0+1))) 
				{ 
					result.delete(i+2, i+4); 
				} 
		}
		return result.toString();
	}

}
