package com.afklm.cati.common.controllers.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.cati.common.springsecurity.SecurityTestConfiguration;
import com.afklm.cati.common.springsecurity.TestWebConfiguration;
import com.afklm.cati.common.spring.rest.controllers.SecurityController;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class SecurityControllerTest extends AbstractControllerTest {
	@Autowired
	DozerBeanMapper dozerBeanMapper;

	@Autowired
	SecurityController securityController;
	
	@Override
	protected Object getController() {
		return securityController;
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodSecurityGetProfilesWithoutAuthenticationPrincipal() throws Exception {
		getMockMvc()
			.perform(get("/securities"))
			.andExpect(status().isInternalServerError())
			.andReturn();	

	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodSecurityGetUser() throws Exception {
		getMockMvc()
			.perform(get("/securities/user"))
			.andExpect(status().isOk())
			.andReturn();	

	}
	
}
