package com.airfrance.repind.entity.reference;

/*PROTECTED REGION ID(_RRJEIHHlEeGuQpk3rMEZsA i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/


/**
 * <p>Title : Reseau.java</p>
 * BO Reseau
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="RESEAU")
public class Reseau implements Serializable {

/*PROTECTED REGION ID(serialUID _RRJEIHHlEeGuQpk3rMEZsA) ENABLED START*/
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
     * code
     */
    @Id
    @Column(name="SCODE")
    private String code;
        
            
    /**
     * nom
     */
    @Column(name="SNOM", nullable=false)
    private String nom;
        
            
    /**
     * type
     */
    @Column(name="STYPE", nullable=false)
    private String type;
        
            
    /**
     * pays
     */
    @Column(name="SPAYS")
    private String pays;
        
            
    /**
     * nature
     */
    @Column(name="SNATURE")
    private String nature;
        
            
    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;
        
            
    /**
     * dateFermeture
     */
    @Column(name="DDATE_FERMETURE")
    private Date dateFermeture;

        
            
    /**
     * enfants
     */
    // 1 <-> * 
    @OneToMany(mappedBy="parent")
    private Set<Reseau> enfants;
        
            
    /**
     * parent
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="SCODE_PERE", nullable=true)
    @ForeignKey(name = "FK_HIE_RES")
    private Reseau parent;
        
    /*PROTECTED REGION ID(_RRJEIHHlEeGuQpk3rMEZsA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public Reseau() {
    }
        
    /** 
     * full constructor
     * @param pCode code
     * @param pNom nom
     * @param pType type
     * @param pPays pays
     * @param pNature nature
     * @param pDateCreation dateCreation
     * @param pDateFermeture dateFermeture
     */
    public Reseau(String pCode, String pNom, String pType, String pPays, String pNature, Date pDateCreation, Date pDateFermeture) {
        this.code = pCode;
        this.nom = pNom;
        this.type = pType;
        this.pays = pPays;
        this.nature = pNature;
        this.dateCreation = pDateCreation;
        this.dateFermeture = pDateFermeture;
    }



    /**
     *
     * @return code
     */
    public String getCode() {
        return this.code;
    }

    /**
     *
     * @param pCode code value
     */
    public void setCode(String pCode) {
        this.code = pCode;
    }

    /**
     *
     * @return dateCreation
     */
    public Date getDateCreation() {
        return this.dateCreation;
    }

    /**
     *
     * @param pDateCreation dateCreation value
     */
    public void setDateCreation(Date pDateCreation) {
        this.dateCreation = pDateCreation;
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
     * @return enfants
     */
    public Set<Reseau> getEnfants() {
        return this.enfants;
    }

    /**
     *
     * @param pEnfants enfants value
     */
    public void setEnfants(Set<Reseau> pEnfants) {
        this.enfants = pEnfants;
    }

    /**
     *
     * @return nature
     */
    public String getNature() {
        return this.nature;
    }

    /**
     *
     * @param pNature nature value
     */
    public void setNature(String pNature) {
        this.nature = pNature;
    }

    /**
     *
     * @return nom
     */
    public String getNom() {
        return this.nom;
    }

    /**
     *
     * @param pNom nom value
     */
    public void setNom(String pNom) {
        this.nom = pNom;
    }

    /**
     *
     * @return parent
     */
    public Reseau getParent() {
        return this.parent;
    }

    /**
     *
     * @param pParent parent value
     */
    public void setParent(Reseau pParent) {
        this.parent = pParent;
    }

    /**
     *
     * @return pays
     */
    public String getPays() {
        return this.pays;
    }

    /**
     *
     * @param pPays pays value
     */
    public void setPays(String pPays) {
        this.pays = pPays;
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
        /*PROTECTED REGION ID(toString_RRJEIHHlEeGuQpk3rMEZsA) ENABLED START*/
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
        buffer.append("code=").append(getCode());
        buffer.append(",");
        buffer.append("nom=").append(getNom());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append(",");
        buffer.append("pays=").append(getPays());
        buffer.append(",");
        buffer.append("nature=").append(getNature());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("dateFermeture=").append(getDateFermeture());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _RRJEIHHlEeGuQpk3rMEZsA) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
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
        final Reseau other = (Reseau) obj;

        // TODO: writes or generates equals method

        return super.equals(other);
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
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

    /*PROTECTED REGION ID(_RRJEIHHlEeGuQpk3rMEZsA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
