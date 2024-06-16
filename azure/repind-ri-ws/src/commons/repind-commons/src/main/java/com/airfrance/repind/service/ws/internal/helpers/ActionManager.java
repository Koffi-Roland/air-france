package com.airfrance.repind.service.ws.internal.helpers;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.ContractConstantValues;
import com.airfrance.ref.util.UList;
import com.airfrance.repind.dto.compref.InformationDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.WarningDTO;
import com.airfrance.repind.dto.individu.WarningResponseDTO;
import com.airfrance.repind.dto.ws.CommunicationPreferencesResponseDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.CreateUpdateRoleContractRequestDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.CreateUpdateRoleContractResponseDTO;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.reference.internal.RefProductComPrefGroupDS;
import com.airfrance.repind.service.reference.internal.RefProductDS;
import com.airfrance.repind.util.transformer.FlyingBlueContractHelperResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service("actionManager")
public class ActionManager {

	private Log logger = LogFactory.getLog(ActionManager.class);

	private static final String CONTRACT_FP = "FP";

	private static final String INVALID_ACTION = "invalid actionCode (values are C, U or D)";
		
	/** warning message */
	private static final String CANNOT_CREATE_COMMUNICATION_PREFERENCES = "Cannot create communication preferences";
	
	/** w001567 context parameter */ 
	public static final String GROUP_CONTEXT = "CREATE_COMPREF";


	@Autowired
	protected IndividuDS individuDS;

	@Autowired
	protected RefProductDS refProductDS;

	@Autowired
	protected CreateOrUpdateRoleUCCRHelper createOrUpdateRoleUCCRHelper;

	@Autowired
	protected CreateOrUpdateFlyingBlueContractHelper createOrUpdateFlyingBlueContractHelper;

	@Autowired
	protected CreateOrUpdateTravelerHelper createTravelerHelperV7;

	@Autowired
	protected CreateOrUpdateSubscribersHelper createOrUpdateSubscribersHelper;

	@Autowired
	protected RefProductComPrefGroupDS refProductComPrefGroupDS;

	@Autowired
	protected CommunicationPreferencesDS communicationPreferenceDS;
	

	public CreateUpdateRoleContractResponseDTO processByActionCode(CreateUpdateRoleContractRequestDTO request) throws JrafDomainException, JrafApplicativeException {
		return processByActionCode(request, true);
	}
		
