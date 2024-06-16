package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_EfLtoNWHEeGWVMiDtSYzVA i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.individu.IndividuDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : MembreDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class MembreDTO implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 3160335531435687375L;


	/**
     * key
     */
    private Integer key;
        
        
    /**
     * version
     */
    private Integer version;
        
        
    /**
     * fonction
     */
    private String fonction;
        
        
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
     * dateDebutValidite
     */
    private Date dateDebutValidite;
        
        
    /**
     * dateFinValidite
     */
    private Date dateFinValidite;
        
        
    /**
     * client
     */
    private String client;
        
        
    /**
     * contact
     */
    private String contact;
        
        
    /**
     * contactAf
     */
    private String contactAf;
        
        
    /**
     * emissionHs
     */
    private String emissionHs;
        
        
    /**
     * serviceAf
     */
    private String serviceAf;
        
        
    /**
     * fonctions
     */
    private Set<FonctionDTO> fonctions;
        
        
    /**
     * individu
     */
    private IndividuDTO individu;
        
        
    /**
     * personneMorale
     */
    private PersonneMoraleDTO personneMorale;
        

    /*PROTECTED REGION ID(_EfLtoNWHEeGWVMiDtSYzVA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public MembreDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pKey key
     * @param pVersion version
     * @param pFonction fonction
     * @param pDateCreation dateCreation
     * @param pSignatureCreation signatureCreation
     * @param pDateModification dateModification
     * @param pSignatureModification signatureModification
     * @param pDateDebutValidite dateDebutValidite
     * @param pDateFinValidite dateFinValidite
     * @param pClient client
     * @param pContact contact
     * @param pContactAf contactAf
     * @param pEmissionHs emissionHs
     * @param pServiceAf serviceAf
     */
    public MembreDTO(Integer pKey, Integer pVersion, String pFonction, Date pDateCreation, String pSignatureCreation, Date pDateModification, String pSignatureModification, Date pDateDebutValidite, Date pDateFinValidite, String pClient, String pContact, String pContactAf, String pEmissionHs, String pServiceAf) {
        this.key = pKey;
        this.version = pVersion;
        this.fonction = pFonction;
        this.dateCreation = pDateCreation;
        this.signatureCreation = pSignatureCreation;
        this.dateModification = pDateModification;
        this.signatureModification = pSignatureModification;
        this.dateDebutValidite = pDateDebutValidite;
        this.dateFinValidite = pDateFinValidite;
        this.client = pClient;
        this.contact = pContact;
        this.contactAf = pContactAf;
        this.emissionHs = pEmissionHs;
        this.serviceAf = pServiceAf;
    }

    /**
     *
     * @return client
     */
    public String getClient() {
        return this.client;
    }

    /**
     *
     * @param pClient client value
     */
    public void setClient(String pClient) {
        this.client = pClient;
    }

    /**
     *
     * @return contact
     */
    public String getContact() {
        return this.contact;
    }

    /**
     *
     * @param pContact contact value
     */
    public void setContact(String pContact) {
        this.contact = pContact;
    }

    /**
     *
     * @return contactAf
     */
    public String getContactAf() {
        return this.contactAf;
    }

    /**
     *
     * @param pContactAf contactAf value
     */
    public void setContactAf(String pContactAf) {
        this.contactAf = pContactAf;
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
     * @return emissionHs
     */
    public String getEmissionHs() {
        return this.emissionHs;
    }

    /**
     *
     * @param pEmissionHs emissionHs value
     */
    public void setEmissionHs(String pEmissionHs) {
        this.emissionHs = pEmissionHs;
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
     * @return fonctions
     */
    public Set<FonctionDTO> getFonctions() {
        return this.fonctions;
    }

    /**
     *
     * @param pFonctions fonctions value
     */
    public void setFonctions(Set<FonctionDTO> pFonctions) {
        this.fonctions = pFonctions;
    }

    /**
     *
     * @return individu
     */
    public IndividuDTO getIndividu() {
        return this.individu;
    }

    /**
     *
     * @param pIndividu individu value
     */
    public void setIndividu(IndividuDTO pIndividu) {
        this.individu = pIndividu;
    }

    /**
     *
     * @return key
     */
    public Integer getKey() {
        return this.key;
    }

    /**
     *
     * @param pKey key value
     */
    public void setKey(Integer pKey) {
        this.key = pKey;
    }

    /**
     *
     * @return personneMorale
     */
    public PersonneMoraleDTO getPersonneMorale() {
        return this.personneMorale;
    }

    /**
     *
     * @param pPersonneMorale personneMorale value
     */
    public void setPersonneMorale(PersonneMoraleDTO pPersonneMorale) {
        this.personneMorale = pPersonneMorale;
    }

    /**
     *
     * @return serviceAf
     */
    public String getServiceAf() {
        return this.serviceAf;
    }

    /**
     *
     * @param pServiceAf serviceAf value
     */
    public void setServiceAf(String pServiceAf) {
        this.serviceAf = pServiceAf;
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
        /*PROTECTED REGION ID(toString_EfLtoNWHEeGWVMiDtSYzVA) ENABLED START*/
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
        buffer.append("key=").append(getKey());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("fonction=").append(getFonction());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("dateDebutValidite=").append(getDateDebutValidite());
        buffer.append(",");
        buffer.append("dateFinValidite=").append(getDateFinValidite());
        buffer.append(",");
        buffer.append("client=").append(getClient());
        buffer.append(",");
        buffer.append("contact=").append(getContact());
        buffer.append(",");
        buffer.append("contactAf=").append(getContactAf());
        buffer.append(",");
        buffer.append("emissionHs=").append(getEmissionHs());
        buffer.append(",");
        buffer.append("serviceAf=").append(getServiceAf());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_EfLtoNWHEeGWVMiDtSYzVA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
