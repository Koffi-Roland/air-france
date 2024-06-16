package com.afklm.repind.v1.createorupdatecomprefbasedonpermws.transformers;

import com.afklm.soa.stubs.w001950.v1.ns1.Information;
import com.afklm.soa.stubs.w001950.v1.ns1.InformationResponse;
import com.afklm.soa.stubs.w001950.v1.ns1.Informations;
import com.afklm.soa.stubs.w001950.v1.ns3.Permission;
import com.afklm.soa.stubs.w001950.v1.ns6.CreateOrUpdateComPrefBasedOnPermissionRequest;
import com.afklm.soa.stubs.w001950.v1.ns6.CreateOrUpdateComPrefBasedOnPermissionResponse;
import com.airfrance.repind.dto.compref.InformationDTO;
import com.airfrance.repind.dto.compref.InformationsDTO;
import com.airfrance.repind.dto.compref.PermissionDTO;
import com.airfrance.repind.dto.compref.PermissionsDTO;
import com.airfrance.repind.dto.ws.RequestorDTO;

import java.util.ArrayList;
import java.util.List;


public class CreateOrUpdateComPrefBasedOnPermissionTransform {
	
	public static PermissionsDTO requestWSToPermissionsDTO(CreateOrUpdateComPrefBasedOnPermissionRequest request) {
		PermissionsDTO permissionsDTO = new PermissionsDTO();
		
		permissionsDTO.setGin(request.getGin());

		RequestorDTO requestorDTO = new RequestorDTO();
		requestorDTO.setChannel(request.getRequestor().getChannel());
		requestorDTO.setSite(request.getRequestor().getSite());
		requestorDTO.setSignature(request.getRequestor().getSignature());
		permissionsDTO.setRequestorDTO(requestorDTO);
		permissionsDTO.setPermission(new ArrayList<PermissionDTO>());
		
		for (Permission permission : request.getPermissionRequest().getPermission()) {
			PermissionDTO permissionDTO = permissionWStoPermissionDTO(permission);
			permissionsDTO.getPermission().add(permissionDTO);
		}
		

		requestorDTO.setSignature(request.getRequestor().getSignature());
		requestorDTO.setSite(request.getRequestor().getSite());
		requestorDTO.setChannel(request.getRequestor().getChannel());
		requestorDTO.setMatricule(request.getRequestor().getMatricule());
		requestorDTO.setManagingCompany(request.getRequestor().getManagingCompany());
		requestorDTO.setOfficeId(request.getRequestor().getOfficeId());
		requestorDTO.setContext(request.getRequestor().getContext());
		requestorDTO.setToken(request.getRequestor().getToken());
		requestorDTO.setIpAddress(request.getRequestor().getIpAddress());
		requestorDTO.setApplicationCode(request.getRequestor().getApplicationCode());
		
		permissionsDTO.setRequestorDTO(requestorDTO);
		
		return permissionsDTO;
	}
	
	public static PermissionDTO permissionWStoPermissionDTO(Permission pPermission) {
		PermissionDTO permissionDTO = new PermissionDTO();
		
		permissionDTO.setPermissionId(pPermission.getPermissionID());
		permissionDTO.setAnswer(pPermission.isPermissionAnswer());
		permissionDTO.setMarket(pPermission.getMarket());
		
		if (pPermission.getLanguage() != null) {
			permissionDTO.setLanguage(pPermission.getLanguage().value());
		} else {
			permissionDTO.setLanguage(null);
		}
		
		return permissionDTO;
	}
	
	public static void informationsDTOtoInformationsWS(CreateOrUpdateComPrefBasedOnPermissionResponse response, List<InformationsDTO> listInformationsDTO) {
		if (response.getInformationResponse() == null) {
			response.setInformationResponse(new InformationResponse());
		}
		
		for (InformationsDTO informationsDTO: listInformationsDTO) {
			informationsDTOtoInformationsWS(response, informationsDTO);
		}
	}
	
	public static void informationsDTOtoInformationsWS(CreateOrUpdateComPrefBasedOnPermissionResponse response, InformationsDTO informationsDTO) {
		if (response.getInformationResponse() == null) {
			response.setInformationResponse(new InformationResponse());
		}
		
		if (informationsDTO != null && informationsDTO.getInformation() != null) {
			Informations informations = new Informations();
			informations.setInformationsId(informationsDTO.getInformationsId());
			
			for (InformationDTO informationDTO : informationsDTO.getInformation()) {
				Information information = new Information();
				information.setCode(informationDTO.getCode());
				information.setDetails(informationDTO.getDetails());
				information.setName(informationDTO.getName());
				informations.getInformation().add(information);
			}
			
			response.getInformationResponse().getInformations().add(informations);
		}
	}
}
