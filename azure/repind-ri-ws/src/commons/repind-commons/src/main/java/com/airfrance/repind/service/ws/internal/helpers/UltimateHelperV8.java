package com.airfrance.repind.service.ws.internal.helpers;

import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.DelegationActionEnum;
import com.airfrance.ref.type.YesNoFlagEnum;
import com.airfrance.repind.dao.environnement.VariablesRepository;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.service.ws.internal.type.UltimateBusinessErrorCode;
import com.airfrance.repind.service.ws.internal.type.UltimateFamilyTypeCode;
import com.airfrance.repind.service.ws.internal.type.UltimateInputErrorCode;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

;

@Service
public class UltimateHelperV8 {

	protected static class Ultimate {

		private final String _delegatorGin;
		private final String _delegateGin;
		private final String _delegationType;
		private final String _delegationStatus;
		private final SignatureDTO _signatureAPP;
		private final String _managingCompany;
		private final String _originGin;
		private IndividuDTO _delegator;
		private IndividuDTO _delegate;
		private AccountDataDTO _delegatorAccount;
		private AccountDataDTO _delegateAccount;
		private IndividuDS _individuDS;
		private DelegationDataDS _delegationDataDS;
		private RoleDS _roleDS;
		private CommunicationPreferencesDS _communicationPreferencesDS;
		private VariablesRepository _variablesRepository;
		private UltimateInputErrorCode _lastError;

		protected Ultimate(final String delegatorGin, final String delegateGin, final String delegationType,
				final String delegationStatus, final SignatureDTO signatureAPP, final String managingCompany,
				final String gin) {

			_delegatorGin = delegatorGin;
			_delegateGin = delegateGin;
			_delegationType = delegationType;
			_signatureAPP = signatureAPP;
			_managingCompany = managingCompany;
			_delegationStatus = delegationStatus;
			_originGin = gin;
			_delegator = new IndividuDTO();
			_delegator.setSgin(_delegatorGin);
			_delegate = new IndividuDTO();
			_delegate.setSgin(_delegateGin);
		}

		private void _createDelegation() throws JrafDomainException {
			final DelegationDataDTO delegationData = new DelegationDataDTO();
			delegationData.setDelegateDTO(_delegate);
			delegationData.setDelegatorDTO(_delegator);
			delegationData.setType(_delegationType);
			delegationData.setStatus(_delegationStatus);

			UltimateHelperV8.LOGGER.info("Create delegation data, status: PENDING");
			_delegationDataDS.updateUltimateDelegationData(delegationData, _signatureAPP, _managingCompany);
			UltimateHelperV8.LOGGER.info("Create delegation data, status: DONE");

		}

		private boolean _delegationAlreadyExist() throws JrafDomainException {
			// Check if the delegation doesn't already exist. If already exist, it s an
			// update, so it s fine
			DelegationDataDTO delegation = new DelegationDataDTO();
			delegation.setDelegateDTO(_delegate);
			delegation.setDelegatorDTO(_delegator);
			delegation.setType(_delegationType);
			if (_delegationDataDS.findDelegation(delegation) != null) {
				UltimateHelperV8.LOGGER.info("Delegation already exist, updating");
				return true;
			}
			return false;
		}

		protected boolean _isDelegatorAlreadyLinkedToAUltimateCustomer() throws UltimateException, JrafDomainException {

			final List<DelegationDataDTO> linkedUltimate = UltimateHelperV8
					.UltimateDelegations(_delegationDataDS.findDelegate(_delegatorGin));

			if (_delegationAlreadyExist()) {
				return false;
			}

			int linkSize = 0;
			// REPIND-1546 : NPE on SONAR
			if (linkedUltimate != null) {			
				linkSize = linkedUltimate.size();
			}
			UltimateHelperV8.LOGGER.info("Number of linked ultimate to this delegator:" + linkSize);

			if (linkSize == 0) {
				return false;
			} else if (linkSize == 1) {
				return true;
			}
			final String message = "More than 1 ultimate customer link to this delegator: " + _delegatorGin;
			UltimateHelperV8.LOGGER.error(message);
			throw new UltimateException(message);
		}

