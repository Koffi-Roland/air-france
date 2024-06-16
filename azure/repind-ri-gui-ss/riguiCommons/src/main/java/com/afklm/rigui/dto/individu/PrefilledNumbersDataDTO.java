package com.afklm.rigui.dto.individu;

/*PROTECTED REGION ID(_EUk2QJ2qEeWBdds6EPJFhg i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : PrefilledNumbersDataDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class PrefilledNumbersDataDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -1834275373071999807L;


	/**
     * prefilledNumbersDataId
     */
    private Integer prefilledNumbersDataId;
        
        
    /**
     * key
     */
    private String key;
        
        
    /**
     * value
     */
    private String value;
        
        
    /**
     * prefilledNumbersDTO
     */
    private PrefilledNumbersDTO prefilledNumbersDTO;
        

    /*PROTECTED REGION ID(_EUk2QJ2qEeWBdds6EPJFhg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public PrefilledNumbersDataDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pPrefilledNumbersDataId prefilledNumbersDataId
     * @param pKey key
     * @param pValue value
     */
    public PrefilledNumbersDataDTO(Integer pPrefilledNumbersDataId, String pKey, String pValue) {
        this.prefilledNumbersDataId = pPrefilledNumbersDataId;
        this.key = pKey;
        this.value = pValue;
    }

    /**
     *
     * @return key
     */
    public String getKey() {
        return this.key;
    }

    /**
     *
     * @param pKey key value
     */
    public void setKey(String pKey) {
        this.key = pKey;
    }

    /**
     *
     * @return prefilledNumbersDTO
     */
    public PrefilledNumbersDTO getPrefilledNumbersDTO() {
        return this.prefilledNumbersDTO;
    }

    /**
     *
     * @param pPrefilledNumbersDTO prefilledNumbersDTO value
     */
    public void setPrefilledNumbersDTO(PrefilledNumbersDTO pPrefilledNumbersDTO) {
        this.prefilledNumbersDTO = pPrefilledNumbersDTO;
    }

    /**
     *
     * @return prefilledNumbersDataId
     */
    public Integer getPrefilledNumbersDataId() {
        return this.prefilledNumbersDataId;
    }

    /**
     *
     * @param pPrefilledNumbersDataId prefilledNumbersDataId value
     */
    public void setPrefilledNumbersDataId(Integer pPrefilledNumbersDataId) {
        this.prefilledNumbersDataId = pPrefilledNumbersDataId;
    }

    /**
     *
     * @return value
     */
    public String getValue() {
        return this.value;
    }

    /**
     *
     * @param pValue value value
     */
    public void setValue(String pValue) {
        this.value = pValue;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_EUk2QJ2qEeWBdds6EPJFhg) ENABLED START*/
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
        buffer.append("prefilledNumbersDataId=").append(getPrefilledNumbersDataId());
        buffer.append(",");
        buffer.append("key=").append(getKey());
        buffer.append(",");
        buffer.append("value=").append(getValue());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_EUk2QJ2qEeWBdds6EPJFhg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
