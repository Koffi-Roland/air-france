package com.airfrance.repind.dao.individu;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.config.WebSicTestConfig;
import com.airfrance.repind.dao.external.ExternalIdentifierRepository;
import com.airfrance.repind.entity.external.ExternalIdentifier;
import com.airfrance.repind.entity.individu.Individu;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSicTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ExternalIdentifierRepositoryTest {

	@Autowired
	private ExternalIdentifierRepository externalIdentifierRepository;
	
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	@Test
	@Transactional
	@Rollback(true)
	public void findExternalIdentifierALLTest() throws JrafDaoException, InvalidParameterException {
		// Init datas
		Individu individu = entityManager.find(Individu.class, "400000192840");

		ExternalIdentifier gigya = new ExternalIdentifier();
		gigya.setGin("400000192840");
		gigya.setType("GIGYA_ID");
		gigya.setIdentifier("AFKL_GIGYA_TEMPORARY");
		gigya.setLastSeenDate(new Date());
		gigya.setCreationDate(new Date());
		gigya.setCreationSignature("TMP");
		gigya.setCreationSite("QVI");
		gigya.setModificationDate(new Date());
		gigya.setModificationSignature("TMP");
		gigya.setModificationSite("QVI");
		gigya.setIndividu(individu);
		externalIdentifierRepository.saveAndFlush(gigya);

		// Call method to test
		List<ExternalIdentifier> eis = externalIdentifierRepository.findExternalIdentifierALL("400000192840");

		// Test result
		Assert.assertNotNull(eis);
		Assert.assertEquals(3, eis.size());
	}
}
