package com.airfrance.repind.dto.profil;

/*PROTECTED REGION ID(_URgWwGkyEeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : ProfilDemarchargeDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ProfilDemarchargeDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -5741091179833549665L;


	/**
     * cle
     */
    private Integer cle;
        
        
    /**
     * demarchage
     */
    private String demarchage;
        
        
    /**
     * typeMailing
     */
    private String typeMailing;
        
        
    /**
     * vitrine
     */
    private String vitrine;
        
        
    /**
     * emplacement
     */
    private String emplacement;
        
        
    /**
     * dateTy
     */
    private Date dateTy;
        
        
    /**
     * dateRetraitTy
     */
    private Date dateRetraitTy;
        
        
    /**
     * jourOuverture
     */
    private String jourOuverture;
        
        
    /**
     * heureOuv
     */
    private String heureOuv;
        
        
    /**
     * heureFerm
     */
    private String heureFerm;
        
        
    /**
     * languePar
     */
    private String languePar;
        
        
    /**
     * langueEcr
     */
    private String langueEcr;
        
        
    /**
     * secteurGeo
     */
    private String secteurGeo;
        
        
    /**
     * profil
     */
    private Profil_mereDTO profil;
        

    /*PROTECTED REGION ID(_URgWwGkyEeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ProfilDemarchargeDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pCle cle
     * @param pDemarchage demarchage
     * @param pTypeMailing typeMailing
     * @param pVitrine vitrine
     * @param pEmplacement emplacement
     * @param pDateTy dateTy
     * @param pDateRetraitTy dateRetraitTy
     * @param pJourOuverture jourOuverture
     * @param pHeureOuv heureOuv
     * @param pHeureFerm heureFerm
     * @param pLanguePar languePar
     * @param pLangueEcr langueEcr
     * @param pSecteurGeo secteurGeo
     */
    public ProfilDemarchargeDTO(Integer pCle, String pDemarchage, String pTypeMailing, String pVitrine, String pEmplacement, Date pDateTy, Date pDateRetraitTy, String pJourOuverture, String pHeureOuv, String pHeureFerm, String pLanguePar, String pLangueEcr, String pSecteurGeo) {
        this.cle = pCle;
        this.demarchage = pDemarchage;
        this.typeMailing = pTypeMailing;
        this.vitrine = pVitrine;
        this.emplacement = pEmplacement;
        this.dateTy = pDateTy;
        this.dateRetraitTy = pDateRetraitTy;
        this.jourOuverture = pJourOuverture;
        this.heureOuv = pHeureOuv;
        this.heureFerm = pHeureFerm;
        this.languePar = pLanguePar;
        this.langueEcr = pLangueEcr;
        this.secteurGeo = pSecteurGeo;
    }

    /**
     *
     * @return cle
     */
    public Integer getCle() {
        return this.cle;
    }

    /**
     *
     * @param pCle cle value
     */
    public void setCle(Integer pCle) {
        this.cle = pCle;
    }

    /**
     *
     * @return dateRetraitTy
     */
    public Date getDateRetraitTy() {
        return this.dateRetraitTy;
    }

    /**
     *
     * @param pDateRetraitTy dateRetraitTy value
     */
    public void setDateRetraitTy(Date pDateRetraitTy) {
        this.dateRetraitTy = pDateRetraitTy;
    }

    /**
     *
     * @return dateTy
     */
    public Date getDateTy() {
        return this.dateTy;
    }

    /**
     *
     * @param pDateTy dateTy value
     */
    public void setDateTy(Date pDateTy) {
        this.dateTy = pDateTy;
    }

    /**
     *
     * @return demarchage
     */
    public String getDemarchage() {
        return this.demarchage;
    }

    /**
     *
     * @param pDemarchage demarchage value
     */
    public void setDemarchage(String pDemarchage) {
        this.demarchage = pDemarchage;
    }

    /**
     *
     * @return emplacement
     */
    public String getEmplacement() {
        return this.emplacement;
    }

    /**
     *
     * @param pEmplacement emplacement value
     */
    public void setEmplacement(String pEmplacement) {
        this.emplacement = pEmplacement;
    }

    /**
     *
     * @return heureFerm
     */
    public String getHeureFerm() {
        return this.heureFerm;
    }

    /**
     *
     * @param pHeureFerm heureFerm value
     */
    public void setHeureFerm(String pHeureFerm) {
        this.heureFerm = pHeureFerm;
    }

    /**
     *
     * @return heureOuv
     */
    public String getHeureOuv() {
        return this.heureOuv;
    }

    /**
     *
     * @param pHeureOuv heureOuv value
     */
    public void setHeureOuv(String pHeureOuv) {
        this.heureOuv = pHeureOuv;
    }

    /**
     *
     * @return jourOuverture
     */
    public String getJourOuverture() {
        return this.jourOuverture;
    }

    /**
     *
     * @param pJourOuverture jourOuverture value
     */
    public void setJourOuverture(String pJourOuverture) {
        this.jourOuverture = pJourOuverture;
    }

    /**
     *
     * @return langueEcr
     */
    public String getLangueEcr() {
        return this.langueEcr;
    }

    /**
     *
     * @param pLangueEcr langueEcr value
     */
    public void setLangueEcr(String pLangueEcr) {
        this.langueEcr = pLangueEcr;
    }

    /**
     *
     * @return languePar
     */
    public String getLanguePar() {
        return this.languePar;
    }

    /**
     *
     * @param pLanguePar languePar value
     */
    public void setLanguePar(String pLanguePar) {
        this.languePar = pLanguePar;
    }

    /**
     *
     * @return profil
     */
    public Profil_mereDTO getProfil() {
        return this.profil;
    }

    /**
     *
     * @param pProfil profil value
     */
    public void setProfil(Profil_mereDTO pProfil) {
        this.profil = pProfil;
    }

    /**
     *
     * @return secteurGeo
     */
    public String getSecteurGeo() {
        return this.secteurGeo;
    }

    /**
     *
     * @param pSecteurGeo secteurGeo value
     */
    public void setSecteurGeo(String pSecteurGeo) {
        this.secteurGeo = pSecteurGeo;
    }

    /**
     *
     * @return typeMailing
     */
    public String getTypeMailing() {
        return this.typeMailing;
    }

    /**
     *
     * @param pTypeMailing typeMailing value
     */
    public void setTypeMailing(String pTypeMailing) {
        this.typeMailing = pTypeMailing;
    }

    /**
     *
     * @return vitrine
     */
    public String getVitrine() {
        return this.vitrine;
    }

    /**
     *
     * @param pVitrine vitrine value
     */
    public void setVitrine(String pVitrine) {
        this.vitrine = pVitrine;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_URgWwGkyEeGhB9497mGnHw) ENABLED START*/
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
        buffer.append("demarchage=").append(getDemarchage());
        buffer.append(",");
        buffer.append("typeMailing=").append(getTypeMailing());
        buffer.append(",");
        buffer.append("vitrine=").append(getVitrine());
        buffer.append(",");
        buffer.append("emplacement=").append(getEmplacement());
        buffer.append(",");
        buffer.append("dateTy=").append(getDateTy());
        buffer.append(",");
        buffer.append("dateRetraitTy=").append(getDateRetraitTy());
        buffer.append(",");
        buffer.append("jourOuverture=").append(getJourOuverture());
        buffer.append(",");
        buffer.append("heureOuv=").append(getHeureOuv());
        buffer.append(",");
        buffer.append("heureFerm=").append(getHeureFerm());
        buffer.append(",");
        buffer.append("languePar=").append(getLanguePar());
        buffer.append(",");
        buffer.append("langueEcr=").append(getLangueEcr());
        buffer.append(",");
        buffer.append("secteurGeo=").append(getSecteurGeo());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_URgWwGkyEeGhB9497mGnHw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
