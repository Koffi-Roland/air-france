package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.repind.ws.createorupdateindividualdata.siccommontype.siccommonenum.AccountStatusEnum;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.Delegate;
import com.afklm.soa.stubs.w000442.v8.request.Delegator;
import com.afklm.soa.stubs.w000442.v8.request.*;
import com.afklm.soa.stubs.w000442.v8.response.BusinessError;
import com.afklm.soa.stubs.w000442.v8.response.BusinessErrorBloc;
import com.afklm.soa.stubs.w000442.v8.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000442.v8.siccommonenum.LanguageCodesEnum;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.*;
import com.airfrance.ref.util.UList;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.individu.MarketLanguageRepository;
import com.airfrance.repind.dao.profil.ProfilsRepository;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.profil.Profils;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.FixMethodOrder;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
class CreateOrUpdateAnIndividualImplV8Test {

    private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV8Test.class);
    private static final String CHANNEL = "B2C";
    private static final String SIGNATURE = "T412211";
    private static final String SITE = "QVI";
    private static final String MAIL = "testloha@af.ri";
    private static final String CONTEXT = "B2C_HOME_PAGE";
    private static final String APP_CODE = "ISI";
    private static final String GIN_FORGET_A = "400309010381";
    private final String GIN_DELEGATOR = "400363296011";
    private final int TWO_TELECOMS = 2;
    private final int ONE_TELECOM = 1;
    private final int ONE_DELEGATION = 1;
    private final int ZERO_DELEGATION = 0;
    private static String GIN = "400622316554";

    @Autowired
    private MarketLanguageRepository marketLanguageRepository;

    @Autowired
    private DelegationDataDS delegationDataDS;

    @Autowired
    private IndividuRepository individuRepository;

    @Autowired
    private CommunicationPreferencesDS comPrefDS;

    @Autowired
    private TelecomDS telecomsDS;

    @Autowired
    private EmailDS emailDS;

    @Autowired
    private ProfilsRepository profilsRepository;

    @Autowired
    private IndividuDS individuDS;

    @Autowired
    private AccountDataDS accountDataDS;

    @Autowired
    private PostalAddressDS postalAddressDS;

    @Autowired
    private CommunicationPreferencesDS communicationPreferenceDS;

    @Autowired
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    /**
     * the Entity Manager
     */
    @PersistenceContext(unitName = "entityManagerFactoryRepind")
    private EntityManager entityManager;

    @Autowired
    @Qualifier("passenger_CreateUpdateIndividualService-v8Bean")
    private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImplV8;

    private String generateString() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();
        for (int i = 0; i < 10; ++i) {
            char c = chars[rd.nextInt(chars.length)];
            sb.append(c);
        }

        return sb.toString();
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

    private String generatePhone() {
        return "04" + RandomStringUtils.randomNumeric(8);
    }

    @Test
    void testCreateOrUpdateIndividual_simpleIndividu() {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start...");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV8Test.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV8Test.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV8Test.SIGNATURE);

        request.setRequestor(requestor);

        IndividualRequest indRequest = new IndividualRequest();
        // indRequest.setCivilian("MR");

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setBirthDate(new Date());
        indInfo.setCivility("MR");
        indInfo.setStatus("V");
        indInfo.setLastNameSC("PHILIPPOT");
        indInfo.setFirstNameSC("PATRICK");

        indRequest.setIndividualInformations(indInfo);

        request.setIndividualRequest(indRequest);

        // TODO workaround for maven issue with Bamboo
        CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
        try {
            response = createOrUpdateIndividualImplV8.createIndividual(request);
        } catch (BusinessErrorBlocBusinessException e) {
            logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
            logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
            return;
        }
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("GIN created : " + response.getGin());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test stop.");
    }

    @Test
    @Rollback(true)
    void testCreateOrUpdateIndividual_CrisisEvent() {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // crisis event (statut hidden for individual)
        request.setProcess(ProcessEnum.H.getCode());

        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV8Test.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV8Test.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV8Test.SIGNATURE);

        request.setRequestor(requestor);

        IndividualRequest indRequest = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setIdentifier("110000017001");

        indRequest.setIndividualInformations(indInfo);
        request.setIndividualRequest(indRequest);

        CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
        try {
            response = createOrUpdateIndividualImplV8.createIndividual(request);
            if (response.isSuccess()) {
                Assertions.assertTrue(true);
            }
        } catch (BusinessErrorBlocBusinessException e) {
            Assertions.assertTrue(false);
        }
    }


    @Test
    void testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KL() {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        request.setProcess(ProcessEnum.E.getCode());

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV8Test.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV8Test.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV8Test.SIGNATURE);

        request.setRequestor(requestor);


        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setCivility("MR");
        indInfo.setLastNameSC("SOCIAL" + generateString());
        indInfo.setFirstNameSC("SOCIAL" + generateString());
        indInfo.setStatus("V");

        indRequest.setIndividualInformations(indInfo);
        request.setIndividualRequest(indRequest);

        // Social
        ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
        ExternalIdentifier ei = new ExternalIdentifier();
        ei.setIdentifier("NumeroIDGIGYA_KL_1234567_" + generatePhone());
        ei.setType("GIGYA_ID");
        eir.setExternalIdentifier(ei);

        ExternalIdentifierData eid = new ExternalIdentifierData();
        eid.setKey("USED_BY_KL");
        eid.setValue("Y");
        eir.getExternalIdentifierData().add(eid);

        request.getExternalIdentifierRequest().add(eir);

        // WS call
        // TODO workaround for maven issue with Bamboo
        CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
        try {
            response = createOrUpdateIndividualImplV8.createIndividual(request);
        } catch (BusinessErrorBlocBusinessException e) {
            logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
            logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
            return;
        }

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        Assertions.assertTrue(response.getGin().startsWith("92"));

        Assertions.assertNotNull(response.getInformationResponse());
        Assertions.assertNotEquals(0, response.getInformationResponse());


        CreateOrUpdateAnIndividualImplV8Test.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KL " + response.getGin());
    }

    @Test
    void testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_AF() {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        request.setProcess(ProcessEnum.E.getCode());

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV8Test.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV8Test.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV8Test.SIGNATURE);

        request.setRequestor(requestor);


        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setCivility("MR");
        indInfo.setLastNameSC("SOCIAL" + generateString());
        indInfo.setFirstNameSC("SOCIAL" + generateString());
        indInfo.setStatus("V");

        indRequest.setIndividualInformations(indInfo);
        request.setIndividualRequest(indRequest);

        // Social
        ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
        ExternalIdentifier ei = new ExternalIdentifier();
        ei.setIdentifier("NumeroIDGIGYA_AF_1234567_" + generatePhone());
        ei.setType("GIGYA_ID");
        eir.setExternalIdentifier(ei);

        ExternalIdentifierData eid = new ExternalIdentifierData();
        eid.setKey("USED_BY_AF");
        eid.setValue("Y");
        eir.getExternalIdentifierData().add(eid);

        request.getExternalIdentifierRequest().add(eir);

        // WS call
        // TODO workaround for maven issue with Bamboo
        CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
        try {
            response = createOrUpdateIndividualImplV8.createIndividual(request);
        } catch (BusinessErrorBlocBusinessException e) {
            logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
            logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
            return;
        }

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        Assertions.assertTrue(response.getGin().startsWith("92"));

        Assertions.assertNotNull(response.getInformationResponse());
        Assertions.assertNotEquals(0, response.getInformationResponse());


        CreateOrUpdateAnIndividualImplV8Test.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_AF " + response.getGin());
    }


    @Test
    void testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KLM_AF() {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        request.setProcess(ProcessEnum.E.getCode());

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV8Test.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV8Test.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV8Test.SIGNATURE);

        request.setRequestor(requestor);


        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setCivility("MR");
        indInfo.setLastNameSC("SOCIAL" + generateString());
        indInfo.setFirstNameSC("SOCIAL" + generateString());
        indInfo.setStatus("V");

        indRequest.setIndividualInformations(indInfo);
        request.setIndividualRequest(indRequest);

        // Social
        ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
        ExternalIdentifier ei = new ExternalIdentifier();
        ei.setIdentifier("NumeroIDGIGYA_AFKL_1234567_" + generatePhone());
        ei.setType("GIGYA_ID");
        eir.setExternalIdentifier(ei);

        ExternalIdentifierData eid = new ExternalIdentifierData();
        eid.setKey("USED_BY_AF");
        eid.setValue("Y");
        eir.getExternalIdentifierData().add(eid);

        ExternalIdentifierData eid_KL = new ExternalIdentifierData();
        eid_KL.setKey("USED_BY_KL");
        eid_KL.setValue("Y");
        eir.getExternalIdentifierData().add(eid_KL);

        request.getExternalIdentifierRequest().add(eir);

        // WS call
        // TODO workaround for maven issue with Bamboo
        CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
        try {
            response = createOrUpdateIndividualImplV8.createIndividual(request);
        } catch (BusinessErrorBlocBusinessException e) {
            logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
            logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
            return;
        }

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        Assertions.assertTrue(response.getGin().startsWith("92"));

        Assertions.assertNotNull(response.getInformationResponse());
        Assertions.assertNotEquals(0, response.getInformationResponse());


        CreateOrUpdateAnIndividualImplV8Test.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KLM_AF " + response.getGin());
    }

    @Test
    void testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KLM_AF_DIFF() {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        request.setProcess(ProcessEnum.E.getCode());

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV8Test.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV8Test.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV8Test.SIGNATURE);

        request.setRequestor(requestor);


        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setCivility("MR");
        indInfo.setLastNameSC("SOCIAL" + generateString());
        indInfo.setFirstNameSC("SOCIAL" + generateString());
        indInfo.setStatus("V");

        indRequest.setIndividualInformations(indInfo);
        request.setIndividualRequest(indRequest);

        // Social
        ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
        ExternalIdentifier ei = new ExternalIdentifier();
        ei.setIdentifier("NumeroIDGIGYA_AFKL_1234567_2_" + generatePhone());
        ei.setType("GIGYA_ID");
        eir.setExternalIdentifier(ei);

        ExternalIdentifierData eid = new ExternalIdentifierData();
        eid.setKey("USED_BY_AF");
        eid.setValue("Y");
        eir.getExternalIdentifierData().add(eid);

        // Social 2
        ExternalIdentifierRequest eir2 = new ExternalIdentifierRequest();
        ExternalIdentifier ei2 = new ExternalIdentifier();
        ei2.setIdentifier("NumeroIDGIGYA_AFKL_1234567_1_" + generatePhone());
        ei2.setType("GIGYA_ID");
        eir2.setExternalIdentifier(ei2);

        ExternalIdentifierData eid_KL2 = new ExternalIdentifierData();
        eid_KL2.setKey("USED_BY_KL");
        eid_KL2.setValue("Y");
        eir2.getExternalIdentifierData().add(eid_KL2);

        request.getExternalIdentifierRequest().add(eir2);

        // WS call
        // TODO workaround for maven issue with Bamboo
        CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
        try {
            response = createOrUpdateIndividualImplV8.createIndividual(request);
        } catch (BusinessErrorBlocBusinessException e) {
            logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
            logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
            return;
        }

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        Assertions.assertTrue(response.getGin().startsWith("92"));

        Assertions.assertNotNull(response.getInformationResponse());
        Assertions.assertNotEquals(0, response.getInformationResponse());


        CreateOrUpdateAnIndividualImplV8Test.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_KLM_AF_DIFF " + response.getGin());
    }

    @Test
    void testCreateOrUpdateIndividual_ExternalIdentifier_FACEBOOK() {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        request.setProcess(ProcessEnum.E.getCode());

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV8Test.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV8Test.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV8Test.SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setCivility("MR");
        indInfo.setLastNameSC("SOCFB" + generateString());
        indInfo.setFirstNameSC("SOCFB" + generateString());
        indInfo.setStatus("V");

        indRequest.setIndividualInformations(indInfo);
        request.setIndividualRequest(indRequest);

        // Social
        ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
        ExternalIdentifier ei = new ExternalIdentifier();
        ei.setIdentifier("NumeroIDFACEBOOK_123456789101112");
        ei.setType("FACEBOOK_ID");
        eir.setExternalIdentifier(ei);

        request.getExternalIdentifierRequest().add(eir);

        // WS call
        // TODO workaround for maven issue with Bamboo
        CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
        try {
            response = createOrUpdateIndividualImplV8.createIndividual(request);
        } catch (BusinessErrorBlocBusinessException e) {
            logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
            logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
            return;
        }

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        Assertions.assertTrue(response.getGin().startsWith("92"));

        Assertions.assertNotNull(response.getInformationResponse());
        Assertions.assertNotEquals(0, response.getInformationResponse());


        CreateOrUpdateAnIndividualImplV8Test.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_FACEBOOK " + response.getGin());
    }

    @Test
    void testCreateOrUpdateIndividual_ExternalIdentifier_TWITTER() {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        request.setProcess(ProcessEnum.E.getCode());

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV8Test.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV8Test.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV8Test.SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setCivility("MR");
        indInfo.setLastNameSC("SOCTWI" + generateString());
        indInfo.setFirstNameSC("SOCTWI" + generateString());
        indInfo.setStatus("V");

        indRequest.setIndividualInformations(indInfo);
        request.setIndividualRequest(indRequest);

        // Social
        ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
        ExternalIdentifier ei = new ExternalIdentifier();
        ei.setIdentifier("NumeroIDTWITTER_123456789101112");
        ei.setType("TWITTER_ID");
        eir.setExternalIdentifier(ei);

        request.getExternalIdentifierRequest().add(eir);

        // WS call
        // TODO workaround for maven issue with Bamboo
        CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
        try {
            response = createOrUpdateIndividualImplV8.createIndividual(request);
        } catch (BusinessErrorBlocBusinessException e) {
            logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
            logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
            return;
        }

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        Assertions.assertTrue(response.getGin().startsWith("92"));

        Assertions.assertNotNull(response.getInformationResponse());
        Assertions.assertNotEquals(0, response.getInformationResponse());


        CreateOrUpdateAnIndividualImplV8Test.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_TWITTER " + response.getGin());
    }


    @Test
    public void testCreateOrUpdateIndividual_ExternalIdentifier_UpdateExternalCivilityProvided() throws BusinessErrorBlocBusinessException{
        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        /* -- CREATE -- */
        CreateUpdateIndividualRequest request = buildRequestExternal();
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        entityManager.flush();
        entityManager.clear();

        String gin = response.getGin();

        /* -- UPDATE -- */
        CreateUpdateIndividualRequest requestUpdate = new CreateUpdateIndividualRequest();
        requestUpdate.setProcess(ProcessEnum.E.getCode());

        IndividualRequest indRequestUpdate = new IndividualRequest();
        IndividualInformationsV3 indInfoUpdate = new IndividualInformationsV3();
        indInfoUpdate.setIdentifier(gin);
        indInfoUpdate.setCivility(CivilityEnum.MISTER.toString());
        indRequestUpdate.setIndividualInformations(indInfoUpdate);

        requestUpdate.setRequestor(request.getRequestor());
        requestUpdate.setIndividualRequest(indRequestUpdate);

        // Execute test
        response = createOrUpdateIndividualImplV8.createIndividual(requestUpdate);
        Individu ind = individuRepository.findBySgin(gin);

        Assertions.assertEquals(GenderEnum.MALE.toString(), ind.getSexe());
        Assertions.assertEquals(CivilityEnum.MISTER.toString(), ind.getCivilite());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Gin updated: " + gin);
        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test");

    }

    @Test
    public void testCreateOrUpdateIndividual_ExternalIdentifier_UpdateExternalGenderProvided() throws BusinessErrorBlocBusinessException{
        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        /* -- CREATE -- */
        CreateUpdateIndividualRequest request = buildRequestExternal();
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        entityManager.flush();
        entityManager.clear();

        String gin = response.getGin();

        /* -- UPDATE -- */
        CreateUpdateIndividualRequest requestUpdate = new CreateUpdateIndividualRequest();
        requestUpdate.setProcess(ProcessEnum.E.getCode());

        IndividualRequest indRequestUpdate = new IndividualRequest();
        IndividualInformationsV3 indInfoUpdate = new IndividualInformationsV3();
        indInfoUpdate.setIdentifier(gin);
        indInfoUpdate.setGender(GenderEnum.MALE.toString());
        indRequestUpdate.setIndividualInformations(indInfoUpdate);

        requestUpdate.setRequestor(request.getRequestor());
        requestUpdate.setIndividualRequest(indRequestUpdate);

        // Execute test
        response = createOrUpdateIndividualImplV8.createIndividual(requestUpdate);
        Individu ind = individuRepository.findBySgin(gin);

        Assertions.assertEquals(GenderEnum.MALE.toString(), ind.getSexe());
        Assertions.assertEquals(CivilityEnum.MISTER.toString(), ind.getCivilite());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Gin updated: " + gin);
        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test");

    }

    @Test
    @Rollback
    void testCreateOrUpdateIndividual_ExternalIdentifier_GigyaOnly() throws BusinessErrorBlocBusinessException {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        request.setProcess(ProcessEnum.E.getCode());

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV8Test.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV8Test.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV8Test.SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        //		IndividualRequest indRequest = new IndividualRequest();

        //		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        //		indInfo.setCivility("MR");
        //		indInfo.setLastNameSC("SOCTWI" + generateString());
        //		indInfo.setFirstNameSC("SOCTWI" + generateString());
        //		indInfo.setStatus("V");
        //
        //		indRequest.setIndividualInformations(indInfo);
        //		request.setIndividualRequest(indRequest);

        // Social
        ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
        ExternalIdentifier ei = new ExternalIdentifier();
        ei.setIdentifier("NumeroIDGIGYA_KL_" + generatePhone() + "_");
        ei.setType("GIGYA_ID");
        eir.setExternalIdentifier(ei);

        ExternalIdentifierData eid = new ExternalIdentifierData();
        eid.setKey("USED_BY_KL");
        eid.setValue("Y");
        ExternalIdentifierData eid2 = new ExternalIdentifierData();
        eid2.setKey("USED_BY_AF");
        eid2.setValue("N");
        eir.getExternalIdentifierData().add(eid);
        eir.getExternalIdentifierData().add(eid2);

        request.getExternalIdentifierRequest().add(eir);

        // WS call
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        Assertions.assertTrue(response.getGin().startsWith("92"));

        Assertions.assertNotNull(response.getInformationResponse());
        Assertions.assertNotEquals(0, response.getInformationResponse());


        CreateOrUpdateAnIndividualImplV8Test.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_Gigya Only " + response.getGin());
    }
    // TODO : Mesurer l unicite du GIGYA sur ExternalIdentifier ou plutot SocialNetwork

    @Test
    void testCreateOrUpdateIndividual_Prospect_CreateNewProspect() throws BusinessErrorBlocBusinessException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

        /*** Set parameters ***/
        // Requestor
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setContext("B2C_HOME_PAGE");
        requestor.setApplicationCode("ISI");
        requestor.setSite("QVI");
        requestor.setSignature("TU_RI");

        // Email
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();

        email.setEmail(CreateOrUpdateAnIndividualImplV8Test.MAIL);
        emailRequest.setEmail(email);

        // Communication Preference
        ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
        CommunicationPreferences comPref = new CommunicationPreferences();
        comPref.setDomain("S");
        comPref.setCommunicationGroupeType("N");
        comPref.setCommunicationType("AF");
        comPref.setOptIn("N");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            comPref.setDateOfConsent(df.parse("30-08-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set date of consent");
        }
        comPref.setSubscriptionChannel("B2C Home Page");

        MarketLanguage ml = new MarketLanguage();
        ml.setLanguage(LanguageCodesEnum.FR);
        ml.setMarket("FR");
        ml.setOptIn("N");
        try {
            ml.setDateOfConsent(df.parse("01-09-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set market language date of consent");
        }

        comPref.getMarketLanguage().add(ml);
        comPrefrequest.setCommunicationPreferences(comPref);

        request.setRequestor(requestor);
        request.getEmailRequest().add(emailRequest);
        request.getComunicationPreferencesRequest().add(comPrefrequest);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Gin updated: " + response.getGin());
        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test");
    }

    @Test
    @Category(com.airfrance.repind.util.TestCategory.Slow.class)
    void testCreateOrUpdateIndividual_Prospect_UpdateExistingIndividual() throws BusinessErrorBlocBusinessException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

        /*** Set parameters ***/
        // Requestor
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setContext("B2C_HOME_PAGE");
        requestor.setApplicationCode("ISI");
        requestor.setSite("QVI");
        requestor.setSignature("TU_RI");
        requestor.setLoggedGin("400424668522");

        // Email
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();

        email.setEmail("t.loharano@gmail.com");
        emailRequest.setEmail(email);

        // Communication Preference
        ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
        CommunicationPreferences comPref = new CommunicationPreferences();
        comPref.setDomain("S");
        comPref.setCommunicationGroupeType("N");
        comPref.setCommunicationType("AF");
        comPref.setOptIn("N");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            comPref.setDateOfConsent(df.parse("30-08-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set date of consent");
        }
        comPref.setSubscriptionChannel("B2C Home Page");

        MarketLanguage ml = new MarketLanguage();
        ml.setLanguage(LanguageCodesEnum.FR);
        ml.setMarket("FR");
        ml.setOptIn("N");
        try {
            ml.setDateOfConsent(df.parse("01-09-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set market language date of consent");
        }

        comPref.getMarketLanguage().add(ml);
        comPrefrequest.setCommunicationPreferences(comPref);

        request.setRequestor(requestor);
        request.getEmailRequest().add(emailRequest);
        request.getComunicationPreferencesRequest().add(comPrefrequest);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Gin updated: " + response.getGin());
        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test");
    }

    @Test
    void testCreateOrUpdateIndividual_ExternalIdentifier_GigyaOnlyUsingTheSameKey() throws BusinessErrorBlocBusinessException {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        request.setProcess(ProcessEnum.E.getCode());

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV8Test.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV8Test.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV8Test.SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        //		IndividualRequest indRequest = new IndividualRequest();

        //		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        //		indInfo.setCivility("MR");
        //		indInfo.setLastNameSC("SOCTWI" + generateString());
        //		indInfo.setFirstNameSC("SOCTWI" + generateString());
        //		indInfo.setStatus("V");
        //
        //		indRequest.setIndividualInformations(indInfo);
        //		request.setIndividualRequest(indRequest);

        // Social
        ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
        ExternalIdentifier ei = new ExternalIdentifier();
        ei.setIdentifier("NumeroIDGIGYA_KL_12345478_" + generateString());
        ei.setType("GIGYA_ID");
        eir.setExternalIdentifier(ei);

        ExternalIdentifierData eid = new ExternalIdentifierData();
        eid.setKey("USED_BY_KL");
        eid.setValue("Y");
        ExternalIdentifierData eid2 = new ExternalIdentifierData();
        eid2.setKey("USED_BY_AF");
        eid2.setValue("N");
        eir.getExternalIdentifierData().add(eid);
        eir.getExternalIdentifierData().add(eid2);

        request.getExternalIdentifierRequest().add(eir);

        // WS call
        try {
            CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

            Assertions.assertNotNull(response);
            Assertions.assertNotNull(response.getGin());
            Assertions.assertTrue(response.getGin().startsWith("92"));

        } catch (BusinessErrorBlocBusinessException exc) {
            Assertions.fail("ERROR : " + exc.getMessage());
        }
        // Tests
    }

    @Test
    void testCreateOrUpdateIndividual_UpdateExternalIdentifier_On_ExistingIndividual() {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        request.setProcess(ProcessEnum.E.getCode());

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV8Test.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV8Test.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV8Test.SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setCivility("MR");
        indInfo.setLastNameSC("SOCIAL" + generateString());
        indInfo.setFirstNameSC("SOCIAL" + generateString());
        indInfo.setStatus("V");

        indRequest.setIndividualInformations(indInfo);
        request.setIndividualRequest(indRequest);

        // Social
        ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
        ExternalIdentifier ei = new ExternalIdentifier();
        ei.setIdentifier("NumeroIDGIGYA_AF_1234567_" + generatePhone());
        ei.setType("GIGYA_ID");
        eir.setExternalIdentifier(ei);

        ExternalIdentifierData eid = new ExternalIdentifierData();
        eid.setKey("USED_BY_AF");
        eid.setValue("Y");
        eir.getExternalIdentifierData().add(eid);

        request.getExternalIdentifierRequest().add(eir);

        // WS call
        // TODO workaround for maven issue with Bamboo
        CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
        try {
            response = createOrUpdateIndividualImplV8.createIndividual(request);
        } catch (BusinessErrorBlocBusinessException e) {
            logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
            logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
            return;
        }

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        Assertions.assertTrue(response.getGin().startsWith("92"));

        Assertions.assertNotNull(response.getInformationResponse());
        Assertions.assertNotEquals(0, response.getInformationResponse());


        CreateOrUpdateAnIndividualImplV8Test.logger.info("testCreateOrUpdateIndividual_ExternalIdentifier_GIGYA_AF " + response.getGin());
    }


    @Test
    @Transactional
    @Rollback(true)
    void testCreateOrUpdateIndividual_CreateDelegationLinkWithInformationCodeNull() throws BusinessErrorBlocBusinessException {

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);
        requestor.setApplicationCode("B2B");
        requestor.setToken("WSSiC2");

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setIdentifier("400622316554");

        indRequest.setIndividualInformations(indInfo);
        request.setIndividualRequest(indRequest);

        // Social
        AccountDelegationDataRequest accountDelegationDataRequest = new AccountDelegationDataRequest();

        Delegator delegator = new Delegator();
        DelegationData delegationData = new DelegationData();
        delegationData.setGin("110000315411");
        delegationData.setDelegationAction("A");
        delegationData.setDelegationType("UM");
        delegator.setDelegationData(delegationData);

        accountDelegationDataRequest.getDelegator().add(delegator);

        // WS call
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());

        Assertions.assertNotNull(response.getInformationResponse());
        Assertions.assertEquals(0, response.getInformationResponse().size());

        logger.info("testCreateOrUpdateIndividual_CreateDelegationLinkWithInformationCodeNull");
    }

    @Test// select * from sic2.REF_PREFERENCE_VALUE; select * from sic2.REF_PREFERENCE_KEY;
    void testCreateOrUpdateIndividual_Preferences_UpdateExistingIndividual() throws BusinessErrorBlocBusinessException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        /*** Set parameters ***/
        // Requestor
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setApplicationCode("ISI");
        requestor.setSite("QVI");
        requestor.setSignature("TU_RI");
        request.setRequestor(requestor);

        // Individual to update
        IndividualRequest reqInd = new IndividualRequest();
        IndividualInformationsV3 indInf = new IndividualInformationsV3();
        indInf.setIdentifier("400491922886");
        reqInd.setIndividualInformations(indInf);
        request.setIndividualRequest(reqInd);

        // Preferences
        Preference preference = new Preference();
        PreferenceData prefData = new PreferenceData();
        prefData.setKey("ML");
        prefData.setValue("LSML");
        preference.setTypePreference("B2C");
        preference.getPreferenceData().add(prefData);

        // Update
        //		PreferenceDataRequest prefDataReq = new PreferenceDataRequest();
        //		prefDataReq.getPreference().add(preference);
        //		request.setPreferenceDataRequest(prefDataReq);

        // WS call
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test. ");
    }

    @Test
    void testCreateOrUpdateIndividual_Alert_UpdateProspectWithGIN() throws BusinessErrorBlocBusinessException {
        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        request.setProcess(ProcessEnum.A.getCode());

        /*** Set parameters ***/
        // Requestor
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setApplicationCode("ISI");
        requestor.setSite("QVI");
        requestor.setSignature("TU_RI");

        // Individual to update
        IndividualRequest reqInd = new IndividualRequest();
        IndividualInformationsV3 indInf = new IndividualInformationsV3();
        indInf.setIdentifier("400622215824");
        reqInd.setIndividualInformations(indInf);

        // Communication Preference
        ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
        CommunicationPreferences comPref = new CommunicationPreferences();
        comPref.setDomain("S");
        comPref.setCommunicationGroupeType("P");
        comPref.setCommunicationType("AF");
        comPref.setOptIn("N");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            comPref.setDateOfConsent(df.parse("30-08-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set date of consent");
        }
        comPref.setSubscriptionChannel("B2C Home Page");

        MarketLanguage ml = new MarketLanguage();
        ml.setLanguage(LanguageCodesEnum.FR);
        ml.setMarket("FR");
        ml.setOptIn("N");
        try {
            ml.setDateOfConsent(df.parse("01-09-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set market language date of consent");
        }

        comPref.getMarketLanguage().add(ml);
        comPrefrequest.setCommunicationPreferences(comPref);

        // Alert
        AlertRequest alertRequest = new AlertRequest();
        Alert alert = new Alert();
        alert.setType("P");
        alert.setOptIn("N");
        /* origin */
        AlertData orig = new AlertData();
        orig.setKey("ORIGIN");
        orig.setValue("CDG");
        alert.getAlertData().add(orig);
        AlertData origEnr = new AlertData();
        origEnr.setKey("ORIGIN_ENR");
        origEnr.setValue("CDG");
        alert.getAlertData().add(origEnr);
        /* origin type */
        AlertData origType = new AlertData();
        origType.setKey("ORIGIN_TYPE");
        origType.setValue("A");
        alert.getAlertData().add(origType);
        /* destination */
        AlertData dest = new AlertData();
        dest.setKey("DESTINATION");
        dest.setValue("NCE");
        alert.getAlertData().add(dest);
        /* destination type */
        AlertData destType = new AlertData();
        destType.setKey("DESTINATION_TYPE");
        destType.setValue("C");
        alert.getAlertData().add(destType);
        /* start date */
        AlertData sDate = new AlertData();
        sDate.setKey("START_DATE");
        sDate.setValue("01102016");
        alert.getAlertData().add(sDate);
        /* end date */
        AlertData eDate = new AlertData();
        eDate.setKey("END_DATE");
        eDate.setValue("31102016");
        alert.getAlertData().add(eDate);
        /* cabin */
        AlertData cabin = new AlertData();
        cabin.setKey("CABIN");
        cabin.setValue("P");
        alert.getAlertData().add(cabin);

        alertRequest.getAlert().add(alert);

        request.setIndividualRequest(reqInd);
        request.setRequestor(requestor);
        request.getComunicationPreferencesRequest().add(comPrefrequest);
        request.setAlertRequest(alertRequest);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test. ");
    }

    /**
     * Create a COM_PREF Domain P groupType S and type RECO Expected : All OK,
     * Com_Pref find in DB
     *
     * @throws BusinessErrorBlocBusinessException
     * @throws JrafDomainException
     */
    @Test
    @Transactional
    @Rollback(true)
    void testCreateOrUpdateIndividual_CreateComPrefRECO()
            throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start...");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Init data
        // Date = 01 Janvier 2010
        Calendar initDate = new GregorianCalendar();

        // Individual
        IndividualRequest indReq = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();

        indInfo.setIdentifier("400424668522");
        indInfo.setLastNameSC("NOMTEST");
        indReq.setIndividualInformations(indInfo);
        request.setIndividualRequest(indReq);

        // Requestor
        RequestorV2 req = new RequestorV2();

        req.setChannel("B2C");
        req.setContext("FB_ENROLMENT");
        req.setApplicationCode("ISI");
        req.setSite("QVI");
        req.setSignature("Test U");
        request.setRequestor(req);

        // Communication preferences
        ComunicationPreferencesRequest comPrefReq = new ComunicationPreferencesRequest();
        CommunicationPreferences comPref = new CommunicationPreferences();

        comPref.setDomain("P");
        comPref.setCommunicationGroupeType("S");
        comPref.setCommunicationType("RECO");
        comPref.setOptIn("N");
        comPref.setDateOfConsent(new Date());
        comPrefReq.setCommunicationPreferences(comPref);
        request.getComunicationPreferencesRequest().add(comPrefReq);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Retrieve updated datas
        com.airfrance.repind.entity.individu.CommunicationPreferences comPref2 = new com.airfrance.repind.entity.individu.CommunicationPreferences();
        comPref2.setGin("400424668522");
        comPref2.setDomain("P");
        comPref2.setComGroupType("S");
        comPref2.setComType("RECO");
        List<com.airfrance.repind.entity.individu.CommunicationPreferences> listFound = communicationPreferencesRepository.findAll(Example.of(comPref2));
        com.airfrance.repind.entity.individu.CommunicationPreferences foundCP = listFound.get(0);

        // Test results
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(1, listFound.size());
        Assertions.assertEquals("RECO", foundCP.getComType());
    }

    /**
     * Update a COM_PREF Domain P groupType S and type RECO Expected : All OK,
     * Com_Pref find in DB and changed
     *
     * @throws BusinessErrorBlocBusinessException
     * @throws JrafDomainException
     */
    @Test
    @Transactional
    @Rollback(true)
    void testCreateOrUpdateIndividual_UpdateComPrefRECO()
            throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start...");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Init data
        // Date = 01 Janvier 2010
        Calendar initDate = new GregorianCalendar(2010, 01, 01);

        Individu testIndividu = individuRepository.findBySgin("400424668522");
        com.airfrance.repind.entity.individu.CommunicationPreferences comPrefInit = new com.airfrance.repind.entity.individu.CommunicationPreferences();

        comPrefInit.setGin("400424668522");
        comPrefInit.setDomain("P");
        comPrefInit.setComGroupType("S");
        comPrefInit.setComType("RECO");
        comPrefInit.setSubscribe("Y");
        comPrefInit.setCreationDate(initDate.getTime());
        comPrefInit.setDateOptin(initDate.getTime());
        comPrefInit.setMarketLanguage(new HashSet<com.airfrance.repind.entity.individu.MarketLanguage>());

        testIndividu.getCommunicationpreferences().add(comPrefInit);

        individuRepository.saveAndFlush(testIndividu);

        // Individual
        IndividualRequest indReq = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();

        indInfo.setIdentifier("400424668522");
        indReq.setIndividualInformations(indInfo);
        request.setIndividualRequest(indReq);

        // Requestor
        RequestorV2 req = new RequestorV2();

        req.setChannel("B2C");
        req.setContext("FB_ENROLMENT");
        req.setApplicationCode("ISI");
        req.setSite("QVI");
        req.setSignature("Test U");
        request.setRequestor(req);

        // Communication preferences
        ComunicationPreferencesRequest comPrefReq = new ComunicationPreferencesRequest();
        CommunicationPreferences comPref = new CommunicationPreferences();

        comPref.setDomain("P");
        comPref.setCommunicationGroupeType("S");
        comPref.setCommunicationType("RECO");
        comPref.setOptIn("N");
        comPref.setDateOfConsent(new Date());
        comPrefReq.setCommunicationPreferences(comPref);
        request.getComunicationPreferencesRequest().add(comPrefReq);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Retrieve updated datas
        com.airfrance.repind.entity.individu.CommunicationPreferences comPrefDtoInit = new com.airfrance.repind.entity.individu.CommunicationPreferences();
        comPrefDtoInit.setGin("400424668522");
        comPrefDtoInit.setDomain("P");
        comPrefDtoInit.setComGroupType("S");
        comPrefDtoInit.setComType("RECO");
        List<com.airfrance.repind.entity.individu.CommunicationPreferences> listFound = communicationPreferencesRepository.findAll(Example.of(comPrefDtoInit));
        com.airfrance.repind.entity.individu.CommunicationPreferences foundCP = listFound.get(0);

        // Test results
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(1, listFound.size());
        Assertions.assertEquals("RECO", foundCP.getComType());
        Assertions.assertEquals(initDate.getTime(), foundCP.getCreationDate());
        Assertions.assertNotEquals(initDate.getTime(), foundCP.getModificationDate());
    }

    @Test
    void testCreateOrUpdateIndividual_Alert_CreatProspectWithMail() throws BusinessErrorBlocBusinessException {
        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        request.setProcess(ProcessEnum.A.getCode());

        /*** Set parameters ***/
        // Requestor
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setApplicationCode("ISI");
        requestor.setSite("QVI");
        requestor.setSignature("TU_RI");


        // Email
        EmailRequest emailReq = new EmailRequest();
        Email email = new Email();
        String mail = generateEmail();
        //		Random rd = new Random();
        //		int randomInt = rd.nextInt(1000);

        //		mail.append("tualert");
        //		mail.append(Integer.toString(randomInt));
        //		mail.append("@yopmail.com");
        email.setEmail(mail);
        email.setMediumCode(MediumCodeEnum.HOME.toString());
        email.setMediumStatus("V");
        emailReq.setEmail(email);

        // Communication Preference
        ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
        CommunicationPreferences comPref = new CommunicationPreferences();
        comPref.setDomain("S");
        comPref.setCommunicationGroupeType("P");
        comPref.setCommunicationType("AF");
        comPref.setOptIn("N");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            comPref.setDateOfConsent(df.parse("30-08-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set date of consent");
        }
        comPref.setSubscriptionChannel("B2C Home Page");

        MarketLanguage ml = new MarketLanguage();
        ml.setLanguage(LanguageCodesEnum.FR);
        ml.setMarket("FR");
        ml.setOptIn("N");
        try {
            ml.setDateOfConsent(df.parse("01-09-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set market language date of consent");
        }

        comPref.getMarketLanguage().add(ml);
        comPrefrequest.setCommunicationPreferences(comPref);

        // Alert
        AlertRequest alertRequest = new AlertRequest();
        Alert alert = new Alert();
        alert.setType("P");
        alert.setOptIn("N");
        /* origin */
        AlertData orig = new AlertData();
        orig.setKey("ORIGIN");
        orig.setValue("PAR");
        alert.getAlertData().add(orig);
        AlertData origEnr = new AlertData();
        origEnr.setKey("ORIGIN_ENR");
        origEnr.setValue("CDG");
        alert.getAlertData().add(origEnr);
        /* origin type */
        AlertData origType = new AlertData();
        origType.setKey("ORIGIN_TYPE");
        origType.setValue("C");
        alert.getAlertData().add(origType);
        /* destination */
        AlertData dest = new AlertData();
        dest.setKey("DESTINATION");
        dest.setValue("LAX");
        alert.getAlertData().add(dest);
        /* destination type */
        AlertData destType = new AlertData();
        destType.setKey("DESTINATION_TYPE");
        destType.setValue("A");
        alert.getAlertData().add(destType);
        /* start date */
        AlertData sDate = new AlertData();
        sDate.setKey("START_DATE");
        sDate.setValue("01012016");
        alert.getAlertData().add(sDate);
        /* end date */
        AlertData eDate = new AlertData();
        eDate.setKey("END_DATE");
        eDate.setValue("31122016");
        alert.getAlertData().add(eDate);
        /* cabin */
        AlertData cabin = new AlertData();
        cabin.setKey("CABIN");
        cabin.setValue("W");
        alert.getAlertData().add(cabin);

        alertRequest.getAlert().add(alert);

        request.getEmailRequest().add(emailReq);
        request.setRequestor(requestor);
        request.getComunicationPreferencesRequest().add(comPrefrequest);
        request.setAlertRequest(alertRequest);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());
        Assertions.assertTrue(response.getGin().startsWith("90"));

        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test. ");
    }

    // BUG SURCOUF
    @Test
    @Category(com.airfrance.repind.util.TestCategory.Slow.class)
    void testCreateOrUpdateIndividual_SurcoufBUG() {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start...");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        request.setStatus("V");

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("SURCW");
        requestor.setToken("WSSiC2");
        requestor.setApplicationCode("MAC");
        requestor.setSite("QVI");
        requestor.setSignature("SURCOUFWEB");

        request.setRequestor(requestor);

        IndividualRequest indRequest = new IndividualRequest();
        // indRequest.setCivilian("MR");

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setBirthDate(new Date());
        indInfo.setCivility("MR");
        indInfo.setStatus("V");
        indInfo.setLastNameSC("Konijnenbelt");
        indInfo.setFirstNameSC("W");

        indRequest.setIndividualInformations(indInfo);

        request.setIndividualRequest(indRequest);


        // Email
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();
        email.setMediumCode(MediumCodeEnum.HOME.toString());
        email.setMediumStatus("V");
        email.setEmail("MAIL@KONIJNENBELTWETGEVING.NL");
        emailRequest.setEmail(email);
        request.getEmailRequest().add(emailRequest);

        // Postal Address
        PostalAddressRequest addRequest = new PostalAddressRequest();
        PostalAddressContent pac = new PostalAddressContent();
        pac.setNumberAndStreet("Baronielaan 229");
        pac.setZipCode("4835JK");
        pac.setCity("Breda");
        pac.setCountryCode("NL");
        pac.setAdditionalInformation("");
        pac.setDistrict("");
        pac.setStateCode("");
        pac.setCorporateName("");
        addRequest.setPostalAddressContent(pac);

        PostalAddressProperties pap = new PostalAddressProperties();
        pap.setMediumCode(MediumCodeEnum.BUSINESS.toString());
        pap.setMediumStatus("V");
        // pap.setVersion("1");
        pap.setIndicAdrNorm(true);    // On force l'adresse
        addRequest.setPostalAddressProperties(pap);

		/*UsageAddress ua = new UsageAddress();
		ua.setUsageNumber("1");
		ua.setApplicationCode("TU");
		addRequest.setUsageAddress(ua);
		 */
        request.getPostalAddressRequest().add(addRequest);

        // Telecom
        TelecomRequest telRequest = new TelecomRequest();
        Telecom telecom = new Telecom();
        telecom.setCountryCode("+31");
        telecom.setMediumCode(MediumCodeEnum.HOME.toString());
        telecom.setMediumStatus("V");
        telecom.setTerminalType(TerminalTypeEnum.MOBILE.toString());
        telecom.setPhoneNumber("0768882841");
        telRequest.setTelecom(telecom);
        request.getTelecomRequest().add(telRequest);


        // TODO workaround for maven issue with Bamboo
        CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
        try {
            response = createOrUpdateIndividualImplV8.createIndividual(request);
        } catch (BusinessErrorBlocBusinessException e) {
            logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
            logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
            return;
        }

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("GIN created : " + response.getGin());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test stop.");
    }

    // Tester la mise à jour d'une commPref d'un individu en conservant la date d'optin initiale
    @Test
    @Transactional
    @Rollback(true)
    void testCreateOrUpdateIndividual_UpdateIndivGinExistingComPref() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start...");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Init data
        // Date = 01 Janvier 2010
        Calendar initDate = new GregorianCalendar(2010, 01, 01);

        Individu testIndividu = individuRepository.findBySgin("400622109881");
        com.airfrance.repind.entity.individu.CommunicationPreferences comPrefInit = new com.airfrance.repind.entity.individu.CommunicationPreferences();
        com.airfrance.repind.entity.individu.MarketLanguage ml = new com.airfrance.repind.entity.individu.MarketLanguage();

        comPrefInit.setGin("400622109881");
        comPrefInit.setDomain("S");
        comPrefInit.setComGroupType("N");
        comPrefInit.setComType("KL");
        comPrefInit.setSubscribe("Y");
        comPrefInit.setDateOptin(initDate.getTime());

        ml.setDateOfConsent(new Date());
        ml.setLanguage("FR");
        ml.setMarket("FR");
        ml.setOptIn("Y");
        ml.setCreationSignature("Test U");
        ml.setCreationSite(CreateOrUpdateAnIndividualImplV8Test.SITE);

        Set<com.airfrance.repind.entity.individu.MarketLanguage> listMl = new HashSet<>();
        listMl.add(ml);
        comPrefInit.setMarketLanguage(listMl);

        testIndividu.getCommunicationpreferences().add(comPrefInit);

        individuRepository.saveAndFlush(testIndividu);

        // Individual
        IndividualRequest indReq = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();

        indInfo.setIdentifier("400622109881");
        indReq.setIndividualInformations(indInfo);
        request.setIndividualRequest(indReq);

        // Requestor
        RequestorV2 req = new RequestorV2();

        req.setChannel("B2C");
        req.setContext("FB_ENROLMENT");
        req.setApplicationCode("ISI");
        req.setSite("QVI");
        req.setSignature("Test U");
        request.setRequestor(req);

        // Communication preferences
        ComunicationPreferencesRequest comPrefReq = new ComunicationPreferencesRequest();
        CommunicationPreferences comPref = new CommunicationPreferences();

        comPref.setDomain("S");
        comPref.setCommunicationGroupeType("N");
        comPref.setCommunicationType("KL");
        comPref.setOptIn("Y");
        //comPref.setDateOfConsent(new Date());
        comPrefReq.setCommunicationPreferences(comPref);
        request.getComunicationPreferencesRequest().add(comPrefReq);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Retrieve updated datas
        com.airfrance.repind.entity.individu.CommunicationPreferences comPref2 = new com.airfrance.repind.entity.individu.CommunicationPreferences();
        comPref2.setGin("400622109881");
        comPref2.setDomain("S");
        comPref2.setComGroupType("N");
        comPref2.setComType("KL");
        List<com.airfrance.repind.entity.individu.CommunicationPreferences> listFound = communicationPreferencesRepository.findAll(Example.of(comPref2));
        com.airfrance.repind.entity.individu.CommunicationPreferences foundCP = listFound.get(0);

        // Test results
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals(1, listFound.size());
        Assertions.assertEquals("KL", foundCP.getComType());
        Assertions.assertEquals(initDate.getTime(), foundCP.getDateOptin());

    }

    // Tester la mise à jour d'une commPref d'un prospet sans conservation de la date d'optin initiale
    @Test
    @Transactional
    @Rollback(true)
    void testCreateOrUpdateIndividual_UpdateProspectGinExistingComPref() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start...");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Init data
        // Date = 01 Janvier 2010
        Calendar initDate = new GregorianCalendar(2010, 01, 01);

        Individu testIndividu = individuRepository.findBySgin("900025086459");
        com.airfrance.repind.entity.individu.CommunicationPreferences comPrefInit = new com.airfrance.repind.entity.individu.CommunicationPreferences();
        com.airfrance.repind.entity.individu.MarketLanguage ml = new com.airfrance.repind.entity.individu.MarketLanguage();

        comPrefInit.setGin("900025086459");
        comPrefInit.setDomain("S");
        comPrefInit.setComGroupType("N");
        comPrefInit.setComType("KL");
        comPrefInit.setSubscribe("Y");
        comPrefInit.setDateOptin(initDate.getTime());

        ml.setDateOfConsent(new Date());
        ml.setLanguage("FR");
        ml.setMarket("FR");
        ml.setOptIn("Y");
        ml.setCreationSignature("Test U");
        ml.setCreationSite(CreateOrUpdateAnIndividualImplV8Test.SITE);

        Set<com.airfrance.repind.entity.individu.MarketLanguage> listMl = new HashSet<>();
        listMl.add(ml);
        comPrefInit.setMarketLanguage(listMl);

        testIndividu.getCommunicationpreferences().add(comPrefInit);

        individuRepository.saveAndFlush(testIndividu);

        // Individual
        IndividualRequest indReq = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();

        indInfo.setIdentifier("900025086459");
        indReq.setIndividualInformations(indInfo);
        request.setIndividualRequest(indReq);

        // Requestor
        RequestorV2 req = new RequestorV2();

        req.setChannel("B2C");
        req.setContext("B2C_HOME_PAGE");
        req.setApplicationCode("ISI");
        req.setSite("QVI");
        req.setSignature("Test U");
        request.setRequestor(req);

        // Communication preferences
        ComunicationPreferencesRequest comPrefReq = new ComunicationPreferencesRequest();
        CommunicationPreferences comPref = new CommunicationPreferences();

        comPref.setDomain("S");
        comPref.setCommunicationGroupeType("N");
        comPref.setCommunicationType("KL");
        comPref.setOptIn("N");
        comPref.setDateOfConsent(new Date());
        comPref.setSubscriptionChannel("B2C");

        MarketLanguage marketLanguage = new MarketLanguage();
        marketLanguage.setDateOfConsent(new Date());
        marketLanguage.setLanguage(LanguageCodesEnum.FR);
        marketLanguage.setMarket("NL");
        marketLanguage.setOptIn("N");

        comPref.getMarketLanguage().add(marketLanguage);
        comPrefReq.setCommunicationPreferences(comPref);
        request.getComunicationPreferencesRequest().add(comPrefReq);

        // Process W for prospect
        request.setProcess("W");

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Retrieve updated datas
        com.airfrance.repind.entity.individu.CommunicationPreferences comPref2 = new com.airfrance.repind.entity.individu.CommunicationPreferences();
        comPref2.setGin("900025086459");
        comPref2.setDomain("S");
        comPref2.setComGroupType("N");
        comPref2.setComType("KL");
        List<com.airfrance.repind.entity.individu.CommunicationPreferences> listFound = communicationPreferencesRepository.findAll(Example.of(comPref2));

        // Test results
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isSuccess());

        // Il y a deux ComPref aucune d elle n'a ete mise a jour...
        for (com.airfrance.repind.entity.individu.CommunicationPreferences cp : listFound) {
            Assertions.assertEquals("S", cp.getDomain());
            Assertions.assertEquals("N", cp.getComGroupType());
            Assertions.assertEquals("KL", cp.getComType());
            Assertions.assertNotEquals(initDate.getTime(), cp.getDateOptin());
        }
    }

    // Tester la mise à jour d'une commPref d'un prospet en conservant la date d'optin initiale
    @Test
    @Transactional
    @Rollback(true)
    void testCreateOrUpdateIndividual_UpdateProspectEmailExistingComPref() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start...");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Init data
        // Date = 01 Janvier 2010
        Calendar initDate = new GregorianCalendar(2010, 01, 01);

        Individu testIndividu = individuRepository.findBySgin("900025086459");
        com.airfrance.repind.entity.individu.CommunicationPreferences comPrefInit = new com.airfrance.repind.entity.individu.CommunicationPreferences();
        com.airfrance.repind.entity.individu.MarketLanguage ml = new com.airfrance.repind.entity.individu.MarketLanguage();

        comPrefInit.setDomain("S");
        comPrefInit.setComGroupType("N");
        comPrefInit.setComType("KL");
        comPrefInit.setSubscribe("Y");
        comPrefInit.setDateOptin(initDate.getTime());

        ml.setDateOfConsent(new Date());
        ml.setLanguage("FR");
        ml.setMarket("FR");
        ml.setOptIn("Y");
        ml.setCreationSignature("Test U");
        ml.setCreationSite(CreateOrUpdateAnIndividualImplV8Test.SITE);

        Set<com.airfrance.repind.entity.individu.MarketLanguage> listMl = new HashSet<>();
        listMl.add(ml);
        comPrefInit.setMarketLanguage(listMl);

        testIndividu.getCommunicationpreferences().add(comPrefInit);

        marketLanguageRepository.saveAndFlush(ml);

        // Individual
        EmailRequest emailReq = new EmailRequest();
        Email email = new Email();

        email.setEmail("t_loharano@msn.com");
        emailReq.setEmail(email);
        request.getEmailRequest().add(emailReq);

        // Requestor
        RequestorV2 req = new RequestorV2();

        req.setChannel("B2C");
        req.setContext("B2C_HOME_PAGE");
        req.setApplicationCode("ISI");
        req.setSite("QVI");
        req.setSignature("Test U");
        request.setRequestor(req);

        // Communication preferences
        ComunicationPreferencesRequest comPrefReq = new ComunicationPreferencesRequest();
        CommunicationPreferences comPref = new CommunicationPreferences();

        comPref.setDomain("S");
        comPref.setCommunicationGroupeType("N");
        comPref.setCommunicationType("KL");
        comPref.setOptIn("Y");
        comPref.setDateOfConsent(new Date());
        comPref.setSubscriptionChannel("B2C");

        MarketLanguage marketLanguage = new MarketLanguage();
        marketLanguage.setDateOfConsent(new Date());
        marketLanguage.setLanguage(LanguageCodesEnum.FR);
        marketLanguage.setMarket("NL");
        marketLanguage.setOptIn("Y");

        comPref.getMarketLanguage().add(marketLanguage);
        comPrefReq.setCommunicationPreferences(comPref);
        request.getComunicationPreferencesRequest().add(comPrefReq);

        // Process W for prospect
        request.setProcess("W");

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Retrieve updated datas
        com.airfrance.repind.entity.individu.CommunicationPreferences comPref2 = new com.airfrance.repind.entity.individu.CommunicationPreferences();
        comPref2.setGin("900025086459");
        comPref2.setDomain("S");
        comPref2.setComGroupType("N");
        comPref2.setComType("KL");
        List<com.airfrance.repind.entity.individu.CommunicationPreferences> listFound = communicationPreferencesRepository.findAll(Example.of(comPref2));
        // CommunicationPreferencesDTO foundCP = listFound.get(0);

        // Test results
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isSuccess());

        // Il y a deux ComPref on cherche celle que l on vient de mettre a jour...
        for (com.airfrance.repind.entity.individu.CommunicationPreferences cp : listFound) {
            if ("B2C".equals(cp.getChannel())) {
                Assertions.assertEquals("S", cp.getDomain());
                Assertions.assertEquals("N", cp.getComGroupType());
                Assertions.assertEquals("KL", cp.getComType());
            } else {
                Assertions.assertEquals("S", cp.getDomain());
                Assertions.assertEquals("N", cp.getComGroupType());
                Assertions.assertEquals("KL", cp.getComType());
                Assertions.assertEquals(initDate.getTime(), cp.getDateOptin());
            }
        }

        //		Assertions.assertEquals("KL", foundCP.getComType());
        //		Assertions.assertEquals(initDate.getTime(), foundCP.getDateOptin());

    }

    // Add a travel doc to a customer
    @Test
    @Transactional
    @Rollback(true)
    void testCreateOrUpdateIndividual_AddTravelDoc() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start...");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Individual
        IndividualRequest indReq = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();

        indInfo.setIdentifier("400424668522");
        indReq.setIndividualInformations(indInfo);
        request.setIndividualRequest(indReq);

        // Requestor
        RequestorV2 req = new RequestorV2();

        req.setChannel("B2C");
        req.setApplicationCode("ISI");
        req.setSite("QVI");
        req.setSignature("Test U w000442v8");
        request.setRequestor(req);

        // Preference = TravelDoc
        PreferenceRequest preferenceRequest = new PreferenceRequest();
        request.setPreferenceRequest(preferenceRequest);

        PreferenceV2 preference = new PreferenceV2();
        preferenceRequest.getPreference().add(preference);
        preference.setType("TDC");

        PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
        preference.setPreferenceDatas(preferenceDatas);

        // key = type
        PreferenceDataV2 typeTdc = new PreferenceDataV2();
        preferenceDatas.getPreferenceData().add(typeTdc);
        typeTdc.setKey("type");
        typeTdc.setValue("PA");

        // key = number
        PreferenceDataV2 number = new PreferenceDataV2();
        preferenceDatas.getPreferenceData().add(number);
        number.setKey("number");
        number.setValue("01AA00011122");

        // key = expirationDate
        PreferenceDataV2 expDate = new PreferenceDataV2();
        preferenceDatas.getPreferenceData().add(expDate);
        expDate.setKey("expirationDate");
        expDate.setValue("31/12/2039");

        // key = countryIssued
        PreferenceDataV2 country = new PreferenceDataV2();
        preferenceDatas.getPreferenceData().add(country);
        country.setKey("countryOfIssue");
        country.setValue("PR");

        // key = touchPoint
        PreferenceDataV2 touch = new PreferenceDataV2();
        preferenceDatas.getPreferenceData().add(touch);
        touch.setKey("touchPoint");
        touch.setValue("ISI");

    }

    /**
     * CONTEXT UM : KIDSOLO
     * REPIND-895
     */
    @Test
    void testTelecomKeyEnum() {
        TelecomKeyEnum telecomKeyEnum = TelecomKeyEnum.fromString("terminalType");
        Assertions.assertNotNull(telecomKeyEnum);
        Assertions.assertEquals(telecomKeyEnum.name(), "TERMINAL_TYPE");

        telecomKeyEnum = TelecomKeyEnum.fromString("countryCode");
        Assertions.assertNotNull(telecomKeyEnum);
        Assertions.assertEquals(telecomKeyEnum.name(), "COUNTRY_CODE");

        telecomKeyEnum = TelecomKeyEnum.fromString("phoneNumber");
        Assertions.assertNotNull(telecomKeyEnum);
        Assertions.assertEquals(telecomKeyEnum.name(), "PHONE_NUMBER");
    }

    @Test
    void testOtherKeyEnum() {
        OtherKeyEnum otherKeyEnum = OtherKeyEnum.fromString("remark");
        Assertions.assertNotNull(otherKeyEnum);
        Assertions.assertEquals(otherKeyEnum.name(), "REMARK");
    }

    @Test
    void testIndividualKeyEnum() {
        IndividuKeyEnum individualKeyEnum = IndividuKeyEnum.fromString("dateOfBirth");
        Assertions.assertNotNull(individualKeyEnum);
        Assertions.assertEquals(individualKeyEnum.name(), "BIRTHDATE");

        individualKeyEnum = IndividuKeyEnum.fromString("spokenLanguage");
        Assertions.assertNotNull(individualKeyEnum);
        Assertions.assertEquals(individualKeyEnum.name(), "SPOKEN_LANGUAGE");
    }

    @Test
    void testContractKeyEnum() {
        ContractKeyEnum contractKeyEnum = ContractKeyEnum.fromString("contractType");
        Assertions.assertNotNull(contractKeyEnum);
        Assertions.assertEquals(contractKeyEnum.name(), "FLYING_BLUE_NUMBER");

        contractKeyEnum = ContractKeyEnum.fromString("contractNumber");
        Assertions.assertNotNull(contractKeyEnum);
        Assertions.assertEquals(contractKeyEnum.name(), "NUM_CONTRACT");
    }

    /**
     * CONTEXT UN : KIDSOLO
     * REPIND-895
     */
    @Test
    void testPostalAddressKeyEnum() {
        PostalAddressKeyEnum postalAddressKeyEnum = PostalAddressKeyEnum.fromString("numberAndStreet");
        Assertions.assertNotNull(postalAddressKeyEnum);
        Assertions.assertEquals(postalAddressKeyEnum.name(), "NUMBER_AND_STREET");

        postalAddressKeyEnum = PostalAddressKeyEnum.fromString("additionalInformation");
        Assertions.assertNotNull(postalAddressKeyEnum);
        Assertions.assertEquals(postalAddressKeyEnum.name(), "ADDITIONAL_INFORMATION");

        postalAddressKeyEnum = PostalAddressKeyEnum.fromString("city");
        Assertions.assertNotNull(postalAddressKeyEnum);
        Assertions.assertEquals(postalAddressKeyEnum.name(), "CITY");

        postalAddressKeyEnum = PostalAddressKeyEnum.fromString("district");
        Assertions.assertNotNull(postalAddressKeyEnum);
        Assertions.assertEquals(postalAddressKeyEnum.name(), "DISTRICT");

        postalAddressKeyEnum = PostalAddressKeyEnum.fromString("zipCode");
        Assertions.assertNotNull(postalAddressKeyEnum);
        Assertions.assertEquals(postalAddressKeyEnum.name(), "ZIPCODE");

        postalAddressKeyEnum = PostalAddressKeyEnum.fromString("state");
        Assertions.assertNotNull(postalAddressKeyEnum);
        Assertions.assertEquals(postalAddressKeyEnum.name(), "STATE");

        postalAddressKeyEnum = PostalAddressKeyEnum.fromString("country");
        Assertions.assertNotNull(postalAddressKeyEnum);
        Assertions.assertEquals(postalAddressKeyEnum.name(), "COUNTRY");
    }

    /**
     * CONTEXT UM : KIDSOLO
     * Create and update an delegation between delegator and delegate
     * with delegationDataInfo
     * REPIND-895
     *
     * @throws JrafDomainException
     */
    @Test
    @Transactional
    @Rollback(true)
    void testCreateOrUpdateIndividual_CreateDelegationData() throws JrafDomainException {
        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start...");

        cleanDelegationDataForTests("400462465416");

        /**
         * CREATE delegation with complementaryInformations (delegationDataInfo)
         */
        CreateUpdateIndividualResponse response = createDelegation("400462465416", 2);

        //TEST output
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isSuccess());

        //TEST number of delegation created
        List<DelegationDataDTO> delegationDelegator = delegationDataDS.findDelegate("400462465416");
        Assertions.assertEquals(ONE_DELEGATION, delegationDelegator.size());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Rollback(false)
    void cleanDelegationDataForTests(String gin) throws JrafDomainException {
        List<DelegationDataDTO> delegationDelegatorDelete = delegationDataDS.findDelegate(gin);

        if (!UList.isNullOrEmpty(delegationDelegatorDelete)) {
            for (DelegationDataDTO delegationDataDTO : delegationDelegatorDelete) {
                delegationDataDS.remove(delegationDataDTO);
            }
        }

        entityManager.flush();
        entityManager.clear();
    }

    /**
     * CONTEXT UM : KIDSOLO
     * Create and update an delegation between delegator and delegate
     * with bad birthDate in delegationDataInfo
     * REPIND-895
     *
     * @throws JrafDomainException
     */
    @Test
    @Rollback(true)
    void testCreateOrUpdateIndividual_CreateAndUpdateDelegationDataWithBadBirthDate() {
        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start...");
        // create delegation with bad birthdate
        CreateUpdateIndividualResponse response = createDelegation("400462465416", 3);
        Assertions.assertNull(response);
    }


    /**
     * CONTEXT UM : KIDSOLO
     * REPIND-895
     */
    @Rollback(true)
    private CreateUpdateIndividualResponse createDelegation(String gin, int scenary) {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        //BALISE REQUESTOR
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setSite("QVI");
        requestor.setApplicationCode(APP_CODE);
        requestor.setSignature(SIGNATURE);
        request.setRequestor(requestor);

        //BALISE INDIVIDUAL_REQUEST
        IndividualRequest indRequest = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();

        indInfo.setIdentifier(gin);
        indRequest.setIndividualInformations(indInfo);
        request.setIndividualRequest(indRequest);

        //BALISE ACCOUNT_DELEGATION_DATA_REQUEST
        AccountDelegationDataRequest accountDelegationDataRequest = new AccountDelegationDataRequest();

        Delegate delegate = new Delegate();

        DelegationData delegationData = new DelegationData();
        delegationData.setGin("400462465420");
        delegationData.setDelegationAction("A");
        delegationData.setDelegationType("UM");

        delegate.setDelegationData(delegationData);

        switch (scenary) {
            // create only one telecom and one postalAddress (update)
            case 1:
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypeTelecom("0480770888", "33"));
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypePostalAddress("NICE", "3 avenue du TEST", "06"));
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypeIndividual("20/06/1984", "FR"));
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypeContract());
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypeOther());
                delegate.getComplementaryInformation().add(createComplementaryInforamtionOfTypeEmail("test@airfrance.airrr"));
                break;

            // create two telecoms and one postalAddress
            case 2:
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypeTelecom("0480770202", "33"));
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypeTelecom("0445770101", "33"));
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypePostalAddress("Marseille", "NICE", "06"));
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypeIndividual("20/12/1984", "FR"));
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypeContract());
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypeOther());
                delegate.getComplementaryInformation().add(createComplementaryInforamtionOfTypeEmail("test@airfrance.airrr"));
                break;

            // create complementary information with a bad birthdate
            case 3:
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypeTelecom("0480770202", "33"));
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypeTelecom("0445770101", "33"));
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypePostalAddress("Marseille", "NICE", "06"));
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypeIndividual("20/13/1984", "FR"));
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypeContract());
                delegate.getComplementaryInformation().add(createComplementaryInformationOfTypeOther());
                delegate.getComplementaryInformation().add(createComplementaryInforamtionOfTypeEmail("test@airfrance.airrr"));
                break;

        }

        accountDelegationDataRequest.getDelegate().add(delegate);

        request.setAccountDelegationDataRequest(accountDelegationDataRequest);

        try {
            return createOrUpdateIndividualImplV8.createIndividual(request);

        } catch (BusinessErrorBlocBusinessException e) {
            logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
            logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
            return null;
        }

    }

    /**
     * CONTEXT UM : KIDSOLO
     * REPIND-895
     */
    private ComplementaryInformation createComplementaryInformationOfTypePostalAddress(String city, String numberAndStreet, String zipCode) {
        ComplementaryInformation complementaryInformation = new ComplementaryInformation();
        complementaryInformation.setType(ComplementaryInformationTypeEnum.POSTAL_ADDRESS.getKey());

        ComplementaryInformationDatas complementaryInformationDatas = new ComplementaryInformationDatas();

        ComplementaryInformationData complementaryInformationData = new ComplementaryInformationData();
        complementaryInformationData.setKey(PostalAddressKeyEnum.NUMBER_AND_STREET.getKey());
        complementaryInformationData.setValue(numberAndStreet);
        complementaryInformationDatas.getComplementaryInformationData().add(complementaryInformationData);

        complementaryInformationData = new ComplementaryInformationData();
        complementaryInformationData.setKey(PostalAddressKeyEnum.CITY.getKey());
        complementaryInformationData.setValue(city);
        complementaryInformationDatas.getComplementaryInformationData().add(complementaryInformationData);

        complementaryInformationData = new ComplementaryInformationData();
        complementaryInformationData.setKey(PostalAddressKeyEnum.ZIPCODE.getKey());
        complementaryInformationData.setValue(zipCode);
        complementaryInformationDatas.getComplementaryInformationData().add(complementaryInformationData);

        complementaryInformation.setComplementaryInformationDatas(complementaryInformationDatas);

        return complementaryInformation;
    }

    /**
     * CONTEXT UM : KIDSOLO
     * REPIND-895
     */
    private ComplementaryInformation createComplementaryInformationOfTypeTelecom(String phoneNumber, String countryCode) {
        ComplementaryInformation complementaryInformation = new ComplementaryInformation();
        complementaryInformation.setType(ComplementaryInformationTypeEnum.PHONE.getKey());

        ComplementaryInformationDatas complementaryInformationDatas = new ComplementaryInformationDatas();

        ComplementaryInformationData complementaryInformationData = new ComplementaryInformationData();
        complementaryInformationData.setKey(TelecomKeyEnum.TERMINAL_TYPE.getKey());
        complementaryInformationData.setValue("M");
        complementaryInformationDatas.getComplementaryInformationData().add(complementaryInformationData);

        complementaryInformationData = new ComplementaryInformationData();
        complementaryInformationData.setKey(TelecomKeyEnum.COUNTRY_CODE.getKey());
        complementaryInformationData.setValue(countryCode);
        complementaryInformationDatas.getComplementaryInformationData().add(complementaryInformationData);

        complementaryInformationData = new ComplementaryInformationData();
        complementaryInformationData.setKey(TelecomKeyEnum.PHONE_NUMBER.getKey());
        complementaryInformationData.setValue(phoneNumber);
        complementaryInformationDatas.getComplementaryInformationData().add(complementaryInformationData);

        complementaryInformation.setComplementaryInformationDatas(complementaryInformationDatas);

        return complementaryInformation;
    }

    private ComplementaryInformation createComplementaryInformationOfTypeContract() {
        ComplementaryInformation complementaryInformation = new ComplementaryInformation();
        complementaryInformation.setType(ComplementaryInformationTypeEnum.CONTRACT.getKey());

        ComplementaryInformationDatas complementaryInformationDatas = new ComplementaryInformationDatas();

        ComplementaryInformationData complementaryInformationData = new ComplementaryInformationData();
        complementaryInformationData.setKey(ContractKeyEnum.FLYING_BLUE_NUMBER.getKey());
        complementaryInformationData.setValue("0123456789");
        complementaryInformationDatas.getComplementaryInformationData().add(complementaryInformationData);

        complementaryInformationData = new ComplementaryInformationData();
        complementaryInformationData.setKey(ContractKeyEnum.NUM_CONTRACT.getKey());
        complementaryInformationData.setValue("1111111111");
        complementaryInformationDatas.getComplementaryInformationData().add(complementaryInformationData);

        complementaryInformation.setComplementaryInformationDatas(complementaryInformationDatas);

        return complementaryInformation;
    }

    private ComplementaryInformation createComplementaryInforamtionOfTypeEmail(String email) {
        ComplementaryInformation complementaryInformation = new ComplementaryInformation();
        complementaryInformation.setType(ComplementaryInformationTypeEnum.EMAIL.getKey());

        ComplementaryInformationDatas complementaryInformationDatas = new ComplementaryInformationDatas();

        ComplementaryInformationData complementaryInformationData = new ComplementaryInformationData();
        complementaryInformationData.setKey(EmailKeyEnum.EMAIL_ADDRESS.getKey());
        complementaryInformationData.setValue(email);
        complementaryInformationDatas.getComplementaryInformationData().add(complementaryInformationData);

        complementaryInformation.setComplementaryInformationDatas(complementaryInformationDatas);

        return complementaryInformation;
    }

    private ComplementaryInformation createComplementaryInformationOfTypeOther() {
        ComplementaryInformation complementaryInformation = new ComplementaryInformation();
        complementaryInformation.setType(ComplementaryInformationTypeEnum.OTHER.getKey());

        ComplementaryInformationDatas complementaryInformationDatas = new ComplementaryInformationDatas();

        ComplementaryInformationData complementaryInformationData = new ComplementaryInformationData();
        complementaryInformationData.setKey(OtherKeyEnum.REMARK.getKey());
        complementaryInformationData.setValue("remark");
        complementaryInformationDatas.getComplementaryInformationData().add(complementaryInformationData);

        complementaryInformation.setComplementaryInformationDatas(complementaryInformationDatas);

        return complementaryInformation;
    }

    /**
     * CONTEXT UM : KIDSOLO
     * REPIND-895
     */
    private ComplementaryInformation createComplementaryInformationOfTypeIndividual(String birthday, String spokenLanguage) {
        ComplementaryInformation complementaryInformation = new ComplementaryInformation();
        complementaryInformation.setType(ComplementaryInformationTypeEnum.INDIVIDU.getKey());

        ComplementaryInformationDatas complementaryInformationDatas = new ComplementaryInformationDatas();

        ComplementaryInformationData complementaryInformationData = new ComplementaryInformationData();
        complementaryInformationData.setKey(IndividuKeyEnum.BIRTHDATE.getKey());
        complementaryInformationData.setValue(birthday);
        complementaryInformationDatas.getComplementaryInformationData().add(complementaryInformationData);

        complementaryInformationData = new ComplementaryInformationData();
        complementaryInformationData.setKey(IndividuKeyEnum.SPOKEN_LANGUAGE.getKey());
        complementaryInformationData.setValue(spokenLanguage);
        complementaryInformationDatas.getComplementaryInformationData().add(complementaryInformationData);

        complementaryInformation.setComplementaryInformationDatas(complementaryInformationDatas);

        return complementaryInformation;
    }

    /**
     * CONTEXT UM : KIDSOLO
     * REPIND-907
     *
     * @return
     */
    private CreateUpdateIndividualRequest createDelegateIndividual() {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        //Type K for kidSolo (UM)
        request.setProcess(ProcessEnum.K.getCode());

        //BALISE REQUESTOR
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setSite("QVI");
        requestor.setApplicationCode(APP_CODE);
        requestor.setSignature(SIGNATURE);

        request.setRequestor(requestor);

        //BALISE INDIVIDUAL_REQUEST
        IndividualRequest indRequest = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();

        indInfo.setCivility("MR");
        indInfo.setLastNameSC("XAVDEVELOP");
        indInfo.setFirstNameSC("KIDPRENOM");
        indInfo.setGender("M");

        indRequest.setIndividualInformations(indInfo);
        request.setIndividualRequest(indRequest);

        return request;
    }

    /**
     * CONTEXT UM : KIDSOLO
     * REPIND-907
     *
     * @return
     */
    private CreateUpdateIndividualRequest updateDelegateIndividual() {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        //Type K for kidSolo (UM)
        request.setProcess(ProcessEnum.K.getCode());

        //BALISE REQUESTOR
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setSite("QVI");
        requestor.setApplicationCode("W000442v8");
        requestor.setSignature("xxxx");
        request.setRequestor(requestor);

        //BALISE INDIVIDUAL_REQUEST
        IndividualRequest indRequest = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();

        indInfo.setIdentifier("940000001985");
        indInfo.setCivility("MR");
        indInfo.setLastNameSC("TOT");
        indInfo.setFirstNameSC("TESTTEST");
        indInfo.setBirthDate(new Date());
        indInfo.setStatus("V");
        indInfo.setVersion("1");
        indRequest.setIndividualInformations(indInfo);

        IndividualProfilV3 indProfil = new IndividualProfilV3();
        indProfil.setLanguageCode("EN");
        indProfil.setChildrenNumber("2");

        indRequest.setIndividualProfil(indProfil);

        request.setIndividualRequest(indRequest);

        return request;
    }

    /**
     * CONTEXT UM : KIDSOLO
     * REPIND-907
     */
    @Test
    @Transactional
    @Rollback(true)
    void testCreateOrUpdateAnIndividual_createIndividualData() {

        CreateUpdateIndividualRequest request = createDelegateIndividual();

        try {
            CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

            //TEST output
            Assertions.assertNotNull(response);
            Assertions.assertTrue(response.isSuccess());

            String gin = response.getGin();
            try {
                //TEST if the new individu exist
                IndividuDTO indDTO = individuDS.getByGin(gin);
                Assertions.assertNotNull(indDTO);
                Assertions.assertEquals("XAVDEVELOP", indDTO.getNom());
                Assertions.assertEquals("KIDPRENOM", indDTO.getPrenom());

                //TEST Profils
                Optional<Profils> profils = profilsRepository.findById(gin);
                Assertions.assertTrue(profils.isPresent());
                // SUppression du Profil depuis Vendredi 02/02/2018
                Assertions.assertNull(profils.get().getScode_langue());

            } catch (JrafDomainException e) {
//				e.printStackTrace();
                Assertions.fail();
            }
        } catch (BusinessErrorBlocBusinessException e) {
            Assertions.fail();
        }
    }

    /**
     * CONTEXT UM : KIDSOLO
     * REPIND-907
     */
    @Test
    @Transactional
    void testCreateOrUpdateAnIndividual_updateIndividualData() {

        CreateUpdateIndividualRequest request = updateDelegateIndividual();

        try {
            CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

            //TEST response
            Assertions.assertNotNull(response);
            Assertions.assertTrue(response.isSuccess());

            try {
                IndividuDTO indDTO = individuDS.getByGin(request.getIndividualRequest().getIndividualInformations().getIdentifier());

                Assertions.assertNotNull(indDTO);
                Assertions.assertEquals(indDTO.getProfilsdto().getScode_langue(), "EN");
                Assertions.assertEquals(indDTO.getNom(), "TOT");

            } catch (JrafDomainException e) {
                e.printStackTrace();
            }
        } catch (BusinessErrorBlocBusinessException e) {
            e.printStackTrace();
        }
    }

    /**
     * CONTEXT UM : KIDSOLO
     * REPIND-907
     *
     * @return
     */
    private CreateUpdateIndividualRequest createForgetMeDemandAsked(String gin) {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        //Type K for kidSolo (UM)
        request.setProcess(ProcessEnum.I.getCode());

        //BALISE REQUESTOR
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setSite("QVI");
        requestor.setApplicationCode("W000442v8");
        requestor.setSignature("xxxx");
        request.setRequestor(requestor);
        requestor.setContext(ContextEnum.FA.getLibelle());

        //BALISE INDIVIDUAL_REQUEST
        IndividualRequest indRequest = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();

        indInfo.setIdentifier(gin);
        indInfo.setCivility("MR");
        indInfo.setLastNameSC("TOT");
        indInfo.setFirstNameSC("TESTTESTFF");
        indInfo.setBirthDate(new Date());
        indInfo.setStatus("V");
        indInfo.setVersion("1");
        indRequest.setIndividualInformations(indInfo);

        IndividualProfilV3 indProfil = new IndividualProfilV3();
        indProfil.setLanguageCode("EN");
        indProfil.setChildrenNumber("2");

        indRequest.setIndividualProfil(indProfil);

        request.setIndividualRequest(indRequest);

        return request;
    }

    /*
     * Return a gin with communicationPreference subscribe = Y
     */
    private String getGinWithComPreferenceSubcribeY() {
        GIN = communicationPreferenceDS.getGinWithComPrefSubscribeY();
        return GIN;
    }

    /**
     * Create a "forget me" request with a individual existing in our database
     * and possessing a or a lot of communicationPreferences
     *
     * @result the response have a status success
     */
    @Test
    @Transactional
    @Rollback(true)
    void testCreateOrUpdateAnIndividual_forgetMeAsked() {
        CreateUpdateIndividualRequest request = createForgetMeDemandAsked(GIN_FORGET_A);
        try {
            CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
            Assertions.assertTrue(response.isSuccess());
        } catch (BusinessErrorBlocBusinessException e) {
            Assertions.fail();
        }
    }


    /**
     * TODO : PRE-REQUIS
     * Create a "forget me" request with a gin already existing
     * in our data table FORGOTTEN_INDIVIDUAL
     *
     * @result this method will be return a BusinessErrorCode.
     * BusinessErrorCode <code>ERROR_248</code>
     */
    @Test
    void testCreateOrUpdateAnIndividual_forgetMeAskedDemandExist() {
        CreateUpdateIndividualRequest request = createForgetMeDemandAsked(GIN);
        try {
            CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
        } catch (BusinessErrorBlocBusinessException e) {
            //TODO : change the error code by 248 : requestAlreadyDoneException
            Assertions.assertEquals(e.getFaultInfo().getBusinessError().getOtherErrorCode(), "ERROR_248");
        }
    }

    /**
     * Create a "forget me" request with a gin not existing
     *
     * @result this method will be return a BusinessErrorCode.
     * BusinessErrorCode <code>ERROR_001</code>
     */
    @Test
    void testCreateOrUpdateAnIndividual_forgetMeAsked_GinNotFound() {
        CreateUpdateIndividualRequest request = createForgetMeDemandAsked("000000000000");
        try {
            CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
            Assertions.fail();
        } catch (BusinessErrorBlocBusinessException e) {
            Assertions.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_001);
        }
    }

    /**
     * Create a "forget me" request with a gin null
     *
     * @result this method will be return a BusinessErrorCode.
     * BusinessErrorCode <code>ERROR_133</code>
     */
    @Test
    void testCreateOrUpdateAnIndividual_forgetMeAsked_Null() {
        CreateUpdateIndividualRequest request = createForgetMeDemandAsked("");
        try {
            CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
            Assertions.fail();
        } catch (BusinessErrorBlocBusinessException e) {
            Assertions.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_133);
        }
    }

    private CreateUpdateIndividualRequest createIndividualWithStatutF() {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        //Type K for kidSolo (UM)
        request.setProcess(ProcessEnum.I.getCode());

        //BALISE REQUESTOR
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setSite("QVI");
        requestor.setApplicationCode("W000442v8");
        requestor.setSignature("xxxx");
        request.setRequestor(requestor);
        requestor.setContext(ContextEnum.FA.getLibelle());

        //BALISE INDIVIDUAL_REQUEST
        IndividualRequest indRequest = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();

        indInfo.setIdentifier("110000019801");
        indInfo.setCivility("MR");
        indInfo.setLastNameSC("TOT");
        indInfo.setFirstNameSC("TESTTEST");
        indInfo.setBirthDate(new Date());
        // statut F is not permitted
        indInfo.setStatus("F");
        indInfo.setVersion("1");
        indRequest.setIndividualInformations(indInfo);

        IndividualProfilV3 indProfil = new IndividualProfilV3();
        indProfil.setLanguageCode("EN");
        indProfil.setChildrenNumber("2");

        indRequest.setIndividualProfil(indProfil);

        request.setIndividualRequest(indRequest);

        return request;
    }

    /**
     * Create a "create individual" request with a statut F
     *
     * @result this method will be return a BusinessErrorCode
     * BusinessErrorCode <code>ERROR_932</code>
     */
    @Test
    void testCreateOrUpdateAnIndividual_withStatutF() {
        CreateUpdateIndividualRequest request = createIndividualWithStatutF();
        try {
            CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
            Assertions.fail();
        } catch (BusinessErrorBlocBusinessException e) {
            Assertions.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERROR_932);

        }
    }

    @Test
    @Rollback(true)
    void testCreateOrUpdateIndividual_createIndividualWithEmail() {
        CreateUpdateIndividualRequest request = initRequestForIndividualCreation();
        CreateUpdateIndividualResponse response;

        // *** Set email to create ***
        EmailRequest emailReq = new EmailRequest();
        Email emailBloc = new Email();

        String mail = generateEmail();
        emailBloc.setEmail(mail);
        emailBloc.setEmailOptin("T");
        emailBloc.setMediumCode("D");
        emailBloc.setMediumStatus("V");

        emailReq.setEmail(emailBloc);
        request.getEmailRequest().add(emailReq);
        logger.info("** Expected email = " + mail + " **");

        try {
            response = createOrUpdateIndividualImplV8.createIndividual(request);
            Assertions.assertNotNull(response);
            Assertions.assertNotNull(response.getGin());

            String createdGin = response.getGin();
            List<EmailDTO> mailList = emailDS.findEmail(createdGin);

            Assertions.assertTrue(mailList.size() == 1);
            Assertions.assertEquals(mail, mailList.get(0).getEmail());
            logger.info("** Email created = " + mailList.get(0).getEmail() + " **");

        } catch (BusinessErrorBlocBusinessException e) {
            Assertions.fail();
        } catch (JrafDomainException e) {
            Assertions.fail();
        }
    }

    @Test
    @Rollback(true)
    void testCreateOrUpdateIndividual_updateIndividualEmailSuccess() {
        CreateUpdateIndividualRequest request = initRequestForIndividualCreation();
        CreateUpdateIndividualResponse responseCreate;
        String gin = null;

        // Create Individual and email
        EmailRequest emailReq = new EmailRequest();
        Email emailBloc = new Email();

        String mail = generateEmail();
        emailBloc.setEmail(mail);
        emailBloc.setEmailOptin("T");
        emailBloc.setMediumCode("D");
        emailBloc.setMediumStatus("V");

        emailReq.setEmail(emailBloc);
        request.getEmailRequest().add(emailReq);

        try {
            responseCreate = createOrUpdateIndividualImplV8.createIndividual(request);
            gin = responseCreate.getGin();
        } catch (BusinessErrorBlocBusinessException e) {
            Assertions.fail();
        }

        // Input for update
        CreateUpdateIndividualRequest requestUpd = new CreateUpdateIndividualRequest();
        CreateUpdateIndividualResponse responseUpd;

        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);

        requestUpd.setRequestor(requestor);

        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setIdentifier(gin);

        indRequest.setIndividualInformations(indInfo);
        requestUpd.setIndividualRequest(indRequest);

        EmailRequest emailReqUpd = new EmailRequest();
        Email emailBlocUpd = new Email();

        String mailUpd = generateEmail();
        emailBlocUpd.setEmail(mailUpd);
        emailBlocUpd.setEmailOptin("T");
        emailBlocUpd.setMediumCode("D");
        emailBlocUpd.setMediumStatus("V");

        emailReqUpd.setEmail(emailBlocUpd);
        requestUpd.getEmailRequest().add(emailReqUpd);

        try {
            responseUpd = createOrUpdateIndividualImplV8.createIndividual(requestUpd);
            Assertions.assertTrue(responseUpd.isSuccess());

            List<EmailDTO> mailList = emailDS.findEmail(gin);
            logger.info("** Expected email = " + mailUpd + " **");
            logger.info("** Email found in DB = " + mailList.get(0).getEmail() + " **");

            Assertions.assertTrue(mailList.size() == 1);
            Assertions.assertEquals(mailUpd, mailList.get(0).getEmail());

        } catch (BusinessErrorBlocBusinessException e) {
            Assertions.fail();
        } catch (JrafDomainException e) {
            Assertions.fail();
        }
    }

    /**
     * Set minimum field for creation of individual
     * Requestor
     * Individual
     * PostalAddress
     *
     * @return request CreateUpdateIndividualRequest initialized to be completed
     */
    private CreateUpdateIndividualRequest initRequestForIndividualCreation() {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // **********  Requestor ****************
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);
        requestor.setApplicationCode(APP_CODE);
        request.setRequestor(requestor);

        // ********** Individual **************
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setBirthDate(new Date());
        indInfo.setCivility("MR");
        indInfo.setStatus("V");
        indInfo.setLastNameSC("NOUVEL");
        indInfo.setFirstNameSC("INDIVIDU");

        indRequest.setIndividualInformations(indInfo);
        request.setIndividualRequest(indRequest);

        // ********** Postal Address (Mandatory for creation) **********
        PostalAddressRequest adrPost = new PostalAddressRequest();
        PostalAddressContent adrCont = new PostalAddressContent();
        PostalAddressProperties adrProp = new PostalAddressProperties();

        adrCont.setNumberAndStreet("Fake");
        adrCont.setCity("Fake");
        adrCont.setZipCode("99999");
        adrCont.setCountryCode("ZZ");

        adrProp.setIndicAdrNorm(false);
        adrProp.setMediumCode("D");
        adrProp.setMediumStatus("V");

        adrPost.setPostalAddressContent(adrCont);
        adrPost.setPostalAddressProperties(adrProp);
        request.getPostalAddressRequest().add(adrPost);

        return request;
    }


    // REPIND-1288 : Store Email as Minus in database

    @Test
    void testCreateOrUpdateIndividual_Prospect_CreateNewProspectEmailUPPER() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

        /*** Set parameters ***/
        // Requestor
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setContext("B2C_HOME_PAGE");
        requestor.setApplicationCode("ISI");
        requestor.setSite("QVI");
        requestor.setSignature("TU_RI");

        // Email
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();

        String emailUpper = generateEmail().toUpperCase();
        email.setEmail(emailUpper);
        emailRequest.setEmail(email);

        // Communication Preference
        ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
        CommunicationPreferences comPref = new CommunicationPreferences();
        comPref.setDomain("S");
        comPref.setCommunicationGroupeType("N");
        comPref.setCommunicationType("AF");
        comPref.setOptIn("N");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            comPref.setDateOfConsent(df.parse("30-08-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set date of consent");
        }
        comPref.setSubscriptionChannel("B2C Home Page");

        MarketLanguage ml = new MarketLanguage();
        ml.setLanguage(LanguageCodesEnum.FR);
        ml.setMarket("FR");
        ml.setOptIn("N");
        try {
            ml.setDateOfConsent(df.parse("01-09-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set market language date of consent");
        }

        comPref.getMarketLanguage().add(ml);
        comPrefrequest.setCommunicationPreferences(comPref);

        request.setRequestor(requestor);
        request.getEmailRequest().add(emailRequest);
        request.getComunicationPreferencesRequest().add(comPrefrequest);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        List<EmailDTO> listEmails = emailDS.findEmail(response.getGin());
        Assertions.assertNotEquals(emailUpper, listEmails.get(0).getEmail());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Gin updated: " + response.getGin());
        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test");
    }


    @Test
    void testCreateOrUpdateIndividual_Prospect_CreateNewTravelerEmailUPPER() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        request.setProcess(ProcessEnum.T.getCode());

        /*** Set parameters ***/
        // Requestor
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setContext("B2C_HOME_PAGE");
        requestor.setApplicationCode("ISI");
        requestor.setSite("QVI");
        requestor.setSignature("TU_RI");

        // Individual
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setBirthDate(new Date());
        indInfo.setCivility("MR");
        indInfo.setStatus("V");
        indInfo.setGender("M");
        indInfo.setLastNameSC(generateString());
        indInfo.setFirstNameSC(generateString());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateInString = "0014-10-05T12:00:00";
        Date date;
        try {
            date = formatter.parse(dateInString);
            indInfo.setBirthDate(date);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }

        indRequest.setIndividualInformations(indInfo);


        // Email
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();

        String emailUpper = generateEmail().toUpperCase();
        email.setEmail(emailUpper);
        email.setMediumCode("D");
        email.setMediumStatus("V");

        emailRequest.setEmail(email);


        request.setRequestor(requestor);
        request.getEmailRequest().add(emailRequest);
        request.setIndividualRequest(indRequest);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        List<EmailDTO> listEmails = emailDS.findEmail(response.getGin());
        Assertions.assertNotEquals(emailUpper, listEmails.get(0).getEmail());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Gin updated: " + response.getGin());
        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test");
    }

    @Test
    @Rollback
    void testCreateOrUpdateIndividual_Prospect_CreateNewIndividualEmailUPPER() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        request.setProcess(ProcessEnum.I.getCode());

        /*** Set parameters ***/
        // Requestor
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setContext("B2C_HOME_PAGE");
        requestor.setApplicationCode("ISI");
        requestor.setSite("QVI");
        requestor.setSignature("TU_RI");

        // Individual
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        // indInfo.setBirthDate(new Date( ) );
        indInfo.setCivility("MR");
        indInfo.setStatus("V");
        indInfo.setLastNameSC(generateString());
        indInfo.setFirstNameSC(generateString());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateInString = "2014-10-05T12:00:00";
        Date date;
        try {
            date = formatter.parse(dateInString);
            indInfo.setBirthDate(date);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }

        indRequest.setIndividualInformations(indInfo);

        // Postal Address
        PostalAddressRequest addRequest = new PostalAddressRequest();
        PostalAddressContent pac = new PostalAddressContent();
        pac.setNumberAndStreet("Baronielaan 229");
        pac.setZipCode("4835JK");
        pac.setCity("Breda");
        pac.setCountryCode("NL");
        pac.setAdditionalInformation("");
        pac.setStateCode("");
        pac.setCorporateName("");
        addRequest.setPostalAddressContent(pac);

        PostalAddressProperties pap = new PostalAddressProperties();
        pap.setMediumCode(MediumCodeEnum.BUSINESS.toString());
        pap.setMediumStatus("V");
        // pap.setVersion("1");
        pap.setIndicAdrNorm(true);    // On force l'adresse
        addRequest.setPostalAddressProperties(pap);


        // Email
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();
        String emailUpper = generateEmail().toUpperCase();
        email.setEmail(emailUpper);
        email.setMediumCode("D");
        email.setMediumStatus("V");
        emailRequest.setEmail(email);

        request.setRequestor(requestor);
        request.getEmailRequest().add(emailRequest);
        request.setIndividualRequest(indRequest);
        request.getPostalAddressRequest().add(addRequest);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        List<EmailDTO> listEmails = emailDS.findEmail(response.getGin());
        Assertions.assertNotEquals(emailUpper, listEmails.get(0).getEmail());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Gin updated: " + response.getGin());
        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test");
    }

    @Test
    @Rollback
    void testCreateOrUpdateIndividual_Prospect_UpdateIndividualEmailUPPER() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        request.setProcess(ProcessEnum.I.getCode());

        /*** Set parameters ***/
        // Requestor
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setContext("B2C_HOME_PAGE");
        requestor.setApplicationCode("ISI");
        requestor.setSite("QVI");
        requestor.setSignature("TU_RI");

        // Individual
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        // indInfo.setBirthDate(new Date( ) );
        indInfo.setCivility("MR");
        indInfo.setStatus("V");
        indInfo.setGender("M");
        indInfo.setLastNameSC(generateString());
        indInfo.setFirstNameSC(generateString());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateInString = "2014-10-05T12:00:00";
        Date date;
        try {
            date = formatter.parse(dateInString);
            indInfo.setBirthDate(date);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }

        indRequest.setIndividualInformations(indInfo);

        // Postal Address
        PostalAddressRequest addRequest = new PostalAddressRequest();
        PostalAddressContent pac = new PostalAddressContent();
        pac.setNumberAndStreet("Baronielaan 229");
        pac.setZipCode("4835JK");
        pac.setCity("Breda");
        pac.setCountryCode("NL");
        pac.setAdditionalInformation("");
        pac.setStateCode("");
        addRequest.setPostalAddressContent(pac);

        PostalAddressProperties pap = new PostalAddressProperties();
        pap.setMediumCode(MediumCodeEnum.BUSINESS.toString());
        pap.setMediumStatus("V");
        // pap.setVersion("1");
        pap.setIndicAdrNorm(true);    // On force l'adresse
        addRequest.setPostalAddressProperties(pap);


        // Email
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();
        String emailUpper = generateEmail().toUpperCase();
        email.setEmail(emailUpper);
        email.setMediumCode("D");
        email.setMediumStatus("V");
        emailRequest.setEmail(email);

        request.setRequestor(requestor);
        request.getEmailRequest().add(emailRequest);
        request.setIndividualRequest(indRequest);
        request.getPostalAddressRequest().add(addRequest);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        String gin = response.getGin();
        List<EmailDTO> listEmails = emailDS.findEmail(gin);
        Assertions.assertNotEquals(emailUpper, listEmails.get(0).getEmail());

        /* -- UPDATE -- */
        CreateUpdateIndividualRequest requestUpdate = new CreateUpdateIndividualRequest();
        requestUpdate.setProcess(ProcessEnum.I.getCode());

        IndividualRequest indRequestUpdate = new IndividualRequest();
        IndividualInformationsV3 indInfoUpdate = new IndividualInformationsV3();
        indInfoUpdate.setIdentifier(response.getGin());
        indRequestUpdate.setIndividualInformations(indInfoUpdate);

        // Email
        EmailRequest emailRequestUpdate = new EmailRequest();
        Email emailUpdate = new Email();
        String emailUpperUpdate = "UPDATE_" + listEmails.get(0).getEmail();
        emailUpdate.setEmail(emailUpperUpdate);
        emailUpdate.setMediumCode("D");
        emailUpdate.setMediumStatus("V");
        emailRequestUpdate.setEmail(emailUpdate);

        requestUpdate.setRequestor(requestor);
        requestUpdate.setIndividualRequest(indRequestUpdate);
        requestUpdate.getEmailRequest().add(emailRequestUpdate);

        // Execute test
        response = createOrUpdateIndividualImplV8.createIndividual(requestUpdate);

        listEmails = emailDS.findEmail(gin);
        Assertions.assertNotEquals(emailUpperUpdate, listEmails.get(0).getEmail());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Gin updated: " + gin);
        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test");
    }

    @Test
    void testCreateOrUpdateIndividual_Prospect_UpdateTravelerEmailUPPER() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        request.setProcess(ProcessEnum.T.getCode());

        /*** Set parameters ***/
        // Requestor
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setContext("B2C_HOME_PAGE");
        requestor.setApplicationCode("ISI");
        requestor.setSite("QVI");
        requestor.setSignature("TU_RI");

        // Individual
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setBirthDate(new Date());
        indInfo.setCivility("MR");
        indInfo.setStatus("V");
        indInfo.setGender("M");
        indInfo.setLastNameSC(generateString());
        indInfo.setFirstNameSC(generateString());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateInString = "0014-10-05T12:00:00";
        Date date;
        try {
            date = formatter.parse(dateInString);
            indInfo.setBirthDate(date);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }

        indRequest.setIndividualInformations(indInfo);


        // Email
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();

        String emailUpper = generateEmail().toUpperCase();
        email.setEmail(emailUpper);
        email.setMediumCode("D");
        email.setMediumStatus("V");

        emailRequest.setEmail(email);


        request.setRequestor(requestor);
        request.getEmailRequest().add(emailRequest);
        request.setIndividualRequest(indRequest);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        String gin = response.getGin();
        List<EmailDTO> listEmails = emailDS.findEmail(gin);
        Assertions.assertNotEquals(emailUpper, listEmails.get(0).getEmail());

        /* -- UPDATE -- */
        CreateUpdateIndividualRequest requestUpdate = new CreateUpdateIndividualRequest();
        requestUpdate.setProcess(ProcessEnum.T.getCode());

        IndividualRequest indRequestUpdate = new IndividualRequest();
        IndividualInformationsV3 indInfoUpdate = new IndividualInformationsV3();
        indInfoUpdate.setIdentifier(response.getGin());
        indRequestUpdate.setIndividualInformations(indInfoUpdate);

        // Email
        EmailRequest emailRequestUpdate = new EmailRequest();
        Email emailUpdate = new Email();
        String emailUpperUpdate = "UPDATE_" + listEmails.get(0).getEmail();
        emailUpdate.setEmail(emailUpperUpdate);
        emailUpdate.setMediumCode("D");
        emailUpdate.setMediumStatus("V");
        emailRequestUpdate.setEmail(emailUpdate);

        requestUpdate.setRequestor(requestor);
        requestUpdate.setIndividualRequest(indRequestUpdate);
        requestUpdate.getEmailRequest().add(emailRequestUpdate);

        // Execute test
        response = createOrUpdateIndividualImplV8.createIndividual(requestUpdate);

        listEmails = emailDS.findEmail(gin);
        Assertions.assertNotEquals(emailUpperUpdate, listEmails.get(0).getEmail());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Gin updated: " + gin);
        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test");
    }

    @Test
    void testCreateOrUpdateIndividual_Prospect_UpdateProspectEmailUPPER() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

        /*** Set parameters ***/
        // Requestor
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setContext("B2C_HOME_PAGE");
        requestor.setApplicationCode("ISI");
        requestor.setSite("QVI");
        requestor.setSignature("TU_RI");

        // Email
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();

        String emailUpper = generateEmail().toUpperCase();
        email.setEmail(emailUpper);
        emailRequest.setEmail(email);

        // Communication Preference
        ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
        CommunicationPreferences comPref = new CommunicationPreferences();
        comPref.setDomain("S");
        comPref.setCommunicationGroupeType("N");
        comPref.setCommunicationType("AF");
        comPref.setOptIn("N");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            comPref.setDateOfConsent(df.parse("30-08-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set date of consent");
        }
        comPref.setSubscriptionChannel("B2C Home Page");

        MarketLanguage ml = new MarketLanguage();
        ml.setLanguage(LanguageCodesEnum.FR);
        ml.setMarket("FR");
        ml.setOptIn("N");
        try {
            ml.setDateOfConsent(df.parse("01-09-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set market language date of consent");
        }

        comPref.getMarketLanguage().add(ml);
        comPrefrequest.setCommunicationPreferences(comPref);

        request.setRequestor(requestor);
        request.getEmailRequest().add(emailRequest);
        request.getComunicationPreferencesRequest().add(comPrefrequest);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        entityManager.flush();
        entityManager.clear();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        String gin = response.getGin();
        List<EmailDTO> listEmails = emailDS.findEmail(gin);
        Assertions.assertNotEquals(emailUpper, listEmails.get(0).getEmail());

        /* -- UPDATE -- */
        CreateUpdateIndividualRequest requestUpdate = new CreateUpdateIndividualRequest();
        requestUpdate.setProcess(ProcessEnum.W.getCode());

        IndividualRequest indRequestUpdate = new IndividualRequest();
        IndividualInformationsV3 indInfoUpdate = new IndividualInformationsV3();
        indInfoUpdate.setIdentifier(response.getGin());
        indRequestUpdate.setIndividualInformations(indInfoUpdate);

        // Email
        EmailRequest emailRequestUpdate = new EmailRequest();
        Email emailUpdate = new Email();
        String emailUpperUpdate = "UPDATE_" + listEmails.get(0).getEmail();
        emailUpdate.setEmail(emailUpperUpdate);
        emailUpdate.setMediumCode("D");
        emailUpdate.setMediumStatus("V");
        emailRequestUpdate.setEmail(emailUpdate);

        requestUpdate.setRequestor(requestor);
        requestUpdate.setIndividualRequest(indRequestUpdate);
        requestUpdate.getEmailRequest().add(emailRequestUpdate);

        // Execute test
        response = createOrUpdateIndividualImplV8.createIndividual(requestUpdate);

        listEmails = emailDS.findEmail(gin);
        Assertions.assertNotEquals(emailUpperUpdate, listEmails.get(0).getEmail());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Gin updated: " + gin);
        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test");
    }

    @Test
    void testCreateOrUpdateIndividual_Prospect_UpdateProspectCivilityProvided() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        /* -- Create -- */
        CreateUpdateIndividualRequest request = buildRequestProspect();
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        entityManager.flush();
        entityManager.clear();

        String gin = response.getGin();

        /* -- UPDATE -- */
        CreateUpdateIndividualRequest requestUpdate = new CreateUpdateIndividualRequest();
        requestUpdate.setProcess(ProcessEnum.W.getCode());

        IndividualRequest indRequestUpdate = new IndividualRequest();
        IndividualInformationsV3 indInfoUpdate = new IndividualInformationsV3();
        indInfoUpdate.setIdentifier(gin);
        indInfoUpdate.setCivility(CivilityEnum.MISTER.toString());
        indRequestUpdate.setIndividualInformations(indInfoUpdate);

        requestUpdate.setRequestor(request.getRequestor());
        requestUpdate.setIndividualRequest(indRequestUpdate);

        // Execute test
        response = createOrUpdateIndividualImplV8.createIndividual(requestUpdate);
        Individu ind = individuRepository.findBySgin(gin);

        Assertions.assertEquals(GenderEnum.MALE.toString(), ind.getSexe());
        Assertions.assertEquals(CivilityEnum.MISTER.toString(), ind.getCivilite());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Gin updated: " + gin);
        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test");
    }

    @Test
    void testCreateOrUpdateIndividual_Prospect_UpdateProspectGenderProvided() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        /* -- Create -- */
        CreateUpdateIndividualRequest request = buildRequestProspect();
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        entityManager.flush();
        entityManager.clear();


        String gin = response.getGin();

        /* -- UPDATE -- */
        CreateUpdateIndividualRequest requestUpdate = new CreateUpdateIndividualRequest();
        requestUpdate.setProcess(ProcessEnum.W.getCode());

        IndividualRequest indRequestUpdate = new IndividualRequest();
        IndividualInformationsV3 indInfoUpdate = new IndividualInformationsV3();
        indInfoUpdate.setIdentifier(gin);
        indInfoUpdate.setGender(GenderEnum.FEMALE.toString());
        indRequestUpdate.setIndividualInformations(indInfoUpdate);

        requestUpdate.setRequestor(request.getRequestor());
        requestUpdate.setIndividualRequest(indRequestUpdate);

        // Execute test
        response = createOrUpdateIndividualImplV8.createIndividual(requestUpdate);
        Individu ind = individuRepository.findBySgin(gin);

        Assertions.assertEquals(GenderEnum.FEMALE.toString(), ind.getSexe());
        Assertions.assertEquals(CivilityEnum.MRS.toString(), ind.getCivilite());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Gin updated: " + gin);
        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test");
    }

    /**
     * Test update on an individual with social data network
     *
     * @throws BusinessErrorBlocBusinessException
     * @throws JrafDomainException
     */
    @Test
    @Transactional
    @Rollback(true)
    void testCreateOrUpdateIndividual_UpdateIndividualWithSocialNetwork()
            throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start...");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Individual
        IndividualRequest indReq = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();

        // Get individual with social network data
        indInfo.setIdentifier("400447405141");
        indReq.setIndividualInformations(indInfo);
        indInfo.setFirstNameSC("Test");
        indInfo.setLastNameSC("Test");
        request.setIndividualRequest(indReq);

        // Requestor
        RequestorV2 req = new RequestorV2();

        req.setChannel("B2C");
        req.setSite("QVI");
        req.setSignature("Test U");
        request.setRequestor(req);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Test results
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    @Transactional
    @Rollback(true)
    void testCreateOrUpdateIndividual_UpdateIndividualNullPointer() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start...");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Individual
        IndividualRequest indReq = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();

        // Get individual
        indInfo.setIdentifier("400274751493");
        indReq.setIndividualInformations(indInfo);
        indInfo.setStatus("V");
        indInfo.setCivility("MISS");
        indInfo.setFirstNameSC("NAOMI");
        indInfo.setLastNameSC("BARCLAIS");
        indInfo.setLanguageCode("EN");

