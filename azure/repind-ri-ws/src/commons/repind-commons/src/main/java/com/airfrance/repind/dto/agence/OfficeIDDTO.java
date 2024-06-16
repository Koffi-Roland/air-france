package com.airfrance.repind.dto.agence;

/*PROTECTED REGION ID(_0VRuD2k1EeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : OfficeIDDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class OfficeIDDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -8655369055448752303L;


	/**
     * cle
     */
    private Long cle;
        
        
    /**
     * dateLastResa
     */
    private Date dateLastResa;
        
        
    /**
     * dateMaj
     */
    private Date dateMaj;
        
        
    /**
     * codeGDS
     */
    private String codeGDS;
        
        
    /**
     * lettreComptoir
     */
    private String lettreComptoir;
        
        
    /**
     * majManuelle
     */
    private String majManuelle;
        
        
    /**
     * officeID
     */
    private String officeID;
        
        
    /**
     * signatureMaj
     */
    private String signatureMaj;
        
        
    /**
     * siteMaj
     */
    private String siteMaj;
        
        
    /**
     * agence
     */
    private AgenceDTO agence;
        

    /*PROTECTED REGION ID(_0VRuD2k1EeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public OfficeIDDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pDateLastResa dateLastResa
     * @param pDateMaj dateMaj
     * @param pCle cle
     * @param pCodeGDS codeGDS
     * @param pLettreComptoir lettreComptoir
     * @param pMajManuelle majManuelle
     * @param pOfficeID officeID
     * @param pSignatureMaj signatureMaj
     * @param pSiteMaj siteMaj
     */
    public OfficeIDDTO(Date pDateLastResa, Date pDateMaj, Long pCle, String pCodeGDS, String pLettreComptoir, String pMajManuelle, String pOfficeID, String pSignatureMaj, String pSiteMaj) {
        this.dateLastResa = pDateLastResa;
        this.dateMaj = pDateMaj;
        this.cle = pCle;
        this.codeGDS = pCodeGDS;
        this.lettreComptoir = pLettreComptoir;
        this.majManuelle = pMajManuelle;
        this.officeID = pOfficeID;
        this.signatureMaj = pSignatureMaj;
        this.siteMaj = pSiteMaj;
    }

    /**
     *
     * @return agence
     */
    public AgenceDTO getAgence() {
        return this.agence;
    }

    /**
     *
     * @param pAgence agence value
     */
    public void setAgence(AgenceDTO pAgence) {
        this.agence = pAgence;
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
     * @return codeGDS
     */
    public String getCodeGDS() {
        return this.codeGDS;
    }

    /**
     *
     * @param pCodeGDS codeGDS value
     */
    public void setCodeGDS(String pCodeGDS) {
        this.codeGDS = pCodeGDS;
    }

    /**
     *
     * @return dateLastResa
     */
    public Date getDateLastResa() {
        return this.dateLastResa;
    }

    /**
     *
     * @param pDateLastResa dateLastResa value
     */
    public void setDateLastResa(Date pDateLastResa) {
        this.dateLastResa = pDateLastResa;
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
     * @return lettreComptoir
     */
    public String getLettreComptoir() {
        return this.lettreComptoir;
    }

    /**
     *
     * @param pLettreComptoir lettreComptoir value
     */
    public void setLettreComptoir(String pLettreComptoir) {
        this.lettreComptoir = pLettreComptoir;
    }

    /**
     *
     * @return majManuelle
     */
    public String getMajManuelle() {
        return this.majManuelle;
    }

    /**
     *
     * @param pMajManuelle majManuelle value
     */
    public void setMajManuelle(String pMajManuelle) {
        this.majManuelle = pMajManuelle;
    }

    /**
     *
     * @return officeID
     */
    public String getOfficeID() {
        return this.officeID;
    }

    /**
     *
     * @param pOfficeID officeID value
     */
    public void setOfficeID(String pOfficeID) {
        this.officeID = pOfficeID;
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
     * @return siteMaj
     */
    public String getSiteMaj() {
        return this.siteMaj;
    }

    /**
     *
     * @param pSiteMaj siteMaj value
     */
    public void setSiteMaj(String pSiteMaj) {
        this.siteMaj = pSiteMaj;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_0VRuD2k1EeGhB9497mGnHw) ENABLED START*/
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
        buffer.append("dateLastResa=").append(getDateLastResa());
        buffer.append(",");
        buffer.append("dateMaj=").append(getDateMaj());
        buffer.append(",");
        buffer.append("cle=").append(getCle());
        buffer.append(",");
        buffer.append("codeGDS=").append(getCodeGDS());
        buffer.append(",");
        buffer.append("lettreComptoir=").append(getLettreComptoir());
        buffer.append(",");
        buffer.append("majManuelle=").append(getMajManuelle());
        buffer.append(",");
        buffer.append("officeID=").append(getOfficeID());
        buffer.append(",");
        buffer.append("signatureMaj=").append(getSignatureMaj());
        buffer.append(",");
        buffer.append("siteMaj=").append(getSiteMaj());
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
		OfficeIDDTO other = (OfficeIDDTO) obj;
		if (cle == null) {
			if (other.cle != null)
				return false;
		} else if (!cle.equals(other.cle)) {
			return false;
		}
		return true;
	}

    /*PROTECTED REGION ID(_0VRuD2k1EeGhB9497mGnHw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
