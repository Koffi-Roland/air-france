package com.afklm.cati.common.controllers.it;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.dozer.DozerBeanMapper;
import org.junit.Ignore;
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
import com.afklm.cati.common.spring.rest.controllers.RefPermissionsQuestionController;
import com.afklm.cati.common.entity.RefPermissionsQuestion;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class RefPermissionsQuestionControllerTest extends AbstractControllerTest {
	@Autowired
	DozerBeanMapper dozerBeanMapper;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RefPermissionsQuestionController refPermissionsQuestionController;
	
	@Override
	protected Object getController() {
		return refPermissionsQuestionController;
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodCollectionList() throws Exception {
		getMockMvc()
			.perform(get("/permissionsQuestion"))
			.andExpect(jsonPath("$", hasSize(3)))
			.andReturn();	

	}
	
	
	@Test
	@WithMockUser(value="user",username = "user",roles = "USER")
	public void testMethodRefPermissionsQuestionCollectionAddWithBadAuthority() throws Exception {
		String refPermissionsQuestionJson;
		RefPermissionsQuestion refPermissionsQuestion = new RefPermissionsQuestion();
		refPermissionsQuestion.setName("test");
		refPermissionsQuestion.setQuestion("test");
		refPermissionsQuestion.setQuestionEN("test");
		refPermissionsQuestionJson = mapper.writeValueAsString(refPermissionsQuestion);

		getMockMvc()
						.perform(post("/permissionsQuestion")
						.contentType("application/json; charset=utf-8")
						.content(refPermissionsQuestionJson))
						.andExpect(status().isUnauthorized())
						.andReturn();

	}
	

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPermissionsQuestionCollectionAdd() throws Exception {
		String refPermissionsQuestionJson;
		RefPermissionsQuestion refPermissionsQuestion = new RefPermissionsQuestion();
		refPermissionsQuestion.setName("test");
		refPermissionsQuestion.setQuestion("test");
		refPermissionsQuestion.setQuestionEN("test");
		
		Date date = new Date();
		refPermissionsQuestion.setDateCreation(date);
		refPermissionsQuestion.setDateModification(date);
		refPermissionsQuestion.setSignatureCreation("test");
		refPermissionsQuestion.setSignatureModification("test");
		refPermissionsQuestion.setSiteCreation("test");
		refPermissionsQuestion.setSiteModification("test");
		refPermissionsQuestionJson = mapper.writeValueAsString(refPermissionsQuestion);

		getMockMvc()
						.perform(post("/permissionsQuestion")
						.contentType("application/json; charset=utf-8")
						.content(refPermissionsQuestionJson))
						.andExpect(status().isCreated())
						.andReturn();

	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPermissionsQuestionGet() throws Exception {
		MvcResult result = getMockMvc()
								.perform(get("/permissionsQuestion/101"))
								.andExpect(status().isOk())
								.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		RefPermissionsQuestion refPermissionsQuestion = mapper.readValue(jsonContent, RefPermissionsQuestion.class);

		assertEquals(refPermissionsQuestion.getName(), "AIRLINES");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPermissionsQuestionGetNotExists() throws Exception {
		getMockMvc()
			.perform(get("/permissionsQuestion/10"))
			.andExpect(status().isNotFound())
			.andReturn();

	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPermissionsQuestionUpdate() throws Exception {
		
		Integer id = 101;

		String refPermissionsQuestionJson;
		RefPermissionsQuestion refPermissionsQuestion = new RefPermissionsQuestion();
		refPermissionsQuestion.setId(id);
		refPermissionsQuestion.setName("test");
		refPermissionsQuestion.setQuestion("testUpdate");
		refPermissionsQuestion.setQuestionEN("test");
		
		Date date = new Date();
		refPermissionsQuestion.setDateCreation(date);
		refPermissionsQuestion.setDateModification(date);
		refPermissionsQuestion.setSignatureCreation("test");
		refPermissionsQuestion.setSignatureModification("test");
		refPermissionsQuestion.setSiteCreation("test");
		refPermissionsQuestion.setSiteModification("test");
		refPermissionsQuestionJson = mapper.writeValueAsString(refPermissionsQuestion);
		
		MvcResult result = getMockMvc()
							.perform(put("/permissionsQuestion/{id}", id)
							.contentType("application/json; charset=utf-8")
							.content(refPermissionsQuestionJson))
							.andExpect(status().isOk())
							.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		RefPermissionsQuestion refPermissionsQuestionResponse = mapper.readValue(jsonContent, RefPermissionsQuestion.class);

		assertEquals(refPermissionsQuestionResponse.getQuestion(), "testUpdate");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPermissionsQuestionUpdateNotExists() throws Exception {

		Integer id = 10;

		String refPermissionsQuestionJson;
		RefPermissionsQuestion refPermissionsQuestion = new RefPermissionsQuestion();
		refPermissionsQuestion.setId(id);
		refPermissionsQuestion.setName("test");
		refPermissionsQuestion.setQuestion("testUpdate");
		refPermissionsQuestion.setQuestionEN("test");
		
		Date date = new Date();
		refPermissionsQuestion.setDateCreation(date);
		refPermissionsQuestion.setDateModification(date);
		refPermissionsQuestion.setSignatureCreation("test");
		refPermissionsQuestion.setSignatureModification("test");
		refPermissionsQuestion.setSiteCreation("test");
		refPermissionsQuestion.setSiteModification("test");
		refPermissionsQuestionJson = mapper.writeValueAsString(refPermissionsQuestion);
		
		getMockMvc()
					.perform(put("/permissionsQuestion/{id}", id)
					.contentType("application/json; charset=utf-8")
					.content(refPermissionsQuestionJson))
					.andExpect(status().isInternalServerError())
					.andReturn();
	}
	
	 /**
	 * REPIND-1238: No more useful as we cannot delete a permission anymore
	 * Button has been disabled in order to be sure to not delete a Permission and alter ID sequence
	 * 
	 * @throws Exception
	 */
	@Ignore
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPermissionsQuestionDelete() throws Exception {
		getMockMvc()
			.perform(delete("/permissionsQuestion/3"))
			.andExpect(status().isOk())
			.andReturn();
	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPermissionsQuestionDeleteNotExists() throws Exception {
		getMockMvc()
			.perform(delete("/permissionsQuestion/10"))
			.andExpect(status().isInternalServerError())
			.andReturn();
	}

}
