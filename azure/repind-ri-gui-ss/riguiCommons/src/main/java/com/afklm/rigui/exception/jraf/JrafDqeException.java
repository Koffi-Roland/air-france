package com.afklm.rigui.exception.jraf;

public class JrafDqeException extends JrafDomainRollbackException {

    /**
     *
     * <p>Title : JrafDqeException.java</p>
     * <p>Description : Exception de la couche domaine</p>
     * <p>Copyright : Copyright (c) 2022</p>
     * <p>Company : AIRFRANCE</p>
     * Exception used when DQE returns an error
     */

    public JrafDqeException(String msg) {
        super(msg);
    }

    public JrafDqeException(Throwable root) {
        super(root);
    }

    public JrafDqeException(String msg, Throwable root) {
        super(msg, root);
    }
}
