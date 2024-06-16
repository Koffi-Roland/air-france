package com.airfrance.repind.transversal.identifycustomercrossreferential.ds;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.*;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders.RequestSearchIndividualFirmDTOBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Search individual and firms domain service
 * @author t950700
 *
 */
@Service
public class SearchIndividualFirmDS extends AbstractDS {

	/*==========================================*/
	/*                                          */
	/*           INJECTED BEANS                 */
	/*                                          */
	/*==========================================*/
	
	/*
	 * SearchIndividualOnMultiCriteria domain service
	 */
	@Autowired
	private SearchIndividualDS searchIndividualDS = null;
	
	/*
	 * SearchFirmOnMultiCriteria domain service
	 */
	@Autowired
	@Qualifier("IdentifierSearchFirmDS")
	private SearchFirmDS searchFirmDSBean = null;
	
	/*
	 * Get Individual's associated firms
	 */
	@Autowired
	private ReadIndividualMembersDS readIndividualMembersDS = null;
	
	/*
	 * Builds SearchFirmOnMultiCriteria requestDTO from IdentifyCustomerCrossReferential requestDTO
	 */
	@Autowired
	private RequestSearchIndividualFirmDTOBuilder requestSearchIndividualFirmDTOBuilder;
	
	
	/*==========================================*/
	/*                                          */
	/*                 LOGGER                   */
	/*                                          */
	/*==========================================*/
	
