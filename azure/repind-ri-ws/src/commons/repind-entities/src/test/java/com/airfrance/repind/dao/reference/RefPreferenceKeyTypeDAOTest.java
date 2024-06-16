package com.airfrance.repind.dao.reference;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.config.WebSicTestConfig;
import com.airfrance.repind.entity.reference.RefPreferenceKeyType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class RefPreferenceKeyTypeDAOTest {
	 /** logger */
    private static final Log log = LogFactory.getLog(RefPreferenceKeyTypeDAOTest.class);
	
	@Autowired
	private RefPreferenceKeyTypeRepository refPreferenceKeyTypeRepository;
	
	@Test
	public void testGetKeyListByType() throws JrafDaoException {
		String type = "TDC";
		List<RefPreferenceKeyType> result = refPreferenceKeyTypeRepository.findByType(type.toUpperCase());
		log.info(result.size());
		
		Assert.assertNotNull(result);
		for (RefPreferenceKeyType data: result) {
			log.info(data.toStringImpl());
		}
	}
}
