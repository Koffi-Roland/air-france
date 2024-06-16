package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhoVoy;
import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonEIS implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonEIS.class);
    
	private List<TabSubstStr> tabEis = new ArrayList<TabSubstStr>();
	public PhonEIS() {
		tabEis.add(new TabSubstStr("EI","E."));
		tabEis.add(new TabSubstStr("AI","E."));
		tabEis.add(new TabSubstStr("ES","E."));
	}
	
	/**
	 *	Recherche une occurence de Tampon dans 
     *     TabEis; si elle est trouvee, le terme 
     *     est remplace par TabEis.Phone 
     *     Cependant, il faut verifier que l'on a (pour ES) 
     *     bien une consonne sauf H et T immediatement apres, 
     *     et qu'il ne s'agit pas du premier caractere 
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		
		int iElem = 0;
		for (TabSubstStr eis : tabEis) {
			int indexEis = result.indexOf(eis.getTexte());
			int txtLen = eis.getTexte().length();
			/* Et pour chacune des occurences ... 
			... le remplacement ne se fait que si la lettre suiveiste 
			est une consonne differente de M, deiss ce cas ...*/ 
			while (indexEis > -1 &&
				   ((iElem != 2) ||
					(iElem == 2) &&
					  (indexEis > 0) &&
					 (((indexEis+txtLen) == result.length()) ||
					 (PhoVoy.isConsonne(result.charAt(indexEis+txtLen))) &&
					 (result.charAt(indexEis+txtLen) != 'H') && (result.charAt(indexEis+txtLen) != 'T'))))
			{ 
				result.replace(indexEis, indexEis+txtLen, eis.getPhone());
				indexEis = result.indexOf(eis.getTexte());
			}
			iElem++;				
		}		
		return result.toString();
	}

}
