package com.afklm.cati.common.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.cati.common.criteria.PaysCriteria;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.model.ModelPays;
import com.afklm.cati.common.service.PaysService;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

/**
 * Test Class for {@link PaysServiceTest}
 * <p>
 * Copyright : Copyright (c) 2018
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@Transactional(value = "transactionManagerSic")
public class PaysServiceTest extends AbstractServiceTest {
	
	@Autowired
	private PaysService paysService;
	
	String userTest = "userTest";
	
	/**
	 * Test to retrieve all Pays
	 * 
	 * @throws CatiCommonsException
	 * @throws JrafDaoException 
	 */
	@Test
	public void testGetAllPays() throws CatiCommonsException, JrafDaoException {
		List<ModelPays> vars = paysService.getAllPays();
		assertThat(vars.size()).isGreaterThan(0);
	}

	/**
	 * Test to retrieve one Pays
	 * 
	 * @return Variables
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetPays() throws CatiCommonsException, JrafDaoException {
		String codePays = "AD";
		Optional<ModelPays> pays = paysService.getPays(codePays);
		assertTrue(pays.isPresent());
	}

	/**
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testUpdatePays() throws CatiCommonsException, JrafDaoException {
		String normalization = "O";
		String codePays = "AD";
		
		Optional<ModelPays> pays = paysService.getPays(codePays);
		
		if(pays.get().getNormalisable().equals("O")){
			normalization = "N";
		}

		PaysCriteria paysCriteria = new PaysCriteria(codePays, normalization);
		paysService.updatePays(paysCriteria);

		Optional<ModelPays> paysUpdated = paysService.getPays(codePays);
		assertTrue(paysUpdated.isPresent());
		assertEquals(paysCriteria.getNormalisable(),paysUpdated.get().getNormalisable());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgumentExceptionCodePaysMissing() throws JrafDaoException, CatiCommonsException {
		String normalization = "O";
		PaysCriteria paysCriteria = new PaysCriteria(null, normalization);
		paysService.updatePays(paysCriteria);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgumentExceptionNormalisableMissing() throws JrafDaoException, CatiCommonsException {
		String codePays = "AD";
		PaysCriteria paysCriteria = new PaysCriteria(codePays, null);
		paysService.updatePays(paysCriteria);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgumentExceptionCodePaysLong() throws JrafDaoException, CatiCommonsException {
		String normalization = "O";
		PaysCriteria paysCriteria = new PaysCriteria("TOOLONG", normalization);
		paysService.updatePays(paysCriteria);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgumentExceptionNormalisableWrong() throws JrafDaoException, CatiCommonsException {
		String codePays = "AD";
		PaysCriteria paysCriteria = new PaysCriteria(codePays, "WRONG");
		paysService.updatePays(paysCriteria);
	}

	
}
