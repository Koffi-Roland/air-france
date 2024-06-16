package com.airfrance.repind.entity.reference;

/*PROTECTED REGION ID(_ItMlIHtNEeaSlc6Hkl1VQg i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefPreferenceKey.java</p>
 * BO RefPreferenceKey
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="REF_PREFERENCE_DATA_KEY")
public class RefPreferenceKey implements Serializable {

/*PROTECTED REGION ID(serialUID _ItMlIHtNEeaSlc6Hkl1VQg) ENABLED START*/
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
     * codeKey
     */
    @Id
    @Column(name="SCODE", length=12)
    private String codeKey;
        
            
    /**
     * libelle
     */
    @Column(name="SLIBELLE_FR", length=255)
    private String libelle;
        
            
    /**
     * libelleEng
     */
    @Column(name="SLIBELLE_EN", length=255)
    private String libelleEng;
        
    /*PROTECTED REGION ID(_ItMlIHtNEeaSlc6Hkl1VQg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public RefPreferenceKey() {
    	//empty constructor
    }
        
    /** 
     * full constructor
     * @param pCodeKey codeKey
     * @param pLibelle libelle
     * @param pLibelleEng libelleEng
     */
    public RefPreferenceKey(String pCodeKey, String pLibelle, String pLibelleEng) {
        this.codeKey = pCodeKey;
        this.libelle = pLibelle;
        this.libelleEng = pLibelleEng;
    }

    /**
     *
     * @return codeKey
     */
    public String getCodeKey() {
        return this.codeKey;
    }

    /**
     *
     * @param pCodeKey codeKey value
     */
    public void setCodeKey(String pCodeKey) {
        this.codeKey = pCodeKey;
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
     * @return libelleEng
     */
    public String getLibelleEng() {
        return this.libelleEng;
    }

    /**
     *
     * @param pLibelleEng libelleEng value
     */
    public void setLibelleEng(String pLibelleEng) {
        this.libelleEng = pLibelleEng;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_ItMlIHtNEeaSlc6Hkl1VQg) ENABLED START*/
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
        buffer.append("codeKey=").append(getCodeKey());
        buffer.append(",");
        buffer.append("libelle=").append(getLibelle());
        buffer.append(",");
        buffer.append("libelleEng=").append(getLibelleEng());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _ItMlIHtNEeaSlc6Hkl1VQg) ENABLED START*/
    
    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        //TODO Customize here if necessary
        return hashCodeImpl();
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
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

    /*PROTECTED REGION ID(_ItMlIHtNEeaSlc6Hkl1VQg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
