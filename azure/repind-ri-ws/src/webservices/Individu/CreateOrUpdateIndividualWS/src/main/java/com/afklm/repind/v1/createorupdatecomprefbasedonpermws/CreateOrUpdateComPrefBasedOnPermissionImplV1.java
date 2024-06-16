package com.afklm.repind.v1.createorupdatecomprefbasedonpermws;


import com.afklm.repind.v1.createorupdatecomprefbasedonpermws.transformers.CreateOrUpdateComPrefBasedOnPermissionTransform;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001950.v1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001950.v1.CreateOrUpdateComPrefBasedOnPermissionV1;
import com.afklm.soa.stubs.w001950.v1.ns1.BusinessError;
import com.afklm.soa.stubs.w001950.v1.ns1.BusinessErrorBloc;
import com.afklm.soa.stubs.w001950.v1.ns1.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w001950.v1.ns3.Permission;
import com.afklm.soa.stubs.w001950.v1.ns6.CreateOrUpdateComPrefBasedOnPermissionRequest;
import com.afklm.soa.stubs.w001950.v1.ns6.CreateOrUpdateComPrefBasedOnPermissionResponse;
import com.afklm.soa.stubs.w001950.v1.ns9.Requestor;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MaximumSubscriptionsException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.compref.InvalidPermissionIdException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.compref.InformationDTO;
import com.airfrance.repind.dto.compref.InformationsDTO;
import com.airfrance.repind.dto.compref.PermissionDTO;
import com.airfrance.repind.dto.compref.PermissionsDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.util.LoggerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@Service("passenger_CreateOrUpdateComPrefBasedOnPermission-v1Bean")
@WebService(endpointInterface="com.afklm.soa.stubs.w001950.v1.CreateOrUpdateComPrefBasedOnPermissionV1", targetNamespace = "http://www.af-klm.com/services/passenger/CreateOrUpdateComPrefBasedOnPermission-v1/wsdl", serviceName = "CreateOrUpdateComPrefBasedOnPermissionService-v1", portName = "CreateOrUpdateComPrefBasedOnPermission-v1-soap11http")
@Slf4j
public class CreateOrUpdateComPrefBasedOnPermissionImplV1 implements CreateOrUpdateComPrefBasedOnPermissionV1 {

	@Autowired
	private IndividuDS individuDS;
	
	@Autowired
	private CommunicationPreferencesDS communicationPreferencesDS;
		
