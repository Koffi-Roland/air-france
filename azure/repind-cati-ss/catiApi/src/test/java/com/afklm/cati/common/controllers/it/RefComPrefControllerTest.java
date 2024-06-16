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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.afklm.cati.common.springsecurity.SecurityTestConfiguration;
import com.afklm.cati.common.springsecurity.TestWebConfiguration;
import com.afklm.cati.common.spring.rest.controllers.RefComPrefController;
import com.afklm.cati.common.spring.rest.resources.RefComPrefResource;
import com.afklm.cati.common.entity.RefComPref;
import com.afklm.cati.common.entity.RefComPrefDomain;
import com.afklm.cati.common.entity.RefComPrefGType;
import com.afklm.cati.common.entity.RefComPrefMedia;
import com.afklm.cati.common.entity.RefComPrefType;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class RefComPrefControllerTest extends AbstractControllerTest {

	@Autowired
	DozerBeanMapper dozerBeanMapper;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RefComPrefController refComPrefController;
	
	@Override
	protected Object getController() {
		return refComPrefController;
	}


	@Test
	@WithMockUser( value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodCollectionList() throws Exception {
		getMockMvc()
			.perform(get("/communicationpreferences"))
			.andExpect(jsonPath("$", hasSize(3)))
			.andReturn();	

	}

	@Test
	@WithMockUser(value="user",username = "user",roles = "AK_USER")
	public void testMethodRefComPrefCollectionAddWithBadAuthority() throws Exception {
		Date date = new Date();
		RefComPrefResource refComPref = new RefComPrefResource();
		refComPref.setRefComprefId(0);
		refComPref.setDescription("Description");
		refComPref.setMandatoryOptin("Y");
		refComPref.setMarket("FR");
		refComPref.setDefaultLanguage1("FR");
		refComPref.setFieldA("Y");
		refComPref.setFieldN("N");
		refComPref.setFieldT("Y");
		refComPref.setSignatureModification("test");
		refComPref.setSiteModification("test");
		refComPref.setDateModification(date);
		refComPref.setSiteCreation("test");
		refComPref.setDateCreation(date);
		refComPref.setComGroupeType("N");
		refComPref.setComType("AF");
		refComPref.setDomain("S");
		refComPref.setMedia("E");

		mapper.writeValueAsString(refComPref);

		getMockMvc()
						.perform(post("/communicationpreferences")
						.contentType("application/json; charset=utf-8")
						.content( mapper.writeValueAsString(refComPref)))
						.andExpect(status().isUnauthorized())
						.andReturn();

	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefCollectionAdd() throws Exception {	
		Date date = new Date();
		RefComPrefResource refComPref = new RefComPrefResource();
		refComPref.setRefComprefId(0);
		refComPref.setDescription("Description");
		refComPref.setMandatoryOptin("Y");
		refComPref.setMarket("FR");
		refComPref.setDefaultLanguage1("FR");
		refComPref.setFieldA("Y");
		refComPref.setFieldN("N");
		refComPref.setFieldT("Y");
		refComPref.setSignatureModification("test");
		refComPref.setSiteModification("test");
		refComPref.setDateModification(date);
		refComPref.setSiteCreation("test");
		refComPref.setDateCreation(date);
		refComPref.setComGroupeType("N");
		refComPref.setComType("AF");
		refComPref.setDomain("S");
		refComPref.setMedia("E");

		getMockMvc()
						.perform(post("/communicationpreferences")
						.contentType("application/json; charset=utf-8")
						.content(mapper.writeValueAsString(refComPref)))
						.andExpect(status().isCreated())
						.andReturn();

	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefGet() throws Exception {
		MvcResult result = getMockMvc()
								.perform(get("/communicationpreferences/101"))
								.andExpect(status().isOk())
								.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		
		RefComPrefResource resource = mapper.readValue(jsonContent, RefComPrefResource.class);

		assertEquals(resource.getDescription(), "desc1");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefGetNotExists() throws Exception {
		getMockMvc()
			.perform(get("/communicationpreferences/10"))
			.andExpect(status().isNotFound())
			.andReturn();

	}


	@Test
	@WithMockUser( value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefUpdate() throws Exception {	
		Integer id = 101;
		Date date = new Date();
		RefComPrefResource refComPref = new RefComPrefResource();
		refComPref.setRefComprefId(1);
		refComPref.setDescription("Description");
		refComPref.setMandatoryOptin("Y");
		refComPref.setMarket("EN");
		refComPref.setDefaultLanguage1("FR");
		refComPref.setFieldA("Y");
		refComPref.setFieldN("N");
		refComPref.setFieldT("Y");
		refComPref.setSignatureModification("test");
		refComPref.setSiteModification("test");
		refComPref.setDateModification(date);
		refComPref.setSiteCreation("test");
		refComPref.setDateCreation(date);
		refComPref.setComGroupeType("N");
		refComPref.setComType("AF");
		refComPref.setDomain("S");
		refComPref.setMedia("E");
		
		MvcResult result = getMockMvc()
							.perform(put("/communicationpreferences/{id}", id)
							.contentType("application/json; charset=utf-8")
							.content(mapper.writeValueAsString(refComPref)))
							.andExpect(status().isOk())
							.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		RefComPrefResource refComPrefResponse = mapper.readValue(jsonContent, RefComPrefResource.class);

		assertEquals(refComPrefResponse.getMarket(), "EN");
		assertEquals(refComPrefResponse.getDateModification(), date);
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefUpdateNotExists() throws Exception {
		String refComPrefJson;
		RefComPrefGType pComGroupeType = new RefComPrefGType();
		pComGroupeType.setCodeGType("N");
		RefComPrefMedia pMedia = new RefComPrefMedia();
		pMedia.setCodeMedia("E");
		RefComPrefType pComType = new RefComPrefType();
		pComType.setCodeType("AF");
		RefComPrefDomain pDomain = new RefComPrefDomain();
		pDomain.setCodeDomain("S");
		
		Integer id = 10;
		RefComPref refComPref = new RefComPref();
		refComPref.setRefComprefId(id);
		refComPref.setMarket("EN");
		refComPref.setDefaultLanguage1("FR");
		refComPref.setDescription("Description");
		refComPref.setFieldA("Y");
		refComPref.setFieldN("Y");
		refComPref.setFieldT("Y");
		refComPref.setMandatoryOptin("Y");
		refComPref.setComGroupeType(pComGroupeType);
		refComPref.setComType(pComType);
		refComPref.setDomain(pDomain);
		refComPref.setMedia(pMedia);
		refComPrefJson = mapper.writeValueAsString(refComPref);

		getMockMvc()
					.perform(put("/communicationpreferences/{id}", id)
					.contentType("application/json; charset=utf-8")
					.content(refComPrefJson))
					.andExpect(status().isInternalServerError())
					.andReturn();
	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDelete() throws Exception {
		getMockMvc()
			.perform(delete("/communicationpreferences/102"))
			.andExpect(status().isOk())
			.andReturn();
	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDeleteNotExists() throws Exception {
		getMockMvc()
			.perform(delete("/communicationpreferences/10"))
			.andExpect(status().isInternalServerError())
			.andReturn();
	}
}
