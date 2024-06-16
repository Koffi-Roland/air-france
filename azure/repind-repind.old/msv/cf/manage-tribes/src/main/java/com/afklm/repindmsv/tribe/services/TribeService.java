package com.afklm.repindmsv.tribe.services;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repindmsv.tribe.criteria.tribe.CreateTribeCriteria;
import com.afklm.repindmsv.tribe.criteria.tribe.DeleteTribeCriteria;
import com.afklm.repindmsv.tribe.criteria.tribe.RetrieveTribeByGinCriteria;
import com.afklm.repindmsv.tribe.criteria.tribe.RetrieveTribeByIdCriteria;
import com.afklm.repindmsv.tribe.entity.node.Member;
import com.afklm.repindmsv.tribe.entity.node.Tribe;
import com.afklm.repindmsv.tribe.model.RolesEnum;
import com.afklm.repindmsv.tribe.model.StatusEnum;
import com.afklm.repindmsv.tribe.model.TribeModel;
import com.afklm.repindmsv.tribe.model.error.BusinessError;
import com.afklm.repindmsv.tribe.repository.MemberRepository;
import com.afklm.repindmsv.tribe.repository.TribeRepository;
import com.afklm.repindmsv.tribe.services.helper.MemberHelper;
import com.afklm.repindmsv.tribe.services.helper.TribeHelper;
import com.afklm.repindmsv.tribe.wrapper.WrapperRetrieveTribeMemberResponse;
import com.afklm.repindmsv.tribe.wrapper.WrapperTribeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.afklm.repindmsv.tribe.config.MVCConfig.BeanMapper;

@Component
@Slf4j
public class TribeService {



	private final TribeRepository tribeRepository;


	private final MemberRepository memberRepository;


	private final MemberHelper memberHelper;


	private final TribeHelper tribeHelper;


	private final BeanMapper mapper;

	public TribeService(TribeRepository tribeRepository, MemberRepository memberRepository, MemberHelper memberHelper, TribeHelper tribeHelper, BeanMapper mapper) {
		this.tribeRepository = tribeRepository;
		this.memberRepository = memberRepository;
		this.memberHelper = memberHelper;
		this.tribeHelper = tribeHelper;
		this.mapper = mapper;
	}

	/**
	 *
	 * Return tribes managed by the gin
	 *
	 * @return response
	 * @throws BusinessException
	 */
	//@UseBookmark
	@Transactional(readOnly=true)
	public ResponseEntity<WrapperRetrieveTribeMemberResponse> findTribeByGin(RetrieveTribeByGinCriteria tribeCriteria) throws BusinessException {

		log.info("begin retrieve member from member repository : ", tribeCriteria.getGin());
		Optional<Tribe> tribeOpt = tribeRepository.retrieveMemberWithValidatedStatus(tribeCriteria.getGin(), StatusEnum.VALIDATED.getName());

		if(!tribeOpt.isPresent()){
			throw new BusinessException(BusinessError.API_MEMBER_NOT_FOUND);
		}
		Tribe tribe = tribeOpt.get();
		Set<Member> members = tribe.getMembers();
		// If there is no members in the tribe, throw an exception
		if(members == null || members.isEmpty()) {
			throw new BusinessException(BusinessError.API_MEMBER_NOT_FOUND);
		}
		// Search the member in the tribe by gin
		Member member = memberHelper.getMemberIfExists(members, tribeCriteria.getGin());

		// If there isn't any tribe managed by the gin, throw an exception
		if(member == null) {
			throw new BusinessException(BusinessError.API_MEMBER_NOT_FOUND);
		}

		// Convert and fill the response
		WrapperRetrieveTribeMemberResponse wrapperTribesResponse =  tribeHelper.converTribeToRetrieveMember(member);
		wrapperTribesResponse.setTribeId(tribe.getId().toString());

		log.info("end retrieve member from member repository : ", tribeCriteria.getGin());
		return new ResponseEntity<>(wrapperTribesResponse, HttpStatus.OK);

	}


