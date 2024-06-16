package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.IdentifierOptionTypeEnum;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.reference.RefTypExtIDDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class RefTypExtIDDSTest {

	@Autowired
	private RefTypExtIDDS refTypExtIdDS;
	
	
	@Test
	public void testGetForExistingExtID_GIGYA() throws JrafDomainException {
		RefTypExtIDDTO result = null;
		
		result = refTypExtIdDS.get("GIGYA_ID");
		
		Assert.assertNotNull(result);
		Assert.assertEquals( "GIGYA_ID", result.getExtID());
		Assert.assertEquals("GIGYA identifier", result.getLibelle());
	}

	@Test
	public void testGetForExistingExtID_FACEBOOK() throws JrafDomainException {
		RefTypExtIDDTO result = null;
		
		result = refTypExtIdDS.get("FACEBOOK_ID");
		
		Assert.assertNotNull(result);
		Assert.assertEquals("FACEBOOK_ID", result.getExtID());
		Assert.assertEquals("Facebook identifier", result.getLibelle());
	}

	@Test
	public void testGetForExistingExtID_TWITTER() throws JrafDomainException {
		RefTypExtIDDTO result = null;
		
		result = refTypExtIdDS.get("TWITTER_ID");
		
		Assert.assertNotNull(result);
		Assert.assertEquals( "TWITTER_ID", result.getExtID());
		Assert.assertEquals("Twitter identifier", result.getLibelle());
	}

	@Test
	public void testGetForExistingExtID_PNM() throws JrafDomainException {
		RefTypExtIDDTO result = null;
		
		result = refTypExtIdDS.get("PNM_ID");
		
		Assert.assertNotNull(result);
		Assert.assertEquals("PNM_ID", result.getExtID());
		Assert.assertEquals("PNM identifier", result.getLibelle());
	}

	
	@Test
	public void testGetForNonExistingExtID() throws JrafDomainException {
		RefTypExtIDDTO result = null;

		result = refTypExtIdDS.get("RANDOM_EXTID");

		Assert.assertNull(result);
	}

	@Test
	public void testGetAllExtID() throws JrafDomainException {
		List<RefTypExtIDDTO> result = null;
		Set<String> sampleExtID = new HashSet<String>(Arrays.asList("GIGYA_ID", "FACEBOOK_ID", "TWITTER_ID", "PNM_ID",
				"INSTAGRAM_ID", "KAKAO_ID", "HYVES_ID", "KLOOT_ID", "WECHAT_ID", "WHATSAPP_ID", "LINKEDIN_ID", "SINAWEIBO_ID",
				"LA", "TA", "CA"));
		
		result = refTypExtIdDS.findAll();
		
		// On devient plus generique en mesurant seulement la taille
		Assert.assertTrue(result.size() >= 12);
		
		// On crée une table pour ce que l on trouve en base
		Set<String> sampleExtIDInDB = new HashSet<String>();
		for (RefTypExtIDDTO rteid : result) {
			sampleExtIDInDB.add(rteid.getExtID());
		}
		
		// On test que les exemples sont bien en base 
		for (String sei : sampleExtID) {
			Assert.assertTrue(sampleExtIDInDB.contains(sei));
		}
	}
	
	@Test
	public void testGetAllExtIDByOption_GIGYA() throws JrafDomainException {
		String result = refTypExtIdDS.getExtIdByOption(IdentifierOptionTypeEnum.GIGYAID.toString());
		
		Assert.assertEquals("GIGYA_ID", result);
	}
	
	@Test
	public void testGetAllExtIDByOption_NULL() throws JrafDomainException {
		String result = refTypExtIdDS.getExtIdByOption(null);
		
		Assert.assertNull(result);
	}
	
	@Test
	public void testGetAllExtIDByOption_EMPTY() throws JrafDomainException {
		String result = refTypExtIdDS.getExtIdByOption("");
		
		Assert.assertNull(result);
	}
	
	@Test
	public void testIsValid_EMPTY() throws JrafDomainException {
		boolean valid = refTypExtIdDS.isValid("");
		Assert.assertFalse(valid);

		valid = refTypExtIdDS.isValid(null);
		Assert.assertFalse(valid);
	}

	@Test
	public void testIsValid_ExistingID() throws JrafDomainException {
		boolean valid = refTypExtIdDS.isValid("FACEBOOK_ID");
		Assert.assertTrue(valid);
	}

	@Test
	public void testIsValid_InvalidID() throws JrafDomainException {
		boolean valid = refTypExtIdDS.isValid("FACEBO_ID");
		Assert.assertFalse(valid);
	}

	
}
