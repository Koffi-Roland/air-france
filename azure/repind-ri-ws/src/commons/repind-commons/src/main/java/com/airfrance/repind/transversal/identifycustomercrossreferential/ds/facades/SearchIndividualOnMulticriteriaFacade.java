package com.airfrance.repind.transversal.identifycustomercrossreferential.ds.facades;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.CheckMultipleResultsDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.SearchIndividualDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessExceptionDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchIndividualOnMulticriteriaFacade {
	
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
	 * SearchAgencyOnMultiCriteria domain service
	 */
	@Autowired
	private SearchIndividualDS searchIndividualDS = null;
	
	
	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/
	/**
	 * Executes SearchIndividualOnMultiCriteria
	 * @param requestDTO
	 * @return responseDTO
	 * @throws SystemException 
	 * @throws BusinessExceptionDTO
	 */
	public ResponseDTO searchIndividual(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException
	{

		
		/*
		 * Executes Search
		 */
		ResponseDTO responseDTO = searchIndividualDS.searchIndividual(requestDTO);
		
		/*
		 * Check multiple results
		 */
		checkMultipleResultsDSBean.checkMultipleResults(requestDTO, responseDTO);
		
		return responseDTO;
	}

}
