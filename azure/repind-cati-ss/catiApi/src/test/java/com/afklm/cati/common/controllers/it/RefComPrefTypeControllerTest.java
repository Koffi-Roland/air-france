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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.afklm.cati.common.springsecurity.SecurityTestConfiguration;
import com.afklm.cati.common.springsecurity.TestWebConfiguration;
import com.afklm.cati.common.spring.rest.controllers.RefComPrefTypeController;
import com.afklm.cati.common.entity.RefComPrefType;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class RefComPrefTypeControllerTest extends AbstractControllerTest {
	@Autowired
	DozerBeanMapper dozerBeanMapper;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RefComPrefTypeController refComPrefTypeController;
	
	@Override
	protected Object getController() {
		return refComPrefTypeController;
	}

	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefTypeCollectionList() throws Exception {
		getMockMvc()
			.perform(get("/communicationtypes"))
			.andExpect(jsonPath("$", hasSize(4)))
			.andReturn();	

	}
	
	
	@Test
	@WithMockUser(value="user",username = "user",roles = "USER")
	public void testMethodRefComPrefTypeCollectionAddWithBadAuthority() throws Exception {
		String refComPrefTypeJson;
		RefComPrefType pType = new RefComPrefType();
		pType.setCodeType("AF");
		pType.setLibelleType("Air France Newsletter");
		pType.setLibelleTypeEN("Newsletter Air France");
		
		refComPrefTypeJson = mapper.writeValueAsString(pType);

		getMockMvc()
						.perform(post("/communicationtypes")
						.contentType("application/json; charset=utf-8")
						.content(refComPrefTypeJson))
						.andExpect(status().isUnauthorized())
						.andReturn();

	}
	

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefTypeCollectionAdd() throws Exception {

		String refComPrefTypeJson;
		RefComPrefType pType = new RefComPrefType();
		pType.setCodeType("AF");
		pType.setLibelleType("Air France Newsletter");
		pType.setLibelleTypeEN("Newsletter Air France");
		
		Date date = new Date();
		pType.setDateCreation(date);
		pType.setDateModification(date);
		pType.setSignatureCreation("test");
		pType.setSignatureModification("test");
		pType.setSiteCreation("test");
		pType.setSiteModification("test");
		
		refComPrefTypeJson = mapper.writeValueAsString(pType);

		getMockMvc()
						.perform(post("/communicationtypes")
						.contentType("application/json; charset=utf-8")
						.content(refComPrefTypeJson))
						.andExpect(status().isCreated())
						.andReturn();

	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefTypeGet() throws Exception {
		MvcResult result = getMockMvc()
								.perform(get("/communicationtypes/AF"))
								.andExpect(status().isOk())
								.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		RefComPrefType refComPrefType = mapper.readValue(jsonContent, RefComPrefType.class);

		assertEquals(refComPrefType.getCodeType(), "AF");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefTypeGetNotExists() throws Exception {
		getMockMvc()
			.perform(get("/communicationtypes/V"))
			.andExpect(status().isNotFound())
			.andReturn();

	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefTypeUpdate() throws Exception {
		String refComPrefTypeJson;
		RefComPrefType pType = new RefComPrefType();
		
		String code = "AF";
		pType.setCodeType(code);
		pType.setLibelleType("Air France Newsletter");
		pType.setLibelleTypeEN("Newsletter Air France");
		
		Date date = new Date();
		pType.setDateModification(date);
		pType.setSignatureModification("test");
		pType.setSiteModification("test");
		
		refComPrefTypeJson = mapper.writeValueAsString(pType);
		
		MvcResult result = getMockMvc()
							.perform(put("/communicationtypes/{code}", code)
							.contentType("application/json; charset=utf-8")
							.content(refComPrefTypeJson))
							.andExpect(status().isOk())
							.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		RefComPrefType refComPrefTypeResponse = mapper.readValue(jsonContent, RefComPrefType.class);
		assertThat(refComPrefTypeResponse).isNotNull();
		assertEquals(refComPrefTypeResponse.getLibelleType(), "Air France Newsletter");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefTypeUpdateNotExists() throws Exception {
		String refComPrefTypeJson;
		RefComPrefType pType = new RefComPrefType();
		
		String code = "AFTEST";
		pType.setCodeType(code);
		pType.setLibelleType("Air France Newsletter");
		pType.setLibelleTypeEN("Newsletter Air France");
		
		refComPrefTypeJson = mapper.writeValueAsString(pType);
		
		getMockMvc()
						.perform(put("/communicationtypes/{code}", code)
						.contentType("application/json; charset=utf-8")
						.content(refComPrefTypeJson))
						.andExpect(status().isInternalServerError())
						.andReturn();
	}
	
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefTypeDelete() throws Exception {
		getMockMvc()
			.perform(delete("/communicationtypes/FB_PART"))
			.andExpect(status().isOk())
			.andReturn();
	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefTypeDeleteNotExists() throws Exception {
		getMockMvc()
			.perform(delete("/communicationtypes/AFTEST"))
			.andExpect(status().isInternalServerError())
			.andReturn();
	}
}
