package com.afklm.cati.common.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.afklm.cati.common.dto.RefComPrefMediaDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefComPrefMediaService;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.entity.RefComPrefMedia;

/**
 * <p>
 * Title : refComPrefMediaServiceTest.java
 * </p>
 * Test Class for {@link RefComPrefMediaService}
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
public class RefComPrefMediaServiceTest extends AbstractServiceTest {

	@Autowired
	private RefComPrefMediaService refComPrefMediaService;
	
	String userTest = "userTest";
	
	/**
	 * Test to retrieve all refComPrefMedia
	 *
	 * @throws JrafDaoException jraf dao exception
	 * @throws CatiCommonsException common exception
	 */
	@Test
	public void testGetAllRefComPrefMedia() throws CatiCommonsException, JrafDaoException {
		List<RefComPrefMedia> refs = refComPrefMediaService.getAllRefComPrefMedia();
		assertThat(refs.size()).isEqualTo(2);
	}

	/**
	 * Test to retrieve one refComPrefMedia
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetRefComPrefMedia() throws CatiCommonsException, JrafDaoException {
		String refComPrefMediaCode = "E";
		Optional<RefComPrefMedia> refComPrefMedia = refComPrefMediaService.getRefComPrefMedia(refComPrefMediaCode);
		assertTrue(refComPrefMedia.isPresent());
	}
	

	/**
	 * Test to post one refComPrefMedia
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddRefComPrefMedia() throws CatiCommonsException, JrafDaoException {

		String code = "T";
		RefComPrefMediaDTO pMediaDTO = new RefComPrefMediaDTO();
		pMediaDTO.setCodeMedia(code);
		pMediaDTO.setLibelleMedia("Test");
		pMediaDTO.setLibelleMediaEN("Test");
		
		Date date = new Date();
		pMediaDTO.setDateCreation(date);
		pMediaDTO.setDateModification(date);
		pMediaDTO.setSignatureCreation("test");
		pMediaDTO.setSignatureModification("test");
		pMediaDTO.setSiteCreation("test");
		pMediaDTO.setSiteModification("test");
		
		refComPrefMediaService.addRefComPrefMedia(pMediaDTO, userTest);
		Optional<RefComPrefMedia> refComPrefMediaResponse = refComPrefMediaService.getRefComPrefMedia(code);
		assertTrue(refComPrefMediaResponse.isPresent());
	}

	/**
	 * Test to update refComPrefMedia
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testUpdateRefComPrefMedia() throws CatiCommonsException, JrafDaoException {

		String code = "E";
		RefComPrefMedia pMedia = new RefComPrefMedia();
		pMedia.setCodeMedia(code);
		pMedia.setLibelleMedia("Test");
		pMedia.setLibelleMediaEN("Test");
		
		Date date = new Date();
		pMedia.setDateCreation(date);
		pMedia.setDateModification(date);
		pMedia.setSignatureCreation("test");
		pMedia.setSignatureModification("test");
		pMedia.setSiteCreation("test");
		pMedia.setSiteModification("test");

		refComPrefMediaService.updateRefComPrefMedia(pMedia, userTest);
		Optional<RefComPrefMedia> refComPrefMediaResponse = refComPrefMediaService.getRefComPrefMedia(code);
		assertTrue(refComPrefMediaResponse.isPresent());
		assertThat(refComPrefMediaResponse.get().getLibelleMedia()).isEqualTo("Test");
		
	}

	/**
	 * Test to remove refComPrefMedia
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testRemoveRefComPrefMedia() throws CatiCommonsException, JrafDaoException {
		String code = "O";
		refComPrefMediaService.removeRefComPrefMedia(code, userTest);
		Optional<RefComPrefMedia> refComPrefMediaResponse = refComPrefMediaService.getRefComPrefMedia(code);
		assertFalse(refComPrefMediaResponse.isPresent());
	}
	
}
