package com.airfrance.repind.fonetik;

import com.airfrance.repind.fonetik.fonc.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class Phonetise {
    /** logger */
    private static final Log logger = LogFactory.getLog(Phonetise.class);
    
	static List<IPhonetikFonction> tabPhonetikFonction = new ArrayList<IPhonetikFonction>();
	static {
		tabPhonetikFonction.add(new PhAbrev());
		tabPhonetikFonction.add(new Phonmac());
		tabPhonetikFonction.add(new PhonFic());
		tabPhonetikFonction.add(new PhonApos());
		tabPhonetikFonction.add(new PhonEm());
		tabPhonetikFonction.add(new PhonTD());
		tabPhonetikFonction.add(new PhonEF());
		tabPhonetikFonction.add(new PhonECC());
		tabPhonetikFonction.add(new PhonKW());
		tabPhonetikFonction.add(new PhonIAS());
		tabPhonetikFonction.add(new PhuEIL());
		tabPhonetikFonction.add(new PheUIL());
		tabPhonetikFonction.add(new PhaILLE());
		tabPhonetikFonction.add(new PhoTION());
		tabPhonetikFonction.add(new PhonAU());
		tabPhonetikFonction.add(new PhoEIL());
		tabPhonetikFonction.add(new PhonOST());
		tabPhonetikFonction.add(new PhocILL());
		tabPhonetikFonction.add(new PhonNT());
		tabPhonetikFonction.add(new PhonCY());
		tabPhonetikFonction.add(new PhonYIC());
		tabPhonetikFonction.add(new PhonYC());
		tabPhonetikFonction.add(new PhonIAZ());
		tabPhonetikFonction.add(new PhonEY());
		tabPhonetikFonction.add(new PhonEX());
		tabPhonetikFonction.add(new PhonEK());
		tabPhonetikFonction.add(new PhonECH());
		tabPhonetikFonction.add(new PhonEAU());
		tabPhonetikFonction.add(new PhonOI());
		tabPhonetikFonction.add(new PhonOIN());
		tabPhonetikFonction.add(new PhonMBP());
		tabPhonetikFonction.add(new PhonAIM());
		tabPhonetikFonction.add(new PhonAN());
		tabPhonetikFonction.add(new PhonOM());
		tabPhonetikFonction.add(new PhonAIE());
		tabPhonetikFonction.add(new PhonEIS());
		tabPhonetikFonction.add(new PhoGNE());
		tabPhonetikFonction.add(new PhonEZE());
		tabPhonetikFonction.add(new PhonEDT());
		tabPhonetikFonction.add(new PhonEH());
		tabPhonetikFonction.add(new PhBlnk());
		tabPhonetikFonction.add(new PhonESH());
		tabPhonetikFonction.add(new PhoDble());
		tabPhonetikFonction.add(new PhonY());
		tabPhonetikFonction.add(new PhonEU());
		tabPhonetikFonction.add(new PhonSL());
		tabPhonetikFonction.add(new PhonPNT());
		tabPhonetikFonction.add(new PhonEG());
		tabPhonetikFonction.add(new PhonIL());
		tabPhonetikFonction.add(new PhonDbs());
		tabPhonetikFonction.add(new PhonOU());
		tabPhonetikFonction.add(new PhonCZ());
	}
	
	public static String phonetise(String str) {
		String result = str;
		String debugTmp = str;
		if (str != null && str.length() > 0) {
			for (IPhonetikFonction phonetikFonc : tabPhonetikFonction) {
				try {
					result = phonetikFonc.process(result);
				} catch (StringIndexOutOfBoundsException e) {
					logger.error("Phonetize error : " + e.getMessage());
				}
				if (logger.isDebugEnabled()) {
					if (!debugTmp.equals(result)) {
						debugTmp = result;
						logger.debug( phonetikFonc.getClass().getSimpleName()+" : '"+debugTmp+"'");
					}
				}
			}
		}				
		logger.debug(result);
		return result;
	}
}
