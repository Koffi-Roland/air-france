package com.afklm.rigui.services.helper;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.dto.adresse.PostalAddressDTO;
import com.afklm.rigui.entity.adresse.PostalAddress;

@Component
public class IndividualHelper {

	@Autowired
	public DozerBeanMapper dozerBeanMapper;

	public String getAdresseFormatResultByGin(List<PostalAddress> pa) throws JrafDomainException {
		if (pa.size() > 0) {
			PostalAddress res = pa.get(0);
			return res.getScode_postal() + " " + res.getSville() + " " + res.getScode_pays();
		} else {
			return "";
		}
	}

	public ArrayList<String> getFunctionnalData(PostalAddressDTO address) throws ServiceException {

		ArrayList<String> functionnalData = new ArrayList<>();

		if (address.getDdate_fonctionnel() != null) {
			functionnalData.add(address.getDdate_fonctionnel().toString().substring(0,
					address.getDdate_fonctionnel().toString().length() - 2));
		}
		if (address.getSsite_fonctionnel() != null) {
			functionnalData.add(address.getSsite_fonctionnel());
		}
		if (address.getSsignature_fonctionnel() != null) {
			functionnalData.add(address.getSsignature_fonctionnel());
		}
		if (address.getScod_err_simple() != null) {
			functionnalData.add(address.getScod_err_simple());
		}
		if (address.getScod_err_detaille() != null) {
			functionnalData.add(address.getScod_err_detaille());
		}
		if (address.getStype_invalidite() != null) {
			functionnalData.add(address.getStype_invalidite());
		}
		if (address.getSenvoi_postal() != null) {
			functionnalData.add(address.getSenvoi_postal());
		}

		return functionnalData;

	}
	
}
