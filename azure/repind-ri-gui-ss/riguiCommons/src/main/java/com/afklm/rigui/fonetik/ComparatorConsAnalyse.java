package com.afklm.rigui.fonetik;

import java.util.Comparator;

public class ComparatorConsAnalyse implements Comparator<Analyse> {

	public int compare(Analyse o1, Analyse o2) {
		return o1.getConsIdPart().compareTo(o2.getConsIdPart());
	}


}
