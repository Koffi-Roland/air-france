package com.airfrance.repind.dto.individu;


import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>Title : ContractDataDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2018</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ContractDataDTO  {
        
    /**
     * key
     */
    private String key;
        
        
    /**
     * value
     */
    private String value;
        
    
    /** 
     * default constructor 
     */
    public ContractDataDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pKey key
     * @param pValue value
     */
    public ContractDataDTO(String pKey, String pValue) {
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
        result = toStringImpl();
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
            .append("key", getKey())
            .append("value", getValue())
            .toString();
    }

}
