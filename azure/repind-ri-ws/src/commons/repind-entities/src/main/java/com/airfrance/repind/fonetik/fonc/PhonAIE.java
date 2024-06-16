package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhUtils;
import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonAIE implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonAIE.class);
    
	private List<TabSubstStr> tabAie = new ArrayList<TabSubstStr>();
	public PhonAIE() {
		tabAie.add(new TabSubstStr("EIE","E."));
		tabAie.add(new TabSubstStr("AIE","E."));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabAie.Texte si elle est trouvee, le terme
	 * est remplace	par tabAie.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr aie : tabAie) {
			result = PhUtils.replace(result, aie.getTexte(), aie.getPhone());
		}
		
		return result;

	}

}
