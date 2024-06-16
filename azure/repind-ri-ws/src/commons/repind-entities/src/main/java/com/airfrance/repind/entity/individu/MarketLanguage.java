package com.airfrance.repind.entity.individu;

/*PROTECTED REGION ID(_abil8JiEEeKTJYyx5c6zlQ i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : MarketLanguage.java</p>
 * BO MarketLanguage
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="MARKET_LANGUAGE")
public class MarketLanguage implements Serializable {

/*PROTECTED REGION ID(serialUID _abil8JiEEeKTJYyx5c6zlQ) ENABLED START*/
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
     * marketLanguageId
     */
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MARKET_LANGUAGE")
    @SequenceGenerator(name="SEQ_MARKET_LANGUAGE", sequenceName = "SEQ_MARKET_LANGUAGE",
			allocationSize = 1)
    @Column(name="MARKET_LANGUAGE_ID", length=12, nullable=false)
    private Integer marketLanguageId;
        
            
    /**
     * comPrefId
     */
    @Column(name="COM_PREF_ID", length=12)
    private Integer comPrefId;
        
            
    /**
     * market
     */
    @Column(name="MARKET", length=3, nullable=false)
    private String market;
        
            
    /**
     * language
     */
    @Column(name="LANGUAGE_CODE", length=2)
    private String language;
        
            
    /**
     * optIn
     */
    @Column(name="OPTIN", length=1, nullable=false)
    private String optIn;
        
            
    /**
     * dateOfConsent
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE_OPTIN")
    private Date dateOfConsent;
        
            
    /**
     * communicationMedia1
     */
    @Column(name="MEDIA1", length=1)
    private String communicationMedia1;
        
            
    /**
     * communicationMedia2
     */
    @Column(name="MEDIA2", length=1)
    private String communicationMedia2;
        
            
    /**
     * communicationMedia3
     */
    @Column(name="MEDIA3", length=1)
    private String communicationMedia3;
        
            
    /**
     * communicationMedia4
     */
    @Column(name="MEDIA4", length=1)
    private String communicationMedia4;
        
            
    /**
     * communicationMedia5
     */
    @Column(name="MEDIA5", length=1)
    private String communicationMedia5;
        
            
    /**
     * creationDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATION_DATE")
    private Date creationDate;
        
            
    /**
     * creationSignature
     */
    @Column(name="CREATION_SIGNATURE", length=16, nullable=false)
    private String creationSignature;
        
            
    /**
     * creationSite
     */
    @Column(name="CREATION_SITE", length=10, nullable=false)
    private String creationSite;
        
            
    /**
     * modificationDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="MODIFICATION_DATE")
    private Date modificationDate;
        
            
    /**
     * modificationSignature
     */
    @Column(name="MODIFICATION_SIGNATURE", length=16)
    private String modificationSignature;
        
            
    /**
     * modificationSite
     */
    @Column(name="MODIFICATION_SITE", length=10)
    private String modificationSite;
        
    /*PROTECTED REGION ID(_abil8JiEEeKTJYyx5c6zlQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    
    /** 
     * default constructor 
     */
    public MarketLanguage() {
    }
    
        
    /** 
     * full constructor
     * @param pMarketLanguageId marketLanguageId
     * @param pComPrefId comPrefId
     * @param pMarket market
     * @param pLanguage language
     * @param pOptIn optIn
     * @param pDateOfConsent dateOfConsent
     * @param pCommunicationMedia1 communicationMedia1
     * @param pCommunicationMedia2 communicationMedia2
     * @param pCommunicationMedia3 communicationMedia3
     * @param pCommunicationMedia4 communicationMedia4
     * @param pCommunicationMedia5 communicationMedia5
     * @param pCreationDate creationDate
     * @param pCreationSignature creationSignature
     * @param pCreationSite creationSite
     * @param pModificationDate modificationDate
     * @param pModificationSignature modificationSignature
     * @param pModificationSite modificationSite
     */
    public MarketLanguage(Integer pMarketLanguageId, Integer pComPrefId, String pMarket, String pLanguage, String pOptIn, Date pDateOfConsent, String pCommunicationMedia1, String pCommunicationMedia2, String pCommunicationMedia3, String pCommunicationMedia4, String pCommunicationMedia5, Date pCreationDate, String pCreationSignature, String pCreationSite, Date pModificationDate, String pModificationSignature, String pModificationSite) {
        this.marketLanguageId = pMarketLanguageId;
        this.comPrefId = pComPrefId;
        this.market = pMarket;
        this.language = pLanguage;
        this.optIn = pOptIn;
        this.dateOfConsent = pDateOfConsent;
        this.communicationMedia1 = pCommunicationMedia1;
        this.communicationMedia2 = pCommunicationMedia2;
        this.communicationMedia3 = pCommunicationMedia3;
        this.communicationMedia4 = pCommunicationMedia4;
        this.communicationMedia5 = pCommunicationMedia5;
        this.creationDate = pCreationDate;
        this.creationSignature = pCreationSignature;
        this.creationSite = pCreationSite;
        this.modificationDate = pModificationDate;
        this.modificationSignature = pModificationSignature;
        this.modificationSite = pModificationSite;
    }

    /**
     *
     * @return comPrefId
     */
    public Integer getComPrefId() {
        return this.comPrefId;
    }

    /**
     *
     * @param pComPrefId comPrefId value
     */
    public void setComPrefId(Integer pComPrefId) {
        this.comPrefId = pComPrefId;
    }

    /**
     *
     * @return communicationMedia1
     */
    public String getCommunicationMedia1() {
        return this.communicationMedia1;
    }

    /**
     *
     * @param pCommunicationMedia1 communicationMedia1 value
     */
    public void setCommunicationMedia1(String pCommunicationMedia1) {
        this.communicationMedia1 = pCommunicationMedia1;
    }

    /**
     *
     * @return communicationMedia2
     */
    public String getCommunicationMedia2() {
        return this.communicationMedia2;
    }

    /**
     *
     * @param pCommunicationMedia2 communicationMedia2 value
     */
    public void setCommunicationMedia2(String pCommunicationMedia2) {
        this.communicationMedia2 = pCommunicationMedia2;
    }

    /**
     *
     * @return communicationMedia3
     */
    public String getCommunicationMedia3() {
        return this.communicationMedia3;
    }

    /**
     *
     * @param pCommunicationMedia3 communicationMedia3 value
     */
    public void setCommunicationMedia3(String pCommunicationMedia3) {
        this.communicationMedia3 = pCommunicationMedia3;
    }

    /**
     *
     * @return communicationMedia4
     */
    public String getCommunicationMedia4() {
        return this.communicationMedia4;
    }

    /**
     *
     * @param pCommunicationMedia4 communicationMedia4 value
     */
    public void setCommunicationMedia4(String pCommunicationMedia4) {
        this.communicationMedia4 = pCommunicationMedia4;
    }

    /**
     *
     * @return communicationMedia5
     */
    public String getCommunicationMedia5() {
        return this.communicationMedia5;
    }

    /**
     *
     * @param pCommunicationMedia5 communicationMedia5 value
     */
    public void setCommunicationMedia5(String pCommunicationMedia5) {
        this.communicationMedia5 = pCommunicationMedia5;
    }

    /**
     *
     * @return creationDate
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     *
     * @param pCreationDate creationDate value
     */
    public void setCreationDate(Date pCreationDate) {
        this.creationDate = pCreationDate;
    }

    /**
     *
     * @return creationSignature
     */
    public String getCreationSignature() {
        return this.creationSignature;
    }

    /**
     *
     * @param pCreationSignature creationSignature value
     */
    public void setCreationSignature(String pCreationSignature) {
        this.creationSignature = pCreationSignature;
    }

    /**
     *
     * @return creationSite
     */
    public String getCreationSite() {
        return this.creationSite;
    }

    /**
     *
     * @param pCreationSite creationSite value
     */
    public void setCreationSite(String pCreationSite) {
        this.creationSite = pCreationSite;
    }

    /**
     *
     * @return dateOfConsent
     */
    public Date getDateOfConsent() {
        return this.dateOfConsent;
    }

    /**
     *
     * @param pDateOfConsent dateOfConsent value
     */
    public void setDateOfConsent(Date pDateOfConsent) {
        this.dateOfConsent = pDateOfConsent;
    }

    /**
     *
     * @return language
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     *
     * @param pLanguage language value
     */
    public void setLanguage(String pLanguage) {
        this.language = pLanguage;
    }

    /**
     *
     * @return market
     */
    public String getMarket() {
        return this.market;
    }

    /**
     *
     * @param pMarket market value
     */
    public void setMarket(String pMarket) {
        this.market = pMarket;
    }

    /**
     *
     * @return marketLanguageId
     */
    public Integer getMarketLanguageId() {
        return this.marketLanguageId;
    }

    /**
     *
     * @param pMarketLanguageId marketLanguageId value
     */
    public void setMarketLanguageId(Integer pMarketLanguageId) {
        this.marketLanguageId = pMarketLanguageId;
    }

    /**
     *
     * @return modificationDate
     */
    public Date getModificationDate() {
        return this.modificationDate;
    }

    /**
     *
     * @param pModificationDate modificationDate value
     */
    public void setModificationDate(Date pModificationDate) {
        this.modificationDate = pModificationDate;
    }

    /**
     *
     * @return modificationSignature
     */
    public String getModificationSignature() {
        return this.modificationSignature;
    }

    /**
     *
     * @param pModificationSignature modificationSignature value
     */
    public void setModificationSignature(String pModificationSignature) {
        this.modificationSignature = pModificationSignature;
    }

    /**
     *
     * @return modificationSite
     */
    public String getModificationSite() {
        return this.modificationSite;
    }

    /**
     *
     * @param pModificationSite modificationSite value
     */
    public void setModificationSite(String pModificationSite) {
        this.modificationSite = pModificationSite;
    }

    /**
     *
     * @return optIn
     */
    public String getOptIn() {
        return this.optIn;
    }

    /**
     *
     * @param pOptIn optIn value
     */
    public void setOptIn(String pOptIn) {
        this.optIn = pOptIn;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_abil8JiEEeKTJYyx5c6zlQ) ENABLED START*/
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
            .append("marketLanguageId", getMarketLanguageId())
            .append("comPrefId", getComPrefId())
            .append("market", getMarket())
            .append("language", getLanguage())
            .append("optIn", getOptIn())
            .append("dateOfConsent", getDateOfConsent())
            .append("communicationMedia1", getCommunicationMedia1())
            .append("communicationMedia2", getCommunicationMedia2())
            .append("communicationMedia3", getCommunicationMedia3())
            .append("communicationMedia4", getCommunicationMedia4())
            .append("communicationMedia5", getCommunicationMedia5())
            .append("creationDate", getCreationDate())
            .append("creationSignature", getCreationSignature())
            .append("creationSite", getCreationSite())
            .append("modificationDate", getModificationDate())
            .append("modificationSignature", getModificationSignature())
            .append("modificationSite", getModificationSite())
            .toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _abil8JiEEeKTJYyx5c6zlQ) ENABLED START*/

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
        final MarketLanguage other = (MarketLanguage) obj;

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

    /*PROTECTED REGION ID(_abil8JiEEeKTJYyx5c6zlQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
