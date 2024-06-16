package com.airfrance.repindutf8.util.ut;

import com.airfrance.repindutf8.util.SicUtf8StringUtils;
import org.junit.Assert;
import org.junit.Test;


public class SicUtf8StringUtilsTest {
	
	// REPIND-1528 : Check and Find HTML in string

	////////////
	// isHTML //
	////////////
	
	@Test
	public void isHTML_Empty() {
		Assert.assertTrue(!SicUtf8StringUtils.isHTML(""));
	}

	@Test
	public void isHTML_Null() {
		Assert.assertTrue(!SicUtf8StringUtils.isHTML(null));
	}
	
	@Test
	public void isHTML_No_HTML() {
		Assert.assertTrue(!SicUtf8StringUtils.isHTML("ABCDE"));
	}

	@Test
	public void isHTML_HTML_Eamp() {
		Assert.assertTrue(SicUtf8StringUtils.isHTML("AB&eamps;CDE"));
	}

	@Test
	public void isHTML_HTML_Debut() {
		Assert.assertTrue(SicUtf8StringUtils.isHTML("&Ccedil;ABCDE"));
	}

	@Test
	public void isHTML_HTML_Fin() {
		Assert.assertTrue(SicUtf8StringUtils.isHTML("ABCDE&#206;"));
	}

	@Test
	public void isHTML_HTML_DeuxFois() {
		Assert.assertTrue(SicUtf8StringUtils.isHTML("ABCDE&amp;#206;"));
	}

	@Test
	public void isHTML_HTML_Deux() {
		Assert.assertTrue(SicUtf8StringUtils.isHTML("ABCDE&quot;FGH&#8221;IJKL"));
	}

	@Test
	public void isHTML_HTML_Cote() {
		Assert.assertTrue(SicUtf8StringUtils.isHTML("ABCD&euro;FGIJKL"));
	}

	@Test
	public void isHTML_HTML_Omicron() {
		Assert.assertTrue(SicUtf8StringUtils.isHTML("ABCD&omicron;FGIJKL"));
	}

	@Test
	public void isHTML_HTML_Seul() {
		Assert.assertTrue(SicUtf8StringUtils.isHTML("&thetasym;"));
	}

	@Test
	public void isHTML_HTML_Hexa() {
		Assert.assertTrue(SicUtf8StringUtils.isHTML("&#x947c;"));
	}

	@Test
	public void isHTML_HTML_Hexa_Bis() {
		Assert.assertTrue(SicUtf8StringUtils.isHTML("AA&#x9c2e;BB"));
	}

	@Test
	public void isHTML_HTML_Number() {
		Assert.assertTrue(SicUtf8StringUtils.isHTML("&#38005;"));
	}
}
