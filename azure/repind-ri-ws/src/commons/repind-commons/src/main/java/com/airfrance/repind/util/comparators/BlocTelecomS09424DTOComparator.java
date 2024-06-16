package com.airfrance.repind.util.comparators;

import com.airfrance.repind.dto.individu.adh.individualinformation.BlocTelecomS09424DTO;
import com.airfrance.repind.dto.individu.adh.individualinformation.TelecomSICDTO;

import java.util.Comparator;

public class BlocTelecomS09424DTOComparator extends CommonsComparator implements Comparator<BlocTelecomS09424DTO> {

	public int compare(BlocTelecomS09424DTO left, BlocTelecomS09424DTO right) {
		
		// check provided telecoms
		Integer result = checkTelecomS09424DTO(left, right);
		
		if(result!=null) {
			return result;
		}
		
		// compare telecom
		result = compare(left.getTelecomsic(), right.getTelecomsic());
		
		return result;
		
	}
	
	public int compare(TelecomSICDTO left, TelecomSICDTO right) {
		
		// check provided telecoms
		Integer result = checkTelecomSICDTO(left, right);
		
		if(result!=null) {
			return result;
		}
		
		// compare date
		result = compareDate(left.getDateModification(), left.getDateModification());
		
		// date are different => return comparison result
		if(result != null && result != 0 ) {
			return result;
		}
		
		// date are the same => compare sain
		result = compareSain(left.getCleTelecom(), right.getCleTelecom());
		
		// return comparison result
		return result;
	}
	
	public Integer checkTelecomS09424DTO(BlocTelecomS09424DTO left, BlocTelecomS09424DTO right) {
	
		Integer result = null;
		
		// both null -> same
		if(left==null && right==null) {
			result = SAME;
		} else
		// left null -> older
		if(left==null) {
			result = OLDER;
		} else
		// right null -> more recent
		if(right==null) {
			result = MORE_RECENT;
		}
		
		return result;
		
	}
	
	public Integer checkTelecomSICDTO(TelecomSICDTO left, TelecomSICDTO right) {
		
		Integer result = null;
		
		// both null -> same
		if(left==null && right==null) {
			result = SAME;
		} else
		// left null -> older
		if(left==null) {
			result = OLDER;
		} else
		// right null -> more recent
		if(right==null) {
			result = MORE_RECENT;
		}
		
		return result;
		
	}
	
}
