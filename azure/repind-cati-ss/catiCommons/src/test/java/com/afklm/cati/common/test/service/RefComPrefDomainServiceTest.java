package com.afklm.cati.common.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.afklm.cati.common.dto.RefComPrefDomainDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefComPrefDomainService;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.entity.RefComPrefDomain;
/**
 * <p>
 * Title : refComPrefDomainServiceTest.java
 * </p>
 * Test Class for {@link RefComPrefDomainService}
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
public class RefComPrefDomainServiceTest extends AbstractServiceTest {

	@Autowired
	private RefComPrefDomainService refComPrefDomainService;
	
	String userTest = "userTest";
	
	/**
	 * Test to retrieve all refComPrefDomain
	 *
	 * @throws JrafDaoException jraf dao exception
	 * @throws CatiCommonsException common exception
	 */
	@Test
	public void testGetAllRefComPrefDomain() throws CatiCommonsException, JrafDaoException {
		List<RefComPrefDomain> refs = refComPrefDomainService.getAllRefComPrefDomain();
		assertThat(refs.size()).isEqualTo(3);
	}

	/**
	 * Test to retrieve one refComPrefDomain
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetRefComPrefDomain() throws CatiCommonsException, JrafDaoException {
		String refComPrefDomainCode = "S";
		Optional<RefComPrefDomain> refComPrefDomain = refComPrefDomainService.getRefComPrefDomain(refComPrefDomainCode);
		assertTrue(refComPrefDomain.isPresent());
	}
	

	/**
	 * Test to post one refComPrefDomain
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddRefComPrefDomain() throws CatiCommonsException, JrafDaoException {

		String code = "T";
		RefComPrefDomainDTO pDomainDTO = new RefComPrefDomainDTO();
		pDomainDTO.setCodeDomain(code);
		pDomainDTO.setLibelleDomain("Test");
		pDomainDTO.setLibelleDomainEN("Test");
		
		Date date = new Date();
		pDomainDTO.setDateCreation(date);
		pDomainDTO.setDateModification(date);
		pDomainDTO.setSignatureCreation("test");
		pDomainDTO.setSignatureModification("test");
		pDomainDTO.setSiteCreation("test");
		pDomainDTO.setSiteModification("test");
		
		refComPrefDomainService.addRefComPrefDomain(pDomainDTO, userTest);
		Optional<RefComPrefDomain> refComPrefDomainResponse = refComPrefDomainService.getRefComPrefDomain(code);
		assertTrue(refComPrefDomainResponse.isPresent());
	}

	/**
	 * Test to update refComPrefDomain
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testUpdateRefComPrefDomain() throws CatiCommonsException, JrafDaoException {

		String code = "S";
		RefComPrefDomain pDomain = new RefComPrefDomain();
		pDomain.setCodeDomain(code);
		pDomain.setLibelleDomain("Test");
		pDomain.setLibelleDomainEN("Test");
		
		Date date = new Date();
		pDomain.setDateCreation(date);
		pDomain.setDateModification(date);
		pDomain.setSignatureCreation("test");
		pDomain.setSignatureModification("test");
		pDomain.setSiteCreation("test");
		pDomain.setSiteModification("test");

		refComPrefDomainService.updateRefComPrefDomain(pDomain, userTest);
		Optional<RefComPrefDomain> refComPrefDomainResponse = refComPrefDomainService.getRefComPrefDomain(code);
		assertTrue(refComPrefDomainResponse.isPresent());
		assertThat(refComPrefDomainResponse.get().getLibelleDomain()).isEqualTo("Test");
		
	}

	/**
	 * Test to remove refComPrefDomain
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testRemoveRefComPrefDomain() throws CatiCommonsException, JrafDaoException {
		String code = "U";
		refComPrefDomainService.removeRefComPrefDomain(code, userTest);
		Optional<RefComPrefDomain> refComPrefDomainResponse = refComPrefDomainService.getRefComPrefDomain(code);
		assertFalse(refComPrefDomainResponse.isPresent());
	}
	
}
