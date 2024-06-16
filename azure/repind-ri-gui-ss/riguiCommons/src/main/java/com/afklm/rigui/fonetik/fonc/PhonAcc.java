package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.Analyse;

import java.util.List;

/**
 * Remplace les e accentues par "E." 
 *
 */
public class PhonAcc {
	public static void Phonacc(List<Analyse> analyses) {
		for (Analyse analyse : analyses) {
			StringBuffer ident = new StringBuffer(analyse.getContenu());
			if (ident.length() > 0 && isEAccent(ident.charAt(ident.length()-1))) {
				ident.replace(ident.length()-1, ident.length(), "E.");
			}
			
			if (ident.length() > 1 && isEAccent(ident.charAt(ident.length()-2)) && ident.charAt(ident.length()-1) == 'S') {
				ident.replace(ident.length()-2, ident.length(), "ESS");
			}			
			analyse.setContenu(ident.toString().replaceAll("[éèêë]", "E"));
		}
	}
	
	private static boolean isEAccent(char car) {
		return (car == 'é'||
				(car == 'è') ||
				(car == 'ê') ||
				(car == 'ë'));
	}
}