//		IndividualProfilV3 indProfil = new IndividualProfilV3();
//		indProfil.setLanguageCode("EN");
//		indProfil.setChildrenNumber("2");
//		indReq.setIndividualProfil(indProfil);

        request.setIndividualRequest(indReq);

        // Email
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();

        email.setEmail("ruth.valentine@talk21.com");
        email.setMediumCode("D");
        email.setMediumStatus("V");
        email.setEmailOptin("N");
        emailRequest.setEmail(email);
        request.getEmailRequest().add(emailRequest);

        // Telecom
        TelecomRequest telRequest = new TelecomRequest();
        Telecom telecom = new Telecom();
        telecom.setCountryCode("GB");
        telecom.setMediumCode(MediumCodeEnum.HOME.toString());
        telecom.setMediumStatus("V");
        telecom.setTerminalType(TerminalTypeEnum.MOBILE.toString());
        telecom.setPhoneNumber("07743667121");
        telRequest.setTelecom(telecom);
        request.getTelecomRequest().add(telRequest);

        // Requestor
        RequestorV2 req = new RequestorV2();

        req.setChannel("B2C");
        req.setSite("QVI");
        req.setSignature("Test U");
        request.setRequestor(req);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Test results
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isSuccess());
    }

    // REPIND-1659 : Check LANGUAGE before insert individual
    @Test
    void testCreateOrUpdateIndividual_createIndividualWithIncorrectLanguage() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setCivility("M.");
        indInfo.setLastNameSC(generateString());
        indInfo.setFirstNameSC(generateString());
        indInfo.setStatus("V");
        indInfo.setLanguageCode("EN");
        indRequest.setIndividualInformations(indInfo);

        IndividualProfilV3 ip = new IndividualProfilV3();
        ip.setEmailOptin("N");
        ip.setLanguageCode("US");

        indRequest.setIndividualProfil(ip);
        request.setIndividualRequest(indRequest);

        // Email address
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();
        email.setEmail(generateEmail());
        email.setMediumCode("D");
        email.setMediumStatus("V");
        email.setEmailOptin("N");
        emailRequest.setEmail(email);
        request.getEmailRequest().add(emailRequest);

        // Postal Address
        PostalAddressRequest addRequest = new PostalAddressRequest();
        PostalAddressContent pac = new PostalAddressContent();
        pac.setNumberAndStreet("not collected");
        pac.setZipCode("99999");
        pac.setCity("not collected");
        pac.setCountryCode("FR");
        pac.setAdditionalInformation("not collected");
        pac.setStateCode("ZZ");
        addRequest.setPostalAddressContent(pac);

        PostalAddressProperties pap = new PostalAddressProperties();
        pap.setMediumCode("D");        // Direct
        pap.setMediumStatus("I");    // Invalid
        pap.setIndicAdrNorm(true);    // On force l'adresse
        addRequest.setPostalAddressProperties(pap);

        UsageAddress ua = new UsageAddress();
        ua.setUsageNumber("1");
        ua.setApplicationCode("ISI");
        ua.setAddressRoleCode("M");
        addRequest.setUsageAddress(ua);

        request.getPostalAddressRequest().add(addRequest);


        // WS call
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());

        Assertions.assertNotNull(response.getInformationResponse());
        Assertions.assertNotNull(response.getGin());
        Assertions.assertNotEquals(0, response.getInformationResponse());

        // Check in DATABASE
        Profils p = profilsRepository.findProfilsByGin(response.getGin());
        Assertions.assertNotNull(p.getScode_langue());
        Assertions.assertEquals("FR", p.getScode_langue());                    // ON check que le "US" a ete remplace par "FR"
    }

    @Test
    void testCreateOrUpdateIndividual_createIndividualWithNoLanguage() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setCivility("M.");
        indInfo.setLastNameSC(generateString());
        indInfo.setFirstNameSC(generateString());
        indInfo.setStatus("V");
        indInfo.setLanguageCode("EN");
        indRequest.setIndividualInformations(indInfo);

        IndividualProfilV3 ip = new IndividualProfilV3();
        ip.setEmailOptin("N");
        ip.setLanguageCode(null);

        indRequest.setIndividualProfil(ip);
        request.setIndividualRequest(indRequest);

        // Email address
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();
        email.setEmail(generateEmail());
        email.setMediumCode("D");
        email.setMediumStatus("V");
        email.setEmailOptin("N");
        emailRequest.setEmail(email);
        request.getEmailRequest().add(emailRequest);

        // Postal Address
        PostalAddressRequest addRequest = new PostalAddressRequest();
        PostalAddressContent pac = new PostalAddressContent();
        pac.setNumberAndStreet("not collected");
        pac.setZipCode("99999");
        pac.setCity("not collected");
        pac.setCountryCode("FR");
        pac.setAdditionalInformation("not collected");
        pac.setStateCode("ZZ");
        addRequest.setPostalAddressContent(pac);

        PostalAddressProperties pap = new PostalAddressProperties();
        pap.setMediumCode("D");        // Direct
        pap.setMediumStatus("I");    // Invalid
        pap.setIndicAdrNorm(true);    // On force l'adresse
        addRequest.setPostalAddressProperties(pap);

        UsageAddress ua = new UsageAddress();
        ua.setUsageNumber("1");
        ua.setApplicationCode("ISI");
        ua.setAddressRoleCode("M");
        addRequest.setUsageAddress(ua);

        request.getPostalAddressRequest().add(addRequest);


        // WS call
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());

        Assertions.assertNotNull(response.getInformationResponse());
        Assertions.assertNotNull(response.getGin());
        Assertions.assertNotEquals(0, response.getInformationResponse());

        // Check in DATABASE
        Profils p = profilsRepository.findProfilsByGin(response.getGin());
        Assertions.assertNotNull(p.getScode_langue());
        Assertions.assertEquals("FR", p.getScode_langue());                    // ON check que la langue a ete positionné par defaut avec FR"
    }

    @Test
    void testCreateOrUpdateIndividual_createIndividualWithEmptyLanguage() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
