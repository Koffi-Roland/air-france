package com.airfrance.ref.exception.external;

public class MaxNbOfPnmIdReachedException extends AbstractExternalIdentifierException {

	private static final long serialVersionUID = -8060931542822711217L;
	
	private static final String ERROR_MESSAGE = "Maximum number of PNM ID reached";
	
	public MaxNbOfPnmIdReachedException(int nbMax) {
		super(ERROR_MESSAGE, String.valueOf(nbMax));
	}
	
}
