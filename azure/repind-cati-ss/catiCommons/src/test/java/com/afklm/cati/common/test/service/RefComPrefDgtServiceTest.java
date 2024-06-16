package com.afklm.cati.common.test.service;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.afklm.cati.common.service.RefComPrefDgtService;
import com.afklm.cati.common.service.RefComPrefService;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.entity.RefComPrefDgt;
import com.afklm.cati.common.entity.RefComPrefDomain;
import com.afklm.cati.common.entity.RefComPrefGType;
import com.afklm.cati.common.entity.RefComPrefType;

/**
 * <p>
 * Title : RefComPrefDgtServiceTest.java
 * </p>
 * Test Class for {@link RefComPrefDgtServiceTest}
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
public class RefComPrefDgtServiceTest extends AbstractServiceTest {

	@Autowired
	private RefComPrefDgtService refComPrefDgtService;

	@Autowired
	private RefComPrefService refComPrefMlService;
	
	String userTest = "userTest";
	
	/**
	 * Test to retrieve all refComPrefDgt
	 *

	 * @throws JrafDaoException jraf dao exception
	 * @throws CatiCommonsException common exception
	 */
	@Test
	public void testGetAllRefComPrefDgt() throws CatiCommonsException, JrafDaoException {
		List<RefComPrefDgt> refs = refComPrefDgtService.getAllRefComPrefDgt();
		assertThat(refs.size()).isEqualTo(3);
	}

	/**
	 * Test to retrieve one refComPrefDgt
	 * 
	 * @return refComPref
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetRefComPrefDgt() throws CatiCommonsException, JrafDaoException {
		Integer refComPrefDgtId = 101;
		Optional<RefComPrefDgt> refComPrefDgt = refComPrefDgtService.getRefComPrefDgt(refComPrefDgtId);
		assertTrue(refComPrefDgt.isPresent());
	}
	

	/**
	 * Test to post one refComPrefDgt
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddRefComPrefDgt() throws CatiCommonsException, JrafDaoException {

		RefComPrefGType pComGroupeType = new RefComPrefGType();
		pComGroupeType.setCodeGType("N");
		RefComPrefType pComType = new RefComPrefType();
		pComType.setCodeType("KL");
		RefComPrefDomain pDomain = new RefComPrefDomain();
		pDomain.setCodeDomain("F");
		
		RefComPrefDgt refComPrefDgt = new RefComPrefDgt();
		refComPrefDgt.setDescription("Description");
		refComPrefDgt.setGroupType(pComGroupeType);
		refComPrefDgt.setType(pComType);
		refComPrefDgt.setDomain(pDomain);
		
		Date date = new Date();
		refComPrefDgt.setDateCreation(date);
		refComPrefDgt.setDateModification(date);
		refComPrefDgt.setSignatureCreation("test");
		refComPrefDgt.setSignatureModification("test");
		refComPrefDgt.setSiteCreation("test");
		refComPrefDgt.setSiteModification("test");
		
		refComPrefDgtService.addRefComPrefDgt(refComPrefDgt, userTest);
		Optional<RefComPrefDgt> refComPrefDgtResponse = refComPrefDgtService.getRefComPrefDgt(1);
		assertTrue(refComPrefDgtResponse.isPresent());
		
	}

	/**
	 * Test to update refComPrefDgt
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testUpdateRefComPrefDgt() throws CatiCommonsException, JrafDaoException {

		Integer id = 102;
		Optional<RefComPrefDgt> refComPrefDgtRequest = refComPrefDgtService.getRefComPrefDgt(id);
		refComPrefDgtRequest.get().getType().setCodeType("KL");
		Long nbResultRefComPrefDgtRequest = refComPrefMlService.countRefComPrefMlByDgt(id);
		
		refComPrefDgtService.updateRefComPrefDgt(refComPrefDgtRequest.get(), userTest);
		Optional<RefComPrefDgt> refComPrefDgtResponse = refComPrefDgtService.getRefComPrefDgt(id);
		Long nbResultRefComPrefDgtResponse = refComPrefMlService.countRefComPrefMlByDgt(id);
		assertTrue(refComPrefDgtResponse.isPresent());
		assertThat(refComPrefDgtResponse.get().getType().getCodeType()).isEqualTo("KL");
		assertThat(nbResultRefComPrefDgtRequest).isEqualTo(nbResultRefComPrefDgtResponse);
		
	}

	/**
	 * Test to remove refComPref
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testRemoveRefComPrefDgt() throws CatiCommonsException, JrafDaoException {
		Integer id = 103;
		refComPrefDgtService.removeRefComPrefDgt(id, userTest);
		Optional<RefComPrefDgt> refComPrefDgtResponse = refComPrefDgtService.getRefComPrefDgt(id);
		assertFalse(refComPrefDgtResponse.isPresent());
	}

	
}
