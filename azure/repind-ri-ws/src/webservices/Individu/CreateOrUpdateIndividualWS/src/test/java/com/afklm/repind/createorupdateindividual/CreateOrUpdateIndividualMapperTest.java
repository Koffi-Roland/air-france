package com.afklm.repind.createorupdateindividual;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.utils.CreateOrUpdateIndividualMapperV7;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v7.request.EmailRequest;
import com.afklm.soa.stubs.w000442.v7.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v7.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.Civilian;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.Email;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.IndividualInformationsV3;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.IndividualProfilV3;
import com.airfrance.repind.dto.ws.EmailDTO;
import com.airfrance.repind.dto.ws.IndividualInformationsDTO;
import com.airfrance.repind.dto.ws.IndividualProfilDTO;
import com.airfrance.repind.dto.ws.IndividualRequestDTO;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateIndividualMapperTest {
	
//	@Autowired
	CreateOrUpdateIndividualMapperV7 createOrUpdateIndividualMapperV7;
	
	@Test
	@Ignore
	public void test_wsV7ToCommon1() {
		
		CreateUpdateIndividualRequest source = initBasicRequestSource();
		
		CreateUpdateIndividualRequestDTO result = createOrUpdateIndividualMapperV7.wsRequestV7ToCommon(source);
		
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getProcess());
		Assert.assertTrue("I".equalsIgnoreCase(result.getProcess()));
		
	}
	
	@Test
	@Ignore
	public void test_wsV7ToCommon2_requestor() {
		
		CreateUpdateIndividualRequest source = initBasicRequestSource();
		RequestorV2 req = new RequestorV2();
		req.setApplicationCode("BDC");
		req.setChannel("B2C");
		req.setContext("W");
		req.setIpAddress("10.60.100.100");
		req.setLoggedGin("400");
		req.setManagingCompany("AF");
		req.setMatricule("M100000");
		req.setOfficeId("RI01");
		req.setReconciliationDataCIN("100");
		req.setSignature("TEST");
		req.setSite("QVI");
		req.setToken("SIC");
		
		source.setRequestor(req);
		
		CreateUpdateIndividualRequestDTO result = createOrUpdateIndividualMapperV7.wsRequestV7ToCommon(source);
		
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getRequestorDTO());
		Assert.assertTrue("BDC".equals(result.getRequestorDTO().getApplicationCode()));
		Assert.assertTrue("B2C".equals(result.getRequestorDTO().getChannel()));
		Assert.assertTrue("W".equals(result.getRequestorDTO().getContext()));
		Assert.assertTrue("10.60.100.100".equals(result.getRequestorDTO().getIpAddress()));
		Assert.assertTrue("400".equals(result.getRequestorDTO().getLoggedGin()));
		Assert.assertTrue("AF".equals(result.getRequestorDTO().getManagingCompany()));
		Assert.assertTrue("M100000".equals(result.getRequestorDTO().getMatricule()));
		Assert.assertTrue("RI01".equals(result.getRequestorDTO().getOfficeId()));
		Assert.assertTrue("100".equals(result.getRequestorDTO().getReconciliationDataCIN()));
		Assert.assertTrue("TEST".equals(result.getRequestorDTO().getSignature()));
		Assert.assertTrue("QVI".equals(result.getRequestorDTO().getSite()));
		Assert.assertTrue("SIC".equals(result.getRequestorDTO().getToken()));
	}

	@Test
	@Ignore
	public void test_wsV7ToCommon3_email() {
		
		CreateUpdateIndividualRequest source = initBasicRequestSource();
		EmailRequest emailReq = new EmailRequest();
		Email email = new Email();
		email.setVersion("1");
		email.setEmailOptin("N");
		email.setMediumCode("D");
		email.setMediumStatus("V");
		email.setEmail("test@ri.af");
		
		emailReq.setEmail(email);
		source.getEmailRequest().add(emailReq);
		
		CreateUpdateIndividualRequestDTO result = createOrUpdateIndividualMapperV7.wsRequestV7ToCommon(source);
		
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getEmailRequestDTO());
		
		EmailDTO resultEmail = result.getEmailRequestDTO().get(0).getEmailDTO();
		Assert.assertTrue("test@ri.af".equals(resultEmail.getEmail()));
		Assert.assertTrue("1".equals(resultEmail.getVersion()));
		Assert.assertTrue("N".equals(resultEmail.getEmailOptin()));
		Assert.assertTrue("D".equals(resultEmail.getMediumCode()));
		Assert.assertTrue("V".equals(resultEmail.getMediumStatus()));
	}
	
	@Test
	@Ignore
	public void test_wsV7ToCommon4_individual() {
		Date birthdate = new Date();
		
		CreateUpdateIndividualRequest source = initBasicRequestSource();
		IndividualRequest indReq = new IndividualRequest();
		Civilian civilian = new Civilian();
		civilian.setTitleCode("DR");
		indReq.setCivilian(civilian);
		
		IndividualInformationsV3 info = new IndividualInformationsV3();
		info.setBirthDate(birthdate);
		info.setCivility("MR");
		info.setFirstNamePseudonym("fnp");
		info.setFirstNameSC("fnsc");
		info.setFlagNoFusion(true);
		info.setFlagThirdTrap(false);
		info.setGender("M");
		info.setIdentifier("100");
		info.setLanguageCode("FR");
		info.setLastNamePseudonym("lnp");
		info.setLastNameSC("lnsc");
		info.setMiddleNameSC("mnsc");
		info.setNationality("ES");
		info.setPersonalIdentifier("200");
		info.setPopulationType("I");
		info.setSecondFirstName("sfn");
		info.setSecondNationality("MG");
		info.setStatus("V");
		info.setVersion("1");
		indReq.setIndividualInformations(info);
		
		IndividualProfilV3 profil = new IndividualProfilV3();
		profil.setCarrierCode("AF");
		profil.setChildrenNumber("4");
		profil.setCivilianCode("PR");
		profil.setCustomerSegment("Y");
		profil.setEmailOptin("A");
		profil.setLanguageCode("FR");
		profil.setProAreaCode("S");
		profil.setProAreaWording("Sales");
		profil.setProFunctionCode("CEO");
		profil.setProFunctionWording("Manager");
		profil.setStudentCode("N");
		indReq.setIndividualProfil(profil);
		
		source.setIndividualRequest(indReq);
		
		CreateUpdateIndividualRequestDTO result = createOrUpdateIndividualMapperV7.wsRequestV7ToCommon(source);
		
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getIndividualRequestDTO());

		IndividualRequestDTO resultInd = result.getIndividualRequestDTO();
		Assert.assertTrue("DR".equals(resultInd.getTitleCode()));
		
		IndividualInformationsDTO resultInfo = result.getIndividualRequestDTO().getIndividualInformationsDTO();
		Assert.assertTrue(birthdate.equals(resultInfo.getBirthDate()));
		Assert.assertTrue("MR".equals(resultInfo.getCivility()));
		Assert.assertTrue("fnp".equals(resultInfo.getFirstNamePseudonym()));
		Assert.assertTrue("fnsc".equals(resultInfo.getFirstNameSC()));
		
		IndividualProfilDTO resultProfil = result.getIndividualRequestDTO().getIndividualProfilDTO();
		Assert.assertTrue("AF".equals(resultProfil.getCarrierCode()));
		Assert.assertTrue("PR".equals(resultProfil.getCivilianCode()));
	}
	
	private CreateUpdateIndividualRequest initBasicRequestSource() {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		request.setNewsletterMediaSending("N");
		request.setStatus("V");
		request.setUpdateCommunicationPrefMode("N");
		request.setUpdatePrefilledNumbersMode("N");
		request.setProcess("I");
		
		return request;
	}
	
}
