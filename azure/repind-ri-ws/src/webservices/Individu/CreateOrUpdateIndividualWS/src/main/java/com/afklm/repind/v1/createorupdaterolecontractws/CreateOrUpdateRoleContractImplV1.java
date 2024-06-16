package com.afklm.repind.v1.createorupdaterolecontractws;


import com.afklm.repind.v1.createorupdaterolecontractws.helpers.BusinessExceptionHelper;
import com.afklm.repind.v1.createorupdaterolecontractws.transformers.CreateUpdateRoleContractRequestTransform;
import com.afklm.repind.v1.createorupdaterolecontractws.transformers.CreateUpdateRoleContractResponseTransform;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001567.v1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001567.v1.CreateOrUpdateRoleContractServiceV1;
import com.afklm.soa.stubs.w001567.v1.data.CreateUpdateRoleContractRequest;
import com.afklm.soa.stubs.w001567.v1.data.CreateUpdateRoleContractResponse;
import com.afklm.soa.stubs.w001567.v1.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w001567.v1.siccommontype.Requestor;
import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.contract.RoleContractUnauthorizedOperationException;
import com.airfrance.ref.exception.contract.RoleTravelerNotCompatibleWithProspectException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.reference.RefProductOwnerDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.CreateUpdateRoleContractResponseDTO;
import com.airfrance.repind.service.reference.internal.RefProductOwnerDS;
import com.airfrance.repind.service.ws.internal.helpers.ActionManager;
import com.airfrance.repind.util.LoggerUtils;
import com.airfrance.repind.util.ReadSoaHeaderHelper;
import com.sun.xml.ws.api.message.Header;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.persistence.NoResultException;
import javax.xml.soap.SOAPException;
import javax.xml.ws.WebServiceContext;
import java.util.List;

@Service("passenger_CreateOrUpdateRoleContractService-v1Bean")
@WebService(endpointInterface="com.afklm.soa.stubs.w001567.v1.CreateOrUpdateRoleContractServiceV1", targetNamespace = "http://www.af-klm.com/services/passenger/CreateOrUpdateRoleContractService-v1/wsdl", serviceName = "CreateOrUpdateRoleContractServiceService-v1", portName = "CreateOrUpdateRoleContractService-v1-soap11http")
@Slf4j
public class CreateOrUpdateRoleContractImplV1 implements CreateOrUpdateRoleContractServiceV1 {
	
	@Autowired
	protected BusinessExceptionHelper businessExceptionHelperV1;

	@Autowired
	protected ActionManager actionManager;
	
	@Autowired
	protected RefProductOwnerDS refProductOwnerDS;
	
	@Resource
	protected WebServiceContext context;

