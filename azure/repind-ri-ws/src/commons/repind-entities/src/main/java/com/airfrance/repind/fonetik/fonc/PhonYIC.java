package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhUtils;
import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonYIC implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonYIC.class);
    
	private List<TabSubstStr> tabYic = new ArrayList<TabSubstStr>();
	public PhonYIC() {
		tabYic.add(new TabSubstStr("CEA","SA"));
		tabYic.add(new TabSubstStr("CEO","SO"));
		tabYic.add(new TabSubstStr("GEA","JA"));
		tabYic.add(new TabSubstStr("GUA","G.A"));
		tabYic.add(new TabSubstStr("GEO","JO"));
		tabYic.add(new TabSubstStr("GUE","G.E"));
		tabYic.add(new TabSubstStr("GUI","G.I"));
		tabYic.add(new TabSubstStr("GUY","G.I"));
		tabYic.add(new TabSubstStr("GUO","G.O"));
		tabYic.add(new TabSubstStr("GU\\.","G.."));
		tabYic.add(new TabSubstStr("QUA","KA"));
		tabYic.add(new TabSubstStr("QUE","KE"));
		tabYic.add(new TabSubstStr("QUI","KI"));
		tabYic.add(new TabSubstStr("QUY","KI"));
		tabYic.add(new TabSubstStr("QU\\.","K."));
		tabYic.add(new TabSubstStr("QU ","K"));
		tabYic.add(new TabSubstStr("QUO","KO"));
		tabYic.add(new TabSubstStr("CUE","KU"));
		tabYic.add(new TabSubstStr("OEU",".EU"));
		tabYic.add(new TabSubstStr("MPT","NT"));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabYic.Texte si elle est trouvee, le terme
	 * est remplace	par tabYic.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr yic : tabYic) {
			result = PhUtils.replace(result, yic.getTexte(), yic.getPhone());
		}
		
		return result;

	}

}
