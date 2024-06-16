package com.airfrance.ref.exception;

public class AgencyNotFoundException extends RefException {

    private static final long serialVersionUID = 1L;

    private static final String ERROR_MESSAGE = "Agency not found";

    public AgencyNotFoundException(String msg) {
        super(ERROR_MESSAGE, msg);
    }

    public AgencyNotFoundException(Throwable root) {
        super(root);
    }

    public AgencyNotFoundException(String msg, Throwable root) {
        super(msg, root);
    }
}


