package com.airfrance.repind.transversal.identifycustomercrossreferential.ds;

import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessErrorDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessExceptionDTO;
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
public abstract class AbstractDS 
{
	
	/*==========================================*/
	/*                                          */
	/*               LOGGER                     */
	/*                                          */
	/*==========================================*/

	private static Log LOGGER  = LogFactory.getLog(AbstractDS.class);

	
	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/

	private String successReturnCode = "000";
	private String successReturnMessage = "SUCCESS";
	
	private static String noResultsReturnCode = "001";
	private static String noResultsReturnMessage = "NO RESULTS FOUND";
	
	private String incoherentIndexCode = "133";
	private String incoherentIndexMessage = "INCOHERENT INPUTS - INDEX HAS TO BE WITHIN 1 AND (MAX_RESULTS/PAGE_SIZE)";
	
	private String missingProcessTypeCode = "133" ;
	private String missingProcessTypeMessage = "PROCESS TYPE IS MANDATORY (\"M\": MANUAL / \"A\": AUTOMATIC)";
	
	private String missingChannelCode = "133";
	private String missingChannelMessage ="CHANNEL IS MANDATORY";
	
	private String missingApplicationCodeCode = "133";
	private String missingApplicationCodeMessage = "APPLICATION CODE IS MANDATORY";
	
	private String missingTypeSearchCode = "133";
	private String missingTypeSearchMessage = "TYPE OF SEARCH IS MANDATORY (\"I\":INDIVIDUALS/\"F\":CORPORATES/\"IF\":INDIVIDUALS AND CORPORATES/\"A\":TRAVEL AGENCIES/\"IA\":INDIVIDUALS AND AGENCIES";
	
	private String missingResponseTypeCode = "133";
	private String missingResponseTypeMessage = "RESPONSE TYPE IS MANDATORY(\"U\": UNIQUE RESPONSE/\"F\": FULL RESPONSE)";
	
	private String multipleResultsCode = "133";
	private String multipleResultsMessage = "RESPONSE TYPE SET TO \"UNIQUE\", YET MULTIPLE RESULTS FOUND";
	
	private String noIndividualsNeitherFirmsReturnCode = "133";
	private String noIndividualsNeitherFirmsReturnMessage = "NEITHER INDIVIDUALS NOR FIRMS FOUND";
	
	private String noIndividualsNeitherAgenciesReturnCode = "133";
	private String noIndividualsNeitherAgenciesReturnMessage = "NEITHER INDIVIDUALS NOR AGENCIES FOUND";
	
	private static String noIndividualsReturnCode = "001";
	private static String noIndividualsReturnMessage = "INDIVIDUAL NOT FOUND";
	
	private String noIndividualsMissingParameterReturnCode = "133";
	private String noIndividualsMissingParameterReturnMessage = "LAST NAME/FIRST NAME OR EMAIL ARE MISSING";
	
	private static String otherExceptionReturnCode = "OTHER";
	private String otherExceptionReturnMessage = "OTHER EXCEPTION";
	
	private String missingCountryCodeCode = "133";
	private String missingCountryCodeMessage = "IF FIRST_NAME AND LAST_NAME ARE SET, (COUNTRY_CODE) OR (EMAIL) OR (PHONE) HAVE TO BE SET";
	
	private String incoherentPhoneCode = "133";
	private String incoherentPhoneMessage = "IF PHONE NUMBER IS SET, PHONE_COUNTRY_CODE HAS TO BE SET (and vice versa)";

	
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	
	/**
	 * Throws a business exception 
	 * @param code
	 * @param message
	 * @throws BusinessException
	 */
	public static void throwBusinessException(String code, String message) throws BusinessExceptionDTO
	{
		if(code == null)
		{
			code = "";
		}
		if(message == null)
		{
			message = "";
		}
		BusinessErrorDTO businessError = new BusinessErrorDTO();
		businessError.setErrorCode(code);
		businessError.setFaultDescription(message);
		businessError.setMissingParameter(message);
		LOGGER.info("IdentifyCustomerCrossReferential | DS BUSINESS EXCEPTION | " + code + message);
		throw new BusinessExceptionDTO(message, businessError);
	}
	

