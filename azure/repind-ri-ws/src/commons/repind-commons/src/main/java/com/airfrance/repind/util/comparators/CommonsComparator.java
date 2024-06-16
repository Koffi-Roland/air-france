package com.airfrance.repind.util.comparators;

import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class CommonsComparator {

	protected final int SAME = 0;
	protected final int GREATER = -1;
	protected final int LOWER = 1;
	protected final int MORE_RECENT = -1;
	protected final int OLDER = 1;
	
	public Integer compareDate(Date left, Date right) {
		
		Integer result = null;
		
		// check date
		result = checkDate(left, right);
		
		// no need to go further
		if(result!=null) {
			return result;
		}
		
		// left date older than right date
		if(left.before(right)) {
			result = OLDER;
		} else if(left.after(right)) {
			// left date more recent than right date
			result = MORE_RECENT;
		}else if( left.equals( right ) ) {
			// left date is equal to right date
			result = SAME;
		}
		
		return result;
	}
	
	public Integer checkDate(Date left, Date right) {
		
		Integer result = null;
		
		// both date are null => equals
		if(left==null && right==null) {
			result = SAME;
		} else
		// left date is more recent than right date
		if(left==null) {
			result = MORE_RECENT;
		} else
		// left date is older than right date
		if(right==null) {
			result = OLDER;
		}
		
		return result;
	}
	
	public Integer compareSain(String left, String right) {
		
		Integer result = null;
		
		// check sain
		result = checkSain(left, right);
		
		// no need to go further
		if(result!=null) {
			return result;
		}
		
		left = left.trim();
		right = right.trim();
		
		Long leftNB = Long.valueOf(left);
		Long rightNB = Long.valueOf(right);
		
		return leftNB.compareTo(rightNB);
		
	}

	public Integer checkSain(String left, String right) {
		
		Integer result = null;
		
		// both sain are null => equals
		if(StringUtils.isEmpty(left) && StringUtils.isEmpty(right)) {
			result = SAME;
		} else 
		// left sain is lower than right sain
		if(StringUtils.isEmpty(left)) {
			result = LOWER;
		} else
		// left sain is more recent than right sain
		if(StringUtils.isEmpty(right)) {
			result = GREATER;
		}	
		
		return result;
	}
}
