package com.afklm.spring.security.habile.demo.model;

import com.afklm.spring.security.habile.HabileUserDetails;

/**
 * Custom implementation for the Principal.<br/>
 * Extends {@link HabileUserDetails} and add one custom field.
 * 
 * @author m408461
 */
public class MyOwnUserDetails extends HabileUserDetails {

    private static final long serialVersionUID = 5769012717928806677L;

    private String myCustomField;

    public MyOwnUserDetails(HabileUserDetails habileUserDetails) {
        super(habileUserDetails.getUsername(),
            habileUserDetails.getLastname(),
            habileUserDetails.getFirstname(),
            habileUserDetails.getEmail(),
            habileUserDetails.getAuthorities(),
            null);
    }

    public String getMyCustomField() {
        return myCustomField;
    }

    public void setMyCustomField(String myCustomField) {
        this.myCustomField = myCustomField;
    }

}
