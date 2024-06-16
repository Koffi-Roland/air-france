package com.airfrance.repind.transversal.identifycustomercrossreferential.ds;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.BusinessException;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.facades.SearchFirmOnMulticriteriaFacade;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessErrorDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessExceptionDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ResponseDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders.RequestSearchAgencyDTOBuilder;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders.ResponseSearchAgencyDTOBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service("IdentifierSearchAgencyDS")
public class SearchAgencyDS extends AbstractDS {

	/*==========================================*/
	/*                                          */
	/*             INJECTED BEANS               */
	/*                                          */
	/*==========================================*/
	
	/*
	 * SearchAgencyOnMultiCriteria domain service 
	 */
	@Autowired
	@Qualifier("AgencySearchAgencyDS")
	private com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds.SearchAgencyDS searchAgencyDSBean = null;
	
	
	/*
	 * Builds SearchAgencyOnMultiCriteria request from IdentifyCustomerCrossReferential request
	 */
	@Autowired
	private RequestSearchAgencyDTOBuilder requestDTOBuilder = null;
	
	
	/*
	 * Builds IdentifyCustomerCrossReferential response from SearchAgencyOnMultiCriteria response
	 */
	@Autowired
	private ResponseSearchAgencyDTOBuilder responseBuilder = null;

	
	/*==========================================*/
	/*                                          */
	/*                 LOGGER                   */
	/*                                          */
	/*==========================================*/
	
	private static Log LOGGER  = LogFactory.getLog(SearchFirmOnMulticriteriaFacade.class);
	
	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/

	/**
	 * Executes SearchAgencyOnMultiCriteria
	 * @throws SystemException 
	 */
	public ResponseDTO searchAgency(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException
	{
		ResponseDTO responseDTO = null;
		com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.ResponseDTO responseDTOSearchAgency = null;
		try {
			LOGGER.info("IdentifyCustomerCrossReferential - SearchAgencyOnMultiCriteria | requestDTO BUILDING START");
			com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.RequestDTO requestDTOSearchAgency = requestDTOBuilder.build(requestDTO);
			responseDTOSearchAgency = searchAgencyDSBean.searchAgency(requestDTOSearchAgency);
			LOGGER.info("IdentifyCustomerCrossReferential - SearchAgencyOnMultiCriteria | response BUILDING START");
			responseDTO = responseBuilder.build(requestDTO, responseDTOSearchAgency);
		} 
		catch (BusinessException e) {
			LOGGER.info("SearchAgencyOnMultiCriteria | BUSINESS EXCEPTION", e);
			BusinessErrorDTO businessError = new BusinessErrorDTO();
			businessError.setErrorCode(e.getFaultInfo().getErrorCode());
			businessError.setFaultDescription(e.getFaultInfo().getFaultDescription());
			businessError.setMissingParameter(e.getFaultInfo().getMissingParameter());
			throw new BusinessExceptionDTO(e.getMessage(), businessError);
		}
		return responseDTO;
	}
	
}
