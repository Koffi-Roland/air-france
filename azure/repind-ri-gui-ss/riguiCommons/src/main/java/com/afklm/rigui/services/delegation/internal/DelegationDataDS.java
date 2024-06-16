package com.afklm.rigui.services.delegation.internal;

import com.afklm.rigui.exception.*;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.exception.jraf.JrafDomainNoRollbackException;
import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;
import com.afklm.rigui.enums.DelegationActionEnum;
import com.afklm.rigui.enums.DelegationSenderEnum;
import com.afklm.rigui.enums.DelegationTypeEnum;
import com.afklm.rigui.dao.delegation.DelegationDataRepository;
import com.afklm.rigui.dto.adresse.TelecomsDTO;
import com.afklm.rigui.dto.delegation.DelegationDataDTO;
import com.afklm.rigui.dto.delegation.DelegationDataInfoDTO;
import com.afklm.rigui.dto.delegation.DelegationDataTransform;
import com.afklm.rigui.dto.individu.AccountDataDTO;
import com.afklm.rigui.dto.individu.IndividuDTO;
import com.afklm.rigui.dto.individu.IndividuTransform;
import com.afklm.rigui.dto.individu.SignatureDTO;
import com.afklm.rigui.dto.reference.RefDelegationInfoTypeDTO;
import com.afklm.rigui.entity.delegation.DelegationData;
import com.afklm.rigui.services.adresse.internal.PostalAddressDS;
import com.afklm.rigui.services.adresse.internal.TelecomDS;
import com.afklm.rigui.services.individu.internal.AccountDataDS;
import com.afklm.rigui.services.individu.internal.IndividuDS;
import com.afklm.rigui.services.marketing.HandleCommunication;
import com.afklm.rigui.services.reference.internal.RefDelegationInfoKeyTypeDS;
import com.afklm.rigui.services.reference.internal.RefDelegationInfoTypeDS;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.*;

@Service
public class DelegationDataDS {

	private static final Log log = LogFactory.getLog(DelegationDataDS.class);

	@Autowired
	private DelegationDataRepository delegationDataRepository;

	@Autowired
	private IndividuDS individuDS;

	@Autowired
	private TelecomDS telecomDS;

	@Autowired
	private PostalAddressDS postalAddressDS;

	@Autowired
	private AccountDataDS accountDataDS;

	@Autowired
	@Qualifier("refDelegationInfoTypeDS")
	private RefDelegationInfoTypeDS refDelegationInfoTypeDS;

	@Autowired
	@Qualifier("refDelegationInfoKeyTypeDS")
	private RefDelegationInfoKeyTypeDS refDelegationInfoKeyTypeDS;

	@Autowired
	ApplicationContext appContext;

	public TelecomDS getTelecomDS() {
		return telecomDS;
	}

	public void setTelecomDS(TelecomDS telecomDS) {
		this.telecomDS = telecomDS;
	}

	public AccountDataDS getAccountDataDS() {
		return accountDataDS;
	}

	public void setAccountDataDS(AccountDataDS accountDataDS) {
		this.accountDataDS = accountDataDS;
	}

	public PostalAddressDS getPostalAddressDS() {
		return postalAddressDS;
	}

	public void setPostalAddressDS(PostalAddressDS postalAddressDS) {
		this.postalAddressDS = postalAddressDS;
	}
	
	public void setDelegationDataRepository(DelegationDataRepository delegationDataRepository) {
		this.delegationDataRepository = delegationDataRepository;
	}

