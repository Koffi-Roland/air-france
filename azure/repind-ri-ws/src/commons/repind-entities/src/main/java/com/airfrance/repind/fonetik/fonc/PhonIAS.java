package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhoVoy;

public class PhonIAS implements IPhonetikFonction {

	/**
	 * Transforme tous les S compris entre 2 
     *     voyelles en un Z 
     *     Par ailleurs, si ce S est final et positionne  
     *     apres une consonne, il est automatiquement enleve  s'il 
     *     ne suit pas un S 
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		
		int txtLength = result.length();
		for (int i = 1; i < txtLength; i++) {
			if (result.charAt(i) == 'S')
			{
				/* S coince entre deux voyelles*/
				if (i<txtLength-1 && PhoVoy.isVoyelle(result.charAt(i-1)) && PhoVoy.isVoyelle(result.charAt(i+1)))
					result.setCharAt(i, 'Z');

				else

				/* S final place apres une consonne differente de S*/
				if ( i == txtLength-1 &&
					 PhoVoy.isConsonne(result.charAt(i-1)) &&
					 result.charAt(i-1) != 'N'  &&
					 result.charAt(i-1) != 'S')
				{
					result.deleteCharAt(i);
					break;
				}

			}
			
		}
				
		return result.toString();
	}

}
