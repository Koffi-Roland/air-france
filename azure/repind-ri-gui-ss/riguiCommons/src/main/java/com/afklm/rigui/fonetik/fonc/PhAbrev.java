package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Recherche une occurence de Tampon dans
 *			  TabAbrev si elle est trouvee le terme
 *			  est remplace par TabAbrev.Phone
 */
public class PhAbrev implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhAbrev.class);
    
	private List<TabSubstStr> tabAbrevSt = new ArrayList<TabSubstStr>();
	public PhAbrev() {
		tabAbrevSt.add(new TabSubstStr("STES","SAINTES"));
		tabAbrevSt.add(new TabSubstStr("STE","SAINTE"));
		tabAbrevSt.add(new TabSubstStr("STS","SAINTS"));
		tabAbrevSt.add(new TabSubstStr("ST","SAINT"));
	}
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr abrev : tabAbrevSt) {
			if(tampon.equals(abrev.getTexte())) {
				result = abrev.getPhone();
				break;
			}
		}
		
		return result;

	}

}
