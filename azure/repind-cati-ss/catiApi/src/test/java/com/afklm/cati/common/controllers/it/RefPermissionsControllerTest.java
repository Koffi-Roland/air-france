package com.afklm.cati.common.controllers.it;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.afklm.cati.common.springsecurity.SecurityTestConfiguration;
import com.afklm.cati.common.springsecurity.TestWebConfiguration;
import com.afklm.cati.common.spring.rest.controllers.RefPermissionsController;
import com.afklm.cati.common.spring.rest.resources.RefPermissionsSaveOrUpdateResource;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityTestConfiguration.class, TestWebConfiguration.class})
@Transactional(value = "transactionManagerSic")
public class RefPermissionsControllerTest extends AbstractControllerTest {
	@Autowired
	DozerBeanMapper dozerBeanMapper;
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	RefPermissionsController refPermissionsController;
	
	@Override
	protected Object getController() {
		return refPermissionsController;
	}
	
	
	@Test
	@WithMockUser(value="user",username = "user",roles = "USER")
	public void testMethodRefPermissionsCollectionAddWithBadAuthority() throws Exception {
		String refPermissionsJson;
		RefPermissionsSaveOrUpdateResource refPermissions = new RefPermissionsSaveOrUpdateResource();
		refPermissions.setRefPermissionsQuestionId(103);
		List<Integer> list = new ArrayList<Integer>();
		list.add(101);
		list.add(102);
		refPermissions.setListComPrefDgt(list);
		Date date = new Date();
		refPermissions.setDateCreation(date);
		refPermissions.setDateModification(date);
		refPermissions.setSignatureCreation("test");
		refPermissions.setSignatureModification("test");
		refPermissions.setSiteCreation("test");
		refPermissions.setSiteModification("test");
		refPermissionsJson = mapper.writeValueAsString(refPermissions);

		getMockMvc()
						.perform(post("/permissions")
						.contentType("application/json; charset=utf-8")
						.content(refPermissionsJson))
						.andExpect(status().isUnauthorized())
						.andReturn();

	}
	

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPermissionsCollectionAdd() throws Exception {
		String refPermissionsJson;
		RefPermissionsSaveOrUpdateResource refPermissions = new RefPermissionsSaveOrUpdateResource();
		refPermissions.setRefPermissionsQuestionId(103);
		List<Integer> list = new ArrayList<Integer>();
		list.add(101);
		list.add(102);
		refPermissions.setListComPrefDgt(list);
		Date date = new Date();
		refPermissions.setDateCreation(date);
		refPermissions.setDateModification(date);
		refPermissions.setSignatureCreation("test");
		refPermissions.setSignatureModification("test");
		refPermissions.setSiteCreation("test");
		refPermissions.setSiteModification("test");
		refPermissionsJson = mapper.writeValueAsString(refPermissions);

		getMockMvc()
						.perform(post("/permissions")
						.contentType("application/json; charset=utf-8")
						.content(refPermissionsJson))
						.andExpect(status().isCreated())
						.andReturn();

	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPermissionsGet() throws Exception {
		MvcResult result = getMockMvc()
								.perform(get("/permissions/101"))
								.andExpect(status().isOk())
								.andReturn();

		String jsonContent = result.getResponse().getContentAsString();
		List<Integer> refPermissions = mapper.readValue(jsonContent, List.class);

		assertEquals(refPermissions.size(), 2);
	}

	@Test
	@WithMockUser(value="admin",username = "admin",roles = "ADMIN_COMMPREF")
	public void testMethodRefPermissionsGetNotExists() throws Exception {
		MvcResult result = getMockMvc()
								.perform(get("/permissions/10"))
								.andExpect(status().isNotFound())
								.andReturn();
	}
}
