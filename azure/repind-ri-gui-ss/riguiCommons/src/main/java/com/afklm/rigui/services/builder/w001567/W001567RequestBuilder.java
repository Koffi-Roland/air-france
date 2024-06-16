package com.afklm.rigui.services.builder.w001567;

import org.springframework.stereotype.Component;

@Component
public class W001567RequestBuilder {
	
	// private static final String DEFAULT_CHANNEL = "B2C";
	// private static final String DEFAULT_SITE = "QVI";
	// private static final String DEFAULT_SIGNATURE = "REPIND/IHM";
	
	// private static final String UPDATE_ACTION_CODE = "U";
	// private static final String DELETE_ACTION_CODE = "D";
	
	// Build the update request for a contract
	/*public CreateUpdateRoleContractRequest buildUpdateRequest(String id, ModelContract contract) {
		
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		Requestor requestor = getRequestor();
		request.setRequestor(requestor);
		request.setGin(id);
		request.setActionCode(UPDATE_ACTION_CODE);
		ContractRequest contractRequest = getContractRequest(contract);
		request.setContractRequest(contractRequest);
		
		return request;
		
	}*/
		
	// Build the delete request for a contract
	/*public CreateUpdateRoleContractRequest buildDeleteRequest(String id, ModelContract contract) {
		
		CreateUpdateRoleContractRequest request = new CreateUpdateRoleContractRequest();
		Requestor requestor = getRequestor();
		request.setRequestor(requestor);
		request.setGin(id);
		request.setActionCode(DELETE_ACTION_CODE);
		ContractRequest contractRequest = getContractRequest(contract);
		request.setContractRequest(contractRequest);
		
		return request;
		
	}*/
	
	/*private Requestor getRequestor() {
		Requestor requestor = new Requestor();
		requestor.setChannel(DEFAULT_CHANNEL);
		requestor.setSite(DEFAULT_SITE);
		requestor.setSignature(DEFAULT_SIGNATURE);
		return requestor;
	}*/
	
	/*private ContractRequest getContractRequest(ModelContract c) {
		
		ContractRequest contractRequest = new ContractRequest();
		
		ContractV2 contract = new ContractV2();
		contract.setContractNumber(c.getContractNumber());
		contract.setContractStatus(c.getStatus());
		contract.setProductType(c.getProductType());
		contract.setContractType(c.getContractType());
		contract.setCompanyCode(c.getCompanyCode());
		contract.setProductSubType(c.getProductSubType());
		contract.setValidityEndDate(c.getEndingDate());
		contract.setValidityStartDate(c.getStartingDate());
		contractRequest.setContract(contract);
		
		return contractRequest;
		
		
	}*/

}
