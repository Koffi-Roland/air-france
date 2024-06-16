package com.airfrance.repind.util.ut;

import com.airfrance.repind.util.GenerateStringFromRegex;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.ArrayList;
import java.util.List;
public class GenerateStringFromRegexTest {

    /** logger */
	private static final Log logger = LogFactory.getLog(GenerateStringFromRegexTest.class);

	/**
	 * Test generateWithPatterns, generate a String using a regex and match with
	 * other regex
	 *  -> Result OK 
	 *  -> Complexe Pattern to check :
	 *  - [0-9a-zA-Z]*[a-zA-Z][0-9a-zA-Z]* String has at least 1 letter (upper or lower) 
	 *  - [0-9a-zA-Z]*[0-9][0-9a-zA-Z]* String has at least 1 num
	 * @throws Exception
	 */
	@Test
	public void generateWithPatterns_resultOK() throws Exception {
		GenerateStringFromRegex generator = new GenerateStringFromRegex("[a-zA-Z0-9]{8,12}");
		List<String> patternsToMatch = new ArrayList<String>();
		patternsToMatch.add("[0-9a-zA-Z]*[a-zA-Z][0-9a-zA-Z]*");
		patternsToMatch.add("[0-9a-zA-Z]*[0-9][0-9a-zA-Z]*");

		String generateString = generator.generateWithPatterns(patternsToMatch);
		logger.info(generateString);

		//On ajoute dans la liste des patterns celui qui sert pour la génération
		patternsToMatch.add("[a-zA-Z0-9]{8,12}");

		Assert.assertTrue(SicStringUtils.isMatchingStringWithPatterns(generateString, patternsToMatch));
	}
	
	/**
	 * Test generateWithPatterns, generate a String using a regex and match with
	 * other regex, try it multiples time -> Result OK
	 * 
	 * @throws Exception
	 */
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 2 mins
	public void generateWithPatterns_resultOK_multiplesTestTimes() throws Exception {
		GenerateStringFromRegex generator = new GenerateStringFromRegex("[a-zA-Z0-9]{8,12}");

		//On ajoute dans la liste des patterns celui qui sert pour la génération
		List<String> patternsToMatch = new ArrayList<String>();
		patternsToMatch.add("[0-9a-zA-Z]*[a-zA-Z][0-9a-zA-Z]*");
		patternsToMatch.add("[0-9a-zA-Z]*[0-9][0-9a-zA-Z]*");

		patternsToMatch.add("[a-zA-Z0-9]{8,12}");

		int nbOfTest = 1000;
		for (int i = 0; i < nbOfTest; i++) {
			String generateString = generator.generateWithPatterns(patternsToMatch);
			logger.info(generateString);

			Assert.assertTrue(SicStringUtils.isMatchingStringWithPatterns(generateString, patternsToMatch));
		}

	}

	/**
	 * Test generateWithPatterns, generate a String using a regex and match with
	 * other regex -> Result OK -> Pattern null
	 * 
	 * @throws Exception
	 */
	@Test
	public void generateWithPatterns_resultOK_patternNull() throws Exception {
		GenerateStringFromRegex generator = new GenerateStringFromRegex("[a-zA-Z0-9]{8,12}");
		List<String> patternsToMatch = null;

		String generateString = generator.generateWithPatterns(patternsToMatch);
		logger.info(generateString);

		Assert.assertTrue(SicStringUtils.isMatchingStringWithPatterns(generateString, patternsToMatch));
	}
	
	/**
	 * Test generateWithPatterns, generate a String using a regex and match with
	 * other regex
	 *  -> Result OK 
	 *  -> Pattern empty
	 * @throws Exception
	 */
	@Test
	public void generateWithPatterns_resultOK_patternEmpty() throws Exception {
		GenerateStringFromRegex generator = new GenerateStringFromRegex("[a-zA-Z0-9]{8,12}");
		List<String> patternsToMatch = new ArrayList<String>();

		String generateString = generator.generateWithPatterns(patternsToMatch);
		logger.info(generateString);

		Assert.assertTrue(SicStringUtils.isMatchingStringWithPatterns(generateString, patternsToMatch));
	}

