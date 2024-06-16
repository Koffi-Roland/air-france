package com.afklm.cati.common.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.afklm.cati.common.dto.RefComPrefGTypeDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefComPrefGTypeService;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.entity.RefComPrefGType;

/**
 * <p>
 * Title : refComPrefGTypeServiceTest.java
 * </p>
 * Test Class for {@link RefComPrefGTypeService}
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
public class RefComPrefGTypeServiceTest extends AbstractServiceTest {

	@Autowired
	private RefComPrefGTypeService refComPrefGTypeService;
	
	String userTest = "userTest";
	
	/**
	 * Test to retrieve all refComPrefGType
	 *
	 * @throws JrafDaoException jraf dao exception
	 * @throws CatiCommonsException common exception
	 */
	@Test
	public void testGetAllRefComPrefGType() throws CatiCommonsException, JrafDaoException {
		List<RefComPrefGType> refs = refComPrefGTypeService.getAllRefComPrefGType();
		assertThat(refs.size()).isEqualTo(3);
	}

	/**
	 * Test to retrieve one refComPrefGType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetRefComPrefGType() throws CatiCommonsException, JrafDaoException {
		String refComPrefGTypeCode = "N";
		Optional<RefComPrefGType> refComPrefGType = refComPrefGTypeService.getRefComPrefGType(refComPrefGTypeCode);
		assertTrue(refComPrefGType.isPresent());
	}
	

	/**
	 * Test to post one refComPrefGType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddRefComPrefGType() throws CatiCommonsException, JrafDaoException {

		String code = "T";
		RefComPrefGTypeDTO pGTypeDTO = new RefComPrefGTypeDTO();
		pGTypeDTO.setCodeGType(code);
		pGTypeDTO.setLibelleGType("Test");
		pGTypeDTO.setLibelleGTypeEN("Test");
		
		Date date = new Date();
		pGTypeDTO.setDateCreation(date);
		pGTypeDTO.setDateModification(date);
		pGTypeDTO.setSignatureCreation("test");
		pGTypeDTO.setSignatureModification("test");
		pGTypeDTO.setSiteCreation("test");
		pGTypeDTO.setSiteModification("test");
		
		refComPrefGTypeService.addRefComPrefGType(pGTypeDTO, userTest);
		Optional<RefComPrefGType> refComPrefGTypeResponse = refComPrefGTypeService.getRefComPrefGType(code);
		assertTrue(refComPrefGTypeResponse.isPresent());
	}

	/**
	 * Test to update refComPrefGType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testUpdateRefComPrefGType() throws CatiCommonsException, JrafDaoException {

		String code = "M";
		RefComPrefGType pGType = new RefComPrefGType();
		pGType.setCodeGType(code);
		pGType.setLibelleGType("Test");
		pGType.setLibelleGTypeEN("Test");
		
		Date date = new Date();
		pGType.setDateCreation(date);
		pGType.setDateModification(date);
		pGType.setSignatureCreation("test");
		pGType.setSignatureModification("test");
		pGType.setSiteCreation("test");
		pGType.setSiteModification("test");

		refComPrefGTypeService.updateRefComPrefGType(pGType, userTest);
		Optional<RefComPrefGType> refComPrefGTypeResponse = refComPrefGTypeService.getRefComPrefGType(code);
		assertTrue(refComPrefGTypeResponse.isPresent());
		assertThat(refComPrefGTypeResponse.get().getLibelleGType()).isEqualTo("Test");
		
	}

	/**
	 * Test to remove refComPrefGType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testRemoveRefComPrefGType() throws CatiCommonsException, JrafDaoException {
		String code = "C";
		refComPrefGTypeService.removeRefComPrefGType(code, userTest);
		Optional<RefComPrefGType> refComPrefGTypeResponse = refComPrefGTypeService.getRefComPrefGType(code);
		assertFalse(refComPrefGTypeResponse.isPresent());
	}
	
}
