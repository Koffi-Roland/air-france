package com.airfrance.repindutf8.service.utf.internal;

public class UtfException extends Exception {

	private static final long serialVersionUID = 814619454143220574L;

	public static UtfException generateException(final UtfErrorCode utfErrorCode, final String gin) {
		return new UtfException(utfErrorCode, "If you see this error, check how you call this webservice + SSD or report it to RI team. Utf;"
				+ utfErrorCode.name() + ";" + utfErrorCode.getId() + ";" + gin);
	}

	private final UtfErrorCode _utfErrorCode;

	private UtfException(final UtfErrorCode utfErrorCode, final String message) {
		super(message);
		_utfErrorCode = utfErrorCode;
	}

	public UtfErrorCode getErrorCode() {
		return _utfErrorCode;
	}

}
