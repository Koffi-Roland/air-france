package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.repind.v8.createorupdateindividualws.helpers.AlertCheckHelper;
import com.afklm.repind.v8.createorupdateindividualws.transformers.IndividuResponseTransform;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.AlertRequest;
import com.afklm.soa.stubs.w000442.v8.request.ComunicationPreferencesRequest;
import com.afklm.soa.stubs.w000442.v8.request.EmailRequest;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.siccommonenum.LanguageCodesEnum;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.ProcessEnum;
import com.airfrance.repind.dto.individu.AlertDTO;
import com.airfrance.repind.dto.individu.AlertDataDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.reference.RefAlertDTO;
import com.airfrance.repind.service.individu.internal.AlertDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.reference.internal.RefAlertDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.fail;
// import com.airfrance.sicutf8.dto.individu.AlertProspectDTO;
//import com.airfrance.sicutf8.service.individu.IAlertProspectDS;
//import com.airfrance.sicutf8.service.prospect.IProspectDS;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateV8ForAlertTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateV8ForAlertTest.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T698964";
	private static final String SITE = "QVI";
	private static final String EMAIL_INDIVIDUS = "clarkkent@yopmail.com";
	//private static final String EMAIL_INDIVIDUS = "test@test.com";
	private static final String GIN_INDIVIDUS = "400415094784";
	private static final String GIN_INDIVIDUS_PROSPECT = "900025059447";
	private static final String EMAIL_INDIVIDUS_PROSPECT = "tomtest@test.com";
	

	private static final String CP_DOMAIN = "S";
	private static final String CP_ComType = "AF";
	private static final String CP_ComGroupType = "P";
	
	private static final Boolean REMOVE_MODE = false;
	
	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v8Bean")
	private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImpl;

	@Autowired
	protected AlertCheckHelper alertHelper;
//
//	@Autowired
//	protected CreateOrUpdateProspectHelper createOrUpdateProspectHelper;

	@Autowired
	protected IndividuDS individuDS;
	
//	@Autowired
//	protected IProspectDS prospectDS;

	@Autowired
	protected CommunicationPreferencesDS CommunicationPreferencesDS;
	
	@Autowired
	protected AlertDS alertDS;
	
