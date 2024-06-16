package com.afklm.batch.contract;

import com.airfrance.common.batch.config.WebConfigTestBatch;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dao.role.BusinessRoleRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.entity.individu.AccountData;
import com.airfrance.repind.entity.role.RoleContrats;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// @Ignore	// Tests have been made for KLM extraction. We adapt that temporarly for TravelDB extraction without changing Unit Test  

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebConfigTestBatch.class)
@Transactional(value = "transactionManagerRepind")
public class BatchPurgeFlyingBlueTest {

	private static Log logger = LogFactory.getLog(BatchPurgeFlyingBlueTest.class);
	
	@Autowired
	private BatchPurgeFlyingBlue batchPurgeFlyingBlue;
	
	
	@Autowired
	private RoleContratsRepository roleContrat;

	@Autowired
	private AccountDataRepository accountData;

	@Autowired
	private BusinessRoleRepository businessRole;
	
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
			batchPurgeFlyingBlue.parseArgs(new String[] {"-C"});
			Assert.fail("Should not succeed");
		} catch (Exception e) {
			Assert.assertEquals(MANDATORY_ARGUMENT_MISSING, e.getMessage());
		}
	}
	
	@Test
	public void parseArgsNoArgumentTest() {
		// Launch Test
		try {
			batchPurgeFlyingBlue.parseArgs(null);
			Assert.fail("Should not succeed");
			
		} catch (Exception e) {
			Assert.assertEquals(NO_ARGUMENTS_TO_THE_BATCH, e.getMessage());
		}
	}
	
	@Test
	public void parseArgsUnknownArgumentTest() {
		try {
			batchPurgeFlyingBlue.parseArgs(new String[] { "-f file -C -truc"});
			Assert.fail("Should not succeed");
		} catch (Exception e) {
			Assert.assertEquals(ARGUMENT_NOT_VALID, e.getMessage());
		}
	}
	
	@Test
	public void parseArgsValidTest() throws Exception {
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "file", "-C"});
		// If this assert is not executed, there is something wrong
		Assert.assertTrue(true);
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
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 1, report,catchUpReport);
		
		Assert.assertEquals("1;;INVALID LINE '' (0)\n", report.toString());
	}

	@Test
	public void treatLineCarriageReturnLineTest() throws JrafDomainException {
		String line = "\r\n";

		StringBuilder report = new StringBuilder();
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 1, report,catchUpReport);
		
		Assert.assertEquals("1;;INVALID LINE '\r\n' (2)\n", report.toString());
	}

	@Test
	public void treatLineBlankLine() throws JrafDomainException {
		String line = "     ";

		StringBuilder report = new StringBuilder();
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 1, report,catchUpReport);
		
		Assert.assertEquals("1;;INVALID LINE '     ' (5)\n", report.toString());
	}
		
	
	/* ********************************************************************************************
	 * 
	 * UNIT TESTS ON EXTIDALREADYEXISTS METHOD
	 * 
	 ******************************************************************************************* */
	
	
	/* ********************************************************************************************
	 * 
	 * UNIT TESTS ON SOCIALALREADYEXISTS METHOD
	 * 
	 ******************************************************************************************* */
	
	
	
	/* ********************************************************************************************
	 * 
	 * INTEGRATION TEST ON TREATLINE METHOD
	 * 
	 ******************************************************************************************* */



	@Test(expected = Test.None.class /* no exception expected */)
	@Ignore		// Test for REAL without RollBack
	public void integrationBatchPurgeFlyingBlue_By_GIN_Test() throws Exception {
		
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("com/airfrance/jraf/batch/contract/it/BatchPurgeFlyingBlue_2019-08-20.txt");
		logger.info(url);
		logger.info(Paths.get(url.toURI()).toString());
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", Paths.get(url.toURI()).toString(), "-C"});
		batchPurgeFlyingBlue.execute();
	}
/*	
	@Test
	@Transactional
	@Rollback(true)
	public void integrationBatchPurgeFlyingBlue_By_CIN_Test() throws Exception {
		
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("com/airfrance/jraf/batch/contract/it/BatchPurgeFlyingBlue_2019-07-22_14-12-00.txt");
		logger.info(url);
		logger.info(Paths.get(url.toURI()).toString());
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", Paths.get(url.toURI()).toString(), "-C"});
		batchPurgeFlyingBlue.execute();
		
		Assert.assertNotNull(batchPurgeFlyingBlue);
		Assert.assertTrue(!batchPurgeFlyingBlue.getLineError());
	}
*/	
	@Test
	@Transactional
	@Rollback(true)
	public void integrationBatchPurgeFlyingBlue_By_CIN_Test() throws Exception {
		
		// Init data
		String cin = "001020080643";
		// String line = "I000000001  OS" + cin +  "ISI\n";
		String line = cin +  "\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "un_fichier_de_TEST", "-C"});
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 3, report,catchUpReport);

		// Check
		Assert.assertEquals("3;001020080643;SUCCESSFULLY DELETED MA CONTRACT NOT FOUND \n", report.toString()); 
		
		checkNullContractAndAccount(cin);
	}
	
	
