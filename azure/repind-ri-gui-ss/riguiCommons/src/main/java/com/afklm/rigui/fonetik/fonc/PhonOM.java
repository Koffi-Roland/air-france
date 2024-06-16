package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.PhoVoy;
import com.afklm.rigui.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonOM implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonOM.class);
    
	private List<TabSubstStr> tabOm = new ArrayList<TabSubstStr>();
	public PhonOM() {
		tabOm.add(new TabSubstStr("AM","AN"));
		tabOm.add(new TabSubstStr("EM","AN"));
		tabOm.add(new TabSubstStr("IM","IN"));
		tabOm.add(new TabSubstStr("OM","ON"));
		tabOm.add(new TabSubstStr("UM","UN"));
		tabOm.add(new TabSubstStr("YM","IN"));
	}
	
	/**
	 *	Recherche une occurence de Tampon dans 
     *     tabOm; si elle est trouvee, le terme 
     *     est remplace par tabOm.Phone 
     *     Le remplacement se fait seulement apres le N ou le M 
     *     s'il n'y a pas de voyelle derriere 
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		
		for (TabSubstStr om : tabOm) {
			int indexOm = result.indexOf(om.getTexte());
			int txtLen = om.getTexte().length();
			/* Et pour chacune des occurences ... 
			... le remplacement ne se fait que si la lettre suivomte 
			est une consonne differente de M, doms ce cas ...*/ 
			while (indexOm > -1) {
				if ((indexOm+txtLen  == result.length()) ||
						 (PhoVoy.isConsonne(result.charAt(indexOm+txtLen))) &&
						  (result.charAt(indexOm+txtLen) != 'M'))
					{ 
						result.replace(indexOm, indexOm+txtLen, om.getPhone());					
					}

				indexOm = result.indexOf(om.getTexte(),indexOm+1);
			}
		}		
		return result.toString();
	}

}
