package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhuEIL implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhuEIL.class);
    
	private List<TabSubstStr> tabEil = new ArrayList<TabSubstStr>();
	public PhuEIL() {
		tabEil.add(new TabSubstStr("CUEILL","KEUILL"));
		tabEil.add(new TabSubstStr("COEILL","KEUILL"));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabEil.Texte si elle est trouvee, le terme
	 * est remplace	par tabEil.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr eil : tabEil) {
			result = result.replaceAll(eil.getTexte(), eil.getPhone());
		}
		
		return result;

	}

}
