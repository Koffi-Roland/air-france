package com.airfrance.repind.service.ws.internal.ut.type;

import com.airfrance.repind.service.ws.internal.type.UltimateCustomerTypeCode;
import com.airfrance.repind.service.ws.internal.type.UltimateFamilyTypeCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UltimateCustomerTypeCodeTest {

	@Before
	public void setUp() throws Exception {
	}
 
	@Test
	public final void test() {
		boolean result;
		result = UltimateCustomerTypeCode.IsUltimateCustomerType("UF");
		Assert.assertFalse(result);
		result = UltimateCustomerTypeCode.IsUltimateCustomerType(UltimateFamilyTypeCode.ULTIMATE_FAMILY.getUltimateFamilyCode());
		Assert.assertFalse(result);
		result = UltimateCustomerTypeCode.IsUltimateCustomerType("UP");
		Assert.assertTrue(result);
		result = UltimateCustomerTypeCode.IsUltimateCustomerType("UL");
		Assert.assertTrue(result);
		result = UltimateCustomerTypeCode.IsUltimateCustomerType("U2");
		Assert.assertTrue(result);
		result = UltimateCustomerTypeCode.IsUltimateCustomerType("US");
		Assert.assertTrue(result);
		result = UltimateCustomerTypeCode.IsUltimateCustomerType(UltimateCustomerTypeCode.ULTIMATE_CLUB_2000.getUltimateCustomerCode());
		Assert.assertTrue(result);
		result = UltimateCustomerTypeCode.IsUltimateCustomerType(UltimateCustomerTypeCode.ULTIMATE_PLATINIUM_FOR_LIFE.getUltimateCustomerCode());
		Assert.assertTrue(result);
		result = UltimateCustomerTypeCode.IsUltimateCustomerType(UltimateCustomerTypeCode.ULTIMATE_SKIPPER.getUltimateCustomerCode());
		Assert.assertTrue(result);
		result = UltimateCustomerTypeCode.IsUltimateCustomerType(UltimateCustomerTypeCode.ULTIMATE_PLATINIUM.getUltimateCustomerCode());
		Assert.assertTrue(result);
		result = UltimateCustomerTypeCode.IsUltimateCustomerType("TOTO");
		Assert.assertFalse(result);
	}

}
