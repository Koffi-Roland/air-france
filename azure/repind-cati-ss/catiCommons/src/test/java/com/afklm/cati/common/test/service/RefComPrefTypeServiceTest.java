package com.afklm.cati.common.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.afklm.cati.common.dto.RefComPrefTypeDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefComPrefTypeService;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.entity.RefComPrefType;

/**
 * <p>
 * Title : refComPrefTypeServiceTest.java
 * </p>
 * Test Class for {@link RefComPrefTypeService}
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
public class RefComPrefTypeServiceTest extends AbstractServiceTest {

	@Autowired
	private RefComPrefTypeService refComPrefTypeService;
	
	String userTest = "userTest";
	
	/**
	 * Test to retrieve all refComPrefType
	 *
	 * @throws JrafDaoException jraf dao exception
	 * @throws CatiCommonsException common exception
	 */
	@Test
	public void testGetAllRefComPrefType() throws CatiCommonsException, JrafDaoException {
		List<RefComPrefType> refs = refComPrefTypeService.getAllRefComPrefType();
		assertThat(refs.size()).isEqualTo(4);
	}

	/**
	 * Test to retrieve one refComPrefType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetRefComPrefType() throws CatiCommonsException, JrafDaoException {
		String refComPrefTypeCode = "AF";
		Optional<RefComPrefType> refComPrefType = refComPrefTypeService.getRefComPrefType(refComPrefTypeCode);
		assertTrue(refComPrefType.isPresent());
		assertThat(refComPrefType.get().getLibelleType()).isEqualTo("Air France Newsletter");
	}
	

	/**
	 * Test to post one refComPrefType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddRefComPrefType() throws CatiCommonsException, JrafDaoException {

		String code = "T";
		RefComPrefTypeDTO pTypeDTO = new RefComPrefTypeDTO();
		pTypeDTO.setCodeType(code);
		pTypeDTO.setLibelleType("Test");
		pTypeDTO.setLibelleTypeEN("Test");
		
		Date date = new Date();
		pTypeDTO.setDateCreation(date);
		pTypeDTO.setDateModification(date);
		pTypeDTO.setSignatureCreation("test");
		pTypeDTO.setSignatureModification("test");
		pTypeDTO.setSiteCreation("test");
		pTypeDTO.setSiteModification("test");
		
		refComPrefTypeService.addRefComPrefType(pTypeDTO, userTest);
		Optional<RefComPrefType> refComPrefTypeResponse = refComPrefTypeService.getRefComPrefType(code);
		assertTrue(refComPrefTypeResponse.isPresent());
	}

	/**
	 * Test to update refComPrefType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testUpdateRefComPrefType() throws CatiCommonsException, JrafDaoException {

		String code = "AF";
		RefComPrefType pType = new RefComPrefType();
		pType.setCodeType(code);
		pType.setLibelleType("Test");
		pType.setLibelleTypeEN("Test");
		
		Date date = new Date();
		pType.setDateCreation(date);
		pType.setDateModification(date);
		pType.setSignatureCreation("test");
		pType.setSignatureModification("test");
		pType.setSiteCreation("test");
		pType.setSiteModification("test");

		refComPrefTypeService.updateRefComPrefType(pType, userTest);
		Optional<RefComPrefType> refComPrefTypeResponse = refComPrefTypeService.getRefComPrefType(code);
		assertTrue(refComPrefTypeResponse.isPresent());
		assertThat(refComPrefTypeResponse.get().getLibelleType()).isEqualTo("Test");
		
	}

	/**
	 * Test to remove refComPrefType
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testRemoveRefComPrefType() throws CatiCommonsException, JrafDaoException {
		String code = "FB_PART";
		refComPrefTypeService.removeRefComPrefType(code, userTest);
		Optional<RefComPrefType> refComPrefTypeResponse = refComPrefTypeService.getRefComPrefType(code);
		assertFalse(refComPrefTypeResponse.isPresent());
	}
	
}
