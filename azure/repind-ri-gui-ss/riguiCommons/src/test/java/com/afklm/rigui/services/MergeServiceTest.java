package com.afklm.rigui.services;

import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.MergeService;
import com.afklm.rigui.spring.TestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * <p>
 * Title : userServiceTest.java
 * </p>
 * Test Class for UserService
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfiguration.class)
class MergeServiceTest {

	@Autowired
	private MergeService mergeService;
	private final static String ginSource = "400491001185";
	private final static String cinSource = "001116082305";

	@Test
	void verifyMergeInputByGinTest() throws ServiceException {
		Assertions.assertEquals(ginSource, mergeService.verifyMergeInput(ginSource));
	}

	@Test
	void verifyMergeInputByCinTest() throws ServiceException {
		Assertions.assertEquals(ginSource , mergeService.verifyMergeInput(cinSource));
	}

	@Test
	void verifyMergeInputNotFoundTest() {
		try {
			mergeService.verifyMergeInput("UnfoundInput");
			Assertions.fail("Should throw a BusinessException");
		} catch (ServiceException e) {
			// ok
		}
	}
}
