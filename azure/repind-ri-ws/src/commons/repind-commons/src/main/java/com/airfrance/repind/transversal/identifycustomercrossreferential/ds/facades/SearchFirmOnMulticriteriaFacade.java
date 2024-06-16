package com.airfrance.repind.transversal.identifycustomercrossreferential.ds.facades;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.CheckMultipleResultsDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.SearchFirmDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessExceptionDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * IdentifyCustomerCrossReferential - SearchFirmOnMultiCriteria facade
 * @author t950700
 *
 */
@Service
public class SearchFirmOnMulticriteriaFacade {
	
	/*==========================================*/
	/*                                          */
	/*             INJECTED BEANS               */
	/*                                          */
	/*==========================================*/

	
	/*
	 * Check multiple results - (responseType="UNIQUE" and nbResults > 1) => throwsBusinessException
	 */
	@Autowired
	private CheckMultipleResultsDS checkMultipleResultsDSBean = null;
	
	/*
	 * SearchFirmOnMultiCriteria domain service
	 */
	@Autowired
	@Qualifier("IdentifierSearchFirmDS")
	private SearchFirmDS searchFirmDSBean = null;
	
	
	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/

	
	/**
	 * Executes SearchFirmOnMultiCriteria
	 * @param requestDTO
	 * @return responseDTO
	 * @throws SystemException 
	 * @throws BusinessExceptionDTO
	 */
	public ResponseDTO searchFirm(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException
	{

		/*
		 * Executes Search
		 */
		ResponseDTO responseDTO = searchFirmDSBean.searchFirm(requestDTO);
		
		/*
		 * Check multiple results
		 */
		checkMultipleResultsDSBean.checkMultipleResults(requestDTO, responseDTO);
		
		return responseDTO;
	}
	
}
