package com.afklm.sicwscustomeridentification.facades;

import com.afklm.sicwscustomeridentification.requestsbuilders.RequestDTOBuilder;
import com.afklm.sicwscustomeridentification.requestsbuilders.ResponseBuilder;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001345.v1.BusinessException;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.request.IdentifyCustomerCrossReferentialRequest;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.response.BusinessError;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.response.IdentifyCustomerCrossReferentialResponse;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.environnement.VariablesDTO;
import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.BuildConditionDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.IdentifyCustomerCrossReferentialDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessExceptionDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ContractIndividualDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.CustomerDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO;
import com.airfrance.repind.util.ReadSoaHeaderHelper;
import com.airfrance.repind.util.SicStringUtils;
import com.sun.xml.ws.api.message.Header;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.soap.SOAPException;
import javax.xml.ws.WebServiceContext;
import java.util.ArrayList;
import java.util.List;


/**
 * Facade between the web service implementation
 * and business treatment call
 * @author t950700
 *
 */
@Service
@Slf4j
public class IdentifyCustomerCrossReferentialFacade {

	/*==========================================*/
	/*                                          */
	/*             INJECTED BEANS               */
	/*                                          */
	/*==========================================*/

	@Autowired
	private IdentifyCustomerCrossReferentialDS identifyCustomerCrossReferentialDSBean;
	
	@Autowired
	@Qualifier("requestDTOBuilderIdentify")
	private RequestDTOBuilder requestDTOBuilder;
	
	@Autowired
	private ResponseBuilder responseBuilder;

	
	/*==========================================*/
	/*                                          */
	/*           PUBLIC METHODS                 */
	/*                                          */
	/*==========================================*/

