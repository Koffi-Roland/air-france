package com.airfrance.repind.transversal.identifycustomercrossreferential.ds;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.*;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders.RequestSearchIndividualAgencyDTOBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SearchIndividualAgencyDS extends AbstractDS {

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
	 * SearchAgencyOnMultiCriteria domain service
	 */
	@Autowired
	@Qualifier("IdentifierSearchAgencyDS")
	private SearchAgencyDS searchAgencyDSBean = null;
	
	/*
	 * Get Individual's associated agencies
	 */
	@Autowired
	private ReadIndividualMembersDS readIndividualMembersDS = null;
	
	/*
	 * Builds SearchAgencyOnMultiCriteria requestDTO from IdentifyCustomerCrossReferential requestDTO
	 */
	@Autowired
	private RequestSearchIndividualAgencyDTOBuilder requestSearchIndividualAgencyDTOBuilder;


	/*==========================================*/
	/*                                          */
	/*                 LOGGER                   */
	/*                                          */
	/*==========================================*/
	
	private static Log LOGGER  = LogFactory.getLog(SearchIndividualAgencyDS.class);
	
	
	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Executes SearchIndividualByMultiCriteria and get its agencies through calling SearchAgencyOnMultiCriteria
	 * @param requestDTO
	 * @return responseDTO
	 * @throws BusinessExceptionDTO 
	 * @throws SystemException 
	 */
	public ResponseDTO searchIndividualAgency(RequestDTO requestDTO, ResponseDTO responseDTO) throws BusinessExceptionDTO, SystemException
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
				// WE GO AHEAD AND LOOK FOR AGENCIES ONLY
				
				LOGGER.info("IdentifyCustomerCrossReferential - NO INDIVIDUAL FOUND - LOOKING FOR AGENCIES ONLY");
				try 
				{
					responseDTO = searchAgencyDSBean.searchAgency(requestDTO);
					
					if(BuildConditionDS.areMultipleAgenciesFound(responseDTO))
					{
						/*
						 * If multiple agencies found
						 */
						if(BuildConditionDS.isResponseTypeUniqueConditionSet(requestDTO))
						{
							/*
							 * UNIQUE result asked
							 */
							LOGGER.info("IdentifyCustomerCrossReferential - EXCEPTION: MULTIPLE AGENCIES FOUND/UNIQUE RESULT ASKED");
							throwBusinessException(getMultipleResultsCode(), getMultipleResultsMessage());
						}
						else
						{
							/*
							 * FULL result asked
							 */
							// responseDTO is returned as it is
							LOGGER.info("IdentifyCustomerCrossReferential - MULTIPLE AGENCIES RETURNED");
						}
					}
					else
					{
						/*
						 * Only one agency found
						 */
						// responseDTO is returned as it is
						LOGGER.info("IdentifyCustomerCrossReferential - ONE AGENCY IS RETURNED");
					}
					
				} 
				catch (BusinessExceptionDTO e1) {
					// IF A BUSINESS EXCEPTION IS RETURNED BY SEARCH AGENCY
					//  IT MEANS THAT NO AGENCY FOUND - 
					// WE THROW THE BUSINESS EXCEPTION
					LOGGER.info("IdentifyCustomerCrossReferential - NEITHER INDIVIDUALS NOR AGENCIES FOUND");
					throwBusinessException(getNoIndividualsNeitherAgenciesReturnCode(), getNoIndividualsNeitherAgenciesReturnMessage());
				}
			} 
			
			
			if(BuildConditionDS.isOnlyOneIndividualFound(responseDTO))
			{
				/*
				 * If only one individual is found
				 */
				// We look for associated agencies
				LOGGER.info("IdentifyCustomerCrossReferential - ONE INDIVIDUAL IS FOUND - LOOKING FOR ASSOCIATED AGENCIES");
				
				ResponseDTO responseAssociatedAgencies = new ResponseDTO();
				int responseAssociatedAgenciesCount = 0;
				List<CustomerDTO> customersList = new ArrayList<CustomerDTO>();
				Set<AgencyDTO> individualAgencies = readIndividualMembersDS.readIndividualAgencies(requestDTO, responseDTO);
				if((individualAgencies != null)
						&&	(!individualAgencies.isEmpty()))
				{
					labelAgencyLoop:  
					for(AgencyDTO agencyDTO : individualAgencies)
					{
						ResponseDTO responseDTOAssociatedAgency = null;
						RequestDTO searchAgencyRequestDTO = requestSearchIndividualAgencyDTOBuilder.build(requestDTO, agencyDTO);
						try
						{
							responseDTOAssociatedAgency = searchAgencyDSBean.searchAgency(searchAgencyRequestDTO);
						}
						catch (BusinessExceptionDTO e) 
						{
							// IF A BUSINESS EXCEPTION IS RETURNED BY SEARCH AGENCY
							//  IT MEANS DATA IN MEMBER TABLE IS INCOHERENT - WE DON'T TREAT
							// THE ELEMENT AND CONTINUE
							continue labelAgencyLoop;
						}
						if((responseDTOAssociatedAgency != null)
								&&	(responseDTOAssociatedAgency.getCustomers() != null)
								&&	(!responseDTOAssociatedAgency.getCustomers().isEmpty()))
						{
							responseDTOAssociatedAgency.getCustomers().get(0).getAgency().setMemberAgency(agencyDTO.getMemberAgency());
							customersList.add(responseDTOAssociatedAgency.getCustomers().get(0));
							responseAssociatedAgenciesCount += 1;
							if(responseAssociatedAgenciesCount > 1)
							{
								break labelAgencyLoop;
							}
						}
								
					}
					responseAssociatedAgencies.setCustomers(customersList);
				}
				
				
				
				if(BuildConditionDS.areMultipleAgenciesFound(responseAssociatedAgencies))
				{
					/*
					 * IF MULTIPLE AGENCIES ASSOCIATED TO INDIVIDUAL
					 * WE RETURN ONLY INDIVIDUAL DATA - responseDTO stays as it is
					 */
				}
				if(BuildConditionDS.isOnlyOneAgencyFound(responseAssociatedAgencies))
				{
					/*
					 * IF ONE AGENCY IS FOUND, WE ADD IT TO THE RESPONSE
					 */
					CustomerDTO customerDTO = new CustomerDTO();
					customerDTO.setAgency(responseAssociatedAgencies.getCustomers().get(0).getAgency());
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
				// We look for agencies
				try {
					responseDTO = searchAgencyDSBean.searchAgency(requestDTO);
				} 
				catch (BusinessExceptionDTO e) 
				{
					/*
					 * NEITHER INDIVIDUALS NOR AGENCIES FOUND
					 */
					throwBusinessException(getNoIndividualsNeitherAgenciesReturnCode(), getNoIndividualsNeitherAgenciesReturnMessage());
				}
				
				if(BuildConditionDS.areMultipleAgenciesFound(responseDTO))
				{
					/*
					 * If multiple agencies found
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
					 * Only one agency found
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
