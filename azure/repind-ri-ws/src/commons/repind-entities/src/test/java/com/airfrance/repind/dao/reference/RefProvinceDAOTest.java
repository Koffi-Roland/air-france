package com.airfrance.repind.dao.reference;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.config.WebSicTestConfig;
import org.junit.Assert;
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
public class RefProvinceDAOTest {
	
	@Autowired
	RefProvinceRepository refProvinceRepository;
	
	@Test
	public void test_getProvinceCode() throws JrafDaoException {
		
		List<String> result = refProvinceRepository.findProvinceCodeByLabel("MIDDLESEX", "GB");
		Assert.assertEquals("MX", result.get(0));
	}
}
