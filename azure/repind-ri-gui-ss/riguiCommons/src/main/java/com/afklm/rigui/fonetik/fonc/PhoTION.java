package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhoTION implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhoTION.class);
    
	private List<TabSubstStr> tabTion = new ArrayList<TabSubstStr>();
	public PhoTION() {
		tabTion.add(new TabSubstStr("TION","SION"));
		tabTion.add(new TabSubstStr("TIEL","SIEL"));
		tabTion.add(new TabSubstStr("TIEN","SIEN"));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabTion.Texte si elle est trouvee, le terme
	 * est remplace	par tabTion.Phone
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		
		for (TabSubstStr tion : tabTion) {
			int indexTion = result.indexOf(tion.getTexte());
			while (indexTion > 0 && result.charAt(indexTion-1) != 'S') {
				result.replace(indexTion, indexTion+tion.getTexte().length(), tion.getPhone());
				indexTion = result.indexOf(tion.getTexte());
			}
		}		
		return result.toString();
	}

}
