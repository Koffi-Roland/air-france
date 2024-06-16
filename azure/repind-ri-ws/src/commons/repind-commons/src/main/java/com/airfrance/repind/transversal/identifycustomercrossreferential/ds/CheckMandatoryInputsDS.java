package com.airfrance.repind.transversal.identifycustomercrossreferential.ds;


import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessExceptionDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO;
import org.springframework.stereotype.Service;

/**
 * Check mandatory inputs 
 * @author t950700
 *
 */
@Service("IdentifierCheckMandatoryInputsDS")
public class CheckMandatoryInputsDS extends AbstractDS
{

	
	
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	
	/**
	 * Check the web service mandatory inputs
	 */
	public void checkMandatoryInputs(RequestDTO requestDTO) throws BusinessExceptionDTO
	{
		handleMissingIndex(requestDTO);
		handleMissingProcessType(requestDTO);
		handleMissingRequestor(requestDTO);
		handleMissingSearchType(requestDTO);
		handleMissingPhone(requestDTO);
		handleMissingIndividualCheck(requestDTO);
		handleMissingResponseType(requestDTO);
	}


	/*===============================================*/
	/*               PRIVATE METHODS                 */
	/*===============================================*/
	
	/**
	 * Handles missing continuity index
	 * @param requestDTO
	 * @throws BusinessException
	 */
	private void handleMissingIndex(RequestDTO requestDTO) throws BusinessExceptionDTO 
	{
		/*
		 * Application parameters: 
		 *   - index
		 *   - page size
		 *   - max allowed results for a manual search
		 */
		
		int index = requestDTO.getIndex();		
		int pageSize = requestDTO.getQueryPageSize();
		int maxResults = requestDTO.getQueryMaxResults();
		
		boolean coherentIndexIndicated = false;
		
		if(!BuildConditionDS.isSearchTypeIndividualConditionSet(requestDTO))
		{
			if(BuildConditionDS.isIndexConditionSet(requestDTO))
			{
				coherentIndexIndicated = true;
			}else {
				index = 1;
				requestDTO.setIndex(index);
				coherentIndexIndicated = true;
			}
			
			if(!coherentIndexIndicated)
			{
				throwBusinessException(getIncoherentIndexCode(), getIncoherentIndexMessage());
			}
		} else {
			index = 1;
			requestDTO.setIndex(index);
			coherentIndexIndicated = true;
		}
		
		/*
		 * Calculating first element in the page
		 */
		int firstElementInPage = (index - 1) * pageSize;
		
		/*
		 * If the index is beyond the maxResults limits,
		 *  we throw a business exception.
		 *  else we load the firms
		 */
		if((firstElementInPage + 1) >= maxResults)
		{
			throwBusinessException(getIncoherentIndexCode(), getIncoherentIndexMessage());
		}
		
	}
	
	/**
	 * Handles missing process type
	 * @param requestDTO
	 * @throws BusinessException
	 */
	private void handleMissingProcessType(RequestDTO requestDTO) throws BusinessExceptionDTO 
	{
		boolean processTypeIndicated = false;
		
		if(!BuildConditionDS.isSearchTypeIndividualConditionSet(requestDTO))
		{
			if(BuildConditionDS.isProcessTypeConditionSet(requestDTO))
			{
				processTypeIndicated = true;
			}else {
				
				processTypeIndicated = true;
				if ( requestDTO.getContext().getResponseType() != null ) {
					if ( requestDTO.getContext().getResponseType().equals("U")) {
						requestDTO.setProcessType("A");
					} else if ( requestDTO.getContext().getResponseType().equals("F")) {
						requestDTO.setProcessType("M");
					}
				}
			}		
			
			if(!processTypeIndicated)
			{
				throwBusinessException(getMissingProcessTypeCode(), getMissingProcessTypeMessage());
			}
			
		} else {
			
			processTypeIndicated = true;
			if ( requestDTO.getContext().getResponseType() != null ) {
				if ( requestDTO.getContext().getResponseType().equals("U")) {
					requestDTO.setProcessType("A");
				} else if ( requestDTO.getContext().getResponseType().equals("F")) {
					requestDTO.setProcessType("M");
				}
			}
		}		
		
	}
	
	/**
	 * Checks if the mandatory inputs Channel/Application code
	 *  have been sent by the client
	 * @param requestDTO
	 * @throws BusinessException
	 */
	private void handleMissingRequestor(RequestDTO requestDTO) throws BusinessExceptionDTO
	{	
		boolean isChannelGiven = false;
		boolean isApplicationCodeGiven = false;
		
		if(BuildConditionDS.isChannelConditionSet(requestDTO))
		{
			isChannelGiven = true;
		}
		
		if(BuildConditionDS.isApplicationCodeConditionSet(requestDTO))
		{
			isApplicationCodeGiven = true;
		}
		
		if(!isChannelGiven)
		{
			throwBusinessException(getMissingChannelCode(), getMissingChannelMessage());
		}
		
		if(!isApplicationCodeGiven)
		{
			throwBusinessException(getMissingApplicationCodeCode(), getMissingApplicationCodeMessage());
		}
	}
	
