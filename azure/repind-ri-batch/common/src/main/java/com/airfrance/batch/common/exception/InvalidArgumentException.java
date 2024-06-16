package com.airfrance.batch.common.exception;

import com.airfrance.ref.exception.jraf.JrafDomainException;

public class InvalidArgumentException extends JrafDomainException {

    private static final long serialVersionUID = 6652341379463328272L;

    public InvalidArgumentException(String msg) {
        super(msg);
    }
    
}
