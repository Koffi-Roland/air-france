package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.PhoVoy;
import com.afklm.rigui.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonIL implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonIL.class);
    
	private List<TabSubstStr> tabIl = new ArrayList<TabSubstStr>();
	public PhonIL() {
		tabIl.add(new TabSubstStr("IL","I"));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * tabIl.Texte si elle est trouvee, le terme
	 * est remplace	par tabIl.Phone
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		
		for (TabSubstStr il : tabIl) {
			int indexIl = result.indexOf(il.getTexte());
			while (indexIl > -1 && 
					/*... se trouvant en fin de chaine ...*/
					(indexIl+il.getTexte().length() == result.length() &&
					 ((result.length() >= (indexIl +1)) ? (indexIl == 0 || PhoVoy.isVoyelle(result.charAt(indexIl-1))) : true )))
			{
				result.replace(indexIl, indexIl+il.getTexte().length(), il.getPhone());
				indexIl = result.indexOf(il.getTexte());
			}
		}		
		return result.toString();
	}

}
