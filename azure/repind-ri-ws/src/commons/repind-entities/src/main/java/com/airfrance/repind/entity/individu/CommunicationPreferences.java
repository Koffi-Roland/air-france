package com.airfrance.repind.entity.individu;

/*PROTECTED REGION ID(_p-QMYUDfEeCSW-5bkMJFig i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/*PROTECTED REGION END*/


/**
 * <p>Title : CommunicationPreferences.java</p>
 * BO CommunicationPreferences
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="COMMUNICATION_PREFERENCES")
public class CommunicationPreferences implements Serializable {

/*PROTECTED REGION ID(serialUID _p-QMYUDfEeCSW-5bkMJFig) ENABLED START*/
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
     * comPrefId
     */
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_COMM_PREF")
    @SequenceGenerator(name="ISEQ_COMM_PREF", sequenceName = "ISEQ_COMM_PREF",
			allocationSize = 1)
    @Column(name="COM_PREF_ID", length=12)
    private Integer comPrefId;
        
            
    /**
     * accountIdentifier
     */
    @Column(name="ACCOUNT_IDENTIFIER", length=8, updatable=false)
    private String accountIdentifier;
        
            
    /**
     * gin
     */
    @Column(name="SGIN", length=12, nullable=false, updatable=false)
    private String gin;
        
            
    /**
     * domain
     */
    @Column(name="DOMAIN")
    private String domain;
        
            
    /**
     * comGroupType
     */
    @Column(name="COM_GROUP_TYPE", length=3)
    private String comGroupType;
        
            
    /**
     * comType
     */
    @Column(name="COM_TYPE", length=3)
    private String comType;
        
            
    /**
     * media1
     */
    @Column(name="MEDIA1", length=1)
    private String media1;
        
            
    /**
     * media2
     */
    @Column(name="MEDIA2", length=1)
    private String media2;
        
            
    /**
     * media3
     */
    @Column(name="MEDIA3", length=1)
    private String media3;
        
            
    /**
     * media4
     */
    @Column(name="MEDIA4", length=1)
    private String media4;
        
            
    /**
     * media5
     */
    @Column(name="MEDIA5", length=1)
    private String media5;
        
            
    /**
     * subscribe
     */
    @Column(name="SUBSCRIBE", length=2)
    private String subscribe;
        
            
    /**
     * creationDate
     */
    @Column(name="CREATION_DATE")
    private java.util.Date creationDate;
        
            
    /**
     * dateOptin
     */
    @Column(name="DATE_OPTIN")
    private java.util.Date dateOptin;
        
            
    /**
     * dateOptinPartners
     */
    @Column(name="DATE_OPTIN_PARTNERS")
    private java.util.Date dateOptinPartners;
        
            
    /**
     * dateOfEntry
     */
    @Column(name="DATE_OF_ENTRY")
    private java.util.Date dateOfEntry;
        
            
    /**
     * modificationDate
     */
    @Column(name="MODIFICATION_DATE")
    private java.util.Date modificationDate;
        
            
    /**
     * modificationSignature
     */
    @Column(name="MODIFICATION_SIGNATURE")
    private String modificationSignature;
        
            
    /**
     * modificationSite
     */
    @Column(name="MODIFICATION_SITE")
    private String modificationSite;
        
            
    /**
     * optinPartners
     */
    @Column(name="OPTIN_PARTNERS")
    private String optinPartners;
        
            
    /**
     * creationSignature
     */
    @Column(name="CREATION_SIGNATURE")
    private String creationSignature;
        
            
    /**
     * creationSite
     */
    @Column(name="CREATION_SITE")
    private String creationSite;
        
            
    /**
     * channel
     */
    @Column(name="CHANNEL")
    private String channel;
        
            
    /**
     * marketLanguage
     */
    // 1 -> *
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="COM_PREF_ID", nullable=true)
    @ForeignKey(name = "FK_COM_PREF_ML_ID")
    private Set<MarketLanguage> marketLanguage;
        
    /*PROTECTED REGION ID(_p-QMYUDfEeCSW-5bkMJFig u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    
    /** 
     * default constructor 
     */
    public CommunicationPreferences() {
    }
    
        
    /** 
     * full constructor
     * @param pComPrefId comPrefId
     * @param pAccountIdentifier accountIdentifier
     * @param pGin gin
     * @param pDomain domain
     * @param pComGroupType comGroupType
     * @param pComType comType
     * @param pMedia1 media1
     * @param pMedia2 media2
     * @param pMedia3 media3
     * @param pMedia4 media4
     * @param pMedia5 media5
     * @param pSubscribe subscribe
     * @param pCreationDate creationDate
     * @param pDateOptin dateOptin
     * @param pDateOptinPartners dateOptinPartners
     * @param pDateOfEntry dateOfEntry
     * @param pModificationDate modificationDate
     * @param pModificationSignature modificationSignature
     * @param pModificationSite modificationSite
     * @param pOptinPartners optinPartners
     * @param pCreationSignature creationSignature
     * @param pCreationSite creationSite
     * @param pChannel channel
     */
    public CommunicationPreferences(Integer pComPrefId, String pAccountIdentifier, String pGin, String pDomain, String pComGroupType, String pComType, String pMedia1, String pMedia2, String pMedia3, String pMedia4, String pMedia5, String pSubscribe, java.util.Date pCreationDate, java.util.Date pDateOptin, java.util.Date pDateOptinPartners, java.util.Date pDateOfEntry, java.util.Date pModificationDate, String pModificationSignature, String pModificationSite, String pOptinPartners, String pCreationSignature, String pCreationSite, String pChannel) {
        this.comPrefId = pComPrefId;
        this.accountIdentifier = pAccountIdentifier;
        this.gin = pGin;
        this.domain = pDomain;
        this.comGroupType = pComGroupType;
        this.comType = pComType;
        this.media1 = pMedia1;
        this.media2 = pMedia2;
        this.media3 = pMedia3;
        this.media4 = pMedia4;
        this.media5 = pMedia5;
        this.subscribe = pSubscribe;
        this.creationDate = pCreationDate;
        this.dateOptin = pDateOptin;
        this.dateOptinPartners = pDateOptinPartners;
        this.dateOfEntry = pDateOfEntry;
        this.modificationDate = pModificationDate;
        this.modificationSignature = pModificationSignature;
        this.modificationSite = pModificationSite;
        this.optinPartners = pOptinPartners;
        this.creationSignature = pCreationSignature;
        this.creationSite = pCreationSite;
        this.channel = pChannel;
    }

    /**
     *
     * @return accountIdentifier
     */
    public String getAccountIdentifier() {
        return this.accountIdentifier;
    }

    /**
     *
     * @param pAccountIdentifier accountIdentifier value
     */
    public void setAccountIdentifier(String pAccountIdentifier) {
        this.accountIdentifier = pAccountIdentifier;
    }

    /**
     *
     * @return channel
     */
    public String getChannel() {
        return this.channel;
    }

    /**
     *
     * @param pChannel channel value
     */
    public void setChannel(String pChannel) {
        this.channel = pChannel;
    }

    /**
     *
     * @return comGroupType
     */
    public String getComGroupType() {
        return this.comGroupType;
    }

    /**
     *
     * @param pComGroupType comGroupType value
     */
    public void setComGroupType(String pComGroupType) {
        this.comGroupType = pComGroupType;
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
     * @return comType
     */
    public String getComType() {
        return this.comType;
    }

    /**
     *
     * @param pComType comType value
     */
    public void setComType(String pComType) {
        this.comType = pComType;
    }

    /**
     *
     * @return creationDate
     */
    public java.util.Date getCreationDate() {
        return this.creationDate;
    }

    /**
     *
     * @param pCreationDate creationDate value
     */
    public void setCreationDate(java.util.Date pCreationDate) {
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
     * @return dateOfEntry
     */
    public java.util.Date getDateOfEntry() {
        return this.dateOfEntry;
    }

    /**
     *
     * @param pDateOfEntry dateOfEntry value
     */
    public void setDateOfEntry(java.util.Date pDateOfEntry) {
        this.dateOfEntry = pDateOfEntry;
    }

    /**
     *
     * @return dateOptin
     */
    public java.util.Date getDateOptin() {
        return this.dateOptin;
    }

    /**
     *
     * @param pDateOptin dateOptin value
     */
    public void setDateOptin(java.util.Date pDateOptin) {
        this.dateOptin = pDateOptin;
    }

    /**
     *
     * @return dateOptinPartners
     */
    public java.util.Date getDateOptinPartners() {
        return this.dateOptinPartners;
    }

    /**
     *
     * @param pDateOptinPartners dateOptinPartners value
     */
    public void setDateOptinPartners(java.util.Date pDateOptinPartners) {
        this.dateOptinPartners = pDateOptinPartners;
    }

    /**
     *
     * @return domain
     */
    public String getDomain() {
        return this.domain;
    }

    /**
     *
     * @param pDomain domain value
     */
    public void setDomain(String pDomain) {
        this.domain = pDomain;
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
     * @return marketLanguage
     */
    public Set<MarketLanguage> getMarketLanguage() {
        return this.marketLanguage;
    }

    /**
     *
     * @param pMarketLanguage marketLanguage value
     */
    public void setMarketLanguage(Set<MarketLanguage> pMarketLanguage) {
        this.marketLanguage = pMarketLanguage;
    }

    /**
     *
     * @return media1
     */
    public String getMedia1() {
        return this.media1;
    }

    /**
     *
     * @param pMedia1 media1 value
     */
    public void setMedia1(String pMedia1) {
        this.media1 = pMedia1;
    }

    /**
     *
     * @return media2
     */
    public String getMedia2() {
        return this.media2;
    }

    /**
     *
     * @param pMedia2 media2 value
     */
    public void setMedia2(String pMedia2) {
        this.media2 = pMedia2;
    }

    /**
     *
     * @return media3
     */
    public String getMedia3() {
        return this.media3;
    }

    /**
     *
     * @param pMedia3 media3 value
     */
    public void setMedia3(String pMedia3) {
        this.media3 = pMedia3;
    }

    /**
     *
     * @return media4
     */
    public String getMedia4() {
        return this.media4;
    }

    /**
     *
     * @param pMedia4 media4 value
     */
    public void setMedia4(String pMedia4) {
        this.media4 = pMedia4;
    }

    /**
     *
     * @return media5
     */
    public String getMedia5() {
        return this.media5;
    }

    /**
     *
     * @param pMedia5 media5 value
     */
    public void setMedia5(String pMedia5) {
        this.media5 = pMedia5;
    }

    /**
     *
     * @return modificationDate
     */
    public java.util.Date getModificationDate() {
        return this.modificationDate;
    }

    /**
     *
     * @param pModificationDate modificationDate value
     */
    public void setModificationDate(java.util.Date pModificationDate) {
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
     * @return optinPartners
     */
    public String getOptinPartners() {
        return this.optinPartners;
    }

    /**
     *
     * @param pOptinPartners optinPartners value
     */
    public void setOptinPartners(String pOptinPartners) {
        this.optinPartners = pOptinPartners;
    }

    /**
     *
     * @return subscribe
     */
    public String getSubscribe() {
        return this.subscribe;
    }

    /**
     *
     * @param pSubscribe subscribe value
     */
    public void setSubscribe(String pSubscribe) {
        this.subscribe = pSubscribe;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_p-QMYUDfEeCSW-5bkMJFig) ENABLED START*/
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
            .append("comPrefId", getComPrefId())
            .append("accountIdentifier", getAccountIdentifier())
            .append("gin", getGin())
            .append("domain", getDomain())
            .append("comGroupType", getComGroupType())
            .append("comType", getComType())
            .append("media1", getMedia1())
            .append("media2", getMedia2())
            .append("media3", getMedia3())
            .append("media4", getMedia4())
            .append("media5", getMedia5())
            .append("subscribe", getSubscribe())
            .append("creationDate", getCreationDate())
            .append("dateOptin", getDateOptin())
            .append("dateOptinPartners", getDateOptinPartners())
            .append("dateOfEntry", getDateOfEntry())
            .append("modificationDate", getModificationDate())
            .append("modificationSignature", getModificationSignature())
            .append("modificationSite", getModificationSite())
            .append("optinPartners", getOptinPartners())
            .append("creationSignature", getCreationSignature())
            .append("creationSite", getCreationSite())
            .append("channel", getChannel())
            .toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _p-QMYUDfEeCSW-5bkMJFig) ENABLED START*/

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
        final CommunicationPreferences other = (CommunicationPreferences) obj;

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

    /*PROTECTED REGION ID(_p-QMYUDfEeCSW-5bkMJFig u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
