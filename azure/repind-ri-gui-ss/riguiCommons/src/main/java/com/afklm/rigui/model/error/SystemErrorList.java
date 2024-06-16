package com.afklm.rigui.model.error;

import java.util.EnumMap;
import java.util.Map;

/**
 * System error list
 *
 * @author m312812
 *
 */
public enum SystemErrorList {

	API_SYSTEM_INTERNAL_SERVER_ERROR(
			new RestError("api.error.system.001", "Internal server error ", null, ErrorType.ERROR)),

	API_SYSTEM_NO_HANDLER_FOUND(
			new RestError("api.error.system.002", "No exposed resource for this URI ", null, ErrorType.ERROR)),

	API_SYSTEM_NO_SUCH_REQUEST_HANDLING_METHOD(
			new RestError("api.error.system.003", "Handle No Such Request Handling Method", null, ErrorType.ERROR)),

	API_SYSTEM_UNSUPPORTED_CONTENT_TYPE(
			new RestError("api.error.system.004", "Unsupported content type", null, ErrorType.ERROR)),

	API_SYSTEM_MESSAGE_NOT_READABLE(
			new RestError("api.error.system.004", "Message not readable", null, ErrorType.ERROR)),

	API_SYSTEM_FAULT(new RestError("api.error.system.005", "System fault from webservice", null, ErrorType.ERROR)),

	API_SYSTEM_UNKNOW_ERROR(new RestError("api.error.system.006", "System unknown error", null, ErrorType.ERROR));

	static Map<SystemErrorList, RestError> enumMap = new EnumMap<>(SystemErrorList.class);

	static {
		for (final SystemErrorList element : values()) {
			enumMap.put(element, element.getError());
		}
	}

	

	

	private RestError error;

	private SystemErrorList(final RestError error) {
		this.error = error;
	}

	public RestError getError() {
		return error;
	}

}
