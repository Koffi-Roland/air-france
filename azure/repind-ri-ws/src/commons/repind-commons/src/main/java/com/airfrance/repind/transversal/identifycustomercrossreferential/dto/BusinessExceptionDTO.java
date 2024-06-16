package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

public class BusinessExceptionDTO extends Exception
{
	/**
	 * Java type that goes as soapenv:Fault detail element.
	 * 
	 */
	private BusinessErrorDTO faultInfo;

	/**
	 * 
	 * @param faultInfo
	 * @param message
	 */
	public BusinessExceptionDTO(String message, BusinessErrorDTO faultInfo) {
	    super(message);
	    this.faultInfo = faultInfo;
	}

	/**
	 * 
	 * @param faultInfo
	 * @param message
	 * @param cause
	 */
	public BusinessExceptionDTO(String message, BusinessErrorDTO faultInfo, Throwable cause) {
	    super(message, cause);
	    this.faultInfo = faultInfo;
	}

	/**
	 * 
	 * @return
	 *     returns fault bean: com.afklm.sicwssearchfirmonmulticriteriauml.searchfirmonmulticriteriatype.BusinessError
	 */
	public BusinessErrorDTO getFaultInfo() {
	    return faultInfo;
	}

	}
