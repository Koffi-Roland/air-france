package com.afklm.rigui.services;

import com.afklm.rigui.dao.external.ExternalIdentifierRepository;
import com.afklm.rigui.dao.profil.ProfilsRepository;
import com.afklm.rigui.dao.role.BusinessRoleRepository;
import com.afklm.rigui.dao.role.RoleContratsRepository;
import com.afklm.rigui.dao.role.RoleGPRepository;
import com.afklm.rigui.dao.role.RoleUCCRRepository;
import com.afklm.rigui.dto.individu.IndividuDTO;
import com.afklm.rigui.dto.lastactivity.LastActivityDTO;
import com.afklm.rigui.dto.role.RoleContratsDTO;
import com.afklm.rigui.entity.profil.Profils;
import com.afklm.rigui.entity.role.BusinessRole;
import com.afklm.rigui.entity.role.RoleContrats;
import com.afklm.rigui.entity.role.RoleGP;
import com.afklm.rigui.enums.IndividualRestrictedTypesEnum;
import com.afklm.rigui.enums.ProductTypeEnum;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.model.error.BusinessErrorList;
import com.afklm.rigui.model.error.RestError;
import com.afklm.rigui.model.individual.ModelIndividual;
import com.afklm.rigui.model.individual.ModelIndividualResult;
import com.afklm.rigui.model.individual.ModelIndividualResume;
import com.afklm.rigui.model.individual.ModelProfile;
import com.afklm.rigui.model.individual.requests.ModelIndividualRequest;
import com.afklm.rigui.services.adresse.internal.EmailDS;
import com.afklm.rigui.services.adresse.internal.PostalAddressDS;
import com.afklm.rigui.services.adresse.internal.TelecomDS;
import com.afklm.rigui.services.builder.w000442.IndividualRequestBuilder;
import com.afklm.rigui.services.delegation.internal.DelegationDataDS;
import com.afklm.rigui.services.handler.W000442BusinessErrorHandler;
import com.afklm.rigui.services.helper.IndividualHelper;
import com.afklm.rigui.services.individu.internal.AccountDataDS;
import com.afklm.rigui.services.individu.internal.AlertDS;
import com.afklm.rigui.services.individu.internal.CommunicationPreferencesDS;
import com.afklm.rigui.services.individu.internal.IndividuDS;
import com.afklm.rigui.services.preference.internal.PreferenceDS;
import com.afklm.rigui.services.resources.LastActivityService;
import com.afklm.rigui.services.role.internal.BusinessRoleDS;
import com.afklm.rigui.services.role.internal.RoleDS;
import com.afklm.rigui.wrapper.individual.WrapperIndividual;
import com.afklm.rigui.wrapper.individual.WrapperIndividualResult;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000442.v8_0_1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8_0_1.CreateUpdateIndividualServiceV8;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd4.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd4.CreateUpdateIndividualResponse;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title : IndividuAllImpl.java
 * </p>
 * Service Implementation to manage IndividuAll
 * <p>
 * Copyright : Copyright (c) 2018
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */

@Service
public class IndividualService {

