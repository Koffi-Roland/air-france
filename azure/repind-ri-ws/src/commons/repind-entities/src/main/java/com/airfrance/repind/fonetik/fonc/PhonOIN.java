package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhoVoy;
import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonOIN implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonOIN.class);
    
	private List<TabSubstStr> tabOin = new ArrayList<TabSubstStr>();
	public PhonOIN() {
		tabOin.add(new TabSubstStr("OUHUAI", "OI"));
		tabOin.add(new TabSubstStr("OUHUI",  "OI"));
		tabOin.add(new TabSubstStr("OUHI",   "OI"));
		tabOin.add(new TabSubstStr("OUI",    "OI"));
	}
	
	/**
	 *	Recherche une occurence de Tampon dans 
     *     TabOin; si elle est trouvee, le terme 
     *     est remplace par TabOin.Phone 
     *     Le remplacement se fait seulement apres le N ou le M 
     *     s'il n'y a pas de voyelle derriere 
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		
		for (TabSubstStr oin : tabOin) {
			int indexOin = result.indexOf(oin.getTexte());
			int txtLen = oin.getTexte().length();
			while (indexOin > -1 && 
					(indexOin+txtLen < result.length() && (result.charAt(indexOin+txtLen) == 'M' || result.charAt(indexOin+txtLen) == 'N') &&
					 (PhoVoy.isConsonne(result.charAt(indexOin+txtLen)))
					)
				  ){
				result.replace(indexOin, indexOin+txtLen, oin.getPhone());
				indexOin = result.indexOf(oin.getTexte());
			}
		}		
		return result.toString();
	}
}
