package com.airfrance.repind.entity.firme;

/*PROTECTED REGION ID(_ULViULbCEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : Chiffre.java</p>
 * BO Chiffre
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="CHIFFRE")
public class Chiffre implements Serializable {

/*PROTECTED REGION ID(serialUID _ULViULbCEeCrCZp8iGNNVw) ENABLED START*/
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
     * key
     */
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_CHIFFRE")
    @SequenceGenerator(name="ISEQ_CHIFFRE", sequenceName = "ISEQ_CHIFFRE", allocationSize = 1)
    @Column(name="IKEY", nullable=false)
    private Integer key;
        
            
    /**
     * type
     */
    @Column(name="STYPE", length=3, nullable=false)
    private String type;
        
            
    /**
     * statut
     */
    @Column(name="SSTATUT", length=1, nullable=false)
    private String statut;
        
            
    /**
     * montant
     */
    @Column(name="NMONTANT", length=12)
    private BigInteger montant;
        
            
    /**
     * monnaie
     */
    @Column(name="SMONNAIE", length=3)
    private String monnaie;
        
            
    /**
     * libelle
     */
    @Column(name="SLIBELLE", length=25)
    private String libelle;
        
            
    /**
     * dateFin
     */
    @Column(name="DDATE_FIN")
    private Date dateFin;
        
            
    /**
     * dateDebut
     */
    @Column(name="DDATE_DEBUT")
    private Date dateDebut;
        
            
    /**
     * dateMaj
     */
    @Column(name="DDATE_MAJ")
    private Date dateMaj;
        
            
    /**
     * personneMorale
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="SGIN", nullable=false)
    @ForeignKey(name = "FK_PM_CH")
    private PersonneMorale personneMorale;
        
    /*PROTECTED REGION ID(_ULViULbCEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public Chiffre() {
    }
        
    /** 
     * full constructor
     * @param pKey key
     * @param pType type
     * @param pStatut statut
     * @param pMontant montant
     * @param pMonnaie monnaie
     * @param pLibelle libelle
     * @param pDateFin dateFin
     * @param pDateDebut dateDebut
     * @param pDateMaj dateMaj
     */
    public Chiffre(Integer pKey, String pType, String pStatut, BigInteger pMontant, String pMonnaie, String pLibelle, Date pDateFin, Date pDateDebut, Date pDateMaj) {
        this.key = pKey;
        this.type = pType;
        this.statut = pStatut;
        this.montant = pMontant;
        this.monnaie = pMonnaie;
        this.libelle = pLibelle;
        this.dateFin = pDateFin;
        this.dateDebut = pDateDebut;
        this.dateMaj = pDateMaj;
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
     * @return key
     */
    public Integer getKey() {
        return this.key;
    }

    /**
     *
     * @param pKey key value
     */
    public void setKey(Integer pKey) {
        this.key = pKey;
    }

    /**
     *
     * @return libelle
     */
    public String getLibelle() {
        return this.libelle;
    }

    /**
     *
     * @param pLibelle libelle value
     */
    public void setLibelle(String pLibelle) {
        this.libelle = pLibelle;
    }

    /**
     *
     * @return monnaie
     */
    public String getMonnaie() {
        return this.monnaie;
    }

    /**
     *
     * @param pMonnaie monnaie value
     */
    public void setMonnaie(String pMonnaie) {
        this.monnaie = pMonnaie;
    }

    /**
     *
     * @return montant
     */
    public BigInteger getMontant() {
        return this.montant;
    }

    /**
     *
     * @param pMontant montant value
     */
    public void setMontant(BigInteger pMontant) {
        this.montant = pMontant;
    }

    /**
     *
     * @return personneMorale
     */
    public PersonneMorale getPersonneMorale() {
        return this.personneMorale;
    }

    /**
     *
     * @param pPersonneMorale personneMorale value
     */
    public void setPersonneMorale(PersonneMorale pPersonneMorale) {
        this.personneMorale = pPersonneMorale;
    }

    /**
     *
     * @return statut
     */
    public String getStatut() {
        return this.statut;
    }

    /**
     *
     * @param pStatut statut value
     */
    public void setStatut(String pStatut) {
        this.statut = pStatut;
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     * @param pType type value
     */
    public void setType(String pType) {
        this.type = pType;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_ULViULbCEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("key=").append(getKey());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("statut=").append(getStatut());
        buffer.append(",");
        buffer.append("montant=").append(getMontant());
        buffer.append(",");
        buffer.append("monnaie=").append(getMonnaie());
        buffer.append(",");
        buffer.append("libelle=").append(getLibelle());
        buffer.append(",");
        buffer.append("dateFin=").append(getDateFin());
        buffer.append(",");
        buffer.append("dateDebut=").append(getDateDebut());
        buffer.append(",");
        buffer.append("dateMaj=").append(getDateMaj());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _ULViULbCEeCrCZp8iGNNVw) ENABLED START*/

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
        final Chiffre other = (Chiffre) obj;

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

    /*PROTECTED REGION ID(_ULViULbCEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
