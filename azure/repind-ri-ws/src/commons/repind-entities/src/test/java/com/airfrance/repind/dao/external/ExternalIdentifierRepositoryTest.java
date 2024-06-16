package com.airfrance.repind.dao.external;

import com.airfrance.ref.exception.external.ExternalIdentifierLinkedToDifferentIndividualException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.type.external.ExternalIdentifierTypeEnum;
import com.airfrance.repind.config.WebSicTestConfig;
import com.airfrance.repind.entity.external.ExternalIdentifier;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSicTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ExternalIdentifierRepositoryTest {
	
	@Autowired
	private ExternalIdentifierRepository externalIdentifierRepository;

	
	// countExternalIdentifier
	@Test
	public void countExternalIdentifier_PNM_NoResultException_Test() throws JrafDaoException {
		Long nb = externalIdentifierRepository.countByGinAndType("999999999999", ExternalIdentifierTypeEnum.PNM_ID.getType());
		Assert.assertEquals("0", nb.toString());
	}

	@Test
	public void countExternalIdentifier_PNM_Unique_Test() throws JrafDaoException {
		Long nb = externalIdentifierRepository.countByGinAndType("400437108896", ExternalIdentifierTypeEnum.PNM_ID.getType());
		Assert.assertEquals("23", nb.toString());
	}

	@Test
	public void countExternalIdentifier_PNM_Multiple_Test() throws JrafDaoException {
		Long nb = externalIdentifierRepository.countByGinAndType("400290863603", ExternalIdentifierTypeEnum.PNM_ID.getType());
		Assert.assertEquals("8", nb.toString());
	}

	@Test
	public void countExternalIdentifier_PNM_Multiple_5_Test() throws JrafDaoException {
		Long nb = externalIdentifierRepository.countByGinAndType("400280493044", ExternalIdentifierTypeEnum.PNM_ID.getType());
		Assert.assertEquals("5", nb.toString());
	}


	// existExternalIdentifier
	@Test
	public void existExternalIdentifier_PNM_NoResultException_Test() throws JrafDaoException, ExternalIdentifierLinkedToDifferentIndividualException {
		ExternalIdentifier ei = externalIdentifierRepository.existExternalIdentifier("99999999-9999-9999-9999-999999999999", "PNM_ID");
		Assert.assertNull(ei);
	}

	@Test
	public void existExternalIdentifier_PNM_Unique_Test() throws JrafDaoException, ExternalIdentifierLinkedToDifferentIndividualException {
		ExternalIdentifier ei = externalIdentifierRepository.existExternalIdentifier("A8042B34-E0D1-4B12-A858-5EA5E80AA759", "PNM_ID");
		Assert.assertNotNull(ei);
		Assert.assertEquals("800020500144", ei.getGin());
	}

	@Test
	public void existExternalIdentifier_PNM_Multiple_Test() throws JrafDaoException {
		try {
			externalIdentifierRepository.existExternalIdentifier("7B3CB9B5-8C48-4F4C-9DAF-9ADD61F7F792", "PNM_ID");
			Assert.fail("On devrait avoir une exception ExternalIdentifierLinkedToDifferentIndividualException");
		} catch (ExternalIdentifierLinkedToDifferentIndividualException ex) {
			Assert.assertTrue(ex.getMessage().startsWith("External identifier linked to different individual:"));	
		}
	}

	@Test
	public void existExternalIdentifier_GIGYA_Unique_Test() throws JrafDaoException, ExternalIdentifierLinkedToDifferentIndividualException {
		ExternalIdentifier ei = externalIdentifierRepository.existExternalIdentifier("GIGYA_TI_GKMkKgADgxxqJlvsuIao", "GIGYA_ID");
		Assert.assertNotNull(ei);
		Assert.assertEquals("400002661817", ei.getGin());
	}
	
	@Test
	public void existExternalIdentifier_FACEBOOK_Unique_Test() throws JrafDaoException, ExternalIdentifierLinkedToDifferentIndividualException {
		ExternalIdentifier ei = externalIdentifierRepository.existExternalIdentifier("FB_10209938031221645", "FACEBOOK_ID");
		Assert.assertNotNull(ei);
		Assert.assertEquals("920000016492", ei.getGin());
	}

	@Test
	public void existExternalIdentifier_TWITTER_Unique_Test() throws JrafDaoException, ExternalIdentifierLinkedToDifferentIndividualException {
		ExternalIdentifier ei = externalIdentifierRepository.existExternalIdentifier("700916482788687873", "TWITTER_ID");
		Assert.assertNotNull(ei);
		Assert.assertEquals("920000014565", ei.getGin());
	}
	
	
	// existExternalIdentifierByGIGYA
	@Test
	public void existExternalIdentifierByGIGYA_NoResultException_Test() throws JrafDaoException, ExternalIdentifierLinkedToDifferentIndividualException {
		String ei = externalIdentifierRepository.existExternalIdentifierByGIGYA("GIGYA_TI_GKMkKgADgxxqJlvsuIao");
		Assert.assertEquals("", ei);
	}
	
	@Test
	public void existExternalIdentifierByGIGYA_Unique_Test() throws JrafDaoException, ExternalIdentifierLinkedToDifferentIndividualException {
		String ei = externalIdentifierRepository.existExternalIdentifierByGIGYA("NumeroIDGIGYA_AFKL_0484104661_iauXTHHyFa");
		Assert.assertEquals("920000085350", ei);
	}
	
	@Test	
	@Ignore	// Pas de jeu de test
	public void existExternalIdentifierByGIGYA_Multiple_Test() throws JrafDaoException {
		try {
			externalIdentifierRepository.existExternalIdentifierByGIGYA("NumeroIDGIGYA_AFKL_1234567_0422000409");
			Assert.fail("On devrait avoir une exception ExternalIdentifierLinkedToDifferentIndividualException");
		} catch (ExternalIdentifierLinkedToDifferentIndividualException ex) {
			Assert.assertTrue(ex.getMessage().startsWith("External identifier linked to different individual:"));	
		}
	}
	
	// findExternalIdentifier
	@Test
	public void findExternalIdentifier_NoResultException_Test() throws JrafDaoException {
		List<ExternalIdentifier> lei = externalIdentifierRepository.findExternalIdentifier("999999999999");
		Assert.assertNotNull(lei);
		Assert.assertEquals(0, lei.size());
	}

	
	@Test
	public void findExternalIdentifier_PNM_Multiple_Test() throws JrafDaoException {
		List<ExternalIdentifier> lei = externalIdentifierRepository.findExternalIdentifier("400329876180");
		Assert.assertNotNull(lei);
		Assert.assertEquals(2, lei.size());
	}
	
	@Test
	public void findExternalIdentifier_PNM_Multiple_5_Test() throws JrafDaoException {
		List<ExternalIdentifier> lei = externalIdentifierRepository.findExternalIdentifier("400379558805");
		Assert.assertNotNull(lei);
		Assert.assertEquals(5, lei.size());
	}
}
