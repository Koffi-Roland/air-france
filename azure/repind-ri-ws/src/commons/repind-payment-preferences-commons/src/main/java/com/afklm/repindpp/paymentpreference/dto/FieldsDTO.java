package com.afklm.repindpp.paymentpreference.dto;

/*PROTECTED REGION ID(_YtXqkFriEeCAX-eiAOZ-9g i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;



/*PROTECTED REGION END*/

/**
 * <p>Title : FieldsDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class FieldsDTO  {
        
    /**
     * fieldId
     */
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
     * paymentsdetailsdto
     */
    private PaymentsDetailsDTO paymentsdetailsdto;
        

    /*PROTECTED REGION ID(_YtXqkFriEeCAX-eiAOZ-9g u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public FieldsDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pFieldId fieldId
     * @param pPaymentFieldCode paymentFieldCode
     * @param pPaymentFieldPreference paymentFieldPreference
     */
    public FieldsDTO(Integer pFieldId, String pPaymentFieldCode, String pPaymentFieldPreference) {
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
     * @return paymentsdetailsdto
     */
    public PaymentsDetailsDTO getPaymentsdetailsdto() {
        return this.paymentsdetailsdto;
    }

    /**
     *
     * @param pPaymentsdetailsdto paymentsdetailsdto value
     */
    public void setPaymentsdetailsdto(PaymentsDetailsDTO pPaymentsdetailsdto) {
        this.paymentsdetailsdto = pPaymentsdetailsdto;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_YtXqkFriEeCAX-eiAOZ-9g) ENABLED START*/
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

    /*PROTECTED REGION ID(_YtXqkFriEeCAX-eiAOZ-9g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
