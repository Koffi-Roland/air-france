package com.airfrance.repind.dto.role;

/*PROTECTED REGION ID(_YiSLILd7EeSM_NEE6QydtQ i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : ContratTcSeqDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ContratTcSeqDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -2428744826609866415L;


	/**
     * numeroTc
     */
    private String numeroTc;
        
        
    /**
     * codeType
     */
    private String codeType;
        
        
    /**
     * nomTc
     */
    private String nomTc;
        
        
    /**
     * dateDebut
     */
    private Date dateDebut;
        
        
    /**
     * dateFin
     */
    private Date dateFin;
        

        

    
    /** 
     * default constructor 
     */
    public ContratTcSeqDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pNumeroTc numeroTc
     * @param pCodeType codeType
     * @param pNomTc nomTc
     * @param pDateDebut dateDebut
     * @param pDateFin dateFin
     */
    public ContratTcSeqDTO(String pNumeroTc, String pCodeType, String pNomTc, Date pDateDebut, Date pDateFin) {
        this.numeroTc = pNumeroTc;
        this.codeType = pCodeType;
        this.nomTc = pNomTc;
        this.dateDebut = pDateDebut;
        this.dateFin = pDateFin;
    }

    /**
     *
     * @return codeType
     */
    public String getCodeType() {
        return this.codeType;
    }

    /**
     *
     * @param pCodeType codeType value
     */
    public void setCodeType(String pCodeType) {
        this.codeType = pCodeType;
    }

    /**
     *
     * @return dateDebut
     */
    public Date getDateDebut() {
        return this.dateDebut;
    }

    /**
     *
     * @param pDateDebut dateDebut value
     */
    public void setDateDebut(Date pDateDebut) {
        this.dateDebut = pDateDebut;
    }

    /**
     *
     * @return dateFin
     */
    public Date getDateFin() {
        return this.dateFin;
    }

    /**
     *
     * @param pDateFin dateFin value
     */
    public void setDateFin(Date pDateFin) {
        this.dateFin = pDateFin;
    }

    /**
     *
     * @return nomTc
     */
    public String getNomTc() {
        return this.nomTc;
    }

    /**
     *
     * @param pNomTc nomTc value
     */
    public void setNomTc(String pNomTc) {
        this.nomTc = pNomTc;
    }

    /**
     *
     * @return numeroTc
     */
    public String getNumeroTc() {
        return this.numeroTc;
    }

    /**
     *
     * @param pNumeroTc numeroTc value
     */
    public void setNumeroTc(String pNumeroTc) {
        this.numeroTc = pNumeroTc;
    }



    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_YiSLILd7EeSM_NEE6QydtQ) ENABLED START*/
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
        buffer.append("numeroTc=").append(getNumeroTc());
        buffer.append(",");
        buffer.append("codeType=").append(getCodeType());
        buffer.append(",");
        buffer.append("nomTc=").append(getNomTc());
        buffer.append(",");
        buffer.append("dateDebut=").append(getDateDebut());
        buffer.append(",");
        buffer.append("dateFin=").append(getDateFin());
        buffer.append("]");
        return buffer.toString();
    }


}
