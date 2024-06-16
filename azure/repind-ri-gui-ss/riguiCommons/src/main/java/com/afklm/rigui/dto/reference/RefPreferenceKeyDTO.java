package com.afklm.rigui.dto.reference;

/*PROTECTED REGION ID(_vNfwQHtPEeaSlc6Hkl1VQg i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : RefPreferenceKeyDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class RefPreferenceKeyDTO implements Serializable {


        
    /**
     * codeKey
     */
    private String codeKey;
        
        
    /**
     * libelle
     */
    private String libelle;
        
        
    /**
     * libelleEng
     */
    private String libelleEng;
        

    /*PROTECTED REGION ID(_vNfwQHtPEeaSlc6Hkl1VQg u var) ENABLED START*/
    // add your custom variables here if necessary
    
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
	     * default constructor 
	     */
	    public RefPreferenceKeyDTO() {
	    //empty constructor
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
        /*PROTECTED REGION ID(toString_vNfwQHtPEeaSlc6Hkl1VQg) ENABLED START*/
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

    /*PROTECTED REGION ID(_vNfwQHtPEeaSlc6Hkl1VQg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}