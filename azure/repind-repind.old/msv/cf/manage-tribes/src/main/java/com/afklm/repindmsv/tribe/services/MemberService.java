package com.afklm.repindmsv.tribe.services;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repindmsv.tribe.criteria.member.AddMemberCriteria;
import com.afklm.repindmsv.tribe.criteria.member.UpdateMemberCriteria;
import com.afklm.repindmsv.tribe.entity.node.Member;
import com.afklm.repindmsv.tribe.entity.node.Tribe;
import com.afklm.repindmsv.tribe.model.RolesEnum;
import com.afklm.repindmsv.tribe.model.StatusEnum;
import com.afklm.repindmsv.tribe.model.error.BusinessError;
import com.afklm.repindmsv.tribe.repository.MemberRepository;
import com.afklm.repindmsv.tribe.repository.TribeRepository;
import com.afklm.repindmsv.tribe.services.helper.MemberHelper;
import com.afklm.repindmsv.tribe.services.helper.TribeHelper;
import com.afklm.repindmsv.tribe.wrapper.WrapperMemberResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class MemberService {
	

	private final TribeRepository tribeRepository;
	
	private final MemberRepository memberRepository;
	
	private final MemberHelper memberHelper;

	private final TribeHelper tribeHelper;
	


	public MemberService(TribeRepository tribeRepository, MemberRepository memberRepository, MemberHelper memberHelper, TribeHelper tribeHelper) {
		this.tribeRepository = tribeRepository;
		this.memberRepository = memberRepository;
		this.memberHelper = memberHelper;
		this.tribeHelper = tribeHelper;
	}

	/**
	 * 
	 * Add a member
	 *
	 * @return response
	 * @throws BusinessException
	 */
	//@UseBookmark
	@Transactional
	public ResponseEntity<WrapperMemberResponse> addMember(AddMemberCriteria memberCriteria) throws BusinessException {

		log.info("begin add member from member service: ", memberCriteria.getTribeId());
		Optional<Tribe> tribeOpt = tribeRepository.retrieveRelationMemberShip(memberCriteria.getTribeId());
		
		final WrapperMemberResponse wrapperMemberResponse = new WrapperMemberResponse();
		
		// If tribe is present we continue
		if(tribeOpt.isPresent()) {
			Tribe tribe = tribeOpt.get();
			String application = memberCriteria.getApplication();
			
			Member member = new Member();
			member.setGin(memberCriteria.getGin());
			member.setStatus(StatusEnum.PENDING.getName());
			member.setRole(RolesEnum.DELEGATOR.getName());

			// Add the member to the tribe
			tribe.addMember(member);
			
			// Set creation signature
			memberHelper.setMemberCreationSignature(member, application);
			
			memberRepository.save(member);
			tribeRepository.save(tribe);
			
			// Fill the response
			wrapperMemberResponse.tribeId = tribe.getId().toString();
			wrapperMemberResponse.status = member.getStatus();
			wrapperMemberResponse.gin = member.getGin();
			
		} else {
			
			// If tribe is not present, throw an exception : TRIBE_NOT_FOUND
			throw new BusinessException(BusinessError.API_TRIBE_NOT_FOUND);
			
		}
		log.info("end add member from member service :", memberCriteria.getTribeId());
		
		return new ResponseEntity<>(wrapperMemberResponse, HttpStatus.OK);
	}
	

	/**
	 * 
	 * Update a relationship
	 *
	 * @return response
	 * @throws BusinessException
	 */
	//@UseBookmark
	@Transactional
	public ResponseEntity<WrapperMemberResponse> updateStatusRelation( UpdateMemberCriteria memberCriteria) throws BusinessException {

		log.info("begin update member from member service: ", memberCriteria.getGin());
		Optional<Tribe> tribeOpt = tribeRepository.retrieveRelationMemberShip(memberCriteria.getTribeId());
		final WrapperMemberResponse wrapperMemberResponse = new WrapperMemberResponse();
		// If tribe is present we continue
		if(tribeOpt.isPresent()) {
			Tribe tribe = tribeOpt.get();
			String gin = memberCriteria.getGin();
			String application = memberCriteria.getApplication();
			Set<Member> members = tribe.getMembers();
			// If there is no members in the tribe, throw an exception
			if(members == null) {
				throw new BusinessException(BusinessError.API_MEMBER_NOT_FOUND);
				
			}
			// Search the member in the tribe by gin
			Member member = memberHelper.getMemberIfExists(members, gin);

			// If there is no member corresponding to the gin, throw an exception
			if(member == null) {
				throw new BusinessException(BusinessError.API_MEMBER_NOT_FOUND);
			}
			member.setStatus(memberCriteria.getStatus());
			if(StatusEnum.REFUSED.getName().equals(member.getStatus())){
				//physical delete of member
				deleteMemberMethod(tribe, member, wrapperMemberResponse);
				wrapperMemberResponse.status = member.getStatus();
			}
			else if(StatusEnum.DELETED.getName().equals(member.getStatus())) {
				//physical delete of member
				deleteMemberMethod(tribe, member, wrapperMemberResponse);
			}
			else {
				// Set modification signature
				memberHelper.setMemberModificationSignature(member, application);
				validatedMemberMethod(tribe, member, wrapperMemberResponse);
			}
		} else {
			// If tribe is not present, throw an exception : TRIBE_NOT_FOUND
			throw new BusinessException(BusinessError.API_TRIBE_NOT_FOUND);
		}
		return new ResponseEntity<>(wrapperMemberResponse, HttpStatus.OK);
	}


	public void deleteMemberMethod(Tribe tribe, Member member, WrapperMemberResponse wrapperMemberResponse) throws BusinessException {
		tribeRepository.detachRelationMemberShip(tribe.getId().toString(), member.getGin());
		// Fill the response
		wrapperMemberResponse.tribeId =  tribeHelper.getIdStringFromUUID(tribe.getId());
		wrapperMemberResponse.gin = member.getGin();

		memberRepository.delete(member);
	}

	public void validatedMemberMethod(Tribe tribe, Member member, WrapperMemberResponse wrapperMemberResponse) throws BusinessException {

		memberRepository.save(member);

		// Fill the response
		wrapperMemberResponse.tribeId = tribeHelper.getIdStringFromUUID(tribe.getId());
		wrapperMemberResponse.status = member.getStatus();
		wrapperMemberResponse.gin = member.getGin();
	}


	

}