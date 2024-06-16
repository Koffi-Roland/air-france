package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.EmailRequest;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.Email;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.IndividualInformationsV3;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.adresse.EmailRepository;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.adresse.TelecomsRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.role.RoleContrats;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV8ForGDPRTest {
	
	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV8ForGDPRTest.class);
	private static final String eligibleGIN = "400257097585";
	private static final String eligibleGINGP = "400005188165";
	private static final String eligibleGINPartner = "400517761441";
	
	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v8Bean")
	private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImplV8;
	
	@Autowired
	private IndividuRepository individuRepository;
	
	@Autowired
	private RoleContratsRepository roleContratsRepository;
	
	@Autowired 
	private PostalAddressRepository postalAddressRepository;
	
	@Autowired
	private TelecomsRepository telecomsRepository;
	
	@Autowired
	private EmailRepository emailRepository;
	
	private RequestorV2 initRequestorNoContext() {
		RequestorV2 req = new RequestorV2();
		req.setApplicationCode("ISI");
		req.setChannel("B2C");
		req.setSignature("IT442v8");
		req.setSite("QVI");
		return req;
	}
	
	private RequestorV2 initRequestor() {
		RequestorV2 req = new RequestorV2();
		req.setContext("FORGET_CONFIRM");
		req.setApplicationCode("ISI");
		req.setChannel("B2C");
		req.setSignature("IT442v8");
		req.setSite("QVI");
		return req;
	}
	
	private IndividualRequest initIndivu() {
		IndividualRequest req = new IndividualRequest();
		IndividualInformationsV3 infos = new IndividualInformationsV3();
		infos.setIdentifier("400424668522");
		req.setIndividualInformations(infos);
		return req;
	}
	
	private IndividualRequest initEligibleIndivu() {
		IndividualRequest req = new IndividualRequest();
		IndividualInformationsV3 infos = new IndividualInformationsV3();
		infos.setIdentifier(eligibleGIN);
		req.setIndividualInformations(infos);
		return req;
	}
	
	private IndividualRequest initEligibleIndivuGP() {
		IndividualRequest req = new IndividualRequest();
		IndividualInformationsV3 infos = new IndividualInformationsV3();
		infos.setIdentifier(eligibleGINGP);
		req.setIndividualInformations(infos);
		return req;
	}
	
	private IndividualRequest initEligibleIndivuPartner() {
		IndividualRequest req = new IndividualRequest();
		IndividualInformationsV3 infos = new IndividualInformationsV3();
		infos.setIdentifier(eligibleGINPartner);
		req.setIndividualInformations(infos);
		return req;
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_updateForgottenIndividual() throws JrafDaoException {
		logger.info("START TEST update forgotten individual ...");
		
		// Prepare request
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(initRequestorNoContext());
		request.setIndividualRequest(initIndivu());
		
		EmailRequest emailReq = new EmailRequest();
		Email email = new Email();
		email.setEmail("random19998522@rdm.tst");
		email.setMediumCode("D");
		email.setMediumStatus("V");
		emailReq.setEmail(email);
		request.getEmailRequest().add(emailReq);
		
		// Init database
		Individu indFromDB = individuRepository.findBySgin("400424668522");
		if (indFromDB != null) {
			indFromDB.setStatutIndividu("F");
			individuRepository.saveAndFlush(indFromDB);
		}
		
		// Execute test
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
			// Individual must not be found as he's in status 'F'
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException ex) {
			Assert.assertTrue(ex.getFaultInfo().getBusinessError().getErrorDetail().contains("not exist"));
		}
		
		
		logger.info("END TEST update forgotten individual ...");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_forgetIndividualSuccess() throws JrafDomainException {
		logger.info("START TEST testCreateOrUpdateIndividual_forgetIndividualSuccess ...");
		
		// Prepare request
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(initRequestor());
		request.setIndividualRequest(initEligibleIndivu());
		
		// Init DB
		Individu indFromDB = individuRepository.findBySgin(eligibleGIN);
		if (indFromDB != null && indFromDB.getRolecontrats() != null) {
			for (RoleContrats rc : indFromDB.getRolecontrats()) {
				if (!"MA".equalsIgnoreCase(rc.getTypeContrat())) {
					rc.setEtat("A");
					roleContratsRepository.saveAndFlush(rc);
				}
			}
		}
		
		// Execute test
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.assertTrue(response.isSuccess());
		} catch (BusinessErrorBlocBusinessException ex) {
			Assert.fail();
		}
		
		// Control result in DB
		// ADR_POST
		List<PostalAddress> adrPost = postalAddressRepository.findPostalAddress(eligibleGIN); // return only valid and invalid addresses
		if (adrPost != null) {
			logger.info("Number of valid or invalid postal addresses found: " + adrPost.size());
		}
		Assert.assertTrue(adrPost == null || adrPost.isEmpty());
		
		// TELECOMS
		List<Telecoms> telList = telecomsRepository.findTelecomsByGIN(eligibleGIN); // return invalid, valid and historical telecoms
		if (telList != null) {
			logger.info("Number of valid or invalid telcoms found: " + telList.size());
		}
		Assert.assertTrue(telList == null || telList.isEmpty());
		
		//EMAILS
		List<com.airfrance.repind.entity.adresse.Email> emailList = emailRepository.findEmail(eligibleGIN); // return valid emails
		if (emailList != null) {
			logger.info("Number of valid emails found: " + emailList.size());
		}
		Assert.assertTrue(emailList == null || emailList.isEmpty());
		
		logger.info("END TEST testCreateOrUpdateIndividual_forgetIndividualSuccess ...");
	}
	
	//REPIND-1642: No more checks on Contractss
	@Ignore
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_forgetIndividualFail() throws JrafDomainException {
		logger.info("START TEST testCreateOrUpdateIndividual_forgetIndividualFail ...");
		
		// Prepare request
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(initRequestor());
		request.setIndividualRequest(initEligibleIndivu());
		
		// Init DB
		Individu indFromDB = individuRepository.findBySgin(eligibleGIN);
		if (indFromDB != null && indFromDB.getRolecontrats() != null) {
			for (RoleContrats rc : indFromDB.getRolecontrats()) {
				if (!"MA".equalsIgnoreCase(rc.getTypeContrat())) {
					rc.setEtat("C");
					roleContratsRepository.saveAndFlush(rc);
				}
			}
		}
		
		// Execute test
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException ex) {
			Assert.assertTrue(ex.getFaultInfo().getBusinessError().getErrorDetail().contains("contract found"));
		}
		
		logger.info("END TEST testCreateOrUpdateIndividual_forgetIndividualFail ...");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_forgetIndividualFailGP() throws JrafDomainException {
		logger.info("START TEST testCreateOrUpdateIndividual_forgetIndividualFail GP ...");
		
		// Prepare request
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(initRequestor());
		request.setIndividualRequest(initEligibleIndivuGP());
	
		// Execute test
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException ex) {
			Assert.assertTrue(ex.getFaultInfo().getBusinessError().getErrorDetail().contains("contract found"));
		}
		
		logger.info("END TEST testCreateOrUpdateIndividual_forgetIndividualFail ...");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_forgetIndividualFailPartner() throws JrafDomainException {
		logger.info("START TEST testCreateOrUpdateIndividual_forgetIndividualFail GP partner ...");
		
		// Prepare request
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(initRequestor());
		request.setIndividualRequest(initEligibleIndivuPartner());
	
		// Execute test
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException ex) {
			Assert.assertTrue(ex.getFaultInfo().getBusinessError().getErrorDetail().contains("contract found"));
		}
		
		logger.info("END TEST testCreateOrUpdateIndividual_forgetIndividualFail ...");
	}
}