	private static Log LOGGER  = LogFactory.getLog(SearchIndividualFirmDS.class);
	
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
	public ResponseDTO searchIndividualFirm(RequestDTO requestDTO, ResponseDTO responseDTO) throws BusinessExceptionDTO, SystemException
	{
		try
		{
			/*
			 * WE START BY LOOKING FOR INDIVIDUALS
			 */
			LOGGER.info("IdentifyCustomerCrossReferential - LOOKING FOR INDIVIDUALS");
			try
			{
				if(responseDTO == null) {
					responseDTO = searchIndividualDS.searchIndividual(requestDTO);
				}
			} 
			catch (BusinessExceptionDTO e) 
			{
				// IF A BUSINESS EXCEPTION IS RETURNED BY SEARCH INDIVIDUAL
				//  IT MEANS THAT NO INDIVIDUAL FOUND - 
				// WE GO AHEAD AND LOOK FOR FIRMS ONLY
				
				LOGGER.info("IdentifyCustomerCrossReferential - NO INDIVIDUAL FOUND - LOOKING FOR FIRMS ONLY");
				try 
				{
					responseDTO = searchFirmDSBean.searchFirm(requestDTO);
					
					if(BuildConditionDS.areMultipleCorporatesFound(responseDTO))
					{
						/*
						 * If multiple firms found
						 */
						if(BuildConditionDS.isResponseTypeUniqueConditionSet(requestDTO))
						{
							/*
							 * UNIQUE result asked
							 */
							LOGGER.info("IdentifyCustomerCrossReferential - EXCEPTION: MULTIPLE FIRMS FOUND/UNIQUE RESULT ASKED");
							throwBusinessException(getMultipleResultsCode(), getMultipleResultsMessage());
						}
						else
						{
							/*
							 * FULL result asked
							 */
							// responseDTO is returned as it is
							LOGGER.info("IdentifyCustomerCrossReferential - MULTIPLE FIRMS RETURNED");
						}
					}
					else
					{
						/*
						 * Only one firm found
						 */
						// responseDTO is returned as it is
						LOGGER.info("IdentifyCustomerCrossReferential - ONE FIRM IS RETURNED");
					}
					
				} 
				catch (BusinessExceptionDTO e1) {
					// IF A BUSINESS EXCEPTION IS RETURNED BY SEARCH FIRM
					//  IT MEANS THAT NO FIRM FOUND - 
					// WE THROW THE BUSINESS EXCEPTION
					LOGGER.info("IdentifyCustomerCrossReferential - NEITHER INDIVIDUALS NOR FIRMS FOUND");
					throwBusinessException(getNoIndividualsNeitherFirmsReturnCode(), getNoIndividualsNeitherFirmsReturnMessage());
				}
			} 
			
			
			if(BuildConditionDS.isOnlyOneIndividualFound(responseDTO))
			{
				/*
				 * If only one individual is found
				 */
				// We look for associated firms
				LOGGER.info("IdentifyCustomerCrossReferential - ONE INDIVIDUAL IS FOUND - LOOKING FOR ASSOCIATED FIRMS");
				
				ResponseDTO responseAssociatedFirms = new ResponseDTO();
				int responseAssociatedFirmsCount = 0;
				List<CustomerDTO> customersList = new ArrayList<CustomerDTO>();
				Set<CorporateDTO> individualCorporates = readIndividualMembersDS.readIndividualCorporates(requestDTO, responseDTO);
				if((individualCorporates != null)
						&&	(!individualCorporates.isEmpty()))
				{
					labelCorporateLoop:  
					for(CorporateDTO corporateDTO : individualCorporates)
					{
						ResponseDTO responseDTOAssociatedFirm = null;
						RequestDTO searchFirmRequestDTO = requestSearchIndividualFirmDTOBuilder.build(requestDTO, corporateDTO);
						try
						{
							responseDTOAssociatedFirm = searchFirmDSBean.searchFirm(searchFirmRequestDTO);
						}
						catch (BusinessExceptionDTO e) 
						{
							// IF A BUSINESS EXCEPTION IS RETURNED BY SEARCH FIRM
							//  IT MEANS DATA IN MEMBER TABLE IS INCOHERENT - WE DON'T TREAT
							// THE ELEMENT AND CONTINUE
							continue labelCorporateLoop;
						}
						if((responseDTOAssociatedFirm != null)
								&&	(responseDTOAssociatedFirm.getCustomers() != null)
								&&	(!responseDTOAssociatedFirm.getCustomers().isEmpty()))
						{
							responseDTOAssociatedFirm.getCustomers().get(0).getCorporate().setMemberCorporate(corporateDTO.getMemberCorporate());
							customersList.add(responseDTOAssociatedFirm.getCustomers().get(0));
							responseAssociatedFirmsCount += 1;
							if(responseAssociatedFirmsCount > 1)
							{
								break labelCorporateLoop;
							}
						}
								
					}
					responseAssociatedFirms.setCustomers(customersList);
				}
				
				
				
				if(BuildConditionDS.areMultipleCorporatesFound(responseAssociatedFirms))
				{
					/*
					 * IF MULTIPLE FIRMS ASSOCIATED TO INDIVIDUAL
					 * WE RETURN ONLY INDIVIDUAL DATA - responseDTO stays as it is
					 */
				}
				if(BuildConditionDS.isOnlyOneCorporateFound(responseAssociatedFirms))
				{
					/*
					 * IF ONE CORPORATE IS FOUND, WE ADD IT TO THE RESPONSE
					 */
					CustomerDTO customerDTO = new CustomerDTO();
					customerDTO.setCorporate(responseAssociatedFirms.getCustomers().get(0).getCorporate());
					responseDTO.getCustomers().add(customerDTO);
				}
					
			}
			else if(BuildConditionDS.areMultipleIndividualsFound(responseDTO))
			{
				/*
				 * If many individuals are found
				 */
				// Nothing is done => responseDTO already contains individuals list
				
			}
			else
			{
				/*
				 * If no individuals are found
				 */
				// We look for firms
				try {
					responseDTO = searchFirmDSBean.searchFirm(requestDTO);
				} 
				catch (BusinessExceptionDTO e) 
				{
					/*
					 * NEITHER INDIVIDUALS NOR FIRMS FOUND
					 */
					throwBusinessException(getNoIndividualsNeitherFirmsReturnCode(), getNoIndividualsNeitherFirmsReturnMessage());
				}
				
				if(BuildConditionDS.areMultipleCorporatesFound(responseDTO))
				{
					/*
					 * If multiple firms found
					 */
					if(BuildConditionDS.isResponseTypeUniqueConditionSet(requestDTO))
					{
						/*
						 * UNIQUE result asked
						 */
						throwBusinessException(getMultipleResultsCode(), getMultipleResultsMessage());
					}
					else
					{
						/*
						 * FULL result asked
						 */
						// responseDTO is returned as it is
					}
				}
				else
				{
					/*
					 * Only one firm found
					 */
					// responseDTO is returned as it is
				}
			
			}
		}
		catch (SystemException e) {
			throw e;
		}
		
		
		return responseDTO;
	}
}
