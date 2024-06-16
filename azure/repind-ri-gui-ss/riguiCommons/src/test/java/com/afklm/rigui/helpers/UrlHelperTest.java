package com.afklm.rigui.helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.afklm.rigui.services.helper.UrlHelper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UrlHelper.class)
class UrlHelperTest {
	
	private String urlWithSpaces = null;
	
	@Autowired
	private UrlHelper urlHelper;
	
	@BeforeEach
	public void init() {
		urlWithSpaces = "/api/resources/individual/1/address/%20%20%20123";
	}
	
	@Test
	void test_CleanUrlWithSpaces() {
		
		// Expected URL
		final String expected = "/api/resources/individual/1/address/   123";
		
		// Remove and replace encoded spaces of the URL
		final String cleanURL = urlHelper.removeSpacesOfUrl(urlWithSpaces);
		
		Assertions.assertEquals(expected, cleanURL);
		
	}

}
