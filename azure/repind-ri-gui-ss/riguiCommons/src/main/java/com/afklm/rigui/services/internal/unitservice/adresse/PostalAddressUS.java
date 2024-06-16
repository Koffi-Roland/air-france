package com.afklm.rigui.services.internal.unitservice.adresse;

import com.afklm.rigui.exception.InvalidParameterException;
import com.afklm.rigui.exception.jraf.JrafDaoException;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.enums.MediumStatusEnum;
import com.afklm.rigui.dao.adresse.FormalizedAdrRepository;
import com.afklm.rigui.dao.adresse.PostalAddressRepository;
import com.afklm.rigui.dao.adresse.PostalAddressToNormalizeRepository;
import com.afklm.rigui.dao.reference.PaysRepository;
import com.afklm.rigui.dao.reference.RefProvinceRepository;
import com.afklm.rigui.dto.adresse.PostalAddressDTO;
import com.afklm.rigui.dto.adresse.Usage_mediumDTO;
import com.afklm.rigui.entity.adresse.FormalizedAdr;
import com.afklm.rigui.entity.adresse.PostalAddress;
import com.afklm.rigui.entity.adresse.PostalAddressToNormalize;
import com.afklm.rigui.entity.reference.Pays;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class PostalAddressUS {

	/** logger */
	private static final Log log = LogFactory.getLog(PostalAddressUS.class);

	/* PROTECTED REGION ID(_DES8EKLhEeSXNpATSKyi0Q u var) ENABLED START */
	// add your custom variables here if necessary

	@Autowired
    private ApplicationContext context;
//QAPortType
	/* PROTECTED REGION END */

	/** references on associated DAOs */
	@Autowired
	private FormalizedAdrRepository formalizedAdrRepository;

	/** references on associated DAOs */
	@Autowired
	private PaysRepository paysRepository;

	/** references on associated DAOs */
	@Autowired
	private PostalAddressRepository postalAddressRepository;

	/** references on associated DAOs */
	@Autowired
	private PostalAddressToNormalizeRepository postalAddressToNormalizeRepository;

	/** references on associated DAOs */
	@Autowired
	private RefProvinceRepository refProvinceRepository;

	@Autowired
	private DqeUS dqeUs;
	
	private final String ISI_APP_CODE = "ISI";
	private final String BDC_APP_CODE = "BDC";
	
	public PostalAddressToNormalizeRepository getPostalAddressToNormalizeRepository() {
		return postalAddressToNormalizeRepository;
	}

	public void setPostalAddressToNormalizeRepository(
			PostalAddressToNormalizeRepository postalAddressToNormalizeRepository) {
		this.postalAddressToNormalizeRepository = postalAddressToNormalizeRepository;
	}

	/**
	 * Initialize the CountryCodeMap for the ISO2-ISO3 conversion
	 */
	public PostalAddressUS() {
	}

	public boolean isUsedBy(PostalAddressDTO addressToCheck, String appCode) {
		boolean result = false;
		
		if (addressToCheck != null && addressToCheck.getUsage_mediumdto() != null) {
			for (Usage_mediumDTO usage : addressToCheck.getUsage_mediumdto()) {
				if (appCode.equalsIgnoreCase(usage.getScode_application())) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public void prepareNewBdcAddressUsage(PostalAddressDTO newAddress, PostalAddressDTO oldAddress) {
		// Historize old address if no ISI usage 
		if (isUsedBy(oldAddress, ISI_APP_CODE)) {
			// Remove BDC usage only
			for (Usage_mediumDTO usage: oldAddress.getUsage_mediumdto()) {
				if (BDC_APP_CODE.equalsIgnoreCase(usage.getScode_application())) {
					oldAddress.getUsage_mediumdto().remove(usage);
				}
			}
		}
		else {
			oldAddress.setNumeroUsage(null);
			oldAddress.getUsage_mediumdto().clear();
			oldAddress.setSstatut_medium(MediumStatusEnum.HISTORIZED.toString());
		}
	}

	public void prepareNewIsiAddressUsage(PostalAddressDTO newAddress, PostalAddressDTO oldAddress) {
		
		// Same code medium = historize old address if no BDC usage 
		if (newAddress.getScode_medium().equalsIgnoreCase(oldAddress.getScode_medium())) {
			if (isUsedBy(oldAddress, BDC_APP_CODE)) {
				// Remove ISI usage only as BDC usage exists
				for (Usage_mediumDTO usage: oldAddress.getUsage_mediumdto()) {
					if (ISI_APP_CODE.equalsIgnoreCase(usage.getScode_application())) {
						oldAddress.getUsage_mediumdto().remove(usage);
					}
				}
			}
			else {
				oldAddress.setNumeroUsage(null);
				oldAddress.getUsage_mediumdto().clear();
				oldAddress.setSstatut_medium(MediumStatusEnum.HISTORIZED.toString());
			}
		}
		else { // switch usage to complementary as they have different medium code
			for (Usage_mediumDTO umd: oldAddress.getUsage_mediumdto()) {
				if (ISI_APP_CODE.equalsIgnoreCase(umd.getScode_application())) {
					umd.setSrole1("C");
					umd.setInum(01);
				}
			}
		}
	}
	/* PROTECTED REGION END */
}
