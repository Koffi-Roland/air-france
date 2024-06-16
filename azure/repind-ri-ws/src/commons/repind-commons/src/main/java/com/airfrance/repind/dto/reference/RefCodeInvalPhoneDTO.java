package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_Ub7wgE4iEeS-eLH--0fARw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;


/*PROTECTED REGION END*/

/**
 * <p>Title : RefCodeInvalPhoneDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class RefCodeInvalPhoneDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 2094264820364840049L;


	/**
     * id
     */
    private Integer id;
        
        
    /**
     * codeError
     */
    private String codeError;
        
        
    /**
     * description
     */
    private String description;
        

    /*PROTECTED REGION ID(_Ub7wgE4iEeS-eLH--0fARw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public RefCodeInvalPhoneDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pId id
     * @param pCodeError codeError
     * @param pDescription description
     */
    public RefCodeInvalPhoneDTO(Integer pId, String pCodeError, String pDescription) {
        this.id = pId;
        this.codeError = pCodeError;
        this.description = pDescription;
    }

    /**
     *
     * @return codeError
     */
    public String getCodeError() {
        return this.codeError;
    }

    /**
     *
     * @param pCodeError codeError value
     */
    public void setCodeError(String pCodeError) {
        this.codeError = pCodeError;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     *
     * @param pDescription description value
     */
    public void setDescription(String pDescription) {
        this.description = pDescription;
    }

    /**
     *
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     *
     * @param pId id value
     */
    public void setId(Integer pId) {
        this.id = pId;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_Ub7wgE4iEeS-eLH--0fARw) ENABLED START*/
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
        buffer.append("codeError=").append(getCodeError());
        buffer.append(",");
        buffer.append("description=").append(getDescription());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_Ub7wgE4iEeS-eLH--0fARw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
