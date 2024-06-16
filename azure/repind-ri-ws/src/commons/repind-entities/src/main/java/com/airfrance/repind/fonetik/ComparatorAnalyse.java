package com.airfrance.repind.fonetik;

import java.util.Comparator;

public class ComparatorAnalyse implements Comparator<Analyse> {

	public int compare(Analyse o1, Analyse o2) {
		return o1.getIdPart().compareTo(o2.getIdPart());
	}


}
