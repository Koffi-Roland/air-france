package com.afklm.cati.common.exception.jraf;

import org.apache.commons.lang.exception.NestableException;

public class JrafException extends NestableException {
    public JrafException(String msg) {
        super(msg);
    }

    public JrafException(Throwable root) {
        super(root);
    }

    public JrafException() {
    }

    public JrafException(String msg, Throwable root) {
        super(msg, root);
    }
}
