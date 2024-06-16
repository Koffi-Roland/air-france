package com.afklm.repindmsv.tribe.services.helper;

import com.afklm.repindmsv.tribe.entity.node.Member;
import com.afklm.repindmsv.tribe.model.StatusEnum;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class MemberHelper {
    
    private static final String CREATION_SITE = "REPINDMSV";
	/**
	 * 
	 * Get member with gin if exists
	 *
	 */
	public Member getMemberIfExists(final Set<Member> members, final String gin) {

		// Search the member in the tribe by gin
		return members.stream()
				.filter(mem -> gin.equals(mem.getGin()))
				.filter(mem -> !StatusEnum.DELETED.getName().equals(mem.getStatus()))
				.filter(mem -> !StatusEnum.REFUSED.getName().equals(mem.getStatus()))
				.findFirst().orElse(null);
		
	}

	/**
	 * 
	 * Set modification signature for a member
	 *
	 * @return signature
	 * @throws BusinessException
	 */
	public void setMemberModificationSignature(Member member, final String application) {
		
		member.setModificationDate(new Date());
		member.setModificationSignature(application);
		member.setModificationSite(CREATION_SITE);
		
	}

	/**
	 * 
	 * Set creation signature for a member
	 *
	 * @return signature
	 * @throws BusinessException
	 */
	public void setMemberCreationSignature(final Member member, final String application) {
		
		member.setCreationDate(new Date());
		member.setCreationSignature(application);
		member.setCreationSite(CREATION_SITE);
		
	}


}