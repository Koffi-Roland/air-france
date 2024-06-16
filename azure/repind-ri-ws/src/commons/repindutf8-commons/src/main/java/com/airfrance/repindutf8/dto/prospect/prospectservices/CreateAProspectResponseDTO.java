package com.airfrance.repindutf8.dto.prospect.prospectservices;

/*PROTECTED REGION ID(_RKaLcIyUEeKPttgn1pql1A i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;

/*PROTECTED REGION END*/

/**
 * <p>Title : CreateAProspectResponseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class CreateAProspectResponseDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 4370196085318805183L;


	/**
     * gin
     */
    private String gin;
        
        
    /**
     * returnDetailsDTO
     */
    private ReturnDetailsDTO returnDetailsDTO;
        

    /*PROTECTED REGION ID(_RKaLcIyUEeKPttgn1pql1A u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public CreateAProspectResponseDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pGin gin
     */
    public CreateAProspectResponseDTO(String pGin) {
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
        /*PROTECTED REGION ID(toString_RKaLcIyUEeKPttgn1pql1A) ENABLED START*/
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

    /*PROTECTED REGION ID(_RKaLcIyUEeKPttgn1pql1A u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
