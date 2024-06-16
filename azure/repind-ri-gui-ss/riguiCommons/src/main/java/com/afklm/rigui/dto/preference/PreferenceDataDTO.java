package com.afklm.rigui.dto.preference;

/*PROTECTED REGION ID(_4JtogE9iEeevnICwxQHWbw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;


/*PROTECTED REGION END*/

/**
 * <p>Title : PreferenceDataDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class PreferenceDataDTO implements Serializable {


        
    /**
     * preferenceDataId
     */
    private Long preferenceDataId;

        
    /**
     * key
     */
    private String key;
        
        
    /**
     * value
     */
    private String value;
        
    
    
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * signatureCreation
     */
    private String signatureCreation;
        
        
    /**
     * siteCreation
     */
    private String siteCreation;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        
        
    /**
     * signatureModification
     */
    private String signatureModification;
        
        
    /**
     * siteModification
     */
    private String siteModification;
        
    /**
     * preferenceDTO
     */
    private PreferenceDTO preferenceDTO;
        

    /*PROTECTED REGION ID(_4JtogE9iEeevnICwxQHWbw u var) ENABLED START*/
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
    public PreferenceDataDTO() {
    //empty constructor
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
     * @return preferenceDTO
     */
    public PreferenceDTO getPreferenceDTO() {
        return this.preferenceDTO;
    }

    /**
     *
     * @param pPreferenceDTO preferenceDTO value
     */
    public void setPreferenceDTO(PreferenceDTO pPreferenceDTO) {
        this.preferenceDTO = pPreferenceDTO;
    }

    /**
     *
     * @return preferenceDataId
     */
    public Long getPreferenceDataId() {
        return this.preferenceDataId;
    }

    /**
     *
     * @param pPreferenceDataId preferenceDataId value
     */
    public void setPreferenceDataId(Long pPreferenceDataId) {
        this.preferenceDataId = pPreferenceDataId;
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
    * @return dateCreation
    */
   public Date getDateCreation() {
       return this.dateCreation;
   }

   /**
    *
    * @param pDateCreation dateCreation value
    */
   public void setDateCreation(Date pDateCreation) {
       this.dateCreation = pDateCreation;
   }

   /**
    *
    * @return dateModification
    */
   public Date getDateModification() {
       return this.dateModification;
   }

   /**
    *
    * @param pDateModification dateModification value
    */
   public void setDateModification(Date pDateModification) {
       this.dateModification = pDateModification;
   }
    
   
   /**
   *
   * @return signatureCreation
   */
  public String getSignatureCreation() {
      return this.signatureCreation;
  }

  /**
   *
   * @param pSignatureCreation signatureCreation value
   */
  public void setSignatureCreation(String pSignatureCreation) {
      this.signatureCreation = pSignatureCreation;
  }

  /**
   *
   * @return signatureModification
   */
  public String getSignatureModification() {
      return this.signatureModification;
  }

  /**
   *
   * @param pSignatureModification signatureModification value
   */
  public void setSignatureModification(String pSignatureModification) {
      this.signatureModification = pSignatureModification;
  }

  /**
   *
   * @return siteCreation
   */
  public String getSiteCreation() {
      return this.siteCreation;
  }

  /**
   *
   * @param pSiteCreation siteCreation value
   */
  public void setSiteCreation(String pSiteCreation) {
      this.siteCreation = pSiteCreation;
  }

  /**
   *
   * @return siteModification
   */
  public String getSiteModification() {
      return this.siteModification;
  }

  /**
   *
   * @param pSiteModification siteModification value
   */
  public void setSiteModification(String pSiteModification) {
      this.siteModification = pSiteModification;
  }
   
    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_4JtogE9iEeevnICwxQHWbw) ENABLED START*/
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
        buffer.append("preferenceDataId=").append(getPreferenceDataId());
        buffer.append(",");
        buffer.append("key=").append(getKey());
        buffer.append(",");
        buffer.append("value=").append(getValue());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_4JtogE9iEeevnICwxQHWbw u m) ENABLED START*/
    // add your custom methods here if necessary
    public PreferenceDataDTO(String pKey, String pValue) {
		this.setKey(pKey);
		this.setValue(pValue);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PreferenceDataDTO other = (PreferenceDataDTO) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equalsIgnoreCase(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equalsIgnoreCase(other.value))
			return false;
		return true;
	}
    /*PROTECTED REGION END*/

}
