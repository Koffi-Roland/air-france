package com.airfrance.repind.dto.zone;

/*PROTECTED REGION ID(_mtzHMDO6EeKT_JQCdHEO1w i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : LienIntCpZdDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class LienIntCpZdDTO implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 4019611598429525538L;


	/**
     * cle
     */
    private Long cle;
        
        
    /**
     * signatureMaj
     */
    private String signatureMaj;
        
        
    /**
     * dateMaj
     */
    private Date dateMaj;
        
        
    /**
     * dateFinLien
     */
    private Date dateFinLien;
        
        
    /**
     * dateDebutLien
     */
    private Date dateDebutLien;
        
        
    /**
     * usage
     */
    private String usage;
        
        
    /**
     * codeVille
     */
    private String codeVille;
        
        
    /**
     * codeProvince
     */
    private String codeProvince;
        
        
    /**
     * codePays
     */
    private String codePays;
        
        
    /**
     * intervalleCodesPostaux
     */
    private IntervalleCodesPostauxDTO intervalleCodesPostaux;
        
        
    /**
     * zoneDecoup
     */
    private ZoneDecoupDTO zoneDecoup;
        

    /*PROTECTED REGION ID(_mtzHMDO6EeKT_JQCdHEO1w u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public LienIntCpZdDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCle cle
     * @param pSignatureMaj signatureMaj
     * @param pDateMaj dateMaj
     * @param pDateFinLien dateFinLien
     * @param pDateDebutLien dateDebutLien
     * @param pUsage usage
     * @param pCodeVille codeVille
     * @param pCodeProvince codeProvince
     * @param pCodePays codePays
     */
    public LienIntCpZdDTO(Long pCle, String pSignatureMaj, Date pDateMaj, Date pDateFinLien, Date pDateDebutLien, String pUsage, String pCodeVille, String pCodeProvince, String pCodePays) {
        this.cle = pCle;
        this.signatureMaj = pSignatureMaj;
        this.dateMaj = pDateMaj;
        this.dateFinLien = pDateFinLien;
        this.dateDebutLien = pDateDebutLien;
        this.usage = pUsage;
        this.codeVille = pCodeVille;
        this.codeProvince = pCodeProvince;
        this.codePays = pCodePays;
    }

    /**
     *
     * @return cle
     */
    public Long getCle() {
        return this.cle;
    }

    /**
     *
     * @param pCle cle value
     */
    public void setCle(Long pCle) {
        this.cle = pCle;
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
     * @return codeVille
     */
    public String getCodeVille() {
        return this.codeVille;
    }

    /**
     *
     * @param pCodeVille codeVille value
     */
    public void setCodeVille(String pCodeVille) {
        this.codeVille = pCodeVille;
    }

    /**
     *
     * @return dateDebutLien
     */
    public Date getDateDebutLien() {
        return this.dateDebutLien;
    }

    /**
     *
     * @param pDateDebutLien dateDebutLien value
     */
    public void setDateDebutLien(Date pDateDebutLien) {
        this.dateDebutLien = pDateDebutLien;
    }

    /**
     *
     * @return dateFinLien
     */
    public Date getDateFinLien() {
        return this.dateFinLien;
    }

    /**
     *
     * @param pDateFinLien dateFinLien value
     */
    public void setDateFinLien(Date pDateFinLien) {
        this.dateFinLien = pDateFinLien;
    }

    /**
     *
     * @return dateMaj
     */
    public Date getDateMaj() {
        return this.dateMaj;
    }

    /**
     *
     * @param pDateMaj dateMaj value
     */
    public void setDateMaj(Date pDateMaj) {
        this.dateMaj = pDateMaj;
    }

    /**
     *
     * @return intervalleCodesPostaux
     */
    public IntervalleCodesPostauxDTO getIntervalleCodesPostaux() {
        return this.intervalleCodesPostaux;
    }

    /**
     *
     * @param pIntervalleCodesPostaux intervalleCodesPostaux value
     */
    public void setIntervalleCodesPostaux(IntervalleCodesPostauxDTO pIntervalleCodesPostaux) {
        this.intervalleCodesPostaux = pIntervalleCodesPostaux;
    }

    /**
     *
     * @return signatureMaj
     */
    public String getSignatureMaj() {
        return this.signatureMaj;
    }

    /**
     *
     * @param pSignatureMaj signatureMaj value
     */
    public void setSignatureMaj(String pSignatureMaj) {
        this.signatureMaj = pSignatureMaj;
    }

    /**
     *
     * @return usage
     */
    public String getUsage() {
        return this.usage;
    }

    /**
     *
     * @param pUsage usage value
     */
    public void setUsage(String pUsage) {
        this.usage = pUsage;
    }

    /**
     *
     * @return zoneDecoup
     */
    public ZoneDecoupDTO getZoneDecoup() {
        return this.zoneDecoup;
    }

    /**
     *
     * @param pZoneDecoup zoneDecoup value
     */
    public void setZoneDecoup(ZoneDecoupDTO pZoneDecoup) {
        this.zoneDecoup = pZoneDecoup;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_mtzHMDO6EeKT_JQCdHEO1w) ENABLED START*/
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
        buffer.append("signatureMaj=").append(getSignatureMaj());
        buffer.append(",");
        buffer.append("dateMaj=").append(getDateMaj());
        buffer.append(",");
        buffer.append("dateFinLien=").append(getDateFinLien());
        buffer.append(",");
        buffer.append("dateDebutLien=").append(getDateDebutLien());
        buffer.append(",");
        buffer.append("usage=").append(getUsage());
        buffer.append(",");
        buffer.append("codeVille=").append(getCodeVille());
        buffer.append(",");
        buffer.append("codeProvince=").append(getCodeProvince());
        buffer.append(",");
        buffer.append("codePays=").append(getCodePays());
        buffer.append("]");
        return buffer.toString();
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cle == null) ? 0 : cle.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LienIntCpZdDTO other = (LienIntCpZdDTO) obj;
		if (cle == null) {
			if (other.cle != null) {
				return false;
			}
		} else if (!cle.equals(other.cle)) {
			return false;
		}
		return true;
	}

    
    /*PROTECTED REGION ID(_mtzHMDO6EeKT_JQCdHEO1w u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
