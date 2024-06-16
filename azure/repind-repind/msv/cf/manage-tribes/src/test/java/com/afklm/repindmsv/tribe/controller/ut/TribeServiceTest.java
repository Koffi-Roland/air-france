package com.afklm.repindmsv.tribe.controller.ut;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repindmsv.tribe.criteria.tribe.CreateTribeCriteria;
import com.afklm.repindmsv.tribe.criteria.tribe.DeleteTribeCriteria;
import com.afklm.repindmsv.tribe.criteria.tribe.RetrieveTribeByGinCriteria;
import com.afklm.repindmsv.tribe.criteria.tribe.RetrieveTribeByIdCriteria;
import com.afklm.repindmsv.tribe.entity.node.Member;
import com.afklm.repindmsv.tribe.entity.node.Tribe;
import com.afklm.repindmsv.tribe.model.MemberModel;
import com.afklm.repindmsv.tribe.model.RolesEnum;
import com.afklm.repindmsv.tribe.model.StatusEnum;
import com.afklm.repindmsv.tribe.model.TribeModel;
import com.afklm.repindmsv.tribe.model.error.ErrorMessage;
import com.afklm.repindmsv.tribe.repository.MemberRepository;
import com.afklm.repindmsv.tribe.repository.TribeRepository;
import com.afklm.repindmsv.tribe.services.TribeService;
import com.afklm.repindmsv.tribe.services.helper.MemberHelper;
import com.afklm.repindmsv.tribe.services.helper.TribeHelper;
import com.afklm.repindmsv.tribe.wrapper.WrapperRetrieveTribeMemberResponse;
import com.afklm.repindmsv.tribe.wrapper.WrapperTribeResponse;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.afklm.repindmsv.tribe.config.MVCConfig.BeanMapper;

@ExtendWith(SpringExtension.class)
class TribeServiceTest {


    
    @Mock
    TribeRepository tribeRepository;
    
    @Mock
    MemberRepository memberRepository;

    @Mock
    MemberHelper memberHelper;

    @Mock
    TribeHelper tribeHelper;

    @Mock
	private BeanMapper mapper;


    private TribeService tribeService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
	private String defaultApplication = "REPIND";
	
	private String defaultManager = "123456678784";
	
	private String defaultName = "Tribe name";
	
	private String defaultType = "Description";
	
	private UUID defaultId = UUID.randomUUID();
	
	private String defaultStatus = StatusEnum.PENDING.getName();
	
	private String roleManager = RolesEnum.MANAGER.getName();
        
    @BeforeEach
    public void setup() throws Exception {

         tribeService = new TribeService(tribeRepository, memberRepository, memberHelper, tribeHelper, mapper);
        
    }
    
    /*
     * 
     *  CREATE A TRIBE
     * 
     */
    @Test
    void testCreateTribe() {

        CreateTribeCriteria createTribeCriteria = new CreateTribeCriteria()
                .withApplication(defaultApplication)
                .withManager(defaultManager)
                .withName(defaultName)
                .withType(defaultType);
        Tribe tribe = tribeCreate();
        when(tribeRepository.save(any())).thenReturn(tribe);


        ResponseEntity<WrapperTribeResponse> response = tribeService.createTribe(createTribeCriteria);

        assertNotNull(response);


        
    }
    
    @Test
    void testDeleteTribe() throws BusinessException {
        DeleteTribeCriteria tribeCriteria = new DeleteTribeCriteria()
                .withTribeId(defaultId.toString())
                .withApplication(defaultApplication);

        Tribe tribe = tribeCreate();
        Member member = memberCreate(tribe);

        when(tribeHelper.getUUIDFromString(tribeCriteria.getTribeId())).thenReturn(defaultId);
        when(tribeRepository.retrieveRelationMemberShip(defaultId.toString())).thenReturn(Optional.of(tribe));

        ResponseEntity<WrapperTribeResponse> response =  tribeService.deleteTribe(tribeCriteria);

        verify(memberRepository).delete(member);
        verify(tribeRepository).deleteById(defaultId);


        assertEquals(response.getBody().id, defaultId.toString());
        
    }


    @Test
    void testDeleteTribeNotFound() throws BusinessException {
        DeleteTribeCriteria tribeCriteria = new DeleteTribeCriteria()
                .withTribeId(defaultId.toString())
                .withApplication(defaultApplication);

        Tribe tribe = tribeCreate();
        Member member = memberCreate(tribe);

        when(tribeHelper.getUUIDFromString(tribeCriteria.getTribeId())).thenReturn(defaultId);
        when(tribeRepository.findById(defaultId)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessException.class, () -> tribeService.deleteTribe(tribeCriteria));
        Assertions.assertEquals(ErrorMessage.ERROR_MESSAGE_404_002.getDescription(), exception.getMessage());
    }



