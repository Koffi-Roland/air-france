package com.afklm.rigui.dto.individu;

/*PROTECTED REGION ID(_tyzIED9KEeCFh9Ea_LFCog i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : UsageClientsDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class UsageClientsDTO  {
        
    /**
     * srin
     */
    private String srin;
        
        
    /**
     * sgin
     */
    private String sgin;
        
        
    /**
     * scode
     */
    private String scode;
        
        
    /**
     * sconst
     */
    private String sconst;
        
        
    /**
     * date_modification
     */
    private Date date_modification;
        

    /*PROTECTED REGION ID(_tyzIED9KEeCFh9Ea_LFCog u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public UsageClientsDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pSrin srin
     * @param pSgin sgin
     * @param pScode scode
     * @param pSconst sconst
     * @param pDate_modification date_modification
     */
    public UsageClientsDTO(String pSrin, String pSgin, String pScode, String pSconst, Date pDate_modification) {
        this.srin = pSrin;
        this.sgin = pSgin;
        this.scode = pScode;
        this.sconst = pSconst;
        this.date_modification = pDate_modification;
    }

    /**
     *
     * @return date_modification
     */
    public Date getDate_modification() {
        return this.date_modification;
    }

    /**
     *
     * @param pDate_modification date_modification value
     */
    public void setDate_modification(Date pDate_modification) {
        this.date_modification = pDate_modification;
    }

    /**
     *
     * @return scode
     */
    public String getScode() {
        return this.scode;
    }

    /**
     *
     * @param pScode scode value
     */
    public void setScode(String pScode) {
        this.scode = pScode;
    }

    /**
     *
     * @return sconst
     */
    public String getSconst() {
        return this.sconst;
    }

    /**
     *
     * @param pSconst sconst value
     */
    public void setSconst(String pSconst) {
        this.sconst = pSconst;
    }

    /**
     *
     * @return sgin
     */
    public String getSgin() {
        return this.sgin;
    }

    /**
     *
     * @param pSgin sgin value
     */
    public void setSgin(String pSgin) {
        this.sgin = pSgin;
    }

    /**
     *
     * @return srin
     */
    public String getSrin() {
        return this.srin;
    }

    /**
     *
     * @param pSrin srin value
     */
    public void setSrin(String pSrin) {
        this.srin = pSrin;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_tyzIED9KEeCFh9Ea_LFCog) ENABLED START*/
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
            .append("srin", getSrin())
            .append("sgin", getSgin())
            .append("scode", getScode())
            .append("sconst", getSconst())
            .append("date_modification", getDate_modification())
            .toString();
    }

    /*PROTECTED REGION ID(_tyzIED9KEeCFh9Ea_LFCog u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
