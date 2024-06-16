package com.afklm.repindmsv.tribe.services.helper;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repindmsv.tribe.entity.node.Member;
import com.afklm.repindmsv.tribe.entity.node.Tribe;
import com.afklm.repindmsv.tribe.model.error.BusinessError;
import com.afklm.repindmsv.tribe.wrapper.WrapperRetrieveTribeMemberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

import com.afklm.repindmsv.tribe.config.MVCConfig.BeanMapper;

@Component
public class TribeHelper {
	
	@Autowired
	private BeanMapper mapper;
    
    private static final String CREATION_SITE = "REPINDMSV";

	/**
	 * 
	 * Get UUID from string id
	 *
	 * @return UUID
	 * @throws BusinessException
	 */
	public UUID getUUIDFromString(String id) throws BusinessException {
		
		UUID uuid = null;
		
		try {
			uuid = UUID.fromString(id);
		} catch (IllegalArgumentException e) {
			
			// If the id in input doesn't correspond to an uuid

			throw new BusinessException(BusinessError.API_INVALID_ID);

			
		}
		
		return uuid;
	}


	public String getIdStringFromUUID(UUID uuid) throws BusinessException {
		String id = null;
		try {
			id = uuid.toString();
		} catch (IllegalArgumentException e) {

			// If the uuid in input doesn't correspond

			throw new BusinessException(BusinessError.API_INVALID_ID);
		}
		return id;

	}
	
	/**
	 * 
	 * Set modification signature for tribe
	 *
	 * @return signature
	 * @throws BusinessException
	 */
	public void setTribeModificationSignature(Tribe tribe, final String application) {
		
		tribe.setModificationDate(new Date());
		tribe.setModificationSignature(application);
		tribe.setModificationSite(CREATION_SITE);
		
	}

	/**
	 * 
	 * Set creation signature for tribe
	 *
	 * @return signature
	 * @throws BusinessException
	 */
	public void setTribeCreationSignature(final Tribe tribe, final String application) {
		
		tribe.setCreationDate(new Date());
		tribe.setCreationSignature(application);
		tribe.setCreationSite(CREATION_SITE);
		
	}

	public WrapperRetrieveTribeMemberResponse converTribeToRetrieveMember (Member member) {
		WrapperRetrieveTribeMemberResponse wrapperRetrieveTribeMemberResponse = new WrapperRetrieveTribeMemberResponse();
		if(member.getTribe() != null){
			wrapperRetrieveTribeMemberResponse.setTribeId(member.getTribe().getId().toString());
		}
		wrapperRetrieveTribeMemberResponse.setGin(member.getGin());
		wrapperRetrieveTribeMemberResponse.setRole(member.getRole());
		wrapperRetrieveTribeMemberResponse.setStatus(member.getStatus());
		if(member.getModificationSignature() != null){
			wrapperRetrieveTribeMemberResponse.setModificationSignature(member.getModificationSignature());
			wrapperRetrieveTribeMemberResponse.setModificationSite(member.getModificationSite());
			wrapperRetrieveTribeMemberResponse.setModificationDate(member.getModificationDate());
		}
		wrapperRetrieveTribeMemberResponse.setCreationSignature(member.getCreationSignature());
		wrapperRetrieveTribeMemberResponse.setCreationSite(member.getCreationSite());
		wrapperRetrieveTribeMemberResponse.setCreationDate(member.getCreationDate());

		return wrapperRetrieveTribeMemberResponse;
	}

}