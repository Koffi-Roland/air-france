package com.airfrance.repind.dao.reference;

import com.airfrance.repind.config.WebSicTestConfig;
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
@SpringBootTest(classes = WebSicTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class RefComPrefDgtDaoTest {
	
    @Autowired
    private RefComPrefDgtRepository refComPrefDgtRepository;
    
	@Test
	public void testCountByDomainGroupAndType() {
		Assert.assertEquals(0, refComPrefDgtRepository.countByDomainGroupAndType("S", "N", "EN"));
		Assert.assertEquals(1, refComPrefDgtRepository.countByDomainGroupAndType("S", "N", "KL"));
	}
}
