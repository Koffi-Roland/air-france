package com.afklm.cati.common.controllers.it;

import com.afklm.cati.common.springsecurity.SecurityTestConfiguration;
import com.afklm.cati.common.springsecurity.TestWebConfiguration;
import com.afklm.cati.common.spring.rest.controllers.RefPcsContractScoreController;
import com.afklm.cati.common.entity.RefPcsScore;
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
 * RefPcsContractScoreControllerTest.java
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class RefPcsContractScoreControllerTest extends AbstractControllerTest {

	private static final String ENDPOINT = "/PcsContractScore";
	private static final String CONTENT_TYPE = "application/json; charset=utf-8";

	@Autowired
	DozerBeanMapper dozerBeanMapper;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RefPcsContractScoreController refPcsContractScoreController;

	@Override
	protected Object getController() {
		return refPcsContractScoreController;
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPcsContractScoreCollectionList() throws Exception {
		getMockMvc().perform(get(ENDPOINT+'/')).andExpect(jsonPath("$", hasSize(2))).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPcsContractScoreCollectionAdd() throws Exception {
		String refPcsContractScoreJson;
		RefPcsScore refPcsScore = new RefPcsScore();
		refPcsScore.setCode("T");
		refPcsScore.setLibelle("Test");
		refPcsScore.setScore(0);

		refPcsContractScoreJson = mapper.writeValueAsString(refPcsScore);

		getMockMvc().perform(post(ENDPOINT+'/').contentType(CONTENT_TYPE).content(refPcsContractScoreJson))
				.andExpect(status().isCreated()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefrefPcsContractScoreUpdate() throws Exception {
		String refPcsContractScoreJson;

		RefPcsScore refPcsContractScore = new RefPcsScore();
		String code = "FP";
		refPcsContractScore.setCode(code);
		refPcsContractScore.setLibelle("Update test");

		refPcsContractScoreJson = mapper.writeValueAsString(refPcsContractScore);

		MvcResult mvcResult = getMockMvc()
				.perform(put(ENDPOINT + "/{code}", code).contentType(CONTENT_TYPE).content(refPcsContractScoreJson))
				.andExpect(status().isOk()).andReturn();

		String jsonResponse = mvcResult.getResponse().getContentAsString();
		RefPcsScore refPcsContractScoreResponse = mapper.readValue(jsonResponse, RefPcsScore.class);

		assertEquals( "Update test", refPcsContractScoreResponse.getLibelle());
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPcsContractScoreUpdateNotExists() throws Exception {
		String refPcsContractScoreJson;

		RefPcsScore refPcsContractScore = new RefPcsScore();

		String code = "NOTEXIST";
		refPcsContractScore.setCode(code);
		refPcsContractScore.setLibelle("Test de mise à jour");
		refPcsContractScore.setScore(0);

		refPcsContractScoreJson = mapper.writeValueAsString(refPcsContractScore);

		getMockMvc().perform(put(ENDPOINT + "/{code}", code).contentType(CONTENT_TYPE).content(refPcsContractScoreJson))
				.andExpect(status().isInternalServerError()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPcsContractScoreDelete() throws Exception {
		getMockMvc().perform(delete(ENDPOINT + "/MA")).andExpect(status().isOk()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPcsContractScoreDeleteNotExists() throws Exception {
		getMockMvc().perform(delete(ENDPOINT + "/NOTEXIST")).andExpect(status().isInternalServerError()).andReturn();
	}
}
