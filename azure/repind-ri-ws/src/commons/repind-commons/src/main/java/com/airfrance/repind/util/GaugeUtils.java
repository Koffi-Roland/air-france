package com.airfrance.repind.util;

import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * 
 * @author e6349052
 *
 */
public class GaugeUtils {

	/** logger */
	private static Log LOGGER  = LogFactory.getLog(GaugeUtils.class);
	
	
	
	/**
	 * Private constructor.
	 */
	private GaugeUtils() {
		
	}
	
	

	/**
	 * @param response
	 * @param percentageMarketingData, percentage between 0% and 40%
	 * @param percentagePaymentPref, percentage between 0% and 25%
	 * @return
	 */
	public static Integer calculPercentageFullProfil(IndividuDTO individuDTO, AccountDataDTO accountDataDTO, List<PostalAddressDTO> postalAddressDTOList, List<TelecomsDTO> telecomsDTOSet, int percentageMarketingData, int percentagePaymentPref) {

		LOGGER.debug("calculPercentageFullProfil");
		Integer percentage = new Integer(0);
		
		//NAMES & EMAIL : 10%
		percentage+=calculNameCivilityInfos(accountDataDTO, individuDTO);
		
		//DATE OF DIRTH : 5%
		percentage+=calculIndividualInfos(individuDTO);
		
		//ADDRESS & PERSONAL INFORMATION : 10%
		percentage+=calculPostalAddress(postalAddressDTOList);
		
		//PHONE NUMBER : 10%
		percentage+=calculTelecom(telecomsDTOSet);

		//MARKETING DATA : 40%
		percentage+=percentageMarketingData;
		
		//PAYEMENT PREF DETAILS : 25%
		percentage+=percentagePaymentPref;
		
		return percentage;
	}	
	
	/**
	 * @param individuDTO
	 * @return
	 */
	public static int calculIndividualInfos(IndividuDTO individuDTO)  {
		int percentage = 0;
		
		//DATE OF DIRTH : 5%
		if(individuDTO != null &&
				individuDTO.getDateNaissance() != null) {
			percentage+=5;
		}
		
		return percentage;
	}
	
	/**
	 * @param individuDTO
	 * @return
	 */
	public static int calculNameCivilityInfos(AccountDataDTO accountDataDTO, IndividuDTO individuDTO) {
		int percentage = 0;
		
		//LAST NAME, FIRST NAME, CIVILITY, MAIL  : 10%
		if(individuDTO != null && accountDataDTO != null &&
		   ((individuDTO.getNomSC() != null && individuDTO.getPrenomSC() != null) || 
		    (individuDTO.getNom() != null && individuDTO.getPrenom() != null)
		   )&&
		   individuDTO.getCivilite() != null && accountDataDTO.getEmailIdentifier() != null) {
			
			percentage+=10;
		}
		
		return percentage;
	}
	

	/**
	 * @param postalAddressDTO
	 * @return
	 */
	public static int calculPostalAddress(List<PostalAddressDTO> listPostalAddressDTO) {
		int percentage = 0;

		//ADDRESS PERSONAL INFORMATION : 10%
		if(listPostalAddressDTO != null && listPostalAddressDTO.size() > 0) {
			for(PostalAddressDTO postAddressDTO : listPostalAddressDTO) {
				if(	postAddressDTO != null && 
					postAddressDTO.getSno_et_rue() != null &&
					postAddressDTO.getSville() != null &&
					postAddressDTO.getScode_pays() != null &&
					postAddressDTO.getScode_postal() != null) {
					percentage+=10;
					break;
				}
			}
		}
		
		return percentage;
	}
	

	/**
	 * @param telecomDTO
	 * @return
	 */
	public static int calculTelecom(List<TelecomsDTO> listTelecomDTO) {
		int percentage = 0;

		//PHONE NUMBER : 10%
		if(listTelecomDTO != null && listTelecomDTO.size() > 0) {
			for(TelecomsDTO telecomDTO : listTelecomDTO) {
				if(	telecomDTO != null &&
					telecomDTO.getSnumero() != null) {
					percentage+=10;
					break;
				}
			}
		}
		return percentage;
	}
	
}