/*
	@Test
	@Transactional
	@Rollback(true)
	public void integrationBatchPurgeFlyingBlue_By_CIN_With_ComPref_Test() throws Exception {
		
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("com/airfrance/jraf/batch/contract/it/BatchPurgeFlyingBlueWithComPref_2019-08-20_14-12-00.txt");
		logger.info(url);
		logger.info(Paths.get(url.toURI()).toString());
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", Paths.get(url.toURI()).toString(), "-C"});
		batchPurgeFlyingBlue.execute();
		
		Assert.assertNotNull(batchPurgeFlyingBlue);
		Assert.assertTrue(!batchPurgeFlyingBlue.getLineError());
	}
*/
	
	@Test
	@Transactional
	@Rollback(true)
	public void integrationBatchPurgeFlyingBlue_By_CIN_With_ComPref_Test() throws Exception {
		
		// Init data
		String cin = "001249913500";
		// String line = "I000000001  OS" + cin +  "ISI\n";
		String line = cin +  "\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "un_fichier_de_TEST", "-C"});
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 3, report,catchUpReport);
		// Check
		Assert.assertEquals("3;" + cin + ";SUCCESSFULLY DELETED \n", report.toString()); 
		
		checkNullContractAndAccount(cin);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void integrationBatchPurgeFlyingBlue_By_CIN_With_ComPref_Test2() throws Exception {
		
		// Init data
		String cin = "002005520306";
		// String line = "I000000001  OS" + cin +  "ISI\n";
		String line = cin +  "\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "un_fichier_de_TEST", "-C"});
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 3, report,catchUpReport);
		// Check
		Assert.assertEquals("3;" + cin + ";SUCCESSFULLY DELETED \n", report.toString()); 
		
		checkNullContractAndAccount(cin);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void integrationBatchPurgeFlyingBlue_By_CIN_With_ComPref_Test3() throws Exception {
		
		// Init data
		String cin = "002058587586";
		// String line = "I000000001  OS" + cin +  "ISI\n";
		String line = cin +  "\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "un_fichier_de_TEST", "-C"});
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 3, report,catchUpReport);
		// Check
		Assert.assertEquals("3;" + cin + ";SUCCESSFULLY DELETED \n", report.toString()); 
		
		checkNullContractAndAccount(cin);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void integrationBatchPurgeFlyingBlue_By_CIN_With_ComPref_Test4() throws Exception {
		
		// Init data
		String cin = "002081489582";
		// String line = "I000000001  OS" + cin +  "ISI\n";
		String line = cin +  "\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "un_fichier_de_TEST", "-C"});
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 3, report,catchUpReport);
		// Check
		Assert.assertEquals("3;" + cin + ";SUCCESSFULLY DELETED \n", report.toString()); 
		
		checkNullContractAndAccount(cin);
	}
