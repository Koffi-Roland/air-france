package com.airfrance.repind.firme.searchfirmonmulticriteria.ds;

import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.BusinessError;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.BusinessException;
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
	private String missingParameterCodeGroup = "133";
	private String missingParameterMessageGroup = "MISSING PARAMETER - AT LEAST NAME HAS TO BE SET";
	
	private String missingParameterCodeCompany = "133";
	private String missingParameterMessageCompany = "MISSING PARAMETER - AT LEAST STRICT_NAME, COUNTRY_CODE AND (PHONE OR EMAIL OR ADDRESS OR IDENT) HAVE TO BE SET";
	
	private String missingParameterCodeFirm = "133";
	private String missingParameterMessageFirm = "MISSING PARAMETER - AT LEAST (NAME) OR (EMAIL) OR (PHONE) OR (IDENT) OR (ZC1 to ZC5) HAVE TO BE SET";
	
	private String missingParameterCodeService = "133";
	private String missingParameterMessageService = "MISSING PARAMETER - AT LEAST (EMAIL) OR (PHONE) OR (COUNTRY_CODE) OR (NAME AND COUNTRY_CODE AND ADDRESS) HAVE TO BE SET";
	
	private String missingParameterCodeAll = "133";
	private String missingParameterMessageAll = "MISSING PARAMETER - AT LEAST NAME HAS TO BE SET";
	
	private String missingContextCode = "133";
	private String missingContextMessage = "MISSING PARAMETER - CHANNEL/REQUESTOR/APPLICATION_CODE MANDATORY";
	
	private String missingProcessTypeCode = "133";
	private String missingProcessTypeMessage = "MISSING PARAMETER - PROCESS TYPE";
	
	private String missingSearchTypeCode = "133";
	private String missingSearchTypeMessage = "IF NAME IS SET, NAME TYPE AND NAME_SEARCH_TYPE HAVE TO BE SET";
	
	private String lengthContextCode = "133";
	private String lengthContextMessage = "CHANNEL/REQUESTOR/APPLICATION_CODE LENGTH NOT MATCHING";

	private String missingZcCode = "133";
	private String missingZcMessage = "ZC are optional, but if given at least ZC1 to ZC4 are mandatory";

	private String missingFirmTypeCode = "133";
	private String missingFirmTypeMessage = "MISSING PARAMETER - FIRM TYPE MANDATORY";
	
	private String successReturnCode = "000";
	private String successReturnMessage = "success";
	private String tooManyResultsReturnCode = "933";
	private String tooManyResultsReturnMessage = "TOO MANY FIRMS FOUND, REFINE CRITERIA";
	private String noResultsReturnCode = "001";
	private String noResultsReturnMessage = "NO RESULTS FOUND";
	
	private String incoherentIndexCode = "133";
	private String incoherentIndexMessage = "INCOHERENT INPUTS - INDEX HAS TO BE WITHIN 1 AND (MAX_RESULTS/PAGE_SIZE)";
	
	private String incoherentFirmTypeIdentTypeCode = "133";
	private String incoherentFirmTypeIdentTypeMessage = "INCOHERENT INPUTS - INCOHERENCE BETWEEN FIRM_TYPE AND IDENTIFICATION_TYPE";
	
	private String missingIdentValueCode = "133";
	private String missingIdentValueMessage = "INCOHERENT INPUTS - IF IDENT_TYPE IS SET IDENT_VALUE HAS TO BE SET AND VICE VERSA";
	
	/*===============================================*/
	/*                   LOGGER                      */
	/*===============================================*/
	private static Log LOGGER  = LogFactory.getLog(CheckMandatoryInputsDS.class);
	
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
		if(code == null)
		{
			code = "";
		}
		if(message == null)
		{
			message = "";
		}
		BusinessError businessError = new BusinessError();
		businessError.setErrorCode(code);
		businessError.setFaultDescription(message);
		businessError.setMissingParameter(message);
		LOGGER.info("SearchFirmOnMultiCriteria | DS BUSINESS EXCEPTION | " + code + message);
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




	
	public String getMissingParameterCodeGroup() {
		return missingParameterCodeGroup;
	}

	public void setMissingParameterCodeGroup(String missingParameterCodeGroup) {
		this.missingParameterCodeGroup = missingParameterCodeGroup;
	}

	public String getMissingParameterMessageGroup() {
		return missingParameterMessageGroup;
	}

	public void setMissingParameterMessageGroup(String missingParameterMessageGroup) {
		this.missingParameterMessageGroup = missingParameterMessageGroup;
	}

	public String getMissingParameterCodeCompany() {
		return missingParameterCodeCompany;
	}

	public void setMissingParameterCodeCompany(String missingParameterCodeCompany) {
		this.missingParameterCodeCompany = missingParameterCodeCompany;
	}

	public String getMissingParameterMessageCompany() {
		return missingParameterMessageCompany;
	}

	public void setMissingParameterMessageCompany(
			String missingParameterMessageCompany) {
		this.missingParameterMessageCompany = missingParameterMessageCompany;
	}

	public String getMissingParameterCodeFirm() {
		return missingParameterCodeFirm;
	}

	public void setMissingParameterCodeFirm(String missingParameterCodeFirm) {
		this.missingParameterCodeFirm = missingParameterCodeFirm;
	}

	public String getMissingParameterMessageFirm() {
		return missingParameterMessageFirm;
	}

	public void setMissingParameterMessageFirm(String missingParameterMessageFirm) {
		this.missingParameterMessageFirm = missingParameterMessageFirm;
	}

	public String getMissingParameterCodeService() {
		return missingParameterCodeService;
	}

	public void setMissingParameterCodeService(String missingParameterCodeService) {
		this.missingParameterCodeService = missingParameterCodeService;
	}

	public String getMissingParameterMessageService() {
		return missingParameterMessageService;
	}

	public void setMissingParameterMessageService(
			String missingParameterMessageService) {
		this.missingParameterMessageService = missingParameterMessageService;
	}

	public String getMissingParameterCodeAll() {
		return missingParameterCodeAll;
	}

	public void setMissingParameterCodeAll(String missingParameterCodeAll) {
		this.missingParameterCodeAll = missingParameterCodeAll;
	}

	public String getMissingParameterMessageAll() {
		return missingParameterMessageAll;
	}

	public void setMissingParameterMessageAll(String missingParameterMessageAll) {
		this.missingParameterMessageAll = missingParameterMessageAll;
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

	public String getMissingFirmTypeCode() {
		return missingFirmTypeCode;
	}

	public void setMissingFirmTypeCode(String missingFirmTypeCode) {
		this.missingFirmTypeCode = missingFirmTypeCode;
	}

	public String getMissingFirmTypeMessage() {
		return missingFirmTypeMessage;
	}

	public void setMissingFirmTypeMessage(String missingFirmTypeMessage) {
		this.missingFirmTypeMessage = missingFirmTypeMessage;
	}

	public String getIncoherentFirmTypeIdentTypeCode() {
		return incoherentFirmTypeIdentTypeCode;
	}

	public void setIncoherentFirmTypeIdentTypeCode(
			String incoherentFirmTypeIdentTypeCode) {
		this.incoherentFirmTypeIdentTypeCode = incoherentFirmTypeIdentTypeCode;
	}

	public String getIncoherentFirmTypeIdentTypeMessage() {
		return incoherentFirmTypeIdentTypeMessage;
	}

	public void setIncoherentFirmTypeIdentTypeMessage(
			String incoherentFirmTypeIdentTypeMessage) {
		this.incoherentFirmTypeIdentTypeMessage = incoherentFirmTypeIdentTypeMessage;
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

	public String getMissingSearchTypeCode() {
		return missingSearchTypeCode;
	}

	public void setMissingSearchTypeCode(String missingSearchTypeCode) {
		this.missingSearchTypeCode = missingSearchTypeCode;
	}

	public String getMissingSearchTypeMessage() {
		return missingSearchTypeMessage;
	}

	public void setMissingSearchTypeMessage(String missingSearchTypeMessage) {
		this.missingSearchTypeMessage = missingSearchTypeMessage;
	}
	
}
