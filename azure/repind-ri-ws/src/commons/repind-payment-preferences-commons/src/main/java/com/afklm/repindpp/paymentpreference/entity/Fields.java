package com.afklm.repindpp.paymentpreference.entity;

/*PROTECTED REGION ID(_cqlq4FreEeCAX-eiAOZ-9g i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : Fields.java</p>
 * BO Fields
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


public class Fields implements Serializable {

/*PROTECTED REGION ID(serialUID _cqlq4FreEeCAX-eiAOZ-9g) ENABLED START*/
   /**
   * Determines if a de-serialized file is compatible with this class.
   *
   * Maintainers must change this value if and only if the new version
   * of this class is not compatible with old versions. See Sun docs
   * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
   * /serialization/spec/class.html#4100> details. </a>
   *
   * Not necessary to include in first version of the class, but
   * included here as a reminder of its importance.
   */
    private static final long serialVersionUID = 1L;
/*PROTECTED REGION END*/

            
    /**
     * fieldId
     */
    @Id
	@Column(name = "FIELDID")
	@SequenceGenerator(name = "ISEQ_FIELDS_ID", sequenceName = "ISEQ_FIELDS_ID",
			allocationSize = 1)
	@GeneratedValue(generator = "ISEQ_FIELDS_ID")
    private Integer fieldId;
        
            
    /**
     * paymentFieldCode
     */
    private String paymentFieldCode;
        
            
    /**
     * paymentFieldPreference
     */
    private String paymentFieldPreference;
        
            
    /**
     * paymentdetails
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(nullable=false)
    @ForeignKey(name = "FK_FIELDS_PAYMENTDETAILS")
    private PaymentDetails paymentdetails;
        
    /*PROTECTED REGION ID(_cqlq4FreEeCAX-eiAOZ-9g u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    
    /** 
     * default constructor 
     */
    public Fields() {
    }
    
        
    /** 
     * full constructor
     * @param pFieldId fieldId
     * @param pPaymentFieldCode paymentFieldCode
     * @param pPaymentFieldPreference paymentFieldPreference
     */
    public Fields(Integer pFieldId, String pPaymentFieldCode, String pPaymentFieldPreference) {
        this.fieldId = pFieldId;
        this.paymentFieldCode = pPaymentFieldCode;
        this.paymentFieldPreference = pPaymentFieldPreference;
    }
    
    /**
     *
     * @return fieldId
     */
    public Integer getFieldId() {
        return this.fieldId;
    }

    /**
     *
     * @param pFieldId fieldId value
     */
    public void setFieldId(Integer pFieldId) {
        this.fieldId = pFieldId;
    }

    /**
     *
     * @return paymentFieldCode
     */
    public String getPaymentFieldCode() {
        return this.paymentFieldCode;
    }

    /**
     *
     * @param pPaymentFieldCode paymentFieldCode value
     */
    public void setPaymentFieldCode(String pPaymentFieldCode) {
        this.paymentFieldCode = pPaymentFieldCode;
    }

    /**
     *
     * @return paymentFieldPreference
     */
    public String getPaymentFieldPreference() {
        return this.paymentFieldPreference;
    }

    /**
     *
     * @param pPaymentFieldPreference paymentFieldPreference value
     */
    public void setPaymentFieldPreference(String pPaymentFieldPreference) {
        this.paymentFieldPreference = pPaymentFieldPreference;
    }

    /**
     *
     * @return paymentdetails
     */
    public PaymentDetails getPaymentdetails() {
        return this.paymentdetails;
    }

    /**
     *
     * @param pPaymentdetails paymentdetails value
     */
    public void setPaymentdetails(PaymentDetails pPaymentdetails) {
        this.paymentdetails = pPaymentdetails;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_cqlq4FreEeCAX-eiAOZ-9g) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }
    
    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
            .append("fieldId", getFieldId())
            .append("paymentFieldCode", getPaymentFieldCode())
            .append("paymentFieldPreference", getPaymentFieldPreference())
            .toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _cqlq4FreEeCAX-eiAOZ-9g) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fields other = (Fields) obj;

        // TODO: writes or generates equals method
        
        return super.equals(other);
    }
  
    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        // TODO: writes or generates hashcode method

        return result;
    }
    
    /*PROTECTED REGION END*/
    
    /*PROTECTED REGION ID(_cqlq4FreEeCAX-eiAOZ-9g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
