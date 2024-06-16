package com.afklm.cati.common.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.afklm.cati.common.dto.VariablesDTO;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.VariablesService;
import com.afklm.cati.common.service.impl.VariablesImpl;
import com.afklm.cati.common.test.spring.TestConfiguration;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.entity.Variables;

/**
 * <p>
 * Title : VariablesServiceTest.java
 * </p>
 * Test Class for {@link VariablesServiceTest}
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
public class VariablesServiceTest extends AbstractServiceTest {
	
	private static final List<String> VARIABLES_NOT_ALTERABLE_TEST = new ArrayList<String>(Arrays.asList("REGEX_WARNING"));

	@Autowired
	private VariablesService variablesService;
	
	String userTest = "userTest";
	
	/**
	 * Test to retrieve all Variables that can be modified
	 *

	 * @throws JrafDaoException jraf dao exception
	 * @throws CatiCommonsException common exception
	 * @throws IllegalAccessException  Illegal exception
	 */
	@Test
	public void testGetAllVariablesAlterable() throws CatiCommonsException, JrafDaoException, IllegalAccessException {
		
        final Field field = FieldUtils.getField(VariablesImpl.class, "VARIABLES_NOT_ALTERABLE", true);
        FieldUtils.removeFinalModifier(field, true);
        FieldUtils.writeStaticField(field, VARIABLES_NOT_ALTERABLE_TEST, true);

		List<Variables> vars = variablesService.getAllVariablesAlterable();
		assertThat(vars.size()).isEqualTo(3);
	}
	
	
	/**
	 * Test to retrieve all Variables
	 *
	 * @throws JrafDaoException jraf dao exception
	 * @throws CatiCommonsException common exception
	 */
	@Test
	public void testGetAllVariables() throws CatiCommonsException, JrafDaoException {
		
		List<Variables> vars = variablesService.getAllVariables();
		assertThat(vars.size()).isEqualTo(4);
	}

	/**
	 * Test to retrieve one Variables
	 * 
	 * @return Variables variable cati
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testGetVariables() throws CatiCommonsException, JrafDaoException {
		
		String key = "PROVIDECUSTOMER360_PERIODINDAYS";
		Optional<Variables> variables = variablesService.getVariables(key);
		assertTrue(variables.isPresent());
	}
	

	/**
	 * Test to post one refComPrefDgt
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddVariables() throws CatiCommonsException, JrafDaoException {
		
		VariablesDTO variablesDTO = new VariablesDTO();
		variablesDTO.setEnvKey("TESTADDKEY");
		variablesDTO.setEnvValue("TESTADDVALUE");
		
		variablesService.addVariables(variablesDTO);
		Optional<Variables> variablesResponse = variablesService.getVariables("TESTADDKEY");
		assertTrue(variablesResponse.isPresent());
	}
	
	/**
	 * Test to post one refComPrefDgt
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testAddVariablesAlreadyExisting() throws CatiCommonsException, JrafDaoException {
		
		VariablesDTO variablesDTO = new VariablesDTO();
		variablesDTO.setEnvKey("PROVIDECUSTOMER360_PERIODINDAYS");
		variablesDTO.setEnvValue("TESTADDVALUE");
		
		variablesService.addVariables(variablesDTO);
		Optional<Variables> variablesResponse = variablesService.getVariables("PROVIDECUSTOMER360_PERIODINDAYS");
		assertTrue("TESTADDVALUE".equalsIgnoreCase(variablesResponse.get().getEnvValue()));
	}

	/**
	 * Test to update Variables
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testUpdateVariables() throws CatiCommonsException, JrafDaoException {
		
		String key = "PROVIDECUSTOMER360_PERIODINDAYS";
		Optional<Variables> variables = variablesService.getVariables(key);
		variables.get().setEnvValue("3000");
		variablesService.updateVariables(variables.get());
		
		Optional<Variables> variablesUpdated = variablesService.getVariables(key);
		assertTrue(variablesUpdated.isPresent());
		assertTrue("3000".equalsIgnoreCase(variablesUpdated.get().getEnvValue()));	
	}
	
	/**
	 * Test to update Variables Key Already Exists
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testUpdateVariablesKeyAlreadyExists() {
		
		String key = "PROVIDECUSTOMER360_PERIODINDAYS";
		Optional<Variables> variables = null;
		
		try {
			variables = variablesService.getVariables(key);
		} catch (JrafDaoException | CatiCommonsException e1) {
			Assert.fail("Get should not raise an error");
		}
		
		variables.get().setEnvKey("FORGOTTEN_PERIODINDAYS");
		variables.get().setEnvValue("3000");
		
		try {
			variablesService.updateVariables(variables.get());
		} catch (Exception | CatiCommonsException e) {
			Assert.assertEquals("identifier of an instance of com.afklm.cati.common.entity.Variables was altered from PROVIDECUSTOMER360_PERIODINDAYS to FORGOTTEN_PERIODINDAYS; nested exception is org.hibernate.HibernateException: identifier of an instance of com.afklm.cati.common.entity.Variables was altered from PROVIDECUSTOMER360_PERIODINDAYS to FORGOTTEN_PERIODINDAYS", e.getMessage());
		}
	}

	/**
	 * Test to remove Variables
	 * 
	 * @throws CatiCommonsException, JrafDaoException
	 */
	@Test
	public void testRemoveVariables() throws CatiCommonsException, JrafDaoException {
		
		String key = "PROVIDECUSTOMER360_PERIODINDAYS";
		
		variablesService.removeVariables(key, userTest);
		Optional<Variables> VariablesResponse = variablesService.getVariables(key);
		assertFalse(VariablesResponse.isPresent());
	}
}
