package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonOST implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonOST.class);
    
	private List<TabSubstStr> tabOst = new ArrayList<TabSubstStr>();
	public PhonOST() {
		tabOst.add(new TabSubstStr("AUD$","AU"));
		tabOst.add(new TabSubstStr("OLD$","O"));
		tabOst.add(new TabSubstStr("OLS$","O"));
		tabOst.add(new TabSubstStr("OLT$","O"));
		tabOst.add(new TabSubstStr("OST$","O"));
		tabOst.add(new TabSubstStr("OUD$","OU"));
		tabOst.add(new TabSubstStr("OUP$","OU"));
		tabOst.add(new TabSubstStr("IER$","IE."));
		tabOst.add(new TabSubstStr("EIL$","E.I"));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabOst.Texte si elle est trouvee, le terme
	 * est remplace	par tabOst.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr ost : tabOst) {
			result = result.replaceAll(ost.getTexte(), ost.getPhone());
		}
		
		return result;

	}

}
