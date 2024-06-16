package com.airfrance.repind.util.transformer;

import com.airfrance.repind.dto.individu.WarningResponseDTO;
import com.airfrance.repind.entity.reference.RefComPref;

import java.util.List;
import java.util.Map;

/**
 * Response of the RefComPref filtre 
 * to get $market FB_ESS and and refComPref world Market * 
 * 
 */
public class RefComPrefResponse {
	
	/** Final list of ref communication preferences */
	private List<RefComPref> refComPrefList;
	/** List of warnings*/
	private WarningResponseDTO warningResponse;
	/** create communication preferences*/
	private boolean createComPref;
	/** Map to get RefComPref by comType as a key */
	private Map<String, RefComPref> mapRefComType;
	
	
	public boolean isCreateComPref() {
		return createComPref;
	}
	public void setCreateComPref(boolean createComPref) {
		this.createComPref = createComPref;
	}
	public List<RefComPref> getRefComPrefList() {
		return refComPrefList;
	}
	public WarningResponseDTO getWarningResponse() {
		return warningResponse;
	}
	public void setRefComPrefList(List<RefComPref> refComPrefList) {
		this.refComPrefList = refComPrefList;
	}
	public void setWarningResponse(WarningResponseDTO warningResponse) {
		this.warningResponse = warningResponse;
	}
	public Map<String, RefComPref> getMapRefComType() {
		return mapRefComType;
	}
	
	public void setMapRefComType(Map<String, RefComPref> mapRefComType) {
		this.mapRefComType = mapRefComType;
	}
	
	
}