	/**
	 * Test generateWithPatterns, generate a String using a regex and match with
	 * other regex
	 *  -> Result KO
	 *  -> Simple Pattern to check (1 only) :
	 *  $ String contains '$'
	 * @throws Exception
	 */
	@Test
	public void generateWithPatterns_resultKO() throws Exception {
		GenerateStringFromRegex generator = new GenerateStringFromRegex("[a-zA-Z0-9]{8,12}");
		List<String> patternsToMatch = new ArrayList<String>();
		patternsToMatch.add("$");

		String generateString = generator.generateWithPatterns(patternsToMatch);
		logger.info(generateString);

		//On ajoute dans la liste des patterns celui qui sert pour la génération
		patternsToMatch.add("[a-zA-Z0-9]{8,12}");

		Assert.assertFalse(SicStringUtils.isMatchingStringWithPatterns(generateString, patternsToMatch));
	}

	/**
	 * Test generateWithPatterns, generate a String using a regex and match with
	 * other regex -> Result OK
	 * 
	 * @throws Exception
	 */
	@Test
	public void generate_resultOK() throws Exception {
		GenerateStringFromRegex generator = new GenerateStringFromRegex("[a-zA-Z0-9]{8,12}");

		String generateString = generator.generate();
		logger.info(generateString);

		//On ajoute dans la liste des patterns celui qui sert pour la génération
		List<String> patternsToMatch = new ArrayList<String>();
		patternsToMatch.add("[a-zA-Z0-9]{8,12}");

		Assert.assertTrue(SicStringUtils.isMatchingStringWithPatterns(generateString, patternsToMatch));
	}

	/**
	 * Test generateWithPatterns, generate a String using a regex and match with
	 * other regex, try it multiples time -> Result OK
	 * 
	 * @throws Exception
	 */
	@Test
	public void generate_resultOK_multiplesTestTimes() throws Exception {
		GenerateStringFromRegex generator = new GenerateStringFromRegex("[a-zA-Z0-9]{8,12}");

		//On ajoute dans la liste des patterns celui qui sert pour la génération
		List<String> patternsToMatch = new ArrayList<String>();
		patternsToMatch.add("[a-zA-Z0-9]{8,12}");

		int nbOfTest = 1000;
		for (int i = 0; i < nbOfTest; i++) {
			String generateString = generator.generate();
			logger.info(generateString);

			Assert.assertTrue(SicStringUtils.isMatchingStringWithPatterns(generateString, patternsToMatch));
		}

	}

	/**
	 * Test generateWithPatterns, generate a String using an uninterpretable
	 * regex
	 * 
	 * @throws Exception
	 */
	@Test
	public void generate_resultOK_regexNotInterpretable() throws Exception {
		try {
			GenerateStringFromRegex generator = new GenerateStringFromRegex("[");
			Assert.fail("ERREUR : On devrait avoir une Exception IllegalArgumentException !");
		} catch (IllegalArgumentException e) {
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test generateWithPatterns, generate a String using a regex and match with
	 * other regex -> Result OK
	 * 
	 * @throws Exception
	 */
	@Test
	public void generate_resultOK_regexNull() throws Exception {
		GenerateStringFromRegex generator = new GenerateStringFromRegex(null);

		String generateString = generator.generate();
		logger.info(generateString);

		//On ajoute dans la liste des patterns celui qui sert pour la génération
		List<String> patternsToMatch = new ArrayList<String>();
		patternsToMatch.add("[a-zA-Z0-9]{8,12}");

		Assert.assertEquals(generateString, "");
	}

	/**
	 * Test generateWithPatterns, generate a String using a regex and match with
	 * other regex -> Result OK
	 * 
	 * @throws Exception
	 */
	@Test
	public void generate_resultOK_regexEmpty() throws Exception {
		GenerateStringFromRegex generator = new GenerateStringFromRegex("");

		String generateString = generator.generate();
		logger.info(generateString);

		//On ajoute dans la liste des patterns celui qui sert pour la génération
		List<String> patternsToMatch = new ArrayList<String>();
		patternsToMatch.add("[a-zA-Z0-9]{8,12}");

		Assert.assertEquals(generateString, "");
	}
}
