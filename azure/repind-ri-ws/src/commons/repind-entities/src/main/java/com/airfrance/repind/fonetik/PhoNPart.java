package com.airfrance.repind.fonetik;

import com.airfrance.repind.fonetik.fonc.PhonCtrl;

import java.util.List;

public class PhoNPart {
	public static void Phonnompart(String buffer, List<Analyse> analyse) {
		buffer = PhUtils.enleveBlancs(buffer);
		String[] tabMots = PhUtils.ExtraitMots(buffer, " ");
		for (int i = 0; i < tabMots.length; i++) {
			String mot = tabMots[i];
			mot = PhonCtrl.Phonctrl(mot);
			mot = mot.trim();
			if (mot.length()>0) {
				Analyse analyseTmp = new Analyse();
				analyseTmp.setContenu(mot.trim());
				analyse.add(analyseTmp);
				
			}
		}
	}
}
