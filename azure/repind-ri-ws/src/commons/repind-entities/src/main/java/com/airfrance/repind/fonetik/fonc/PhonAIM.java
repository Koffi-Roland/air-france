package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.PhoVoy;
import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonAIM implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonAIM.class);
    
	private List<TabSubstStr> tabAim = new ArrayList<TabSubstStr>();
	public PhonAIM() {
		tabAim.add(new TabSubstStr("EIN","IN"));
		tabAim.add(new TabSubstStr("EIM","IN"));
		tabAim.add(new TabSubstStr("AIN","IN"));
		tabAim.add(new TabSubstStr("AIM","IN"));
		tabAim.add(new TabSubstStr("IEN","IN"));
		tabAim.add(new TabSubstStr("EAN","AN"));
	}
	
	/**
	 *	Recherche une occurence de Tampon dans 
     *     TabEim; si elle est trouvee, le terme 
     *     est remplace par TabEim.Phone 
     *     Cependant, il faut verifier que l'on a 
     *     bien une consonne sauf M et N immediatement apres. 
	 */
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		
		for (TabSubstStr aim : tabAim) {
			int indexAim = result.indexOf(aim.getTexte());
			int txtLen = aim.getTexte().length();
			/* Et pour chacune des occurences ...
			... le remplacement ne se fait que si la lettre suivante
			est une consonne differente de M ou N, dans ce cas ...*/
			while (indexAim > -1 && 
					((result.charAt(indexAim+1) == 'A') ||
							 (((indexAim+txtLen) == result.length()) ||
							 ((PhoVoy.isConsonne(result.charAt(indexAim+txtLen))) &&
							  (result.charAt(indexAim+txtLen) != 'N') &&
							  (result.charAt(indexAim+txtLen) != 'M')))))
			{
				result.replace(indexAim, indexAim+txtLen, aim.getPhone());
				indexAim = result.indexOf(aim.getTexte());
			}
		}		
		return result.toString();
	}
}
