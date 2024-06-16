package com.afklm.cati.common.test.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefPermissionsService;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.entity.RefPermissions;
import com.afklm.cati.common.entity.RefPermissionsQuestion;

/**
 * <p>
 * Title : RefPermissionsServiceTest.java
 * </p>
 * Test Class for {@link RefPermissionsServiceTest}
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
public class RefPermissionsServiceTest extends AbstractServiceTest {

	@Autowired
	private RefPermissionsService refPermissionsService;
	
	String userTest = "userTest";

	/**
	 * Test to retrieve comPref for one refPermissionsQuestion
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetComPrefDgtForOneRefPermissionsQuestion() throws CatiCommonsException, JrafDaoException {
		
		RefPermissionsQuestion refPermissionsQuestion = new RefPermissionsQuestion();
		refPermissionsQuestion.setId(101);
		
		List<Integer> comPrefForThisPermissionQuestion = refPermissionsService.getRefComPrefDgtByRefPermissionsQuestion(refPermissionsQuestion);
		
		assertEquals(2, comPrefForThisPermissionQuestion.size());
	}

	@Test
	public void testGetComPrefDgtForOneRefPermissionsQuestionWithNoneComPref() throws CatiCommonsException, JrafDaoException {
		
		RefPermissionsQuestion refPermissionsQuestion = new RefPermissionsQuestion();
		refPermissionsQuestion.setId(3);
		
		List<Integer> comPrefForThisPermissionQuestion = refPermissionsService.getRefComPrefDgtByRefPermissionsQuestion(refPermissionsQuestion);
		
		assertEquals(0, comPrefForThisPermissionQuestion.size());
	}

	/**
	 * Test to post one refPermissionsQuestion
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testRemoveOnePermissionsForOnePermissionsQuestion() throws CatiCommonsException, JrafDaoException {

		Integer id = 101;
		RefPermissions refPermissions = new RefPermissions();
		
		Date date = new Date();
		refPermissions.setDateCreation(date);
		refPermissions.setDateModification(date);
		refPermissions.setSignatureCreation("test");
		refPermissions.setSignatureModification("test");
		refPermissions.setSiteCreation("test");
		refPermissions.setSiteModification("test");
		
		List<Integer> listComPrefDgt = new ArrayList<Integer>();
		listComPrefDgt.add(101);
		
		refPermissionsService.addRefPermissions(refPermissions, id, listComPrefDgt, userTest);

		RefPermissionsQuestion refPermissionsQuestion = new RefPermissionsQuestion();
		refPermissionsQuestion.setId(id);
		List<Integer> comPrefForThisPermissionQuestion = refPermissionsService.getRefComPrefDgtByRefPermissionsQuestion(refPermissionsQuestion);
		
		assertEquals(1, comPrefForThisPermissionQuestion.size());
		
	}
	
	/**
	 * Test to post one refPermissionsQuestion
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddPermissionsForOnePermissionsQuestion() throws CatiCommonsException, JrafDaoException {

		Integer id = 101;
		RefPermissions refPermissions = new RefPermissions();
		
		Date date = new Date();
		refPermissions.setDateCreation(date);
		refPermissions.setDateModification(date);
		refPermissions.setSignatureCreation("test");
		refPermissions.setSignatureModification("test");
		refPermissions.setSiteCreation("test");
		refPermissions.setSiteModification("test");
		
		List<Integer> listComPrefDgt = new ArrayList<Integer>();
		listComPrefDgt.add(101);
		listComPrefDgt.add(102);
		listComPrefDgt.add(103);
		
		refPermissionsService.addRefPermissions(refPermissions, id, listComPrefDgt, userTest);

		RefPermissionsQuestion refPermissionsQuestion = new RefPermissionsQuestion();
		refPermissionsQuestion.setId(id);
		List<Integer> comPrefForThisPermissionQuestion = refPermissionsService.getRefComPrefDgtByRefPermissionsQuestion(refPermissionsQuestion);
		
		assertEquals(3, comPrefForThisPermissionQuestion.size());
		
	}

	/**
	 * Test to post one refPermissionsQuestion
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddAndRemovePermissionsForOnePermissionsQuestion() throws CatiCommonsException, JrafDaoException {

		Integer id = 101;
		RefPermissions refPermissions = new RefPermissions();
		
		Date date = new Date();
		refPermissions.setDateCreation(date);
		refPermissions.setDateModification(date);
		refPermissions.setSignatureCreation("test");
		refPermissions.setSignatureModification("test");
		refPermissions.setSiteCreation("test");
		refPermissions.setSiteModification("test");
		
		List<Integer> listComPrefDgt = new ArrayList<Integer>();
		listComPrefDgt.add(102);
		listComPrefDgt.add(103);
		
		refPermissionsService.addRefPermissions(refPermissions, id, listComPrefDgt, userTest);

		RefPermissionsQuestion refPermissionsQuestion = new RefPermissionsQuestion();
		refPermissionsQuestion.setId(id);
		List<Integer> comPrefForThisPermissionQuestion = refPermissionsService.getRefComPrefDgtByRefPermissionsQuestion(refPermissionsQuestion);
		
		assertEquals(2, comPrefForThisPermissionQuestion.size());
		
	}
	
}
