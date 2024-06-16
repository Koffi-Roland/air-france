package com.airfrance.jraf.batch.individu;

import com.airfrance.jraf.batch.config.WebConfigTestBatch;
import com.airfrance.jraf.batch.individu.helper.BatchExternalIdentifierHelper;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebConfigTestBatch.class)
@Transactional(value = "transactionManagerRepind")
// @Ignore
public class BatchExternalIdentifierTest {

	private static Log logger = LogFactory.getLog(BatchExternalIdentifierTest.class);
	
	@Autowired
	private BatchExternalIdentifier batchExternalIdentifier;
	
	@Autowired
	private ExternalIdentifierDS eids;
	
	@Autowired
	private BatchExternalIdentifierHelper helper;
	
    protected static final String MANDATORY_ARGUMENT_MISSING = "Mandatory argument missing : ";
    protected static final String NO_ARGUMENTS_TO_THE_BATCH = "No arguments to the batch";
    protected static final String ARGUMENT_NOT_VALID = "Argument not valid";

    
	/* ********************************************************************************************
	 * 
	 * UNIT TESTS ON PARSEARGS METHOD
	 * 
	 ******************************************************************************************* */
    
	@Test
	public void parseArgsMissingMandatoryArgumentTest() {
		
		// Launch Test
		try {
			batchExternalIdentifier.parseArgs(new String[] {"-C"});
			Assert.fail("Should not succeed");
		} catch (Exception e) {
			Assert.assertEquals(MANDATORY_ARGUMENT_MISSING, e.getMessage());
		}
	}
	
	@Test
	public void parseArgsNoArgumentTest() {
		// Launch Test
		try {
			batchExternalIdentifier.parseArgs(null);
			Assert.fail("Should not succeed");
			
		} catch (Exception e) {
			Assert.assertEquals(NO_ARGUMENTS_TO_THE_BATCH, e.getMessage());
		}
	}
	
	@Test
	public void parseArgsUnknownArgumentTest() {
		try {
			batchExternalIdentifier.parseArgs(new String[] { "-f file -C -truc"});
			Assert.fail("Should not succeed");
		} catch (Exception e) {
			Assert.assertEquals(ARGUMENT_NOT_VALID, e.getMessage());
		}
	}
	
	@Test
	public void parseArgsValidTest() throws Exception {
		batchExternalIdentifier.parseArgs(new String[] { "-f", "file", "-C"});
		// If this assert is not executed, there is something wrong
		Assert.assertTrue(true);
	}
	
	@Test
	@Ignore
	public void batchExternalIdentifierWithNoExistingFileTest() {
		try {
		batchExternalIdentifier.parseArgs(new String[] { "-f", "file", "-C"});
		batchExternalIdentifier.execute();
		Assert.fail();
		} catch (Exception e) {
			Assert.assertTrue(NoSuchFileException.class.equals(e.getCause().getClass()));
		}
	}

	/* ********************************************************************************************
	 * 
	 * UNIT TESTS ON TREATLINES METHOD
	 * 
	 ******************************************************************************************* */
	@Test
	public void treatLineEmptyLineTest() throws JrafDomainException {
		String line = "";

		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);
		
