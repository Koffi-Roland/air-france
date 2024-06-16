package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonEF implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonEF.class);
    
	private List<TabSubstStr> tabEf = new ArrayList<TabSubstStr>();	
	public PhonEF() {
		tabEf.add(new TabSubstStr("EV","EF"));
		tabEf.add(new TabSubstStr("OV","OF"));
	}
	
	/**
	 *  Recherche une occurence de Tampon dans 
	 *  Tabef si elle est trouvee, le terme 
	 *  est remplace  par Tabef.Phone 
	 *  Attention, ici, il s'agit de sons terminaux 
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr ef : tabEf) {
			int indexEl = result.indexOf(ef.getTexte());
			if (indexEl > -1 && indexEl+ef.getTexte().length()==result.length())
				result = result.replaceAll(ef.getTexte()+"$", ef.getPhone());
		}
		
		return result;
	}

}