	/** logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(IndividualService.class);
	
	@Autowired
	private IndividuDS individuDS;
	
	@Autowired
	private AccountDataDS accountDataDS;
	
	@Autowired
	private RoleGPRepository roleGPRepository;
	
	@Autowired
	private RoleDS roleDS;
	
	@Autowired
	private EmailDS emailDS;
	 
	@Autowired
	private PostalAddressDS adressesDS;

	@Autowired
	private AlertDS alertesDS;

	@Autowired
	private ProfilsRepository profilsRepository;
	
	@Autowired
	private TelecomDS telecomsDS;

	@Autowired
	private CommunicationPreferencesDS commPrefDS;

	@Autowired
	private DelegationDataDS delegationDataDS;

	@Autowired
	private PreferenceDS preferenceDS;
	
	@Autowired
	private BusinessRoleDS businessRoleDS;
	
	@Autowired
	private RoleContratsRepository roleContratsRepository;
	
	@Autowired
	private ExternalIdentifierRepository externalIdentifierRepository;
    
    @Autowired
    private IndividualHelper individualHelper;
    
    @Autowired
    private RoleUCCRRepository roleUCCRRepository;

	@Autowired
	public DozerBeanMapper dozerBeanMapper;
	
	@Autowired
	private IndividualRequestBuilder individualRequestBuilder;
	
	@Autowired
	private BusinessRoleRepository businessRoleRepository;
	
	@Autowired
	@Qualifier("consumerW000442v8")
	private CreateUpdateIndividualServiceV8 createOrUpdateService;

	@Autowired
	private LastActivityService lastActivityService;
	
	public boolean updateIndividual(ModelIndividualRequest modelIndividualRequest) throws SystemException, ServiceException {
		
		String gin = modelIndividualRequest.getIdentification().getGin();
		CreateUpdateIndividualRequest request = individualRequestBuilder.buildUpdateRequest(gin, modelIndividualRequest);
		try {
			CreateUpdateIndividualResponse res = createOrUpdateService.createIndividual(request);
			return res.isSuccess();
		} catch (BusinessErrorBlocBusinessException e) {
			RestError restError = W000442BusinessErrorHandler.handleBusinessErrorException(e);
			throw new ServiceException(restError, HttpStatus.BAD_REQUEST);
		}
		
	}

	@Transactional
    public IndividuDTO getIndividualByIdentifiant(String identifiant) throws JrafDomainException, ServiceException {

    	IndividuDTO ind = getIndividualByGin(identifiant); 
		
		if (ind == null){
			ind = getIndividualByCin(identifiant);
		}
		
		if(ind == null) {			
			throw new ServiceException(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError(), HttpStatus.NOT_FOUND);
		}
		
		return ind;
		
    }

	public String getGinByIdentifiant(String identifiant) throws ServiceException {
		try {
			return getIndividualByIdentifiant(identifiant).getSgin();
		} catch (JrafDomainException e) {
			throw new ServiceException(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError(), HttpStatus.NOT_FOUND);
		}
	}
    
    // @Transactional
	// REPIND-1546 : CRITICAL SONAR because Transactional could not be private so change to Public or delete Transactional
    private IndividuDTO getIndividualByCin(String cin) throws JrafDomainException, ServiceException {
    	
    	/*
    	 * Call DS after JRAF Migration
    	 */
    	RoleContrats rc = roleContratsRepository.findByNumeroContrat(cin);
    	