/*	
	@Test
	@Transactional
	@Rollback(true)
	public void integrationBatchPurgeFlyingBlue_By_CIN_With_MA_After_FB_Test() throws Exception {
		
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("com/airfrance/jraf/batch/contract/it/BatchPurgeFlyingBlueWithMAAfterFB_2019-08-21_15-30-00.txt");
		logger.info(url);
		logger.info(Paths.get(url.toURI()).toString());
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", Paths.get(url.toURI()).toString(), "-C"});
		batchPurgeFlyingBlue.execute();
		
		Assert.assertNotNull(batchPurgeFlyingBlue);
		Assert.assertTrue(!batchPurgeFlyingBlue.getLineError());
	}
*/
	@Test
	@Transactional
	@Rollback(true)
	public void integrationBatchPurgeFlyingBlue_By_CIN_With_MA_After_FB_Test() throws Exception {
		
		// Init data
		String cin = "001024838963";
		// String line = "I000000001  OS" + cin +  "ISI\n";
		String line = cin +  "\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "un_fichier_de_TEST", "-C"});
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 3, report,catchUpReport);
		// Check
		Assert.assertEquals("3;" + cin + ";SUCCESSFULLY DELETED \n", report.toString()); 
		
		checkNullContractAndAccount(cin);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void integrationBatchPurgeFlyingBlue_By_CIN_With_MA_After_FB_Test2() throws Exception {
		
		// Init data
		String cin = "001024839361";
		// String line = "I000000001  OS" + cin +  "ISI\n";
		String line = cin +  "\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "un_fichier_de_TEST", "-C"});
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 3, report,catchUpReport);
		// Check
		Assert.assertEquals("3;" + cin + ";SUCCESSFULLY DELETED \n", report.toString()); 
				
		checkNullContractAndAccount(cin);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void integrationBatchPurgeFlyingBlue_By_CIN_With_MA_After_FB_Test3() throws Exception {
		
		// Init data
		String cin = "001024837644";
		// String line = "I000000001  OS" + cin +  "ISI\n";
		String line = cin +  "\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "un_fichier_de_TEST", "-C"});
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 3, report,catchUpReport);
		// Check
		Assert.assertEquals("3;" + cin + ";SUCCESSFULLY DELETED \n", report.toString()); 
		
		checkNullContractAndAccount(cin);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void treatLineBatchPurgeFlyingBlueMultipleFPTest() throws Exception {
		// Init data
		String cin = "000000001006";
		// String line = "I000000001  OS" + cin +  "ISI\n";
		String line = cin +  "\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "un_fichier_de_TEST", "-C"});
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 3, report,catchUpReport);
		// Check
		Assert.assertEquals("3;" + cin + ";SUCCESSFULLY DELETED \n", report.toString());
		
		checkNullContractAndAccount(cin);
		checkNotNullContractAndAccount("000000009209");				// CIN restant en lien avec ACCOUNT_DATA et ROLE_CONTRAT
	}

	@Test
	@Transactional
	@Rollback(true)
	public void treatLineBatchPurgeFlyingBlue_Triple_FPTest() throws Exception {
		// Init data
		String cin = "002112320802";
		// String line = "I000000001  OS" + cin +  "ISI\n";
		String line = cin +  "\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "un_fichier_de_TEST", "-C"});
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 3, report,catchUpReport);
		// Check
		Assert.assertEquals("3;" + cin + ";SUCCESSFULLY DELETED \n", report.toString());
		
		checkNullContractAndAccount(cin);
		checkNotNullContractAndAccount("002112320791");				// One of the two other FP
	}

	@Test
	@Transactional
	@Rollback(true)
	public void treatLineBatchPurgeFlyingBlue_OneFP_AccountDeleted_Test() throws Exception {
		// Init data
		String cin = "002126619536";
		String gin = "400474099162";
		String ma = "567101AN";
		// String line = "I000000001  OS" + cin +  "ISI\n";
		String line = cin +  "\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "un_fichier_de_TEST", "-C"});
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 3, report,catchUpReport);
		// Check
		Assert.assertEquals("3;" + cin + ";SUCCESSFULLY DELETED \n", report.toString());
		
		checkNullContractAndAccount(cin, ma);
		checkNullContractFBAndMA(cin, ma);		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void treatLineBatchPurgeFlyingBlue_OneFP_WithoutMA_WithoutAccountTest() throws Exception {
		// Init data
		String cin = "001120755343";
		// String line = "I000000001  OS" + cin +  "ISI\n";
		String line = cin +  "\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "un_fichier_de_TEST", "-C"});
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 3, report,catchUpReport);
		// Check
		Assert.assertEquals("3;" + cin + ";SUCCESSFULLY DELETED ACCOUNT NOT FOUND MA CONTRACT NOT FOUND \n", report.toString());
		
		checkNullContractAndAccount(cin);
		// checkNotNullContractAndAccount("002112320791");				// One of the two other FP
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void treatLineBatchPurgeFlyingBlue_OneFP_WithoutMA_Test() throws Exception {
		// Init data
		String cin = "001120694395";
		// String line = "I000000001  OS" + cin +  "ISI\n";
		String line = cin +  "\n";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "un_fichier_de_TEST", "-C"});
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 3, report,catchUpReport);		// Check
		Assert.assertEquals("3;" + cin + ";SUCCESSFULLY DELETED MA CONTRACT NOT FOUND \n", report.toString());
		
		checkNullContractAndAccount(cin);
		// checkNotNullContractAndAccount("002112320791");				// One of the two other FP
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void treatLineBatchPurgeFlyingBlue_OneFP_WithoutMA_Test2() throws Exception {
		// Init data
		String cin = "001120694395";
		// String line = "I000000001  OS" + cin +  "ISI\n";
		String line = cin +  "";
		
		// Execute test
		StringBuilder report = new StringBuilder();
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "un_fichier_de_TEST", "-C"});
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 3, report,catchUpReport);
		// Check
		Assert.assertEquals("3;" + cin + ";SUCCESSFULLY DELETED MA CONTRACT NOT FOUND \n", report.toString());
		
		checkNullContractAndAccount(cin);
		// checkNotNullContractAndAccount("002112320791");				// One of the two other FP
	}

	@Test
	@Transactional
	@Rollback(true)
	public void treatLineBatchPurgeFlyingBlueRemoveAccountDataFb() throws Exception {
		// Init data
		String cin = "002051501453";
		String line = cin +  "";
		checkNotNullAccountAndContractFB(cin);
		// Execute test
		StringBuilder report = new StringBuilder();
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", "un_fichier_de_TEST", "-C"});
		StringBuilder catchUpReport = new StringBuilder();
		batchPurgeFlyingBlue.treatLine(line, 1, report,catchUpReport);
		// Check
		Assert.assertEquals("1;" + cin + ";SUCCESSFULLY DELETED ACCOUNT NOT FOUND \n", report.toString());
		checkNullAccountAndContractFB(cin);

	}
	
