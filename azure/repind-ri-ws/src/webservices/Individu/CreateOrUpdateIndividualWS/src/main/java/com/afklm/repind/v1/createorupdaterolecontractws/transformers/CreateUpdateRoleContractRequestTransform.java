package com.afklm.repind.v1.createorupdaterolecontractws.transformers;

import com.afklm.soa.stubs.w001567.v1.data.CreateUpdateRoleContractRequest;
import com.afklm.soa.stubs.w001567.v1.request.ContractRequest;
import com.afklm.soa.stubs.w001567.v1.siccommontype.Requestor;
import com.afklm.soa.stubs.w001567.v1.sicindividutype.ContractData;
import com.afklm.soa.stubs.w001567.v1.sicindividutype.ContractV2;
import com.airfrance.ref.util.UList;
import com.airfrance.repind.dto.individu.ContractDataDTO;
import com.airfrance.repind.dto.individu.ContractV2DTO;
import com.airfrance.repind.dto.ws.RequestorDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.ContractRequestDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.CreateUpdateRoleContractRequestDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Transformer
 * CreateUpdateRoleContractRequest BO --> DTO 
 *
 */
public final class CreateUpdateRoleContractRequestTransform {

	public static CreateUpdateRoleContractRequestDTO bo2Dto(CreateUpdateRoleContractRequest request) {
		return bo2Dto(request, true);
	}
	
	public static CreateUpdateRoleContractRequestDTO bo2Dto(CreateUpdateRoleContractRequest request, boolean isFBRecognitionActivate) {
		
		CreateUpdateRoleContractRequestDTO result = new CreateUpdateRoleContractRequestDTO();
		
		result.setActionCode(request.getActionCode());
		result.setGin(request.getGin());
		result.setRequestor(bo2Dto(request.getRequestor()));
		result.setContractRequest(bo2Dto(request.getContractRequest(), isFBRecognitionActivate));
		return result;
	}
	
	private static RequestorDTO bo2Dto(Requestor requestor) {
		RequestorDTO result = new RequestorDTO();
		result.setApplicationCode(requestor.getApplicationCode());
		result.setChannel(requestor.getChannel());
		result.setContext(requestor.getContext());
		result.setIpAddress(requestor.getIpAddress());
		result.setManagingCompany(requestor.getManagingCompany());
		result.setMatricule(requestor.getMatricule());
		result.setOfficeId(requestor.getOfficeId());
		result.setSignature(requestor.getSignature());
		result.setSite(requestor.getSite());
		result.setToken(requestor.getToken());
		
		return result;
	}
	
	private static ContractRequestDTO bo2Dto(ContractRequest contractRequest) {
		return bo2Dto(contractRequest, true);
	}
	
	private static ContractRequestDTO bo2Dto(ContractRequest contractRequest, boolean isFBRecognitionActivate) {
		ContractRequestDTO result = new ContractRequestDTO();
		result.setContract(bo2Dto(contractRequest.getContract()));
		List<ContractDataDTO> contractDataDto = new ArrayList<ContractDataDTO>();
		List<ContractData> contractData= contractRequest.getContractData();
		if(!UList.isNullOrEmpty(contractData)) {
			
			if (contractRequest.getContract() != null) {
				// Flag in database should be linked to FP process for RoleContract
		    	if (!"FP".equals(contractRequest.getContract().getProductType()))  {    		
		    		isFBRecognitionActivate = true;
		    	}
			}
			
			for(ContractData cd : contractData) {
				// On tient compte du Flag et du fait que le code est celui ci. Car le Boolean n influence que ces clefs
				if (cd != null && (
						isFBRecognitionActivate || 
						( 
						!"tierLevel".equalsIgnoreCase(cd.getKey()) &&
						!"memberType".equalsIgnoreCase(cd.getKey()) &&
						!"milesBalance".equalsIgnoreCase(cd.getKey()) &&
						!"qualifyingMiles".equalsIgnoreCase(cd.getKey()) &&
						!"qualifyingSegments".equalsIgnoreCase(cd.getKey())
						)
						)
					) {
					contractDataDto.add(bo2Dto(cd));
				}
			}
		}
		result.setContractData(contractDataDto);
		
		return result;
	}
	
	private static ContractV2DTO bo2Dto(ContractV2 contractV2) {
		ContractV2DTO result = new ContractV2DTO();
		result.setContractNumber(contractV2.getContractNumber());
		result.setContractStatus(contractV2.getContractStatus());
		result.setContractType(contractV2.getContractType());
		result.setProductType(contractV2.getProductType());
		result.setProductSubType(contractV2.getProductSubType());
		result.setValidityStartDate(contractV2.getValidityStartDate());
		result.setValidityEndDate(contractV2.getValidityEndDate());
		result.setCompanyCode(contractV2.getCompanyCode());
		result.setIataCode(contractV2.getIataCode());
		
		return result;
	}
	
	private static ContractDataDTO bo2Dto(ContractData contractData) {
		ContractDataDTO result = new ContractDataDTO();
		result.setKey(contractData.getKey());
		result.setValue(contractData.getValue());
		
		return result;
	}
}
