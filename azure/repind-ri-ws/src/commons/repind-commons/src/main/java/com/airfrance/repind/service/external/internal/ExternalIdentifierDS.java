package com.airfrance.repind.service.external.internal;

import com.airfrance.ref.exception.IndivudualNotFoundException;
import com.airfrance.ref.exception.InvalidLanguageCodeException;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.external.InvalidExternalIdentifierDataKeyException;
import com.airfrance.ref.exception.external.InvalidExternalIdentifierDataValueException;
import com.airfrance.ref.exception.external.InvalidExternalIdentifierTypeException;
import com.airfrance.ref.exception.external.InvalidPnmIdException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.YesNoFlagEnum;
import com.airfrance.ref.type.external.*;
import com.airfrance.ref.util.FormatUtils;
import com.airfrance.repind.dao.external.ExternalIdentifierRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDataDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierTransform;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.IndividuTransform;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.entity.external.ExternalIdentifier;
import com.airfrance.repind.entity.external.ExternalIdentifierData;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.internal.unitservice.external.ExternalIdentifierUS;
import com.airfrance.repind.service.reference.internal.RefLanguageDS;
import com.airfrance.repind.service.reference.internal.RefTypExtIDDS;
import com.airfrance.repind.util.service.ExternalIdentifierUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExternalIdentifierDS {

    /** logger */
    private static final Log log = LogFactory.getLog(ExternalIdentifierDS.class);

    /** the Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;
    
    @Autowired
    private ExternalIdentifierRepository externalIdentifierRepository;
    
    @Autowired
    private IndividuRepository individuRepository;

    /*PROTECTED REGION ID(_TY4tNE4kEeS-eLH--0fARw u var) ENABLED START*/
    
    @Autowired
    private IndividuDS individuDS;
    
    @Autowired
    private RefLanguageDS refLanguageDS;
    
    @Autowired
    private RefTypExtIDDS refTypExtIDDS;
    
    @Autowired
    protected ExternalIdentifierUS externalIdentifierUS;
    
    public static final int NB_MAX_EXTERNAL_IDENTIFIER = 100;
    public static final int NB_MAX_EXTERNAL_IDENTIFIER_DATA = 100;
	public static final int NB_MAX_SOCIAL_ID = 10;

	private static final String ILLEGAL_EXCEPTION_ARGUMENT_MESSAGE = "Unable to find external identifier without GIN";
    
    @Transactional(readOnly=true)
    public ExternalIdentifierDTO get(ExternalIdentifierDTO dto) throws JrafDomainException {
		return get(dto.getIdentifierId());
    }

    @Transactional(readOnly=true)
    public ExternalIdentifierDTO get(Serializable oid) throws JrafDomainException {
    	Optional<ExternalIdentifier> email = externalIdentifierRepository.findById((long) oid);
		if (!email.isPresent())
			return null;
		
		return ExternalIdentifierTransform.bo2DtoLight(email.get());
    }

    /**
     * @return EntityManager
     */ 
    public EntityManager getEntityManager() {
        /*PROTECTED REGION ID(_TY4tNE4kEeS-eLH--0fARwgem ) ENABLED START*/
        return entityManager;
        /*PROTECTED REGION END*/
    }

    /**
     *  @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /*PROTECTED REGION ID(_TY4tNE4kEeS-eLH--0fARw u m) ENABLED START*/

    @Transactional(readOnly=true)
	public List<ExternalIdentifierDTO> findExternalIdentifier(String gin) throws JrafDomainException {
		
		// gin is required
		if(StringUtils.isEmpty(gin)) {
			throw new IllegalArgumentException(ILLEGAL_EXCEPTION_ARGUMENT_MESSAGE);
		}
		
		// find external identifiers linked to provided GIN
		List<ExternalIdentifier> externalIdentifierList = externalIdentifierRepository.findExternalIdentifier(gin);
		
		// transform to DTO with external identifier data
		return ExternalIdentifierTransform.bo2DtoForProvide(externalIdentifierList);
	}


	@Transactional(readOnly=true)
	public List<ExternalIdentifierDTO> findExternalIdentifierByGinAndType(String gin, String type) throws JrafDomainException {

		// gin and type are required
		if(StringUtils.isEmpty(gin) && StringUtils.isEmpty(type)) {
			throw new InvalidParameterException("Unable to find external identifier without GIN and type");
		}

		// find external identifiers linked to provided GIN and type
		List<ExternalIdentifier> externalIdentifierList = externalIdentifierRepository.findByGinAndTypeOrderByModificationDateAsc(gin, type);

		// transform to DTO with external identifier data
		return ExternalIdentifierTransform.bo2DtoForProvide(externalIdentifierList);
	}

    @Transactional(readOnly=true)
	public List<ExternalIdentifierDTO> findExternalIdentifierPNMAndGIGYA(String gin) throws JrafDomainException {
		
		// gin is required
		if(StringUtils.isEmpty(gin)) {
			throw new IllegalArgumentException(ILLEGAL_EXCEPTION_ARGUMENT_MESSAGE);
		}
		
		// find external identifiers linked to provided GIN
		List<ExternalIdentifier> externalIdentifierList = externalIdentifierRepository.findExternalIdentifierPNMAndGIGYA(gin);
		
		// transform to DTO with external identifier data
		return ExternalIdentifierTransform.bo2DtoForProvide(externalIdentifierList);
	}

    @Transactional(readOnly=true)
	public List<ExternalIdentifierDTO> findExternalIdentifierALL(String gin) throws JrafDomainException {
		
		// gin is required
		if(StringUtils.isEmpty(gin)) {
			throw new IllegalArgumentException(ILLEGAL_EXCEPTION_ARGUMENT_MESSAGE);
		}
		
		// find external identifiers linked to provided GIN
		List<ExternalIdentifier> externalIdentifierList = externalIdentifierRepository.findExternalIdentifierALL(gin);
		
		// transform to DTO with external identifier data
		return ExternalIdentifierTransform.bo2DtoForProvide(externalIdentifierList);
	}

    @Transactional(readOnly=true)
	public String existExternalIdentifierByGIGYA(String gigya) throws JrafDomainException {
		
		// GIGYA is required
		if(StringUtils.isEmpty(gigya)) {
			throw new IllegalArgumentException("Unable to find external identifier GIGYA without GIGYA");
		}
		
		// find if exist an external identifiers linked to provided GIGYA
		return externalIdentifierRepository.existExternalIdentifierByGIGYA(gigya);
	}

    @Transactional(readOnly=true)
    public ExternalIdentifierDTO existExternalIdentifier(String extID, String type) throws JrafDomainException {
    	
    	if (StringUtils.isEmpty((extID)) || StringUtils.isEmpty(type)) {
    		throw new IllegalArgumentException("Unable to find external identifier without External ID or External Type");
    	}
    	
    	ExternalIdentifier extIdentifier = externalIdentifierRepository.existExternalIdentifier(extID, type);
    	
    	return ExternalIdentifierTransform.bo2DtoForProvide(extIdentifier);
    }
    

    @Transactional(readOnly=true)
	public ExternalIdentifierDTO findExternalIdentifier(ExternalIdentifierDTO externalIdentifierDTO) throws JrafDomainException {
			
		// external identifier and individual are required
		if(externalIdentifierDTO==null || externalIdentifierDTO.getIndividu()==null) {
			throw new IllegalArgumentException("Unable to find external identifier");
		}
		
		// transform external identifier to BO with individual link
		ExternalIdentifier externalIdentifier = ExternalIdentifierTransform.dto2BoForFind(externalIdentifierDTO);
		
		// find external identifier
		externalIdentifier = externalIdentifierRepository.findExternalIdentifier(externalIdentifier);
		
		// transform to DTO with external identifier data
		return ExternalIdentifierTransform.bo2DtoForProvide(externalIdentifier);
	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateExternalIdentifier(String gin, List<ExternalIdentifierDTO> externalIdentifierListFromWS, SignatureDTO signatureFromAPP, String process) throws JrafDomainException {
			
		// nothing to do
    	if(externalIdentifierListFromWS==null || externalIdentifierListFromWS.isEmpty()) {
    		return;
    	}
		
		// get individual from database
		IndividuDTO invidividuFromDB = individuDS.getByGin(gin);
		
		// no individual -> error
		if(invidividuFromDB==null) {
			throw new IndivudualNotFoundException("Unable to find individual linked to following gin: "+gin);
		}
		
		// check external identifiers
		checkExternalIdentifier(externalIdentifierListFromWS);
		
		// loop on external identifiers provided in input
		for (ExternalIdentifierDTO externalIdentifierFromWS : externalIdentifierListFromWS) {			

		    // update external identifier
			updateExternalIdentifier(invidividuFromDB, externalIdentifierFromWS, signatureFromAPP);
		}
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void deleteExternalIdentifierGigyaNotInRequest(String gin, List<ExternalIdentifierDTO> externalIdentifierListFromWS, SignatureDTO signatureFromAPP) throws JrafDomainException {
				
		if (externalIdentifierListFromWS != null) {

			StringBuilder externalIdentifierList = new StringBuilder();
			
			// Construction de la liste de valeur à ne pas supprimer
			for (ExternalIdentifierDTO externalIdentifier : externalIdentifierListFromWS) {
				
				if (externalIdentifier != null && !"".equals(externalIdentifier.getIdentifier()) && 
						refTypExtIDDS.get(externalIdentifier.getType()) != null) {
					
					if ("".equals(externalIdentifierList.toString())) {
						externalIdentifierList.append(externalIdentifier.getIdentifier());
					} else {
						externalIdentifierList.append(",");
						externalIdentifierList.append(externalIdentifier.getIdentifier());
					}
				}
			}
			
			if (!"".equals(externalIdentifierList.toString())) {
				
				// SUppression logique des autres
				externalIdentifierRepository.removeExternalIdentifierNotIn(gin, externalIdentifierList.toString(), signatureFromAPP.getSignature(), signatureFromAPP.getSite());
			}			
		}
	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateExternalIdentifier(String gin, List<ExternalIdentifierDTO> externalIdentifierListFromWS, SignatureDTO signatureFromAPP) throws JrafDomainException {
			
		updateExternalIdentifier(gin, externalIdentifierListFromWS, signatureFromAPP, null);
	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateExternalIdentifier(IndividuDTO individuFromDB, ExternalIdentifierDTO externalIdentifierFromWS, SignatureDTO signatureFromAPP) throws JrafDomainException {
		
		// link identifier to individual
		externalIdentifierFromWS.setIndividu(individuFromDB);
		
		// find matching external identifier in DB
		ExternalIdentifierDTO externalIdentifierFromDB = findExternalIdentifier(externalIdentifierFromWS);

		// no external identifier found in DB -> this is a creation
		if (externalIdentifierFromDB == null) {

			// Put dictionary of external identifier list according to their type
			Map<String, List<ExternalIdentifierDTO>> dictionary = socialIdDictionary(individuFromDB.getExternalIdentifierList());

			// If no social IDs for the corresponding Individual, then skip
			if (dictionary != null) {
				//check for each type, if the size is greater than 10 -> remove last and add create new socialId
				for (Map.Entry<String, List<ExternalIdentifierDTO>> entry : dictionary.entrySet()) {
					String key = entry.getKey();
					List<ExternalIdentifierDTO> socialIdList = entry.getValue();

					boolean isOverLimit = isNbSocialIdOverLimit(socialIdList);

					if (externalIdentifierFromWS.getType().equals(key) && isOverLimit) {
						socialIdList.sort(Comparator.comparing(pnm -> pnm.getModificationDate()));
						deleteOldestExternalIdentifier(individuFromDB, socialIdList, NB_MAX_SOCIAL_ID);
					}
				}
			}

			individuRepository.flush();

			// prepare external identifier for creation
			ExternalIdentifierUtils.prepareExternalIdentifierForCreation(externalIdentifierFromWS, signatureFromAPP);
			
			// create external identifier
			createWithLinkedData(externalIdentifierFromWS);

		}
		// a matching external identifier was found in DB -> this is an update
		else {
			
			// prepare external identifier for update
			ExternalIdentifierUtils.prepareExternalIdentifierForUpdate(externalIdentifierFromWS, externalIdentifierFromDB, signatureFromAPP);
			
			// update external identifier
			updateWithLinkedData(externalIdentifierFromDB);
			
		}
		
	}

	/**
	 *
	 * @param individuFromDB
	 * @param socialIdList
	 * @param nbMaxSocialId
	 *
	 * Delete from database all SOCIAL_ID that exceed nbMaxPnmId parameter
	 */
	private void deleteOldestExternalIdentifier(IndividuDTO individuFromDB, List<ExternalIdentifierDTO> socialIdList, int nbMaxSocialId) throws JrafDomainException {
		// 1 : extract Ids to delete
		List<ExternalIdentifierDTO> listToDeleted = socialIdList.subList(0, socialIdList.size() - nbMaxSocialId + 1);

		// 2 : Remove it from IndividuFromDB
		individuFromDB.getExternalIdentifierList().removeAll(listToDeleted);

		// 3 : If the individu has some Account Data, do not erase it all when deleting the EI ; else do nothing
		if (individuFromDB.getAccountdatadto() != null){
			individuFromDB.getAccountdatadto().setIndividudto(individuFromDB);
		}

		individuRepository.save(IndividuTransform.dto2Bo(individuFromDB));

	}

	private Map<String, List<ExternalIdentifierDTO>> socialIdDictionary(List<ExternalIdentifierDTO> externalIdentifierList) {
		if (CollectionUtils.isEmpty(externalIdentifierList)) {
			return Collections.emptyMap();
		}

		return externalIdentifierList.stream()
				.collect(Collectors.groupingBy(ExternalIdentifierDTO::getType));
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createWithLinkedData(ExternalIdentifierDTO externalIdentifierDTO) throws JrafDomainException {
		
		// build external identifier with linked data
        ExternalIdentifier externalIdentifier = ExternalIdentifierTransform.dto2BoForCreation(externalIdentifierDTO);

        // create in database (call the abstract class)
        externalIdentifierRepository.saveAndFlush(externalIdentifier);

        // update DTO 
        ExternalIdentifierTransform.bo2DtoLight(externalIdentifier, externalIdentifierDTO);
		
	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateWithLinkedData(ExternalIdentifierDTO externalIdentifierDTO) throws JrafDomainException {
		
		// get external identifier in database
		Optional<ExternalIdentifier> externalIdentifier = externalIdentifierRepository.findById(externalIdentifierDTO.getIdentifierId());

		if (externalIdentifier.isPresent()) {
			// get linked data
			Set<ExternalIdentifierData> externalIdentifierDataList = externalIdentifier.get().getExternalIdentifierDataList();

			// clear linked data
			if(externalIdentifierDataList!=null && !externalIdentifierDataList.isEmpty()) {
				externalIdentifierDataList.clear();
			}

			// update BO
			ExternalIdentifierTransform.dto2BoForUpdate(externalIdentifierDTO, externalIdentifier.get());
		}
	}
	
	public void checkExternalIdentifier(List<ExternalIdentifierDTO> externalIdentifierListFromWS) throws JrafDomainException {

		// no provided external identifier -> nothing to do
		if(externalIdentifierListFromWS==null || externalIdentifierListFromWS.isEmpty()) {
			return;
		}
		
		// too many external identifier -> error
		if(externalIdentifierListFromWS.size()>NB_MAX_EXTERNAL_IDENTIFIER) {
			throw new InvalidParameterException("No more than "+NB_MAX_EXTERNAL_IDENTIFIER+" external identifiers supported");
		}
		
		// check all external identifiers
		for(ExternalIdentifierDTO externalIdentifierFromWS : externalIdentifierListFromWS) {
			checkExternalIdentifier(externalIdentifierFromWS);
			checkExternalIdentifierData(externalIdentifierFromWS.getExternalIdentifierDataList());
		}
		
	}
	
	public void checkExternalIdentifier(ExternalIdentifierDTO externalIdentifierFromWS) throws JrafDomainException {
		
		// get params
		String identifier = externalIdentifierFromWS.getIdentifier();
		String type = externalIdentifierFromWS.getType();
		
		// invalid type -> error
		if(!refTypExtIDDS.isValid(type)) {
			throw new InvalidExternalIdentifierTypeException(type);
		}

		// check PNM ID format
		if (ExternalIdentifierTypeEnum.PNM_ID.getType().equals(type) && !FormatUtils.isValidPnmId(identifier)) {
			throw new InvalidPnmIdException(identifier);
		}
	}
	
	
	private void checkExternalIdentifierData(List<ExternalIdentifierDataDTO> externalIdentifierDataListFromWS) throws JrafDomainException {

		// no provided external identifier data -> nothing to do
		if(externalIdentifierDataListFromWS==null || externalIdentifierDataListFromWS.isEmpty()) {
			return;
		}
		
		// too many external identifier -> error
		if(externalIdentifierDataListFromWS.size()>NB_MAX_EXTERNAL_IDENTIFIER_DATA) {
			throw new InvalidParameterException("No more than "+NB_MAX_EXTERNAL_IDENTIFIER_DATA+" external identifier data supported");
		}
		
		// duplicates -> error
		if(ExternalIdentifierUtils.hasDuplicates(externalIdentifierDataListFromWS)) {
			throw new InvalidParameterException("External data keys have to be unique");
		}
		
		// check all external identifier data
		for(ExternalIdentifierDataDTO externalIdentifierDataFromWS : externalIdentifierDataListFromWS) {
			checkExternalIdentifierData(externalIdentifierDataFromWS);
		}
	
	}
	
	private void checkExternalIdentifierData(ExternalIdentifierDataDTO externalIdentifierDataFromWS) throws JrafDomainException {
		
		// get params
		String key = externalIdentifierDataFromWS.getKey();
		String value = externalIdentifierDataFromWS.getValue();

		// invalid key -> error
		if(!ExternalIdentifierDataKeyEnum.isValid(key)) {
			throw new InvalidExternalIdentifierDataKeyException(key);
		}
		
		// get key enum
		ExternalIdentifierDataKeyEnum keyEnum = ExternalIdentifierDataKeyEnum.getEnum(externalIdentifierDataFromWS.getKey());
		
		switch(keyEnum) {
		
			case PNM_NAME:
				
				// invalid PNM name -> error
				if(!PnmNameEnum.isValid(value)) {
					throw new InvalidExternalIdentifierDataValueException(value);
				}
				
			break;
		
			case APPLICATION_LANGUAGE_CODE:
				
				// invalid application language code -> error
				boolean isValid = !refLanguageDS.isValidLanguageCode(value);

				if(isValid) {
					throw new InvalidLanguageCodeException(value);
				}
			
			break;
			
			case PNM_AIRLINE:
				
				// invalid PNM airline -> error
				if(!PnmAirlineEnum.isValid(value)) {
					throw new InvalidExternalIdentifierDataValueException(value);
				}
			
			break;
			
			case OPTIN: 
			case USED_BY_AF:
			case USED_BY_KL:
				// invalid OPTIN -> error
				if(!YesNoFlagEnum.isValid(value)) {
					throw new InvalidExternalIdentifierDataValueException(value);
				}
				
			break;
			
			case APPLICATION_NAME:
			case DEVICE_NAME:
				
				// empty value -> error
				if(StringUtils.isEmpty(value)) {
					throw new InvalidExternalIdentifierDataValueException(value);
				}
			
			break;
			
		}
		
	}

	/**
	 *
	 * @param socialIdList List of SOCIAL_ID from DB
	 * @return true if EID from DB size exceed 10
	 * return false if EID from DB is null or size less than 9
	 */
	private Boolean isNbSocialIdOverLimit(List<ExternalIdentifierDTO> socialIdList) {
		boolean result = false;

		if (!CollectionUtils.isEmpty(socialIdList)) {
			return socialIdList.size() >= NB_MAX_SOCIAL_ID;
		}
		else {
			return result;
		}
	}
	
	
	@Transactional(rollbackFor = JrafDomainNoRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void resetAllAlias(String gin) throws JrafDomainException {
		
		if (log.isInfoEnabled()) log.info("All Alias must be erased !");

		try {		
			Individu individu = individuRepository.findBySgin(gin);
			if (individu.getExternalIdentifierList() != null) {
				for (Iterator<ExternalIdentifier> iterator = individu.getExternalIdentifierList().iterator(); iterator.hasNext(); ) {
					ExternalIdentifier externalIdentifier = iterator.next();
					if (AliasIdentifierTypeEnum.getAllAlias() != null && AliasIdentifierTypeEnum.getAllAlias().contains(externalIdentifier.getType())) {
						iterator.remove();
						externalIdentifierRepository.delete(externalIdentifier);
					}
				}
			}
		} catch (Exception e ) {
			log.error("ERROR: Error during deleting all Alias !");
			throw new JrafDomainRollbackException(e);
		}
	}
	
	
	public void setExternalIdentifierUS(ExternalIdentifierUS externalIdentifierUS) {
		this.externalIdentifierUS = externalIdentifierUS;
		
	}

	
	public ExternalIdentifierUS getExternalIdentifierUS() {
		return externalIdentifierUS;
	}

	
	public List<ExternalIdentifierDTO> findByExample(ExternalIdentifierDTO externalIdentifier) throws JrafDomainException {
		ExternalIdentifier email = ExternalIdentifierTransform.dto2BoLight(externalIdentifier);
		List<ExternalIdentifierDTO> result = new ArrayList<>();
		for (ExternalIdentifier found : externalIdentifierRepository.findAll(Example.of(email))) {
			result.add(ExternalIdentifierTransform.bo2DtoLight(found));
			}
		return result;
	}

	
	public List<ExternalIdentifierDTO> findByTypeAndId(String identifierType, String identifierId) throws JrafDomainException {
		List<ExternalIdentifierDTO> result = new ArrayList<>();
		for (ExternalIdentifier found : externalIdentifierRepository.findByTypeAndIdentifier(identifierType, identifierId)) {
			result.add(ExternalIdentifierTransform.bo2DtoLight(found));
			}
		return result;
	}
	
	
	public int getNumberExternalIdentifierByGin(String gin) throws JrafDaoException {
		return externalIdentifierRepository.getNumberExternalIdentifierByGin(gin);
	}
	
}
