package com.airfrance.repind.transversal.identifycustomercrossreferential.ds.facades;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.CheckMultipleResultsDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.SearchIndividualAgencyDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessExceptionDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Facade for Search Individual/Agency
 * @author t950700
 *
 */
@Service
public class SearchIndividualAgencyOnMultiCriteriaFacade {

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
	 * SearchIndividualAgeny domain service
	 */
	@Autowired
	private SearchIndividualAgencyDS searchIndividualAgencyDS = null;
	
	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Executes SearchIndividualByMultiCriteria and get its firm through calling SearchFirmOnMultiCriteria
	 * @param requestDTO
	 * @return responseDTO
	 * @throws BusinessExceptionDTO 
	 * @throws SystemException 
	 */
	public ResponseDTO searchIndividualAgency(RequestDTO requestDTO, ResponseDTO responseDTOToUse) throws BusinessExceptionDTO, SystemException
	{
		
		
		/*
		 * Executes Search
		 */
		ResponseDTO responseDTO = searchIndividualAgencyDS.searchIndividualAgency(requestDTO, responseDTOToUse);
		
		/*
		 * Check multiple results
		 */
		checkMultipleResultsDSBean.checkMultipleResults(requestDTO, responseDTO);
		
		return responseDTO;
	}
}
