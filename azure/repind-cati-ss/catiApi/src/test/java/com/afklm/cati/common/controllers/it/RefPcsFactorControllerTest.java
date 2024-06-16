package com.afklm.cati.common.controllers.it;

import com.afklm.cati.common.springsecurity.SecurityTestConfiguration;
import com.afklm.cati.common.springsecurity.TestWebConfiguration;
import com.afklm.cati.common.spring.rest.controllers.RefPcsFactorController;
import com.afklm.cati.common.entity.RefPcsFactor;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * RefPcsFactorControllerTest.java
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class RefPcsFactorControllerTest extends AbstractControllerTest {

	private static final String ENDPOINT = "/PcsFactor";
	private static final String CONTENT_TYPE = "application/json; charset=utf-8";

	@Autowired
	DozerBeanMapper dozerBeanMapper;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RefPcsFactorController refPcsFactorController;

	@Override
	protected Object getController() {
		return refPcsFactorController;
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPcsFactorCollectionList() throws Exception {
		getMockMvc().perform(get(ENDPOINT+'/')).andExpect(jsonPath("$", hasSize(2))).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPcsFactorCollectionAdd() throws Exception {
		String refPcsFactorJson;
		RefPcsFactor refPcsFactor = new RefPcsFactor();
		refPcsFactor.setCode("T");
		refPcsFactor.setLibelle("Test");
		refPcsFactor.setFactor(0);

		refPcsFactorJson = mapper.writeValueAsString(refPcsFactor);

		getMockMvc().perform(post(ENDPOINT+'/').contentType(CONTENT_TYPE).content(refPcsFactorJson))
				.andExpect(status().isCreated()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefrefPcsFactorUpdate() throws Exception {
		String refrefPcsFactorJson;

		RefPcsFactor refPcsFactor = new RefPcsFactor();
		String code = "C";
		refPcsFactor.setCode(code);
		refPcsFactor.setLibelle("Update test");

		refrefPcsFactorJson = mapper.writeValueAsString(refPcsFactor);

		MvcResult mvcResult = getMockMvc()
				.perform(put(ENDPOINT + "/{code}", code).contentType(CONTENT_TYPE).content(refrefPcsFactorJson))
				.andExpect(status().isOk()).andReturn();

		String jsonResponse = mvcResult.getResponse().getContentAsString();
		RefPcsFactor refPcsFactorResponse = mapper.readValue(jsonResponse, RefPcsFactor.class);

		assertEquals( "Update test", refPcsFactorResponse.getLibelle());
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPcsFactorUpdateNotExists() throws Exception {
		String refPcsFactorJson;

		RefPcsFactor refPcsFactor = new RefPcsFactor();

		String code = "NOTEXIST";
		refPcsFactor.setCode(code);
		refPcsFactor.setLibelle("Test de mise à jour");
		refPcsFactor.setFactor(0);

		refPcsFactorJson = mapper.writeValueAsString(refPcsFactor);

		getMockMvc().perform(put(ENDPOINT + "/{code}", code).contentType(CONTENT_TYPE).content(refPcsFactorJson))
				.andExpect(status().isInternalServerError()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPcsFactorDelete() throws Exception {
		String code = "NC";
		getMockMvc().perform(delete(ENDPOINT + "/NC")).andExpect(status().isOk()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPcsFactorDeleteNotExists() throws Exception {
		getMockMvc().perform(delete(ENDPOINT + "/NOTEXIST")).andExpect(status().isInternalServerError()).andReturn();
	}
}
