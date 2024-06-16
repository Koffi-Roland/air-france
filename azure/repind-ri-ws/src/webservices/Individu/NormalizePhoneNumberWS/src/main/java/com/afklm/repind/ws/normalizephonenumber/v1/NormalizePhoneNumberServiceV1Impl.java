package com.afklm.repind.ws.normalizephonenumber.v1;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001070.v1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001070.v1.NormalizePhoneNumberServiceV1;
import com.afklm.soa.stubs.w001070.v1.data.NormalizePhoneNumberRequest;
import com.afklm.soa.stubs.w001070.v1.data.NormalizePhoneNumberResponse;
import com.afklm.soa.stubs.w001070.v1.response.BusinessError;
import com.afklm.soa.stubs.w001070.v1.response.BusinessErrorBloc;
import com.afklm.soa.stubs.w001070.v1.response.BusinessErrorCodeEnum;
import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.telecom.NormalizePhoneNumberDTO;
import com.airfrance.repind.service.reference.internal.ErrorDS;
import com.airfrance.repind.service.telecom.internal.NormalizePhoneNumberDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@Service("passenger_NormalizePhoneNumberService-v1Bean")
@WebService(endpointInterface = "com.afklm.soa.stubs.w001070.v1.NormalizePhoneNumberServiceV1", targetNamespace = "http://www.af-klm.com/services/passenger/NormalizePhoneNumberService-v1/wsdl", serviceName = "NormalizePhoneNumberServiceService-v1", portName = "NormalizePhoneNumberService-v1-soap11http")
public class NormalizePhoneNumberServiceV1Impl implements NormalizePhoneNumberServiceV1 {

	@Autowired
	private NormalizePhoneNumberDS normalizePhoneNumberDS;
	
	@Autowired
	private ErrorDS errorDS;
	
	private final String UNABLE_TO_GET_LABEL_ERROR = "UNABLE_TO_GET_LABEL";
	private final String ERROR_PREFIX = "ERROR_";
	
	@WebMethod(exclude=false)
	public NormalizePhoneNumberResponse normalizePhoneNumber(NormalizePhoneNumberRequest arg0) throws BusinessErrorBlocBusinessException, SystemException {

		NormalizePhoneNumberDTO normalizePhoneNumberDTO = null;
		
		try {
		
			String countryCode = arg0.getCountryCode();
			String phoneNumber = arg0.getPhoneNumber();
			normalizePhoneNumberDTO = normalizePhoneNumberDS.normalizePhoneNumber(countryCode, phoneNumber);
		
		}
		// 131 INVALID_COUNTRY
		catch (InvalidCountryCodeException e) {
			throwBusinessException(BusinessErrorCodeEnum.ERROR_131, e.getMessage());
		}
		// 701 TOO_ LONG
		catch (TooLongPhoneNumberException e) {
			throwBusinessException(BusinessErrorCodeEnum.ERROR_701, e.getMessage());
		} 
		// 702 TOO_SHORT
		catch (TooShortPhoneNumberException e) {
			throwBusinessException(BusinessErrorCodeEnum.ERROR_702, e.getMessage());
		} 
		// 703 UNKNOWN
		catch (InvalidPhoneNumberException e) {
			throwBusinessException(BusinessErrorCodeEnum.ERROR_703, e.getMessage());
		} 
		// 705 NOT_NORMALIZED
		catch (NormalizedPhoneNumberException e) {
			throwBusinessException(BusinessErrorCodeEnum.ERROR_705, e.getMessage());
		} 
		// 905 TECHNICAL_ERROR
		catch (JrafDomainException e) {
			throwBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
		}
		
		return NormalizePhoneNumberTransformV1.dtoToWs(normalizePhoneNumberDTO);
	}
	
	private void throwBusinessException(BusinessErrorCodeEnum errorCode,String detail) throws BusinessErrorBlocBusinessException {
		
		String label;
		
		try {
			String errorCodeStr = errorCode.toString();
			String errorNum = errorCodeStr.replace(ERROR_PREFIX,"");
			label = errorDS.getErrorDetails(errorNum).getLabelUK();
		} catch (Exception e) {
			label = UNABLE_TO_GET_LABEL_ERROR;
		}
		
		BusinessError error = new BusinessError();
		error.setErrorCode(errorCode);
		error.setErrorLabel(label);
		error.setErrorDetail(detail);
		
		BusinessErrorBloc errorBloc = new BusinessErrorBloc();
		errorBloc.setBusinessError(error);
		
		throw new BusinessErrorBlocBusinessException("",errorBloc);
	}

}
