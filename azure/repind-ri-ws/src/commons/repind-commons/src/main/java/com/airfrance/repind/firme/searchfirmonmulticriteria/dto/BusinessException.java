package com.airfrance.repind.firme.searchfirmonmulticriteria.dto;

/**
 * Business exception for SearchFirmOnMultiCriteria
 * @author t950700
 *
 */
public class BusinessException
extends Exception
{

/**
 * Java type that goes as soapenv:Fault detail element.
 * 
 */
private BusinessError faultInfo;

/**
 * 
 * @param faultInfo
 * @param message
 */
public BusinessException(String message, BusinessError faultInfo) {
    super(message);
    this.faultInfo = faultInfo;
}

/**
 * 
 * @param faultInfo
 * @param message
 * @param cause
 */
public BusinessException(String message, BusinessError faultInfo, Throwable cause) {
    super(message, cause);
    this.faultInfo = faultInfo;
}

/**
 * 
 * @return
 *     returns fault bean: com.afklm.sicwssearchfirmonmulticriteriauml.searchfirmonmulticriteriatype.BusinessError
 */
public BusinessError getFaultInfo() {
    return faultInfo;
}

}
