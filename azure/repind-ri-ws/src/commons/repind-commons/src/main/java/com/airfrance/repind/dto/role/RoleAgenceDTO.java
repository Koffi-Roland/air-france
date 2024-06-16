package com.airfrance.repind.dto.role;

/*PROTECTED REGION ID(_YsVEIDqZEeS2wtWjh0gEaw i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : RoleAgenceDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class RoleAgenceDTO implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -3795414042575309131L;


	/**
     * cleRole
     */
    private Integer cleRole;
        
        
    /**
     * type
     */
    private String type;
        
        
    /**
     * sousType
     */
    private String sousType;
        
        
    /**
     * typeIncentive
     */
    private String typeIncentive;
        
        
    /**
     * etat
     */
    private String etat;
        
        
    /**
     * numero
     */
    private String numero;
        
        
    /**
     * nomContrat
     */
    private String nomContrat;
        
        
    /**
     * statusCrtSeq
     */
    private String statusCrtSeq;
        
        
    /**
     * libelle
     */
    private String libelle;
        
        
    /**
     * debutValidite
     */
    private Date debutValidite;
        
        
    /**
     * finValidite
     */
    private Date finValidite;
        
        
    /**
     * contracting
     */
    private String contracting;
        
        
    /**
     * exclusion
     */
    private String exclusion;
        
        
    /**
     * gin
     */
    private String gin;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * siteCreation
     */
    private String siteCreation;
        
        
    /**
     * signatureCreation
     */
    private String signatureCreation;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        
        
    /**
     * siteModification
     */
    private String siteModification;
        
        
    /**
     * signatureModification
     */
    private String signatureModification;
        
        
    /**
     * version
     */
    private Integer version;
        
        
    /**
     * businessRole
     */
    private BusinessRoleDTO businessRole;
        
        
    /**
     * contratTcSeq
     */
    private ContratTcSeqDTO contratTcSeq;
        

    /*PROTECTED REGION ID(_YsVEIDqZEeS2wtWjh0gEaw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public RoleAgenceDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCleRole cleRole
     * @param pType type
     * @param pSousType sousType
     * @param pTypeIncentive typeIncentive
     * @param pEtat etat
     * @param pNumero numero
     * @param pNomContrat nomContrat
     * @param pStatusCrtSeq statusCrtSeq
     * @param pLibelle libelle
     * @param pDebutValidite debutValidite
     * @param pFinValidite finValidite
     * @param pContracting contracting
     * @param pExclusion exclusion
     * @param pGin gin
     * @param pDateCreation dateCreation
     * @param pSiteCreation siteCreation
     * @param pSignatureCreation signatureCreation
     * @param pDateModification dateModification
     * @param pSiteModification siteModification
     * @param pSignatureModification signatureModification
     * @param pVersion version
     */
    public RoleAgenceDTO(Integer pCleRole, String pType, String pSousType, String pTypeIncentive, String pEtat, String pNumero, String pNomContrat, String pStatusCrtSeq, String pLibelle, Date pDebutValidite, Date pFinValidite, String pContracting, String pExclusion, String pGin, Date pDateCreation, String pSiteCreation, String pSignatureCreation, Date pDateModification, String pSiteModification, String pSignatureModification, Integer pVersion) {
        this.cleRole = pCleRole;
        this.type = pType;
        this.sousType = pSousType;
        this.typeIncentive = pTypeIncentive;
        this.etat = pEtat;
        this.numero = pNumero;
        this.nomContrat = pNomContrat;
        this.statusCrtSeq = pStatusCrtSeq;
        this.libelle = pLibelle;
        this.debutValidite = pDebutValidite;
        this.finValidite = pFinValidite;
        this.contracting = pContracting;
        this.exclusion = pExclusion;
        this.gin = pGin;
        this.dateCreation = pDateCreation;
        this.siteCreation = pSiteCreation;
        this.signatureCreation = pSignatureCreation;
        this.dateModification = pDateModification;
        this.siteModification = pSiteModification;
        this.signatureModification = pSignatureModification;
        this.version = pVersion;
    }

    /**
     *
     * @return businessRole
     */
    public BusinessRoleDTO getBusinessRole() {
        return this.businessRole;
    }

    /**
     *
     * @param pBusinessRole businessRole value
     */
    public void setBusinessRole(BusinessRoleDTO pBusinessRole) {
        this.businessRole = pBusinessRole;
    }

    /**
     *
     * @return cleRole
     */
    public Integer getCleRole() {
        return this.cleRole;
    }

    /**
     *
     * @param pCleRole cleRole value
     */
    public void setCleRole(Integer pCleRole) {
        this.cleRole = pCleRole;
    }

    /**
     *
     * @return contracting
     */
    public String getContracting() {
        return this.contracting;
    }

    /**
     *
     * @param pContracting contracting value
     */
    public void setContracting(String pContracting) {
        this.contracting = pContracting;
    }

    /**
     *
     * @return contratTcSeq
     */
    public ContratTcSeqDTO getContratTcSeq() {
        return this.contratTcSeq;
    }

    /**
     *
     * @param pContratTcSeq contratTcSeq value
     */
    public void setContratTcSeq(ContratTcSeqDTO pContratTcSeq) {
        this.contratTcSeq = pContratTcSeq;
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
     * @return debutValidite
     */
    public Date getDebutValidite() {
        return this.debutValidite;
    }

    /**
     *
     * @param pDebutValidite debutValidite value
     */
    public void setDebutValidite(Date pDebutValidite) {
        this.debutValidite = pDebutValidite;
    }

    /**
     *
     * @return etat
     */
    public String getEtat() {
        return this.etat;
    }

    /**
     *
     * @param pEtat etat value
     */
    public void setEtat(String pEtat) {
        this.etat = pEtat;
    }

    /**
     *
     * @return exclusion
     */
    public String getExclusion() {
        return this.exclusion;
    }

    /**
     *
     * @param pExclusion exclusion value
     */
    public void setExclusion(String pExclusion) {
        this.exclusion = pExclusion;
    }

    /**
     *
     * @return finValidite
     */
    public Date getFinValidite() {
        return this.finValidite;
    }

    /**
     *
     * @param pFinValidite finValidite value
     */
    public void setFinValidite(Date pFinValidite) {
        this.finValidite = pFinValidite;
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
     * @return libelle
     */
    public String getLibelle() {
        return this.libelle;
    }

    /**
     *
     * @param pLibelle libelle value
     */
    public void setLibelle(String pLibelle) {
        this.libelle = pLibelle;
    }

    /**
     *
     * @return nomContrat
     */
    public String getNomContrat() {
        return this.nomContrat;
    }

    /**
     *
     * @param pNomContrat nomContrat value
     */
    public void setNomContrat(String pNomContrat) {
        this.nomContrat = pNomContrat;
    }

    /**
     *
     * @return numero
     */
    public String getNumero() {
        return this.numero;
    }

    /**
     *
     * @param pNumero numero value
     */
    public void setNumero(String pNumero) {
        this.numero = pNumero;
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
     * @return sousType
     */
    public String getSousType() {
        return this.sousType;
    }

    /**
     *
     * @param pSousType sousType value
     */
    public void setSousType(String pSousType) {
        this.sousType = pSousType;
    }

    /**
     *
     * @return statusCrtSeq
     */
    public String getStatusCrtSeq() {
        return this.statusCrtSeq;
    }

    /**
     *
     * @param pStatusCrtSeq statusCrtSeq value
     */
    public void setStatusCrtSeq(String pStatusCrtSeq) {
        this.statusCrtSeq = pStatusCrtSeq;
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
     * @return typeIncentive
     */
    public String getTypeIncentive() {
        return this.typeIncentive;
    }

    /**
     *
     * @param pTypeIncentive typeIncentive value
     */
    public void setTypeIncentive(String pTypeIncentive) {
        this.typeIncentive = pTypeIncentive;
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
        /*PROTECTED REGION ID(toString_YsVEIDqZEeS2wtWjh0gEaw) ENABLED START*/
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
        buffer.append("cleRole=").append(getCleRole());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("sousType=").append(getSousType());
        buffer.append(",");
        buffer.append("typeIncentive=").append(getTypeIncentive());
        buffer.append(",");
        buffer.append("etat=").append(getEtat());
        buffer.append(",");
        buffer.append("numero=").append(getNumero());
        buffer.append(",");
        buffer.append("nomContrat=").append(getNomContrat());
        buffer.append(",");
        buffer.append("statusCrtSeq=").append(getStatusCrtSeq());
        buffer.append(",");
        buffer.append("libelle=").append(getLibelle());
        buffer.append(",");
        buffer.append("debutValidite=").append(getDebutValidite());
        buffer.append(",");
        buffer.append("finValidite=").append(getFinValidite());
        buffer.append(",");
        buffer.append("contracting=").append(getContracting());
        buffer.append(",");
        buffer.append("exclusion=").append(getExclusion());
        buffer.append(",");
        buffer.append("gin=").append(getGin());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_YsVEIDqZEeS2wtWjh0gEaw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
