package com.afklm.cati.common.exception.jraf;

public class JrafDomainException extends JrafException {
    private static final long serialVersionUID = -187185419114121297L;

    public JrafDomainException(String msg) {
        super(msg);
    }

    public JrafDomainException(Throwable root) {
        super(root);
    }

    public JrafDomainException(String msg, Throwable root) {
        super(msg, root);
    }
}
