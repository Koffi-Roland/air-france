package com.afklm.cati.common.controllers.it;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.cati.common.springsecurity.SecurityTestConfiguration;
import com.afklm.cati.common.springsecurity.TestWebConfiguration;
import com.afklm.cati.common.spring.rest.controllers.VariablesController;
import com.afklm.cati.common.entity.Variables;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class VariablesControllerTest extends AbstractControllerTest {
	
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	VariablesController variablesController;
	
	@Override
	protected Object getController() {
		return variablesController;
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN")
	public void testMethodVariablesList() throws Exception {
		getMockMvc()
			.perform(get("/variables"))
			.andExpect(jsonPath("$", hasSize(3)))
			.andReturn();
	}
	
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "USER")
	public void testMethodVariablesCreateWithBadAuthority() throws Exception {
		Variables variables = new Variables();
		variables.setEnvKey("BAD_USER");
		variables.setEnvValue("REJECTED");
		
		String variablesJson = mapper.writeValueAsString(variables);

		getMockMvc()
			.perform(post("/variables")
			.contentType("application/json; charset=utf-8")
			.content(variablesJson))
			.andExpect(status().isUnauthorized())
			.andReturn();
	}
	

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN")
	public void testMethodVariablesAdd() throws Exception {
		Variables variables = new Variables();
		variables.setEnvKey("NEW_KEY");
		variables.setEnvValue("NEW_VALUE");
		
		String variablesJson = mapper.writeValueAsString(variables);

		getMockMvc()
			.perform(post("/variables")
			.contentType("application/json; charset=utf-8")
			.content(variablesJson))
			.andExpect(status().isCreated())
			.andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN")
	public void testMethodVariablesGet() throws Exception {
		MvcResult result = getMockMvc()
								.perform(get("/variables/TEST_1_1"))
								.andExpect(status().isOk())
								.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		Variables variables = mapper.readValue(jsonContent, Variables.class);

		assertEquals(variables.getEnvValue(), "1095");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN")
	public void testMethodVariablesGetNotExists() throws Exception {
		getMockMvc()
			.perform(get("/variables/NOT_EXIST"))
			.andExpect(status().isNotFound())
			.andReturn();
	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN")
	public void testMethodVariablesUpdate() throws Exception {
		Variables variables = new Variables();
		variables.setEnvKey("TEST_1_1");
		variables.setEnvValue("3000");
		
		String variablesJson = mapper.writeValueAsString(variables);
		
		MvcResult result = getMockMvc()
							.perform(put("/variables/{key}", "TEST_1_1")
							.contentType("application/json; charset=utf-8")
							.content(variablesJson))
							.andExpect(status().isOk())
							.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		Variables variablesResponse = mapper.readValue(jsonContent, Variables.class);

		assertEquals(variablesResponse.getEnvKey(), "TEST_1_1");
		assertEquals(variablesResponse.getEnvValue(), "3000");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN")
	public void testMethodVariablesUpdateNotExists() throws Exception {
		Variables variables = new Variables();
		variables.setEnvKey("PROVIDECUSTOMER360");
		variables.setEnvValue("3000");
		
		String variablesJson = mapper.writeValueAsString(variables);
		
		getMockMvc()
			.perform(put("/variables/{key}", "PROVIDECUSTOMER360")
			.contentType("application/json; charset=utf-8")
			.content(variablesJson))
			.andExpect(status().isInternalServerError())
			.andReturn();
	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN")
	public void testMethodVariablesDelete() throws Exception {
		getMockMvc()
			.perform(delete("/variables/TEST_1_1"))
			.andExpect(status().isOk())
			.andReturn();
	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN")
	public void testMethodVariablesDeleteNotExists() throws Exception {
		getMockMvc()
			.perform(delete("/variables/PROVIDECUSTOMER360"))
			.andExpect(status().isInternalServerError())
			.andReturn();
	}
}
