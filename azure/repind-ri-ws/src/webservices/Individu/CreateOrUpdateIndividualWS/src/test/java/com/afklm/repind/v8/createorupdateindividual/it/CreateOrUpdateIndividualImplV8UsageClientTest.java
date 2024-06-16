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
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repind.dao.individu.UsageClientsRepository;
import com.airfrance.repind.entity.individu.UsageClients;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateIndividualImplV8UsageClientTest {
	private static final Log logger = LogFactory.getLog(CreateOrUpdateIndividualImplV8UsageClientTest.class);

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v8Bean")
	private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImplV8;
	
	 @Autowired
	private UsageClientsRepository usageClientsRepository;
	 @PersistenceContext(unitName = "entityManagerFactoryRepind")
		protected EntityManager entityManager;
	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "test";
	private static final String SITE = "QVI";
	
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
	
	private CreateUpdateIndividualResponse callCreateIndividual(String applicationCode) throws BusinessErrorBlocBusinessException {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		PostalAddressRequest postalAddress = new PostalAddressRequest();
		PostalAddressContent postalAddressContent = new PostalAddressContent();
		postalAddressContent.setNumberAndStreet(generateString());
		postalAddressContent.setCity(generateString());
		postalAddressContent.setZipCode("31000");
		postalAddressContent.setCountryCode("FR");
		PostalAddressProperties postalAddressProperty = new PostalAddressProperties();
		postalAddressProperty.setMediumCode("D");
		postalAddressProperty.setMediumStatus("V");
		postalAddressProperty.setIndicAdrNorm(true);
		postalAddress.setPostalAddressContent(postalAddressContent);
		postalAddress.setPostalAddressProperties(postalAddressProperty);
		request.getPostalAddressRequest().add(postalAddress);
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setToken("WSSiC2");
		requestor.setApplicationCode(applicationCode);
		request.setRequestor(requestor);
		IndividualRequest indRequest = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date( ) );
		indInfo.setCivility("MR");
		indInfo.setStatus("V");
		indInfo.setLastNameSC("Etanol"); 
		indInfo.setFirstNameSC("POTATIUM");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);
		try {
		return createOrUpdateIndividualImplV8.createIndividual(request);
		}catch(BusinessErrorBlocBusinessException e) {
			logger.error(e.getFaultInfo().getBusinessError().getErrorCode());
			logger.error(e.getFaultInfo().getBusinessError().getErrorDetail());
			logger.error(e.getFaultInfo().getBusinessError().getErrorLabel());
			logger.error(e.getFaultInfo().getBusinessError().getOtherErrorCode());

			throw e;
		}
	}
	
	@Transactional
	@Test
	@Rollback(true)
	public void usageClientsNonIHM() throws BusinessErrorBlocBusinessException, InvalidParameterException {
		CreateUpdateIndividualResponse response = callCreateIndividual("ISI");
		String gin = response.getGin();
		Assert.assertTrue(response.isSuccess());
		Assert.assertNotNull(response.getGin());
		UsageClients usage = new UsageClients();
		usage.setSgin(gin);
		List<UsageClients> usageClients = usageClientsRepository.findAll(Example.of(usage));
		assertEquals(1, usageClients.size());
		assertEquals("ISI", usageClients.get(0).getScode());	
	}
}
