package com.airfrance.repind.util;

import com.airfrance.repind.dto.individu.adh.individualinformation.BlocAdressS09424DTO;
import com.airfrance.repind.dto.individu.adh.individualinformation.BlocTelecomS09424DTO;
import com.airfrance.repind.dto.individu.adh.individualinformation.IndividuDTO;
import com.airfrance.repind.dto.individu.myaccountcustomerdata.MyAccountCustomerDataResponseDTO;
import com.airfrance.repind.dto.individu.myaccountcustomerdata.MyAccountDataDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

/**
 * 
 * @author e6349052
 *
 */
public class GaugeUtilsADH {

	/** logger */
	private static Log LOGGER  = LogFactory.getLog(GaugeUtilsADH.class);
	
	
	
	/**
	 * Private constructor.
	 */
	private GaugeUtilsADH() {
		
	}
	
	

	/**
	 * @param response
	 * @param percentageMarketingData, percentage between 0% and 40%
	 * @param percentagePaymentPref, percentage between 0% and 25%
	 * @return
	 */
	public static Integer calculPercentageFullProfil(MyAccountCustomerDataResponseDTO response, int percentageMarketingData, int percentagePaymentPref) {

		LOGGER.debug("calculPercentageFullProfil");
		Integer percentage = new Integer(0);
		
		//NAMES & EMAIL : 10%
		percentage+=calculNameCivilityInfos(response.getMyaccountdatadto(), response.getIndividudto());
		
		//DATE OF DIRTH : 5%
		percentage+=calculIndividualInfos(response.getIndividudto());
		
		//ADDRESS & PERSONAL INFORMATION : 10%
		percentage+=calculPostalAddress(response.getBlocadresss09424dto());
		
		//PHONE NUMBER : 10%
		percentage+=calculTelecom(response.getBloctelecoms09424dto());

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
		if(individuDTO != null && individuDTO.getInfosindividu() != null &&
				individuDTO.getInfosindividu().getDateNaissance() != null) {
			percentage+=5;
		}
		
		return percentage;
	}
	
	/**
	 * @param individuDTO
	 * @return
	 */
	public static int calculNameCivilityInfos(MyAccountDataDTO myaccountDTO, IndividuDTO individuDTO) {
		int percentage = 0;
		
		//LAST NAME, FIRST NAME, CIVILITY, MAIL  : 10%
		if(individuDTO != null && individuDTO.getInfosindividu() != null && myaccountDTO != null &&
		   ((individuDTO.getInfosindividu().getNomSC() != null && individuDTO.getInfosindividu().getPrenomSC() != null) || 
		    (individuDTO.getInfosindividu().getNom() != null && individuDTO.getInfosindividu().getPrenom() != null)
		   )&&
		   individuDTO.getInfosindividu().getCivilite() != null && myaccountDTO.getEmailIdentifier() != null) {
			
			percentage+=10;
		}
		
		return percentage;
	}
	

	/**
	 * @param postalAddressDTO
	 * @return
	 */
	public static int calculPostalAddress(Set<BlocAdressS09424DTO> listPostalAddressDTO) {
		int percentage = 0;

		//ADDRESS PERSONAL INFORMATION : 10%
		if(listPostalAddressDTO != null && listPostalAddressDTO.size() > 0) {
			for(BlocAdressS09424DTO postAddressDTO : listPostalAddressDTO) {
				if(	postAddressDTO != null && 
					postAddressDTO.getAdressepostale4emeligneadrsic() != null &&
					postAddressDTO.getAdressepostale4emeligneadrsic().getNumeroRue() != null &&
					postAddressDTO.getAdressepostale4emeligneadrsic().getVille() != null &&
					postAddressDTO.getAdressepostale4emeligneadrsic().getCodePays() != null &&
					postAddressDTO.getAdressepostale4emeligneadrsic().getCodePostal() != null) {
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
	public static int calculTelecom(Set<BlocTelecomS09424DTO> listTelecomDTO) {
		int percentage = 0;

		//PHONE NUMBER : 10%
		if(listTelecomDTO != null && listTelecomDTO.size() > 0) {
			for(BlocTelecomS09424DTO telecomDTO : listTelecomDTO) {
				if(	telecomDTO != null &&
					telecomDTO.getTelecomsic() != null &&
					telecomDTO.getTelecomsic().getNumeroTelecom() != null) {
					percentage+=10;
					break;
				}
			}
		}
		return percentage;
	}
	
}
