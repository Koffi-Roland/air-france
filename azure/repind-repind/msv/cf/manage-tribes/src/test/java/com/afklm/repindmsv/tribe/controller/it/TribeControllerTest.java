package com.afklm.repindmsv.tribe.controller.it;

import com.afklm.repindmsv.tribe.entity.node.Member;
import com.afklm.repindmsv.tribe.entity.node.Tribe;
import com.afklm.repindmsv.tribe.model.MemberModel;
import com.afklm.repindmsv.tribe.model.RolesEnum;
import com.afklm.repindmsv.tribe.model.StatusEnum;
import com.afklm.repindmsv.tribe.model.TribeModel;
import com.afklm.repindmsv.tribe.model.error.ErrorMessage;
import com.afklm.repindmsv.tribe.repository.MemberRepository;
import com.afklm.repindmsv.tribe.repository.TribeRepository;
import com.afklm.repindmsv.tribe.wrapper.WrapperTribeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.ServerControls;
import org.neo4j.harness.TestServerBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class TribeControllerTest {

    MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext webApplicationContext;
    
    @Autowired
    TribeRepository tribeRepository;
    
    @Autowired
    MemberRepository memberRepository;
	
    @Autowired
	private ObjectMapper mapper;

	private String defaultApplication = "REPIND";
	
	private String defaultManager = "123456678785";
	
	private String defaultMember = "123456678999";
	
	private String defaultName = "Tribe name";
	
	private String defaultType = "FB_FAMILY";
	
	private String defaultStatus = StatusEnum.PENDING.toString();

	private static ServerControls embeddedDataBaseServer;


	private static final String SERVICE_NAME = "/manage-tribes";

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	@BeforeAll
	static void initializeNeo4j() {
		embeddedDataBaseServer = TestServerBuilders.newInProcessBuilder().newServer();
	}

	@AfterAll
	static void stopNeo4j() {
		embeddedDataBaseServer.close();
	}

	@DynamicPropertySource
	static void neo4jProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.neo4j.uri", embeddedDataBaseServer::boltURI);
		registry.add("spring.neo4j.authentication.username", () -> "neo4j");
		registry.add("spring.neo4j.authentication.password", () -> null);
	}


	/*
     * 
     * CREATE A TRIBE
     * 
     */
    @Test
    void testCreateTribe() throws Exception {
    	
    	String response = mockMvc.perform(post(SERVICE_NAME+"/tribe/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("application", defaultApplication)
        		.param("type", defaultType)
        		.param("name", defaultName)
        		.param("manager", defaultManager))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
    	
    	WrapperTribeResponse wrapperTribeResponse = mapper.readValue(response, WrapperTribeResponse.class);
    	
    	String id = wrapperTribeResponse.id;
        
        checkTribe(id, defaultManager, defaultName, defaultType, defaultApplication, null, false);
        

    }


    /*
     * 
     * DELETE A TRIBE
     * 
     */
    @Test
    void testDeleteTribe() throws Exception {
        
    	String tribeId = initTestData("Test", defaultType, "111111111111", defaultMember).getId().toString();
        
        Optional<Tribe> tribe = tribeRepository.findById(UUID.fromString(tribeId));
        
        assertTrue(tribe.isPresent());
        
    	mockMvc.perform(delete(SERVICE_NAME+"/tribe/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("application", defaultApplication)
        		.param("id", tribeId.toString()))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(tribeId.toString())));

        tribe = tribeRepository.findById(UUID.fromString(tribeId));
        
        assertFalse(tribe.isPresent());
        
    }

    @Test
    void testDeleteTribeNotFound() throws Exception {
        
    	mockMvc.perform(delete(SERVICE_NAME+"/tribe/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("application", defaultApplication)
				.param("id", "111f1111-11d1-1111-1dda-11d1d1d11111"))
		    	.andExpect(status().isNotFound())
				.andExpect(result -> assertEquals(ErrorMessage.ERROR_MESSAGE_404_002.getDescription(), result.getResolvedException().getMessage()));
    }
    

    /*
     * 
     * GET A TRIBE BY ID
     * 
     */
    @Test
    void testGetTribeById() throws Exception {

    	String tribeId = initTestData(defaultName, defaultType, defaultManager, defaultMember).getId().toString();
        
    	String response = mockMvc.perform(get(SERVICE_NAME+"/tribe/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("id", tribeId.toString())
        		.param("application", defaultApplication))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
    	
    	TribeModel tribeModel = mapper.readValue(response, TribeModel.class);
    	
        checkGetTribe(tribeModel);
        
    }
    
    @Test
    void testGetTribeByIdNotFound() throws Exception {

    	mockMvc.perform(get(SERVICE_NAME+"/tribe/")
        		.contentType(MediaType.APPLICATION_JSON)
				.param("id", "111f1111-11d1-1111-1dda-11d1d1d11111")
				.param("application", defaultApplication))
		    	.andExpect(status().isNotFound())
				.andExpect(result -> assertEquals(ErrorMessage.ERROR_MESSAGE_404_002.getDescription(), result.getResolvedException().getMessage()));
    }


    @Test
    void testGetTribesByGinNotExist() throws Exception {

    	mockMvc.perform(get(SERVICE_NAME+"/tribe-member/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("gin", "999999999999")
        		.param("application", defaultApplication))
		    	.andExpect(status().isNotFound())
				.andExpect(result -> assertEquals(ErrorMessage.ERROR_MESSAGE_404_003.getDescription(), result.getResolvedException().getMessage()));
    }
    
    
    /*
     * 
     * COMMON METHODS
     * 
     */
    
    private void checkGetTribe(TribeModel tribe) {
    	
    	assertEquals(defaultName, tribe.getName());
    	assertEquals(defaultType, tribe.getType());
    	assertEquals(1, tribe.getMembers().size());

    	Iterator<MemberModel> iterator = tribe.getMembers().iterator();
    	MemberModel m1 = iterator.next();    	
    	MemberModel m2 = new MemberModel();
    	if(iterator.hasNext()) {
        	m2 = iterator.next();
    	}
    	if(defaultManager.equals(m1.getGin())) {
    		assertTrue(m1.getRole().contains(RolesEnum.MANAGER.getName()));
    		assertNull(m2.getRole());
    	} else if(defaultManager.equals(m2.getGin())) {
    		assertTrue(m2.getRole().contains(RolesEnum.MANAGER.getName()));
    		assertNull(m1.getRole());
    	} else {
    		fail();
    	}
    	assertEquals(defaultApplication, tribe.getSignature().getCreationSignature());
    }
    
    private void checkTribe(String id, String manager, String name, String type, String application, String modifApplication, boolean hasMember) {
    	
    	Tribe tribe = tribeRepository.findById(UUID.fromString(id)).get();
    	assertEquals(name, tribe.getName());
    	assertEquals(type, tribe.getType());
    	if (hasMember) {
        	assertEquals(2, tribe.getMembers().size());
    	} else {
        	assertEquals(1, tribe.getMembers().size());
    	}
    	
    	Iterator<Member> iterator = tribe.getMembers().iterator();
    	Member m1 = iterator.next();
    	
    	Member m2 = new Member();
    	if(iterator.hasNext()) {
        	m2 = iterator.next();
    	}
    	if(defaultManager.equals(m1.getGin())) {
    		assertTrue(m1.getRole().contains(RolesEnum.MANAGER.getName()));
    		assertNull(m2.getRole());
    	} else if(defaultManager.equals(m2.getGin())) {
    		assertTrue(m2.getRole().contains(RolesEnum.MANAGER.getName()));
    		assertNull(m1.getRole());
    	} else {
    		fail();
    	}
    	assertEquals(application, tribe.getCreationSignature());
    	
    	if(modifApplication != null) {
        	assertEquals(modifApplication, tribe.getModificationSignature());
    	}
    	
    }
    
    private Tribe initTestData(String name, String type, String manager, String member) {
    	
    	Tribe tribe = new Tribe(name);
    	tribe.setType(type);
    	tribe.setCreationSignature(defaultApplication);
    	
    	tribe.addMember(createMember(tribe, manager, true));
    	tribe.addMember(createMember(tribe, member, false));
        
    	tribeRepository.save(tribe);
        
        return tribe;
        
    }
    
    private Member createMember(final Tribe tribe, final String gin, boolean manager) {

    	
    	Member member = new Member();
    	if(manager == true) {
    		member.setRole(RolesEnum.MANAGER.getName());
			member.setStatus("V");
    	}else{
			member.setStatus(defaultStatus);

		}
    	member.setTribe(tribe);
    	member.setGin(gin);

    	memberRepository.save(member);
    	
    	return member;
    }
    
}
