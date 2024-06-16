package com.airfrance.repind.dto.ws;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title : CommunicationPreferencesResponseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2018</p>
 * <p>Company : AIRFRANCE</p>
 */
	public class CommunicationPreferencesResponseDTO implements Serializable {
        
    /**
     * communicationPreferencesDTO
     */
    private List<com.airfrance.repind.dto.individu.CommunicationPreferencesDTO> communicationPreferencesDTO;
    
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
    
    /** 
     * default constructor 
     */
    public CommunicationPreferencesResponseDTO() {
    	this.communicationPreferencesDTO = new ArrayList<com.airfrance.repind.dto.individu.CommunicationPreferencesDTO>();
    }
    
    /**
     *
     * @return communicationPreferencesDTO
     */
    public List<com.airfrance.repind.dto.individu.CommunicationPreferencesDTO> getCommunicationPreferencesDTO() {
        return this.communicationPreferencesDTO;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
