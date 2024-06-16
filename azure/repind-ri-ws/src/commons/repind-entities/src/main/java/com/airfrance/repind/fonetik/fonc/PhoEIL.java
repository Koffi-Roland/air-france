package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhUtils;
import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhoEIL implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhoEIL.class);
    
	private List<TabSubstStr> tabEil = new ArrayList<TabSubstStr>();
	public PhoEIL() {
		tabEil.add(new TabSubstStr("OEIL",".E.I"));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabEil.Texte si elle est trouvee, le terme
	 * est remplace	par tabEil.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr eil : tabEil) {
			result = PhUtils.replace(result, eil.getTexte(), eil.getPhone());
		}
		
		return result;

	}

}
