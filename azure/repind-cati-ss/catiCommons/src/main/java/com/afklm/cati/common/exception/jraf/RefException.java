package com.afklm.cati.common.exception.jraf;

import com.afklm.cati.common.exception.jraf.JrafDomainRollbackException;

public abstract class RefException extends JrafDomainRollbackException {

    private static final long serialVersionUID = 3864091841760580271L;

    private static final String SEPARATOR = ": ";

    public RefException(String msg, String value) {
        super(msg+SEPARATOR+value);
    }

    public RefException(String msg) {
        super(msg);
    }

    public RefException(Throwable root) {
        super(root);
    }

    public RefException(String msg, Throwable root) {
        super(msg, root);
    }

}
