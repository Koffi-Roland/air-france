package com.airfrance.repind.service.adresse.internal;

import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.ws.PostalAddressContentDTO;
import com.airfrance.repind.dto.ws.PostalAddressPropertiesDTO;
import com.airfrance.repind.dto.ws.PostalAddressRequestDTO;
import com.airfrance.repind.dto.ws.UsageAddressDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class PostalAddressDSTest {
	
	private final static Log log = LogFactory.getLog(PostalAddressDSTest.class);


	@Test(expected = Test.None.class /* no exception expected */)
	public void testPostalAddressDS_checkISIUsageAlreadyExistOK() {
		log.info("**** START testPostalAddressDS_checkISIUsageAlreadyExistOK *****");
		
		PostalAddressRequestDTO adrToCheck = initAddress();
		PostalAddressRequestDTO adrToCompare = initAddress();
		
		adrToCheck.getPostalAddressPropertiesDTO().setMediumCode("D");
		adrToCheck.getUsageAddressDTO().setApplicationCode("ISI");
		adrToCheck.getUsageAddressDTO().setUsageNumber("01");
		adrToCheck.getUsageAddressDTO().setAddressRoleCode("M");
		
		adrToCompare.getPostalAddressPropertiesDTO().setMediumCode("P");
		adrToCompare.getUsageAddressDTO().setApplicationCode("ISI");
		adrToCompare.getUsageAddressDTO().setUsageNumber("01");
		adrToCompare.getUsageAddressDTO().setAddressRoleCode("C");
		
//		try {
//			PostalAddressDS.checkUsageAlreadyExist(adrToCheck, adrToCompare);
//		} catch (JrafDomainException e) {
//			Assert.fail();
//		}
		
		log.info("**** END testPostalAddressDS_checkISIUsageAlreadyExistOK *****");
	}

	@Test(expected = Test.None.class /* no exception expected */)
	public void testPostalAddressDS_checkISIUsageAlreadyExistKO() {
		log.info("**** START testPostalAddressDS_checkISIUsageAlreadyExistKO *****");
		
		PostalAddressRequestDTO adrToCheck = initAddress();
		PostalAddressRequestDTO adrToCompare = initAddress();
		
		adrToCheck.getPostalAddressPropertiesDTO().setMediumCode("D");
		adrToCheck.getUsageAddressDTO().setApplicationCode("ISI");
		adrToCheck.getUsageAddressDTO().setUsageNumber("01");
		adrToCheck.getUsageAddressDTO().setAddressRoleCode("M");
		
		adrToCompare.getPostalAddressPropertiesDTO().setMediumCode("P");
		adrToCompare.getUsageAddressDTO().setApplicationCode("ISI");
		adrToCompare.getUsageAddressDTO().setUsageNumber("01");
		adrToCompare.getUsageAddressDTO().setAddressRoleCode("M");
		
//		try {
//			PostalAddressDS.checkUsageAlreadyExist(adrToCheck, adrToCompare);
//			Assert.fail();
//		} catch (JrafDomainException e) {
//			Assert.assertTrue(e.getMessage().contains("exists"));
//		}
		
		log.info("**** END testPostalAddressDS_checkISIUsageAlreadyExistKO *****");
	}

	private PostalAddressRequestDTO initAddress() {
		PostalAddressRequestDTO response = new PostalAddressRequestDTO();
		PostalAddressContentDTO postalAddressContent = new PostalAddressContentDTO();
	    PostalAddressPropertiesDTO postalAddressProperties = new PostalAddressPropertiesDTO();
	    UsageAddressDTO usageAddress = new UsageAddressDTO();
		
	    response.setPostalAddressContentDTO(postalAddressContent);
	    response.setPostalAddressPropertiesDTO(postalAddressProperties);
	    response.setUsageAddressDTO(usageAddress);
	    
	    postalAddressContent.setCountryCode("ZZ");
	    postalAddressProperties.setMediumStatus("V");
	    
		return response;
	}
}
