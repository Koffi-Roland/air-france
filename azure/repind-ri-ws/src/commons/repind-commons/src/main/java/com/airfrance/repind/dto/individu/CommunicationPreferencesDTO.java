package com.airfrance.repind.dto.individu;

/*PROTECTED REGION ID(_rVOfAEDiEeCSW-5bkMJFig i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : CommunicationPreferencesDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class CommunicationPreferencesDTO  implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 5946012258359115616L;

    /**
     * comPrefId
     */
    private Integer comPrefId;
        
        
    /**
     * accountIdentifier
     */
    private String accountIdentifier;
        
        
    /**
     * domain
     */
    private String domain;
        
        
    /**
     * gin
     */
    private String gin;
        
        
    /**
     * dateOptin
     */
    private Date dateOptin;
        
        
    /**
     * dateOptinPartners
     */
    private Date dateOptinPartners;
        
        
    /**
     * dateOfEntry
     */
    private Date dateOfEntry;
        
        
    /**
     * creationDate
     */
    private Date creationDate;
        
        
    /**
     * modificationDate
     */
    private Date modificationDate;
        
        
    /**
     * modificationSignature
     */
    private String modificationSignature;
        
        
    /**
     * modificationSite
     */
    private String modificationSite;
        
        
    /**
     * optinPartners
     */
    private String optinPartners;
        
        
    /**
     * creationSignature
     */
    private String creationSignature;
        
        
    /**
     * creationSite
     */
    private String creationSite;
        
        
    /**
     * channel
     */
    private String channel;
        
        
    /**
     * comGroupType
     */
    private String comGroupType;
        
        
    /**
     * comType
     */
    private String comType;
        
        
    /**
     * subscribe
     */
    private String subscribe;
        
        
    /**
     * media1
     */
    private String media1;
        
        
    /**
     * media2
     */
    private String media2;
        
        
    /**
     * media3
     */
    private String media3;
        
        
    /**
     * media4
     */
    private String media4;
        
        
    /**
     * media5
     */
    private String media5;
        
        
    /**
     * marketLanguageDTO
     */
    private Set<MarketLanguageDTO> marketLanguageDTO;
        

    /*PROTECTED REGION ID(_rVOfAEDiEeCSW-5bkMJFig u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public CommunicationPreferencesDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pAccountIdentifier accountIdentifier
     * @param pComPrefId comPrefId
     * @param pDomain domain
     * @param pGin gin
     * @param pDateOptin dateOptin
     * @param pDateOptinPartners dateOptinPartners
     * @param pDateOfEntry dateOfEntry
     * @param pCreationDate creationDate
     * @param pModificationDate modificationDate
     * @param pModificationSignature modificationSignature
     * @param pModificationSite modificationSite
     * @param pOptinPartners optinPartners
     * @param pCreationSignature creationSignature
     * @param pCreationSite creationSite
     * @param pChannel channel
     * @param pComGroupType comGroupType
     * @param pComType comType
     * @param pSubscribe subscribe
     * @param pMedia1 media1
     * @param pMedia2 media2
     * @param pMedia3 media3
     * @param pMedia4 media4
     * @param pMedia5 media5
     */
    public CommunicationPreferencesDTO(String pAccountIdentifier, Integer pComPrefId, String pDomain, String pGin, Date pDateOptin, Date pDateOptinPartners, Date pDateOfEntry, Date pCreationDate, Date pModificationDate, String pModificationSignature, String pModificationSite, String pOptinPartners, String pCreationSignature, String pCreationSite, String pChannel, String pComGroupType, String pComType, String pSubscribe, String pMedia1, String pMedia2, String pMedia3, String pMedia4, String pMedia5) {
        this.accountIdentifier = pAccountIdentifier;
        this.comPrefId = pComPrefId;
        this.domain = pDomain;
        this.gin = pGin;
        this.dateOptin = pDateOptin;
        this.dateOptinPartners = pDateOptinPartners;
        this.dateOfEntry = pDateOfEntry;
        this.creationDate = pCreationDate;
        this.modificationDate = pModificationDate;
        this.modificationSignature = pModificationSignature;
        this.modificationSite = pModificationSite;
        this.optinPartners = pOptinPartners;
        this.creationSignature = pCreationSignature;
        this.creationSite = pCreationSite;
        this.channel = pChannel;
        this.comGroupType = pComGroupType;
        this.comType = pComType;
        this.subscribe = pSubscribe;
        this.media1 = pMedia1;
        this.media2 = pMedia2;
        this.media3 = pMedia3;
        this.media4 = pMedia4;
        this.media5 = pMedia5;
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
     * @return dateOfEntry
     */
    public Date getDateOfEntry() {
        return this.dateOfEntry;
    }

    /**
     *
     * @param pDateOfEntry dateOfEntry value
     */
    public void setDateOfEntry(Date pDateOfEntry) {
        this.dateOfEntry = pDateOfEntry;
    }

    /**
     *
     * @return dateOptin
     */
    public Date getDateOptin() {
        return this.dateOptin;
    }

    /**
     *
     * @param pDateOptin dateOptin value
     */
    public void setDateOptin(Date pDateOptin) {
        this.dateOptin = pDateOptin;
    }

    /**
     *
     * @return dateOptinPartners
     */
    public Date getDateOptinPartners() {
        return this.dateOptinPartners;
    }

    /**
     *
     * @param pDateOptinPartners dateOptinPartners value
     */
    public void setDateOptinPartners(Date pDateOptinPartners) {
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
     * @return marketLanguageDTO
     */
    public Set<MarketLanguageDTO> getMarketLanguageDTO() {
        if(this.marketLanguageDTO == null){
            this.marketLanguageDTO = new HashSet<>();
        }
        return this.marketLanguageDTO;
    }

    /**
     *
     * @param pMarketLanguageDTO marketLanguageDTO value
     */
    public void setMarketLanguageDTO(Set<MarketLanguageDTO> pMarketLanguageDTO) {
        this.marketLanguageDTO = pMarketLanguageDTO;
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
        /*PROTECTED REGION ID(toString_rVOfAEDiEeCSW-5bkMJFig) ENABLED START*/
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
            .append("accountIdentifier", getAccountIdentifier())
            .append("comPrefId", getComPrefId())
            .append("domain", getDomain())
            .append("gin", getGin())
            .append("dateOptin", getDateOptin())
            .append("dateOptinPartners", getDateOptinPartners())
            .append("dateOfEntry", getDateOfEntry())
            .append("creationDate", getCreationDate())
            .append("modificationDate", getModificationDate())
            .append("modificationSignature", getModificationSignature())
            .append("modificationSite", getModificationSite())
            .append("optinPartners", getOptinPartners())
            .append("creationSignature", getCreationSignature())
            .append("creationSite", getCreationSite())
            .append("channel", getChannel())
            .append("comGroupType", getComGroupType())
            .append("comType", getComType())
            .append("subscribe", getSubscribe())
            .append("media1", getMedia1())
            .append("media2", getMedia2())
            .append("media3", getMedia3())
            .append("media4", getMedia4())
            .append("media5", getMedia5())
            .toString();
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comGroupType == null) ? 0 : comGroupType.hashCode());
		result = prime * result + ((comType == null) ? 0 : comType.hashCode());
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
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
		CommunicationPreferencesDTO other = (CommunicationPreferencesDTO) obj;
		if (comGroupType == null) {
			if (other.comGroupType != null)
				return false;
		} else if (!comGroupType.equals(other.comGroupType))
			return false;
		if (comType == null) {
			if (other.comType != null)
				return false;
		} else if (!comType.equals(other.comType))
			return false;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		return true;
	}





	
    
    

    /*PROTECTED REGION ID(_rVOfAEDiEeCSW-5bkMJFig u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
