package com.afklm.repindmsv.tribe.controller.ut;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repindmsv.tribe.controller.MemberController;
import com.afklm.repindmsv.tribe.criteria.member.AddMemberCriteria;
import com.afklm.repindmsv.tribe.criteria.member.UpdateMemberCriteria;
import com.afklm.repindmsv.tribe.entity.node.Member;
import com.afklm.repindmsv.tribe.entity.node.Tribe;
import com.afklm.repindmsv.tribe.model.RolesEnum;
import com.afklm.repindmsv.tribe.model.StatusEnum;
import com.afklm.repindmsv.tribe.model.error.ErrorMessage;
import com.afklm.repindmsv.tribe.repository.MemberRepository;
import com.afklm.repindmsv.tribe.repository.TribeRepository;
import com.afklm.repindmsv.tribe.services.MemberService;
import com.afklm.repindmsv.tribe.services.helper.MemberHelper;
import com.afklm.repindmsv.tribe.services.helper.TribeHelper;
import com.afklm.repindmsv.tribe.wrapper.WrapperMemberResponse;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MemberServiceTest {

    
    @Mock
    MemberController memberController;
    
    @Mock
    TribeRepository tribeRepository;
    
    @Mock
    MemberRepository memberRepository;


    @Mock
    MemberHelper memberHelper;

    @Mock
    TribeHelper tribeHelper;


    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private MemberService memberService;

	private String defaultApplication = "REPIND";
	
	private String defaultGin = "123456678784";



    private String defaultManager = "123456678785";
	
	private String defaultName = "Tribe name";
	
	private UUID defaultId = UUID.randomUUID();
	
	private String defaultStatus = StatusEnum.PENDING.getName();
	
	private String defaultType = "Type";
        
    @BeforeEach
    public void setup() throws Exception {
        memberService = new MemberService(tribeRepository, memberRepository, memberHelper, tribeHelper);
        
    }
    
    /*
     * 
     *  ADD A MEMBER
     * 
     */
    @Test
    void testAddAMember() throws Exception {
        Tribe tribe = tribeCreate();
        Member member = memberCreate(tribe);


        AddMemberCriteria memberCriteria = new AddMemberCriteria()
                .withApplication(defaultApplication)
                .withGin(defaultGin)
                .withTribeId(defaultId.toString());




		when(tribeRepository.retrieveRelationMemberShip(defaultId.toString())).thenReturn(Optional.of(tribe));

        memberService.addMember(memberCriteria);


		//verify
        verify(tribeRepository).save(tribe);
        verify(memberRepository).save(member);
        
    }

    @Test
    void testAddAMemberTribeNotExist() throws Exception {


        AddMemberCriteria memberCriteria = new AddMemberCriteria()
                .withApplication(defaultApplication)
                .withGin(defaultGin)
                .withTribeId(defaultId.toString());


        when(tribeRepository.findById(defaultId)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessException.class, () -> memberService.addMember(memberCriteria));
        Assertions.assertEquals(ErrorMessage.ERROR_MESSAGE_404_002.getDescription(), exception.getMessage());
    }

    @Test
    void testUpdateStatusRelationValidated() throws Exception {
        UpdateMemberCriteria memberCriteria = new UpdateMemberCriteria()
        .withApplication(defaultApplication)
        .withStatus(StatusEnum.VALIDATED.getName())
        .withGin(defaultGin)
        .withTribeId(defaultId.toString());


        Tribe tribe = tribeCreate();
        Member member = memberCreate(tribe);


        when(tribeRepository.retrieveRelationMemberShip(defaultId.toString())).thenReturn(Optional.of(tribe));
        when(memberHelper.getMemberIfExists(tribe.getMembers(), defaultGin)).thenReturn(member);
        when(tribeHelper.getIdStringFromUUID(tribe.getId())).thenReturn(tribe.getId().toString());


        ResponseEntity<WrapperMemberResponse> response = memberService.updateStatusRelation(memberCriteria);

        verify(memberRepository).save(member);

        assertEquals(response.getBody().status, StatusEnum.VALIDATED.getName());
        assertEquals(response.getBody().tribeId, defaultId.toString());
        assertEquals(response.getBody().gin, defaultGin);


    	

    }


    @Test
    void testUpdateStatusRelationMemberNotExist() throws Exception {
        UpdateMemberCriteria memberCriteria = new UpdateMemberCriteria()
                .withApplication(defaultApplication)
                .withStatus(StatusEnum.VALIDATED.getName())
                .withGin(defaultGin)
                .withTribeId(defaultId.toString());

        Tribe tribe = tribeCreate();


        when(tribeRepository.retrieveRelationMemberShip(defaultId.toString())).thenReturn(Optional.of(tribe));
        when(memberHelper.getMemberIfExists(tribe.getMembers(), defaultGin)).thenReturn(null);

        Throwable exception = assertThrows(BusinessException.class, () -> memberService.updateStatusRelation(memberCriteria));
        Assertions.assertEquals(ErrorMessage.ERROR_MESSAGE_404_003.getDescription(), exception.getMessage());
    }

    @Test
    void testUpdateStatusRelationTribeNotExist() throws Exception {

        UpdateMemberCriteria memberCriteria = new UpdateMemberCriteria()
                .withApplication(defaultApplication)
                .withStatus(StatusEnum.VALIDATED.getName())
                .withGin(defaultGin)
                .withTribeId(defaultId.toString());


        when(tribeRepository.findById(defaultId)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessException.class, () -> memberService.updateStatusRelation(memberCriteria));
        Assertions.assertEquals(ErrorMessage.ERROR_MESSAGE_404_002.getDescription(), exception.getMessage());
    }
    

    @Test
    void testUpdateStatusRelationRefused() throws Exception {


        UpdateMemberCriteria memberCriteria = new UpdateMemberCriteria()
                .withApplication(defaultApplication)
                .withStatus(StatusEnum.REFUSED.getName())
                .withGin(defaultGin)
                .withTribeId(defaultId.toString());


        Tribe tribe = tribeCreate();
        Member member = memberCreate(tribe);


        when(tribeRepository.retrieveRelationMemberShip(defaultId.toString())).thenReturn(Optional.of(tribe));
        when(memberHelper.getMemberIfExists(tribe.getMembers(), defaultGin)).thenReturn(member);
        when(tribeHelper.getIdStringFromUUID(tribe.getId())).thenReturn(tribe.getId().toString());


        ResponseEntity<WrapperMemberResponse> response = memberService.updateStatusRelation(memberCriteria);

        verify(memberRepository).delete(member);

        assertEquals(response.getBody().status, StatusEnum.REFUSED.getName());
        assertEquals(response.getBody().tribeId, defaultId.toString());
        assertEquals(response.getBody().gin, defaultGin);

        
    }


    @Test
    void testUpdateStatusRelationDeleted() throws Exception {


        UpdateMemberCriteria memberCriteria = new UpdateMemberCriteria()
                .withApplication(defaultApplication)
                .withStatus(StatusEnum.DELETED.getName())
                .withGin(defaultGin)
                .withTribeId(defaultId.toString());


        Tribe tribe = tribeCreate();
        Member member = memberCreate(tribe);


        when(tribeRepository.retrieveRelationMemberShip(defaultId.toString())).thenReturn(Optional.of(tribe));
        when(memberHelper.getMemberIfExists(tribe.getMembers(), defaultGin)).thenReturn(member);
        when(tribeHelper.getIdStringFromUUID(tribe.getId())).thenReturn(tribe.getId().toString());


        ResponseEntity<WrapperMemberResponse> response = memberService.updateStatusRelation(memberCriteria);

        verify(memberRepository).delete(member);


        assertEquals(response.getBody().tribeId, defaultId.toString());
        assertEquals(response.getBody().gin, defaultGin);


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
        member.setGin(defaultGin);
        member.setRole(RolesEnum.DELEGATOR.getName());
        member.setStatus(StatusEnum.PENDING.getName());

        Set<Member> members = new HashSet<>();
        members.add(member);

        tribe.setMembers(members);

        return member;
    }
    
}
