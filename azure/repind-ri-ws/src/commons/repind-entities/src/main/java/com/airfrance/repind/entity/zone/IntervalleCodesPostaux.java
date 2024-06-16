package com.airfrance.repind.entity.zone;

/*PROTECTED REGION ID(_1fvY4OHAEeS79pPzHY2rFw i) ENABLED START*/

// add not generated imports here

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/*PROTECTED REGION END*/


/**
 * <p>Title : IntervalleCodesPostaux.java</p>
 * BO IntervalleCodesPostaux
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="INT_CODE_POSTAUX")
public class IntervalleCodesPostaux implements Serializable {

/*PROTECTED REGION ID(serialUID _1fvY4OHAEeS79pPzHY2rFw) ENABLED START*/
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_INT_CP")
    @SequenceGenerator(name="ISEQ_INT_CP", sequenceName = "ISEQ_INT_CP", allocationSize = 1)
    @Column(name="ICLE", length=10)
    private Long cle;
        
            
    /**
     * codePostalDebut
     */
    @Column(name="ICODE_POST_DEB", length=5)
    private String codePostalDebut;
        
            
    /**
     * codePostalFin
     */
    @Column(name="ICODE_POST_FIN")
    private String codePostalFin;
        
            
    /**
     * codePays
     */
    @Column(name="SCODE_PAYS", length=2)
    private String codePays;
        
            
    /**
     * usage
     */
    @Column(name="SUSAGE", length=2)
    private String usage;
        
            
    /**
     * liensIntCpZd
     */
    // 1 <-> * 
    @OneToMany(mappedBy="intervalleCodesPostaux")
    private Set<LienIntCpZd> liensIntCpZd;
        
    /*PROTECTED REGION ID(_1fvY4OHAEeS79pPzHY2rFw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public IntervalleCodesPostaux() {
    }
        
    /** 
     * full constructor
     * @param pCle cle
     * @param pCodePostalDebut codePostalDebut
     * @param pCodePostalFin codePostalFin
     * @param pCodePays codePays
     * @param pUsage usage
     */
    public IntervalleCodesPostaux(Long pCle, String pCodePostalDebut, String pCodePostalFin, String pCodePays, String pUsage) {
        this.cle = pCle;
        this.codePostalDebut = pCodePostalDebut;
        this.codePostalFin = pCodePostalFin;
        this.codePays = pCodePays;
        this.usage = pUsage;
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
     * @return codePostalDebut
     */
    public String getCodePostalDebut() {
        return this.codePostalDebut;
    }

    /**
     *
     * @param pCodePostalDebut codePostalDebut value
     */
    public void setCodePostalDebut(String pCodePostalDebut) {
        this.codePostalDebut = pCodePostalDebut;
    }

    /**
     *
     * @return codePostalFin
     */
    public String getCodePostalFin() {
        return this.codePostalFin;
    }

    /**
     *
     * @param pCodePostalFin codePostalFin value
     */
    public void setCodePostalFin(String pCodePostalFin) {
        this.codePostalFin = pCodePostalFin;
    }

    /**
     *
     * @return liensIntCpZd
     */
    public Set<LienIntCpZd> getLiensIntCpZd() {
        return this.liensIntCpZd;
    }

    /**
     *
     * @param pLiensIntCpZd liensIntCpZd value
     */
    public void setLiensIntCpZd(Set<LienIntCpZd> pLiensIntCpZd) {
        this.liensIntCpZd = pLiensIntCpZd;
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
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_1fvY4OHAEeS79pPzHY2rFw) ENABLED START*/
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
        buffer.append("codePostalDebut=").append(getCodePostalDebut());
        buffer.append(",");
        buffer.append("codePostalFin=").append(getCodePostalFin());
        buffer.append(",");
        buffer.append("codePays=").append(getCodePays());
        buffer.append(",");
        buffer.append("usage=").append(getUsage());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _1fvY4OHAEeS79pPzHY2rFw) ENABLED START*/
    
    /**
     * {@inheritDoc}
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        //TODO Customize here if necessary
        return hashCodeImpl();
    }

    /**
     * {@inheritDoc}
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        //TODO Customize here if necessary
        return equalsImpl(obj);
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

    /*PROTECTED REGION ID(_1fvY4OHAEeS79pPzHY2rFw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
