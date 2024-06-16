package com.airfrance.repind.transversal.identifycustomercrossreferential.ds;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessErrorDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessExceptionDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ResponseDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders.RequestSearchFirmDTOBuilder;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders.ResponseSearchFirmDTOBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("IdentifierSearchFirmDS")
public class SearchFirmDS extends AbstractDS{

	/*==========================================*/
	/*                                          */
	/*             INJECTED BEANS               */
	/*                                          */
	/*==========================================*/

	/*
	 * SearchFirmOnMultiCriteria domain service
	 */
	@Autowired
	@Qualifier("FirmeSearchFirmDS")
	private com.airfrance.repind.firme.searchfirmonmulticriteria.ds.SearchFirmDS searchFirmDSBean = null;
	
	
	/*
	 * Builds SearchFirmOnMultiCriteria request from IdentifyCustomerCrossReferential request
	 */
	@Autowired
	private RequestSearchFirmDTOBuilder requestDTOBuilder = null;
	
	
	/*
	 * Builds IdentifyCustomerCrossReferential response from SearchFirmOnMultiCriteria response
	 */
	@Autowired
	private ResponseSearchFirmDTOBuilder responseBuilder = null;
	
	
	/*==========================================*/
	/*                                          */
	/*                 LOGGER                   */
	/*                                          */
	/*==========================================*/
	
	private static Log LOGGER  = LogFactory.getLog(SearchFirmDS.class);
	
	
	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/

	
	/**
	 * Executes SearchFirmOnMultiCriteria
	 * @throws SystemException 
	 */
	public ResponseDTO searchFirm(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException
	{
		ResponseDTO responseDTO = null;
		com.airfrance.repind.firme.searchfirmonmulticriteria.dto.ResponseDTO responseDTOSearchFirm = null;
		try {
			LOGGER.info("IdentifyCustomerCrossReferential - SearchFirmOnMultiCriteria | requestDTO BUILDING START");
			com.airfrance.repind.firme.searchfirmonmulticriteria.dto.RequestDTO requestDTOSearchFirm = requestDTOBuilder.build(requestDTO);
			responseDTOSearchFirm = searchFirmDSBean.searchFirm(requestDTOSearchFirm);
			LOGGER.info("IdentifyCustomerCrossReferential - SearchFirmOnMultiCriteria | response BUILDING START");
			responseDTO = responseBuilder.build(requestDTO, responseDTOSearchFirm);
		} 
		catch (com.airfrance.repind.firme.searchfirmonmulticriteria.dto.BusinessException e) {
			LOGGER.info("IdentifyCustomerCrossReferential - SearchFirmOnMultiCriteria | BUSINESS EXCEPTION", e);
			BusinessErrorDTO businessError = new BusinessErrorDTO();
			businessError.setErrorCode(e.getFaultInfo().getErrorCode());
			businessError.setFaultDescription(e.getFaultInfo().getFaultDescription());
			businessError.setMissingParameter(e.getFaultInfo().getMissingParameter());
			throw new BusinessExceptionDTO(e.getMessage(), businessError);
			
		} 
		return responseDTO;
	}
}
