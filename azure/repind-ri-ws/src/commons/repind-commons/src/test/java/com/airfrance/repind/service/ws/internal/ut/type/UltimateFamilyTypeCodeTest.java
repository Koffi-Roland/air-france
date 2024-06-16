package com.airfrance.repind.service.ws.internal.ut.type;

import com.airfrance.repind.service.ws.internal.type.UltimateCustomerTypeCode;
import com.airfrance.repind.service.ws.internal.type.UltimateFamilyTypeCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UltimateFamilyTypeCodeTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void test() {
		boolean result;
		result = UltimateFamilyTypeCode.IsUltimateFamilyType("UF");
		Assert.assertTrue(result);
		result = UltimateFamilyTypeCode.IsUltimateFamilyType(UltimateFamilyTypeCode.ULTIMATE_FAMILY.getUltimateFamilyCode());
		Assert.assertTrue(result);
		result = UltimateFamilyTypeCode.IsUltimateFamilyType("UP");
		Assert.assertFalse(result);
		result = UltimateFamilyTypeCode.IsUltimateFamilyType("UL");
		Assert.assertFalse(result);
		result = UltimateFamilyTypeCode.IsUltimateFamilyType("U2");
		Assert.assertFalse(result);
		result = UltimateFamilyTypeCode.IsUltimateFamilyType("US");
		Assert.assertFalse(result);
		result = UltimateFamilyTypeCode.IsUltimateFamilyType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());
		Assert.assertFalse(result);
		result = UltimateFamilyTypeCode.IsUltimateFamilyType(UltimateCustomerTypeCode.ULTIMATE_PLATINIUM_FOR_LIFE.getUltimateCustomerCode());
		Assert.assertFalse(result);
		result = UltimateFamilyTypeCode.IsUltimateFamilyType(UltimateCustomerTypeCode.ULTIMATE_SKIPPER.getUltimateCustomerCode());
		Assert.assertFalse(result);
		result = UltimateFamilyTypeCode.IsUltimateFamilyType(UltimateCustomerTypeCode.ULTIMATE_PLATINIUM.getUltimateCustomerCode());
		Assert.assertFalse(result);
		result = UltimateFamilyTypeCode.IsUltimateFamilyType("TOTO");
		Assert.assertFalse(result);
	}

}