	public CreateUpdateRoleContractResponseDTO processByActionCode(CreateUpdateRoleContractRequestDTO request, boolean isFBRecognitionActivate) throws JrafDomainException, JrafApplicativeException {
		String contractNumber;
		Boolean success = false;
		CommunicationPreferencesResponseDTO comPrefResponse = null;
		WarningResponseDTO warningResponse = null;

		String productType = request.getContractRequest().getContract().getProductType();
		String productSubType = request.getContractRequest().getContract().getProductSubType();

		String contractType = refProductDS.getContractTypeByProductType(productType, productSubType);

		if (StringUtils.isBlank(contractType)) {
			throw new InvalidParameterException("Bad Product/ContractType " + request.getContractRequest().getContract().getProductType());				
		}

		//Context Parameter not allowed for ActionCode D & U
		if (!StringUtils.isEmpty(request.getRequestor().getContext())) {
			if (!request.getActionCode().equalsIgnoreCase("C") && request.getRequestor().getContext().equalsIgnoreCase(GROUP_CONTEXT)) {
				throw new InvalidParameterException("Context not allowed for ActionCode " + request.getActionCode());
			}
			if (!request.getRequestor().getContext().equalsIgnoreCase(GROUP_CONTEXT)) {
				throw new InvalidParameterException("Context not valid");
			}
		}
		
		switch(contractType) {
			case ContractConstantValues.CONTRACT_TYPE_UCCR:
				contractNumber = createUpdateDeleteRoleUCCR(request);
				success = contractNumber != null && !"".equals(contractNumber);
				break;
			case ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT:
				FlyingBlueContractHelperResponse flyingBlueResponse = createUpdateDeleteRoleContract(request, isFBRecognitionActivate);
				comPrefResponse = flyingBlueResponse.getComPrefResponse();
				warningResponse = flyingBlueResponse.getWarningResponse();
				contractNumber = flyingBlueResponse.getNumberContract();
				success = flyingBlueResponse.getSuccess();
				break;
			case ContractConstantValues.CONTRACT_TYPE_TRAVELER:
				contractNumber = createUpdateDeleteRoleTraveler(request);
				success = contractNumber != null && !"".equals(contractNumber);
				break;
			default:
				logger.warn("No action associated with Product/ContractType '" + request.getContractRequest().getContract().getProductType() + "'");
				throw new InvalidParameterException("Bad Product/ContractType " + request.getContractRequest().getContract().getProductType());				
		}
		
		//If ActionCode = C and Context = CREATE COMRPEF, we use the GROUP to generate the communications preferences
		if (request.getActionCode().equals("C") && !StringUtils.isEmpty(request.getRequestor().getContext()) && request.getRequestor().getContext().equalsIgnoreCase(GROUP_CONTEXT)) {
			Map<InformationDTO, CommunicationPreferencesDTO> response = communicationPreferenceDS.createComPrefBasedOnAGroup(request.getGin(), request.getContractRequest().getContract().getProductType(), request.getContractRequest().getContract().getProductSubType(), null, null, request.getRequestor().getSignature(), request.getRequestor().getSite(), request.getRequestor().getChannel());	
			comPrefResponse = buildCommunicationPreferencesResponseDTO(response); 
			warningResponse = buildWarningResponseDTO(response);
		}

		return buildCreateOrUpdateRoleContractResponse(contractNumber, success, comPrefResponse, warningResponse);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private CommunicationPreferencesResponseDTO buildCommunicationPreferencesResponseDTO(Map<InformationDTO, CommunicationPreferencesDTO> map){
		CommunicationPreferencesResponseDTO response = new CommunicationPreferencesResponseDTO();
		Set<CommunicationPreferencesDTO> set = new HashSet<CommunicationPreferencesDTO>();
		
		for (Map.Entry<InformationDTO, CommunicationPreferencesDTO> e : map.entrySet()) {
			if ("0".equalsIgnoreCase(e.getKey().getCode())) {
				set.add((CommunicationPreferencesDTO)e.getValue());
			}
		}
		
		response.getCommunicationPreferencesDTO().addAll(new ArrayList(set)); 
		
		return response;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private WarningResponseDTO buildWarningResponseDTO(Map<InformationDTO, CommunicationPreferencesDTO> map){
		WarningResponseDTO response = new WarningResponseDTO();
		Set<WarningDTO> set = new HashSet<WarningDTO>();
		for (Map.Entry<InformationDTO, CommunicationPreferencesDTO> e : map.entrySet()) {
			if ("1".equalsIgnoreCase(e.getKey().getCode())) {
				WarningDTO warning = new WarningDTO();
				warning.setWarningCode(CANNOT_CREATE_COMMUNICATION_PREFERENCES);
				warning.setWarningDetails(e.getKey().getName() + " : " + e.getKey().getDetails());
				
				set.add(warning);
			}
		}
		
		response.setWarning(new ArrayList(set)) ;

		return response;
	}
	

	/**
	 * Dispatch the request for role uccr using the action code
	 * @param request
	 * @return
	 * @throws JrafDomainException
	 * @throws JrafApplicativeException
	 */
	private String createUpdateDeleteRoleUCCR(CreateUpdateRoleContractRequestDTO request) throws JrafDomainException, JrafApplicativeException {
		String contractNumber = null;
		if ("C".equals(request.getActionCode())) {
			// Launch checks which are commons for each creation
			creationCommonsChecks(request);
			contractNumber = createOrUpdateRoleUCCRHelper.createRoleUCCR(request);
		} else if ("U".equals(request.getActionCode())) {
			contractNumber =  createOrUpdateRoleUCCRHelper.updateRoleUCCR(request);
		} else if ("D".equals(request.getActionCode())) {
			boolean success = createOrUpdateFlyingBlueContractHelper.deleteRoleFP(request);
		} else {
			logger.error(INVALID_ACTION);
			throw new InvalidParameterException(INVALID_ACTION);
		}
		return contractNumber;
	}

	/**
	 * Chooses the action to execute for role traveler using the action code
	 * @param request
	 * @return
	 * @throws JrafDomainException
	 * @throws JrafApplicativeException
	 */
	private String createUpdateDeleteRoleTraveler(CreateUpdateRoleContractRequestDTO request) throws JrafDomainException, JrafApplicativeException {
		if ("C".equals(request.getActionCode())) {
			// Launch checks which are commons for each creation
			creationCommonsChecks(request);
			return createTravelerHelperV7.createOrUpdateRoleTr(request);
		} else if ("U".equals(request.getActionCode())) { 
			checkIfGinExist(request);
			return createTravelerHelperV7.createOrUpdateRoleTr(request);
		} else if ("D".equals(request.getActionCode())) {
			return null;
		}
		else {
			logger.error(INVALID_ACTION);
			throw new InvalidParameterException(INVALID_ACTION);
		}
	}

	/**
	 * Chooses the action to execute for role contract using the action code
	 * @param request
	 * @return
	 * @throws JrafDomainException
	 */
	private FlyingBlueContractHelperResponse createUpdateDeleteRoleContract(CreateUpdateRoleContractRequestDTO request) throws JrafDomainException, JrafApplicativeException {
		return createUpdateDeleteRoleContract(request, true);
	}
	private FlyingBlueContractHelperResponse createUpdateDeleteRoleContract(CreateUpdateRoleContractRequestDTO request, boolean isFBRecognitionActivate) throws JrafDomainException, JrafApplicativeException {
		if(createOrUpdateFlyingBlueContractHelper.checkIfGinIsTraveler(request) && "C".equals(request.getActionCode())){
			createOrUpdateFlyingBlueContractHelper.UpdateGinTravelerToIndividu(request);
		}
		if (contractIsFlyingBlue(request.getContractRequest().getContract().getProductType())) {
			return createUpdateDeleteRoleFP(request, isFBRecognitionActivate);
		} else {
			String numContract = createOrUpdateSubscribersHelper.createUpdateDeleteSubscribersHelper(request);

			FlyingBlueContractHelperResponse fbchr = new FlyingBlueContractHelperResponse();
			if (Boolean.TRUE.toString().equals(numContract)) {
				fbchr.setSuccess(true);
			} else {
				fbchr.setNumberContract(numContract);
			}

			return fbchr;
		}
	}

	/**
	 * Launch the action to execute for Flying Blue role contract using the action code.
	 * @param request
	 * @return
	 * @throws JrafDomainException
	 * @throws JrafApplicativeException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public FlyingBlueContractHelperResponse createUpdateDeleteRoleFP(CreateUpdateRoleContractRequestDTO request, boolean isFBRecognitionActivate) throws JrafDomainException, JrafApplicativeException {
		if ("C".equals(request.getActionCode())) {
			// Launch checks which are commons for each creation
			creationCommonsChecks(request);
			return createOrUpdateFlyingBlueContractHelper.createRoleFP(request, isFBRecognitionActivate);
		} else if ("U".equals(request.getActionCode())) {
			return createOrUpdateFlyingBlueContractHelper.updateRoleFP(request, isFBRecognitionActivate);
		} else if ("D".equals(request.getActionCode())) {
			FlyingBlueContractHelperResponse fbchr = new FlyingBlueContractHelperResponse();
			fbchr.setSuccess(createOrUpdateFlyingBlueContractHelper.deleteRoleFP(request));
			return fbchr;
		} else {
			logger.error(INVALID_ACTION);
			throw new InvalidParameterException(INVALID_ACTION);
		}
	}

	private void checkInput(CreateUpdateRoleContractRequestDTO request) throws InvalidParameterException, MissingParameterException {
		if (request.getContractRequest().getContract().getContractType() != null
				&& checkContractType(request.getContractRequest().getContract().getContractType())) {
			logger.error("Invalid Parameter : invalid contractType (values are U, I, A, C, B, N, G, F, S, T)");
			throw new InvalidParameterException("invalid ContractType (values are U, I, A, C, B, N, G, F, S, T)");
		}

		if(request.getContractRequest().getContract().getContractStatus() == null) {
			logger.error("Missing Parameter : contractStatus is mandatory");
			throw new MissingParameterException("contractStatus is mandatory");
		} else if(checkContractStatus(request.getContractRequest().getContract().getContractStatus())) {
			logger.error("Invalid Parameter : invalid contractStatus (values are U, P, C, A or S)");
			throw new InvalidParameterException("invalid contractStatus (values are U, P, C, A or S)");
		}

	}

	private CreateUpdateRoleContractResponseDTO buildCreateOrUpdateRoleContractResponse(String contractNumber,
			Boolean success, CommunicationPreferencesResponseDTO comPref, WarningResponseDTO warningResponse) {

		CreateUpdateRoleContractResponseDTO response = new CreateUpdateRoleContractResponseDTO();

		if (contractNumber != null && !contractNumber.isEmpty()) {
			response.setSuccess(true);
			response.setContractNumber(contractNumber);

			if (!(comPref == null || UList.isNullOrEmpty(comPref.getCommunicationPreferencesDTO()))) {
				response.setCommunicationPreferenceResponse(comPref);
			}

			if (!(warningResponse == null || UList.isNullOrEmpty(warningResponse.getWarning()))) {
				response.setWarningResponse(warningResponse);
			}

		} else {
			response.setSuccess(success);
		}
		return response;
	}

	// REPIND-V44 : Ano on a inverse contratType et contratStatus
	private boolean checkContractType(String contractType) {
		if ("U".equals(contractType) || "I".equals(contractType) || "C".equals(contractType)
				|| "A".equals(contractType) || "B".equals(contractType) || "N".equals(contractType)
				|| "G".equals(contractType) || "F".equals(contractType) || "S".equals(contractType) || "T".equals(contractType)) {
			return false;
		}
		return true;	
	}

	// REPIND-V44 : Ano on a inverse contratType et contratStatus
	private boolean checkContractStatus(String contractStatus) {
		if("U".equals(contractStatus) || "P".equals(contractStatus) || "C".equals(contractStatus) ||
				"A".equals(contractStatus) || "S".equals(contractStatus) ) {
			return false;
		}
		return true;
	}

	private void checkIfGinExist(CreateUpdateRoleContractRequestDTO request) throws MissingParameterException {
		if (request.getGin() == null || request.getGin().isEmpty()) {
			logger.error("Missing Parameter : Gin is mandatory");
			throw new MissingParameterException("Gin is mandatory");
		}
	}

	/**
	 * Launch checks which are commons for each creation
	 * @param request
	 * @throws JrafDomainException
	 */
	private void creationCommonsChecks(CreateUpdateRoleContractRequestDTO request) throws JrafDomainException {
		checkInput(request);
		checkIfGinExist(request);
		// For creation we check if the individual exist
		IndividuDTO individuDTO = individuDS.getByGin(request.getGin());

		// If individual is not found we return a NotFoundException
		if (individuDTO == null) {
			logger.error("Individual not found : Gin = " + request.getGin());
			throw new NotFoundException("Gin = " + request.getGin());
		}
	}

	private boolean contractIsFlyingBlue(String contractType) {
		return CONTRACT_FP.equals(contractType);
	}

}
