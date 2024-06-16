package com.airfrance.ref.exception.jraf;


/**
 * Erreur couche de donn�es
 * 
 * @author Fabrice P�pin
 * @author C�dric Torcq
 */
public class JrafDaoException extends JrafDomainRollbackException {

	private static final long serialVersionUID = 2725768744820486017L;

	public JrafDaoException(String msg) {
		super(msg);
	}
	
	public JrafDaoException(Throwable root) {
            super(root);
	}
	
	public JrafDaoException(String msg, Throwable root) {
		super(msg, root);
	}
	
}
