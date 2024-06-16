package com.airfrance.repind.util.comparators;

import com.airfrance.repind.dto.external.ExternalIdentifierDataDTO;

import java.util.Comparator;

public class ExternalIdentifierDataDTOComparator extends CommonsComparator implements Comparator<ExternalIdentifierDataDTO> {

	public int compare(ExternalIdentifierDataDTO left, ExternalIdentifierDataDTO right) {
		
		if(left == right) {
			return SAME;
		} else 
		if(left==null) {
			return LOWER;
		} else 
		if(right==null) {
			return GREATER;
		}
	
		String leftKey = left.getKey();
		String rightKey = right.getKey();
		
		if(leftKey==null || !leftKey.equals(rightKey)) {
			return LOWER;
		}
			
		return SAME;
	}

}
