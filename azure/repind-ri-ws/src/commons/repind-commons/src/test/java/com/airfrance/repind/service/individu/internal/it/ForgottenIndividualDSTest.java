package com.airfrance.repind.service.individu.internal.it;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.service.individu.internal.ForgottenIndividualDS;
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
public class ForgottenIndividualDSTest {
	
	@Autowired
	private ForgottenIndividualDS forgottenIndividualDS;
	
	@Test
	@Transactional
	public void testIsExisting(){
		try {
			Assert.assertFalse(forgottenIndividualDS.isExisting("000000000000"));
		} catch (JrafDomainException e) {
			Assert.fail();
		}
	}
}
