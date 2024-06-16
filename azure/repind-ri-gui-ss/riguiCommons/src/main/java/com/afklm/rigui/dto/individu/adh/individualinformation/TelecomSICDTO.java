package com.afklm.rigui.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b1qUHyMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : TelecomSICDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class TelecomSICDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 6991877354449917019L;


	/**
     * cleTelecom
     */
    private String cleTelecom;
        
        
    /**
     * version
     */
    private String version;
        
        
    /**
     * statutMedium
     */
    private String statutMedium;
        
        
    /**
     * codeMedium
     */
    private String codeMedium;
        
        
    /**
     * typeTerminal
     */
    private String typeTerminal;
        
        
    /**
     * indicatifPays
     */
    private String indicatifPays;
        
        
    /**
     * indicatifRegion
     */
    private String indicatifRegion;
        
        
    /**
     * numeroTelecom
     */
    private String numeroTelecom;
        
        
    /**
     * numeroTelecomNormalize
     */
    private String numeroTelecomNormalize;
        
        
    /**
     * indicatifPaysNormalize
     */
    private String indicatifPaysNormalize;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        
        
    /**
     * normNatPhoneNumber
     */
    private String normNatPhoneNumber;
        
        
    /**
     * normInterCountryCode
     */
    private String normInterCountryCode;
        
        
    /**
     * normInterPhoneNumber
     */
    private String normInterPhoneNumber;
        
        
    /**
     * normTerminalTypeDetail
     */
    private String normTerminalTypeDetail;
        

    /*PROTECTED REGION ID(_b1qUHyMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public TelecomSICDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCleTelecom cleTelecom
     * @param pVersion version
     * @param pStatutMedium statutMedium
     * @param pCodeMedium codeMedium
     * @param pTypeTerminal typeTerminal
     * @param pIndicatifPays indicatifPays
     * @param pIndicatifRegion indicatifRegion
     * @param pNumeroTelecom numeroTelecom
     * @param pNumeroTelecomNormalize numeroTelecomNormalize
     * @param pIndicatifPaysNormalize indicatifPaysNormalize
     * @param pDateCreation dateCreation
     * @param pDateModification dateModification
     * @param pNormNatPhoneNumber normNatPhoneNumber
     * @param pNormInterCountryCode normInterCountryCode
     * @param pNormInterPhoneNumber normInterPhoneNumber
     * @param pNormTerminalTypeDetail normTerminalTypeDetail
     */
    public TelecomSICDTO(String pCleTelecom, String pVersion, String pStatutMedium, String pCodeMedium, String pTypeTerminal, String pIndicatifPays, String pIndicatifRegion, String pNumeroTelecom, String pNumeroTelecomNormalize, String pIndicatifPaysNormalize, Date pDateCreation, Date pDateModification, String pNormNatPhoneNumber, String pNormInterCountryCode, String pNormInterPhoneNumber, String pNormTerminalTypeDetail) {
        this.cleTelecom = pCleTelecom;
        this.version = pVersion;
        this.statutMedium = pStatutMedium;
        this.codeMedium = pCodeMedium;
        this.typeTerminal = pTypeTerminal;
        this.indicatifPays = pIndicatifPays;
        this.indicatifRegion = pIndicatifRegion;
        this.numeroTelecom = pNumeroTelecom;
        this.numeroTelecomNormalize = pNumeroTelecomNormalize;
        this.indicatifPaysNormalize = pIndicatifPaysNormalize;
        this.dateCreation = pDateCreation;
        this.dateModification = pDateModification;
        this.normNatPhoneNumber = pNormNatPhoneNumber;
        this.normInterCountryCode = pNormInterCountryCode;
        this.normInterPhoneNumber = pNormInterPhoneNumber;
        this.normTerminalTypeDetail = pNormTerminalTypeDetail;
    }

    /**
     *
     * @return cleTelecom
     */
    public String getCleTelecom() {
        return this.cleTelecom;
    }

    /**
     *
     * @param pCleTelecom cleTelecom value
     */
    public void setCleTelecom(String pCleTelecom) {
        this.cleTelecom = pCleTelecom;
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
     * @return indicatifPays
     */
    public String getIndicatifPays() {
        return this.indicatifPays;
    }

    /**
     *
     * @param pIndicatifPays indicatifPays value
     */
    public void setIndicatifPays(String pIndicatifPays) {
        this.indicatifPays = pIndicatifPays;
    }

    /**
     *
     * @return indicatifPaysNormalize
     */
    public String getIndicatifPaysNormalize() {
        return this.indicatifPaysNormalize;
    }

    /**
     *
     * @param pIndicatifPaysNormalize indicatifPaysNormalize value
     */
    public void setIndicatifPaysNormalize(String pIndicatifPaysNormalize) {
        this.indicatifPaysNormalize = pIndicatifPaysNormalize;
    }

    /**
     *
     * @return indicatifRegion
     */
    public String getIndicatifRegion() {
        return this.indicatifRegion;
    }

    /**
     *
     * @param pIndicatifRegion indicatifRegion value
     */
    public void setIndicatifRegion(String pIndicatifRegion) {
        this.indicatifRegion = pIndicatifRegion;
    }

    /**
     *
     * @return normInterCountryCode
     */
    public String getNormInterCountryCode() {
        return this.normInterCountryCode;
    }

    /**
     *
     * @param pNormInterCountryCode normInterCountryCode value
     */
    public void setNormInterCountryCode(String pNormInterCountryCode) {
        this.normInterCountryCode = pNormInterCountryCode;
    }

    /**
     *
     * @return normInterPhoneNumber
     */
    public String getNormInterPhoneNumber() {
        return this.normInterPhoneNumber;
    }

    /**
     *
     * @param pNormInterPhoneNumber normInterPhoneNumber value
     */
    public void setNormInterPhoneNumber(String pNormInterPhoneNumber) {
        this.normInterPhoneNumber = pNormInterPhoneNumber;
    }

    /**
     *
     * @return normNatPhoneNumber
     */
    public String getNormNatPhoneNumber() {
        return this.normNatPhoneNumber;
    }

    /**
     *
     * @param pNormNatPhoneNumber normNatPhoneNumber value
     */
    public void setNormNatPhoneNumber(String pNormNatPhoneNumber) {
        this.normNatPhoneNumber = pNormNatPhoneNumber;
    }

    /**
     *
     * @return normTerminalTypeDetail
     */
    public String getNormTerminalTypeDetail() {
        return this.normTerminalTypeDetail;
    }

    /**
     *
     * @param pNormTerminalTypeDetail normTerminalTypeDetail value
     */
    public void setNormTerminalTypeDetail(String pNormTerminalTypeDetail) {
        this.normTerminalTypeDetail = pNormTerminalTypeDetail;
    }

    /**
     *
     * @return numeroTelecom
     */
    public String getNumeroTelecom() {
        return this.numeroTelecom;
    }

    /**
     *
     * @param pNumeroTelecom numeroTelecom value
     */
    public void setNumeroTelecom(String pNumeroTelecom) {
        this.numeroTelecom = pNumeroTelecom;
    }

    /**
     *
     * @return numeroTelecomNormalize
     */
    public String getNumeroTelecomNormalize() {
        return this.numeroTelecomNormalize;
    }

    /**
     *
     * @param pNumeroTelecomNormalize numeroTelecomNormalize value
     */
    public void setNumeroTelecomNormalize(String pNumeroTelecomNormalize) {
        this.numeroTelecomNormalize = pNumeroTelecomNormalize;
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
     * @return typeTerminal
     */
    public String getTypeTerminal() {
        return this.typeTerminal;
    }

    /**
     *
     * @param pTypeTerminal typeTerminal value
     */
    public void setTypeTerminal(String pTypeTerminal) {
        this.typeTerminal = pTypeTerminal;
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_b1qUHyMUEeCWJOBY8f-ONQ) ENABLED START*/
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
        buffer.append("cleTelecom=").append(getCleTelecom());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("statutMedium=").append(getStatutMedium());
        buffer.append(",");
        buffer.append("codeMedium=").append(getCodeMedium());
        buffer.append(",");
        buffer.append("typeTerminal=").append(getTypeTerminal());
        buffer.append(",");
        buffer.append("indicatifPays=").append(getIndicatifPays());
        buffer.append(",");
        buffer.append("indicatifRegion=").append(getIndicatifRegion());
        buffer.append(",");
        buffer.append("numeroTelecom=").append(getNumeroTelecom());
        buffer.append(",");
        buffer.append("numeroTelecomNormalize=").append(getNumeroTelecomNormalize());
        buffer.append(",");
        buffer.append("indicatifPaysNormalize=").append(getIndicatifPaysNormalize());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("normNatPhoneNumber=").append(getNormNatPhoneNumber());
        buffer.append(",");
        buffer.append("normInterCountryCode=").append(getNormInterCountryCode());
        buffer.append(",");
        buffer.append("normInterPhoneNumber=").append(getNormInterPhoneNumber());
        buffer.append(",");
        buffer.append("normTerminalTypeDetail=").append(getNormTerminalTypeDetail());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_b1qUHyMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
