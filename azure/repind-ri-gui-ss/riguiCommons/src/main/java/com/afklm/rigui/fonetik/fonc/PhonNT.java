package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.PhoVoy;
import com.afklm.rigui.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonNT implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonNT.class);
    
	private List<TabSubstStr> tabNt = new ArrayList<TabSubstStr>();
	public PhonNT() {
		tabNt.add(new TabSubstStr("NT","N."));
	}
	
	/**
	 * Recherche une occurence de Tampon dans         Auteur : N. LICARI 
     *      TabNnt si elle est trouvee, le terme 
     *      est remplace  par TabNnt.Phone. Il faut 
     *      aussi que la lettre apres le T soit diff 
     *      d'une voyelle, du H ou du R 
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		
		
		for (TabSubstStr nt : tabNt) {
			int indexnt = result.indexOf(nt.getTexte());
			int txtLen = nt.getTexte().length();
			while (indexnt > 0 && 
					((indexnt+txtLen == result.length() ||
					  ((result.charAt(indexnt+txtLen) != 'H') &&
					   (result.charAt(indexnt+txtLen) != 'R') &&
					   ((result.charAt(indexnt+txtLen) == '.') && (indexnt+txtLen+1 == result.length())) &&
					   (PhoVoy.isConsonne(result.charAt(indexnt+txtLen)))
					  )
					))
				   ) {
				result.replace(indexnt, indexnt+nt.getTexte().length(), nt.getPhone());
				indexnt = result.indexOf(nt.getTexte());
			}
		}		
		return result.toString();

	}

}
