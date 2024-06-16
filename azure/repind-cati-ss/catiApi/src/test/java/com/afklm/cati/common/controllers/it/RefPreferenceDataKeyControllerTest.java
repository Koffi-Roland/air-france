package com.afklm.cati.common.controllers.it;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.dozer.DozerBeanMapper;
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
import com.afklm.cati.common.spring.rest.controllers.RefPreferenceDataKeyController;
import com.afklm.cati.common.entity.RefPreferenceDataKey;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * RefPreferenceDataKeyControllerTest.java
 * @author m430152
 */

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class RefPreferenceDataKeyControllerTest extends AbstractControllerTest {

	private static final String ENDPOINT = "/preferenceDataKeys";
	private static final String CONTENT_TYPE = "application/json; charset=utf-8";

	@Autowired
	DozerBeanMapper dozerBeanMapper;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RefPreferenceDataKeyController refPreferenceDataKeyController;

	@Override
	protected Object getController() {
		return refPreferenceDataKeyController;
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceDataKeyCollectionList() throws Exception {
		getMockMvc().perform(get(ENDPOINT)).andExpect(jsonPath("$", hasSize(4))).andReturn();
	}


	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceDataKeyCollectionAdd() throws Exception {
		String refPreferenceDataKeyJson;
		
		RefPreferenceDataKey pDataKey = new RefPreferenceDataKey();
		pDataKey.setCode("T");
		pDataKey.setLibelleFr("Test");
		pDataKey.setLibelleEn("Test");
		
		refPreferenceDataKeyJson = mapper.writeValueAsString(pDataKey);

		getMockMvc().perform(post(ENDPOINT).contentType(CONTENT_TYPE).content(refPreferenceDataKeyJson))
				.andExpect(status().isCreated()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceDataKeyGet() throws Exception {
		String targetCode = "KLMHOUSEWISH1";

		MvcResult mvcResult = getMockMvc().perform(get(ENDPOINT + "/" + targetCode)).andExpect(status().isOk())
				.andReturn();

		String jsonResponse = mvcResult.getResponse().getContentAsString();
		RefPreferenceDataKey refPreferenceDataKey = mapper.readValue(jsonResponse, RefPreferenceDataKey.class);

		assertEquals(refPreferenceDataKey.getCode(), targetCode);
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceTypeGetNotExists() throws Exception {
		String targetCode = "AAA";
		getMockMvc().perform(get(ENDPOINT + "/" + targetCode)).andExpect(status().isNotFound()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceDataKeyUpdate() throws Exception {
		String refPreferenceDataKeyJson;

		RefPreferenceDataKey refPreferenceDataKey = new RefPreferenceDataKey();
		String code = "KLMHOUSEWISH1";
		refPreferenceDataKey.setCode(code);
		refPreferenceDataKey.setLibelleEn("Update test");

		refPreferenceDataKeyJson = mapper.writeValueAsString(refPreferenceDataKey);

		MvcResult mvcResult = getMockMvc()
				.perform(put(ENDPOINT + "/{code}", code).contentType(CONTENT_TYPE).content(refPreferenceDataKeyJson))
				.andExpect(status().isOk()).andReturn();

		String jsonResponse = mvcResult.getResponse().getContentAsString();
		RefPreferenceDataKey refPreferenceDataKeyResponse = mapper.readValue(jsonResponse, RefPreferenceDataKey.class);

		assertEquals(refPreferenceDataKeyResponse.getLibelleEn(), "Update test");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceDataKeyUpdateNotExists() throws Exception {
		String refPreferenceDataKeyJson;

		RefPreferenceDataKey refPreferenceDataKey = new RefPreferenceDataKey();
		String code = "NOTEXIST";
		refPreferenceDataKey.setCode(code);
		refPreferenceDataKey.setLibelleEn("Update test");

		refPreferenceDataKeyJson = mapper.writeValueAsString(refPreferenceDataKey);

		getMockMvc().perform(put(ENDPOINT + "/{code}", code).contentType(CONTENT_TYPE).content(refPreferenceDataKeyJson))
				.andExpect(status().isInternalServerError()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceDataKeyDelete() throws Exception {
		getMockMvc().perform(delete(ENDPOINT + "/KLMHOUSEWISH3")).andExpect(status().isOk()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceDataKeyDeleteWithDependencies() throws Exception {
		getMockMvc().perform(delete(ENDPOINT + "/KLMHOUSEWISH1")).andExpect(status().isLocked()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceTypeDeleteNotExists() throws Exception {
		getMockMvc().perform(delete(ENDPOINT + "/NOTEXIST")).andExpect(status().isInternalServerError()).andReturn();
	}
}