		Assert.assertEquals("1;;INVALID LINE\n", report.toString());
	}

	@Test
	public void treatLineCarriageReturnLineTest() throws JrafDomainException {
		String line = "\r\n";

		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);
		
		Assert.assertEquals("1;;INVALID LINE\n", report.toString());
	}

	@Test
	public void treatLineBlankLine() throws JrafDomainException {
		String line = "     ";

		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);
		
		Assert.assertEquals("1;;INVALID LINE\n", report.toString());
	}
	
	@Test
	public void treatLineMissingExternalIdentifierTest() throws JrafDomainException {
		// Init data
		String line = ",,GIGYA_ID,,END\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);


		// Check
		Assert.assertEquals("1;;MISSING EXTERNAL IDENTIFIER\n", report.toString()); 
	}
	
	@Test
	@Ignore
	public void treatLineInvalidLineTest() throws JrafDomainException {
		// Init data
		String line = ",,,,,,,,,AFKL_54687654132687,,,,END\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);


		// Check
		Assert.assertEquals("1;;INVALID LINE\n", report.toString()); 
		
	}
	
	@Test
	public void treatLineMissingExternalTypeTest() throws JrafDomainException {
		// Init data
		String line = ",AFKL_54687654132687,,,END\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);

		// Check
		Assert.assertEquals("1;;MISSING EXTERNAL TYPE\n", report.toString()); 
	}
	
	@Test
	public void treatLineInvalidExternalType() throws JrafDomainException {
		// Init data
		String line = ",AFKL_54687654132687,INVALID_TYPE_ID,,END\n";
		batchExternalIdentifier.setExtTypeIds(helper.getAllTypeExtId());
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);

		// Check
		Assert.assertEquals("1;;INVALID EXTERNAL TYPE 'INVALID_TYPE_ID'\n", report.toString()); 
	}
	
	/* ********************************************************************************************
	 * 
	 * UNIT TESTS ON EXTIDALREADYEXISTS METHOD
	 * 
	 ******************************************************************************************* */
	
	@Test
	@Ignore
	public void extIdAlreadyExistsExternalExistTest() throws JrafDomainException {
		// Init data
		String report = "";
		
		// Execute test
		report = batchExternalIdentifier.extIdAlreadyExists("NumeroIDGIGYA_AFKL_0413288788_MosZwreAhQ", "GIGYA_ID", "");
		// Check
		Assert.assertTrue(batchExternalIdentifier.getLineError());
		Assert.assertEquals(";400413556501;EXTERNAL ALREADY EXISTS\n", report);
	}

	@Test
	public void extIdAlreadyExistsExternalLinkedToDifferentTest() throws JrafDomainException {
		// Init data
		String report = "";
		
		// Execute test
		report = batchExternalIdentifier.extIdAlreadyExists("AFKL_201412110804__guid_Byzb9rctyUqwk8nR4lMRrZaDfuSmd0ed5s4p", "GIGYA_ID", "");
		// Check
		Assert.assertTrue(batchExternalIdentifier.getLineError());
		Assert.assertEquals(";;EXTERNAL LINKED TO DIFFERENT INDIVIDUAL\n", report);
	}

	@Test
	public void extIdAlreadyExistsUnkownExternalTest() throws JrafDomainException {
		// Init data
		String report = "";
		
		// Execute test
		report = batchExternalIdentifier.extIdAlreadyExists("NouveauIDGIGYA_AFKL_0451241763_bBlVihNPgH", "GIGYA_ID", "");
		// Check
		Assert.assertFalse(batchExternalIdentifier.getLineError());
		Assert.assertEquals("", report);
	}

	@Test
	public void extIdAlreadyUsedByKLIsNTest() throws JrafDomainException {
		// Init data
		String report = "";
		
		// Execute
		report = batchExternalIdentifier.extIdAlreadyExists("NumeroIDGIGYA_AFKL_0439493260_BVqvlYOLtv", "GIGYA_ID", "");
	
		Assert.assertTrue(report.contains("400413556501"));
	}

	@Test
	public void extIdAlreadyUsedByKLIsNKLTest() throws JrafDomainException {
		// Init data
		String report = "";
		
		// Execute
		report = batchExternalIdentifier.extIdAlreadyExists("NumeroIDGIGYA_AFKL_0439493260_BVqvlYOLtv", "GIGYA_ID", "KL");
	
		Assert.assertTrue(report.contains("400413556501"));
	}

	@Test
	public void extIdAlreadyUsedByKLIsNAFTest() throws JrafDomainException {
		// Init data
		String report = "";
		
		// Execute
		report = batchExternalIdentifier.extIdAlreadyExists("NumeroIDGIGYA_AFKL_0439493260_BVqvlYOLtv", "GIGYA_ID", "AF");
	
 		Assert.assertFalse(batchExternalIdentifier.getLineError());
	}

	@Test
	public void getIndividualGinUnknownGinTest() throws JrafDomainException {
		String gin = "";
		
		gin = batchExternalIdentifier.getIndividualGin("600004879870", "", "");

		Assert.assertTrue(batchExternalIdentifier.getLineError());
		Assert.assertEquals(";;PROVIDED GIN NOT FOUND 600004879870\n", gin);
	}

	@Test
	public void getIndividualGinUnknownGinWithOtherValuesTest() throws JrafDomainException {
		String gin = "";
		
		gin = batchExternalIdentifier.getIndividualGin("600004879870", "Other Value", "Other Value");

		Assert.assertTrue(batchExternalIdentifier.getLineError());
		Assert.assertEquals(";;PROVIDED GIN NOT FOUND 600004879870\n", gin);
	}

	@Test
	public void getIndividualGinKnownGinTest() throws JrafDomainException {
		String gin = "";
		
		gin = batchExternalIdentifier.getIndividualGin("400462465022", "", "");

		Assert.assertFalse(batchExternalIdentifier.getLineError());
		Assert.assertEquals("400462465022", gin);
	}

	
	@Test
	public void getIndividualGinUnknownFBTest() throws JrafDomainException {
		String gin = "";
		
		gin = batchExternalIdentifier.getIndividualGin("", "9999999999", "");

		Assert.assertTrue(batchExternalIdentifier.getLineError());
		Assert.assertEquals(";;PROVIDED FP NOT FOUND 9999999999\n", gin);
	}

	@Test
	public void getIndividualGinUnknownFBWithOtherValuesTest() throws JrafDomainException {
		String gin = "";
		
		gin = batchExternalIdentifier.getIndividualGin("", "9999999999", "A789BCZ");

		Assert.assertTrue(batchExternalIdentifier.getLineError());
		Assert.assertEquals(";;PROVIDED FP NOT FOUND 9999999999\n", gin);
	}

	@Test
	public void getIndividualGinKnownFBTest() throws JrafDomainException {
		String gin = "";
		
		gin = batchExternalIdentifier.getIndividualGin("", "2034011474", "");

		Assert.assertFalse(batchExternalIdentifier.getLineError());
		Assert.assertEquals("400272037442", gin);
	}

	@Test
	public void getIndividualGinUnknownMyATest() throws JrafDomainException {
		String gin = "";
		
		gin = batchExternalIdentifier.getIndividualGin("", "", "88888888");
		
		Assert.assertTrue(batchExternalIdentifier.getLineError());
		Assert.assertEquals(";;PROVIDED MA NOT FOUND 88888888\n", gin);
	}
	
	// 547800AM 400462465022
	@Test
	public void getIndividualGinKnownMyATest() throws JrafDomainException {
		String gin = "";
		
		gin = batchExternalIdentifier.getIndividualGin("", "", "547800AM");

		Assert.assertFalse(batchExternalIdentifier.getLineError());
		Assert.assertEquals("400462465022", gin);
	}
	
	
	
	/* ********************************************************************************************
	 * 
	 * INTEGRATION TEST ON TREATLINE METHOD
	 * 
	 ******************************************************************************************* */
	@Test
	@Transactional
	@Rollback(true)
	public void treatLineBasicCaseTest() throws JrafDomainException {
		// Init data
		String line = ",NouveauIDGIGYA_AFKL_0451241763_bBlVihNPgH,GIGYA_ID,,END\n";
		batchExternalIdentifier.setCommit(true);
		batchExternalIdentifier.setExtTypeIds(helper.getAllTypeExtId());
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);

		ExternalIdentifierDTO eiDTO = eids.existExternalIdentifier("NouveauIDGIGYA_AFKL_0451241763_bBlVihNPgH", "GIGYA_ID");
		// Check
		String[] result = report.toString().split(";");
		Assert.assertTrue(StringUtils.isNotBlank(result[1]));
		Assert.assertTrue(result[1].startsWith("92"));
		Assert.assertNotNull(eiDTO);
		Assert.assertEquals("SUCCESSFULLY INSERTED\n", result[2]); 
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void treatLineInsertByGINTest() throws JrafDomainException {
		// Init data
		String line = "400476889502,NouveauIDGIGYA_AFKL_0451241763_bBlVihNPgH,GIGYA_ID,,END\n";
		batchExternalIdentifier.setCommit(true);
		batchExternalIdentifier.setExtTypeIds(helper.getAllTypeExtId());
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);


		ExternalIdentifierDTO eiDTO = eids.existExternalIdentifier("NouveauIDGIGYA_AFKL_0451241763_bBlVihNPgH", "GIGYA_ID");
		// Check
		String[] result = report.toString().split(";");
		Assert.assertEquals("400476889502", result[1]);
		Assert.assertNotNull(eiDTO);
		Assert.assertEquals("SUCCESSFULLY INSERTED\n", result[2]);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	@Ignore			// On a descoppé cette fonctionnalite de recherche via FB 
	public void treatLineInsertByFBTest() throws JrafDomainException {
		// Init data
		String line = ",NouveauIDGIGYA_AFKL_0451241763_bBlVihNPgH,GIGYA_ID,,END\n";
		batchExternalIdentifier.setCommit(true);
		batchExternalIdentifier.setExtTypeIds(helper.getAllTypeExtId());
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);

		ExternalIdentifierDTO eiDTO = eids.existExternalIdentifier("NouveauIDGIGYA_AFKL_0451241763_bBlVihNPgH", "GIGYA_ID");
		// Check
		String[] result = report.toString().split(";");
		Assert.assertEquals("400272037442", result[1]);
		Assert.assertNotNull(eiDTO);
