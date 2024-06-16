package com.airfrance.repind.util;

import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterUtils {
	
	/**
	 * Filter emails according following criteria:
	 * <ul>
	 * 	<li>no more than limit emails</li>
	 * 	<li>only VALID emails</li>
	 * </ul>
	 * 
	 * @param emailDTOSet
	 * @param limit
	 * @return
	 */
	public static Set<EmailDTO> filterEmail(Set<EmailDTO> emailDTOSet, int limit) {

		if(emailDTOSet==null) {
			return null;
		}
		
		Set<EmailDTO> filteredEmailDTOSet = new HashSet<EmailDTO>();
		
		for(EmailDTO emailDTO : emailDTOSet) {
			
			// no more than limit emails
			if(filteredEmailDTOSet.size()==limit) {
				break;
			}
			
			// only valid emails
			if(!MediumStatusEnum.VALID.toString().equals(emailDTO.getStatutMedium())) {
				continue;
			}
			
			filteredEmailDTOSet.add(emailDTO);
			
		}
		
		return filteredEmailDTOSet;
	}
	
	/**
	 * Filter postal addresses according following criteria:
	 * <ul>
	 * 	<li>no more than nbMax postal address</li>
	 * 	<li>only VALID postal address</li>
	 * </ul>
	 * 
	 * @param postalAddressDTOList
	 * @param limit
	 * @return
	 */
	public static List<PostalAddressDTO> filterPostalAddress(List<PostalAddressDTO> postalAddressDTOList, int limit) {

		if(postalAddressDTOList==null) {
			return null;
		}
		
		List<PostalAddressDTO> filteredPostalAddressDTOList = new ArrayList<PostalAddressDTO>();
		
		for(PostalAddressDTO postalAddressDTO : postalAddressDTOList) {
			
			// no more than limit emails
			if(filteredPostalAddressDTOList.size()==limit) {
				break;
			}
			
			// only valid emails
			if(!MediumStatusEnum.VALID.toString().equals(postalAddressDTO.getSstatut_medium())) {
				continue;
			}
			
			filteredPostalAddressDTOList.add(postalAddressDTO);
			
		}
		
		return filteredPostalAddressDTOList;
	}
	
    
    
}
