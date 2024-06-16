package com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds;


import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.RequestDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.ResponseDTO;
import org.springframework.stereotype.Service;

/**
 * Get the token that will be sent to the client
 * @author t950700
 *
 */
@Service("agencyObtainTokenDS")
public class ObtainTokenDS  extends AbstractDS {

	/*===============================================*/
	/*               INSTANCE VARIABLES              */
	/*===============================================*/
	
	private int tokenLimit;

	
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	   /**
		 * Get a token associated to the search request
		 * Token value depends on: 
		 * 		Time
		 * 		Name of (company, group, ...)
		 *      country code
		 *      application code
		 *      
		 *      TOKEN IS USED TO ALLOW COMPANIES/FIRMS/... CREATION
		 *      BY OTHER APPLICATIONS. => A TOKEN IS RETURNED BY
		 *      SearchFirmOnMultiCriteria ONLY IF NO FIRMS 
		 *      ARE FOUND.
		*/
	public void readToken(RequestDTO requestDTO, ResponseDTO responseDTO) {

//		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))
//				&& 	(BuildConditionsDS.isApplicationCodeConditionSet(requestDTO))	
//				&& 	(BuildConditionsDS.isCountryConditionSet(requestDTO))
//				&& 	(BuildConditionsDS.isFirmTypeConditionSet(requestDTO))
//				&&	(responseDTO != null) 
//				&& 	(responseDTO.getFirms() != null)
//				&&	(responseDTO.getFirms().size() <= tokenLimit))
//		{
//			IPersonneMoraleUS personneMoraleUS = new PersonneMoraleUS();
//			
//			String tokenKey = personneMoraleUS.generateTokenKey(requestDTO.getIdentity().getName(), 
//					requestDTO.getIdentity().getFirmType(),
//					requestDTO.getContacts().getPostalAddressBloc().getCountryCode(),
//					requestDTO.getRequestor().getApplicationCode());
//			
//			responseDTO.setToken(tokenKey);
//		}
//		else
//		{
//			responseDTO.setToken("");
//		}
		responseDTO.setToken("");
	}

	/*==========================================*/
	/*                 ACCESSORS                */
	/*==========================================*/
	
	public int getTokenLimit() {
		return tokenLimit;
	}

	public void setTokenLimit(int tokenLimit) {
		this.tokenLimit = tokenLimit;
	}

}
