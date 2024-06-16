package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.PhoVoy;

public class PhonEY implements IPhonetikFonction {

	/**
	 * Transforme tous les Y en un I si : 
     *      ce dernier precede ou suit une consonne 
     *
	 *	    Si le caractere precedent est un A, il 
	 *		    devient alors E 
     * 
     *      Si le suivant est un U et celui qui suit 
     *      est != de U et de . alors le suivant de Y 
     *      devient un . (ex : FAYENT -> FEY.NT) 
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		for (int indiceTampon = 0; indiceTampon < result.length(); indiceTampon++) {
			if (result.charAt(indiceTampon) == 'Y' && indiceTampon>0) 
			{
				/* Y avec une consonne   droite ou   gauche */ 
				if ( PhoVoy.isConsonne(result.charAt(indiceTampon-1)) || 
					 indiceTampon+1 == result.length() ||
					 PhoVoy.isConsonne(result.charAt(indiceTampon+1))
					) 
					result.setCharAt(indiceTampon, 'I'); 
	 
	 
				/* Y suivant un A */ 
				if (result.charAt(indiceTampon-1) == 'A') 
					result.setCharAt(indiceTampon-1, 'E');
	 
	 
				/* Y precedent un E qui ne precede pas un U ou un . */ 
				if ( indiceTampon < result.length()-2 && ((result.charAt(indiceTampon+1) == 'E') && 
					 (result.charAt(indiceTampon+2) != 'U' && result.charAt(indiceTampon+2) != '.'))) 
					result.setCharAt(indiceTampon+1, '.');
	 
			}
		}
		return result.toString();
	}

}
