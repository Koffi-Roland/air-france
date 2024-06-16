package com.afklm.repind.v7.createorupdateindividual.ut.helper;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v7.createorupdateindividualws.helpers.CommunicationPreferencesHelper;
import com.afklm.repind.v7.createorupdateindividualws.helpers.CreateOrUpdateProspectHelper;
import com.afklm.soa.stubs.w000442.v7.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v7.request.*;
import com.afklm.soa.stubs.w000442.v7.response.InformationResponse;
import com.afklm.soa.stubs.w000442.v7.siccommonenum.LanguageCodesEnum;
import com.afklm.soa.stubs.w000442.v7.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.*;
import com.airfrance.ref.exception.ReconciliationProcessException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CreateOrUpdateProspectHelperTest extends CreateOrUpdateProspectHelper {

    @Autowired
    private IndividuRepository individuRepository;

    @Autowired
    private CreateOrUpdateProspectHelper createOrUpdateProspectHelper;


    private static final Log logger = LogFactory.getLog(CreateOrUpdateProspectHelperTest.class);

    // Initialization : done before each test case annotated @Test
    @Before
    public void setUp() throws JrafDomainException {
        individuDS = EasyMock.createMock(IndividuDS.class);
        myAccountDS = EasyMock.createMock(MyAccountDS.class);
        telecomDS = EasyMock.createMock(TelecomDS.class);
        communicationPreferencesHelper = EasyMock.createNiceMock(CommunicationPreferencesHelper.class);
    }

    @Test
    public void testCreateOrUpdateProspectHelperFindIndividual() throws JrafDomainException, BusinessErrorBlocBusinessException {

        logger.info("Start test");

        String mail = generateEmail();

        List<ProspectIndividuDTO> prospectIndDTOList = new ArrayList<ProspectIndividuDTO>();

        ProspectIndividuDTO prospectIndDTO = createProspectIndividuDTO(mail, "400518839290", "contractNumberTest");

        prospectIndDTOList.add(prospectIndDTO);

        EasyMock.expect(myAccountDS.findAnIndividualByEmail(mail)).andReturn(prospectIndDTOList);
        EasyMock.replay(myAccountDS);

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        RequestorV2 requestorV2 = new RequestorV2();
        request.setRequestor(requestorV2);
        EmailRequest emailRequest = new EmailRequest();
        Email emailProspect = new Email();
        emailProspect.setEmail(mail);
        emailRequest.setEmail(emailProspect);
        request.getEmailRequest().add(emailRequest);

        ProspectIndividuDTO returnProspectInd = findIndividual(request);
        Assert.assertNotNull(returnProspectInd);
        Assert.assertEquals(prospectIndDTO.getCin(), returnProspectInd.getCin());
        Assert.assertEquals(prospectIndDTO.getGin(), returnProspectInd.getGin());
        Assert.assertEquals(mail, returnProspectInd.getEmail());

        logger.info("End test");

    }

    @Test
    public void testCreateOrUpdateProspectHelperFindIndividualReconciliationByGin() throws JrafDomainException, BusinessErrorBlocBusinessException {

        logger.info("Start test");

        String mail = generateEmail();

        List<ProspectIndividuDTO> prospectIndDTOList = new ArrayList<ProspectIndividuDTO>();

        ProspectIndividuDTO prospectIndDTO = createProspectIndividuDTO(mail, "400518839290", "contractNumberTest");
        ProspectIndividuDTO prospectIndDTO2 = createProspectIndividuDTO(mail, "400518839111", "contractNumberTest2");
        ProspectIndividuDTO prospectIndDTO3 = createProspectIndividuDTO(mail, "400518839112", "contractNumberTest3");
        ProspectIndividuDTO prospectIndDTO4 = createProspectIndividuDTO(mail, "400518839113", "contractNumberTest4");

        prospectIndDTOList.add(prospectIndDTO);
        prospectIndDTOList.add(prospectIndDTO2);
        prospectIndDTOList.add(prospectIndDTO3);
        prospectIndDTOList.add(prospectIndDTO4);

        EasyMock.expect(myAccountDS.findAnIndividualByEmail(mail)).andReturn(prospectIndDTOList);
        EasyMock.replay(myAccountDS);

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        RequestorV2 requestorV2 = new RequestorV2();
        requestorV2.setLoggedGin(prospectIndDTO3.getGin());
        request.setRequestor(requestorV2);
        EmailRequest emailRequest = new EmailRequest();
        Email emailProspect = new Email();
        emailProspect.setEmail(mail);
        emailRequest.setEmail(emailProspect);
        request.getEmailRequest().add(emailRequest);

        ProspectIndividuDTO returnProspectInd = findIndividual(request);
        Assert.assertNotNull(returnProspectInd);
        Assert.assertEquals(prospectIndDTO3.getCin(), returnProspectInd.getCin());
        Assert.assertEquals(prospectIndDTO3.getGin(), returnProspectInd.getGin());
        Assert.assertEquals(mail, returnProspectInd.getEmail());

        logger.info("End test");

    }


    @Test
    public void testCreateOrUpdateProspectHelperFindIndividualReconciliationByCin() throws JrafDomainException, BusinessErrorBlocBusinessException {

        logger.info("Start test");

        String mail = generateEmail();

        List<ProspectIndividuDTO> prospectIndDTOList = new ArrayList<ProspectIndividuDTO>();

        ProspectIndividuDTO prospectIndDTO = createProspectIndividuDTO(mail, "400518839290", "contractNumberTest");
        ProspectIndividuDTO prospectIndDTO2 = createProspectIndividuDTO(mail, "400518839111", "contractNumberTest2");
        ProspectIndividuDTO prospectIndDTO3 = createProspectIndividuDTO(mail, "400518839112", "contractNumberTest3");
        ProspectIndividuDTO prospectIndDTO4 = createProspectIndividuDTO(mail, "400518839113", "contractNumberTest4");

        prospectIndDTOList.add(prospectIndDTO);
        prospectIndDTOList.add(prospectIndDTO2);
        prospectIndDTOList.add(prospectIndDTO3);
        prospectIndDTOList.add(prospectIndDTO4);

        EasyMock.expect(myAccountDS.findAnIndividualByEmail(mail)).andReturn(prospectIndDTOList);
        EasyMock.replay(myAccountDS);

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        RequestorV2 requestorV2 = new RequestorV2();
        requestorV2.setReconciliationDataCIN(prospectIndDTO3.getCin());
        request.setRequestor(requestorV2);
        EmailRequest emailRequest = new EmailRequest();
        Email emailProspect = new Email();
        emailProspect.setEmail(mail);
        emailRequest.setEmail(emailProspect);
        request.getEmailRequest().add(emailRequest);

        ProspectIndividuDTO returnProspectInd = findIndividual(request);
        Assert.assertNotNull(returnProspectInd);
        Assert.assertEquals(prospectIndDTO3.getCin(), returnProspectInd.getCin());
        Assert.assertEquals(prospectIndDTO3.getGin(), returnProspectInd.getGin());
        Assert.assertEquals(mail, returnProspectInd.getEmail());

        logger.info("End test");

    }

    @Test
    public void testCreateOrUpdateProspectHelperFindIndividualReconciliationProcessException() throws BusinessErrorBlocBusinessException, JrafDomainException {

        logger.info("Start test");

        String mail = generateEmail();

        List<ProspectIndividuDTO> prospectIndDTOList = new ArrayList<ProspectIndividuDTO>();

        ProspectIndividuDTO prospectIndDTO = createProspectIndividuDTO(mail, "400518839290", "contractNumberTest");
        ProspectIndividuDTO prospectIndDTO2 = createProspectIndividuDTO(mail, "400518839111", "contractNumberTest2");
        ProspectIndividuDTO prospectIndDTO3 = createProspectIndividuDTO(mail, "400518839112", "contractNumberTest3");

        prospectIndDTOList.add(prospectIndDTO);
        prospectIndDTOList.add(prospectIndDTO2);
        prospectIndDTOList.add(prospectIndDTO3);

        EasyMock.expect(myAccountDS.findAnIndividualByEmail(mail)).andReturn(prospectIndDTOList);
        EasyMock.replay(myAccountDS);

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        RequestorV2 requestorV2 = new RequestorV2();
        requestorV2.setContext("B2C_HOME_PAGE");
        request.setRequestor(requestorV2);
        EmailRequest emailRequest = new EmailRequest();
        Email emailProspect = new Email();
        emailProspect.setEmail(mail);
        emailRequest.setEmail(emailProspect);
        request.getEmailRequest().add(emailRequest);

        boolean reconciliationException = false;
        try {
            findIndividual(request);
        } catch (ReconciliationProcessException e) {
            reconciliationException = true;
        }

        Assert.assertTrue(reconciliationException);

        logger.info("End test");

    }

    @Test
    public void testCreateOrUpdateProspectHelperUpdateIndividualProspect() throws BusinessErrorBlocBusinessException, JrafDomainException {

        logger.info("Start test");

        String mail = generateEmail();

        List<ProspectIndividuDTO> prospectIndDTOList = new ArrayList<ProspectIndividuDTO>();

        ProspectIndividuDTO prospectIndDTO = createProspectIndividuDTO(mail, "400518839290", "contractNumberTest");

        prospectIndDTOList.add(prospectIndDTO);

        EasyMock.expect(myAccountDS.findAnIndividualByEmail(mail)).andReturn(prospectIndDTOList);
        EasyMock.replay(myAccountDS);

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        RequestorV2 requestorV2 = new RequestorV2();
        requestorV2.setContext("B2C_HOME_PAGE");
        request.setRequestor(requestorV2);

        IndividualRequest indRequest = new IndividualRequest();
        IndividualInformationsV3 indInformations = new IndividualInformationsV3();
        indInformations.setNationality("FR");
        indInformations.setSecondNationality("EN");
        indInformations.setMiddleNameSC("TestName");

        IndividualProfilV3 indProfil = new IndividualProfilV3();
        indProfil.setLanguageCode("FR");
        indRequest.setIndividualProfil(indProfil);
        indRequest.setIndividualInformations(indInformations);
        request.setIndividualRequest(indRequest);

        ComunicationPreferencesRequest comPrefRequest = new ComunicationPreferencesRequest();

        Media media = new Media();
        media.setMedia1("E");

        CommunicationPreferences comPref = new CommunicationPreferences();
        comPref.setCommunicationGroupeType("N");
        comPref.setCommunicationType("AF");
        comPref.setDomain("S");
        comPref.setMedia(media);
        comPref.setOptIn("Y");
        MarketLanguage ml = new MarketLanguage();
        ml.setMarket("FR");
        ml.setLanguage(LanguageCodesEnum.FR);
        ml.setMedia(media);
        ml.setOptIn("Y");

        comPref.getMarketLanguage().add(ml);

        comPrefRequest.setCommunicationPreferences(comPref);

        request.getComunicationPreferencesRequest().add(comPrefRequest);

        TelecomRequest telecomRequest = new TelecomRequest();
        Telecom telecom = new Telecom();
        telecom.setCountryCode("FR");
        telecom.setTerminalType("T");
        telecom.setMediumCode("D");
        telecom.setMediumStatus("V");
        telecom.setPhoneNumber("0658794565");
        telecomRequest.setTelecom(telecom);
        request.getTelecomRequest().add(telecomRequest);

        PreferenceDataRequest prefRequest = new PreferenceDataRequest();
        Preference pref = new Preference();
        pref.setTypePreference("WW");
        PreferenceData prefData = new PreferenceData();
        prefData.setKey("PA");
        prefData.setValue("NCE");
        pref.getPreferenceData().add(prefData);
        prefRequest.getPreference().add(pref);

        request.setPreferenceDataRequest(prefRequest);
        EmailRequest emailRequest = new EmailRequest();
        Email emailProspect = new Email();
        emailProspect.setEmail(mail);
        emailRequest.setEmail(emailProspect);
        request.getEmailRequest().add(emailRequest);

        CreateUpdateIndividualRequest createUpdateIndRequest = updateIndividualProspect(prospectIndDTO, request);

        // Test requestor
        Assert.assertEquals("B2C", createUpdateIndRequest.getRequestor().getApplicationCode());
        Assert.assertEquals("B2C", createUpdateIndRequest.getRequestor().getChannel());

        // Test IndividualRequest
        Assert.assertEquals("FR", createUpdateIndRequest.getIndividualRequest().getIndividualInformations().getNationality());
        Assert.assertEquals("EN", createUpdateIndRequest.getIndividualRequest().getIndividualInformations().getSecondNationality());
        Assert.assertEquals("TestName", createUpdateIndRequest.getIndividualRequest().getIndividualInformations().getSecondFirstName());
        Assert.assertEquals("400518839290", createUpdateIndRequest.getIndividualRequest().getIndividualInformations().getIdentifier());
        Assert.assertEquals("FR", createUpdateIndRequest.getIndividualRequest().getIndividualInformations().getLanguageCode());

        // Test ComPrefRequest
        Assert.assertEquals("AF", createUpdateIndRequest.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getCommunicationType());
        Assert.assertEquals("S", createUpdateIndRequest.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getDomain());
        Assert.assertEquals("FR", createUpdateIndRequest.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage().get(0).getMarket());

        // Test TelecomRequest
        Assert.assertEquals("0658794565", createUpdateIndRequest.getTelecomRequest().get(0).getTelecom().getPhoneNumber());

        // Test PreferenceRequest
        Assert.assertEquals("NCE", createUpdateIndRequest.getPreferenceDataRequest().getPreference().get(0).getPreferenceData().get(0).getValue());

        logger.info("End test");

    }


    @Test
    @Rollback(true)
    public void testCreateOrUpdateProspectHelperUpdateProspectCode4() throws BusinessErrorBlocBusinessException, JrafDomainException {

        logger.info("Start test");

        Individu individuForTest = individuRepository.findBySgin("900045821344");
        individuForTest.getCommunicationpreferences().clear();

        com.airfrance.repind.entity.individu.CommunicationPreferences communicationPreferencesBO = new com.airfrance.repind.entity.individu.CommunicationPreferences();
        communicationPreferencesBO.setChannel("B2C");
        communicationPreferencesBO.setComGroupType("N");
        communicationPreferencesBO.setComType("AF");
        communicationPreferencesBO.setCreationDate(new Date());
        communicationPreferencesBO.setCreationSignature("TESTRI");
        communicationPreferencesBO.setCreationSite("QVI");
        communicationPreferencesBO.setDateOptin(new Date());
        communicationPreferencesBO.setDomain("S");
        communicationPreferencesBO.setGin("900045821344");
        communicationPreferencesBO.setSubscribe("Y");
        communicationPreferencesBO.setMedia1("E");

        com.airfrance.repind.entity.individu.MarketLanguage marketLanguageBO = new com.airfrance.repind.entity.individu.MarketLanguage();
        marketLanguageBO.setCommunicationMedia1("E");
        marketLanguageBO.setCreationDate(new Date());
        marketLanguageBO.setCreationSignature("TESTRI");
        marketLanguageBO.setCreationSite("QVI");
        marketLanguageBO.setDateOfConsent(new Date());
        marketLanguageBO.setLanguage("FR");
        marketLanguageBO.setMarket("FR");
        marketLanguageBO.setOptIn("Y");
        communicationPreferencesBO.setMarketLanguage(new HashSet<com.airfrance.repind.entity.individu.MarketLanguage>());
        communicationPreferencesBO.getMarketLanguage().add(marketLanguageBO);

        individuForTest.getCommunicationpreferences().add(communicationPreferencesBO);
        individuForTest = individuRepository.saveAndFlush(individuForTest);

        individuForTest = individuRepository.findBySgin("900045821344");

        IndividuDTO updateProspectDTO = new IndividuDTO();
        IndividuDTO findProspectDTO = IndividuTransform.bo2Dto(individuForTest);

        List<CommunicationPreferencesDTO> comPrefDTO = new ArrayList<CommunicationPreferencesDTO>();

        updateProspectDTO.setCommunicationpreferencesdto(comPrefDTO);

        CreateUpdateIndividualRequest requestDTO = new CreateUpdateIndividualRequest();
        RequestorV2 requestorDTO = new RequestorV2();
        requestorDTO.setApplicationCode("ISI");
        requestorDTO.setChannel("B2C");
        requestorDTO.setSignature("TESTRI");
        requestorDTO.setSite("QVI");

        requestDTO.setRequestor(requestorDTO);

        requestDTO.setUpdateCommunicationPrefMode("U");

        CreateUpdateIndividualResponse responseDTO = new CreateUpdateIndividualResponse();

        List<CommunicationPreferencesDTO> listComPref = new ArrayList<CommunicationPreferencesDTO>();
        CommunicationPreferencesDTO comPref = new CommunicationPreferencesDTO();
        comPref.setComType("AF");
        comPref.setComGroupType("N");
        comPref.setDomain("S");
        comPref.setMedia1("E");
        comPref.setSubscribe("Y");

        Set<MarketLanguageDTO> setMarketLanguage = new HashSet<MarketLanguageDTO>();
        MarketLanguageDTO marketLanguage = new MarketLanguageDTO();
        marketLanguage.setMarket("FR");
        marketLanguage.setLanguage("FR");
        marketLanguage.setOptIn("Y");
        marketLanguage.setMedia1("E");

        setMarketLanguage.add(marketLanguage);
        comPref.setMarketLanguageDTO(setMarketLanguage);
        listComPref.add(comPref);
        updateProspectDTO.setCommunicationpreferencesdto(listComPref);

        createOrUpdateProspectHelper.updateProspect(updateProspectDTO, findProspectDTO, responseDTO, requestDTO);

        for (InformationResponse ir : responseDTO.getInformationResponse()) {
            Assert.assertEquals("4", ir.getInformation().getInformationCode());
        }

        logger.info("End test");

    }


    @Test
    @Rollback(true)
    public void testCreateOrUpdateProspectHelperUpdateProspectCode1() throws BusinessErrorBlocBusinessException, JrafDomainException {

        logger.info("Start test");

        Individu individuForTest = individuRepository.findBySgin("900045821344");
        individuForTest.getCommunicationpreferences().clear();
        individuForTest = individuRepository.saveAndFlush(individuForTest);

        IndividuDTO updateProspectDTO = new IndividuDTO();
        IndividuDTO findProspectDTO = IndividuTransform.bo2Dto(individuForTest);

        List<CommunicationPreferencesDTO> comPrefDTO = new ArrayList<CommunicationPreferencesDTO>();

        updateProspectDTO.setCommunicationpreferencesdto(comPrefDTO);

        CreateUpdateIndividualRequest requestDTO = new CreateUpdateIndividualRequest();
        RequestorV2 requestorDTO = new RequestorV2();
        requestorDTO.setApplicationCode("ISI");
        requestorDTO.setChannel("B2C");
        requestorDTO.setSignature("TESTRI");
        requestorDTO.setSite("QVI");

        requestDTO.setRequestor(requestorDTO);

        CreateUpdateIndividualResponse responseDTO = new CreateUpdateIndividualResponse();

        List<CommunicationPreferencesDTO> listComPref = new ArrayList<CommunicationPreferencesDTO>();
        CommunicationPreferencesDTO comPref = new CommunicationPreferencesDTO();
        comPref.setComType("AF");
        comPref.setComGroupType("N");
        comPref.setDomain("S");
        comPref.setMedia1("E");
        comPref.setSubscribe("Y");

        Set<MarketLanguageDTO> setMarketLanguage = new HashSet<MarketLanguageDTO>();
        MarketLanguageDTO marketLanguage = new MarketLanguageDTO();
        marketLanguage.setMarket("FR");
        marketLanguage.setLanguage("FR");
        marketLanguage.setOptIn("Y");
        marketLanguage.setMedia1("E");
        marketLanguage.setCreationSignature("signature");
        marketLanguage.setCreationSite("site");

        setMarketLanguage.add(marketLanguage);
        comPref.setMarketLanguageDTO(setMarketLanguage);
        listComPref.add(comPref);
        updateProspectDTO.setCommunicationpreferencesdto(listComPref);

        createOrUpdateProspectHelper.updateProspect(updateProspectDTO, findProspectDTO, responseDTO, requestDTO);

        for (InformationResponse ir : responseDTO.getInformationResponse()) {
            Assert.assertEquals("1", ir.getInformation().getInformationCode());
        }

        logger.info("End test");

    }

    @Test
    @Rollback(true)
    public void testCreateOrUpdateProspectHelperUpdateProspectCode7() throws BusinessErrorBlocBusinessException, JrafDomainException {

        logger.info("Start test");

        Individu individuForTest = individuRepository.findBySgin("900045821344");
        individuForTest.getCommunicationpreferences().clear();

        com.airfrance.repind.entity.individu.CommunicationPreferences communicationPreferencesBO = new com.airfrance.repind.entity.individu.CommunicationPreferences();
        communicationPreferencesBO.setChannel("B2C");
        communicationPreferencesBO.setComGroupType("N");
        communicationPreferencesBO.setComType("AF");
        communicationPreferencesBO.setCreationDate(new Date());
        communicationPreferencesBO.setCreationSignature("TESTRI");
        communicationPreferencesBO.setCreationSite("QVI");
        communicationPreferencesBO.setDateOptin(new Date());
        communicationPreferencesBO.setDomain("S");
        communicationPreferencesBO.setGin("900045821344");
        communicationPreferencesBO.setSubscribe("N");
        communicationPreferencesBO.setMedia1("E");

        com.airfrance.repind.entity.individu.MarketLanguage marketLanguageBO = new com.airfrance.repind.entity.individu.MarketLanguage();
        marketLanguageBO.setCommunicationMedia1("E");
        marketLanguageBO.setCreationDate(new Date());
        marketLanguageBO.setCreationSignature("TESTRI");
        marketLanguageBO.setCreationSite("QVI");
        marketLanguageBO.setDateOfConsent(new Date());
        marketLanguageBO.setLanguage("FR");
        marketLanguageBO.setMarket("FR");
        marketLanguageBO.setOptIn("N");
        communicationPreferencesBO.setMarketLanguage(new HashSet<com.airfrance.repind.entity.individu.MarketLanguage>());
        communicationPreferencesBO.getMarketLanguage().add(marketLanguageBO);

        individuForTest.getCommunicationpreferences().add(communicationPreferencesBO);
        individuForTest = individuRepository.saveAndFlush(individuForTest);

        individuForTest = individuRepository.findBySgin("900045821344");

        IndividuDTO updateProspectDTO = new IndividuDTO();
        IndividuDTO findProspectDTO = IndividuTransform.bo2Dto(individuForTest);

        List<CommunicationPreferencesDTO> comPrefDTO = new ArrayList<CommunicationPreferencesDTO>();

        updateProspectDTO.setCommunicationpreferencesdto(comPrefDTO);

        CreateUpdateIndividualRequest requestDTO = new CreateUpdateIndividualRequest();
        RequestorV2 requestorDTO = new RequestorV2();
        requestorDTO.setApplicationCode("ISI");
        requestorDTO.setChannel("B2C");
        requestorDTO.setSignature("TESTRI");
        requestorDTO.setSite("QVI");

        requestDTO.setRequestor(requestorDTO);

        requestDTO.setUpdateCommunicationPrefMode("U");

        CreateUpdateIndividualResponse responseDTO = new CreateUpdateIndividualResponse();

        List<CommunicationPreferencesDTO> listComPref = new ArrayList<CommunicationPreferencesDTO>();
        CommunicationPreferencesDTO comPref = new CommunicationPreferencesDTO();
        comPref.setComType("AF");
        comPref.setComGroupType("N");
        comPref.setDomain("S");
        comPref.setMedia1("E");
        comPref.setSubscribe("N");

        Set<MarketLanguageDTO> setMarketLanguage = new HashSet<MarketLanguageDTO>();
        MarketLanguageDTO marketLanguage = new MarketLanguageDTO();
        marketLanguage.setMarket("FR");
        marketLanguage.setLanguage("FR");
        marketLanguage.setOptIn("N");
        marketLanguage.setMedia1("E");

        setMarketLanguage.add(marketLanguage);
        comPref.setMarketLanguageDTO(setMarketLanguage);
        listComPref.add(comPref);
        updateProspectDTO.setCommunicationpreferencesdto(listComPref);

        createOrUpdateProspectHelper.updateProspect(updateProspectDTO, findProspectDTO, responseDTO, requestDTO);

        for (InformationResponse ir : responseDTO.getInformationResponse()) {
            Assert.assertEquals("7", ir.getInformation().getInformationCode());
        }

        logger.info("End test");

    }


    @Test
    public void testCreateOrUpdateProspectHelperSearchProspectByEmailWithLastnameOnly() throws JrafDomainException, BusinessErrorBlocBusinessException {

        logger.info("Start test");

        List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
        listInd.add(createGenericIndForFilter("test1", "test1"));
        listInd.add(createGenericIndForFilter("test2", "test2"));
        listInd.add(createGenericIndForFilter(null, null));
        listInd.add(createGenericIndForFilter("test1", null));

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        IndividualRequest individualRequest = new IndividualRequest();
        IndividualInformationsV3 individualInformations = new IndividualInformationsV3();
        individualInformations.setLastNameSC("test1");

        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();
        email.setEmail("test@test.fr");
        emailRequest.setEmail(email);
        request.getEmailRequest().add(emailRequest);

        individualRequest.setIndividualInformations(individualInformations);
        request.setIndividualRequest(individualRequest);

        EasyMock.expect(individuDS.findProspectByEmail("test@test.fr")).andReturn(listInd);
        EasyMock.replay(individuDS);

        IndividuDTO indResponse = searchProspectByEmail(request);
        Assert.assertEquals("test1", indResponse.getNom());
        Assert.assertNull(indResponse.getPrenom());

        logger.info("End test");

    }


    @Test
    public void testCreateOrUpdateProspectHelperSearchProspectByEmailWithoutName() throws JrafDomainException, BusinessErrorBlocBusinessException {

        logger.info("Start test");

        List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
        listInd.add(createGenericIndForFilter("test1", "test1"));
        listInd.add(createGenericIndForFilter("test2", "test2"));
        listInd.add(createGenericIndForFilter(null, null));
        listInd.add(createGenericIndForFilter("test1", null));

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        IndividualRequest individualRequest = new IndividualRequest();
        IndividualInformationsV3 individualInformations = new IndividualInformationsV3();

        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();
        email.setEmail("test@test.fr");
        emailRequest.setEmail(email);
        request.getEmailRequest().add(emailRequest);

        individualRequest.setIndividualInformations(individualInformations);
        request.setIndividualRequest(individualRequest);

        EasyMock.expect(individuDS.findProspectByEmail("test@test.fr")).andReturn(listInd);
        EasyMock.replay(individuDS);

        IndividuDTO indResponse = searchProspectByEmail(request);
        Assert.assertNull(indResponse.getNom());
        Assert.assertNull(indResponse.getPrenom());

        logger.info("End test");

    }

    @Test
    public void testCreateOrUpdateProspectHelperSearchProspectByEmailWithFirstnameAndLastname() throws JrafDomainException, BusinessErrorBlocBusinessException {

        logger.info("Start test");

        List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
        listInd.add(createGenericIndForFilter("test1", "test1"));
        listInd.add(createGenericIndForFilter("test2", "test2"));
        listInd.add(createGenericIndForFilter(null, null));
        listInd.add(createGenericIndForFilter("test1", null));

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        IndividualRequest individualRequest = new IndividualRequest();
        IndividualInformationsV3 individualInformations = new IndividualInformationsV3();
        individualInformations.setLastNameSC("test1");
        individualInformations.setFirstNameSC("test1");

        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();
        email.setEmail("test@test.fr");
        emailRequest.setEmail(email);
        request.getEmailRequest().add(emailRequest);

        individualRequest.setIndividualInformations(individualInformations);
        request.setIndividualRequest(individualRequest);

        EasyMock.expect(individuDS.findProspectByEmail("test@test.fr")).andReturn(listInd);
        EasyMock.replay(individuDS);

        IndividuDTO indResponse = searchProspectByEmail(request);
        Assert.assertEquals("test1", indResponse.getNom());
        Assert.assertEquals("test1", indResponse.getPrenom());

        logger.info("End test");

    }

    @Test
    public void testCreateOrUpdateProspectHelperSearchProspectByEmailWithOnlyOneIndividual() throws JrafDomainException, BusinessErrorBlocBusinessException {

        logger.info("Start test");

        List<IndividuDTO> listInd = new ArrayList<IndividuDTO>();
        listInd.add(createGenericIndForFilter("test1", "test1"));

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        IndividualRequest individualRequest = new IndividualRequest();
        IndividualInformationsV3 individualInformations = new IndividualInformationsV3();

        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();
        email.setEmail("test@test.fr");
        emailRequest.setEmail(email);
        request.getEmailRequest().add(emailRequest);

        individualRequest.setIndividualInformations(individualInformations);
        request.setIndividualRequest(individualRequest);

        EasyMock.expect(individuDS.findProspectByEmail("test@test.fr")).andReturn(listInd);
        EasyMock.replay(individuDS);

        IndividuDTO indResponse = searchProspectByEmail(request);
        Assert.assertEquals("test1", indResponse.getNom());
        Assert.assertEquals("test1", indResponse.getPrenom());

        logger.info("End test");

    }

    public IndividuDTO createGenericIndForFilter(String lastname, String firstname) {

        IndividuDTO indDTO = new IndividuDTO();

        indDTO.setNom(lastname);
        indDTO.setPrenom(firstname);
        indDTO.setType("W");

        return indDTO;

    }

    public ProspectIndividuDTO createProspectIndividuDTO(String mail, String gin, String cin) {

        ProspectIndividuDTO prospectIndDTO = new ProspectIndividuDTO();
        prospectIndDTO.setEmail(mail);
        prospectIndDTO.setGin(gin);
        prospectIndDTO.setCin(cin);

        return prospectIndDTO;
    }

    private String generateEmail() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();

        for (int i = 0; i < 12; ++i) {
            char c = chars[rd.nextInt(chars.length)];
            sb.append(c);
            sb.append(i == 7 ? "." : "");
        }
        sb.append("@bidon.fr");

        return sb.toString();
    }

}
