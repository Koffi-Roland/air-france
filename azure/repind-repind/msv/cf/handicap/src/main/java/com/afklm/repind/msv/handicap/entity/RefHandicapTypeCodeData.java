package com.afklm.repind.msv.handicap.entity;

import jakarta.persistence.*;

import java.io.Serializable;





@Entity


@Table(name="REF_HANDICAP_TYPE_CODE_DATA")
public class RefHandicapTypeCodeData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private RefHandicapTypeCodeDataID refHandicapTypeCodeDataID;
    
    /**
     * minLength
     */
    @Column(name="IMIN_LENGTH")
    private Integer minLength;
        
            
    /**
     * maxLength
     */
    @Column(name="IMAX_LENGTH")
    private Integer maxLength;
        
            
    /**
     * dataType
     */
    @Column(name="SDATA_TYPE")
    private String dataType;
        
            
    /**
     * condition
     */
    @Column(name="SCONDITION")
    private String condition;


	public RefHandicapTypeCodeDataID getRefHandicapTypeCodeDataID() {
		return refHandicapTypeCodeDataID;
	}


	public void setRefHandicapTypeCodeDataID(RefHandicapTypeCodeDataID refHandicapTypeCodeDataID) {
		this.refHandicapTypeCodeDataID = refHandicapTypeCodeDataID;
	}


	public Integer getMinLength() {
		return minLength;
	}


	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}


	public Integer getMaxLength() {
		return maxLength;
	}


	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}


	public String getDataType() {
		return dataType;
	}


	public void setDataType(String dataType) {
		this.dataType = dataType;
	}


	public String getCondition() {
		return condition;
	}


	public void setCondition(String condition) {
		this.condition = condition;
	}
    
}
