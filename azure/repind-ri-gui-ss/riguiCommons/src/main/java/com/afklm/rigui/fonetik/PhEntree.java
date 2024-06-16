package com.afklm.rigui.fonetik;

import com.afklm.rigui.fonetik.fonc.PhonAcc;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhEntree {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhEntree.class);
    
	static final int NBKRINDIC = 18;
	static final int NBKRELEMINDIC = 12;
	static final int NBCONS = 12;
	static final int FNTK_OK =				0;		/* phonetisation correcte */
	static final int FNTK_NOK_ELT_EXCLUS =	2;		/* au moins un element n'a pas ete phonetise suite a une exclusion */

	
	public static int Fonetik_Entree(PhonetikInput input) {
		
		int result = FNTK_OK;
		String sIdent = input.getIdent();
		sIdent = sIdent.toUpperCase();
		sIdent = sIdent.replaceAll("[åãáàâäÀÁÂÃÄÅ]", "A");
		sIdent = sIdent.replaceAll("[òóôöõÒÓÔÕÖ]", "O");
		sIdent = sIdent.replaceAll("[úùûüÙÚÛÜ]", "U");
		sIdent = sIdent.replaceAll("[íìîïÌÍÎÏ]", "I");
		sIdent = sIdent.replaceAll("[ýÿÝ]", "Y");
		sIdent = sIdent.replaceAll("[çÇ]", "S");
		sIdent = sIdent.replaceAll("É", "é");
		sIdent = sIdent.replaceAll("È", "è");
		sIdent = sIdent.replaceAll("Ê", "ê");
		sIdent = sIdent.replaceAll("Ë", "ë");
		sIdent = sIdent.replaceAll("[:cntrl:]", " ");
		sIdent = sIdent.replaceAll("[\\-\\./\\*'ÆØ\\(\\)&\\%\\{\\},ª_\t]", " ");

		List<Analyse> analyses = new ArrayList<Analyse>();
		PhoNPart.Phonnompart(sIdent, analyses);
		PhonAcc.Phonacc(analyses);
		for (Analyse analyse : analyses) {			
			if (analyse.getContenu().charAt(0) == '?') {
				result = FNTK_NOK_ELT_EXCLUS;
			}
			String sIdentPhonetise = Phonetise.phonetise(analyse.getContenu());
			if (sIdentPhonetise.length()>NBKRINDIC) {
				sIdentPhonetise = sIdentPhonetise.substring(0, NBKRINDIC);
			}
			analyse.setIdPart(sIdentPhonetise);
			analyse.setConsIdPart(PhUtils.extraitConsonnes(sIdentPhonetise));
		}
		
		Collections.sort(analyses, new ComparatorAnalyse());
		
		StringBuffer indict = new StringBuffer(" ");
		boolean first = true;
		for (Analyse analyse : analyses) {
			String clef = analyse.getIdPart();
			String consClef = analyse.getConsIdPart();
			if (clef.length()>0) {
				if (first && clef.length() > NBKRELEMINDIC) {
					clef = analyse.getIdPart().substring(0, NBKRELEMINDIC);				
				}
				first = false;
				if (indict.charAt(indict.length()-1) == clef.charAt(0)) {
					indict.append(clef.substring(1));
				} else {
					indict.append(clef);
				}							
			}
		}
		
		String sIndict = indict.toString().trim().replaceAll("\\?", "");
		if (sIndict.length()>NBKRINDIC) {
			sIndict = sIndict.substring(0, NBKRINDIC);
		}
		input.setIndict(sIndict.trim());

		Collections.sort(analyses, new ComparatorConsAnalyse());
		
		StringBuffer consIndict = new StringBuffer(" ");
		first = true;
		for (Analyse analyse : analyses) {
			String consClef = analyse.getConsIdPart();
			if (consClef.length()>0) {
				if (first && consClef.length() > NBKRELEMINDIC) {
					consClef = analyse.getIdPart().substring(0, NBKRELEMINDIC);				
				} else if (consClef.length() > NBCONS) {
					consClef = analyse.getIdPart().substring(0, NBCONS);
				}
				first = false;
				if (consIndict.charAt(consIndict.length()-1) == consClef.charAt(0)) {
					consIndict.append(consClef.substring(1));
				} else {
					consIndict.append(consClef);
				}							
			}
		}
		
		String sConsIndict = consIndict.toString().trim().replaceAll("\\?", "");
		if (sConsIndict.length()>NBCONS) {
			sConsIndict = sConsIndict.substring(0, NBCONS);
		}
		sConsIndict = sConsIndict.trim();
		if (sConsIndict.length()==0) {
			sConsIndict = "H";
		}
		input.setIndCons(sConsIndict);

		logger.debug("Phonetise : " + indict);
		
		return result;
	}
	
}
