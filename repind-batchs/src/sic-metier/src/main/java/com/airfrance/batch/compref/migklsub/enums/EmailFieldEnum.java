package com.airfrance.batch.compref.migklsub.enums;

import com.airfrance.repind.entity.adresse.Email;

import java.util.Arrays;
import java.util.function.Consumer;

public enum EmailFieldEnum {
    GIN ( "GIN"  ),
    EMAIL_ADDRESS ( "Email_Address" );

    private String value;

    EmailFieldEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EmailFieldEnum fromString(String iValue){
        return Arrays.stream(EmailFieldEnum.values()).filter(x -> x.getValue().equals(iValue)).findFirst().orElse(null);
    }

    public static Consumer<Object> mapConsumer(EmailFieldEnum iEmailFieldEnum , Email ioEmail){
        if(ioEmail != null && iEmailFieldEnum != null){
            switch (iEmailFieldEnum){
                case EMAIL_ADDRESS:
                    return (x) -> ioEmail.setEmail(((String)x).toLowerCase());
                case GIN:
                    return (x) -> ioEmail.setSgin((String)x);
                default:
                    return null;
            }
        }
        return null;
    }
}
