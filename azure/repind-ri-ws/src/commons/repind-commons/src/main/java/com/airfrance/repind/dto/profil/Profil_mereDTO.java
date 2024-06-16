package com.airfrance.repind.dto.profil;

/*PROTECTED REGION ID(_S9GWwDUfEeCq6pHdxM8RnQ i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : Profil_mereDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class Profil_mereDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -8697908332517109095L;


	/**
     * icle_prf
     */
    private Integer icle_prf;
        
        
    /**
     * sgin_pm
     */
    private String sgin_pm;
        
        
    /**
     * icle_role
     */
    private Integer icle_role;
        
        
    /**
     * icle_banq
     */
    private Integer icle_banq;
        
        
    /**
     * icle_fact
     */
    private Integer icle_fact;
        
        
    /**
     * icle_financ
     */
    private Integer icle_financ;
        
        
    /**
     * sgin_ind
     */
    private String sgin_ind;
        
        
    /**
     * stype
     */
    private String stype;
        
        
    /**
     * profilContentieux
     */
    private ProfilContentieuxDTO profilContentieux;
        
        
    /**
     * profilDemarcharge
     */
    private ProfilDemarchargeDTO profilDemarcharge;
        
        
    /**
     * profilQualitatif
     */
    private ProfilQualitatifDTO profilQualitatif;
        
        
    /**
     * profil_afdto
     */
    private Profil_afDTO profil_afdto;
        

    /*PROTECTED REGION ID(_S9GWwDUfEeCq6pHdxM8RnQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public Profil_mereDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pIcle_prf icle_prf
     * @param pSgin_pm sgin_pm
     * @param pIcle_role icle_role
     * @param pIcle_banq icle_banq
     * @param pIcle_fact icle_fact
     * @param pIcle_financ icle_financ
     * @param pSgin_ind sgin_ind
     * @param pStype stype
     */
    public Profil_mereDTO(Integer pIcle_prf, String pSgin_pm, Integer pIcle_role, Integer pIcle_banq, Integer pIcle_fact, Integer pIcle_financ, String pSgin_ind, String pStype) {
        this.icle_prf = pIcle_prf;
        this.sgin_pm = pSgin_pm;
        this.icle_role = pIcle_role;
        this.icle_banq = pIcle_banq;
        this.icle_fact = pIcle_fact;
        this.icle_financ = pIcle_financ;
        this.sgin_ind = pSgin_ind;
        this.stype = pStype;
    }

    /**
     *
     * @return icle_banq
     */
    public Integer getIcle_banq() {
        return this.icle_banq;
    }

    /**
     *
     * @param pIcle_banq icle_banq value
     */
    public void setIcle_banq(Integer pIcle_banq) {
        this.icle_banq = pIcle_banq;
    }

    /**
     *
     * @return icle_fact
     */
    public Integer getIcle_fact() {
        return this.icle_fact;
    }

    /**
     *
     * @param pIcle_fact icle_fact value
     */
    public void setIcle_fact(Integer pIcle_fact) {
        this.icle_fact = pIcle_fact;
    }

    /**
     *
     * @return icle_financ
     */
    public Integer getIcle_financ() {
        return this.icle_financ;
    }

    /**
     *
     * @param pIcle_financ icle_financ value
     */
    public void setIcle_financ(Integer pIcle_financ) {
        this.icle_financ = pIcle_financ;
    }

    /**
     *
     * @return icle_prf
     */
    public Integer getIcle_prf() {
        return this.icle_prf;
    }

    /**
     *
     * @param pIcle_prf icle_prf value
     */
    public void setIcle_prf(Integer pIcle_prf) {
        this.icle_prf = pIcle_prf;
    }

    /**
     *
     * @return icle_role
     */
    public Integer getIcle_role() {
        return this.icle_role;
    }

    /**
     *
     * @param pIcle_role icle_role value
     */
    public void setIcle_role(Integer pIcle_role) {
        this.icle_role = pIcle_role;
    }

    /**
     *
     * @return profilContentieux
     */
    public ProfilContentieuxDTO getProfilContentieux() {
        return this.profilContentieux;
    }

    /**
     *
     * @param pProfilContentieux profilContentieux value
     */
    public void setProfilContentieux(ProfilContentieuxDTO pProfilContentieux) {
        this.profilContentieux = pProfilContentieux;
    }

    /**
     *
     * @return profilDemarcharge
     */
    public ProfilDemarchargeDTO getProfilDemarcharge() {
        return this.profilDemarcharge;
    }

    /**
     *
     * @param pProfilDemarcharge profilDemarcharge value
     */
    public void setProfilDemarcharge(ProfilDemarchargeDTO pProfilDemarcharge) {
        this.profilDemarcharge = pProfilDemarcharge;
    }

    /**
     *
     * @return profilQualitatif
     */
    public ProfilQualitatifDTO getProfilQualitatif() {
        return this.profilQualitatif;
    }

    /**
     *
     * @param pProfilQualitatif profilQualitatif value
     */
    public void setProfilQualitatif(ProfilQualitatifDTO pProfilQualitatif) {
        this.profilQualitatif = pProfilQualitatif;
    }

    /**
     *
     * @return profil_afdto
     */
    public Profil_afDTO getProfil_afdto() {
        return this.profil_afdto;
    }

    /**
     *
     * @param pProfil_afdto profil_afdto value
     */
    public void setProfil_afdto(Profil_afDTO pProfil_afdto) {
        this.profil_afdto = pProfil_afdto;
    }

    /**
     *
     * @return sgin_ind
     */
    public String getSgin_ind() {
        return this.sgin_ind;
    }

    /**
     *
     * @param pSgin_ind sgin_ind value
     */
    public void setSgin_ind(String pSgin_ind) {
        this.sgin_ind = pSgin_ind;
    }

    /**
     *
     * @return sgin_pm
     */
    public String getSgin_pm() {
        return this.sgin_pm;
    }

    /**
     *
     * @param pSgin_pm sgin_pm value
     */
    public void setSgin_pm(String pSgin_pm) {
        this.sgin_pm = pSgin_pm;
    }

    /**
     *
     * @return stype
     */
    public String getStype() {
        return this.stype;
    }

    /**
     *
     * @param pStype stype value
     */
    public void setStype(String pStype) {
        this.stype = pStype;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_S9GWwDUfEeCq6pHdxM8RnQ) ENABLED START*/
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
        buffer.append("icle_prf=").append(getIcle_prf());
        buffer.append(",");
        buffer.append("sgin_pm=").append(getSgin_pm());
        buffer.append(",");
        buffer.append("icle_role=").append(getIcle_role());
        buffer.append(",");
        buffer.append("icle_banq=").append(getIcle_banq());
        buffer.append(",");
        buffer.append("icle_fact=").append(getIcle_fact());
        buffer.append(",");
        buffer.append("icle_financ=").append(getIcle_financ());
        buffer.append(",");
        buffer.append("sgin_ind=").append(getSgin_ind());
        buffer.append(",");
        buffer.append("stype=").append(getStype());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_S9GWwDUfEeCq6pHdxM8RnQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
