package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_FFONoKU0EeSXNpATSKyi0Q i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import com.airfrance.ref.exception.SicDomainException;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : FonctionDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class FonctionDTO implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -5574472758219269222L;


	/**
     * cle
     */
    private Integer cle;
        
        
    /**
     * fonction
     */
    private String fonction;
        
        
    /**
     * dateDebutValidite
     */
    private Date dateDebutValidite;
        
        
    /**
     * dateFinValidite
     */
    private Date dateFinValidite;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * signatureCreation
     */
    private String signatureCreation;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        
        
    /**
     * signatureModification
     */
    private String signatureModification;
        
        
    /**
     * version
     */
    private Integer version;
        
        
    /**
     * emails
     */
    private Set<EmailDTO> emails;
        
        
    /**
     * membre
     */
    private MembreDTO membre;
        
        
    /**
     * postalAddresses
     */
    private Set<PostalAddressDTO> postalAddresses;
        
        
    /**
     * telecoms
     */
    private Set<TelecomsDTO> telecoms;
        

    /*PROTECTED REGION ID(_FFONoKU0EeSXNpATSKyi0Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public FonctionDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCle cle
     * @param pFonction fonction
     * @param pDateDebutValidite dateDebutValidite
     * @param pDateFinValidite dateFinValidite
     * @param pDateCreation dateCreation
     * @param pSignatureCreation signatureCreation
     * @param pDateModification dateModification
     * @param pSignatureModification signatureModification
     * @param pVersion version
     */
    public FonctionDTO(Integer pCle, String pFonction, Date pDateDebutValidite, Date pDateFinValidite, Date pDateCreation, String pSignatureCreation, Date pDateModification, String pSignatureModification, Integer pVersion) {
        this.cle = pCle;
        this.fonction = pFonction;
        this.dateDebutValidite = pDateDebutValidite;
        this.dateFinValidite = pDateFinValidite;
        this.dateCreation = pDateCreation;
        this.signatureCreation = pSignatureCreation;
        this.dateModification = pDateModification;
        this.signatureModification = pSignatureModification;
        this.version = pVersion;
    }

    /**
     *
     * @return cle
     */
    public Integer getCle() {
        return this.cle;
    }

    /**
     *
     * @param pCle cle value
     */
    public void setCle(Integer pCle) {
        this.cle = pCle;
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
     * @return dateDebutValidite
     */
    public Date getDateDebutValidite() {
        return this.dateDebutValidite;
    }

    /**
     *
     * @param pDateDebutValidite dateDebutValidite value
     */
    public void setDateDebutValidite(Date pDateDebutValidite) {
        this.dateDebutValidite = pDateDebutValidite;
    }

    /**
     *
     * @return dateFinValidite
     */
    public Date getDateFinValidite() {
        return this.dateFinValidite;
    }

    /**
     *
     * @param pDateFinValidite dateFinValidite value
     */
    public void setDateFinValidite(Date pDateFinValidite) {
        this.dateFinValidite = pDateFinValidite;
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
     * @return emails
     */
    public Set<EmailDTO> getEmails() {
        return this.emails;
    }

    /**
     *
     * @param pEmails emails value
     */
    public void setEmails(Set<EmailDTO> pEmails) {
        this.emails = pEmails;
    }

    /**
     *
     * @return fonction
     */
    public String getFonction() {
        return this.fonction;
    }

    /**
     *
     * @param pFonction fonction value
     */
    public void setFonction(String pFonction) {
        this.fonction = pFonction;
    }

    /**
     *
     * @return membre
     */
    public MembreDTO getMembre() {
        return this.membre;
    }

    /**
     *
     * @param pMembre membre value
     */
    public void setMembre(MembreDTO pMembre) {
        this.membre = pMembre;
    }

    /**
     *
     * @return postalAddresses
     */
    public Set<PostalAddressDTO> getPostalAddresses() {
        return this.postalAddresses;
    }

    /**
     *
     * @param pPostalAddresses postalAddresses value
     */
    public void setPostalAddresses(Set<PostalAddressDTO> pPostalAddresses) {
        this.postalAddresses = pPostalAddresses;
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
     * @return telecoms
     */
    public Set<TelecomsDTO> getTelecoms() {
        return this.telecoms;
    }

    /**
     *
     * @param pTelecoms telecoms value
     */
    public void setTelecoms(Set<TelecomsDTO> pTelecoms) {
        this.telecoms = pTelecoms;
    }

    /**
     *
     * @return version
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     *
     * @param pVersion version value
     */
    public void setVersion(Integer pVersion) {
        this.version = pVersion;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_FFONoKU0EeSXNpATSKyi0Q) ENABLED START*/
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
        buffer.append("cle=").append(getCle());
        buffer.append(",");
        buffer.append("fonction=").append(getFonction());
        buffer.append(",");
        buffer.append("dateDebutValidite=").append(getDateDebutValidite());
        buffer.append(",");
        buffer.append("dateFinValidite=").append(getDateFinValidite());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_FFONoKU0EeSXNpATSKyi0Q u m) ENABLED START*/
    // add your custom methods here if necessary
    
    public boolean isValid() throws SicDomainException{
    	if (getCle()!=null && getVersion() == null && getFonction() == null && getDateDebutValidite() == null && getDateFinValidite() == null) {
			return true;
		}
    	else {
    		if (getVersion() == null) {
				throw new SicDomainException(RefTableREF_ERREUR._REF_133+" Attribut 'Version' pour la fonction est obligatoire");
			}
    		if (getFonction() == null) {
				throw new SicDomainException(RefTableREF_ERREUR._REF_133+" Attribut 'Fonction' pour la fonction est obligatoire");

			}
    		if (getDateDebutValidite() == null) {
				throw new SicDomainException(RefTableREF_ERREUR._REF_211+"  Attribut 'DateDebutValidite' pour la fonction est obligatoire");

			}
    		if (getDateDebutValidite() != null && getDateFinValidite() !=null && getDateFinValidite().before(getDateDebutValidite()) ) {
				throw new SicDomainException(RefTableREF_ERREUR._REF_266+" Date de fin inferieure a Date de debut");
			}
    	}
    	return true;
    }
    /*PROTECTED REGION END*/

}
