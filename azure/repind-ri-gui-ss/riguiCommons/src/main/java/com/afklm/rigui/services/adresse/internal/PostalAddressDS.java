
package com.afklm.rigui.services.adresse.internal;

import com.afklm.rigui.dao.adresse.PostalAddressRepository;
import com.afklm.rigui.dao.adresse.Usage_mediumRepository;
import com.afklm.rigui.dto.adresse.PostalAddressDTO;
import com.afklm.rigui.dto.adresse.PostalAddressTransform;
import com.afklm.rigui.entity.adresse.PostalAddress;
import com.afklm.rigui.entity.adresse.Usage_medium;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.util.UList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostalAddressDS {
	@Autowired
	private Usage_mediumRepository usageMediumRepository;

	@Autowired
	private PostalAddressRepository postalAddressRepository;

	public void create(PostalAddressDTO postalAddressDTO){
		// light transformation dto -> bo
		PostalAddress postalAddress = PostalAddressTransform.dto2Bo(postalAddressDTO);
		
		if (!UList.isNullOrEmpty(postalAddress.getUsage_medium())) {
			for (Usage_medium usage : postalAddress.getUsage_medium()) {
				usageMediumRepository.saveAndFlush(usage);
			}
		}
		
		// create in database (call the abstract class)
		postalAddressRepository.saveAndFlush(postalAddress);
		
		// Version update and Id update if needed
		PostalAddressTransform.bo2Dto(postalAddress, postalAddressDTO);
	}
	

	

	public void remove(PostalAddressDTO dto){
		remove(dto.getSain());
	}

	public void remove(String sain){
		postalAddressRepository.deleteById(sain);
	}

	public void update(PostalAddressDTO postalAddressDTO){
		PostalAddress postalAddress = postalAddressRepository.findById(postalAddressDTO.getSain()).get();
		PostalAddressTransform.dto2BoLight(postalAddressDTO, postalAddress);
	}


	public PostalAddressDTO get(PostalAddressDTO dto) throws JrafDomainException {
		return get(dto.getSain());
	}

	public PostalAddressDTO get(String sain) throws JrafDomainException {
		Optional<PostalAddress> postalAddress = postalAddressRepository.findById(sain);
		if (!postalAddress.isPresent()) {
			return null;
		}
		return PostalAddressTransform.bo2DtoLight(postalAddress.get());
	}

	public List<PostalAddress> getAddressByGin(String sgin){
		return postalAddressRepository.findPostalAddress(sgin);
	}

	public int getNumberProAddressesByGin(String gin){
		return postalAddressRepository.getNumberProOrPersoAddressesByGinAndCodeMedium(gin, "P");
	}

	public int getNumberPersoAddressesByGin(String gin){
		return postalAddressRepository.getNumberProOrPersoAddressesByGinAndCodeMedium(gin, "D");
	}

}
