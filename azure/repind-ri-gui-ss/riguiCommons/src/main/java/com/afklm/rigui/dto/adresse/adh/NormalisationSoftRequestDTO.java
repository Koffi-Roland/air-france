package com.afklm.rigui.dto.adresse.adh;

/*PROTECTED REGION ID(_BFQXwHx2EeCAmbGwtfTi3Q i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : NormalisationSoftRequestDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class NormalisationSoftRequestDTO  {
        
    /**
     * uid
     */
    private String uid;
        
        
    /**
     * upwd
     */
    private String upwd;
        
        
    /**
     * rowid
     */
    private String rowid;
        
        
    /**
     * className
     */
    private String className;
        
        
    /**
     * comp
     */
    private String comp;
        
        
    /**
     * street
     */
    private String street;
        
        
    /**
     * local
     */
    private String local;
        
        
    /**
     * zip
     */
    private String zip;
        
        
    /**
     * city
     */
    private String city;
        
        
    /**
     * state
     */
    private String state;
        
        
    /**
     * country
     */
    private String country;
        

    /*PROTECTED REGION ID(_BFQXwHx2EeCAmbGwtfTi3Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public NormalisationSoftRequestDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pUid uid
     * @param pUpwd upwd
     * @param pRowid rowid
     * @param pClassName className
     * @param pComp comp
     * @param pStreet street
     * @param pLocal local
     * @param pZip zip
     * @param pCity city
     * @param pState state
     * @param pCountry country
     */
    public NormalisationSoftRequestDTO(String pUid, String pUpwd, String pRowid, String pClassName, String pComp, String pStreet, String pLocal, String pZip, String pCity, String pState, String pCountry) {
        this.uid = pUid;
        this.upwd = pUpwd;
        this.rowid = pRowid;
        this.className = pClassName;
        this.comp = pComp;
        this.street = pStreet;
        this.local = pLocal;
        this.zip = pZip;
        this.city = pCity;
        this.state = pState;
        this.country = pCountry;
    }

    /**
     *
     * @return city
     */
    public String getCity() {
        return this.city;
    }

    /**
     *
     * @param pCity city value
     */
    public void setCity(String pCity) {
        this.city = pCity;
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
     * @return comp
     */
    public String getComp() {
        return this.comp;
    }

    /**
     *
     * @param pComp comp value
     */
    public void setComp(String pComp) {
        this.comp = pComp;
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
     * @return local
     */
    public String getLocal() {
        return this.local;
    }

    /**
     *
     * @param pLocal local value
     */
    public void setLocal(String pLocal) {
        this.local = pLocal;
    }

    /**
     *
     * @return rowid
     */
    public String getRowid() {
        return this.rowid;
    }

    /**
     *
     * @param pRowid rowid value
     */
    public void setRowid(String pRowid) {
        this.rowid = pRowid;
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
     * @return street
     */
    public String getStreet() {
        return this.street;
    }

    /**
     *
     * @param pStreet street value
     */
    public void setStreet(String pStreet) {
        this.street = pStreet;
    }

    /**
     *
     * @return uid
     */
    public String getUid() {
        return this.uid;
    }

    /**
     *
     * @param pUid uid value
     */
    public void setUid(String pUid) {
        this.uid = pUid;
    }

    /**
     *
     * @return upwd
     */
    public String getUpwd() {
        return this.upwd;
    }

    /**
     *
     * @param pUpwd upwd value
     */
    public void setUpwd(String pUpwd) {
        this.upwd = pUpwd;
    }

    /**
     *
     * @return zip
     */
    public String getZip() {
        return this.zip;
    }

    /**
     *
     * @param pZip zip value
     */
    public void setZip(String pZip) {
        this.zip = pZip;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_BFQXwHx2EeCAmbGwtfTi3Q) ENABLED START*/
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
            .append("uid", getUid())
            .append("upwd", getUpwd())
            .append("rowid", getRowid())
            .append("className", getClassName())
            .append("comp", getComp())
            .append("street", getStreet())
            .append("local", getLocal())
            .append("zip", getZip())
            .append("city", getCity())
            .append("state", getState())
            .append("country", getCountry())
            .toString();
    }

    /*PROTECTED REGION ID(_BFQXwHx2EeCAmbGwtfTi3Q u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