	/**
	 *
	 * Return tribe by id
	 *
	 * @return response
	 * @throws BusinessException
	 */
	//@UseBookmark
	@Transactional(readOnly=true)
	public ResponseEntity<TribeModel> findTribeById(RetrieveTribeByIdCriteria tribeCriteria) throws BusinessException {

		TribeModel tribeModel;


		// Get the tribe by id and members
		log.info("begin retrieve tribe from repository :", tribeCriteria.getTribeId());
		Optional<Tribe> tribe = tribeRepository.retrieveRelationMemberShip(tribeHelper.getUUIDFromString(tribeCriteria.getTribeId()).toString(), StatusEnum.VALIDATED.getName());


		log.info("end retrieve tribe from repository :", tribeCriteria.getTribeId());
		// Check if the tribe exists
		if(tribe.isPresent()) {
			log.info("begin mapping tribe :", tribe.get().getId().toString());
			tribeModel = mapper.tribeToTribeModel(tribe.get());
			log.info("end mapping tribe :", tribe.get().getId().toString());
		} else {

			// If there isn't any tribe corresponding to this id, throw an exception
			throw new BusinessException(BusinessError.API_TRIBE_NOT_FOUND);

		}

		return new ResponseEntity<>(tribeModel, HttpStatus.OK);
	}


	/**
	 *
	 * Create a tribe
	 *
	 * @return response
	 * @throws BusinessException
	 */
	//@UseBookmark
	@Transactional(rollbackFor = {RuntimeException.class})
	public ResponseEntity<WrapperTribeResponse> createTribe(final CreateTribeCriteria tribeCriteria) {

		log.info("begin creation of tribe :", tribeCriteria.getName());

		Tribe tribe = new Tribe(tribeCriteria.getName());

		String ginManager = tribeCriteria.getManager();
		String application = tribeCriteria.getApplication();

		tribe.setType(tribeCriteria.getType());
		log.info("begin find member from member repository for creation of tribe : ", ginManager);
		Member member = memberRepository.findByGin(ginManager);

		if(member == null){
			//Creation of manager relationship
			member = new Member();
		}
		member.setGin(tribeCriteria.getManager());
		member.setRole(RolesEnum.MANAGER.getName());
		member.setStatus(StatusEnum.VALIDATED.getName());

		// Set the creation signature of the manager
		memberHelper.setMemberCreationSignature(member, application);


		log.info("creation of the MEMBERSHIP relation ");
		// Add the manager to the tribe
		tribe.addMember(member);

		// Set the creation signature of the tribe
		tribeHelper.setTribeCreationSignature(tribe, tribeCriteria.getApplication());

		final WrapperTribeResponse wrapperTribeResponse = new WrapperTribeResponse();


		// Save the tribe and fill the response
		wrapperTribeResponse.id = tribeRepository.save(tribe).getId().toString();

		return new ResponseEntity<>(wrapperTribeResponse, HttpStatus.OK);
	}


	/**
	 *
	 * Delete a tribe
	 *
	 * @return response
	 * @throws BusinessException
	 */
	//@UseBookmark
	@Transactional
	public ResponseEntity<WrapperTribeResponse> deleteTribe(DeleteTribeCriteria tribeCriteria) throws BusinessException {
		log.info("start deletion tribe: ", tribeCriteria.getTribeId());

		WrapperTribeResponse wrapperTribeResponse = new WrapperTribeResponse();

		UUID uuid = tribeHelper.getUUIDFromString(tribeCriteria.getTribeId());
		// Get the tribe by id
		Optional<Tribe> tribeOpt = tribeRepository.retrieveRelationMemberShip(uuid.toString());

		// Check if the tribe exists
		if(tribeOpt.isPresent()) {
			Tribe tribe = tribeOpt.get();
			for (Member member: tribe.getMembers()) {
				tribeRepository.detachRelationMemberShip(tribe.getId().toString(), member.getGin());
				memberRepository.delete(member);
			}

			// Delete the tribe
			tribeRepository.deleteById(uuid);

		} else {

			// If there isn't any tribe corresponding to this id, throw an exception
			throw new BusinessException(BusinessError.API_TRIBE_NOT_FOUND);

		}

		// Fill the response
		wrapperTribeResponse.id = tribeCriteria.getTribeId();
		log.info("end deletion tribe: ", tribeCriteria.getTribeId());

		return new ResponseEntity<>(wrapperTribeResponse, HttpStatus.OK);
	}

}
