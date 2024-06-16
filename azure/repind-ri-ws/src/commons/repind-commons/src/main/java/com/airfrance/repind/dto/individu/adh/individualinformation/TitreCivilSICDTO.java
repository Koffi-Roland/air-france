package com.airfrance.repind.dto.individu.adh.individualinformation;

/*PROTECTED REGION ID(_b1qTxCMUEeCWJOBY8f-ONQ i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;


/*PROTECTED REGION END*/

/**
 * <p>Title : TitreCivilSICDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class TitreCivilSICDTO  {
        
    /**
     * codeTitre
     */
    private String codeTitre;
        
        
    /**
     * libelleFrancais
     */
    private String libelleFrancais;
        
        
    /**
     * libelleAnglais
     */
    private String libelleAnglais;
        

    /*PROTECTED REGION ID(_b1qTxCMUEeCWJOBY8f-ONQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public TitreCivilSICDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCodeTitre codeTitre
     * @param pLibelleFrancais libelleFrancais
     * @param pLibelleAnglais libelleAnglais
     */
    public TitreCivilSICDTO(String pCodeTitre, String pLibelleFrancais, String pLibelleAnglais) {
        this.codeTitre = pCodeTitre;
        this.libelleFrancais = pLibelleFrancais;
        this.libelleAnglais = pLibelleAnglais;
    }

    /**
     *
     * @return codeTitre
     */
    public String getCodeTitre() {
        return this.codeTitre;
    }

    /**
     *
     * @param pCodeTitre codeTitre value
     */
    public void setCodeTitre(String pCodeTitre) {
        this.codeTitre = pCodeTitre;
    }

    /**
     *
     * @return libelleAnglais
     */
    public String getLibelleAnglais() {
        return this.libelleAnglais;
    }

    /**
     *
     * @param pLibelleAnglais libelleAnglais value
     */
    public void setLibelleAnglais(String pLibelleAnglais) {
        this.libelleAnglais = pLibelleAnglais;
    }

    /**
     *
     * @return libelleFrancais
     */
    public String getLibelleFrancais() {
        return this.libelleFrancais;
    }

    /**
     *
     * @param pLibelleFrancais libelleFrancais value
     */
    public void setLibelleFrancais(String pLibelleFrancais) {
        this.libelleFrancais = pLibelleFrancais;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_b1qTxCMUEeCWJOBY8f-ONQ) ENABLED START*/
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
            .append("codeTitre", getCodeTitre())
            .append("libelleFrancais", getLibelleFrancais())
            .append("libelleAnglais", getLibelleAnglais())
            .toString();
    }

    /*PROTECTED REGION ID(_b1qTxCMUEeCWJOBY8f-ONQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
