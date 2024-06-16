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

import static org.assertj.core.api.Assertions.assertThat;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.afklm.cati.common.springsecurity.SecurityTestConfiguration;
import com.afklm.cati.common.springsecurity.TestWebConfiguration;
import com.afklm.cati.common.spring.rest.controllers.RefComPrefGTypeController;
import com.afklm.cati.common.entity.RefComPrefGType;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class RefComPrefGTypeControllerTest extends AbstractControllerTest {
	@Autowired
	DozerBeanMapper dozerBeanMapper;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RefComPrefGTypeController refComPrefGTypeController;
	
	@Override
	protected Object getController() {
		return refComPrefGTypeController;
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefGTypeCollectionList() throws Exception {
		getMockMvc()
			.perform(get("/grouptypes"))
			.andExpect(jsonPath("$", hasSize(3)))
			.andReturn();	

	}
	
	
	@Test
	@WithMockUser(value="user",username = "user",roles = "USER")
	public void testMethodRefComPrefGTypeCollectionAddWithBadAuthority() throws Exception {
		String refComPrefGTypeJson;
		RefComPrefGType pGType = new RefComPrefGType();
		pGType.setCodeGType("AF");
		pGType.setLibelleGType("Air France Newsletter");
		pGType.setLibelleGTypeEN("Newsletter Air France");
		
		refComPrefGTypeJson = mapper.writeValueAsString(pGType);

		getMockMvc()
						.perform(post("/grouptypes")
						.contentType("application/json; charset=utf-8")
						.content(refComPrefGTypeJson))
						.andExpect(status().isUnauthorized())
						.andReturn();

	}
	

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefGTypeCollectionAdd() throws Exception {

		String refComPrefGTypeJson;
		RefComPrefGType pGType = new RefComPrefGType();
		pGType.setCodeGType("S");
		pGType.setLibelleGType("Service");
		pGType.setLibelleGTypeEN("Service");
		
		Date date = new Date();
		pGType.setDateCreation(date);
		pGType.setDateModification(date);
		pGType.setSignatureCreation("test");
		pGType.setSignatureModification("test");
		pGType.setSiteCreation("test");
		pGType.setSiteModification("test");
		
		refComPrefGTypeJson = mapper.writeValueAsString(pGType);

		getMockMvc()
						.perform(post("/grouptypes")
						.contentType("application/json; charset=utf-8")
						.content(refComPrefGTypeJson))
						.andExpect(status().isCreated())
						.andReturn();

	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefGTypeGet() throws Exception {
		MvcResult result = getMockMvc()
								.perform(get("/grouptypes/N"))
								.andExpect(status().isOk())
								.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		RefComPrefGType refComPrefGType = mapper.readValue(jsonContent, RefComPrefGType.class);
		assertEquals(refComPrefGType.getCodeGType(), "N");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefGTypeGetNotExists() throws Exception {
		getMockMvc()
			.perform(get("/grouptypes/V"))
			.andExpect(status().isNotFound())
			.andReturn();

	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefGTypeUpdate() throws Exception {
		String refComPrefGTypeJson;
		RefComPrefGType pGType = new RefComPrefGType();
		
		String code = "N";
		pGType.setCodeGType(code);
		pGType.setLibelleGType("Newsletter test");
		pGType.setLibelleGTypeEN("Newsletter test");
		
		Date date = new Date();
		pGType.setDateModification(date);
		pGType.setSignatureModification("test");
		pGType.setSiteModification("test");
		
		refComPrefGTypeJson = mapper.writeValueAsString(pGType);
		
		MvcResult result = getMockMvc()
							.perform(put("/grouptypes/{code}", code)
							.contentType("application/json; charset=utf-8")
							.content(refComPrefGTypeJson))
							.andExpect(status().isOk())
							.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		RefComPrefGType refComPrefGTypeResponse = mapper.readValue(jsonContent, RefComPrefGType.class);
		assertThat(refComPrefGTypeResponse).isNotNull();
		assertEquals(refComPrefGTypeResponse.getLibelleGType(), "Newsletter test");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefGTypeUpdateNotExists() throws Exception {
		String refComPrefGTypeJson;
		RefComPrefGType pGType = new RefComPrefGType();
		
		String code = "NTEST";
		pGType.setCodeGType(code);
		
		refComPrefGTypeJson = mapper.writeValueAsString(pGType);
		
		getMockMvc()
						.perform(put("/grouptypes/{code}", code)
						.contentType("application/json; charset=utf-8")
						.content(refComPrefGTypeJson))
						.andExpect(status().isInternalServerError())
						.andReturn();
	}
	
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefGTypeDelete() throws Exception {
		getMockMvc()
			.perform(delete("/grouptypes/C"))
			.andExpect(status().isOk())
			.andReturn();
	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefGTypeDeleteNotExists() throws Exception {
		getMockMvc()
			.perform(delete("/grouptypes/NTEST"))
			.andExpect(status().isInternalServerError())
			.andReturn();
	}
}