//		Assert.assertNotNull(eiDTO.getExternalIdentifierDataList());
//		Assert.assertEquals("USED_BY_KL", eiDTO.getExternalIdentifierDataList().get(0).getKey());
		Assert.assertEquals("SUCCESSFULLY INSERTED\n", result[2]);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	@Ignore			// On a descoppé cette fonctionnalite de recherche via MA
	public void treatLineInsertByMyATest() throws JrafDomainException {
		// Init data
		String line = ",NouveauIDGIGYA_AFKL_0451241763_bBlVihNPgH,GIGYA_ID,,547800AM,END\n";
		batchExternalIdentifier.setCommit(true);
		batchExternalIdentifier.setExtTypeIds(helper.getAllTypeExtId());
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);

		ExternalIdentifierDTO eiDTO = eids.existExternalIdentifier("NouveauIDGIGYA_AFKL_0451241763_bBlVihNPgH", "GIGYA_ID");

		// Check
		String[] result = report.toString().split(";");
		Assert.assertEquals("400462465022", result[1]);
		Assert.assertNotNull(eiDTO);
		Assert.assertNotNull(eiDTO.getExternalIdentifierDataList());
		Assert.assertEquals("USED_BY_KL", eiDTO.getExternalIdentifierDataList().get(0).getKey());
		Assert.assertEquals("SUCCESSFULLY INSERTED\n", result[2]);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void treatLineUpdateExistingExternalTest() throws JrafDomainException {
		String line = ",NumeroIDGIGYA_AFKL_0494031715_eEXycRxkgQ,GIGYA_ID,KL,END\n";
		batchExternalIdentifier.setCommit(true);
		batchExternalIdentifier.setExtTypeIds(helper.getAllTypeExtId());
		
		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);

		ExternalIdentifierDTO eiDTO = eids.existExternalIdentifier("NumeroIDGIGYA_AFKL_0494031715_eEXycRxkgQ", "GIGYA_ID");

		// Check
		String[] result = report.toString().split(";");
		Assert.assertEquals("920000002153", result[1]);
		Assert.assertNotNull(eiDTO);
		Assert.assertNotNull(eiDTO.getExternalIdentifierDataList());
		Assert.assertEquals("USED_BY_KL", eiDTO.getExternalIdentifierDataList().get(0).getKey());
		Assert.assertEquals("Y", eiDTO.getExternalIdentifierDataList().get(0).getValue());
		// Assert.assertEquals("EXTERNAL ALREADY EXISTS\n", result[2]);

	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void treatLineUpdateExistingExternalWithOtherKeyTest() throws JrafDomainException {
		String line = ",NumeroIDGIGYA_AFKL_0461376600_UJbsMeFklE,GIGYA_ID,AF,END\n";
		batchExternalIdentifier.setCommit(true);
		batchExternalIdentifier.setExtTypeIds(helper.getAllTypeExtId());
		
		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);

		ExternalIdentifierDTO eiDTO = eids.existExternalIdentifier("NumeroIDGIGYA_AFKL_0461376600_UJbsMeFklE", "GIGYA_ID");
		
		// Check
		String[] result = report.toString().split(";");
		Assert.assertEquals("920000002433", result[1]);
		Assert.assertNotNull(eiDTO);
		Assert.assertNotNull(eiDTO.getExternalIdentifierDataList());
		Assert.assertEquals(3, eiDTO.getExternalIdentifierDataList().size());
		Assert.assertEquals("SUCCESSFULLY INSERTED\n", result[2]);
	}
	
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void treatLineInsertNotCommitTest() throws JrafDomainException {
		String line = ",NouveauIDGIGYA_AFKL_0451241763_bBlVihNPgH,GIGYA_ID,,END";
		batchExternalIdentifier.setCommit(false);
		batchExternalIdentifier.setExtTypeIds(helper.getAllTypeExtId());
		
		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);

		Assert.assertEquals("1;;INSERTION NOT COMMITED\n", report.toString());
	}

	@Test(expected = Test.None.class /* no exception expected */)
	@Transactional
	@Ignore
	@Rollback(true)
	public void integrationBatchExternalIdentifierTest() throws Exception {
		
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("com/airfrance/jraf/batch/individu/test/BatchExternalIdentifierMultipleCasesTest.txt");
		logger.info(url);
		logger.info(Paths.get(url.toURI()).toString());
		batchExternalIdentifier.parseArgs(new String[] { "-f", Paths.get(url.toURI()).toString(), "-C"});
		batchExternalIdentifier.execute();
	}

	@Test(expected = Test.None.class /* no exception expected */)
	@Transactional
	@Ignore
	public void treatLineInsertTemporaryTest() throws JrafDomainException {
		batchExternalIdentifier.setCommit(true);
		batchExternalIdentifier.execute();
	}
	
