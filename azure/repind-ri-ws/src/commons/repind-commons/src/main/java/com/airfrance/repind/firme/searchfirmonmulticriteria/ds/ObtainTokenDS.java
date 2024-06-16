package com.airfrance.repind.firme.searchfirmonmulticriteria.ds;


import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.RequestDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.ResponseDTO;
import com.airfrance.repind.service.internal.unitservice.firm.PersonneMoraleUS;
import org.springframework.stereotype.Service;

@Service
public class ObtainTokenDS  extends AbstractDS implements IObtainTokenDS {

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
		 *      BY OTHER APPLICATIONS. 
	 * @throws InvalidParameterException 
		*/
	public void readToken(RequestDTO requestDTO, ResponseDTO responseDTO) throws InvalidParameterException {

		if(responseDTO == null){
			throw new InvalidParameterException("responseDTO must not be null");
		}
		if (((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))
				&& 	(BuildConditionsDS.isApplicationCodeConditionSet(requestDTO))	
				&& 	(BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& 	(BuildConditionsDS.isFirmTypeConditionSet(requestDTO))
				&&  (!"A".equalsIgnoreCase(requestDTO.getIdentity().getFirmType()))
				/*&& 	(responseDTO.getFirms() != null)
				&&	(responseDTO.getTotalNumber() <= tokenLimit)*/)
		{
			PersonneMoraleUS personneMoraleUS = new PersonneMoraleUS();
			
			String tokenKey = personneMoraleUS.generateTokenKey(requestDTO.getIdentity().getName(), 
					requestDTO.getIdentity().getFirmType(),
					requestDTO.getContacts().getPostalAddressBloc().getCountryCode(),
					requestDTO.getRequestor().getApplicationCode());
			
			responseDTO.setToken(tokenKey);
		}
		else
		{
			responseDTO.setToken("");
		}
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
