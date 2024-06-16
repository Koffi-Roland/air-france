package com.afklm.cati.common.spring.rest.resources;

import java.io.Serializable;

import com.afklm.cati.common.entity.RefComPrefGroupInfo;

public class RefComPrefGroupIdResource extends CatiCommonResource implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4634609266319780882L;

    private RefComPrefDgtResource refComPrefDgt;
    private RefComPrefGroupInfo refComPrefGroupInfo;
    
	/*public RefComPrefDgtResource getRefComPrefDgt() {
		return refComPrefDgt;
	}
	public void setRefComPrefDgt(RefComPrefDgtResource refComPrefDgt) {
		this.refComPrefDgt = refComPrefDgt;
	}*/
	public RefComPrefGroupInfo getRefComPrefGroupInfo() {
		return refComPrefGroupInfo;
	}
	public void setRefComPrefGroupInfo(RefComPrefGroupInfo refComPrefGroupInfo) {
		this.refComPrefGroupInfo = refComPrefGroupInfo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
  
}
