package com.afklm.cati.common.controllers.it;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
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
import com.afklm.cati.common.spring.rest.controllers.RefComPrefMediaController;
import com.afklm.cati.common.entity.RefComPrefMedia;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class RefComPrefMediaControllerTest extends AbstractControllerTest {
	@Autowired
	DozerBeanMapper dozerBeanMapper;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RefComPrefMediaController refComPrefMediaController;
	
	@Override
	protected Object getController() {
		return refComPrefMediaController;
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefMediaCollectionList() throws Exception {
		getMockMvc()
			.perform(get("/medias"))
			.andExpect(jsonPath("$", hasSize(2)))
			.andReturn();	

	}
	
	
	@Test
	@WithMockUser(value="user",username = "user",roles = "USER")
	public void testMethodRefComPrefMediaCollectionAddWithBadAuthority() throws Exception {
		String refComPrefMediaJson;
		RefComPrefMedia pMedia = new RefComPrefMedia();
		pMedia.setCodeMedia("M");
		pMedia.setLibelleMediaEN("Email test");
		pMedia.setLibelleMedia("Email test");
		
		refComPrefMediaJson = mapper.writeValueAsString(pMedia);

		getMockMvc()
						.perform(post("/medias")
						.contentType("application/json; charset=utf-8")
						.content(refComPrefMediaJson))
						.andExpect(status().isUnauthorized())
						.andReturn();

	}
	

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefMediaCollectionAdd() throws Exception {

		String refComPrefMediaJson;
		RefComPrefMedia pMedia = new RefComPrefMedia();
		pMedia.setCodeMedia("M");
		pMedia.setLibelleMediaEN("Email test");
		pMedia.setLibelleMedia("Email test");
		
		Date date = new Date();
		pMedia.setDateCreation(date);
		pMedia.setDateModification(date);
		pMedia.setSignatureCreation("test");
		pMedia.setSignatureModification("test");
		pMedia.setSiteCreation("test");
		pMedia.setSiteModification("test");
		
		refComPrefMediaJson = mapper.writeValueAsString(pMedia);

		getMockMvc()
						.perform(post("/medias")
						.contentType("application/json; charset=utf-8")
						.content(refComPrefMediaJson))
						.andExpect(status().isCreated())
						.andReturn();

	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefMediaGet() throws Exception {
		MvcResult result = getMockMvc()
								.perform(get("/medias/E"))
								.andExpect(status().isOk())
								.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		RefComPrefMedia refComPrefMedia = mapper.readValue(jsonContent, RefComPrefMedia.class);

		assertEquals(refComPrefMedia.getCodeMedia(), "E");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefMediaGetNotExists() throws Exception {
		getMockMvc()
			.perform(get("/medias/V"))
			.andExpect(status().isNotFound())
			.andReturn();

	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefMediaUpdate() throws Exception {
		String refComPrefMediaJson;
		RefComPrefMedia pMedia = new RefComPrefMedia();
		
		String code = "E";
		pMedia.setCodeMedia(code);
		pMedia.setLibelleMedia("Email test");
		pMedia.setLibelleMediaEN("Email test");
		
		Date date = new Date();
		pMedia.setDateModification(date);
		pMedia.setSignatureModification("test");
		pMedia.setSiteModification("test");
		
		refComPrefMediaJson = mapper.writeValueAsString(pMedia);
		
		MvcResult result = getMockMvc()
							.perform(put("/medias/{code}", code)
							.contentType("application/json; charset=utf-8")
							.content(refComPrefMediaJson))
							.andExpect(status().isOk())
							.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		RefComPrefMedia refComPrefMediaResponse = mapper.readValue(jsonContent, RefComPrefMedia.class);
		assertThat(refComPrefMediaResponse).isNotNull();
		assertEquals(refComPrefMediaResponse.getLibelleMedia(), "Email test");
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefMediaUpdateNotExists() throws Exception {
		String refComPrefMediaJson;
		RefComPrefMedia pMedia = new RefComPrefMedia();
		
		String code = "V";
		pMedia.setCodeMedia(code);
		pMedia.setLibelleMedia("V");
		pMedia.setLibelleMediaEN("V");
		
		refComPrefMediaJson = mapper.writeValueAsString(pMedia);
		
		getMockMvc()
						.perform(put("/medias/{code}", code)
						.contentType("application/json; charset=utf-8")
						.content(refComPrefMediaJson))
						.andExpect(status().isInternalServerError())
						.andReturn();
	}
	
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefMediaDelete() throws Exception {
		getMockMvc()
			.perform(delete("/medias/O"))
			.andExpect(status().isOk())
			.andReturn();
	}
	
	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefComPrefMediaDeleteNotExists() throws Exception {
		getMockMvc()
			.perform(delete("/medias/V"))
			.andExpect(status().isInternalServerError())
			.andReturn();
	}
}
