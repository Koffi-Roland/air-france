package com.airfrance.repind.dto.firme.providefirmdata;

/*PROTECTED REGION ID(_8Yt6EONVEeCJ_971XLeDSQ i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.firme.PersonneMoraleDTO;

import java.io.Serializable;
import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : ProvideFirmDataResponseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ProvideFirmDataResponseDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -861857122065496253L;


	/**
     * identificationNumber
     */
    private String identificationNumber;
        
        
    /**
     * personneMoraleDTO
     */
    private List<PersonneMoraleDTO> personneMoraleDTO;
        

    /*PROTECTED REGION ID(_8Yt6EONVEeCJ_971XLeDSQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ProvideFirmDataResponseDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pIdentificationNumber identificationNumber
     */
    public ProvideFirmDataResponseDTO(String pIdentificationNumber) {
        this.identificationNumber = pIdentificationNumber;
    }

    /**
     *
     * @return identificationNumber
     */
    public String getIdentificationNumber() {
        return this.identificationNumber;
    }

    /**
     *
     * @param pIdentificationNumber identificationNumber value
     */
    public void setIdentificationNumber(String pIdentificationNumber) {
        this.identificationNumber = pIdentificationNumber;
    }

    /**
     *
     * @return personneMoraleDTO
     */
    public List<PersonneMoraleDTO> getPersonneMoraleDTO() {
        return this.personneMoraleDTO;
    }

    /**
     *
     * @param pPersonneMoraleDTO personneMoraleDTO value
     */
    public void setPersonneMoraleDTO(List<PersonneMoraleDTO> pPersonneMoraleDTO) {
        this.personneMoraleDTO = pPersonneMoraleDTO;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_8Yt6EONVEeCJ_971XLeDSQ) ENABLED START*/
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
        buffer.append("identificationNumber=").append(getIdentificationNumber());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_8Yt6EONVEeCJ_971XLeDSQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
