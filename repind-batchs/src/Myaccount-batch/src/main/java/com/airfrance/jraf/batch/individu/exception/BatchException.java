package com.airfrance.jraf.batch.individu.exception;

import com.airfrance.ref.exception.RefException;

public class BatchException extends RefException {


    //Constructor that accepts a message
    public BatchException(String message)
    {
       super(message);
    }
}