		protected boolean _isInputValid() throws JrafDomainException, UltimateException {
			
			if(StringUtils.equalsIgnoreCase(_delegationStatus, DelegationActionEnum.DELETED.toString()) && _delegationAlreadyExist()){
				// If we want to delete the delegation, no need for any business check
				return true;
			}
			
			if (!_delegateGin.equals(_originGin)) {
				_lastError = UltimateInputErrorCode.SENDER_MUST_BE_THE_DELEGATE;
				return false;
			}
			
			// Test if delegator exist
			try {
				_delegator = _individuDS.getByGin(_delegatorGin);
			} catch (final JrafDomainException e) {
				_lastError = UltimateInputErrorCode.DELEGATOR_DOESNT_EXIST;
				return false;
			}

			// Test if delegator exist
			if (_delegator == null) {
				_lastError = UltimateInputErrorCode.DELEGATOR_DOESNT_EXIST;
				return false;
			}

			_delegatorAccount = _delegator.getAccountdatadto();

			// Test if delegator have an account data
			if (_delegatorAccount == null) {
				_lastError = UltimateInputErrorCode.DELEGATOR_DOESNT_HAVE_ACCOUNT_DATA;
				return false;
			}

			// Test if delegator is Flying Blue customer
			if (StringUtils.isEmpty(_delegatorAccount.getFbIdentifier())) {
				_lastError = UltimateInputErrorCode.DELEGATOR_IS_NOT_A_FLYING_BLUE_CUSTOMER;
				return false;
			}

			// Test if delegator is an ultimate customer himself						
			
			// REPIND-1804 : Get the Ultimate COM PREF
			final CommunicationPreferencesDTO comPrefDelegator = _communicationPreferencesDS.findComPrefId(_delegatorAccount.getSgin(), "U", "S", "UL_PS");
			if (comPrefDelegator != null && !YesNoFlagEnum.NO.toString().equals(comPrefDelegator.getSubscribe())){
//				final RoleContratsDTO delegatorRole = _roleDS.findRoleContratsFP(_delegatorAccount.getFbIdentifier());
//				final String delegatorMemberType = delegatorRole.getMemberType();
//				if (UltimateCustomerTypeCode.IsUltimateCustomerType(delegatorMemberType)) {
				_lastError = UltimateInputErrorCode.DELEGATOR_IS_ULTIMATE_CUSTOMER_HIMSELF;
				return false;
			}

			// Test if delegate exist
			try {
				_delegate = _individuDS.getByGin(_delegateGin);
			} catch (final JrafDomainException e) {
				_lastError = UltimateInputErrorCode.DELEGATE_DOESNT_EXIST;
				return false;
			}

			// Test if delegate exist
			if (_delegate == null) {
				_lastError = UltimateInputErrorCode.DELEGATE_DOESNT_EXIST;
				return false;
			}

			_delegateAccount = _delegate.getAccountdatadto();

			// Test if delegate have an account data
			if (_delegateAccount == null) {
				_lastError = UltimateInputErrorCode.DELEGATE_DOESNT_HAVE_ACCOUNT_DATA;
				return false;
			}

			// Test if delegator is Flying Blue customer
			if (StringUtils.isEmpty(_delegateAccount.getFbIdentifier())) {
				_lastError = UltimateInputErrorCode.DELEGATE_IS_NOT_A_FLYING_BLUE_CUSTOMER;
				return false;
			}

			// Test if delegator is an ultimate customer

			// REPIND-1804 : Get the Ultimate COM PREF
			final CommunicationPreferencesDTO comPrefDelegate = _communicationPreferencesDS.findComPrefId(_delegateAccount.getSgin(), "U", "S", "UL_PS");
			if (comPrefDelegate == null) { 
// 				final RoleContratsDTO delegateRole = _roleDS.findRoleContratsFP(_delegateAccount.getFbIdentifier());
//				final String delegateMemberType = delegateRole.getMemberType();
//				if (!UltimateCustomerTypeCode.IsUltimateCustomerType(delegateMemberType)) {
				_lastError = UltimateInputErrorCode.DELEGATE_IS_NOT_A_ULTIMATE_CUSTOMER;
				// UltimateHelperV8.LOGGER.error(delegateMemberType);
				return false;
			}

			// Test if ultimate type is accepted
			if (UltimateFamilyTypeCode.IsUltimateFamilyType(_delegationType)) {
				return true;
			}

			_lastError = UltimateInputErrorCode.ULTIMATE_DELEGATION_TYPE_NOT_ACCEPTED;
			return false;
		}

