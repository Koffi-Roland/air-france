package com.airfrance.repind.dto.ws;

/*PROTECTED REGION ID(_tQCHMNW_Eeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : MediaDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class MediaDTO implements Serializable {


        
    /**
     * media1
     */
    private String media1;
        
        
    /**
     * media2
     */
    private String media2;
        
        
    /**
     * media3
     */
    private String media3;
        
        
    /**
     * media4
     */
    private String media4;
        
        
    /**
     * media5
     */
    private String media5;
        

    /*PROTECTED REGION ID(_tQCHMNW_Eeef5oRB6XPNlw u var) ENABLED START*/
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
	    public MediaDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return media1
     */
    public String getMedia1() {
        return this.media1;
    }

    /**
     *
     * @param pMedia1 media1 value
     */
    public void setMedia1(String pMedia1) {
        this.media1 = pMedia1;
    }

    /**
     *
     * @return media2
     */
    public String getMedia2() {
        return this.media2;
    }

    /**
     *
     * @param pMedia2 media2 value
     */
    public void setMedia2(String pMedia2) {
        this.media2 = pMedia2;
    }

    /**
     *
     * @return media3
     */
    public String getMedia3() {
        return this.media3;
    }

    /**
     *
     * @param pMedia3 media3 value
     */
    public void setMedia3(String pMedia3) {
        this.media3 = pMedia3;
    }

    /**
     *
     * @return media4
     */
    public String getMedia4() {
        return this.media4;
    }

    /**
     *
     * @param pMedia4 media4 value
     */
    public void setMedia4(String pMedia4) {
        this.media4 = pMedia4;
    }

    /**
     *
     * @return media5
     */
    public String getMedia5() {
        return this.media5;
    }

    /**
     *
     * @param pMedia5 media5 value
     */
    public void setMedia5(String pMedia5) {
        this.media5 = pMedia5;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_tQCHMNW_Eeef5oRB6XPNlw) ENABLED START*/
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
        buffer.append("media1=").append(getMedia1());
        buffer.append(",");
        buffer.append("media2=").append(getMedia2());
        buffer.append(",");
        buffer.append("media3=").append(getMedia3());
        buffer.append(",");
        buffer.append("media4=").append(getMedia4());
        buffer.append(",");
        buffer.append("media5=").append(getMedia5());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_tQCHMNW_Eeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
