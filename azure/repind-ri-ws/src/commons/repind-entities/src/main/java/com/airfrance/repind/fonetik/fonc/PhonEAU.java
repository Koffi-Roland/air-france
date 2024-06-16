package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhUtils;
import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonEAU implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonEAU.class);
    
	private List<TabSubstStr> tabEau = new ArrayList<TabSubstStr>();
	public PhonEAU() {
		tabEau.add(new TabSubstStr("EAU","O"));
		tabEau.add(new TabSubstStr("AU","O"));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabEau.Texte si elle est trouvee, le terme
	 * est remplace	par tabEau.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr eau : tabEau) {
			result = PhUtils.replace(result, eau.getTexte(), eau.getPhone());
		}
		
		return result;

	}

}
