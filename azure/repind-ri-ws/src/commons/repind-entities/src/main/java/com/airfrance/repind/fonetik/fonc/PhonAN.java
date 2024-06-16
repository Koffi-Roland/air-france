package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhoVoy;
import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonAN implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonAN.class);
    
	private List<TabSubstStr> tabAn = new ArrayList<TabSubstStr>();
	public PhonAN() {
		tabAn.add(new TabSubstStr("EN", "AN"));
	}
	
	/**
	 *	Recherche une occurence de Tampon dans 
     *     tabAn; si elle est trouvee, le terme 
     *     est remplace par tabAn.Phone 
     *     Le remplacement se fait seulement apres le N ou le M 
     *     s'il n'y a pas de voyelle derriere 
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		
		for (TabSubstStr an : tabAn) {
			int indexAn = result.indexOf(an.getTexte());
			int txtLen = an.getTexte().length();
			/* Et pour chacune des occurences ... 
			... le remplacement ne se fait que si la lettre suivante 
			est une consonne differente de N, dans ce cas ...*/ 
			while (indexAn > -1) {
				if ((indexAn+txtLen  == result.length()) ||
						 (PhoVoy.isConsonne(result.charAt(indexAn+txtLen))) &&
						  (result.charAt(indexAn+txtLen) != 'N'))
					{ 
						result.replace(indexAn, indexAn+txtLen, an.getPhone());					
					}

				indexAn = result.indexOf(an.getTexte(),indexAn+1);
			}
		}		
		return result.toString();
	}
}
