package com.airfrance.repind.entity.individu;

import junit.framework.Assert;
import org.junit.Test;

public class IndividuTest extends Individu {

	private static final long serialVersionUID = -7383072998704672840L;
	
	private final String EMPTY_STR = "";
	private final String NULL_STR = "";
	private final String SAMPLE_STR = "ÀÁÂÄàáâäÇçÈÉÊËèéêëÌÍÎÏìíîïÒÓÔÖòóôöÙÚÛÜüùúûÝ?ýÿ"; // only replaced chars
	private final String EXPECTED_STR = "AAAAAAAACCEEEEEEEEIIIIIIIIOOOOOOOOUUUUUUUUY?YY";
	
	@Test
	public void testNormalizeEmpty() {
		String result = normalize(EMPTY_STR);
		Assert.assertEquals(EMPTY_STR,result);
	}
	
	@Test
	public void testNormalizeNull() {
		String result = normalize(NULL_STR);
		Assert.assertEquals(NULL_STR,result);
	}
	
	@Test
	public void testNormalize() {
		String result = normalize(SAMPLE_STR);
		Assert.assertEquals(EXPECTED_STR,result);
	}
	
}
