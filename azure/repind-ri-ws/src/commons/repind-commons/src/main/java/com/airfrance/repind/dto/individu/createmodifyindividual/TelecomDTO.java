package com.airfrance.repind.dto.individu.createmodifyindividual;

/*PROTECTED REGION ID(_nV_MsTRkEeCc7ZsKsK1lbQ i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : TelecomDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class TelecomDTO  {
        
    /**
     * cleTelecom
     */
    private String cleTelecom;
        
        
    /**
     * version
     */
    private Integer version;
        
        
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
        

    /*PROTECTED REGION ID(_nV_MsTRkEeCc7ZsKsK1lbQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public TelecomDTO() {
    
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
     */
    public TelecomDTO(String pCleTelecom, Integer pVersion, String pStatutMedium, String pCodeMedium, String pTypeTerminal, String pIndicatifPays, String pIndicatifRegion, String pNumeroTelecom, String pNumeroTelecomNormalize, String pIndicatifPaysNormalize) {
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
        /*PROTECTED REGION ID(toString_nV_MsTRkEeCc7ZsKsK1lbQ) ENABLED START*/
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
            .append("cleTelecom", getCleTelecom())
            .append("version", getVersion())
            .append("statutMedium", getStatutMedium())
            .append("codeMedium", getCodeMedium())
            .append("typeTerminal", getTypeTerminal())
            .append("indicatifPays", getIndicatifPays())
            .append("indicatifRegion", getIndicatifRegion())
            .append("numeroTelecom", getNumeroTelecom())
            .append("numeroTelecomNormalize", getNumeroTelecomNormalize())
            .append("indicatifPaysNormalize", getIndicatifPaysNormalize())
            .toString();
    }

    /*PROTECTED REGION ID(_nV_MsTRkEeCc7ZsKsK1lbQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
