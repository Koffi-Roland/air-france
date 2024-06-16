package com.afklm.repindmsv.tribe.controller.it;

import com.afklm.repindmsv.tribe.entity.node.Member;
import com.afklm.repindmsv.tribe.entity.node.Tribe;
import com.afklm.repindmsv.tribe.model.RolesEnum;
import com.afklm.repindmsv.tribe.model.StatusEnum;
import com.afklm.repindmsv.tribe.model.error.ErrorMessage;
import com.afklm.repindmsv.tribe.repository.MemberRepository;
import com.afklm.repindmsv.tribe.repository.TribeRepository;
import com.afklm.repindmsv.tribe.wrapper.WrapperMemberResponse;
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
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MemberControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    TribeRepository tribeRepository;
    
    @Autowired
    MemberRepository memberRepository;

	@Autowired
	private ObjectMapper mapper;

	
	private String defaultApplication = "REPIND";
	
	private String defaultManager = "123456678788";
	
	private String defaultMember = "123456678599";
	
	private String defaultName = "Tribe name";
	
	private String defaultType = "FB_FAMILY";
		
	private String defaultStatus = StatusEnum.PENDING.getName();



	private static ServerControls embeddedDataBaseServer;
	private static final String SERVICE_NAME = "/manage-tribes";

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
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
     * ADD A MEMBER
     * 
     */
    @Test
    void testAddMember() throws Exception {

    	UUID tribeId = initTestData(defaultName, defaultType, defaultManager, null).getId();
        
    	mockMvc.perform(post(SERVICE_NAME+"/member/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("application", defaultApplication)
        		.param("tribeId", tribeId.toString())
        		.param("gin", defaultMember))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.tribeId", is(tribeId.toString())))
	        .andExpect(jsonPath("$.gin", is(defaultMember)))
			.andExpect(jsonPath("$.status", is(defaultStatus)));

    	checkMember(tribeId, defaultMember, defaultStatus, defaultApplication, null);
        
    }

    @Test
    void testAddMemberTribeNotFound() throws Exception {

    	mockMvc.perform(post(SERVICE_NAME+"/member/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("application", defaultApplication)
				.param("tribeId", "111f1111-11d1-1111-1dda-11d1d1d11111")
        		.param("gin", defaultMember))
	        	.andExpect(status().isNotFound())
				.andExpect(result -> assertEquals(ErrorMessage.ERROR_MESSAGE_404_002.getDescription(), result.getResolvedException().getMessage()));
        
    }


    

    /*
     * 
     * UPDATE A RELATION STATUS
     * 
     */
    @Test
	void testUpdateRelationStatus() throws Exception {

		UUID tribeId = initTestData(defaultName, defaultType, defaultManager, defaultMember).getId();

		String newStatus = StatusEnum.VALIDATED.getName();
		String newApplication = "REPIND";

		String response = mockMvc.perform(put(SERVICE_NAME+"/member/")
				.contentType(MediaType.APPLICATION_JSON)
				.param("application", newApplication)
				.param("tribeId", tribeId.toString())
				.param("status", newStatus)
				.param("gin", defaultMember))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.tribeId", is(tribeId.toString())))
				.andExpect(jsonPath("$.gin", is(defaultMember)))
				.andExpect(jsonPath("$.status", is(newStatus)))
				.andReturn().getResponse().getContentAsString();

		WrapperMemberResponse wrapperMemberResponse = mapper.readValue(response, WrapperMemberResponse.class);

		assertEquals(wrapperMemberResponse.gin, defaultMember);
		assertEquals(wrapperMemberResponse.tribeId, tribeId.toString());
		assertEquals(wrapperMemberResponse.status, newStatus);

	}

    @Test
	void testUpdateRelationStatusTribeNotFound() throws Exception {

		mockMvc.perform(put(SERVICE_NAME+"/member/")
				.contentType(MediaType.APPLICATION_JSON)
				.param("application", defaultApplication)
				.param("tribeId", "111f1111-11d1-1111-1dda-11d1d1d11111")
				.param("status", StatusEnum.VALIDATED.getName())
				.param("gin", defaultMember))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertEquals(ErrorMessage.ERROR_MESSAGE_404_002.getDescription(), result.getResolvedException().getMessage()));

	}

    @Test
	void testUpdateRelationStatusMemberNotFound() throws Exception {

		UUID tribeId = initTestData(defaultName, defaultType, defaultManager, null).getId();

		mockMvc.perform(put(SERVICE_NAME+"/member/")
				.contentType(MediaType.APPLICATION_JSON)
				.param("application", defaultApplication)
				.param("tribeId", tribeId.toString())
				.param("status", StatusEnum.VALIDATED.getName())
				.param("gin", defaultMember))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertEquals(ErrorMessage.ERROR_MESSAGE_404_003.getDescription(), result.getResolvedException().getMessage()));
	}


    /*
     * 
     * DELETE A MEMBER
     * 
     */
    @Test
	void testDeleteAMember() throws Exception {

		UUID tribeId = initTestData(defaultName, defaultType, defaultManager, defaultMember).getId();

		String response = mockMvc.perform(delete(SERVICE_NAME+"/member/")
				.contentType(MediaType.APPLICATION_JSON)
				.param("application", defaultApplication)
				.param("tribeId", tribeId.toString())
				.param("gin", defaultMember))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		WrapperMemberResponse wrapperMemberResponse = mapper.readValue(response, WrapperMemberResponse.class);

		assertEquals(wrapperMemberResponse.gin, defaultMember);
		assertEquals(wrapperMemberResponse.tribeId, tribeId.toString());


		Tribe tribe = tribeRepository.findById(tribeId).get();

		for (Member member : tribe.getMembers()) {
			assertNotEquals(defaultMember, member.getGin());
		}

	}

    @Test
	void testDeleteAMemberTribeNotFound() throws Exception {

		mockMvc.perform(delete(SERVICE_NAME+"/member/")
				.contentType(MediaType.APPLICATION_JSON)
				.param("application", defaultApplication)
				.param("tribeId", "111f1111-11d1-1111-1dda-11d1d1d11111")
				.param("gin", defaultMember)
				.param("status", StatusEnum.DELETED.getName()))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertEquals(ErrorMessage.ERROR_MESSAGE_404_002.getDescription(), result.getResolvedException().getMessage()));

	}

    @Test
	void testDeleteAMemberMemberNotFound() throws Exception {

		UUID tribeId = initTestData(defaultName, defaultType, defaultManager, null).getId();

		mockMvc.perform(delete(SERVICE_NAME+"/member/")
				.contentType(MediaType.APPLICATION_JSON)
				.param("application", defaultApplication)
				.param("tribeId", tribeId.toString())
				.param("gin", defaultMember))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertEquals(ErrorMessage.ERROR_MESSAGE_404_003.getDescription(), result.getResolvedException().getMessage()));
	}
    
    /*
     * 
     * COMMON METHODS
     * 
     */
    
    private void checkMember(UUID id, String gin, String status, String application, String modifApplication) {
    	
    	Tribe tribe = tribeRepository.findById(id).get();

    	assertEquals(2, tribe.getMembers().size());

    	Iterator<Member> iterator = tribe.getMembers().iterator();
    	Member m1 = iterator.next();    	
    	Member m2 = new Member();
    	if(iterator.hasNext()) {
        	m2 = iterator.next();
    	}
    	if(gin.equals(m1.getGin())) {
    		assertEquals(RolesEnum.DELEGATOR.getName(), m1.getRole());
        	assertEquals(status, m1.getStatus());
        	assertEquals(application, m1.getCreationSignature());
        	if(modifApplication != null) {
            	assertEquals(modifApplication, m1.getModificationSignature());
        	}
    	} else if(gin.equals(m2.getGin())) {
    		assertTrue(m1.getRole().contains(RolesEnum.MANAGER.getName()));
        	assertEquals(status, m2.getStatus());
        	assertEquals(application, m2.getCreationSignature());
        	if(modifApplication != null) {
            	assertEquals(modifApplication, m2.getModificationSignature());
        	}
    	} else {
    		fail();
    	}
    	
    	
    }
    
    private Tribe initTestData(String name, String type, String manager, String member) {
    	
    	Tribe tribe = new Tribe(name);
    	tribe.setType(type);
    	tribe.setCreationSignature(defaultApplication);
    	
    	tribe.addMember(createMember(tribe, manager, true));
    	
    	if(member != null) {
        	tribe.addMember(createMember(tribe, member, false));
    	}
        
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
    	member.setCreationSignature(defaultApplication);
		memberRepository.save(member);

    	
    	return member;
    }
    
}
