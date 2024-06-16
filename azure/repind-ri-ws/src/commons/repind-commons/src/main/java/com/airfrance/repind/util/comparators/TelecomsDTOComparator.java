package com.airfrance.repind.util.comparators;

import com.airfrance.repind.dto.adresse.TelecomsDTO;

import java.util.Comparator;

public class TelecomsDTOComparator extends CommonsComparator implements Comparator<TelecomsDTO> {

	public int compare(TelecomsDTO left, TelecomsDTO right) {
		
		Integer result = null;
		
		// compare date
		result = compareDate(left.getDdate_modification(), right.getDdate_modification());
		
		// date are different => return comparison result
		if( result!=null && result != 0 ) {
			return result;
		}
		
		// date are the same => compare sain
		result = compareSain(left.getSain(), right.getSain());
		
		// return comparison result
		return result;
	}
	
}
