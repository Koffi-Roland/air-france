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
import com.afklm.cati.common.spring.rest.controllers.RefComPrefCountryMarketController;
import com.afklm.cati.common.entity.RefComPrefCountryMarket;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class RefComPrefCountryMarketControllerTest extends AbstractControllerTest {
	@Autowired
	DozerBeanMapper dozerBeanMapper;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RefComPrefCountryMarketController refComPrefCountryMarketController;
	
	@Override
	protected Object getController() {
		return refComPrefCountryMarketController;
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefCountryMarketCollectionList() throws Exception {
		getMockMvc()
			.perform(get("/countrymarkets"))
			.andExpect(jsonPath("$", hasSize(4)))
			.andReturn();	

	}
	
	
	@Test
	@WithMockUser(value="user",username = "user",roles = "AK_USER")
	public void testMethodRefComPrefCountryMarketCollectionAddWithBadAuthority() throws Exception {
		String refComPrefCountryMarketJson;
		RefComPrefCountryMarket pCountryMarket = new RefComPrefCountryMarket();
		pCountryMarket.setCodePays("AE");
		pCountryMarket.setMarket("AE");
		
		refComPrefCountryMarketJson = mapper.writeValueAsString(pCountryMarket);

		getMockMvc()
						.perform(post("/countrymarkets")
						.contentType("application/json; charset=utf-8")
						.content(refComPrefCountryMarketJson))
						.andExpect(status().isUnauthorized())
						.andReturn();

	}
	

	@Test
	@WithMockUser(value="user",username = "user",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefCountryMarketCollectionAdd() throws Exception {

		String refComPrefCountryMarketJson;
		RefComPrefCountryMarket pCountryMarket = new RefComPrefCountryMarket();
		pCountryMarket.setCodePays("AE");
		pCountryMarket.setMarket("AE");
		
		Date date = new Date();
		pCountryMarket.setDateCreation(date);
		pCountryMarket.setDateModification(date);
		pCountryMarket.setSignatureCreation("test");
		pCountryMarket.setSignatureModification("test");
		pCountryMarket.setSiteCreation("test");
		pCountryMarket.setSiteModification("test");
		
		refComPrefCountryMarketJson = mapper.writeValueAsString(pCountryMarket);

		getMockMvc()
						.perform(post("/countrymarkets")
						.contentType("application/json; charset=utf-8")
						.content(refComPrefCountryMarketJson))
						.andExpect(status().isCreated())
						.andReturn();

	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefCountryMarketGet() throws Exception {
		MvcResult result = getMockMvc()
								.perform(get("/countrymarkets/FR"))
								.andExpect(status().isOk())
								.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		RefComPrefCountryMarket refComPrefCountryMarket = mapper.readValue(jsonContent, RefComPrefCountryMarket.class);

		assertEquals(refComPrefCountryMarket.getMarket(), "FR");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefCountryMarketGetNotExists() throws Exception {
		getMockMvc()
			.perform(get("/countrymarkets/V"))
			.andExpect(status().isNotFound())
			.andReturn();

	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefCountryMarketUpdate() throws Exception {
		String refComPrefCountryMarketJson;
		RefComPrefCountryMarket pCountryMarket = new RefComPrefCountryMarket();
		
		String code = "BE";
		pCountryMarket.setCodePays(code);
		pCountryMarket.setMarket("BE");
		
		Date date = new Date();
		pCountryMarket.setDateModification(date);
		pCountryMarket.setSignatureModification("test");
		pCountryMarket.setSiteModification("test");
		
		refComPrefCountryMarketJson = mapper.writeValueAsString(pCountryMarket);
		
		MvcResult result = getMockMvc()
							.perform(put("/countrymarkets/{code}", code)
							.contentType("application/json; charset=utf-8")
							.content(refComPrefCountryMarketJson))
							.andExpect(status().isOk())
							.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		RefComPrefCountryMarket refComPrefCountryMarketResponse = mapper.readValue(jsonContent, RefComPrefCountryMarket.class);

		assertThat(refComPrefCountryMarketResponse).isNotNull();
		assertEquals(refComPrefCountryMarketResponse.getMarket(), "BE");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefCountryMarketUpdateNotExists() throws Exception {
		String refComPrefCountryMarketJson;
		RefComPrefCountryMarket pCountryMarket = new RefComPrefCountryMarket();
		
		String code = "V";
		pCountryMarket.setCodePays(code);
		pCountryMarket.setMarket("V");
		
		refComPrefCountryMarketJson = mapper.writeValueAsString(pCountryMarket);
		
		getMockMvc()
						.perform(put("/countrymarkets/{code}", code)
						.contentType("application/json; charset=utf-8")
						.content(refComPrefCountryMarketJson))
						.andExpect(status().isInternalServerError())
						.andReturn();
	}
	
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefCountryMarketDelete() throws Exception {
		getMockMvc()
			.perform(delete("/countrymarkets/BE"))
			.andExpect(status().isOk())
			.andReturn();
	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefCountryMarketDeleteNotExists() throws Exception {
		getMockMvc()
			.perform(delete("/countrymarkets/V"))
			.andExpect(status().isInternalServerError())
			.andReturn();
	}
}
