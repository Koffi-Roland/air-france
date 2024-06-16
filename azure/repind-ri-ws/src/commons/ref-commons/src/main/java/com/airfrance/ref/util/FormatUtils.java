package com.airfrance.ref.util;

import org.apache.commons.lang.StringUtils;

public class FormatUtils {

	public static final String REGEX_PNM_ID = "^[a-zA-Z0-9\\-]{0,50}$";
		
	public static boolean isValidPnmId(String pnmId) {
		
		if(StringUtils.isEmpty(pnmId)) {
			return false;
		}
		
		return pnmId.matches(REGEX_PNM_ID);
	}
}
