package com.afklm.repind.v1.createorupdaterolecontractws.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v1.createorupdatecomprefbasedonpermws.it.helper.CreateOrUpdateComPrefHelper;
import com.afklm.repind.v1.createorupdaterolecontractws.CreateOrUpdateRoleContractImplV1;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001567.v1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001567.v1.data.CreateUpdateRoleContractRequest;
import com.afklm.soa.stubs.w001567.v1.data.CreateUpdateRoleContractResponse;
import com.afklm.soa.stubs.w001567.v1.request.ContractRequest;
import com.afklm.soa.stubs.w001567.v1.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w001567.v1.siccommontype.Requestor;
import com.afklm.soa.stubs.w001567.v1.sicindividutype.ContractData;
import com.afklm.soa.stubs.w001567.v1.sicindividutype.ContractV2;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.reference.*;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.reference.RefProductOwnerDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.RoleUCCRDTO;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.reference.*;
import com.airfrance.repind.entity.role.RoleContrats;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.reference.internal.RefProductOwnerDS;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import com.airfrance.repind.service.ws.internal.helpers.ActionManager;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.soap.SOAPException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateRoleContractImplV1Test {
		
	@Autowired
	private AccountDataDS accountDataDS;

	@Autowired
	private BusinessRoleDS businessRoleDS;

	@Autowired
	private RefProductOwnerDS refProductOwnerDS;
	
	@Autowired
	protected ActionManager actionManager;

	@Autowired
	private RefComPrefGroupInfoRepository refComPrefGroupInfoRepository;

	@Autowired
	private RefComPrefDgtRepository refComPrefDgtRepository;

	@Autowired
	private RefComPrefGroupRepository refComPrefGroupRepository;

	@Autowired
	private RefProductRepository refProductRepository;

	@Autowired
	private RefProductComPrefGroupRepository refProductComPrefGroupRepository;

	@Autowired
	private CreateOrUpdateComPrefHelper createOrUpdateComPrefHelper;
	
	@Autowired
	private CommunicationPreferencesRepository communicationPreferencesRepository;

	@Autowired
	private RoleContratsRepository roleContratsRepository;
	
	@Autowired
	@Qualifier("passenger_CreateOrUpdateRoleContractService-v1Bean")
	@Spy
	private CreateOrUpdateRoleContractImplV1 createOrUpdateRoleContractV1;
	
	// Initialization : done before each test case annotated @Test
	@Before
	public void setUp() throws JrafDomainException, SOAPException {
		MockitoAnnotations.initMocks(this);
		
		Mockito.doNothing().when(createOrUpdateRoleContractV1).checkRightsOnContract(Mockito.any(String.class), Mockito.any(String.class));

		//		createOrUpdateRoleContractV1.setActionManager(new ActionManager());
//		
//		createOrUpdateRoleContractV1.setBusinessExceptionHelperV1(new BusinessExceptionHelper());
	}
	
	@Test
	public void test_createOrUpdateRoleContract_MissingRequest() throws SystemException {
		CreateUpdateRoleContractRequest request = null;
		
		try {
			createOrUpdateRoleContractV1.createRoleContract(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(mpe.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_133);
		}
	}

	@Test
	public void test_createOrUpdateRoleContract_MissingActionCode() throws SystemException {
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		
		try {
			createOrUpdateRoleContractV1.createRoleContract(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(mpe.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_133);
			Assert.assertTrue(mpe.getFaultInfo().getBusinessError().getErrorDetail().toLowerCase().contains("actioncode is mandatory"));
		}
	}
	
	@Test
	public void test_createOrUpdateRoleContract_MissingContractInformation() throws SystemException {
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("C");
		
		try {
			createOrUpdateRoleContractV1.createRoleContract(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(mpe.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_133);
			Assert.assertTrue(mpe.getFaultInfo().getBusinessError().getErrorDetail().toLowerCase().contains("contract informations are mandatory"));
		}
	}
	
	@Test
	public void test_createOrUpdateRoleContract_MissingProductType() throws SystemException {
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("C");
		ContractRequest contractRequest = new ContractRequest();
		ContractV2 contract = new ContractV2();
		contractRequest.setContract(contract);
		request.setContractRequest(contractRequest);
		
		try {
			createOrUpdateRoleContractV1.createRoleContract(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(mpe.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_133);
			Assert.assertTrue(mpe.getFaultInfo().getBusinessError().getErrorDetail().toLowerCase().contains("productype is mandatory"));
		}
	}
	
	@Test
	@Ignore
	public void test_createOrUpdateRoleContract_MissingContractStatus() throws SystemException {
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("C");
		ContractRequest contractRequest = new ContractRequest();
		ContractV2 contract = new ContractV2();
		contract.setProductType("UC");
		contractRequest.setContract(contract);
		request.setContractRequest(contractRequest);
		
		try {
			createOrUpdateRoleContractV1.createRoleContract(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(mpe.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_133);
			Assert.assertTrue(mpe.getFaultInfo().getBusinessError().getErrorDetail().toLowerCase().contains("contractstatus is mandatory"));
		}
	}
	
//} else if(checkContractStatus(request.getContractRequest().getContract().getContractStatus())) {
//	LOGGER.error("Invalid Parameter : invalid contractStatus (values are U, P, C, A or S)");
//	throw new InvalidParameterException("invalid contractStatus (values are U, P, C, A or S)");
//}

	@Test
	@Ignore
	public void test_createOrUpdateRoleContract_MissingRequestor() throws SystemException {
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("C");
		ContractRequest contractRequest = new ContractRequest();
		ContractV2 contract = new ContractV2();
		contract.setProductType("UC");
		contract.setContractStatus("C");
		contractRequest.setContract(contract);
		request.setContractRequest(contractRequest);
		try {
			createOrUpdateRoleContractV1.createRoleContract(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(mpe.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_133);
			Assert.assertTrue(mpe.getFaultInfo().getBusinessError().getErrorDetail().toLowerCase().contains("requestor is mandatory"));
		}
	}
	
	@Test
	public void test_createOrUpdateRoleContract_MissingChannel() throws SystemException {
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("C");
		ContractRequest contractRequest = new ContractRequest();
		ContractV2 contract = new ContractV2();
		contract.setProductType("UC");
		contract.setContractStatus("C");
		contractRequest.setContract(contract);
		request.setContractRequest(contractRequest);
		Requestor requestor = new Requestor();
		request.setRequestor(requestor);
		try {
			createOrUpdateRoleContractV1.createRoleContract(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(mpe.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_133);
			Assert.assertTrue(mpe.getFaultInfo().getBusinessError().getErrorDetail().toLowerCase().contains("channel is mandatory"));
		}
	}
	
	@Test
	public void test_createOrUpdateRoleContract_MissingSite() throws SystemException {
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("C");
		ContractRequest contractRequest = new ContractRequest();
		ContractV2 contract = new ContractV2();
		contract.setProductType("UC");
		contract.setContractStatus("C");
		contractRequest.setContract(contract);
		request.setContractRequest(contractRequest);
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		request.setRequestor(requestor);
		try {
			createOrUpdateRoleContractV1.createRoleContract(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(mpe.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_133);
			Assert.assertTrue(mpe.getFaultInfo().getBusinessError().getErrorDetail().toLowerCase().contains("site is mandatory"));
		}
	}
	
	@Test
	public void test_createOrUpdateRoleContract_MissingSignature() throws SystemException {
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("C");
		ContractRequest contractRequest = new ContractRequest();
		ContractV2 contract = new ContractV2();
		contract.setProductType("UC");
		contract.setContractStatus("C");
		contractRequest.setContract(contract);
		request.setContractRequest(contractRequest);
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		request.setRequestor(requestor);
		try {
			createOrUpdateRoleContractV1.createRoleContract(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(mpe.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_133);
			Assert.assertTrue(mpe.getFaultInfo().getBusinessError().getErrorDetail().toLowerCase().contains("signature is mandatory"));
		}
	}
	
	@Test
	public void test_createOrUpdateRoleContract_MissingCeIdOnCreateUCCR() throws SystemException {
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("C");
		request.setGin("400424668522");
		ContractRequest cr = new ContractRequest();
		ContractV2 contract = new ContractV2();
		contract.setContractNumber("999999999900");
		contract.setProductType("UC");
		contract.setContractStatus("C");
		cr.setContract(contract);
		request.setContractRequest(cr);
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("RI");
		request.setRequestor(requestor);
		try {
			CreateUpdateRoleContractResponse response = createOrUpdateRoleContractV1.createRoleContract(request);
			Assert.fail("Numero de contrat manquant");
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(mpe.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_133);
			Assert.assertTrue(mpe.getFaultInfo().getBusinessError().getErrorDetail().toLowerCase().contains("corporateenvironmentid is mandatory"));
		}
	}
	
	@Test
	public void test_createOrUpdateRoleContract_MissingContractNumberOnCreateUCCR() throws SystemException {
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("C");
		request.setGin("400424668522");
		ContractRequest cr = new ContractRequest();
		ContractV2 contract = new ContractV2();
		ContractData contractData = new ContractData();
		contract.setProductType("UC");
		contract.setContractStatus("C");
		contractData.setKey("CorporateEnvironmentID");
		contractData.setValue("testU");
		cr.setContract(contract);
		cr.getContractData().add(contractData);
		request.setContractRequest(cr);
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("RI");
		request.setRequestor(requestor);
		try {
			CreateUpdateRoleContractResponse response = createOrUpdateRoleContractV1.createRoleContract(request);
			Assert.fail("Numero de contrat manquant");
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(mpe.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_133);
			Assert.assertTrue(mpe.getFaultInfo().getBusinessError().getErrorDetail().toLowerCase().contains("contractnumber is mandatory"));
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_createOrUpdateRoleContract_DuplicateContractNumberCreateUCCR() throws SystemException, JrafDomainException {
		// Init DB with test value
		BusinessRoleDTO brDto = new BusinessRoleDTO();
		brDto.setGinInd("400424668522");
		brDto.setNumeroContrat("999999999900");
		brDto.setType("U");
		
		RoleUCCRDTO uccrDto = new RoleUCCRDTO();
		uccrDto.setCleRole(brDto.getCleRole());
		uccrDto.setCeID("testU");
		uccrDto.setGin("400424668522");
		uccrDto.setType("UC");
		uccrDto.setUccrID("999999999900");
		uccrDto.setEtat("C");
		brDto.setRoleUCCRDTO(uccrDto);
		
		businessRoleDS.createABusinessRole(brDto);
		
		// Call webservice
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("C");
		ContractRequest cr = new ContractRequest();
		ContractV2 contract = new ContractV2();
		ContractData contractData = new ContractData();
		contract.setContractNumber("999999999900");
		contract.setProductType("UC");
		contract.setContractStatus("C");
		contractData.setKey("CorporateEnvironmentID");
		contractData.setValue("testU");
		cr.setContract(contract);
		cr.getContractData().add(contractData);
		request.setContractRequest(cr);
		request.setGin("400424668522");
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("RI");
		request.setRequestor(requestor);
		try {
			CreateUpdateRoleContractResponse response = createOrUpdateRoleContractV1.createRoleContract(request);
			Assert.fail("On cree un numero de contrat deje existant");
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(mpe.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_134);
			Assert.assertTrue(mpe.getFaultInfo().getBusinessError().getErrorDetail().toLowerCase().contains("already exists"));
		}
	}
	
	@Test
	public void test_createOrUpdateRoleContract_UpdateUnknownContract() throws SystemException {
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("U");
		ContractRequest cr = new ContractRequest();
		ContractV2 contract = new ContractV2();
		ContractData contractData = new ContractData();
		contract.setContractNumber("000000000000");
		contract.setProductType("UC");
		contract.setContractStatus("S");
		contractData.setKey("CorporateEnvironmentID");
		contractData.setValue("testU");
		cr.setContract(contract);
		cr.getContractData().add(contractData);
		request.setContractRequest(cr);
		request.setGin("400424668522");
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("RI");
		request.setRequestor(requestor);
		try {
			CreateUpdateRoleContractResponse response = createOrUpdateRoleContractV1.createRoleContract(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(mpe.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_212);
			Assert.assertTrue(mpe.getFaultInfo().getBusinessError().getErrorDetail().toLowerCase().contains("not found"));
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_createOrUpdateRoleContract_UpdateContract() throws SystemException, JrafDomainException, BusinessErrorBlocBusinessException {
		// Init DB with test value
		BusinessRoleDTO brDto = new BusinessRoleDTO();
		brDto.setGinInd("400424668522");
		brDto.setNumeroContrat("999999999999");
		brDto.setType("U");
		
		RoleUCCRDTO uccrDto = new RoleUCCRDTO();
		uccrDto.setCleRole(brDto.getCleRole());
		uccrDto.setCeID("testU");
		uccrDto.setGin("400424668522");
		uccrDto.setType("UC");
		uccrDto.setUccrID("999999999999");
		uccrDto.setEtat("C");
		brDto.setRoleUCCRDTO(uccrDto);
		
		businessRoleDS.createABusinessRole(brDto);
		
		// Call webservice
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("U");
		ContractRequest cr = new ContractRequest();
		ContractV2 contract = new ContractV2();
		ContractData contractData = new ContractData();
		contract.setContractNumber("999999999999");
		contract.setProductType("UC");
		contract.setContractStatus("S");
		contract.setValidityStartDate(new Date());
		contract.setValidityEndDate(new Date());
		contractData.setKey("CorporateEnvironmentID");
		contractData.setValue("testU");
		cr.setContract(contract);
		cr.getContractData().add(contractData);
		request.setContractRequest(cr);
		request.setGin("400424668522");
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("RI");
		request.setRequestor(requestor);
		
		CreateUpdateRoleContractResponse response = createOrUpdateRoleContractV1.createRoleContract(request);
		
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getContractNumber(), "999999999999");
	}

	// REPIND-876 : Change process for Hachiko
	@Test
	@Transactional
	@Rollback(true)
	public void test_createOrUpdateRoleContract_CreateContractFromHachiko() throws SystemException, JrafDomainException, BusinessErrorBlocBusinessException {

		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setGin("400518869843");
		request.setActionCode("C");
		Requestor requestor = new Requestor();
		requestor.setSignature("HACHIKO");
		requestor.setChannel("BAF");
		requestor.setSite("AF");
		requestor.setApplicationCode("ISI");
		ContractRequest contractRequest = new ContractRequest();
		ContractV2 contract = new ContractV2();
		contract.setContractNumber("test12345678");
		contract.setContractType("F");
		contract.setProductType("FP");
		contract.setCompanyCode("AF");
		contract.setContractStatus("C");
		contractRequest.setContract(contract);
		request.setContractRequest(contractRequest);
		request.setRequestor(requestor);

		ContractData contractData1  = new ContractData();
		contractData1.setKey("TIERLEVEL");
		contractData1.setValue("A");

		ContractData contractData2  = new ContractData();
		contractData2.setKey("MEMBERTYPE");
		contractData2.setValue("");

		ContractData contractData3  = new ContractData();
		contractData3.setKey("MILESBALANCE");
		contractData3.setValue("0");

		ContractData contractData4  = new ContractData();
		contractData4.setKey("QUALIFYINGMILES");
		contractData4.setValue("0");

		ContractData contractData5  = new ContractData();
		contractData5.setKey("QUALIFYINGSEGMENTS");
		contractData5.setValue("0");

		request.getContractRequest().getContractData().add(contractData1);
		request.getContractRequest().getContractData().add(contractData2);
		request.getContractRequest().getContractData().add(contractData3);
		request.getContractRequest().getContractData().add(contractData4);
		request.getContractRequest().getContractData().add(contractData5);
		
		CreateUpdateRoleContractResponse response = createOrUpdateRoleContractV1.createRoleContract(request);
		
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getContractNumber(), "test12345678");
		
		AccountDataDTO accountData = accountDataDS.getByGin("400518869843");
		Assert.assertNotNull(accountData);
		Assert.assertTrue(accountData.getPasswordToChange() == 9);
	}
	
	// REPIND-876 : Change process for Hachiko
	@Test
	@Transactional
	@Rollback(true)
	public void test_createOrUpdateRoleContract_CreateContractNotFromHachiko() throws SystemException, JrafDomainException, BusinessErrorBlocBusinessException {

		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setGin("400518869843");
		request.setActionCode("C");
		Requestor requestor = new Requestor();
		requestor.setSignature("HACHIKO");
		requestor.setChannel("BAF");
		requestor.setSite("AF");
		requestor.setApplicationCode("HAC");
		ContractRequest contractRequest = new ContractRequest();
		ContractV2 contract = new ContractV2();
		contract.setContractNumber("test12345678");
		contract.setContractType("F");
		contract.setProductType("FP");
		contract.setCompanyCode("AF");
		contract.setContractStatus("C");
		contractRequest.setContract(contract);
		request.setContractRequest(contractRequest);
		request.setRequestor(requestor);

		ContractData contractData1  = new ContractData();
		contractData1.setKey("TIERLEVEL");
		contractData1.setValue("A");

		ContractData contractData2  = new ContractData();
		contractData2.setKey("MEMBERTYPE");
		contractData2.setValue("");

		ContractData contractData3  = new ContractData();
		contractData3.setKey("MILESBALANCE");
		contractData3.setValue("0");

		ContractData contractData4  = new ContractData();
		contractData4.setKey("QUALIFYINGMILES");
		contractData4.setValue("0");

		ContractData contractData5  = new ContractData();
		contractData5.setKey("QUALIFYINGSEGMENTS");
		contractData5.setValue("0");

		request.getContractRequest().getContractData().add(contractData1);
		request.getContractRequest().getContractData().add(contractData2);
		request.getContractRequest().getContractData().add(contractData3);
		request.getContractRequest().getContractData().add(contractData4);
		request.getContractRequest().getContractData().add(contractData5);
		
		CreateUpdateRoleContractResponse response = createOrUpdateRoleContractV1.createRoleContract(request);
		
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getContractNumber(), "test12345678");
		
		AccountDataDTO accountData = accountDataDS.getByGin("400518869843");
		Assert.assertNotNull(accountData);
		Assert.assertTrue(accountData.getPasswordToChange() == 0);
	}
	


	private CreateUpdateRoleContractRequest createRequest() {
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		ContractRequest contractRequest = new ContractRequest(); 
		ContractData contractData1 = new ContractData();
		contractData1.setKey("TIERLEVEL");
		contractData1.setValue("A");
		ContractData contractData2 = new ContractData();
		contractData2.setKey("MEMBERTYPE");
		contractData2.setValue("T");
		ContractData contractData3 = new ContractData();
		contractData3.setKey("MILESBALANCE");
		contractData3.setValue("24275");
		ContractData contractData4 = new ContractData();
		contractData4.setKey("QUALIFYINGMILES");
		contractData4.setValue("24275");
		ContractData contractData5 = new ContractData();
		contractData5.setKey("QUALIFYINGSEGMENTS");
		contractData5.setValue("2425");
		
		contractRequest.getContractData().add(contractData1);
		contractRequest.getContractData().add(contractData2);
		contractRequest.getContractData().add(contractData3);
		contractRequest.getContractData().add(contractData4);
		contractRequest.getContractData().add(contractData5);
		request.setContractRequest(contractRequest);
		return request;
	}
	
	@Test
	public void createUpdateRoleContract_userIdUnknownTest () throws JrafDomainException {
		// Init header
		String userID = "none";
		String consumerID = "w00799265"; // = HACHIKO
		
		String type = "FP";
		String subType = null;
		
		List<RefProductOwnerDTO> rpos = refProductOwnerDS.getAssociations(type, subType, userID, consumerID);
		
		Assert.assertTrue(rpos != null && !rpos.isEmpty());	
	}
	
	/**
	 * Create a FlyingBlue contract with serveral comprefs
	 * 
	 * F N FB_PART -> Optin
	 * F N FB_PROG -> Optin
	 * F N FB_NEWS -> Optout
	 * U S UL_PS   -> Optin
	 * T N KQ      -> Not supported
	 * 
	 * @throws InvalidParameterException 
	 * @throws JrafDaoException 
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@SuppressWarnings("deprecation")
	@Test
	@Transactional
	@Rollback(true)
	public void createContractWithComPref() throws InvalidParameterException, JrafDaoException, BusinessErrorBlocBusinessException, SystemException {
		/*-- CREATE COM PREF MANDATORY Y --*/
		RefComPrefGroupInfo refComPrefGroupInfo = new RefComPrefGroupInfo();
		refComPrefGroupInfo.setCode("TEST_UNIT1");
		refComPrefGroupInfo.setLibelleFR("MA QUESTION");
		refComPrefGroupInfo.setLibelleEN("MY QUESTION");
		refComPrefGroupInfo.setMandatoryOption("Y");
		refComPrefGroupInfo.setDefaultOption("Y");
		refComPrefGroupInfo.setDateCreation(new Date());
		refComPrefGroupInfo.setSiteCreation("QVI");
		refComPrefGroupInfo.setSignatureCreation("JUNIT");
		refComPrefGroupInfoRepository.saveAndFlush(refComPrefGroupInfo);
		
		RefComPrefDgt refComPrefDgt = refComPrefDgtRepository.findByDGT("F", "N", "FB_PART").get(0);

		RefComPrefGroup refComPrefGroup = new RefComPrefGroup();
		refComPrefGroup.setRefComPrefGroupId(new RefComPrefGroupId(refComPrefDgt ,refComPrefGroupInfo));
		refComPrefGroup.setDateCreation(new Date());
		refComPrefGroup.setSignatureCreation("JUNIT");
		refComPrefGroup.setSiteCreation("QVI");
		refComPrefGroupRepository.saveAndFlush(refComPrefGroup);
		
		refComPrefDgt = refComPrefDgtRepository.findByDGT("U", "S", "UL_PS").get(0);

		refComPrefGroup = new RefComPrefGroup();
		refComPrefGroup.setRefComPrefGroupId(new RefComPrefGroupId(refComPrefDgt ,refComPrefGroupInfo));
		refComPrefGroup.setDateCreation(new Date());
		refComPrefGroup.setSignatureCreation("JUNIT");
		refComPrefGroup.setSiteCreation("QVI");
		refComPrefGroupRepository.saveAndFlush(refComPrefGroup);
		
		refComPrefDgt = refComPrefDgtRepository.findByDGT("T", "N", "KQ").get(0);

		refComPrefGroup = new RefComPrefGroup();
		refComPrefGroup.setRefComPrefGroupId(new RefComPrefGroupId(refComPrefDgt ,refComPrefGroupInfo));
		refComPrefGroup.setDateCreation(new Date());
		refComPrefGroup.setSignatureCreation("JUNIT");
		refComPrefGroup.setSiteCreation("QVI");
		refComPrefGroupRepository.saveAndFlush(refComPrefGroup);
		
		Optional<RefProduct> refProduct = refProductRepository.findById(1);
		
		RefProductComPrefGroup refProductComPrefGroup = new RefProductComPrefGroup();
		refProductComPrefGroup.setRefProductComPrefGroupId(new RefProductComPrefGroupId(refProduct.get(), refComPrefGroupInfo));
		refProductComPrefGroup.setDateCreation(new Date());
		refProductComPrefGroup.setSignatureCreation("JUNIT");
		refProductComPrefGroup.setSiteCreation("QVI");
		refProductComPrefGroupRepository.saveAndFlush(refProductComPrefGroup);
		
		/*-- CREATE COMPREF MANDATORY N BUT DEFAULT Y --*/
		refComPrefGroupInfo = new RefComPrefGroupInfo();
		refComPrefGroupInfo.setCode("TEST_UNIT2");
		refComPrefGroupInfo.setLibelleFR("MA QUESTION");
		refComPrefGroupInfo.setLibelleEN("MY QUESTION");
		refComPrefGroupInfo.setMandatoryOption("N");
		refComPrefGroupInfo.setDefaultOption("Y");
		refComPrefGroupInfo.setDateCreation(new Date());
		refComPrefGroupInfo.setSiteCreation("QVI");
		refComPrefGroupInfo.setSignatureCreation("JUNIT");
		refComPrefGroupInfoRepository.saveAndFlush(refComPrefGroupInfo);
		
		refComPrefDgt = refComPrefDgtRepository.findByDGT("F", "N", "FB_PROG").get(0);

		refComPrefGroup = new RefComPrefGroup();
		refComPrefGroup.setRefComPrefGroupId(new RefComPrefGroupId(refComPrefDgt ,refComPrefGroupInfo));
		refComPrefGroup.setDateCreation(new Date());
		refComPrefGroup.setSignatureCreation("JUNIT");
		refComPrefGroup.setSiteCreation("QVI");
		refComPrefGroupRepository.saveAndFlush(refComPrefGroup);
		
		refProduct = refProductRepository.findById(1);
		
		refProductComPrefGroup = new RefProductComPrefGroup();
		refProductComPrefGroup.setRefProductComPrefGroupId(new RefProductComPrefGroupId(refProduct.get(), refComPrefGroupInfo));
		refProductComPrefGroup.setDateCreation(new Date());
		refProductComPrefGroup.setSignatureCreation("JUNIT");
		refProductComPrefGroup.setSiteCreation("QVI");
		refProductComPrefGroupRepository.saveAndFlush(refProductComPrefGroup);
		
		/*-- CREATE COMPREF MANDATORY N BUT DEFAULT N --*/
		 refComPrefGroupInfo = new RefComPrefGroupInfo();
		refComPrefGroupInfo.setCode("TEST_UNIT3");
		refComPrefGroupInfo.setLibelleFR("MA QUESTION");
		refComPrefGroupInfo.setLibelleEN("MY QUESTION");
		refComPrefGroupInfo.setMandatoryOption("N");
		refComPrefGroupInfo.setDefaultOption("N");
		refComPrefGroupInfo.setDateCreation(new Date());
		refComPrefGroupInfo.setSiteCreation("QVI");
		refComPrefGroupInfo.setSignatureCreation("JUNIT");
		refComPrefGroupInfoRepository.saveAndFlush(refComPrefGroupInfo);
		
		refComPrefDgt = refComPrefDgtRepository.findByDGT("F", "N", "FB_NEWS").get(0);
		
		refComPrefGroup = new RefComPrefGroup();
		refComPrefGroup.setRefComPrefGroupId(new RefComPrefGroupId(refComPrefDgt ,refComPrefGroupInfo));
		refComPrefGroup.setDateCreation(new Date());
		refComPrefGroup.setSignatureCreation("JUNIT");
		refComPrefGroup.setSiteCreation("QVI");
		refComPrefGroupRepository.saveAndFlush(refComPrefGroup);
		
		refProduct = refProductRepository.findById(1);
		
		refProductComPrefGroup = new RefProductComPrefGroup();
		refProductComPrefGroup.setRefProductComPrefGroupId(new RefProductComPrefGroupId(refProduct.get(), refComPrefGroupInfo));
		refProductComPrefGroup.setDateCreation(new Date());
		refProductComPrefGroup.setSignatureCreation("JUNIT");
		refProductComPrefGroup.setSiteCreation("QVI");
		refProductComPrefGroupRepository.saveAndFlush(refProductComPrefGroup);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);

		/*-- REQUEST FOR CREATING CONTRACT --*/
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("C");
		request.setGin(gin);
		
		ContractRequest contractRequest = new ContractRequest();
		
		ContractV2 contract = new ContractV2();
		contract.setContractNumber("000000002049");
		contract.setContractType("C");
		contract.setProductType("FP");
		contract.setContractStatus("C");
		
		List<ContractData> contractDatas = new ArrayList<>();
		ContractData contractData = new ContractData();
		contractData.setKey("tierLevel");
		contractData.setValue("A");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("memberType");
		contractData.setValue("T");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("milesBalance");
		contractData.setValue("24275");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("qualifyingMiles");
		contractData.setValue("1000");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("qualifyingSegments");
		contractData.setValue("50");
		contractDatas.add(contractData);
		
		contractRequest.getContractData().addAll(contractDatas);
		
		request.setContractRequest(contractRequest);
		contractRequest.setContract(contract);
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("TEST");
		requestor.setContext("CREATE_COMPREF");
		
		request.setRequestor(requestor);
		
		CreateUpdateRoleContractResponse response = createOrUpdateRoleContractV1.createRoleContract(request);

		Assert.assertTrue(response.isSuccess());
		
		Assert.assertEquals(4, response.getCommunicationPreferenceResponse().getCommunicationpreferences().size());
		Assert.assertEquals(1, response.getWarningResponse().getWarning().size());
		
		List<CommunicationPreferences> communicationPreferences = communicationPreferencesRepository.findByGin(gin);
		Assert.assertEquals(4, communicationPreferences.size());
		for (CommunicationPreferences comPref : communicationPreferences) {
			if (comPref.getComGroupType().equalsIgnoreCase("FB_NEWS")) {
				Assert.assertEquals("N", comPref.getSubscribe());
			} else if (comPref.getComGroupType().equalsIgnoreCase("FB_PROG")) {
				Assert.assertEquals("Y", comPref.getSubscribe());
			} else if (comPref.getComGroupType().equalsIgnoreCase("FB_PART")) {
				Assert.assertEquals("Y", comPref.getSubscribe());
			} else if (comPref.getComGroupType().equalsIgnoreCase("UL_PS")) {
				Assert.assertEquals("Y", comPref.getSubscribe());
			}
		}
		
		Assert.assertEquals("T - N - KQ : This domain is not supported yet", response.getWarningResponse().getWarning().get(0).getWarningDetail());
		Assert.assertEquals("Cannot create communication preferences", response.getWarningResponse().getWarning().get(0).getWarningLabel());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void deleteContractFP() throws BusinessErrorBlocBusinessException, SystemException, InvalidParameterException, JrafDaoException {
		/*-- REQUEST FOR CREATING CONTRACT --*/
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("D");
		request.setGin("400491001395");
		
		ContractRequest contractRequest = new ContractRequest();
		
		ContractV2 contract = new ContractV2();
		contract.setContractNumber("001116082375");
		contract.setContractType("C");
		contract.setProductType("FP");
		contract.setContractStatus("C");
		
		List<ContractData> contractDatas = new ArrayList<>();
		ContractData contractData = new ContractData();
		contractData.setKey("tierLevel");
		contractData.setValue("A");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("memberType");
		contractData.setValue("T");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("milesBalance");
		contractData.setValue("24275");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("qualifyingMiles");
		contractData.setValue("1000");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("qualifyingSegments");
		contractData.setValue("50");
		contractDatas.add(contractData);
		
		contractRequest.getContractData().addAll(contractDatas);
		
		request.setContractRequest(contractRequest);
		contractRequest.setContract(contract);
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("TEST");
		
		request.setRequestor(requestor);
		
		CreateUpdateRoleContractResponse response = createOrUpdateRoleContractV1.createRoleContract(request);
		
		Assert.assertTrue(response.isSuccess());
		// ContractNumber is NULL be cause we delete the line ! 
		Assert.assertEquals(response.getContractNumber(), null);
		// Assert.assertEquals(response.getContractNumber(), "001116082375");
	}
	
	/**
	 * Create a FlyingBlue Contract and then delete it
	 * 
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 * @throws InvalidParameterException
	 * @throws JrafDaoException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void createDeleteContract() throws BusinessErrorBlocBusinessException, SystemException, InvalidParameterException, JrafDaoException {
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);
		
		/*-- REQUEST FOR CREATING CONTRACT --*/
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("C");
		request.setGin(gin);
		
		ContractRequest contractRequest = new ContractRequest();
		
		ContractV2 contract = new ContractV2();
		contract.setContractNumber("000000002049");
		contract.setContractType("C");
		contract.setProductType("FP");
		contract.setContractStatus("C");
		
		List<ContractData> contractDatas = new ArrayList<>();
		ContractData contractData = new ContractData();
		contractData.setKey("tierLevel");
		contractData.setValue("A");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("memberType");
		contractData.setValue("T");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("milesBalance");
		contractData.setValue("24275");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("qualifyingMiles");
		contractData.setValue("1000");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("qualifyingSegments");
		contractData.setValue("50");
		contractDatas.add(contractData);
		
		contractRequest.getContractData().addAll(contractDatas);
		
		request.setContractRequest(contractRequest);
		contractRequest.setContract(contract);
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("TEST");
		requestor.setContext("CREATE_COMPREF");
		
		request.setRequestor(requestor);
		
		CreateUpdateRoleContractResponse response = createOrUpdateRoleContractV1.createRoleContract(request);
		
		Assert.assertTrue(response.isSuccess());

		List<RoleContrats> listContracts = roleContratsRepository.findRoleContrats(gin);
		Assert.assertEquals(1, listContracts.size());
		Assert.assertEquals("C", listContracts.get(0).getEtat());
		Assert.assertEquals("FP", listContracts.get(0).getTypeContrat());
		Assert.assertEquals("000000002049", listContracts.get(0).getNumeroContrat());
		
		/*-- REQUEST FOR DELETING CONTRACT --*/
		CreateUpdateRoleContractRequest requestD = new CreateUpdateRoleContractRequest();
		requestD.setActionCode("D");
		requestD.setGin(gin);
		
		ContractRequest contractRequestD = new ContractRequest();
		
		ContractV2 contractD = new ContractV2();
		contractD.setContractNumber("000000002049");
		contractD.setProductType("FP");
		
		requestD.setContractRequest(contractRequestD);
		contractRequestD.setContract(contractD);
			
		requestor.setContext(null);
		requestD.setRequestor(requestor);
		
		CreateUpdateRoleContractResponse responseD = createOrUpdateRoleContractV1.createRoleContract(requestD);
		
		Assert.assertTrue(responseD.isSuccess());
		
		listContracts = roleContratsRepository.findRoleContrats(gin);
		Assert.assertEquals(0, listContracts.size());
	}
	
	/**
	 * Create a FlyingBlue Contract and then update it
	 * 
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 * @throws InvalidParameterException
	 * @throws JrafDaoException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void createUpdateContract() throws BusinessErrorBlocBusinessException, SystemException, InvalidParameterException, JrafDaoException {
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);
		
		/*-- REQUEST FOR CREATING CONTRACT --*/
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("C");
		request.setGin(gin);
		
		ContractRequest contractRequest = new ContractRequest();
		
		ContractV2 contract = new ContractV2();
		contract.setContractNumber("000000002049");
		contract.setContractType("C");
		contract.setProductType("FP");
		contract.setContractStatus("C");
		
		List<ContractData> contractDatas = new ArrayList<>();
		ContractData contractData = new ContractData();
		contractData.setKey("tierLevel");
		contractData.setValue("A");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("memberType");
		contractData.setValue("T");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("milesBalance");
		contractData.setValue("24275");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("qualifyingMiles");
		contractData.setValue("1000");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("qualifyingSegments");
		contractData.setValue("50");
		contractDatas.add(contractData);
		
		contractRequest.getContractData().addAll(contractDatas);
		
		request.setContractRequest(contractRequest);
		contractRequest.setContract(contract);
		
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("TEST");
		
		request.setRequestor(requestor);
		
		CreateUpdateRoleContractResponse response = createOrUpdateRoleContractV1.createRoleContract(request);
		
		Assert.assertTrue(response.isSuccess());

		List<RoleContrats> listContracts = roleContratsRepository.findRoleContrats(gin);
		Assert.assertEquals(1, listContracts.size());
		Assert.assertEquals("C", listContracts.get(0).getEtat());
		Assert.assertEquals("FP", listContracts.get(0).getTypeContrat());	
		Assert.assertEquals("000000002049", listContracts.get(0).getNumeroContrat());

// REPIND-1381 : We do not return this data when Boolean is activated as now in DEV ENV		 
//		Assert.assertEquals("A", listContracts.get(0).getTier());
//		Assert.assertEquals("T", listContracts.get(0).getMemberType());
//		Assert.assertEquals("24275", listContracts.get(0).getSoldeMiles().toString());
//		Assert.assertEquals("1000", listContracts.get(0).getMilesQualif().toString());
//		Assert.assertEquals("50", listContracts.get(0).getSegmentsQualif().toString());
		
		/*-- REQUEST FOR UPDATING CONTRACT --*/
		CreateUpdateRoleContractRequest requestU = new CreateUpdateRoleContractRequest();
		requestU.setActionCode("U");
		requestU.setGin(gin);
		
		ContractRequest contractRequestU= new ContractRequest();
		
		ContractV2 contractU = new ContractV2();
		contractU.setContractNumber("000000002049");
		contractU.setProductType("FP");
		contractU.setContractStatus("S");
		
		List<ContractData> contractDatasU = new ArrayList<>();
		ContractData contractDataU = new ContractData();
		contractDataU.setKey("tierLevel");
		contractDataU.setValue("P");
		contractDatasU.add(contractDataU);
		contractDataU = new ContractData();
		contractDataU.setKey("memberType");
		contractDataU.setValue("T");
		contractDatasU.add(contractDataU);
		contractDataU = new ContractData();
		contractDataU.setKey("milesBalance");
		contractDataU.setValue("0");
		contractDatasU.add(contractDataU);
		contractDataU = new ContractData();
		contractDataU.setKey("qualifyingMiles");
		contractDataU.setValue("0");
		contractDatasU.add(contractDataU);
		contractDataU = new ContractData();
		contractDataU.setKey("qualifyingSegments");
		contractDataU.setValue("0");
		contractDatasU.add(contractDataU);
		
		contractRequestU.getContractData().addAll(contractDatasU);
		
		requestU.setContractRequest(contractRequestU);
		contractRequestU.setContract(contractU);
			
		requestU.setRequestor(requestor);
		
		CreateUpdateRoleContractResponse responseU = createOrUpdateRoleContractV1.createRoleContract(requestU);
		
		Assert.assertTrue(responseU.isSuccess());
		
		listContracts = roleContratsRepository.findRoleContrats(gin);
		Assert.assertEquals(1, listContracts.size());
		Assert.assertEquals("C", listContracts.get(0).getEtat());
		Assert.assertEquals("FP", listContracts.get(0).getTypeContrat());
		Assert.assertEquals("000000002049", listContracts.get(0).getNumeroContrat());

// REPIND-1381 : We do not return this data when Boolean is activated as now in DEV ENV		
//		Assert.assertEquals("P", listContracts.get(0).getTier());
//		Assert.assertEquals("T", listContracts.get(0).getMemberType());
//		Assert.assertEquals("0", listContracts.get(0).getSoldeMiles().toString());
//		Assert.assertEquals("0", listContracts.get(0).getMilesQualif().toString());
//		Assert.assertEquals("0", listContracts.get(0).getSegmentsQualif().toString());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void test_createOrUpdateRoleContract_UpdateContract2() throws SystemException, JrafDomainException, BusinessErrorBlocBusinessException {
		// Init DB with test value
		BusinessRoleDTO brDto = new BusinessRoleDTO();
		brDto.setGinInd("400424668522");
		brDto.setNumeroContrat("999999999999");
		brDto.setType("U");
		
		RoleUCCRDTO uccrDto = new RoleUCCRDTO();
		uccrDto.setCleRole(brDto.getCleRole());
		uccrDto.setCeID("testU");
		uccrDto.setGin("400424668522");
		uccrDto.setType("UC");
		uccrDto.setUccrID("999999999999");
		uccrDto.setEtat("C");
		brDto.setRoleUCCRDTO(uccrDto);
		
		businessRoleDS.createABusinessRole(brDto);
		
		// Call webservice
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("U");
		ContractRequest cr = new ContractRequest();
		ContractV2 contract = new ContractV2();
		ContractData contractData = new ContractData();
		contract.setContractNumber("999999999999");
		contract.setProductType("UC");
		contract.setContractStatus("S");
		contract.setValidityStartDate(new Date());
		contract.setValidityEndDate(new Date());
		contractData.setKey("CorporateEnvironmentID");
		contractData.setValue("testU");
		cr.setContract(contract);
		cr.getContractData().add(contractData);
		request.setContractRequest(cr);
		request.setGin("400424668522");
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("RI");
		request.setRequestor(requestor);
		
		CreateUpdateRoleContractResponse response = createOrUpdateRoleContractV1.createRoleContract(request);
		
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getContractNumber(), "999999999999");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_createOrUpdateRoleContract_DuplicateContractNumberCreateUCCR2() throws SystemException, JrafDomainException {
		// Init DB with test value
		BusinessRoleDTO brDto = new BusinessRoleDTO();
		brDto.setGinInd("400424668522");
		brDto.setNumeroContrat("999999999900");
		brDto.setType("U");
		
		RoleUCCRDTO uccrDto = new RoleUCCRDTO();
		uccrDto.setCleRole(brDto.getCleRole());
		uccrDto.setCeID("testU");
		uccrDto.setGin("400424668522");
		uccrDto.setType("UC");
		uccrDto.setUccrID("999999999900");
		uccrDto.setEtat("C");
		brDto.setRoleUCCRDTO(uccrDto);
		
		businessRoleDS.createABusinessRole(brDto);
		
		// Call webservice
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setActionCode("C");
		ContractRequest cr = new ContractRequest();
		ContractV2 contract = new ContractV2();
		ContractData contractData = new ContractData();
		contract.setContractNumber("999999999900");
		contract.setProductType("UC");
		contract.setContractStatus("C");
		contractData.setKey("CorporateEnvironmentID");
		contractData.setValue("testU");
		cr.setContract(contract);
		cr.getContractData().add(contractData);
		request.setContractRequest(cr);
		request.setGin("400424668522");
		Requestor requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSite("QVI");
		requestor.setSignature("RI");
		request.setRequestor(requestor);
		try {
			createOrUpdateRoleContractV1.createRoleContract(request);
			Assert.fail("On cree un numero de contrat deje existant");
		} catch (BusinessErrorBlocBusinessException mpe) {
			Assert.assertEquals(mpe.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_134);
			Assert.assertTrue(mpe.getFaultInfo().getBusinessError().getErrorDetail().toLowerCase().contains("already exists"));
		}
	}
	
	// Optin / Optout
	@Test
// 	@Transactional
	public void test_createOrUpdateRoleContract_HachikoOptinOptout() throws SystemException, JrafDomainException, BusinessErrorBlocBusinessException {

		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		request.setGin("400519037644");
		request.setActionCode("C");
		Requestor requestor = new Requestor();
		requestor.setSignature("HACHIKO");
		requestor.setChannel("MOB");
		requestor.setSite("TLS");
		requestor.setApplicationCode("ISI");
		ContractRequest contractRequest = new ContractRequest();
		ContractV2 contract = new ContractV2();
		
		String fbNumber = "90117" + RandomStringUtils.randomNumeric(5);
		
		contract.setContractNumber(fbNumber);
		contract.setContractType("C");
		contract.setProductType("FP");
		contract.setCompanyCode("AF");
		contract.setContractStatus("C");
		contractRequest.setContract(contract);
		request.setContractRequest(contractRequest);
		request.setRequestor(requestor);

		ContractData contractData1  = new ContractData();
		contractData1.setKey("TIERLEVEL");
		contractData1.setValue("A");

		ContractData contractData2  = new ContractData();
		contractData2.setKey("MEMBERTYPE");
		contractData2.setValue("");

		ContractData contractData3  = new ContractData();
		contractData3.setKey("MILESBALANCE");
		contractData3.setValue("0");

		ContractData contractData4  = new ContractData();
		contractData4.setKey("QUALIFYINGMILES");
		contractData4.setValue("0");

		ContractData contractData5  = new ContractData();
		contractData5.setKey("QUALIFYINGSEGMENTS");
		contractData5.setValue("0");

		request.getContractRequest().getContractData().add(contractData1);
		request.getContractRequest().getContractData().add(contractData2);
		request.getContractRequest().getContractData().add(contractData3);
		request.getContractRequest().getContractData().add(contractData4);
		request.getContractRequest().getContractData().add(contractData5);
		
		CreateUpdateRoleContractResponse response = createOrUpdateRoleContractV1.createRoleContract(request);
		
		Assert.assertNotNull(response);
		Assert.assertEquals("00" + fbNumber, response.getContractNumber());
		
//		AccountDataDTO accountData = accountDataDS.getByGin("400518869843");
//		Assert.assertNotNull(accountData);
//		Assert.assertTrue(accountData.getPasswordToChange() == 0);
	}

}