    	if(rc != null) {
        	String gin = rc.getGin();
        	return getIndividualByGin(gin);
    	} else {
    		return null;
    	}
    }

    // @Transactional
	// REPIND-1546 : CRITICAL SONAR because Transactional could not be private so change to Public or delete Transactional
    private IndividuDTO getIndividualByGin(String identifiant) throws ServiceException {
		
		IndividuDTO ind = null;
		
		try {
			ind = individuDS.getIndividualOrProspectByGinExceptForgotten(identifiant);
		} catch(NoResultException | JrafDomainException e) {
			String msg = "Individual with id " + identifiant + " not found";
			LOGGER.error(msg);
		}
		
		return ind;
	}
	
	@Transactional
	public int isContractAndIndividualHaveSameNumber(String sgin) throws ServiceException {
		
        try {
        	return individuDS.isContractAndIndividualHaveSameNumber(sgin);
		} catch (DataAccessException e) {
			String msg = "Can't count asked number from DB";
			LOGGER.error(msg, e);
			throw new ServiceException(BusinessErrorList.API_CANT_ACCESS_DB.getError(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	public WrapperIndividualResult getListIndividualResult(String identifiant) throws ServiceException {
		
		WrapperIndividualResult wrapperIndividualResult = new WrapperIndividualResult();
        try {
            // Provide individual with GIN
            IndividuDTO individu = getIndividualByIdentifiant(identifiant);
            wrapperIndividualResult.individuals = new ArrayList<ModelIndividualResult>();
            wrapperIndividualResult.individuals.add(getIndividualResults(individu, ""));

            List<String> gins = businessRoleDS.getSginIndByContractNumber(identifiant);
            if (gins.size() > 0) {
                for (int i = 0; i < gins.size(); i++) {
                    individu = getIndividualByIdentifiant(gins.get(i));
                    wrapperIndividualResult.individuals.add(getIndividualResults(individu, identifiant));
                }
            }
        } catch (DataAccessException | JrafDomainException e) {
            String msg = "Can't count asked individuals from DB";
            LOGGER.error(msg, e);
			throw new ServiceException(BusinessErrorList.API_CANT_ACCESS_DB.getError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return wrapperIndividualResult;
	}

	// @Transactional
	// REPIND-1546 : CRITICAL SONAR because Transcational could not be private so change to Public or delete Transactional	
    private ModelIndividualResult getIndividualResults(IndividuDTO individu, String contract) throws JrafDomainException {
    	ModelIndividualResult individualResult = new ModelIndividualResult();
        if (individu != null) {

        	individualResult = dozerBeanMapper.map(individu, ModelIndividualResult.class);
        	individualResult.setAddress(individualHelper.getAdresseFormatResultByGin(adressesDS.getAddressByGin(individualResult.getGin())));
            if (contract.equals("")) {
            	individualResult.setContractNumber(roleDS.getFirstContractNumberOrTypeByGin(individualResult.getGin(), true));
            	individualResult.setContracType(roleDS.getFirstContractNumberOrTypeByGin(individualResult.getGin(), false));
            } else {
            	individualResult.setContractNumber(contract);
                individualResult.setContracType(businessRoleDS.getContractTypeByContractNumber(contract).get(0));
            }
        }
        return individualResult;
    }

	@Transactional
	public WrapperIndividual getIndividualDetailsByIdentifiant(String identifiant) throws JrafDomainException, ServiceException {

		WrapperIndividual wrapperIndividual = new WrapperIndividual();
		try {
			IndividuDTO individual = getIndividualByIdentifiant(identifiant);
			if (individual == null) {
				throw new ServiceException(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			// Extract individual profile directly from the DAO
			Profils profilsEntity = profilsRepository.findBySgin(individual.getSgin());
			
			ModelProfile modelProfile = new ModelProfile();
			if (profilsEntity != null) {
				modelProfile = dozerBeanMapper.map(profilsEntity, ModelProfile.class);
			}

			ModelIndividual modelIndividual = new ModelIndividual();
			modelIndividual = dozerBeanMapper.map(individual, ModelIndividual.class);
			modelIndividual.setProfilsdto(modelProfile);
			// Last activity according to the given gin
			LastActivityDTO lastActivityDTO = this.lastActivityService.getLastActivityByGin(identifiant);
			modelIndividual.setLastActivity(lastActivityDTO);
			
			wrapperIndividual.individu = modelIndividual;
			
			wrapperIndividual.resume = getIndividualResume(individual);
			
		} catch (DataAccessException | JrafDomainException e) {
			String msg = "Can't retrieve asked Individual from DB";
			LOGGER.error(msg, e);
			throw new ServiceException(BusinessErrorList.API_CANT_ACCESS_DB.getError(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
    	return wrapperIndividual;
	}
	
	/**
	 * Return the resume of an individual
	 * 
	 * @param individual:
	 *            individualDTO
	 * @return
	 * @throws JrafDomainException
	 */
	private ModelIndividualResume getIndividualResume(IndividuDTO individual) throws JrafDomainException {
		String individualGIN = individual.getSgin();
		ModelIndividualResume individualResume = new ModelIndividualResume();
		individualResume.flyingBlueContractNumber = this.getOnlyOneFPNumberByGin(individualGIN);
		List<BusinessRole> roles = businessRoleRepository.findByGinInd(individualGIN);
		int flyingBlueContracts = 0, otherContracts = 0;
		for (BusinessRole br : roles) {
			if ("C".equals(br.getType())) {
				RoleContrats rc = roleContratsRepository.findByNumeroContrat(br.getNumeroContrat());
				if (rc != null) {
					if ("FP".equals(rc.getTypeContrat())) {
						flyingBlueContracts++;
					} else {
						otherContracts++;
					}
				}
			}
		}
		individualResume.flyingBlueContractsCount = flyingBlueContracts;
		individualResume.otherContractsCount = otherContracts;
		individualResume.hasFlyingBlueContract = roleDS.isFlyingBlueByGin(individualGIN);
		individualResume.hasMyAccountContract = roleDS.isMyAccountByGin(individualGIN);
		individualResume.personnalTelecomsCount = telecomsDS.getNumberTelecomsPerso(individualGIN);
		individualResume.professionalTelecomsCount = telecomsDS.getNumberTelecomsPro(individualGIN);
		individualResume.optInCommPrefCount = commPrefDS.getNumberOptinComPrefByGin(individualGIN);
		individualResume.optOutCommPrefCount = commPrefDS.getNumberOptoutComPrefByGin(individualGIN);
		individualResume.personnalEmailsCount = emailDS.getNumberPersoEmailByGin(individualGIN);
		individualResume.professionalEmailsCount = emailDS.getNumberProEmailByGin(individualGIN);
		individualResume.personnalAddressesCount = adressesDS.getNumberPersoAddressesByGin(individualGIN);
		individualResume.professionalAddressesCount = adressesDS.getNumberProAddressesByGin(individualGIN);
		individualResume.externalIdentifiersCount = externalIdentifierRepository.findByGin(individualGIN).size();
		individualResume.optInAlertsCount = alertesDS.getNumberOptinAlertsByGin(individualGIN);
		individualResume.optOutAlertsCount = alertesDS.getNumberOptoutAlertsByGin(individualGIN);
		individualResume.preferencesCount = preferenceDS.getPreferencesNumberByGin(individualGIN);
		individualResume.delegatesCount = delegationDataDS.getDelegateNumberByGin(individual);
		individualResume.delegatorsCount = delegationDataDS.getDelegatorNumberByGin(individual);
		individualResume.GPRolesCount = roleGPRepository.getGPRolesCount(individualGIN);
		individualResume.UCCRRolesCount = roleUCCRRepository.findByGin(individualGIN).size();
		individualResume.isAFEmployee = isAFEmployee(individualGIN);
		individualResume.isDoctor = isDoctor(individualGIN);
		individualResume.hasAccountDataDeleted = accountDataDS.isAccountDeleteByGin(individualGIN);
		individualResume.setIsRestrictedTypeIndividual(IndividualRestrictedTypesEnum.isIndividualRestrictedType(individual.getType()));
		return individualResume;
	}
	
	private boolean isAFEmployee(String gin) {
		List<RoleGP> roles = roleGPRepository.getRoleGPByGin(gin);
		for (RoleGP role : roles) {
			// The condition to be AF employee is the order identifier to be 001
			if ("001".equals(role.getOrdreIdentifiant())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the Individual is a Doctor by checking Business Role Doctor
	 * @param gin
	 * @return True if it's a Doctor
	 */
	private boolean isDoctor(String gin) {
		List<BusinessRole> roles = businessRoleRepository.findByGinInd(gin);
		for (BusinessRole role : roles) {
			// The condition to be AF employee is the order identifier to be 001
			if ("D".equals(role.getType())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method to return the last contract updated for the gin
	 * Implemented after change of method getFPNumberByGin which return List<String> instead of a simple String
	 * @param gin
	 * @return
	 * @throws JrafDomainException
	 */
	private String getOnlyOneFPNumberByGin(String gin) throws JrafDomainException {
		List<RoleContratsDTO> fbContracts = roleDS.findRoleContrats(gin, ProductTypeEnum.FLYING_BLUE.toString());
		if(fbContracts.isEmpty() || fbContracts == null) return null;
		RoleContratsDTO fbContractSelected = fbContracts.get(0);
		for(RoleContratsDTO fbContract:fbContracts) {
			if(fbContract.getDateModification().after(fbContractSelected.getDateModification())) {
				fbContractSelected = fbContract;
			}
		}
		return fbContractSelected.getNumeroContrat();
	}

}
