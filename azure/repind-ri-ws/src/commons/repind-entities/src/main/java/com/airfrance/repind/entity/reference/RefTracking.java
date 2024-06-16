package com.airfrance.repind.entity.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="REF_TRACKING")
public class RefTracking implements Serializable {

    private static final long serialVersionUID = 1L;
            
    /**
     * refTrackingId
     */
    @Id
    @Column(name="REF_TRACKING_ID", length=12, nullable=false)
    private Integer refTrackingId;
        
            
    /**
     * code
     */
    @Column(name="SCODE", length=50, nullable=false)
    private String code;
        
            
    /**
     * value
     */
    @Column(name="SVALUE", length=255, nullable=false)
    private String value;
    
    /**
     * valueNormalized
     */
    @Column(name="SVALUE_NORMALIZED", length=255, nullable=false)
    private String valueNormalized;
    
    /**
     * description
     */
    @Column(name="SDESCRIPTION", length=255)
    private String description;
    
    /**
     * icon
     */
    @Column(name="SICON", length=255)
    private String icon;

	public RefTracking() {
		super();
	}

	public RefTracking(Integer refTrackingId, String code, String value, String valueNormalized, String description, String icon) {
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
		return "RefTracking [refTrackingId=" + refTrackingId + ", code=" + code + ", value=" + value
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
		RefTracking other = (RefTracking) obj;
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
