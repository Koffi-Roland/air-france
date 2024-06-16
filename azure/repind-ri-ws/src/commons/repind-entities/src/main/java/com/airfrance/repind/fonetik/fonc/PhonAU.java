package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonAU implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonAU.class);
    
	private List<TabSubstStr> tabAu = new ArrayList<TabSubstStr>();
	public PhonAU() {
		tabAu.add(new TabSubstStr("AMPS","AN"));
		tabAu.add(new TabSubstStr("EMPS","AN"));
		tabAu.add(new TabSubstStr("AULT","AU"));
		tabAu.add(new TabSubstStr("AULD","AU"));
		tabAu.add(new TabSubstStr("UECH","UESH"));
		tabAu.add(new TabSubstStr("AIL","A.Y."));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 *	  TabAu si elle est trouvee, le terme
	 *	  est remplace par TabAu.Phone
     *    Attention, ici, il s'agit de sons terminaux 
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr au : tabAu) {
			int indexAu = result.indexOf(au.getTexte());
			if (indexAu+au.getTexte().length() == result.length()) {
				result = result.replace(au.getTexte(), au.getPhone());
			}
		}
		
		return result;

	}

}
