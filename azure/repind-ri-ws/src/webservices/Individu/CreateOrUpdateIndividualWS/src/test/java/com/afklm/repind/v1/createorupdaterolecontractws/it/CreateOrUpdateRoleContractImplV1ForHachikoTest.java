package com.afklm.repind.v1.createorupdaterolecontractws.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v1.createorupdatecomprefbasedonpermws.it.helper.CreateOrUpdateComPrefHelper;
import com.afklm.repind.v1.createorupdaterolecontractws.CreateOrUpdateRoleContractImplV1;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001567.v1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001567.v1.data.CreateUpdateRoleContractRequest;
import com.afklm.soa.stubs.w001567.v1.data.CreateUpdateRoleContractResponse;
import com.afklm.soa.stubs.w001567.v1.request.ContractRequest;
import com.afklm.soa.stubs.w001567.v1.siccommontype.Requestor;
import com.afklm.soa.stubs.w001567.v1.sicindividutype.ContractData;
import com.afklm.soa.stubs.w001567.v1.sicindividutype.ContractV2;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.adresse.EmailRepository;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import org.junit.Assert;
import org.junit.Before;
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

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateRoleContractImplV1ForHachikoTest {
		
	private static String FBRecognitionActivated  = "FBRECOGNITION_ACTIVATED";
	
	@Autowired
	private CreateOrUpdateComPrefHelper createOrUpdateComPrefHelper;
	
	@Autowired
	protected RoleDS roleDS;

	@Autowired
	private AccountDataDS accountDataDS;

	@Autowired
	private EmailRepository emailRepository;
	
	@Autowired
	@Qualifier("passenger_CreateOrUpdateRoleContractService-v1Bean")
	@Spy
	private CreateOrUpdateRoleContractImplV1 createOrUpdateRoleContractV1;

	private final String EMAIL_ADDRESS = "testfp@ri.com";
	
	// Initialization : done before each test case annotated @Test
	@Before
	public void setUp() throws JrafDomainException, SOAPException {
		MockitoAnnotations.initMocks(this);
		
		// VariablesDS variablesDS = Mockito.mock(VariablesDS.class);
		// Mockito.when(variablesDS.getByEnvKey(FBRecognitionActivated)).thenReturn(new VariablesDTO(FBRecognitionActivated, "true")); // Mock

		Mockito.doNothing().when(createOrUpdateRoleContractV1).checkRightsOnContract(Mockito.any(String.class), Mockito.any(String.class));
	}
	
	/**
	 * Create a FlyingBlue Contract and then delete it
	 * 
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 * @throws JrafDomainException 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void CreateOrUpdateRoleContractImplV1ForHachikoTest() throws BusinessErrorBlocBusinessException, SystemException, JrafDomainException {
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
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getContractNumber());
		
		RoleContratsDTO rcdout = roleDS.findRoleContractByNumContract(response.getContractNumber());
		
		Assert.assertNotNull(rcdout);

		Assert.assertNull(rcdout.getTier());
	}

	/**
	 * Create a FlyingBlue Contract and then check account data compliancy
	 *
	 */
	@Test
	@Transactional
	@Rollback()
	public void CreateOrUpdateFP_CheckAccountDataTest() throws BusinessErrorBlocBusinessException, SystemException, JrafDomainException {
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);

		/*-- CREATE EMAIL FOR TEST --*/
		Email email = new Email();
		email.setSgin(gin);
		email.setEmail(EMAIL_ADDRESS);
		email.setCodeMedium("D");
		email.setStatutMedium("V");
		email.setSiteCreation("TEST");
		email.setSignatureCreation("TEST");
		email.setDateCreation(new Date());
		email.setAutorisationMailing("N");

		emailRepository.saveAndFlush(email);

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
		contractData.setValue("0");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("qualifyingMiles");
		contractData.setValue("0");
		contractDatas.add(contractData);
		contractData = new ContractData();
		contractData.setKey("qualifyingSegments");
		contractData.setValue("0");
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

		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getContractNumber());

		AccountDataDTO adDTO = accountDataDS.getByGin(gin);

		Assert.assertNotNull(adDTO);

		// Validate Email
		Assert.assertNotNull(adDTO.getEmailIdentifier());
		Assert.assertEquals(EMAIL_ADDRESS, adDTO.getEmailIdentifier());

		// Validate MyAccount
		Assert.assertNotNull(adDTO.getAccountIdentifier());
		RoleContratsDTO rcDTO = roleDS.findRoleContractByNumContract(adDTO.getAccountIdentifier());
		Assert.assertNotNull(rcDTO);

	}
}


