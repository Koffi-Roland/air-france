package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhUtils;
import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhocILL implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhocILL.class);
    
	private List<TabSubstStr> tabIll = new ArrayList<TabSubstStr>();
	public PhocILL() {
		tabIll.add(new TabSubstStr("CUEI","KUEI"));
		tabIll.add(new TabSubstStr("COEI","KUEI"));
		tabIll.add(new TabSubstStr("EILL","E.I"));
		tabIll.add(new TabSubstStr("AILL","A.I"));
		tabIll.add(new TabSubstStr("OUIN","OIN"));
		tabIll.add(new TabSubstStr("AULT","O.T"));
		tabIll.add(new TabSubstStr("CILL","SSY."));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabIll.Texte si elle est trouvee, le terme
	 * est remplace	par tabIll.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr ill : tabIll) {
			result = PhUtils.replace(result, ill.getTexte(), ill.getPhone());
		}
		
		return result;

	}

}