		protected boolean _isUltimateFamilyTooBig() throws JrafDomainException, UltimateException {

			final int maximumFamilySize = Integer
					.parseInt(_variablesRepository.findById("ULTIMATE_FAMILY_MAX_SIZE").get().getEnvValue());

			if (_delegationAlreadyExist()) {
				return false;
			}

			UltimateHelperV8.LOGGER.info("Start searching for delegation by delegate: " + _delegateGin);
			final List<DelegationDataDTO> family = UltimateHelperV8
					.UltimateDelegations(_delegationDataDS.findDelegator(_delegateGin));

			int familySize = 0;
			
			// REPIND-1546 : NPE on SONAR
			if (family != null) {						
				familySize = family.size();
			}
			
			UltimateHelperV8.LOGGER.info("Family size:" + familySize);
			if (familySize < maximumFamilySize) {
				return false;
			}
			if (familySize == maximumFamilySize) {
				return true;
			}
			final String message = "Family size is already bigger than maximum family size: " + familySize;
			UltimateHelperV8.LOGGER.error(message);
			throw new UltimateException(message);

		}

		protected void _process(final IndividuDS individu, final DelegationDataDS delegationData, final RoleDS role,
				final CommunicationPreferencesDS communicationPreferences, final VariablesRepository variables) throws UltimateException, JrafDomainException {

			_individuDS = individu;
			_delegationDataDS = delegationData;
			_communicationPreferencesDS = communicationPreferences;
			_roleDS = role;
			_variablesRepository = variables;

			if (!_isInputValid()) {
				final String message = "Input parameter invalid: " + _lastError.name();
				UltimateHelperV8.LOGGER.error(message);
				throw new UltimateException(message);
			}

			if (_isUltimateFamilyTooBig()) {
				final String message = "Business error: " + UltimateBusinessErrorCode.TOO_MANY_FAMILY_MEMBERS.name();
				UltimateHelperV8.LOGGER.error(message);
				throw new UltimateException(message);
			}

			if (_isDelegatorAlreadyLinkedToAUltimateCustomer()) {
				final String message = "Business error: "
						+ UltimateBusinessErrorCode.ALREADY_LINKED_TO_A_ULTIMATE_CUSTOMER.name();
				UltimateHelperV8.LOGGER.error(message);
				throw new UltimateException(message);
			}

			_createDelegation();

		}

	}

	private static final Log LOGGER = LogFactory.getLog(UltimateHelperV8.class);

	/**
	 * Return a sub list of the original, containing only non-ultimate
	 * DelegationDataDTO
	 *
	 * @param origin
	 *            the origin list of DelegationDataDTO
	 * @return a new list with the same elements as origin, minus ultimate
	 *         delegationDataDTO
	 *
	 */
	public static List<DelegationDataDTO> NonUltimateDelegations(final List<DelegationDataDTO> origin) {
		if (origin == null) {
			return null;
		}
		final List<DelegationDataDTO> nonUltimateDelegation = new ArrayList<>();
		for (final DelegationDataDTO delegation : origin) {
			// REPIND-1804 : NO NPE
			if (delegation != null && !UltimateFamilyTypeCode.IsUltimateFamilyType(delegation.getType())) {
				nonUltimateDelegation.add(delegation);
			}
		}
		return nonUltimateDelegation;
	}

