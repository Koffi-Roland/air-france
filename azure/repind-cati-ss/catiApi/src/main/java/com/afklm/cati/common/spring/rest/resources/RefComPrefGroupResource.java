package com.afklm.cati.common.spring.rest.resources;

import java.io.Serializable;

public class RefComPrefGroupResource extends CatiCommonResource implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4634609266319780882L;
    
    private RefComPrefGroupIdResource refComPrefGroupId;
    
	public RefComPrefGroupResource() {
		super();
	}
	

	public RefComPrefGroupResource(RefComPrefGroupIdResource refComPrefGroupId) {
		super();
		this.refComPrefGroupId = refComPrefGroupId;
	}


	public RefComPrefGroupIdResource getRefComPrefGroupId() {
		return refComPrefGroupId;
	}

	public void setRefComPrefGroupId(RefComPrefGroupIdResource refComPrefGroupId) {
		this.refComPrefGroupId = refComPrefGroupId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

	
    
    
    
    
  
}
