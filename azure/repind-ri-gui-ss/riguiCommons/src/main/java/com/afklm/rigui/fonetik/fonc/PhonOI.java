package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.PhUtils;
import com.afklm.rigui.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonOI implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonOI.class);
    
	private List<TabSubstStr> tabOi = new ArrayList<TabSubstStr>();
	public PhonOI() {
		tabOi.add(new TabSubstStr("OUHUE", "OI"));
		tabOi.add(new TabSubstStr("OUHA",  "OI"));
		tabOi.add(new TabSubstStr("OUHE",  "OI"));
		tabOi.add(new TabSubstStr("OHE",   "OI"));
		tabOi.add(new TabSubstStr("OHA",   "OI"));
		tabOi.add(new TabSubstStr("OUE",   "OI"));
		tabOi.add(new TabSubstStr("OUA",   "OI"));
		tabOi.add(new TabSubstStr("OE",    "OI"));
		tabOi.add(new TabSubstStr("OA",    "OI"));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabOi.Texte si elle est trouvee, le terme
	 * est remplace	par tabOi.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr oi : tabOi) {
			result = PhUtils.replace(result, oi.getTexte(), oi.getPhone());
		}
		
		return result;

	}

}
