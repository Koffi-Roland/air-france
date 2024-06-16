package com.airfrance.repind.firme.searchfirmonmulticriteria.ds;

import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.BusinessException;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.RequestDTO;
import org.springframework.stereotype.Service;



@Service
public class CheckMandatoryInputsDS extends AbstractDS implements ICheckMandatoryInputsDS {

	
	
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
		handleMissingNameSearchType(requestDTO);
		handleMissingFirmType(requestDTO);
		handleMissingIdentValue(requestDTO);
		handleIncoherentFirmAndIdentType(requestDTO);
		handleMissingRequestor(requestDTO);
		handleMissingParametersAccordingFirmType(requestDTO);
		handleMissingZc(requestDTO);
	}
	
	/**
	 * Check the web service mandatory inputs
	 */
	public void checkMandatoryInputsV2(RequestDTO requestDTO) throws BusinessException 
	{
		handleMissingIndex(requestDTO);
		handleMissingProcessType(requestDTO);
		handleMissingNameSearchType(requestDTO);
		handleMissingFirmType(requestDTO);
		handleMissingIdentValue(requestDTO);
		handleIncoherentFirmAndIdentType(requestDTO);
		handleMissingRequestor(requestDTO);
		handleMissingParametersAccordingFirmType(requestDTO);
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
	 * If Name is set, name search type has to be set
	 * @param requestDTO
	 * @throws BusinessException
	 */
	protected void handleMissingNameSearchType(RequestDTO requestDTO) throws BusinessException {
		if(BuildConditionsDS.isNameConditionSet(requestDTO))
		{
			if((! BuildConditionsDS.isNameSearchTypeConditionSet(requestDTO))
					|| (! BuildConditionsDS.isNameTypeConditionSet(requestDTO)))
			{
				throwsException(getMissingSearchTypeCode(), getMissingSearchTypeMessage());
			}
		}
		
	}
	
	
	/**
	 * Handles missing firm type
	 * @param requestDTO
	 * @throws BusinessException
	 */
	protected void handleMissingFirmType(RequestDTO requestDTO) throws BusinessException {
		boolean firmTypeIndicated = false;
		
		if(BuildConditionsDS.isFirmTypeConditionSet(requestDTO))
		{
			firmTypeIndicated = true;
		}
		
		if(!firmTypeIndicated)
		{
			throwsException(getMissingFirmTypeCode(), getMissingFirmTypeMessage());
		}
	}
	
	/**
	 * If IdentType is set, identValue has to be set
	 * @param requestDTO
	 * @throws BusinessException 
	 */
	protected void handleMissingIdentValue(RequestDTO requestDTO) throws BusinessException 
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
	 * Handles incoherence between FirmType and IdentType
	 * @param requestDTO
	 * @throws BusinessException 
	 */
	protected void handleIncoherentFirmAndIdentType(RequestDTO requestDTO) throws BusinessException {

		if((BuildConditionsDS.isSirenConditionSet(requestDTO))
				&&	(!BuildConditionsDS.isFirmTypeCompaniesConditionSet(requestDTO)))
		{
			throwsException(getIncoherentFirmTypeIdentTypeCode(), getIncoherentFirmTypeIdentTypeMessage());
		}
		
		if((BuildConditionsDS.isSiretConditionSet(requestDTO))
				&&	(!BuildConditionsDS.isFirmTypeFirmsConditionSet(requestDTO)))
		{
			throwsException(getIncoherentFirmTypeIdentTypeCode(), getIncoherentFirmTypeIdentTypeMessage());
		}
		
	}
	
	/**
	 * Checks the given ZC
	 * ZC are optional, but if given at least ZC1 to ZC4 are mandatory
	 */
	protected void handleMissingZc(RequestDTO requestDTO) throws BusinessException 
	{
		boolean isZc1Given = false;
		boolean isZc2Given = false;
		
		if(BuildConditionsDS.isZC1ConditionSet(requestDTO))
		{
			 isZc1Given = true;
		}
		if(BuildConditionsDS.isZC2ConditionSet(requestDTO))
		{
			 isZc2Given = true;
		}		
		
		if((isZc1Given == true)
				&& ((isZc2Given == false)))
		{
			throwsException(getMissingZcCode(), getMissingZcMessage());
		}
		if((isZc2Given == true)
				&& ((isZc1Given == false)))
		{
			throwsException(getMissingZcCode(), getMissingZcMessage());
		}
		
	}

	
	/**
	 * Handles the possible combinations of firmType and Inputs
	 * @param requestDTO
	 * @throws BusinessException
	 */
	protected void handleMissingParametersAccordingFirmType(RequestDTO requestDTO) throws BusinessException
	{
		boolean validInput = false;

		if(BuildConditionsDS.isNameStrictConditionSet(requestDTO)
				|| BuildConditionsDS.isNameLikeConditionSet(requestDTO))
		{
			validInput = true;
		}
		if(BuildConditionsDS.isEmailConditionSet(requestDTO))
		{
			validInput = true;
		}
		if(BuildConditionsDS.isPhoneConditionSet(requestDTO))
		{
			validInput = true;
		}
		if((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&&	(BuildConditionsDS.isIdentValueConditionSet(requestDTO)))
		{
			validInput = true;
		}

		if((BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&&	(BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&&	(BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&&	(BuildConditionsDS.isZC4ConditionSet(requestDTO))
				&&	(BuildConditionsDS.isZC5ConditionSet(requestDTO)))
		{
			validInput = true;
		}

		if(!validInput)
		{
			/*
			 * Handles FirmType == Firm
			 */
			if(BuildConditionsDS.isFirmTypeFirmsConditionSet(requestDTO))
			{
				throwsException(getMissingParameterCodeFirm(), getMissingParameterMessageFirm());
			}
			/*
			 * Handles FirmType == Service
			 */
			if(BuildConditionsDS.isFirmTypeServicesConditionSet(requestDTO))
			{	
				throwsException(getMissingParameterCodeService(), getMissingParameterMessageService());
			}
			/*
			 * Handles FirmType == All
			 */
			if(BuildConditionsDS.isFirmTypeAllConditionSet(requestDTO))
			{
				throwsException(getMissingParameterCodeFirm(), getMissingParameterMessageFirm());
			}
			/*
			 * Handles FirmType == Group
			 */
			if(BuildConditionsDS.isFirmTypeGroupsConditionSet(requestDTO))
			{
				throwsException(getMissingParameterCodeGroup(), getMissingParameterMessageGroup());
			}
			/*
			 * Handles FirmType == Company
			 */
			if(BuildConditionsDS.isFirmTypeCompaniesConditionSet(requestDTO))
			{	
				throwsException(getMissingParameterCodeCompany(), getMissingParameterMessageCompany());
			}
		}
	}
	
	/**
	 * Checks if the mandatory inputs Channel/Application code
	 *  have been sent by the client
	 * @param requestDTO
	 * @throws BusinessException
	 */
	protected void handleMissingRequestor(RequestDTO requestDTO) throws BusinessException
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
