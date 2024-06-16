package com.afklm.rigui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afklm.rigui.services.helper.UrlHelper;

@Service
public class UrlService {
	
	@Autowired
	private UrlHelper urlHelper;
	
	private static final String DASH_CHARACTER = "/";
	
	/**
	 * This method extract the resource id that might be given in a URL.
	 * @param url - of the HTTP request
	 * @return the id of the resource
	 */
	public String extractResourceId(String url) {
		
		String[] splittedUrl = url.split(DASH_CHARACTER);
		
		int urlBlocksCount = splittedUrl.length;
		
		String section = splittedUrl[urlBlocksCount - 1];
		String id = urlHelper.removeSpacesOfUrl(section);
		
		return id;
		
	}

}