    @Transactional(readOnly=true)
	public Integer countWhere(DelegationDataDTO dto) throws JrafDomainException {
		DelegationData delegationData = DelegationDataTransform.dto2BoLight(dto);
		return (int) delegationDataRepository.count(Example.of(delegationData));
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(DelegationDataDTO delegationDataDTO) throws JrafDomainException {
		// create with individual links
		DelegationData delegationData = DelegationDataTransform.dto2Bo(delegationDataDTO);

		// create in database (call the abstract class)
		delegationDataRepository.saveAndFlush(delegationData);

		// get delegations with links
		DelegationDataTransform.bo2Dto(delegationData, delegationDataDTO);
	}

	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createWithLinkedData(DelegationDataDTO delegationDataDTO) throws JrafDomainException {
		DelegationData delegationData = DelegationDataTransform.dto2BoForCreation(delegationDataDTO);

		// create in database (call the abstract class)
		delegationDataRepository.saveAndFlush(delegationData);
		
		// update DTO
		DelegationDataTransform.bo2DtoLight(delegationData, delegationDataDTO);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateWithLinkedData(DelegationDataDTO delegationDataDTO) throws JrafDomainException {

		// get delegationData in database
		Optional<DelegationData> delegationDataOpt = delegationDataRepository.findById(delegationDataDTO.getDelegationId());
		DelegationData delegationData = delegationDataOpt.get();
		
		if (delegationDataDTO.getStatus().equals("D")) {
			delegationData.setStatus("D");
		} else {
			delegationData = DelegationDataTransform.dto2BoForCreation(delegationDataDTO);
		}

		delegationDataRepository.saveAndFlush(delegationData);

		// update BO
		DelegationDataTransform.bo2DtoLight(delegationData, delegationDataDTO);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(DelegationDataDTO dto) throws JrafDomainException {
		DelegationData delegationData = DelegationDataTransform.dto2BoForCreation(dto);
		delegationDataRepository.delete(delegationData);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(Serializable oid) throws JrafDomainException {
		delegationDataRepository.deleteById((int) oid);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void update(DelegationDataDTO delegationDataDTO) throws JrafDomainException {
		// chargement du bo
		Optional<DelegationData> delegationDataOpt = delegationDataRepository.findById(delegationDataDTO.getDelegationId());
		DelegationData delegationData = delegationDataOpt.get();

		// transformation dto -> bo avec liens individu
		DelegationDataTransform.dto2Bo(delegationDataDTO, delegationData);
	}

    @Transactional(readOnly=true)
	public List<DelegationDataDTO> findByExample(DelegationDataDTO dto) throws JrafDomainException {
		DelegationData delegationData = DelegationDataTransform.dto2BoLight(dto);
		List<DelegationDataDTO> results = new ArrayList<>();
		for (DelegationData result : delegationDataRepository.findAll(Example.of(delegationData))) {
			results.add(DelegationDataTransform.bo2DtoLight(result));
		}
		return results;
	}


    @Transactional(readOnly=true)
	public DelegationDataDTO get(DelegationDataDTO dto) throws JrafDomainException {
		return get(dto.getDelegationId());
	}

    @Transactional(readOnly=true)
	public DelegationDataDTO get(Serializable oid) throws JrafDomainException {
		Optional<DelegationData> delegationData = delegationDataRepository.findById((int) oid);
		if (!delegationData.isPresent())
			return null;
		return DelegationDataTransform.bo2DtoLight(delegationData.get());
	}

    @Transactional(readOnly=true)
	public DelegationDataDTO getWithLinkedData(Serializable oid) throws JrafDomainException {
		Optional<DelegationData> delegationData = delegationDataRepository.findById((int) oid);
		if (!delegationData.isPresent())
			return null;
		
		return DelegationDataTransform.bo2Dto(delegationData.get());
	}
	protected void checkMandatoryFields(DelegationDataDTO delegationDataDTO) throws MissingParameterException {

		if (delegationDataDTO.getDelegateDTO() == null) {
			throw new MissingParameterException("Delegate data is missing");
		}

		if (delegationDataDTO.getDelegatorDTO() == null) {
			throw new MissingParameterException("Delegator data is missing");
		}

		if (StringUtils.isEmpty(delegationDataDTO.getDelegateDTO().getSgin())) {
			throw new MissingParameterException("Delegate GIN is mandatory");
		}

		if (StringUtils.isEmpty(delegationDataDTO.getDelegatorDTO().getSgin())) {
			throw new MissingParameterException("Delegator GIN is mandatory");
		}

		if (StringUtils.isEmpty(delegationDataDTO.getStatus())) {
			throw new MissingParameterException("Delegation action is mandatory");
		}

		if (StringUtils.isEmpty(delegationDataDTO.getType())) {
			throw new MissingParameterException("Delegation type is mandatory");
		}

	}

	protected void checkMandatoryFieldsForKidSolo(DelegationDataDTO delegationDataDTO)
			throws MissingParameterException {
		/**
		 * TODO : Implement check with DB
		 */
	}

	/**
	 * This method is aimed to fill individual data for provide:
	 *
	 * <ul>
	 * <li>telecoms data</li>
	 * <li>account data</li>
	 * </ul>
	 *
	 * @param individuDTO
	 * @throws JrafDomainException
	 */
	private void fillIndividualDataForProvide(IndividuDTO individuDTO) throws JrafDomainException {

		String gin = individuDTO.getSgin();

		// get telecoms data
		List<TelecomsDTO> telecomDTOList = telecomDS.findLatest(gin);

		// get account data
		AccountDataDTO accountDataDTO = accountDataDS.getByGin(gin);
		// if no emailIdentifier, the last valid email is returned
		if (accountDataDTO != null && accountDataDTO.getEmailIdentifier() == null) {
			String lastValidEmail = accountDataDS.getLastValidEmailForGin(gin);
			accountDataDTO.setEmailIdentifier(lastValidEmail);
		}

		// add telecoms data
		if (telecomDTOList != null) {
			individuDTO.setTelecoms(new HashSet<>(telecomDTOList));
		}

		// add account data
		if (accountDataDTO != null) {
			individuDTO.setAccountdatadto(accountDataDTO);
		}

	}

	/**
	 * This method is aimed to remove individual data for provide for KID SOLO
	 * context:
	 * @param individu
	 * @throws JrafDomainException
	 */
	private IndividuDTO removeIndividualDataForKidSolo(IndividuDTO individu) {

		IndividuDTO individuDTOforKS = new IndividuDTO();
		individuDTOforKS.setSgin(individu.getSgin());
		individuDTOforKS.setCivilite(individu.getCivilite());
		individuDTOforKS.setPrenom(individu.getPrenom());
		individuDTOforKS.setNom(individu.getNom());

		return individu;
	}

	public void sendEmailCRMPush(String gin_delegator, String gin_delegate, String campaignId, String managinCompany) {
		try {
			HashMap<String, IndividuDTO> delegationInfos = getDelegationInfosForHandleComm(gin_delegator, gin_delegate);
			String emailDelegator = individuDS.getLastValidEmail(gin_delegator);
			String emailDelegate = individuDS.getLastValidEmail(gin_delegate);
			// Call HandleCommunication Service
			HandleCommunication handleComm = new HandleCommunication(appContext);
			handleComm.askHandleCommDelegationAction(delegationInfos, campaignId, emailDelegator, emailDelegate,
					managinCompany);
		} catch (Exception e) {
			// e.printStackTrace();
			DelegationDataDS.log.fatal(e);
		}
	}

	public HashMap<String, IndividuDTO> getDelegationInfosForHandleComm(String gin_delegator, String gin_delegate)
			throws JrafDomainException {
		HashMap<String, IndividuDTO> delegationMap = new HashMap<>();
		IndividuDTO delegatorIndividu = individuDS.getByGin(gin_delegator);
		IndividuDTO delegateIndividu = individuDS.getByGin(gin_delegate);

		delegationMap.put("delegator", delegatorIndividu);
		delegationMap.put("delegate", delegateIndividu);
		return delegationMap;
	}

	private DelegationSenderEnum computeSender(String requestGIN, String delegatorGIN, String delegateGIN)
			throws InvalidParameterException {

		DelegationSenderEnum sender = null;

		if (requestGIN.equals(delegatorGIN)) {
			sender = DelegationSenderEnum.DELEGATOR;
		} else if (requestGIN.equals(delegateGIN)) {
			sender = DelegationSenderEnum.DELEGATE;
		} else {
			throw new InvalidParameterException("Cannot determine the sender, invalid parameter");
		}

		return sender;
	}

	/**
	 * Check if the type passed is UM or UA (compliant Kid Solo)
	 *
	 * @param type
	 * @return
	 */
	public boolean isDelegationTypeKidSolo(String type) {
		return _isDelegationFromThisType(type, "UM") || _isDelegationFromThisType(type, "UA");
	}

	/**
	 * Check if type passed are similar or not
	 *
	 * @param typeToCheck
	 * @param refType
	 * @return
	 */
	private boolean _isDelegationFromThisType(String typeToCheck, String refType) {
		if (!StringUtils.isNotBlank(typeToCheck) && !StringUtils.isNotBlank(refType)) {
			return false;
		}
        // REPIND-1398 : Test SONAR NPE
		if (typeToCheck != null) {
			return typeToCheck.equals(refType);
		} else {
			return false;
		}
	}

	public int getDelegateNumberByGin(IndividuDTO i) {
		try {
			return delegationDataRepository.countDelegateNumberByGin(IndividuTransform.dto2BoLight(i).getSgin());
		} catch (JrafDomainException e) {
			log.error(e.getMessage());
			return 0;
		}
	}

	
	public int getDelegatorNumberByGin(IndividuDTO i) {
		try {
			return delegationDataRepository.countDelegatorNumberByGin(IndividuTransform.dto2BoLight(i).getSgin());
		} catch (JrafDomainException e) {
			log.error(e.getMessage());
			return 0;
		}
	}

}
