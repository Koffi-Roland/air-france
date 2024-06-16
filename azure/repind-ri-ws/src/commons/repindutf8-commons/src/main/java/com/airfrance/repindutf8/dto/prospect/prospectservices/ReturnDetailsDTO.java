package com.airfrance.repindutf8.dto.prospect.prospectservices;

/*PROTECTED REGION ID(_f8s7EIyUEeKPttgn1pql1A i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : ReturnDetailsDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ReturnDetailsDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -6351986274957125072L;


	/**
     * detailedCode
     */
    private String detailedCode;
        
        
    /**
     * labelCode
     */
    private String labelCode;
        

    /*PROTECTED REGION ID(_f8s7EIyUEeKPttgn1pql1A u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ReturnDetailsDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pDetailedCode detailedCode
     * @param pLabelCode labelCode
     */
    public ReturnDetailsDTO(String pDetailedCode, String pLabelCode) {
        this.detailedCode = pDetailedCode;
        this.labelCode = pLabelCode;
    }

    /**
     *
     * @return detailedCode
     */
    public String getDetailedCode() {
        return this.detailedCode;
    }

    /**
     *
     * @param pDetailedCode detailedCode value
     */
    public void setDetailedCode(String pDetailedCode) {
        this.detailedCode = pDetailedCode;
    }

    /**
     *
     * @return labelCode
     */
    public String getLabelCode() {
        return this.labelCode;
    }

    /**
     *
     * @param pLabelCode labelCode value
     */
    public void setLabelCode(String pLabelCode) {
        this.labelCode = pLabelCode;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_f8s7EIyUEeKPttgn1pql1A) ENABLED START*/
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
        buffer.append("detailedCode=").append(getDetailedCode());
        buffer.append(",");
        buffer.append("labelCode=").append(getLabelCode());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_f8s7EIyUEeKPttgn1pql1A u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