/*	
	000006306866
	002001646053
	002002165523
	002002543475
	002002870670
	002003267275
	110002929561
	400002954837
	400004246746	
*/	
	@Test
	@Transactional
	@Rollback(true)
	public void treatLineInsertByGINWithoutExtIndDataTest() throws JrafDomainException {
		// Init data
		String line = "000006306866,33675881527,WHATSAPP_ID,AF,END\n";
		batchExternalIdentifier.setCommit(true);
		batchExternalIdentifier.setExtTypeIds(helper.getAllTypeExtId());
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);


		ExternalIdentifierDTO eiDTO = eids.existExternalIdentifier("33675881527", "WHATSAPP_ID");
		// Check
		String[] result = report.toString().split(";");
		Assert.assertEquals("000006306866", result[1]);
		Assert.assertNotNull(eiDTO);
		Assert.assertEquals("SUCCESSFULLY INSERTED\n", result[2]);
	}

	@Test
	@Transactional
	@Rollback(true)
	@Ignore 	// We must correct that, because we catch a NOT FOUND Go And See REPIND-1486
	public void treatLineInsertByGINWithPRODErrorCauseByDELETETest() throws JrafDomainException {
		// Init data
//		String line = "400266520506,31611880507,kl,HEROKU,WHATSAPP_ID";
		String line = "400266520506,31611880507,WHATSAPP_ID,kl,END\n";
		batchExternalIdentifier.setCommit(true);
		batchExternalIdentifier.setExtTypeIds(helper.getAllTypeExtId());
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchExternalIdentifier.treatLine(line, 1, report);


		ExternalIdentifierDTO eiDTO = eids.existExternalIdentifier("31611880507", "WHATSAPP_ID");
		// Check
		String[] result = report.toString().split(";");
		Assert.assertEquals("400266520506", result[1]);
		Assert.assertNotNull(eiDTO);
		Assert.assertEquals("SUCCESSFULLY INSERTED\n", result[2]);
	}

}
