package com.afklm.repind.v7.createorupdateindividual;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.createorupdateindividual.helper.CreateOrUpdateAnIndividualChangeEmailAccountDataHelper;
import com.afklm.repind.v7.createorupdateindividualws.CreateOrUpdateIndividualImplV7;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000442.v7.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v7.request.EmailRequest;
import com.afklm.soa.stubs.w000442.v7.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v7.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.Email;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.IndividualInformationsV3;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.dao.individu.AccountDataRepository;
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

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualChangeEmailAccountData {

	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v7Bean")
	private CreateOrUpdateIndividualImplV7 createOrUpdateIndividualImplV7;

	@Autowired
	AccountDataRepository accountDataRepository;

	@Autowired
	CreateOrUpdateAnIndividualChangeEmailAccountDataHelper helper;

	// BUG change of email in AccountData where email from contact change (with specific rules)
	/**
	 * This test is about a bug IM02496944 and REPIND-970 where B2C is changing
	 * a contact email that also must change account connection email Expected :
	 * The email in ACCOUNT_DATA table must be equals to the new define in EMAIL
	 * table
	 * 
	 * @throws BusinessErrorBlocBusinessException
	 * @throws SystemException
	 * @throws JrafDaoException
	 */
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void testCreateOrUpdateIndividual_ChangeAccountDataEmail()
			throws BusinessErrorBlocBusinessException, SystemException, JrafDaoException {

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();

		request.setStatus("V");

		// Preparing the request		
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("DS");
		requestor.setSite("TLS");
		requestor.setSignature("DALLAS");

		request.setRequestor(requestor);

		IndividualRequest indRequest = new IndividualRequest();
		// indRequest.setCivilian("MR");

		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setIdentifier("400509651532");
		indInfo.setCivility("Miss");
		indRequest.setIndividualInformations(indInfo);

		request.setIndividualRequest(indRequest);

		// Email
		EmailRequest emailRequest = new EmailRequest();
		Email email = new Email();
		email.setMediumCode("D");
		email.setMediumStatus("V");
		email.setEmail("testChangeAccountData@airfrance.fr");
		emailRequest.setEmail(email);
		request.getEmailRequest().add(emailRequest);

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);

		Assert.assertNotNull(response);
		Assert.assertNotNull(response.isSuccess());
		Assert.assertTrue(response.isSuccess());

		Assert.assertNotNull(accountDataRepository.findBySgin("400509651532").getEmailIdentifier(),
				"testChangeAccountData@airfrance.fr");

		helper.after();

	}
	
	
	
	
}
