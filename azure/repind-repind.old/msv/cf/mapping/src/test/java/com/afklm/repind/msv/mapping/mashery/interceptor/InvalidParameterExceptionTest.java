package com.afklm.repind.msv.mapping.mashery.interceptor;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class InvalidParameterExceptionTest {

	@Test
	void InvalidParameterExceptionTest2Param() {

		final Object o = new Object();
		final InvalidParameterException e = new InvalidParameterException("name", o);
		Assert.assertEquals("name", e.getFieldName());
		Assert.assertEquals(o, e.getFieldValue());
		Assert.assertNull(e.getMessage());

	}

	@Test
	void InvalidParameterExceptionTest3Param() {

		final Object o = new Object();
		final InvalidParameterException e = new InvalidParameterException("name", o, "message");
		Assert.assertEquals("name", e.getFieldName());
		Assert.assertEquals( o, e.getFieldValue());
		Assert.assertEquals("message", e.getMessage());

	}

	@Test
	void InvalidParameterExceptionTestNull() {

		final InvalidParameterException e = new InvalidParameterException();
		Assert.assertNull(e.getFieldName());
		Assert.assertNull(e.getFieldValue());
		Assert.assertNull(e.getMessage());

	}

}
