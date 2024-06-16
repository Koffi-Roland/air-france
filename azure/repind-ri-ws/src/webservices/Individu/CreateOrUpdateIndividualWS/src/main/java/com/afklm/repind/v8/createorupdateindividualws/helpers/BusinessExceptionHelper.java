package com.afklm.repind.v8.createorupdateindividualws.helpers;

import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.response.BusinessError;
import com.afklm.soa.stubs.w000442.v8.response.BusinessErrorBloc;
import com.afklm.soa.stubs.w000442.v8.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000442.v8.siccommonenum.BusinessErrorTypeEnum;
import com.afklm.soa.stubs.w000442.v8.siccommontype.InternalBusinessError;
import com.airfrance.ref.exception.AdhesionException;
import com.airfrance.ref.exception.MarketingErrorException;
import com.airfrance.repind.service.reference.internal.ErrorDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessExceptionHelper {
	
	private static final String UNABLE_TO_GET_LABEL_ERROR = "UNABLE_TO_GET_LABEL";

	@Autowired
	protected ErrorDS errorDS;
	
	private final String ERROR_PREFIX = "ERROR_";

	/**
	 * Lever une {@link BusinessErrorBlocBusinessException} sans {@link InternalBusinessError}.
	 * @param numError : numero de l'erreur.
	 * @param label : label de l'erreur.
	 * @param detail : detail de l'erreur.
	 * @throws BusinessErrorBlocBusinessException
	 */
	public BusinessErrorBlocBusinessException createBusinessException(BusinessErrorCodeEnum numError, String detail) {
		
		BusinessError businessError = buildBusinessException(numError, null, detail);
		
		return createBusinessException(businessError, null);
	}
	
	/**
	 * Lever une {@link BusinessErrorBlocBusinessException} sans {@link InternalBusinessError}.
	 * @param numError : numero de l'erreur.
	 * @param otherErrorCode : cas de l'erreur OTHER
	 * @param label : label de l'erreur.
	 * @param detail : detail de l'erreur.
	 * @throws BusinessErrorBlocBusinessException
	 */
	public BusinessErrorBlocBusinessException createBusinessException(BusinessErrorCodeEnum numError, String otherErrorCode, String detail) {
		
		BusinessError businessError = buildBusinessException(numError, otherErrorCode, detail);
		
		return createBusinessException(businessError, null);
	}
	
	/**
	 * Exception de {@link BusinessErrorTypeEnum} Adhesion.
	 * @param e : {@link AdhesionException} concernée.
	 * @throws BusinessErrorBlocBusinessException : {@link BusinessErrorBlocBusinessException} levé.
	 */
	public BusinessErrorBlocBusinessException createAdhesionBusinessException(BusinessErrorCodeEnum errorCode, AdhesionException e) throws BusinessErrorBlocBusinessException {
		
		BusinessError businessError = buildBusinessException(errorCode, null, "Adhesion error");	
		InternalBusinessError internalBusinessError = buildInternalBusinessException(e.getCodeErreur(), e.getMessage(), BusinessErrorTypeEnum.ERROR_ADHESION);
		
		return createBusinessException(businessError, internalBusinessError);
	}
	
	
	/**
	 * Exception de {@link BusinessErrorTypeEnum} Marketing.
	 * @param numError : TODO remplacer par BusinessEnumCodeError.
	 * @param label : label de l'erreur.
	 * @param detail : detail de l'erreur.
	 * @throws BusinessErrorBlocBusinessException
	 */
	public BusinessErrorBlocBusinessException createMarketingBusinessException(BusinessErrorCodeEnum errorCode, MarketingErrorException e) throws BusinessErrorBlocBusinessException {
		
		BusinessError businessError = buildBusinessException(errorCode, null, "Marketing error");
		InternalBusinessError internalBusinessError = buildInternalBusinessException(e.getFaultInfo(), null, BusinessErrorTypeEnum.ERROR_MARKETING);

		return createBusinessException(businessError, internalBusinessError);
	}

	protected BusinessErrorBlocBusinessException createBusinessException(BusinessError businessError, InternalBusinessError internalBusinessError) {
		
		BusinessErrorBloc businessErrorBloc = new BusinessErrorBloc();
		businessErrorBloc.setBusinessError(businessError);
		businessErrorBloc.setInternalBusinessError(internalBusinessError);
		
		return new BusinessErrorBlocBusinessException("", businessErrorBloc);
	}
	
	protected BusinessError buildBusinessException(BusinessErrorCodeEnum errorCode, String otherErrorCode, String detail) {
		
		String label;
		
		try {
			String errorCodeStr = (errorCode==BusinessErrorCodeEnum.OTHER) ? otherErrorCode : errorCode.toString();
			String errorNum = errorCodeStr.replace(ERROR_PREFIX,"");
			label = errorDS.getErrorDetails(errorNum).getLabelUK();
		} catch (Exception e) {
			label = UNABLE_TO_GET_LABEL_ERROR;
		}
		
		BusinessError businessError = new BusinessError();
		businessError.setErrorCode(errorCode);
		businessError.setOtherErrorCode(otherErrorCode);
		businessError.setErrorLabel(label);
		businessError.setErrorDetail(detail);

		return businessError;
	}
	
	protected InternalBusinessError buildInternalBusinessException(String errorCode, String label,BusinessErrorTypeEnum errorType) {
		
		InternalBusinessError internalBusinessError = new InternalBusinessError();
		internalBusinessError.setErrorCode(errorCode);
		if (label != null && label.length() > 70) {
			label = label.substring(0, 69);
		} 
		internalBusinessError.setErrorLabel(label);
		internalBusinessError.setErrorType(errorType);
		
		return internalBusinessError;
		
	}

	public BusinessErrorBlocBusinessException createCustomBusinessException(String numError, String detail) throws BusinessErrorBlocBusinessException {
		
		BusinessError businessError = buildCustomBusinessException(numError, numError, detail);
		
		return createBusinessException(businessError, null);
	}

	protected BusinessError buildCustomBusinessException(String numError, String otherErrorCode, String detail) {
		
		String label;
		
		try {
			String errorNum = numError.replace(ERROR_PREFIX,"");
			label = errorDS.getErrorDetails(errorNum).getLabelUK();
		} catch (Exception e) {
			label = UNABLE_TO_GET_LABEL_ERROR;
		}
		
		BusinessError businessError = new BusinessError();
		businessError.setErrorCode(null);
		businessError.setOtherErrorCode(otherErrorCode);
		businessError.setErrorLabel(label);
		businessError.setErrorDetail(detail);

		return businessError;
	}
	
}
