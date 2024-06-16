package com.airfrance.repind.transversal.identifycustomercrossreferential.ds;

import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessExceptionDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ResponseDTO;
import org.springframework.stereotype.Service;

/**
 * Check results number and throw exception if (responseType="UNIQUE" and nbResults > 1)
 * @author t950700
 *
 */
@Service 
public class CheckMultipleResultsDS extends AbstractDS {

	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Check results number and throw exception if (responseType="UNIQUE" and nbResults > 1)
	 */
	public void checkMultipleResults(RequestDTO requestDTO, ResponseDTO responseDTO) throws BusinessExceptionDTO
	{

		if(BuildConditionDS.isResponseTypeUniqueConditionSet(requestDTO))
		{

			/*
			 * Check multiple individuals
			 */
			if(BuildConditionDS.isSearchTypeIndividualConditionSet(requestDTO) || BuildConditionDS.isSearchTypeIndividualFirmConditionSet(requestDTO)
					|| BuildConditionDS.isSearchTypeIndividualAgencyConditionSet(requestDTO))
			{
				if(BuildConditionDS.areMultipleIndividualsFound(responseDTO))
				{
					throwBusinessException(getMultipleResultsCode(), getMultipleResultsMessage());
				}
			}
		}
		
		/*
		 * Check for no individuals found
		 * SearchIndividualByMultiCriteriaWS does not return results with relevance lower than 40% and does not throw a business exception
		 * for this case we artificially throw a businessException in IdentifyCustomerCrossRefrentialWS
		 */
		if(responseDTO.getCustomers() == null || (responseDTO.getCustomers() != null
				&&	responseDTO.getCustomers().size() < 1))
		{
			throwBusinessException(getNoResultsReturnCode(), getNoResultsReturnMessage());
		}
	}
}
