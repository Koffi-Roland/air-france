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
import com.afklm.cati.common.spring.rest.controllers.RefPreferenceTypeController;
import com.afklm.cati.common.entity.RefPreferenceType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * RefPreferenceTypeControllerTest.java
 * @author m430152
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class RefPreferenceTypeControllerTest extends AbstractControllerTest {

	private static final String ENDPOINT = "/preferenceTypes";
	private static final String CONTENT_TYPE = "application/json; charset=utf-8";

	@Autowired
	DozerBeanMapper dozerBeanMapper;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RefPreferenceTypeController refPreferenceTypeController;

	@Override
	protected Object getController() {
		return refPreferenceTypeController;
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceTypeCollectionList() throws Exception {
		getMockMvc().perform(get(ENDPOINT)).andExpect(jsonPath("$", hasSize(4))).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceTypeCollectionAdd() throws Exception {
		String refPreferenceTypeJson;
		RefPreferenceType pType = new RefPreferenceType();
		pType.setCode("T");
		pType.setLibelleFR("Test");
		pType.setLibelleEN("Test");

		refPreferenceTypeJson = mapper.writeValueAsString(pType);

		getMockMvc().perform(post(ENDPOINT).contentType(CONTENT_TYPE).content(refPreferenceTypeJson))
				.andExpect(status().isCreated()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceTypeGet() throws Exception {
		String targetCode = "GPC";

		MvcResult mvcResult = getMockMvc().perform(get(ENDPOINT + "/" + targetCode)).andExpect(status().isOk())
				.andReturn();

		String jsonResponse = mvcResult.getResponse().getContentAsString();
		RefPreferenceType refPreferenceType = mapper.readValue(jsonResponse, RefPreferenceType.class);

		assertEquals(refPreferenceType.getCode(), targetCode);
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceTypeGetNotExists() throws Exception {
		String targetCode = "AAA";
		getMockMvc().perform(get(ENDPOINT + "/" + targetCode)).andExpect(status().isNotFound()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceTypeUpdate() throws Exception {
		String refPreferenceTypeJson;

		RefPreferenceType refPreferenceType = new RefPreferenceType();
		String code = "GPC";
		refPreferenceType.setCode(code);
		refPreferenceType.setLibelleEN("Update test");

		refPreferenceTypeJson = mapper.writeValueAsString(refPreferenceType);

		MvcResult mvcResult = getMockMvc()
				.perform(put(ENDPOINT + "/{code}", code).contentType(CONTENT_TYPE).content(refPreferenceTypeJson))
				.andExpect(status().isOk()).andReturn();

		String jsonResponse = mvcResult.getResponse().getContentAsString();
		RefPreferenceType refPreferenceTypeResponse = mapper.readValue(jsonResponse, RefPreferenceType.class);

		assertEquals( "Update test", refPreferenceTypeResponse.getLibelleEN());
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceTypeUpdateNotExists() throws Exception {
		String refPreferenceTypeJson;

		RefPreferenceType refPreferenceType = new RefPreferenceType();

		String code = "NOTEXIST";
		refPreferenceType.setCode(code);
		refPreferenceType.setLibelleFR("Test de mise à jour");
		refPreferenceType.setLibelleEN("Update test");

		refPreferenceTypeJson = mapper.writeValueAsString(refPreferenceType);

		getMockMvc().perform(put(ENDPOINT + "/{code}", code).contentType(CONTENT_TYPE).content(refPreferenceTypeJson))
				.andExpect(status().isInternalServerError()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceTypeDelete() throws Exception {
		getMockMvc().perform(delete(ENDPOINT + "/ECC")).andExpect(status().isOk()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceTypeDeleteWithDependencies() throws Exception {
		getMockMvc().perform(delete(ENDPOINT + "/GPC")).andExpect(status().isLocked()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceTypeDeleteNotExists() throws Exception {
		getMockMvc().perform(delete(ENDPOINT + "/NOTEXIST")).andExpect(status().isInternalServerError()).andReturn();
	}
}
