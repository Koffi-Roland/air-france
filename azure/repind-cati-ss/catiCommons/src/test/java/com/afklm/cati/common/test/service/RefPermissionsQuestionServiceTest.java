package com.afklm.cati.common.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.afklm.cati.common.dto.RefPermissionsQuestionDTO;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefPermissionsQuestionService;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.entity.RefPermissionsQuestion;

/**
 * <p>
 * Title : RefPermissionsQuestionServiceTest.java
 * </p>
 * Test Class for {@link RefPermissionsQuestionServiceTest}
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
public class RefPermissionsQuestionServiceTest extends AbstractServiceTest {

	@Autowired
	private RefPermissionsQuestionService refPermissionsQuestionService;
	
	String userTest = "userTest";
	
	/**
	 * Test to retrieve all refPermissionsQuestion
	 *
	 * @throws JrafDaoException jraf dao exception
	 * @throws CatiCommonsException common exception
	 */
	@Test
	public void testGetAllRefPermissionsQuestion() throws CatiCommonsException, JrafDaoException {
		List<RefPermissionsQuestion> refs = refPermissionsQuestionService.getAllRefPermissionsQuestion();
		assertThat(refs.size()).isEqualTo(3);
	}

	/**
	 * Test to retrieve one refPermissionsQuestion
	 * 
	 * @return refPermissionsQuestion premission question
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetRefPermissionsQuestion() throws CatiCommonsException, JrafDaoException {
		Integer refPermissionsQuestionId = 101;
		Optional<RefPermissionsQuestion> refPermissionsQuestion = refPermissionsQuestionService.getRefPermissionsQuestion(refPermissionsQuestionId);
		assertTrue(refPermissionsQuestion.isPresent());
	}
	

	/**
	 * Test to post one refPermissionsQuestion
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddRefPermissionsQuestion() throws CatiCommonsException, JrafDaoException {

		RefPermissionsQuestionDTO refPermissionsQuestionDTO = new RefPermissionsQuestionDTO();
		refPermissionsQuestionDTO.setName("test");
		refPermissionsQuestionDTO.setQuestion("test");
		refPermissionsQuestionDTO.setQuestionEN("test");
		
		Date date = new Date();
		refPermissionsQuestionDTO.setDateCreation(date);
		refPermissionsQuestionDTO.setDateModification(date);
		refPermissionsQuestionDTO.setSignatureCreation("test");
		refPermissionsQuestionDTO.setSignatureModification("test");
		refPermissionsQuestionDTO.setSiteCreation("test");
		refPermissionsQuestionDTO.setSiteModification("test");
		
		refPermissionsQuestionService.addRefPermissionsQuestion(refPermissionsQuestionDTO, userTest);
		Optional<RefPermissionsQuestion> refPermissionsQuestionResponse = refPermissionsQuestionService.getRefPermissionsQuestion(1);
		assertTrue(refPermissionsQuestionResponse.isPresent());
		
	}

	/**
	 * Test to update refPermissionsQuestion
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testUpdateRefPermissionsQuestion() throws CatiCommonsException, JrafDaoException {

		Integer id = 103;
		RefPermissionsQuestion refPermissionsQuestion = new RefPermissionsQuestion();
		refPermissionsQuestion.setId(id);
		refPermissionsQuestion.setName("test2");
		refPermissionsQuestion.setQuestion("test");
		refPermissionsQuestion.setQuestionEN("test");
		
		Date date = new Date();
		refPermissionsQuestion.setDateCreation(date);
		refPermissionsQuestion.setDateModification(date);
		refPermissionsQuestion.setSignatureCreation("test");
		refPermissionsQuestion.setSignatureModification("test");
		refPermissionsQuestion.setSiteCreation("test");
		refPermissionsQuestion.setSiteModification("test");
		
		refPermissionsQuestionService.updateRefPermissionsQuestion(refPermissionsQuestion, userTest);
		Optional<RefPermissionsQuestion> refPermissionsQuestionResponse = refPermissionsQuestionService.getRefPermissionsQuestion(id);
		assertTrue(refPermissionsQuestionResponse.isPresent());
		assertThat(refPermissionsQuestionResponse.get().getName()).isEqualTo("test2");
		
	}

	/**
	 * REPIND-1238: No more useful as we cannot delete a permission anymore
	 * Button has been disabled in order to be sure to not delete a Permission and alter ID sequence
	 * 
	 * Test to remove refPermissionsQuestion
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 * 
	 */
	@Ignore
	public void testRemoveRefPermissionsQuestion() throws CatiCommonsException, JrafDaoException {
		Integer id = 3;
		refPermissionsQuestionService.removeRefPermissionsQuestion(id, userTest);
		Optional<RefPermissionsQuestion> refPermissionsQuestionResponse = refPermissionsQuestionService.getRefPermissionsQuestion(id);
		assertFalse(refPermissionsQuestionResponse.isPresent());
	}
	
}
