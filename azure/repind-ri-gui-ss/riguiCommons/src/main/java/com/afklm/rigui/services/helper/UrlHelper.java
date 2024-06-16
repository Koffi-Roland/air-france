package com.afklm.rigui.services.helper;

import org.springframework.stereotype.Component;

@Component
public class UrlHelper {
	
	private static final String SPACE_ENCODING = "%20";
	private static final String SPACE = " ";
	
	/**
	 * This method remove the space encoding that can be in the URL
	 * @param urlSection - the part of the URL that might contain space encoding 
	 * @return a clean String (all the %20 are replaced with a pure space)
	 */
	public String removeSpacesOfUrl(String urlSection) {
		
		if (!urlSection.contains(SPACE_ENCODING)) return urlSection;
		
		return urlSection.replaceAll(SPACE_ENCODING, SPACE);
		
	}

}
