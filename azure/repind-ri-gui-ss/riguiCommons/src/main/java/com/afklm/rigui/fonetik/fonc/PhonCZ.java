package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.PhUtils;
import com.afklm.rigui.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonCZ implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonCZ.class);
    
	private List<TabSubstStr> tabCz = new ArrayList<TabSubstStr>();
	public PhonCZ() {
		tabCz.add(new TabSubstStr("KZ","KS"));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabCz.Texte si elle est trouvee, le terme
	 * est remplace	par tabCz.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr cz : tabCz) {
			result = PhUtils.replace(result, cz.getTexte(), cz.getPhone());
		}
		
		return result;

	}

}
