package com.airfrance.repind.util.transformer;

import com.airfrance.repind.dto.individu.WarningResponseDTO;
import com.airfrance.repind.dto.ws.CommunicationPreferencesResponseDTO;

/**
 * Reponse of CreateRoleContractFlyingBlueContractHelper 
 * 
 */
public class FlyingBlueContractHelperResponse {

	private CommunicationPreferencesResponseDTO comPrefResponse;
	private WarningResponseDTO warningResponse;
	private String numberContract;
	private Boolean success;
	
	public String getNumberContract() {
		return numberContract;
	}

	public void setNumberContract(String numberContract) {
		this.numberContract = numberContract;
	}

	public WarningResponseDTO getWarningResponse() {
		return warningResponse;
	}
	public void setWarningResponse(WarningResponseDTO warningResponse) {
		this.warningResponse = warningResponse;
	}
	
	public CommunicationPreferencesResponseDTO getComPrefResponse() {
		return comPrefResponse;
	}

	public void setComPrefResponse(CommunicationPreferencesResponseDTO comPrefResponse) {
		this.comPrefResponse = comPrefResponse;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	
}
