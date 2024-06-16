package com.afklm.repindmsv.tribe.model.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import org.springframework.http.HttpStatus;

/**
 * System error list
 *
 * @author m312812
 *
 */
public enum SystemErrorList implements IError {


	API_SYSTEM_INTERNAL_SERVER_ERROR( new RestError(ErrorMessage.ERROR_MESSAGE_SYSTEM_001 , HttpStatus.INTERNAL_SERVER_ERROR)),

	API_SYSTEM_NO_HANDLER_FOUND(
			new RestError(ErrorMessage.ERROR_MESSAGE_SYSTEM_002 , HttpStatus.INTERNAL_SERVER_ERROR)),

	API_SYSTEM_NO_SUCH_REQUEST_HANDLING_METHOD(
			new RestError(ErrorMessage.ERROR_MESSAGE_SYSTEM_003 , HttpStatus.INTERNAL_SERVER_ERROR)),

	API_SYSTEM_UNSUPPORTED_CONTENT_TYPE(
			new RestError( ErrorMessage.ERROR_MESSAGE_SYSTEM_004 ,HttpStatus.UNSUPPORTED_MEDIA_TYPE)),

	API_SYSTEM_MESSAGE_NOT_READABLE(
			new RestError(ErrorMessage.ERROR_MESSAGE_SYSTEM_005 , HttpStatus.INTERNAL_SERVER_ERROR)),

	API_SYSTEM_FAULT(new RestError(ErrorMessage.ERROR_MESSAGE_SYSTEM_006, HttpStatus.INTERNAL_SERVER_ERROR)),

	API_SYSTEM_UNKNOW_ERROR(new RestError(ErrorMessage.ERROR_MESSAGE_SYSTEM_007 , HttpStatus.INTERNAL_SERVER_ERROR)),

	API_CLIENT_CALL_ERROR(new RestError(ErrorMessage.ERROR_MESSAGE_SYSTEM_008 , HttpStatus.INTERNAL_SERVER_ERROR));
	

	

	private RestError error;

	private SystemErrorList(final RestError error) {
		this.error = error;
	}

	public RestError getError() {
		return error;
	}

	@Override
	public RestError getRestError() {
		return null;
	}
}
