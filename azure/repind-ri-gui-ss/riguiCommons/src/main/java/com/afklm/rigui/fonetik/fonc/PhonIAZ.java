package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonIAZ implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonIAZ.class);
    
	private List<TabSubstStr> tabIaz = new ArrayList<TabSubstStr>();
	public PhonIAZ() {
		tabIaz.add(new TabSubstStr("Q","K"));
		tabIaz.add(new TabSubstStr("W","V"));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabIaz.Texte si elle est trouvee, le terme
	 * est remplace	par tabIaz.Phone
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		
		for (TabSubstStr iaz : tabIaz) {
			int indexIaz = result.indexOf(iaz.getTexte());
			while (indexIaz > -1 && (indexIaz != 0 || (indexIaz == 0 && (result.length()==1 || result.charAt(indexIaz+1) != 'U')))) {
				result.replace(indexIaz, indexIaz+iaz.getTexte().length(), iaz.getPhone());
				indexIaz = result.indexOf(iaz.getTexte());
			}
		}		
		return result.toString();
	}
}
