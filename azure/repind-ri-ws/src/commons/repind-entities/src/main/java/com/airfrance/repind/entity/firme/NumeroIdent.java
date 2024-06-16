package com.airfrance.repind.entity.firme;

/*PROTECTED REGION ID(_hCn90LbCEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : NumeroIdent.java</p>
 * BO NumeroIdent
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="NUMERO_IDENT")
public class NumeroIdent implements Serializable {

/*PROTECTED REGION ID(serialUID _hCn90LbCEeCrCZp8iGNNVw) ENABLED START*/
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_NUMERO_IDENT")
    @SequenceGenerator(name="ISEQ_NUMERO_IDENT", sequenceName = "ISEQ_NUMERO_IDENT", allocationSize = 1)
    @Column(name="IKEY", nullable=false)
    private Long key;
        
            
    /**
     * statut
     */
    @Column(name="SSTATUT", length=1, nullable=false)
    private String statut;
        
            
    /**
     * numero
     */
    @Column(name="SNUMERO", length=20)
    private String numero;
        
            
    /**
     * type
     */
    @Column(name="STYPE", length=2)
    private String type;
        
            
    /**
     * libelle
     */
    @Column(name="SLIBELLE", length=40)
    private String libelle;
        
            
    /**
     * dateOuverture
     */
    @Column(name="DDATE_OUVERTURE")
    private Date dateOuverture;
        
            
    /**
     * dateFermeture
     */
    @Column(name="DDATE_FERMETURE")
    private Date dateFermeture;
        
            
    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;
        
            
    /**
     * personneMorale
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="SGIN", nullable=false)
    @ForeignKey(name = "FK_PM_NUM")
    private PersonneMorale personneMorale;
        
    /*PROTECTED REGION ID(_hCn90LbCEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public NumeroIdent() {
    }
        
    /** 
     * full constructor
     * @param pKey key
     * @param pStatut statut
     * @param pNumero numero
     * @param pType type
     * @param pLibelle libelle
     * @param pDateOuverture dateOuverture
     * @param pDateFermeture dateFermeture
     * @param pDateModification dateModification
     */
    public NumeroIdent(Long pKey, String pStatut, String pNumero, String pType, String pLibelle, Date pDateOuverture, Date pDateFermeture, Date pDateModification) {
        this.key = pKey;
        this.statut = pStatut;
        this.numero = pNumero;
        this.type = pType;
        this.libelle = pLibelle;
        this.dateOuverture = pDateOuverture;
        this.dateFermeture = pDateFermeture;
        this.dateModification = pDateModification;
    }

    /**
     *
     * @return dateFermeture
     */
    public Date getDateFermeture() {
        return this.dateFermeture;
    }

    /**
     *
     * @param pDateFermeture dateFermeture value
     */
    public void setDateFermeture(Date pDateFermeture) {
        this.dateFermeture = pDateFermeture;
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
     * @return dateOuverture
     */
    public Date getDateOuverture() {
        return this.dateOuverture;
    }

    /**
     *
     * @param pDateOuverture dateOuverture value
     */
    public void setDateOuverture(Date pDateOuverture) {
        this.dateOuverture = pDateOuverture;
    }

    /**
     *
     * @return key
     */
    public Long getKey() {
        return this.key;
    }

    /**
     *
     * @param pKey key value
     */
    public void setKey(Long pKey) {
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
     * @return numero
     */
    public String getNumero() {
        return this.numero;
    }

    /**
     *
     * @param pNumero numero value
     */
    public void setNumero(String pNumero) {
        this.numero = pNumero;
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
        /*PROTECTED REGION ID(toString_hCn90LbCEeCrCZp8iGNNVw) ENABLED START*/
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
        buffer.append("statut=").append(getStatut());
        buffer.append(",");
        buffer.append("numero=").append(getNumero());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("libelle=").append(getLibelle());
        buffer.append(",");
        buffer.append("dateOuverture=").append(getDateOuverture());
        buffer.append(",");
        buffer.append("dateFermeture=").append(getDateFermeture());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _hCn90LbCEeCrCZp8iGNNVw) ENABLED START*/

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
        final NumeroIdent other = (NumeroIdent) obj;

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

    /*PROTECTED REGION ID(_hCn90LbCEeCrCZp8iGNNVw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
