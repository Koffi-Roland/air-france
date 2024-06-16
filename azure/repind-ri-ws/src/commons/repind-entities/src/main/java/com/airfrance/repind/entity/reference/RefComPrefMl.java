package com.airfrance.repind.entity.reference;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name="REF_COMPREF_ML")
public class RefComPrefMl implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
       

	/**
     * refComPrefMlId
     */
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REF_COMPREF_ML")
    @SequenceGenerator(name="SEQ_REF_COMPREF_ML", sequenceName = "SEQ_REF_COMPREF_ML",
			allocationSize = 1)
    @Column(name="REF_MARKET_LANGUAGE_ID", length=12, nullable=false)
    private Integer refComPrefMlId;
	
    
    /**
     * refComPrefDgt
     */
    @ManyToOne
    @JoinColumn(name = "REF_COMPREF_DGT_ID", nullable=false)
    @ForeignKey(name = "FK_REF_COMPREF_DGT")
    private RefComPrefDgt refComPrefDgt;
        
    
    /**
     * mandatoryOption
     */
    @Column(name="MANDATORY_OPTIN", length=30, nullable=false)
    private String mandatoryOption;
    
    
    /**
     * market
     */
    @Column(name="MARKET", length=3, nullable=false)
    private String market;
    
    
    /**
     * defaultLanguage1
     */
    @Column(name="DEFAULT_LANGUAGE_1", length=2, nullable=false)
    private String defaultLanguage1;
    
    
    /**
     * defaultLanguage2
     */
    @Column(name="DEFAULT_LANGUAGE_2", length=2)
    private String defaultLanguage2;
    
    
    /**
     * defaultLanguage3
     */
    @Column(name="DEFAULT_LANGUAGE_3", length=2)
    private String defaultLanguage3;
    
    
    /**
     * defaultLanguage4
     */
    @Column(name="DEFAULT_LANGUAGE_4", length=2)
    private String defaultLanguage4;
    
    
    /**
     * defaultLanguage5
     */
    @Column(name="DEFAULT_LANGUAGE_5", length=2)
    private String defaultLanguage5;
    
    
    /**
     * defaultLanguage6
     */
    @Column(name="DEFAULT_LANGUAGE_6", length=2)
    private String defaultLanguage6;
    
    
    /**
     * defaultLanguage7
     */
    @Column(name="DEFAULT_LANGUAGE_7", length=2)
    private String defaultLanguage7;
    
    
    /**
     * defaultLanguage8
     */
    @Column(name="DEFAULT_LANGUAGE_8", length=2)
    private String defaultLanguage8;
    
    
    /**
     * defaultLanguage9
     */
    @Column(name="DEFAULT_LANGUAGE_9", length=2)
    private String defaultLanguage9;
    
    
    /**
     * defaultLanguage10
     */
    @Column(name="DEFAULT_LANGUAGE_10", length=2)
    private String defaultLanguage10;
    
    
    /**
     * fieldA
     */
    @Column(name="A", length=1)
    private String fieldA;
    
    
    /**
     * fieldN
     */
    @Column(name="N", length=1)
    private String fieldN;
    
    
    /**
     * fieldT
     */
    @Column(name="T", length=1)
    private String fieldT;
    
    
    /**
     * media
     */
    @Column(name="MEDIA", length=2)
    private String media;
    
    
    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION", nullable=false)
    private Date dateCreation;
    
    
    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION", nullable=false)
    private String siteCreation;
    
    
    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", nullable=false)
    private String signatureCreation;
    
    
    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION", nullable=false)
    private Date dateModification;
    
    
    /**
     * siteModification
     */
    @Column(name="SSITE_MODIFICATION", nullable=false)
    private String siteModification;
    
    
    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION", nullable=false)
    private String signatureModification;


	public RefComPrefMl() {
		super();
	}


	public RefComPrefMl(Integer refComPrefMlId, RefComPrefDgt refComPrefDgt, String mandatoryOption, String market,
			String defaultLanguage1, String defaultLanguage2, String defaultLanguage3, String defaultLanguage4,
			String defaultLanguage5, String defaultLanguage6, String defaultLanguage7, String defaultLanguage8,
			String defaultLanguage9, String defaultLanguage10, String fieldA, String fieldN, String fieldT,
			String media, Date dateCreation, String siteCreation, String signatureCreation, Date dateModification,
			String siteModification, String signatureModification) {
		super();
		this.refComPrefMlId = refComPrefMlId;
		this.refComPrefDgt = refComPrefDgt;
		this.mandatoryOption = mandatoryOption;
		this.market = market;
		this.defaultLanguage1 = defaultLanguage1;
		this.defaultLanguage2 = defaultLanguage2;
		this.defaultLanguage3 = defaultLanguage3;
		this.defaultLanguage4 = defaultLanguage4;
		this.defaultLanguage5 = defaultLanguage5;
		this.defaultLanguage6 = defaultLanguage6;
		this.defaultLanguage7 = defaultLanguage7;
		this.defaultLanguage8 = defaultLanguage8;
		this.defaultLanguage9 = defaultLanguage9;
		this.defaultLanguage10 = defaultLanguage10;
		this.fieldA = fieldA;
		this.fieldN = fieldN;
		this.fieldT = fieldT;
		this.media = media;
		this.dateCreation = dateCreation;
		this.siteCreation = siteCreation;
		this.signatureCreation = signatureCreation;
		this.dateModification = dateModification;
		this.siteModification = siteModification;
		this.signatureModification = signatureModification;
	}


	public Integer getRefComPrefMlId() {
		return refComPrefMlId;
	}


	public void setRefComPrefMlId(Integer refComPrefMlId) {
		this.refComPrefMlId = refComPrefMlId;
	}


	public RefComPrefDgt getRefComPrefDgt() {
		return refComPrefDgt;
	}


	public void setRefComPrefDgt(RefComPrefDgt refComPrefDgt) {
		this.refComPrefDgt = refComPrefDgt;
	}


	public String getMandatoryOption() {
		return mandatoryOption;
	}


	public void setMandatoryOption(String mandatoryOption) {
		this.mandatoryOption = mandatoryOption;
	}


	public String getMarket() {
		return market;
	}


	public void setMarket(String market) {
		this.market = market;
	}


	public String getDefaultLanguage1() {
		return defaultLanguage1;
	}


	public void setDefaultLanguage1(String defaultLanguage1) {
		this.defaultLanguage1 = defaultLanguage1;
	}


	public String getDefaultLanguage2() {
		return defaultLanguage2;
	}


	public void setDefaultLanguage2(String defaultLanguage2) {
		this.defaultLanguage2 = defaultLanguage2;
	}


	public String getDefaultLanguage3() {
		return defaultLanguage3;
	}


	public void setDefaultLanguage3(String defaultLanguage3) {
		this.defaultLanguage3 = defaultLanguage3;
	}


	public String getDefaultLanguage4() {
		return defaultLanguage4;
	}


	public void setDefaultLanguage4(String defaultLanguage4) {
		this.defaultLanguage4 = defaultLanguage4;
	}


	public String getDefaultLanguage5() {
		return defaultLanguage5;
	}


	public void setDefaultLanguage5(String defaultLanguage5) {
		this.defaultLanguage5 = defaultLanguage5;
	}


	public String getDefaultLanguage6() {
		return defaultLanguage6;
	}


	public void setDefaultLanguage6(String defaultLanguage6) {
		this.defaultLanguage6 = defaultLanguage6;
	}


	public String getDefaultLanguage7() {
		return defaultLanguage7;
	}


	public void setDefaultLanguage7(String defaultLanguage7) {
		this.defaultLanguage7 = defaultLanguage7;
	}


	public String getDefaultLanguage8() {
		return defaultLanguage8;
	}


	public void setDefaultLanguage8(String defaultLanguage8) {
		this.defaultLanguage8 = defaultLanguage8;
	}


	public String getDefaultLanguage9() {
		return defaultLanguage9;
	}


	public void setDefaultLanguage9(String defaultLanguage9) {
		this.defaultLanguage9 = defaultLanguage9;
	}


	public String getDefaultLanguage10() {
		return defaultLanguage10;
	}


	public void setDefaultLanguage10(String defaultLanguage10) {
		this.defaultLanguage10 = defaultLanguage10;
	}


	public String getFieldA() {
		return fieldA;
	}


	public void setFieldA(String fieldA) {
		this.fieldA = fieldA;
	}


	public String getFieldN() {
		return fieldN;
	}


	public void setFieldN(String fieldN) {
		this.fieldN = fieldN;
	}


	public String getFieldT() {
		return fieldT;
	}


	public void setFieldT(String fieldT) {
		this.fieldT = fieldT;
	}


	public String getMedia() {
		return media;
	}


	public void setMedia(String media) {
		this.media = media;
	}


	public Date getDateCreation() {
		return dateCreation;
	}


	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	public String getSiteCreation() {
		return siteCreation;
	}


	public void setSiteCreation(String siteCreation) {
		this.siteCreation = siteCreation;
	}


	public String getSignatureCreation() {
		return signatureCreation;
	}


	public void setSignatureCreation(String signatureCreation) {
		this.signatureCreation = signatureCreation;
	}


	public Date getDateModification() {
		return dateModification;
	}


	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}


	public String getSiteModification() {
		return siteModification;
	}


	public void setSiteModification(String siteModification) {
		this.siteModification = siteModification;
	}


	public String getSignatureModification() {
		return signatureModification;
	}


	public void setSignatureModification(String signatureModification) {
		this.signatureModification = signatureModification;
	}


	@Override
	public String toString() {
		return "RefComPrefMl [refComPrefMlId=" + refComPrefMlId + ", refComPrefDgt=" + refComPrefDgt
				+ ", mandatoryOption=" + mandatoryOption + ", market=" + market + ", defaultLanguage1="
				+ defaultLanguage1 + ", defaultLanguage2=" + defaultLanguage2 + ", defaultLanguage3=" + defaultLanguage3
				+ ", defaultLanguage4=" + defaultLanguage4 + ", defaultLanguage5=" + defaultLanguage5
				+ ", defaultLanguage6=" + defaultLanguage6 + ", defaultLanguage7=" + defaultLanguage7
				+ ", defaultLanguage8=" + defaultLanguage8 + ", defaultLanguage9=" + defaultLanguage9
				+ ", defaultLanguage10=" + defaultLanguage10 + ", fieldA=" + fieldA + ", fieldN=" + fieldN + ", fieldT="
				+ fieldT + ", media=" + media + ", dateCreation=" + dateCreation + ", siteCreation=" + siteCreation
				+ ", signatureCreation=" + signatureCreation + ", dateModification=" + dateModification
				+ ", siteModification=" + siteModification + ", signatureModification=" + signatureModification + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreation == null) ? 0 : dateCreation.hashCode());
		result = prime * result + ((dateModification == null) ? 0 : dateModification.hashCode());
		result = prime * result + ((defaultLanguage1 == null) ? 0 : defaultLanguage1.hashCode());
		result = prime * result + ((defaultLanguage10 == null) ? 0 : defaultLanguage10.hashCode());
		result = prime * result + ((defaultLanguage2 == null) ? 0 : defaultLanguage2.hashCode());
		result = prime * result + ((defaultLanguage3 == null) ? 0 : defaultLanguage3.hashCode());
		result = prime * result + ((defaultLanguage4 == null) ? 0 : defaultLanguage4.hashCode());
		result = prime * result + ((defaultLanguage5 == null) ? 0 : defaultLanguage5.hashCode());
		result = prime * result + ((defaultLanguage6 == null) ? 0 : defaultLanguage6.hashCode());
		result = prime * result + ((defaultLanguage7 == null) ? 0 : defaultLanguage7.hashCode());
		result = prime * result + ((defaultLanguage8 == null) ? 0 : defaultLanguage8.hashCode());
		result = prime * result + ((defaultLanguage9 == null) ? 0 : defaultLanguage9.hashCode());
		result = prime * result + ((fieldA == null) ? 0 : fieldA.hashCode());
		result = prime * result + ((fieldN == null) ? 0 : fieldN.hashCode());
		result = prime * result + ((fieldT == null) ? 0 : fieldT.hashCode());
		result = prime * result + ((mandatoryOption == null) ? 0 : mandatoryOption.hashCode());
		result = prime * result + ((market == null) ? 0 : market.hashCode());
		result = prime * result + ((media == null) ? 0 : media.hashCode());
		result = prime * result + ((refComPrefDgt == null) ? 0 : refComPrefDgt.hashCode());
		result = prime * result + ((refComPrefMlId == null) ? 0 : refComPrefMlId.hashCode());
		result = prime * result + ((signatureCreation == null) ? 0 : signatureCreation.hashCode());
		result = prime * result + ((signatureModification == null) ? 0 : signatureModification.hashCode());
		result = prime * result + ((siteCreation == null) ? 0 : siteCreation.hashCode());
		result = prime * result + ((siteModification == null) ? 0 : siteModification.hashCode());
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
		RefComPrefMl other = (RefComPrefMl) obj;
		if (dateCreation == null) {
			if (other.dateCreation != null)
				return false;
		} else if (!dateCreation.equals(other.dateCreation))
			return false;
		if (dateModification == null) {
			if (other.dateModification != null)
				return false;
		} else if (!dateModification.equals(other.dateModification))
			return false;
		if (defaultLanguage1 == null) {
			if (other.defaultLanguage1 != null)
				return false;
		} else if (!defaultLanguage1.equals(other.defaultLanguage1))
			return false;
		if (defaultLanguage10 == null) {
			if (other.defaultLanguage10 != null)
				return false;
		} else if (!defaultLanguage10.equals(other.defaultLanguage10))
			return false;
		if (defaultLanguage2 == null) {
			if (other.defaultLanguage2 != null)
				return false;
		} else if (!defaultLanguage2.equals(other.defaultLanguage2))
			return false;
		if (defaultLanguage3 == null) {
			if (other.defaultLanguage3 != null)
				return false;
		} else if (!defaultLanguage3.equals(other.defaultLanguage3))
			return false;
		if (defaultLanguage4 == null) {
			if (other.defaultLanguage4 != null)
				return false;
		} else if (!defaultLanguage4.equals(other.defaultLanguage4))
			return false;
		if (defaultLanguage5 == null) {
			if (other.defaultLanguage5 != null)
				return false;
		} else if (!defaultLanguage5.equals(other.defaultLanguage5))
			return false;
		if (defaultLanguage6 == null) {
			if (other.defaultLanguage6 != null)
				return false;
		} else if (!defaultLanguage6.equals(other.defaultLanguage6))
			return false;
		if (defaultLanguage7 == null) {
			if (other.defaultLanguage7 != null)
				return false;
		} else if (!defaultLanguage7.equals(other.defaultLanguage7))
			return false;
		if (defaultLanguage8 == null) {
			if (other.defaultLanguage8 != null)
				return false;
		} else if (!defaultLanguage8.equals(other.defaultLanguage8))
			return false;
		if (defaultLanguage9 == null) {
			if (other.defaultLanguage9 != null)
				return false;
		} else if (!defaultLanguage9.equals(other.defaultLanguage9))
			return false;
		if (fieldA == null) {
			if (other.fieldA != null)
				return false;
		} else if (!fieldA.equals(other.fieldA))
			return false;
		if (fieldN == null) {
			if (other.fieldN != null)
				return false;
		} else if (!fieldN.equals(other.fieldN))
			return false;
		if (fieldT == null) {
			if (other.fieldT != null)
				return false;
		} else if (!fieldT.equals(other.fieldT))
			return false;
		if (mandatoryOption == null) {
			if (other.mandatoryOption != null)
				return false;
		} else if (!mandatoryOption.equals(other.mandatoryOption))
			return false;
		if (market == null) {
			if (other.market != null)
				return false;
		} else if (!market.equals(other.market))
			return false;
		if (media == null) {
			if (other.media != null)
				return false;
		} else if (!media.equals(other.media))
			return false;
		if (refComPrefDgt == null) {
			if (other.refComPrefDgt != null)
				return false;
		} else if (!refComPrefDgt.equals(other.refComPrefDgt))
			return false;
		if (refComPrefMlId == null) {
			if (other.refComPrefMlId != null)
				return false;
		} else if (!refComPrefMlId.equals(other.refComPrefMlId))
			return false;
		if (signatureCreation == null) {
			if (other.signatureCreation != null)
				return false;
		} else if (!signatureCreation.equals(other.signatureCreation))
			return false;
		if (signatureModification == null) {
			if (other.signatureModification != null)
				return false;
		} else if (!signatureModification.equals(other.signatureModification))
			return false;
		if (siteCreation == null) {
			if (other.siteCreation != null)
				return false;
		} else if (!siteCreation.equals(other.siteCreation))
			return false;
		if (siteModification == null) {
			if (other.siteModification != null)
				return false;
		} else if (!siteModification.equals(other.siteModification))
			return false;
		return true;
	}
}


