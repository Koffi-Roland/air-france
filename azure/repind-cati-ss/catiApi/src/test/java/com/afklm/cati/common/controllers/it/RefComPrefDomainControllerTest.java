package com.afklm.cati.common.controllers.it;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.afklm.cati.common.spring.rest.controllers.RefComPrefDomainController;
import com.afklm.cati.common.entity.RefComPrefDomain;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class RefComPrefDomainControllerTest extends AbstractControllerTest {
	@Autowired
	DozerBeanMapper dozerBeanMapper;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RefComPrefDomainController refComPrefDomainController;
	
	@Override
	protected Object getController() {
		return refComPrefDomainController;
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDomainCollectionList() throws Exception {
		getMockMvc()
			.perform(get("/domains"))
			.andExpect(jsonPath("$", hasSize(3)))
			.andReturn();	

	}
	
	
	@Test
	@WithMockUser(value="user",username = "user",roles = "USER")
	public void testMethodRefComPrefDomainCollectionAddWithBadAuthority() throws Exception {
		String refComPrefDomainJson;
		RefComPrefDomain pDomain = new RefComPrefDomain();
		pDomain.setCodeDomain("S");
		pDomain.setLibelleDomainEN("Sales");
		pDomain.setLibelleDomain("Ventes");
		
		refComPrefDomainJson = mapper.writeValueAsString(pDomain);

		getMockMvc()
						.perform(post("/domains")
						.contentType("application/json; charset=utf-8")
						.content(refComPrefDomainJson))
						.andExpect(status().isUnauthorized())
						.andReturn();

	}
	

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDomainCollectionAdd() throws Exception {

		String refComPrefDomainJson;
		RefComPrefDomain pDomain = new RefComPrefDomain();
		pDomain.setCodeDomain("S");
		pDomain.setLibelleDomainEN("Sales");
		pDomain.setLibelleDomain("Ventes");
		
		Date date = new Date();
		pDomain.setDateCreation(date);
		pDomain.setDateModification(date);
		pDomain.setSignatureCreation("test");
		pDomain.setSignatureModification("test");
		pDomain.setSiteCreation("test");
		pDomain.setSiteModification("test");
		
		refComPrefDomainJson = mapper.writeValueAsString(pDomain);

		getMockMvc()
						.perform(post("/domains")
						.contentType("application/json; charset=utf-8")
						.content(refComPrefDomainJson))
						.andExpect(status().isCreated())
						.andReturn();

	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDomainGet() throws Exception {
		MvcResult result = getMockMvc()
								.perform(get("/domains/S"))
								.andExpect(status().isOk())
								.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		RefComPrefDomain refComPrefDomain = mapper.readValue(jsonContent, RefComPrefDomain.class);

		assertEquals(refComPrefDomain.getCodeDomain(), "S");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDomainGetNotExists() throws Exception {
		getMockMvc()
			.perform(get("/domains/V"))
			.andExpect(status().isNotFound())
			.andReturn();

	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDomainUpdate() throws Exception {
		String refComPrefDomainJson;
		RefComPrefDomain pDomain = new RefComPrefDomain();
		
		String code = "S";
		pDomain.setCodeDomain(code);
		pDomain.setLibelleDomain("SalesUpdate");
		pDomain.setLibelleDomainEN("SalesUpdate");
		
		Date date = new Date();
		pDomain.setDateModification(date);
		pDomain.setSignatureModification("test");
		pDomain.setSiteModification("test");
		
		refComPrefDomainJson = mapper.writeValueAsString(pDomain);
		
		MvcResult result = getMockMvc()
							.perform(put("/domains/{code}", code)
							.contentType("application/json; charset=utf-8")
							.content(refComPrefDomainJson))
							.andExpect(status().isOk())
							.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		RefComPrefDomain refComPrefDomainResponse = mapper.readValue(jsonContent, RefComPrefDomain.class);

		assertThat(refComPrefDomainResponse).isNotNull();
		assertEquals(refComPrefDomainResponse.getLibelleDomain(), "SalesUpdate");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDomainUpdateNotExists() throws Exception {
		String refComPrefDomainJson;
		RefComPrefDomain pDomain = new RefComPrefDomain();
		
		String code = "V";
		pDomain.setCodeDomain(code);
		pDomain.setLibelleDomain("SalesUpdate");
		pDomain.setLibelleDomainEN("SalesUpdate");
		
		refComPrefDomainJson = mapper.writeValueAsString(pDomain);
		
		getMockMvc()
						.perform(put("/domains/{code}", code)
						.contentType("application/json; charset=utf-8")
						.content(refComPrefDomainJson))
						.andExpect(status().isInternalServerError())
						.andReturn();
	}
	
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDomainDelete() throws Exception {
		getMockMvc()
			.perform(delete("/domains/U"))
			.andExpect(status().isOk())
			.andReturn();
	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefDomainDeleteNotExists() throws Exception {
		getMockMvc()
			.perform(delete("/domains/V"))
			.andExpect(status().isInternalServerError())
			.andReturn();
	}
}
