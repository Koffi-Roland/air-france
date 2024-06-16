package com.afklm.repind.v4.provideindividualdata.helpers;

import com.afklm.soa.stubs.w000418.v4.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v4.response.BusinessError;
import com.afklm.soa.stubs.w000418.v4.response.BusinessErrorBloc;
import com.afklm.soa.stubs.w000418.v4.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000418.v4.siccommontype.InternalBusinessError;
import com.airfrance.repind.service.reference.internal.ErrorDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("businessExceptionHelperV4")
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
	public void throwBusinessException(BusinessErrorCodeEnum numError, String detail) throws BusinessErrorBlocBusinessException {
		
		BusinessError businessError = buildBusinessException(numError, detail);
		
		throwBusinessException(businessError, null);
	}
	
	protected void throwBusinessException(BusinessError businessError, InternalBusinessError internalBusinessError) throws BusinessErrorBlocBusinessException {
		
		BusinessErrorBloc businessErrorBloc = new BusinessErrorBloc();
		businessErrorBloc.setBusinessError(businessError);
		businessErrorBloc.setInternalBusinessError(internalBusinessError);
		
		throw new BusinessErrorBlocBusinessException("", businessErrorBloc);
	}
	
	protected BusinessError buildBusinessException(BusinessErrorCodeEnum errorCode, String detail) {
		
		String label;
		
		try {
			String errorCodeStr = errorCode.toString();
			String errorNum = errorCodeStr.replace(ERROR_PREFIX,"");
			label = errorDS.getErrorDetails(errorNum).getLabelUK();
		} catch (Exception e) {
			label = UNABLE_TO_GET_LABEL_ERROR;
		}
		
		BusinessError businessError = new BusinessError();
		businessError.setErrorCode(errorCode);
		businessError.setErrorLabel(label);
		businessError.setErrorDetail(detail);

		return businessError;
	}
	
}