//			indInfo.setIdentifier("900029595554");
        indInfo.setCivility("M.");
        indInfo.setLastNameSC(generateString());
        indInfo.setFirstNameSC(generateString());
        indInfo.setStatus("V");
        indInfo.setLanguageCode("EN");
        indRequest.setIndividualInformations(indInfo);

        IndividualProfilV3 ip = new IndividualProfilV3();
        ip.setEmailOptin("N");
        ip.setLanguageCode("");

        indRequest.setIndividualProfil(ip);
        request.setIndividualRequest(indRequest);

        // Email address
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();
        email.setEmail(generateEmail());
        email.setMediumCode("D");
        email.setMediumStatus("V");
        email.setEmailOptin("N");
        emailRequest.setEmail(email);
        request.getEmailRequest().add(emailRequest);

        // Postal Address
        PostalAddressRequest addRequest = new PostalAddressRequest();
        PostalAddressContent pac = new PostalAddressContent();
        pac.setNumberAndStreet("not collected");
        pac.setZipCode("99999");
        pac.setCity("not collected");
        pac.setCountryCode("FR");
        pac.setAdditionalInformation("not collected");
        pac.setStateCode("ZZ");
        addRequest.setPostalAddressContent(pac);

        PostalAddressProperties pap = new PostalAddressProperties();
        pap.setMediumCode("D");        // Direct
        pap.setMediumStatus("I");    // Invalid
        pap.setIndicAdrNorm(true);    // On force l'adresse
        addRequest.setPostalAddressProperties(pap);

        UsageAddress ua = new UsageAddress();
        ua.setUsageNumber("1");
        ua.setApplicationCode("ISI");
        ua.setAddressRoleCode("M");
        addRequest.setUsageAddress(ua);

        request.getPostalAddressRequest().add(addRequest);


        // WS call
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());

        Assertions.assertNotNull(response.getInformationResponse());
        Assertions.assertNotNull(response.getGin());
        Assertions.assertNotEquals(0, response.getInformationResponse());

        // Check in DATABASE
        Profils p = profilsRepository.findProfilsByGin(response.getGin());
        Assertions.assertNotNull(p.getScode_langue());
        Assertions.assertEquals("FR", p.getScode_langue());                    // ON check que la langue a ete positionné par defaut avec FR"
    }

    @Test
    void testCreateOrUpdateIndividual_createIndividualWithCorrectLanguage() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setCivility("M.");
        indInfo.setLastNameSC(generateString());
        indInfo.setFirstNameSC(generateString());
        indInfo.setStatus("V");
        indInfo.setLanguageCode("EN");
        indRequest.setIndividualInformations(indInfo);

        IndividualProfilV3 ip = new IndividualProfilV3();
        ip.setEmailOptin("N");
        ip.setLanguageCode("BR");

        indRequest.setIndividualProfil(ip);
        request.setIndividualRequest(indRequest);

        // Email address
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();
        email.setEmail(generateEmail());
        email.setMediumCode("D");
        email.setMediumStatus("V");
        // email.setVersion("1");
        email.setEmailOptin("N");
        emailRequest.setEmail(email);
        request.getEmailRequest().add(emailRequest);

        // Postal Address
        PostalAddressRequest addRequest = new PostalAddressRequest();
        PostalAddressContent pac = new PostalAddressContent();
        pac.setNumberAndStreet("not collected");
        pac.setZipCode("99999");
        pac.setCity("not collected");
        pac.setCountryCode("FR");
        pac.setAdditionalInformation("not collected");
        // pac.setDistrict("");
        pac.setStateCode("ZZ");
        // pac.setCorporateName("");
        addRequest.setPostalAddressContent(pac);

        PostalAddressProperties pap = new PostalAddressProperties();
        pap.setMediumCode("D");        // Direct
        pap.setMediumStatus("I");    // Invalid
        // pap.setVersion("1");
        pap.setIndicAdrNorm(true);    // On force l'adresse
        addRequest.setPostalAddressProperties(pap);

        UsageAddress ua = new UsageAddress();
        ua.setUsageNumber("1");
        ua.setApplicationCode("ISI");
        ua.setAddressRoleCode("M");
        addRequest.setUsageAddress(ua);

        request.getPostalAddressRequest().add(addRequest);


        // WS call
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());

        Assertions.assertNotNull(response.getInformationResponse());
        Assertions.assertNotNull(response.getGin());
        Assertions.assertNotEquals(0, response.getInformationResponse());

        // Check in DATABASE
        Profils p = profilsRepository.findProfilsByGin(response.getGin());
        Assertions.assertNotNull(p.getScode_langue());
        Assertions.assertEquals("BR", p.getScode_langue());                    // ON check que le "BR" est correctement possitionné
    }

    @Test
    @Transactional
    @Rollback(true)
    void testCreateOrUpdateIndividual_UpdateIndividualWithCorrectLanguage() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setIdentifier(GIN);
        indInfo.setCivility("MR");
        indRequest.setIndividualInformations(indInfo);

        IndividualProfilV3 ip = new IndividualProfilV3();
        ip.setLanguageCode("DE");

        indRequest.setIndividualProfil(ip);
        request.setIndividualRequest(indRequest);

        // WS call
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());

        // Check in DATABASE
        Profils p = profilsRepository.findProfilsByGin(GIN);
        Assertions.assertNotNull(p.getScode_langue());
        Assertions.assertEquals("DE", p.getScode_langue());                    // ON check que le "BR" est correctement possitionné
    }

    @Test
    @Transactional
    @Rollback(true)
    void testCreateOrUpdateIndividual_UpdateIndividualWithUnknownLanguage() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setIdentifier(GIN);
        indInfo.setCivility("MR");
        indRequest.setIndividualInformations(indInfo);

        IndividualProfilV3 ip = new IndividualProfilV3();
        ip.setLanguageCode("HK");

        indRequest.setIndividualProfil(ip);
        request.setIndividualRequest(indRequest);

        // WS call
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());

        // Check in DATABASE
        Profils p = profilsRepository.findProfilsByGin(GIN);
        Assertions.assertNotNull(p.getScode_langue());
        Assertions.assertEquals("FR", p.getScode_langue());                    // ON check que le "BR" est correctement possitionné
    }

    @Test
    void testCreateOrUpdateIndividual_CreateIndividualWithNonValidCivility() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();

        indInfo.setCivility("MAN");
        indInfo.setFirstNameSC("Fi" + generateString());
        indInfo.setLastNameSC("La" + generateString());

        indRequest.setIndividualInformations(indInfo);

        IndividualProfilV3 ip = new IndividualProfilV3();
        ip.setLanguageCode("FR");

        indRequest.setIndividualProfil(ip);
        request.setIndividualRequest(indRequest);

        // WS call
        try {
            CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
            Assertions.fail("We should have an Exception due to CIVILITY");

        } catch (Exception e) {

            BusinessErrorBlocBusinessException bebe = (BusinessErrorBlocBusinessException) e;
            Assertions.assertNotNull(bebe.getFaultInfo());

            BusinessErrorBloc beb = bebe.getFaultInfo();
            Assertions.assertNotNull(beb);

            BusinessError be = beb.getBusinessError();
            Assertions.assertNotNull(be);
            Assertions.assertNotNull(be.getErrorCode());

            Assertions.assertEquals("ERROR_133", be.getErrorCode().toString());
            Assertions.assertEquals("MISSING PARAMETERS", be.getErrorLabel());
            Assertions.assertEquals("Missing parameter exception: The field civility not valid", be.getErrorDetail());
        }
    }

    @Test
    void testCreateOrUpdateIndividual_CreateIndividualWithNonValidMediumStatus() {
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setCivility("M.");
        indInfo.setLastNameSC(generateString());
        indInfo.setFirstNameSC(generateString());
        indInfo.setStatus("V");
        indInfo.setLanguageCode("EN");
        indRequest.setIndividualInformations(indInfo);

        IndividualProfilV3 ip = new IndividualProfilV3();
        ip.setEmailOptin("N");
        ip.setLanguageCode("BR");

        indRequest.setIndividualProfil(ip);
        request.setIndividualRequest(indRequest);

        // Email address
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();
        email.setEmail(generateEmail());
        email.setMediumCode("D");
        email.setMediumStatus("V");
        // email.setVersion("1");
        email.setEmailOptin("N");
        emailRequest.setEmail(email);
        request.getEmailRequest().add(emailRequest);

        // Postal Address
        PostalAddressRequest addRequest = new PostalAddressRequest();
        PostalAddressContent pac = new PostalAddressContent();
        pac.setNumberAndStreet("not collected");
        pac.setZipCode("99999");
        pac.setCity("not collected");
        pac.setCountryCode("FR");
        pac.setAdditionalInformation("not collected");
        // pac.setDistrict("");
        pac.setStateCode("ZZ");
        // pac.setCorporateName("");
        addRequest.setPostalAddressContent(pac);

        PostalAddressProperties pap = new PostalAddressProperties();
        pap.setMediumCode("D");        // Direct
        pap.setMediumStatus("L");    // Invalid
        // pap.setVersion("1");
        pap.setIndicAdrNorm(true);    // On force l'adresse
        addRequest.setPostalAddressProperties(pap);

        request.getPostalAddressRequest().add(addRequest);


        // WS call
        try {
            createOrUpdateIndividualImplV8.createIndividual(request);
            Assertions.fail("Exception should be raised because of invalid parameter medium status");
        } catch (Exception e) {
            BusinessErrorBlocBusinessException businessErrorBlocBusinessException = (BusinessErrorBlocBusinessException) e;
            Assertions.assertNotNull(businessErrorBlocBusinessException.getFaultInfo());

            BusinessErrorBloc businessErrorBloc = businessErrorBlocBusinessException.getFaultInfo();
            Assertions.assertNotNull(businessErrorBlocBusinessException);

            BusinessError be = businessErrorBloc.getBusinessError();
            Assertions.assertNotNull(be);
            Assertions.assertNotNull(be.getErrorCode());

            Assertions.assertEquals("ERROR_932", be.getErrorCode().toString());
            Assertions.assertEquals("INVALID PARAMETER", be.getErrorLabel());
            Assertions.assertEquals("Invalid medium status L", be.getErrorDetail());
        }

    }

    @Test
    void testCreateOrUpdateIndividual_UpdateIndividualWithNonValidCivility() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();

        indInfo.setCivility("MAN");
        indInfo.setIdentifier("400509996993");

        indRequest.setIndividualInformations(indInfo);

        IndividualProfilV3 ip = new IndividualProfilV3();
        ip.setLanguageCode("FR");

        indRequest.setIndividualProfil(ip);
        request.setIndividualRequest(indRequest);

        // WS call
        try {
            CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
            Assertions.fail("We should have an Exception due to CIVILITY");

        } catch (Exception e) {

            BusinessErrorBlocBusinessException bebe = (BusinessErrorBlocBusinessException) e;
            Assertions.assertNotNull(bebe.getFaultInfo());

            BusinessErrorBloc beb = bebe.getFaultInfo();
            Assertions.assertNotNull(beb);

            BusinessError be = beb.getBusinessError();
            Assertions.assertNotNull(be);
            Assertions.assertNotNull(be.getErrorCode());

            Assertions.assertEquals("ERROR_133", be.getErrorCode().toString());
            Assertions.assertEquals("MISSING PARAMETERS", be.getErrorLabel());
            Assertions.assertEquals("Missing parameter exception: The field civility not valid", be.getErrorDetail());
        }
    }

    @Test
    void testCreateOrUpdateIndividual_CreateIndividualWithNonValidTitle() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();

        indInfo.setCivility("MISS");
        indInfo.setFirstNameSC("Fi" + generateString());
        indInfo.setLastNameSC("La" + generateString());

        indRequest.setIndividualInformations(indInfo);

        Civilian civ = new Civilian();
        civ.setTitleCode("RAF");

        indRequest.setCivilian(civ);

        IndividualProfilV3 ip = new IndividualProfilV3();
        ip.setLanguageCode("FR");

        indRequest.setIndividualProfil(ip);
        request.setIndividualRequest(indRequest);

        // WS call
        try {
            CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
            Assertions.fail("We should have an Exception due to TITLE");

        } catch (Exception e) {

            BusinessErrorBlocBusinessException bebe = (BusinessErrorBlocBusinessException) e;
            Assertions.assertNotNull(bebe.getFaultInfo());

            BusinessErrorBloc beb = bebe.getFaultInfo();
            Assertions.assertNotNull(beb);

            BusinessError be = beb.getBusinessError();
            Assertions.assertNotNull(be);
            Assertions.assertNotNull(be.getErrorCode());

            Assertions.assertEquals("ERROR_133", be.getErrorCode().toString());
            Assertions.assertEquals("MISSING PARAMETERS", be.getErrorLabel());
            Assertions.assertEquals("Missing parameter exception: The field title not valid", be.getErrorDetail());
        }
    }


    @Test
    void testCreateOrUpdateIndividual_UpdateIndividualWithNonValidTitle() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setIdentifier(GIN);
        indInfo.setCivility("MISS");
        indRequest.setIndividualInformations(indInfo);

        Civilian civ = new Civilian();
        civ.setTitleCode("RAF");

        indRequest.setCivilian(civ);

        IndividualProfilV3 ip = new IndividualProfilV3();
        ip.setLanguageCode("FR");

        indRequest.setIndividualProfil(ip);
        request.setIndividualRequest(indRequest);

        // WS call
        try {
            CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
            Assertions.fail("We should have an Exception due to TITLE");

        } catch (Exception e) {

            BusinessErrorBlocBusinessException bebe = (BusinessErrorBlocBusinessException) e;
            Assertions.assertNotNull(bebe.getFaultInfo());

            BusinessErrorBloc beb = bebe.getFaultInfo();
            Assertions.assertNotNull(beb);

            BusinessError be = beb.getBusinessError();
            Assertions.assertNotNull(be);
            Assertions.assertNotNull(be.getErrorCode());

            Assertions.assertEquals("ERROR_133", be.getErrorCode().toString());
            Assertions.assertEquals("MISSING PARAMETERS", be.getErrorLabel());
            Assertions.assertEquals("Missing parameter exception: The field title not valid", be.getErrorDetail());
        }
    }

    @Test
    @Rollback(false)
    void testCreateOrUpdateIndividual_CreateIndividualWithMinusCivility() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