	@Override
	public CreateUpdateRoleContractResponse createRoleContract(CreateUpdateRoleContractRequest request)
			throws BusinessErrorBlocBusinessException, SystemException {

		log.info("##### START CreateOrUpdateRoleContract-V1 ###############################");

		CreateUpdateRoleContractResponseDTO response = null;

		boolean isFBRecognitionActivate = false;

		try {

			// 1. CHECKING INPUTS => EITHER CREATION OR UPDATE 
			checkInput(request);
			// 2. CHECKING MODIFICATION/CREATION RIGHTS FOR WS USER
			checkRightsOnContract(request.getContractRequest().getContract().getProductType(), request.getContractRequest().getContract().getProductSubType());
			
			response = actionManager.processByActionCode(CreateUpdateRoleContractRequestTransform.bo2Dto(request, isFBRecognitionActivate), isFBRecognitionActivate);
		}
		// ERROR 001 : INDIVIDUAL NOT FOUND
		catch (NotFoundException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV1.throwBusinessException(BusinessErrorCodeEnum.ERROR_001, e.getMessage());
		}
		catch (RoleTravelerNotCompatibleWithProspectException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV1.throwBusinessException(BusinessErrorCodeEnum.ERROR_001, e.getMessage());
		}
		// ERROR 133 : MISSING PARAMETER
		catch(MissingParameterException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV1.throwBusinessException(BusinessErrorCodeEnum.ERROR_133, e.getMessage());
		}
		// ERROR 134 : EXISTING INDIVIDUAL CONTRACT NUMBER
		catch (ContractExistException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV1.throwBusinessException(BusinessErrorCodeEnum.ERROR_134, e.getMessage());
		}
		// ERROR 212 : CONTRACT NOT FOUND
		catch (NoResultException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV1.throwBusinessException(BusinessErrorCodeEnum.ERROR_212, e.getMessage());
		}
		// ERROR 212 : CONTRACT NOT FOUND
		catch (ContractNotFoundException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV1.throwBusinessException(BusinessErrorCodeEnum.ERROR_212, e.getMessage());
		}
		// ERROR 385 : ACCOUNT NOT FOUND
		catch (AccountNotFoundException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV1.throwBusinessException(BusinessErrorCodeEnum.ERROR_385, e.getMessage());
		}
		// ERROR 932 : INVALID PARAMETER
		catch(InvalidParameterException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV1.throwBusinessException(BusinessErrorCodeEnum.ERROR_932, e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV1.throwBusinessException(BusinessErrorCodeEnum.ERROR_932, e.getMessage());
		}
		// ERROR 905 : TECHNICAL ERROR
		catch (Exception e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV1.throwBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
		}

		log.info("##### END CreateOrUpdateRoleContract-V1 ###############################");

		return CreateUpdateRoleContractResponseTransform.dto2Bo(response);
	}

	/**
	 * CHECKING INPUT : checking Individual Request information 
	 * 
	 * @param request
	 * @throws MissingParameterException
	 */
	protected void checkInput(CreateUpdateRoleContractRequest request) throws MissingParameterException, InvalidParameterException {
		if (request == null) {
			log.error("Missing Parameter : request is mandatory");
			throw new MissingParameterException("request is mandatory");
		}
		if (request.getActionCode() == null || request.getActionCode().isEmpty()) {
			log.error("Missing Parameter : actionCode is mandatory");
			throw new MissingParameterException("actionCode is mandatory");
		}
		if (request.getContractRequest() == null || request.getContractRequest().getContract() == null) {
			log.error("Missing Parameter : contract informations are mandatory");
			throw new MissingParameterException("contract informations are mandatory");
		}
		if (request.getContractRequest().getContract().getProductType() == null || request.getContractRequest().getContract().getProductType().isEmpty()) {
			log.error("Missing Parameter : producType is mandatory");
			throw new MissingParameterException("producType is mandatory");
		}
		
		Requestor requestor = request.getRequestor(); 
		if (requestor == null) {
			log.error("Missing Parameter : requestor is mandatory");
			throw new MissingParameterException("requestor is Mandatory");
		} else if (requestor.getChannel() == null || requestor.getChannel().isEmpty()) {
			log.error("Missing Parameter : channel is mandatory");
			throw new MissingParameterException("channel is Mandatory");
		} else if (requestor.getSite() == null || requestor.getSite().isEmpty()) {
			log.error("Missing Parameter : site is mandatory");
			throw new MissingParameterException("site is Mandatory");
		} else if (requestor.getSignature() == null || requestor.getSignature().isEmpty()) {
			log.error("Missing Parameter : signature is mandatory");
			throw new MissingParameterException("signature is Mandatory");
		}
	}

	/**
	 * 
	 * 
	 * @param type
	 * @param subType
	 * @throws JrafDomainException
	 * @throws SOAPException 
	 * @throws ContractNotFoundException, JrafDomainException 
	 */
	public void checkRightsOnContract(String type, String subType) throws SOAPException, JrafDomainException {
		Header header = ReadSoaHeaderHelper.getHeaderFromContext(context, "trackingMessageHeader");
		//REPIND-1009: no more check using the userID (not relevant)
		//String userID = ReadSoaHeaderHelper.getHeaderChildren(header, "userID");
		
		String userID = null;
		String consumerID = ReadSoaHeaderHelper.getHeaderChildren(header, "consumerID");

		//REPIND-1009: no more check using the userID (not relevant)
		/*if (userID == null || userID.matches("[a-zA-Z]?[0-9]+")) {
			userID = null;
		}*/
		
		List<RefProductOwnerDTO> rpos = refProductOwnerDS.getAssociations(type, subType, userID, consumerID);
		if (rpos == null || rpos.isEmpty()) {
			throw new RoleContractUnauthorizedOperationException("300", "This contract cannot be managed by this consumer");
		}
	}

	/**
	 * @return the businessExceptionHelperV1
	 */
	public BusinessExceptionHelper getBusinessExceptionHelperV1() {
		return businessExceptionHelperV1;
	}

	/**
	 * @param businessExceptionHelperV1 the businessExceptionHelperV1 to set
	 */
	public void setBusinessExceptionHelperV1(BusinessExceptionHelper businessExceptionHelperV1) {
		this.businessExceptionHelperV1 = businessExceptionHelperV1;
	}

	/**
	 * @return the actionManager
	 */
	public ActionManager getActionManager() {
		return actionManager;
	}

	/**
	 * @param actionManager the actionManager to set
	 */
	public void setActionManager(ActionManager actionManager) {
		this.actionManager = actionManager;
	}
}
