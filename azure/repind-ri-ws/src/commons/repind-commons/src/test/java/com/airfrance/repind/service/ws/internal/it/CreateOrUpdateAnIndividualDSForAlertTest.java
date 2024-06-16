package com.airfrance.repind.service.ws.internal.it;

import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.ProcessEnum;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.individu.AlertDTO;
import com.airfrance.repind.dto.individu.AlertDataDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.reference.RefAlertDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.service.individu.internal.AlertDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.ws.internal.CreateOrUpdateIndividualDS;
import com.airfrance.repind.service.ws.internal.helpers.AlertHelper;
import com.airfrance.repind.service.ws.internal.helpers.UltimateException;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.fail;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualDSForAlertTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualDSForAlertTest.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T698964";
	private static final String SITE = "QVI";
	private static final String EMAIL_INDIVIDUS = "clarkkent@yopmail.com";
	// private static final String EMAIL_INDIVIDUS = "test@test.com";
	private static final String GIN_INDIVIDUS = "400415094784";
	private static final String GIN_INDIVIDUS_PROSPECT = "900025059447";
	private static final String EMAIL_INDIVIDUS_PROSPECT = "tomtest@test.com";

	private static final String CP_DOMAIN = "S";
	private static final String CP_ComType = "AF";
	private static final String CP_ComGroupType = "P";

	private static final Boolean REMOVE_MODE = false;

	@Autowired
	private CreateOrUpdateIndividualDS createOrUpdateDS;

	@Autowired
	protected IndividuDS individuDS;

	@Autowired
	protected AlertHelper alertHelper;

	@Autowired
	protected CommunicationPreferencesDS CommunicationPreferencesDS;
	
	@Autowired
	protected AlertDS alertDS;

	private List<RefAlertDTO> listRefInp;
	private List<RefAlertDTO> listRefEnv;

	private String GIN_PARAMETER = "gin";
	private String EMAIL_PARAMETER = "email";

	/*
	 * Create alert for random Email
	 */
	@Test
	public void test_CreateAlertForIndividualWithEmail()
			throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		removeAllAlertForGin(GIN_INDIVIDUS);
		logger.info("Test CreateAlertForIndividualWithEmail start...");

		CreateUpdateIndividualRequestDTO request = generateAnIndividusAlertRequest(EMAIL_INDIVIDUS, EMAIL_PARAMETER);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		AlertDTO alert = findLastAlertByGin(response.getGin());
		if (alert != null) {
			logger.info("alertID = " + alert.getAlertId());
			remove(alert);
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
		logger.info("Test CreateAlertForIndividual stop...");
	}

	@Test
	public void test_CreateAlertForProspectWithEmail()
			throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("Test CreateAlertForProspectWithEmail start...");

		removeAllAlertForGin(GIN_INDIVIDUS_PROSPECT);

		CreateUpdateIndividualRequestDTO request = generateAnIndividusAlertRequest(EMAIL_INDIVIDUS_PROSPECT,
				EMAIL_PARAMETER);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		AlertDTO alert = findLastAlertProspectByGin(response.getGin());
		if (alert != null) {
			logger.info("alertID = " + alert.getAlertId());
			removeProspect(alert);
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
		logger.info("Test CreateAlertForIndividual stop...");
	}

	/*
	 * Create alert with gin for individual
	 */
	@Test
	public void test_CreateAlertForIndividualWithGin()
			throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("Test CreateAlertForIndividualWithGin start...");

		CreateUpdateIndividualRequestDTO request = generateAnIndividusAlertRequest(GIN_INDIVIDUS, GIN_PARAMETER);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		AlertDTO alert = findLastAlertByGin(GIN_INDIVIDUS);
		if (alert != null) {
			logger.info("alertID = " + alert.getAlertId());
			// remove(alert);
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
		logger.info("Test CreateAlertForIndividual stop...");
	}

	/*
	 * Create alert with gin for individual
	 */
	@Test
	public void test_CreateAlertForExistingProspectWithGin()
			throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("Test CreateAlertForExistingProspectWithGin start...");

		CreateUpdateIndividualRequestDTO request = generateAnIndividusAlertRequest(GIN_INDIVIDUS_PROSPECT, "gin");

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		AlertDTO alert = findLastAlertProspectByGin(GIN_INDIVIDUS_PROSPECT);
		if (alert != null) {
			logger.info("alertID = " + alert.getAlertId());
			removeProspect(alert);
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
		logger.info("Test CreateAlertForIndividual stop...");
	}

	@Test(expected = Test.None.class /* no exception expected */)
	@Rollback(true)
	public void createPromoAlert() {
		logger.info("Test createPromoAlert start...");
		CreateUpdateIndividualRequestDTO request = generateMandatoryRequest();

		EmailRequestDTO emailRequestDTO = new EmailRequestDTO();
		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setEmail("xavierCroisssant@croissant.ru");
		emailRequestDTO.setEmailDTO(emailDTO);

		CommunicationPreferencesRequestDTO comPrefRequest = new CommunicationPreferencesRequestDTO();
		request.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		request.getCommunicationPreferencesRequestDTO().add(generateComPrefRequest());

		AlertRequestDTO alertRequest = new AlertRequestDTO();
		com.airfrance.repind.dto.ws.AlertDTO alert = new com.airfrance.repind.dto.ws.AlertDTO();
		alert.setType("N");
		alert.setOptin("Y");
		alert.setAlertDataDTO(new ArrayList<com.airfrance.repind.dto.ws.AlertDataDTO>());

		com.airfrance.repind.dto.ws.AlertDataDTO alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("ORIGIN");
		alertData.setValue("CDG");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("ORIGIN_TYPE");
		alertData.setValue("A");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("DESTINATION");
		alertData.setValue("NCE");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("DESTINATION_TYPE");
		alertData.setValue("C");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("START_DATE");
		alertData.setValue("03102016");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("END_DATE");
		alertData.setValue("31102016");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("CABIN");
		alertData.setValue("C");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("ORIGIN_ENR");
		alertData.setValue("Ori");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("PRICE_MAX_THRESHOLD");
		alertData.setValue("3500");
		alert.getAlertDataDTO().add(alertData);

		alertRequest.setAlertDTO(new ArrayList<com.airfrance.repind.dto.ws.AlertDTO>());
		alertRequest.getAlertDTO().add(alert);
		request.setAlertRequestDTO(alertRequest);
		request.getCommunicationPreferencesRequestDTO().add(comPrefRequest);

		try {
			CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
			// Execute test
			response = createOrUpdateDS.createOrUpdateV8(request, response);
		} catch (Exception e) {
			// don't care
		}

	}

	@Test
	public void test_CreateAlert2TimeWithDifferentType_REPIND637() throws JrafDomainException {

		logger.info("Test test_CreateAlert2TimeWithDifferentType_REPIND637 start...");

		CreateUpdateIndividualRequestDTO request = generateMandatoryRequest();
		// generate alert part
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		com.airfrance.repind.dto.ws.AlertDTO alert = new com.airfrance.repind.dto.ws.AlertDTO();
		alert.setType("N");
		alert.setOptin("Y");
		alert.setAlertDataDTO(new ArrayList<com.airfrance.repind.dto.ws.AlertDataDTO>());

		com.airfrance.repind.dto.ws.AlertDataDTO alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("ORIGIN");
		alertData.setValue("CDG");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("ORIGIN_TYPE");
		alertData.setValue("A");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("DESTINATION");
		alertData.setValue("NCE");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("DESTINATION_TYPE");
		alertData.setValue("C");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("START_DATE");
		alertData.setValue("03102016");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("END_DATE");
		alertData.setValue("31102016");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("CABIN");
		alertData.setValue("C");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("ORIGIN_ENR");
		alertData.setValue("Ori");
		alert.getAlertDataDTO().add(alertData);

		alertRequest.setAlertDTO(new ArrayList<com.airfrance.repind.dto.ws.AlertDTO>());
		alertRequest.getAlertDTO().add(alert);
		request.setAlertRequestDTO(alertRequest);

		// individual part
		request.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		request.getCommunicationPreferencesRequestDTO().add(generateComPrefRequest());
		request.setIndividualRequestDTO(generateIndividualInformation(GIN_INDIVIDUS_PROSPECT));

		request.getAlertRequestDTO().getAlertDTO().get(0).setType("N");
		request.getCommunicationPreferencesRequestDTO().get(0).getCommunicationPreferencesDTO()
				.setCommunicationGroupeType("N");

		try {
			CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
			// Execute test
			response = createOrUpdateDS.createOrUpdateV8(request, response);
		} catch (Exception e) {
			// don't care
		}

		request = generateMandatoryRequest();
		// generate alert part
		alertRequest = new AlertRequestDTO();
		alertRequest.setAlertDTO(new ArrayList<com.airfrance.repind.dto.ws.AlertDTO>());
		alert = new com.airfrance.repind.dto.ws.AlertDTO();
		alert.setAlertDataDTO(new ArrayList<com.airfrance.repind.dto.ws.AlertDataDTO>());
		alert.setType("P");
		alert.setOptin("Y");

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("ORIGIN");
		alertData.setValue("CDG");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("ORIGIN_TYPE");
		alertData.setValue("A");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("DESTINATION");
		alertData.setValue("NCE");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("DESTINATION_TYPE");
		alertData.setValue("C");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("START_DATE");
		alertData.setValue("03102016");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("END_DATE");
		alertData.setValue("31102016");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("CABIN");
		alertData.setValue("C");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("ORIGIN_ENR");
		alertData.setValue("Ori");
		alert.getAlertDataDTO().add(alertData);

		alertData = new com.airfrance.repind.dto.ws.AlertDataDTO();
		alertData.setKey("PRICE_MAX_THRESHOLD");
		alertData.setValue("3500");
		alert.getAlertDataDTO().add(alertData);

		alertRequest.getAlertDTO().add(alert);
		request.setAlertRequestDTO(alertRequest);

		// individual part
		request.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		request.getCommunicationPreferencesRequestDTO().add(generateComPrefRequest());
		request.setIndividualRequestDTO(generateIndividualInformation(GIN_INDIVIDUS_PROSPECT));

		try {
			CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
			// Execute test
			response = createOrUpdateDS.createOrUpdateV8(request, response);
			if (!response.getSuccess()) {
				fail();
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
		logger.info("Test test_CreateAlert2TimeWithDifferentType_REPIND637 stop...");
	}

	/*
	 * Test when we add a fakeKey
	 */
	@Test
	public void test_CreateAlertForIndividualErrorKeyNotAllowed() throws JrafDomainException {

		logger.info("Test CreateAlertForIndividualErrorKeyNotAllowed start...");

		CreateUpdateIndividualRequestDTO request = generateAnIndividusAlertRequest(GIN_INDIVIDUS, GIN_PARAMETER);

		// generate alertData part
		com.airfrance.repind.dto.ws.AlertDataDTO dataError = new com.airfrance.repind.dto.ws.AlertDataDTO();
		dataError.setKey("FAKEKEY");
		dataError.setValue("EMPTY");
		request.getAlertRequestDTO().getAlertDTO().get(0).getAlertDataDTO()
				.add(request.getAlertRequestDTO().getAlertDTO().get(0).getAlertDataDTO().size() - 1, dataError);

		try {
			CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
			// Execute test
			response = createOrUpdateDS.createOrUpdateV8(request, response);
		} catch (Exception e) {
			Assert.assertTrue("Wrong key not allowed", true);
		}
		logger.info("Test CreateAlertForIndividualErrorKeyNotAllowed stop...");
	}

	/*
	 * Test when we remove a mandatory key
	 */
	@Test
	public void test_CreateAlertForIndividualErrorNoAlertData()
			throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("Test test_CreateAlertForIndividualErrorNoAlertData start...");

		CreateUpdateIndividualRequestDTO request = generateAnIndividusAlertRequest(GIN_INDIVIDUS, GIN_PARAMETER);
		request.getAlertRequestDTO().getAlertDTO().get(0).getAlertDataDTO().clear();

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();

		try {		
			// Execute test
			response = createOrUpdateDS.createOrUpdateV8(request, response);
		
		} catch (Exception e) {
			Assert.assertNotNull(e);
			Assert.assertNotNull(e.getMessage());
			Assert.assertEquals("Missing parameter exception: Missing alert data for alert", ((MissingParameterException) e).getMessage());
		}
		
		logger.info("Test test_CreateAlertForIndividualErrorNoAlertData stop...");

	}

	/*
	 * Test when we remove a mandatory key
	 */
	@Test
	public void test_UpdateAlertForIndividual()
			throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("Test CreateAlertForIndividualWithGin start...");

		removeAllAlertForGin(GIN_INDIVIDUS);

		CreateUpdateIndividualRequestDTO request = generateAnIndividusAlertRequest(GIN_INDIVIDUS, "gin");
		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);
		
		
		AlertDTO alertOri = findLastAlertByGin(GIN_INDIVIDUS);
		if (alertOri != null) {
			logger.info("alertID = " + alertOri.getAlertId());
			request = generateAnIndividusAlertRequest(GIN_INDIVIDUS, "gin");
			
			com.airfrance.repind.dto.ws.AlertDTO updateAlertDTO = generateNewAlertObject("P", false, "UPDATE");
			updateAlertDTO.setAlertId(alertOri.getAlertId());

			Set<AlertDataDTO> alertOriAlertsDatasDTO = alertOri.getAlertDataDTO();
			AlertDataDTO correspondingAlertDataDTO = alertHelper.findAlertDataByKey(alertOriAlertsDatasDTO,
					AlertHelper.PromoKeyComPrefId);

			com.airfrance.repind.dto.ws.AlertDataDTO comPref = TransformAlertDataDTOToRequest(correspondingAlertDataDTO);
			updateAlertDTO.getAlertDataDTO().add(comPref);
			
			request.getAlertRequestDTO().getAlertDTO().set(0, updateAlertDTO);
			
			response = createOrUpdateDS.createOrUpdateV8(request, response);

			AlertDTO alertUpd = findLastAlertByGin(GIN_INDIVIDUS);
			
			if (alertOri.getAlertId().equals(alertUpd.getAlertId())) {
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}
		} else {
			Assert.assertTrue(false);
		}
		
		logger.info("Test CreateAlertForIndividual stop...");
	}

	private com.airfrance.repind.dto.ws.AlertDataDTO TransformAlertDataDTOToRequest(AlertDataDTO alertDataDTO) {
		com.airfrance.repind.dto.ws.AlertDataDTO ret = new com.airfrance.repind.dto.ws.AlertDataDTO();
		if (alertDataDTO.getKey() != null) {
			ret.setKey(alertDataDTO.getKey());
		}
		if (alertDataDTO.getValue() != null) {
			ret.setValue(alertDataDTO.getValue());
		}
		return ret;
	}

	/*
	 * Test error when we go over the limit
	 */
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class) // 2 mins
	public void test_LimitAlert()
			throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("Test LimitAlert start...");
		generateRefAlert();
		CreateUpdateIndividualRequestDTO request = generateMandatoryRequest();
		request.setIndividualRequestDTO(generateIndividualInformation(GIN_INDIVIDUS));// individual part
		request.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		request.getCommunicationPreferencesRequestDTO().add(generateComPrefRequest());

		// clean all alert before remove
		removeAllAlertForGin(GIN_INDIVIDUS);
		
		List<AlertDTO> alertsForGin = alertDS.findAlert(GIN_INDIVIDUS);
		if (alertsForGin != null)
			System.out.println("AlertsForGin : " + alertsForGin.size());

		// generate max alert
		Integer max = Integer.parseInt(alertHelper.findRefAlertByString(alertHelper.MaxAlert, "ENV").getValue());
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		alertRequest.setAlertDTO(new ArrayList<com.airfrance.repind.dto.ws.AlertDTO>());
		for (int i = 0; i < max - 1; i++) {
			alertRequest.getAlertDTO().add(generateNewAlertObject("P", true));
		}

		request.setAlertRequestDTO(alertRequest);

		// we send limit of alert for this individus
		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// we send an new alert = generate error
		// we add alert optin to N
		alertRequest = new AlertRequestDTO();
		alertRequest.setAlertDTO(new ArrayList<com.airfrance.repind.dto.ws.AlertDTO>());
		
		for (int i = 0; i < 5; i++) {
			com.airfrance.repind.dto.ws.AlertDTO alert = generateNewAlertObject("P", true);
			alert.setOptin("N");
			alertRequest.getAlertDTO().add(alert);
		}

		request.setAlertRequestDTO(alertRequest);
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		logger.info("Error correctely catch when we add max alert ");
		alertRequest = new AlertRequestDTO();
		alertRequest.setAlertDTO(new ArrayList<com.airfrance.repind.dto.ws.AlertDTO>());
		alertRequest.getAlertDTO().add(generateNewAlertObject("P", true));
		alertRequest.getAlertDTO().add(generateNewAlertObject("P", true));
		request.setAlertRequestDTO(alertRequest);

		try {
			response = createOrUpdateDS.createOrUpdateV8(request, response);
			Assert.assertTrue(false);
		} catch (Exception e) {
			logger.info("Error correctely catch when we add max alert ");
			Assert.assertTrue(true);
		} finally {
			removeAllAlertForGin(GIN_INDIVIDUS);
		}
		logger.info("Test LimitAlert stop...");
	}

	/*
	 * Test error when we go far over the limit with some OPTOUT alert
	 */
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class) // 2 mins
	public void test_LimitAlertWithAlertOptOUT()
			throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("Test LimitAlertWithAlertNotOptIN start...");
		generateRefAlert();
		CreateUpdateIndividualRequestDTO request = generateMandatoryRequest();
		request.setIndividualRequestDTO(generateIndividualInformation(GIN_INDIVIDUS));// individual part
		request.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		request.getCommunicationPreferencesRequestDTO().add(generateComPrefRequest());

		// clean all alert before remove
		removeAllAlertForGin(GIN_INDIVIDUS);

		// generate max alert
		Integer max = Integer.parseInt(alertHelper.findRefAlertByString(alertHelper.MaxAlert, "ENV").getValue());
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		alertRequest.setAlertDTO(new ArrayList<com.airfrance.repind.dto.ws.AlertDTO>());
		for (int i = 0; i < max - 5; i++) {
			alertRequest.getAlertDTO().add(generateNewAlertObject("P", true));
		}

		request.setAlertRequestDTO(alertRequest);

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		com.airfrance.repind.dto.ws.AlertDTO alert;
		for (int i = 0; i < 10; i++) {
			alert = generateNewAlertObject("P", true);
			alert.setOptin("N");
			alertRequest.getAlertDTO().add(alert);
		}
		request.setAlertRequestDTO(alertRequest);
		// we send limit of alert for this individus
		try {
			// Execute test
			response = createOrUpdateDS.createOrUpdateV8(request, response);

			Assert.assertTrue(false);
		} catch (Exception e) {
			logger.info("Error correctely catch when we add max alert ");
			Assert.assertTrue(true);
		} finally {
			removeAllAlertForGin(GIN_INDIVIDUS);
		}
		logger.info("Test LimitAlertWithAlertNotOptIN stop...");
	}

	@Test
	public void test_SetAllAlertOptOut()
			throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("Test UpdateAlertOptOut start...");
		generateRefAlert();
		CreateUpdateIndividualRequestDTO request = generateMandatoryRequest();
		request.setIndividualRequestDTO(generateIndividualInformation(GIN_INDIVIDUS));// individual part
		request.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		request.getCommunicationPreferencesRequestDTO().add(generateComPrefRequest());

		// clean all alert before remove
		removeAllAlertForGin(GIN_INDIVIDUS);

		// generate alert OPTIN
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		alertRequest.setAlertDTO(new ArrayList<com.airfrance.repind.dto.ws.AlertDTO>());
		for (int i = 0; i < 5; i++) {
			alertRequest.getAlertDTO().add(generateNewAlertObject("P", true));
		}
		request.setAlertRequestDTO(alertRequest);
		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// update OPTIN to no

		request = generateMandatoryRequest();
		request.setIndividualRequestDTO(generateIndividualInformation(GIN_INDIVIDUS));// individual part
		request.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		request.getCommunicationPreferencesRequestDTO().add(generateComPrefRequest("N"));

		response = createOrUpdateDS.createOrUpdateV8(request, response);

		// find user compref
		checkComPrefStatus(GIN_INDIVIDUS, "N");
		logger.info("Test UpdateAlertOptOut stop...");
	}

	private CreateUpdateIndividualRequestDTO generateAnIndividusAlertRequest(String search, String parameter)
			throws JrafDomainException {

		generateRefAlert();
		CreateUpdateIndividualRequestDTO request = generateMandatoryRequest();

		// generate alert part
		AlertRequestDTO alertRequest = new AlertRequestDTO();
		alertRequest.setAlertDTO(new ArrayList<com.airfrance.repind.dto.ws.AlertDTO>());
		alertRequest.getAlertDTO().add(generateNewAlertObject("P", true));
		request.setAlertRequestDTO(alertRequest);

		// individual part
		request.setCommunicationPreferencesRequestDTO(new ArrayList<CommunicationPreferencesRequestDTO>());
		request.getCommunicationPreferencesRequestDTO().add(generateComPrefRequest());
		if (parameter.equals(GIN_PARAMETER)) {
			request.setIndividualRequestDTO(generateIndividualInformation(search));
		} else {
			request.setEmailRequestDTO(new ArrayList<EmailRequestDTO>());
			request.getEmailRequestDTO().add(generateEmailRequest(search));
		}

		return request;

	}

	private void removeAllAlertForGin(String gin) throws JrafDomainException {

		List<AlertDTO> res = alertDS.findAlert(gin);
		
		if (res != null && res.size() >= 1) {
			
			for (AlertDTO alertDTO : res) {
				if ("P".equals(alertDTO.getType())) {
					remove(alertDTO, true);
				}
			}
		}
	}

	private void checkComPrefStatus(String gin, String status) throws JrafDomainException {
		CommunicationPreferencesDTO sComPref = new CommunicationPreferencesDTO();
		sComPref.setGin(GIN_INDIVIDUS);
		List<CommunicationPreferencesDTO> lComPref = CommunicationPreferencesDS.findCommunicationPreferences(gin);
		if (lComPref.size() > 0) {
			for (CommunicationPreferencesDTO cpDTO : lComPref) {
				if (cpDTO.getComGroupType().equals(CP_ComGroupType) && cpDTO.getDomain().equals(CP_DOMAIN)
						&& cpDTO.getComType().equals(CP_ComType)) {
					if (cpDTO.getSubscribe().equals(status)) {
						Assert.assertTrue(true);
						return;
					} else {
						logger.info("ComPref : " + cpDTO.getComPrefId() + " is not set to N");
					}
				}
			}
		}
		logger.info("Gin : " + gin + " has no compref");
		Assert.assertTrue(false);

	}

	/*
	 * Function used to test
	 */

	private IndividualRequestDTO generateIndividualInformation(String ginIndividus) {
		IndividualRequestDTO ret = new IndividualRequestDTO();
		IndividualInformationsDTO indiInfo = new IndividualInformationsDTO();
		indiInfo.setIdentifier(ginIndividus);
		IndividualRequestDTO indiReq = new IndividualRequestDTO();
		indiReq.setIndividualInformationsDTO(indiInfo);
		ret.setIndividualInformationsDTO(indiInfo);
		return ret;
	}

	private Integer getRandomNum(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	private void generateRefAlert() throws JrafDomainException {
		alertHelper.updatelistsRef("P");
		this.listRefInp = alertHelper.listRefInp;
		this.listRefEnv = alertHelper.listRefEnv;
	}

	private AlertDTO findLastAlertByGin(Long gin) throws JrafDomainException {
		return findLastAlertByGin(gin.toString());
	}

	private AlertDTO findLastAlertByGin(String gin) throws JrafDomainException {

		List<AlertDTO> list = alertDS.findAlert(gin);
		AlertDTO ret = null;
		for (AlertDTO alertDTO : list) {
			if (ret == null || alertDTO.getAlertId() > ret.getAlertId()) {
				ret = alertDTO;
			}
		}
		return ret;

	}

	private AlertDTO findLastAlertProspectByGin(Long gin) throws JrafDomainException {
		return findLastAlertProspectByGin(gin.toString());
	}

	private AlertDTO findLastAlertProspectByGin(String gin) throws JrafDomainException {

		AlertDTO search = new AlertDTO();
		search.setSgin(gin);
		List<AlertDTO> list = alertDS.findAlert(gin);
		AlertDTO ret = null;
		for (AlertDTO alertDTO : list) {
			if (ret == null || alertDTO.getAlertId() > ret.getAlertId()) {
				ret = alertDTO;
			}
		}
		return ret;

	}

	private EmailRequestDTO generateEmailRequest(String mail) {
		EmailDTO email = new EmailDTO();
		email.setEmail(mail);
		email.setEmailOptin("A");
		email.setMediumCode("D");
		email.setMediumStatus("V");
		EmailRequestDTO ret = new EmailRequestDTO();
		ret.setEmailDTO(email);

		return ret;
	}

	private CommunicationPreferencesRequestDTO generateComPrefRequest() {
		return generateComPrefRequest("Y");
	}

	private CommunicationPreferencesRequestDTO generateComPrefRequest(String optin) {
		CommunicationPreferencesRequestDTO ret = new CommunicationPreferencesRequestDTO();
		ret.setCommunicationPreferencesDTO(new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO());
		ret.getCommunicationPreferencesDTO().setDomain("S");
		ret.getCommunicationPreferencesDTO().setCommunicationGroupeType("P");
		ret.getCommunicationPreferencesDTO().setCommunicationType("AF");
		ret.getCommunicationPreferencesDTO().setOptIn(optin);
		ret.getCommunicationPreferencesDTO().setSubscriptionChannel("TEST");
		ret.getCommunicationPreferencesDTO().setDateOfConsent(new Date());

		com.airfrance.repind.dto.ws.MarketLanguageDTO ml = new com.airfrance.repind.dto.ws.MarketLanguageDTO();
		ml.setDateOfConsent(new Date());
		ml.setMarket("FR");
		ml.setLanguage("FR");
		ml.setOptIn(optin);
		ret.getCommunicationPreferencesDTO()
				.setMarketLanguageDTO(new ArrayList<com.airfrance.repind.dto.ws.MarketLanguageDTO>());
		ret.getCommunicationPreferencesDTO().getMarketLanguageDTO().add(ml);

		return ret;
	}

	private CreateUpdateIndividualRequestDTO generateMandatoryRequest() {

		CreateUpdateIndividualRequestDTO ret = new CreateUpdateIndividualRequestDTO();

		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		ret.setRequestorDTO(requestor);

		ret.setProcess(ProcessEnum.A.getCode());

		return ret;
	}

	private com.airfrance.repind.dto.ws.AlertDTO generateNewAlertObject(String type, boolean onlyMandatory)
			throws JrafDomainException {
		return generateNewAlertObject(type, onlyMandatory, "TU"); // Si on precise pas le text, On envoie un text
																	// generique pour les TU
	}

	private com.airfrance.repind.dto.ws.AlertDTO generateNewAlertObject(String type, boolean onlyMandatory, String text)
			throws JrafDomainException {
		com.airfrance.repind.dto.ws.AlertDTO ret = new com.airfrance.repind.dto.ws.AlertDTO();
		ret.setType(type);
		ret.setOptin("Y");
		ret.setAlertDataDTO(new ArrayList<com.airfrance.repind.dto.ws.AlertDataDTO>());
		for (RefAlertDTO refAlertDTO : listRefInp) {
			if (refAlertDTO.getMandatory().equals("Y") || !onlyMandatory) {
				com.airfrance.repind.dto.ws.AlertDataDTO data = new com.airfrance.repind.dto.ws.AlertDataDTO();
				data.setKey(refAlertDTO.getKey());
				switch (refAlertDTO.getValue()) {
				case "DATE":
					data.setValue("20092016");
					break;
				default:
					data.setValue(text);
					break;
				}
				if (!refAlertDTO.getKey().equals(AlertHelper.PromoKeyComPrefId)) {
					ret.getAlertDataDTO().add(data);
				}
			}
		}
		return ret;
	}

	private void remove(AlertDTO alert) throws JrafDomainException {
		remove(alert, this.REMOVE_MODE);
	}

	private void remove(AlertDTO alert, boolean remove) throws JrafDomainException {
		if (remove) {
			alertDS.remove(alert);
		}
	}

	private void removeProspect(AlertDTO alert) throws JrafDomainException {
		removeProspect(alert, false);
	}

	private void removeProspect(AlertDTO alert, boolean remove) throws JrafDomainException {
		if (remove) {
			alertDS.remove(alert);
		}
	}

}
