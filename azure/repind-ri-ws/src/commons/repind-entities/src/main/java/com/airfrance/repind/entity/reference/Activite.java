package com.airfrance.repind.entity.reference;

/*PROTECTED REGION ID(_WsVtUOKVEeS4tOUM_Y9lGQ i) ENABLED START*/

// add not generated imports here

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : Activite.java</p>
 * BO Activite
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="ACTIVITE")
public class Activite implements Serializable {

/*PROTECTED REGION ID(serialUID _WsVtUOKVEeS4tOUM_Y9lGQ) ENABLED START*/
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
     * activite
     */
    @Id
    @Column(name="SACTIVITE", length=4)
    private String activite;
        
            
    /**
     * libelle
     */
    @Column(name="SLIBELLE", length=70)
    private String libelle;

	@Column(name = "SCODE", length = 2, nullable = false)
	private String code;

	@Column(name = "SPAYS")
	private String pays;
        
    /*PROTECTED REGION ID(_WsVtUOKVEeS4tOUM_Y9lGQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public Activite() {
    }
        
    /** 
     * full constructor
     * @param pActivite activite
     * @param pLibelle libelle
     */
    public Activite(String pActivite, String pLibelle) {
        this.activite = pActivite;
        this.libelle = pLibelle;
    }

	public Activite(String activite, String libelle, String code, String pays) {
		super();
		this.activite = activite;
		this.libelle = libelle;
		this.code = code;
		this.pays = pays;
	}

	/**
	 *
	 * @return activite
	 */
    public String getActivite() {
        return this.activite;
    }

    /**
     *
     * @param pActivite activite value
     */
    public void setActivite(String pActivite) {
        this.activite = pActivite;
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the pays
	 */
	public String getPays() {
		return pays;
	}

	/**
	 * @param pays
	 *            the pays to set
	 */
	public void setPays(String pays) {
		this.pays = pays;
	}

	/**
	 *
	 * @return object as string
	 */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_WsVtUOKVEeS4tOUM_Y9lGQ) ENABLED START*/
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
        buffer.append("activite=").append(getActivite());
        buffer.append(",");
        buffer.append("libelle=").append(getLibelle());
        buffer.append("]");
        return buffer.toString();
    }

     
    
    /*PROTECTED REGION ID(equals hash _WsVtUOKVEeS4tOUM_Y9lGQ) ENABLED START*/
    
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

    /*PROTECTED REGION ID(_WsVtUOKVEeS4tOUM_Y9lGQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
