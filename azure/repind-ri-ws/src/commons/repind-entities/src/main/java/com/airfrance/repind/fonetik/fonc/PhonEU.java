package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhUtils;
import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonEU implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonEU.class);
    
	private List<TabSubstStr> tabNeu = new ArrayList<TabSubstStr>();
	public PhonEU() {
		tabNeu.add(new TabSubstStr("EU","E."));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabNeu.Texte si elle est trouvee, le terme
	 * est remplace	par tabNeu.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr neu : tabNeu) {
			result = PhUtils.replace(result, neu.getTexte(), neu.getPhone());
		}
		
		return result;

	}

}
