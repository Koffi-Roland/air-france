package com.airfrance.ref.exception;

public class InvalidCommercialZoneException extends RefException {

    private static final long serialVersionUID = 1L;

    private static final String ERROR_MESSAGE = "Invalid ZC";

    public InvalidCommercialZoneException(String message) {
            super(ERROR_MESSAGE, message);
        }

}