	/**
	 * Depending on search criteria, this method returns: 
	 * 		- Individuals
	 * 		- Firms
	 * 		- Agencies
	 * @throws SystemException 
	 * @throws BusinessExceptionDTO 
	 */
	public IdentifyCustomerCrossReferentialResponse search(IdentifyCustomerCrossReferentialRequest request, WebServiceContext context) throws BusinessException, SystemException
	{
 		IdentifyCustomerCrossReferentialResponse identifyCustomerCrossReferentialResponse = null;
		
 		// REPIND-1441 : Trace who are consuming us and for what 
 		TraceInput(request, context); 		
 		
		/*
		 * RequestDTO building
		 */
		RequestDTO requestDTO = requestDTOBuilder.build(request);
		
		//REPIND-1808: Add ConsumerId in Requestor
		try {
			requestDTO.getContext().getRequestor().setConsumerId(getConsumerId(context));
		} catch (JrafDomainException e) {
			BusinessError businessError = new BusinessError();
			businessError.setErrorCode("OTHER");
			businessError.setFaultDescription(e.getMessage());
			businessError.setMissingParameter(e.getMessage());
			throw new BusinessException(e.getMessage(), businessError);
		}
		
		/*
		 * Individual search - without calling SearchIndividualByMultiCriteria
		 */
		com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ResponseDTO responseDTO = null;
		try {
			responseDTO = identifyCustomerCrossReferentialDSBean.search(requestDTO);
			
			if ( responseDTO == null){							
				log.info("identifyCustomerCrossReferential | BUSINESS EXCEPTION - DATA NOT FOUND");
				BusinessError error = new BusinessError();		
				error.setErrorCode(RefTableREF_ERREUR._REF_001);
				error.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_001,"EN"));
				// on envoie une exception en sortie du webservice 
				throw new BusinessException(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_001, "EN"), error);
			}
		}
		catch (BusinessExceptionDTO e) 
		{
			BusinessError err = new BusinessError();
			if(e.getFaultInfo() != null)
			{
				if(e.getFaultInfo().getErrorCode() != null)
				{
					err.setErrorCode(e.getFaultInfo().getErrorCode());
				}
				if(e.getFaultInfo().getFaultDescription() != null)
				{
					err.setFaultDescription(e.getFaultInfo().getFaultDescription());
				}
			}
			
			BusinessException be = new BusinessException(err.getErrorCode(), err);
			throw be;
		} 

		try{
			if(BuildConditionDS.isSearchTypeIndividualConditionSet(requestDTO)) {
				//RECUPERATION DU MemberType si CONTRAT FP only, via APPEL OSIRIS !!!!
				if (responseDTO != null){
					List<CustomerDTO> customersList = responseDTO.getCustomers();
					if(customersList != null) {
						List<CustomerDTO> newCustomersList = new ArrayList<CustomerDTO>(); 
						//Pour 1 cutomer 
						for (CustomerDTO customer : customersList) {
							//Get liste des contrats
							List<ContractIndividualDTO> contractIndList = customer.getIndividual().getContractIndividual();
							if(contractIndList != null) {
								// REPIND-1312 : Read database in order to know if service is activate or not.
								VariablesDTO var = null;
								
								List<ContractIndividualDTO> newContratIndList = new ArrayList<ContractIndividualDTO>(); 
								for (ContractIndividualDTO contract : contractIndList) {
									if ( contract.getProductType().equals("FP")) {
										String FB = contract.getContractNumber();
										if ( FB != null) {
											String memberType = getMemberType(requestDTO, FB, context);
											if(memberType != null && !memberType.isEmpty()) {
												contract.setMemberType(memberType);
											}
										}
									}
									//on reconstitue une nlle liste de contrat pour l'individual 
									newContratIndList.add(contract);
								}
								//Update liste des contrats de l'individual
								customer.getIndividual().setContractIndividual(newContratIndList);
							}
							//on reconstitue une nlle liste de customer
							newCustomersList.add(customer); 
						}
						//Update de la response
						responseDTO.setCustomers(newCustomersList);
					}
				}
			}
		} catch (JrafDomainException e) {
			BusinessError businessError = new BusinessError();
			businessError.setErrorCode("OTHER");
			businessError.setFaultDescription(e.getMessage());
			businessError.setMissingParameter(e.getMessage());
			throw new BusinessException(e.getMessage(), businessError);
		} 
		
		/*
		 * IdentifyCustomerCrossReferentialResponse building
		 */
		identifyCustomerCrossReferentialResponse = responseBuilder.build(requestDTO, responseDTO);
		
		return identifyCustomerCrossReferentialResponse;
	}

	
	private String getMemberType(RequestDTO request, String FB, WebServiceContext context) throws JrafDomainException {
		String memberType = "";
		return memberType;
	}

	// REPIND-1441 : Trace who are consuming us and for what 
	private void TraceInput(IdentifyCustomerCrossReferentialRequest request, WebServiceContext context) {
		
		String retour = "W001345V1; ";

		// CONSUMER ID
		retour += SicStringUtils.TraceInputConsumer(context);

		// SITE + SIGNATURE + APPLICATION CODE
		if (request != null) {
			
			if (request.getContext() != null && request.getContext().getRequestor() != null) {
				
				retour += SicStringUtils.TraceInputRequestor("", "", "",
						request.getContext().getRequestor().getSite(), request.getContext().getRequestor().getSignature(), request.getContext().getRequestor().getApplicationCode());
			}
			
			// SEARCH : MANUEL/AUTO DRIVE POPULATION TARGET
			retour += "PRO:" + SicStringUtils.WriteNull(request.getProcessType()) + "; ";
			
			if (request.getContext() != null) {
				retour += "TOS:" + SicStringUtils.WriteNull(request.getContext().getTypeOfSearch()) + "; ";	
				retour += "TOF:" + SicStringUtils.WriteNull(request.getContext().getTypeOfFirm()) + "; ";
				retour += "RT:" + SicStringUtils.WriteNull(request.getContext().getResponseType()) + "; ";
			}
			
			if (request.getSearchIdentifier() != null) {
			
				// INPUT INDIVIDUAL
				if (request.getProvideIdentifier() != null) {
					if (request.getProvideIdentifier().getCustomerGin() != null) {
						retour += "ID:GIN; " ;
					} else {
						retour += "ID:" + request.getProvideIdentifier().getIdentificationType() + "; " ;
					}
				}
	
				// INPUT CONTACT
				if (request.getSearchIdentifier().getPersonsIdentity() != null) {
					retour += "IND:";
					if (request.getSearchIdentifier().getPersonsIdentity().getTitle() != null) {
						retour += "CV|";
					}
					if (request.getSearchIdentifier().getPersonsIdentity().getBirthDate() != null) {
						retour += "BD|";
					}
					if (request.getSearchIdentifier().getPersonsIdentity().getLastName() != null) {
						retour += "LN" + SicStringUtils.WriteNull(request.getSearchIdentifier().getPersonsIdentity().getLastNameSearchType()) + "|";
					}
					if (request.getSearchIdentifier().getPersonsIdentity().getFirstName() != null) {
						retour += "FN" + SicStringUtils.WriteNull(request.getSearchIdentifier().getPersonsIdentity().getFirstNameSearchType()) + "|";
					}
					retour = retour.substring(0, retour.length()-1);
					retour += "; ";
					
					if (request.getSearchIdentifier().getEmail() != null) {
						retour += "EM:" + request.getSearchIdentifier().getEmail().getEmail() + "; " ;	
					}
					
					if (request.getSearchIdentifier().getTelecom() != null) {
						retour += "TEL:" + request.getSearchIdentifier().getTelecom().getPhoneNumber() + "; " ;	
					}
					if (request.getSearchIdentifier().getPostalAddress() != null) {
						retour += "PA:" + request.getSearchIdentifier().getPostalAddress().getZipCode() + "; " ;	
					}
				}
			}
		}
		log.info(retour);
	}
	
	//REPIND-1808: ConsumerId must be retrieved for CCP Calls
	public String getConsumerId(WebServiceContext context) throws JrafDomainException {
		Header header = ReadSoaHeaderHelper.getHeaderFromContext(context, "trackingMessageHeader");
		String consumerId = null;
		
		try {
			consumerId = ReadSoaHeaderHelper.getHeaderChildren(header, "consumerID");
		} catch (SOAPException e) {
			throw new JrafDomainException("Error during retrieve of ConsumerId");
		}

		return consumerId;
	}
}
