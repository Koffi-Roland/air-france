package com.afklm.repind.v1.createorupdatecomprefbasedonpermws.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v1.createorupdatecomprefbasedonpermws.CreateOrUpdateComPrefBasedOnPermissionImplV1;
import com.afklm.repind.v1.createorupdatecomprefbasedonpermws.it.helper.CreateOrUpdateComPrefHelper;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001950.v1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001950.v1.ns1.Information;
import com.afklm.soa.stubs.w001950.v1.ns3.Permission;
import com.afklm.soa.stubs.w001950.v1.ns4.PermissionRequest;
import com.afklm.soa.stubs.w001950.v1.ns6.CreateOrUpdateComPrefBasedOnPermissionRequest;
import com.afklm.soa.stubs.w001950.v1.ns6.CreateOrUpdateComPrefBasedOnPermissionResponse;
import com.afklm.soa.stubs.w001950.v1.ns7.LanguageCodesEnum;
import com.afklm.soa.stubs.w001950.v1.ns9.Requestor;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dto.reference.RefComPrefDgtDTO;
import com.airfrance.repind.dto.reference.RefPermissionsDTO;
import com.airfrance.repind.dto.reference.RefPermissionsIdDTO;
import com.airfrance.repind.dto.reference.RefPermissionsQuestionDTO;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.MarketLanguage;
import com.airfrance.repind.service.reference.internal.RefComPrefDgtDS;
import com.airfrance.repind.service.reference.internal.RefPermissionsDS;
import com.airfrance.repind.service.reference.internal.RefPermissionsQuestionDS;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateComPrefBasedOnPermissionImplV1Test {

	@Autowired
	private CreateOrUpdateComPrefBasedOnPermissionImplV1 createOrUpdateComPrefBasedOnPermissionImplV1;

	@Autowired
	private CreateOrUpdateComPrefHelper createOrUpdateComPrefHelper;

	@Autowired
	private RefPermissionsQuestionDS refPermissionsQuestionDS;

	@Autowired
	private RefComPrefDgtDS refComPrefDgtDS;

	@Autowired
	private RefPermissionsDS refPermissionsDS;

	@Autowired
	private IndividuRepository individuRepository;

	private RefPermissionsQuestionDTO refPermissionsQuestion;
	private Requestor requestor;

	@Before
	@Transactional
	@Rollback(true)
	public void beforeTest() throws InvalidParameterException {
		requestor = new Requestor();
		requestor.setChannel("B2C");
		requestor.setSignature("TESTRI");
		requestor.setSite("QVI");

		refPermissionsQuestion = new RefPermissionsQuestionDTO();
		refPermissionsQuestion.setDateCreation(new Date());
		refPermissionsQuestion.setName("FOR IT TEST");
		refPermissionsQuestion.setQuestion("Blabla en FR");
		refPermissionsQuestion.setQuestionEN("Blabla en En");
		refPermissionsQuestion.setSignatureCreation("TESTMZ_C");
		refPermissionsQuestion.setSiteCreation("QVI_C");
		refPermissionsQuestionDS.create(refPermissionsQuestion);
	}

	@Test
	public void testInvalidPermissionId() {
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin("110000012801");
		request.setRequestor(requestor);

		Permission permission = new Permission();
		permission.setPermissionID("999");
		permission.setPermissionAnswer(true);

		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);

		request.setPermissionRequest(permissionRequest);

		try {
			createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		} catch (BusinessErrorBlocBusinessException | SystemException e) {
			Assert.assertEquals("PERMISSION ID UNKNOWN", e.getMessage());
		}
	}
	
	/**
	 * Create a Permission a Domain not supported
	 *  -> One Compref : P S RECO
	 *  
	 * @throws JrafDomainException 
	 * @throws SystemException 
	 * @throws BusinessErrorBlocBusinessException 
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionDomainInvalid() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException{
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("T", "N", "KQ");

		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);

		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);

		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);

		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);

		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);

		request.setPermissionRequest(permissionRequest);

		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);

		Assert.assertEquals(false, response.isSuccess());
		Assert.assertEquals(true, response.getInformationResponse().getInformations()
				.get(0).getInformation().get(0).getDetails().equals("This domain is not supported yet"));

		Individu individu = individuRepository.findBySgin(gin);

		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());

		Assert.assertEquals(0, listComPref.size());
	}

	/**
	 * Create a Permission without Market Language in input
	 *  -> One Compref : P S RECO
	 *  
	 * @throws JrafDomainException 
	 * @throws SystemException 
	 * @throws BusinessErrorBlocBusinessException 
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionPrivacyWithoutML() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException{
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("P", "S", "RECO");

		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);

		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);

		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);

		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);

		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);

		request.setPermissionRequest(permissionRequest);

		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);

		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals(true, response.getInformationResponse().getInformations()
				.get(0).getInformation().get(0).getDetails().equals("Communication preference created"));

		Individu individu = individuRepository.findBySgin(gin);

		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());

		Assert.assertEquals(1, listComPref.size());
		Assert.assertTrue(listComPref.get(0).getMarketLanguage().isEmpty());
	}


	/**
	 * Update a Permission
	 *  -> One Compref : P S RECO
	 *  
	 * @throws JrafDomainException 
	 * @throws SystemException 
	 * @throws BusinessErrorBlocBusinessException 
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdatePermissionPrivacyWithoutML() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException{

		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("P", "S", "RECO");

		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);

		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);

		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);

		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);

		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);

		request.setPermissionRequest(permissionRequest);

		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);

		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals(true, response.getInformationResponse().getInformations()
				.get(0).getInformation().get(0).getDetails().equals("Communication preference created"));

		Individu individu = individuRepository.findBySgin(gin);

		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());

		Assert.assertEquals(1, listComPref.size());
		Assert.assertTrue(listComPref.get(0).getMarketLanguage().isEmpty());

		/**
		 * UPDATE PART
		 * 
		 */
		request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);

		permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
	
		permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);

		request.setPermissionRequest(permissionRequest);

		response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);

		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals(true, response.getInformationResponse().getInformations()
				.get(0).getInformation().get(0).getDetails().equals("Communication preference updated"));

		individu = individuRepository.findBySgin(gin);

		listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());

		Assert.assertEquals(1, listComPref.size());
		Assert.assertTrue(listComPref.get(0).getMarketLanguage().isEmpty());
	}

	/**
	 * Create a Permission with Market FR Language FR in input
	 *  -> One Compref : F N AF
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionSalesWithML_ISI_OK() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "T", "AF");

		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);

		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);

		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);

		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("FR");
		permission.setLanguage(LanguageCodesEnum.FR);

		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);

		request.setPermissionRequest(permissionRequest);

		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);

		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - T - AF", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);

		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());

		List<MarketLanguage> listMarketLanguage = new ArrayList<MarketLanguage>();
		listMarketLanguage.addAll(listComPref.get(0).getMarketLanguage());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getMarket());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
		Assert.assertEquals("Y", listMarketLanguage.get(0).getOptIn());
	}

	
	/**
	 * Create a Permission with Market FR Language FR in input 
	 *  -> One Compref : S T AF
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionSalesWithML_OK() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "T", "AF");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(false, "FR", false, "V", "FR", false);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("FR");
		permission.setLanguage(LanguageCodesEnum.FR);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());
		
		List<MarketLanguage> listMarketLanguage = new ArrayList<MarketLanguage>();
		listMarketLanguage.addAll(listComPref.get(0).getMarketLanguage());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getMarket());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
		Assert.assertEquals("Y", listMarketLanguage.get(0).getOptIn());
	}
	
	/**
	 * Create a Permission 
	 *  -> One Compref : S T AF
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionSalesWithoutML_ISI_OK () throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "T", "AF");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - T - AF", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());
	}
	
	/**
	 * Create a Permission without any address postal
	 *  -> One Compref : S T AF
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionSalesWithoutML_ISI_KO() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "N", "AF");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", false, "V", "FR", false);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(false, response.isSuccess());
		Assert.assertEquals("No ISIS or valid mailing address available", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(0, listComPref.size());
	}
	
	/**
	 * Create a Permission without any profil
	 *  -> One Compref : S T AF
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionSalesWithoutML_PROFIL_KO() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "N", "AF");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(false, "FR", true, "V", "FR", true);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - N - AF", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());
	}
	
	/**
	 * Create a Permission with Market XX Language XX in input 
	 *  -> One Compref : S T AF
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionSalesWithoutML_MARKET_KO() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "N", "AF");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "GG", true);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(false, response.isSuccess());
		Assert.assertEquals("No Market is associated to country: GG", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(0, listComPref.size());
	}
	
	/**
	 * Create a Permission with Market FR in input
	 *  -> One Compref : S T AF
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionSalesWithMarketOnly() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "N", "AF");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", false, "V", "FR", false);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("FR");
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(false, response.isSuccess());
		Assert.assertEquals("Cannot create communication preference. Language is missing in input", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(0, listComPref.size());
	}
	
	/**
	 * Create a Permission with Language FR in input
	 *  -> One Compref : S T AF
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionSalesWithLanguageOnly() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "T", "AF");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", false, "V", "FR", false);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setLanguage(LanguageCodesEnum.FR);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(false, response.isSuccess());
		Assert.assertEquals("Cannot create communication preference. Market is missing in input", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(0, listComPref.size());
	}
	
	/**
	 * Create a Permission which is not referenced for the Market ZM
	 *  -> One Compref : S T AF
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Ignore
	@Transactional
	@Rollback(true)
	public void testCreatePermissionSalesUnknow() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "T", "AF");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "ZM", true);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(false, response.isSuccess());
		Assert.assertEquals("Communication preference S T AF not available for market ZM", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(0, listComPref.size());
	}
	
	/**
	 * Update a Permission with Market FR Language FR in input + Profil + ISI V
	 *  -> One Compref : S T AF Optin Y
	 *  -> One Compref : S T AF Optin N
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdatePermissionSales() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "T", "AF");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("FR");
		permission.setLanguage(LanguageCodesEnum.FR);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - T - AF", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());
		
		List<MarketLanguage> listMarketLanguage = new ArrayList<MarketLanguage>();
		listMarketLanguage.addAll(listComPref.get(0).getMarketLanguage());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getMarket());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
		Assert.assertEquals("Y", listMarketLanguage.get(0).getOptIn());
		
		request.getPermissionRequest().getPermission().get(0).setPermissionAnswer(false);
		response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - T - AF", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference updated", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		individu = individuRepository.findBySgin(gin);
		
		listComPref.clear();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());
		
		listMarketLanguage.clear();
		listMarketLanguage.addAll(listComPref.get(0).getMarketLanguage());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getMarket());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
		Assert.assertEquals("N", listMarketLanguage.get(0).getOptIn());
	}
	
	/**
	 * Create a Permission with Market CN Language CN in input + Profil + ISI V
	 *  -> One Compref : F N FB_PROG
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionFBWithML_ISI_OK() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("F", "N", "FB_PROG");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(false);
		permission.setMarket("FR");
		permission.setLanguage(LanguageCodesEnum.FR);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("F - N - FB_PROG", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());
		
		List<MarketLanguage> listMarketLanguage = new ArrayList<MarketLanguage>();
		listMarketLanguage.addAll(listComPref.get(0).getMarketLanguage());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getMarket());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
		Assert.assertEquals("N", listMarketLanguage.get(0).getOptIn());
	}
	
	/**
	 * Create a Permission + Profil + ISI V
	 *  -> One Compref : F N FB_PROG
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionFBWithoutML_ISI_OK() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("F", "N", "FB_PROG");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(false);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("F - N - FB_PROG", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());
		
		List<MarketLanguage> listMarketLanguage = new ArrayList<MarketLanguage>();
		listMarketLanguage.addAll(listComPref.get(0).getMarketLanguage());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getMarket());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
		Assert.assertEquals("N", listMarketLanguage.get(0).getOptIn());
	}
	
	/**
	 * Create a Permission without any address postal
	 *  -> One Compref : F N FB_PROG
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionFBWithML_ISI_KO() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("F", "N", "FB_PROG");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", false, "V", "FR", false);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(false, response.isSuccess());
		Assert.assertEquals("No ISIS or valid mailing address available", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(0, listComPref.size());
	}
	
	/**
	 * Create a Permission without any profil
	 *  -> One Compref : F N FB_PROG
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionFBWithML_PROFIL_KO() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("F", "N", "FB_PROG");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(false, "FR", true, "V", "FR", true);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("F - N - FB_PROG", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());
	}
	
	/**
	 * Create a Permission which is not referenced for the Market FR
	 *  -> One Compref : F N KQ
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionFBUnknow() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("F", "N", "KQ");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(false, response.isSuccess());
		Assert.assertEquals("Communication preference not available for market FR", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(0, listComPref.size());
	}
	
	/**
	 * Create a Permission + Profil + ISI V
	 *  -> One Compref : F N FB_PROG Optin Y
	 *  -> One Compref : F N FB_PROG Optin N
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdatePermissionFB() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("F", "N", "FB_PROG");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("FR");
		permission.setLanguage(LanguageCodesEnum.FR);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("F - N - FB_PROG", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());
		
		List<MarketLanguage> listMarketLanguage = new ArrayList<MarketLanguage>();
		listMarketLanguage.addAll(listComPref.get(0).getMarketLanguage());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getMarket());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
		Assert.assertEquals("Y", listMarketLanguage.get(0).getOptIn());
		
		request.getPermissionRequest().getPermission().get(0).setPermissionAnswer(false);
		response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("F - N - FB_PROG", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference updated", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		individu = individuRepository.findBySgin(gin);
		
		listComPref.clear();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());
		
		listMarketLanguage.clear();
		listMarketLanguage.addAll(listComPref.get(0).getMarketLanguage());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getMarket());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
		Assert.assertEquals("N", listMarketLanguage.get(0).getOptIn());
	}
	
	/**
	 * Create a Permission with Market CN Language CN in input + Profil + ISI V
	 *  -> One Compref : S N AF
	 *  -> One Compref : F N FB_PROG
	 *  -> One Compref : P S RECO
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionS_FB_P() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "N", "AF");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		refComPrefDgt = refComPrefDgtDS.findByDGT("F", "N", "FB_PROG");
		
		refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		refComPrefDgt = refComPrefDgtDS.findByDGT("P", "S", "RECO");
		
		refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("CN");
		permission.setLanguage(LanguageCodesEnum.CH);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		for (Information information : response.getInformationResponse().getInformations().get(0).getInformation()) {
			Assert.assertEquals("0", information.getCode());
			Assert.assertEquals("Communication preference created", information.getDetails());
		}
			
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(3, listComPref.size());
		
		List<MarketLanguage> listMarketLanguage = new ArrayList<MarketLanguage>();
		for (CommunicationPreferences comPref : listComPref) {
			if (comPref.getDomain().equalsIgnoreCase("F")) {
				Assert.assertEquals("Y", comPref.getSubscribe());
				listMarketLanguage.clear();
				listMarketLanguage.addAll(comPref.getMarketLanguage());
				Assert.assertEquals("FR", listMarketLanguage.get(0).getMarket());
				Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
				Assert.assertEquals("Y", listMarketLanguage.get(0).getOptIn());
			} else if (comPref.getDomain().equalsIgnoreCase("S")) {
				Assert.assertEquals("Y", comPref.getSubscribe());
				listMarketLanguage.clear();
				listMarketLanguage.addAll(comPref.getMarketLanguage());
				Assert.assertEquals("CN", listMarketLanguage.get(0).getMarket());
				Assert.assertEquals("CH", listMarketLanguage.get(0).getLanguage());
				Assert.assertEquals("Y", listMarketLanguage.get(0).getOptIn());
			} else if (comPref.getDomain().equalsIgnoreCase("P")) {
				Assert.assertEquals("Y", comPref.getSubscribe());
				Assert.assertTrue(comPref.getMarketLanguage().isEmpty());
			}
		}
	}
	
	/**
	 * Update a Permission with Market CN Language CN in input + Profil + ISI V
	 *  -> One Compref : S T AF Optin Y -> N
	 *  -> One Compref : F N FB_PROG Optin Y -> N
	 *  -> One Compref : P S RECO Optin Y -> N
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdatePermissionS_FB_P() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "T", "AF");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		refComPrefDgt = refComPrefDgtDS.findByDGT("F", "N", "FB_PROG");
		
		refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		refComPrefDgt = refComPrefDgtDS.findByDGT("P", "S", "RECO");
		
		refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("CN");
		permission.setLanguage(LanguageCodesEnum.CH);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		permission.setPermissionAnswer(false);
		response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		for (Information information : response.getInformationResponse().getInformations().get(0).getInformation()) {
			Assert.assertEquals("0", information.getCode());
			Assert.assertEquals("Communication preference updated", information.getDetails());
		}
			
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(3, listComPref.size());
		
		List<MarketLanguage> listMarketLanguage = new ArrayList<MarketLanguage>();
		for (CommunicationPreferences comPref : listComPref) {
			if (comPref.getDomain().equalsIgnoreCase("F")) {
				Assert.assertEquals("N", comPref.getSubscribe());
				listMarketLanguage.clear();
				listMarketLanguage.addAll(comPref.getMarketLanguage());
				Assert.assertEquals("FR", listMarketLanguage.get(0).getMarket());
				Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
				Assert.assertEquals("N", listMarketLanguage.get(0).getOptIn());
			} else if (comPref.getDomain().equalsIgnoreCase("S")) {
				Assert.assertEquals("N", comPref.getSubscribe());
				listMarketLanguage.clear();
				listMarketLanguage.addAll(comPref.getMarketLanguage());
				Assert.assertEquals("CN", listMarketLanguage.get(0).getMarket());
				Assert.assertEquals("CH", listMarketLanguage.get(0).getLanguage());
				Assert.assertEquals("N", listMarketLanguage.get(0).getOptIn());
			} else if (comPref.getDomain().equalsIgnoreCase("P")) {
				Assert.assertEquals("N", comPref.getSubscribe());
				Assert.assertTrue(comPref.getMarketLanguage().isEmpty());
			}
		}
	}
	
	/**
	 * Create a Permission with Market RW Language FR in input
	 *  -> One Compref : S A KQ
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Ignore
	@Transactional
	@Rollback(true)
	public void testCreatePermissionParticipatingAirlinesWithML_OK() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "A", "KQ");

		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);

		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);

		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);

		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("RW");
		permission.setLanguage(LanguageCodesEnum.FR);

		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);

		request.setPermissionRequest(permissionRequest);

		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);

		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - A - KQ", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);

		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());

		List<MarketLanguage> listMarketLanguage = new ArrayList<MarketLanguage>();
		listMarketLanguage.addAll(listComPref.get(0).getMarketLanguage());
		Assert.assertEquals("RW", listMarketLanguage.get(0).getMarket());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
		Assert.assertEquals("Y", listMarketLanguage.get(0).getOptIn());
	}
	
	/**
	 * Update a Permission with Market RW Language FR in input
	 *  -> One Compref : S A KQ Optin Y
	 *  -> One Compref : S A KQ Optin N
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Ignore
	@Transactional
	@Rollback(true)
	public void testUpdatePermissionParticipatingAirlinesWithML_OK() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "A", "KQ");

		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);

		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);

		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);

		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("RW");
		permission.setLanguage(LanguageCodesEnum.FR);

		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);

		request.setPermissionRequest(permissionRequest);

		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);

		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - A - KQ", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);

		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());

		List<MarketLanguage> listMarketLanguage = new ArrayList<MarketLanguage>();
		listMarketLanguage.addAll(listComPref.get(0).getMarketLanguage());
		Assert.assertEquals("RW", listMarketLanguage.get(0).getMarket());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
		Assert.assertEquals("Y", listMarketLanguage.get(0).getOptIn());
		
		/**
		 * UPDATE PART
		 * 
		 */
		request.getPermissionRequest().getPermission().get(0).setPermissionAnswer(false);
		response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - A - KQ", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference updated", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		individu = individuRepository.findBySgin(gin);
		
		listComPref.clear();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());
		
		listMarketLanguage.clear();
		listMarketLanguage.addAll(listComPref.get(0).getMarketLanguage());
		Assert.assertEquals("RW", listMarketLanguage.get(0).getMarket());
		Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
		Assert.assertEquals("N", listMarketLanguage.get(0).getOptIn());
	}
	
	/**
	 * Create a Permission with Market RW Language DE in input
	 *  -> One Compref : S A KQ
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Ignore
	@Transactional
	@Rollback(true)
	public void testCreatePermissionParticipatingAirlinesWithML_OK_DefaultLanguage() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "A", "KQ");

		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);

		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);

		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);

		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("RW");
		permission.setLanguage(LanguageCodesEnum.DE);

		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);

		request.setPermissionRequest(permissionRequest);

		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);

		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - A - KQ", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);

		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());

		List<MarketLanguage> listMarketLanguage = new ArrayList<MarketLanguage>();
		listMarketLanguage.addAll(listComPref.get(0).getMarketLanguage());
		Assert.assertEquals("RW", listMarketLanguage.get(0).getMarket());
		Assert.assertEquals("DE", listMarketLanguage.get(0).getLanguage());
		Assert.assertEquals("Y", listMarketLanguage.get(0).getOptIn());
	}
	
	/**
	 * Create a Permission without Market and Language DE
	 *  -> One Compref : S A KQ
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Ignore
	@Transactional
	@Rollback(true)
	public void testCreatePermissionParticipatingAirlinesWithoutML() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "A", "KQ");

		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);

		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);

		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);

		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);

		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);

		request.setPermissionRequest(permissionRequest);

		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);

		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - A - KQ", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);

		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(1, listComPref.size());
	}
	
	/**
	 * Create a Permission wich is not referenced for market NZ
	 *  -> One Compref : S A KQ
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Ignore
	@Transactional
	@Rollback(true)
	public void testCreatePermissionParticipatingAirlinesWithUnknowMarket() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "A", "KQ");

		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);

		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);

		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);

		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("NZ");
		permission.setLanguage(LanguageCodesEnum.DE);

		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);

		request.setPermissionRequest(permissionRequest);

		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);

		Assert.assertEquals(false, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - A - KQ", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		Individu individu = individuRepository.findBySgin(gin);

		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(0, listComPref.size());
	}
	
	/**
	 * Create a Permission with Market CN Language CN in input + Profil + ISI V
	 *  -> One Compref : S N AF
	 *  -> One Compref : F N FB_PROG
	 *  -> One Compref : P S RECO
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePermissionS_FB_P_A() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "N", "AF");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		refComPrefDgt = refComPrefDgtDS.findByDGT("F", "N", "FB_PROG");
		
		refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		refComPrefDgt = refComPrefDgtDS.findByDGT("P", "S", "RECO");
		
		refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("CN");
		permission.setLanguage(LanguageCodesEnum.CH);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		for (Information information : response.getInformationResponse().getInformations().get(0).getInformation()) {
			if (information.getName().equalsIgnoreCase("S - T - AF")) {
				Assert.assertEquals("0", information.getCode());
				Assert.assertEquals("Communication preference created", information.getDetails());
			} else if (information.getName().equalsIgnoreCase("F - N - FB_PROG")) {
				Assert.assertEquals("0", information.getCode());
				Assert.assertEquals("Communication preference created", information.getDetails());
			} else if (information.getName().equalsIgnoreCase("P - S - RECO")) {
				Assert.assertEquals("0", information.getCode());
				Assert.assertEquals("Communication preference created", information.getDetails());
			}
		}
			
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(3, listComPref.size());
		
		List<MarketLanguage> listMarketLanguage = new ArrayList<MarketLanguage>();
		for (CommunicationPreferences comPref : listComPref) {
			if (comPref.getDomain().equalsIgnoreCase("F")) {
				Assert.assertEquals("Y", comPref.getSubscribe());
				listMarketLanguage.clear();
				listMarketLanguage.addAll(comPref.getMarketLanguage());
				Assert.assertEquals("FR", listMarketLanguage.get(0).getMarket());
				Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
				Assert.assertEquals("Y", listMarketLanguage.get(0).getOptIn());
			} else if (comPref.getDomain().equalsIgnoreCase("S")) {
				Assert.assertEquals("Y", comPref.getSubscribe());
				listMarketLanguage.clear();
				listMarketLanguage.addAll(comPref.getMarketLanguage());
				Assert.assertEquals("CN", listMarketLanguage.get(0).getMarket());
				Assert.assertEquals("CH", listMarketLanguage.get(0).getLanguage());
				Assert.assertEquals("Y", listMarketLanguage.get(0).getOptIn());
			} else if (comPref.getDomain().equalsIgnoreCase("P")) {
				Assert.assertEquals("Y", comPref.getSubscribe());
				Assert.assertTrue(comPref.getMarketLanguage().isEmpty());
			}
		}
	}
	
	/**
	 * Update a Permission with Market CN Language CN in input + Profil + ISI V
	 *  -> One Compref : S N AF Y -> N
	 *  -> One Compref : F N FB_PROG Y -> N
	 *  -> One Compref : P S RECO Y -> N
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdatePermissionS_FB_P_A() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		/*-- CREATE PERMISSION FOR TEST --*/		
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("S", "N", "AF");
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		refComPrefDgt = refComPrefDgtDS.findByDGT("F", "N", "FB_PROG");
		
		refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		refComPrefDgt = refComPrefDgtDS.findByDGT("P", "S", "RECO");
		
		refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
				
		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("RW");
		permission.setLanguage(LanguageCodesEnum.FR);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		for (Information information : response.getInformationResponse().getInformations().get(0).getInformation()) {
			if (information.getName().equalsIgnoreCase("S - T - AF")) {
				Assert.assertEquals("0", information.getCode());
				Assert.assertEquals("Communication preference created", information.getDetails());
			} else if (information.getName().equalsIgnoreCase("F - N - FB_PROG")) {
				Assert.assertEquals("0", information.getCode());
				Assert.assertEquals("Communication preference created", information.getDetails());
			} else if (information.getName().equalsIgnoreCase("P - S - RECO")) {
				Assert.assertEquals("0", information.getCode());
				Assert.assertEquals("Communication preference created", information.getDetails());
			}
		}
			
		Individu individu = individuRepository.findBySgin(gin);
		
		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(3, listComPref.size());
		
		List<MarketLanguage> listMarketLanguage = new ArrayList<MarketLanguage>();
		for (CommunicationPreferences comPref : listComPref) {
			if (comPref.getDomain().equalsIgnoreCase("F")) {
				Assert.assertEquals("Y", comPref.getSubscribe());
				listMarketLanguage.clear();
				listMarketLanguage.addAll(comPref.getMarketLanguage());
				Assert.assertEquals("FR", listMarketLanguage.get(0).getMarket());
				Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
				Assert.assertEquals("Y", listMarketLanguage.get(0).getOptIn());
			} else if (comPref.getDomain().equalsIgnoreCase("S")) {
				Assert.assertEquals("Y", comPref.getSubscribe());
				listMarketLanguage.clear();
				listMarketLanguage.addAll(comPref.getMarketLanguage());
				Assert.assertEquals("RW", listMarketLanguage.get(0).getMarket());
				Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
				Assert.assertEquals("Y", listMarketLanguage.get(0).getOptIn());

			} else if (comPref.getDomain().equalsIgnoreCase("P")) {
				Assert.assertEquals("Y", comPref.getSubscribe());
				Assert.assertTrue(comPref.getMarketLanguage().isEmpty());
			}
		}
		
		/**
		 * UPDATE PART
		 * 
		 */
		request.getPermissionRequest().getPermission().get(0).setPermissionAnswer(false);
		response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		for (Information information : response.getInformationResponse().getInformations().get(0).getInformation()) {
			if (information.getName().equalsIgnoreCase("S - T - AF")) {
				Assert.assertEquals("0", information.getCode());
				Assert.assertEquals("Communication preference updated", information.getDetails());
			} else if (information.getName().equalsIgnoreCase("F - N - FB_PROG")) {
				Assert.assertEquals("0", information.getCode());
				Assert.assertEquals("Communication preference updated", information.getDetails());
			} else if (information.getName().equalsIgnoreCase("P - S - RECO")) {
				Assert.assertEquals("0", information.getCode());
				Assert.assertEquals("Communication preference updated", information.getDetails());
			}
		}
		
		listComPref.clear();
		listComPref.addAll(individu.getCommunicationpreferences());
		Assert.assertEquals(3, listComPref.size());
		
		listMarketLanguage.clear();
		for (CommunicationPreferences comPref : listComPref) {
			if (comPref.getDomain().equalsIgnoreCase("F")) {
				Assert.assertEquals("N", comPref.getSubscribe());
				listMarketLanguage.clear();
				listMarketLanguage.addAll(comPref.getMarketLanguage());
				Assert.assertEquals("FR", listMarketLanguage.get(0).getMarket());
				Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
				Assert.assertEquals("N", listMarketLanguage.get(0).getOptIn());
			} else if (comPref.getDomain().equalsIgnoreCase("S")) {
				Assert.assertEquals("N", comPref.getSubscribe());
				listMarketLanguage.clear();
				listMarketLanguage.addAll(comPref.getMarketLanguage());
				Assert.assertEquals("RW", listMarketLanguage.get(0).getMarket());
				Assert.assertEquals("FR", listMarketLanguage.get(0).getLanguage());
				Assert.assertEquals("N", listMarketLanguage.get(0).getOptIn());

			} else if (comPref.getDomain().equalsIgnoreCase("P")) {
				Assert.assertEquals("N", comPref.getSubscribe());
				Assert.assertTrue(comPref.getMarketLanguage().isEmpty());
			}
		}
	}
	
	/**
	 * Create a Full test that tests all possible cases for Sales Domain
	 * 
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testAllCasesSalesDomain() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
		
		RefComPrefDgtDTO refComPrefDgtSNAF = refComPrefDgtDS.findByDGT("S", "N", "AF");
		
		/*---- CREATE PERMISSION C01 ----*/
		/* Normal case with Market and Language in input */
		RefPermissionsQuestionDTO refPermissionsQuestionC01 = new RefPermissionsQuestionDTO();
		refPermissionsQuestionC01.setDateCreation(new Date());
		refPermissionsQuestionC01.setName("C01");
		refPermissionsQuestionC01.setQuestion("Blabla en FR");
		refPermissionsQuestionC01.setQuestionEN("Blabla en En");
		refPermissionsQuestionC01.setSignatureCreation("TESTMZ_C");
		refPermissionsQuestionC01.setSiteCreation("QVI_C");
		refPermissionsQuestionDS.create(refPermissionsQuestionC01);
		
		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestionC01, refComPrefDgtSNAF);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest("999999999999", true, "FR", true, "V", "FR", true);
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("FR");
		permission.setLanguage(LanguageCodesEnum.FR);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - N - AF", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		/*---- CREATE PERMISSION C02 ----*/
		/* Normal case with Market and Language in input but the Market is unknown in CATI*/
		RefPermissionsQuestionDTO refPermissionsQuestionC02 = new RefPermissionsQuestionDTO();
		refPermissionsQuestionC02.setDateCreation(new Date());
		refPermissionsQuestionC02.setName("C02");
		refPermissionsQuestionC02.setQuestion("Blabla en FR");
		refPermissionsQuestionC02.setQuestionEN("Blabla en En");
		refPermissionsQuestionC02.setSignatureCreation("TESTMZ_C");
		refPermissionsQuestionC02.setSiteCreation("QVI_C");
		refPermissionsQuestionDS.create(refPermissionsQuestionC02);
		
		refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestionC02, refComPrefDgtSNAF);
		refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		gin = createOrUpdateComPrefHelper.generateIndividualForTest("999999999998", true, "FR", true, "V", "FR", true);
		
		request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("GG");
		permission.setLanguage(LanguageCodesEnum.FR);
		
		permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - N - AF", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
				
		/*---- CREATE PERMISSION C04 ----*/
		/* No Market and no Language in input, we use FlyingBlue values*/
		RefPermissionsQuestionDTO refPermissionsQuestionC04 = new RefPermissionsQuestionDTO();
		refPermissionsQuestionC04.setDateCreation(new Date());
		refPermissionsQuestionC04.setName("C04");
		refPermissionsQuestionC04.setQuestion("Blabla en FR");
		refPermissionsQuestionC04.setQuestionEN("Blabla en En");
		refPermissionsQuestionC04.setSignatureCreation("TESTMZ_C");
		refPermissionsQuestionC04.setSiteCreation("QVI_C");
		refPermissionsQuestionDS.create(refPermissionsQuestionC04);
		
		refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestionC04, refComPrefDgtSNAF);
		refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		gin = createOrUpdateComPrefHelper.generateIndividualForTest("999999999996", true, "FR", true, "V", "FR", true);
		
		request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		
		permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals("0", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - N - AF", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Communication preference created", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
				
		/*---- CREATE PERMISSION C06 ----*/
		/* Case in error because only Market is provided in input */
		RefPermissionsQuestionDTO refPermissionsQuestionC06 = new RefPermissionsQuestionDTO();
		refPermissionsQuestionC06.setDateCreation(new Date());
		refPermissionsQuestionC06.setName("C06");
		refPermissionsQuestionC06.setQuestion("Blabla en FR");
		refPermissionsQuestionC06.setQuestionEN("Blabla en En");
		refPermissionsQuestionC06.setSignatureCreation("TESTMZ_C");
		refPermissionsQuestionC06.setSiteCreation("QVI_C");
		refPermissionsQuestionDS.create(refPermissionsQuestionC06);
		
		refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestionC06, refComPrefDgtSNAF);
		refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		gin = createOrUpdateComPrefHelper.generateIndividualForTest("999999999994", true, "FR", true, "V", "FR", true);
		
		request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setMarket("GG");
		
		permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(false, response.isSuccess());
		Assert.assertEquals("1", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - N - AF", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Cannot create communication preference. Language is missing in input", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
				
		/*---- CREATE PERMISSION C08 ----*/
		/* Case in error because only Language is provided in input */
		RefPermissionsQuestionDTO refPermissionsQuestionC08 = new RefPermissionsQuestionDTO();
		refPermissionsQuestionC08.setDateCreation(new Date());
		refPermissionsQuestionC08.setName("C08");
		refPermissionsQuestionC08.setQuestion("Blabla en FR");
		refPermissionsQuestionC08.setQuestionEN("Blabla en En");
		refPermissionsQuestionC08.setSignatureCreation("TESTMZ_C");
		refPermissionsQuestionC08.setSiteCreation("QVI_C");
		refPermissionsQuestionDS.create(refPermissionsQuestionC08);
		
		refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestionC08, refComPrefDgtSNAF);
		refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		gin = createOrUpdateComPrefHelper.generateIndividualForTest("999999999992", true, "FR", true, "V", "FR", true);
		
		request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		permission.setLanguage(LanguageCodesEnum.FR);
		
		permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(false, response.isSuccess());
		Assert.assertEquals("1", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - N - AF", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("Cannot create communication preference. Market is missing in input", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
		
		/*---- CREATE PERMISSION C09 ----*/
		/* Case in error because no Market and no Language in input and also an error occured during FlyingBlue Algo */
		RefPermissionsQuestionDTO refPermissionsQuestionC09 = new RefPermissionsQuestionDTO();
		refPermissionsQuestionC09.setDateCreation(new Date());
		refPermissionsQuestionC09.setName("C08");
		refPermissionsQuestionC09.setQuestion("Blabla en FR");
		refPermissionsQuestionC09.setQuestionEN("Blabla en En");
		refPermissionsQuestionC09.setSignatureCreation("TESTMZ_C");
		refPermissionsQuestionC09.setSiteCreation("QVI_C");
		refPermissionsQuestionDS.create(refPermissionsQuestionC09);
		
		refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestionC09, refComPrefDgtSNAF);
		refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);
		
		gin = createOrUpdateComPrefHelper.generateIndividualForTest("999999999991", true, "FR", false, "V", "FR", true);
		
		request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		request.setRequestor(requestor);
		
		permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);
		
		permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		
		Assert.assertEquals(false, response.isSuccess());
		Assert.assertEquals("1", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getCode());
		Assert.assertEquals("S - N - AF", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getName());
		Assert.assertEquals("No ISIS or valid mailing address available", response.getInformationResponse().getInformations().get(0).getInformation().get(0).getDetails());
	}
	
	@Test
	public void testGinNotFound() {
		
		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin("999999999999");
		request.setRequestor(requestor);
		
		Permission permission = new Permission();
		permission.setPermissionID("241");
		permission.setPermissionAnswer(true);
		
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);
		
		request.setPermissionRequest(permissionRequest);
		
		try {
			createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);
		} catch (BusinessErrorBlocBusinessException | SystemException e) {
			Assert.assertEquals("NOT FOUND", e.getMessage());
		}
	}
	
	/**
	 * Test Small Signature Permission
	 *  
	 * @throws JrafDomainException 
	 * @throws SystemException 
	 * @throws BusinessErrorBlocBusinessException 
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testSignaturePermissionSmall() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException{
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("P", "S", "RECO");

		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);

		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);

		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		requestor.setSignature("A");
		request.setRequestor(requestor);

		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);

		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);

		request.setPermissionRequest(permissionRequest);

		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);

		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals(true, response.getInformationResponse().getInformations()
				.get(0).getInformation().get(0).getDetails().equals("Communication preference created"));

		Individu individu = individuRepository.findBySgin(gin);

		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());

		Assert.assertEquals(1, listComPref.size());
		Assert.assertNotEquals(requestor.getSignature(), listComPref.get(0).getCreationSignature());
	}	
	
	/**
	 * Test Normal Signature Permission
	 *  
	 * @throws JrafDomainException 
	 * @throws SystemException 
	 * @throws BusinessErrorBlocBusinessException 
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testSignaturePermissionNormal() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException{
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("P", "S", "RECO");

		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);

		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);

		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		requestor.setSignature("AAAAA");
		request.setRequestor(requestor);

		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);

		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);

		request.setPermissionRequest(permissionRequest);

		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);

		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals(true, response.getInformationResponse().getInformations()
				.get(0).getInformation().get(0).getDetails().equals("Communication preference created"));

		Individu individu = individuRepository.findBySgin(gin);

		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());

		Assert.assertEquals(1, listComPref.size());
		Assert.assertNotEquals(requestor.getSignature(), listComPref.get(0).getCreationSignature());
	}	
	
	/**
	 * Test Big Signature Permission
	 *  
	 * @throws JrafDomainException 
	 * @throws SystemException 
	 * @throws BusinessErrorBlocBusinessException 
	 * 
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testSignaturePermissionBig() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException{
		RefComPrefDgtDTO refComPrefDgt = refComPrefDgtDS.findByDGT("P", "S", "RECO");

		RefPermissionsIdDTO refPermissionsId = new RefPermissionsIdDTO(refPermissionsQuestion, refComPrefDgt);
		RefPermissionsDTO refPermissions = new RefPermissionsDTO();
		refPermissions.setRefPermissionsId(refPermissionsId);
		refPermissions.setDateCreation(new Date());
		refPermissions.setSignatureCreation("TESTRI");
		refPermissions.setSiteCreation("QVI");
		refPermissions.setDateModification(new Date());
		refPermissions.setSignatureModification("TESTRI");
		refPermissions.setSiteModification("QVI");
		refPermissionsDS.create(refPermissions);

		/*-- CREATE INDIVIDU FOR TEST --*/
		String gin = createOrUpdateComPrefHelper.generateIndividualForTest(true, "FR", true, "V", "FR", true);

		CreateOrUpdateComPrefBasedOnPermissionRequest request = new CreateOrUpdateComPrefBasedOnPermissionRequest();
		request.setGin(gin);
		requestor.setSignature("AAAAAAAAAAAAAAAA");
		request.setRequestor(requestor);

		Permission permission = new Permission();
		permission.setPermissionID(refPermissions.getRefPermissionsId().getQuestionId().getId().toString());
		permission.setPermissionAnswer(true);

		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.getPermission().add(permission);

		request.setPermissionRequest(permissionRequest);

		CreateOrUpdateComPrefBasedOnPermissionResponse response = createOrUpdateComPrefBasedOnPermissionImplV1.createOrUpdateComPrefBasedOnPermission(request);

		Assert.assertEquals(true, response.isSuccess());
		Assert.assertEquals(true, response.getInformationResponse().getInformations()
				.get(0).getInformation().get(0).getDetails().equals("Communication preference created"));

		Individu individu = individuRepository.findBySgin(gin);

		List<CommunicationPreferences> listComPref = new ArrayList<CommunicationPreferences>();
		listComPref.addAll(individu.getCommunicationpreferences());

		Assert.assertEquals(1, listComPref.size());
		Assert.assertNotEquals(requestor.getSignature(), listComPref.get(0).getCreationSignature());
	}	
}
