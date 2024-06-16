package com.airfrance.repind.dto.adresse.adh;

/*PROTECTED REGION ID(_HXntsHx2EeCAmbGwtfTi3Q i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : NormalisationSoftResponseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class NormalisationSoftResponseDTO  {
        
    /**
     * wsErr
     */
    private Integer wsErr;
        
        
    /**
     * rowId
     */
    private String rowId;
        
        
    /**
     * returnCode1
     */
    private Integer returnCode1;
        
        
    /**
     * returnCode2
     */
    private String returnCode2;
        
        
    /**
     * adrComplement
     */
    private String adrComplement;
        
        
    /**
     * numAndStreet
     */
    private String numAndStreet;
        
        
    /**
     * locality
     */
    private String locality;
        
        
    /**
     * zipCode
     */
    private String zipCode;
        
        
    /**
     * cityR
     */
    private String cityR;
        
        
    /**
     * state
     */
    private String state;
        
        
    /**
     * country
     */
    private String country;
        
        
    /**
     * mailingAdrLine1
     */
    private String mailingAdrLine1;
        
        
    /**
     * mailingAdrLine2
     */
    private String mailingAdrLine2;
        
        
    /**
     * mailingAdrLine3
     */
    private String mailingAdrLine3;
        
        
    /**
     * mailingAdrLine4
     */
    private String mailingAdrLine4;
        
        
    /**
     * mailingAdrLine5
     */
    private String mailingAdrLine5;
        
        
    /**
     * mailingAdrLine6
     */
    private String mailingAdrLine6;
        
        
    /**
     * mailingAdrLine7
     */
    private String mailingAdrLine7;
        
        
    /**
     * mailingAdrLine8
     */
    private String mailingAdrLine8;
        
        
    /**
     * mailingAdrLine9
     */
    private String mailingAdrLine9;
        
        
    /**
     * className
     */
    private String className;
        
        
    /**
     * numError
     */
    private String numError;
        
        
    /**
     * libError
     */
    private String libError;
        

    /*PROTECTED REGION ID(_HXntsHx2EeCAmbGwtfTi3Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public NormalisationSoftResponseDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pWsErr wsErr
     * @param pRowId rowId
     * @param pReturnCode1 returnCode1
     * @param pReturnCode2 returnCode2
     * @param pAdrComplement adrComplement
     * @param pNumAndStreet numAndStreet
     * @param pLocality locality
     * @param pZipCode zipCode
     * @param pCityR cityR
     * @param pState state
     * @param pCountry country
     * @param pMailingAdrLine1 mailingAdrLine1
     * @param pMailingAdrLine2 mailingAdrLine2
     * @param pMailingAdrLine3 mailingAdrLine3
     * @param pMailingAdrLine4 mailingAdrLine4
     * @param pMailingAdrLine5 mailingAdrLine5
     * @param pMailingAdrLine6 mailingAdrLine6
     * @param pMailingAdrLine7 mailingAdrLine7
     * @param pMailingAdrLine8 mailingAdrLine8
     * @param pMailingAdrLine9 mailingAdrLine9
     * @param pClassName className
     * @param pNumError numError
     * @param pLibError libError
     */
    public NormalisationSoftResponseDTO(Integer pWsErr, String pRowId, Integer pReturnCode1, String pReturnCode2, String pAdrComplement, String pNumAndStreet, String pLocality, String pZipCode, String pCityR, String pState, String pCountry, String pMailingAdrLine1, String pMailingAdrLine2, String pMailingAdrLine3, String pMailingAdrLine4, String pMailingAdrLine5, String pMailingAdrLine6, String pMailingAdrLine7, String pMailingAdrLine8, String pMailingAdrLine9, String pClassName, String pNumError, String pLibError) {
        this.wsErr = pWsErr;
        this.rowId = pRowId;
        this.returnCode1 = pReturnCode1;
        this.returnCode2 = pReturnCode2;
        this.adrComplement = pAdrComplement;
        this.numAndStreet = pNumAndStreet;
        this.locality = pLocality;
        this.zipCode = pZipCode;
        this.cityR = pCityR;
        this.state = pState;
        this.country = pCountry;
        this.mailingAdrLine1 = pMailingAdrLine1;
        this.mailingAdrLine2 = pMailingAdrLine2;
        this.mailingAdrLine3 = pMailingAdrLine3;
        this.mailingAdrLine4 = pMailingAdrLine4;
        this.mailingAdrLine5 = pMailingAdrLine5;
        this.mailingAdrLine6 = pMailingAdrLine6;
        this.mailingAdrLine7 = pMailingAdrLine7;
        this.mailingAdrLine8 = pMailingAdrLine8;
        this.mailingAdrLine9 = pMailingAdrLine9;
        this.className = pClassName;
        this.numError = pNumError;
        this.libError = pLibError;
    }

    /**
     *
     * @return adrComplement
     */
    public String getAdrComplement() {
        return this.adrComplement;
    }

    /**
     *
     * @param pAdrComplement adrComplement value
     */
    public void setAdrComplement(String pAdrComplement) {
        this.adrComplement = pAdrComplement;
    }

    /**
     *
     * @return cityR
     */
    public String getCityR() {
        return this.cityR;
    }

    /**
     *
     * @param pCityR cityR value
     */
    public void setCityR(String pCityR) {
        this.cityR = pCityR;
    }

    /**
     *
     * @return className
     */
    public String getClassName() {
        return this.className;
    }

    /**
     *
     * @param pClassName className value
     */
    public void setClassName(String pClassName) {
        this.className = pClassName;
    }

    /**
     *
     * @return country
     */
    public String getCountry() {
        return this.country;
    }

    /**
     *
     * @param pCountry country value
     */
    public void setCountry(String pCountry) {
        this.country = pCountry;
    }

    /**
     *
     * @return libError
     */
    public String getLibError() {
        return this.libError;
    }

    /**
     *
     * @param pLibError libError value
     */
    public void setLibError(String pLibError) {
        this.libError = pLibError;
    }

    /**
     *
     * @return locality
     */
    public String getLocality() {
        return this.locality;
    }

    /**
     *
     * @param pLocality locality value
     */
    public void setLocality(String pLocality) {
        this.locality = pLocality;
    }

    /**
     *
     * @return mailingAdrLine1
     */
    public String getMailingAdrLine1() {
        return this.mailingAdrLine1;
    }

    /**
     *
     * @param pMailingAdrLine1 mailingAdrLine1 value
     */
    public void setMailingAdrLine1(String pMailingAdrLine1) {
        this.mailingAdrLine1 = pMailingAdrLine1;
    }

    /**
     *
     * @return mailingAdrLine2
     */
    public String getMailingAdrLine2() {
        return this.mailingAdrLine2;
    }

    /**
     *
     * @param pMailingAdrLine2 mailingAdrLine2 value
     */
    public void setMailingAdrLine2(String pMailingAdrLine2) {
        this.mailingAdrLine2 = pMailingAdrLine2;
    }

    /**
     *
     * @return mailingAdrLine3
     */
    public String getMailingAdrLine3() {
        return this.mailingAdrLine3;
    }

    /**
     *
     * @param pMailingAdrLine3 mailingAdrLine3 value
     */
    public void setMailingAdrLine3(String pMailingAdrLine3) {
        this.mailingAdrLine3 = pMailingAdrLine3;
    }

    /**
     *
     * @return mailingAdrLine4
     */
    public String getMailingAdrLine4() {
        return this.mailingAdrLine4;
    }

    /**
     *
     * @param pMailingAdrLine4 mailingAdrLine4 value
     */
    public void setMailingAdrLine4(String pMailingAdrLine4) {
        this.mailingAdrLine4 = pMailingAdrLine4;
    }

    /**
     *
     * @return mailingAdrLine5
     */
    public String getMailingAdrLine5() {
        return this.mailingAdrLine5;
    }

    /**
     *
     * @param pMailingAdrLine5 mailingAdrLine5 value
     */
    public void setMailingAdrLine5(String pMailingAdrLine5) {
        this.mailingAdrLine5 = pMailingAdrLine5;
    }

    /**
     *
     * @return mailingAdrLine6
     */
    public String getMailingAdrLine6() {
        return this.mailingAdrLine6;
    }

    /**
     *
     * @param pMailingAdrLine6 mailingAdrLine6 value
     */
    public void setMailingAdrLine6(String pMailingAdrLine6) {
        this.mailingAdrLine6 = pMailingAdrLine6;
    }

    /**
     *
     * @return mailingAdrLine7
     */
    public String getMailingAdrLine7() {
        return this.mailingAdrLine7;
    }

    /**
     *
     * @param pMailingAdrLine7 mailingAdrLine7 value
     */
    public void setMailingAdrLine7(String pMailingAdrLine7) {
        this.mailingAdrLine7 = pMailingAdrLine7;
    }

    /**
     *
     * @return mailingAdrLine8
     */
    public String getMailingAdrLine8() {
        return this.mailingAdrLine8;
    }

    /**
     *
     * @param pMailingAdrLine8 mailingAdrLine8 value
     */
    public void setMailingAdrLine8(String pMailingAdrLine8) {
        this.mailingAdrLine8 = pMailingAdrLine8;
    }

    /**
     *
     * @return mailingAdrLine9
     */
    public String getMailingAdrLine9() {
        return this.mailingAdrLine9;
    }

    /**
     *
     * @param pMailingAdrLine9 mailingAdrLine9 value
     */
    public void setMailingAdrLine9(String pMailingAdrLine9) {
        this.mailingAdrLine9 = pMailingAdrLine9;
    }

    /**
     *
     * @return numAndStreet
     */
    public String getNumAndStreet() {
        return this.numAndStreet;
    }

    /**
     *
     * @param pNumAndStreet numAndStreet value
     */
    public void setNumAndStreet(String pNumAndStreet) {
        this.numAndStreet = pNumAndStreet;
    }

    /**
     *
     * @return numError
     */
    public String getNumError() {
        return this.numError;
    }

    /**
     *
     * @param pNumError numError value
     */
    public void setNumError(String pNumError) {
        this.numError = pNumError;
    }

    /**
     *
     * @return returnCode1
     */
    public Integer getReturnCode1() {
        return this.returnCode1;
    }

    /**
     *
     * @param pReturnCode1 returnCode1 value
     */
    public void setReturnCode1(Integer pReturnCode1) {
        this.returnCode1 = pReturnCode1;
    }

    /**
     *
     * @return returnCode2
     */
    public String getReturnCode2() {
        return this.returnCode2;
    }

    /**
     *
     * @param pReturnCode2 returnCode2 value
     */
    public void setReturnCode2(String pReturnCode2) {
        this.returnCode2 = pReturnCode2;
    }

    /**
     *
     * @return rowId
     */
    public String getRowId() {
        return this.rowId;
    }

    /**
     *
     * @param pRowId rowId value
     */
    public void setRowId(String pRowId) {
        this.rowId = pRowId;
    }

    /**
     *
     * @return state
     */
    public String getState() {
        return this.state;
    }

    /**
     *
     * @param pState state value
     */
    public void setState(String pState) {
        this.state = pState;
    }

    /**
     *
     * @return wsErr
     */
    public Integer getWsErr() {
        return this.wsErr;
    }

    /**
     *
     * @param pWsErr wsErr value
     */
    public void setWsErr(Integer pWsErr) {
        this.wsErr = pWsErr;
    }

    /**
     *
     * @return zipCode
     */
    public String getZipCode() {
        return this.zipCode;
    }

    /**
     *
     * @param pZipCode zipCode value
     */
    public void setZipCode(String pZipCode) {
        this.zipCode = pZipCode;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_HXntsHx2EeCAmbGwtfTi3Q) ENABLED START*/
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
            .append("wsErr", getWsErr())
            .append("rowId", getRowId())
            .append("returnCode1", getReturnCode1())
            .append("returnCode2", getReturnCode2())
            .append("adrComplement", getAdrComplement())
            .append("numAndStreet", getNumAndStreet())
            .append("locality", getLocality())
            .append("zipCode", getZipCode())
            .append("cityR", getCityR())
            .append("state", getState())
            .append("country", getCountry())
            .append("mailingAdrLine1", getMailingAdrLine1())
            .append("mailingAdrLine2", getMailingAdrLine2())
            .append("mailingAdrLine3", getMailingAdrLine3())
            .append("mailingAdrLine4", getMailingAdrLine4())
            .append("mailingAdrLine5", getMailingAdrLine5())
            .append("mailingAdrLine6", getMailingAdrLine6())
            .append("mailingAdrLine7", getMailingAdrLine7())
            .append("mailingAdrLine8", getMailingAdrLine8())
            .append("mailingAdrLine9", getMailingAdrLine9())
            .append("className", getClassName())
            .append("numError", getNumError())
            .append("libError", getLibError())
            .toString();
    }

    /*PROTECTED REGION ID(_HXntsHx2EeCAmbGwtfTi3Q u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