	/**
	 * Checks if the mandatory SearchType
	 *  have been sent by the client
	 * @param requestDTO
	 * @throws BusinessException
	 */
	private void handleMissingSearchType(RequestDTO requestDTO) throws BusinessExceptionDTO
	{	
		boolean isSearchTypeGiven = false;
		
		if(BuildConditionDS.isSearchTypeConditionSet(requestDTO))
		{
			isSearchTypeGiven = true;
		}
		
		if(!isSearchTypeGiven)
		{
			throwBusinessException(getMissingTypeSearchCode(), getMissingTypeSearchMessage());
		}
	}
	
	
	/**
	 * 
	 * @param requestDTO
	 * @throws BusinessExceptionDTO
	 */
	private void handleMissingPhone(RequestDTO requestDTO) throws BusinessExceptionDTO 
	{
		if((BuildConditionDS.isPhoneConditionSet(requestDTO))
				&&	(! BuildConditionDS.isPhoneCountryCodeConditionSet(requestDTO)))
		{
			throwBusinessException(getIncoherentPhoneCode(), getIncoherentPhoneMessage());
		}
		
		if((BuildConditionDS.isPhoneCountryCodeConditionSet(requestDTO))
				&&	(! BuildConditionDS.isPhoneConditionSet(requestDTO)))
		{
			throwBusinessException(getIncoherentPhoneCode(), getIncoherentPhoneMessage());
		}
		
	}
	
	
	/**
	 * Missing Input check in SearchIndividualByMultiCriteria
	 * @param requestDTO
	 */
	private void handleMissingIndividualCheck(RequestDTO requestDTO) throws BusinessExceptionDTO
	{
		if(BuildConditionDS.isSearchTypeIndividualConditionSet(requestDTO))
		{
			if((BuildConditionDS.isSearchLastNameIndividualConditionSet(requestDTO))
					&&	(BuildConditionDS.isSearchFirstNameIndividualConditionSet(requestDTO)))
			{
				if((! BuildConditionDS.isSearchCountryCodeConditionSet(requestDTO))
						&&	(! BuildConditionDS.isEmailConditionSet(requestDTO))
						&&	(! BuildConditionDS.isPhoneConditionSet(requestDTO))
						&&	(! BuildConditionDS.isBirthdateConditionSet(requestDTO)))
				{
					throwBusinessException(getMissingCountryCodeCode(), getMissingCountryCodeMessage());
				}
			}	
		}
		
		
		if(BuildConditionDS.isSearchTypeIndividualAgencyConditionSet(requestDTO))
		{
			if((BuildConditionDS.isSearchLastNameIndividualConditionSet(requestDTO))
					&&	(BuildConditionDS.isSearchFirstNameIndividualConditionSet(requestDTO)))
			{
				if((! BuildConditionDS.isSearchCountryCodeConditionSet(requestDTO))
						&&	(! BuildConditionDS.isEmailConditionSet(requestDTO))
						&&	(! BuildConditionDS.isPhoneConditionSet(requestDTO)))
				{
					throwBusinessException(getMissingCountryCodeCode(), getMissingCountryCodeMessage());
				}
			}
		}
		
		
		if(BuildConditionDS.isSearchTypeIndividualFirmConditionSet(requestDTO))
		{
			if((BuildConditionDS.isSearchLastNameIndividualConditionSet(requestDTO))
					&&	(BuildConditionDS.isSearchFirstNameIndividualConditionSet(requestDTO)))
			{
				if((! BuildConditionDS.isSearchCountryCodeConditionSet(requestDTO))
						&&	(! BuildConditionDS.isEmailConditionSet(requestDTO))
						&&	(! BuildConditionDS.isPhoneConditionSet(requestDTO)))
				{
					throwBusinessException(getMissingCountryCodeCode(), getMissingCountryCodeMessage());
				}
			}
		}
	}
	
	/**
	 * Checks if the mandatory ResponseType
	 *  have been sent by the client
	 * @param requestDTO
	 * @throws BusinessException
	 */
	private void handleMissingResponseType(RequestDTO requestDTO) throws BusinessExceptionDTO
	{	
		boolean isResponseTypeGiven = false;
		
		if(BuildConditionDS.isResponseTypeConditionSet(requestDTO))
		{
			isResponseTypeGiven = true;
		}
		
		if(!isResponseTypeGiven)
		{
			throwBusinessException(getMissingResponseTypeCode(), getMissingResponseTypeMessage());
		}
		
	}
	
		
}
