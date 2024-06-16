package com.afklm.repind.searchindividualbymulticriteriaws;

import com.afklm.soa.stubs.w001271.v2.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001271.v2.response.BusinessError;
import com.afklm.soa.stubs.w001271.v2.response.BusinessErrorBloc;
import com.afklm.soa.stubs.w001271.v2.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w001271.v2.siccommonenum.BusinessErrorTypeEnum;
import com.afklm.soa.stubs.w001271.v2.siccommontype.InternalBusinessError;
import com.airfrance.ref.exception.AdhesionException;
import com.airfrance.ref.exception.MarketingErrorException;
import com.airfrance.repind.service.reference.internal.ErrorDS;
import com.airfrance.repind.util.SicStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("businessExceptionHelperV5")
public class BusinessExceptionHelper {
	
	private static final String UNABLE_TO_GET_LABEL_ERROR = "UNABLE_TO_GET_LABEL";
	private static final int ERROR_DETAIL_MAX_LENGTH = 255;
	private static final int ERROR_LABEL_MAX_LENGTH = 70;

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
	public void throwBusinessException(BusinessErrorCodeEnum numError, String detail) throws BusinessErrorBlocBusinessException {
		
		BusinessError businessError = buildBusinessException(numError, null, detail);
		
		throwBusinessException(businessError, null);
	}
	
	/**
	 * Lever une {@link BusinessErrorBlocBusinessException} sans {@link InternalBusinessError}.
	 * @param numError : numero de l'erreur.
	 * @param otherErrorCode : cas de l'erreur OTHER
	 * @param label : label de l'erreur.
	 * @param detail : detail de l'erreur.
	 * @throws BusinessErrorBlocBusinessException
	 */
	public void throwBusinessException(BusinessErrorCodeEnum numError, String otherErrorCode, String detail) throws BusinessErrorBlocBusinessException {
		
		BusinessError businessError = buildBusinessException(numError, otherErrorCode, detail);
		
		throwBusinessException(businessError, null);
	}
	
	/**
	 * Exception de {@link BusinessErrorTypeEnum} Adhesion.
	 * @param e : {@link AdhesionException} concernée.
	 * @throws BusinessErrorBlocBusinessException : {@link BusinessErrorBlocBusinessException} levé.
	 */
	public void throwAdhesionBusinessException(BusinessErrorCodeEnum errorCode, AdhesionException e) throws BusinessErrorBlocBusinessException {
		
		BusinessError businessError = buildBusinessException(errorCode, null, "Adhesion error");	
		InternalBusinessError internalBusinessError = buildInternalBusinessException(e.getCodeErreur(), e.getMessage(), BusinessErrorTypeEnum.ERROR_ADHESION);
		
		throwBusinessException(businessError, internalBusinessError);
	}
	
	
	/**
	 * Exception de {@link BusinessErrorTypeEnum} Marketing.
	 * @param numError : TODO remplacer par BusinessEnumCodeError.
	 * @param label : label de l'erreur.
	 * @param detail : detail de l'erreur.
	 * @throws BusinessErrorBlocBusinessException
	 */
	public void throwMarketingBusinessException(BusinessErrorCodeEnum errorCode, MarketingErrorException e) throws BusinessErrorBlocBusinessException {
		
		BusinessError businessError = buildBusinessException(errorCode, null, "Marketing error");
		InternalBusinessError internalBusinessError = buildInternalBusinessException(e.getFaultInfo(), null, BusinessErrorTypeEnum.ERROR_MARKETING);

		throwBusinessException(businessError, internalBusinessError);
	}

	protected void throwBusinessException(BusinessError businessError, InternalBusinessError internalBusinessError) throws BusinessErrorBlocBusinessException {
		
		BusinessErrorBloc businessErrorBloc = new BusinessErrorBloc();
		businessErrorBloc.setBusinessError(businessError);
		businessErrorBloc.setInternalBusinessError(internalBusinessError);
		
		throw new BusinessErrorBlocBusinessException("", businessErrorBloc);
	}
	
	protected BusinessError buildBusinessException(BusinessErrorCodeEnum errorCode, String otherErrorCode, String detail) {
		
		String label;
		
		try {
			String errorCodeStr = (errorCode==BusinessErrorCodeEnum.ERR_001) ? otherErrorCode : errorCode.toString();
			String errorNum = errorCodeStr.replace(ERROR_PREFIX,"");
			label = errorDS.getErrorDetails(errorNum).getLabelUK();
		} catch (Exception e) {
			label = UNABLE_TO_GET_LABEL_ERROR;
		}
		
		BusinessError businessError = new BusinessError();
		businessError.setErrorCode(errorCode);
		businessError.setOtherErrorCode(otherErrorCode);
		businessError.setErrorLabel(SicStringUtils.truncate(label,ERROR_LABEL_MAX_LENGTH));
		businessError.setErrorDetail(SicStringUtils.truncate(detail,ERROR_DETAIL_MAX_LENGTH));

		return businessError;
	}
	
	protected InternalBusinessError buildInternalBusinessException(String errorCode, String label,BusinessErrorTypeEnum errorType) {
		
		InternalBusinessError internalBusinessError = new InternalBusinessError();
		internalBusinessError.setErrorCode(errorCode);
		internalBusinessError.setErrorLabel(SicStringUtils.truncate(label,ERROR_LABEL_MAX_LENGTH));
		internalBusinessError.setErrorType(errorType);
		
		return internalBusinessError;
		
	}
	
}
