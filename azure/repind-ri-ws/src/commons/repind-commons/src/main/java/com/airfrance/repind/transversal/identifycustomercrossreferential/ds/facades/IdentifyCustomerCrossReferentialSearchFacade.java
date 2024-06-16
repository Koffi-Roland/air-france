package com.airfrance.repind.transversal.identifycustomercrossreferential.ds.facades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.IdentifierOptionTypeEnum;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.adh.individualinformation.IndividualInformationRequestDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.BuildConditionDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.BuildQueryDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.CheckMandatoryInputsDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.IdentifyCustomerCrossReferentialTransform;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.*;
import com.airfrance.repind.util.CallersUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.IdentifierOptionTypeEnum;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.agence.AgenceDTO;
import com.airfrance.repind.dto.firme.EtablissementDTO;
import com.airfrance.repind.dto.firme.PersonneMoraleDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.adh.individualinformation.IndividualInformationRequestDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.BusinessException;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.agence.internal.AgenceDS;
import com.airfrance.repind.service.firm.internal.ProvideFirmDataDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.AbstractDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.BuildConditionDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.BuildQueryDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.CheckMandatoryInputsDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.IdentifyCustomerCrossReferentialTransform;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessErrorDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessExceptionDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.PostalAddressIndividualDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ResponseDTO;
import com.airfrance.repind.util.CallersUtils;
import com.airfrance.repind.util.ConstantValues;


@Service
public class IdentifyCustomerCrossReferentialSearchFacade {
	
	/*==========================================*/
	/*                                          */
	/*             INJECTED BEANS               */
	/*                                          */
	/*==========================================*/

	/*
	 * Set Request properties (page size / max results)
	 */
	@Autowired
	@Qualifier("IdentifyBuildQueryDS")
	private BuildQueryDS buildQueryDSBean = null;
	
	/*
	 * Check mandatory inputs domain service
	 */
	@Autowired
	@Qualifier("IdentifierCheckMandatoryInputsDS")
	private CheckMandatoryInputsDS checkMandatoryInputsDSBean = null;

	/*
	 * Facade for calling SearchFirmOnMultiCriteria
	 */
	@Autowired
	private SearchFirmOnMulticriteriaFacade searchFirmOnMulticriteriaFacade = null;

	/*
	 * Facade for calling SearchAgencyOnMultiCriteria
	 */
	@Autowired
	private SearchAgencyOnMulticriteriaFacade searchAgencyOnMulticriteriaFacade = null;
	
	/*
	 * Facade for calling SearchIndividualOnMultiCriteria
	 */
	@Autowired
	private SearchIndividualOnMulticriteriaFacade searchIndividualOnMulticriteriaFacade = null;

	/*
	 * Facade for SearchIndividual and SearchFirm
	 */
	@Autowired
	private SearchIndividualFirmOnMulticriteriaFacade searchIndividualFirmOnMulticriteriaFacade = null;

	/*
	 * Facade for SearchIndividual and SearchAgency
	 */
	@Autowired
	private SearchIndividualAgencyOnMultiCriteriaFacade searchIndividualAgencyOnMulticriteriaFacade = null;
	
	
	/*
	 * Set individual data
	 */
	@Autowired
	private MyAccountDS myAccountDS;

	@Autowired
	private ProvideFirmDataDS provideFirmDataDS;
	
	@Autowired
	protected PostalAddressDS postalAddressDS;
	
	@Autowired
	protected TelecomDS telecomDS;
	
	@Autowired
	protected EmailDS emailDS;
	
	@Autowired
	protected RoleDS roleDS;

	@Autowired
	protected AgenceDS agenceDS;
	
