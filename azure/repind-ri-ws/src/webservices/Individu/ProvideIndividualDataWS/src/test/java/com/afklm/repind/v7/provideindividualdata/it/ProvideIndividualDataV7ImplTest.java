package com.afklm.repind.v7.provideindividualdata.it;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.v7.provideindividualdata.ProvideIndividualDataV7Impl;
import com.afklm.repind.v7.provideindividualdata.helpers.PaymentPreferencesDataHelper;
import com.afklm.repind.v7.provideindividualdata.helpers.UtfHelper;
import com.afklm.repind.v7.provideindividualdata.type.ScopesToProvideEnum;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v7.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v7.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v7.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v7.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000418.v7.response.CommunicationPreferencesResponse;
import com.afklm.soa.stubs.w000418.v7.response.ContractResponse;
import com.afklm.soa.stubs.w000418.v7.response.TelecomResponse;
import com.afklm.soa.stubs.w000418.v7.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000418.v7.siccommontype.Signature;
import com.afklm.soa.stubs.w000418.v7.sicindividutype.ContractV2;
import com.afklm.soa.stubs.w000418.v7.sicindividutype.PreferenceDataV2;
import com.afklm.soa.stubs.w000418.v7.sicindividutype.PreferenceV2;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.RoleDoctorEnum;
import com.airfrance.ref.type.SignatureTypeEnum;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.preference.PreferenceRepository;
import com.airfrance.repind.dao.role.BusinessRoleRepository;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.delegation.DelegationDataInfoDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.preference.Preference;
import com.airfrance.repind.entity.preference.PreferenceData;
import com.airfrance.repind.entity.role.BusinessRole;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ProvideIndividualDataV7ImplTest {

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "TEST_RI";
	private static final String SITE = "QVI";
	private static final String MATRICULE = "M406368";
	private static final String APP_CODE = "ISI";
	
	@Autowired
	@Qualifier("passenger_ProvideIndividualData-v07Bean")
	private ProvideIndividualDataV7Impl provideIndividualDataV7Impl;
	
	@Autowired
	private PreferenceRepository preferenceRepository;
	
	@Autowired
	private IndividuRepository individuRepository;

	@Autowired
	private CommunicationPreferencesRepository communicationPreferencesRepository;

	@Autowired
	private DelegationDataDS delegationDataDS;
	
	@Autowired
	private IndividuDS individuDS;

	@Autowired
	private BusinessRoleRepository businessRoleRepository;
	
	private RequestorV2 initRequestor() {
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setMatricule(MATRICULE);
		requestor.setApplicationCode(APP_CODE);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		
		return requestor;
	}
	
	/**
	 * Provide a COM_PREF Domain P groupType S and type RECO 
	 * Expected : All OK, Com_Pref find in DB
	 * 
	 * @throws BusinessErrorBlocBusinessException
	 * @throws JrafDomainException
	 * @throws SystemException
	 */
	@Test
	@Transactional
	@Rollback(true)
	public void testProvideAnIndividual_ComPrefRECO()
			throws BusinessErrorBlocBusinessException, JrafDomainException, SystemException {

		// Init data
		// Date = 01 Janvier 2010
		Calendar initDate = new GregorianCalendar(2010, 01, 01);

		Individu testIndividu = individuRepository.findBySgin("400424668522");
		com.airfrance.repind.entity.individu.CommunicationPreferences comPrefInit = new com.airfrance.repind.entity.individu.CommunicationPreferences();

		comPrefInit.setDomain("P");
		comPrefInit.setComGroupType("S");
		comPrefInit.setComType("RECO");
		comPrefInit.setSubscribe("Y");
		comPrefInit.setCreationDate(initDate.getTime());
		comPrefInit.setDateOptin(initDate.getTime());
		comPrefInit.setGin("400424668522");

		communicationPreferencesRepository.saveAndFlush(comPrefInit);

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400424668522");
		request.setOption("GIN");
		request.getScopeToProvide().add("COMMUNICATION PREFERENCE");

			ProvideIndividualInformationResponse response = provideIndividualDataV7Impl
				.provideIndividualDataByIdentifier(request);
			
			boolean isReturnComPref = false;
			for(CommunicationPreferencesResponse comPref : response.getCommunicationPreferencesResponse()) {
			if ("P".equals(comPref.getCommunicationPreferences().getDomain())
					&& "S".equals(comPref.getCommunicationPreferences().getCommunicationGroupeType())
					&& "RECO".equals(comPref.getCommunicationPreferences().getCommunicationType())) {
				isReturnComPref = true;
			}
			}

		Assert.assertTrue(isReturnComPref);
	}

	/**
	 * Create a provide's request with a individual of type F in our database
	 * 
	 * @result this method will be return a BusinessErrorCode (the individual
	 *         must not appear in the response). BusinessErrorCode
	 *         <code>ERROR_001</code>
	 */
	@Test
	@Transactional
	public void provideIndividualData_provideIndividualDataByIdentifierStatutF(){
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		//TODO : create a individu and update with statut F
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("110000028901");
		request.setOption("GIN");
		
		try{
			ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode().toString(), BusinessErrorCodeEnum.ERROR_001.toString());
		} catch (SystemException e) {
			Assert.fail();
		}
	}
	
	/**
	 * Create a provide's request with a individual not exist our database
	 * @result this method will be return a BusinessErrorCode (the individual must not appear in the response).
	 *         BusinessErrorCode <code>ERROR_001</code>
	 */
	@Test
	@Transactional
	public void provideIndividualData_provideIndividualDataByIdentifierStatutFNotExist(){
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		//TODO : create a individu and update with statut F
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("000000000000");
		request.setOption("GIN");
		
		try{
			ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode().toString(), BusinessErrorCodeEnum.ERROR_001.toString());
		} catch (SystemException e) {
			Assert.fail();
		}
	}
	
	
	@Test
    @Transactional
    @Rollback(true)
	@Ignore
	//TODO reactivate when prospect preference migration done
	public void providIndividualDataByGin_prospectPreferencesExist() throws JrafDaoException, BusinessErrorBlocBusinessException, SystemException, InvalidParameterException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		// init request
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("900000008115");
		request.setOption("GIN");
		request.getScopeToProvide().add("PREFERENCE");
		
		// init data
		Date today = new Date();
		Preference pref = preferenceRepository.getOne(Long.valueOf(2));
		pref.setGin("900000008115");
		pref.setType("TPC");
		pref.setDateCreation(today);
		pref.setSignatureCreation("TEST_RI");
		pref.setSiteCreation("QVI");
		
		for (PreferenceData prefData : pref.getPreferenceData()) {
//			if (prefData.getKey().equalsIgnoreCase("TYPE")) {
//				prefData.setValue("WWP");
//			}
			if (prefData.getKey().equalsIgnoreCase("preferredAirport")) {
				prefData.setValue("NCE");
			}
		}
		
		preferenceRepository.saveAndFlush(pref);
		
		// execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		
		String preferredAirport = "";
		if (response.getPreferenceResponse() != null) {
			for (PreferenceV2 prefBloc : response.getPreferenceResponse().getPreference()) {
				if (prefBloc.getPreferenceDatas() != null) {
					for (PreferenceDataV2 prefDataResp : prefBloc.getPreferenceDatas().getPreferenceData()) {
						if (prefDataResp.getKey().equalsIgnoreCase("preferredAirport")) {
							preferredAirport = prefDataResp.getValue();
						}
					}
				}
			}
		}
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertEquals("900000008115", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertEquals("NCE", preferredAirport);
	}
	
	
	//TODO TEST to be done
	
	// Provide marketing data from BDM
	@Test
	@Ignore
	public void provideIndividualDataByGin_individualMarketingFromBDM() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		// init request
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("800007748476");
		request.setOption("GIN");
		request.getScopeToProvide().add("MARKETING");
		
		// Execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
				
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getPreferenceResponse());
		Assert.assertTrue(response.getPreferenceResponse().getPreference().size() > 1);
		
	}

	// REPIND-1019: must return individual even if GIN is not 12 char long
	@Test
	public void provideIndividualDataByGin_withNoCompleteGin() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		// init request
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("8174514");
		request.setOption("GIN");
		request.getScopeToProvide().add("ALL");
		
		// Execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
				
		Assert.assertNotNull(response);
		Assert.assertEquals("000008174514", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		
	}
	
	// Provide Travel Doc preferences from SIC DB
	@Test
	@Transactional
    @Rollback(true)
	@Ignore
	public void provideIndividualDataByGin_individualTravelDocFromSIC() throws BusinessErrorBlocBusinessException, SystemException, JrafDaoException, InvalidParameterException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		// init request
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400424668522");
		request.setOption("GIN");
		request.getScopeToProvide().add("PREFERENCE");
		
		// init data
		Date today = new Date();
		Preference pref = preferenceRepository.getOne(Long.valueOf(1));
		pref.setGin("400424668522");
		pref.setType("TDC");
		pref.setDateCreation(today);
		pref.setSignatureCreation("TEST_RI");
		pref.setSiteCreation("QVI");
		
		for (PreferenceData prefData : pref.getPreferenceData()) {
			if (prefData.getKey().equalsIgnoreCase("FIRST_NAME")) {
				prefData.setValue("test");
			}
			if (prefData.getKey().equalsIgnoreCase("COUNTRY_ISSUED")) {
				prefData.setValue("RI");
			}
		}
		
		preferenceRepository.saveAndFlush(pref);
		
		// Execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		
		String firstName = "";
		String country = "";
		
		if (response.getPreferenceResponse() != null) {
			for (PreferenceV2 prefBloc : response.getPreferenceResponse().getPreference()) {
				for (PreferenceDataV2 prefDataResp : prefBloc.getPreferenceDatas().getPreferenceData()) {
					if (prefDataResp.getKey().equalsIgnoreCase("FIRST_NAME") && prefBloc.getId().equalsIgnoreCase("1")) {
						firstName = prefDataResp.getValue();
					}
					if (prefDataResp.getKey().equalsIgnoreCase("COUNTRY_ISSUED") && prefBloc.getId().equalsIgnoreCase("1")) {
						country = prefDataResp.getValue();
					}
				}
			}
		}
		
		Assert.assertNotNull(response);
		Assert.assertEquals("test", firstName);
		Assert.assertEquals("RI", country);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void provideIndividualDataByGin_delegationDataUM() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {
				
		IndividuDTO individuDtoTut = new IndividuDTO();
		individuDtoTut = individuDS.getByGin("110000014901");
		
		IndividuDTO individuDtoUM = new IndividuDTO();
		individuDtoUM = individuDS.getByGin("000000042404");
		
		DelegationDataDTO delegationDataDTO = new DelegationDataDTO();
		delegationDataDTO.setStatus("A");
		delegationDataDTO.setType("UA");
		delegationDataDTO.setCreationDate(new Date());
		delegationDataDTO.setCreationSignature("TESTMZ");
		delegationDataDTO.setCreationSite("QVI");
		delegationDataDTO.setModificationDate(new Date());
		delegationDataDTO.setModificationSignature("TESTMZ");
		delegationDataDTO.setModificationSite("QVI");
		delegationDataDTO.setSender("DGR");
		delegationDataDTO.setDelegatorDTO(individuDtoTut);
		delegationDataDTO.setDelegateDTO(individuDtoUM);
		
		delegationDataDTO.setDelegationDataInfoDTO(new HashSet<DelegationDataInfoDTO>());
		
		/*------------ TEL 1 ------------*/
		DelegationDataInfoDTO delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(0);
		delegationDataInfoDTO.setType("TEL");
		delegationDataInfoDTO.setKey("phoneNumber");
		delegationDataInfoDTO.setValue("2549464894");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(0);
		delegationDataInfoDTO.setType("TEL");
		delegationDataInfoDTO.setKey("terminalType");
		delegationDataInfoDTO.setValue("M");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(0);
		delegationDataInfoDTO.setType("TEL");
		delegationDataInfoDTO.setKey("countryCode");
		delegationDataInfoDTO.setValue("33");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		/*------------ TEL 2 ------------*/
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(1);
		delegationDataInfoDTO.setType("TEL");
		delegationDataInfoDTO.setKey("phoneNumber");
		delegationDataInfoDTO.setValue("12123121231");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(1);
		delegationDataInfoDTO.setType("TEL");
		delegationDataInfoDTO.setKey("countryCode");
		delegationDataInfoDTO.setValue("33");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(1);
		delegationDataInfoDTO.setType("TEL");
		delegationDataInfoDTO.setKey("terminalType");
		delegationDataInfoDTO.setValue("T");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		/*------------ PAC ------------*/
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(2);
		delegationDataInfoDTO.setType("PAC");
		delegationDataInfoDTO.setKey("numberAndStreet");
		delegationDataInfoDTO.setValue("10 Rue de France");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(2);
		delegationDataInfoDTO.setType("PAC");
		delegationDataInfoDTO.setKey("additionalInformation");
		delegationDataInfoDTO.setValue("MOULIN");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(2);
		delegationDataInfoDTO.setType("PAC");
		delegationDataInfoDTO.setKey("city");
		delegationDataInfoDTO.setValue("Nice");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(2);
		delegationDataInfoDTO.setType("PAC");
		delegationDataInfoDTO.setKey("zipCode");
		delegationDataInfoDTO.setValue("06000");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(2);
		delegationDataInfoDTO.setType("PAC");
		delegationDataInfoDTO.setKey("state");
		delegationDataInfoDTO.setValue("PACA");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(2);
		delegationDataInfoDTO.setType("PAC");
		delegationDataInfoDTO.setKey("country");
		delegationDataInfoDTO.setValue("FR");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		/*------------ EMAIL ------------*/
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(3);
		delegationDataInfoDTO.setType("EMA");
		delegationDataInfoDTO.setKey("emailAddress");
		delegationDataInfoDTO.setValue("Test@gmail.com");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		/*------------ CONTRACT ------------*/
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(4);
		delegationDataInfoDTO.setType("CON");
		delegationDataInfoDTO.setKey("flyingBlueNumber");
		delegationDataInfoDTO.setValue("999999999");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(4);
		delegationDataInfoDTO.setType("CON");
		delegationDataInfoDTO.setKey("numContract");
		delegationDataInfoDTO.setValue("111111111");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(4);
		delegationDataInfoDTO.setType("CON");
		delegationDataInfoDTO.setKey("numContract");
		delegationDataInfoDTO.setValue("000000000");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		/*------------ INDIVIDU ------------*/
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(5);
		delegationDataInfoDTO.setType("IND");
		delegationDataInfoDTO.setKey("dateOfBirth");
		delegationDataInfoDTO.setValue("22/03/1987");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(5);
		delegationDataInfoDTO.setType("IND");
		delegationDataInfoDTO.setKey("spokenLanguage");
		delegationDataInfoDTO.setValue("FR");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(5);
		delegationDataInfoDTO.setType("IND");
		delegationDataInfoDTO.setKey("spokenLanguage");
		delegationDataInfoDTO.setValue("EN");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		/*------------ OTHERS ------------*/
		delegationDataInfoDTO = new DelegationDataInfoDTO();
		delegationDataInfoDTO.setTypeGroupId(6);
		delegationDataInfoDTO.setType("OTH");
		delegationDataInfoDTO.setKey("remark");
		delegationDataInfoDTO.setValue("Mange des bananes");
		delegationDataDTO.getDelegationDataInfoDTO().add(delegationDataInfoDTO);
		
		delegationDataDS.createWithLinkedData(delegationDataDTO);
		
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TESTMZ");
		
		request.setIdentificationNumber("110000014901");
		request.setOption("GIN");
		request.setRequestor(requestor);
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		
		// Execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertEquals(7, response.getDelegationDataResponse().getDelegate().get(0).getComplementaryInformation().size());
		Assert.assertNull(response.getDelegationDataResponse().getDelegate().get(0).getDelegationIndividualData().getAccountIdentifier());
		Assert.assertNull(response.getDelegationDataResponse().getDelegate().get(0).getDelegationIndividualData().getEmailIdentifier());
		Assert.assertNull(response.getDelegationDataResponse().getDelegate().get(0).getDelegationIndividualData().getFBIdentifier());
		Assert.assertEquals(0, response.getDelegationDataResponse().getDelegate().get(0).getTelecom().size());
	}
	
	@Test
	@Transactional
	public void provideIndividualData_provideIndividualDataByIdentifierCCNR16() throws BusinessErrorBlocBusinessException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("1234567890123456");
		request.setOption("AC");
		
		try{
			ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
			Assert.fail();			
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode().toString(), BusinessErrorCodeEnum.ERROR_001.toString());
		} catch (SystemException e) {
			Assert.fail();
		}
	}
	
	@Test
	@Transactional
	public void provideIndividualDataByGIN_EmailInvalid() throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		// Init request
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400518660834");
		request.setOption("GIN");
		request.getScopeToProvide().add("EMAIL");
		
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals(1, response.getEmailResponse().size());
		
		Assert.assertEquals("400518660834", response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertEquals("raymonmercy1@gmail.com", response.getEmailResponse().get(0).getEmail().getEmail());
	}	

	@Test
	@Transactional
	public void provideIndividualData_provideIndividualDataByIdentifierCCNR15() throws BusinessErrorBlocBusinessException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("123456789012345");
		request.setOption("AC");
		
		try{
			ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
			Assert.fail();			
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode().toString(), BusinessErrorCodeEnum.ERROR_001.toString());
		} catch (SystemException e) {
			Assert.fail();
		}
	}


	@Test
	public void provideIndividualDataByGin_TelecomFlag() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TESTMZ");
		
		request.setIdentificationNumber("110000014901");
		request.setOption("GIN");
		request.setRequestor(requestor);
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		
		// Execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getEmailResponse());
		Assert.assertEquals(1, response.getEmailResponse().size());
		
		Assert.assertEquals("110000014901", response.getIndividualResponse().getIndividualInformations().getIdentifier());
	}

	// REPIND-1287 : Check Rules for flag "isNoValidNormalizedTelecom" : 
	
	@Test
	public void provideIndividualDataByGin_isNoValidNormalizedTelecom_TelValidNormalized() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TESTMZ");
		
		request.setIdentificationNumber("400363138323");
		request.setOption("GIN");
		request.setRequestor(requestor);
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		
		// Execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getTelecomResponse());
		Assert.assertEquals(1, response.getTelecomResponse().size());
		Assert.assertEquals(false, response.getTelecomResponse().get(0).getTelecomFlags().isFlagNoValidNormalizedTelecom());
		// Telecom is a valid normalized telecom
	}

	@Test
	public void provideIndividualDataByGin_isNoValidNormalizedTelecom_TelValidNotNormalized() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TESTMZ");
		
		request.setIdentificationNumber("400450460125");
		request.setOption("GIN");
		request.setRequestor(requestor);
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		
		// Execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getTelecomResponse());
		Assert.assertEquals(1, response.getTelecomResponse().size());
		Assert.assertEquals(true, response.getTelecomResponse().get(0).getTelecomFlags().isFlagNoValidNormalizedTelecom());
		// Telecom is not a valid normalized telecom
	}

	@Test
	public void provideIndividualDataByGin_isNoValidNormalizedTelecom_TelNotValidNotNormalized() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TESTMZ");
		
		request.setIdentificationNumber("400363158785");
		request.setOption("GIN");
		request.setRequestor(requestor);
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		
		// Execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getTelecomResponse());
		Assert.assertEquals(1, response.getTelecomResponse().size());
		Assert.assertEquals(true, response.getTelecomResponse().get(0).getTelecomFlags().isFlagNoValidNormalizedTelecom());		
		// Telecom is not valid so not normalized telecom
	}	

	@Test
	public void provideIndividualDataByGin_isNoValidNormalizedTelecom_TelNotValidNotNormalized_KO() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TESTMZ");
		
		request.setIdentificationNumber("400363009033");
		request.setOption("GIN");
		request.setRequestor(requestor);
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		
		// Execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse());
		Assert.assertNotNull(response.getTelecomResponse());
		Assert.assertEquals(2, response.getTelecomResponse().size());
		
		for (TelecomResponse tel : response.getTelecomResponse()) { 
			if (tel.getTelecom().getMediumStatus().equals("V")) {
				Assert.assertEquals(false, tel.getTelecomFlags().isFlagNoValidNormalizedTelecom());
			} else {
				Assert.assertEquals(true, tel.getTelecomFlags().isFlagNoValidNormalizedTelecom());
			}
		}
		// Telecom is not valid so not normalized telecom
	}	
	
	@Test
	public void provideIndividualDataByGin_REPIND_PP_UTF_inError() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException, UtfException {

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TESTMZ");
		
		request.setIdentificationNumber("400319584754");
		request.setOption("GIN");
		request.setRequestor(requestor);
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		
		PaymentPreferencesDataHelper paymentPreferencesDataHelperMock = Mockito.mock(PaymentPreferencesDataHelper.class);
		provideIndividualDataV7Impl.setPaymentPreferencesDataHelperV5(paymentPreferencesDataHelperMock);
		Mockito.doThrow(new JrafDomainException("INTERNAL ERROR")).when(paymentPreferencesDataHelperMock).provideMaskedPaymentPreferences(Mockito.anyString());
		UtfHelper utfHelperMock = Mockito.mock(UtfHelper.class);
		provideIndividualDataV7Impl.setUtfHelper(utfHelperMock);
		Mockito.doThrow(new JrafDomainException("INTERNAL ERROR")).when(utfHelperMock).getByGin(Mockito.anyString());
		
		// Execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertTrue(response.getAccountDataResponse().getAccountData().getPercentageFullProfil() <= 75);
		Assert.assertNull(response.getUtfResponse());
	}

	@Test
	@Transactional
	public void provideIndividualData_ForMergeWithWarning() throws BusinessErrorBlocBusinessException, SystemException{
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("400476649505");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getWarningResponse());		
		Assert.assertNotNull(response.getWarningResponse().get(0));
		Assert.assertNotNull(response.getWarningResponse().get(0).getWarning());
		Assert.assertEquals("INDIVIDUAL MERGED", response.getWarningResponse().get(0).getWarning().getWarningCode());
		Assert.assertEquals("400478201136", response.getWarningResponse().get(0).getWarning().getWarningDetails());
	}

	@Test
	@Transactional
	public void provideIndividualData_ForMergeWithWarningWithoutAccountData()
			throws BusinessErrorBlocBusinessException, SystemException {
		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		request.setRequestor(initRequestor());
		request.setIdentificationNumber("110000012801");
		request.setOption("GIN");
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());

		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl
				.provideIndividualDataByIdentifier(request);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getWarningResponse());
		Assert.assertNotNull(response.getWarningResponse().get(0));
		Assert.assertNotNull(response.getWarningResponse().get(0).getWarning());
		Assert.assertEquals("INDIVIDUAL MERGED", response.getWarningResponse().get(0).getWarning().getWarningCode());
		Assert.assertEquals("400003729376", response.getWarningResponse().get(0).getWarning().getWarningDetails());
	}
	
	@Test
	public void provideIndividualDataByGin_PostalUsage() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TESTMZ");
		
		request.setIdentificationNumber("110000014901");
		request.setOption("GIN");
		request.setRequestor(requestor);
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		
		// Execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getPostalAddressResponse());		
		Assert.assertNotNull(response.getPostalAddressResponse().get(0).getUsageAddress()); //ISI
	}
	
	@Test
	public void provideIndividualDataByGin_CallersWithTelecom() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TESTMZ");
		
		request.setIdentificationNumber("960000000222");
		request.setOption("GIN");
		request.setRequestor(requestor);
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		
		// Execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertNotNull(response.getTelecomResponse());
		Assert.assertEquals(1, response.getTelecomResponse().size());
	}
	
	@Test
	public void provideIndividualDataByGin_CallersWithoutTelecom() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();
		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TESTMZ");
		
		request.setIdentificationNumber("960000000174");
		request.setOption("GIN");
		request.setRequestor(requestor);
		request.getScopeToProvide().add(ScopesToProvideEnum.ALL.toString());
		
		// Execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertEquals(0, response.getTelecomResponse().size());
	}


	@Test
	public void provideIndividualDataByGin_CallersWithType_D() throws JrafDomainException, BusinessErrorBlocBusinessException, SystemException {

		ProvideIndividualInformationRequest request = new ProvideIndividualInformationRequest();

		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		requestor.setApplicationCode("ISI");
		requestor.setSite("QVI");
		requestor.setSignature("TESTMZ");

		request.setIdentificationNumber("960000000174");
		request.setOption("GIN");
		request.setRequestor(requestor);
		request.getScopeToProvide().add(ScopesToProvideEnum.CONTRACT.toString());

		//create businessRole doctor
		BusinessRole role = createRoleD();
		ContractResponse expectedRoleDoctorResponse = preparedBusinessRoleDoctor(role);

		// Execute request and test
		ProvideIndividualInformationResponse response = provideIndividualDataV7Impl.provideIndividualDataByIdentifier(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getIndividualResponse().getIndividualInformations().getIdentifier());
		Assert.assertEquals(expectedRoleDoctorResponse.getContract().getContractNumber(), response.getContractResponse().get(0).getContract().getContractNumber());
		Assert.assertEquals(expectedRoleDoctorResponse.getContract().getContractType(), response.getContractResponse().get(0).getContract().getContractType());
		Assert.assertEquals(RoleDoctorEnum.DR.getCode(), response.getContractResponse().get(0).getContract().getProductType());
		Assert.assertEquals("C", response.getContractResponse().get(0).getContract().getContractStatus());
		Assert.assertEquals(expectedRoleDoctorResponse.getSignature().get(0).getSignature(), response.getContractResponse().get(0).getSignature().get(0).getSignature());
		Assert.assertEquals(expectedRoleDoctorResponse.getSignature().get(0).getSignatureSite(), response.getContractResponse().get(0).getSignature().get(0).getSignatureSite());
		Assert.assertEquals(expectedRoleDoctorResponse.getSignature().get(0).getSignatureType(), response.getContractResponse().get(0).getSignature().get(0).getSignatureType());

		//delete role
		deleteBusinessRole(role);
	}



	public BusinessRole createRoleD(){
		BusinessRole businessRole = new BusinessRole();
		businessRole.setGinInd("960000000174");
		businessRole.setType("D");
		businessRole.setNumeroContrat("123456789");
		businessRole.setSignatureCreation("REPIND");
		businessRole.setSiteCreation("REPIND");
		businessRole.setDateCreation(new Date());
		businessRoleRepository.save(businessRole);

		return businessRole;

	}

	public ContractResponse preparedBusinessRoleDoctor(BusinessRole businessRole) {

		Signature signature = new Signature();
		signature.setSignature(businessRole.getSignatureCreation());
		signature.setSignatureSite(businessRole.getSiteCreation());
		signature.setDate(businessRole.getDateCreation());
		signature.setSignatureType(SignatureTypeEnum.CREATION.toString());


		ContractV2 contractV2 = new ContractV2();
		contractV2.setContractNumber(businessRole.getNumeroContrat());
		contractV2.setContractType(businessRole.getType());

		ContractResponse contractResponse = new ContractResponse();
		contractResponse.setContract(contractV2);
		contractResponse.getSignature().add(signature);

		return contractResponse;
	}

	public void deleteBusinessRole(BusinessRole businessRole){
		businessRoleRepository.deleteByGinIndAndType(businessRole.getGinInd(), businessRole.getType());
	}


}
