package com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds;

import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.BusinessError;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.BusinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Abstract Domain service class
 *   Contains shared logger reference
 *   Handles Exception throwing from DS layer
 *   
 * @author t950700
 *
 */
public abstract class AbstractDS {
	/*===============================================*/
	/*         LOGGER AND LOGGING METHODS            */
	/*===============================================*/
	private static final Log log = LogFactory.getLog(AbstractDS.class);

	public static Log getLog() {
		return log;
	}

	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
	private String missingParameterCode = "133";
	private String missingParameterMessage = "MISSING PARAMETER- TO PERFORM A SEARCH: AT LEAST( NAME/NAME_SEARCH_TYPE=\"S\"OR\"L\" OR EMAIL OR PHONE OR AGENCY_IDENTIFIER)";
	
	private String missingContextCode = "133";
	private String missingContextMessage = "MISSING PARAMETER - CHANNEL/REQUESTOR/APPLICATION_CODE MANDATORY";
	
	private String missingProcessTypeCode = "133";
	private String missingProcessTypeMessage = "MISSING PARAMETER - PROCESS TYPE";
	
	private String lengthContextCode = "133";
	private String lengthContextMessage = "CHANNEL/REQUESTOR/APPLICATION_CODE LENGTH NOT MATCHING";

	private String missingZcCode = "133";
	private String missingZcMessage = "ZC are optional, but if given at least ZC1 to ZC4 are mandatory";

	private String missingAgencyTypeCode = "133";
	private String missingAgencyTypeMessage = "MISSING PARAMETER - AGENCY TYPE MANDATORY";
	
	private String successReturnCode = "000";
	private String successReturnMessage = "success";
	private String tooManyResultsReturnCode = "933";
	private String tooManyResultsReturnMessage = "TOO MANY AGENCIES FOUND, REFINE CRITERIA";
	private String noResultsReturnCode = "001";
	private String noResultsReturnMessage = "NO RESULTS FOUND";
	
	private String incoherentIndexCode = "133";
	private String incoherentIndexMessage = "INCOHERENT INPUTS - SIRET FOR FIRMS / SIREN FOR COMPANIES";
	
	private String incoherentAgencyTypeIdentTypeCode = "133";
	private String incoherentAgencyTypeIdentTypeMessage = "INCOHERENT INPUTS - INCOHERENCE BETWEEN AGENCY_TYPE AND IDENTIFICATION_TYPE";
	
	private String missingIdentValueCode = "133";
	private String missingIdentValueMessage = "INCOHERENT INPUTS - IF IDENT_TYPE IS SET IDENT_VALUE HAS TO BE SET AND VICE VERSA";
	
	
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	
	/**
	 * Throws a business exception 
	 * @param code
	 * @param message
	 * @throws BusinessException
	 */
	public void throwsException(String code, String message) throws BusinessException
	{
		BusinessError businessError = new BusinessError();
		businessError.setErrorCode(code);
		businessError.setFaultDescription(message);
		businessError.setMissingParameter(message);
		getLog().info(message);
		throw new BusinessException(message, businessError);
	}
	
