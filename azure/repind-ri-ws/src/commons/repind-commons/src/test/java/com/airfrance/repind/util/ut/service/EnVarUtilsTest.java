package com.airfrance.repind.util.ut.service;

import com.airfrance.repind.util.service.EnvVarUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EnVarUtilsTest {

	/**
	 * Verify if the List generated with pattern is equals to the one expected
	 * 
	 * @throws Exception
	 */
	@Test
	public void preparePatternsToMatchPasswordTest() throws Exception {
		String generatepasswordpatternsdelimiter = "</next/>";
		String generatepasswordpatternstomatch = "[0-9a-zA-Z]*[a-zA-Z][0-9a-zA-Z]*</next/>[0-9a-zA-Z]*[0-9][0-9a-zA-Z]*";

		List<String> listPatterns = EnvVarUtils.preparePatternsToMatchPassword(generatepasswordpatternstomatch,
				generatepasswordpatternsdelimiter);
		List<String> listPatternsToHave = new ArrayList<String>();
		listPatternsToHave.add("[0-9a-zA-Z]*[a-zA-Z][0-9a-zA-Z]*");
		listPatternsToHave.add("[0-9a-zA-Z]*[0-9][0-9a-zA-Z]*");

		Assert.assertEquals(listPatternsToHave, listPatterns);
	}
}