	/*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/
	
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


	public static String getNoResultsReturnCode() {
		return noResultsReturnCode;
	}


	public void setNoResultsReturnCode(String noResultsReturnCode) {
		AbstractDS.noResultsReturnCode = noResultsReturnCode;
	}


	public static String getNoResultsReturnMessage() {
		return noResultsReturnMessage;
	}


	public void setNoResultsReturnMessage(String noResultsReturnMessage) {
		AbstractDS.noResultsReturnMessage = noResultsReturnMessage;
	}


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


	public String getMissingChannelCode() {
		return missingChannelCode;
	}


	public void setMissingChannelCode(String missingChannelCode) {
		this.missingChannelCode = missingChannelCode;
	}


	public String getMissingChannelMessage() {
		return missingChannelMessage;
	}


	public void setMissingChannelMessage(String missingChannelMessage) {
		this.missingChannelMessage = missingChannelMessage;
	}


	public String getMissingApplicationCodeCode() {
		return missingApplicationCodeCode;
	}


	public void setMissingApplicationCodeCode(String missingApplicationCodeCode) {
		this.missingApplicationCodeCode = missingApplicationCodeCode;
	}


	public String getMissingApplicationCodeMessage() {
		return missingApplicationCodeMessage;
	}


	public void setMissingApplicationCodeMessage(
			String missingApplicationCodeMessage) {
		this.missingApplicationCodeMessage = missingApplicationCodeMessage;
	}


	public String getMissingTypeSearchCode() {
		return missingTypeSearchCode;
	}


	public void setMissingTypeSearchCode(String missingTypeSearchCode) {
		this.missingTypeSearchCode = missingTypeSearchCode;
	}


	public String getMissingTypeSearchMessage() {
		return missingTypeSearchMessage;
	}


	public void setMissingTypeSearchMessage(String missingTypeSearchMessage) {
		this.missingTypeSearchMessage = missingTypeSearchMessage;
	}


	public String getMissingResponseTypeCode() {
		return missingResponseTypeCode;
	}


	public void setMissingResponseTypeCode(String missingResponseTypeCode) {
		this.missingResponseTypeCode = missingResponseTypeCode;
	}


	public String getMissingResponseTypeMessage() {
		return missingResponseTypeMessage;
	}


	public void setMissingResponseTypeMessage(String missingResponseTypeMessage) {
		this.missingResponseTypeMessage = missingResponseTypeMessage;
	}


	public String getMultipleResultsCode() {
		return multipleResultsCode;
	}


	public void setMultipleResultsCode(String multipleResultsCode) {
		this.multipleResultsCode = multipleResultsCode;
	}


	public String getMultipleResultsMessage() {
		return multipleResultsMessage;
	}


	public void setMultipleResultsMessage(String multipleResultsMessage) {
		this.multipleResultsMessage = multipleResultsMessage;
	}
	
	
	public String getNoIndividualsNeitherFirmsReturnCode() {
		return noIndividualsNeitherFirmsReturnCode;
	}
	

	public void setNoIndividualsNeitherFirmsReturnCode(
			String noIndividualsNeitherFirmsReturnCode) {
		this.noIndividualsNeitherFirmsReturnCode = noIndividualsNeitherFirmsReturnCode;
	}
	
	
	public String getNoIndividualsNeitherFirmsReturnMessage() {
		return noIndividualsNeitherFirmsReturnMessage;
	}
	
	
	public void setNoIndividualsNeitherFirmsReturnMessage(
			String noIndividualsNeitherFirmsReturnMessage) {
		this.noIndividualsNeitherFirmsReturnMessage = noIndividualsNeitherFirmsReturnMessage;
	}
	
	


	public String getNoIndividualsNeitherAgenciesReturnCode() {
		return noIndividualsNeitherAgenciesReturnCode;
	}
	
	


	public void setNoIndividualsNeitherAgenciesReturnCode(
			String noIndividualsNeitherAgenciesReturnCode) {
		this.noIndividualsNeitherAgenciesReturnCode = noIndividualsNeitherAgenciesReturnCode;
	}
	
	


	public String getNoIndividualsNeitherAgenciesReturnMessage() {
		return noIndividualsNeitherAgenciesReturnMessage;
	}
	
	


	public void setNoIndividualsNeitherAgenciesReturnMessage(
			String noIndividualsNeitherAgenciesReturnMessage) {
		this.noIndividualsNeitherAgenciesReturnMessage = noIndividualsNeitherAgenciesReturnMessage;
	}


	public static String getNoIndividualsReturnCode() {
		return noIndividualsReturnCode;
	}


	public void setNoIndividualsReturnCode(String noIndividualsReturnCode) {
		AbstractDS.noIndividualsReturnCode = noIndividualsReturnCode;
	}


	public static String getNoIndividualsReturnMessage() {
		return noIndividualsReturnMessage;
	}


	public void setNoIndividualsReturnMessage(String noIndividualsReturnMessage) {
		AbstractDS.noIndividualsReturnMessage = noIndividualsReturnMessage;
	}


	public String getNoIndividualsMissingParameterReturnCode() {
		return noIndividualsMissingParameterReturnCode;
	}


	public void setNoIndividualsMissingParameterReturnCode(
			String noIndividualsMissingParameterReturnCode) {
		this.noIndividualsMissingParameterReturnCode = noIndividualsMissingParameterReturnCode;
	}


	public String getNoIndividualsMissingParameterReturnMessage() {
		return noIndividualsMissingParameterReturnMessage;
	}


	public void setNoIndividualsMissingParameterReturnMessage(
			String noIndividualsMissingParameterReturnMessage) {
		this.noIndividualsMissingParameterReturnMessage = noIndividualsMissingParameterReturnMessage;
	}


	public static String getOtherExceptionReturnCode() {
		return otherExceptionReturnCode;
	}


	public void setOtherExceptionReturnCode(String otherExceptionReturnCode) {
		AbstractDS.otherExceptionReturnCode = otherExceptionReturnCode;
	}


	public String getOtherExceptionReturnMessage() {
		return otherExceptionReturnMessage;
	}


	public void setOtherExceptionReturnMessage(String otherExceptionReturnMessage) {
		this.otherExceptionReturnMessage = otherExceptionReturnMessage;
	}
	
	


	public String getMissingCountryCodeCode() {
		return missingCountryCodeCode;
	}
	
	


	public void setMissingCountryCodeCode(String missingCountryCodeCode) {
		this.missingCountryCodeCode = missingCountryCodeCode;
	}
	
	


	public String getMissingCountryCodeMessage() {
		return missingCountryCodeMessage;
	}
	
	


	public void setMissingCountryCodeMessage(String missingCountryCodeMessage) {
		this.missingCountryCodeMessage = missingCountryCodeMessage;
	}


	public String getIncoherentPhoneCode() {
		return incoherentPhoneCode;
	}


	public void setIncoherentPhoneCode(String incoherentPhoneCode) {
		this.incoherentPhoneCode = incoherentPhoneCode;
	}


	public String getIncoherentPhoneMessage() {
		return incoherentPhoneMessage;
	}


	public void setIncoherentPhoneMessage(String incoherentPhoneMessage) {
		this.incoherentPhoneMessage = incoherentPhoneMessage;
	}
}
