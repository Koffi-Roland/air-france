package com.afklm.rigui.exception;

import com.afklm.rigui.exception.jraf.JrafDaoException;

public class TooManyResultsDaoException extends JrafDaoException {

	private static final long serialVersionUID = 1919679355623057746L;

	public TooManyResultsDaoException(String msg) {
		super(msg);
	}

	public TooManyResultsDaoException(Throwable root) {
		super(root);
	}

	public TooManyResultsDaoException(String msg, Throwable root) {
		super(msg, root);
	}

}
