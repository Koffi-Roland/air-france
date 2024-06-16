package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhoVoy;
import com.airfrance.repind.util.NormalizedStringUtils;

public class PhonTD implements IPhonetikFonction {

	/**
	 * Elimination des T/D suivi de consonne sauf si :
     *                   - position = 0
     *                   - suivant  = R
     *                   - suivant  = H
     *                   - avant derniere position
	 */
	public String process(String tampon) {
		String result = tampon;
		for (int indiceTampon = 1; indiceTampon < result.length()-1; indiceTampon++) {
            if((result.charAt(indiceTampon) == 'T') || (result.charAt(indiceTampon) == 'D'))
            {
                if((PhoVoy.isConsonne(result.charAt(indiceTampon+1))) && (result.charAt(indiceTampon+1) != 'R') && (result.charAt(indiceTampon+1) != 'H'))
                {
                	if(indiceTampon+2 < result.length())
                    {
						result = NormalizedStringUtils.removeCharAt(result, indiceTampon);
                    }
                }
            }
		}
		return result;
	}

}
