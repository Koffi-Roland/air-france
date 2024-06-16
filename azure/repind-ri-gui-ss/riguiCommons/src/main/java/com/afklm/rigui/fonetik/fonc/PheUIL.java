package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.PhUtils;
import com.afklm.rigui.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PheUIL implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PheUIL.class);
    
	private List<TabSubstStr> tabUil = new ArrayList<TabSubstStr>();
	public PheUIL() {
		tabUil.add(new TabSubstStr("OEILL",".E.Y."));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabUil.Texte si elle est trouvee, le terme
	 * est remplace	par tabUil.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr uil : tabUil) {
			result = PhUtils.replace(result, uil.getTexte(), uil.getPhone());
		}
		
		return result;

	}

}
