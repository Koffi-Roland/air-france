package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonSL implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonSL.class);
    
	private List<TabSubstStr> tabSl = new ArrayList<TabSubstStr>();
	public PhonSL() {
		tabSl.add(new TabSubstStr("SL","L"));
		tabSl.add(new TabSubstStr("SN","N"));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabSl.Texte si elle est trouvee, le terme
	 * est remplace	par tabSl.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		if (tampon.length()>2) {
			result = tampon.substring(1);
			
			for (TabSubstStr sl : tabSl) {
				result = result.replaceAll(sl.getTexte(), sl.getPhone());
			}
			
			result = tampon.charAt(0) + result;			
		} 
		
		return result;

	}

}
