package com.afklm.cati.common.spring.rest.resources;

import java.util.Collections;
import java.util.List;

/**
 * used in request body for RefPreferenceKeyType transaction
 * @author M436128
 */
public class RefPreferenceKeyTypeResource {

    private Integer refId;
    private String key;
    private List<String> listPreferenceType;
    private Integer minLength;
    private Integer maxLength;
    private String dataType;
    private String condition;

    public Integer getRefId() {
        return refId;
    }

    public void setRefId(final Integer refId) {
        this.refId = refId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public List<String> getListPreferenceType() {
        return listPreferenceType;
    }
    
    /**
     * used to handle update requestbody
     * @param type
     */
    public void setType(final String type) {
    	 this.listPreferenceType = Collections.singletonList(type);
    }

    public void setListPreferenceType(final List<String> listPreferenceType) {
        this.listPreferenceType = listPreferenceType;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(final Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(final Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(final String dataType) {
        this.dataType = dataType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(final String condition) {
        this.condition = condition;
    }

}
