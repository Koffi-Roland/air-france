package com.airfrance.repind.service;

import com.airfrance.repind.util.NormalizedStringUtils;
import junit.framework.Assert;
import org.junit.Test;

public class NormalizedStringUtilsTest extends NormalizedStringUtils {

	private final String NORMALIZABLE_STRING = " '-.0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyzÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝßàáâãäåæçèéêëìíîïðñòóôõöùúûüýþÿ";
	private final String NORMALIZABLE_CHAR = "…™¼½¾ŠšžŸ²³´¨¸¹ºµ";
	private final String NORMALIZABLE_CHAR_REPLACED = "...TM1⁄41⁄23⁄4SszY23   1oμ";
	
	private final String NOT_NORMALIZABLE_STRING = "!#$%&()*+,/:;<=>?@[\\]^`{|}~€‚ƒ„…†‡ˆ‰Š‹Œ‘’“”•–—™š›œžŸ¡¢£¤¥¦§¨©ª«¬®°±²³´µ¶·¸¹º»¼½¾¿×Þ÷ø";
	private final String NOTHING_TO_NORMALIZE    = "!#$%&()*+,/:<=>?@[\\]`{|}~€‚ƒ„†‡ˆ‰‹Œ‘’“”•–—›œ¡¢£¤¥¦§©«¬®°±¶·»¿×Þ÷ø";
	private final String TRANSFORMED_STRING = "AAAAaaaaCcEEEEeeeeIIIIiiiiOOOOooooUUUUuuuuYyySS";
	
	private final String UNCHANGED_CHARS = "-.0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";
	private final String REMOVABLE_CHARS = "^';\"ª&#117;";
	private final String TRANSFORMABLE_CHARS = "ÀÁÂÄàáâäÇçÈÉÊËèéêëÌÍÎÏìíîïÒÓÔÖòóôöÙÚÛÜüùúûÝýÿß";
		
	@Test
	public void testIsNormalizableString_True() {
		boolean result = isNormalizableString(NORMALIZABLE_STRING);
		Assert.assertTrue(result);
	}
	
	@Test
	public void testIsNormalizableString_False() {
		boolean result = isNormalizableString(NOT_NORMALIZABLE_STRING);
		Assert.assertFalse(result);
	}
	
	@Test
	public void testNormalizeString_Success() {
		
		String allowedString = UNCHANGED_CHARS + REMOVABLE_CHARS + TRANSFORMABLE_CHARS;
		String expectedNormalizedString = UNCHANGED_CHARS + TRANSFORMED_STRING;
		String result = normalizeString(allowedString);
		Assert.assertEquals(expectedNormalizedString, result);
	}
	
	@Test
	public void testNormalizeString_NothingNormalized() {
		String result = normalizeString(NOTHING_TO_NORMALIZE);
		Assert.assertEquals(NOTHING_TO_NORMALIZE, result);
	}

	@Test
	public void testNormalizeString_NormalizedChar() {
		String result = normalizeString(NORMALIZABLE_CHAR);
		Assert.assertEquals(NORMALIZABLE_CHAR_REPLACED, result);
	}
	
	@Test
	public void testNormalizeString_Allemand() {
		String result = normalizeString("ß");
		Assert.assertEquals("SS", result);
	}

	@Test
	public void testNormalizeString_Bizarre() {
		String result = normalizeString("ăAüUŜ");
		Assert.assertEquals("aAuUS", result);
	}
}
