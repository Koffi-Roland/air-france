package com.airfrance.repind.fonetik.fonc;

import com.airfrance.repind.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonFic implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonFic.class);
    
	private List<TabSubstStr> tabFic = new ArrayList<TabSubstStr>();
	public PhonFic() {
		tabFic.add(new TabSubstStr("FRANCAI","FRANSAI"));
		tabFic.add(new TabSubstStr("FRANCOI","FRANSOI"));
	}
	
	/**
	 * Recherche une occurence de Tampon dans 
	 * TabFic.Texte si elle est trouvee, le terme
	 * est remplace	par TabFic.Phone
	 */
	public String process(String tampon) {
		String result = tampon;
		
		for (TabSubstStr fic : tabFic) {
			result = result.replaceAll(fic.getTexte(), fic.getPhone());
		}
		
		return result;

	}

}
