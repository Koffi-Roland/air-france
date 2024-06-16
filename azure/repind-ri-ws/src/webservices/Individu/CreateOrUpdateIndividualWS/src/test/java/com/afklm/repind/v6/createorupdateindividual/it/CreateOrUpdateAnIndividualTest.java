package com.afklm.repind.v6.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v6.createorupdateindividualws.CreateOrUpdateIndividualImplV6;
import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v6.request.ComunicationPreferencesRequest;
import com.afklm.soa.stubs.w000442.v6.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v6.request.TelecomRequest;
import com.afklm.soa.stubs.w000442.v6.siccommontype.Requestor;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.CommunicationPreferences;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.IndividualInformations;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.Telecom;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.TerminalTypeEnum;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualTest extends CreateOrUpdateIndividualImplV6{

	private static final String SITE = "QVI";
	private static final String SIGNATURE = "W000442";
	private static final String GIN = "400452712062";
	private static final String CIVILITY = "MR";
	private static final String STATUS = "P";
	private static final String LAST_NAME = "EON";
	private static final String FIRST_NAME = "Kevin";

	private static Date NOW = new Date();

	private CreateUpdateIndividualRequest request;

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v6Bean")
	private CreateOrUpdateIndividualImplV6 createOrUpdateIndividualImplV6;	

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp(){

		request = new CreateUpdateIndividualRequest();

		Requestor requestor = new Requestor();
		IndividualRequest individualRequest = new IndividualRequest();
		IndividualInformations individualInformation = new IndividualInformations();
		individualRequest.setIndividualInformations(individualInformation);

		request.setRequestor(requestor);	
		request.setIndividualRequest(individualRequest);

	}

	private void initializeRequestor(CreateUpdateIndividualRequest request){
		request.getRequestor().setSite(SITE);
		request.getRequestor().setSignature(SIGNATURE);
	}

	/*
	 * CHECK INPUT METHOD 
	 */
	@Test
	public void testCheckInputCreationOK() throws MissingParameterException, InvalidParameterException {

		initializeRequestor(request);

		request.getIndividualRequest().getIndividualInformations().setBirthDate(NOW);
		request.getIndividualRequest().getIndividualInformations().setCivility(CIVILITY);
		request.getIndividualRequest().getIndividualInformations().setStatus(STATUS);
		request.getIndividualRequest().getIndividualInformations().setLastNameSC(LAST_NAME);
		request.getIndividualRequest().getIndividualInformations().setFirstNameSC(FIRST_NAME);

		checkInput(request);
	}

	// STATUS IS MISSING FOR EXAMPLE 
	@Test
	public void testCheckInputCreationKO() throws MissingParameterException, InvalidParameterException {

		initializeRequestor(request);

		request.getIndividualRequest().getIndividualInformations().setBirthDate(NOW);
		request.getIndividualRequest().getIndividualInformations().setCivility(CIVILITY);		
		request.getIndividualRequest().getIndividualInformations().setLastNameSC(LAST_NAME);
		request.getIndividualRequest().getIndividualInformations().setFirstNameSC(FIRST_NAME);

		expectedEx.expect(MissingParameterException.class);
		expectedEx.expectMessage("The field status is mandatory");
		checkInput(request);
	}

	// IF GIN IS GIVEN, IT IS OK
	@Test
	public void testCheckInputCreationReturn() throws MissingParameterException, InvalidParameterException {

		initializeRequestor(request);		
		request.getIndividualRequest().getIndividualInformations().setIdentifier(GIN);

		checkInput(request);		
	}

	// CAN NOT CREATE COMMUNICATION PREFERENCES WHILE CREATING AN INDIVIDUAL
	@Test
	public void testCheckInputCreationWithCommunicationPreferences() throws MissingParameterException, InvalidParameterException {

		initializeRequestor(request);		

		request.getIndividualRequest().getIndividualInformations().setBirthDate(NOW);
		request.getIndividualRequest().getIndividualInformations().setCivility(CIVILITY);		
		request.getIndividualRequest().getIndividualInformations().setStatus(STATUS);
		request.getIndividualRequest().getIndividualInformations().setLastNameSC(LAST_NAME);
		request.getIndividualRequest().getIndividualInformations().setFirstNameSC(FIRST_NAME);

		ComunicationPreferencesRequest communicationPreferencesRequest = new ComunicationPreferencesRequest();
		CommunicationPreferences commPref = new CommunicationPreferences();		
		commPref.setDomain("S");
		commPref.setCommunicationGroupeType("AF");
		commPref.setCommunicationType("N");
		communicationPreferencesRequest.setCommunicationPreferences(commPref);
		request.getComunicationPreferencesRequest().add(communicationPreferencesRequest);

		expectedEx.expect(InvalidParameterException.class);
		expectedEx.expectMessage("Unable to create an individual with communication preferences");

		checkInput(request);	
	}

	/*
	 * NORMALIZE TELECOMS 
	 */

	private void initializeTelecom(CreateUpdateIndividualRequest request, boolean isMediumCode, String ...mediumCode){

		TelecomRequest telecom = new TelecomRequest();
		Telecom t = new Telecom();
		t.setCountryCode("33");
		t.setPhoneNumber("0493564789");		
		if(isMediumCode){
			t.setMediumCode(mediumCode[0]);
		}
		t.setTerminalType(TerminalTypeEnum.FIX.toString());
		telecom.setTelecom(t);
		request.getTelecomRequest().add(telecom);
	}

	// TESTS SUR LE TRANSFORM : MEDIUM CODE EN PARTICULIER (PROPRE A LA V6)
	@Test
	public void testNormalizeTelecomsWithMissingMediumCode() throws JrafDomainException {

		initializeTelecom(request, false);

		expectedEx.expect(MissingParameterException.class);
		expectedEx.expectMessage("Missing medium code");

		normalizeTelecoms(request);		
	}

	@Test
	public void testNormalizeTelecomsMissingWithWrongMediumCode() throws JrafDomainException {

		String wrongMediumCode = "A";
		initializeTelecom(request, true, wrongMediumCode);

		expectedEx.expect(InvalidParameterException.class);
		expectedEx.expectMessage("Invalid medium code: "+wrongMediumCode);

		normalizeTelecoms(request);		
	}

}
