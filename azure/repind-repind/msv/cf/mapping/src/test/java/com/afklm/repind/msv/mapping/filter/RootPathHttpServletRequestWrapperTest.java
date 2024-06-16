package com.afklm.repind.msv.mapping.filter;

import jakarta.servlet.http.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Enumeration;
import java.util.Vector;

/**
 * RootPathHttpServletRequestWrapper Wrapper that adds a HTTP header suitable
 * for HATEOAS
 *
 * @author M312812
 *
 */
@ExtendWith(SpringExtension.class)
class RootPathHttpServletRequestWrapperTest {

	String rootPath = "ROOT";
	HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
	RootPathHttpServletRequestWrapper wrapper = new RootPathHttpServletRequestWrapper(httpServletRequest, rootPath);

	@BeforeEach
	void init() {}

	@Test
	void testConstructor() {

		Assert.assertEquals(rootPath, wrapper.getRootPath());

	}

	@Test
	void testGetHeader() {

		final String otherHeader = "OTHER";
		final String otherValue = otherHeader + "-HEADER";

		Mockito.when(httpServletRequest.getHeader(otherHeader)).thenReturn(otherValue);

		Assert.assertEquals("ROOT", wrapper.getHeader(RootPathHttpServletRequestWrapper.X_FORWARDED_PREFIX));
		Assert.assertEquals(otherValue, wrapper.getHeader(otherHeader));

	}

	@Test
	void testGetHeaderNames() {

		final Vector<String> vt = new Vector<>();
		vt.add("java");
		vt.add("php");
		vt.add("array");
		vt.add("string");
		vt.add("c");
		final Enumeration<String> enm = vt.elements();

		Mockito.when(httpServletRequest.getHeaderNames()).thenReturn(enm);

		boolean found = false;
		final Enumeration<String> headers = wrapper.getHeaderNames();
		String header = null;
		for (; headers.hasMoreElements();) {
			header = headers.nextElement();
			if (RootPathHttpServletRequestWrapper.X_FORWARDED_PREFIX.equals(header)) {
				found = true;
				break;
			}
		}

		Assert.assertTrue(found);
	}

}
