package com.afklm.rigui.fonetik.fonc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PhonEH implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonEH.class);
    
	/**
	 *	Supprime le H muet dans Tampon. 
     *     Ils ne doivent pas suivre un C
     *     ou un S et pas preceder un autre H 
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
				
		if (result.length()>1) {
			int indexEh = result.indexOf("H");
			while (indexEh > -1) {
				if (indexEh == 0 || 
						 (result.charAt(indexEh-1) != 'C' && result.charAt(indexEh-1) != 'S') && 
						 (indexEh+1 == result.length() || 
						 (indexEh+1 != result.length() && result.charAt(indexEh+1) != 'H'))) 
				{ 
					result.deleteCharAt(indexEh);	
					indexEh--;
				}
				indexEh = result.indexOf("H",indexEh+1);
			}				
		}
		return result.toString();
	}

}
