package com.afklm.rigui.model.individual.requests;

public class ModelAlertRequest {
    private Integer alertId;
    private String sgin;
    private String type;
    private String optIn;

    public Integer getAlertId() {
        return alertId;
    }

    public void setAlertId(final Integer alertId) {
        this.alertId = alertId;
    }

    public String getSgin() {
        return sgin;
    }

    public void setSgin(final String sgin) {
        this.sgin = sgin;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getOptIn() {
        return optIn;
    }

    public void setOptIn(final String optIn) {
        this.optIn = optIn;
    }
}
