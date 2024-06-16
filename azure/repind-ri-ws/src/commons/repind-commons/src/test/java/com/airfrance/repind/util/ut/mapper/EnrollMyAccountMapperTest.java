package com.airfrance.repind.util.ut.mapper;

import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerRequestDTO;
import com.airfrance.repind.dto.ws.IndividualInformationsDTO;
import com.airfrance.repind.dto.ws.IndividualRequestDTO;
import com.airfrance.repind.dto.ws.RequestorDTO;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.util.mapper.EnrollMyAccountMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class EnrollMyAccountMapperTest {
	
	final String GIN = "4000123456789";
	
	@Autowired
	EnrollMyAccountMapper enrollMyAccountMapper;
	
	@Test
	public void wsRequestToCommonTest() {
		
		MyAccountCustomerRequestDTO ws = new MyAccountCustomerRequestDTO();
		SignatureDTO sign = new SignatureDTO();
		
		sign.setSite("QVI");
		sign.setSignature("TEST");
		sign.setIpAddress("10.60.100.100");
		sign.setApplicationCode("RPD");
		
		ws.setSignature(sign);
		ws.setCivility("MRS");
		ws.setGin(GIN);
		ws.setFirstName("firstname");
		ws.setLastName("lastname");
		ws.setLanguage("FR");
		ws.setChannel("B2C");
		
		CreateUpdateIndividualRequestDTO resp = enrollMyAccountMapper.wsRequestToIndividualCommon(ws);
		
		Assert.assertNotNull(resp);
		
		IndividualRequestDTO ind = resp.getIndividualRequestDTO();
		Assert.assertNotNull(ind);
		
		IndividualInformationsDTO info = resp.getIndividualRequestDTO().getIndividualInformationsDTO();
		Assert.assertNotNull(info);
		Assert.assertTrue("MRS".equals(info.getCivility()));
		Assert.assertTrue(GIN.equals(info.getIdentifier()));
		Assert.assertTrue("firstname".equals(info.getFirstNameSC()));
		Assert.assertTrue("lastname".equals(info.getLastNameSC()));
		Assert.assertTrue("FR".equals(info.getLanguageCode()));
		
		RequestorDTO req = resp.getRequestorDTO();
		Assert.assertNotNull(req);
		Assert.assertTrue("B2C".equals(req.getChannel()));
		Assert.assertTrue("QVI".equals(req.getSite()));
		Assert.assertTrue("TEST".equals(req.getSignature()));
		Assert.assertTrue("10.60.100.100".equals(req.getIpAddress()));
		Assert.assertTrue("RPD".equals(req.getApplicationCode()));
	}
	
}
