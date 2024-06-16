package com.airfrance.repind.entity.agence;

/*PROTECTED REGION ID(_N3he4PqXEeaFPPuBEnmKFQ i) ENABLED START*/

// add not generated imports here

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : BspData.java</p>
 * BO BspData
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="BSP_DATA")
public class BspData implements Serializable {

/*PROTECTED REGION ID(serialUID _N3he4PqXEeaFPPuBEnmKFQ) ENABLED START*/
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
     * bspDataId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_BSP_DATA")
    @SequenceGenerator(name="ISEQ_BSP_DATA", sequenceName = "ISEQ_BSP_DATA", allocationSize = 1)
    @Column(name="BSP_DATA_ID", length=12)
    private Integer bspDataId;
        
            
    /**
     * gin
     */
    @Column(name="SGIN", length=12)
    private String gin;
        
            
    /**
     * type
     */
    @Column(name="TYPE", length=50, nullable=false)
    private String type;
        
            
    /**
     * company
     */
    @Column(name="COMPANY", length=50, nullable=false)
    private String company;
        
            
    /**
     * authorization
     */
    @Column(name="AUTHORIZATION", length=1, nullable=false)
    private String authorization;
        
            
    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;
        
            
    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", length=16)
    private String signatureCreation;
        
            
    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION", length=10)
    private String siteCreation;
        
            
    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;
        
            
    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16)
    private String signatureModification;
        
            
    /**
     * siteModification
     */
    @Column(name="SSITE_MODIFICATION", length=10)
    private String siteModification;
        
    /*PROTECTED REGION ID(_N3he4PqXEeaFPPuBEnmKFQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public BspData() {
    	//empty constructor
    }
        
    /** 
     * full constructor
     * @param pBspDataId bspDataId
     * @param pGin gin
     * @param pType type
     * @param pCompany company
     * @param pAuthorization authorization
     * @param pDateCreation dateCreation
     * @param pSignatureCreation signatureCreation
     * @param pSiteCreation siteCreation
     * @param pDateModification dateModification
     * @param pSignatureModification signatureModification
     * @param pSiteModification siteModification
     */
    public BspData(Integer pBspDataId, String pGin, String pType, String pCompany, String pAuthorization, Date pDateCreation, String pSignatureCreation, String pSiteCreation, Date pDateModification, String pSignatureModification, String pSiteModification) {
        this.bspDataId = pBspDataId;
        this.gin = pGin;
        this.type = pType;
        this.company = pCompany;
        this.authorization = pAuthorization;
        this.dateCreation = pDateCreation;
        this.signatureCreation = pSignatureCreation;
        this.siteCreation = pSiteCreation;
        this.dateModification = pDateModification;
        this.signatureModification = pSignatureModification;
        this.siteModification = pSiteModification;
    }

    /**
     *
     * @return authorization
     */
    public String getAuthorization() {
        return this.authorization;
    }

    /**
     *
     * @param pAuthorization authorization value
     */
    public void setAuthorization(String pAuthorization) {
        this.authorization = pAuthorization;
    }

    /**
     *
     * @return bspDataId
     */
    public Integer getBspDataId() {
        return this.bspDataId;
    }

    /**
     *
     * @param pBspDataId bspDataId value
     */
    public void setBspDataId(Integer pBspDataId) {
        this.bspDataId = pBspDataId;
    }

    /**
     *
     * @return company
     */
    public String getCompany() {
        return this.company;
    }

    /**
     *
     * @param pCompany company value
     */
    public void setCompany(String pCompany) {
        this.company = pCompany;
    }

    /**
     *
     * @return dateCreation
     */
    public Date getDateCreation() {
        return this.dateCreation;
    }

    /**
     *
     * @param pDateCreation dateCreation value
     */
    public void setDateCreation(Date pDateCreation) {
        this.dateCreation = pDateCreation;
    }

    /**
     *
     * @return dateModification
     */
    public Date getDateModification() {
        return this.dateModification;
    }

    /**
     *
     * @param pDateModification dateModification value
     */
    public void setDateModification(Date pDateModification) {
        this.dateModification = pDateModification;
    }

    /**
     *
     * @return gin
     */
    public String getGin() {
        return this.gin;
    }

    /**
     *
     * @param pGin gin value
     */
    public void setGin(String pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return signatureCreation
     */
    public String getSignatureCreation() {
        return this.signatureCreation;
    }

    /**
     *
     * @param pSignatureCreation signatureCreation value
     */
    public void setSignatureCreation(String pSignatureCreation) {
        this.signatureCreation = pSignatureCreation;
    }

    /**
     *
     * @return signatureModification
     */
    public String getSignatureModification() {
        return this.signatureModification;
    }

    /**
     *
     * @param pSignatureModification signatureModification value
     */
    public void setSignatureModification(String pSignatureModification) {
        this.signatureModification = pSignatureModification;
    }

    /**
     *
     * @return siteCreation
     */
    public String getSiteCreation() {
        return this.siteCreation;
    }

    /**
     *
     * @param pSiteCreation siteCreation value
     */
    public void setSiteCreation(String pSiteCreation) {
        this.siteCreation = pSiteCreation;
    }

    /**
     *
     * @return siteModification
     */
    public String getSiteModification() {
        return this.siteModification;
    }

    /**
     *
     * @param pSiteModification siteModification value
     */
    public void setSiteModification(String pSiteModification) {
        this.siteModification = pSiteModification;
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     * @param pType type value
     */
    public void setType(String pType) {
        this.type = pType;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_N3he4PqXEeaFPPuBEnmKFQ) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }
    
    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
        buffer.append("[");
        buffer.append("bspDataId=").append(getBspDataId());
        buffer.append(",");
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("company=").append(getCompany());
        buffer.append(",");
        buffer.append("authorization=").append(getAuthorization());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _N3he4PqXEeaFPPuBEnmKFQ) ENABLED START*/
    
    /**
     * {@inheritDoc}
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        //TODO Customize here if necessary
        return hashCodeImpl();
    }

    /**
     * {@inheritDoc}
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        //TODO Customize here if necessary
        return equalsImpl(obj);
    }
    
    /*PROTECTED REGION END*/
    
    /**
     * Generated implementation method for hashCode
     * You can stop calling it in the hashCode() generated in protected region if necessary
     * @return hashcode
     */
    private int hashCodeImpl() {
        return super.hashCode();
    }

    /**
     * Generated implementation method for equals
     * You can stop calling it in the equals() generated in protected region if necessary
     * @return if param equals the current object
     * @param obj Object to compare with current
     */
    private boolean equalsImpl(Object obj) {
        return super.equals(obj);
    }

    /*PROTECTED REGION ID(_N3he4PqXEeaFPPuBEnmKFQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