	/**
	 * Return a sub list of the original, containing only ultimate
	 * DelegationDataDTO
	 *
	 * @param origin
	 *            the origin list of DelegationDataDTO
	 * @return a new list with the same elements as origin, minus non-ultimate
	 *         delegationDataDTO
	 *
	 */
	public static List<DelegationDataDTO> UltimateDelegations(final List<DelegationDataDTO> origin) {
		if (origin == null) {
			return null;
		}
		final List<DelegationDataDTO> ultimateDelegation = new ArrayList<>();
		for (final DelegationDataDTO delegation : origin) {
			if (UltimateFamilyTypeCode.IsUltimateFamilyType(delegation.getType())) {
				ultimateDelegation.add(delegation);
			}
		}
		return ultimateDelegation;

	}

	@Autowired
	private IndividuDS individuDS;

	@Autowired
	private DelegationDataDS delegationDataDS;

	@Autowired
	protected RoleDS roleDS;

	@Autowired
	protected CommunicationPreferencesDS communicationPreferencesDS;
	
	@Autowired
	private VariablesRepository variablesRepository;

	public int createUltimateFamilyLinks(final List<DelegationDataDTO> delegations, final SignatureDTO signatureAPP,
			final String managingCompany, final String gin) throws JrafDomainException, UltimateException {

		if (delegations == null) {
			return 0;
		}

		/*
		 * Données disponible via le webservice createorupdateindividualV7:
		 * DelegationDataDTO directement. On considere que l'individu recu en
		 * requete principale est le delegator (le membre ultimate), étant
		 * donnée que le cas d'utilisation sera qu'un ultimate va demander a
		 * ajouter des membres de sa famille a son compte. On recupere ensuite
		 * la liste de delegate a partir du code pre existant, ce qui devrait
		 * normalement nous permettre d'assigner plusieurs membres de la famille
		 * à un ultimate en 1 seule requete
		 */
		UltimateHelperV8.LOGGER.info("Processing ultimate family link: STARTING");
		int count = 0;

		for (final DelegationDataDTO delegation : delegations) {

			// REPIND-1804 : No NPE
			if (delegation == null) {
				UltimateHelperV8.LOGGER.info("Processing ultimate family link: ABORT");
				continue;
			}
			
			// on ne dispose que du gin dans les individuDTO fourni
			final IndividuDTO delegator = delegation.getDelegatorDTO();
			final IndividuDTO delegate = delegation.getDelegateDTO();
			final String delegationType = delegation.getType();

			if (!UltimateFamilyTypeCode.IsUltimateFamilyType(delegationType)) {
				UltimateHelperV8.LOGGER
						.info("Element rejected: not an ultimate type. Type received: " + delegationType);
				continue;
			}

			UltimateHelperV8.LOGGER.info("Processing ultimate family link, element " + count + ": STARTING");

			if (delegator == null) {
				throw new MissingParameterException("Delegator must not be null");
			}

			if (delegate == null) {
				throw new MissingParameterException("Delegate must not be null");
			}

			final Ultimate ultimate = CreateUltimateInstance(delegator.getSgin(), delegate.getSgin(), delegationType,
					delegation.getStatus(), signatureAPP, managingCompany, gin);

			try {
				ultimate._process(individuDS, delegationDataDS, roleDS, communicationPreferencesDS, variablesRepository);
			} catch (JrafDomainException | UltimateException e) {
				UltimateHelperV8.LOGGER.error("Processing ultimate family link, element " + count + ": ERROR");
				UltimateHelperV8.LOGGER.error("Processing ultimate family link: ABORDED");
				throw e;
			}

			UltimateHelperV8.LOGGER.info("Processing ultimate family link, element " + count + ": SUCCESS");
			count++;
		}

		UltimateHelperV8.LOGGER.info("Processing ultimate family link: SUCCESS");
		return count;

	}

	protected Ultimate CreateUltimateInstance(final String delegatorGin, final String delegateGin,
			final String delegationType, final String delegationStatus, final SignatureDTO signature,
			final String managingCompany, final String gin) {
		return new Ultimate(delegatorGin, delegateGin, delegationType, delegationStatus, signature, managingCompany,
				gin);
	}

}
