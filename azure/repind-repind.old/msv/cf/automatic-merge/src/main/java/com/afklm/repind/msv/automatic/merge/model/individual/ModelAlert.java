package com.afklm.repind.msv.automatic.merge.model.individual;

import java.util.Set;

public class ModelAlert {

    private Integer alertId;
    private String sgin;
    private ModelSignature signature;
    private String type;
    private String optIn;
    private Set<ModelAlertData> alertData;

    public Integer getAlertId() {
        return alertId;
    }

    public void setAlertId(Integer alertId) {
        this.alertId = alertId;
    }

    public String getSgin() {
        return sgin;
    }

    public void setSgin(String sgin) {
        this.sgin = sgin;
    }

    public ModelSignature getSignature() {
        return signature;
    }

    public void setSignature(ModelSignature signature) {
        this.signature = signature;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOptIn() {
        return optIn;
    }

    public void setOptIn(String optIn) {
        this.optIn = optIn;
    }

    public Set<ModelAlertData> getAlertData() {
        return alertData;
    }

    public void setAlertData(Set<ModelAlertData> alertData) {
        this.alertData = alertData;
    }
}
