package com.airfrance.repind.service.delegation.internal;

import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.DelegationActionEnum;
import com.airfrance.ref.type.DelegationSenderEnum;
import com.airfrance.ref.type.DelegationTypeEnum;
import com.airfrance.repind.dao.delegation.DelegationDataRepository;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.delegation.DelegationDataInfoDTO;
import com.airfrance.repind.dto.delegation.DelegationDataTransform;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.IndividuTransform;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.reference.RefDelegationInfoTypeDTO;
import com.airfrance.repind.dto.tracking.TrackingDTO;
import com.airfrance.repind.entity.delegation.DelegationData;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.marketing.HandleCommunication;
import com.airfrance.repind.service.reference.internal.RefDelegationInfoKeyTypeDS;
import com.airfrance.repind.service.reference.internal.RefDelegationInfoTypeDS;
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

	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

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
	public DelegationDataDTO getWithLinkedData(DelegationDataDTO dto) throws JrafDomainException {
		return getWithLinkedData(dto.getDelegationId());
	}


    @Transactional(readOnly=true)
	public DelegationDataDTO getWithLinkedData(Serializable oid) throws JrafDomainException {
		Optional<DelegationData> delegationData = delegationDataRepository.findById((int) oid);
		if (!delegationData.isPresent())
			return null;
		
		return DelegationDataTransform.bo2Dto(delegationData.get());
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateDelegationData(String gin, List<DelegationDataDTO> delegationDataListDTO,
			SignatureDTO signatureDTO) throws JrafDomainException {
		updateDelegationData(gin, delegationDataListDTO, signatureDTO, null);
	}

	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateDelegationData(String gin, List<DelegationDataDTO> delegationDataListDTO,
			SignatureDTO signatureDTO, String managingCompany) throws JrafDomainException {

		if (delegationDataListDTO == null || delegationDataListDTO.isEmpty()) {
			return;
		}

		for (DelegationDataDTO delegationDataDTO : delegationDataListDTO) {
			updateDelegationData(gin, delegationDataDTO, signatureDTO, managingCompany);
		}
	}

	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateUltimateDelegationData(DelegationDataDTO delegationDataDTO, SignatureDTO signatureDTO,
			String managingCompany) throws JrafDomainException {

		if (delegationDataDTO == null) {
			return;
		}

		if (signatureDTO == null) {
			throw new MissingParameterException("Signature must be set");
		}

		if (signatureDTO.getSignature() == null) {
			throw new MissingParameterException("Signature must be set");
		}

		if (signatureDTO.getSite() == null) {
			throw new MissingParameterException("Signature must be set");
		}

		// check mandatory fields
		checkMandatoryFields(delegationDataDTO);

		// check validity
		DelegationActionEnum statusFromWS = DelegationActionEnum.getEnumMandatory(delegationDataDTO.getStatus());
		isDelegationTypeValid(delegationDataDTO);

		// get gins
		String delegateDTOgin = delegationDataDTO.getDelegateDTO().getSgin();
		String delegatorDTOgin = delegationDataDTO.getDelegatorDTO().getSgin();

		// get individuals
		IndividuDTO delegatorDTO = individuDS.get(delegationDataDTO.getDelegatorDTO());
		IndividuDTO delegateDTO = individuDS.get(delegationDataDTO.getDelegateDTO());

		// check if delegation members exist
		// ERROR 706
		if (delegatorDTO == null || delegatorDTO.getSgin() == null) {
			throw new DelegationGinNotFoundException(delegatorDTOgin);
		}
		if (delegateDTO == null || delegateDTO.getSgin() == null) {
			throw new DelegationGinNotFoundException(delegateDTOgin);
		}

		// if the gins are identical
		// ERROR 707
		if (delegateDTOgin.equals(delegatorDTOgin)) {
			throw new DelegationGinsIdenticalException(delegatorDTOgin);
		}

		// Check if the gins are linked to accountdatas
		// ERROR 708
		if (delegateDTO.getAccountdatadto() == null) {
			throw new DelegationGinWithoutAccountException(delegateDTOgin);
		}
		if (delegatorDTO.getAccountdatadto() == null) {
			throw new DelegationGinWithoutAccountException(delegatorDTOgin);
		}

		delegationDataDTO.setDelegateDTO(delegateDTO);
		delegationDataDTO.setDelegatorDTO(delegatorDTO);

		DelegationDataDTO delegationFoundDTO = findDelegation(delegationDataDTO);

		// if a delegation isnt found (based on TYPE, and the two GINS)
		if (delegationFoundDTO == null) {
			// it s created
			delegationDataDTO.prepareForCreation(signatureDTO);

			if (statusFromWS != DelegationActionEnum.ACCEPTED) {
				throw new DelegationActionException(delegateDTOgin, delegatorDTOgin);
			}
			delegationDataDTO.setSender(DelegationSenderEnum.DELEGATE.toString());
			create(delegationDataDTO);

		} else {

			// get info from DB
			DelegationActionEnum statusFromDB = DelegationActionEnum.getEnum(delegationFoundDTO.getStatus());

			if (statusFromDB != DelegationActionEnum.ACCEPTED || statusFromWS != DelegationActionEnum.DELETED) {
				throw new DelegationActionException(delegateDTOgin, delegatorDTOgin);
			}

			// Mapping of the 2 delegation objects so we can update
			delegationDataDTO.setDelegationId(delegationFoundDTO.getDelegationId());
			delegationDataDTO.setSender(DelegationSenderEnum.DELEGATE.toString());
			delegationDataDTO.setCreationDate(delegationFoundDTO.getCreationDate());
			delegationDataDTO.setCreationSignature(delegationFoundDTO.getCreationSignature());
			delegationDataDTO.setCreationSite(delegationFoundDTO.getCreationSite());

			delegationDataDTO.prepareForUpdate(signatureDTO, delegationFoundDTO);
			update(delegationDataDTO);
		}

	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateDelegationData(String gin, DelegationDataDTO delegationDataDTO, SignatureDTO signatureDTO,
			String managingCompany) throws JrafDomainException {

		if (delegationDataDTO == null) {
			return;
		}

		if (signatureDTO == null) {
			throw new MissingParameterException("Signature must be set");
		}

		if (signatureDTO.getSignature() == null) {
			throw new MissingParameterException("Signature must be set");
		}

		if (signatureDTO.getSite() == null) {
			throw new MissingParameterException("Signature must be set");
		}

		// check mandatory fields
		checkMandatoryFields(delegationDataDTO);

		// check validity
		DelegationActionEnum statusFromWS = DelegationActionEnum.getEnumMandatory(delegationDataDTO.getStatus());
		isDelegationTypeValid(delegationDataDTO);

		// Check if delegation is for kid solo or not, delegation with type UA or UM
		boolean isKidSoloDelegation = isDelegationTypeKidSolo(delegationDataDTO);

		if (isKidSoloDelegation) {
			checkMandatoryFieldsForKidSolo(delegationDataDTO);
		}

		// get gins
		String delegateDTOgin = delegationDataDTO.getDelegateDTO().getSgin();
		String delegatorDTOgin = delegationDataDTO.getDelegatorDTO().getSgin();

		// get sender from WS
		DelegationSenderEnum senderFromWS = computeSender(gin, delegatorDTOgin, delegateDTOgin);

		// get individuals
		IndividuDTO delegatorDTO = individuDS.get(delegationDataDTO.getDelegatorDTO());
		IndividuDTO delegateDTO = individuDS.get(delegationDataDTO.getDelegateDTO());

		// check if delegation members exist
		// ERROR 706
		if (delegatorDTO == null || delegatorDTO.getSgin() == null) {
			throw new DelegationGinNotFoundException(delegatorDTOgin);
		}
		if (delegateDTO == null || delegateDTO.getSgin() == null) {
			throw new DelegationGinNotFoundException(delegateDTOgin);
		}

		// These conditions are not required for Kid Solo Delegation
		if (!isKidSoloDelegation) {
			// if the gins are identical
			// ERROR 707
			if (delegateDTOgin.equals(delegatorDTOgin)) {
				throw new DelegationGinsIdenticalException(gin);
			}
			// Check if the gins are linked to accountdatas
			// ERROR 708
			if (delegateDTO.getAccountdatadto() == null) {
				throw new DelegationGinWithoutAccountException(delegateDTOgin);
			}
		}

		if (delegatorDTO.getAccountdatadto() == null) {
			throw new DelegationGinWithoutAccountException(delegatorDTOgin);
		}

		delegationDataDTO.setDelegateDTO(delegateDTO);
		delegationDataDTO.setDelegatorDTO(delegatorDTO);

		DelegationDataDTO delegationFoundDTO = findDelegation(delegationDataDTO);
		if (isKidSoloDelegation) {
			// Process of creation of update Delegation for Kid Solo
			/**
			 * TODO : KID SOLO PROCESS CREATION AND UPDATE
			 */
			if (delegationFoundDTO == null) {
				delegationDataDTO.prepareForCreation(signatureDTO);

				if (statusFromWS != DelegationActionEnum.ACCEPTED) {
					throw new DelegationActionException(delegateDTOgin, delegatorDTOgin);
				}
				delegationDataDTO.setSender(DelegationSenderEnum.DELEGATOR.toString());
				checkDelegationData(delegationDataDTO);

				createWithLinkedData(delegationDataDTO);
			} else {
				checkDelegationData(delegationDataDTO);
				// get info from DB
				DelegationActionEnum statusFromDB = DelegationActionEnum.getEnum(delegationFoundDTO.getStatus());

				// Test if the status into DB or WS is good
				if ((statusFromDB != DelegationActionEnum.ACCEPTED && statusFromDB != DelegationActionEnum.DELETED)
						|| (statusFromWS != DelegationActionEnum.ACCEPTED
								&& statusFromWS != DelegationActionEnum.DELETED)) {
					throw new DelegationActionException(delegateDTOgin, delegatorDTOgin);
				}

				// Mapping of the 2 delegation objects so we can update
				delegationDataDTO.setDelegationId(delegationFoundDTO.getDelegationId());
				delegationDataDTO.setSender(DelegationSenderEnum.DELEGATOR.toString());
				delegationDataDTO.setCreationDate(delegationFoundDTO.getCreationDate());
				delegationDataDTO.setCreationSignature(delegationFoundDTO.getCreationSignature());
				delegationDataDTO.setCreationSite(delegationFoundDTO.getCreationSite());

				delegationDataDTO.prepareForUpdate(signatureDTO, delegationFoundDTO);

				updateWithLinkedData(delegationDataDTO);
			}

			// Regular Delegation process
		} else {
			// if a delegation isnt found (based on TYPE, and the two GINS)
			if (delegationFoundDTO == null) {
				// it s created
				delegationDataDTO.prepareForCreation(signatureDTO);

				if (statusFromWS != DelegationActionEnum.INVITED) {
					throw new DelegationActionException(delegateDTOgin, delegatorDTOgin);
				}

				delegationDataDTO.setSender(senderFromWS.toString());

				// we could set the first status with INVITED whatever happens
				// delegationDataDTO.setStatus(DelegationActionEnum.INVITED.toString());
				if (senderFromWS == DelegationSenderEnum.DELEGATOR) {
					sendEmailCRMPush(delegatorDTOgin, delegateDTOgin, "INV_EMP", managingCompany);
				}

				if (senderFromWS == DelegationSenderEnum.DELEGATE) {
					sendEmailCRMPush(delegatorDTOgin, delegateDTOgin, "INV_TM", managingCompany);
				}

				create(delegationDataDTO);

			} else {

				// get info from DB
				DelegationActionEnum statusFromDB = DelegationActionEnum.getEnum(delegationFoundDTO.getStatus());

				// if the line in DB has a "closed" status, we wont create or update. It s not a
				// normal behavior
				if (statusFromDB != DelegationActionEnum.INVITED && statusFromDB != DelegationActionEnum.ACCEPTED) {
					throw new DelegationActionException(delegateDTOgin, delegatorDTOgin);
				}
				// Mapping of the 2 delegation objects so we can update
				delegationDataDTO.setDelegationId(delegationFoundDTO.getDelegationId());
				delegationDataDTO.setSender(senderFromWS.toString());
				delegationDataDTO.setCreationDate(delegationFoundDTO.getCreationDate());
				delegationDataDTO.setCreationSignature(delegationFoundDTO.getCreationSignature());
				delegationDataDTO.setCreationSite(delegationFoundDTO.getCreationSite());

				String sender = delegationFoundDTO.getSender();
				String ginSender = null;
				if (sender.equals(DelegationSenderEnum.DELEGATOR.toString())) {
					ginSender = delegationFoundDTO.getDelegatorDTO().getSgin();
				} else if (sender.equals(DelegationSenderEnum.DELEGATE.toString())) {
					ginSender = delegationFoundDTO.getDelegateDTO().getSgin();
				}

				switch (statusFromWS) {
				/*
				 * case INVITED: if(statusFromDB!=DelegationActionEnum.INVITED)
				 * { create(delegationDataDTO); } break;
				 */
				case REJECTED:
					if (!gin.equals(ginSender) && statusFromDB == DelegationActionEnum.INVITED) {

						delegationDataDTO.prepareForUpdate(signatureDTO, delegationFoundDTO);
						update(delegationDataDTO);

						if (senderFromWS == DelegationSenderEnum.DELEGATOR) {
							sendEmailCRMPush(delegatorDTOgin, delegateDTOgin, "REJ_EMP", managingCompany);
						} else if (senderFromWS == DelegationSenderEnum.DELEGATE) {
							sendEmailCRMPush(delegatorDTOgin, delegateDTOgin, "REJ_TM", managingCompany);
						}

					} else {
						throw new DelegationActionException(delegateDTOgin, delegatorDTOgin);
					}
					break;
				case CANCELLED:
					if (gin.equals(ginSender) && statusFromDB == DelegationActionEnum.INVITED) {

						delegationDataDTO.prepareForUpdate(signatureDTO, delegationFoundDTO);
						update(delegationDataDTO);

					} else {
						throw new DelegationActionException(delegateDTOgin, delegatorDTOgin);
					}
					break;
				case ACCEPTED:
					if (!gin.equals(ginSender) && statusFromDB == DelegationActionEnum.INVITED) {

						delegationDataDTO.prepareForUpdate(signatureDTO, delegationFoundDTO);
						update(delegationDataDTO);

						if (senderFromWS == DelegationSenderEnum.DELEGATOR) {
							sendEmailCRMPush(delegatorDTOgin, delegateDTOgin, "ACC_EMP", managingCompany);
						} else if (senderFromWS == DelegationSenderEnum.DELEGATE) {
							sendEmailCRMPush(delegatorDTOgin, delegateDTOgin, "ACC_TM", managingCompany);
						}

					} else {
						throw new DelegationActionException(delegateDTOgin, delegatorDTOgin);
					}
					break;
				case DELETED:
					if (statusFromDB == DelegationActionEnum.ACCEPTED) {

						delegationDataDTO.prepareForUpdate(signatureDTO, delegationFoundDTO);
						update(delegationDataDTO);

						if (senderFromWS == DelegationSenderEnum.DELEGATOR) {
							sendEmailCRMPush(delegatorDTOgin, delegateDTOgin, "DEL_EMP", managingCompany);
						} else if (senderFromWS == DelegationSenderEnum.DELEGATE) {
							sendEmailCRMPush(delegatorDTOgin, delegateDTOgin, "DEL_TM", managingCompany);
						}

					} else {
						throw new DelegationActionException(delegateDTOgin, delegatorDTOgin);
					}
					break;
				default:
					throw new DelegationActionException(delegateDTOgin, delegatorDTOgin);
				}
			}
		}
	}

	/**
	 * Find all delegators of an individual with following criteria
	 *
	 * <ul>
	 * <li>status = ACCEPTED or INVITED</li>
	 * </ul>
	 * <b>***************************************************************************</b><br>
	 * <br>
	 * <b> Warning, in context UM (KIDSOLO), the vocabulary is reversed.</b><br>
	 * <ul>
	 * <li><u>Delegator : Individual which declare UM and Attendant </u></li>
	 * <li><u>Delegate : UM (Unaccompanied minor) or Attendant (Person accompanying
	 * minor)</u></li>
	 * </ul>
	 * <br>
	 * <b>***************************************************************************</b><br>
	 */

    @Transactional(readOnly=true)
	public List<DelegationDataDTO> findDelegator(String gin) throws JrafDomainException {

		List<DelegationData> delegatorList = delegationDataRepository.findDelegator(gin);

		// provide delegation data with individual
		List<DelegationDataDTO> delegatorDTOList = DelegationDataTransform.bo2Dto(delegatorList);

		// fill individual data for provide
		for (DelegationDataDTO delegatorDTO : delegatorDTOList) {
			DelegationTypeEnum type = DelegationTypeEnum.fromString(delegatorDTO.getType());
			if (type == DelegationTypeEnum.UNACOMPAGNED_MINOR
					|| type == DelegationTypeEnum.UNACOMPAGNED_MINOR_ATTENDANT) {
				// For KID SOLO, we must not provide data such as FB_IDENTIFER,
				// EMAIL_IDENTIFIER...
				// To be compliant with GDPR, that's why we are removing those data here
				delegatorDTO.setDelegateDTO(removeIndividualDataForKidSolo(delegatorDTO.getDelegateDTO()));
			} else {
				fillIndividualDataForProvide(delegatorDTO.getDelegatorDTO());
			}
		}

		return delegatorDTOList;

	}

	/**
	 * Find all delegates of an individual with following criteria
	 *
	 * <ul>
	 * <li>status = ACCEPTED or INVITED</li>
	 * </ul>
	 * <b>***************************************************************************</b><br>
	 * <br>
	 * <b> Warning, in context UM (KIDSOLO), the vocabulary is reversed.</b><br>
	 * <ul>
	 * <li><u>Delegator : Individual which declare UM and Attendant </u></li>
	 * <li><u>Delegate : UM (Unaccompanied minor) or Attendant (Person accompanying
	 * minor)</u></li>
	 * </ul>
	 * <br>
	 * <b>***************************************************************************</b><br>
	 *
	 */

    @Transactional(readOnly=true)
	public List<DelegationDataDTO> findDelegate(String gin) throws JrafDomainException {

		List<DelegationData> delegateList = delegationDataRepository.findDelegate(gin);

		// provide delegation data with individual
		List<DelegationDataDTO> delegateDTOList = DelegationDataTransform.bo2Dto(delegateList);

		for (DelegationDataDTO delegateDTO : delegateDTOList) {

			DelegationTypeEnum type = DelegationTypeEnum.fromString(delegateDTO.getType());
			if (type == DelegationTypeEnum.UNACOMPAGNED_MINOR
					|| type == DelegationTypeEnum.UNACOMPAGNED_MINOR_ATTENDANT) {
				// For KID SOLO, we must not provide data such as FB_IDENTIFER,
				// EMAIL_IDENTIFIER...
				// To be compliant with GDPR, that's why we are removing those data here
				delegateDTO.setDelegateDTO(removeIndividualDataForKidSolo(delegateDTO.getDelegateDTO()));

			} else {
				// fill individual data for provide
				fillIndividualDataForProvide(delegateDTO.getDelegateDTO());
			}
		}

		return delegateDTOList;

	}

	/**
	 * Find a delegation
	 */

    @Transactional(readOnly=true)
	public DelegationDataDTO findDelegation(DelegationDataDTO delegationDTO) throws JrafDomainException {

		// individual links are needed for search
		DelegationData delegationData = DelegationDataTransform.dto2Bo(delegationDTO);

		DelegationData delegationFound = delegationDataRepository.findDelegation(delegationData);

		DelegationDataDTO delegationFoundDTO = null;

		if (delegationFound != null) {
			// provide individual links
			delegationFoundDTO = DelegationDataTransform.bo2Dto(delegationFound);
		}

		return delegationFoundDTO;

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
	 * This method return if the type of delegation is a valid type. Replace
	 * oldly Enum used for doing that
	 *
	 * @param delegationDataDTO
	 * @return
	 */
	public boolean isDelegationTypeValid(DelegationDataDTO delegationDataDTO) {
		return delegationDataRepository.isDelegationTypeValid(delegationDataDTO.getType());
	}

	/**
	 * Return if the type of the delegation is a kid solo type (UM or UA)
	 *
	 * @param delegationDataDTO
	 * @return
	 */
	public boolean isDelegationTypeKidSolo(DelegationDataDTO delegationDataDTO) {
		return isDelegationTypeKidSolo(delegationDataDTO.getType())
				|| isDelegationTypeKidSolo(delegationDataDTO.getType());
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

	private boolean delegationDataInfoTypeExistsInDB(String type) throws JrafDomainException {
		List<RefDelegationInfoTypeDTO> refDelegationInfoTypeList = refDelegationInfoTypeDS.findByType(type);
		if (refDelegationInfoTypeList == null) {
			DelegationDataDS.log.debug("delegationInfo type not found");
			return false;
		} else {
			DelegationDataDS.log.debug("found " + refDelegationInfoTypeList.size() + " delegation of type " + type);
			return (refDelegationInfoTypeList.size() > 0);
		}
	}

	private void checkDelegationData(DelegationDataDTO delegationDataDTO) throws JrafDomainException {
		if (delegationDataDTO.getDelegationDataInfoDTO() == null) {
			return;
		}

		for (DelegationDataInfoDTO d : delegationDataDTO.getDelegationDataInfoDTO()) {
			if (d.getType() == null || d.getType().isEmpty()) {
				throw new MissingParameterException("Missing type of delegationDataInfo");
			}

			if (!delegationDataInfoTypeExistsInDB(d.getType())) {
				throw new InvalidParameterException("Unknown delegationInfo type: " + d.getType());
			}
			controlKeyValue(d.getType(), d.getKey(), d.getValue());
		}
	}

	private void controlKeyValue(String type, String key, String value) throws JrafDomainException {
		if (key == null || key.isEmpty()) {
			throw new MissingParameterException("Missing key for type : " + type);
		}

		if (value == null || value.isEmpty()) {
			throw new MissingParameterException("The value for " + key + " not be null or empty");
		}

		if (!refDelegationInfoKeyTypeDS.refKeyAndTypeExist(key, type, value)) {
			throw new InvalidParameterException("Unknown delegationInfo key : " + key + " for type : " + type);
		}
	}

	
	public void updateDelegationStatus(DelegationDataDTO delegationDataDTO) throws JrafDomainException {
		if (delegationDataDTO != null) {
			delegationDataRepository.saveAndFlush(DelegationDataTransform.dto2Bo(delegationDataDTO));
		}
	}

	
	public int getDelegateNumberByGin(IndividuDTO i) {
		try {
			return delegationDataRepository.getDelegateNumberByGin(IndividuTransform.dto2BoLight(i));
		} catch (JrafDomainException e) {
			log.error(e.getMessage());
			return 0;
		}
	}

	
	public int getDelegatorNumberByGin(IndividuDTO i) {
		try {
			return delegationDataRepository.getDelegatorNumberByGin(IndividuTransform.dto2BoLight(i));
		} catch (JrafDomainException e) {
			log.error(e.getMessage());
			return 0;
		}
	}

    public void deleteDelegateLink(String updatedGin) throws JrafDomainException {
		if (updatedGin != null) {
			List<DelegationData> links = delegationDataRepository.findDelegate(updatedGin);

			if (links != null && !links.isEmpty()) {
				delegationDataRepository.deleteInBatch(links);
			}
		}
    }
}