//	@Autowired
//	protected IAlertProspectDS alertProspectDS;
	
	@Autowired
	protected RefAlertDS refAlertDS;

	private List<RefAlertDTO> listRefInp;
	private List<RefAlertDTO> listRefEnv;

	private String GIN_PARAMETER = "gin";
	private String EMAIL_PARAMETER = "email";

	/*
	 * Create alert for random Email
	 */
	@Test
	public void test_CreateAlertForIndividualWithEmail() throws BusinessErrorBlocBusinessException, JrafDomainException {

		removeAllAlertForGin(GIN_INDIVIDUS);
		logger.info("Test CreateAlertForIndividualWithEmail start...");
		
		CreateUpdateIndividualRequest request = generateAnIndividusAlertRequest(EMAIL_INDIVIDUS, EMAIL_PARAMETER);
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);
		
		AlertDTO alert = findLastAlertByGin(response.getGin());
		if(alert != null){
			logger.info("alertID = "+alert.getAlertId());
			remove(alert);
			Assert.assertTrue(true);
		}else{
			Assert.assertTrue(false);
		}
		logger.info("Test CreateAlertForIndividual stop...");
	}
	
	@Test
	public void test_CreateAlertForProspectWithEmail() throws BusinessErrorBlocBusinessException, JrafDomainException {

		logger.info("Test CreateAlertForProspectWithEmail start...");

		removeAllAlertForGin(GIN_INDIVIDUS_PROSPECT);
		
		CreateUpdateIndividualRequest request = generateAnIndividusAlertRequest(EMAIL_INDIVIDUS_PROSPECT, EMAIL_PARAMETER);
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);
				
		AlertDTO alert = findLastAlertProspectByGin(response.getGin());
		if(alert != null){
			logger.info("alertID = "+alert.getAlertId());
			removeProspect(alert);
			Assert.assertTrue(true);
		}else{
			Assert.assertTrue(false);
		}
		logger.info("Test CreateAlertForIndividual stop...");
	}

	/*
	 * Create alert with gin for individual
	 */
	@Test
	public void test_CreateAlertForIndividualWithGin() throws JrafDomainException, BusinessErrorBlocBusinessException {

		logger.info("Test CreateAlertForIndividualWithGin start...");

		CreateUpdateIndividualRequest request = generateAnIndividusAlertRequest(GIN_INDIVIDUS, GIN_PARAMETER);
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);
		
		AlertDTO alert = findLastAlertByGin(GIN_INDIVIDUS);
		if(alert != null){
			logger.info("alertID = "+alert.getAlertId());
			//remove(alert);
			Assert.assertTrue(true);
		}else{
			Assert.assertTrue(false);
		}
		logger.info("Test CreateAlertForIndividual stop...");
	}
	

	/*
	 * Create alert with gin for individual
	 */
	@Test
	public void test_CreateAlertForExistingProspectWithGin() throws JrafDomainException, BusinessErrorBlocBusinessException {

		logger.info("Test CreateAlertForExistingProspectWithGin start...");

		CreateUpdateIndividualRequest request = generateAnIndividusAlertRequest(GIN_INDIVIDUS_PROSPECT, "gin");		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);
		
		AlertDTO alert = findLastAlertProspectByGin(GIN_INDIVIDUS_PROSPECT);
		if(alert != null){
			logger.info("alertID = "+alert.getAlertId());
			removeProspect(alert);
			Assert.assertTrue(true);
		}else{
			Assert.assertTrue(false);
		}
		logger.info("Test CreateAlertForIndividual stop...");
	}
	
	
	
	@Test
	public void test_CreateAlert2TimeWithDifferentType_REPIND637() throws JrafDomainException, BusinessErrorBlocBusinessException {

		logger.info("Test test_CreateAlert2TimeWithDifferentType_REPIND637 start...");
		
		CreateUpdateIndividualRequest request = generateMandatoryRequest();
		//generate alert part
		AlertRequest alertRequest = new AlertRequest();
		Alert alert = new Alert();
		alert.setType("N");
		alert.setOptIn("Y");
		
		AlertData alertData = new AlertData();
		alertData.setKey("ORIGIN");
		alertData.setValue("CDG");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("ORIGIN_TYPE");
		alertData.setValue("A");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("DESTINATION");
		alertData.setValue("NCE");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("DESTINATION_TYPE");
		alertData.setValue("C");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("START_DATE");
		alertData.setValue("03102016");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("END_DATE");
		alertData.setValue("31102016");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("CABIN");
		alertData.setValue("C");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("ORIGIN_ENR");
		alertData.setValue("Ori");
		alert.getAlertData().add(alertData);
		
		
		alertRequest.getAlert().add(alert);
		request.setAlertRequest(alertRequest);
		
		// individual part
		request.getComunicationPreferencesRequest().add(generateComPrefRequest());
		request.setIndividualRequest(generateIndividualInformation(GIN_INDIVIDUS_PROSPECT));		
		
		request.getAlertRequest().getAlert().get(0).setType("N");
		request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().setCommunicationGroupeType("N");

		try{
			logger.info(createOrUpdateIndividualImpl.createIndividual(request).toString());
		}catch(Exception e){
			//don't care
		}
		
		request = generateMandatoryRequest();
		//generate alert part
		alertRequest = new AlertRequest();
		alert = new Alert();
		alert.setType("P");
		alert.setOptIn("Y");
		
		alertData = new AlertData();
		alertData.setKey("ORIGIN");
		alertData.setValue("CDG");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("ORIGIN_TYPE");
		alertData.setValue("A");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("DESTINATION");
		alertData.setValue("NCE");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("DESTINATION_TYPE");
		alertData.setValue("C");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("START_DATE");
		alertData.setValue("03102016");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("END_DATE");
		alertData.setValue("31102016");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("CABIN");
		alertData.setValue("C");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("ORIGIN_ENR");
		alertData.setValue("Ori");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("PRICE_MAX_THRESHOLD");
		alertData.setValue("3500");
		alert.getAlertData().add(alertData);
		
		
		alertRequest.getAlert().add(alert);
		request.setAlertRequest(alertRequest);
		
		// individual part
		request.getComunicationPreferencesRequest().add(generateComPrefRequest());
		request.setIndividualRequest(generateIndividualInformation(GIN_INDIVIDUS_PROSPECT));		
		
		try{
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);
			if(!response.isSuccess()){
				fail();
			}
		}catch(Exception e){
			fail(e.getMessage());
		}
		logger.info("Test test_CreateAlert2TimeWithDifferentType_REPIND637 stop...");
	}
		
	

	/*
	 * Test when we add a fakeKey
	 */
	@Test
	public void test_CreateAlertForIndividualErrorKeyNotAllowed() throws JrafDomainException, BusinessErrorBlocBusinessException {

		logger.info("Test CreateAlertForIndividualErrorKeyNotAllowed start...");

		CreateUpdateIndividualRequest request = generateAnIndividusAlertRequest(GIN_INDIVIDUS, GIN_PARAMETER);
		
		//generate alertData part
		AlertData dataError = new AlertData();
		dataError.setKey("FAKEKEY");
		dataError.setValue("EMPTY");
		request.getAlertRequest().getAlert().get(0).getAlertData().add(request.getAlertRequest().getAlert().get(0).getAlertData().size()- 1,dataError);
		
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue("Wrong key not allowed", true);
		}
		logger.info("Test CreateAlertForIndividualErrorKeyNotAllowed stop...");
	}
	
	
	/*
	 * Test when we remove a mandatory key
	 */
	@Test
	public void test_CreateAlertForIndividualErrorKeyMandatoryMissing() throws JrafDomainException {

		logger.info("Test CreateAlertForIndividualErrorKeyMandatoryMissing start...");
		generateRefAlert();
		CreateUpdateIndividualRequest request = generateMandatoryRequest();
		request.getComunicationPreferencesRequest().add(generateComPrefRequest());
		request.getEmailRequest().add(generateEmailRequest(EMAIL_INDIVIDUS));

		//generate alert part
		AlertRequest alertRequest = new AlertRequest();
		alertRequest.getAlert().add(generateNewAlertObject("P", true));
		request.setAlertRequest(alertRequest);
		
		AlertData search = request.getAlertRequest().getAlert().get(0).getAlertData().get(getRandomNum(0, request.getAlertRequest().getAlert().get(0).getAlertData().size() - 1 ));
		logger.info("AlertData removed : " + search.getKey());
		request.getAlertRequest().getAlert().get(0).getAlertData().remove(search);
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);
			Assert.assertTrue("Key missing", false);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue("Key missing", true);
		}
		logger.info("Test CreateAlertForIndividualErrorKeyMandatoryMissing stop...");
	}
	
	/*
	 * Test when we remove a mandatory key
	 */
	@Test
	public void test_CreateAlertForIndividualErrorNoAlertData() throws JrafDomainException, BusinessErrorBlocBusinessException {

		logger.info("Test CreateAlertForIndividualErrorKeyNotAllowed start...");

		CreateUpdateIndividualRequest request = generateAnIndividusAlertRequest(GIN_INDIVIDUS, GIN_PARAMETER);
		request.getAlertRequest().getAlert().get(0).getAlertData().clear();
		
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertTrue("No alert Data sent", true);
		}
		logger.info("Test CreateAlertForIndividualErrorKeyNotAllowed stop...");
	}
	
	/*
	 * Test when we remove a mandatory key
	 */
	@Test
	public void test_UpdateAlertForIndividual() throws JrafDomainException, BusinessErrorBlocBusinessException {

		logger.info("Test CreateAlertForIndividualWithGin start...");

		removeAllAlertForGin(GIN_INDIVIDUS);
		
		CreateUpdateIndividualRequest request = generateAnIndividusAlertRequest(GIN_INDIVIDUS, "gin");
		createOrUpdateIndividualImpl.createIndividual(request);
		
		AlertDTO alertOri = findLastAlertByGin(GIN_INDIVIDUS);
		if(alertOri != null){
			logger.info("alertID = "+alertOri.getAlertId());
			request = generateAnIndividusAlertRequest(GIN_INDIVIDUS, "gin");
			request.getAlertRequest().getAlert().set(0, generateNewAlertObject("P", false, "UPDATE"));
			request.getAlertRequest().getAlert().get(0).setAlertId(alertOri.getAlertId().toString());
			AlertData comPref = TransformAlertDataDTOToRequest(alertHelper.findAlertDataByKey(alertOri.getAlertDataDTO(), AlertCheckHelper.PromoKeyComPrefId));			
			request.getAlertRequest().getAlert().get(0).getAlertData().add(comPref);
			createOrUpdateIndividualImpl.createIndividual(request);

			AlertDTO alertUpd = findLastAlertByGin(GIN_INDIVIDUS);
			if(alertOri.getAlertId().intValue() == alertUpd.getAlertId().intValue()){
				Assert.assertTrue(true);
			}else{
				Assert.assertTrue(false);
			}
		}else{
			Assert.assertTrue(false);
		}
		logger.info("Test CreateAlertForIndividual stop...");
	}
	
	private AlertData TransformAlertDataDTOToRequest(AlertDataDTO alertDataDTO) {
		AlertData ret = new AlertData();
		if(alertDataDTO.getKey() != null){
			ret.setKey(alertDataDTO.getKey());
		}
		if(alertDataDTO.getValue() != null){
			ret.setValue(alertDataDTO.getValue());
		}
		return ret;
	}

	/*
	 * Test error when we go over the limit
	 */
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 3 mins
	public void test_LimitAlert() throws JrafDomainException, BusinessErrorBlocBusinessException {

		logger.info("Test LimitAlert start...");
		generateRefAlert();
		CreateUpdateIndividualRequest request = generateMandatoryRequest();
		request.setIndividualRequest(generateIndividualInformation(GIN_INDIVIDUS));// individual part
		request.getComunicationPreferencesRequest().add(generateComPrefRequest());
		
		//clean all alert before remove
		removeAllAlertForGin(GIN_INDIVIDUS);
		
		// Keep this magic line
		List<AlertDTO> alertsForGin = alertDS.findAlert(GIN_INDIVIDUS);
		
		//generate max alert
		Integer max = Integer.parseInt(alertHelper.findRefAlertByString(alertHelper.MaxAlert, "ENV").getValue());
		AlertRequest alertRequest = new AlertRequest();
		for (int i = 0; i < max - 1; i++) {
			alertRequest.getAlert().add(generateNewAlertObject("P", true));
		}
		
		request.setAlertRequest(alertRequest);
		
		//we send limit of alert for this individus
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);
		
		//we send an new alert = generate error
		//we add alert optin to N
		alertRequest = new AlertRequest();
		Alert alert;
		for (int i = 0; i < 5; i++) {
			alert = generateNewAlertObject("P", true);
			alert.setOptIn("N");
			alertRequest.getAlert().add(alert);
			
		}
		
		request.setAlertRequest(alertRequest);
		response = createOrUpdateIndividualImpl.createIndividual(request);
		
		logger.info("Error correctely catch when we add max alert ");
		alertRequest = new AlertRequest();
		alertRequest.getAlert().add(generateNewAlertObject("P", true));
		alertRequest.getAlert().add(generateNewAlertObject("P", true));
		request.setAlertRequest(alertRequest);
		
		try {
			response = createOrUpdateIndividualImpl.createIndividual(request);
			Assert.assertTrue(false);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info("Error correctely catch when we add max alert ");
			Assert.assertTrue(true);
		}finally{
			removeAllAlertForGin(GIN_INDIVIDUS);
		}
		logger.info("Test LimitAlert stop...");
	}
	
	/*
	 * Test error when we go far over the limit with some OPTOUT alert  
	 */
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 2 mins
	public void test_LimitAlertWithAlertOptOUT() throws JrafDomainException, BusinessErrorBlocBusinessException {

		logger.info("Test LimitAlertWithAlertNotOptIN start...");
		generateRefAlert();
		CreateUpdateIndividualRequest request = generateMandatoryRequest();
		request.setIndividualRequest(generateIndividualInformation(GIN_INDIVIDUS));// individual part
		request.getComunicationPreferencesRequest().add(generateComPrefRequest());
		
		//clean all alert before remove
		removeAllAlertForGin(GIN_INDIVIDUS);
		
		//generate max alert
		Integer max = Integer.parseInt(alertHelper.findRefAlertByString(alertHelper.MaxAlert, "ENV").getValue());
		AlertRequest alertRequest = new AlertRequest();
		for (int i = 0; i < max-5; i++) {
			alertRequest.getAlert().add(generateNewAlertObject("P", true));
		}
		
		request.setAlertRequest(alertRequest);
		createOrUpdateIndividualImpl.createIndividual(request);

		Alert alert;
		for (int i = 0; i < 10; i++) {
			alert = generateNewAlertObject("P", true);
			alert.setOptIn("N");
			alertRequest.getAlert().add(alert);
		}
		request.setAlertRequest(alertRequest);
		//we send limit of alert for this individus
		try{	
			createOrUpdateIndividualImpl.createIndividual(request);
			Assert.assertTrue(false);
		} catch (BusinessErrorBlocBusinessException e) {
			logger.info("Error correctely catch when we add max alert ");
			Assert.assertTrue(true);
		}finally{
			removeAllAlertForGin(GIN_INDIVIDUS);
		}
		logger.info("Test LimitAlertWithAlertNotOptIN stop...");
	}
	
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 45 secs
	public void test_UpdateAlertOptOut() throws JrafDomainException, BusinessErrorBlocBusinessException {

		logger.info("Test UpdateAlertOptOut start...");
		generateRefAlert();
		CreateUpdateIndividualRequest request = generateMandatoryRequest();
		request.setIndividualRequest(generateIndividualInformation(GIN_INDIVIDUS));// individual part
		request.getComunicationPreferencesRequest().add(generateComPrefRequest());
		
		//clean all alert before remove
		removeAllAlertForGin(GIN_INDIVIDUS);
		
		//generate alert OPTIN
		AlertRequest alertRequest = new AlertRequest();
		for (int i = 0; i < 5; i++) {
			alertRequest.getAlert().add(generateNewAlertObject("P", true));
		}
		request.setAlertRequest(alertRequest);
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);
		
		Assert.assertNotNull(response);		
		Assert.assertTrue(response.isSuccess());
		
		//update OPTIN to no
		List<AlertDTO> list = alertDS.findAlert(GIN_INDIVIDUS);
		for (AlertDTO alertDTO : list) {
			alertRequest = new AlertRequest();
			alertDTO.setOptIn("N");
			alertRequest.getAlert().add(IndividuResponseTransform.transformToAlert(alertDTO));
			request.setAlertRequest(alertRequest);
			request.setProcess("A");
			CreateUpdateIndividualResponse response2 = createOrUpdateIndividualImpl.createIndividual(request);
			
			Assert.assertNotNull(response2);		
			Assert.assertTrue(response2.isSuccess());
		}
		
		//find user compref
		//sComPref.setComGroupType("P");
		//sComPref.setDomain("S");
		//sComPref.setComType("AF");
		//sComPref.setSubscribe("N");
		checkComPrefStatus(GIN_INDIVIDUS, "N");
		logger.info("Test UpdateAlertOptOut stop...");
		
	}
	
	

	@Test
	public void test_SetAllAlertOptOut() throws JrafDomainException, BusinessErrorBlocBusinessException {

		logger.info("Test UpdateAlertOptOut start...");
		generateRefAlert();
		CreateUpdateIndividualRequest request = generateMandatoryRequest();
		request.setIndividualRequest(generateIndividualInformation(GIN_INDIVIDUS));// individual part
		request.getComunicationPreferencesRequest().add(generateComPrefRequest());
		
		//clean all alert before remove
		removeAllAlertForGin(GIN_INDIVIDUS);
		
		//generate alert OPTIN
		AlertRequest alertRequest = new AlertRequest();
		for (int i = 0; i < 5; i++) {
			alertRequest.getAlert().add(generateNewAlertObject("P", true));
		}
		request.setAlertRequest(alertRequest);
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);
		
		//update OPTIN to no

		request = generateMandatoryRequest();
		request.setIndividualRequest(generateIndividualInformation(GIN_INDIVIDUS));// individual part
		request.getComunicationPreferencesRequest().add(generateComPrefRequest("N"));
		
		response = createOrUpdateIndividualImpl.createIndividual(request);
		
		//find user compref
		checkComPrefStatus(GIN_INDIVIDUS, "N");
		logger.info("Test UpdateAlertOptOut stop...");
	}
	
	@Test		
	@Rollback(true)
	public void test_CreatePromoAlert() throws JrafDomainException, BusinessErrorBlocBusinessException {		
		logger.info("Test test_CreatePromoAlert start...");		
				
		CreateUpdateIndividualRequest request = generateMandatoryRequest();		
		//generate alert part		
		AlertRequest alertRequest = new AlertRequest();		
		Alert alert = new Alert();		
		AlertData alertData = null;
				
		request = generateMandatoryRequest();		
		//generate alert part		
		alertRequest = new AlertRequest();		
		alert = new Alert();		
		alert.setType("P");		
		alert.setOptIn("Y");		
				
		alertData = new AlertData();		
		alertData.setKey("ORIGIN");		
		alertData.setValue("CDG");		
		alert.getAlertData().add(alertData);		
				
		alertData = new AlertData();		
		alertData.setKey("ORIGIN_TYPE");		
		alertData.setValue("A");		
		alert.getAlertData().add(alertData);		
				
		alertData = new AlertData();		
		alertData.setKey("DESTINATION");		
		alertData.setValue("NCE");		
		alert.getAlertData().add(alertData);		
				
		alertData = new AlertData();		
		alertData.setKey("DESTINATION_TYPE");		
		alertData.setValue("C");		
		alert.getAlertData().add(alertData);		
				
		alertData = new AlertData();		
		alertData.setKey("START_DATE");		
		alertData.setValue("03102016");		
		alert.getAlertData().add(alertData);		
				
		alertData = new AlertData();		
		alertData.setKey("END_DATE");		
		alertData.setValue("31102016");		
		alert.getAlertData().add(alertData);		
				
		alertData = new AlertData();		
		alertData.setKey("CABIN");		
		alertData.setValue("C");		
		alert.getAlertData().add(alertData);		
				
		alertData = new AlertData();		
		alertData.setKey("ORIGIN_ENR");		
		alertData.setValue("Ori");		
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();		
		alertData.setKey("PRICE_MAX_THRESHOLD");		
		alertData.setValue("15");		
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();		
		alertData.setKey("CURRENCY");		
		alertData.setValue("EUR");		
		alert.getAlertData().add(alertData);
				
		alertRequest.getAlert().add(alert);		
		request.setAlertRequest(alertRequest);		
				
		// individual part		
		request.getComunicationPreferencesRequest().add(generateComPrefRequest());		
		request.setIndividualRequest(generateIndividualInformation(GIN_INDIVIDUS_PROSPECT));				
				
		try{		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);		
			if(!response.isSuccess()){		
				fail();		
			}		
		}catch(Exception e){		
			fail(e.getMessage());		
		}		
		logger.info("Test test_CreatePromoAlert stop...");		
	}
	
	@Test		
	@Rollback(true)
	public void test_CreatePromoAlertWithlessProcessA() throws JrafDomainException, BusinessErrorBlocBusinessException {		
		logger.info("Test test_CreatePromoAlert start...");		
				
				
		CreateUpdateIndividualRequest request =  new CreateUpdateIndividualRequest();
		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		request.setProcess("");
		request.setRequestor(requestor);
				
		//generate alert part		
		AlertRequest alertRequest = new AlertRequest();		
		Alert alert = new Alert();		
		AlertData alertData = null;
				
		//generate alert part		
		alertRequest = new AlertRequest();		
		alert = new Alert();		
		alert.setType("P");		
		alert.setOptIn("Y");		
				
		alertData = new AlertData();		
		alertData.setKey("ORIGIN");		
		alertData.setValue("CDG");		
		alert.getAlertData().add(alertData);		
				
		alertData = new AlertData();		
		alertData.setKey("ORIGIN_TYPE");		
		alertData.setValue("A");		
		alert.getAlertData().add(alertData);		
				
		alertData = new AlertData();		
		alertData.setKey("DESTINATION");		
		alertData.setValue("NCE");		
		alert.getAlertData().add(alertData);		
				
		alertData = new AlertData();		
		alertData.setKey("DESTINATION_TYPE");		
		alertData.setValue("C");		
		alert.getAlertData().add(alertData);		
				
		alertData = new AlertData();		
		alertData.setKey("START_DATE");		
		alertData.setValue("03102016");		
		alert.getAlertData().add(alertData);		
				
		alertData = new AlertData();		
		alertData.setKey("END_DATE");		
		alertData.setValue("31102016");		
		alert.getAlertData().add(alertData);		
				
		alertData = new AlertData();		
		alertData.setKey("CABIN");		
		alertData.setValue("C");		
		alert.getAlertData().add(alertData);		
				
		alertData = new AlertData();		
		alertData.setKey("ORIGIN_ENR");		
		alertData.setValue("Ori");		
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();		
		alertData.setKey("PRICE_MAX_THRESHOLD");		
		alertData.setValue("15");		
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();		
		alertData.setKey("CURRENCY");		
		alertData.setValue("EUR");		
		alert.getAlertData().add(alertData);
				
		alertRequest.getAlert().add(alert);		
		request.setAlertRequest(alertRequest);		
				
		// individual part		
		request.getComunicationPreferencesRequest().add(generateComPrefRequest());		
		request.setIndividualRequest(generateIndividualInformation(GIN_INDIVIDUS_PROSPECT));				
				
		try{		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);		
			if(response.isSuccess()){		
				fail();		
			}		
		}catch(Exception e){		
			Assert.assertTrue(true);		
		}		
		logger.info("Test test_CreatePromoAlert stop...");		
	}
	
	private CreateUpdateIndividualRequest generateAnIndividusAlertRequest(String search, String parameter) throws JrafDomainException, BusinessErrorBlocBusinessException {

		generateRefAlert();
		CreateUpdateIndividualRequest request = generateMandatoryRequest();

		//generate alert part
		AlertRequest alertRequest = new AlertRequest();
		alertRequest.getAlert().add(generateNewAlertObject("P", true));
		request.setAlertRequest(alertRequest);
		
		// individual part
		request.getComunicationPreferencesRequest().add(generateComPrefRequest());
		if(parameter.equals(GIN_PARAMETER)){
			request.setIndividualRequest(generateIndividualInformation(search));
		}else{
			request.getEmailRequest().add(generateEmailRequest(search));
		}
		
		return request;
		
	}

	private void removeAllAlertForGin(String gin) throws JrafDomainException {
		AlertDTO search = new AlertDTO();
		search.setSgin(gin);
		search.setType("P");
		List<AlertDTO> res = alertDS.findByExample(search);
		
		if(res.size() >=  1){
			for (AlertDTO alertDTO : res) {
				remove(alertDTO, true);
			}
		}
	}
	private void checkComPrefStatus(String gin, String status) throws JrafDomainException {
		CommunicationPreferencesDTO sComPref = new CommunicationPreferencesDTO();
		sComPref.setGin(GIN_INDIVIDUS);
		List<CommunicationPreferencesDTO> lComPref = CommunicationPreferencesDS.findCommunicationPreferences(gin);
		if(lComPref.size() > 0){
			for (CommunicationPreferencesDTO cpDTO : lComPref) {
				if(cpDTO.getComGroupType().equals(CP_ComGroupType) && cpDTO.getDomain().equals(CP_DOMAIN) && cpDTO.getComType().equals(CP_ComType)){
					if(cpDTO.getSubscribe().equals(status)){
						Assert.assertTrue(true);
						return;
					}else{
						logger.info("ComPref : "+cpDTO.getComPrefId()+" is not set to N");
					}
				}
			}
		}
		logger.info("Gin : "+gin+" has no compref");
		Assert.assertTrue(false);
		
	}

	/*
	 * Function used to test
	 */

	private IndividualRequest generateIndividualInformation(String ginIndividus) {
		IndividualRequest ret = new IndividualRequest();
		IndividualInformationsV3 indiInfo = new IndividualInformationsV3();
		indiInfo.setIdentifier(ginIndividus);
		IndividualRequest indiReq = new IndividualRequest();
		indiReq.setIndividualInformations(indiInfo);
		ret.setIndividualInformations(indiInfo);
		return ret;
	}

	private Integer getRandomNum(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	
	private void generateRefAlert() throws JrafDomainException {
		alertHelper.updatelistsRef("P");
		this.listRefInp =  alertHelper.listRefInp;
		this.listRefEnv =  alertHelper.listRefEnv;
	}

	
	private AlertDTO findLastAlertByGin(Long gin) throws JrafDomainException {
		return findLastAlertByGin(gin.toString());
	}

	private AlertDTO findLastAlertByGin(String gin) throws JrafDomainException {

		AlertDTO search = new AlertDTO();
		search.setSgin(gin);
		List<AlertDTO> list = alertDS.findAlert(gin);
		AlertDTO ret = null;
		for (AlertDTO alertDTO : list) {
			if(ret == null || alertDTO.getAlertId() > ret.getAlertId()){
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
			if(ret == null || alertDTO.getAlertId() > ret.getAlertId()){
				ret = alertDTO;
			}
		}
		return ret;
		
	}
	private EmailRequest generateEmailRequest(String mail) {
		Email email = new Email();
		email.setEmail(mail);
		email.setEmailOptin("T");
		email.setMediumCode("D");
		email.setMediumStatus("V");
		EmailRequest ret = new EmailRequest();
		ret.setEmail(email);
		 
		return ret;
	}


	private ComunicationPreferencesRequest generateComPrefRequest() {
		return generateComPrefRequest("Y");
	}
	private ComunicationPreferencesRequest generateComPrefRequest(String optin) {
		ComunicationPreferencesRequest ret = new ComunicationPreferencesRequest();
		ret.setCommunicationPreferences(new CommunicationPreferences());
		ret.getCommunicationPreferences().setDomain("S");
		ret.getCommunicationPreferences().setCommunicationGroupeType("P");
		ret.getCommunicationPreferences().setCommunicationType("AF");
		ret.getCommunicationPreferences().setOptIn(optin);
		ret.getCommunicationPreferences().setSubscriptionChannel("TEST");
		ret.getCommunicationPreferences().setDateOfConsent(new Date());
		
		MarketLanguage ml = new MarketLanguage();
		ml.setDateOfConsent(new Date());
		ml.setMarket("FR");
		ml.setLanguage(LanguageCodesEnum.FR);
		ml.setOptIn(optin);
		ret.getCommunicationPreferences().getMarketLanguage().add(ml);
		
		return ret;
	}

	private CreateUpdateIndividualRequest generateMandatoryRequest() {
		
		CreateUpdateIndividualRequest ret =  new CreateUpdateIndividualRequest();
		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		ret.setRequestor(requestor);
		
		ret.setProcess(ProcessEnum.A.getCode());
		
		return ret;
	}

	private Alert generateNewAlertObject(String type, boolean onlyMandatory) throws JrafDomainException{
		return generateNewAlertObject(type, onlyMandatory, "TU"); //Si on precise pas le text, On envoie un text generique pour les TU
	}

	private Alert generateNewAlertObject(String type, boolean onlyMandatory, String text) throws JrafDomainException{
		Alert ret = new Alert();
		ret.setType(type);
		ret.setOptIn("Y");
		for (RefAlertDTO refAlertDTO : listRefInp) {
			if(refAlertDTO.getMandatory().equals("Y") || !onlyMandatory){
				AlertData data = new AlertData();
				data.setKey(refAlertDTO.getKey());
				switch (refAlertDTO.getValue()) {
				case "DATE":
					data.setValue("20092016");
					break;
				default:
					data.setValue(text);
					break;
				}
				if(!refAlertDTO.getKey().equals(AlertCheckHelper.PromoKeyComPrefId)){
					ret.getAlertData().add(data);
				}
			}
		}
		return ret;
	}
	

	private void remove(AlertDTO alert) throws JrafDomainException {
		remove(alert, this.REMOVE_MODE);
	}
	private void remove(AlertDTO alert, boolean remove) throws JrafDomainException {
		if(remove){
			alertDS.remove(alert);
		}
	}
	
	private void removeProspect(AlertDTO alert) throws JrafDomainException {
		removeProspect(alert, false);
	}
	private void removeProspect(AlertDTO alert, boolean remove) throws JrafDomainException {
		if(remove){
			alertDS.remove(alert);
		}
	}



	@Test
	public void test_CreateAlert_And_UpdateComPref_For_Optout() throws JrafDomainException {

		logger.info("Test test_CreateAlert_And_UpdateComPref_For_Optout start...");
		
		CreateUpdateIndividualRequest request = generateMandatoryRequest();
		//generate alert part
		AlertRequest alertRequest = new AlertRequest();
		Alert alert = new Alert();
		alert.setType("P");
		alert.setOptIn("Y");
		
		AlertData alertData = new AlertData();
		alertData.setKey("ORIGIN");
		alertData.setValue("CDG");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("ORIGIN_TYPE");
		alertData.setValue("A");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("DESTINATION");
		alertData.setValue("NCE");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("DESTINATION_TYPE");
		alertData.setValue("C");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("START_DATE");
		alertData.setValue("03102019");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("END_DATE");
		alertData.setValue("31102031");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("CABIN");
		alertData.setValue("C");
		alert.getAlertData().add(alertData);
		
		alertData = new AlertData();
		alertData.setKey("ORIGIN_ENR");
		alertData.setValue("Ori");
		alert.getAlertData().add(alertData);
		
		
		alertRequest.getAlert().add(alert);
		request.setAlertRequest(alertRequest);
		
		// individual part
		request.getComunicationPreferencesRequest().add(generateComPrefRequest());
		request.setIndividualRequest(generateIndividualInformation(GIN_INDIVIDUS_PROSPECT));		
		
//		request.getAlertRequest().getAlert().get(0).setType("P");
		request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().setCommunicationGroupeType("P");
		request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().setCommunicationType("AF");;
		request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().setDomain("S");

		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);
			
			Assert.assertNotNull(response);
			Assert.assertTrue(response.isSuccess());
			
			// ALERT DATA CREATED
			
			// PROSPECT CREATED
			
			
		} catch (BusinessErrorBlocBusinessException e) {
			
			Assert.fail("There is a problem : " + e.getMessage());
		}
		
		logger.info("Test test_CreateAlert_And_UpdateComPref_For_Optout stop...");
	}
}
