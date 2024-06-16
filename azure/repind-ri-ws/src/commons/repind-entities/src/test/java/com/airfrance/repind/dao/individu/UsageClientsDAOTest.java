package com.airfrance.repind.dao.individu;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repind.config.WebSicTestConfig;
import com.airfrance.repind.entity.individu.UsageClients;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
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
public class UsageClientsDAOTest {

	@Autowired
	public UsageClientsRepository usageClientsRepository;

	//@Autowired
	//public UsageClientsDS usageClientsDS;
	
	@PersistenceContext(unitName="entityManagerFactoryRepind")
	private EntityManager entityManager;
	
	@Before
	public void before() {
		try {
			entityManager.createNativeQuery(
					"INSERT INTO SIC2.INDIVIDUS_ALL (SGIN, IVERSION, SCIVILITE, SSEXE, SNON_FUSIONNABLE, DDATE_CREATION, SSIGNATURE_CREATION, DDATE_MODIFICATION, SSIGNATURE_MODIFICATION, SSITE_CREATION, SSITE_MODIFICATION, STYPE, SSTATUT_INDIVIDU) VALUES "
							+ "('000000000018', 0, 'MR', 'M', 'O',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata',TO_DATE('2017/01/01', 'yyyy/mm/dd'), 'tata', 'totot', 'tata', 'I', 'V')")
					.executeUpdate();
		}catch(Exception e) {}
	}

	@Test
	@Transactional
	@Rollback(true)
	public void createOrUpdateTest() throws InvalidParameterException {
		String gin = "000000000018";
		String applicationCode = "ISI";
		
		UsageClients usageClients = new UsageClients();
		usageClients.setSgin(gin);
		usageClients.setScode(applicationCode);
		usageClients.setDate_modification(new Date());
		usageClientsRepository.saveAndFlush(usageClients);
		
		usageClients = new UsageClients();
		usageClients.setSgin(gin);
		usageClients.setScode("BDC");
		usageClients.setDate_modification(new Date());
		usageClientsRepository.saveAndFlush(usageClients);
		
		
		//usageClientsDS.add(gin, applicationCode, new Date());
		
		usageClients = new UsageClients();
		usageClients.setSgin(gin);

		List<UsageClients> result = usageClientsRepository.findAll(Example.of(usageClients));
		Assert.assertEquals(2, result.size());
		boolean isFoundBDC = false;
		boolean isFoundISI = false;
		for(UsageClients usage : result) {
			Assert.assertEquals(gin, usage.getSgin());
			switch(usage.getScode()) {
			case "BDC":
				Assert.assertEquals(false, isFoundBDC);
				isFoundBDC = true;
				break;
			case "ISI":
				Assert.assertEquals(false, isFoundISI);
				isFoundISI = true;
				break;
			default:
				Assert.fail();
				break;
			}
		}
	}
}
