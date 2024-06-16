package com.airfrance.repind.dao.delegation;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.config.WebSicTestConfig;
import com.airfrance.repind.dao.AbstractDAOTest;
import com.airfrance.repind.entity.delegation.DelegationData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSicTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class DelegationDataDAOTest extends AbstractDAOTest {

	@Autowired
	private DelegationDataRepository delegationDataRepository;

	@Test
	public void testIsDelegationTypeValid() throws JrafDaoException, InvalidParameterException {
		DelegationData delegation = new DelegationData();
		delegation.setType("TM");
		int result = new Long(delegationDataRepository.count(Example.of(delegation))).intValue();
		if (result > 0) {
			Assert.assertTrue(delegationDataRepository.isDelegationTypeValid("TM"));
		} else {
			Assert.assertFalse(delegationDataRepository.isDelegationTypeValid("TM"));
		}
		delegation.setType("UM");
		result = (int) delegationDataRepository.count(Example.of(delegation));
		if (result > 0) {
			Assert.assertTrue(delegationDataRepository.isDelegationTypeValid("UM"));
		} else {
			Assert.assertFalse(delegationDataRepository.isDelegationTypeValid("UM"));
		}
		delegation.setType("UF");
		result = (int) delegationDataRepository.count(Example.of(delegation));
		if (result > 0) {
			Assert.assertTrue(delegationDataRepository.isDelegationTypeValid("UF"));
		} else {
			Assert.assertFalse(delegationDataRepository.isDelegationTypeValid("UF"));
		}
		delegation.setType("UA");
		result = (int) delegationDataRepository.count(Example.of(delegation));
		if (result > 0) {
			Assert.assertTrue(delegationDataRepository.isDelegationTypeValid("UA"));
		} else {
			Assert.assertFalse(delegationDataRepository.isDelegationTypeValid("UA"));
		}
	}
}
