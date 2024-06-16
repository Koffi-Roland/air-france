package com.afklm.repind.ws.provideginforuserid;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.common.systemfault.v1.SystemFault;
import com.afklm.soa.stubs.w000422.v1.BusinessException;
import com.afklm.soa.stubs.w000422.v1.ProvideGinForUserIdServiceV1;
import com.afklm.soa.stubs.w000422.v1.data.MyActIdentifierTypeEnum;
import com.afklm.soa.stubs.w000422.v1.request.BusinessError;
import com.afklm.soa.stubs.w000422.v1.request.BusinessErrorTypeEnum;
import com.afklm.soa.stubs.w000422.v1.request.ProvideGinForUserIdRequest;
import com.afklm.soa.stubs.w000422.v1.request.ProvideGinForUserIdResponse;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdRequestDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdResponseDTO;
import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;


/**
 * <p>Title : ProvideGinByIdImpl.java</p>
 * 
 * <p>Copyright : Copyright (c) 2011</p>
 * <p>Company : AIRFRANCE</p>
 * 
 * @author 1091171
 */
@WebService(endpointInterface="com.afklm.soa.stubs.w000422.v1.ProvideGinForUserIdServiceV1", targetNamespace = "http://www.af-klm.com/services/passenger/ProvideGinForUserIdService-v1/wsdl", serviceName = "ProvideGinForUserIdServiceService-v1", portName = "ProvideGinForUserIdService-v1-soap11http")
@Slf4j
public class ProvideGinForUserIdImpl implements ProvideGinForUserIdServiceV1 {


	/*
	 * MyAccountDS reference which support the operation needed by the service
	 * implementation. This ref. is injected by spring.
	 */
	@Autowired
	private MyAccountDS myAccountDS = null;

	
	/**
	 * @see com.afklm.repind.ws.searchindividualinformation.IndividualInformationServiceV1#searchByIdentifier(com.afklm.repind.ws.searchindividualinformation.individualinformationtype.IndividualInformationRequest)
	 */
	public ProvideGinForUserIdResponse provideGinForUserId (ProvideGinForUserIdRequest request) throws BusinessException, SystemException {

			ProvideGinForUserIdResponse resultResponse = new ProvideGinForUserIdResponse();
			
		log.debug("getGinByIdentifier :");
		log.debug("request            : {}",request);
		log.debug("MyAccountDS         : {}",myAccountDS);
			
			
			ProvideGinForUserIdRequestDTO requestDTO = new ProvideGinForUserIdRequestDTO();
			
			// *********************************************************************************
			// 					Vérification des paramètres entrants 
			// *********************************************************************************
			
			// Si les paramètres obligatoires sont absents
			if(request.getCustomerIdentifier() == null || "".equals(request.getCustomerIdentifier()) ||
			   request.getIdentifierType() 	   == null || "".equals(request.getIdentifierType().name()))
			{
				BusinessError error = new BusinessError();
				
				// on récupére le code d'erreur individu à partir des tables de reférences "Missing Parameters"
				error.setReturnCode(BusinessErrorTypeEnum.ERROR_133.value());

				// on envoie une exception en sortie du webservice 
				throw new BusinessException(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_133, "EN"), error);
			}
			
			// On valide les paramètres de la requete demandée
			requestDTO.setIdentifier(request.getCustomerIdentifier());
			requestDTO.setIdentifierType(request.getIdentifierType().name());
			
		log.debug("requestDTO : {}",requestDTO);
			
			// *********************************************************************************
			// 					        Execution de la requête
			// *********************************************************************************
			ProvideGinForUserIdResponseDTO resultDTO = null;
			try {
				// on execute la requete a partir du Domain Service
				resultDTO = myAccountDS.provideGinForUserId(requestDTO);
			} catch (JrafDomainException e) {
				// Une erreur s'est produite
				log.error("provideGinByIdentifier : Problem during provideGinByIdentifier : ", e);
				
			}	
			
			if(resultDTO == null){
				throw new SystemException("result is null", new SystemFault());
			}
			
			// ERR 133 : MISSING PARAMETERS
			if("133".equals(resultDTO.getReturnCode()))
			{
				BusinessError error = new BusinessError();
				error.setReturnCode(BusinessErrorTypeEnum.ERROR_133.value());
				throw new BusinessException(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_133, "EN"), error);
			}
			// ERR 382 : EMAIL ALREADY USED
			else if("382".equals(resultDTO.getReturnCode()))
			{
				BusinessError error = new BusinessError();
				error.setReturnCode(BusinessErrorTypeEnum.ERROR_382.value());
				throw new BusinessException(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_382,"EN"), error);
			}
			// ERR 001 : NOT FOUND
			else if ("001".equals(resultDTO.getReturnCode())) 
			{
				BusinessError error = new BusinessError();
				
				// on récupére le code d'erreur individu à partir des tables de reférences "non trouvé"
				error.setReturnCode(BusinessErrorTypeEnum.ERROR_001.value());
				
				// on envoie une exception en sortie du webservice 
				throw new BusinessException(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_001, "EN"), error);
			}
				
			log.debug("resultDTO : {}",resultDTO);
			resultResponse.setGIN(resultDTO.getGin());
			resultResponse.setFoundIdentifier(MyActIdentifierTypeEnum.valueOf(resultDTO.getFoundIdentifier()));
			log.debug("result : {}",resultResponse);
			return resultResponse;
	}


	public MyAccountDS getMyAccountDS() {
		return myAccountDS;
	}


	public void setMyAccountDS(MyAccountDS myAccountDS) {
		this.myAccountDS = myAccountDS;
	}


}
