package com.afklm.rigui.model.individual.requests;

import java.util.Date;

public class ModelConsentRequest {

    private String gin;
    private int id;
    private String isConsent;
    private String application;
    private Date dateConsent;

    public String getApplication() {
        return application;
    }

    public void setApplication(final String application) {
        this.application = application;
    }

    public String getGin() {
        return gin;
    }

    public void setGin(final String gin) {
        this.gin = gin;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getIsConsent() {
        return isConsent;
    }

    public void setIsConsent(final String isConsent) {
        this.isConsent = isConsent;
    }

    public Date getDateConsent() {
        return dateConsent;
    }

    public void setDateConsent() {
        this.dateConsent = new Date();
    }

}
