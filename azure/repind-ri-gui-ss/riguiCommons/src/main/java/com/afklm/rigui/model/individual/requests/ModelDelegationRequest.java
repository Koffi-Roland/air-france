package com.afklm.rigui.model.individual.requests;

public class ModelDelegationRequest {

    String gin;
    String id;
    String status;
    String type;
    String delegate;
    String delegator;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getDelegate() {
        return delegate;
    }

    public void setDelegate(final String delegate) {
        this.delegate = delegate;
    }

    public String getDelegator() {
        return delegator;
    }

    public void setDelegator(final String delegator) {
        this.delegator = delegator;
    }

    public String getGin() {
        return gin;
    }

    public void setGin(final String gin) {
        this.gin = gin;
    }


}
