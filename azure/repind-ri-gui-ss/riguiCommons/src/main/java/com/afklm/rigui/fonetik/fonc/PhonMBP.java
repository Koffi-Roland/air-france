package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonMBP implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonMBP.class);
    
	private List<TabSubstStr> tabMbp = new ArrayList<TabSubstStr>();
	public PhonMBP() {
		tabMbp.add(new TabSubstStr("MP", "NP"));
		tabMbp.add(new TabSubstStr("MB", "NB"));
	}
	
	/**
	 *	Recherche une occurence de Tampon dans 
     *     TabMpb; si elle est trouvee, le terme 
     *     est remplace par TabMpb.Phone 
     *     Cependant, si les lettres OU precedent 
     *     MP ou MB, le remplacement ne doit pas 
     *     avoir lieu. (ex : ASSOUMBA -> ASOUNBA) 
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		
		for (TabSubstStr mbp : tabMbp) {
			int indexMbp = result.indexOf(mbp.getTexte());
			int txtLen = mbp.getTexte().length();
			while (indexMbp > -1 && 
					(indexMbp == 1 || (result.charAt(indexMbp-2) != 'O' && result.charAt(indexMbp-1) != 'U'))
				  ) {
				result.replace(indexMbp, indexMbp+txtLen, mbp.getPhone());
				indexMbp = result.indexOf(mbp.getTexte());
			}
		}		
		return result.toString();
	}
}
