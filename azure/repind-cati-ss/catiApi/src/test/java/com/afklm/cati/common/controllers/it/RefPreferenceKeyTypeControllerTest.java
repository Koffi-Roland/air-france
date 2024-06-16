package com.afklm.cati.common.controllers.it;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

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
import com.afklm.cati.common.spring.rest.controllers.RefPreferenceKeyTypeController;
import com.afklm.cati.common.spring.rest.resources.RefPreferenceKeyTypeResource;
import com.afklm.cati.common.entity.RefPreferenceKeyType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * RefPreferenceKeyTypeControllerTest.java
 * @author m430152
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class RefPreferenceKeyTypeControllerTest extends AbstractControllerTest {

	private static final String ENDPOINT = "/preferenceKeyTypes";
	private static final String CONTENT_TYPE = "application/json; charset=utf-8";

	@Autowired
	DozerBeanMapper dozerBeanMapper;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RefPreferenceKeyTypeController refPreferenceKeyTypeController;

	@Override
	protected Object getController() {
		return refPreferenceKeyTypeController;
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceKeyTypeCollectionList() throws Exception {
		getMockMvc().perform(get(ENDPOINT)).andExpect(jsonPath("$", hasSize(4))).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceKeyTypeCollectionAdd() throws Exception {
		String refPreferenceKeyTypeJson;
		RefPreferenceKeyTypeResource pKeyType = new RefPreferenceKeyTypeResource();
		pKeyType.setRefId(666);
		pKeyType.setKey("KLMHOUSEWISH1");
		pKeyType.setListPreferenceType(Collections.singletonList("GPC"));
		pKeyType.setMinLength(1);
		pKeyType.setMaxLength(99);
		pKeyType.setDataType("String");
		pKeyType.setCondition("M");
		

		refPreferenceKeyTypeJson = mapper.writeValueAsString(pKeyType);

		getMockMvc().perform(post(ENDPOINT).contentType(CONTENT_TYPE).content(refPreferenceKeyTypeJson))
				.andExpect(status().isCreated()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceKeyTypeGet() throws Exception {
		Integer targetRefId = 400;

		MvcResult mvcResult = getMockMvc().perform(get(ENDPOINT + "/" + targetRefId)).andExpect(status().isOk())
				.andReturn();

		String jsonResponse = mvcResult.getResponse().getContentAsString();
		RefPreferenceKeyType refPreferenceKeyType = mapper.readValue(jsonResponse, RefPreferenceKeyType.class);

		assertEquals(refPreferenceKeyType.getRefId(), targetRefId);
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceKeyTypeGetNotExists() throws Exception {
		 int targetRefId = 0;
		getMockMvc().perform(get(ENDPOINT + "/" + targetRefId)).andExpect(status().isNotFound()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceKeyTypeUpdate() throws Exception {
		String refPreferenceKeyTypeJson;

		RefPreferenceKeyTypeResource refPreferenceKeyType = new RefPreferenceKeyTypeResource();
		Integer refId = 400;
		refPreferenceKeyType.setRefId(refId);
		refPreferenceKeyType.setKey("KLMHOUSEWISH2");
		refPreferenceKeyType.setListPreferenceType(Collections.singletonList("TDC"));
		refPreferenceKeyType.setMinLength(1);
		refPreferenceKeyType.setMaxLength(200);
		refPreferenceKeyType.setDataType("String");
		refPreferenceKeyType.setCondition("O");
		
		refPreferenceKeyTypeJson = mapper.writeValueAsString(refPreferenceKeyType);

		 getMockMvc()
				.perform(put(ENDPOINT + "/{refId}", refId).contentType(CONTENT_TYPE).content(refPreferenceKeyTypeJson))
				.andExpect(status().isOk());

		 MvcResult mvcResult =  getMockMvc().perform(get(ENDPOINT + "/" + refId)).andExpect(status().isOk())
					.andReturn();
		 String jsonResponse = mvcResult.getResponse().getContentAsString();	
		RefPreferenceKeyType refPreferenceKeyTypeResponse = mapper.readValue(jsonResponse, RefPreferenceKeyType.class);

		assertEquals( "KLMHOUSEWISH2", refPreferenceKeyTypeResponse.getKey());
		assertEquals( "TDC", refPreferenceKeyTypeResponse.getType());
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceKeyTypeUpdateNotExists() throws Exception {
		String refPreferenceKeyTypeJson;
		
		RefPreferenceKeyTypeResource refPreferenceKeyType = new RefPreferenceKeyTypeResource();
		Integer refId = 0;
		refPreferenceKeyType.setRefId(refId);
		refPreferenceKeyType.setKey("KLMHOUSEWISH2");
		refPreferenceKeyType.setListPreferenceType(Collections.singletonList("TDC"));
		refPreferenceKeyType.setMinLength(1);
		refPreferenceKeyType.setMaxLength(200);
		refPreferenceKeyType.setDataType("String");
		refPreferenceKeyType.setCondition("O");

		refPreferenceKeyTypeJson = mapper.writeValueAsString(refPreferenceKeyType);

		getMockMvc().perform(put(ENDPOINT + "/{refId}", refId).contentType(CONTENT_TYPE).content(refPreferenceKeyTypeJson))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceKeyTypeDelete() throws Exception {
		getMockMvc().perform(delete(ENDPOINT + "/" + 400)).andExpect(status().isOk()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPreferenceTypeDeleteNotExists() throws Exception {
		getMockMvc().perform(delete(ENDPOINT + "/" + 0)).andExpect(status().isInternalServerError()).andReturn();
	}
}