    @Test
    void testGetTribeById() throws BusinessException {
        RetrieveTribeByIdCriteria tribeByIdCriteria = new RetrieveTribeByIdCriteria()
        .withTribeId(defaultId.toString())
        .withApplication(defaultApplication);

        Tribe tribe = tribeCreate();
        Member member = memberCreate(tribe);


        Set<Member> members = new HashSet<>();
        members.add(member);
        TribeModel tribeModel = new TribeModel();
        tribeModel.setId(tribe.getId().toString());
        tribeModel.setStatus(tribe.getStatus());
        tribeModel.setType(tribe.getType());
        MemberModel memberModel = new MemberModel();
        memberModel.setGin(member.getGin());
        memberModel.setStatus(member.getStatus());
        Set<MemberModel> membersModel = new HashSet<>();
        membersModel.add(memberModel);
        tribeModel.setMembers(membersModel);



        when(tribeHelper.getUUIDFromString(tribeByIdCriteria.getTribeId())).thenReturn(defaultId);
        when(tribeRepository.retrieveRelationMemberShip(defaultId.toString(), StatusEnum.VALIDATED.getName())).thenReturn(Optional.of(tribe));
        when(mapper.tribeToTribeModel(tribe)).thenReturn(tribeModel);

        ResponseEntity<TribeModel> response = tribeService.findTribeById(tribeByIdCriteria);


        assertEquals(response.getBody().getId(), defaultId.toString());
        assertEquals( 1, response.getBody().getMembers().size());
    }


    @Test
    void testGetTribeByIdNotFound() throws BusinessException {
        RetrieveTribeByIdCriteria tribeByIdCriteria = new RetrieveTribeByIdCriteria()
                .withTribeId(defaultId.toString())
                .withApplication(defaultApplication);

        when(tribeHelper.getUUIDFromString(tribeByIdCriteria.getTribeId())).thenReturn(defaultId);
        when(tribeRepository.findById(defaultId)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessException.class, () -> tribeService.findTribeById(tribeByIdCriteria));
        Assertions.assertEquals(ErrorMessage.ERROR_MESSAGE_404_002.getDescription(), exception.getMessage());
    }


    @Test
    void testGetByGin() throws BusinessException {

        RetrieveTribeByGinCriteria tribeByGinCriteria = new RetrieveTribeByGinCriteria()
                .withGin(defaultManager)
                .withApplication(defaultApplication);

        Tribe tribe = tribeCreate();
        Member member = memberCreate(tribe);

        WrapperRetrieveTribeMemberResponse wrapperTribesResponse = new WrapperRetrieveTribeMemberResponse();

        wrapperTribesResponse.setTribeId(tribe.getId().toString());
        wrapperTribesResponse.setGin(member.getGin());
        wrapperTribesResponse.setStatus(member.getStatus());
        wrapperTribesResponse.setRole(member.getRole());

        when(tribeRepository.retrieveMemberWithValidatedStatus(tribeByGinCriteria.getGin(), StatusEnum.VALIDATED.getName())).thenReturn(Optional.of(tribe));
        when(tribeHelper.converTribeToRetrieveMember(member)).thenReturn(wrapperTribesResponse);
        when(memberHelper.getMemberIfExists(tribe.getMembers(), tribeByGinCriteria.getGin())).thenReturn(member);

        ResponseEntity<WrapperRetrieveTribeMemberResponse> response = tribeService.findTribeByGin(tribeByGinCriteria);

        assertEquals(response.getBody().getGin(), defaultManager);
        assertEquals(response.getBody().getTribeId(), defaultId.toString());
        assertEquals(response.getBody().getStatus(), StatusEnum.VALIDATED.getName());
    }


    @Test
    void testGetTribeByGinNotFound() {
        RetrieveTribeByGinCriteria tribeByGinCriteria = new RetrieveTribeByGinCriteria()
                .withGin(defaultManager)
                .withApplication(defaultApplication);

        when(tribeRepository.findById(defaultId)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessException.class, () -> tribeService.findTribeByGin(tribeByGinCriteria));
        Assertions.assertEquals(ErrorMessage.ERROR_MESSAGE_404_003.getDescription(), exception.getMessage());
    }


    private Tribe tribeCreate(){
        Tribe tribe = new Tribe(defaultName);
        tribe.setId(defaultId);
        tribe.setType(defaultType);
        tribe.setStatus(StatusEnum.VALIDATED.getName());

        return tribe;
    }


    private Member memberCreate(Tribe tribe) {

        Member member = new Member();
        member.setGin(defaultManager);
        member.setRole(RolesEnum.DELEGATOR.getName());
        member.setStatus(StatusEnum.VALIDATED.getName());
        member.setTribe(tribe);

        Set<Member> members = new HashSet<>();
        members.add(member);

        tribe.setMembers(members);

        return member;
    }
    
}
