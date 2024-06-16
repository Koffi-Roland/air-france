package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_iy51wAThEee_Y7_I5loOBA i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : RefDetailsKeyDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class RefDetailsKeyDTO implements Serializable {


        
    /**
     * refDetailsKeyId
     */
    private Integer refDetailsKeyId;
        
        
    /**
     * code
     */
    private String code;
        
        
    /**
     * labelFR
     */
    private String labelFR;
        
        
    /**
     * labelEN
     */
    private String labelEN;
        
        
    /**
     * detailsKeyID
     */
    private Integer detailsKeyID;
        
        
    /**
     * typeEvent
     */
    private RefTypeEventDTO typeEvent;
        

    /*PROTECTED REGION ID(_iy51wAThEee_Y7_I5loOBA u var) ENABLED START*/
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
	    public RefDetailsKeyDTO() {
	    //empty constructor
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
     * @return detailsKeyID
     */
    public Integer getDetailsKeyID() {
        return this.detailsKeyID;
    }

    /**
     *
     * @param pDetailsKeyID detailsKeyID value
     */
    public void setDetailsKeyID(Integer pDetailsKeyID) {
        this.detailsKeyID = pDetailsKeyID;
    }

    /**
     *
     * @return labelEN
     */
    public String getLabelEN() {
        return this.labelEN;
    }

    /**
     *
     * @param pLabelEN labelEN value
     */
    public void setLabelEN(String pLabelEN) {
        this.labelEN = pLabelEN;
    }

    /**
     *
     * @return labelFR
     */
    public String getLabelFR() {
        return this.labelFR;
    }

    /**
     *
     * @param pLabelFR labelFR value
     */
    public void setLabelFR(String pLabelFR) {
        this.labelFR = pLabelFR;
    }

    /**
     *
     * @return refDetailsKeyId
     */
    public Integer getRefDetailsKeyId() {
        return this.refDetailsKeyId;
    }

    /**
     *
     * @param pRefDetailsKeyId refDetailsKeyId value
     */
    public void setRefDetailsKeyId(Integer pRefDetailsKeyId) {
        this.refDetailsKeyId = pRefDetailsKeyId;
    }

    /**
     *
     * @return typeEvent
     */
    public RefTypeEventDTO getTypeEvent() {
        return this.typeEvent;
    }

    /**
     *
     * @param pTypeEvent typeEvent value
     */
    public void setTypeEvent(RefTypeEventDTO pTypeEvent) {
        this.typeEvent = pTypeEvent;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_iy51wAThEee_Y7_I5loOBA) ENABLED START*/
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
        buffer.append("refDetailsKeyId=").append(getRefDetailsKeyId());
        buffer.append(",");
        buffer.append("code=").append(getCode());
        buffer.append(",");
        buffer.append("labelFR=").append(getLabelFR());
        buffer.append(",");
        buffer.append("labelEN=").append(getLabelEN());
        buffer.append(",");
        buffer.append("detailsKeyID=").append(getDetailsKeyID());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_iy51wAThEee_Y7_I5loOBA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
