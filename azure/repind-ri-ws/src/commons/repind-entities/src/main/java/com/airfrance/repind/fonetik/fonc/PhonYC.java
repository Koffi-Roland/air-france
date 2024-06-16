package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhUtils;
import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonYC implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonYC.class);
    
	private List<TabSubstStr> tabYc = new ArrayList<TabSubstStr>();
	public PhonYC() {
		tabYc.add(new TabSubstStr("GH","G."));
		tabYc.add(new TabSubstStr("GE","JE"));
		tabYc.add(new TabSubstStr("GI","JI"));
		tabYc.add(new TabSubstStr("GY","JI"));
		tabYc.add(new TabSubstStr("CE","SE"));
		tabYc.add(new TabSubstStr("CI","SI"));
		tabYc.add(new TabSubstStr("CY","SI"));
		tabYc.add(new TabSubstStr("BH","B"));
		tabYc.add(new TabSubstStr("BV","VV"));
		tabYc.add(new TabSubstStr("BW","VV"));
		tabYc.add(new TabSubstStr("DH","D"));
		tabYc.add(new TabSubstStr("DT","T"));
		tabYc.add(new TabSubstStr("FH","F"));
		tabYc.add(new TabSubstStr("JH","J"));
		tabYc.add(new TabSubstStr("KH","K"));
		tabYc.add(new TabSubstStr("LH","L"));
		tabYc.add(new TabSubstStr("MH","M"));
		tabYc.add(new TabSubstStr("NH","N"));
		tabYc.add(new TabSubstStr("QH","K"));
		tabYc.add(new TabSubstStr("RH","R"));
		tabYc.add(new TabSubstStr("SZ","Z"));
		tabYc.add(new TabSubstStr("TH","T"));
		tabYc.add(new TabSubstStr("TD","D"));
		tabYc.add(new TabSubstStr("VH","V"));
		tabYc.add(new TabSubstStr("WH","V"));
		tabYc.add(new TabSubstStr("XH","KS"));
		tabYc.add(new TabSubstStr("ZH","Z"));
		tabYc.add(new TabSubstStr("PH","F"));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabYc.Texte si elle est trouvee, le terme
	 * est remplace	par tabYc.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr yc : tabYc) {
			result = PhUtils.replace(result, yc.getTexte(), yc.getPhone());
		}
		
		return result;

	}

}
