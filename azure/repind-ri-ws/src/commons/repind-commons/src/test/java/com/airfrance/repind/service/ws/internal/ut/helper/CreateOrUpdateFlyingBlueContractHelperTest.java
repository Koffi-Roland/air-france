package com.airfrance.repind.service.ws.internal.ut.helper;

import com.airfrance.ref.exception.ContractExistException;
import com.airfrance.ref.exception.ContractNotFoundException;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.AddressRoleCodeEnum;
import com.airfrance.ref.type.CommunicationPreferencesConstantValues;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.MarketLanguageRepository;
import com.airfrance.repind.dao.profil.ProfilsRepository;
import com.airfrance.repind.dao.reference.RefComPrefCountryMarketRepository;
import com.airfrance.repind.dao.reference.RefComPrefRepository;
import com.airfrance.repind.dto.individu.ContractDataDTO;
import com.airfrance.repind.dto.individu.ContractV2DTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.dto.ws.RequestorDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.ContractRequestDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.CreateUpdateRoleContractRequestDTO;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.profil.Profils;
import com.airfrance.repind.entity.reference.RefComPref;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.service.ws.internal.helpers.CreateOrUpdateFlyingBlueContractHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ContextConfiguration(locations="classpath:/config/application-context-spring-test.xml")
@Transactional(transactionManager="myTxManager2")
public class CreateOrUpdateFlyingBlueContractHelperTest extends CreateOrUpdateFlyingBlueContractHelper{

	private PostalAddressRepository postalAddressRepository;
	private RefComPrefCountryMarketRepository refComPrefCountryMarketRepository;
	private RefComPrefRepository refComPrefRepository;
	private MarketLanguageRepository marketLanguageRepository;
	private CommunicationPreferencesRepository communicationPreferencesRepository;
	private RoleDS roleDS;
	private ProfilsRepository profilsRepository;

	private BusinessRoleDS businessRoleDS;
	private IndividuDS individuDS;
	private CommunicationPreferencesDS communicationPreferencesDS;

	CreateOrUpdateFlyingBlueContractHelper createOrUpdateFB;
	

	@Before
	public void setUp() throws Exception {
		createOrUpdateFB = new CreateOrUpdateFlyingBlueContractHelper();
		postalAddressRepository = Mockito.mock(PostalAddressRepository.class);
		refComPrefCountryMarketRepository = Mockito.mock(RefComPrefCountryMarketRepository.class);
		refComPrefRepository = Mockito.mock(RefComPrefRepository.class);
		marketLanguageRepository = Mockito.mock(MarketLanguageRepository.class);
		individuDS=Mockito.mock(IndividuDS.class);
		communicationPreferencesDS = Mockito.mock(CommunicationPreferencesDS.class);
		communicationPreferencesRepository = Mockito.mock(CommunicationPreferencesRepository.class);
		roleDS = Mockito.mock(RoleDS.class);
		businessRoleDS = Mockito.mock(BusinessRoleDS.class);
		profilsRepository = Mockito.mock(ProfilsRepository.class);
		createOrUpdateFB.setPostalAddressRepository(postalAddressRepository);
		createOrUpdateFB.setRefComPrefRepository(refComPrefRepository);
		createOrUpdateFB.setCommunicationPreferencesRepository(communicationPreferencesRepository);
		createOrUpdateFB.setRefComPrefCountryMarketRepository(refComPrefCountryMarketRepository);
		createOrUpdateFB.setMarketLanguageRepository(marketLanguageRepository);
		createOrUpdateFB.setRoleDS(roleDS);
		createOrUpdateFB.setProfilsRepository(profilsRepository);
		createOrUpdateFB.setBusinessRoleDS(businessRoleDS);
		createOrUpdateFB.setIndividuDS(individuDS);
		createOrUpdateFB.setCommunicationPreferencesDS(communicationPreferencesDS);

	}

