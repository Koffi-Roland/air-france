package com.afklm.rigui.entity.profil;

/*PROTECTED REGION ID(_9X5zsGkvEeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : ProfilDemarchage.java</p>
 * BO ProfilDemarchage
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="PR_DEMARCHAGE")
public class ProfilDemarchage implements Serializable {

    /*PROTECTED REGION ID(serialUID _9X5zsGkvEeGhB9497mGnHw) ENABLED START*/
    /**
     * Determines if a de-serialized file is compatible with this class.
     *
     * Maintainers must change this value if and only if the new version
     * of this class is not compatible with old versions. See Sun docs
     * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
     * /serialization/spec/class.html#4100> details. </a>
     *
     * Not necessary to include in first version of the class, but
     * included here as a reminder of its importance.
     */
    private static final long serialVersionUID = 1L;
    /*PROTECTED REGION END*/


    /**
     * cle
     */
    @Id
    @GeneratedValue(generator = "foreignGenerator")
    @GenericGenerator(name = "foreignGenerator", strategy = "foreign", parameters = { @Parameter(name = "property", value = "profil") })
    @Column(name="ICLE_PRF", length=10)
    private Integer cle;


    /**
     * demarchage
     */
    @Column(name="SDEMARCHAGE", length=1)
    private String demarchage;


    /**
     * typeMailing
     */
    @Column(name="STYPE_MAILING", length=2)
    private String typeMailing;


    /**
     * vitrine
     */
    @Column(name="SVITRINE", length=1)
    private String vitrine;


    /**
     * emplacement
     */
    @Column(name="SEMPLACEMENT", length=32)
    private String emplacement;


    /**
     * dateTy
     */
    @Column(name="DDATE_TY")
    private Date dateTy;


    /**
     * dateRetraitTy
     */
    @Column(name="DDATE_RETRAIT_TY")
    private Date dateRetraitTy;


    /**
     * jourOuverture
     */
    @Column(name="SJOUR_OUVERTURE", length=7)
    private String jourOuverture;


    /**
     * heureOuv
     */
    @Column(name="SHEURE_OUV", length=5)
    private String heureOuv;


    /**
     * heureFerm
     */
    @Column(name="SHEURE_FERM", length=5)
    private String heureFerm;


    /**
     * languePar
     */
    @Column(name="SLANGUE_PAR", length=2)
    private String languePar;


    /**
     * langueEcr
     */
    @Column(name="SLANGUE_ECR", length=2)
    private String langueEcr;


    /**
     * secteurGeo
     */
    @Column(name="SSECTEUR_GEO", length=4)
    private String secteurGeo;


    /**
     * profil
     */
    // 1 <-> 1
    @OneToOne(mappedBy="profilDemarchage")
    private Profil_mere profil;

    /*PROTECTED REGION ID(_9X5zsGkvEeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    /**
     * default constructor 
     */
    public ProfilDemarchage() {
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
    public ProfilDemarchage(Integer pCle, String pDemarchage, String pTypeMailing, String pVitrine, String pEmplacement, Date pDateTy, Date pDateRetraitTy, String pJourOuverture, String pHeureOuv, String pHeureFerm, String pLanguePar, String pLangueEcr, String pSecteurGeo) {
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
    public Profil_mere getProfil() {
        return this.profil;
    }

    /**
     *
     * @param pProfil profil value
     */
    public void setProfil(Profil_mere pProfil) {
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
        /*PROTECTED REGION ID(toString_9X5zsGkvEeGhB9497mGnHw) ENABLED START*/
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



    /*PROTECTED REGION ID(equals hash _9X5zsGkvEeGhB9497mGnHw) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProfilDemarchage other = (ProfilDemarchage) obj;

        // TODO: writes or generates equals method

        return super.equals(other);
    }

    /**
     * {@inheritDoc}
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        // TODO: writes or generates hashcode method

        return result;
    }

    /*PROTECTED REGION END*/

    /**
     * Generated implementation method for hashCode
     * You can stop calling it in the hashCode() generated in protected region if necessary
     * @return hashcode
     */
    private int hashCodeImpl() {
        return super.hashCode();
    }

    /**
     * Generated implementation method for equals
     * You can stop calling it in the equals() generated in protected region if necessary
     * @return if param equals the current object
     * @param obj Object to compare with current
     */
    private boolean equalsImpl(Object obj) {
        return super.equals(obj);
    }

    /*PROTECTED REGION ID(_9X5zsGkvEeGhB9497mGnHw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
