package com.afklm.cati.common.exception.jraf;

public class JrafDomainRollbackException extends JrafDomainException {
    private static final long serialVersionUID = 7998543808624078889L;

    public JrafDomainRollbackException(String msg) {
        super(msg);
    }

    public JrafDomainRollbackException(Throwable root) {
        super(root);
    }

    public JrafDomainRollbackException(String msg, Throwable root) {
        super(msg, root);
    }
}
