package com.airfrance.repindutf8.dto.prospect.prospectservices;

/*PROTECTED REGION ID(_IU840J0KEeKEJ_XC1Z7jtg i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : UpdateDataOnAProspectDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class UpdateDataOnAProspectDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -4421226498132842944L;


	/**
     * gin
     */
    private String gin;
        
        
    /**
     * returnDetailsDTO
     */
    private ReturnDetailsDTO returnDetailsDTO;
        

    /*PROTECTED REGION ID(_IU840J0KEeKEJ_XC1Z7jtg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public UpdateDataOnAProspectDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pGin gin
     */
    public UpdateDataOnAProspectDTO(String pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return gin
     */
    public String getGin() {
        return this.gin;
    }

    /**
     *
     * @param pGin gin value
     */
    public void setGin(String pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return returnDetailsDTO
     */
    public ReturnDetailsDTO getReturnDetailsDTO() {
        return this.returnDetailsDTO;
    }

    /**
     *
     * @param pReturnDetailsDTO returnDetailsDTO value
     */
    public void setReturnDetailsDTO(ReturnDetailsDTO pReturnDetailsDTO) {
        this.returnDetailsDTO = pReturnDetailsDTO;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_IU840J0KEeKEJ_XC1Z7jtg) ENABLED START*/
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
        buffer.append("gin=").append(getGin());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_IU840J0KEeKEJ_XC1Z7jtg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
