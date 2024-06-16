package com.airfrance.batch.automaticmerge.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ServiceException extends Exception{

    private final List<String> gins;


    public ServiceException(String message, List<String> gins) {
        super(message);
        this.gins = new ArrayList<>(gins);
    }

}
