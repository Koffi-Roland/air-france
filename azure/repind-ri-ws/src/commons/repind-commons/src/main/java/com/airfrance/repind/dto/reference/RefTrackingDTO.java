package com.airfrance.repind.dto.reference;

import java.io.Serializable;

public class RefTrackingDTO implements Serializable {

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
     * description
     */
    private String description;
    
    /**
     * icon
     */
    private String icon;

	public RefTrackingDTO() {
		super();
	}

	public RefTrackingDTO(Integer refTrackingId, String code, String value, String valueNormalized, String description, String icon) {
		super();
		this.refTrackingId = refTrackingId;
		this.code = code;
		this.value = value;
		this.valueNormalized = valueNormalized;
		this.description = description;
		this.icon = icon;
	}

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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "RefTrackingDTO [refTrackingId=" + refTrackingId + ", code=" + code + ", value=" + value
				+ ", valueNormalized=" + valueNormalized + ", description=" + description + ", icon=" + icon + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((refTrackingId == null) ? 0 : refTrackingId.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((valueNormalized == null) ? 0 : valueNormalized.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RefTrackingDTO other = (RefTrackingDTO) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (refTrackingId == null) {
			if (other.refTrackingId != null)
				return false;
		} else if (!refTrackingId.equals(other.refTrackingId))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (valueNormalized == null) {
			if (other.valueNormalized != null)
				return false;
		} else if (!valueNormalized.equals(other.valueNormalized))
			return false;
		return true;
	}
}
