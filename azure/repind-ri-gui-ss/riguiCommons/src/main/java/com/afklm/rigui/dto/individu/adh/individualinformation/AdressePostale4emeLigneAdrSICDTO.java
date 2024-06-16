package com.afklm.rigui.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b1qT9CMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : AdressePostale4emeLigneAdrSICDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class AdressePostale4emeLigneAdrSICDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -8952205492020152636L;


	/**
     * cleAdresse
     */
    private String cleAdresse;
        
        
    /**
     * version
     */
    private String version;
        
        
    /**
     * numeroUsage
     */
    private String numeroUsage;
        
        
    /**
     * codeMedium
     */
    private String codeMedium;
        
        
    /**
     * indicAdrNorm
     */
    private Boolean indicAdrNorm;
        
        
    /**
     * statutMedium
     */
    private String statutMedium;
        
        
    /**
     * raisonSociale
     */
    private String raisonSociale;
        
        
    /**
     * ville
     */
    private String ville;
        
        
    /**
     * complAdr
     */
    private String complAdr;
        
        
    /**
     * numeroRue
     */
    private String numeroRue;
        
        
    /**
     * lieuDit
     */
    private String lieuDit;
        
        
    /**
     * codePostal
     */
    private String codePostal;
        
        
    /**
     * codePays
     */
    private String codePays;
        
        
    /**
     * codeProvince
     */
    private String codeProvince;
        
        
    /**
     * cityLineFormat
     */
    private String cityLineFormat;
        
        
    /**
     * preferedAdress
     */
    private Boolean preferedAdress;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        
        
    /**
     * creationSignature
     */
    private String creationSignature;
        
        
    /**
     * modificationSignature
     */
    private String modificationSignature;
        
        
    /**
     * creationSite
     */
    private String creationSite;
        
        
    /**
     * modificationSite
     */
    private String modificationSite;
        
        
    /**
     * blocadresss09424
     */
    private BlocAdressS09424DTO blocadresss09424;
        

    /*PROTECTED REGION ID(_b1qT9CMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public AdressePostale4emeLigneAdrSICDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCleAdresse cleAdresse
     * @param pVersion version
     * @param pNumeroUsage numeroUsage
     * @param pCodeMedium codeMedium
     * @param pIndicAdrNorm indicAdrNorm
     * @param pStatutMedium statutMedium
     * @param pRaisonSociale raisonSociale
     * @param pVille ville
     * @param pComplAdr complAdr
     * @param pNumeroRue numeroRue
     * @param pLieuDit lieuDit
     * @param pCodePostal codePostal
     * @param pCodePays codePays
     * @param pCodeProvince codeProvince
     * @param pCityLineFormat cityLineFormat
     * @param pPreferedAdress preferedAdress
     * @param pDateCreation dateCreation
     * @param pDateModification dateModification
     * @param pCreationSignature creationSignature
     * @param pModificationSignature modificationSignature
     * @param pCreationSite creationSite
     * @param pModificationSite modificationSite
     */
    public AdressePostale4emeLigneAdrSICDTO(String pCleAdresse, String pVersion, String pNumeroUsage, String pCodeMedium, Boolean pIndicAdrNorm, String pStatutMedium, String pRaisonSociale, String pVille, String pComplAdr, String pNumeroRue, String pLieuDit, String pCodePostal, String pCodePays, String pCodeProvince, String pCityLineFormat, Boolean pPreferedAdress, Date pDateCreation, Date pDateModification, String pCreationSignature, String pModificationSignature, String pCreationSite, String pModificationSite) {
        this.cleAdresse = pCleAdresse;
        this.version = pVersion;
        this.numeroUsage = pNumeroUsage;
        this.codeMedium = pCodeMedium;
        this.indicAdrNorm = pIndicAdrNorm;
        this.statutMedium = pStatutMedium;
        this.raisonSociale = pRaisonSociale;
        this.ville = pVille;
        this.complAdr = pComplAdr;
        this.numeroRue = pNumeroRue;
        this.lieuDit = pLieuDit;
        this.codePostal = pCodePostal;
        this.codePays = pCodePays;
        this.codeProvince = pCodeProvince;
        this.cityLineFormat = pCityLineFormat;
        this.preferedAdress = pPreferedAdress;
        this.dateCreation = pDateCreation;
        this.dateModification = pDateModification;
        this.creationSignature = pCreationSignature;
        this.modificationSignature = pModificationSignature;
        this.creationSite = pCreationSite;
        this.modificationSite = pModificationSite;
    }

    /**
     *
     * @return blocadresss09424
     */
    public BlocAdressS09424DTO getBlocadresss09424() {
        return this.blocadresss09424;
    }

    /**
     *
     * @param pBlocadresss09424 blocadresss09424 value
     */
    public void setBlocadresss09424(BlocAdressS09424DTO pBlocadresss09424) {
        this.blocadresss09424 = pBlocadresss09424;
    }

    /**
     *
     * @return cityLineFormat
     */
    public String getCityLineFormat() {
        return this.cityLineFormat;
    }

    /**
     *
     * @param pCityLineFormat cityLineFormat value
     */
    public void setCityLineFormat(String pCityLineFormat) {
        this.cityLineFormat = pCityLineFormat;
    }

    /**
     *
     * @return cleAdresse
     */
    public String getCleAdresse() {
        return this.cleAdresse;
    }

    /**
     *
     * @param pCleAdresse cleAdresse value
     */
    public void setCleAdresse(String pCleAdresse) {
        this.cleAdresse = pCleAdresse;
    }

    /**
     *
     * @return codeMedium
     */
    public String getCodeMedium() {
        return this.codeMedium;
    }

    /**
     *
     * @param pCodeMedium codeMedium value
     */
    public void setCodeMedium(String pCodeMedium) {
        this.codeMedium = pCodeMedium;
    }

    /**
     *
     * @return codePays
     */
    public String getCodePays() {
        return this.codePays;
    }

    /**
     *
     * @param pCodePays codePays value
     */
    public void setCodePays(String pCodePays) {
        this.codePays = pCodePays;
    }

    /**
     *
     * @return codePostal
     */
    public String getCodePostal() {
        return this.codePostal;
    }

    /**
     *
     * @param pCodePostal codePostal value
     */
    public void setCodePostal(String pCodePostal) {
        this.codePostal = pCodePostal;
    }

    /**
     *
     * @return codeProvince
     */
    public String getCodeProvince() {
        return this.codeProvince;
    }

    /**
     *
     * @param pCodeProvince codeProvince value
     */
    public void setCodeProvince(String pCodeProvince) {
        this.codeProvince = pCodeProvince;
    }

    /**
     *
     * @return complAdr
     */
    public String getComplAdr() {
        return this.complAdr;
    }

    /**
     *
     * @param pComplAdr complAdr value
     */
    public void setComplAdr(String pComplAdr) {
        this.complAdr = pComplAdr;
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
     * @return indicAdrNorm
     */
    public Boolean getIndicAdrNorm() {
        return this.indicAdrNorm;
    }

    /**
     *
     * @param pIndicAdrNorm indicAdrNorm value
     */
    public void setIndicAdrNorm(Boolean pIndicAdrNorm) {
        this.indicAdrNorm = pIndicAdrNorm;
    }

    /**
     *
     * @return lieuDit
     */
    public String getLieuDit() {
        return this.lieuDit;
    }

    /**
     *
     * @param pLieuDit lieuDit value
     */
    public void setLieuDit(String pLieuDit) {
        this.lieuDit = pLieuDit;
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
     * @return numeroRue
     */
    public String getNumeroRue() {
        return this.numeroRue;
    }

    /**
     *
     * @param pNumeroRue numeroRue value
     */
    public void setNumeroRue(String pNumeroRue) {
        this.numeroRue = pNumeroRue;
    }

    /**
     *
     * @return numeroUsage
     */
    public String getNumeroUsage() {
        return this.numeroUsage;
    }

    /**
     *
     * @param pNumeroUsage numeroUsage value
     */
    public void setNumeroUsage(String pNumeroUsage) {
        this.numeroUsage = pNumeroUsage;
    }

    /**
     *
     * @return preferedAdress
     */
    public Boolean getPreferedAdress() {
        return this.preferedAdress;
    }

    /**
     *
     * @param pPreferedAdress preferedAdress value
     */
    public void setPreferedAdress(Boolean pPreferedAdress) {
        this.preferedAdress = pPreferedAdress;
    }

    /**
     *
     * @return raisonSociale
     */
    public String getRaisonSociale() {
        return this.raisonSociale;
    }

    /**
     *
     * @param pRaisonSociale raisonSociale value
     */
    public void setRaisonSociale(String pRaisonSociale) {
        this.raisonSociale = pRaisonSociale;
    }

    /**
     *
     * @return statutMedium
     */
    public String getStatutMedium() {
        return this.statutMedium;
    }

    /**
     *
     * @param pStatutMedium statutMedium value
     */
    public void setStatutMedium(String pStatutMedium) {
        this.statutMedium = pStatutMedium;
    }

    /**
     *
     * @return version
     */
    public String getVersion() {
        return this.version;
    }

    /**
     *
     * @param pVersion version value
     */
    public void setVersion(String pVersion) {
        this.version = pVersion;
    }

    /**
     *
     * @return ville
     */
    public String getVille() {
        return this.ville;
    }

    /**
     *
     * @param pVille ville value
     */
    public void setVille(String pVille) {
        this.ville = pVille;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_b1qT9CMUEeCWJOBY8f-ONQ) ENABLED START*/
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
        buffer.append("cleAdresse=").append(getCleAdresse());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("numeroUsage=").append(getNumeroUsage());
        buffer.append(",");
        buffer.append("codeMedium=").append(getCodeMedium());
        buffer.append(",");
        buffer.append("indicAdrNorm=").append(getIndicAdrNorm());
        buffer.append(",");
        buffer.append("statutMedium=").append(getStatutMedium());
        buffer.append(",");
        buffer.append("raisonSociale=").append(getRaisonSociale());
        buffer.append(",");
        buffer.append("ville=").append(getVille());
        buffer.append(",");
        buffer.append("complAdr=").append(getComplAdr());
        buffer.append(",");
        buffer.append("numeroRue=").append(getNumeroRue());
        buffer.append(",");
        buffer.append("lieuDit=").append(getLieuDit());
        buffer.append(",");
        buffer.append("codePostal=").append(getCodePostal());
        buffer.append(",");
        buffer.append("codePays=").append(getCodePays());
        buffer.append(",");
        buffer.append("codeProvince=").append(getCodeProvince());
        buffer.append(",");
        buffer.append("cityLineFormat=").append(getCityLineFormat());
        buffer.append(",");
        buffer.append("preferedAdress=").append(getPreferedAdress());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("creationSignature=").append(getCreationSignature());
        buffer.append(",");
        buffer.append("modificationSignature=").append(getModificationSignature());
        buffer.append(",");
        buffer.append("creationSite=").append(getCreationSite());
        buffer.append(",");
        buffer.append("modificationSite=").append(getModificationSite());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_b1qT9CMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
