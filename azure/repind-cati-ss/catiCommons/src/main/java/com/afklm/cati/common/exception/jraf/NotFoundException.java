package com.afklm.cati.common.exception.jraf;


public class NotFoundException extends RefException {

    private static final long serialVersionUID = 1887368075357506246L;

    private static final String ERROR_MESSAGE = "Individual not found";

    public NotFoundException(String msg) {
        super(ERROR_MESSAGE, msg);
    }

    public NotFoundException(Throwable root) {
        super(root);
    }

    public NotFoundException(String msg, Throwable root) {
        super(msg, root);
    }

}