	@Test
	public void testCreateOrUpdateRoleFP_ExistingContract() throws JrafDomainException {
		CreateUpdateRoleContractRequestDTO request = mockRequest();
		List<String> listeCodePays = new ArrayList<>();
		listeCodePays.add("FR");
		Mockito.when(postalAddressRepository.findISIPostalAddressCodePays("400297753692", AddressRoleCodeEnum.PRINCIPAL.toString(), "ISI"))
		.thenReturn(listeCodePays);
		RoleContratsDTO roleContract = new RoleContratsDTO();
		Mockito.when(roleDS.findRoleContratsFP("00005C124515")).thenReturn(roleContract);

		try {
			createOrUpdateFB.createRoleFP(request);
			Assert.fail();
		} catch (Exception mpe) {
			Assert.assertTrue(mpe instanceof ContractExistException);
		}
	}

	@Test
	public void testCreateOrUpdateRoleFP_RuleNAT_FieldN() throws JrafDomainException {
		Profils profils = mockProfilsMailing("N");
		RefComPref refCompRef = mockRefComPref("Y","N","N");
		Assert.assertEquals("Y", this.ruleNAT(refCompRef, profils));
	}

	@Test
	public void testCreateOrUpdateRoleFP_RuleNAT_FieldT() throws JrafDomainException {
		Profils profils = mockProfilsMailing("T");
		RefComPref refCompRef = mockRefComPref("N","N","Y");
		Assert.assertEquals("Y", this.ruleNAT(refCompRef, profils));
	}

	@Test
	public void testCreateOrUpdateRoleFP_RuleNAT_emptyMailing() throws JrafDomainException {
		Profils profils = mockProfilsMailing("");
		RefComPref refCompRef = mockRefComPref("N","N","Y");
		Assert.assertNull(this.ruleNAT(refCompRef, profils));
	}

	@Test
	public void testCreateOrUpdateRoleFP_RuleNAT_NullMailing() throws JrafDomainException {
		Profils profils = mockProfilsMailing(null);
		RefComPref refCompRef = mockRefComPref("N","N","Y");
		Assert.assertNull(this.ruleNAT(refCompRef, profils));
	}

	@Test
	public void testCreateOrUpdateRoleFP_RuleNAT_FieldA() throws JrafDomainException {
		Profils profils = mockProfilsMailing("A");
		RefComPref refCompRef = mockRefComPref("N","Y","N");
		Assert.assertEquals("Y", this.ruleNAT(refCompRef, profils));
	}

	@Test
	public void testCreateOrUpdateRoleFP_RuleLanguageCodeProfil() throws JrafDomainException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Profils profils = mockProfilsCodeLanguage("FR");
		RefComPref refCompRef = new RefComPref();
		refCompRef.setDefaultLanguage1("EN");
		refCompRef.setDefaultLanguage2("FR");
		refCompRef.setDefaultLanguage3("AU");
				
    	CommunicationPreferencesDS communicationPreferencesDS = new CommunicationPreferencesDS();
    	Method method = CommunicationPreferencesDS.class.getDeclaredMethod("ruleLanguage", RefComPref.class, Profils.class);
    	method.setAccessible(true);
    	Object language = method.invoke(communicationPreferencesDS, refCompRef, profils);