    @Autowired
    private CallersUtils callersUtils;
	
	
	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Check mandatory inputs then executes search operation according to search type
	 * @param requestDTO
	 * @return responseDTO
	 * @throws BusinessExceptionDTO
	 * @throws SystemException
	 * @throws JrafDomainException 
	 */
	public ResponseDTO search(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException
	{
		ResponseDTO responseDTO = null;
		
		/*
		 * Set Request properties (page size / max results)
		 */
		buildQueryDSBean.buildQuery(requestDTO);
		
		/* 
		 * Check mandatory inputs
		 */
		checkMandatoryInputsDSBean.checkMandatoryInputs(requestDTO);
		
		/*
		 * Execute the search according to search type
		 */

		/* DONE : if ProvideIdentifier filled 		=> access by "Provide"
		 * if customerGIN filled then option ='GIN'
		 * if identifier filled then  option = typeOfIdentifier
		 *      : if ProvideIdentifier not specified => access by "Search"
		 */
		
		/*
		 * FOR TypeOfSearch = ['I','F','A'] with ProvideIdentifier specified :
		 *
		 */
		boolean isTypeOfSearchIAF = this.checkTypeOfSearchIAF(requestDTO);
		
		//TYPE OF SEARCH :
		//I : Individual	
		if(BuildConditionDS.isSearchTypeIndividualConditionSet(requestDTO))
		{
			if ( isTypeOfSearchIAF == true ) { 
				//PROVIDE
				responseDTO =  this.searchIndividual(requestDTO);
			} else{
				//SEARCH
				responseDTO = executeIndividualSearch(requestDTO);
			}

		} //F : Firme
		else if(BuildConditionDS.isSearchTypeFirmConditionSet(requestDTO))
		{
			if ( isTypeOfSearchIAF == true) {
				//PROVIDE
				responseDTO = this.searchFirm(requestDTO);
			} else {
				//SEARCH
				responseDTO = executeFirmSearch(requestDTO);
			}

		} //A : Agence
		else if(BuildConditionDS.isSearchTypeAgencyConditionSet(requestDTO))
		{
			if ( isTypeOfSearchIAF == true) {
				//PROVIDE
				responseDTO = this.searchAgency(requestDTO);
			} else {
				//SEARCH
				responseDTO = executeAgencySearch(requestDTO);
			}

		} //IF : Individu + Firme
		else if(BuildConditionDS.isSearchTypeIndividualFirmConditionSet(requestDTO))
		{
			//recherche individu
			if ( isTypeOfSearchIAF == true ) {
				//PROVIDE
				responseDTO = this.searchIndividual(requestDTO);
			}
			responseDTO = executeIndividualFirmSearch(requestDTO, responseDTO);

		} //IA : Individu + Agence
		else if(BuildConditionDS.isSearchTypeIndividualAgencyConditionSet(requestDTO))
		{
			//recherche individu
			if ( isTypeOfSearchIAF == true ) {
				//PROVIDE
				responseDTO = this.searchIndividual(requestDTO);
			}
			responseDTO = executeIndividualAgencySearch(requestDTO, responseDTO);
		}
		
		return responseDTO;
	}





	/*==========================================*/
	/*                                          */
	/*             PRIVATE METHODS              */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Search individual
	 * @param requestDTO
	 * @return responseDTO
	 * @throws BusinessExceptionDTO
	 * @throws SystemException
	 * @throws JrafDomainException 
	 */
	private ResponseDTO executeIndividualSearch(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException {
		ResponseDTO responseDTO = searchIndividualOnMulticriteriaFacade.searchIndividual(requestDTO);
		return responseDTO;
	}


	/**
	 * Search firm
	 * @param requestDTO
	 * @return responseDTO
	 * @throws BusinessExceptionDTO
	 * @throws SystemException
	 */
	private ResponseDTO executeFirmSearch(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException {
		ResponseDTO responseDTO = searchFirmOnMulticriteriaFacade.searchFirm(requestDTO);
		return responseDTO;
	}


	/**
	 * Search agency
	 * @param requestDTO
	 * @return responseDTO
	 * @throws BusinessExceptionDTO
	 * @throws SystemException
	 */
	private ResponseDTO executeAgencySearch(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException {
		ResponseDTO responseDTO = searchAgencyOnMulticriteriaFacade.searchAgency(requestDTO);
		return responseDTO;
	}


	/**
	 * Search individual and its associated firm
	 * @param requestDTO
	 * @return responseDTO
	 * @throws BusinessExceptionDTO
	 * @throws SystemException
	 */
	private ResponseDTO executeIndividualFirmSearch(RequestDTO requestDTO, ResponseDTO responseDTOToUse) throws BusinessExceptionDTO, SystemException {
		ResponseDTO responseDTO = searchIndividualFirmOnMulticriteriaFacade.searchIndividualFirm(requestDTO, responseDTOToUse);
		return responseDTO;
	}


	/**
	 * Search individual and its associated agency
	 * @param requestDTO
	 * @return responseDTO
	 * @throws BusinessExceptionDTO
	 * @throws SystemException
	 */
	private ResponseDTO executeIndividualAgencySearch(RequestDTO requestDTO, ResponseDTO responseDTOToUse) throws BusinessExceptionDTO, SystemException {
		ResponseDTO responseDTO = searchIndividualAgencyOnMulticriteriaFacade.searchIndividualAgency(requestDTO, responseDTOToUse);
		return responseDTO;
	}

    /**
     * checkTypeOfSearchIAF : Check for TypeOfSearch in ['I','A','F'] than AccessByProvide structure is not empty !
     * 
     * @param requestDTO
     * @return boolean
     */
    private boolean checkTypeOfSearchIAF(RequestDTO requestDTO) {

        boolean isTypeOfSearch = false;

        if (requestDTO.getProvideIdentifier() != null) {

            String gin = requestDTO.getProvideIdentifier().getCustomerGin();
            String idType = requestDTO.getProvideIdentifier().getIdentifierType();
            String idValue = requestDTO.getProvideIdentifier().getIdentifierValue();
            isTypeOfSearch = StringUtils.isNotBlank(gin) || StringUtils.isNotBlank(idType) && StringUtils.isNotBlank(idValue);
        }

        return isTypeOfSearch;
    }
	
	
	
	/**
	 * Throws a business exception 
	 * @param code
	 * @param message
	 * @throws BusinessException
	 */
	public void throwBusinessException(String code, String message) throws BusinessExceptionDTO
	{
		if(code == null)
		{
			code = "";
		}
		if(message == null)
		{
			message = "";
		}
		BusinessErrorDTO businessError = new BusinessErrorDTO();
		businessError.setErrorCode(code);
		businessError.setFaultDescription(message);
		businessError.setMissingParameter(message);
		throw new BusinessExceptionDTO(message, businessError);
	}



    /**
     * searchIndividual : Search Individual data
     * 
     * @param requestDTO
     * @return
     * @throws BusinessExceptionDTO
     * @throws SystemException
     */
    private ResponseDTO searchIndividual(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException {

        ResponseDTO responseDTO = null;

        IndividuDTO individuDto = null;

        List<PostalAddressDTO> postalAddressDTOList = new ArrayList<PostalAddressDTO>();
        List<TelecomsDTO> telecomsDTOList = new ArrayList<TelecomsDTO>();
        List<EmailDTO> emailsDTOList = new ArrayList<EmailDTO>();
        List<RoleContratsDTO> roleContratsDTOList = new ArrayList<RoleContratsDTO>();

        try {

            individuDto = this.executeIndividualProvide(requestDTO);

            if (individuDto != null) {

                // Recupéraation des PostalAddress Valides
                postalAddressDTOList = postalAddressDS.findPostalAddress(individuDto.getSgin());
                
                // Recupéraation des telecoms
            	telecomsDTOList = telecomDS.findLatest(individuDto.getSgin());
            	
                // Recupéraation des emails
            	emailsDTOList = emailDS.findEmail(individuDto.getSgin());
            	
                // Recupéraation des contrats
            	roleContratsDTOList = roleDS.findRoleContrats(individuDto.getSgin());
            }
        } catch (JrafDomainException e) {
            
            throwBusinessException("OTHER", e.getMessage());
        }

        if (individuDto != null) {

            List<PostalAddressIndividualDTO> postalAddressIndividualList = IdentifyCustomerCrossReferentialTransform.postalAddressDTOToPostalAddressIndividualDTO(postalAddressDTOList);
            responseDTO = IdentifyCustomerCrossReferentialTransform.individuDtoToResponseDto(requestDTO, individuDto, postalAddressIndividualList, telecomsDTOList, emailsDTOList, roleContratsDTOList);
        }

        return responseDTO;
    }

	/**
	 * executeIndividualProvide
	 * @param requestDTO
	 * @return responseDTO
	 * @throws BusinessExceptionDTO
	 * @throws SystemException
	 * @throws JrafDomainException 
	 */
	private IndividuDTO executeIndividualProvide(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException, JrafDomainException {
		
		IndividualInformationRequestDTO iiRequestDTO = new IndividualInformationRequestDTO();
		  
		String gin = requestDTO.getProvideIdentifier().getCustomerGin();  
		if ( (gin != null) && (gin.replace(" ", "").length() > 0) ) {
			
			iiRequestDTO.setIdentificationNumber(requestDTO.getProvideIdentifier().getCustomerGin());
			iiRequestDTO.setOption(IdentifierOptionTypeEnum.GIN.toString());
			
			//RPEIND-1808: Retrieve Callers only for specific Consumer and Context
			if (callersUtils.isAuthorized(requestDTO.getContext().getRequestor().getConsumerId(), requestDTO.getContext().getRequestor().getContext())) {
				iiRequestDTO.setPopulationTargeted("C");
			}
			
		} else if( requestDTO.getProvideIdentifier().getIdentifierType()!= null && 
		           requestDTO.getProvideIdentifier().getIdentifierValue() != null) {
			
			iiRequestDTO.setIdentificationNumber(requestDTO.getProvideIdentifier().getIdentifierValue());
			iiRequestDTO.setOption(requestDTO.getProvideIdentifier().getIdentifierType());
		}

		IndividuDTO individuDTO = myAccountDS.searchIndividualInformation(iiRequestDTO);
		
		//RPEIND-1808: Retrieve Callers only for specific Consumer and Context
		if (individuDTO != null && "C".equalsIgnoreCase(individuDTO.getType())) {
			if (!callersUtils.isAuthorized(requestDTO.getContext().getRequestor().getConsumerId(), requestDTO.getContext().getRequestor().getContext())) {
				return null;
			}
		}
		
		return individuDTO;
	}

	
	/**
	 * searchFirm : Search Firm data
	 * @param requestDTO
	 * @return
	 * @throws BusinessExceptionDTO
	 * @throws SystemException
	 */
	private ResponseDTO searchFirm(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException {

		ResponseDTO responseDTO = null;
		PersonneMoraleDTO  personneMoraleDto = null;

		String siret = null;
		try {

			personneMoraleDto = this.executeProvideFirmData(requestDTO);

			//N° SIRET disponible uniquement pour les Etablissements
			if ( personneMoraleDto != null && personneMoraleDto.getClass().equals(EtablissementDTO.class)){
				EtablissementDTO etablissement = (EtablissementDTO)personneMoraleDto;

				if ( etablissement.getPostalAddresses() != null){

					List<PostalAddressDTO> postalAddDtoList = etablissement.getPostalAddresses();
					for (PostalAddressDTO postalAdd : postalAddDtoList){
						//on ne recupere le n° siret que pour les firmes françaises
						if (postalAdd.getScode_pays().equals("FR")) {
							siret = etablissement.getSiret();
						}
					}
				}
			}
			/*else if ( personneMoraleDto.getClass().equals(EntrepriseDTO.class)) {}
			  else if ( personneMoraleDto.getClass().equals(ServiceDTO.class)) {}
			  else if ( personneMoraleDto.getClass().equals(GroupeDTO.class)) {}
			*/

		} catch (JrafDomainException e) {
			throwBusinessException("OTHER", e.getMessage());
		}

		if ( personneMoraleDto != null) {
			responseDTO = IdentifyCustomerCrossReferentialTransform.personneMoraleDtoToResponseDto(requestDTO, personneMoraleDto, siret);
		}

		return responseDTO;
	}

	/**
	 * executeProvideFirmData
	 * @param requestDTO
	 * @return
	 * @throws BusinessExceptionDTO
	 * @throws SystemException
	 * @throws JrafDomainException
	 */
	private PersonneMoraleDTO executeProvideFirmData(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException, JrafDomainException {

		PersonneMoraleDTO firmResult=null;

		try {
		String gin = requestDTO.getProvideIdentifier().getCustomerGin();
		List<String> scopeToProvide = Arrays.asList("EMAILS", "TELECOMS", "POSTAL_ADDRESSES", "SYNONYMS", "COMMERCIAL_ZONES", "KEY_NUMBERS");
		if ( (( gin != null )&&(gin.replace(" ", "").length() > 0) ) ) {

			firmResult = provideFirmDataDS.findByEnumSearchWithAllCollections("GIN", gin, "", Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, scopeToProvide);

		} else if( requestDTO.getProvideIdentifier().getIdentifierType()!= null &&
		           requestDTO.getProvideIdentifier().getIdentifierValue() != null) {

			String type = requestDTO.getProvideIdentifier().getIdentifierType();
			String value = requestDTO.getProvideIdentifier().getIdentifierValue();
			firmResult = provideFirmDataDS.findByEnumSearchWithAllCollections(type, value, "", Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, scopeToProvide);
		}
		}
		catch(JrafDomainException j) {
			if(j.getMessage().equals(ConstantValues.NO_ENUM_CONSTANT_EXCEPTION)) {
				AbstractDS.throwBusinessException(AbstractDS.getOtherExceptionReturnCode(), "Invalid identificationType for type of search F");
			}
		}

		return firmResult;
	}


	/**
	 * searchAgency : Search Agency data
	 * @param requestDTO
	 * @return
	 * @throws BusinessExceptionDTO
	 * @throws SystemException
	 */
	private ResponseDTO searchAgency(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException {

		ResponseDTO responseDTO = null;
		AgenceDTO  agenceDto = null;
		try {

			agenceDto = this.executeProvideAgencyData(requestDTO);

		} catch (JrafDomainException e) {
			throwBusinessException("OTHER", e.getMessage());
		}

		if (agenceDto != null) {
		    responseDTO = IdentifyCustomerCrossReferentialTransform.agenceDtoToResponseDto(requestDTO, agenceDto);
		}

		return responseDTO;
	}

	/**
	 * executeProvideAgencyData
	 * @param requestDTO
	 * @return
	 * @throws BusinessExceptionDTO
	 * @throws SystemException
	 * @throws JrafDomainException
	 */
	private AgenceDTO executeProvideAgencyData(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException, JrafDomainException {

		AgenceDTO agenceResult =null;

		String gin = requestDTO.getProvideIdentifier().getCustomerGin();
		List<String> scopeToProvide = Arrays.asList("EMAILS", "TELECOMS", "POSTAL_ADDRESSES", "SYNONYMS", "COMMERCIAL_ZONES", "KEY_NUMBERS");
		if ( (( gin != null )&&(gin.replace(" ", "").length() > 0)) ) {

			agenceResult = agenceDS.findByEnumSearchWithAllCollections("GIN", gin, "", null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, scopeToProvide);

		} else if( requestDTO.getProvideIdentifier().getIdentifierType()!= null &&
		           requestDTO.getProvideIdentifier().getIdentifierValue() != null) {

			String type = requestDTO.getProvideIdentifier().getIdentifierType();
			String value = requestDTO.getProvideIdentifier().getIdentifierValue();
			agenceResult = agenceDS.findByEnumSearchWithAllCollections(type, value, "", null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, scopeToProvide);
		}

		return agenceResult;
	}



}
