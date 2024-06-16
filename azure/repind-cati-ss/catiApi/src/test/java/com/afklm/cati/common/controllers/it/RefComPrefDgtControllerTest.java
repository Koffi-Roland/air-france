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
import com.afklm.cati.common.spring.rest.controllers.RefComPrefDgtController;
import com.afklm.cati.common.spring.rest.resources.RefComPrefDgtResource;
import com.afklm.cati.common.entity.RefComPrefDgt;
import com.afklm.cati.common.entity.RefComPrefDomain;
import com.afklm.cati.common.entity.RefComPrefGType;
import com.afklm.cati.common.entity.RefComPrefType;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class RefComPrefDgtControllerTest extends AbstractControllerTest {
	@Autowired
	DozerBeanMapper dozerBeanMapper;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RefComPrefDgtController refComPrefDgtController;
	
	@Override
	protected Object getController() {
		return refComPrefDgtController;
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodCollectionList() throws Exception {
		getMockMvc()
			.perform(get("/communicationpreferencesdgt"))
			.andExpect(jsonPath("$", hasSize(3)))
			.andReturn();	

	}
	
	
	@Test
	@WithMockUser(value="user",username = "user",roles = "USER")
	public void testMethodRefComPrefDgtCollectionAddWithBadAuthority() throws Exception {
		
		RefComPrefDgtResource resource = new RefComPrefDgtResource();
		resource.setDomain("S");
		resource.setGroupType("N");
		resource.setType("AF");

		getMockMvc()
						.perform(post("/communicationpreferencesdgt")
						.contentType("application/json; charset=utf-8")
						.content(mapper.writeValueAsString(resource)))
						.andExpect(status().isUnauthorized())
						.andReturn();

	}
	

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDgtCollectionAdd() throws Exception {
		String refComPrefJson;
		
		RefComPrefDgtResource refComPrefDgt = new RefComPrefDgtResource();
		refComPrefDgt.setDescription("test");
		refComPrefDgt.setGroupType("N");
		refComPrefDgt.setType("AF");
		refComPrefDgt.setDomain("S");
		
		Date date = new Date();
		refComPrefDgt.setDateCreation(date);
		refComPrefDgt.setDateModification(date);
		refComPrefDgt.setSignatureCreation("test");
		refComPrefDgt.setSignatureModification("test");
		refComPrefDgt.setSiteCreation("test");
		refComPrefDgt.setSiteModification("test");
		refComPrefJson = mapper.writeValueAsString(refComPrefDgt);

		getMockMvc()
						.perform(post("/communicationpreferencesdgt")
						.contentType("application/json; charset=utf-8")
						.content(refComPrefJson))
						.andExpect(status().isCreated())
						.andReturn();

	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDgtGet() throws Exception {
		MvcResult result = getMockMvc()
								.perform(get("/communicationpreferencesdgt/101"))
								.andExpect(status().isOk())
								.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		RefComPrefDgtResource refComPrefDgt = mapper.readValue(jsonContent, RefComPrefDgtResource.class);

		assertEquals(refComPrefDgt.getDescription(), "desc1");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDgtGetNotExists() throws Exception {
		getMockMvc()
			.perform(get("/communicationpreferencesdgt/10"))
			.andExpect(status().isNotFound())
			.andReturn();

	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDgtUpdate() throws Exception {
		String refComPrefDgtJson;
		
		Integer id = 101;
		RefComPrefDgtResource refComPrefDgt = new RefComPrefDgtResource();
		refComPrefDgt.setRefComPrefDgtId(id);
		refComPrefDgt.setDescription("test");
		refComPrefDgt.setGroupType("N");
		refComPrefDgt.setType("KL");
		refComPrefDgt.setDomain("S");
		
		Date date = new Date();
		refComPrefDgt.setDateModification(date);
		refComPrefDgt.setSignatureModification("test");
		refComPrefDgt.setSiteModification("test");
		
		refComPrefDgtJson = mapper.writeValueAsString(refComPrefDgt);
		
		MvcResult result = getMockMvc()
							.perform(put("/communicationpreferencesdgt/{id}", id)
							.contentType("application/json; charset=utf-8")
							.content(refComPrefDgtJson))
							.andExpect(status().isOk())
							.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		RefComPrefDgtResource refComPrefDgtResponse = mapper.readValue(jsonContent, RefComPrefDgtResource.class);

		assertEquals(refComPrefDgtResponse.getType(), "KL");
		assertEquals(refComPrefDgtResponse.getDateModification(), date);
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDgtUpdateNotExists() throws Exception {
		String refComPrefDgtJson;
		RefComPrefGType pComGroupeType = new RefComPrefGType();
		pComGroupeType.setCodeGType("N");
		RefComPrefType pComType = new RefComPrefType();
		pComType.setCodeType("AF");
		RefComPrefDomain pDomain = new RefComPrefDomain();
		pDomain.setCodeDomain("S");
		
		Integer id = 10;
		RefComPrefDgt refComPrefDgt = new RefComPrefDgt();
		refComPrefDgt.setRefComPrefDgtId(id);
		refComPrefDgt.setGroupType(pComGroupeType);
		refComPrefDgt.setType(pComType);
		refComPrefDgt.setDomain(pDomain);
		
		Date date = new Date();
		refComPrefDgt.setDateModification(date);
		refComPrefDgt.setSignatureModification("test");
		refComPrefDgt.setSiteModification("test");
		
		refComPrefDgtJson = mapper.writeValueAsString(refComPrefDgt);

		getMockMvc()
					.perform(put("/communicationpreferencesdgt/{id}", id)
					.contentType("application/json; charset=utf-8")
					.content(refComPrefDgtJson))
					.andExpect(status().isInternalServerError())
					.andReturn();
	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDgtDelete() throws Exception {
		getMockMvc()
			.perform(delete("/communicationpreferencesdgt/102"))
			.andExpect(status().isOk())
			.andReturn();
	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDgtDeleteNotExists() throws Exception {
		getMockMvc()
			.perform(delete("/communicationpreferencesdgt/10"))
			.andExpect(status().isInternalServerError())
			.andReturn();
	}

}
