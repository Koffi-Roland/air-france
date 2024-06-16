package com.airfrance.repind.util.ut;

import com.airfrance.repind.util.NormalizedStringUtilsV2;
import org.junit.Assert;
import org.junit.Test;


public class NormalizedStringUtilsV2Test extends NormalizedStringUtilsV2 {
	
	private final String NAME_TO_NORMALIZE_1 = "ÀÁÂÄÅÃàáâäåã";
	private final String NAME_TO_NORMALIZE_2 = "ÇçÈÉÊËèéêëÌÍÎÏìíîï";
	private final String NAME_TO_NORMALIZE_3 = "ÒÓÔÖÕØòóôöõðøÙÚÛÜüùúû";
	private final String NAME_TO_NORMALIZE_4 = "ñÑšŠÆæŒœÝýÿß&#";
	private final String NAME_TO_NORMALIZE_5 = "A0123456789Z";

	private final String ADDR_TO_NORMALIZE_1 = "14 av de l’�opéra";
	private final String NORMALIZED_ADDR_1 = "14 av de l opera";
	private final String NORMALIZED_NAME_1 = "AAAAAAaaaaaa";
	private final String NORMALIZED_NAME_2 = "CcEEEEeeeeIIIIiiii";
	private final String NORMALIZED_NAME_3 = "OOOOOOoooooooUUUUuuuu";
	private final String NORMALIZED_NAME_4 = "nNsSAEaeOEoeYyySS";
	private final String NORMALIZED_NAME_5 = "AZ";

	@Test
	public void testNormalizeAdress_success_1() {
		String result = normalizeAddrString(ADDR_TO_NORMALIZE_1);

		Assert.assertEquals(NORMALIZED_ADDR_1, result);
	}

	@Test
	public void testNormalizeName_success_1() {
		String result = normalizeName(NAME_TO_NORMALIZE_1);
		
		Assert.assertEquals(NORMALIZED_NAME_1, result);
	}

	@Test
	public void testNormalizeName_success_2() {
		String result = normalizeName(NAME_TO_NORMALIZE_2);
		
		Assert.assertEquals(NORMALIZED_NAME_2, result);
	}
	
	@Test
	public void testNormalizeName_success_3() {
		String result = normalizeName(NAME_TO_NORMALIZE_3);
		
		Assert.assertEquals(NORMALIZED_NAME_3, result);
	}
	
	@Test
	public void testNormalizeName_success_4() {
		String result = normalizeName(NAME_TO_NORMALIZE_4);
		
		Assert.assertEquals(NORMALIZED_NAME_4, result);
	}
	
	@Test
	public void testNormalizeName_success_5() {
		String result = normalizeName(NAME_TO_NORMALIZE_5);
		
		Assert.assertEquals(NORMALIZED_NAME_5, result);
	}
}
