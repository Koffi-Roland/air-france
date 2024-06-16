package com.afklm.rigui.spring.rest.resources;


import java.io.Serializable;


public class RefTrackingResource implements Serializable {

    private static final long serialVersionUID = 1L;
            
    /**
     * refTrackingId
     */
    private Integer refTrackingId;
        
            
    /**
     * code
     */
    private String code;
        
            
    /**
     * value
     */
    private String value;
    
    /**
     * valueNormalized
     */
    private String valueNormalized;
    
    /**
     * icon
     */
    private String icon;


	public Integer getRefTrackingId() {
		return refTrackingId;
	}

	public void setRefTrackingId(Integer refTrackingId) {
		this.refTrackingId = refTrackingId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueNormalized() {
		return valueNormalized;
	}

	public void setValueNormalized(String valueNormalized) {
		this.valueNormalized = valueNormalized;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
