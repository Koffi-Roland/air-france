package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.repind.v8.createorupdateindividualws.transformers.IndividuRequestTransform;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.IndividualInformationsV3;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.service.consent.internal.ConsentDS;
import com.airfrance.repind.service.ws.internal.CreateOrUpdateIndividualDS;
import com.airfrance.repind.service.ws.internal.helpers.UltimateException;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV8ForConsentTest {

	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV8Test.class);
	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "TESTRI";
	private static final String SITE = "QVI";	
	
	@Autowired
	protected CreateOrUpdateIndividualDS createOrUpdateIndividualDS;
	
	@MockBean
	private ConsentDS consentDSMock;
	
	/** the Entity Manager*/
	@PersistenceContext(unitName="entityManagerFactoryRepind")
	private EntityManager entityManager;

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v8Bean")
	private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImplV8;


	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividualDefaultConsentsOK() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("Test start...");
		
		createOrUpdateIndividualDS.setConsentDS(consentDSMock);
		Mockito.doNothing().when(consentDSMock).createDefaultConsents(Mockito.anyString(), Mockito.anyString());
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForConsentTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForConsentTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForConsentTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("PHILIPPOT"); 
		indInfo.setFirstNameSC("PATRICK");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		
		CreateUpdateIndividualRequestDTO requestDTO = IndividuRequestTransform.transformRequestWSToDTO(request);
		
		CreateModifyIndividualResponseDTO responseDTO = new CreateModifyIndividualResponseDTO();

		responseDTO = createOrUpdateIndividualDS.createOrUpdateV8(requestDTO, responseDTO);
		
		Assert.assertNotNull(responseDTO);		
		Assert.assertTrue(responseDTO.getSuccess());
		Assert.assertNotNull(responseDTO.getGin());

		logger.info("GIN created : " + responseDTO.getGin());

		logger.info("Test stop.");
	}
	
	@Test
	@Rollback(true)
	public void testCreateOrUpdateIndividualDefaultConsentsKO() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {

		logger.info("Test start...");
		
		createOrUpdateIndividualDS.setConsentDS(consentDSMock);
		Mockito.doThrow(new JrafDomainException("Error during creation of default consents")).when(consentDSMock).createDefaultConsents(Mockito.anyString(), Mockito.anyString());
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CreateOrUpdateAnIndividualImplV8ForConsentTest.CHANNEL);
		requestor.setSite(CreateOrUpdateAnIndividualImplV8ForConsentTest.SITE);
		requestor.setSignature(CreateOrUpdateAnIndividualImplV8ForConsentTest.SIGNATURE);

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("PHILIPPOT"); 
		indInfo.setFirstNameSC("PATRICK");

		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);
		
		CreateUpdateIndividualRequestDTO requestDTO = IndividuRequestTransform.transformRequestWSToDTO(request);
		
		CreateModifyIndividualResponseDTO responseDTO = new CreateModifyIndividualResponseDTO();

		responseDTO = createOrUpdateIndividualDS.createOrUpdateV8(requestDTO, responseDTO);
		
		Assert.assertNotNull(responseDTO);		
		Assert.assertTrue(responseDTO.getSuccess());
		Assert.assertNotNull(responseDTO.getGin());

		logger.info("GIN created : " + responseDTO.getGin());

		logger.info("Test stop.");
	}
}
