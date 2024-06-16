package com.airfrance.repind.dao.role.it;

import com.airfrance.repind.config.WebSicTestConfig;
import com.airfrance.repind.dao.role.RoleGPRepository;
import com.airfrance.repind.entity.role.RoleGP;
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
public class RoleGPDAOTest {
	
	/** logger */
	private static final Log log = LogFactory.getLog(RoleGPDAOTest.class);

	
	@Autowired 
	RoleGPRepository roleDAO;
	
	final String ginTest = "400424668522";
	final String mat = "40636835";
	final String CODE = "AFS";
	
	@Test
	public void roleGpDaoTest_getRoleGpByGin() {
		try {
			List<RoleGP> result = roleDAO.findByGin(ginTest);
			Assert.assertNotNull(result);
			Assert.assertEquals(mat, result.get(0).getMatricule());
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testGetRefOrigin1() {
		
		Object[] result = roleDAO.getRefOriginByCode(CODE);
		Object[] resultf = (Object[]) result[0];
		log.info("Result = " + resultf[0].toString() + " | " + resultf[1].toString());
		
		Assert.assertNotNull(resultf);
		Assert.assertTrue(resultf[1].toString().contains("AIR FRANCE"));
		
	}
}
