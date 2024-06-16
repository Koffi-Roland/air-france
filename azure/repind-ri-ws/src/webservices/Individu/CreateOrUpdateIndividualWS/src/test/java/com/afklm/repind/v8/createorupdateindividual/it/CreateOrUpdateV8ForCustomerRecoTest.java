package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.EmailRequest;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.TelecomRequest;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.Email;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.IndividualInformationsV3;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.Telecom;
import com.airfrance.ref.type.ProcessEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.GregorianCalendar;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateV8ForCustomerRecoTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateV8ForCustomerRecoTest.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	
	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v8Bean")
	private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImpl;

	@Test
	@Ignore
	public void test_CreateOrUpdateV8_FromCustomerRecognition() throws IOException {

		logger.info("Test start...");
		
		
		logger.info("Test parcours du fichier... ");
		
		
		String fileName = "20160805.CUSTO.RECO.EXPORT.csv";
		
		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		
		// TODO : Mettre en relatif !!
		BufferedReader br = new BufferedReader(new FileReader("C:/git/sic/src/webservices/Individu/CreateOrUpdateIndividualWS/src/test/resources/" + fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();				// Lecture de la premiere ligne

	        while (line != null) {
	        	String[] lineTraveler =  line.split(";");
	        		        	
	        	String identifiant = lineTraveler[0];
	        	
	        	logger.info("test_CreateOrUpdateV7_FromCustomerRecognition " + identifiant);
	        	
	        	String civility = lineTraveler[1];
	        	String lastName = lineTraveler[2];
	        	String firstName = lineTraveler[3];
	        	String birthDate = lineTraveler[4];
	        	String eMail = lineTraveler[5];
	        	String typeEmail = lineTraveler[6];
	        	String telecom1 = lineTraveler[7];
	        	String typeTelecom1 = lineTraveler[8];
	        	String telecom2 = lineTraveler[9];
	        	String typeTelecom2 = lineTraveler[10];
	        	
	    		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
	    		
	    		request.setProcess(ProcessEnum.T.getCode());

	    		request.setRequestor(requestor);
	    		
	    		IndividualRequest indRequest = new IndividualRequest();
	    		
	    		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
	    		indInfo.setCivility(civility);
	    		indInfo.setStatus("V"); 
	    		indInfo.setLastNameSC(lastName); 
	    		indInfo.setFirstNameSC(firstName);

	    		// BIRTHDATE
	    		if (birthDate != null && !"".equals(birthDate)) {
	    			String[] bd = birthDate.split("-");
	    			int year = Integer.valueOf(bd[0]);
	    			int month = Integer.valueOf(bd[1]);
	    			int dayOfMonth = Integer.valueOf(bd[2]);
	    			GregorianCalendar gc = new GregorianCalendar(year, month, dayOfMonth);
	    			indInfo.setBirthDate(gc.getTime());
	    		}
	    		
	    		indRequest.setIndividualInformations(indInfo);
	    		
	    		// BLOC EMAIL	    		
	    		if (eMail != null && !"".equals(eMail)) {
		    		EmailRequest emailRequest = new EmailRequest();
		    		Email email = new Email();
		    		email.setEmail(eMail);
		    		email.setEmailOptin("N");
		    		email.setMediumCode(typeEmail);
		    		email.setMediumStatus("V");
		    		emailRequest.setEmail(email);
		    		request.getEmailRequest().add(emailRequest);
	    		}
/*	    		
	    		// BLOCK TELECOMS
	    		if (telecom1 != null && !"".equals(telecom1)) {

	    			telecom1 = telecom1.replace('+', ' ');
	    			telecom1 = telecom1.trim();
	    			String countryCode1 = telecom1.substring(0, 2);
	    			telecom1 = telecom1.substring(2, telecom1.length());
	    			
		    		TelecomRequest telRequest = new TelecomRequest();
		    		Telecom telecom = new Telecom();
		    		telecom.setCountryCode(null);
		    		telecom.setMediumCode(typeTelecom1);
		    		telecom.setMediumStatus("V");
		    		telecom.setTerminalType("T");
		    		telecom.setPhoneNumber(telecom1);
		    		
		    		telRequest.setTelecom(telecom);
		    		request.getTelecomRequest().add(telRequest);
	    		}

	    		// BLOCK TELECOMS
	    		if (telecom2 != null && !"".equals(telecom2)) {
	    			telecom2 = telecom2.replace('+', ' ');
	    			telecom2 = telecom2.trim();
	    			String countryCode2 = telecom2.substring(0, 2);
	    			telecom2 = telecom2.substring(2, telecom2.length());

	    			TelecomRequest telRequest = new TelecomRequest();
		    		Telecom telecom = new Telecom();
		    		telecom.setCountryCode(countryCode2);
		    		telecom.setMediumCode(typeTelecom2);
		    		telecom.setMediumStatus("V");
		    		telecom.setTerminalType("T");
		    		telecom.setPhoneNumber(telecom2);
		    		telRequest.setTelecom(telecom);
		    		request.getTelecomRequest().add(telRequest);
	    		}
*/	    		
	    		request.setIndividualRequest(indRequest);

	    		CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);

	    		// Tests
	    		Assert.assertNotNull(response);		
	    		Assert.assertNotNull(response.isSuccess());
	    		Assert.assertTrue(response.isSuccess());
	    		Assert.assertNotNull(response.getGin());
	    		Assert.assertTrue(response.getGin().startsWith("91"));
	    		
	    		Assert.assertNotNull(response.getInformationResponse());
	    		Assert.assertNotEquals(0, response.getInformationResponse());
	    		
	    		logger.info("test_CreateOrUpdateV7_FromCustomerRecognition " + response.getGin());
	        
	    		line = br.readLine();			// On lit les lignes suivantes
	        }
	        
	    } catch (Exception ex) {
	    	Assert.fail("test_CreateOrUpdateV7_FromCustomerRecognition ERROR : " + ex.getMessage());
	    } finally {
	        br.close();
	    }


		
		
		
		logger.info("Test stop.");
	}


	@Test
	@Ignore
	public void test_CreateOrUpdateV7_FromCustomerRecognition_20161007() throws IOException {

		logger.info("Test start...");
		
		logger.info("Test parcours du fichier... ");
		
		String fileName = "20161007.CUSTOMER_RECO_CBS_sample.csv";
		
		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		
		// TODO : Mettre en relatif !!
		BufferedReader br = new BufferedReader(new FileReader("C:/git/sic/src/webservices/Individu/CreateOrUpdateIndividualWS/src/test/resources/" + fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();				// Lecture de la premiere ligne

	        while (line != null) {
	        	String[] lineTraveler =  line.split(";");
	        	
	        	if (lineTraveler.length > 1) {
	        	
	        		String identifiant = lineTraveler[0];
	        	
	        		logger.info("test_CreateOrUpdateV7_FromCustomerRecognition_20161007 " + identifiant);	        	
	        	
		        	String civility = lineTraveler[1];
		        	String lastName = lineTraveler[2];
		        	String firstName = lineTraveler[3];
		        	String birthDate = lineTraveler[4];
		        	String eMail = lineTraveler[5];
		        	String typeEmail = lineTraveler[6];
		        	String countryCodeTelecom = "";
		        	String numberTelecom = "";
		        	String typeTelecom = "";
		        	if (lineTraveler.length > 7) {
			        	countryCodeTelecom = lineTraveler[7];
			        	numberTelecom = lineTraveler[8];
			        	typeTelecom = lineTraveler[9];
		        	}
		        	
		    		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		    		
		    		request.setProcess(ProcessEnum.T.getCode());
	
		    		request.setRequestor(requestor);
		    		
		    		IndividualRequest indRequest = new IndividualRequest();
		    		
		    		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		    		indInfo.setCivility(civility);
		    		indInfo.setStatus("V"); 
		    		indInfo.setLastNameSC(lastName); 
		    		indInfo.setFirstNameSC(firstName);
	
		    		// BIRTHDATE
		    		if (birthDate != null && !"".equals(birthDate)) {
		    			String[] bd = birthDate.split("-");
		    			int year = Integer.valueOf(bd[0]);
		    			int month = Integer.valueOf(bd[1]);
		    			int dayOfMonth = Integer.valueOf(bd[2]);
		    			GregorianCalendar gc = new GregorianCalendar(year, month, dayOfMonth);
		    			indInfo.setBirthDate(gc.getTime());
		    		}
		    		
		    		indRequest.setIndividualInformations(indInfo);
		    		
		    		// BLOC EMAIL	    		
		    		if (eMail != null && !"".equals(eMail)) {
			    		EmailRequest emailRequest = new EmailRequest();
			    		Email email = new Email();
			    		email.setEmail(eMail);
			    		email.setEmailOptin("N");
			    		// email.setMediumCode(typeEmail);
			    		email.setMediumCode("D");
			    		email.setMediumStatus("V");
			    		emailRequest.setEmail(email);
			    		request.getEmailRequest().add(emailRequest);
		    		}
	
		    		// BLOCK TELECOMS
		    		if (numberTelecom != null && !"".equals(numberTelecom)) {
	
			    		TelecomRequest telRequest = new TelecomRequest();
			    		Telecom telecom = new Telecom();
			    		telecom.setCountryCode(countryCodeTelecom);
			    		telecom.setMediumCode("D");
			    		telecom.setMediumStatus("V");
			    		telecom.setTerminalType("T");
			    		telecom.setPhoneNumber(numberTelecom);
			    		
			    		telRequest.setTelecom(telecom);
			    		request.getTelecomRequest().add(telRequest);
		    		}
	
		    		request.setIndividualRequest(indRequest);
	
		    		CreateUpdateIndividualResponse response = createOrUpdateIndividualImpl.createIndividual(request);
	
		    		// Tests
		    		Assert.assertNotNull(response);		
		    		Assert.assertNotNull(response.isSuccess());
		    		Assert.assertTrue(response.isSuccess());
		    		Assert.assertNotNull(response.getGin());
		    		Assert.assertTrue(response.getGin().startsWith("91"));
		    		
		    		Assert.assertNotNull(response.getInformationResponse());
		    		Assert.assertNotEquals(0, response.getInformationResponse());
		    		
		    		logger.info("test_CreateOrUpdateV7_FromCustomerRecognition_20161007 " + response.getGin());
	        	}
	        
	    		line = br.readLine();			// On lit les lignes suivantes
	        }
	        
	    } catch (Exception ex) {
	    	String messageError = "test_CreateOrUpdateV7_FromCustomerRecognition_20161007 ERROR : " + ex.getMessage(); 
	    	Assert.fail(messageError);
	    	logger.info(messageError);
	    } finally {
	        br.close();
	    }


		
		
		
		logger.info("Test stop.");
	}
}
