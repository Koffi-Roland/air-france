package com.afklm.cati.common.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefComPrefService;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.repository.RefComPrefMlRepository;
import com.afklm.cati.common.entity.RefComPref;
import com.afklm.cati.common.entity.RefComPrefDomain;
import com.afklm.cati.common.entity.RefComPrefGType;
import com.afklm.cati.common.entity.RefComPrefMedia;
import com.afklm.cati.common.entity.RefComPrefMl;
import com.afklm.cati.common.entity.RefComPrefType;

/**
 * <p>
 * Title : refComPrefServiceTest.java
 * </p>
 * Test Class for {@link RefComPrefService}
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
public class RefComPrefServiceTest extends AbstractServiceTest {

	@Autowired
	private RefComPrefService refComPrefService;
	@Autowired
	private RefComPrefMlRepository refComPrefMlRepo;
	
	String userTest = "userTest";
	
	/**
	 * Test to retrieve all refComPref
	 * 
	 * @throws CatiCommonsException
	 * @throws JrafDaoException 
	 */
	@Test
	public void testGetAllRefComPref() throws CatiCommonsException, JrafDaoException {
		List<RefComPref> refs = refComPrefService.getAllRefComPref();
		assertThat(refs.size()).isEqualTo(3);
	}

	/**
	 * Test to retrieve one refComPref
	 * 
	 * @return refComPref ref communication preferences
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetRefComPref() throws CatiCommonsException, JrafDaoException {
		Integer refComPrefId = 101;
		Optional<RefComPref> refComPref = refComPrefService.getRefComPref(refComPrefId);
		assertTrue(refComPref.isPresent());
	}
	

	/**
	 * Test to post one refComPref
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddRefComPref() throws CatiCommonsException, JrafDaoException {

		RefComPrefGType pComGroupeType = new RefComPrefGType();
		pComGroupeType.setCodeGType("N");
		RefComPrefMedia pMedia = new RefComPrefMedia();
		pMedia.setCodeMedia("E");
		RefComPrefType pComType = new RefComPrefType();
		pComType.setCodeType("KL");
		RefComPrefDomain pDomain = new RefComPrefDomain();
		pDomain.setCodeDomain("S");
		
		RefComPref refComPref = new RefComPref();
		refComPref.setMarket("GB");
		refComPref.setDefaultLanguage1("FR");
		refComPref.setDescription("Description");
		refComPref.setFieldA("Y");
		refComPref.setFieldN("Y");
		refComPref.setFieldT("Y");
		refComPref.setMandatoryOptin("Y");
		refComPref.setComGroupeType(pComGroupeType);
		refComPref.setComType(pComType);
		refComPref.setDomain(pDomain);
		refComPref.setMedia(pMedia);
		
		Date date = new Date();
		refComPref.setDateCreation(date);
		refComPref.setDateModification(date);
		refComPref.setSignatureCreation("test");
		refComPref.setSignatureModification("test");
		refComPref.setSiteCreation("test");
		refComPref.setSiteModification("test");

		assertEquals(3,refComPrefMlRepo.findAll().size());
		refComPrefService.addRefComPref(refComPref, userTest);
		assertEquals(4,refComPrefMlRepo.findAll().size());
		
	}

	/**
	 * Test to update refComPref
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testUpdateRefComPref() throws CatiCommonsException, JrafDaoException {

		RefComPrefGType pComGroupeType = new RefComPrefGType();
		pComGroupeType.setCodeGType("N");
		RefComPrefMedia pMedia = new RefComPrefMedia();
		pMedia.setCodeMedia("E");
		RefComPrefType pComType = new RefComPrefType();
		pComType.setCodeType("KL");
		RefComPrefDomain pDomain = new RefComPrefDomain();
		pDomain.setCodeDomain("S");
		
		Integer id = 101;
		RefComPref refComPref = new RefComPref();
		refComPref.setRefComprefId(id);
		refComPref.setMarket("EN");
		refComPref.setDefaultLanguage1("FR");
		refComPref.setDescription("Description");
		refComPref.setFieldA("Y");
		refComPref.setFieldN("Y");
		refComPref.setFieldT("Y");
		refComPref.setMandatoryOptin("Y");
		refComPref.setComGroupeType(pComGroupeType);
		refComPref.setComType(pComType);
		refComPref.setDomain(pDomain);
		refComPref.setMedia(pMedia);
		
		Date date = new Date();
		refComPref.setDateCreation(date);
		refComPref.setDateModification(date);
		refComPref.setSignatureCreation("test");
		refComPref.setSignatureModification("test");
		refComPref.setSiteCreation("test");
		refComPref.setSiteModification("test");
		
		refComPrefService.updateRefComPref(refComPref, userTest);
		Optional<RefComPrefMl> refComPrefResponse = refComPrefMlRepo.findById(id);
		assertTrue(refComPrefResponse.isPresent());
		assertThat(refComPrefResponse.get().getRefComPrefDgt().getType().getCodeType()).isEqualTo("KL");
		
	}

	/**
	 * Test to remove refComPref
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testRemoveRefComPref() throws CatiCommonsException, JrafDaoException {
		Integer id = 101;
		refComPrefService.removeRefComPref(id, userTest);
		Optional<RefComPref> refComPrefResponse = refComPrefService.getRefComPref(id);
		assertFalse(refComPrefResponse.isPresent());
	}
	
}