	@Override
	public CreateOrUpdateComPrefBasedOnPermissionResponse createOrUpdateComPrefBasedOnPermission(CreateOrUpdateComPrefBasedOnPermissionRequest request) throws BusinessErrorBlocBusinessException, SystemException {

		log.info("##### START CreateOrUpdateComPrefBasedOnPermission-V1 ###############################");
		
		List<InformationsDTO> listInformations = null;
		
		try {
			// 1. CHECKING INPUTS (GIN, PERMISSION ID)
			checkInput(request);
			
			// 2. TRANSFORM REQUEST TO PERMISSION DTO
			PermissionsDTO permissionsDTO = CreateOrUpdateComPrefBasedOnPermissionTransform.requestWSToPermissionsDTO(request);
			
			// 3. CALL DS TO CREATE COM PREF
			log.info("##### CreateOrUpdateComPrefBasedOnPermission-V1 : GIN = {}",request.getGin());
			for (PermissionDTO permissionDTO : permissionsDTO.getPermission()) {
				log.info("##### CreateOrUpdateComPrefBasedOnPermission-V1 : PermissionId = {}",permissionDTO.getPermissionId());
				log.info("##### CreateOrUpdateComPrefBasedOnPermission-V1 : PermissionAnswer = {}",permissionDTO.getAnswer());
			}
			listInformations = communicationPreferencesDS.createComPrefBasedOnAPermission(permissionsDTO);

		}
		// ERROR 001 : INDIVIDUAL NOT FOUND
		catch (NotFoundException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throwBusinessError(BusinessErrorCodeEnum.ERROR_001, e.getMessage());
		}
		// ERROR 133 : MISSING PARAMETER
		catch(MissingParameterException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throwBusinessError(BusinessErrorCodeEnum.ERROR_133, e.getMessage());
		}
		// ERROR 390 : PERMISSION ID UNKNOWN
		catch(InvalidPermissionIdException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throwBusinessError(BusinessErrorCodeEnum.ERROR_390, e.getMessage());
		}
		// 551 : MAXIMUM NUMBER OF SUBSCRIBED NEWLETTER SALES REACHED
		catch (MaximumSubscriptionsException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throwBusinessError(BusinessErrorCodeEnum.ERROR_551, e.getMessage());
		}
		// ERROR 932 : INVALID PARAMETER
		catch(InvalidParameterException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throwBusinessError(BusinessErrorCodeEnum.ERROR_932, e.getMessage());
		}
		// ERROR 905 : TECHNICAL ERROR
		catch (Exception e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throwBusinessError(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
		}
		
		CreateOrUpdateComPrefBasedOnPermissionResponse response = buildCreateOrUpdateComPrefBasedOnPermissionResponse(listInformations);

		log.info("##### END CreateOrUpdateComPrefBasedOnPermission-V1 ###############################");

		return response;
	}
	
	/**
	 * BUILD REPONSE : Build the reponse from list of Informations
	 * 
	 * @param informations
	 * @return CreateOrUpdateComPrefBasedOnPermissionResponse
	 */
	private CreateOrUpdateComPrefBasedOnPermissionResponse buildCreateOrUpdateComPrefBasedOnPermissionResponse(List<InformationsDTO> informations) {
		CreateOrUpdateComPrefBasedOnPermissionResponse response = new CreateOrUpdateComPrefBasedOnPermissionResponse();
		response.setSuccess(isSuccessProcess(informations));
		
		CreateOrUpdateComPrefBasedOnPermissionTransform.informationsDTOtoInformationsWS(response, informations);
		
		log.info("##### CreateOrUpdateComPrefBasedOnPermission-V1 : Process = {}",response.isSuccess());
		
		return response;
	}
	
	/**
	 * SUCCESS PROCESS : If at least one compref is not well created then return false, otherwise return true
	 * 
	 * @param informations
	 * @return boolean
	 */
	private boolean isSuccessProcess(List<InformationsDTO> informations) {
		for (InformationsDTO informationsDTO : informations) {
			for (InformationDTO informationDTO : informationsDTO.getInformation()) {
				if (!informationDTO.getCode().equalsIgnoreCase("0")) {
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * CHECKING INPUT : Checking request information 
	 * 
	 * @param request
	 * @throws JrafDomainException 
	 */
	private void checkInput(CreateOrUpdateComPrefBasedOnPermissionRequest request) throws JrafDomainException {
		if(request == null) {
			log.error("Missing Parameter : request is mandatory");
			throw new MissingParameterException("request is mandatory");
		}
		
		Requestor requestor = request.getRequestor();
		if (requestor == null) {
			log.error("Missing Parameter : requestor is mandatory");
			throw new MissingParameterException("requestor is Mandatory");
		} else if(requestor.getChannel() == null || requestor.getChannel().isEmpty()) {
			log.error("Missing Parameter : channel is mandatory");
			throw new MissingParameterException("channel is Mandatory");
		} else if(requestor.getSite() == null || requestor.getSite().isEmpty()) {
			log.error("Missing Parameter : site is mandatory");
			throw new MissingParameterException("site is Mandatory");
		} else if(requestor.getSignature() == null || requestor.getSignature().isEmpty()) {
			log.error("Missing Parameter : signature is mandatory");
			throw new MissingParameterException("signature is Mandatory");
		}
		
		if (request.getPermissionRequest() == null) {
			log.error("Missing Parameter : permission request is mandatory");
			throw new MissingParameterException("Permission request is Mandatory");
		}
		
		if (request.getPermissionRequest().getPermission().isEmpty()) {
			log.error("Missing Parameter : permission is mandatory");
			throw new MissingParameterException("Permission is Mandatory");
		}
		
		List<String> listPermissionId = new ArrayList<String>();
		for (Permission permission : request.getPermissionRequest().getPermission()) {
			if (permission.getPermissionID() == null || permission.getPermissionID().isEmpty()) {
				log.error("Missing Parameter : permission id is mandatory");
				throw new MissingParameterException("Permission Id is Mandatory");
			}
			for (String id : listPermissionId) {
				if (id.equalsIgnoreCase(permission.getPermissionID())) {
					log.error("Invalid Parameter : permission id {} duplicated",id);
					throw new InvalidParameterException("Permission Id duplicated: " + id);
				}
			}
			listPermissionId.add(permission.getPermissionID());
		}
				
		IndividuDTO individu = individuDS.getByGin(request.getGin());
		if (individu == null) {
			log.error("Not Found : individu does not exist");
			throw new NotFoundException(request.getGin());
		}
	}
	
	/**
	 * TRHOW BUSINESS ERROR: Throw BusinessError from Exception
	 * 
	 * @param code
	 * @param message
	 * @throws BusinessErrorBlocBusinessException 
	 */
	private void throwBusinessError(BusinessErrorCodeEnum code, String message) throws BusinessErrorBlocBusinessException {
		
		BusinessErrorBloc businessErrorBloc = new BusinessErrorBloc();
		businessErrorBloc.setBusinessError(buildBusinessException(code, message));
		businessErrorBloc.setInternalBusinessError(null);
		throw new BusinessErrorBlocBusinessException(businessErrorBloc.getBusinessError().getErrorLabel(), businessErrorBloc);
	}
	
	/**
	 * BUILD BUSINESS ERROR: Build a Business Error from Exception
	 * 
	 * @param code
	 * @param message
	 * @return BusinessError
	 */
	private BusinessError buildBusinessException(BusinessErrorCodeEnum code, String message) {
		BusinessError error = new BusinessError();
		error.setErrorCode(code);
		error.setErrorLabel(RefTableREF_ERREUR.instance().getLibelle(code.toString().replaceAll("ERROR_", ""), "EN"));
		error.setErrorDetail(message);
		
		return error;
	}
	
}
