package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonCY implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonCY.class);
    
	private List<TabSubstStr> tabCy = new ArrayList<TabSubstStr>();
	public PhonCY() {
		tabCy.add(new TabSubstStr("AE$","A"));
		tabCy.add(new TabSubstStr("AH$","A"));
		tabCy.add(new TabSubstStr("EH$","E"));
		tabCy.add(new TabSubstStr("IH$","I"));
		tabCy.add(new TabSubstStr("OH$","O"));
		tabCy.add(new TabSubstStr("UH$","U"));
		tabCy.add(new TabSubstStr("YH$","I"));
		tabCy.add(new TabSubstStr("HH$","H"));
		tabCy.add(new TabSubstStr("AS$","A"));
		tabCy.add(new TabSubstStr("ES$","E"));
		tabCy.add(new TabSubstStr("IS$","I"));
		tabCy.add(new TabSubstStr("OS$","O"));
		tabCy.add(new TabSubstStr("US$","U"));
		tabCy.add(new TabSubstStr("YS$","I"));
		tabCy.add(new TabSubstStr("AT$","A"));
		tabCy.add(new TabSubstStr("ET$","E."));
		tabCy.add(new TabSubstStr("ST$","S"));
		tabCy.add(new TabSubstStr("IT$","I"));
		tabCy.add(new TabSubstStr("OT$","O"));
		tabCy.add(new TabSubstStr("UT$","U"));
		tabCy.add(new TabSubstStr("YT$","I"));
		tabCy.add(new TabSubstStr("AX$","A"));
		tabCy.add(new TabSubstStr("EX$","E."));
		tabCy.add(new TabSubstStr("IX$","I"));
		tabCy.add(new TabSubstStr("OX$","O"));
		tabCy.add(new TabSubstStr("UX$","U"));
		tabCy.add(new TabSubstStr("YX$","I"));
		tabCy.add(new TabSubstStr("AZ$","A"));
		tabCy.add(new TabSubstStr("EZ$","E."));
		tabCy.add(new TabSubstStr("YZ$","IZ"));
		tabCy.add(new TabSubstStr("UZ$","U"));
		tabCy.add(new TabSubstStr("YZ$","I"));
		tabCy.add(new TabSubstStr("ER$","E."));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabCy.Texte si elle est trouvee, le terme
	 * est remplace	par tabCy.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr cy : tabCy) {
			result = result.replaceAll(cy.getTexte(), cy.getPhone());
		}
		
		return result;

	}
}