//		indInfo.setIdentifier(GIN);
        indInfo.setCivility("ms ");

        indInfo.setFirstNameSC("Nom");
        indInfo.setLastNameSC("Prénom");
        indInfo.setStatus("V");

/*
		"M.";
		"MISS";
		"MR";
		"MRS";
		"MS";
*/
        indRequest.setIndividualInformations(indInfo);

        IndividualProfilV3 ip = new IndividualProfilV3();
        ip.setLanguageCode("FR");

        indRequest.setIndividualProfil(ip);
        request.setIndividualRequest(indRequest);

        // WS call
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        Assertions.assertNotNull(response);

        Assertions.assertNotNull(response.isSuccess());

        Assertions.assertTrue(response.isSuccess());

    }


    @Test
    @Rollback(false)
    void testCreateOrUpdateIndividual_UpdateIndividualWithMinusCivility() throws BusinessErrorBlocBusinessException, JrafDomainException {

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setIdentifier("400509996621");
        indInfo.setCivility("ms ");

        indInfo.setStatus("V");

        indRequest.setIndividualInformations(indInfo);

        IndividualProfilV3 ip = new IndividualProfilV3();
        ip.setLanguageCode("FR");

        indRequest.setIndividualProfil(ip);
        request.setIndividualRequest(indRequest);

        // WS call
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
    }


    /**
     * Test update on an individual with IPV6
     *
     * @throws BusinessErrorBlocBusinessException
     * @throws JrafDomainException
     */
    @Test
    void testCreateOrUpdateIndividual_simpleIndividu_IPV6_30() {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start...");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV8Test.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV8Test.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV8Test.SIGNATURE);
        requestor.setIpAddress("0000:0000:0000:0000:0000:0000:0000:0000");

        request.setRequestor(requestor);

        IndividualRequest indRequest = new IndividualRequest();
        // indRequest.setCivilian("MR");

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setBirthDate(new Date());
        indInfo.setCivility("MR");
        indInfo.setStatus("V");
        indInfo.setLastNameSC("PHILIPPOT");
        indInfo.setFirstNameSC("PATRICK");

        indRequest.setIndividualInformations(indInfo);

        request.setIndividualRequest(indRequest);

        // TODO workaround for maven issue with Bamboo
        CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
        try {
            response = createOrUpdateIndividualImplV8.createIndividual(request);
        } catch (BusinessErrorBlocBusinessException e) {
            logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
            logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());

            Assertions.fail("On ne devrait pas avoir d erreur");
            return;
        }
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("GIN created : " + response.getGin());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test stop.");
    }

    /**
     * Test update on an individual with IPV6
     *
     * @throws BusinessErrorBlocBusinessException
     * @throws JrafDomainException
     */
    @Test
    void testCreateOrUpdateIndividual_simpleIndividu_IPV6_45() {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start...");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV8Test.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV8Test.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV8Test.SIGNATURE);
        requestor.setIpAddress("0000:0000:0000:0000:0000:0000:xxx.xxx.xxx.xxx");

        request.setRequestor(requestor);

        IndividualRequest indRequest = new IndividualRequest();
        // indRequest.setCivilian("MR");

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setBirthDate(new Date());
        indInfo.setCivility("MR");
        indInfo.setStatus("V");
        indInfo.setLastNameSC("PHILIPPOT");
        indInfo.setFirstNameSC("PATRICK");

        indRequest.setIndividualInformations(indInfo);

        request.setIndividualRequest(indRequest);

        // TODO workaround for maven issue with Bamboo
        CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
        try {
            response = createOrUpdateIndividualImplV8.createIndividual(request);
        } catch (BusinessErrorBlocBusinessException e) {

            logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
            logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());

            Assertions.fail("On ne devrait pas avoir d erreur");
            return;
        }
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());


        try {
            //TEST if the new individu exist
            IndividuDTO indDTO = individuDS.getByGin(response.getGin());
            Assertions.assertNotNull(indDTO);
            Assertions.assertEquals("PHILIPPOT", indDTO.getNom());
            Assertions.assertEquals("PATRICK", indDTO.getPrenom());


//			AccountDataDTO acc = accountDataDS.getByGin(response.getGin());
//			Assertions.assertNotNull(acc);

            // Assertions.assertNotNull(acc);


        } catch (JrafDomainException e) {
//			e.printStackTrace();
            Assertions.fail();
        }


        CreateOrUpdateAnIndividualImplV8Test.logger.info("GIN created : " + response.getGin());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test stop.");
    }
    
    @Test
    @Rollback(true)
    void testForgetAccount() {
        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start...");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        String gin = "400447478453";

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CreateOrUpdateAnIndividualImplV8Test.CHANNEL);
        requestor.setSite(CreateOrUpdateAnIndividualImplV8Test.SITE);
        requestor.setSignature(CreateOrUpdateAnIndividualImplV8Test.SIGNATURE);
        requestor.setIpAddress("0000:0000:0000:0000:0000:0000:xxx.xxx.xxx.xxx");
        requestor.setContext(ContextEnum.FC.getLibelle());
        request.setRequestor(requestor);

        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setIdentifier(gin);

        IndividualRequest indRequest = new IndividualRequest();
        indRequest.setIndividualInformations(indInfo);
        request.setIndividualRequest(indRequest);
        
        try {
        	CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        	Assertions.assertNotNull(response);
        	Assertions.assertNotNull(response.isSuccess());
        	Assertions.assertTrue(response.isSuccess());

        	try {
				IndividuDTO indDTO = individuDS.getAllByGin(gin);
				Assertions.assertNotNull(indDTO);
				Assertions.assertEquals(indDTO.getStatutIndividu(), ForgetEnum.STATUS.getCode());
				Assertions.assertEquals(indDTO.getType(), ForgetEnum.STATUS.getCode());

				AccountDataDTO accountdatadto = indDTO.getAccountdatadto();
				Assertions.assertNotNull(accountdatadto);
				Assertions.assertEquals(accountdatadto.getStatus(), AccountStatusEnum.D.name());
				Assertions.assertNull(accountdatadto.getSocialNetworkId());

			} catch (JrafDomainException e) {
				Assertions.fail(e.getMessage());
			}
        	
        } catch (BusinessErrorBlocBusinessException e) {
            logger.info(e.getFaultInfo().getBusinessError().getErrorCode());
            logger.info(e.getFaultInfo().getBusinessError().getErrorDetail());
            Assertions.fail(e.getMessage());
        }
    	
        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test stop.");
    }


    @Test
    void testCreateOrUpdateIndividual_DefaultGenderAndCivility_Process_W() throws BusinessErrorBlocBusinessException {

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

        // Requestor
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setContext("B2C_HOME_PAGE");
        requestor.setApplicationCode("ISI");
        requestor.setSite("QVI");
        requestor.setSignature("TU_RI");

        // Email
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();

        email.setEmail(CreateOrUpdateAnIndividualImplV8Test.MAIL);
        emailRequest.setEmail(email);

        // Communication Preference
        ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
        CommunicationPreferences comPref = new CommunicationPreferences();
        comPref.setDomain("S");
        comPref.setCommunicationGroupeType("N");
        comPref.setCommunicationType("AF");
        comPref.setOptIn("N");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            comPref.setDateOfConsent(df.parse("30-08-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set date of consent");
        }
        comPref.setSubscriptionChannel("B2C Home Page");

        MarketLanguage ml = new MarketLanguage();
        ml.setLanguage(LanguageCodesEnum.FR);
        ml.setMarket("FR");
        ml.setOptIn("N");
        try {
            ml.setDateOfConsent(df.parse("01-09-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set market language date of consent");
        }

        comPref.getMarketLanguage().add(ml);
        comPrefrequest.setCommunicationPreferences(comPref);

        request.setRequestor(requestor);
        request.getEmailRequest().add(emailRequest);
        request.getComunicationPreferencesRequest().add(comPrefrequest);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("Gin updated: " + response.getGin());
        // Check individual
        Individu testIndividu = individuRepository.findBySgin(response.getGin());
        Assertions.assertEquals(GenderEnum.UNKNOWN.toString(), testIndividu.getSexe());
        Assertions.assertEquals(CivilityEnum.M_.toString(), testIndividu.getCivilite());
        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test");
    }

    @Test
    void testCreateOrUpdateIndividual_DefaultGenderAndCivility_Process_A() throws BusinessErrorBlocBusinessException {
        CreateOrUpdateAnIndividualImplV8Test.logger.info("Test start... ");

        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        request.setProcess(ProcessEnum.A.getCode());

        /*** Set parameters ***/
        // Requestor
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel("B2C");
        requestor.setApplicationCode("ISI");
        requestor.setSite("QVI");
        requestor.setSignature("TU_RI");


        // Email
        EmailRequest emailReq = new EmailRequest();
        Email email = new Email();
        String mail = generateEmail();
        email.setEmail(mail);
        email.setMediumCode(MediumCodeEnum.HOME.toString());
        email.setMediumStatus("V");
        emailReq.setEmail(email);

        // Communication Preference
        ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
        CommunicationPreferences comPref = new CommunicationPreferences();
        comPref.setDomain("S");
        comPref.setCommunicationGroupeType("P");
        comPref.setCommunicationType("AF");
        comPref.setOptIn("N");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            comPref.setDateOfConsent(df.parse("30-08-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set date of consent");
        }
        comPref.setSubscriptionChannel("B2C Home Page");

        MarketLanguage ml = new MarketLanguage();
        ml.setLanguage(LanguageCodesEnum.FR);
        ml.setMarket("FR");
        ml.setOptIn("N");
        try {
            ml.setDateOfConsent(df.parse("01-09-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set market language date of consent");
        }

        comPref.getMarketLanguage().add(ml);
        comPrefrequest.setCommunicationPreferences(comPref);

        // Alert
        AlertRequest alertRequest = new AlertRequest();
        Alert alert = new Alert();
        alert.setType("P");
        alert.setOptIn("N");
        /* origin */
        AlertData orig = new AlertData();
        orig.setKey("ORIGIN");
        orig.setValue("PAR");
        alert.getAlertData().add(orig);
        AlertData origEnr = new AlertData();
        origEnr.setKey("ORIGIN_ENR");
        origEnr.setValue("CDG");
        alert.getAlertData().add(origEnr);
        /* origin type */
        AlertData origType = new AlertData();
        origType.setKey("ORIGIN_TYPE");
        origType.setValue("C");
        alert.getAlertData().add(origType);
        /* destination */
        AlertData dest = new AlertData();
        dest.setKey("DESTINATION");
        dest.setValue("LAX");
        alert.getAlertData().add(dest);
        /* destination type */
        AlertData destType = new AlertData();
        destType.setKey("DESTINATION_TYPE");
        destType.setValue("A");
        alert.getAlertData().add(destType);
        /* start date */
        AlertData sDate = new AlertData();
        sDate.setKey("START_DATE");
        sDate.setValue("01012016");
        alert.getAlertData().add(sDate);
        /* end date */
        AlertData eDate = new AlertData();
        eDate.setKey("END_DATE");
        eDate.setValue("31122016");
        alert.getAlertData().add(eDate);
        /* cabin */
        AlertData cabin = new AlertData();
        cabin.setKey("CABIN");
        cabin.setValue("W");
        alert.getAlertData().add(cabin);

        alertRequest.getAlert().add(alert);

        request.getEmailRequest().add(emailReq);
        request.setRequestor(requestor);
        request.getComunicationPreferencesRequest().add(comPrefrequest);
        request.setAlertRequest(alertRequest);

        // Execute test
        CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

        // Tests
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.isSuccess());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getGin());
        Assertions.assertTrue(response.getGin().startsWith("90"));
        // Check individual
        Individu testIndividu = individuRepository.findBySgin(response.getGin());
        Assertions.assertEquals(GenderEnum.UNKNOWN.toString(), testIndividu.getSexe());
        Assertions.assertEquals(CivilityEnum.M_.toString(), testIndividu.getCivilite());

        CreateOrUpdateAnIndividualImplV8Test.logger.info("End of test. ");
    }


    CreateUpdateIndividualRequest buildRequestProspect(){
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
        request.setProcess(ProcessEnum.W.getCode());   // Determine a prospect

        /*** Set parameters ***/
        // Requestor
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setContext(CONTEXT);
        requestor.setApplicationCode(APP_CODE);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);

        IndividualRequest individualRequest = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setCivility("MRS");
        indInfo.setFirstNameSC("Prénom");
        indInfo.setLastNameSC("Nom");
        Date date = new Date();
        indInfo.setBirthDate(date);
        indInfo.setNationality("FR");
        indInfo.setSecondNationality("IT");
        indInfo.setStatus("V");

        individualRequest.setIndividualInformations(indInfo);

        // Email
        EmailRequest emailRequest = new EmailRequest();
        Email email = new Email();

        String emailUpper = generateEmail().toUpperCase();
        email.setEmail(emailUpper);
        emailRequest.setEmail(email);

        // Communication Preference
        ComunicationPreferencesRequest comPrefrequest = new ComunicationPreferencesRequest();
        CommunicationPreferences comPref = new CommunicationPreferences();
        comPref.setDomain("S");
        comPref.setCommunicationGroupeType("N");
        comPref.setCommunicationType("AF");
        comPref.setOptIn("N");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            comPref.setDateOfConsent(df.parse("30-08-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set date of consent");
        }
        comPref.setSubscriptionChannel("B2C Home Page");

        MarketLanguage ml = new MarketLanguage();
        ml.setLanguage(LanguageCodesEnum.FR);
        ml.setMarket("FR");
        ml.setOptIn("N");
        try {
            ml.setDateOfConsent(df.parse("01-09-2016"));
        } catch (ParseException e) {
            CreateOrUpdateAnIndividualImplV8Test.logger.error("unable to set market language date of consent");
        }

        comPref.getMarketLanguage().add(ml);
        comPrefrequest.setCommunicationPreferences(comPref);

        request.setRequestor(requestor);
        request.getEmailRequest().add(emailRequest);
        request.getComunicationPreferencesRequest().add(comPrefrequest);
        request.setIndividualRequest(individualRequest);

        return request;
    }

    CreateUpdateIndividualRequest buildRequestExternal(){
        CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

        request.setProcess(ProcessEnum.E.getCode());

        // Preparing the request
        RequestorV2 requestor = new RequestorV2();
        requestor.setChannel(CHANNEL);
        requestor.setSite(SITE);
        requestor.setSignature(SIGNATURE);
        requestor.setApplicationCode(APP_CODE);

        request.setRequestor(requestor);

        // Individual Request Bloc
        IndividualRequest indRequest = new IndividualRequest();

        IndividualRequest individualRequest = new IndividualRequest();
        IndividualInformationsV3 indInfo = new IndividualInformationsV3();
        indInfo.setFirstNameSC("Prénom");
        indInfo.setLastNameSC("Nom");
        Date date = new Date();
        indInfo.setBirthDate(date);
        indInfo.setNationality("FR");
        indInfo.setSecondNationality("IT");
        indInfo.setStatus("V");

        indRequest.setIndividualInformations(indInfo);

        ExternalIdentifierRequest eir = new ExternalIdentifierRequest();
        ExternalIdentifier ei = new ExternalIdentifier();
        ei.setIdentifier("ExternalIdIntegrationTest");
        ei.setType("PNM_ID");
        eir.setExternalIdentifier(ei);

        request.getExternalIdentifierRequest().add(eir);
        request.setIndividualRequest(indRequest);

        return request;
    }
}
