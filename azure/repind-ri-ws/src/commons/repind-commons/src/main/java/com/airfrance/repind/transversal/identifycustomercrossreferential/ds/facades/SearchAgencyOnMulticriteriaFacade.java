package com.airfrance.repind.transversal.identifycustomercrossreferential.ds.facades;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.CheckMultipleResultsDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.SearchAgencyDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessExceptionDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * IndetifyCustomerCrossreferential - Search agency facade
 * @author t950700
 *
 */
@Service
public class SearchAgencyOnMulticriteriaFacade {
	
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
	@Qualifier("IdentifierSearchAgencyDS")
	private SearchAgencyDS searchAgencyDSBean = null;
	
	
	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/
	/**
	 * Executes SearchAgencyOnMultiCriteria
	 * @param requestDTO
	 * @return responseDTO
	 * @throws SystemException 
	 * @throws BusinessExceptionDTO
	 */
	public ResponseDTO searchAgency(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException
	{

		/*
		 * Executes Search
		 */
		ResponseDTO responseDTO = searchAgencyDSBean.searchAgency(requestDTO);
		
		/*
		 * Check multiple results
		 */
		checkMultipleResultsDSBean.checkMultipleResults(requestDTO, responseDTO);
		
		
		return responseDTO;
	}
}