		Assert.assertEquals("FR", language.toString());
	}


	@Test
	public void testCreateOrUpdateRoleFP_RuleLanguageDefault() throws JrafDomainException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Profils profils = mockProfilsCodeLanguage("");
		RefComPref refCompRef = new RefComPref();
		refCompRef.setDefaultLanguage1("EN");
		refCompRef.setDefaultLanguage2("FR");
		refCompRef.setDefaultLanguage3("AU");
		
    	CommunicationPreferencesDS communicationPreferencesDS = new CommunicationPreferencesDS();
    	Method method = CommunicationPreferencesDS.class.getDeclaredMethod("ruleLanguage", RefComPref.class, Profils.class);
    	method.setAccessible(true);
    	Object language = method.invoke(communicationPreferencesDS, refCompRef, profils);

		Assert.assertEquals("EN", language.toString());
	}


	@Test
	public void testCreateOrUpdateRoleFP_RuleLanguageCodePaysNull() throws JrafDomainException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		Profils profils = mockProfilsCodeLanguage(null);
		RefComPref refCompRef = new RefComPref();
		refCompRef.setDefaultLanguage1("EN");
		refCompRef.setDefaultLanguage2("FR");
		refCompRef.setDefaultLanguage3("AU");
		
    	CommunicationPreferencesDS communicationPreferencesDS = new CommunicationPreferencesDS();
    	Method method = CommunicationPreferencesDS.class.getDeclaredMethod("ruleLanguage", RefComPref.class, Profils.class);
    	method.setAccessible(true);
    	Object language = method.invoke(communicationPreferencesDS, refCompRef, profils);

		Assert.assertEquals("EN", language.toString());
	}

	@Test
	public void testCreateOrUpdateRoleFP_RuleLanguageNull() throws JrafDomainException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Profils profils = mockProfilsCodeLanguage(null);
		RefComPref refCompRef = new RefComPref();
		
    	CommunicationPreferencesDS communicationPreferencesDS = new CommunicationPreferencesDS();
    	Method method = CommunicationPreferencesDS.class.getDeclaredMethod("ruleLanguage", RefComPref.class, Profils.class);
    	method.setAccessible(true);
    	Object language = method.invoke(communicationPreferencesDS, refCompRef, profils);
    	
		Assert.assertNull(language);
	}


	@Test
	public void testCreateOrUpdateRoleFP_deleteRoleContractCheckInput() throws JrafDomainException {
		CreateUpdateRoleContractRequestDTO request = mockRequestLight();
		try {
			this.deleteRoleFP(request);
			Assert.fail();
		} catch (Exception mpe) {
			Assert.assertTrue(mpe instanceof MissingParameterException);
			Assert.assertTrue(mpe.getMessage().contains("contractNumber is Mandatory"));
		}
	}

	@Test
	public void testCreateOrUpdateRoleFP_deleteNotFoundRoleContract() throws JrafDomainException {
		CreateUpdateRoleContractRequestDTO request = mockRequest();
		Mockito.when(roleDS.findRoleContratsFP("5C124515")).thenReturn(null);

		try {
			createOrUpdateFB.deleteRoleFP(request);
			Assert.fail();
		} catch (Exception mpe) {
			Assert.assertTrue(mpe instanceof ContractNotFoundException);
		}
	}


	@Test
	public void testCreateOrUpdateRoleFP_deleteRoleContractGinNotFound() throws JrafDomainException {
		CreateUpdateRoleContractRequestDTO request = mockRequest();
		RoleContratsDTO roleContract = new RoleContratsDTO();
		roleContract.setCleRole(1);
		Mockito.when(roleDS.findRoleContratsFP("5C124515")).thenReturn(roleContract);
		Mockito.when(businessRoleDS.getBusinessRoleByCleRole(roleContract.getCleRole())).thenReturn(new BusinessRoleDTO());

		try {
			createOrUpdateFB.deleteRoleFP(request);
			Assert.fail();
		} catch (Exception mpe) {
			//			Assert.assertTrue(mpe instanceof NotFoundException);
		}
	}



	@Test
	public void testCreateOrUpdateRoleFP_checkMandatoryFBdata_missingTierLevel() throws JrafDomainException {
		List<ContractDataDTO> listeContractData = mockContractData_Case1();
		try {
			this.checkMandatoryFlyingBlueData(listeContractData );
		} catch (Exception mpe) {
			Assert.assertTrue(mpe instanceof MissingParameterException);
			Assert.assertTrue(mpe.getMessage().contains("TierLevel is Mandatory"));
		}
	}

	@Test
	public void testCreateOrUpdateRoleFP_checkMandatoryFBdata_missingMemberType() throws JrafDomainException {
		List<ContractDataDTO> listeContractData = mockContractData_Case2();
		try {
			this.checkMandatoryFlyingBlueData(listeContractData );
		} catch (Exception mpe) {
			Assert.assertTrue(mpe instanceof MissingParameterException);
			Assert.assertTrue(mpe.getMessage().contains("MemberType is Mandatory"));
		}
	}


	@Test
	public void testCreateOrUpdateRoleFP_checkMandatoryFBdata_missingMilesBalance() throws JrafDomainException {
		List<ContractDataDTO> listeContractData = mockContractData_Case3();
		try {
			this.checkMandatoryFlyingBlueData(listeContractData );
		} catch (Exception mpe) {
			Assert.assertTrue(mpe instanceof MissingParameterException);
			Assert.assertTrue(mpe.getMessage().contains("MilesBalance is Mandatory"));
		}
	}


	@Test
	public void testCreateOrUpdateRoleFP_checkMandatoryFBdata_missingQualifyingMiles() throws JrafDomainException {
		List<ContractDataDTO> listeContractData = mockContractData_Case4();
		try {
			this.checkMandatoryFlyingBlueData(listeContractData );
		} catch (Exception mpe) {
			Assert.assertTrue(mpe instanceof MissingParameterException);
			Assert.assertTrue(mpe.getMessage().contains("QualifyingMiles is Mandatory"));
		}
	}


	@Test
	public void testCreateOrUpdateRoleFP_checkMandatoryFBdata_missingQualifyingSegments() throws JrafDomainException {
		List<ContractDataDTO> listeContractData = mockContractData_Case5();
		try {
			this.checkMandatoryFlyingBlueData(listeContractData );
		} catch (Exception mpe) {
			Assert.assertTrue(mpe instanceof MissingParameterException);
			Assert.assertTrue(mpe.getMessage().contains("QualifyingSegments is Mandatory"));
		}
	}

	@Test
	public void testCreateOrUpdateRoleFP_checkMandatoryFBdata_invalidMilesBalance() throws JrafDomainException {
		List<ContractDataDTO> listeContractData = mockContractData_Case6();
		try {
			this.checkMandatoryFlyingBlueData(listeContractData );
		} catch (Exception mpe) {
			Assert.assertTrue(mpe instanceof InvalidParameterException);
			Assert.assertTrue(mpe.getMessage().contains("MilesBalance should be a number"));
		}
	}

	@Test
	public void testCreateOrUpdateRoleFP_checkMandatoryFBdata_invalidQualifyingSegments() throws JrafDomainException {
		List<ContractDataDTO> listeContractData = mockContractData_Case7();
		try {
			this.checkMandatoryFlyingBlueData(listeContractData );
		} catch (Exception mpe) {
			Assert.assertTrue(mpe instanceof InvalidParameterException);
			Assert.assertTrue(mpe.getMessage().contains("QualifyingMiles should be a number"));
		}
	}

	@Test
	public void testCreateOrUpdateRoleFP_checkMandatoryFBdata_invalidQualifyingMiles() throws JrafDomainException {
		List<ContractDataDTO> listeContractData = mockContractData_Case8();
		try {
			this.checkMandatoryFlyingBlueData(listeContractData );
		} catch (Exception mpe) {
			Assert.assertTrue(mpe instanceof InvalidParameterException);
			Assert.assertTrue(mpe.getMessage().contains("QualifyingSegments should be a number"));
		}
	}

	@Test
	public void testCreateTelemarketingCommunicationPreferencesShouldSuccess() throws JrafDaoException {
		String gin = "400297753692";
		Date dateCreation = new Date();
		RequestorDTO requestor = new RequestorDTO();
		requestor.setSignature("B2C");
		requestor.setSite("KL");
		requestor.setApplicationCode("KLM");
		
		Mockito.when(communicationPreferencesRepository.findComPrefId("400297753692", CommunicationPreferencesConstantValues.DOMAIN_P, CommunicationPreferencesConstantValues.GROUP_TYPE_T, CommunicationPreferencesConstantValues.COM_TYPE_TEL)).thenReturn(null);
		
		CommunicationPreferences result = createOrUpdateFB.createTelemarketingCommunicationPreferences(gin, dateCreation, requestor);
		
		Assert.assertNotNull(result);
		Assert.assertEquals(CommunicationPreferencesConstantValues.DOMAIN_P, result.getDomain());
		Assert.assertEquals(CommunicationPreferencesConstantValues.COM_TYPE_TEL, result.getComType());
		Assert.assertEquals(CommunicationPreferencesConstantValues.GROUP_TYPE_T, result.getComGroupType());
		Assert.assertEquals(gin, result.getGin());
		Assert.assertEquals(dateCreation, result.getModificationDate());
		Assert.assertEquals(dateCreation, result.getCreationDate());
		Assert.assertEquals("N", result.getSubscribe());
	}

	@Test
	public void testCreateTelemarketingCommunicationPreferencesShouldFailIfExists() throws JrafDaoException {
		String gin = "400297753692";
		Date dateCreation = new Date();
		RequestorDTO requestor = new RequestorDTO();
		requestor.setSignature("B2C");
		requestor.setSite("KL");
		requestor.setApplicationCode("KLM");
		
		Mockito.when(communicationPreferencesRepository.findComPrefId(gin, CommunicationPreferencesConstantValues.DOMAIN_P, CommunicationPreferencesConstantValues.GROUP_TYPE_T, CommunicationPreferencesConstantValues.COM_TYPE_TEL)).thenReturn(new CommunicationPreferences());
		
		CommunicationPreferences result = createOrUpdateFB.createTelemarketingCommunicationPreferences(gin, dateCreation, requestor);
		
		Assert.assertNull(result);

	}

	
	private List<ContractDataDTO> mockContractData_Case1() {
		List<ContractDataDTO> result = new ArrayList<>();
		ContractDataDTO contractData2  = new ContractDataDTO();
		contractData2.setKey("MEMBERTYPE");
		contractData2.setValue("T");

		ContractDataDTO contractData3  = new ContractDataDTO();
		contractData3.setKey("MILESBALANCE");
		contractData3.setValue("24275");

		ContractDataDTO contractData4  = new ContractDataDTO();
		contractData4.setKey("QUALIFYINGMILES");
		contractData4.setValue("24275");

		ContractDataDTO contractData5  = new ContractDataDTO();
		contractData5.setKey("QUALIFYINGSEGMENTS");
		contractData5.setValue("2425");

		result.add(contractData2);
		result.add(contractData3);
		result.add(contractData4);
		result.add(contractData5);

		return result;
	}


	private List<ContractDataDTO> mockContractData_Case2() {
		List<ContractDataDTO> result = new ArrayList<>();
		ContractDataDTO contractData1  = new ContractDataDTO();
		contractData1.setKey("TIERLEVEL");
		contractData1.setValue("A");

		ContractDataDTO contractData3  = new ContractDataDTO();
		contractData3.setKey("MILESBALANCE");
		contractData3.setValue("24275");

		ContractDataDTO contractData4  = new ContractDataDTO();
		contractData4.setKey("QUALIFYINGMILES");
		contractData4.setValue("24275");

		ContractDataDTO contractData5  = new ContractDataDTO();
		contractData5.setKey("QUALIFYINGSEGMENTS");
		contractData5.setValue("2425");

		result.add(contractData1);
		result.add(contractData3);
		result.add(contractData4);
		result.add(contractData5);

		return result;
	}

	private List<ContractDataDTO> mockContractData_Case3() {
		List<ContractDataDTO> result = new ArrayList<>();
		ContractDataDTO contractData1  = new ContractDataDTO();
		contractData1.setKey("TIERLEVEL");
		contractData1.setValue("A");

		ContractDataDTO contractData2  = new ContractDataDTO();
		contractData2.setKey("MEMBERTYPE");
		contractData2.setValue("T");

		ContractDataDTO contractData4  = new ContractDataDTO();
		contractData4.setKey("QUALIFYINGMILES");
		contractData4.setValue("24275");

		ContractDataDTO contractData5  = new ContractDataDTO();
		contractData5.setKey("QUALIFYINGSEGMENTS");
		contractData5.setValue("2425");

		result.add(contractData1);
		result.add(contractData2);
		result.add(contractData4);
		result.add(contractData5);

		return result;
	}

	private List<ContractDataDTO> mockContractData_Case4() {
		List<ContractDataDTO> result = new ArrayList<>();
		ContractDataDTO contractData1  = new ContractDataDTO();
		contractData1.setKey("TIERLEVEL");
		contractData1.setValue("A");

		ContractDataDTO contractData2  = new ContractDataDTO();
		contractData2.setKey("MEMBERTYPE");
		contractData2.setValue("T");

		ContractDataDTO contractData3  = new ContractDataDTO();
		contractData3.setKey("MILESBALANCE");
		contractData3.setValue("24275");

		ContractDataDTO contractData5  = new ContractDataDTO();
		contractData5.setKey("QUALIFYINGSEGMENTS");
		contractData5.setValue("2425");

		result.add(contractData1);
		result.add(contractData2);
		result.add(contractData3);
		result.add(contractData5);

		return result;
	}

	private List<ContractDataDTO> mockContractData_Case5() {
		List<ContractDataDTO> result = new ArrayList<>();
		ContractDataDTO contractData1  = new ContractDataDTO();
		contractData1.setKey("TIERLEVEL");
		contractData1.setValue("A");

		ContractDataDTO contractData2  = new ContractDataDTO();
		contractData2.setKey("MEMBERTYPE");
		contractData2.setValue("T");

		ContractDataDTO contractData3  = new ContractDataDTO();
		contractData3.setKey("MILESBALANCE");
		contractData3.setValue("24275");

		ContractDataDTO contractData4  = new ContractDataDTO();
		contractData4.setKey("QUALIFYINGMILES");
		contractData4.setValue("24275");

		result.add(contractData1);
		result.add(contractData2);
		result.add(contractData3);
		result.add(contractData4);

		return result;
	}

	private List<ContractDataDTO> mockContractData_Case6() {
		List<ContractDataDTO> result = new ArrayList<>();
		ContractDataDTO contractData1  = new ContractDataDTO();
		contractData1.setKey("TIERLEVEL");
		contractData1.setValue("A");

		ContractDataDTO contractData2  = new ContractDataDTO();
		contractData2.setKey("MEMBERTYPE");
		contractData2.setValue("T");

		ContractDataDTO contractData3  = new ContractDataDTO();
		contractData3.setKey("MILESBALANCE");
		contractData3.setValue("242A5");

		ContractDataDTO contractData4  = new ContractDataDTO();
		contractData4.setKey("QUALIFYINGMILES");
		contractData4.setValue("24275");

		ContractDataDTO contractData5  = new ContractDataDTO();
		contractData5.setKey("QUALIFYINGSEGMENTS");
		contractData5.setValue("2425");

		result.add(contractData1);
		result.add(contractData2);
		result.add(contractData3);
		result.add(contractData4);
		result.add(contractData5);

		return result;
	}

	private List<ContractDataDTO> mockContractData_Case7() {
		List<ContractDataDTO> result = new ArrayList<>();
		ContractDataDTO contractData1  = new ContractDataDTO();
		contractData1.setKey("TIERLEVEL");
		contractData1.setValue("A");

		ContractDataDTO contractData2  = new ContractDataDTO();
		contractData2.setKey("MEMBERTYPE");
		contractData2.setValue("T");

		ContractDataDTO contractData3  = new ContractDataDTO();
		contractData3.setKey("MILESBALANCE");
		contractData3.setValue("2425");

		ContractDataDTO contractData4  = new ContractDataDTO();
		contractData4.setKey("QUALIFYINGMILES");
		contractData4.setValue("242A75");

		ContractDataDTO contractData5  = new ContractDataDTO();
		contractData5.setKey("QUALIFYINGSEGMENTS");
		contractData5.setValue("2425");

		result.add(contractData1);
		result.add(contractData2);
		result.add(contractData3);
		result.add(contractData4);
		result.add(contractData5);

		return result;
	}

	private List<ContractDataDTO> mockContractData_Case8() {
		List<ContractDataDTO> result = new ArrayList<>();
		ContractDataDTO contractData1  = new ContractDataDTO();
		contractData1.setKey("TIERLEVEL");
		contractData1.setValue("A");

		ContractDataDTO contractData2  = new ContractDataDTO();
		contractData2.setKey("MEMBERTYPE");
		contractData2.setValue("T");

		ContractDataDTO contractData3  = new ContractDataDTO();
		contractData3.setKey("MILESBALANCE");
		contractData3.setValue("2425");

		ContractDataDTO contractData4  = new ContractDataDTO();
		contractData4.setKey("QUALIFYINGMILES");
		contractData4.setValue("24275");

		ContractDataDTO contractData5  = new ContractDataDTO();
		contractData5.setKey("QUALIFYINGSEGMENTS");
		contractData5.setValue("242A5");

		result.add(contractData1);
		result.add(contractData2);
		result.add(contractData3);
		result.add(contractData4);
		result.add(contractData5);

		return result;
	}

	private CreateUpdateRoleContractRequestDTO mockRequestLight() {
		CreateUpdateRoleContractRequestDTO request = new CreateUpdateRoleContractRequestDTO();
		ContractRequestDTO contractRequest = new ContractRequestDTO();
		ContractV2DTO contract = new ContractV2DTO();
		contractRequest.setContract(contract);
		request.setContractRequest(contractRequest);
		return request;
	}

	
	private CreateUpdateRoleContractRequestDTO mockRequestFPAndMyAccount() {
		CreateUpdateRoleContractRequestDTO request = new CreateUpdateRoleContractRequestDTO();
		request.setGin("000000000500");
		RequestorDTO requestor = new RequestorDTO();
		requestor.setSignature("B2C");
		requestor.setSite("KL");
		requestor.setApplicationCode("KLM");
		ContractRequestDTO contractRequest = new ContractRequestDTO();
		ContractV2DTO contract = new ContractV2DTO();
		contract.setContractNumber("001024800150");
		contract.setContractType("C");
		contract.setProductType("FP");
		contract.setContractStatus("C");
		contractRequest.setContract(contract);
		request.setContractRequest(contractRequest);
		request.setRequestor(requestor);

		ContractDataDTO contractData1  = new ContractDataDTO();
		contractData1.setKey("TIERLEVEL");
		contractData1.setValue("A");

		ContractDataDTO contractData2  = new ContractDataDTO();
		contractData2.setKey("MEMBERTYPE");
		contractData2.setValue("T");

		ContractDataDTO contractData3  = new ContractDataDTO();
		contractData3.setKey("MILESBALANCE");
		contractData3.setValue("24275");

		ContractDataDTO contractData4  = new ContractDataDTO();
		contractData4.setKey("QUALIFYINGMILES");
		contractData4.setValue("24275");

		ContractDataDTO contractData5  = new ContractDataDTO();
		contractData5.setKey("QUALIFYINGSEGMENTS");
		contractData5.setValue("2425");

		request.getContractRequest().getContractData().add(contractData1);
		request.getContractRequest().getContractData().add(contractData2);
		request.getContractRequest().getContractData().add(contractData3);
		request.getContractRequest().getContractData().add(contractData4);
		request.getContractRequest().getContractData().add(contractData5);

		return request;
	}

	private CreateUpdateRoleContractRequestDTO mockRequest() {
		CreateUpdateRoleContractRequestDTO request = new CreateUpdateRoleContractRequestDTO();
		request.setGin("400297753692");
		RequestorDTO requestor = new RequestorDTO();
		requestor.setSignature("B2C");
		requestor.setSite("KL");
		requestor.setApplicationCode("KLM");
		ContractRequestDTO contractRequest = new ContractRequestDTO();
		ContractV2DTO contract = new ContractV2DTO();
		contract.setContractNumber("5C124515");
		contract.setContractType("C");
		contract.setProductType("FP");
		contract.setContractStatus("C");
		contractRequest.setContract(contract);
		request.setContractRequest(contractRequest);
		request.setRequestor(requestor);

		ContractDataDTO contractData1  = new ContractDataDTO();
		contractData1.setKey("TIERLEVEL");
		contractData1.setValue("A");

		ContractDataDTO contractData2  = new ContractDataDTO();
		contractData2.setKey("MEMBERTYPE");
		contractData2.setValue("T");

		ContractDataDTO contractData3  = new ContractDataDTO();
		contractData3.setKey("MILESBALANCE");
		contractData3.setValue("24275");

		ContractDataDTO contractData4  = new ContractDataDTO();
		contractData4.setKey("QUALIFYINGMILES");
		contractData4.setValue("24275");

		ContractDataDTO contractData5  = new ContractDataDTO();
		contractData5.setKey("QUALIFYINGSEGMENTS");
		contractData5.setValue("2425");

		request.getContractRequest().getContractData().add(contractData1);
		request.getContractRequest().getContractData().add(contractData2);
		request.getContractRequest().getContractData().add(contractData3);
		request.getContractRequest().getContractData().add(contractData4);
		request.getContractRequest().getContractData().add(contractData5);

		return request;
	}

	private RefComPref mockRefComPref(String fieldN, String fieldA, String fieldT) {
		RefComPref refComPref = new RefComPref();
		refComPref.setFieldA(fieldA);
		refComPref.setFieldN(fieldN);
		refComPref.setFieldT(fieldT);
		return refComPref;
	}

	private Profils mockProfilsMailing(String mailing) {
		Profils profils = new Profils();
		profils.setSmailing_autorise(mailing);
		return profils;
	}

	private Profils mockProfilsCodeLanguage(String pCodeLanguage) {
		Profils profils = new Profils();
		profils.setScode_langue(pCodeLanguage);
		return profils;
	}

}
