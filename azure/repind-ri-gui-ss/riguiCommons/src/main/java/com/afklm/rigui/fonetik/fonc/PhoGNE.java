package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.PhUtils;
import com.afklm.rigui.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhoGNE implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhoGNE.class);
    
	private List<TabSubstStr> tabGne = new ArrayList<TabSubstStr>();
	public PhoGNE() {
		tabGne.add(new TabSubstStr("GNA","NIA"));
		tabGne.add(new TabSubstStr("GNE","NIE"));
		tabGne.add(new TabSubstStr("GNI","NII"));
		tabGne.add(new TabSubstStr("GNY","NII"));
		tabGne.add(new TabSubstStr("GNO","NIO"));
		tabGne.add(new TabSubstStr("GNU","NIU"));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabGne.Texte si elle est trouvee, le terme
	 * est remplace	par tabGne.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr gne : tabGne) {
			result = PhUtils.replace(result, gne.getTexte(), gne.getPhone());
		}
		
		return result;

	}

}