// PRIVATE FUNCTION	
	
	private void checkNullContractAndAccount(String cin) throws JrafDaoException {		
		checkContractAndAccount(cin, false);
	}

	private void checkNotNullContractAndAccount(String cin) throws JrafDaoException {		
		checkContractAndAccount(cin, true);
	}

	private void checkContractAndAccount(String cin, boolean notNull) throws JrafDaoException {
		
		RoleContrats roleContratFP = roleContrat.findRoleContratsByNumContract(cin);
		if (notNull) {
			Assert.assertNotNull(roleContratFP);	
		} else {
			Assert.assertNull(roleContratFP);
		}
		
		AccountData accountDataFP = new AccountData();
		accountDataFP.setFbIdentifier(cin);
		Object[] fbIdentifierAndGin = accountData.findFbIdentifierAndGin(accountDataFP);
		
		if (notNull) {
			Assert.assertNotNull(fbIdentifierAndGin);	
		} else {
			Assert.assertNull(fbIdentifierAndGin);
		}
	}
	
	private void checkNullContractAndAccount(String cin, String ma) throws JrafDaoException {
		
		RoleContrats roleContratFP = roleContrat.findRoleContratsByNumContract(cin);
		Assert.assertNull(roleContratFP);											// ROLE CONTRAT FB DELETED
				
		List<String> lcin = businessRole.getSginIndByContractNumber(cin);
		Assert.assertEquals(new ArrayList<String>(), lcin);							// BUSINESS ROLE FB DELETED
				
		AccountData accountDataFP = new AccountData();
		accountDataFP.setAccountIdentifier(ma);
		Object[] fbIdentifierAndGin = accountData.findFbIdentifierAndGin(accountDataFP);
		Assert.assertNull(fbIdentifierAndGin);										// ACCOUNT DATA DELETED
	}

	private void checkNullContractFBAndMA(String cin, String ma) throws JrafDaoException {
		
		RoleContrats roleContratFP = roleContrat.findRoleContratsByNumContract(cin);
		Assert.assertNull(roleContratFP);											// ROLE CONTRAT FB DELETED
				
		List<String> lcin = businessRole.getSginIndByContractNumber(cin);
		Assert.assertEquals(new ArrayList<String>(), lcin);							// BUSINESS ROLE FB DELETED
				
		RoleContrats roleContratMA = roleContrat.findRoleContratsByNumContract(ma);
		Assert.assertNull(roleContratMA);											// ROLE CONTRAT MA DELETED
				
		List<String> lma = businessRole.getSginIndByContractNumber(ma);
		Assert.assertEquals(new ArrayList<String>(), lma);							// BUSINESS ROLE MA DELETED
	}

	private void checkNotNullAccountAndContractFB(String cin){
		RoleContrats roleContratFB = roleContrat.findRoleContratsByNumContract(cin);
		AccountData accountDataFb = accountData.findByFbIdentifier(cin);
		boolean notNull = roleContratFB != null && accountDataFb != null;
		Assert.assertTrue(notNull);
	}
	private void checkNullAccountAndContractFB(String cin){
		RoleContrats roleContratFB = roleContrat.findRoleContratsByNumContract(cin);
		AccountData accountDataFb = accountData.findByFbIdentifier(cin);
		boolean notNull = roleContratFB == null && accountDataFb == null;
		Assert.assertTrue(notNull);
	}


	@Test(expected = Test.None.class /* no exception expected */)
	@Ignore
	public void integrationBatchPurgeFlyingBlue_NewFormat_Test() throws Exception {
		
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("com/airfrance/jraf/batch/contract/it/BatchPurgeFlyingBlueWithNewFormat_2019-10-02_12-00-00.txt");
		logger.info(url);
		logger.info(Paths.get(url.toURI()).toString());
		batchPurgeFlyingBlue.parseArgs(new String[] { "-f", Paths.get(url.toURI()).toString(), ""});
		batchPurgeFlyingBlue.execute();
	}
}
