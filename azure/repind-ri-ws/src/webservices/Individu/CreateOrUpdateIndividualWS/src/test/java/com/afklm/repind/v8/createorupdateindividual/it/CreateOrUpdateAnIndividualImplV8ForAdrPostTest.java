package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.PostalAddressRequest;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.IndividualInformationsV3;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.PostalAddressContent;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.PostalAddressProperties;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.UsageAddress;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.adresse.Usage_medium;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV8ForAdrPostTest {
	
	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV8ForAdrPostTest.class);
	
	private final static String GIN_TEST = "400519178230";
	private final static String CHANNEL = "DS";
	private final static String SITE = "QVI";
	private final static String SIGNATURE = "Test";
	private final static String NUM_RUE = "1 AVENUE DU TEST RHÖÔNE";
	private final static String NUM_RUE_NORM = "1 AVENUE DU TEST RHOONE";
	
	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v8Bean")
	private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImplV8;
	
	@Autowired
	private PostalAddressDS postalAddressDS;
	
	@Autowired 
	private VariablesDS variablesDS;
	
	@Rollback(true)
	@Test
	@Transactional
	public void testCreatePostalAddress_Isi01_directNoUsage() {
		
		logger.info("***** Start testCreatePostalAddress_Isi01_directNoUsage *******");
		
		CreateUpdateIndividualRequest request = initRequest("ISI");
		
		List<PostalAddressRequest> adrList = new ArrayList<PostalAddressRequest>();
		adrList.add(initAdrPost(false, null, "D", "V", null, null));
		
		request.getPostalAddressRequest().addAll(adrList);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		
		List<PostalAddress> adrListFromDB = null;
		try {
			adrListFromDB = postalAddressDS.getAddressByGin(GIN_TEST);
		} catch (JrafDomainException e) {
			logger.info(e.getMessage());
			return;
		}
		
		Assert.assertNotNull(adrListFromDB);
		PostalAddress pa = getTestAddress(adrListFromDB, "ISI", 1, "M");
		
		Assert.assertNotNull(pa);
		Assert.assertTrue(pa.getSno_et_rue().equalsIgnoreCase(NUM_RUE_NORM));
		
		logger.info("***** End testCreatePostalAddress_Isi01_directNoUsage *******");
	}
	
	@Rollback(true)
	@Test
	@Transactional
	public void testCreatePostalAddress_Isi01_directWithUsage() {
		
		logger.info("***** Start testCreatePostalAddress_Isi01_directWithUsage *******");
		
		CreateUpdateIndividualRequest request = initRequest("ISI");
		
		List<PostalAddressRequest> adrList = new ArrayList<PostalAddressRequest>();
		adrList.add(initAdrPost(true, "ISI", "D", "V", "M", "01"));
		
		request.getPostalAddressRequest().addAll(adrList);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		
		List<PostalAddress> adrListFromDB = null;
		try {
			adrListFromDB = postalAddressDS.getAddressByGin(GIN_TEST);
		} catch (JrafDomainException e) {
			logger.info(e.getMessage());
			return;
		}
		
		Assert.assertNotNull(adrListFromDB);
		PostalAddress pa = getTestAddress(adrListFromDB, "ISI", 1, "M");
		
		Assert.assertNotNull(pa);
		Assert.assertTrue(pa.getSno_et_rue().equalsIgnoreCase(NUM_RUE_NORM));
		
		logger.info("***** End testCreatePostalAddress_Isi01_directWithUsage *******");
	}
	
	@Rollback(true)
	@Test
	@Transactional
	public void testCreatePostalAddress_Bdc01_directWithUsage() {
		
		logger.info("***** Start testCreatePostalAddress_Bdc01_directWithUsage *******");
		
		CreateUpdateIndividualRequest request = initRequest("BDC");
		
		List<PostalAddressRequest> adrList = new ArrayList<PostalAddressRequest>();
		adrList.add(initAdrPost(true, "BDC", "D", "V", null, "01"));
		
		request.getPostalAddressRequest().addAll(adrList);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());
		
		List<PostalAddress> adrListFromDB = null;
		try {
			adrListFromDB = postalAddressDS.getAddressByGin(GIN_TEST);
		} catch (JrafDomainException e) {
			logger.info(e.getMessage());
			return;
		}
		
		Assert.assertNotNull(adrListFromDB);
		PostalAddress pa = getTestAddress(adrListFromDB, "BDC", 1, null);
		
		Assert.assertNotNull(pa);
		Assert.assertTrue(pa.getSno_et_rue().equalsIgnoreCase(NUM_RUE_NORM));
		
		logger.info("***** End testCreatePostalAddress_Bdc01_directWithUsage *******");
	}
	
	@Rollback(true)
	@Test
	@Transactional
	public void testCreatePostalAddress_Bdc02_directWithUsage() {
		
		logger.info("***** Start testCreatePostalAddress_Bdc02_directWithUsage *******");
		
		CreateUpdateIndividualRequest request = initRequest("BDC");
		
		List<PostalAddressRequest> adrList = new ArrayList<PostalAddressRequest>();
		adrList.add(initAdrPost(true, "BDC", "D", "V", null, "02"));
		
		request.getPostalAddressRequest().addAll(adrList);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());
		
		List<PostalAddress> adrListFromDB = null;
		try {
			adrListFromDB = postalAddressDS.getAddressByGin(GIN_TEST);
		} catch (JrafDomainException e) {
			logger.info(e.getMessage());
			return;
		}
		
		Assert.assertNotNull(adrListFromDB);
		PostalAddress pa = getTestAddress(adrListFromDB, "BDC", 1, null);
		PostalAddress pa2 = getTestAddress(adrListFromDB, "BDC", 2, null);
		
		Assert.assertNull(pa);
		Assert.assertNotNull(pa2);
		Assert.assertTrue(pa2.getSno_et_rue().equalsIgnoreCase(NUM_RUE_NORM));
		
		logger.info("***** End testCreatePostalAddress_Bdc02_directWithUsage *******");
	}
	
	@Rollback(true)
	@Test
	@Transactional
	public void testCreatePostalAddress_MaxAddresReached() throws NumberFormatException, JrafDomainException {
		
		logger.info("***** Start testCreatePostalAddress_MaxAddresReached *******");
		
		int maxAdr = Integer.valueOf(variablesDS.getByEnvKey("MAX_POSTAL_ADDRESS").getEnvValue());
		
		// Prepare test --> Insert 5 postal address for this GIN
		for (int i = 0; i < maxAdr ; i++) {
			PostalAddressDTO padto = new PostalAddressDTO();
			padto.setSgin(GIN_TEST);
			padto.setVersion(1);
			padto.setIcod_err(0);
			padto.setSstatut_medium("V");
			padto.setScode_medium("D");
			padto.setDdate_creation(new Date());
			padto.setSignature_creation(SIGNATURE);
			padto.setSsite_creation(SITE);
			padto.setSno_et_rue(i + " rue du test");
			padto.setScode_postal("06000");
			padto.setSville("NICE");
			padto.setScode_pays("FR");
			
			Set<Usage_mediumDTO> umdlist = new HashSet<Usage_mediumDTO>();
			Usage_mediumDTO umd = new Usage_mediumDTO();
			umd.setScode_application("BDC");
			umd.setInum(i);
			umdlist.add(umd);
			padto.setUsage_mediumdto(umdlist);
			
			postalAddressDS.create(padto);
		}
		
		CreateUpdateIndividualRequest request = initRequest("ISI");
		
		List<PostalAddressRequest> adrList = new ArrayList<PostalAddressRequest>();
		adrList.add(initAdrPost(false, "ISI", "D", "V", null, null));
		
		request.getPostalAddressRequest().addAll(adrList);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.assertEquals("ERROR_132", e.getFaultInfo().getBusinessError().getOtherErrorCode());
		}
		
		logger.info("***** End testCreatePostalAddress_MaxAddresReached *******");
	}
	
	@Rollback(true)
	@Test
	@Transactional
	public void testCreatePostalAddress_DeleteExtraAddress() throws NumberFormatException, JrafDomainException {
		
		logger.info("***** Start testCreatePostalAddress_DeleteExtraAddress *******");
		
		int maxAdr = Integer.valueOf(variablesDS.getByEnvKey("MAX_POSTAL_ADDRESS").getEnvValue());
		
		// Prepare test --> Insert 4 postal address for this GIN (as 1 postal adr already exists in DB)
		for (int i = 1; i < maxAdr ; i++) {
			PostalAddressDTO padto = new PostalAddressDTO();
			padto.setSgin(GIN_TEST);
			padto.setVersion(1);
			padto.setIcod_err(0);
			padto.setSstatut_medium("V");
			padto.setScode_medium("D");
			padto.setDdate_creation(new Date());
			padto.setSignature_creation(SIGNATURE);
			padto.setSsite_creation(SITE);
			padto.setSno_et_rue(i + " AVENUE DU TEST");
			padto.setScode_postal("06560");
			padto.setSville("VALBONNE");
			padto.setScode_pays("FR");
			
			postalAddressDS.create(padto);
		}
		
		CreateUpdateIndividualRequest request = initRequest("ISI");
		
		List<PostalAddressRequest> adrList = new ArrayList<PostalAddressRequest>();
		adrList.add(initAdrPost(false, "ISI", "D", "X", null, null));
		
		request.getPostalAddressRequest().addAll(adrList);
		
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		try {
			response = createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.assertNotNull(response);
			Assert.assertTrue(response.isSuccess());
			
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
			Assert.fail();
		}
		
		logger.info("***** End testCreatePostalAddress_DeleteExtraAddress *******");
	}

	private PostalAddress getTestAddress(List<PostalAddress> adrListFromDB, String appCode, Integer num, String role) {
		
		if (adrListFromDB != null) {
			for (PostalAddress pa : adrListFromDB) {
				if (pa.getSstatut_medium() != null && "V".equalsIgnoreCase(pa.getSstatut_medium())) {
					if (pa.getUsage_medium() != null) {
						for (Usage_medium um : pa.getUsage_medium()) {
							if (appCode.equalsIgnoreCase(um.getScode_application()) && num.equals(um.getInum())) {
								if (role != null && role.equalsIgnoreCase(um.getSrole1())) {
									return pa;
								}
								else if (role == null && um.getSrole1() == null) {
									return pa;
								}
							}
						}								
					}
				}
			}
		}
		
		return null;
	}

	private PostalAddressRequest initAdrPost(boolean usageFromInput, String appCode, String medium, String status, String role, String num) {
		
		PostalAddressRequest adr = new PostalAddressRequest();
		PostalAddressProperties prop = new PostalAddressProperties();
		PostalAddressContent cont = new PostalAddressContent();
		UsageAddress usg = null;
		
		prop.setIndicAdrNorm(true);
		prop.setMediumCode(medium);
		prop.setMediumStatus(status);
		adr.setPostalAddressProperties(prop);

		cont.setNumberAndStreet(NUM_RUE);
		cont.setCity("VALBONNE");
		cont.setZipCode("06560");
		cont.setCountryCode("FR");
		adr.setPostalAddressContent(cont);
		
		
		if (usageFromInput) {
			usg = new UsageAddress();
			usg.setApplicationCode(appCode);
			usg.setUsageNumber(num);
			if (role != null) {
				usg.setAddressRoleCode(role);
			}
			
			adr.setUsageAddress(usg);
		} 
		
		return adr;
	}

	private CreateUpdateIndividualRequest initRequest(String appCode) {
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode(appCode);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier(GIN_TEST);

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		
		return request;
	}
}
