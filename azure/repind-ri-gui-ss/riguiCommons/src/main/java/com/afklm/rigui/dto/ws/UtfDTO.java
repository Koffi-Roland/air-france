package com.afklm.rigui.dto.ws;

/*PROTECTED REGION ID(_zjqsoNW4Eeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : UtfDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class UtfDTO implements Serializable {


        
    /**
     * id
     */
    private Long id;
        
        
    /**
     * type
     */
    private String type;
    
    
    private UtfDatasDTO utfDatasDTO;
        

    /*PROTECTED REGION ID(_zjqsoNW4Eeef5oRB6XPNlw u var) ENABLED START*/
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
	    public UtfDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     *
     * @param pId id value
     */
    public void setId(Long pId) {
        this.id = pId;
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
	 * @return the utfDatasDTO
	 */
	public UtfDatasDTO getUtfDatasDTO() {
		return utfDatasDTO;
	}

	/**
	 * @param utfDatasDTO the utfDatasDTO to set
	 */
	public void setUtfDatasDTO(UtfDatasDTO utfDatasDTO) {
		this.utfDatasDTO = utfDatasDTO;
	}

	/**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_zjqsoNW4Eeef5oRB6XPNlw) ENABLED START*/
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
        buffer.append("id=").append(getId());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_zjqsoNW4Eeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
