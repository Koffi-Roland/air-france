package com.afklm.cati.common.controllers.it;

import com.afklm.cati.common.springsecurity.SecurityTestConfiguration;
import com.afklm.cati.common.springsecurity.TestWebConfiguration;
import com.afklm.cati.common.spring.rest.controllers.RefPcsNonContractScoreController;
import com.afklm.cati.common.entity.RefPcsScore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
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
 * RefPcsNonContractScoreControllerTest.java
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class RefPcsNonContractScoreControllerTest extends AbstractControllerTest {

	private static final String ENDPOINT = "/PcsNonContractScore";
	private static final String CONTENT_TYPE = "application/json; charset=utf-8";

	@Autowired
	DozerBeanMapper dozerBeanMapper;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RefPcsNonContractScoreController refPcsNonContractScoreController;

	@Override
	protected Object getController() {
		return refPcsNonContractScoreController;
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPcsNonContractScoreCollectionList() throws Exception {
		getMockMvc().perform(get(ENDPOINT+'/')).andExpect(jsonPath("$", hasSize(2))).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPcsNonContractScoreCollectionAdd() throws Exception {
		String refPcsNonContractScoreJson;
		RefPcsScore refPcsScore = new RefPcsScore();
		refPcsScore.setCode("T");
		refPcsScore.setLibelle("Test");
		refPcsScore.setScore(0);

		refPcsNonContractScoreJson = mapper.writeValueAsString(refPcsScore);

		getMockMvc().perform(post(ENDPOINT+'/').contentType(CONTENT_TYPE).content(refPcsNonContractScoreJson))
				.andExpect(status().isCreated()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefrefPcsNonContractScoreUpdate() throws Exception {
		String refPcsNonContractScoreJson;

		RefPcsScore refPcsNonContractScore = new RefPcsScore();
		String code = "FN";
		refPcsNonContractScore.setCode(code);
		refPcsNonContractScore.setLibelle("Update test");

		refPcsNonContractScoreJson = mapper.writeValueAsString(refPcsNonContractScore);

		MvcResult mvcResult = getMockMvc()
				.perform(put(ENDPOINT + "/{code}", code).contentType(CONTENT_TYPE).content(refPcsNonContractScoreJson))
				.andExpect(status().isOk()).andReturn();

		String jsonResponse = mvcResult.getResponse().getContentAsString();
		RefPcsScore refPcsNonContractScoreResponse = mapper.readValue(jsonResponse, RefPcsScore.class);

		assertEquals( "Update test", refPcsNonContractScoreResponse.getLibelle());
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPcsNonContractScoreUpdateNotExists() throws Exception {
		String refPcsNonContractScoreJson;

		RefPcsScore refPcsNonContractScore = new RefPcsScore();

		String code = "NOTEXIST";
		refPcsNonContractScore.setCode(code);
		refPcsNonContractScore.setLibelle("Test de mise à jour");
		refPcsNonContractScore.setScore(0);

		refPcsNonContractScoreJson = mapper.writeValueAsString(refPcsNonContractScore);

		getMockMvc().perform(put(ENDPOINT + "/{code}", code).contentType(CONTENT_TYPE).content(refPcsNonContractScoreJson))
				.andExpect(status().isInternalServerError()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPcsNonContractScoreDelete() throws Exception {
		getMockMvc().perform(delete(ENDPOINT + "/LN")).andExpect(status().isOk()).andReturn();
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPcsNonContractScoreDeleteNotExists() throws Exception {
		getMockMvc().perform(delete(ENDPOINT + "/NOTEXIST")).andExpect(status().isInternalServerError()).andReturn();
	}
}
