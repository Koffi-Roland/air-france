package com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds;

import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.BusinessException;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.RequestDTO;
import org.springframework.stereotype.Service;



@Service("AgencyCheckMandatoryInputsDS")
public class CheckMandatoryInputsDS extends AbstractDS {

	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	
	/**
	 * Check the web service mandatory inputs
	 */
	public void checkMandatoryInputs(RequestDTO requestDTO) throws BusinessException 
	{
		handleMissingIndex(requestDTO);
		handleMissingProcessType(requestDTO);
		handleMissingIdentValue(requestDTO);
		handleMissingRequestor(requestDTO);
		handleMissingParameters(requestDTO);
		handleMissingZc(requestDTO);
	}




	/*===============================================*/
	/*               PRIVATE METHODS                 */
	/*===============================================*/
	
	/**
	 * Handles missing continuity index
	 * @param requestDTO
	 * @throws BusinessException
	 */
	private void handleMissingIndex(RequestDTO requestDTO) throws BusinessException 
	{
		/*
		 * Application parameters: 
		 *   - index
		 *   - page size
		 *   - max allowed results for a manual search
		 */
		int index = requestDTO.getQueryIndex();
		int pageSize = requestDTO.getQueryPageSize();
		int maxResults = requestDTO.getQueryMaxResults();
		
		boolean coherentIndexIndicated = false;
		if(BuildConditionsDS.isIndexConditionSet(requestDTO))
		{
			coherentIndexIndicated = true;
		}
		
		if(!coherentIndexIndicated)
		{
			throwsException(getIncoherentIndexCode(), getIncoherentIndexMessage());
		}
		
		/*
		 * Calculating first and last element in the page
		 */
		int firstElementInPage = (index - 1) * pageSize;
		int lastElementInPage = (index * pageSize);
		
		/*
		 * If the index is beyond the maxResults limits,
		 *  we throw a business exception.
		 *  else we load the firms
		 */
		if((firstElementInPage + 1) >= maxResults)
		{
			throwsException(getIncoherentIndexCode(), getIncoherentIndexMessage());
		}
		
	}
	
	/**
	 * Handles missing process type
	 * @param requestDTO
	 * @throws BusinessException
	 */
	private void handleMissingProcessType(RequestDTO requestDTO) throws BusinessException 
	{
		boolean processTypeIndicated = false;
		
		if(BuildConditionsDS.isProcessTypeConditionSet(requestDTO))
		{
			processTypeIndicated = true;
		}
		
		if(!processTypeIndicated)
		{
			throwsException(getMissingProcessTypeCode(), getMissingProcessTypeMessage());
		}
		
	}
	
	
	/**
	 * If IdentType is set, identValue has to be set
	 * @param requestDTO
	 * @throws BusinessException 
	 */
	private void handleMissingIdentValue(RequestDTO requestDTO) throws BusinessException 
	{
		if(BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
		{
			if(!BuildConditionsDS.isIdentValueConditionSet(requestDTO))
			{
				throwsException(getMissingIdentValueCode(), getMissingIdentValueMessage());
			}
		}
		if(BuildConditionsDS.isIdentValueConditionSet(requestDTO))
		{
			if(!BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
			{
				throwsException(getMissingIdentValueCode(), getMissingIdentValueMessage());
			}
		}
		
	}
	
	
	/**
	 * Checks the given ZC
	 * ZC are optional, but if given at least ZC1 to ZC4 are mandatory
	 */
	private void handleMissingZc(RequestDTO requestDTO) throws BusinessException 
	{
		boolean isZc1Given = false;
		boolean isZc2Given = false;
		boolean isZc3Given = false;
		boolean isZc4Given = false;
		
		if(BuildConditionsDS.isZC1ConditionSet(requestDTO))
		{
			 isZc1Given = true;
		}
		if(BuildConditionsDS.isZC2ConditionSet(requestDTO))
		{
			 isZc2Given = true;
		}
		if(BuildConditionsDS.isZC3ConditionSet(requestDTO))
		{
			 isZc3Given = true;
		}
		if(BuildConditionsDS.isZC4ConditionSet(requestDTO))
		{
			 isZc4Given = true;
		}
		
		if((isZc1Given == true)
				&& ((isZc2Given == false) || (isZc3Given == false) || (isZc4Given == false)))
		{
			throwsException(getMissingZcCode(), getMissingZcMessage());
		}
	}


	/**
	 * Handles the possible combinations of firmType and Inputs
	 * @param requestDTO
	 * @throws BusinessException
	 */
	private void handleMissingParameters(RequestDTO requestDTO) throws BusinessException
	{
		boolean validInputAgencies = false;
		
		
	
		if(BuildConditionsDS.isNameStrictConditionSet(requestDTO))
		{
			validInputAgencies = true;
		}
		if(BuildConditionsDS.isNameLikeConditionSet(requestDTO))
		{
			validInputAgencies = true;
		}
		if(BuildConditionsDS.isEmailConditionSet(requestDTO))
		{
			validInputAgencies = true;
		}
		if(BuildConditionsDS.isPhoneConditionSet(requestDTO))
		{
			validInputAgencies = true;
		}
		if((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&&	(BuildConditionsDS.isIdentValueConditionSet(requestDTO)))
		{
			validInputAgencies = true;
		}
		
		if(!validInputAgencies)
		{
			throwsException(getMissingParameterCode(), getMissingParameterMessage());
		}
	}
	
	/**
	 * Checks if the mandatory inputs Channel/Requestor/Application code
	 *  have been sent by the client
	 * @param requestDTO
	 * @throws BusinessException
	 */
	private void handleMissingRequestor(RequestDTO requestDTO) throws BusinessException
	{	
		boolean isChannelGiven = false;
		boolean isApplicationCodeGiven = false;
		
		if(BuildConditionsDS.isChannelConditionSet(requestDTO))
		{
			isChannelGiven = true;
		}
		
		if(BuildConditionsDS.isApplicationCodeConditionSet(requestDTO))
		{
			isApplicationCodeGiven = true;
		}
		
		if(!isChannelGiven)
		{
			throwsException(getMissingContextCode(), getMissingContextMessage());
		}
		
		if(!isApplicationCodeGiven)
		{
			throwsException(getMissingContextCode(), getMissingContextMessage());
		}
	}
}
