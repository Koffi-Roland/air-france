package com.afklm.cati.common.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.afklm.cati.common.dto.RefComPrefCountryMarketDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefComPrefCountryMarketService;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.entity.RefComPrefCountryMarket;

/**
 * <p>
 * Title : refComPrefCountryMarketServiceTest.java
 * </p>
 * Test Class for {@link RefComPrefCountryMarketService}
 * <p>
 * Copyright : Copyright (c) 2017
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@Transactional(value = "transactionManagerSic")
public class RefComPrefCountryMarketServiceTest extends AbstractServiceTest{

	@Autowired
	private RefComPrefCountryMarketService refComPrefCountryMarketService;
	
	String userTest = "userTest";
	
	/**
	 * Test to retrieve all refComPrefCountryMarket
	 *
	 * @throws JrafDaoException jraf dao exception
	 * @throws CatiCommonsException common exception
	 */
	@Test
	public void testGetAllRefComPrefCountryMarket() throws CatiCommonsException, JrafDaoException {
		List<RefComPrefCountryMarket> refs = refComPrefCountryMarketService.getAllRefComPrefCountryMarket();
		assertThat(refs.size()).isEqualTo(4);
	}

	/**
	 * Test to retrieve one refComPrefCountryMarket
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetRefComPrefCountryMarket() throws CatiCommonsException, JrafDaoException {
		String refComPrefCountryMarketCode = "FR";
		Optional<RefComPrefCountryMarket> refComPrefCountryMarket = refComPrefCountryMarketService.getRefComPrefCountryMarket(refComPrefCountryMarketCode);
		assertTrue(refComPrefCountryMarket.isPresent());
	}
	

	/**
	 * Test to post one refComPrefCountryMarket
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddRefComPrefCountryMarket() throws CatiCommonsException, JrafDaoException {

		String code = "TE";
		RefComPrefCountryMarketDTO pCountryMarket = new RefComPrefCountryMarketDTO();
		pCountryMarket.setCodePays(code);
		pCountryMarket.setMarket(code);
		Date date = new Date();
		pCountryMarket.setDateCreation(date);
		pCountryMarket.setDateModification(date);
		pCountryMarket.setSignatureCreation("test");
		pCountryMarket.setSignatureModification("test");
		pCountryMarket.setSiteCreation("test");
		pCountryMarket.setSiteModification("test");
		
		refComPrefCountryMarketService.addRefComPrefCountryMarket(pCountryMarket, userTest);
		Optional<RefComPrefCountryMarket> refComPrefCountryMarketResponse = refComPrefCountryMarketService.getRefComPrefCountryMarket(code);
		assertTrue(refComPrefCountryMarketResponse.isPresent());
	}

	/**
	 * Test to update refComPrefCountryMarket
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testUpdateRefComPrefCountryMarket() throws CatiCommonsException, JrafDaoException {

		String code = "FR";
		RefComPrefCountryMarket pCountryMarket = new RefComPrefCountryMarket();
		pCountryMarket.setCodePays(code);
		pCountryMarket.setMarket("AE");
		
		Date date = new Date();
		pCountryMarket.setDateCreation(date);
		pCountryMarket.setDateModification(date);
		pCountryMarket.setSignatureCreation("test");
		pCountryMarket.setSignatureModification("test");
		pCountryMarket.setSiteCreation("test");
		pCountryMarket.setSiteModification("test");

		refComPrefCountryMarketService.updateRefComPrefCountryMarket(pCountryMarket, userTest);
		Optional<RefComPrefCountryMarket> refComPrefCountryMarketResponse = refComPrefCountryMarketService.getRefComPrefCountryMarket(code);
		assertTrue(refComPrefCountryMarketResponse.isPresent());
		assertThat(refComPrefCountryMarketResponse.get().getMarket()).isEqualTo("AE");
		
	}

	/**
	 * Test to remove refComPrefCountryMarket
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testRemoveRefComPrefCountryMarket() throws CatiCommonsException, JrafDaoException {
		String code = "AD";
		refComPrefCountryMarketService.removeRefComPrefCountryMarket(code, userTest);
		Optional<RefComPrefCountryMarket> refComPrefCountryMarketResponse = refComPrefCountryMarketService.getRefComPrefCountryMarket(code);
		assertFalse(refComPrefCountryMarketResponse.isPresent());
	}
	
}
