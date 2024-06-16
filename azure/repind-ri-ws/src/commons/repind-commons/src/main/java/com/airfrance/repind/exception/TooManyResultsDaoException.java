package com.airfrance.repind.exception;

import com.airfrance.ref.exception.jraf.JrafDaoException;

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