	/*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/
	
	public String getIncoherentIndexCode() {
		return incoherentIndexCode;
	}

	public void setIncoherentIndexCode(String incoherentIndexCode) {
		this.incoherentIndexCode = incoherentIndexCode;
	}

	public String getIncoherentIndexMessage() {
		return incoherentIndexMessage;
	}

	public void setIncoherentIndexMessage(String incoherentIndexMessage) {
		this.incoherentIndexMessage = incoherentIndexMessage;
	}

	public String getMissingParameterCode() {
		return missingParameterCode;
	}

	public void setMissingParameterCode(String missingParameterCode) {
		this.missingParameterCode = missingParameterCode;
	}

	public String getMissingParameterMessage() {
		return missingParameterMessage;
	}
	
	public String getMissingProcessTypeCode() {
		return missingProcessTypeCode;
	}

	public void setMissingProcessTypeCode(String missingProcessTypeCode) {
		this.missingProcessTypeCode = missingProcessTypeCode;
	}

	public String getMissingProcessTypeMessage() {
		return missingProcessTypeMessage;
	}

	public void setMissingProcessTypeMessage(String missingProcessTypeMessage) {
		this.missingProcessTypeMessage = missingProcessTypeMessage;
	}

	public void setMissingParameterMessage(String missingParameterMessage) {
		this.missingParameterMessage = missingParameterMessage;
	}

	public String getMissingContextCode() {
		return missingContextCode;
	}

	public void setMissingContextCode(String missingContextCode) {
		this.missingContextCode = missingContextCode;
	}

	public String getMissingContextMessage() {
		return missingContextMessage;
	}

	public void setMissingContextMessage(String missingContextMessage) {
		this.missingContextMessage = missingContextMessage;
	}

	public String getLengthContextCode() {
		return lengthContextCode;
	}

	public void setLengthContextCode(String lengthContextCode) {
		this.lengthContextCode = lengthContextCode;
	}

	public String getLengthContextMessage() {
		return lengthContextMessage;
	}

	public void setLengthContextMessage(String lengthContextMessage) {
		this.lengthContextMessage = lengthContextMessage;
	}
	
	public String getMissingZcCode() {
		return missingZcCode;
	}

	public void setMissingZcCode(String missingZcCode) {
		this.missingZcCode = missingZcCode;
	}

	public String getMissingZcMessage() {
		return missingZcMessage;
	}

	public void setMissingZcMessage(String missingZcMessage) {
		this.missingZcMessage = missingZcMessage;
	}
	
	public String getSuccessReturnCode() {
		return successReturnCode;
	}

	public void setSuccessReturnCode(String successReturnCode) {
		this.successReturnCode = successReturnCode;
	}

	public String getSuccessReturnMessage() {
		return successReturnMessage;
	}

	public void setSuccessReturnMessage(String successReturnMessage) {
		this.successReturnMessage = successReturnMessage;
	}

	public String getTooManyResultsReturnCode() {
		return tooManyResultsReturnCode;
	}

	public void setTooManyResultsReturnCode(String tooManyResultsReturnCode) {
		this.tooManyResultsReturnCode = tooManyResultsReturnCode;
	}

	public String getTooManyResultsReturnMessage() {
		return tooManyResultsReturnMessage;
	}

	public void setTooManyResultsReturnMessage(String tooManyResultsReturnMessage) {
		this.tooManyResultsReturnMessage = tooManyResultsReturnMessage;
	}

	public String getNoResultsReturnCode() {
		return noResultsReturnCode;
	}

	public void setNoResultsReturnCode(String noResultsReturnCode) {
		this.noResultsReturnCode = noResultsReturnCode;
	}

	public String getNoResultsReturnMessage() {
		return noResultsReturnMessage;
	}

	public void setNoResultsReturnMessage(String noResultsReturnMessage) {
		this.noResultsReturnMessage = noResultsReturnMessage;
	}

	public String getMissingAgencyTypeCode() {
		return missingAgencyTypeCode;
	}

	public void setMissingAgencyTypeCode(String missingAgencyTypeCode) {
		this.missingAgencyTypeCode = missingAgencyTypeCode;
	}

	public String getMissingAgencyTypeMessage() {
		return missingAgencyTypeMessage;
	}

	public void setMissingAgencyTypeMessage(String missingAgencyTypeMessage) {
		this.missingAgencyTypeMessage = missingAgencyTypeMessage;
	}

	public String getIncoherentAgencyTypeIdentTypeCode() {
		return incoherentAgencyTypeIdentTypeCode;
	}

	public void setIncoherentAgencyTypeIdentTypeCode(
			String incoherentAgencyTypeIdentTypeCode) {
		this.incoherentAgencyTypeIdentTypeCode = incoherentAgencyTypeIdentTypeCode;
	}

	public String getIncoherentAgencyTypeIdentTypeMessage() {
		return incoherentAgencyTypeIdentTypeMessage;
	}

	public void setIncoherentAgencyTypeIdentTypeMessage(
			String incoherentAgencyTypeIdentTypeMessage) {
		this.incoherentAgencyTypeIdentTypeMessage = incoherentAgencyTypeIdentTypeMessage;
	}

	public String getMissingIdentValueCode() {
		return missingIdentValueCode;
	}

	public void setMissingIdentValueCode(String missingIdentValueCode) {
		this.missingIdentValueCode = missingIdentValueCode;
	}

	public String getMissingIdentValueMessage() {
		return missingIdentValueMessage;
	}

	public void setMissingIdentValueMessage(String missingIdentValueMessage) {
		this.missingIdentValueMessage = missingIdentValueMessage;
	}
	
}
