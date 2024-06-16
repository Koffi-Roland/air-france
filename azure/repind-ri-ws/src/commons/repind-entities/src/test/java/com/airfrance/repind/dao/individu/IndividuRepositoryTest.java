package com.airfrance.repind.dao.individu;


import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebSicTestConfig;
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

import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSicTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class IndividuRepositoryTest {

	@Autowired
	private IndividuRepository individuRepository;
	
	private static final String GIN_TEST = "400519507860";
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_findGPByInfosAndMatricule() throws JrafDomainException  {
		// Init datas
		String lastname = "INTEGTESTGP";
		String firstname = "TESTGP";
		String birthdate = "01/01/2016";
		String matricule = "00000001";
		
		List<Individu> individu = individuRepository.findByNomAndPrenomAndDateNaissanceAndMatricule(lastname, firstname, birthdate, matricule);
		
		Assert.assertNotNull(individu);
		Assert.assertTrue(individu.size() == 1);
		Assert.assertEquals(GIN_TEST, individu.get(0).getSgin());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_findGPByNameOnly() {
		// Init datas
		String lastname = "INTEGTESTGP";
		String firstname = "TESTGP";
		
		List<Individu> individu = individuRepository.findByNomAndPrenom(lastname, firstname);
		
		Assert.assertNotNull(individu);
		Assert.assertTrue(individu.size() == 1);
		Assert.assertEquals(GIN_TEST, individu.get(0).getSgin());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test_findGPByInfos() throws JrafDomainException  {
		// Init datas
		String lastname = "INTEGTESTGP";
		String firstname = "TESTGP";
		String birthdate = "01/01/2016";
		
		List<Individu> individu = individuRepository.findByNomAndPrenomAndDateNaissance(lastname, firstname, birthdate);
		
		Assert.assertNotNull(individu);
		Assert.assertTrue(individu.size() == 1);
		Assert.assertEquals(GIN_TEST, individu.get(0).getSgin());
	}
}
