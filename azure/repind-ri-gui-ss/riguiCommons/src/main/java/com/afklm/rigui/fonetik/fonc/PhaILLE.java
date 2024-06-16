package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhaILLE implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhaILLE.class);
    
	private List<TabSubstStr> tabAil = new ArrayList<TabSubstStr>();
	public PhaILLE() {
		tabAil.add(new TabSubstStr("ILL",".Y."));
	}
	/**
	 * Recherche une occurence de Tampon dans 
     *      TabAil; si elle est trouvee, le terme 
	 * 	    est remplace  par TabAil.Phone
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		
		for (TabSubstStr ail : tabAil) {
			int indexAil = result.indexOf(ail.getTexte());
			while (indexAil >= 0) {
				// le remplacement ne se fait que si la lettre precedente 
				// est differente de C, V, M, G ou W , dans ce cas, ...*/ 
				if (indexAil == 0 || (result.charAt(indexAil-1) != 'C' && result.charAt(indexAil-1) != 'V' && 
					result.charAt(indexAil-1) != 'M' && result.charAt(indexAil-1) != 'G' && result.charAt(indexAil-1) != 'W')) 
				{
					result = result.replace(indexAil, indexAil+ail.getTexte().length(), ail.getPhone());
				} else {
					break;
				}
				indexAil = result.indexOf(ail.getTexte());
			}

		}
		
		return result.toString();
	}

}
